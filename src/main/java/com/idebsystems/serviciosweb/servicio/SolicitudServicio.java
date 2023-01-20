/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.ParametroDAO;
import com.idebsystems.serviciosweb.dao.SolicitudDAO;
import com.idebsystems.serviciosweb.dao.SolicitudEnvioDAO;
import com.idebsystems.serviciosweb.dao.UsuarioDAO;
import com.idebsystems.serviciosweb.dto.SolicitudDTO;
import com.idebsystems.serviciosweb.entities.Parametro;
import com.idebsystems.serviciosweb.entities.Solicitud;
import com.idebsystems.serviciosweb.entities.SolicitudEnvio;
import com.idebsystems.serviciosweb.entities.Usuario;
import com.idebsystems.serviciosweb.util.FechaUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import com.idebsystems.serviciosweb.mappers.SolicitudMapper;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author jorge
 */
public class SolicitudServicio {

    private static final Logger LOGGER = Logger.getLogger(SolicitudServicio.class.getName());

    private final SolicitudDAO dao = new SolicitudDAO();

    public List<SolicitudDTO> listarSolicitudes(String fechaInicial, String fechaFinal, String codigoSolicitud, String codigoRC,
            Integer desde, Integer hasta) throws Exception {
        try {
            List<SolicitudDTO> listaSolicitudDto = new ArrayList<>();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            List<Object> respuesta = dao.listarSolicitudes(FechaUtil.fechaInicial(sdf.parse(fechaInicial)), 
                    FechaUtil.fechaFinal(sdf.parse(fechaFinal)), codigoSolicitud, codigoRC, desde, hasta);

            //sacar los resultados retornados
            Integer totalRegistros = (Integer) respuesta.get(0);
            //sacar los registros
            List<Solicitud> listaSolicitud = (List<Solicitud>) respuesta.get(1);

            listaSolicitud.forEach(sol -> {
                SolicitudDTO dto = SolicitudMapper.INSTANCE.entityToDto(sol);
                dto.setTotalRegistros(totalRegistros);
                listaSolicitudDto.add(dto);
            });

            return listaSolicitudDto;
            
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public SolicitudDTO guardarSolicitud(SolicitudDTO solicitudDto) throws Exception {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(solicitudDto.getFechaTexto()));
            c.set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
            c.set(Calendar.MINUTE, Calendar.getInstance().get(Calendar.MINUTE));
            
            solicitudDto.setFechaSolicitud(c.getTime());
            solicitudDto.setFechaModifica(new Date());
            
            LOGGER.log(Level.INFO, "fecha soli: {0}", solicitudDto.getFechaSolicitud());
            
            Solicitud solicitud = SolicitudMapper.INSTANCE.dtoToEntity(solicitudDto);
            
            if (Objects.isNull(solicitud.getId()) || solicitud.getId() == 0) {//es cuando es nuevo, solo ahi se pone el ultimo codigo solicitud
                solicitud.setCodigoSolicitud(dao.getUltimoCodigoSolicitud());
            }
            
            solicitud = dao.guardarSolicitud(solicitud);
            
            solicitudDto = SolicitudMapper.INSTANCE.entityToDto(solicitud);

            //una vez guardada correctamente se envia por correo
            enviarCorreoProveedores(solicitudDto);

            solicitudDto.setRespuesta("OK");
            
            return solicitudDto;
            
        }catch(Exception exc){
            if(exc.getMessage()!=null && exc.getMessage().contains("solicitud_codigo_sol_uk")){
                solicitudDto.setRespuesta("YA EXISTE REGISTRADA UNA SOLICITUD CON EL CODIGO DE SOLICITUD: ".concat(solicitudDto.getCodigoSolicitud()));
                return solicitudDto;
            }
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    public SolicitudDTO buscarSolicitudPorNumero(String numeroSolicitud, Long idUsuario) throws Exception {
        try{
            UsuarioDAO userDao = new UsuarioDAO();
            Usuario userProv = userDao.buscarUsuarioPorId(idUsuario);
            
            if(userProv.getIdRol() == 2){//si es un proveedor busca solo la solicitud enviada a el,en base al correo
                Solicitud solicitud = dao.buscarSolicitudPorNumeroYCorreo(numeroSolicitud, userProv.getCorreo());
                SolicitudDTO dto = SolicitudMapper.INSTANCE.entityToDto(solicitud);
                return dto;
            }
            else{
                Solicitud solicitud = dao.buscarSolicitudPorNumero(numeroSolicitud);
                SolicitudDTO dto = SolicitudMapper.INSTANCE.entityToDto(solicitud);
                return dto;
            }
            
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    private void enviarCorreoProveedores(SolicitudDTO solicitudDto){
        try{
            //generar la url que se envia a los proveedores
            String encriptado = encriptar(solicitudDto.getCodigoSolicitud(), null);
            
            //buscar los parametros para el envio
            //consultar los prametros del correo desde la base de datos.
            ParametroDAO paramDao = new ParametroDAO();
            List<Parametro> listaParams = paramDao.listarParametros();
            //para obtener la ip del sistema para generar la url que se envia por correo
            List<Parametro> paramsIP = listaParams.stream().filter(p -> p.getNombre().contains("IP_SISTEMA")).collect(Collectors.toList());
            
            String url = paramsIP.get(0).getValor().concat("index?token=").concat(encriptado);

            List<Parametro> paramsMail = listaParams.stream().filter(p -> p.getNombre().contains("MAIL")).collect(Collectors.toList());
            
            Parametro paramNomRemit = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("NOMBREREMITENTEMAIL")).findAny().get();
            Parametro paramSubect = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("ASUNTOMAIL_SC")).findAny().get();
            Parametro paramMsm = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("MENSAJEMAIL_SC")).findAny().get();
            Parametro aliasCorreoEnvio = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("ALIASMAIL")).findAny().get();

            //generar el mensaje
            String mensaje = paramMsm.getValor();
            mensaje = mensaje.replace("[url]", url);
            
            LOGGER.log(Level.INFO, "la url: {0}", url);
            //guardar quien envia la solicitud a los proveedores para tener un registro de que cada vez se envia
            SolicitudEnvioDAO envioDao = new SolicitudEnvioDAO();
            SolicitudEnvio solicitudEnvio = new SolicitudEnvio();
            solicitudEnvio.setCorreosEnvia(solicitudDto.getCorreos());
            solicitudEnvio.setFechaEnvia(new Date());
            solicitudEnvio.setNumeroRC(solicitudDto.getCodigoRC());
            solicitudEnvio.setUsuarioEnvia(solicitudDto.getUsuarioModifica());
            solicitudEnvio.setIdSolicitud(solicitudDto.getId());
            solicitudEnvio.setUrl(url);
            envioDao.guardarSolicitudEnvio(solicitudEnvio);
            
            
            CorreoServicio srvCorreo = new CorreoServicio();
            srvCorreo.enviarCorreo(solicitudDto.getCorreos(), paramSubect.getValor(), mensaje, aliasCorreoEnvio.getValor(), paramNomRemit.getValor(), null);
        
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
        }
    }
    
