/*
 * Proyecto API Rest (ServiciosWeb)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author jorge
 */
public class UtilPersistencia {
    
    private static final String UNIDAD_PERSISTENCIA = "persistenciaIDEB";
    public static EntityManagerFactory emf;
    
    public static void crearEntityManagerFactory(){
        emf = Persistence.createEntityManagerFactory(UNIDAD_PERSISTENCIA);
    }
    
}
