/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.OrdenCompraDTO;
import com.idebsystems.serviciosweb.entities.OrdenCompra;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author jorge
 */
@Mapper
public interface OrdenCompraMapper {
    
    OrdenCompraMapper INSTANCE = Mappers.getMapper(OrdenCompraMapper.class);
            
    OrdenCompraDTO entityToDto(OrdenCompra ordenCompra);
    OrdenCompra dtoToEntity(OrdenCompraDTO ordenCompraDto);
}
