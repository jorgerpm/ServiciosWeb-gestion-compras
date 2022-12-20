/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.AutorizacionOrdenCompra;
import com.idebsystems.serviciosweb.entities.OrdenCompra;
import com.idebsystems.serviciosweb.util.Persistencia;
import java.sql.SQLException;
import java.util.Date;
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

    public void guardarAutorizadores(List<AutorizacionOrdenCompra> listaAuts, OrdenCompra ordenCompra, List<Long> listaElimnar) throws Exception {
        try {

            getEntityManager();

            em.getTransaction().begin();
            
            //revisar si se tiene que eliminar autorizadores
            listaElimnar.forEach(idElim ->{
                AutorizacionOrdenCompra autElimn = em.find(AutorizacionOrdenCompra.class, idElim);
                em.remove(autElimn);
            });

            listaAuts.forEach(aut -> {
                //si ya xiste con el idusuario y el idordencompra que ya no le vuelva a persistir
                Query query = em.createQuery("FROM AutorizacionOrdenCompra a WHERE a.idUsuario = :idUsuario AND a.idOrdenCompra = :idOrdenCompra");
                query.setParameter("idUsuario", aut.getIdUsuario());
                query.setParameter("idOrdenCompra", aut.getIdOrdenCompra());
                List<AutorizacionOrdenCompra> listData = query.getResultList();
                
                if(listData.isEmpty())
                    em.persist(aut);
            });

            //actualiza el estado de la oc a por_autorizar
            em.merge(ordenCompra);
            
            //actualizar las solciitud y cotizacion el estado
            //cambiar el estado de la solicitud a POR_AUTORIZAR
            Query query = em.createQuery("UPDATE Solicitud s SET s.estado = :estado, s.usuarioModifica = :usuarioModifica, s.fechaModifica = :fechaModifica "
                    + " WHERE s.codigoRC = :codigoRc");
            query.setParameter("estado", ordenCompra.getEstado());
            query.setParameter("codigoRc", ordenCompra.getCodigoRC());
            query.setParameter("usuarioModifica", ordenCompra.getUsuarioModifica());
            query.setParameter("fechaModifica", new Date());
            query.executeUpdate();
            
            //cambiar el estado de la cotizacion POR_AUTORIZAR
            query = em.createQuery("UPDATE Cotizacion c SET c.estado = :estado, c.usuarioModifica = :usuarioModifica, c.fechaModifica = :fechaModifica "
                    + " WHERE c.codigoCotizacion = :codigoCotizacion");
            query.setParameter("estado", ordenCompra.getEstado());
            query.setParameter("codigoCotizacion", ordenCompra.getCodigoRC().concat("-").concat(ordenCompra.getRucProveedor()));
            query.setParameter("usuarioModifica", ordenCompra.getUsuarioModifica());
            query.setParameter("fechaModifica", new Date());
            query.executeUpdate();

            em.flush();

            em.getTransaction().commit();

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
