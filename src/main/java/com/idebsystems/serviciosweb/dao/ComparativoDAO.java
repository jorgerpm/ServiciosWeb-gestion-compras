/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.dao;

import com.idebsystems.serviciosweb.entities.Comparativo;
import com.idebsystems.serviciosweb.util.Persistencia;
import java.sql.SQLException;
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
public class ComparativoDAO extends Persistencia {
    
    private static final Logger LOGGER = Logger.getLogger(ComparativoDAO.class.getName());
    
    public Comparativo guardarComparativo(Comparativo comparativo) throws Exception {
        try{
            getEntityManager();

            em.getTransaction().begin();

            if (Objects.nonNull(comparativo.getId()) && comparativo.getId() > 0) {
                em.merge(comparativo); //update
            } else {
                em.persist(comparativo); //insert
            }
            
            comparativo.getListaDetalles().forEach(detalle -> {
                detalle.setComparativo(comparativo);
                em.persist(detalle);
            });
            
            //cambiar el estado de la solicitud a comparado
//            Query query = em.createQuery("UPDATE Solicitud s SET s.estado = 'COMPARADO', s.usuarioModifica = :usuarioModifica, s.fechaModifica = :fechaModifica WHERE s.codigoRC = :codigoRc");
//            query.setParameter("codigoRc", cotizacion.getCodigoRC());
//            query.setParameter("usuarioModifica", cotizacion.getUsuarioModifica());
//            query.setParameter("fechaModifica", new Date());
//            query.executeUpdate();
            
            em.flush(); //Confirmar el insert o update

            em.getTransaction().commit();

            return comparativo;
            
        } catch (SQLException exc) {
            rollbackTransaction();
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } catch (Exception exc) {
            rollbackTransaction();
//            if(exc.getMessage() != null && exc.getMessage().contains("cotizacion_codigo_cotizacion_uk"))
//                throw new Exception("YA EXISTE EL CODIGO DE COTIZACION");
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        } finally {
            closeEntityManager();
        }
    }
    
    
    
    public List<Object> listarComparativos(Date fechaInicial, Date fechaFinal, String codigoSolicitud, String codigoRC,
            Integer desde, Integer hasta) throws Exception {
        try {
            List<Object> respuesta = new ArrayList<>();
            getEntityManager();

            String sql = "FROM Comparativo c ";

            if (Objects.nonNull(codigoSolicitud) && !codigoSolicitud.isBlank()) {
                sql = sql.concat(" WHERE UPPER(c.codigoSolicitud) = :codigoSolicitud ");
            }
            else if (Objects.nonNull(codigoRC) && !codigoRC.isBlank()) {
                sql = sql.concat(" WHERE UPPER(c.solicitud.codigoRC) = :codigoRC ");
            }
            else{
                sql = sql.concat(" WHERE c.fechaComparativo BETWEEN :fechaInicial AND :fechaFinal ");
            }

            sql = sql.concat(" order by c.fechaComparativo");

            Query query = em.createQuery(sql);

            if (Objects.nonNull(codigoSolicitud) && !codigoSolicitud.isBlank()) {
                query.setParameter("codigoSolicitud", codigoSolicitud.toUpperCase());
            }
            else if (Objects.nonNull(codigoRC) && !codigoRC.isBlank()) {
                query.setParameter("codigoRC", codigoRC.toUpperCase());
            }
            else{
                query.setParameter("fechaInicial", fechaInicial);
                query.setParameter("fechaFinal", fechaFinal);
            }

            //para obtener el total de los registros a buscar
            Integer totalRegistros = query.getResultList().size();
            respuesta.add(totalRegistros);

            //para la paginacion
            query.setFirstResult(desde).setMaxResults(hasta);

            List<Comparativo> listaComp = query.getResultList();
            respuesta.add(listaComp);

            //new BufferedReader(new InputStreamReader(inputstream));
            
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
