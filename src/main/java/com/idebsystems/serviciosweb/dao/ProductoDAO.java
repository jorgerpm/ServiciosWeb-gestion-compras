/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.Producto;
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
public class ProductoDAO extends Persistencia {
    private static final Logger LOGGER = Logger.getLogger(ProductoDAO.class.getName());
    
    public List<Producto> listarProductos() throws Exception {
        try {
            getEntityManager();

            Query query = em.createQuery("FROM Producto p order by p.nombre");

            List<Producto> listaProducto = query.getResultList();

            return listaProducto;

       } catch (NoResultException exc) {
            return null;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    public Producto guardarProducto(Producto producto) throws Exception {
        try {

            getEntityManager();

            em.getTransaction().begin();

            if (Objects.nonNull(producto.getId()) && producto.getId() > 0) {
                em.merge(producto); //update
            } else {
                em.persist(producto); //insert
            }
            em.flush(); //Confirmar el insert o update

            em.getTransaction().commit();

            return producto;

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
