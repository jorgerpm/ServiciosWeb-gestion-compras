/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.Parametro;
import com.idebsystems.serviciosweb.entities.Proveedor;
import com.idebsystems.serviciosweb.entities.Solicitud;
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
public class SolicitudDAO extends Persistencia {

    private static final Logger LOGGER = Logger.getLogger(SolicitudDAO.class.getName());

    public List<Object> listarSolicitudes(Date fechaInicial, Date fechaFinal, String codigoSolicitud, String codigoRC,
            Integer desde, Integer hasta) throws Exception {
        try {
            List<Object> respuesta = new ArrayList<>();
            getEntityManager();

            String sql = "FROM Solicitud s ";

            if (Objects.nonNull(codigoSolicitud) && !codigoSolicitud.isBlank()) {
                sql = sql.concat(" WHERE UPPER(s.codigoSolicitud) = :codigoSolicitud ");
            }
            else if (Objects.nonNull(codigoRC) && !codigoRC.isBlank()) {
                sql = sql.concat(" WHERE UPPER(s.codigoRC) = :codigoRC ");
            }
            else{
                sql = sql.concat(" WHERE s.fechaSolicitud BETWEEN :fechaInicial AND :fechaFinal ");
            }

            sql = sql.concat(" order by s.fechaSolicitud");

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

            //para obtener el total de los registros a buscar
            Integer totalRegistros = query.getResultList().size();
            respuesta.add(totalRegistros);

            //para la paginacion
            query.setFirstResult(desde).setMaxResults(hasta);

            List<Solicitud> listaSols = query.getResultList();
            respuesta.add(listaSols);

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
    
    public Solicitud guardarSolicitud(Solicitud solicitud) throws Exception {
        try{
            getEntityManager();

            em.getTransaction().begin();
            
            boolean actualizarParam = false;

            if (Objects.nonNull(solicitud.getId()) && solicitud.getId() > 0) {
                solicitud.getListaDetalles().forEach(s -> {
                    s.setSolicitud(solicitud);
                });
                em.merge(solicitud); //update
                //buscar los detalles de la sol pra eliminarlos y volver a ingresar los nuevos
                Query query = em.createQuery("DELETE FROM SolicitudDetalle d WHERE d.solicitud.id = " + solicitud.getId());
                int tantos = query.executeUpdate();
            } else {
                actualizarParam = true;
                em.persist(solicitud); //insert
               
                //solo se inserta cuando son nueva solicitud, cuando se modifica no se cambia nada de los detalles, 
                //porque se borra el path del archivo.
//                solicitud.getListaDetalles().forEach(detalle -> {
//                    detalle.setSolicitud(solicitud);
//                    em.persist(detalle);
//                });
            }
            
            //insertar los detalles
            solicitud.getListaDetalles().forEach(detalle -> {
                detalle.setSolicitud(solicitud);
                em.persist(detalle);
            });
            
            
            
            //para actualizar el parametro con el codigo de colicitud
            if (actualizarParam) {
                Query query = em.createQuery("FROM Parametro p where p.nombre = :nombre");
                query.setParameter("nombre", "CODIGO_SOLICITUD");
                List<Parametro> listas = query.getResultList();
                if(!listas.isEmpty()){
                    Parametro param = listas.get(0);
                    param.setValor(solicitud.getCodigoSolicitud());
                    em.merge(param);
                }
            }
            //hasta aca//para actualizar el parametro con el codigo de colicitud
            
            
            em.flush(); //Confirmar el insert o update

            em.getTransaction().commit();
            
            LOGGER.log(Level.INFO, "id solic generado: {0}", solicitud.getId());

            return solicitud;
            
        } catch (SQLException exc) {
            rollbackTransaction();
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } catch (Exception exc) {
            rollbackTransaction();
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    
    public Solicitud buscarSolicitudPorNumero(String numeroSolicitud) throws Exception {
        try{
            getEntityManager();
            
            Query query = em.createQuery("FROM Solicitud s WHERE s.codigoSolicitud = :numeroSolicitud");
            query.setParameter("numeroSolicitud", numeroSolicitud);
            
            Solicitud solicitud = (Solicitud)query.getSingleResult();
            
            return solicitud;
            
        } catch (NoResultException exc) {
            return null;
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }finally {
            closeEntityManager();
        }
    }
    
    public String getUltimoCodigoSolicitud() throws Exception {
        try{
            getEntityManager();
            
            Query query = em.createNativeQuery("select max(codigo_solicitud::bigint) from solicitud s where codigo_solicitud is not null and codigo_solicitud <> ''");
            
            List<Long> lista = query.getResultList();
            if(lista.isEmpty()){
                return "1";
            }
            else{
                if(Objects.nonNull(lista.get(0))){
                    Long next = lista.get(0) + 1;
                    return next.toString();
                }
                else{
                    return "1";
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
            closeEntityManager();
        }
    }
    
    
    public Solicitud buscarSolicitudPorNumeroYCorreo(String numeroSolicitud, String correo) throws Exception {
        try{
            getEntityManager();
            
            //Query query = em.createQuery("FROM Solicitud s WHERE s.codigoSolicitud = :numeroSolicitud AND s.correos LIKE :correo");
            Query query = em.createQuery("Select s FROM Solicitud s, SolicitudEnvio e WHERE s.id = e.idSolicitud AND s.codigoSolicitud = :numeroSolicitud AND e.correosEnvia LIKE :correo");
            query.setParameter("numeroSolicitud", numeroSolicitud);
            query.setParameter("correo", "%".concat(correo).concat("%"));
            
            List<Solicitud> lista = query.getResultList();
            
            if(lista.isEmpty())
                return null;
            else
                return lista.get(0);
            
        } catch (NoResultException exc) {
            return null;
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }finally {
            closeEntityManager();
        }
    }
    
    
    public List<Object> listarSolicitudesProveedor(Date fechaInicial, Date fechaFinal, String codigoSolicitud, String codigoRC,
            Integer desde, Integer hasta, Proveedor proveedor) throws Exception {
        try {
            List<Object> respuesta = new ArrayList<>();
            getEntityManager();

            String sql = "SELECT s FROM Solicitud s "
                    + " left outer join Cotizacion c on c.rucProveedor = :rucProveedor and s.codigoSolicitud = c.codigoSolicitud and c.estado not in ('ANULADO', 'RECHAZADO') "
                    + " where c.id is null "
                    + " and s.estado in ('ENVIADO','COTIZADO') ";
//                    + " where s.estado in ('ENVIADO','COTIZADO') ";

            if (Objects.nonNull(codigoSolicitud) && !codigoSolicitud.isBlank()) {
                sql = sql.concat(" and UPPER(s.codigoSolicitud) = :codigoSolicitud ");
            }
            else if (Objects.nonNull(codigoRC) && !codigoRC.isBlank()) {
                sql = sql.concat(" and UPPER(s.codigoRC) = :codigoRC ");
            }
            else{
                sql = sql.concat(" and s.fechaSolicitud BETWEEN :fechaInicial AND :fechaFinal ");
            }
            
            if(Objects.nonNull(proveedor)){
//                sql = sql.concat(" AND (s.correos like :correoProveedor OR s.correos like :correoProveedorContab)");
                sql = sql.concat(" AND s.id in (Select e.idSolicitud FROM SolicitudEnvio e WHERE e.correosEnvia like :correoProveedor OR e.correosEnvia like :correoProveedorContab)");
            }

            sql = sql.concat(" order by s.fechaSolicitud");

            Query query = em.createQuery(sql);
            query.setParameter("rucProveedor", proveedor.getRuc());

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
            
            if(Objects.nonNull(proveedor)){
                query.setParameter("correoProveedor", "%".concat(proveedor.getCorreo()).concat("%"));
                query.setParameter("correoProveedorContab", "%".concat(proveedor.getCorreoContabilidad()).concat("%"));
            }

            //para obtener el total de los registros a buscar
            Integer totalRegistros = query.getResultList().size();
            respuesta.add(totalRegistros);

            //para la paginacion
            query.setFirstResult(desde).setMaxResults(hasta);

            List<Solicitud> listaSols = query.getResultList();
            respuesta.add(listaSols);

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
}
