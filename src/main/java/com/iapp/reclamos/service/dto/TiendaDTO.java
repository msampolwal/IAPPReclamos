package com.iapp.reclamos.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Tienda entity.
 */
public class TiendaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String url;

    private String nombreLogistica;

    private String urlLogistica;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombreLogistica() {
        return nombreLogistica;
    }

    public void setNombreLogistica(String nombreLogistica) {
        this.nombreLogistica = nombreLogistica;
    }

    public String getUrlLogistica() {
        return urlLogistica;
    }

    public void setUrlLogistica(String urlLogistica) {
        this.urlLogistica = urlLogistica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TiendaDTO tiendaDTO = (TiendaDTO) o;
        if (tiendaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tiendaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TiendaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", url='" + getUrl() + "'" +
            ", nombreLogistica='" + getNombreLogistica() + "'" +
            ", urlLogistica='" + getUrlLogistica() + "'" +
            "}";
    }
}
