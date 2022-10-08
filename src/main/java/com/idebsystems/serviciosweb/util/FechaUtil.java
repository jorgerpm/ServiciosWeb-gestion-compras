/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idebsystems.serviciosweb.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author jorge
 */
public class FechaUtil {
    
    public static Date fechaInicial(Date fechaIni) throws Exception {
        if(fechaIni == null)
            return null;
        Calendar cini = Calendar.getInstance();
        cini.setTime(fechaIni);
        cini.set(Calendar.HOUR_OF_DAY, 0);
        cini.set(Calendar.MINUTE, 0);
        cini.set(Calendar.SECOND, 0);
        return cini.getTime();
    }
    
    public static Date fechaFinal(Date fechaFin) throws Exception {
        if(fechaFin == null)
            return null;
        Calendar cfin = Calendar.getInstance();
        cfin.setTime(fechaFin);
        cfin.set(Calendar.HOUR_OF_DAY, 23);
        cfin.set(Calendar.MINUTE, 59);
        cfin.set(Calendar.SECOND, 59);
        return cfin.getTime();
    }
}
