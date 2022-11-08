/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.SolicitudEnvio;
import com.idebsystems.serviciosweb.util.Persistencia;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class SolicitudEnvioDAO extends Persistencia {
    
    private static final Logger LOGGER = Logger.getLogger(SolicitudEnvioDAO.class.getName());
    
    public SolicitudEnvio guardarSolicitudEnvio(SolicitudEnvio solicitudEnvio) throws Exception {
        try{
            getEntityManager();

            em.getTransaction().begin();

            if (Objects.nonNull(solicitudEnvio.getId()) && solicitudEnvio.getId() > 0) {
                em.merge(solicitudEnvio); //update
            } else {
                em.persist(solicitudEnvio); //insert
            }
            
            em.flush();

            em.getTransaction().commit();

            return solicitudEnvio;
            
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
