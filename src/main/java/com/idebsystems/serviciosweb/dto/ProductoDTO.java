/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dto;

/**
 *
 * @author israe
 */
public class ProductoDTO {
    
    private long id;
    private String codigoProducto;
    private String nombre;
    private double valorUnitario;
    private boolean tieneIva;
    private long idEstado;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public boolean isTieneIva() {
        return tieneIva;
    }

    public void setTieneIva(boolean tieneIva) {
        this.tieneIva = tieneIva;
    }

    public long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(long idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public String toString() {
        return "ProductoDTO{" + "id=" + id + ", codigoProducto=" + codigoProducto + ", nombre=" + nombre + ", valorUnitario=" + valorUnitario + ", tieneIva=" + tieneIva + ", idEstado=" + idEstado + '}';
    }
}
