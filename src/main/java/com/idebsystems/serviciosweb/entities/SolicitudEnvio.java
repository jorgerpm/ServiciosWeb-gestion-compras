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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jorge
 */
@Entity
@Table(name = "solicitud_envio")
public class SolicitudEnvio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "numero_rc")
    private String numeroRC;
    @Column(name = "usuario_envia")
    private String usuarioEnvia;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_envia")
    private Date fechaEnvia;
    @Column(name = "correos_envia")
    private String correosEnvia;
    @Column(name = "id_solictud")
    private Long idSolicitud;
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumeroRC() {
        return numeroRC;
    }

    public void setNumeroRC(String numeroRC) {
        this.numeroRC = numeroRC;
    }

    public String getUsuarioEnvia() {
        return usuarioEnvia;
    }

    public void setUsuarioEnvia(String usuarioEnvia) {
        this.usuarioEnvia = usuarioEnvia;
    }

    public Date getFechaEnvia() {
        return fechaEnvia;
    }

    public void setFechaEnvia(Date fechaEnvia) {
        this.fechaEnvia = fechaEnvia;
    }

    public String getCorreosEnvia() {
        return correosEnvia;
    }

    public void setCorreosEnvia(String correosEnvia) {
        this.correosEnvia = correosEnvia;
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
}
