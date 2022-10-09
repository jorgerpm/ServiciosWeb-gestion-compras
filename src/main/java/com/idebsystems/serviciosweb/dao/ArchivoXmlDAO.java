/*
 * Proyecto API Rest (ServiciosWeb)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.ArchivoXml;
import com.idebsystems.serviciosweb.util.Persistencia;
import java.sql.SQLException;
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
public class ArchivoXmlDAO extends Persistencia {

    private static final Logger LOGGER = Logger.getLogger(ArchivoXmlDAO.class.getName());
    
    public String guardarDatosArchivo(ArchivoXml archivoXml) throws Exception {
        try {

            getEntityManager();

            em.getTransaction().begin();

            if (Objects.nonNull(archivoXml.getId()) && archivoXml.getId() > 0) {
                em.merge(archivoXml);
            } else {
                em.persist(archivoXml);
            }
            em.flush();

            em.getTransaction().commit();

            return "OK";

        } catch (SQLException exc) {
            rollbackTransaction();
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } catch (Exception exc) {
            rollbackTransaction();
            if(exc.getMessage().contains("archivoxml_autorizacion_key"))
                return "esa factura ya está cargada en la base de datos";
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    public List<ArchivoXml> listarArchivosXml() throws Exception {
        try {
            getEntityManager();

            Query query = em.createQuery("FROM ArchivoXml r");

            List<ArchivoXml> listaArchivoXml = query.getResultList();

            return listaArchivoXml;

       } catch (NoResultException exc) {
            return null;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    public List<ArchivoXml> listarPorFecha(Date fechaInicio, Date fechaFinal, Long idUsuarioCarga, Integer desde, Integer hasta) throws Exception {
        try {
            getEntityManager();

            String sql = "FROM ArchivoXml ax WHERE ax.fechaAutorizacion between :fechaInicio AND :fechaFinal ";
            
            if(Objects.nonNull(idUsuarioCarga)){
                sql += " AND ax.idUsuarioCarga = :idUsuarioCarga";
            }
            
            Query query = em.createQuery(sql);
                    
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFinal", fechaFinal);
            
            if(Objects.nonNull(idUsuarioCarga)){
                query.setParameter("idUsuarioCarga", idUsuarioCarga);
            }

            //para la paginacion
            query.setFirstResult(desde).setMaxResults(hasta);
            
            List<ArchivoXml> listaPorFecha = query.getResultList();

            return listaPorFecha;

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
