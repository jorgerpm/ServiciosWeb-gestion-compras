/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.AutorizacionOrdenCompraDTO;
import com.idebsystems.serviciosweb.entities.AutorizacionOrdenCompra;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author jorge
 */
@Mapper
public interface AutorizacionOrdenCompraMapper {
    
    AutorizacionOrdenCompraMapper INSTANCE = Mappers.getMapper(AutorizacionOrdenCompraMapper.class);
            
    AutorizacionOrdenCompraDTO entityToDto(AutorizacionOrdenCompra autorizacion);
    AutorizacionOrdenCompra dtoToEntity(AutorizacionOrdenCompraDTO autorizacionDto);
}
