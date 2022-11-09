/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.OrdenCompra;
import com.idebsystems.serviciosweb.util.Persistencia;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;

/**
 *
 * @author jorge
 */
public class OrdenCompraDAO extends Persistencia {
    
    private static final Logger LOGGER = Logger.getLogger(OrdenCompraDAO.class.getName());
    
    public OrdenCompra generarOrdenCompra(OrdenCompra ordenCompra) throws Exception {
        try{
            getEntityManager();

            em.getTransaction().begin();

            if (Objects.nonNull(ordenCompra.getId()) && ordenCompra.getId() > 0) {
                em.merge(ordenCompra); //update
            } else {
                em.persist(ordenCompra); //insert
            }
            
            ordenCompra.getListaDetalles().forEach(detalle -> {
                detalle.setOrdenCompra(ordenCompra);
                em.persist(detalle);
            });
            
            //cambiar el estado de la solicitud a GENERADO_OC
            Query query = em.createQuery("UPDATE Solicitud s SET s.estado = 'GENERADO_OC' WHERE s.codigoRC = :codigoRc");
            query.setParameter("codigoRc", ordenCompra.getCodigoRC());
            query.executeUpdate();
            
            //colocar a todas las cotizaciones como rechazadas
            //y que la solicitud seleccionada quede como GENERADO_OC
            query = em.createQuery("UPDATE Cotizacion c SET c.estado = 'RECHAZADO' WHERE c.codigoRC = :codigoRc");
            query.setParameter("codigoRc", ordenCompra.getCodigoRC());
            query.executeUpdate();
            
            query = em.createQuery("UPDATE Cotizacion c SET c.estado = 'GENERADO_OC' WHERE c.codigoCotizacion = :codigoCotizacion");
            query.setParameter("codigoCotizacion", ordenCompra.getCodigoRC().concat("-").concat(ordenCompra.getRucProveedor()));
            query.executeUpdate();
            
            em.flush(); //Confirmar el insert o update

            em.getTransaction().commit();

            return ordenCompra;
            
        } catch (SQLException exc) {
            rollbackTransaction();
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } catch (Exception exc) {
            rollbackTransaction();
            if(exc.getMessage() != null && exc.getMessage().contains("orden_compra_codigo_orden_compra_uk"))
                throw new Exception("YA EXISTE EL CODIGO DE ORDEN DE COMPRA");
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
}
