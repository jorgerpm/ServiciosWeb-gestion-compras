/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.CheckListRecepcionDTO;
import com.idebsystems.serviciosweb.entities.CheckListRecepcion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author jorge
 */
@Mapper
public interface CheckListRecepcionMapper {
    
    CheckListRecepcionMapper INSTANCE = Mappers.getMapper(CheckListRecepcionMapper.class);
            
    CheckListRecepcionDTO entityToDto(CheckListRecepcion recepcion);
    CheckListRecepcion dtoToEntity(CheckListRecepcionDTO recepcionDto);
}
