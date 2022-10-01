/*
 * Proyecto API Rest (ServiciosWeb)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb;

import com.idebsystems.serviciosweb.dto.ArchivoXmlDTO;
import com.idebsystems.serviciosweb.servicio.ArchivoXmlServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author jorge
 */
@Path("/archivoXml")
public class ArchivoXmlREST {

    private static final Logger LOGGER = Logger.getLogger(ArchivoXmlREST.class.getName());

    private final ArchivoXmlServicio service = new ArchivoXmlServicio();

    @POST
    @Path("/cargarArchivoXml")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String cargarArchivoXml(ArchivoXmlDTO fileDto) throws Exception {
        try {
            LOGGER.log(Level.INFO, "entroooooooooooo: {0}", fileDto);
            //primero guardar el pdf RIDE
            service.guardarArchivoXml(fileDto.getPdfBase64());
            
            String pathSaveXml = service.guardarArchivoXml(fileDto.getXmlBase64());

            return service.guardarXmlToDB(pathSaveXml, fileDto.getNombreArchivoXml(), fileDto.getNombreArchivoPdf(), 
                    fileDto.getUrlArchivo(), fileDto.getIdUsuarioCarga(), fileDto.getTipoDocumento());

        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }

    @POST
    @Path("/guardarXmlDB")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String guardarXmlDB(ArchivoXmlDTO fileDto) throws Exception {
        try {
            LOGGER.log(Level.INFO, "entroooooooooooo: {0}", fileDto);
            return service.guardarXmlToDB(fileDto.getUbicacionArchivo(), fileDto.getNombreArchivoXml(), fileDto.getNombreArchivoPdf(), 
                    fileDto.getUrlArchivo(), fileDto.getIdUsuarioCarga(), fileDto.getTipoDocumento());
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    @GET
    @Path("/listarArchivosXml")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<ArchivoXmlDTO> listarArchivosXml() throws Exception {
        try {
            LOGGER.log(Level.INFO, "entroooooooooooo: {0}");
            //buscar en la bdd los roles
            return service.listarArchivosXml();
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
