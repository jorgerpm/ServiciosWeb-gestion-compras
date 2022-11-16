/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.AutorizacionOrdenCompraDAO;
import com.idebsystems.serviciosweb.dao.OrdenCompraDAO;
import com.idebsystems.serviciosweb.dto.AutorizacionOrdenCompraDTO;
import com.idebsystems.serviciosweb.dto.RespuestaDTO;
import com.idebsystems.serviciosweb.entities.AutorizacionOrdenCompra;
import com.idebsystems.serviciosweb.entities.OrdenCompra;
import com.idebsystems.serviciosweb.mappers.AutorizacionOrdenCompraMapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            
            dao.guardarAutorizadores(listaAuts, orden);
            
            return new RespuestaDTO("OK");
            
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
