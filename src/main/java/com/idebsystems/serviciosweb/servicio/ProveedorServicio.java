/*
 * Proyecto API Rest (ServiciosWeb)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.ParametroDAO;
import com.idebsystems.serviciosweb.dao.ProveedorDAO;
import com.idebsystems.serviciosweb.dto.ProveedorDTO;
import com.idebsystems.serviciosweb.entities.Parametro;
import com.idebsystems.serviciosweb.entities.Proveedor;
import com.idebsystems.serviciosweb.entities.Usuario;
import com.idebsystems.serviciosweb.mappers.ProveedorMapper;
import com.idebsystems.serviciosweb.util.MyMD5;
import static com.idebsystems.serviciosweb.util.MyMD5.getInstance;
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
import java.util.stream.Collectors;

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
            if (Objects.nonNull(data)) {
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
            Integer totalRegistros = (Integer) respuesta.get(0);
            List<Proveedor> listaProveedor = (List<Proveedor>) respuesta.get(1);

            listaProveedor.forEach(proveedor -> {
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
        try {
            Proveedor proveedorRespuesta;
            
            Proveedor proveedor = ProveedorMapper.INSTANCE.dtoToEntity(proveedorDto);
            
            //en la gestoin proveedores cuando se registra un nuevo proveedor se debe guardar el usuario
            if(Objects.isNull(proveedorDto.getId()) || proveedorDto.getId() == 0){//solo cuando es nuevo proveedor
                Usuario usuario = new Usuario();
                usuario.setNombre(proveedorDto.getRazonSocial());
                usuario.setUsuario(proveedorDto.getRuc());
                MyMD5 md = getInstance();
                usuario.setClave(md.hashData(proveedorDto.getRuc().getBytes()));//la clave en este caso es el mismo ruc
                usuario.setCorreo(proveedorDto.getCorreo());
                usuario.setIdEstado(proveedorDto.getIdEstado());
                usuario.setIdRol(2);
                proveedorRespuesta = dao.guardarProveedorUsuario(proveedor, usuario);
            }
            else{
                proveedorRespuesta = dao.guardarProveedor(proveedor);
            }

            proveedorDto = ProveedorMapper.INSTANCE.entityToDto(proveedorRespuesta);
            proveedorDto.setRespuesta("OK");
            return proveedorDto;
            
        } catch (Exception exc) {
            if (exc.getMessage() != null && exc.getMessage().contains("proveedor_ruc_key")) {
                proveedorDto.setRespuesta("YA EXISTE UN PROVEEDOR CON EL RUC: ".concat(proveedorDto.getRuc()));
                return proveedorDto;
            }
            if (exc.getMessage() != null && exc.getMessage().contains("usuario_correo_key")) {
                proveedorDto.setRespuesta("YA EXISTE UN USUARIO CON EL CORREO ELECTRONICO: ".concat(proveedorDto.getCorreo())
                        .concat(" INGRESE UN CORREO DIFERENTE PARA EL USUARIO DEL PROVEEDOR."));
                return proveedorDto;
            }
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }

    public ProveedorDTO guardarProveedorUsuario(ProveedorDTO proveedorDto) throws Exception {
        try {
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

            //enviar un correo de que un proveedor se registro en el sistema
            enviarCorreoNuevoProveedor(proveedorDto);

            return proveedorDto;
        } catch (Exception exc) {
            if (exc.getMessage() != null && exc.getMessage().contains("proveedor_ruc_key")) {
                proveedorDto.setRespuesta("YA EXISTE UN PROVEEDOR CON EL RUC: ".concat(proveedorDto.getRuc()));
                return proveedorDto;
            }
            if (exc.getMessage() != null && exc.getMessage().contains("usuario_correo_key")) {
                proveedorDto.setRespuesta("YA EXISTE UN USUARIO CON EL CORREO ELECTRONICO: ".concat(proveedorDto.getCorreo())
                        .concat(" INGRESE UN CORREO DIFERENTE PARA EL USUARIO DEL PROVEEDOR."));
                return proveedorDto;
            }
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }

    public String cargaMasivaProveedores(String archivoBase64) throws Exception {
        String respuesta = "ok";
        try {

            Base64.Decoder decoder = Base64.getDecoder();
            byte[] fileBytes = decoder.decode(archivoBase64);
            InputStream targetStream = new ByteArrayInputStream(fileBytes);
            BufferedReader br = new BufferedReader(new InputStreamReader(targetStream));

            String content;
            List<Proveedor> listaProveedores = new ArrayList<>();

            int i = 0;
            while ((content = br.readLine()) != null) {
                if (i > 0) {
                    try {
//                        LOGGER.log(Level.INFO, "contenido: {0}", content);
//String salt = System.getProperty("line.separator");
//LOGGER.log(Level.INFO, "contiene salto?? {0}", content.contains(salt));

                        content = content.replaceAll("\n", "");
                        content = content.replaceAll("\"", "");

                        String[] textoSeparado = content.split(";", 14); //el 14 es porque se debe leer 14 posiciones de la linea
                        
                        if(Objects.isNull(textoSeparado[3]) || textoSeparado[3].isBlank()){
                            throw new Exception("LA RAZON SOCIAL NO PUEDE ESTAR VACIA.");
                        }
                        if(Objects.isNull(textoSeparado[1]) || textoSeparado[1].isBlank()){
                            throw new Exception("EL RUC NO PUEDE ESTAR VACIO.");
                        }
//                        if(textoSeparado[1].length() != 13){
//                            throw new Exception("EL RUC DEBE CONTENER 13 DIGITOS.");
//                        }
                        if(Objects.isNull(textoSeparado[7]) || textoSeparado[7].isBlank()){
                            throw new Exception("EL CORREO NO PUEDE ESTAR VACIO.");
                        }

//                        LOGGER.log(Level.INFO, "cantidad linea: {0}", textoSeparado.length);
                        ProveedorDTO proveedorDto = new ProveedorDTO();

                        proveedorDto.setCodigoJD(textoSeparado[0]);
                        proveedorDto.setRuc(textoSeparado[1]);

                        proveedorDto.setNombreComercial(textoSeparado[2]);
                        proveedorDto.setRazonSocial(textoSeparado[3]);

                        proveedorDto.setCarpeta(textoSeparado[4]);
                        proveedorDto.setServicioProducto(textoSeparado[5]);
                        proveedorDto.setContacto(textoSeparado[6]);
                        proveedorDto.setCorreo(textoSeparado[7]);
                        proveedorDto.setTelefono1(textoSeparado[8]);
                        proveedorDto.setCredito(textoSeparado[9]);

                        proveedorDto.setDireccion(textoSeparado[10]);
                        proveedorDto.setContabilidad(textoSeparado[11]);
                        proveedorDto.setTelefonoContabilidad(textoSeparado[12]);
                        proveedorDto.setCorreoContabilidad(textoSeparado[13]);

                        //                    proveedorDto.setTelefono2(Objects.nonNull(textoSeparado[14]) ? textoSeparado[14] : null);
                        proveedorDto.setIdEstado(1);

                        Proveedor proveedor = ProveedorMapper.INSTANCE.dtoToEntity(proveedorDto);

                        listaProveedores.add(proveedor);

                    } catch (Exception exc) {
                        respuesta = respuesta.concat("Se produjo un error en la línea: " + (i + 1) + ". ");
                        LOGGER.log(Level.INFO, "se produjo un error en la linea: {0}", (i + 1));
                        LOGGER.log(Level.INFO, "Error: {0}", exc.getMessage());
                    }
                }
                i++;
            }

            dao.cargaMasivaProveedores(listaProveedores);

            if (!respuesta.equalsIgnoreCase("ok")) {
                respuesta = respuesta.replace("ok", "No se pudo cargar las siguientes líneas del archivo: ");
            }

            return respuesta;
        } catch (Exception exc) {
            if (exc.getMessage() != null && exc.getMessage().contains("usuario_correo_key")) {
                respuesta = "YA EXISTE UN USUARIO CON EL CORREO ELECTRONICO: "
                        .concat(
                        exc.getMessage().substring(exc.getMessage().indexOf("correo)=(")+9, exc.getMessage().indexOf(") already exists"))
                )
                        .concat(" INGRESE UN CORREO DIFERENTE PARA EL USUARIO DEL PROVEEDOR.");
                return respuesta;
            }
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }

    public List<ProveedorDTO> listarProveedoresActivosNombre(String valorBusqueda) throws Exception {
        try {
            List<ProveedorDTO> listaProveedorDto = new ArrayList();

            List<Proveedor> listaProveedor = dao.listarProveedoresActivosNombre(valorBusqueda);

            listaProveedor.forEach(proveedor -> {
                ProveedorDTO proveedorDto = ProveedorMapper.INSTANCE.entityToDto(proveedor);
                listaProveedorDto.add(proveedorDto);
            });

            return listaProveedorDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }

    private void enviarCorreoNuevoProveedor(ProveedorDTO proveedorDto) {
        try {

            Thread correo = new Thread(() -> {
                try {
                    
                    //buscar los parametros para el envio
                    //consultar los prametros del correo desde la base de datos.
                    ParametroDAO paramDao = new ParametroDAO();
                    List<Parametro> listaParams = paramDao.listarParametros();
                    
                    List<Parametro> paramsMail = listaParams.stream().filter(p -> p.getNombre().contains("MAIL")).collect(Collectors.toList());
                    
                    Parametro paramNomRemit = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("NOMBREREMITENTEMAIL")).findAny().get();
                    Parametro paramSubect = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("ASUNTOMAIL_NUEVO_PROV")).findAny().get();
                    Parametro paramMsm = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("MENSAJEMAIL_NUEVO_PROV")).findAny().get();
                    Parametro aliasCorreoEnvio = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("ALIASMAIL")).findAny().get();
                    
                    Parametro paramCorreos = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("EMAILS_COTIZACION_REG")).findAny().orElse(new Parametro());
                    
                    //generar el mensaje
                    String mensaje = paramMsm.getValor();
                    mensaje = mensaje.replace("[razonSocial]", proveedorDto.getRazonSocial());
                    mensaje = mensaje.replace("[ruc]", proveedorDto.getRuc());
                    
                    String correos = null;
                    
                    if (Objects.nonNull(paramCorreos.getValor()) && !paramCorreos.getValor().isBlank()) {
                        correos = paramCorreos.getValor();
                    }
                    
                    if (Objects.nonNull(correos)) {
                        CorreoServicio srvCorreo = new CorreoServicio();
                        srvCorreo.enviarCorreo(correos, paramSubect.getValor(), mensaje, aliasCorreoEnvio.getValor(), paramNomRemit.getValor(), null);
                    }
                    
                } catch (Exception exc) {
                    LOGGER.log(Level.SEVERE, null, exc);
                }
            });

            correo.start();

        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
        }
    }

//    public static void main(String[] args) {
//        String g = ";N/A;ANDINAGRAPH;;NO CALIFICADO;MATERIALES IMPRESOS;DANIELA ARELLANO;andinagraph@gmail.com;998415786;N/A;;;;";
//        String[] po = g.split(";", 14);
//        System.out.println("po: " + po.length);
//        for(String f:po){
//            System.out.println(f);
//        }
//    }
}
