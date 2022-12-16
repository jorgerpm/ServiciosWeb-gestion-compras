/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.HistorialDocumentoDAO;
import com.idebsystems.serviciosweb.dao.UsuarioDAO;
import com.idebsystems.serviciosweb.dto.HistorialDocumentoDTO;
import com.idebsystems.serviciosweb.entities.HistorialDocumento;
import com.idebsystems.serviciosweb.entities.Usuario;
import com.idebsystems.serviciosweb.mappers.HistorialDocumentoMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class HistorialDocumentoServicio {
    
    private static final Logger LOGGER = Logger.getLogger(HistorialDocumentoServicio.class.getName());
    private final HistorialDocumentoDAO dao = new HistorialDocumentoDAO();
    
    public List<HistorialDocumentoDTO> buscarHistorialDocs(String codigoSolicitud, String codigoRC, String tipoDocumento) throws Exception {
        try {
            List<HistorialDocumentoDTO> listaDto = new ArrayList();
            
            List<HistorialDocumento> listaHist = dao.buscarHistorialDocs(codigoSolicitud, codigoRC, tipoDocumento);
            
            UsuarioDAO usdao = new UsuarioDAO();
            List<Usuario> listusers = usdao.listarUsuarios();
            
            listaHist.forEach(hist->{
                HistorialDocumentoDTO hisDto = HistorialDocumentoMapper.INSTANCE.entityToDto(hist);
                
                hisDto.setUsuarioCambio(listusers.stream().filter(u -> hist.getUsuarioCambio().equals(u.getId()+"")).findAny().orElse(new Usuario()).getNombre());
                hisDto.setTotalRegistros(listaHist.size());
                listaDto.add(hisDto);
            });

            return listaDto;
            
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
