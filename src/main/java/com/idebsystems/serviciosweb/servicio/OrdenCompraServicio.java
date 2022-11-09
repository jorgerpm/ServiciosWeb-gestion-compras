/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.CotizacionDAO;
import com.idebsystems.serviciosweb.dao.OrdenCompraDAO;
import com.idebsystems.serviciosweb.dao.ProveedorDAO;
import com.idebsystems.serviciosweb.dto.OrdenCompraDTO;
import com.idebsystems.serviciosweb.dto.OrdenCompraDetalleDTO;
import com.idebsystems.serviciosweb.entities.Cotizacion;
import com.idebsystems.serviciosweb.entities.CotizacionDetalle;
import com.idebsystems.serviciosweb.entities.OrdenCompra;
import com.idebsystems.serviciosweb.entities.Proveedor;
import com.idebsystems.serviciosweb.mappers.OrdenCompraMapper;
import com.idebsystems.serviciosweb.mappers.ProveedorMapper;
import com.idebsystems.serviciosweb.util.FechaUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class OrdenCompraServicio {
    
    private static final Logger LOGGER = Logger.getLogger(OrdenCompraServicio.class.getName());
    
    private final OrdenCompraDAO dao = new OrdenCompraDAO();
    
    public OrdenCompraDTO generarOrdenCompra(OrdenCompraDTO ordenCompraDTO) throws Exception {
        try{
            //buscar la cotizacion para en base a esa generar la orden de compra
            CotizacionDAO cotDao = new CotizacionDAO();
            Cotizacion cotizacion = cotDao.buscarCotizacionRucNumeroRC(ordenCompraDTO.getCodigoRC(), ordenCompraDTO.getRucProveedor());

            ordenCompraDTO.setCodigoOrdenCompra(cotizacion.getCodigoCotizacion());
            ordenCompraDTO.setCodigoRC(cotizacion.getCodigoRC());
            ordenCompraDTO.setDescuento(cotizacion.getDescuento());
            ordenCompraDTO.setFormaPago(cotizacion.getFormaPago());
            ordenCompraDTO.setIva(cotizacion.getIva());
            ordenCompraDTO.setObservacion(cotizacion.getObservacion());
            ordenCompraDTO.setRucProveedor(cotizacion.getRucProveedor());
            ordenCompraDTO.setSubtotal(cotizacion.getSubtotal());
            ordenCompraDTO.setSubtotalSinIva(cotizacion.getSubtotalSinIva());
            ordenCompraDTO.setTotal(cotizacion.getIva());
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
            
            OrdenCompra ordenCompra = OrdenCompraMapper.INSTANCE.dtoToEntity(ordenCompraDTO);
            
            ordenCompra = dao.generarOrdenCompra(ordenCompra);
            
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

            //se debe buscar el provedor para enviarlo con la cotizacion
            ProveedorDAO proDao = new ProveedorDAO();
            
            for(OrdenCompra ordCompra : listaOrdenCompra){
                //se debe buscar el provedor para enviarlo con la cotizacion
                Proveedor prov = proDao.buscarProveedorRuc(ordCompra.getRucProveedor());
                
                OrdenCompraDTO dto = OrdenCompraMapper.INSTANCE.entityToDto(ordCompra);
                dto.setTotalRegistros(totalRegistros);
                dto.setProveedorDto(ProveedorMapper.INSTANCE.entityToDto(prov));
                
                listaOrdenCompraDto.add(dto);
            }

            return listaOrdenCompraDto;
            
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
