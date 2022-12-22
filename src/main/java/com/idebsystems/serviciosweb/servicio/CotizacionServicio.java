/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.CotizacionDAO;
import com.idebsystems.serviciosweb.dao.ProveedorDAO;
import com.idebsystems.serviciosweb.dao.SolicitudDAO;
import com.idebsystems.serviciosweb.dto.CotizacionDTO;
import com.idebsystems.serviciosweb.entities.Cotizacion;
import com.idebsystems.serviciosweb.entities.Proveedor;
import com.idebsystems.serviciosweb.entities.Solicitud;
import com.idebsystems.serviciosweb.mappers.CotizacionMapper;
import com.idebsystems.serviciosweb.mappers.ProveedorMapper;
import com.idebsystems.serviciosweb.mappers.SolicitudMapper;
import com.idebsystems.serviciosweb.util.FechaUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class CotizacionServicio {
    
    private static final Logger LOGGER = Logger.getLogger(CotizacionServicio.class.getName());

    private final CotizacionDAO dao = new CotizacionDAO();

    public List<CotizacionDTO> listarCotizaciones(String fechaInicial, String fechaFinal, String codigoSolicitud, String codigoRC,
            Integer desde, Integer hasta) throws Exception {
        try {
            List<CotizacionDTO> listaCotizacionDto = new ArrayList<>();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            List<Object> respuesta = dao.listarCotizaciones(FechaUtil.fechaInicial(sdf.parse(fechaInicial)), 
                    FechaUtil.fechaFinal(sdf.parse(fechaFinal)), codigoSolicitud, codigoRC, desde, hasta);

            //sacar los resultados retornados
            Integer totalRegistros = (Integer) respuesta.get(0);
            //sacar los registros
            List<Cotizacion> listaCotizacion = (List<Cotizacion>) respuesta.get(1);

            //se debe buscar el provedor para enviarlo con la cotizacion
            ProveedorDAO proDao = new ProveedorDAO();
            
            //se necesita enviar la observacion de la solciitud
            SolicitudDAO soldao = new SolicitudDAO();
            
            for(Cotizacion cotiz : listaCotizacion){
                //se debe buscar el provedor para enviarlo con la cotizacion
                Proveedor prov = proDao.buscarProveedorRuc(cotiz.getRucProveedor());
                
                //solicitud
                Solicitud solicitud = soldao.buscarSolicitudPorNumero(cotiz.getCodigoSolicitud());
                
                CotizacionDTO dto = CotizacionMapper.INSTANCE.entityToDto(cotiz);
                dto.setTotalRegistros(totalRegistros);
                dto.setProveedorDto(ProveedorMapper.INSTANCE.entityToDto(prov));
                dto.setSolicitudDto(SolicitudMapper.INSTANCE.entityToDto(solicitud));
                
                listaCotizacionDto.add(dto);
            }

            return listaCotizacionDto;
            
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public CotizacionDTO guardarCotizacion(CotizacionDTO cotizacionDTO) throws Exception {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(cotizacionDTO.getFechaTexto()));
            c.set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
            c.set(Calendar.MINUTE, Calendar.getInstance().get(Calendar.MINUTE));
            
            cotizacionDTO.setFechaCotizacion(c.getTime());
            cotizacionDTO.setFechaModifica(new Date());
            
            Cotizacion cotizacion = CotizacionMapper.INSTANCE.dtoToEntity(cotizacionDTO);
            
            //buscar la cotizacion del mismo proveedor para que no se vuelva a enviar si ya la envio y debe estar RECHAZADO
            Cotizacion cot = dao.buscarCotizacionRucNumeroSol(cotizacion.getCodigoSolicitud(), cotizacion.getRucProveedor());
            if(Objects.nonNull(cot) && Objects.nonNull(cot.getEstado()) && !cot.getEstado().equalsIgnoreCase("ANULADO")){
                CotizacionDTO dot = new CotizacionDTO();
                dot.setRespuesta("La cotización ya fue enviada. No se puede enviar nuevamente la misma cotización por el mismo proveedor.");
                return dot;
            }
            if(Objects.nonNull(cot)){
                cotizacion.setId(cot.getId());
                cotizacion.getListaDetalles().forEach(d -> {
                    d.setCotizacion(cot);
                    d.getCotizacion().setId(cot.getId());
                });
            }
            
            cotizacion = dao.guardarCotizacion(cotizacion);
            
            cotizacionDTO = CotizacionMapper.INSTANCE.entityToDto(cotizacion);
            cotizacionDTO.setRespuesta("OK");
            
            return cotizacionDTO;
            
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    public CotizacionDTO buscarCotizacionRucNumeroSol(String codigoSolicitud, String ruc) throws Exception {
        try{
            
            Cotizacion cotizacion = dao.buscarCotizacionRucNumeroSol(codigoSolicitud, ruc);
            
            CotizacionDTO cotizacionDTO = CotizacionMapper.INSTANCE.entityToDto(cotizacion);
            
            //se debe buscar el provedor para enviarlo con la cotizacion
            ProveedorDAO proDao = new ProveedorDAO();
            Proveedor prov = proDao.buscarProveedorRuc(cotizacion.getRucProveedor());
            cotizacionDTO.setProveedorDto(ProveedorMapper.INSTANCE.entityToDto(prov));
            
            return cotizacionDTO;
            
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    public CotizacionDTO cambiarEstadoCotizacion(CotizacionDTO cotizacionDTO) throws Exception {
        try{
            Cotizacion cotizacion = dao.buscarCotizacionID(cotizacionDTO.getId());
            
            cotizacion.setFechaModifica(new Date());
            cotizacion.setEstado(cotizacionDTO.getEstado());
            cotizacion.setUsuarioModifica(cotizacionDTO.getUsuarioModifica());
            cotizacion.setObservacion(cotizacionDTO.getObservacion());
            
            cotizacion = dao.cambiarEstadoCotizacion(cotizacion);
            
            cotizacionDTO = CotizacionMapper.INSTANCE.entityToDto(cotizacion);
            
            return cotizacionDTO;
            
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public List<CotizacionDTO> getCotizacionesParaComparativo(String codigoSolicitud) throws Exception {
        try{
        
            List<Cotizacion> listaCotizaciones = dao.getCotizacionesParaComparativo(codigoSolicitud);
           
            List<CotizacionDTO> listaDto = new ArrayList<>();
            
            //buscar la solicitud para enviar junto con la cotizacion
            SolicitudDAO soldao = new SolicitudDAO();
            Solicitud sol = soldao.buscarSolicitudPorNumero(codigoSolicitud);
            
            //se debe buscar el provedor para enviarlo con la cotizacion
            ProveedorDAO proDao = new ProveedorDAO();
            
            for(Cotizacion cot : listaCotizaciones){
                
                CotizacionDTO cotizacionDTO = CotizacionMapper.INSTANCE.entityToDto(cot);
                
                //se debe buscar el provedor para enviarlo con la cotizacion
                Proveedor prov = proDao.buscarProveedorRuc(cot.getRucProveedor());
                cotizacionDTO.setProveedorDto(ProveedorMapper.INSTANCE.entityToDto(prov));
                
                //agregar la solicitud
                cotizacionDTO.setSolicitudDto(SolicitudMapper.INSTANCE.entityToDto(sol));
                
                listaDto.add(cotizacionDTO);
            }
            
            return listaDto;
            
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
