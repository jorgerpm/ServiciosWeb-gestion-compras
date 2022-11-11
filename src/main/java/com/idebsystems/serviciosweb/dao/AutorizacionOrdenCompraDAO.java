/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.AutorizacionOrdenCompra;
import com.idebsystems.serviciosweb.util.Persistencia;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author jorge
 */
public class AutorizacionOrdenCompraDAO extends Persistencia {
    
    private static final Logger LOGGER = Logger.getLogger(AutorizacionOrdenCompraDAO.class.getName());
    
    public List<AutorizacionOrdenCompra> getAutorizacionesIDOrdenCompra(Long idOrdenCompra) throws Exception {
        try {
            getEntityManager();

            Query query = em.createQuery("FROM AutorizacionOrdenCompra a WHERE a.idOrdenCompra = :idOrdenCompra ");

            query.setParameter("idOrdenCompra", idOrdenCompra);

            List<AutorizacionOrdenCompra> respuesta = query.getResultList();
            
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
}
