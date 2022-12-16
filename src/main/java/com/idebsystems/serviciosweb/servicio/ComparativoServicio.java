/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.ComparativoDAO;
import com.idebsystems.serviciosweb.dao.ProveedorDAO;
import com.idebsystems.serviciosweb.dto.ComparativoDTO;
import com.idebsystems.serviciosweb.dto.ComparativoDetalleDTO;
import com.idebsystems.serviciosweb.entities.Comparativo;
import com.idebsystems.serviciosweb.entities.Proveedor;
import com.idebsystems.serviciosweb.mappers.ComparativoMapper;
import com.idebsystems.serviciosweb.mappers.ProveedorMapper;
import com.idebsystems.serviciosweb.util.FechaUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class ComparativoServicio {
    
    private static final Logger LOGGER = Logger.getLogger(ComparativoServicio.class.getName());

    private final ComparativoDAO dao = new ComparativoDAO();
    
    public ComparativoDTO guardarComparativo(ComparativoDTO comparativoDTO) throws Exception {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            Calendar c = Calendar.getInstance();
//            c.setTime(sdf.parse(comparativoDTO.getFechaTexto()));
            c.set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
            c.set(Calendar.MINUTE, Calendar.getInstance().get(Calendar.MINUTE));
            
            comparativoDTO.setFechaComparativo(c.getTime());
            comparativoDTO.setFechaModifica(new Date());
            
            Comparativo comparativo = ComparativoMapper.INSTANCE.dtoToEntity(comparativoDTO);
            
            comparativo = dao.guardarComparativo(comparativo);
            
            comparativoDTO = ComparativoMapper.INSTANCE.entityToDto(comparativo);
            comparativoDTO.setRespuesta("OK");
            
            return comparativoDTO;
            
        }catch(Exception exc){
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
    public List<ComparativoDTO> listarComparativos(String fechaInicial, String fechaFinal, String codigoSolicitud, String codigoRC,
            Integer desde, Integer hasta) throws Exception {
        try {
            List<ComparativoDTO> listaDto = new ArrayList<>();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            List<Object> respuesta = dao.listarComparativos(FechaUtil.fechaInicial(sdf.parse(fechaInicial)), 
                    FechaUtil.fechaFinal(sdf.parse(fechaFinal)), codigoSolicitud, codigoRC, desde, hasta);

            //sacar los resultados retornados
            Integer totalRegistros = (Integer) respuesta.get(0);
            //sacar los registros
            List<Comparativo> listaComparativo = (List<Comparativo>) respuesta.get(1);

            //se debe buscar el provedor para enviarlo con la cotizacion
            ProveedorDAO proDao = new ProveedorDAO();
            
            for(Comparativo comp : listaComparativo){

                //se debe buscar el provedor para enviarlo con la cotizacion
//                
                
                ComparativoDTO dto = ComparativoMapper.INSTANCE.entityToDto(comp);
                dto.setTotalRegistros(totalRegistros);
                
                for(ComparativoDetalleDTO det : dto.getListaDetalles()){
                    Proveedor prov = proDao.buscarProveedorRuc(det.getCotizacion().getRucProveedor());
                    det.setProveedorDto(ProveedorMapper.INSTANCE.entityToDto(prov));
                }
                
//                
                
                listaDto.add(dto);
            }

            return listaDto;
            
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
