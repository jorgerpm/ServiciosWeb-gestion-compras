/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.AutorizacionOrdenCompraDAO;
import com.idebsystems.serviciosweb.dao.CotizacionDAO;
import com.idebsystems.serviciosweb.dao.OrdenCompraDAO;
import com.idebsystems.serviciosweb.dao.OrdenCompraDetalleDAO;
import com.idebsystems.serviciosweb.dao.ParametroDAO;
import com.idebsystems.serviciosweb.dao.ProveedorDAO;
import com.idebsystems.serviciosweb.dao.SolicitudDAO;
import com.idebsystems.serviciosweb.dao.UsuarioDAO;
import com.idebsystems.serviciosweb.dto.AutorizacionOrdenCompraDTO;
import com.idebsystems.serviciosweb.dto.OrdenCompraDTO;
import com.idebsystems.serviciosweb.dto.OrdenCompraDetalleDTO;
import com.idebsystems.serviciosweb.entities.AutorizacionOrdenCompra;
import com.idebsystems.serviciosweb.entities.Comparativo;
import com.idebsystems.serviciosweb.entities.ComparativoDetalle;
import com.idebsystems.serviciosweb.entities.Cotizacion;
import com.idebsystems.serviciosweb.entities.CotizacionDetalle;
import com.idebsystems.serviciosweb.entities.OrdenCompra;
import com.idebsystems.serviciosweb.entities.OrdenCompraDetalle;
import com.idebsystems.serviciosweb.entities.Parametro;
import com.idebsystems.serviciosweb.entities.Proveedor;
import com.idebsystems.serviciosweb.entities.Solicitud;
import com.idebsystems.serviciosweb.entities.Usuario;
import com.idebsystems.serviciosweb.mappers.AutorizacionOrdenCompraMapper;
import com.idebsystems.serviciosweb.mappers.OrdenCompraDetalleMapper;
import com.idebsystems.serviciosweb.mappers.OrdenCompraMapper;
import com.idebsystems.serviciosweb.mappers.ProveedorMapper;
import com.idebsystems.serviciosweb.util.FechaUtil;
import java.math.BigDecimal;
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
public class OrdenCompraServicio {
    
    private static final Logger LOGGER = Logger.getLogger(OrdenCompraServicio.class.getName());
    
    private final OrdenCompraDAO dao = new OrdenCompraDAO();
    
