/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.ProductoDAO;
import com.idebsystems.serviciosweb.dto.ProductoDTO;
import com.idebsystems.serviciosweb.entities.Producto;
import com.idebsystems.serviciosweb.mappers.ProductoMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author israe
 */
public class ProductoServicio {
    private static final Logger LOGGER = Logger.getLogger(ProductoServicio.class.getName());
    
    private final ProductoDAO dao = new ProductoDAO();
    
    public List<ProductoDTO> listarProductos() throws Exception {
        try {
            List<ProductoDTO> listaProductoDto = new ArrayList();
            
            List<Producto> listaProducto = dao.listarProductos();
            
            listaProducto.forEach(producto->{
                ProductoDTO productoDto = new ProductoDTO();
                productoDto = ProductoMapper.INSTANCE.entityToDto(producto);
                listaProductoDto.add(productoDto);
            });
            return listaProductoDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    public ProductoDTO guardarProducto(ProductoDTO productoDto) throws Exception {
        try{
            Producto producto = ProductoMapper.INSTANCE.dtoToEntity(productoDto);
            Producto productoRespuesta = dao.guardarProducto(producto);
            productoDto = ProductoMapper.INSTANCE.entityToDto(productoRespuesta);
            return productoDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
}
