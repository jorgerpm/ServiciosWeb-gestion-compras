/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dto;

import java.util.Objects;

/**
 *
 * @author jorge
 */
public class SolicitudDetalleDTO {
    
    private long id;
    private Integer cantidad;
    private String detalle;
    private String pathArchivo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getDetalle() {
        if(Objects.nonNull(detalle)) 
            detalle = detalle.replaceAll("'", "");
        return detalle;
    }

    public void setDetalle(String detalle) {
        if(Objects.nonNull(detalle)) 
            detalle = detalle.replaceAll("'", "");
        this.detalle = detalle;
    }

    public String getPathArchivo() {
        return pathArchivo;
    }

    public void setPathArchivo(String pathArchivo) {
        this.pathArchivo = pathArchivo;
    }

    @Override
    public String toString() {
        return "SolicitudDetalleDTO{" + "id=" + id + ", cantidad=" + cantidad + ", detalle=" + detalle + ", pathArchivo=" + pathArchivo + '}';
    }
    
    
}
