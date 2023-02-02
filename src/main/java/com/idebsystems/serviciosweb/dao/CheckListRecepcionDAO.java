/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.OrdenCompra;
import com.idebsystems.serviciosweb.entities.CheckListRecepcion;
import com.idebsystems.serviciosweb.entities.CheckListRecepcionDetalle;
import com.idebsystems.serviciosweb.entities.Usuario;
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
public class CheckListRecepcionDAO extends Persistencia {

    private static final Logger LOGGER = Logger.getLogger(CheckListRecepcionDAO.class.getName());
    
    public void generarCheckList(CheckListRecepcion checkListRecepcion, OrdenCompra ordenCompra) throws Exception {
        try {

            getEntityManager();

            em.getTransaction().begin();
            
            em.persist(checkListRecepcion);

            checkListRecepcion.getListaDetalles().forEach(aut -> {
                aut.setCheckListRecepcion(checkListRecepcion);
                em.persist(aut);
            });

            //actualiza el estado de la oc a por_autorizar
            em.merge(ordenCompra);
            
            //actualizar las solciitud y cotizacion el estado
            //cambiar el estado de la solicitud a PENDIENTE_RECEPCION
            Query query = em.createQuery("UPDATE Solicitud s SET s.estado = :estado, s.usuarioModifica = :usuarioModifica, s.fechaModifica = :fechaModifica "
                    + " WHERE s.codigoSolicitud = :codigoSolicitud");
            query.setParameter("estado", ordenCompra.getEstado());
            query.setParameter("codigoSolicitud", ordenCompra.getCodigoSolicitud());
            query.setParameter("usuarioModifica", ordenCompra.getUsuarioModifica());
            query.setParameter("fechaModifica", new Date());
            query.executeUpdate();
            
            //cambiar el estado de la cotizacion PENDIENTE_RECEPCION
            query = em.createQuery("UPDATE Cotizacion c SET c.estado = :estado, c.usuarioModifica = :usuarioModifica, c.fechaModifica = :fechaModifica "
                    + " WHERE c.codigoCotizacion = :codigoCotizacion");
            query.setParameter("estado", ordenCompra.getEstado());
            query.setParameter("codigoCotizacion", ordenCompra.getCodigoSolicitud().concat("-").concat(ordenCompra.getRucProveedor()));
            query.setParameter("usuarioModifica", ordenCompra.getUsuarioModifica());
            query.setParameter("fechaModifica", new Date());
            query.executeUpdate();

            em.flush();

            em.getTransaction().commit();

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
    
    public List<Object> listarCheckList(Date fechaInicial, Date fechaFinal, String codigoSolicitud, String codigoRC,
            Integer desde, Integer hasta, Usuario userSesion, boolean rolPrincipal, boolean buscarTodo) throws Exception {
        try {
            List<Object> respuesta = new ArrayList<>();
            getEntityManager();

            String sql = "Select distinct d.checkListRecepcion FROM CheckListRecepcionDetalle d ";

            if (Objects.nonNull(codigoSolicitud) && !codigoSolicitud.isBlank()) {
                sql = sql.concat(" WHERE UPPER(d.checkListRecepcion.solicitud.codigoSolicitud) = :codigoSolicitud ");
            }
            else if (Objects.nonNull(codigoRC) && !codigoRC.isBlank()) {
                sql = sql.concat(" WHERE UPPER(d.checkListRecepcion.solicitud.codigoRC) = :codigoRC ");
            }
            else{
                sql = sql.concat(" WHERE d.checkListRecepcion.fechaRecepcion BETWEEN :fechaInicial AND :fechaFinal ");
            }
            
            if(!buscarTodo){
                if(!rolPrincipal && userSesion.getIdRol() != 1){
                    sql = sql.concat(" AND d.idUsuario = :idUsuario ");
                }
            }

            sql = sql.concat(" order by d.checkListRecepcion.fechaRecepcion");

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
            
            if(!buscarTodo){
                if(!rolPrincipal && userSesion.getIdRol() != 1){
                    query.setParameter("idUsuario", userSesion.getId());
                }
            }

            //para obtener el total de los registros a buscar
            Integer totalRegistros = query.getResultList().size();
            respuesta.add(totalRegistros);

            //para la paginacion
            query.setFirstResult(desde).setMaxResults(hasta);

            List<CheckListRecepcion> listaComp = query.getResultList();
            respuesta.add(listaComp);

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
    
    
    
    public CheckListRecepcion buscarCheckListPorID(long id) throws Exception {
        try {
            getEntityManager();
            
            CheckListRecepcion check = em.find(CheckListRecepcion.class, id);
            
            return check;

        } catch (NoResultException exc) {
            return null;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    
    public void guardarCheckListRecepcion(CheckListRecepcion checkListRecepcion) throws Exception {
        try {

            getEntityManager();

            em.getTransaction().begin();
            
            em.merge(checkListRecepcion);

            for(CheckListRecepcionDetalle aut : checkListRecepcion.getListaDetalles()) {
                em.merge(aut);
            }
            
            //actualizar las solciitud y cotizacion el estado
            //cambiar el estado de la solicitud a PENDIENTE_RECEPCION
            Query query = em.createQuery("UPDATE Solicitud s SET s.estado = :estado, s.usuarioModifica = :usuarioModifica, s.fechaModifica = :fechaModifica "
                    + " WHERE s.codigoSolicitud = :codigoSolicitud");
            query.setParameter("estado", checkListRecepcion.getEstado());
            query.setParameter("codigoSolicitud", checkListRecepcion.getCodigoSolicitud());
            query.setParameter("usuarioModifica", checkListRecepcion.getUsuarioModifica()+"");
            query.setParameter("fechaModifica", new Date());
            query.executeUpdate();
            
            //cambiar el estado de la cotizacion PENDIENTE_RECEPCION
            query = em.createQuery("UPDATE Cotizacion c SET c.estado = :estado, c.usuarioModifica = :usuarioModifica, c.fechaModifica = :fechaModifica "
                    + " WHERE c.codigoCotizacion = :codigoCotizacion");
            query.setParameter("estado", checkListRecepcion.getEstado());
            query.setParameter("codigoCotizacion", checkListRecepcion.getCodigoSolicitud().concat("-").concat(checkListRecepcion.getOrdenCompra().getRucProveedor()));
            query.setParameter("usuarioModifica", checkListRecepcion.getUsuarioModifica()+"");
            query.setParameter("fechaModifica", new Date());
            query.executeUpdate();
            
            
            query = em.createQuery("UPDATE OrdenCompra o SET o.estado = :estado, o.usuarioModifica = :usuarioModifica, o.fechaModifica = :fechaModifica "
                    + " WHERE o.codigoSolicitud = :codigoSolicitud");
            query.setParameter("estado", checkListRecepcion.getEstado());
            query.setParameter("codigoSolicitud", checkListRecepcion.getCodigoSolicitud());
            query.setParameter("usuarioModifica", checkListRecepcion.getUsuarioModifica()+"");
            query.setParameter("fechaModifica", new Date());
            query.executeUpdate();
            

            em.flush();

            em.getTransaction().commit();

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
    
    
    public List<CheckListRecepcionDetalle> buscarDetallesCheckListPorID(CheckListRecepcion checkListRecepcion) throws Exception {
        try {
            getEntityManager();
            
            Query query = em.createQuery("FROM CheckListRecepcionDetalle d WHERE d.checkListRecepcion = :checkListRecepcion");
            query.setParameter("checkListRecepcion", checkListRecepcion);
            
            List<CheckListRecepcionDetalle> lista = query.getResultList();
            
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
}
