/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.OrdenCompraDAO;
import com.idebsystems.serviciosweb.dao.CheckListRecepcionDAO;
import com.idebsystems.serviciosweb.dao.ParametroDAO;
import com.idebsystems.serviciosweb.dao.PreguntaChecklistRecepcionDAO;
import com.idebsystems.serviciosweb.dao.ProveedorDAO;
import com.idebsystems.serviciosweb.dao.RolDAO;
import com.idebsystems.serviciosweb.dao.SolicitudDAO;
import com.idebsystems.serviciosweb.dao.UsuarioDAO;
import com.idebsystems.serviciosweb.dto.CheckListRecepcionDTO;
import com.idebsystems.serviciosweb.dto.CheckListRecepcionDetalleDTO;
import com.idebsystems.serviciosweb.dto.RespuestaDTO;
import com.idebsystems.serviciosweb.dto.UsuarioDTO;
import com.idebsystems.serviciosweb.entities.OrdenCompra;
import com.idebsystems.serviciosweb.entities.CheckListRecepcion;
import com.idebsystems.serviciosweb.entities.CheckListRecepcionDetalle;
import com.idebsystems.serviciosweb.entities.Parametro;
import com.idebsystems.serviciosweb.entities.PreguntaChecklistRecepcion;
import com.idebsystems.serviciosweb.entities.Proveedor;
import com.idebsystems.serviciosweb.entities.Rol;
import com.idebsystems.serviciosweb.entities.Solicitud;
import com.idebsystems.serviciosweb.entities.Usuario;
import com.idebsystems.serviciosweb.mappers.CheckListRecepcionMapper;
import com.idebsystems.serviciosweb.mappers.ProveedorMapper;
import com.idebsystems.serviciosweb.util.FechaUtil;
import java.text.SimpleDateFormat;
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
public class CheckListRecepcionServicio {

    private static final Logger LOGGER = Logger.getLogger(CheckListRecepcionServicio.class.getName());

    private final CheckListRecepcionDAO dao = new CheckListRecepcionDAO();

