/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.PreguntaChecklistRecepcionDAO;
import com.idebsystems.serviciosweb.dao.RolDAO;
import com.idebsystems.serviciosweb.dto.PreguntaChecklistRecepcionDTO;
import com.idebsystems.serviciosweb.entities.PreguntaChecklistRecepcion;
import com.idebsystems.serviciosweb.entities.Rol;
import com.idebsystems.serviciosweb.mappers.PreguntaChecklistRecepcionMapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class PreguntaChecklistRecepcionServicio {
    
    private static final Logger LOGGER = Logger.getLogger(PreguntaChecklistRecepcionServicio.class.getName());
    
    private final PreguntaChecklistRecepcionDAO dao = new PreguntaChecklistRecepcionDAO();
    
    public List<PreguntaChecklistRecepcionDTO> listarPreguntas() throws Exception {
        try {
            List<PreguntaChecklistRecepcion> preguntas = dao.listarPreguntas();
           
            //para buscar el rol
            RolDAO roldao = new RolDAO();
            
            List<PreguntaChecklistRecepcionDTO> preguntasDto = new ArrayList<>();
                    
            for(PreguntaChecklistRecepcion pregunta : preguntas){
                
                PreguntaChecklistRecepcionDTO preguntaDto = PreguntaChecklistRecepcionMapper.INSTANCE.entityToDto(pregunta);
                
                Rol rol = roldao.buscarRolPorId(pregunta.getIdRol());
                preguntaDto.setRol(rol);
                
                preguntasDto.add(preguntaDto);
            }
            
            return preguntasDto;
            
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    public PreguntaChecklistRecepcionDTO guardarPregunta(PreguntaChecklistRecepcionDTO preguntaDto) throws Exception {
        try {
            
            PreguntaChecklistRecepcion pregunta = PreguntaChecklistRecepcionMapper.INSTANCE.dtoToEntity(preguntaDto);
            
            pregunta.setFechaModifica(new Date());
            
            pregunta = dao.guardarPregunta(pregunta);
            
            preguntaDto = PreguntaChecklistRecepcionMapper.INSTANCE.entityToDto(pregunta);
            
            return preguntaDto;
            
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    
    public List<PreguntaChecklistRecepcionDTO> buscarPreguntasPorRol(Long idRol) throws Exception {
        try {
            List<PreguntaChecklistRecepcion> preguntas = dao.buscarPreguntasPorRol(idRol);
           
            //para buscar el rol
            RolDAO roldao = new RolDAO();
            
            List<PreguntaChecklistRecepcionDTO> preguntasDto = new ArrayList<>();
                    
            for(PreguntaChecklistRecepcion pregunta : preguntas){
                
                PreguntaChecklistRecepcionDTO preguntaDto = PreguntaChecklistRecepcionMapper.INSTANCE.entityToDto(pregunta);
                
                preguntasDto.add(preguntaDto);
            }
            
            return preguntasDto;
            
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
