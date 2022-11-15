/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.HistorialDocumentoDTO;
import com.idebsystems.serviciosweb.entities.HistorialDocumento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author jorge
 */
@Mapper
public interface HistorialDocumentoMapper {
    
    HistorialDocumentoMapper INSTANCE = Mappers.getMapper(HistorialDocumentoMapper.class);
            
    HistorialDocumentoDTO entityToDto(HistorialDocumento historialDocumento);
    HistorialDocumento dtoToEntity(HistorialDocumentoDTO historialDocumentoDto);
    
}
