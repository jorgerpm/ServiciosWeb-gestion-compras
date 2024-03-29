/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author jorge
 */
public class CheckListRecepcionDTO {
    
    private long id;
    private SolicitudDTO solicitud;
    private OrdenCompraDTO ordenCompra;
    private String codigoSolicitud;
    private Date fechaRecepcion;
    private String estado;
    private String usuario;
    private Date fechaRecepcionBodega;
    private String codigoMaterial;
    private int cantidadRecibida;
    private Long usuarioModifica;
    private Date fechaModifica;
    private BigDecimal montoTotalFactura;
    private Date fechaAprobacionArtes;
    private List<CheckListRecepcionDetalleDTO> listaDetalles;
    //
    private int totalRegistros;
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SolicitudDTO getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudDTO solicitud) {
        this.solicitud = solicitud;
    }

    public OrdenCompraDTO getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompraDTO ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public String getCodigoSolicitud() {
        return codigoSolicitud;
    }

    public void setCodigoSolicitud(String codigoSolicitud) {
        this.codigoSolicitud = codigoSolicitud;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
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

    public Long getUsuarioModifica() {
        return usuarioModifica;
    }

    public void setUsuarioModifica(Long usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }

    public Date getFechaModifica() {
        return fechaModifica;
    }

    public void setFechaModifica(Date fechaModifica) {
        this.fechaModifica = fechaModifica;
    }

    public List<CheckListRecepcionDetalleDTO> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<CheckListRecepcionDetalleDTO> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public int getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(int totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public Date getFechaRecepcionBodega() {
        return fechaRecepcionBodega;
    }

    public void setFechaRecepcionBodega(Date fechaRecepcionBodega) {
        this.fechaRecepcionBodega = fechaRecepcionBodega;
    }

    public String getCodigoMaterial() {
        if(Objects.nonNull(codigoMaterial)) 
            codigoMaterial = codigoMaterial.replaceAll("'", "");
        return codigoMaterial;
    }

    public void setCodigoMaterial(String codigoMaterial) {
        if(Objects.nonNull(codigoMaterial)) 
            codigoMaterial = codigoMaterial.replaceAll("'", "");
        this.codigoMaterial = codigoMaterial;
    }

    public int getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(int cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    public BigDecimal getMontoTotalFactura() {
        return montoTotalFactura;
    }

    public void setMontoTotalFactura(BigDecimal montoTotalFactura) {
        this.montoTotalFactura = montoTotalFactura;
    }

    public Date getFechaAprobacionArtes() {
        return fechaAprobacionArtes;
    }

    public void setFechaAprobacionArtes(Date fechaAprobacionArtes) {
        this.fechaAprobacionArtes = fechaAprobacionArtes;
    }

    @Override
    public String toString() {
        return "CheckListRecepcionDTO{" + "id=" + id + ", codigoSolicitud=" + codigoSolicitud + ", fechaRecepcion=" + fechaRecepcion + ", estado=" + estado + ", usuario=" + usuario + ", fechaRecepcionBodega=" + fechaRecepcionBodega + ", codigoMaterial=" + codigoMaterial + ", cantidadRecibida=" + cantidadRecibida + ", usuarioModifica=" + usuarioModifica + ", fechaModifica=" + fechaModifica + ", montoTotalFactura=" + montoTotalFactura + ", totalRegistros=" + totalRegistros + '}';
    }

    
    
}