    public RespuestaDTO generarCheckList(CheckListRecepcionDTO checkListDto) throws Exception {
        try {
            //buscar la orden de compra por el id para actualizar el estado el usuariomodifica y la fechamodifica
            OrdenCompraDAO ordenDao = new OrdenCompraDAO();
            OrdenCompra orden = ordenDao.buscarOrdenCompraID(checkListDto.getOrdenCompra().getId());

            if (orden.getEstado().equalsIgnoreCase("PENDIENTE_RECEPCION")) {
                return new RespuestaDTO("ESTA ORDEN DE COMPRA YA TIENE ASIGNADA RECEPCIONES.");
            }

            orden.setEstado("PENDIENTE_RECEPCION");
            orden.setUsuarioModifica(checkListDto.getUsuarioModifica() + "");
            orden.setFechaModifica(new Date());

            //buscar la solciitud por el id
            SolicitudDAO soldao = new SolicitudDAO();
            Solicitud sol = soldao.buscarSolicitudPorNumero(orden.getCodigoSolicitud());

            CheckListRecepcion checkListRecepcion = new CheckListRecepcion();

            checkListRecepcion.setCodigoSolicitud(orden.getCodigoSolicitud());
            checkListRecepcion.setEstado("PENDIENTE_RECEPCION");
            checkListRecepcion.setFechaModifica(new Date());
            checkListRecepcion.setFechaRecepcion(new Date());
            checkListRecepcion.setOrdenCompra(orden);
            checkListRecepcion.setSolicitud(sol);
            checkListRecepcion.setUsuario(checkListDto.getUsuario());
            checkListRecepcion.setUsuarioModifica(checkListDto.getUsuarioModifica());

            List<CheckListRecepcionDetalle> listaReceptores = new ArrayList();

            //para buscar las preuntas por el rol
            UsuarioServicio usdao = new UsuarioServicio();
            PreguntaChecklistRecepcionDAO prgdao = new PreguntaChecklistRecepcionDAO();

            for (CheckListRecepcionDetalleDTO check : checkListDto.getListaDetalles()) {

                //aqui se debe consultar las preguntas por cada rol que se vaya a guardar.
                UsuarioDTO userdto = usdao.buscarUsuarioPorId(check.getIdUsuario());
                List<PreguntaChecklistRecepcion> preguntas = prgdao.buscarPreguntasPorRol(userdto.getIdRol());

                if (Objects.isNull(preguntas) || preguntas.isEmpty()) {
                    return new RespuestaDTO("NO EXISTEN PREGUNTAS REGISTRADAS PARA EL ROL " + userdto.getNombreRol());
                }

                preguntas.forEach(preg -> {
                    CheckListRecepcionDetalle detalle = new CheckListRecepcionDetalle();
                    detalle.setCheckListRecepcion(checkListRecepcion);
                    detalle.setIdUsuario(check.getIdUsuario());
                    detalle.setIdRol(check.getIdRol());
                    detalle.setPregunta(preg.getPregunta());
                    detalle.setCamposBodega(check.getCamposBodega());
                    detalle.setFechaAprobacionArtes(check.getFechaAprobacionArtes());
                    

                    listaReceptores.add(detalle);
                });

            }

            checkListRecepcion.setListaDetalles(listaReceptores);

            dao.generarCheckList(checkListRecepcion, orden);
            
            //despues de generar el checklist se debe enviar por correo a los receptores
            enviarCorreoReceptores(checkListDto.getListaDetalles(), orden);
            

            return new RespuestaDTO("OK");

        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }

    public List<CheckListRecepcionDTO> listarCheckList(String fechaInicial, String fechaFinal, String codigoSolicitud, String codigoRC,
            long idUsuario, boolean rolPrincipal, Integer desde, Integer hasta) throws Exception {
        try {
            List<CheckListRecepcionDTO> listaDto = new ArrayList<>();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            UsuarioDAO usdao = new UsuarioDAO();
            Usuario userSesion = usdao.buscarUsuarioPorId(idUsuario);
            
            List<Object> respuesta = dao.listarCheckList(FechaUtil.fechaInicial(sdf.parse(fechaInicial)),
                    FechaUtil.fechaFinal(sdf.parse(fechaFinal)), codigoSolicitud, codigoRC, desde, hasta);
            
            //sacar los resultados retornados
            Integer totalRegistros = (Integer) respuesta.get(0);
            //sacar los registros
            List<CheckListRecepcion> listaCheckListRecepcion = (List<CheckListRecepcion>) respuesta.get(1);
            
            //buscar los roles para el nombre rol que se necesita
            RolDAO roldao = new RolDAO();
            List<Rol> listaRoles = roldao.listarRoles();
            
            //Proveedor
            ProveedorDAO pdao = new ProveedorDAO();
            
            //buscar el usuario para
            listaCheckListRecepcion.stream().map((comp) -> CheckListRecepcionMapper.INSTANCE.entityToDto(comp)).map((dto) -> {
                dto.setTotalRegistros(totalRegistros);
                return dto;                
            }).forEachOrdered((dto) -> {
                
                //buscar y agregar el proveedor al dto
                try{
                    Proveedor prov = pdao.buscarProveedorRuc(dto.getOrdenCompra().getRucProveedor());
                    dto.getOrdenCompra().setProveedorDto(ProveedorMapper.INSTANCE.entityToDto(prov));
                }catch(Exception exc){
                    
                }
                
                boolean agregar = false;
                if(rolPrincipal || userSesion.getIdRol() == 1)
                    agregar = true;
                
                List<CheckListRecepcionDetalleDTO> detallesDto = new ArrayList();
                
                for(CheckListRecepcionDetalleDTO deta : dto.getListaDetalles()) {
                    deta.setNombreRol(
                                    listaRoles.stream().filter(search-> search.getId() == deta.getIdRol()).findFirst().orElse(new Rol()).getNombre()
                            );
                    
                    if(!rolPrincipal && userSesion.getIdRol() != 1){
                        if(idUsuario == deta.getIdUsuario() /*&& Objects.isNull(deta.getRespuesta())*/){//esto hace que se muestre por cada user, ya que si la respusta era vacia le smostraba, pero si ya se llena la respuesta ya no les muestra al mismo user
                            agregar = true;        
                            detallesDto.add(deta);
                        }
                    }
                }
                if (agregar) {
                    if(!rolPrincipal && userSesion.getIdRol() != 1){
                        dto.setListaDetalles(detallesDto);
                    }
                    listaDto.add(dto);
                }
            });

            return listaDto;

        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    
    public RespuestaDTO guardarCheckListRecepcion(CheckListRecepcionDTO checkListDto) throws Exception {
        try {

            CheckListRecepcion checkListRecepcion = dao.buscarCheckListPorID(checkListDto.getId());

            //buscar el usuario modifica
            UsuarioDAO udao = new UsuarioDAO();
            Usuario usuario = udao.buscarUsuarioPorId(checkListDto.getUsuarioModifica());
            
            checkListRecepcion.setFechaModifica(new Date());
            checkListRecepcion.setUsuarioModifica(checkListDto.getUsuarioModifica());
            checkListRecepcion.setEstado("PENDIENTE_RECEPCION");
            
            //para saber si debe actualizar los campos de bodega, segun el detalle que tenga en si
            String tieneCamposBodega = "NO";
            String tieneFechaAprob = "NO";
            for(CheckListRecepcionDetalle ff : checkListRecepcion.getListaDetalles()){
                if(ff.getIdUsuario() == usuario.getId()){
                    tieneCamposBodega = ff.getCamposBodega();
                    tieneFechaAprob = ff.getFechaAprobacionArtes();
                }
            }
            if(tieneFechaAprob.equalsIgnoreCase("SI")){
                checkListRecepcion.setFechaAprobacionArtes(checkListDto.getFechaAprobacionArtes());
            }
            
            //esto solo se actualiza cuando viene de bodega
            //o para el administrador tambien puede cambiar
            if(usuario.getIdRol() == 1 || usuario.getIdRol() == 5L || tieneCamposBodega.equalsIgnoreCase("SI")){//para el idrol = 5
                checkListRecepcion.setFechaRecepcionBodega(checkListDto.getFechaRecepcionBodega());
                checkListRecepcion.setCantidadRecibida(checkListDto.getCantidadRecibida());
                checkListRecepcion.setCodigoMaterial(checkListDto.getCodigoMaterial());
            }
            if(usuario.getIdRol() == 1 || usuario.getIdRol() == 7 || usuario.getIdRol() == 8){//para el idrol de asistente compras y para el de coordinador compras
                checkListRecepcion.setMontoTotalFactura(checkListDto.getMontoTotalFactura());
            }
            
            for(int i=0;i<checkListRecepcion.getListaDetalles().size();i++) {
//                LOGGER.log(Level.INFO, "detaid: {0}", checkListRecepcion.getListaDetalles().get(i).getId());
                for(CheckListRecepcionDetalleDTO detaDto : checkListDto.getListaDetalles()) {
//                    LOGGER.log(Level.INFO, "detaDtoid: {0}", detaDto.getId());
                    
                    if(Objects.equals(checkListRecepcion.getListaDetalles().get(i).getId(), detaDto.getId())){
                        
//                        LOGGER.log(Level.INFO, "dentroooo::: ");
                        
                        checkListRecepcion.getListaDetalles().get(i).setRespuesta(detaDto.getRespuesta());
                        checkListRecepcion.getListaDetalles().get(i).setFecha(new Date());
                        checkListRecepcion.getListaDetalles().get(i).setObservacion(detaDto.getObservacion());
                    }
                    
                }
            }

            dao.guardarCheckListRecepcion(checkListRecepcion);
            
            //se necesita buscar los detalles y si todos los detalles ya tienen una respuesta se debe cambiar el estado a completo
            List<CheckListRecepcionDetalle> listaDets = dao.buscarDetallesCheckListPorID(checkListRecepcion);
            
            boolean tieneRespuesta = true;
            for(CheckListRecepcionDetalle det : listaDets) {
                if(Objects.isNull(det.getRespuesta())){
                    tieneRespuesta = false;
                }
            }
            if(tieneRespuesta){
                checkListRecepcion.setEstado("COMPLETO");
                dao.guardarCheckListRecepcion(checkListRecepcion);
            }

            return new RespuestaDTO("OK");

        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    private void enviarCorreoReceptores(List<CheckListRecepcionDetalleDTO> listaReceptores, OrdenCompra orden){
        try{
            //buscar los parametros para el envio
            //consultar los prametros del correo desde la base de datos.
            ParametroDAO paramDao = new ParametroDAO();
            List<Parametro> listaParams = paramDao.listarParametros();
            
            //para obtener la ip del sistema para generar la url que se envia por correo
            List<Parametro> paramsIP = listaParams.stream().filter(p -> p.getNombre().contains("IP_SISTEMA")).collect(Collectors.toList());

            List<Parametro> paramsMail = listaParams.stream().filter(p -> p.getNombre().contains("MAIL")).collect(Collectors.toList());
            
            Parametro paramNomRemit = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("NOMBREREMITENTEMAIL")).findAny().get();
            Parametro paramSubect = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("ASUNTOMAIL_CHECKLIST_RECEPCION")).findAny().get();
            Parametro paramMsm = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("MENSAJEMAIL_CHECKLIST_RECEPCION")).findAny().get();
            Parametro aliasCorreoEnvio = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("ALIASMAIL")).findAny().get();
            
            //Parametro paramCorreos = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("MAILS_APROBADORES")).findAny().orElse(new Parametro());
            
            //buscar los usuarios para obtener los correos de a quien se enviara
//            StringBuilder correos = new StringBuilder();
            CorreoServicio srvCorreo = new CorreoServicio();
            UsuarioDAO usDao = new UsuarioDAO();
            for(CheckListRecepcionDetalleDTO usAut : listaReceptores) {
                Usuario usuario = usDao.buscarUsuarioPorId(usAut.getIdUsuario());
                if(Objects.nonNull(usuario.getCorreo()) && !usuario.getCorreo().isBlank()){
                    //generar el mensaje
                    String mensaje = paramMsm.getValor();
                    
                    LOGGER.log(Level.INFO, "usuario: {0}", usuario.getNombre());
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
