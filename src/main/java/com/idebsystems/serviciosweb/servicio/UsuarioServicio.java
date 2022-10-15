/*
 * Proyecto API Rest (ServiciosWeb)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.UsuarioDAO;
import com.idebsystems.serviciosweb.dto.UsuarioDTO;
import com.idebsystems.serviciosweb.entities.Usuario;
import com.idebsystems.serviciosweb.mappers.UsuarioMapper;
import com.idebsystems.serviciosweb.util.MyMD5;
import static com.idebsystems.serviciosweb.util.MyMD5.getInstance;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author jorge
 */
public class UsuarioServicio {

    private static final Logger LOGGER = Logger.getLogger(UsuarioServicio.class.getName());

    private final UsuarioDAO dao = new UsuarioDAO();

    public UsuarioDTO loginSistema(UsuarioDTO userDto) throws Exception {
        try {
            Usuario user = dao.loginSistema(userDto.getUsuario(), userDto.getClave());
            
            if (Objects.nonNull(user)) {

                userDto = UsuarioMapper.INSTANCE.entityToDto(user);

            } else {
                return new UsuarioDTO();
            }

            return userDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public List<UsuarioDTO> listarUsuarios() throws Exception {
        try {
            List<UsuarioDTO> listaUsuarioDto = new ArrayList<UsuarioDTO>();
            
            List<Usuario> listaUsuario = dao.listarUsuarios();
            
            listaUsuario.forEach(usuario->{
                UsuarioDTO usuarioDto = new UsuarioDTO();
                usuarioDto = UsuarioMapper.INSTANCE.entityToDto(usuario);
                listaUsuarioDto.add(usuarioDto);
            });

            return listaUsuarioDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public UsuarioDTO guardarUsuario(UsuarioDTO usuarioDto) throws Exception {
        try{
            Usuario usuario = UsuarioMapper.INSTANCE.dtoToEntity(usuarioDto);
            Usuario usuarioRespuesta = dao.guardarUsuario(usuario);
            usuarioDto = UsuarioMapper.INSTANCE.entityToDto(usuarioRespuesta);
            return usuarioDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public UsuarioDTO generarClavePorCorreo(String correo) throws Exception {
        try{
            Usuario usuario = dao.buscarUsuarioPorCorreo(correo);
            if(Objects.isNull(usuario))
                return null;//el usuario con ese correo no existe
            
            //se procede a generar la nueva clave
            final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            SecureRandom random = new SecureRandom();
            String nuevaClave = IntStream.range(0, 8)
                .map(i -> random.nextInt(chars.length()))
                .mapToObj(randomIndex -> String.valueOf(chars.charAt(randomIndex)))
                .collect(Collectors.joining());
            
            MyMD5 md = getInstance();
            usuario.setClave(md.hashData(nuevaClave.getBytes()));
            
            usuario = dao.guardarUsuario(usuario);
            
            UsuarioDTO usuarioDto = UsuarioMapper.INSTANCE.entityToDto(usuario);
            usuarioDto.setClave(nuevaClave);
            
            return usuarioDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}