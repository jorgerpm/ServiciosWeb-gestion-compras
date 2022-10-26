/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;


import com.idebsystems.serviciosweb.entities.Cotizacion;
import com.idebsystems.serviciosweb.util.Persistencia;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author jorge
 */
public class CotizacionDAO extends Persistencia {

    private static final Logger LOGGER = Logger.getLogger(CotizacionDAO.class.getName());

    public List<Object> listarCotizaciones(Date fechaInicial, Date fechaFinal, String codigoRC,
            Integer desde, Integer hasta) throws Exception {
        try {
            List<Object> respuesta = new ArrayList<>();
            getEntityManager();

            String sql = "FROM Cotizacion c WHERE c.fechaCotizacion BETWEEN :fechaInicial AND :fechaFinal ";

            if (Objects.nonNull(codigoRC) && !codigoRC.isBlank()) {
                sql = sql.concat(" AND UPPER(c.codigoRC) = :codigoRC ");
            }

            sql = sql.concat(" order by c.fechaCotizacion");

            Query query = em.createQuery(sql);
            query.setParameter("fechaInicial", fechaInicial);
            query.setParameter("fechaFinal", fechaFinal);

            if (Objects.nonNull(codigoRC) && !codigoRC.isBlank()) {
                query.setParameter("codigoRC", codigoRC.toUpperCase());
            }

            //para obtener el total de los registros a buscar
            Integer totalRegistros = query.getResultList().size();
            respuesta.add(totalRegistros);

            //para la paginacion
            query.setFirstResult(desde).setMaxResults(hasta);

            List<Cotizacion> listaCotiz = query.getResultList();
            respuesta.add(listaCotiz);

            return respuesta;

        } catch (NoResultException exc) {
            return null;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
}
