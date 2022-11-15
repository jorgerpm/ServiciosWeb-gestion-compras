/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author jorge
 */
public class HistorialDocumentoDTO {
 
    private long id;
    private Long idDocumento;
    private String documento;
    private String codigoRC;
    private String estado;
    private Date fechaCambio;
    private String usuarioCambio;
    private BigDecimal valorTotal;
    private String observacion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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

    public Date getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public String getUsuarioCambio() {
        return usuarioCambio;
    }

    public void setUsuarioCambio(String usuarioCambio) {
        this.usuarioCambio = usuarioCambio;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public String toString() {
        return "HistorialDocumentoDTO{" + "id=" + id + ", idDocumento=" + idDocumento + ", documento=" + documento + ", codigoRC=" + codigoRC + ", estado=" + estado + ", fechaCambio=" + fechaCambio + ", usuarioCambio=" + usuarioCambio + ", valorTotal=" + valorTotal + ", observacion=" + observacion + '}';
    }
    
}
