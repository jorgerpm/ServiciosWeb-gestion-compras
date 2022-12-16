/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jorge
 */
@Entity
@Table(name = "comparativo")
@SequenceGenerator(name = "comparativo_id_seq", initialValue = 1, sequenceName = "comparativo_id_seq", allocationSize = 1)
public class Comparativo implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "comparativo_id_seq")
    @Column(name = "id")
    private Long id;
    
    @JoinColumn(name = "id_solicitud", referencedColumnName = "id")
    @OneToOne
    private Solicitud solicitud;
    @Column(name = "codigo_solicitud")
    private String codigoSolicitud;
    @Column(name = "codigo_comparativo")
    private String codigoComparativo;
    @Column(name = "fecha_comparativo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaComparativo;
    private String usuario;
    private String estado;
    private String observacion;
    @Column(name = "usuario_modifica")
    private Long usuarioModifica;
    @Column(name = "fecha_modifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModifica;
    
    @OneToMany(mappedBy = "comparativo", fetch = FetchType.LAZY)
    private List<ComparativoDetalle> listaDetalles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
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

    public List<ComparativoDetalle> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<ComparativoDetalle> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }
    
    
}
