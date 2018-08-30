package com.iapp.reclamos.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TipoReclamo entity.
 */
public class TipoReclamoDTO implements Serializable {

    private Long id;

    @NotNull
    private String descripcion;

    @NotNull
    private Boolean notificaALogistica;

    @NotNull
    private Boolean notificaATienda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean isNotificaALogistica() {
        return notificaALogistica;
    }

    public void setNotificaALogistica(Boolean notificaALogistica) {
        this.notificaALogistica = notificaALogistica;
    }

    public Boolean isNotificaATienda() {
        return notificaATienda;
    }

    public void setNotificaATienda(Boolean notificaATienda) {
        this.notificaATienda = notificaATienda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoReclamoDTO tipoReclamoDTO = (TipoReclamoDTO) o;
        if (tipoReclamoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoReclamoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoReclamoDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", notificaALogistica='" + isNotificaALogistica() + "'" +
            ", notificaATienda='" + isNotificaATienda() + "'" +
            "}";
    }
}
