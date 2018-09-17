package com.iapp.reclamos.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.iapp.reclamos.domain.enumeration.Estado;

/**
 * A DTO for the Reclamo entity.
 */
public class ReclamoDTO implements Serializable {

    private Long id;

    @NotNull
    private String observacion;

    private Estado estado;

    private Long pedidoId;

    private String tipoNombre;
    
    private Long tipoId;
    
    private Boolean notificaLogistica;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getTipoNombre() {
		return tipoNombre;
	}

	public void setTipoNombre(String tipoNombre) {
		this.tipoNombre = tipoNombre;
	}

	public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoReclamoId) {
        this.tipoId = tipoReclamoId;
    }

    public Boolean getNotificaLogistica() {
		return notificaLogistica;
	}

	public void setNotificaLogistica(Boolean notificaLogistica) {
		this.notificaLogistica = notificaLogistica;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReclamoDTO reclamoDTO = (ReclamoDTO) o;
        if (reclamoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reclamoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReclamoDTO{" +
            "id=" + getId() +
            ", observacion='" + getObservacion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", pedido=" + getPedidoId() +
            ", tipo=" + getTipoId() +
            "}";
    }
}
