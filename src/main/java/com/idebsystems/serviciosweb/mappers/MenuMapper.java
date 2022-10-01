/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.MenuDTO;
import com.idebsystems.serviciosweb.entities.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author israe
 */
@Mapper
public interface MenuMapper {
    MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);
            
    MenuDTO entityToDto(Menu menu);
    Menu dtoToEntity(MenuDTO menuDto);
}
