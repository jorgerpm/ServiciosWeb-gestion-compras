/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dto;

import java.util.Date;
import java.util.List;

/**
 *
 * @author jorge
 */
public class ComparativoDTO {
    
    private Long id;
    private SolicitudDTO solicitud;
    private String codigoSolicitud;
    private String codigoComparativo;
    private Date fechaComparativo;
    private String usuario;
    private String estado;
    private String observacion;
    private Long usuarioModifica;
    private Date fechaModifica;
    private List<ComparativoDetalleDTO> listaDetalles;
    //
    private String respuesta;
    private int totalRegistros;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SolicitudDTO getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudDTO solicitud) {
        this.solicitud = solicitud;
    }

    public String getCodigoSolicitud() {
        return codigoSolicitud;
    }

    public void setCodigoSolicitud(String codigoSolicitud) {
        this.codigoSolicitud = codigoSolicitud;
    }

    public String getCodigoComparativo() {
        return codigoComparativo;
    }

    public void setCodigoComparativo(String codigoComparativo) {
        this.codigoComparativo = codigoComparativo;
    }

    public Date getFechaComparativo() {
        return fechaComparativo;
    }

    public void setFechaComparativo(Date fechaComparativo) {
        this.fechaComparativo = fechaComparativo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public List<ComparativoDetalleDTO> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<ComparativoDetalleDTO> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public int getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(int totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    
    
    @Override
    public String toString() {
        return "ComparativoDTO{" + "id=" + id + ", codigoSolicitud=" + codigoSolicitud + ", codigoComparativo=" + codigoComparativo + ", fechaComparativo=" + fechaComparativo + ", usuario=" + usuario + ", estado=" + estado + ", observacion=" + observacion + ", usuarioModifica=" + usuarioModifica + ", fechaModifica=" + fechaModifica + '}';
    }
    
    
}
