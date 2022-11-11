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
public class OrdenCompraDTO {

    private long id;
    private Date fechaOrdenCompra;
    private String codigoRC;
    private String codigoOrdenCompra;
    private String estado;
    private String usuario;
    private String rucProveedor;
    private BigDecimal subtotal;
    private BigDecimal subtotalSinIva;
    private BigDecimal iva;
    private BigDecimal total;
    private BigDecimal descuento = BigDecimal.ZERO;
    private String observacion;
    private String formaPago;
    private List<OrdenCompraDetalleDTO> listaDetalles;
    //
    private Integer totalRegistros;
    private String fechaTexto;
    private ProveedorDTO proveedorDto;
    private Long idUsuario;
    private String usuarioModifica;
    private Date fechaModifica;
    private String respuesta;
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaOrdenCompra() {
        return fechaOrdenCompra;
    }

    public void setFechaOrdenCompra(Date fechaOrdenCompra) {
        this.fechaOrdenCompra = fechaOrdenCompra;
    }

    public String getCodigoRC() {
        return codigoRC;
    }

    public void setCodigoRC(String codigoRC) {
        this.codigoRC = codigoRC;
    }

    public String getCodigoOrdenCompra() {
        return codigoOrdenCompra;
    }

    public void setCodigoOrdenCompra(String codigoOrdenCompra) {
        this.codigoOrdenCompra = codigoOrdenCompra;
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

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public List<OrdenCompraDetalleDTO> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<OrdenCompraDetalleDTO> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
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

    @Override
    public String toString() {
        return "OrdenCompraDTO{" + "id=" + id + ", fechaOrdenCompra=" + fechaOrdenCompra + ", codigoRC=" + codigoRC + ", codigoOrdenCompra=" + codigoOrdenCompra + ", estado=" + estado + ", usuario=" + usuario + ", rucProveedor=" + rucProveedor + ", subtotal=" + subtotal + ", subtotalSinIva=" + subtotalSinIva + ", iva=" + iva + ", total=" + total + ", descuento=" + descuento + ", observacion=" + observacion + ", formaPago=" + formaPago + ", totalRegistros=" + totalRegistros + ", fechaTexto=" + fechaTexto + ", idUsuario=" + idUsuario + '}';
    }

    
}
