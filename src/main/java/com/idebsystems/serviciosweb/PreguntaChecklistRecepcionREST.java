/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb;

import com.idebsystems.serviciosweb.dto.PreguntaChecklistRecepcionDTO;
import com.idebsystems.serviciosweb.servicio.PreguntaChecklistRecepcionServicio;
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
@Path("/preguntaChecklistRecepcion")
public class PreguntaChecklistRecepcionREST {
    
    private static final Logger LOGGER = Logger.getLogger(PreguntaChecklistRecepcionREST.class.getName());
    
    private final PreguntaChecklistRecepcionServicio servicio = new PreguntaChecklistRecepcionServicio();
    
    @GET
    @Path("/listarPreguntas")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<PreguntaChecklistRecepcionDTO> listarPreguntas() throws Exception {
        try {
            LOGGER.log(Level.INFO, "entroooooooooooo: {0}");
            //buscar en la bdd los roles
            return servicio.listarPreguntas();
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    @POST
    @Path("/guardarPregunta")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public PreguntaChecklistRecepcionDTO guardarPregunta(PreguntaChecklistRecepcionDTO preguntaDto) throws Exception {
        try {
            LOGGER.log(Level.INFO, "entroooooooooooo: {0}", preguntaDto);
            //guardar en la bdd el rol
            return servicio.guardarPregunta(preguntaDto);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    @GET
    @Path("/buscarPreguntasPorRol")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<PreguntaChecklistRecepcionDTO> buscarPreguntasPorRol(@QueryParam(value = "idRol") Long idRol) throws Exception {
        try {
            LOGGER.log(Level.INFO, "idRol: {0}", idRol);
            //buscar en la bdd los roles
            return servicio.buscarPreguntasPorRol(idRol);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
