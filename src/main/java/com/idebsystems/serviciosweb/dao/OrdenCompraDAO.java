/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.AutorizacionOrdenCompra;
import com.idebsystems.serviciosweb.entities.OrdenCompra;
import com.idebsystems.serviciosweb.util.Persistencia;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author jorge
 */
public class OrdenCompraDAO extends Persistencia {
    
    private static final Logger LOGGER = Logger.getLogger(OrdenCompraDAO.class.getName());
    
    public OrdenCompra generarOrdenCompra(OrdenCompra ordenCompra) throws Exception {
        try{
            getEntityManager();

            em.getTransaction().begin();

            if (Objects.nonNull(ordenCompra.getId()) && ordenCompra.getId() > 0) {
                em.merge(ordenCompra); //update
            } else {
                em.persist(ordenCompra); //insert
            }
            
            ordenCompra.getListaDetalles().forEach(detalle -> {
                detalle.setOrdenCompra(ordenCompra);
                em.persist(detalle);
            });
            
            //cambiar el estado de la solicitud a GENERADO_OC
            Query query = em.createQuery("UPDATE Solicitud s SET s.estado = 'GENERADO_OC', s.usuarioModifica = :usuarioModifica, s.fechaModifica = :fechaModifica "
                    + " WHERE s.codigoRC = :codigoRc");
            query.setParameter("codigoRc", ordenCompra.getCodigoRC());
            query.setParameter("usuarioModifica", ordenCompra.getUsuarioModifica());
            query.setParameter("fechaModifica", new Date());
            query.executeUpdate();
            
            //colocar a todas las cotizaciones como rechazadas
            //y que la solicitud seleccionada quede como GENERADO_OC
            query = em.createQuery("UPDATE Cotizacion c SET c.estado = 'RECHAZADO', c.usuarioModifica = :usuarioModifica, c.fechaModifica = :fechaModifica "
                    + " WHERE c.codigoRC = :codigoRc AND c.codigoCotizacion <> :codigoCotizacion");
            query.setParameter("codigoRc", ordenCompra.getCodigoRC());
            query.setParameter("codigoCotizacion", ordenCompra.getCodigoRC().concat("-").concat(ordenCompra.getRucProveedor()));
            query.setParameter("usuarioModifica", ordenCompra.getUsuarioModifica());
            query.setParameter("fechaModifica", new Date());
            query.executeUpdate();
            
            query = em.createQuery("UPDATE Cotizacion c SET c.estado = 'GENERADO_OC', c.usuarioModifica = :usuarioModifica, c.fechaModifica = :fechaModifica "
                    + " WHERE c.codigoCotizacion = :codigoCotizacion");
            query.setParameter("codigoCotizacion", ordenCompra.getCodigoRC().concat("-").concat(ordenCompra.getRucProveedor()));
            query.setParameter("usuarioModifica", ordenCompra.getUsuarioModifica());
            query.setParameter("fechaModifica", new Date());
            query.executeUpdate();
            
            em.flush(); //Confirmar el insert o update

            em.getTransaction().commit();

            return ordenCompra;
            
        } catch (SQLException exc) {
            rollbackTransaction();
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } catch (Exception exc) {
            rollbackTransaction();
            if(exc.getMessage() != null && exc.getMessage().contains("orden_compra_codigo_orden_compra_uk"))
                throw new Exception("YA EXISTE EL CODIGO DE ORDEN DE COMPRA");
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    public List<Object> listarOrdenesCompras(Date fechaInicial, Date fechaFinal, String codigoRC,
            Integer desde, Integer hasta) throws Exception {
        try {
            List<Object> respuesta = new ArrayList<>();
            getEntityManager();

            String sql = "FROM OrdenCompra oc ";

            if (Objects.nonNull(codigoRC) && !codigoRC.isBlank()) {
                sql = sql.concat(" WHERE UPPER(oc.codigoRC) = :codigoRC ");
            }
            else{
                sql = sql.concat(" WHERE oc.fechaOrdenCompra BETWEEN :fechaInicial AND :fechaFinal ");
            }

            sql = sql.concat(" order by oc.fechaOrdenCompra");

            Query query = em.createQuery(sql);

            if (Objects.nonNull(codigoRC) && !codigoRC.isBlank()) {
                query.setParameter("codigoRC", codigoRC.toUpperCase());
            }
            else{
                query.setParameter("fechaInicial", fechaInicial);
                query.setParameter("fechaFinal", fechaFinal);
            }

            //para obtener el total de los registros a buscar
            Integer totalRegistros = query.getResultList().size();
            respuesta.add(totalRegistros);

            //para la paginacion
            query.setFirstResult(desde).setMaxResults(hasta);

            List<OrdenCompra> listaOrdenCompra = query.getResultList();
            respuesta.add(listaOrdenCompra);

            //new BufferedReader(new InputStreamReader(inputstream));
            
            return respuesta;

        } catch (NoResultException exc) {
            return null;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    public OrdenCompra buscarOrdenCompraID(Long idOrdenCompra) throws Exception {
        try {
            
            getEntityManager();

            OrdenCompra orden = em.find(OrdenCompra.class, idOrdenCompra);
            
            return orden;

        } catch (NoResultException exc) {
            return null;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    public OrdenCompra autorizarOrdenCompra(OrdenCompra ordenCompra, AutorizacionOrdenCompra autorizacion) throws Exception {
        try{
            getEntityManager();

            em.getTransaction().begin();
            
            em.merge(ordenCompra); //update
            
            if(Objects.nonNull(autorizacion.getId()) && autorizacion.getId() > 0){
                em.merge(autorizacion);
            }
            else{
                em.persist(autorizacion);
            }
            
            //cambiar el estado de la solicitud a GENERADO_OC
            Query query = em.createQuery("UPDATE Solicitud s SET s.estado = :estado, s.usuarioModifica = :usuarioModifica, s.fechaModifica = :fechaModifica "
                    + " WHERE s.codigoRC = :codigoRc");
            query.setParameter("estado", ordenCompra.getEstado());
            query.setParameter("codigoRc", ordenCompra.getCodigoRC());
            query.setParameter("usuarioModifica", ordenCompra.getUsuarioModifica());
            query.setParameter("fechaModifica", new Date());
            query.executeUpdate();
            
            //cambiar el estado de la cotizacion
            query = em.createQuery("UPDATE Cotizacion c SET c.estado = :estado, c.usuarioModifica = :usuarioModifica, c.fechaModifica = :fechaModifica "
                    + " WHERE c.codigoCotizacion = :codigoCotizacion");
            query.setParameter("estado", ordenCompra.getEstado());
            query.setParameter("codigoCotizacion", ordenCompra.getCodigoRC().concat("-").concat(ordenCompra.getRucProveedor()));
            query.setParameter("usuarioModifica", ordenCompra.getUsuarioModifica());
            query.setParameter("fechaModifica", new Date());
            query.executeUpdate();
            
            em.flush(); //Confirmar el insert o update

            em.getTransaction().commit();

            return ordenCompra;
            
        } catch (SQLException exc) {
            rollbackTransaction();
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } catch (Exception exc) {
            rollbackTransaction();
            if(exc.getMessage() != null && exc.getMessage().contains("orden_compra_codigo_orden_compra_uk"))
                throw new Exception("YA EXISTE EL CODIGO DE ORDEN DE COMPRA");
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    
    public List<Object[]> listarOrdenesPorAutorizar(String codigoRC, Long idUsuario, boolean rolPrincipal) throws Exception {
        try {
            getEntityManager();

            String sql = "select oc.*, u.nombre from orden_compra oc, autorizacion_orden_compra aoc, usuario u " +
                        " where oc.id = aoc.id_orden_compra AND aoc.id_usuario = u.id " +
                        " AND oc.estado IN ('POR_AUTORIZAR', 'AUTORIZADO_TEMP') AND aoc.estado IS NULL " ;
                        

//            String sql = "select oc from OrdenCompra oc, AutorizacionOrdenCompra aoc where oc.id = aoc.idOrdenCompra AND oc.estado = :estado ";

            if(Objects.nonNull(codigoRC) && !codigoRC.isBlank()){
                sql = sql.concat(" AND oc.codigo_rc = ?codigoRC ");
            }
            if(!rolPrincipal){
                sql = sql.concat(" and aoc.id_usuario = ?idUsuario ");
            }
            sql = sql.concat(" order by oc.fecha_orden_compra");

            Query query = em.createNativeQuery(sql);
//            query.setParameter("estado", "POR_AUTORIZAR");
            
            if(Objects.nonNull(codigoRC) && !codigoRC.isBlank()){
                query.setParameter("codigoRC", codigoRC);
            }
            if(!rolPrincipal){
                query.setParameter("idUsuario", idUsuario);
            }
            
            List<Object[]> listaOrdenCompra = query.getResultList();
            
            return listaOrdenCompra;

        } catch (NoResultException exc) {
            return null;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
}
