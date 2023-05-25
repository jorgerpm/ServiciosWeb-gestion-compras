/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.ParametroDAO;
import com.idebsystems.serviciosweb.dto.SolicitudDTO;
import com.idebsystems.serviciosweb.entities.Parametro;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class IntegracionJDServicio {
    
    private static final Logger LOGGER = Logger.getLogger(IntegracionJDServicio.class.getName());
    
    public SolicitudDTO buscarRCJD(String codigoRC) throws Exception {
        try{
            //aqui llamar al servicio externo que consume la base de oracle JD
            //primero buscar la url de invocacion del servicio registrado en los parametros
            ParametroDAO pdao = new ParametroDAO();
            Parametro param = pdao.buscarParametroPorId(44);
            
            String urlws = param.getValor();
            
            
            //el body tipo json para enviar
            String body = "{\"codigoRC\": " + codigoRC + "}";
            
            //invocacion al servicio
            HttpClient httpClient = HttpClient.newBuilder().build();

            HttpRequest request = HttpRequest.newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString(body))
            //.header("Authorization", "Basic " + Base64.getEncoder().encodeToString(("baeldung:123456").getBytes()))
            .uri(URI.create(urlws))
            .build();

            HttpResponse<String> response = httpClient
			.send(request, HttpResponse.BodyHandlers.ofString());
            
            
//            response.statusCode();
//            response.body(); //viene en json
            
            LOGGER.log(Level.INFO, "response.statusCode(): {0}", response.statusCode());
            LOGGER.log(Level.INFO, "response.body(): {0}", response.body());
            
            return new SolicitudDTO();
            
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
