/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb;

import com.idebsystems.serviciosweb.dto.ProductoDTO;
import com.idebsystems.serviciosweb.servicio.ProductoServicio;
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
 * @author israe
 */
@Path("producto")
public class ProductoREST {
    private static final Logger LOGGER = Logger.getLogger(ProductoREST.class.getName());

    private final ProductoServicio service = new ProductoServicio();
    
    @GET
    @Path("/listarProductos")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<ProductoDTO> listarProductos(
            @QueryParam(value = "desde") Integer desde,
            @QueryParam(value = "hasta") Integer hasta,
            @QueryParam(value = "valBusq") String valorBusqueda) throws Exception {
        try {
            LOGGER.log(Level.INFO, "desde: {0}", desde);
            LOGGER.log(Level.INFO, "hasta: {0}", hasta);
            LOGGER.log(Level.INFO, "valorBusqueda: {0}", valorBusqueda);
            //buscar en la bdd los productos
            return service.listarProductos(desde, hasta, valorBusqueda);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    @POST
    @Path("/guardarProducto")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public ProductoDTO guardarProducto(ProductoDTO productoDto) throws Exception {
        try {
            LOGGER.log(Level.INFO, "entroooooooooooo: {0}");
            //guardar en la bdd el producto
            return service.guardarProducto(productoDto);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
