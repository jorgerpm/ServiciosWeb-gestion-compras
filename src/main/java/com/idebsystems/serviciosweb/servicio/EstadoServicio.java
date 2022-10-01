/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.EstadoDAO;
import com.idebsystems.serviciosweb.dto.EstadoDTO;
import com.idebsystems.serviciosweb.entities.Estado;
import com.idebsystems.serviciosweb.mappers.EstadoMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author israe
 */
public class EstadoServicio {
    private static final Logger LOGGER = Logger.getLogger(EstadoServicio.class.getName());
    
    private final EstadoDAO dao = new EstadoDAO();
    
    public List<EstadoDTO> listarEstado() throws Exception {
        try {
            List<EstadoDTO> listaEstadoDto = new ArrayList<EstadoDTO>();
            
            List<Estado> listaEstado = dao.listarEstados();
            
            listaEstado.forEach(estado->{
                EstadoDTO estadoDto = new EstadoDTO();
                estadoDto = EstadoMapper.INSTANCE.entityToDto(estado);
                listaEstadoDto.add(estadoDto);
            });

            return listaEstadoDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public EstadoDTO guardarEstado(EstadoDTO estadoDto) throws Exception {
        try{
            Estado estado = EstadoMapper.INSTANCE.dtoToEntity(estadoDto);
            Estado estadoRespuesta = dao.guardarEstado(estado);
            estadoDto = EstadoMapper.INSTANCE.entityToDto(estadoRespuesta);
            return estadoDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
