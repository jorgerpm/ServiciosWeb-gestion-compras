/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.MenuRol;
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
public class MenuRolDAO extends Persistencia {
    private static final Logger LOGGER = Logger.getLogger(MenuRolDAO.class.getName());
    
    public List<MenuRol> listarMenuRoles() throws Exception {
        try {
            getEntityManager();

            Query query = em.createQuery("FROM MenuRol m");

            List<MenuRol> listaMenuRol = query.getResultList();

            return listaMenuRol;

       } catch (NoResultException exc) {
            return null;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    public MenuRol guardarMenuRol(MenuRol menuRol) throws Exception {
        try {

            getEntityManager();

            em.getTransaction().begin();

            if (Objects.nonNull(menuRol.getId()) && menuRol.getId() > 0) {
                em.merge(menuRol); //update
            } else {
                em.persist(menuRol); //insert
            }
            em.flush(); //Confirmar el insert o update

            em.getTransaction().commit();

            return menuRol;

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
