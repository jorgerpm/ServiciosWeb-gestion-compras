/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author jorge
 */
@Entity
@Table(name = "comparativo_detalle")
@SequenceGenerator(name = "comparativo_detalle_id_seq", initialValue = 1, sequenceName = "comparativo_detalle_id_seq", allocationSize = 1)
public class ComparativoDetalle implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "comparativo_detalle_id_seq")
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_comparativo", referencedColumnName = "id")
    private Comparativo comparativo;
    @JoinColumn(name = "id_cotizacion", referencedColumnName = "id")
    @OneToOne
    private Cotizacion cotizacion;
    private Boolean seleccionada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Comparativo getComparativo() {
        return comparativo;
    }

    public void setComparativo(Comparativo comparativo) {
        this.comparativo = comparativo;
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public Boolean getSeleccionada() {
        return seleccionada;
    }

    public void setSeleccionada(Boolean seleccionada) {
        this.seleccionada = seleccionada;
    }
    
    
}
