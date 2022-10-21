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
public class SolicitudDTO {
    
    private long id;
    private String codigoSolicitud;
    private Date fechaSolicitud;
    private String codigoRC;
    private String estado;
    private String usuario;
    private String correos;
    private String observacion;
    private List<SolicitudDetalleDTO> listaDetalles;
    //
    private Integer totalRegistros;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigoSolicitud() {
        return codigoSolicitud;
    }

    public void setCodigoSolicitud(String codigoSolicitud) {
        this.codigoSolicitud = codigoSolicitud;
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

    @Override
    public String toString() {
        return "SolicitudDTO{" + "id=" + id + ", codigoSolicitud=" + codigoSolicitud + ", fechaSolicitud=" + fechaSolicitud + ", codigoRC=" + codigoRC + ", estado=" + estado + ", usuario=" + usuario + ", correos=" + correos + ", observacion=" + observacion + ", totalRegistros=" + totalRegistros + '}';
    }
    
    
    
}
