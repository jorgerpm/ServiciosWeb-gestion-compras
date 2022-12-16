/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.ComparativoDetalleDTO;
import com.idebsystems.serviciosweb.entities.ComparativoDetalle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author jorge
 */
@Mapper
public interface ComparativoDetalleMapper {
    
    ComparativoDetalleMapper INSTANCE = Mappers.getMapper(ComparativoDetalleMapper.class);
            
    ComparativoDetalleDTO entityToDto(ComparativoDetalle comparativoDetalle);
    ComparativoDetalle dtoToEntity(ComparativoDetalleDTO comparativoDetalleDTO);
}
