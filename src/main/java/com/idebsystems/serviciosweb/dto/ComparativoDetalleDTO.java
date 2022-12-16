/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dto;

/**
 *
 * @author jorge
 */
public class ComparativoDetalleDTO {
    
    private Long id;
    private CotizacionDTO cotizacion;
    private Boolean seleccionada;
    //
    private ProveedorDTO proveedorDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CotizacionDTO getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(CotizacionDTO cotizacion) {
        this.cotizacion = cotizacion;
    }

    public Boolean getSeleccionada() {
        return seleccionada;
    }

    public void setSeleccionada(Boolean seleccionada) {
        this.seleccionada = seleccionada;
    }

    public ProveedorDTO getProveedorDto() {
        return proveedorDto;
    }

    public void setProveedorDto(ProveedorDTO proveedorDto) {
        this.proveedorDto = proveedorDto;
    }

    @Override
    public String toString() {
        return "ComparativoDetalleDTO{" + "id=" + id + ", seleccionada=" + seleccionada + '}';
    }
    
    
}
