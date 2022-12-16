/*
 * Proyecto API Rest (ServiciosWeb)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jorge
 */
@Entity
@Table(name = "proveedor")
public class Proveedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ruc;
    private String nombreComercial;
    private String razonSocial;
    private String direccion;
    private String telefono1;
    private String telefono2;
    private String contacto;
    private String correo;
    private String contabilidad;
    @Column(name = "telefono_contabilidad")
    private String telefonoContabilidad;
    @Column(name = "correo_contabilidad")
    private String correoContabilidad;
    private String codigoJD;
    private long idEstado;
    private String carpeta;
    @Column(name = "servicio_producto")
    private String servicioProducto;
    private String credito;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCodigoJD() {
        return codigoJD;
    }

    public void setCodigoJD(String codigoJD) {
        this.codigoJD = codigoJD;
    }

    public long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(long idEstado) {
        this.idEstado = idEstado;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getContabilidad() {
        return contabilidad;
    }

    public void setContabilidad(String contabilidad) {
        this.contabilidad = contabilidad;
    }

    public String getCorreoContabilidad() {
        return correoContabilidad;
    }

    public void setCorreoContabilidad(String correoContabilidad) {
        this.correoContabilidad = correoContabilidad;
    }

    

    public String getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(String carpeta) {
        this.carpeta = carpeta;
    }

    public String getServicioProducto() {
        return servicioProducto;
    }

    public void setServicioProducto(String servicioProducto) {
        this.servicioProducto = servicioProducto;
    }

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public String getTelefonoContabilidad() {
        return telefonoContabilidad;
    }

    public void setTelefonoContabilidad(String telefonoContabilidad) {
        this.telefonoContabilidad = telefonoContabilidad;
    }
    
    
}
