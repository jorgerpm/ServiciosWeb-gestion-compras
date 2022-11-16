/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb;

import com.idebsystems.serviciosweb.dto.AutorizacionOrdenCompraDTO;
import com.idebsystems.serviciosweb.dto.RespuestaDTO;
import com.idebsystems.serviciosweb.servicio.AutorizacionOrdenCompraServicio;
import java.util.List;
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
@Path("/autorizacionOrdenCompra")
public class AutorizacionOrdenCompraREST {
    
    private static final Logger LOGGER = Logger.getLogger(AutorizacionOrdenCompraREST.class.getName());
    
    private final AutorizacionOrdenCompraServicio servicio = new AutorizacionOrdenCompraServicio();
    
    @POST
    @Path("/guardarAutorizadores")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RespuestaDTO guardarAutorizadores(List<AutorizacionOrdenCompraDTO> listaAuts) throws Exception {
        try {
            LOGGER.log(Level.INFO, "entroooooooooooo: {0}", listaAuts);
            //guardar en la bdd el rol
            return servicio.guardarAutorizadores(listaAuts);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
}