    public SolicitudDTO getUltimoCodigoSolicitud() throws Exception {
        try{
            SolicitudDTO dto = new SolicitudDTO();
            dto.setCodigoSolicitud(dao.getUltimoCodigoSolicitud());
            return dto;
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public String encriptar(String datos, String claveSecreta) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        String hash = "hashidebsystems1";
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(hash.getBytes(), "AES"));
        byte[] encodedValue = cipher.doFinal(datos.getBytes());
        String encriptado =  Base64.getEncoder().encodeToString(encodedValue);

        return encriptado;
    }
    
//    public String desencriptar(String datosEncriptados, String claveSecreta) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
//        byte[] claveEncriptacion = "IDEBSYSTEMS cia ltda".getBytes("UTF-8");
//        
//        MessageDigest sha = MessageDigest.getInstance("SHA-1");
//        
//        claveEncriptacion = sha.digest(claveEncriptacion);
//        claveEncriptacion = Arrays.copyOf(claveEncriptacion, 16);
//        
//        SecretKeySpec secretKey = new SecretKeySpec(claveEncriptacion, "AES");
//        //
//
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
//        cipher.init(Cipher.DECRYPT_MODE, secretKey);
//        
//        byte[] bytesEncriptados = Base64.getDecoder().decode(datosEncriptados);
//        byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
//        String datos = new String(datosDesencriptados);
//        
//        return datos;
//    }
//    
//    public static void main(String arg[]){
//        try{
//            
//        Cipher cipher = Cipher.getInstance("AES");
//                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec("hashidebsystems1".getBytes(), "AES"));
//                byte[] encodedValue = cipher.doFinal("alguno123".getBytes());
//                String encript =  Base64.getEncoder().encodeToString(encodedValue);
//                
//                System.out.println("encript: " + encript);
//                
//        }catch(Exception exc){
//            exc.printStackTrace();
//        }
//    }
    
}
