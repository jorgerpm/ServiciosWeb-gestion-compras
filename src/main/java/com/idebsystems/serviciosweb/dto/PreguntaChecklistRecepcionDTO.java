/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dto;

import com.idebsystems.serviciosweb.entities.Rol;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author jorge
 */
public class PreguntaChecklistRecepcionDTO {
    
    private Long id;
    private Long idRol;
    private String pregunta;
    private long idEstado;
    private Date fechaModifica;
    private Long usuarioModifica;
    //
    private Rol rol;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
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

    public Date getFechaModifica() {
        return fechaModifica;
    }

    public void setFechaModifica(Date fechaModifica) {
        this.fechaModifica = fechaModifica;
    }

    public Long getUsuarioModifica() {
        return usuarioModifica;
    }

    public void setUsuarioModifica(Long usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(long idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public String toString() {
        return "PreguntaChecklistRecepcionDTO{" + "id=" + id + ", idRol=" + idRol + ", pregunta=" + pregunta + ", idEstado=" + idEstado + ", fechaModifica=" + fechaModifica + ", usuarioModifica=" + usuarioModifica + '}';
    }
    
    
}
