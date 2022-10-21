/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.SolicitudDTO;
import com.idebsystems.serviciosweb.entities.Solicitud;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author jorge
 */
@Mapper
public interface SolicitudMapper {
    
    SolicitudMapper INSTANCE = Mappers.getMapper(SolicitudMapper.class);
            
    SolicitudDTO entityToDto(Solicitud solicitud);
    Solicitud dtoToEntity(SolicitudDTO solicitudDto);
}
