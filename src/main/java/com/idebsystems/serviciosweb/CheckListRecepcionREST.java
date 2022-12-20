/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb;

import com.idebsystems.serviciosweb.dto.CheckListRecepcionDTO;
import com.idebsystems.serviciosweb.dto.RespuestaDTO;
import com.idebsystems.serviciosweb.servicio.CheckListRecepcionServicio;
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
@Path("/checkListRecepcion")
public class CheckListRecepcionREST {
 
    private static final Logger LOGGER = Logger.getLogger(CheckListRecepcionREST.class.getName());
    
    private final CheckListRecepcionServicio servicio = new CheckListRecepcionServicio();
    
    @POST
    @Path("/generarCheckList")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RespuestaDTO generarCheckList(CheckListRecepcionDTO checkListRecepcionDTO) throws Exception {
        try {
            LOGGER.log(Level.INFO, "entroooooooooooo: {0}", checkListRecepcionDTO);
            //guardar en la bdd el rol
            return servicio.generarCheckList(checkListRecepcionDTO);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    @GET
    @Path("/listarCheckList")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<CheckListRecepcionDTO> listarCheckList(
            @QueryParam(value = "fechaInicial") String fechaInicial,
            @QueryParam(value = "fechaFinal") String fechaFinal,
            @QueryParam(value = "codigoSolicitud") String codigoSolicitud,
            @QueryParam(value = "codigoRC") String codigoRC,
            @QueryParam(value = "idUsuario") Long idUsuario,
            @QueryParam(value = "rolPrincipal") boolean rolPrincipal,
            @QueryParam(value = "desde") Integer desde,
            @QueryParam(value = "hasta") Integer hasta) throws Exception {
        try {
            LOGGER.log(Level.INFO, "fechas: {0}", fechaInicial);
            LOGGER.log(Level.INFO, "rolPrincipal: {0}", rolPrincipal);
            LOGGER.log(Level.INFO, "idUsuario: {0}", idUsuario);
            return servicio.listarCheckList(fechaInicial, fechaFinal, codigoSolicitud, codigoRC, idUsuario, rolPrincipal, desde, hasta);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    
    @POST
    @Path("/guardarCheckListRecepcion")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RespuestaDTO guardarCheckListRecepcion(CheckListRecepcionDTO checkListRecepcionDTO) throws Exception {
        try {
            LOGGER.log(Level.INFO, "getFechaRecepcionBodega: {0}", checkListRecepcionDTO.getFechaRecepcionBodega());
            LOGGER.log(Level.INFO, "getCodigoMaterial: {0}", checkListRecepcionDTO.getCodigoMaterial());
            LOGGER.log(Level.INFO, "getCantidadRecibida: {0}", checkListRecepcionDTO.getCantidadRecibida());
            //guardar en la bdd el rol
            return servicio.guardarCheckListRecepcion(checkListRecepcionDTO);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
}
