/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.FormaPagoDAO;
import com.idebsystems.serviciosweb.dto.FormaPagoDTO;
import com.idebsystems.serviciosweb.entities.FormaPago;
import com.idebsystems.serviciosweb.mappers.FormaPagoMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author israe
 */
public class FormaPagoServicio {
    private static final Logger LOGGER = Logger.getLogger(FormaPagoServicio.class.getName());
    
    private final FormaPagoDAO dao = new FormaPagoDAO();
    
    public List<FormaPagoDTO> listarFormasPago() throws Exception {
        try {
            List<FormaPagoDTO> listaFormaPagoDto = new ArrayList();
            
            List<FormaPago> listaFormaPago = dao.listarFormasPago();
            
            listaFormaPago.forEach(formaPago->{
                FormaPagoDTO formaPagoDto = new FormaPagoDTO();
                formaPagoDto = FormaPagoMapper.INSTANCE.entityToDto(formaPago);
                listaFormaPagoDto.add(formaPagoDto);
            });

            return listaFormaPagoDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public FormaPagoDTO guardarFormaPago(FormaPagoDTO formaPagoDto) throws Exception {
        try{
            FormaPago formaPago = FormaPagoMapper.INSTANCE.dtoToEntity(formaPagoDto);
            FormaPago formaPagoRespuesta = dao.guardarFormaPago(formaPago);
            formaPagoDto = FormaPagoMapper.INSTANCE.entityToDto(formaPagoRespuesta);
            return formaPagoDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
