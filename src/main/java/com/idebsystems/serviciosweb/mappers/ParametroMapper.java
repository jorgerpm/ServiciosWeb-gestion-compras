/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.ParametroDTO;
import com.idebsystems.serviciosweb.entities.Parametro;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author israe
 */
@Mapper
public interface ParametroMapper {
    ParametroMapper INSTANCE = Mappers.getMapper(ParametroMapper.class);
            
    ParametroDTO entityToDto(Parametro parametro);
    Parametro dtoToEntity(ParametroDTO parametroDto);
}
