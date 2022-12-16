/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb;

import com.idebsystems.serviciosweb.dto.ComparativoDTO;
import com.idebsystems.serviciosweb.servicio.ComparativoServicio;
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
@Path("/comparativo")
public class ComparativoREST {
    
    private static final Logger LOGGER = Logger.getLogger(ComparativoREST.class.getName());
    
    private final ComparativoServicio servicio = new ComparativoServicio();
    
    @POST
    @Path("/guardarComparativo")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public ComparativoDTO guardarComparativo(ComparativoDTO comparativoDTO) throws Exception {
        try {
            LOGGER.log(Level.INFO, "entroooooooooooo: {0}", comparativoDTO);
            //guardar en la bdd el rol
            return servicio.guardarComparativo(comparativoDTO);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    @GET
    @Path("/listarComparativos")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<ComparativoDTO> listarComparativos(
            @QueryParam(value = "fechaInicial") String fechaInicial,
            @QueryParam(value = "fechaFinal") String fechaFinal,
            @QueryParam(value = "codigoSolicitud") String codigoSolicitud,
            @QueryParam(value = "codigoRC") String codigoRC,
            @QueryParam(value = "desde") Integer desde,
            @QueryParam(value = "hasta") Integer hasta) throws Exception {
        try {
            LOGGER.log(Level.INFO, "fechas: {0}", fechaInicial);
            LOGGER.log(Level.INFO, "fechas: {0}", fechaFinal);
            LOGGER.log(Level.INFO, "codigoRC: {0}", codigoRC);
            return servicio.listarComparativos(fechaInicial, fechaFinal, codigoSolicitud, codigoRC, desde, hasta);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
