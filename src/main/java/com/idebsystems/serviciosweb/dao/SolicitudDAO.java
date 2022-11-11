/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

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

    public List<Object> listarSolicitudes(Date fechaInicial, Date fechaFinal, String codigoRC,
            Integer desde, Integer hasta) throws Exception {
        try {
            List<Object> respuesta = new ArrayList<>();
            getEntityManager();

            String sql = "FROM Solicitud s ";

            if (Objects.nonNull(codigoRC) && !codigoRC.isBlank()) {
                sql = sql.concat(" WHERE UPPER(s.codigoRC) = :codigoRC ");
            }
            else{
                sql = sql.concat(" WHERE s.fechaSolicitud BETWEEN :fechaInicial AND :fechaFinal ");
            }

            sql = sql.concat(" order by s.fechaSolicitud");

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

            if (Objects.nonNull(solicitud.getId()) && solicitud.getId() > 0) {
                em.merge(solicitud); //update
                
                //buscar los detalles de la sol pra eliminarlos y volver a ingresar los nuevos
                Query query = em.createQuery("DELETE FROM SolicitudDetalle d WHERE d.solicitud.id = " + solicitud.getId());
                int tantos = query.executeUpdate();
            } else {
                em.persist(solicitud); //insert
            }
            
            solicitud.getListaDetalles().forEach(detalle -> {
                detalle.setSolicitud(solicitud);
                em.persist(detalle);
            });
            
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
            if(exc.getMessage()!=null && exc.getMessage().contains("solicitud_codigo_rc_uk"))
                throw new Exception("YA EXISTE REGISTRADA UNA SOLICITUD CON EL CODIGO DE RC: ".concat(solicitud.getCodigoRC()));
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    
    public Solicitud buscarSolicitudPorNumero(String numeroRC) throws Exception {
        try{
            getEntityManager();
            
            Query query = em.createQuery("FROM Solicitud s WHERE s.codigoRC = :numeroRC");
            query.setParameter("numeroRC", numeroRC);
            
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
}
