/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.FormaPagoDTO;
import com.idebsystems.serviciosweb.entities.FormaPago;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author israe
 */
@Mapper
public interface FormaPagoMapper {
    FormaPagoMapper INSTANCE = Mappers.getMapper(FormaPagoMapper.class);
            
    FormaPagoDTO entityToDto(FormaPago formaPago);
    FormaPago dtoToEntity(FormaPagoDTO formaPagoDto);
}
