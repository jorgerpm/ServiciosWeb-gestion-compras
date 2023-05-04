/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.AutorizacionOrdenCompra;
import com.idebsystems.serviciosweb.entities.Comparativo;
import com.idebsystems.serviciosweb.entities.OrdenCompra;
import com.idebsystems.serviciosweb.entities.OrdenCompraDetalle;
import com.idebsystems.serviciosweb.entities.Parametro;
import com.idebsystems.serviciosweb.entities.Usuario;
import com.idebsystems.serviciosweb.util.Persistencia;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author jorge
 */
public class OrdenCompraDAO extends Persistencia {
    
    private static final Logger LOGGER = Logger.getLogger(OrdenCompraDAO.class.getName());
    
    public OrdenCompra generarOrdenCompra(OrdenCompra ordenCompra, Comparativo comparativo) throws Exception {
        try{
            getEntityManager();

            em.getTransaction().begin();

            //aqui generar el secuencial para el codigo_oc
            String seqCodOC = getUltimoCodigoOC(em);
            ordenCompra.setCodigoOrdenCompra(seqCodOC);
            
            
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
                    + " WHERE s.codigoSolicitud = :codigoSolicitud");
            query.setParameter("codigoSolicitud", ordenCompra.getCodigoSolicitud());
            query.setParameter("usuarioModifica", ordenCompra.getUsuarioModifica());
            query.setParameter("fechaModifica", new Date());
            query.executeUpdate();
            
            //colocar a todas las cotizaciones como rechazadas
            //y que la solicitud seleccionada quede como GENERADO_OC
            query = em.createQuery("UPDATE Cotizacion c SET c.estado = 'RECHAZADO', c.usuarioModifica = :usuarioModifica, c.fechaModifica = :fechaModifica "
                    + " WHERE c.codigoRC = :codigoRc AND c.codigoCotizacion <> :codigoCotizacion");
            query.setParameter("codigoRc", ordenCompra.getCodigoRC());
            query.setParameter("codigoCotizacion", ordenCompra.getCodigoSolicitud().concat("-").concat(ordenCompra.getRucProveedor()));
            query.setParameter("usuarioModifica", ordenCompra.getUsuarioModifica());
            query.setParameter("fechaModifica", new Date());
            query.executeUpdate();
            
            query = em.createQuery("UPDATE Cotizacion c SET c.estado = 'GENERADO_OC', c.usuarioModifica = :usuarioModifica, c.fechaModifica = :fechaModifica "
                    + " WHERE c.codigoCotizacion = :codigoCotizacion");
            query.setParameter("codigoCotizacion", ordenCompra.getCodigoSolicitud().concat("-").concat(ordenCompra.getRucProveedor()));
            query.setParameter("usuarioModifica", ordenCompra.getUsuarioModifica());
            query.setParameter("fechaModifica", new Date());
            query.executeUpdate();
            
            
            ///guardar el comparativo
            em.persist(comparativo);
            
            comparativo.getListaDetalles().forEach(detalle -> {
                detalle.setComparativo(comparativo);
                em.persist(detalle);
            });
            
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
            String codigoSolicitud, Integer desde, Integer hasta) throws Exception {
        try {
            List<Object> respuesta = new ArrayList<>();
            getEntityManager();

            String sql = "FROM OrdenCompra oc ";

            if (Objects.nonNull(codigoRC) && !codigoRC.isBlank()) {
                sql = sql.concat(" WHERE UPPER(oc.codigoRC) = :codigoRC ");
            }
            else if (Objects.nonNull(codigoSolicitud) && !codigoSolicitud.isBlank()) {
                sql = sql.concat(" WHERE UPPER(oc.codigoSolicitud) = :codigoSolicitud ");
            }
            else{
                sql = sql.concat(" WHERE oc.fechaOrdenCompra BETWEEN :fechaInicial AND :fechaFinal ");
            }

            sql = sql.concat(" order by oc.fechaOrdenCompra");

            Query query = em.createQuery(sql);

            if (Objects.nonNull(codigoRC) && !codigoRC.isBlank()) {
                query.setParameter("codigoRC", codigoRC.toUpperCase());
            }
            else if (Objects.nonNull(codigoSolicitud) && !codigoSolicitud.isBlank()) {
                query.setParameter("codigoSolicitud", codigoSolicitud.toUpperCase());
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
            
            //cambiar el estado de la solicitud
            Query query = em.createQuery("UPDATE Solicitud s SET s.estado = :estado, s.usuarioModifica = :usuarioModifica, s.fechaModifica = :fechaModifica "
                    + " WHERE s.codigoSolicitud = :codigoSolicitud");
            query.setParameter("estado", ordenCompra.getEstado());
            query.setParameter("codigoSolicitud", ordenCompra.getCodigoSolicitud());
            query.setParameter("usuarioModifica", ordenCompra.getUsuarioModifica());
            query.setParameter("fechaModifica", new Date());
            query.executeUpdate();
            
            //cambiar el estado de la cotizacion
            query = em.createQuery("UPDATE Cotizacion c SET c.estado = :estado, c.usuarioModifica = :usuarioModifica, c.fechaModifica = :fechaModifica "
                    + " WHERE c.codigoCotizacion = :codigoCotizacion");
            query.setParameter("estado", ordenCompra.getEstado());
            query.setParameter("codigoCotizacion", ordenCompra.getCodigoSolicitud().concat("-").concat(ordenCompra.getRucProveedor()));
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
    
    
    public List<Object[]> listarOrdenesPorAutorizar(String codigoRC, String codigoSolicitud, Usuario userSesion, boolean rolPrincipal) throws Exception {
        try {
            getEntityManager();

            String sql = "select oc.id, oc.fecha_orden_compra, oc.codigo_orden_compra, " +
"	oc.codigo_rc, " +
"	oc.codigo_solicitud, " +
"	oc.estado, " +
"	oc.usuario, " +
"	oc.ruc_proveedor, " +
"	oc.observacion, " +
"	oc.subtotal, " +
"	oc.subtotal_sin_iva, " +
"	oc.iva, " +
"	oc.total, " +
"	oc.descuento, " +
"	oc.forma_pago, " +
"	oc.usuario_modifica, " +
"	oc.fecha_modifica, "
                    + " u.nombre, oc.unidad_negocio_rc, oc.detalle_final "
                    + " from orden_compra oc, autorizacion_orden_compra aoc, usuario u " +
                        " where oc.id = aoc.id_orden_compra AND aoc.id_usuario = u.id " +
                        " AND oc.estado IN ('POR_AUTORIZAR', 'AUTORIZADO_TEMP') AND aoc.estado IS NULL " ;
                        

//            String sql = "select oc from OrdenCompra oc, AutorizacionOrdenCompra aoc where oc.id = aoc.idOrdenCompra AND oc.estado = :estado ";

            if(Objects.nonNull(codigoRC) && !codigoRC.isBlank()){
                sql = sql.concat(" AND oc.codigo_rc = ?codigoRC ");
            }
            if(Objects.nonNull(codigoSolicitud) && !codigoSolicitud.isBlank()){
                sql = sql.concat(" AND oc.codigo_solicitud = ?codigoSolicitud ");
            }
            
            if(userSesion.getIdRol() != 1){ //si es un rol diferente al administrador
                if(!rolPrincipal){
                    sql = sql.concat(" and aoc.id_usuario = ?idUsuario ");
                }
            }
            
            sql = sql.concat(" order by oc.fecha_orden_compra");

            Query query = em.createNativeQuery(sql);
//            query.setParameter("estado", "POR_AUTORIZAR");
            
            if(Objects.nonNull(codigoRC) && !codigoRC.isBlank()){
                query.setParameter("codigoRC", codigoRC.toUpperCase());
            }
            if(Objects.nonNull(codigoSolicitud) && !codigoSolicitud.isBlank()){
                query.setParameter("codigoSolicitud", codigoSolicitud.toUpperCase());
            }
            
            if(userSesion.getIdRol() != 1){ //si es un rol diferente al administrador
                if(!rolPrincipal){
                    query.setParameter("idUsuario", userSesion.getId());
                }
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
    
    
    public String getUltimoCodigoOC(EntityManager em) throws Exception {
        try{
            String numSql = "select MAX(replace (CODIGO_ORDEN_COMPRA, 'OC-', '')) from orden_compra";
            Query queryNumSql = em.createNativeQuery(numSql);
            
            List<String> lista = queryNumSql.getResultList();
            if(lista.isEmpty()){
                //buscar en los parametros y tomar ese numero del parametro
                Query query = em.createQuery("FROM Parametro p where p.nombre = :nombre");
                query.setParameter("nombre", "CODIGO_ORDEN_COMPRA");

                List<Parametro> listas = query.getResultList();

                if(!listas.isEmpty()){
                    return "OC-" + listas.get(0).getValor();
                }
                else{
                    return "OC-1";
                }
            }
            else{
                if(Objects.nonNull(lista.get(0))){
                    Long next = Long.parseLong(lista.get(0)) + 1;
                    return "OC-" + next;
                }
                else{
                    //buscar en los parametros y tomar ese numero del parametro
                    Query query = em.createQuery("FROM Parametro p where p.nombre = :nombre");
                    query.setParameter("nombre", "CODIGO_ORDEN_COMPRA");

                    List<Parametro> listas = query.getResultList();

                    if(!listas.isEmpty()){
                        return "OC-" + listas.get(0).getValor();
                    }
                    else{
                        return "OC-1";
                    }
                }
            }

//            Query query = em.createQuery("FROM Parametro p where p.nombre = :nombre");
//            query.setParameter("nombre", "CODIGO_SOLICITUD");
//            
//            List<Parametro> listas = query.getResultList();
//            
//            if(!listas.isEmpty()){
//                Parametro param = listas.get(0);
//                Long next = Long.parseLong(param.getValor()) + 1;
//                
//                param.setValor(next.toString());
//                em.getTransaction().begin();
//                em.merge(param);
//                em.getTransaction().commit();
//                return next.toString();
//            }
//            return "1";
            
        } catch (NoResultException exc) {
            return null;
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }finally {
            //closeEntityManager(); aqui no se debe cerrar
        }
    }
    
    
    
    
    public OrdenCompra actualizarOrdenCompra(OrdenCompra ordenCompra) throws Exception {
        try{
            getEntityManager();

            em.getTransaction().begin();
            
            em.merge(ordenCompra); //update
            
            for(OrdenCompraDetalle detalle : ordenCompra.getListaDetalles()) {
                LOGGER.log(Level.INFO,"lod etalles en el dao");
                detalle.setOrdenCompra(ordenCompra);
                em.merge(detalle);
                LOGGER.log(Level.INFO,"si actualizo");
            }
            
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
}
