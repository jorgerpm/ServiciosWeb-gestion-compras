/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.Cotizacion;
import com.idebsystems.serviciosweb.entities.HistorialDocumento;
import com.idebsystems.serviciosweb.entities.OrdenCompra;
import com.idebsystems.serviciosweb.entities.Solicitud;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author jorge
 */
public class HistorialDocumentoDAO {
    
    public static HistorialDocumento getHistorial(Object documento){
//            Long idDocumento, String codigoRC, String documento, 
//            String estado, String usuario, String observacion, BigDecimal total){
        

        HistorialDocumento his = new HistorialDocumento();
        
        if(documento instanceof Solicitud){
            
            Solicitud entity = (Solicitud)documento;
            
            his.setIdDocumento(entity.getId());
            his.setCodigoRC(entity.getCodigoRC());
            his.setDocumento("SOLICITUD");
            his.setEstado(entity.getEstado());
            his.setFechaCambio(new Date());
    //        his.setId(0);
            his.setObservacion(entity.getObservacion());
            his.setUsuarioCambio(entity.getUsuario());
            his.setValorTotal(BigDecimal.ZERO);
        }
        if(documento instanceof Cotizacion){
            
            Cotizacion entity = (Cotizacion)documento;
            
            his.setIdDocumento(entity.getId());
            his.setCodigoRC(entity.getCodigoRC());
            his.setDocumento("COTIZACION");
            his.setEstado(entity.getEstado());
            his.setFechaCambio(new Date());
    //        his.setId(0);
            his.setObservacion(entity.getObservacion());
            his.setUsuarioCambio(entity.getUsuario());
            his.setValorTotal(entity.getTotal());
        }
        if(documento instanceof OrdenCompra){
            
            OrdenCompra entity = (OrdenCompra)documento;
            
            his.setIdDocumento(entity.getId());
            his.setCodigoRC(entity.getCodigoRC());
            his.setDocumento("ORDEN_COMPRA");
            his.setEstado(entity.getEstado());
            his.setFechaCambio(new Date());
    //        his.setId(0);
            his.setObservacion(entity.getObservacion());
            his.setUsuarioCambio(entity.getUsuario());
            his.setValorTotal(entity.getTotal());
        }
        
//        if(documento.getClass() == Solicitud.class){
//            
//        }
        
        return his;
    }
}
