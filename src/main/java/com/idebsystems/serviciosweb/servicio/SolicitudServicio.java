/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.SolicitudDAO;
import com.idebsystems.serviciosweb.dto.SolicitudDTO;
import com.idebsystems.serviciosweb.entities.Solicitud;
import com.idebsystems.serviciosweb.util.FechaUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import com.idebsystems.serviciosweb.mappers.SolicitudMapper;
import java.util.logging.Level;

/**
 *
 * @author jorge
 */
public class SolicitudServicio {

    private static final Logger LOGGER = Logger.getLogger(SolicitudServicio.class.getName());

    private final SolicitudDAO dao = new SolicitudDAO();

    public List<SolicitudDTO> listarSolicitudes(String fechaInicial, String fechaFinal, String codSolicitud,
            Integer desde, Integer hasta) throws Exception {
        try {
            List<SolicitudDTO> listaSolicitudDto = new ArrayList<>();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            List<Object> respuesta = dao.listarSolicitudes(FechaUtil.fechaInicial(sdf.parse(fechaInicial)), FechaUtil.fechaFinal(sdf.parse(fechaFinal)),
                    codSolicitud, desde, hasta);

            //sacar los resultados retornados
            Integer totalRegistros = (Integer) respuesta.get(0);
            //sacar los registros
            List<Solicitud> listaSolicitud = (List<Solicitud>) respuesta.get(1);

            listaSolicitud.forEach(sol -> {
                SolicitudDTO dto = SolicitudMapper.INSTANCE.entityToDto(sol);
                dto.setTotalRegistros(totalRegistros);
                listaSolicitudDto.add(dto);
            });

            return listaSolicitudDto;
            
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public SolicitudDTO guardarCotizacion(SolicitudDTO solicitudDto) throws Exception {
        try{
            Solicitud solicitud = SolicitudMapper.INSTANCE.dtoToEntity(solicitudDto);
            
            solicitud = dao.guardarCotizacion(solicitud);
            
            solicitudDto = SolicitudMapper.INSTANCE.entityToDto(solicitud);
            
            return solicitudDto;
            
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
