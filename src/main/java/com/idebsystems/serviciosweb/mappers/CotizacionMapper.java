/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.mappers;

import com.idebsystems.serviciosweb.dto.CotizacionDTO;
import com.idebsystems.serviciosweb.entities.Cotizacion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author jorge
 */
@Mapper
public interface CotizacionMapper {
    
    CotizacionMapper INSTANCE = Mappers.getMapper(CotizacionMapper.class);
            
    CotizacionDTO entityToDto(Cotizacion cotizacion);
    Cotizacion dtoToEntity(CotizacionDTO cotizacionDto);
}
