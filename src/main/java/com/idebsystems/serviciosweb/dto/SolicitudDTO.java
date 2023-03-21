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
        if(Objects.nonNull(codigoRC)) 
            codigoRC = codigoRC.replaceAll("'", "");
        return codigoRC;
    }

    public void setCodigoRC(String codigoRC) {
        if(Objects.nonNull(codigoRC)) 
            codigoRC = codigoRC.replaceAll("'", "");
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
        if(Objects.nonNull(correos)) 
            correos = correos.replaceAll("'", "");
        return correos;
    }

    public void setCorreos(String correos) {
        if(Objects.nonNull(correos)) 
            correos = correos.replaceAll("'", "");
        this.correos = correos;
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
        if(Objects.nonNull(codigoSolicitud)) 
            codigoSolicitud = codigoSolicitud.replaceAll("'", "");
        return codigoSolicitud;
    }

    public void setCodigoSolicitud(String codigoSolicitud) {
        if(Objects.nonNull(codigoSolicitud)) 
            codigoSolicitud = codigoSolicitud.replaceAll("'", "");
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
        if(Objects.nonNull(estadoRC)) 
            estadoRC = estadoRC.replaceAll("'", "");
        return estadoRC;
    }

    public void setEstadoRC(String estadoRC) {
        if(Objects.nonNull(estadoRC)) 
            estadoRC = estadoRC.replaceAll("'", "");
        this.estadoRC = estadoRC;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getUnidadNegocioRC() {
        if(Objects.nonNull(unidadNegocioRC)) 
            unidadNegocioRC = unidadNegocioRC.replaceAll("'", "");
        return unidadNegocioRC;
    }

    public void setUnidadNegocioRC(String unidadNegocioRC) {
        if(Objects.nonNull(unidadNegocioRC)) 
            unidadNegocioRC = unidadNegocioRC.replaceAll("'", "");
        this.unidadNegocioRC = unidadNegocioRC;
    }

    @Override
    public String toString() {
        return "SolicitudDTO{" + "id=" + id + ", fechaSolicitud=" + fechaSolicitud + ", fechaTexto=" + fechaTexto + ", codigoRC=" + codigoRC + ", estado=" + estado + ", usuario=" + usuario + ", correos=" + correos + ", observacion=" + observacion + ", usuarioModifica=" + usuarioModifica + ", fechaModifica=" + fechaModifica + ", codigoSolicitud=" + codigoSolicitud + ", montoAprobado=" + montoAprobado + ", fechaAutorizaRC=" + fechaAutorizaRC + ", estadoRC=" + estadoRC + ", unidadNegocioRC=" + unidadNegocioRC + ", totalRegistros=" + totalRegistros + ", respuesta=" + respuesta + '}';
    }

    
}
