/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author jorge
 */
public class CotizacionDetalleDTO {
    
    private long id;
    private Integer cantidad;
    private String detalle;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
    private Boolean tieneIva;
    private String observacion;

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

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Boolean getTieneIva() {
        return tieneIva;
    }

    public void setTieneIva(Boolean tieneIva) {
        this.tieneIva = tieneIva;
    }

    public String getObservacion() {
        if(Objects.nonNull(observacion)) 
            observacion = observacion.replaceAll("'", "");
        return observacion;
    }

    public void setObservacion(String observacion) {
        if(Objects.nonNull(observacion)) 
            observacion = observacion.replaceAll("'", "");
        this.observacion = observacion;
    }

    @Override
    public String toString() {
        return "CotizacionDetalleDTO{" + "id=" + id + ", cantidad=" + cantidad + ", detalle=" + detalle + ", valorUnitario=" + valorUnitario + ", valorTotal=" + valorTotal + ", tieneIva=" + tieneIva + ", observacion=" + observacion + '}';
    }
    
    
}
