/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.RolDTO;
import com.idebsystems.serviciosweb.entities.Rol;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author israe
 */
@Mapper
public interface RolMapper {
    RolMapper INSTANCE = Mappers.getMapper(RolMapper.class);
            
    RolDTO entityToDto(Rol rol);
    Rol dtoToEntity(RolDTO rolDto);
}
