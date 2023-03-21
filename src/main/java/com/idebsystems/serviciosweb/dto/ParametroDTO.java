/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.idebsystems.serviciosweb.dto;

import java.util.Objects;

/**
 *
 * @author israe
 */
public class ParametroDTO {
    private long id;
    private String nombre;
    private String valor;
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

    public String getValor() {
        if(Objects.nonNull(valor)) 
            valor = valor.replaceAll("'", "");
        return valor;
    }

    public void setValor(String valor) {
        if(Objects.nonNull(valor)) 
            valor = valor.replaceAll("'", "");
        this.valor = valor;
    }

    public long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(long idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public String toString() {
        return "ParametroDTO{" + "id=" + id + ", nombre=" + nombre + ", valor=" + valor + ", idEstado=" + idEstado + '}';
    }
}
