package com.iapp.reclamos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TipoReclamo.
 */
@Entity
@Table(name = "tipo_reclamo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoReclamo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "notifica_a_logistica", nullable = false)
    private Boolean notificaALogistica;

    @NotNull
    @Column(name = "notifica_a_tienda", nullable = false)
    private Boolean notificaATienda;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TipoReclamo descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean isNotificaALogistica() {
        return notificaALogistica;
    }

    public TipoReclamo notificaALogistica(Boolean notificaALogistica) {
        this.notificaALogistica = notificaALogistica;
        return this;
    }

    public void setNotificaALogistica(Boolean notificaALogistica) {
        this.notificaALogistica = notificaALogistica;
    }

    public Boolean isNotificaATienda() {
        return notificaATienda;
    }

    public TipoReclamo notificaATienda(Boolean notificaATienda) {
        this.notificaATienda = notificaATienda;
        return this;
    }

    public void setNotificaATienda(Boolean notificaATienda) {
        this.notificaATienda = notificaATienda;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TipoReclamo tipoReclamo = (TipoReclamo) o;
        if (tipoReclamo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoReclamo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoReclamo{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", notificaALogistica='" + isNotificaALogistica() + "'" +
            ", notificaATienda='" + isNotificaATienda() + "'" +
            "}";
    }
}
