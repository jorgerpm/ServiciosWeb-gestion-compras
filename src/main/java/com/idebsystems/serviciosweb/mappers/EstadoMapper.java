/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.EstadoDTO;
import com.idebsystems.serviciosweb.entities.Estado;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author israe
 */
@Mapper
public interface EstadoMapper {
    EstadoMapper INSTANCE = Mappers.getMapper(EstadoMapper.class);
            
    EstadoDTO entityToDto(Estado estado);
    Estado dtoToEntity(EstadoDTO estadoDto);
}
