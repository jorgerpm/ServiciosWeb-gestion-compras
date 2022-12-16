/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.OrdenCompraDAO;
import com.idebsystems.serviciosweb.dao.CheckListRecepcionDAO;
import com.idebsystems.serviciosweb.dao.PreguntaChecklistRecepcionDAO;
import com.idebsystems.serviciosweb.dao.RolDAO;
import com.idebsystems.serviciosweb.dao.SolicitudDAO;
import com.idebsystems.serviciosweb.dto.CheckListRecepcionDTO;
import com.idebsystems.serviciosweb.dto.CheckListRecepcionDetalleDTO;
import com.idebsystems.serviciosweb.dto.RespuestaDTO;
import com.idebsystems.serviciosweb.dto.UsuarioDTO;
import com.idebsystems.serviciosweb.entities.OrdenCompra;
import com.idebsystems.serviciosweb.entities.CheckListRecepcion;
import com.idebsystems.serviciosweb.entities.CheckListRecepcionDetalle;
import com.idebsystems.serviciosweb.entities.PreguntaChecklistRecepcion;
import com.idebsystems.serviciosweb.entities.Rol;
import com.idebsystems.serviciosweb.entities.Solicitud;
import com.idebsystems.serviciosweb.mappers.CheckListRecepcionMapper;
import com.idebsystems.serviciosweb.util.FechaUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

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

            List<CheckListRecepcionDetalle> listaAuts = new ArrayList();

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

                    listaAuts.add(detalle);
                });

            }

            checkListRecepcion.setListaDetalles(listaAuts);

            dao.generarCheckList(checkListRecepcion, orden);

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
            
            List<Object> respuesta = dao.listarCheckList(FechaUtil.fechaInicial(sdf.parse(fechaInicial)),
                    FechaUtil.fechaFinal(sdf.parse(fechaFinal)), codigoSolicitud, codigoRC, desde, hasta);
            
            //sacar los resultados retornados
            Integer totalRegistros = (Integer) respuesta.get(0);
            //sacar los registros
            List<CheckListRecepcion> listaCheckListRecepcion = (List<CheckListRecepcion>) respuesta.get(1);
            
            //buscar los roles para el nombre rol que se necesita
            RolDAO roldao = new RolDAO();
            List<Rol> listaRoles = roldao.listarRoles();
            
            //buscar el usuario para
            listaCheckListRecepcion.stream().map((comp) -> CheckListRecepcionMapper.INSTANCE.entityToDto(comp)).map((dto) -> {
                dto.setTotalRegistros(totalRegistros);
                return dto;                
            }).forEachOrdered((dto) -> {
                
                boolean agregar = rolPrincipal;
                
                List<CheckListRecepcionDetalleDTO> detallesDto = new ArrayList();
                
                for(CheckListRecepcionDetalleDTO deta : dto.getListaDetalles()) {
                    deta.setNombreRol(
                                    listaRoles.stream().filter(search-> search.getId() == deta.getIdRol()).findFirst().orElse(new Rol()).getNombre()
                            );
                    
                    if(!rolPrincipal){
                        if(idUsuario == deta.getIdUsuario() && Objects.isNull(deta.getRespuesta())){
                            agregar = true;        
                            detallesDto.add(deta);
                        }
                    }
                }
                if (agregar) {
                    if(!rolPrincipal){
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

//            LOGGER.log(Level.INFO, "v: {0}", checkListRecepcion);
//            LOGGER.log(Level.INFO, "detalles: {0}", checkListRecepcion.getListaDetalles());
//            LOGGER.log(Level.INFO, "sise: {0}", checkListRecepcion.getListaDetalles().size());
            
            checkListRecepcion.setFechaModifica(new Date());
            checkListRecepcion.setUsuarioModifica(checkListDto.getUsuarioModifica());
            checkListRecepcion.setEstado("PENDIENTE_RECEPCION");
            checkListRecepcion.setFechaRecepcionBodega(checkListDto.getFechaRecepcionBodega());
            checkListRecepcion.setCantidadRecibida(checkListDto.getCantidadRecibida());
            checkListRecepcion.setCodigoMaterial(checkListDto.getCodigoMaterial());
            
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
}
