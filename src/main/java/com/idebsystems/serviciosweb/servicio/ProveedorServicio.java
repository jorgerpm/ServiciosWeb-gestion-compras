/*
 * Proyecto API Rest (ServiciosWeb)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.ProveedorDAO;
import com.idebsystems.serviciosweb.dto.ProveedorDTO;
import com.idebsystems.serviciosweb.entities.Proveedor;
import com.idebsystems.serviciosweb.entities.Usuario;
import com.idebsystems.serviciosweb.mappers.ProveedorMapper;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class ProveedorServicio {
    private static final Logger LOGGER = Logger.getLogger(ProveedorServicio.class.getName());

    private final ProveedorDAO dao = new ProveedorDAO();

    public ProveedorDTO buscarProveedorRuc(String ruc) throws Exception {
        try {
            Proveedor data = dao.buscarProveedorRuc(ruc);
            LOGGER.log(Level.INFO, "proveedor encontrado: {0}", data);
            if(Objects.nonNull(data)){
                ProveedorDTO dto = new ProveedorDTO();
                dto.setCodigoJD(data.getCodigoJD());
                dto.setId(data.getId());
                dto.setRuc(data.getRuc());
                dto.setRazonSocial(data.getRazonSocial());
                dto.setTelefono1(data.getTelefono1());
                dto.setDireccion(data.getDireccion());
                return dto;
            }
            return new ProveedorDTO();
            
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public List<ProveedorDTO> listarProveedores() throws Exception {
        try {
            List<ProveedorDTO> listaProveedorDto = new ArrayList<ProveedorDTO>();
            
            List<Proveedor> listaProveedor = dao.listarProveedores();
            
            listaProveedor.forEach(proveedor->{
                ProveedorDTO proveedorDto = new ProveedorDTO();
                proveedorDto = ProveedorMapper.INSTANCE.entityToDto(proveedor);
                listaProveedorDto.add(proveedorDto);
            });

            return listaProveedorDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public ProveedorDTO guardarProveedor(ProveedorDTO proveedorDto) throws Exception {
        try{
            Proveedor proveedor = ProveedorMapper.INSTANCE.dtoToEntity(proveedorDto);
            Proveedor proveedorRespuesta = dao.guardarProveedor(proveedor);
            proveedorDto = ProveedorMapper.INSTANCE.entityToDto(proveedorRespuesta);
            return proveedorDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public ProveedorDTO guardarProveedorUsuario(ProveedorDTO proveedorDto) throws Exception {
        try{
            Usuario usuario = new Usuario();
            usuario.setNombre(proveedorDto.getRazonSocial());
            usuario.setUsuario(proveedorDto.getRuc());
            usuario.setClave(proveedorDto.getClave());
            usuario.setCorreo(proveedorDto.getCorreo());
            usuario.setIdEstado(proveedorDto.getIdEstado());
            usuario.setIdRol(2);
            Proveedor proveedor = ProveedorMapper.INSTANCE.dtoToEntity(proveedorDto);
            Proveedor proveedorRespuesta = dao.guardarProveedorUsuario(proveedor, usuario);
            proveedorDto = ProveedorMapper.INSTANCE.entityToDto(proveedorRespuesta);
            return proveedorDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public String cargaMasivaProveedores(String archivoBase64) throws Exception {
        try{
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] fileBytes = decoder.decode(archivoBase64);
            InputStream targetStream = new ByteArrayInputStream(fileBytes);
            
            int content;
            while ((content = targetStream.read()) != -1) {
                System.out.print((char)content);
            }
            
            return "Datos del archivo subidos con éxito";
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