    public OrdenCompraDTO generarOrdenCompra(OrdenCompraDTO ordenCompraDTO) throws Exception {
        try{
            CotizacionDAO cotDao = new CotizacionDAO();
            
            
            //aqui tambien se debe guardar la comparacion en la base de datos. esto en el DAO
            //buscar la solicitud
            SolicitudDAO soldao = new SolicitudDAO();
            Solicitud solic = soldao.buscarSolicitudPorNumero(ordenCompraDTO.getCodigoSolicitud());
            
            Comparativo comparativo = new Comparativo();
            comparativo.setObservacion(ordenCompraDTO.getObservacion());
            comparativo.setCodigoComparativo(solic.getCodigoSolicitud());
            comparativo.setCodigoSolicitud(solic.getCodigoSolicitud());
            comparativo.setEstado("GENERADO");
            comparativo.setFechaComparativo(new Date());
            comparativo.setFechaModifica(new Date());
            comparativo.setSolicitud(solic);
            comparativo.setUsuario(ordenCompraDTO.getUsuario());
            comparativo.setUsuarioModifica(ordenCompraDTO.getIdUsuario());
            //buscar las cotizaciones para generar los detalles
            List<ComparativoDetalle> listDetCompat = new ArrayList();
            List<Cotizacion> listaCot = cotDao.getCotizacionesParaComparativo(ordenCompraDTO.getCodigoSolicitud());
            for(Cotizacion cot : listaCot){
                ComparativoDetalle cd = new ComparativoDetalle();
                cd.setComparativo(comparativo);
                cd.setCotizacion(cot);
                if(cot.getRucProveedor().equalsIgnoreCase(ordenCompraDTO.getRucProveedor()))
                    cd.setSeleccionada(Boolean.TRUE);
                else
                    cd.setSeleccionada(Boolean.FALSE);
                
                listDetCompat.add(cd);
            }
            comparativo.setListaDetalles(listDetCompat);
            
            
            //buscar la cotizacion para en base a esa generar la orden de compra
            
            Cotizacion cotizacion = cotDao.buscarCotizacionRucNumeroSol(ordenCompraDTO.getCodigoSolicitud(), ordenCompraDTO.getRucProveedor());
            
            //GENERAR LA ORDENCOMPRADTO
            ordenCompraDTO.setCodigoOrdenCompra(cotizacion.getCodigoCotizacion());
            ordenCompraDTO.setCodigoRC(cotizacion.getCodigoRC());
            ordenCompraDTO.setDescuento(cotizacion.getDescuento());
            ordenCompraDTO.setFormaPago(cotizacion.getFormaPago());
            ordenCompraDTO.setIva(cotizacion.getIva());
            ordenCompraDTO.setObservacion(cotizacion.getObservacion());
            ordenCompraDTO.setRucProveedor(cotizacion.getRucProveedor());
            ordenCompraDTO.setSubtotal(cotizacion.getSubtotal());
            ordenCompraDTO.setSubtotalSinIva(cotizacion.getSubtotalSinIva());
            ordenCompraDTO.setTotal(cotizacion.getTotal());
            ordenCompraDTO.setCodigoSolicitud(cotizacion.getCodigoSolicitud());
            
            final List<OrdenCompraDetalleDTO> listaDetalles = new ArrayList<>();
            for(CotizacionDetalle det : cotizacion.getListaDetalles()) {
                OrdenCompraDetalleDTO ordenCompraDet = new OrdenCompraDetalleDTO();
                ordenCompraDet.setCantidad(det.getCantidad());
                ordenCompraDet.setDetalle(det.getDetalle());
                ordenCompraDet.setObservacion(det.getObservacion());
                ordenCompraDet.setTieneIva(det.getTieneIva());
                ordenCompraDet.setValorTotal(det.getValorTotal());
                ordenCompraDet.setValorUnitario(det.getValorUnitario());
                
                listaDetalles.add(ordenCompraDet);
            }
            ordenCompraDTO.setListaDetalles(listaDetalles);
            
            ordenCompraDTO.setUsuario(ordenCompraDTO.getUsuario());
            ordenCompraDTO.setEstado(ordenCompraDTO.getEstado());
            ordenCompraDTO.setFechaOrdenCompra(new Date());
            ordenCompraDTO.setUsuarioModifica(ordenCompraDTO.getIdUsuario()+"");
            ordenCompraDTO.setFechaModifica(new Date());
            
            OrdenCompra ordenCompra = OrdenCompraMapper.INSTANCE.dtoToEntity(ordenCompraDTO);
            
            ordenCompra = dao.generarOrdenCompra(ordenCompra, comparativo);
            
            ordenCompraDTO = OrdenCompraMapper.INSTANCE.entityToDto(ordenCompra);
            
            return ordenCompraDTO;
            
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public List<OrdenCompraDTO> listarOrdenesCompras(String fechaInicial, String fechaFinal, String codigoRC,
            Integer desde, Integer hasta) throws Exception {
        try {
            List<OrdenCompraDTO> listaOrdenCompraDto = new ArrayList<>();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            List<Object> respuesta = dao.listarOrdenesCompras(FechaUtil.fechaInicial(sdf.parse(fechaInicial)), FechaUtil.fechaFinal(sdf.parse(fechaFinal)),
                    codigoRC, desde, hasta);

            //sacar los resultados retornados
            Integer totalRegistros = (Integer) respuesta.get(0);
            //sacar los registros
            List<OrdenCompra> listaOrdenCompra = (List<OrdenCompra>) respuesta.get(1);

            //buscar los usuarios
            UsuarioDAO usdao = new UsuarioDAO();
            List<Usuario> listUsers = usdao.listarUsuarios();
            
            //se debe buscar el provedor para enviarlo con la cotizacion
            ProveedorDAO proDao = new ProveedorDAO();
            
            //para los usuarios autorizadores
            AutorizacionOrdenCompraDAO autDao = new AutorizacionOrdenCompraDAO();
            
            for(OrdenCompra ordCompra : listaOrdenCompra){
                //se debe buscar el provedor para enviarlo con la cotizacion
                Proveedor prov = proDao.buscarProveedorRuc(ordCompra.getRucProveedor());
                
                OrdenCompraDTO dto = OrdenCompraMapper.INSTANCE.entityToDto(ordCompra);
                dto.setTotalRegistros(totalRegistros);
                dto.setProveedorDto(ProveedorMapper.INSTANCE.entityToDto(prov));
                
                dto.setListaAutorizaciones(new ArrayList<>());
                List<AutorizacionOrdenCompra> listAuts = autDao.getAutorizacionesIDOrdenCompra(ordCompra.getId());
                listAuts.forEach(aut -> {
                    AutorizacionOrdenCompraDTO autdto = AutorizacionOrdenCompraMapper.INSTANCE.entityToDto(aut);
                    String nombreUsuario = listUsers.stream().filter(u -> aut.getIdUsuario() == u.getId()).findAny().orElse(new Usuario()).getNombre();
                    autdto.setNombreUsuario(nombreUsuario);
                    
                    dto.getListaAutorizaciones().add(autdto);
                });
                
                
                listaOrdenCompraDto.add(dto);
            }

            return listaOrdenCompraDto;
            
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public OrdenCompraDTO autorizarOrdenCompra(OrdenCompraDTO ordenDto) throws Exception {
        try{
            
            AutorizacionOrdenCompra autorizacion = null;
            boolean noAutorizaciones = Boolean.FALSE;
            
            //buscar en la tabla autorizaciones si tiene asignado ese usuario que autoriza
            //si si exite, se actualiza la fecha el estado del registro de ese usuario
            //si no existe se crea un nuevo registro en la tabla
            //en la tabla de autorizaciones si ya estan autorizados todos los registros, se cambia el estdo a autorizado de la orden
            //si alguno es rechazado la orden se debe poner como rechazado
            AutorizacionOrdenCompraDAO ordenDao = new AutorizacionOrdenCompraDAO();
            List<AutorizacionOrdenCompra> autorizaciones = ordenDao.getAutorizacionesIDOrdenCompra(ordenDto.getId());
            for(AutorizacionOrdenCompra aut : autorizaciones){
                if(Objects.nonNull(aut.getEstado()) && (aut.getEstado().equalsIgnoreCase("RECHAZADO") || aut.getEstado().equalsIgnoreCase("ANULADO"))){
                    ordenDto.setRespuesta("ESTA ORDEN DE COMPRA YA FUE RECHAZADA");
                    return ordenDto;
                }
                if(Objects.equals(aut.getIdUsuario(), ordenDto.getIdUsuario())){
                    autorizacion = aut;
                }
                //si no es el mismo usuario
                else if(Objects.isNull(aut.getEstado()) || !aut.getEstado().equalsIgnoreCase("AUTORIZADO")){
                    noAutorizaciones = Boolean.TRUE;
                }
            }
            
            //si no existe se crea un nuevo registro en la tabla autorizaciones
            if(Objects.isNull(autorizacion)){
                autorizacion = new AutorizacionOrdenCompra();
                autorizacion.setIdOrdenCompra(ordenDto.getId());
                autorizacion.setIdUsuario(ordenDto.getIdUsuario());
            }
            autorizacion.setEstado(ordenDto.getEstado());
            autorizacion.setFechaAutoriza(new Date());
            autorizacion.setObservacion(ordenDto.getObservacion());
            autorizacion.setUsuarioAutoriza(ordenDto.getUsuario());
            
            
            //se debe buscar la orden de compra con el id, 
            OrdenCompra ordenCompra = dao.buscarOrdenCompraID(ordenDto.getId());
            if(ordenCompra.getEstado().equalsIgnoreCase("AUTORIZADO")){
                ordenDto.setRespuesta("ESTA ORDEN DE COMPRA YA ESTA AUTORIZADA");
                return ordenDto;
            }
            //actualizar el estado de la orden de compra
            if(ordenDto.getEstado().equalsIgnoreCase("AUTORIZADO")){
                if(autorizaciones.isEmpty())
                    ordenCompra.setEstado("AUTORIZADO");
                else if(noAutorizaciones)//no estan todos autorizados
                    ordenCompra.setEstado("AUTORIZADO_TEMP");
                else
                    ordenCompra.setEstado("AUTORIZADO");
            }
            else{
                ordenCompra.setEstado(ordenDto.getEstado());
            }
//            ordenCompra.setUsuario(ordenDto.getUsuario());
            ordenCompra.setFechaModifica(new Date());
            ordenCompra.setUsuarioModifica(ordenDto.getIdUsuario()+"");
            
            
            
            ordenCompra = dao.autorizarOrdenCompra(ordenCompra, autorizacion);
            
            ordenDto = OrdenCompraMapper.INSTANCE.entityToDto(ordenCompra);
            ordenDto.setRespuesta("OK");
            
            //para enviar el correo al proveedor seleccionado , se envia solo cuando sea autorizado.
            if(ordenCompra.getEstado().equalsIgnoreCase("AUTORIZADO")){
                enviarCorreoOCAutorizado(ordenDto);
            }
            
            return ordenDto;
            
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    public List<OrdenCompraDTO> listarOrdenesPorAutorizar(String codigoRC, String codigoSolicitud, Long idUsuario, boolean rolPrincipal) throws Exception {
        try {
            List<OrdenCompraDTO> listaOrdenCompraDto = new ArrayList<>();

            List<Object[]> listaOrdenCompra = dao.listarOrdenesPorAutorizar(codigoRC, codigoSolicitud, idUsuario, rolPrincipal);
            
            //se debe buscar el provedor para enviarlo con la cotizacion
            ProveedorDAO proDao = new ProveedorDAO();
            
            //para los detalles
            OrdenCompraDetalleDAO detDao = new OrdenCompraDetalleDAO();
            
            for(Object[] ordCompra : listaOrdenCompra){
                //se debe buscar el provedor para enviarlo con la cotizacion
                Proveedor prov = proDao.buscarProveedorRuc(ordCompra[7].toString());
                
                OrdenCompraDTO dto = new OrdenCompraDTO();
                
                dto.setId((Long)ordCompra[0]);
                dto.setFechaOrdenCompra(ordCompra[1]!=null?(Date)ordCompra[1]:null);
                dto.setCodigoOrdenCompra(ordCompra[2].toString());
                dto.setCodigoRC(ordCompra[3].toString());
                dto.setCodigoSolicitud(ordCompra[4].toString());
                dto.setEstado(ordCompra[5].toString());
                dto.setUsuario(ordCompra[6].toString());
                dto.setRucProveedor(ordCompra[7].toString());
                dto.setObservacion(ordCompra[8]!=null?ordCompra[8].toString():null);
                dto.setSubtotal(ordCompra[9]!=null ? BigDecimal.valueOf(Double.parseDouble(ordCompra[9].toString())) : BigDecimal.ZERO);
                dto.setSubtotalSinIva(ordCompra[10]!=null ? BigDecimal.valueOf(Double.parseDouble(ordCompra[10].toString())) : BigDecimal.ZERO);
                dto.setIva(ordCompra[11]!=null ? BigDecimal.valueOf(Double.parseDouble(ordCompra[11].toString())) : BigDecimal.ZERO);
                dto.setTotal(ordCompra[12]!=null ? BigDecimal.valueOf(Double.parseDouble(ordCompra[12].toString())) : BigDecimal.ZERO);
                dto.setDescuento(ordCompra[13]!=null ? BigDecimal.valueOf(Double.parseDouble(ordCompra[13].toString())) : BigDecimal.ZERO);
                dto.setFormaPago(ordCompra[14].toString());
                dto.setUsuarioModifica(ordCompra[15].toString());
                dto.setFechaModifica(ordCompra[16]!=null?(Date)ordCompra[16]:null);
                dto.setAutorizador(ordCompra[17].toString());
                
                
                dto.setListaDetalles(new ArrayList<>());
                List<OrdenCompraDetalle> listaDetalles = detDao.buscarDetallesOC(dto.getId());
                listaDetalles.forEach(det -> {
                    dto.getListaDetalles().add(OrdenCompraDetalleMapper.INSTANCE.entityToDto(det));
                });
                
                
                dto.setTotalRegistros(listaOrdenCompra.size());
                dto.setProveedorDto(ProveedorMapper.INSTANCE.entityToDto(prov));
                
                
                listaOrdenCompraDto.add(dto);
            }

            return listaOrdenCompraDto;
            
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    private void enviarCorreoOCAutorizado(OrdenCompraDTO ordenCompraDTO){
        try{
            //buscar el proveedor
            ProveedorDAO provDao = new ProveedorDAO();
            Proveedor proveedor = provDao.buscarProveedorRuc(ordenCompraDTO.getRucProveedor());
            
            //buscar los parametros para el envio
            //consultar los prametros del correo desde la base de datos.
            ParametroDAO paramDao = new ParametroDAO();
            List<Parametro> listaParams = paramDao.listarParametros();

            List<Parametro> paramsMail = listaParams.stream().filter(p -> p.getNombre().contains("MAIL")).collect(Collectors.toList());
            
            Parametro paramNomRemit = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("NOMBREREMITENTEMAIL")).findAny().get();
            Parametro paramSubect = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("ASUNTOMAIL_OC_APR")).findAny().get();
            Parametro paramMsm = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("MENSAJEMAIL_OC_APR")).findAny().get();
            Parametro aliasCorreoEnvio = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("ALIASMAIL")).findAny().get();
            
            Parametro paramCorreos = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("MAILS_OC_APR")).findAny().get();

            //generar el mensaje
            String mensaje = paramMsm.getValor();
            mensaje = mensaje.replace("[razonSocial]", proveedor.getRazonSocial());
            mensaje = mensaje.replace("[codigoSolicitud]", ordenCompraDTO.getCodigoSolicitud());
            mensaje = mensaje.replace("[codigoRC]", ordenCompraDTO.getCodigoRC());
            
            String correos = proveedor.getCorreo() + ";" + paramCorreos.getValor();
            
            
            CorreoServicio srvCorreo = new CorreoServicio();
            srvCorreo.enviarCorreo(correos, paramSubect.getValor(), mensaje, aliasCorreoEnvio.getValor(), paramNomRemit.getValor());
        
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
        }
    }
}
