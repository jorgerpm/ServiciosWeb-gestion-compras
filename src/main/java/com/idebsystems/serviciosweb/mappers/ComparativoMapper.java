/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.ComparativoDTO;
import com.idebsystems.serviciosweb.entities.Comparativo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author jorge
 */
@Mapper
public interface ComparativoMapper {
    
    ComparativoMapper INSTANCE = Mappers.getMapper(ComparativoMapper.class);
            
    ComparativoDTO entityToDto(Comparativo comparativo);
    Comparativo dtoToEntity(ComparativoDTO comparativoDTO);
}
