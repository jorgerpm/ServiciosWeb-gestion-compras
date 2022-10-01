/*
 * Proyecto API Rest (ServiciosWeb)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.util;

import javax.persistence.EntityManager;

/**
 *
 * @author jorge
 */
public class Persistencia {

    
    protected EntityManager em;
    

    /**
     * este metodo opbtiene el entity manager para gestionar la persistencia de
     * jpa
     * @throws Exception
     */
    protected void getEntityManager() throws Exception {
        try {
            //si es null se crea uno nuevo, caso contrario se trabaja con la misma EntityManagerFactory
            if(UtilPersistencia.emf == null){
                System.out.println("el emf es nulo ********");
                UtilPersistencia.crearEntityManagerFactory();
            }
            
            if(em != null && em.isOpen())
                System.out.println("la conexion esta abierta: " + em);
            
            //
            if(em != null)
                System.out.println("el em NO es nulo: " + em);
            
            
            em = UtilPersistencia.emf.createEntityManager();
            
//            System.out.println("se abre la em + " + em);
            
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new Exception(exc);
        }
    }

    /**
     * este metodo cierra la conexion del entity manager para que no quede
     * ninguna conexion abierta.
     * @throws java.lang.Exception
     */
    protected void closeEntityManager() throws Exception {
        try{
            if(em !=null && em.isOpen()){
//                if(em.getTransaction().isActive()){
//                    System.out.println("ACTIVA " + em.getTransaction().isActive());
//                    em.flush();
//                }
                em.clear();
                em.close();
            }
            
            em = null;
            
        }catch(IllegalStateException exc){
            exc.printStackTrace();
            throw new Exception(exc);
        }catch(Exception exc){
            exc.printStackTrace();
            throw new Exception(exc);
        }
        
    }
    
    /**
     * metodo para deshacer la transaccion activa por algun error que se pueda producir
     * Este metodo se llama cuando se producen exepciones, se coloca en los catch(){}
     */
    protected void rollbackTransaction() {
        if(em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
    
    //////////////////
//    
//    protected EntityManager abrirConexion() throws Exception {
//        try {
//            System.out.println("VALOR DE EM 111 " + em);
//            EntityManagerFactory emf = Persistence.createEntityManagerFactory(UNIDAD_PERSISTENCIA);
//            return emf.createEntityManager();
//            
//        } catch (Exception exc) {
//            exc.printStackTrace();
//            throw new Exception(exc);
//        }
//    }
//
//    /**
//     * este metodo cierra la conexion del entity manager para que no quede
//     * ninguna conexion abierta.
//     * @throws java.lang.Exception
//     */
//    protected void cerrarConexion(EntityManager em1) throws Exception {
//        try{
//            System.out.println("VALOR DE EM 222 " + em);
//            if(em1 != null){
//                em1.close();
//            }
//        }catch(IllegalStateException exc){
//            exc.printStackTrace();
//            throw new Exception(exc);
//        }
//        
//    }
}
