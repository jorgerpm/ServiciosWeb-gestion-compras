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

            String sql = "FROM Solicitud s WHERE s.fechaSolicitud BETWEEN :fechaInicial AND :fechaFinal ";

            if (Objects.nonNull(codigoRC) && !codigoRC.isBlank()) {
                sql = sql.concat(" AND UPPER(s.codigoRC) = :codigoRC ");
            }

            sql = sql.concat(" order by s.fechaSolicitud");

            Query query = em.createQuery(sql);
            query.setParameter("fechaInicial", fechaInicial);
            query.setParameter("fechaFinal", fechaFinal);

            if (Objects.nonNull(codigoRC) && !codigoRC.isBlank()) {
                query.setParameter("codigoRC", codigoRC.toUpperCase());
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
    
    public Solicitud guardarCotizacion(Solicitud solicitud) throws Exception {
        try{
            getEntityManager();

            em.getTransaction().begin();

            if (Objects.nonNull(solicitud.getId()) && solicitud.getId() > 0) {
                em.merge(solicitud); //update
            } else {
                em.persist(solicitud); //insert
            }
            
            solicitud.getListaDetalles().forEach(detalle -> {
                detalle.setSolicitud(solicitud);
                em.persist(detalle);
            });
            
            em.flush(); //Confirmar el insert o update

            em.getTransaction().commit();

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
}
