/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;


import com.idebsystems.serviciosweb.entities.Cotizacion;
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
public class CotizacionDAO extends Persistencia {

    private static final Logger LOGGER = Logger.getLogger(CotizacionDAO.class.getName());

    public List<Object> listarCotizaciones(Date fechaInicial, Date fechaFinal, String codigoSolicitud, String codigoRC,
            Integer desde, Integer hasta, Integer idRol, String rucProveedor) throws Exception {
        try {
            List<Object> respuesta = new ArrayList<>();
            getEntityManager();

            String sql = "FROM Cotizacion c ";

            if (Objects.nonNull(codigoSolicitud) && !codigoSolicitud.isBlank()) {
                sql = sql.concat(" WHERE UPPER(c.codigoSolicitud) = :codigoSolicitud ");
            }
            else if (Objects.nonNull(codigoRC) && !codigoRC.isBlank()) {
                sql = sql.concat(" WHERE UPPER(c.codigoRC) = :codigoRC ");
            }
            else{
                sql = sql.concat(" WHERE c.fechaCotizacion BETWEEN :fechaInicial AND :fechaFinal ");
            }
            
            if(Objects.nonNull(idRol) && idRol == 2){ //es para proveedores y sacan solo lo del proveedor que ingreso al sistema
                sql = sql.concat(" AND c.rucProveedor = :rucProveedor ");
            }

            sql = sql.concat(" order by c.fechaCotizacion");

            Query query = em.createQuery(sql);

            if (Objects.nonNull(codigoSolicitud) && !codigoSolicitud.isBlank()) {
                query.setParameter("codigoSolicitud", codigoSolicitud.toUpperCase());
            }
            else if (Objects.nonNull(codigoRC) && !codigoRC.isBlank()) {
                query.setParameter("codigoRC", codigoRC.toUpperCase());
            }
            else{
                query.setParameter("fechaInicial", fechaInicial);
                query.setParameter("fechaFinal", fechaFinal);
            }
            
            if(Objects.nonNull(idRol) && idRol == 2){ //es para proveedores y sacan solo lo del proveedor que ingreso al sistema
                query.setParameter("rucProveedor", rucProveedor);
            }

            //para obtener el total de los registros a buscar
            Integer totalRegistros = query.getResultList().size();
            respuesta.add(totalRegistros);

            //para la paginacion
            query.setFirstResult(desde).setMaxResults(hasta);

            List<Cotizacion> listaCotiz = query.getResultList();
            respuesta.add(listaCotiz);

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
    
    
    public Cotizacion guardarCotizacion(Cotizacion cotizacion) throws Exception {
        try{
            getEntityManager();

            em.getTransaction().begin();

            if (Objects.nonNull(cotizacion.getId()) && cotizacion.getId() > 0) {
                em.merge(cotizacion); //update
            } else {
                em.persist(cotizacion); //insert
            }
            
            cotizacion.getListaDetalles().forEach(detalle -> {
                detalle.setCotizacion(cotizacion);
                em.persist(detalle);
            });
            
            //cambiar el estado de la solicitud a cotizado
            Query query = em.createQuery("UPDATE Solicitud s SET s.estado = 'COTIZADO', s.usuarioModifica = :usuarioModifica, s.fechaModifica = :fechaModifica WHERE s.codigoRC = :codigoRc");
            query.setParameter("codigoRc", cotizacion.getCodigoRC());
            query.setParameter("usuarioModifica", cotizacion.getUsuarioModifica());
            query.setParameter("fechaModifica", new Date());
            query.executeUpdate();
            
            em.flush(); //Confirmar el insert o update

            em.getTransaction().commit();

            return cotizacion;
            
        } catch (SQLException exc) {
            rollbackTransaction();
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } catch (Exception exc) {
            rollbackTransaction();
            if(exc.getMessage() != null && exc.getMessage().contains("cotizacion_codigo_cotizacion_uk"))
                throw new Exception("YA EXISTE EL CODIGO DE COTIZACION");
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    
    public Cotizacion buscarCotizacionRucNumeroSol(String codigoSolicitud, String ruc) throws Exception {
        try {
            getEntityManager();

            String sql = "FROM Cotizacion c WHERE UPPER(c.codigoCotizacion) = :codigoCotizacion ";            

            Query query = em.createQuery(sql);
            query.setParameter("codigoCotizacion", codigoSolicitud.toUpperCase().concat("-").concat(ruc));

            if(query.getResultList().isEmpty()){
                return new Cotizacion();
            }
            else{
                return (Cotizacion)query.getResultList().get(0);
            }

        } catch (NoResultException exc) {
            return null;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    
    
    public Cotizacion cambiarEstadoCotizacion(Cotizacion cotizacion) throws Exception {
        try{
            getEntityManager();

            em.getTransaction().begin();

            if (Objects.nonNull(cotizacion.getId()) && cotizacion.getId() > 0) {
                em.merge(cotizacion); //update
            }
            //SOLO SE CAMBIA EL ESTADO A LA COTIZACION, PORQUE SOLO ES A UNA Y NO A TODA LA SOLCIITUD
            //cambiar el estado de la solicitud al estado de la cotizacion que se envia desde pantalla
//            Query query = em.createQuery("UPDATE Solicitud s SET s.estado = :estado, s.usuarioModifica = :usuarioModifica, s.fechaModifica = :fechaModifica WHERE s.codigoRC = :codigoRc");
//            query.setParameter("codigoRc", cotizacion.getCodigoRC());
//            query.setParameter("usuarioModifica", cotizacion.getUsuarioModifica());
//            query.setParameter("fechaModifica", new Date());
//            query.setParameter("estado", cotizacion.getEstado());
//            query.executeUpdate();
            
            em.flush(); //Confirmar el insert o update

            em.getTransaction().commit();

            return cotizacion;
            
        } catch (SQLException exc) {
            rollbackTransaction();
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } catch (Exception exc) {
            rollbackTransaction();
            if(exc.getMessage() != null && exc.getMessage().contains("cotizacion_codigo_cotizacion_uk"))
                throw new Exception("YA EXISTE EL CODIGO DE COTIZACION");
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    
    public Cotizacion buscarCotizacionID(Long id) throws Exception {
        try {
            getEntityManager();

            return em.find(Cotizacion.class, id);

        } catch (NoResultException exc) {
            return null;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    public List<Cotizacion> getCotizacionesParaComparativo(String codigoSolicitud) throws Exception {
        try {
            getEntityManager();

            String sql = "FROM Cotizacion c WHERE UPPER(c.codigoSolicitud) = :codigoSolicitud AND c.estado = 'COTIZADO' ";            

            Query query = em.createQuery(sql);
            query.setParameter("codigoSolicitud", codigoSolicitud.toUpperCase());

            List<Cotizacion> lista = query.getResultList();
            
            return lista;

        } catch (NoResultException exc) {
            return null;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    
    
    public String rechazarTodasCotizaciones(Cotizacion cotizacion) throws Exception {
        try {
            getEntityManager();

            em.getTransaction().begin();
            
            Query query = em.createQuery("UPDATE Cotizacion c SET c.estado = 'RECHAZADO', c.observacion = :observacion, "
                    + " c.usuarioModifica = :usuarioModifica, c.fechaModifica = :fechaModifica "
                    + " WHERE c.codigoSolicitud = :codigoSolicitud AND c.codigoRC = :codigoRC");
            query.setParameter("codigoSolicitud", cotizacion.getCodigoSolicitud());
            query.setParameter("codigoRC", cotizacion.getCodigoRC());
            query.setParameter("usuarioModifica", cotizacion.getUsuarioModifica());
            query.setParameter("fechaModifica", new Date());
            query.setParameter("observacion", cotizacion.getObservacion());
            query.executeUpdate();
            
            //cambiar el estado de la solicitud a cotizado
            query = em.createQuery("UPDATE Solicitud s SET s.estado = 'RECHAZADO', s.observacion = :observacion, "
                    + " s.usuarioModifica = :usuarioModifica, s.fechaModifica = :fechaModifica WHERE s.codigoSolicitud = :codigoSolicitud AND s.codigoRC = :codigoRc");
            query.setParameter("codigoRc", cotizacion.getCodigoRC());
            query.setParameter("codigoSolicitud", cotizacion.getCodigoSolicitud());
            query.setParameter("usuarioModifica", cotizacion.getUsuarioModifica());
            query.setParameter("fechaModifica", new Date());
            query.setParameter("observacion", cotizacion.getObservacion());
            query.executeUpdate();
            
            em.flush(); //Confirmar el insert o update

            em.getTransaction().commit();
            
            return "OK";

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
