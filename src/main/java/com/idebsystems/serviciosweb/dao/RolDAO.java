/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.Rol;
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
public class RolDAO extends Persistencia {
    private static final Logger LOGGER = Logger.getLogger(RolDAO.class.getName());
    
    public List<Rol> listarRoles() throws Exception {
        try {
            getEntityManager();

            Query query = em.createQuery("FROM Rol r order by r.nombre");

            List<Rol> listaRol = query.getResultList();

            return listaRol;

       } catch (NoResultException exc) {
            return null;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    public Rol guardarRol(Rol rol) throws Exception {
        try {

            getEntityManager();

            em.getTransaction().begin();

            if (Objects.nonNull(rol.getId()) && rol.getId() > 0) {
                em.merge(rol); //update
            } else {
                em.persist(rol); //insert
            }
            em.flush(); //Confirmar el insert o update

            em.getTransaction().commit();

            return rol;

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
    
    
    public Rol buscarRolPorId(Long idRol) throws Exception {
        try {
            getEntityManager();

            Query query = em.createQuery("FROM Rol r WHERE r.id = :idRol");
            query.setParameter("idRol", idRol);

            Rol rol = (Rol)query.getSingleResult();

            return rol;

       } catch (NoResultException exc) {
            return null;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    
    public List<Rol> buscarRolCheckList() throws Exception {
        try {
            getEntityManager();

            Query query = em.createQuery("FROM Rol r WHERE r.cheklistRecepcion = TRUE AND r.idEstado = 1 ORDER BY r.nombre");

            List<Rol> listaRoles = query.getResultList();

            return listaRoles;

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
