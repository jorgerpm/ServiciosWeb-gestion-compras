/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dto;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author jorge
 */
public class AutorizacionOrdenCompraDTO {

    private long id;
    private Long idOrdenCompra;
    private Long idUsuario;
    private Date fechaAutoriza;
    private String usuarioAutoriza;
    private String estado;
    private String observacion;
    private String usuarioModifica;
    //
    private String nombreUsuario;
    private String usuariosEliminar;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(Long idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaAutoriza() {
        return fechaAutoriza;
    }

    public void setFechaAutoriza(Date fechaAutoriza) {
        this.fechaAutoriza = fechaAutoriza;
    }

    public String getUsuarioAutoriza() {
        return usuarioAutoriza;
    }

    public void setUsuarioAutoriza(String usuarioAutoriza) {
        this.usuarioAutoriza = usuarioAutoriza;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        if(Objects.nonNull(observacion)) 
            observacion = observacion.replaceAll("'", "");
        return observacion;
    }

    public void setObservacion(String observacion) {
        if(Objects.nonNull(observacion)) 
            observacion = observacion.replaceAll("'", "");
        this.observacion = observacion;
    }

    public String getUsuarioModifica() {
        return usuarioModifica;
    }

    public void setUsuarioModifica(String usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getUsuariosEliminar() {
        return usuariosEliminar;
    }

    public void setUsuariosEliminar(String usuariosEliminar) {
        this.usuariosEliminar = usuariosEliminar;
    }

    @Override
    public String toString() {
        return "AutorizacionOrdenCompraDTO{" + "id=" + id + ", idOrdenCompra=" + idOrdenCompra + ", idUsuario=" + idUsuario + ", fechaAutoriza=" + fechaAutoriza + ", usuarioAutoriza=" + usuarioAutoriza + ", estado=" + estado + ", observacion=" + observacion + ", usuarioModifica=" + usuarioModifica + ", nombreUsuario=" + nombreUsuario + ", usuariosEliminar=" + usuariosEliminar + '}';
    }

}
