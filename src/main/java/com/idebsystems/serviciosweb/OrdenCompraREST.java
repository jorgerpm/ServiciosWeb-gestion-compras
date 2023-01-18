/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb;

import com.idebsystems.serviciosweb.dto.OrdenCompraDTO;
import com.idebsystems.serviciosweb.servicio.OrdenCompraServicio;
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
            LOGGER.log(Level.INFO, "detalles: {0}", ordenCompraDTO.getListaDetalles());
            //guardar en la bdd el rol
            return servicio.generarOrdenCompra(ordenCompraDTO);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    @GET
    @Path("/listarOrdenesCompras")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<OrdenCompraDTO> listarOrdenesCompras(
            @QueryParam(value = "fechaInicial") String fechaInicial,
            @QueryParam(value = "fechaFinal") String fechaFinal,
            @QueryParam(value = "codigoRC") String codigoRC,
            @QueryParam(value = "codigoSolicitud") String codigoSolicitud,
            @QueryParam(value = "desde") Integer desde,
            @QueryParam(value = "hasta") Integer hasta) throws Exception {
        try {
            LOGGER.log(Level.INFO, "fechas: {0}", fechaInicial);
            LOGGER.log(Level.INFO, "fechas: {0}", fechaFinal);
            LOGGER.log(Level.INFO, "codigoRC: {0}", codigoRC);
            LOGGER.log(Level.INFO, "codigoSolicitud: {0}", codigoSolicitud);
            return servicio.listarOrdenesCompras(fechaInicial, fechaFinal, codigoRC, codigoSolicitud, desde, hasta);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    @POST
    @Path("/autorizarOrdenCompra")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    /*en el ordenCompraDto se debe enviar:
    //idUsuario
    //observacion
    //usuario
    //estado
    // y el id
    */
    public OrdenCompraDTO autorizarOrdenCompra(OrdenCompraDTO ordenCompraDto) throws Exception {
        try {
            LOGGER.log(Level.INFO, "ordenCompraDto: {0}", ordenCompraDto);
            return servicio.autorizarOrdenCompra(ordenCompraDto);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    
    @GET
    @Path("/listarOrdenesPorAutorizar")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<OrdenCompraDTO> listarOrdenesPorAutorizar(
            @QueryParam(value = "codigoRC") String codigoRC,
            @QueryParam(value = "codigoSolicitud") String codigoSolicitud,
            @QueryParam(value = "idUsuario") Long idUsuario,
            @QueryParam(value = "rolPrincipal") boolean rolPrincipal) throws Exception {
        try {
            LOGGER.log(Level.INFO, "idUsuario: {0}", idUsuario);
            LOGGER.log(Level.INFO, "rolPrincipal: {0}", rolPrincipal);
            LOGGER.log(Level.INFO, "codigoRC: {0}", codigoRC);
            LOGGER.log(Level.INFO, "codigoSolicitud: {0}", codigoSolicitud);
            return servicio.listarOrdenesPorAutorizar(codigoRC, codigoSolicitud, idUsuario, rolPrincipal);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
