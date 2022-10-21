/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.SolicitudDetalleDTO;
import com.idebsystems.serviciosweb.entities.SolicitudDetalle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author jorge
 */
@Mapper
public interface SolicitudDetalleMapper {
    
    SolicitudDetalleMapper INSTANCE = Mappers.getMapper(SolicitudDetalleMapper.class);
            
    SolicitudDetalleDTO entityToDto(SolicitudDetalle solicitudDetalle);
    SolicitudDetalle dtoToEntity(SolicitudDetalleDTO solicitudDetalleDto);
}
