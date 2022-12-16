/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.PreguntaChecklistRecepcion;
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
public class PreguntaChecklistRecepcionDAO extends Persistencia {

    private static final Logger LOGGER = Logger.getLogger(PreguntaChecklistRecepcionDAO.class.getName());
    
    public List<PreguntaChecklistRecepcion> listarPreguntas() throws Exception {
        try {
            getEntityManager();

            Query query = em.createQuery("FROM PreguntaChecklistRecepcion p ");

            List<PreguntaChecklistRecepcion> lista = query.getResultList();
            
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
    
    
    public PreguntaChecklistRecepcion guardarPregunta(PreguntaChecklistRecepcion pregunta) throws Exception {
        try {
            
            getEntityManager();

            em.getTransaction().begin();

            if (Objects.nonNull(pregunta.getId()) && pregunta.getId() > 0) {
                em.merge(pregunta); //update
            } else {
                em.persist(pregunta); //insert
            }
            
            em.flush(); //Confirmar el insert o update

            em.getTransaction().commit();

            return pregunta;
            
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
    
    
    
    public List<PreguntaChecklistRecepcion> buscarPreguntasPorRol(Long idRol) throws Exception {
        try {
            getEntityManager();

            Query query = em.createQuery("FROM PreguntaChecklistRecepcion p WHERE p.idRol = :idRol AND p.idEstado = 1 ");
            query.setParameter("idRol", idRol);

            List<PreguntaChecklistRecepcion> lista = query.getResultList();
            
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
