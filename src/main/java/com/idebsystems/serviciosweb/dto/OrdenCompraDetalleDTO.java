/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dto;

import java.math.BigDecimal;

/**
 *
 * @author jorge
 */
public class OrdenCompraDetalleDTO {
    
    private long id;
    private Integer cantidad;
    private String detalle;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
    private Boolean tieneIva;
    private String observacion;
    private String codigoProducto;

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
        return detalle;
    }

    public void setDetalle(String detalle) {
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
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    @Override
    public String toString() {
        return "OrdenCompraDetalleDTO{" + "id=" + id + ", cantidad=" + cantidad + ", detalle=" + detalle + ", valorUnitario=" + valorUnitario + ", valorTotal=" + valorTotal + ", tieneIva=" + tieneIva + ", observacion=" + observacion + ", codigoProducto=" + codigoProducto + '}';
    }

    
}
