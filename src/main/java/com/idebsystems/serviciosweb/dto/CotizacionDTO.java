/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jorge
 */
public class CotizacionDTO {
    
    private long id;
    private Date fechaCotizacion;
    private String codigoRC;
    private String codigoCotizacion;
    private String estado;
    private String usuario;
    private Long idProveedor;
    private BigDecimal subtotal;
    private BigDecimal subtotalSinIva;
    private BigDecimal iva;
    private BigDecimal total;
    private BigDecimal descuento = BigDecimal.ZERO;
    private String observacion;
    private List<CotizacionDetalleDTO> listaDetalles;
    //
    private Integer totalRegistros;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaCotizacion() {
        return fechaCotizacion;
    }

    public void setFechaCotizacion(Date fechaCotizacion) {
        this.fechaCotizacion = fechaCotizacion;
    }

    public String getCodigoRC() {
        return codigoRC;
    }

    public void setCodigoRC(String codigoRC) {
        this.codigoRC = codigoRC;
    }

    public String getCodigoCotizacion() {
        return codigoCotizacion;
    }

    public void setCodigoCotizacion(String codigoCotizacion) {
        this.codigoCotizacion = codigoCotizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getSubtotalSinIva() {
        return subtotalSinIva;
    }

    public void setSubtotalSinIva(BigDecimal subtotalSinIva) {
        this.subtotalSinIva = subtotalSinIva;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public String getObservacion() {
        return observacion;
    }

    public List<CotizacionDetalleDTO> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<CotizacionDetalleDTO> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    
    
    @Override
    public String toString() {
        return "CotizacionDTO{" + "id=" + id + ", fechaCotizacion=" + fechaCotizacion + ", codigoRC=" + codigoRC + ", codigoCotizacion=" + codigoCotizacion + ", estado=" + estado + ", usuario=" + usuario + ", idProveedor=" + idProveedor + ", subtotal=" + subtotal + ", subtotalSinIva=" + subtotalSinIva + ", iva=" + iva + ", total=" + total + ", descuento=" + descuento + ", observacion=" + observacion + '}';
    }
    
    
}
