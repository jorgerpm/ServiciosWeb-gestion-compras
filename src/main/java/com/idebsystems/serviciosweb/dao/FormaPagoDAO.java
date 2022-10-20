/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.FormaPago;
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
public class FormaPagoDAO extends Persistencia {
    private static final Logger LOGGER = Logger.getLogger(FormaPagoDAO.class.getName());
    
    public List<FormaPago> listarFormasPago() throws Exception {
        try {
            getEntityManager();

            Query query = em.createQuery("FROM FormaPago fp order by fp.nombre");

            List<FormaPago> listaFormaPago = query.getResultList();

            return listaFormaPago;

       } catch (NoResultException exc) {
            return null;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    public FormaPago guardarFormaPago(FormaPago formaPago) throws Exception {
        try {

            getEntityManager();

            em.getTransaction().begin();

            if (Objects.nonNull(formaPago.getId()) && formaPago.getId() > 0) {
                em.merge(formaPago); //update
            } else {
                em.persist(formaPago); //insert
            }
            em.flush(); //Confirmar el insert o update

            em.getTransaction().commit();

            return formaPago;

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
