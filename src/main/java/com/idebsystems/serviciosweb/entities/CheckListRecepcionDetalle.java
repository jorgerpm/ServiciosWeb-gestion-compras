/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jorge
 */
@Entity
@Table(name = "check_list_recepcion_detalle")
public class CheckListRecepcionDetalle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_usuario")
    private Long idUsuario;
    @Column(name = "id_rol")
    private Long idRol;
    private String pregunta;
    private String respuesta;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    private String observacion;
    @Column(name = "campos_bodega")
    private String camposBodega;
    @Column(name = "fecha_aprobacion_artes")
    private String fechaAprobacionArtes;
    

    @ManyToOne
    @JoinColumn(name = "id_check_list_recepcion", referencedColumnName = "id")
    private CheckListRecepcion checkListRecepcion;

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
        return pregunta;
    }

    public void setPregunta(String pregunta) {
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
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public CheckListRecepcion getCheckListRecepcion() {
        return checkListRecepcion;
    }

    public void setCheckListRecepcion(CheckListRecepcion checkListRecepcion) {
        this.checkListRecepcion = checkListRecepcion;
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
