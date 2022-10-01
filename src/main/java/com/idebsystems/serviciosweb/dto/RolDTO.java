/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.idebsystems.serviciosweb.dto;

/**
 *
 * @author israe
 */
public class RolDTO {
    
    private long id;
    private String nombre;
    private long idEstado;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(long idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public String toString() {
        return "RolDTO{" + "id=" + id + ", nombre=" + nombre + ", idEstado=" + idEstado + '}';
    }

}
