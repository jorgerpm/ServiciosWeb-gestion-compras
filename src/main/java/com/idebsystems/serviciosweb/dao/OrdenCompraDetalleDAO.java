/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.OrdenCompraDetalle;
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
public class OrdenCompraDetalleDAO extends Persistencia {
    
    private static final Logger LOGGER = Logger.getLogger(OrdenCompraDetalleDAO.class.getName());

    public List<OrdenCompraDetalle> buscarDetallesOC(Long idOrdenCompra) throws Exception {
        try {
            
            getEntityManager();

            Query query = em.createQuery("FROM OrdenCompraDetalle d WHERE d.ordenCompra.id = :idOrdenCompra");
            query.setParameter("idOrdenCompra", idOrdenCompra);
            
            List<OrdenCompraDetalle> listaDatos = query.getResultList();
            
            return listaDatos;

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
