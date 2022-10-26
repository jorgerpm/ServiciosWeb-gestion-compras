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
            return servicio.listarCotizaciones(fechaInicial, fechaFinal, codigoRC, desde, hasta);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
