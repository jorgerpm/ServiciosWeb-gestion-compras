/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dto;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author jorge
 */
public class CheckListRecepcionDetalleDTO {

    private Long id;
    private Long idRol;
    private Long idUsuario;
    private String pregunta;
    private String respuesta;
    private Date fecha;
    private String observacion;
    private String camposBodega;
    private String fechaAprobacionArtes;
    //
    private String nombreRol;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPregunta() {
        if(Objects.nonNull(pregunta)) 
            pregunta = pregunta.replaceAll("'", "");
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        if(Objects.nonNull(pregunta)) 
            pregunta = pregunta.replaceAll("'", "");
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getCamposBodega() {
        return camposBodega;
    }

    public void setCamposBodega(String camposBodega) {
        this.camposBodega = camposBodega;
    }

    public String getFechaAprobacionArtes() {
        return fechaAprobacionArtes;
    }

    public void setFechaAprobacionArtes(String fechaAprobacionArtes) {
        this.fechaAprobacionArtes = fechaAprobacionArtes;
    }
    
}
