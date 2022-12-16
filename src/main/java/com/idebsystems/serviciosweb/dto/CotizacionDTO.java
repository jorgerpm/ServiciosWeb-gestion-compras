/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dto;

import com.idebsystems.serviciosweb.entities.Solicitud;
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
    private String fechaTexto;
    private String codigoRC;
    private String codigoCotizacion;
    private String estado;
    private String usuario;
    private String rucProveedor;
    private BigDecimal subtotal;
    private BigDecimal subtotalSinIva;
    private BigDecimal iva;
    private BigDecimal total;
    private BigDecimal descuento = BigDecimal.ZERO;
    private String observacion;
    private String tiempoEntrega;
    private String validezCotizacion;
    private String formaPago;
    private String adicionales;
    private List<CotizacionDetalleDTO> listaDetalles;
    //
    private Integer totalRegistros;
    private String respuesta;
    private ProveedorDTO proveedorDto;
    private String usuarioModifica;
    private Date fechaModifica;
    private String codigoSolicitud;
    private SolicitudDTO solicitudDto;

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

    public String getRucProveedor() {
        return rucProveedor;
    }

    public void setRucProveedor(String rucProveedor) {
        this.rucProveedor = rucProveedor;
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

    public String getTiempoEntrega() {
        return tiempoEntrega;
    }

    public void setTiempoEntrega(String tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }

    public String getValidezCotizacion() {
        return validezCotizacion;
    }

    public void setValidezCotizacion(String validezCotizacion) {
        this.validezCotizacion = validezCotizacion;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getAdicionales() {
        return adicionales;
    }

    public void setAdicionales(String adicionales) {
        this.adicionales = adicionales;
    }

    public String getFechaTexto() {
        return fechaTexto;
    }

    public void setFechaTexto(String fechaTexto) {
        this.fechaTexto = fechaTexto;
    }

    public ProveedorDTO getProveedorDto() {
        return proveedorDto;
    }

    public void setProveedorDto(ProveedorDTO proveedorDto) {
        this.proveedorDto = proveedorDto;
    }

    public String getUsuarioModifica() {
        return usuarioModifica;
    }

    public void setUsuarioModifica(String usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }

    public Date getFechaModifica() {
        return fechaModifica;
    }

    public void setFechaModifica(Date fechaModifica) {
        this.fechaModifica = fechaModifica;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getCodigoSolicitud() {
        return codigoSolicitud;
    }

    public void setCodigoSolicitud(String codigoSolicitud) {
        this.codigoSolicitud = codigoSolicitud;
    }

    public SolicitudDTO getSolicitudDto() {
        return solicitudDto;
    }

    public void setSolicitudDto(SolicitudDTO solicitudDto) {
        this.solicitudDto = solicitudDto;
    }

    @Override
    public String toString() {
        return "CotizacionDTO{" + "id=" + id + ", fechaCotizacion=" + fechaCotizacion + ", fechaTexto=" + fechaTexto + ", codigoRC=" + codigoRC + ", codigoCotizacion=" + codigoCotizacion + ", estado=" + estado + ", usuario=" + usuario + ", rucProveedor=" + rucProveedor + ", subtotal=" + subtotal + ", subtotalSinIva=" + subtotalSinIva + ", iva=" + iva + ", total=" + total + ", descuento=" + descuento + ", observacion=" + observacion + ", tiempoEntrega=" + tiempoEntrega + ", validezCotizacion=" + validezCotizacion + ", formaPago=" + formaPago + ", adicionales=" + adicionales + ", totalRegistros=" + totalRegistros + ", respuesta=" + respuesta + ", usuarioModifica=" + usuarioModifica + ", fechaModifica=" + fechaModifica + ", codigoSolicitud=" + codigoSolicitud + '}';
    }

    

}
