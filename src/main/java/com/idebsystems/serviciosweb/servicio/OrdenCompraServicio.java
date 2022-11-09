/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.CotizacionDAO;
import com.idebsystems.serviciosweb.dao.OrdenCompraDAO;
import com.idebsystems.serviciosweb.dto.OrdenCompraDTO;
import com.idebsystems.serviciosweb.dto.OrdenCompraDetalleDTO;
import com.idebsystems.serviciosweb.entities.Cotizacion;
import com.idebsystems.serviciosweb.entities.CotizacionDetalle;
import com.idebsystems.serviciosweb.entities.OrdenCompra;
import com.idebsystems.serviciosweb.mappers.OrdenCompraMapper;
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
}
