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
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
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
                ProveedorDTO dto = ProveedorMapper.INSTANCE.entityToDto(data);
                return dto;
            }
            return new ProveedorDTO();
            
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public List<ProveedorDTO> listarProveedores(Integer desde, Integer hasta, String valorBusqueda) throws Exception {
        try {
            List<ProveedorDTO> listaProveedorDto = new ArrayList();
            
            List<Object> respuesta = dao.listarProveedores(desde, hasta, valorBusqueda);
            
            //sacar los resultados retornados
            Integer totalRegistros = (Integer)respuesta.get(0);
            List<Proveedor> listaProveedor = (List<Proveedor>)respuesta.get(1);
            
            listaProveedor.forEach(proveedor->{
                ProveedorDTO proveedorDto = new ProveedorDTO();
                proveedorDto = ProveedorMapper.INSTANCE.entityToDto(proveedor);
                //agregar el total de registros
                proveedorDto.setTotalRegistros(totalRegistros);
                    
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
            proveedorDto.setRespuesta("OK");
            return proveedorDto;
        } catch (Exception exc) {
            if(exc.getMessage()!=null && exc.getMessage().contains("proveedor_ruc_key")){
                proveedorDto.setRespuesta("YA EXISTE UN PROVEEDOR CON EL RUC: ".concat(proveedorDto.getRuc()));
                return proveedorDto;
            }
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
            proveedorDto.setRespuesta("OK");
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
            BufferedReader br = new BufferedReader(new InputStreamReader(targetStream));
            
            String content;
            List<ProveedorDTO> listaProveedoresDto = new ArrayList<>();
            ProveedorDTO proveedorDto = new ProveedorDTO();
            int i = 0;
            while ((content = br.readLine()) != null) {
                if(i>0){
                    String[] textoSeparado = content.split(";");
                    proveedorDto.setCodigoJD(textoSeparado[0]);
                    proveedorDto.setRuc(textoSeparado[1]);
                    proveedorDto.setRazonSocial(textoSeparado[2]);
                    proveedorDto.setNombreComercial(textoSeparado[3]);
                    proveedorDto.setDireccion(textoSeparado[4]);
                    proveedorDto.setCorreo(textoSeparado[5]);
                    proveedorDto.setTelefono1(textoSeparado[6]);
                    proveedorDto.setTelefono2(textoSeparado[7]);
                    proveedorDto.setIdEstado(1);
                    
                    listaProveedoresDto.add(proveedorDto);
                    Proveedor proveedor = ProveedorMapper.INSTANCE.dtoToEntity(proveedorDto);
                    dao.cargaMasivaProveedores(proveedor);
                }
                i++;
            }
            return "ok";
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    public List<ProveedorDTO> listarProveedoresActivosNombre(String valorBusqueda) throws Exception {
        try {
            List<ProveedorDTO> listaProveedorDto = new ArrayList();
            
            List<Proveedor> listaProveedor = dao.listarProveedoresActivosNombre(valorBusqueda);
            
            listaProveedor.forEach(proveedor->{
                ProveedorDTO proveedorDto = ProveedorMapper.INSTANCE.entityToDto(proveedor);
                listaProveedorDto.add(proveedorDto);
            });

            return listaProveedorDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
