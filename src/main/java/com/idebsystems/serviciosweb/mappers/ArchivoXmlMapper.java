/*
 * Proyecto API Rest (ServiciosWeb)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.ArchivoXmlDTO;
import com.idebsystems.serviciosweb.entities.ArchivoXml;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author jorge
 */
@Mapper
public interface ArchivoXmlMapper {
    ArchivoXmlMapper INSTANCE = Mappers.getMapper(ArchivoXmlMapper.class);
            
    ArchivoXmlDTO entityToDto(ArchivoXml data);
    ArchivoXml dtoToEntity(ArchivoXmlDTO rolDto);
}
