/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb;

import com.idebsystems.serviciosweb.dto.CotizacionDTO;
import com.idebsystems.serviciosweb.servicio.CotizacionServicio;
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
@Path("/cotizacion")
public class CotizacionREST {
    
    private static final Logger LOGGER = Logger.getLogger(CotizacionREST.class.getName());

    private final CotizacionServicio servicio = new CotizacionServicio();

    @GET
    @Path("/listarCotizaciones")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<CotizacionDTO> listarCotizaciones(
            @QueryParam(value = "fechaInicial") String fechaInicial,
            @QueryParam(value = "fechaFinal") String fechaFinal,
            @QueryParam(value = "codigoRC") String codigoRC,
            @QueryParam(value = "desde") Integer desde,
            @QueryParam(value = "hasta") Integer hasta) throws Exception {
        try {
            LOGGER.log(Level.INFO, "fechas: {0}", fechaInicial);
            LOGGER.log(Level.INFO, "fechas: {0}", fechaFinal);
            LOGGER.log(Level.INFO, "codigoRC: {0}", codigoRC);
            return servicio.listarCotizaciones(fechaInicial, fechaFinal, codigoRC, desde, hasta);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    @POST
    @Path("/guardarCotizacion")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public CotizacionDTO guardarCotizacion(CotizacionDTO cotizacionDTO) throws Exception {
        try{
            LOGGER.log(Level.INFO, "solicitud: {0}", cotizacionDTO);
            LOGGER.log(Level.INFO, "detalles: {0}", cotizacionDTO.getListaDetalles());
            return servicio.guardarCotizacion(cotizacionDTO);
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    @GET
    @Path("/buscarCotizacionRucNumeroRC")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public CotizacionDTO buscarCotizacionRucNumeroRC(
            @QueryParam(value = "codigoRC") String codigoRC,
            @QueryParam(value = "ruc") String ruc) throws Exception {
        try {
            LOGGER.log(Level.INFO, "codigoRC: {0}", codigoRC);
            LOGGER.log(Level.INFO, "ruc: {0}", ruc);
            
            return servicio.buscarCotizacionRucNumeroRC(codigoRC, ruc);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}