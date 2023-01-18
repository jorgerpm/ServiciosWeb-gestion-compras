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
@Table(name = "check_list_recepcion")
@SequenceGenerator(name = "check_list_recepcion_id_seq", initialValue = 1, sequenceName = "check_list_recepcion_id_seq", allocationSize = 1)
public class CheckListRecepcion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "check_list_recepcion_id_seq")
    @Column(name = "id")
    private long id;

    
    @JoinColumn(name = "id_solicitud", referencedColumnName = "id")
    @OneToOne
    private Solicitud solicitud;
    
    @JoinColumn(name = "id_orden_compra", referencedColumnName = "id")
    @OneToOne
    private OrdenCompra ordenCompra;
    @Column(name = "codigo_solicitud")
    private String codigoSolicitud;
    @Column(name = "fecha_recepcion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecepcion;
    private String estado;
    private String usuario;
    
    @Column(name = "fecha_recepcion_bodega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecepcionBodega;
    @Column(name = "codigo_material")
    private String codigoMaterial;
    @Column(name = "cantidad_recibida")
    private int cantidadRecibida;
    
    @Column(name = "usuario_modifica")
    private Long usuarioModifica;
    @Column(name = "fecha_modifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModifica;
    
    
    @Column(name = "monto_total_factura")
    private BigDecimal montoTotalFactura;
    
    @OneToMany(mappedBy = "checkListRecepcion", fetch = FetchType.LAZY)
    private List<CheckListRecepcionDetalle> listaDetalles;
    //

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public String getCodigoSolicitud() {
        return codigoSolicitud;
    }

    public void setCodigoSolicitud(String codigoSolicitud) {
        this.codigoSolicitud = codigoSolicitud;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
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

    public List<CheckListRecepcionDetalle> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<CheckListRecepcionDetalle> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public Date getFechaRecepcionBodega() {
        return fechaRecepcionBodega;
    }

    public void setFechaRecepcionBodega(Date fechaRecepcionBodega) {
        this.fechaRecepcionBodega = fechaRecepcionBodega;
    }

    public String getCodigoMaterial() {
        return codigoMaterial;
    }

    public void setCodigoMaterial(String codigoMaterial) {
        this.codigoMaterial = codigoMaterial;
    }

    public int getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(int cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    public BigDecimal getMontoTotalFactura() {
        return montoTotalFactura;
    }

    public void setMontoTotalFactura(BigDecimal montoTotalFactura) {
        this.montoTotalFactura = montoTotalFactura;
    }
    

}
