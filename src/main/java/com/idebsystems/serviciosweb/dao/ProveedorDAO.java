/*
 * Proyecto API Rest (ServiciosWeb)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.Proveedor;
import com.idebsystems.serviciosweb.entities.Usuario;
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
 * @author jorge
 */
public class ProveedorDAO  extends Persistencia {
    private static final Logger LOGGER = Logger.getLogger(ProveedorDAO.class.getName());

    public Proveedor buscarProveedorRuc(String ruc) throws Exception {
        try {
            getEntityManager();

            Query query = em.createQuery("FROM Proveedor p WHERE p.ruc = :ruc");
            query.setParameter("ruc", ruc);

            Proveedor data = (Proveedor) query.getSingleResult();

            return data;

       } catch (NoResultException exc) {
            return null;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    public List<Object> listarProveedores(Integer desde, Integer hasta, String valorBusqueda) throws Exception {
        try {
            List<Object> respuesta = new ArrayList<>();
            getEntityManager();

            String sql = "FROM Proveedor r ";
            
            if(Objects.nonNull(valorBusqueda) && !valorBusqueda.isBlank()){
                sql = sql.concat(" WHERE UPPER(r.razonSocial) like :valorBusqueda OR r.ruc = :ruc OR r.codigoJD = :codigoJD ");
            }
            
            sql = sql.concat(" order by r.razonSocial");
            
            Query query = em.createQuery(sql);
            
            if(Objects.nonNull(valorBusqueda) && !valorBusqueda.isBlank()){
                query.setParameter("valorBusqueda", "%".concat(valorBusqueda.toUpperCase()).concat("%"));
                query.setParameter("ruc", valorBusqueda);
                query.setParameter("codigoJD", valorBusqueda);
            }
            
            //para obtener el total de los registros a buscar
            Integer totalRegistros = query.getResultList().size();
            respuesta.add(totalRegistros);
            
            //para la paginacion
            query.setFirstResult(desde).setMaxResults(hasta);

            List<Proveedor> listaProveedor = query.getResultList();
            respuesta.add(listaProveedor);

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
    
    public Proveedor guardarProveedor(Proveedor proveedor) throws Exception {
        try {
            getEntityManager();
            em.getTransaction().begin();
            
            if (Objects.nonNull(proveedor.getId()) && proveedor.getId() > 0) {
                em.merge(proveedor); //update
            } else {
                em.persist(proveedor); //insert
            }
            
            em.flush(); //Confirmar el insert o update
            em.getTransaction().commit();
            return proveedor;
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
    
    public Proveedor guardarProveedorUsuario(Proveedor proveedor, Usuario usuario) throws Exception {
        try {
            getEntityManager();
            em.getTransaction().begin();
            
            if (Objects.nonNull(proveedor.getId()) && proveedor.getId() > 0) {
                em.merge(proveedor); //update
            } else {
                em.persist(proveedor); //insert
            }
            
            if (Objects.nonNull(usuario.getId()) && usuario.getId() > 0) {
                em.merge(usuario); //update
            } else {
                em.persist(usuario); //insert
            }
            
            em.flush(); //Confirmar el insert o update
            em.getTransaction().commit();
            return proveedor;
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
    
    public String cargaMasivaProveedores(Proveedor proveedor) throws Exception {
        try {
            getEntityManager();
            em.getTransaction().begin();
            
            if (Objects.nonNull(proveedor.getId()) && proveedor.getId() > 0) {
                em.merge(proveedor); //update
            } else {
                em.persist(proveedor); //insert
            }
            
            em.flush(); //Confirmar el insert o update
            em.getTransaction().commit();
            return "ok";
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
    
    
    public List<Proveedor> listarProveedoresActivosNombre(String valorBusqueda) throws Exception {
        try {
            
            getEntityManager();

            String sql = "FROM Proveedor r WHERE r.idEstado = 1 ";
            
            if(Objects.nonNull(valorBusqueda) && !valorBusqueda.isBlank()){
                sql = sql.concat(" AND UPPER(r.razonSocial) like :valorBusqueda OR r.ruc = :ruc OR r.codigoJD = :codigoJD ");
            }
            
            sql = sql.concat(" order by r.razonSocial");
            
            Query query = em.createQuery(sql);
            
            if(Objects.nonNull(valorBusqueda) && !valorBusqueda.isBlank()){
                query.setParameter("valorBusqueda", "%".concat(valorBusqueda.toUpperCase()).concat("%"));
                query.setParameter("ruc", valorBusqueda);
                query.setParameter("codigoJD", valorBusqueda);
            }

            List<Proveedor> listaProveedor = query.getResultList();

            return listaProveedor;

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
