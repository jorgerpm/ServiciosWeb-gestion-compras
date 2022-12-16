/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.PreguntaChecklistRecepcionDTO;
import com.idebsystems.serviciosweb.entities.PreguntaChecklistRecepcion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author jorge
 */
@Mapper
public interface PreguntaChecklistRecepcionMapper {
    
    PreguntaChecklistRecepcionMapper INSTANCE = Mappers.getMapper(PreguntaChecklistRecepcionMapper.class);
            
    PreguntaChecklistRecepcionDTO entityToDto(PreguntaChecklistRecepcion pregunta);
    PreguntaChecklistRecepcion dtoToEntity(PreguntaChecklistRecepcionDTO pregunatDto);
}
