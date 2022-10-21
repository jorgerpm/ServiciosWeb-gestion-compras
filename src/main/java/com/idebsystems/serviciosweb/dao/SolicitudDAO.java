/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.Solicitud;
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
public class SolicitudDAO extends Persistencia {

    private static final Logger LOGGER = Logger.getLogger(SolicitudDAO.class.getName());

    public List<Object> listarSolicitudes(Date fechaInicial, Date fechaFinal, String codSolicitud,
            Integer desde, Integer hasta) throws Exception {
        try {
            List<Object> respuesta = new ArrayList<>();
            getEntityManager();

            String sql = "FROM Solicitud s WHERE s.fechaSolicitud BETWEEN :fechaInicial AND :fechaFinal ";

            if (Objects.nonNull(codSolicitud) && !codSolicitud.isBlank()) {
                sql = sql.concat(" AND UPPER(s.codigoSolicitud) = :codSolicitud ");
            }

            sql = sql.concat(" order by s.fechaSolicitud");

            Query query = em.createQuery(sql);
            query.setParameter("fechaInicial", fechaInicial);
            query.setParameter("fechaFinal", fechaFinal);

            if (Objects.nonNull(codSolicitud) && !codSolicitud.isBlank()) {
                query.setParameter("codSolicitud", codSolicitud.toUpperCase());
            }

            //para obtener el total de los registros a buscar
            Integer totalRegistros = query.getResultList().size();
            respuesta.add(totalRegistros);

            //para la paginacion
            query.setFirstResult(desde).setMaxResults(hasta);

            List<Solicitud> listaSols = query.getResultList();
            respuesta.add(listaSols);

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
