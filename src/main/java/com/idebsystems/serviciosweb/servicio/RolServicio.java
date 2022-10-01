/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.RolDAO;
import com.idebsystems.serviciosweb.dto.RolDTO;
import com.idebsystems.serviciosweb.entities.Rol;
import com.idebsystems.serviciosweb.mappers.RolMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author israe
 */
public class RolServicio {
    private static final Logger LOGGER = Logger.getLogger(RolServicio.class.getName());
    
    private final RolDAO dao = new RolDAO();
    
    public List<RolDTO> listarRoles() throws Exception {
        try {
            List<RolDTO> listaRolDto = new ArrayList<RolDTO>();
            
            List<Rol> listaRol = dao.listarRoles();

            /*for(int i=0; i<listaRol.size();i++) {
                Rol rol = listaRol.get(i);
                //List<RolDTO> listaRolDto = new Li
                RolDTO rolDto = new RolDTO();
                rolDto = RolMapper.INSTANCE.entityToDto(rol);
                listaRolDto.add(rolDto);
            }*/
            
            listaRol.forEach(rol->{
                RolDTO rolDto = new RolDTO();
                rolDto = RolMapper.INSTANCE.entityToDto(rol);
                listaRolDto.add(rolDto);
            });
            
            /*for(Rol rol:listaRol) {
                RolDTO rolDto = new RolDTO();
                rolDto = RolMapper.INSTANCE.entityToDto(rol);
                listaRolDto.add(rolDto);
            }*/

            return listaRolDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public RolDTO guardarRol(RolDTO rolDto) throws Exception {
        try{
            Rol rol = RolMapper.INSTANCE.dtoToEntity(rolDto);
            Rol rolRespuesta = dao.guardarRol(rol);
            rolDto = RolMapper.INSTANCE.entityToDto(rolRespuesta);
            return rolDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
