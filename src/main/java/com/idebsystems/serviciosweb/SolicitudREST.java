/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb;

import com.idebsystems.serviciosweb.dto.SolicitudDTO;
import com.idebsystems.serviciosweb.servicio.SolicitudServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author jorge
 */
@Path("/solicitud")
public class SolicitudREST {

    private static final Logger LOGGER = Logger.getLogger(SolicitudREST.class.getName());

    private final SolicitudServicio servicio = new SolicitudServicio();

    @GET
    @Path("/listarSolicitudes")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<SolicitudDTO> listarSolicitudes(
            @QueryParam(value = "fechaInicial") String fechaInicial,
            @QueryParam(value = "fechaFinal") String fechaFinal,
            @QueryParam(value = "codSolicitud") String codSolicitud,
            @QueryParam(value = "desde") Integer desde,
            @QueryParam(value = "hasta") Integer hasta) throws Exception {
        try {
            LOGGER.log(Level.INFO, "fechas: {0}", fechaInicial);
            LOGGER.log(Level.INFO, "fechas: {0}", fechaFinal);
            return servicio.listarSolicitudes(fechaInicial, fechaFinal, codSolicitud, desde, hasta);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    @POST
    @Path("/guardarSolicitud")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public SolicitudDTO guardarCotizacion(SolicitudDTO solicitudDto) throws Exception {
        try{
            LOGGER.log(Level.INFO, "solicitud: {0}", solicitudDto);
            LOGGER.log(Level.INFO, "detalles: {0}", solicitudDto.getListaDetalles());
            return servicio.guardarCotizacion(solicitudDto);
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
