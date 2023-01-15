/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jorge
 */
public class SolicitudDTO {

    private long id;
    private Date fechaSolicitud;
    private String fechaTexto;
    private String codigoRC;
    private String estado;
    private String usuario;
    private String correos;
    private String observacion;
    private List<SolicitudDetalleDTO> listaDetalles;
    private String usuarioModifica;
    private Date fechaModifica;
    private String codigoSolicitud;
    private BigDecimal montoAprobado;
    private Date fechaAutorizaRC;
    private String estadoRC;
    private String unidadNegocioRC;
    //
    private Integer totalRegistros;
    private String respuesta;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getCodigoRC() {
        return codigoRC;
    }

    public void setCodigoRC(String codigoRC) {
        this.codigoRC = codigoRC;
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

    public String getCorreos() {
        return correos;
    }

    public void setCorreos(String correos) {
        this.correos = correos;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<SolicitudDetalleDTO> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<SolicitudDetalleDTO> listaDetalles) {
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

    public String getCodigoSolicitud() {
        return codigoSolicitud;
    }

    public void setCodigoSolicitud(String codigoSolicitud) {
        this.codigoSolicitud = codigoSolicitud;
    }

    public BigDecimal getMontoAprobado() {
        return montoAprobado;
    }

    public void setMontoAprobado(BigDecimal montoAprobado) {
        this.montoAprobado = montoAprobado;
    }

    public Date getFechaAutorizaRC() {
        return fechaAutorizaRC;
    }

    public void setFechaAutorizaRC(Date fechaAutorizaRC) {
        this.fechaAutorizaRC = fechaAutorizaRC;
    }

    public String getEstadoRC() {
        return estadoRC;
    }

    public void setEstadoRC(String estadoRC) {
        this.estadoRC = estadoRC;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getUnidadNegocioRC() {
        return unidadNegocioRC;
    }

    public void setUnidadNegocioRC(String unidadNegocioRC) {
        this.unidadNegocioRC = unidadNegocioRC;
    }

    @Override
    public String toString() {
        return "SolicitudDTO{" + "id=" + id + ", fechaSolicitud=" + fechaSolicitud + ", fechaTexto=" + fechaTexto + ", codigoRC=" + codigoRC + ", estado=" + estado + ", usuario=" + usuario + ", correos=" + correos + ", observacion=" + observacion + ", usuarioModifica=" + usuarioModifica + ", fechaModifica=" + fechaModifica + ", codigoSolicitud=" + codigoSolicitud + ", montoAprobado=" + montoAprobado + ", fechaAutorizaRC=" + fechaAutorizaRC + ", estadoRC=" + estadoRC + ", unidadNegocioRC=" + unidadNegocioRC + ", totalRegistros=" + totalRegistros + ", respuesta=" + respuesta + '}';
    }

    
}
