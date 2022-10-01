/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.MenuRolDAO;
import com.idebsystems.serviciosweb.dto.MenuRolDTO;
import com.idebsystems.serviciosweb.entities.MenuRol;
import com.idebsystems.serviciosweb.mappers.MenuRolMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author israe
 */
public class MenuRolServicio {
    private static final Logger LOGGER = Logger.getLogger(MenuRolServicio.class.getName());
    
    private final MenuRolDAO dao = new MenuRolDAO();
    
    public List<MenuRolDTO> listarMenuRoles() throws Exception {
        try {
            List<MenuRolDTO> listaMenuRolDto = new ArrayList<MenuRolDTO>();
            
            List<MenuRol> listaMenuRol = dao.listarMenuRoles();
            
            listaMenuRol.forEach(menuRol->{
                MenuRolDTO menuRolDto = new MenuRolDTO();
                menuRolDto = MenuRolMapper.INSTANCE.entityToDto(menuRol);
                listaMenuRolDto.add(menuRolDto);
            });

            return listaMenuRolDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public MenuRolDTO guardarMenuRol(MenuRolDTO menuRolDto) throws Exception {
        try{
            MenuRol menuRol = MenuRolMapper.INSTANCE.dtoToEntity(menuRolDto);
            MenuRol menuRolRespuesta = dao.guardarMenuRol(menuRol);
            menuRolDto = MenuRolMapper.INSTANCE.entityToDto(menuRolRespuesta);
            return menuRolDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
