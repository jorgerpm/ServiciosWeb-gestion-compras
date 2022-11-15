/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb;

import com.idebsystems.serviciosweb.dto.HistorialDocumentoDTO;
import com.idebsystems.serviciosweb.servicio.HistorialDocumentoServicio;
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
@Path("/historialDocumento")
public class HistorialDocumentoREST {

    private static final Logger LOGGER = Logger.getLogger(HistorialDocumentoREST.class.getName());

    private final HistorialDocumentoServicio servicio = new HistorialDocumentoServicio();

    @GET
    @Path("/buscarHistorialDocs")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<HistorialDocumentoDTO> buscarHistorialDocs(
            @QueryParam(value = "codigoRC") String codigoRC, 
            @QueryParam(value = "tipoDocumento") String tipoDocumento) throws Exception {
        try {
            LOGGER.log(Level.INFO, "codigoRC: {0}", codigoRC);
            LOGGER.log(Level.INFO, "tipoDocumento: {0}", tipoDocumento);
            //buscar en la bdd los roles
            return servicio.buscarHistorialDocs(codigoRC, tipoDocumento);

        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
