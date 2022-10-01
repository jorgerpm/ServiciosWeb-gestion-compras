/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.Estado;
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
 * @author israe
 */
public class EstadoDAO extends Persistencia {
    private static final Logger LOGGER = Logger.getLogger(EstadoDAO.class.getName());
    
    public List<Estado> listarEstados() throws Exception {
        try {
            getEntityManager();

            Query query = em.createQuery("FROM Estado e");

            List<Estado> listaEstado = query.getResultList();

            return listaEstado;

       } catch (NoResultException exc) {
            return null;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    public Estado guardarEstado(Estado estado) throws Exception {
        try {

            getEntityManager();

            em.getTransaction().begin();

            if (Objects.nonNull(estado.getId()) && estado.getId() > 0) {
                em.merge(estado); //update
            } else {
                em.persist(estado); //insert
            }
            em.flush(); //Confirmar el insert o update

            em.getTransaction().commit();

            return estado;

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
