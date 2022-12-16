/*
 * Proyecto API Rest (ServiciosWeb)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dto;

/**
 *
 * @author jorge
 */
public class ProveedorDTO {
    private Long id;
    private String nombreComercial;
    private String razonSocial;
    private String direccion;
    private String telefono1;
    private String telefono2;
    private String correo;
    private String ruc;
    private String codigoJD;
    private long idEstado;
    private String clave;
    
    private String contacto;
    private String contabilidad;
    private String telefonoContabilidad;
    private String correoContabilidad;
    private String carpeta;
    private String servicioProducto;
    private String credito;
    
    //
    private Integer totalRegistros;
    private String archivoBase64;

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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getArchivoBase64() {
        return archivoBase64;
    }

    public void setArchivoBase64(String archivoBase64) {
        this.archivoBase64 = archivoBase64;
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
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

    @Override
    public String toString() {
        return "ProveedorDTO{" + "id=" + id + ", nombreComercial=" + nombreComercial + ", razonSocial=" + razonSocial + ", direccion=" + direccion + ", telefono1=" + telefono1 + ", telefono2=" + telefono2 + ", correo=" + correo + ", ruc=" + ruc + ", codigoJD=" + codigoJD + ", idEstado=" + idEstado + ", clave=" + clave + '}';
    }
}
