/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.AutorizacionOrdenCompraDAO;
import com.idebsystems.serviciosweb.dao.OrdenCompraDAO;
import com.idebsystems.serviciosweb.dao.ParametroDAO;
import com.idebsystems.serviciosweb.dao.UsuarioDAO;
import com.idebsystems.serviciosweb.dto.AutorizacionOrdenCompraDTO;
import com.idebsystems.serviciosweb.dto.RespuestaDTO;
import com.idebsystems.serviciosweb.entities.AutorizacionOrdenCompra;
import com.idebsystems.serviciosweb.entities.OrdenCompra;
import com.idebsystems.serviciosweb.entities.Parametro;
import com.idebsystems.serviciosweb.entities.Usuario;
import com.idebsystems.serviciosweb.mappers.AutorizacionOrdenCompraMapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author jorge
 */
public class AutorizacionOrdenCompraServicio {
    
    private static final Logger LOGGER = Logger.getLogger(AutorizacionOrdenCompraServicio.class.getName());
    
    private final AutorizacionOrdenCompraDAO dao = new AutorizacionOrdenCompraDAO();
    
    public RespuestaDTO guardarAutorizadores(List<AutorizacionOrdenCompraDTO> listaAutsDto) throws Exception {
        try {
            List<AutorizacionOrdenCompra> listaAuts = new ArrayList();
                    
            listaAutsDto.forEach(autDto -> {
                AutorizacionOrdenCompra aut = AutorizacionOrdenCompraMapper.INSTANCE.dtoToEntity(autDto);
                listaAuts.add(aut);
            });
            
            //buscar la orden de compra por el id para actualizar el estado el usuariomodifica y la fechamodifica
            OrdenCompraDAO ordenDao = new OrdenCompraDAO();
            OrdenCompra orden = ordenDao.buscarOrdenCompraID(listaAutsDto.get(0).getIdOrdenCompra());
            
            if(orden.getEstado().equalsIgnoreCase("POR_AUTORIZAR")){
                return new RespuestaDTO("ESTA ORDEN DE COMPRA YA TIENE ASIGNADO AUTORIZADORES.");
            }
            
            orden.setEstado("POR_AUTORIZAR");
            orden.setUsuarioModifica(listaAutsDto.get(0).getUsuarioModifica());
            orden.setFechaModifica(new Date());
            
            //comprobar si existe autorizadores por eliminar
            List<Long> listaElimnar = new ArrayList();
            String[] listaElimnarTemp = listaAutsDto.get(0).getUsuariosEliminar().split(",");
            if(listaElimnarTemp.length > 0){
                for(int i=0;i<listaElimnarTemp.length;i++){
                    if(Objects.nonNull(listaElimnarTemp[i]) && !listaElimnarTemp[i].isBlank()){
                        listaElimnar.add(Long.parseLong(listaElimnarTemp[i]));
                    }
                }
            }
            
            dao.guardarAutorizadores(listaAuts, orden, listaElimnar);
            
            //una vez guardados los autorizadores se les debe enviar el correo electronico
            enviarCorreoAutorizadores(listaAuts, orden);
            
            return new RespuestaDTO("OK");
            
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    private void enviarCorreoAutorizadores(List<AutorizacionOrdenCompra> listaAuts, OrdenCompra orden){
        try{
            //buscar los parametros para el envio
            //consultar los prametros del correo desde la base de datos.
            ParametroDAO paramDao = new ParametroDAO();
            List<Parametro> listaParams = paramDao.listarParametros();
            
            //para obtener la ip del sistema para generar la url que se envia por correo
            List<Parametro> paramsIP = listaParams.stream().filter(p -> p.getNombre().contains("IP_SISTEMA")).collect(Collectors.toList());

            List<Parametro> paramsMail = listaParams.stream().filter(p -> p.getNombre().contains("MAIL")).collect(Collectors.toList());
            
            Parametro paramNomRemit = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("NOMBREREMITENTEMAIL")).findAny().get();
            Parametro paramSubect = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("ASUNTOMAIL_APROBADORES")).findAny().get();
            Parametro paramMsm = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("MENSAJEMAIL_APROBADORES")).findAny().get();
            Parametro aliasCorreoEnvio = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("ALIASMAIL")).findAny().get();
            
//            Parametro paramCorreos = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("MAILS_APROBADORES")).findAny().get();

            //generar el mensaje
            String mensaje = paramMsm.getValor();
            
            //buscar los usuarios para obtener los correos de a quien se enviara
//            StringBuilder correos = new StringBuilder();
            CorreoServicio srvCorreo = new CorreoServicio();
            UsuarioDAO usDao = new UsuarioDAO();
            for(AutorizacionOrdenCompra usAut : listaAuts) {
                Usuario usuario = usDao.buscarUsuarioPorId(usAut.getIdUsuario());
                if(Objects.nonNull(usuario.getCorreo()) && !usuario.getCorreo().isBlank()){
//                    correos.append(usuario.getCorreo());
//                    correos.append(";");
                    
                    mensaje = mensaje.replace("[nombreUsuario]", usuario.getNombre());
                    mensaje = mensaje.replace("[codigoSolicitud]", orden.getCodigoSolicitud());
                    mensaje = mensaje.replace("[codigoRC]", orden.getCodigoRC());
                    mensaje = mensaje.replace("[url]", paramsIP.get(0).getValor());

                    
                    srvCorreo.enviarCorreo(usuario.getCorreo(), paramSubect.getValor(), mensaje, aliasCorreoEnvio.getValor(), paramNomRemit.getValor(), null);
                }
            }
        
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
        }
    }
}
