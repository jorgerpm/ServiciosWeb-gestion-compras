/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.CotizacionDAO;
import com.idebsystems.serviciosweb.dao.ProveedorDAO;
import com.idebsystems.serviciosweb.dto.CotizacionDTO;
import com.idebsystems.serviciosweb.entities.Cotizacion;
import com.idebsystems.serviciosweb.entities.Proveedor;
import com.idebsystems.serviciosweb.mappers.CotizacionMapper;
import com.idebsystems.serviciosweb.mappers.ProveedorMapper;
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

    public List<CotizacionDTO> listarCotizaciones(String fechaInicial, String fechaFinal, String codigoRC,
            Integer desde, Integer hasta) throws Exception {
        try {
            List<CotizacionDTO> listaCotizacionDto = new ArrayList<>();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            List<Object> respuesta = dao.listarCotizaciones(FechaUtil.fechaInicial(sdf.parse(fechaInicial)), FechaUtil.fechaFinal(sdf.parse(fechaFinal)),
                    codigoRC, desde, hasta);

            //sacar los resultados retornados
            Integer totalRegistros = (Integer) respuesta.get(0);
            //sacar los registros
            List<Cotizacion> listaCotizacion = (List<Cotizacion>) respuesta.get(1);

            //se debe buscar el provedor para enviarlo con la cotizacion
            ProveedorDAO proDao = new ProveedorDAO();
            
            for(Cotizacion cotiz : listaCotizacion){
                //se debe buscar el provedor para enviarlo con la cotizacion
                Proveedor prov = proDao.buscarProveedorRuc(cotiz.getRucProveedor());
                
                CotizacionDTO dto = CotizacionMapper.INSTANCE.entityToDto(cotiz);
                dto.setTotalRegistros(totalRegistros);
                dto.setProveedorDto(ProveedorMapper.INSTANCE.entityToDto(prov));
                
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
            Cotizacion cot = dao.buscarCotizacionRucNumeroRC(cotizacion.getCodigoRC(), cotizacion.getRucProveedor());
            if(Objects.nonNull(cot) && Objects.nonNull(cot.getEstado()) && !cot.getEstado().equalsIgnoreCase("RECHAZADO")){
                CotizacionDTO dot = new CotizacionDTO();
                dot.setRespuesta("Ya cotizacion ya fue enviada. No se puede enviar nuevamente la misma cotizacion");
                return dot;
            }
            
            cotizacion = dao.guardarCotizacion(cotizacion);
            
            cotizacionDTO = CotizacionMapper.INSTANCE.entityToDto(cotizacion);
            
            return cotizacionDTO;
            
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    public CotizacionDTO buscarCotizacionRucNumeroRC(String numeroRC, String ruc) throws Exception {
        try{
            
            Cotizacion cotizacion = dao.buscarCotizacionRucNumeroRC(numeroRC, ruc);
            
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
}
