/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.idebsystems.serviciosweb;

import com.idebsystems.serviciosweb.dto.ProveedorDTO;
import com.idebsystems.serviciosweb.servicio.ProveedorServicio;
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
@Path("/proveedor")
public class ProveedorREST {
    private static final Logger LOGGER = Logger.getLogger(ProveedorREST.class.getName());

    private final ProveedorServicio service = new ProveedorServicio();
    
    @GET
    @Path("/buscarProveedorRuc")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public ProveedorDTO buscarProveedorRuc(@QueryParam(value = "ruc") String ruc) throws Exception {
        try {
            LOGGER.log(Level.INFO, "entroooooooooooo: {0}");
            //buscar en la bdd los roles
            return service.buscarProveedorRuc(ruc);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    @GET
    @Path("/listarProveedores")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<ProveedorDTO> listarProveedores() throws Exception {
        try {
            LOGGER.log(Level.INFO, "entroooooooooooo: {0}");
            //buscar en la bdd los proveedores
            return service.listarProveedores();
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    @POST
    @Path("/guardarProveedor")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public ProveedorDTO guardarProveedor(ProveedorDTO proveedorDto) throws Exception {
        try {
            LOGGER.log(Level.INFO, "entroooooooooooo: {0}");
            //guardar en la bdd el rol
            return service.guardarProveedor(proveedorDto);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    @POST
    @Path("/guardarProveedorUsuario")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public ProveedorDTO guardarProveedorUsuario(ProveedorDTO proveedorDto) throws Exception {
        try {
            LOGGER.log(Level.INFO, "entroooooooooooo: {0}");
            //guardar en la bdd el rol
            return service.guardarProveedorUsuario(proveedorDto);
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    @POST
    @Path("/cargaMasivaProveedores")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public ProveedorDTO cargaMasivaProveedores(ProveedorDTO proveedorDto) throws Exception {
        try {
            LOGGER.log(Level.INFO, "entroooooooooooo: {0}", proveedorDto);
            LOGGER.log(Level.INFO, "entroooooooooooo: {0}", proveedorDto.getArchivoBase64());
            //guardar en la bdd el rol
            service.cargaMasivaProveedores(proveedorDto.getArchivoBase64());
            return proveedorDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
