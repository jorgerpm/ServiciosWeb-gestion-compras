/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.CotizacionDAO;
import com.idebsystems.serviciosweb.dto.CotizacionDTO;
import com.idebsystems.serviciosweb.entities.Cotizacion;
import com.idebsystems.serviciosweb.mappers.CotizacionMapper;
import com.idebsystems.serviciosweb.util.FechaUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class CotizacionServicio {
    
    private static final Logger LOGGER = Logger.getLogger(CotizacionServicio.class.getName());

    private final CotizacionDAO dao = new CotizacionDAO();

    public List<CotizacionDTO> listarCotizaciones(String fechaInicial, String fechaFinal, String codigoRC,
            Integer desde, Integer hasta) throws Exception {
        try {
            List<CotizacionDTO> listaCotizacionDto = new ArrayList<>();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            List<Object> respuesta = dao.listarCotizaciones(FechaUtil.fechaInicial(sdf.parse(fechaInicial)), FechaUtil.fechaFinal(sdf.parse(fechaFinal)),
                    codigoRC, desde, hasta);

            //sacar los resultados retornados
            Integer totalRegistros = (Integer) respuesta.get(0);
            //sacar los registros
            List<Cotizacion> listaCotizacion = (List<Cotizacion>) respuesta.get(1);

            listaCotizacion.forEach(sol -> {
                CotizacionDTO dto = CotizacionMapper.INSTANCE.entityToDto(sol);
                dto.setTotalRegistros(totalRegistros);
                listaCotizacionDto.add(dto);
            });

            return listaCotizacionDto;
            
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
