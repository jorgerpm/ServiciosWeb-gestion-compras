/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.idebsystems.serviciosweb.dto;

/**
 *
 * @author israe
 */
public class MenuRolDTO {
    private long id;
    private long idRol;
    private long idMenu;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdRol() {
        return idRol;
    }

    public void setIdRol(long idRol) {
        this.idRol = idRol;
    }

    public long getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(long idMenu) {
        this.idMenu = idMenu;
    }

    @Override
    public String toString() {
        return "MenuRolDTO{" + "id=" + id + ", idRol=" + idRol + ", idMenu=" + idMenu + '}';
    }
    
    
}
