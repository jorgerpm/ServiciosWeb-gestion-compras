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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jorge
 */
@Entity
@Table(name = "orden_compra")
public class OrdenCompra implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "fecha_orden_compra")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOrdenCompra;
    @Column(name = "codigo_rc")
    private String codigoRC;
    @Column(name = "codigo_orden_compra")
    private String codigoOrdenCompra;
    private String estado;
    private String usuario;

    @Column(name = "ruc_proveedor")
    private String rucProveedor;

    private BigDecimal subtotal;
    @Column(name = "subtotal_sin_iva")
    private BigDecimal subtotalSinIva;
    private BigDecimal iva;
    private BigDecimal total;
    private BigDecimal descuento = BigDecimal.ZERO;
    private String observacion;
    @Column(name = "forma_pago")
    private String formaPago;

    @OneToMany(mappedBy = "ordenCompra", fetch = FetchType.LAZY)
    private List<OrdenCompraDetalle> listaDetalles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaOrdenCompra() {
        return fechaOrdenCompra;
    }

    public void setFechaOrdenCompra(Date fechaOrdenCompra) {
        this.fechaOrdenCompra = fechaOrdenCompra;
    }

    public String getCodigoRC() {
        return codigoRC;
    }

    public void setCodigoRC(String codigoRC) {
        this.codigoRC = codigoRC;
    }

    public String getCodigoOrdenCompra() {
        return codigoOrdenCompra;
    }

    public void setCodigoOrdenCompra(String codigoOrdenCompra) {
        this.codigoOrdenCompra = codigoOrdenCompra;
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

    public String getRucProveedor() {
        return rucProveedor;
    }

    public void setRucProveedor(String rucProveedor) {
        this.rucProveedor = rucProveedor;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getSubtotalSinIva() {
        return subtotalSinIva;
    }

    public void setSubtotalSinIva(BigDecimal subtotalSinIva) {
        this.subtotalSinIva = subtotalSinIva;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public List<OrdenCompraDetalle> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<OrdenCompraDetalle> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }
    
    
}
