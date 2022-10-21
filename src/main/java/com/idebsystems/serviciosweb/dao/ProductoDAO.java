/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.Producto;
import com.idebsystems.serviciosweb.util.Persistencia;
import java.sql.SQLException;
import java.util.ArrayList;
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
    
    public List<Object> listarProductos(Integer desde, Integer hasta, String valorBusqueda) throws Exception {
        try {
            List<Object> respuesta = new ArrayList<>();
            getEntityManager();

            String sql = "FROM Producto p ";
            
            if(Objects.nonNull(valorBusqueda) && !valorBusqueda.isBlank()){
                sql = sql.concat(" WHERE UPPER(p.nombre) like :valorBusqueda OR UPPER(p.codigoProducto) like :valorBusqueda1");
            }
            
            sql = sql.concat(" order by p.nombre");
            
            Query query = em.createQuery(sql);
            
            if(Objects.nonNull(valorBusqueda) && !valorBusqueda.isBlank()){
                query.setParameter("valorBusqueda", "%".concat(valorBusqueda.toUpperCase()).concat("%"));
                query.setParameter("valorBusqueda1", "%".concat(valorBusqueda.toUpperCase()).concat("%"));
            }

            //para obtener el total de los registros a buscar
            Integer totalRegistros = query.getResultList().size();
            respuesta.add(totalRegistros);
            
            //para la paginacion
            query.setFirstResult(desde).setMaxResults(hasta);
            
            List<Producto> listaProducto = query.getResultList();
            respuesta.add(listaProducto);

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
