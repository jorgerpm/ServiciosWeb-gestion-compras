/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jorge
 */
@Entity
@Table(name = "solicitud")
@SequenceGenerator(name = "solicitud_id_seq", initialValue = 1, sequenceName = "solicitud_id_seq", allocationSize = 1)
public class Solicitud implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "solicitud_id_seq")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "fecha_solicitud")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolicitud;
    @Column(name = "codigo_rc")
    private String codigoRC;
    private String estado;
    private String usuario;
    private String correos;
    private String observacion;

    @OneToMany(mappedBy = "solicitud", fetch = FetchType.LAZY)
    private List<SolicitudDetalle> listaDetalles;

    @Column(name = "usuario_modifica")
    private String usuarioModifica;
    @Column(name = "fecha_modifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModifica;
    @Column(name = "codigo_solicitud")
    private String codigoSolicitud;
    
    @Column(name = "monto_aprobado_rc")
    private BigDecimal montoAprobado;
    @Column(name = "fecha_autoriza_rc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAutorizaRC;
    @Column(name = "estado_rc")
    private String estadoRC;
    
    @Column(name = "unidad_negocio_rc")
    private String unidadNegocioRC;
    @Column(name = "autorizado_por_rc")
    private String autorizadoPorRC;
    @Column(name = "solicitado_por_rc")
    private String solicitadoPorRC;
    @Column(name = "razon_rechazo")
    private String razonRechazo;

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

    public List<SolicitudDetalle> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<SolicitudDetalle> listaDetalles) {
        this.listaDetalles = listaDetalles;
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

    public String getUnidadNegocioRC() {
        return unidadNegocioRC;
    }

    public void setUnidadNegocioRC(String unidadNegocioRC) {
        this.unidadNegocioRC = unidadNegocioRC;
    }

    public String getAutorizadoPorRC() {
        return autorizadoPorRC;
    }

    public void setAutorizadoPorRC(String autorizadoPorRC) {
        this.autorizadoPorRC = autorizadoPorRC;
    }

    public String getSolicitadoPorRC() {
        return solicitadoPorRC;
    }

    public void setSolicitadoPorRC(String solicitadoPorRC) {
        this.solicitadoPorRC = solicitadoPorRC;
    }

    public String getRazonRechazo() {
        return razonRechazo;
    }

    public void setRazonRechazo(String razonRechazo) {
        this.razonRechazo = razonRechazo;
    }

}
