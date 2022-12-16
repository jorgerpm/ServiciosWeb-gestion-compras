/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb;

import com.idebsystems.serviciosweb.dto.ReporteDTO;
import com.idebsystems.serviciosweb.servicio.ReporteServicio;
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
@Path("/reportes")
public class ReporteREST {
    
    private static final Logger LOGGER = Logger.getLogger(ReporteREST.class.getName());

    private final ReporteServicio service = new ReporteServicio();
    
    @GET
    @Path("/generarReportePdf")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public ReporteDTO generarReportePdf(
            @QueryParam(value = "tipo") String tipo,
            @QueryParam(value = "id") Long id
    ) throws Exception {
        try {
            LOGGER.log(Level.INFO, "tipo: {0}", tipo);
            LOGGER.log(Level.INFO, "id: {0}", id);
            //buscar en la bdd los roles
            return null; //service.generarReportePdf(tipo, id);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
