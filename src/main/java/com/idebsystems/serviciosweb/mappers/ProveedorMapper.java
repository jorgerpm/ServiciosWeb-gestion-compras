/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.ProveedorDTO;
import com.idebsystems.serviciosweb.entities.Proveedor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author israe
 */
@Mapper
public interface ProveedorMapper {
    ProveedorMapper INSTANCE = Mappers.getMapper(ProveedorMapper.class);
            
    ProveedorDTO entityToDto(Proveedor proveedor);
    Proveedor dtoToEntity(ProveedorDTO proveedorDto);
}
