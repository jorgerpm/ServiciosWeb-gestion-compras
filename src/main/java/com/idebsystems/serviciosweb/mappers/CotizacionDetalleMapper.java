/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.CotizacionDetalleDTO;
import com.idebsystems.serviciosweb.entities.CotizacionDetalle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author jorge
 */
@Mapper
public interface CotizacionDetalleMapper {
    
    CotizacionDetalleMapper INSTANCE = Mappers.getMapper(CotizacionDetalleMapper.class);
            
    CotizacionDetalleDTO entityToDto(CotizacionDetalle cotizacionDetalle);
    CotizacionDetalle dtoToEntity(CotizacionDetalleDTO cotizacionDetalleDto);
}
