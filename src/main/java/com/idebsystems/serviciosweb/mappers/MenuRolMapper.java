/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.MenuRolDTO;
import com.idebsystems.serviciosweb.entities.MenuRol;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author israe
 */
@Mapper
public interface MenuRolMapper {
    MenuRolMapper INSTANCE = Mappers.getMapper(MenuRolMapper.class);
            
    MenuRolDTO entityToDto(MenuRol menuRol);
    MenuRol dtoToEntity(MenuRolDTO menuRolDto);
}
