/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb;

import com.idebsystems.serviciosweb.dto.OrdenCompraDTO;
import com.idebsystems.serviciosweb.servicio.OrdenCompraServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author jorge
 */
@Path("/ordenCompra")
public class OrdenCompraREST {
    
    private static final Logger LOGGER = Logger.getLogger(OrdenCompraREST.class.getName());
    
    private final OrdenCompraServicio servicio = new OrdenCompraServicio();
    
    @POST
    @Path("/generarOrdenCompra")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public OrdenCompraDTO generarOrdenCompra(OrdenCompraDTO ordenCompraDTO) throws Exception {
        try {
            LOGGER.log(Level.INFO, "entroooooooooooo: {0}", ordenCompraDTO);
            //guardar en la bdd el rol
            return servicio.generarOrdenCompra(ordenCompraDTO);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
