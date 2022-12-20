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
            
            //buscar si ya existe el proveedor con ese ruc
            Query queryProv = em.createQuery("FROM Proveedor p WHERE p.ruc = :ruc");
            queryProv.setParameter("ruc", proveedor.getRuc());
            List<Proveedor> listaProvs = queryProv.getResultList();
            LOGGER.log(Level.INFO, "la listaProvs: {0}", listaProvs.isEmpty());
            if(listaProvs.isEmpty()){// si es vacio no existe el proveedor, se crea el nuevo proveedor
                em.persist(proveedor); //insert
            }
            else{//si ya existe se actualiza
                proveedor.setId(listaProvs.get(0).getId());
                em.merge(proveedor); //update
            }
            
            //buscar el usuario para ver si ya existe y no volver a registrar el mismo usuario con ese proveedor
            Query query = em.createQuery("FROM Usuario u WHERE u.usuario = :usuario");
            query.setParameter("usuario", usuario.getUsuario());
            List<Usuario> listaUser = query.getResultList();
//            LOGGER.log(Level.INFO, "la lista: {0}", listaUser.isEmpty());
            if(listaUser.isEmpty()){// si es vacio no existe el usuario, se crea el nuevo user
                em.persist(usuario); //insert
            }
            else{//si ya existe se actualiza
                usuario.setId(listaUser.get(0).getId());
                em.merge(usuario); //update
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
            //proveedor_ruc_key
            if(exc.getMessage()!=null && exc.getMessage().contains("usuario_usuario_key"))
                throw new Exception("YA EXISTE EL USUARIO CON EL RUC INGRESADO: ".concat(proveedor.getRuc()));
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
