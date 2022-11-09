/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.OrdenCompraDetalleDTO;
import com.idebsystems.serviciosweb.entities.OrdenCompraDetalle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author jorge
 */
@Mapper
public interface OrdenCompraDetalleMapper {
    
    OrdenCompraDetalleMapper INSTANCE = Mappers.getMapper(OrdenCompraDetalleMapper.class);
            
    OrdenCompraDetalleDTO entityToDto(OrdenCompraDetalle ordenCompraDetalle);
    OrdenCompraDetalle dtoToEntity(OrdenCompraDetalleDTO ordenCompraDetalleDto);
}
