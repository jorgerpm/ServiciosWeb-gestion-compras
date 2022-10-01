/*
 * Proyecto API Rest (ServiciosWeb)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.ArchivoXml;
import com.idebsystems.serviciosweb.util.Persistencia;
import java.sql.SQLException;
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

            return "Archivo xml guardado con éxito";

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
}
