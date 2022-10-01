/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.MenuDAO;
import com.idebsystems.serviciosweb.dto.MenuDTO;
import com.idebsystems.serviciosweb.entities.Menu;
import com.idebsystems.serviciosweb.mappers.MenuMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author israe
 */
public class MenuServicio {
    private static final Logger LOGGER = Logger.getLogger(MenuServicio.class.getName());
    
    private final MenuDAO dao = new MenuDAO();
    
    public List<MenuDTO> listarMenus() throws Exception {
        try {
            List<MenuDTO> listaMenuDto = new ArrayList<MenuDTO>();
            
            List<Menu> listaMenu = dao.listarMenus();
            
            listaMenu.forEach(menu->{
                MenuDTO menuDto = new MenuDTO();
                menuDto = MenuMapper.INSTANCE.entityToDto(menu);
                listaMenuDto.add(menuDto);
            });

            return listaMenuDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public MenuDTO guardarMenu(MenuDTO menuDto) throws Exception {
        try{
            Menu menu = MenuMapper.INSTANCE.dtoToEntity(menuDto);
            Menu menuRespuesta = dao.guardarMenu(menu);
            menuDto = MenuMapper.INSTANCE.entityToDto(menuRespuesta);
            return menuDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public List<MenuDTO> listarMenusPorRol(String idRolDto) throws Exception {
        try {
            List<MenuDTO> listaMenuDto = new ArrayList<MenuDTO>();
            
            List<Menu> listaMenu = dao.listarMenus();
            
            listaMenu.forEach(menu->{
                MenuDTO menuDto = new MenuDTO();
                menuDto = MenuMapper.INSTANCE.entityToDto(menu);
                listaMenuDto.add(menuDto);
            });

            return listaMenuDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
