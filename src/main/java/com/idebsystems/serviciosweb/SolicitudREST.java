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
            @QueryParam(value = "codigoSolicitud") String codigoSolicitud,
            @QueryParam(value = "codigoRC") String codigoRC,
            @QueryParam(value = "desde") Integer desde,
            @QueryParam(value = "hasta") Integer hasta,
            @QueryParam(value = "idUsuario") Long idUsuario) throws Exception {
        try {
            LOGGER.log(Level.INFO, "fechas: {0}", fechaInicial);
            LOGGER.log(Level.INFO, "fechas: {0}", fechaFinal);
            LOGGER.log(Level.INFO, "idUsuario: {0}", idUsuario);
            return servicio.listarSolicitudes(fechaInicial, fechaFinal, codigoSolicitud, codigoRC, desde, hasta, idUsuario);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    @POST
    @Path("/guardarSolicitud")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public SolicitudDTO guardarSolicitud(SolicitudDTO solicitudDto) throws Exception {
        try{
            LOGGER.log(Level.INFO, "solicitud1: {0}", solicitudDto);
            LOGGER.log(Level.INFO, "detalles: {0}", solicitudDto.getListaDetalles());
            return servicio.guardarSolicitud(solicitudDto);
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    @GET
    @Path("/buscarSolicitudPorNumero")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public SolicitudDTO buscarSolicitudPorNumero(
            @QueryParam(value = "numeroSolicitud") String numeroSolicitud,
            @QueryParam(value = "idUsuario") Long idUsuario) throws Exception {
        try{
            LOGGER.log(Level.INFO, "numeroSolicitud: {0}", numeroSolicitud);
            
            return servicio.buscarSolicitudPorNumero(numeroSolicitud, idUsuario);
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    @GET
    @Path("/getUltimoCodigoSolicitud")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public SolicitudDTO getUltimoCodigoSolicitud() throws Exception {
        try{
            return servicio.getUltimoCodigoSolicitud();
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
