package com.iapp.reclamos.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Pedido entity.
 */
public class PedidoDTO implements Serializable {

    private Long id;

    private LocalDate fechaEntrega;

    private Float montoCompra;

    private String dniCliente;

    private String nombreCliente;

    private String mailCliente;

    private String idProducto;

    private String descripcionProducto;

    private Long tiendaId;
    
    private Long reclamoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Float getMontoCompra() {
        return montoCompra;
    }

    public void setMontoCompra(Float montoCompra) {
        this.montoCompra = montoCompra;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getMailCliente() {
        return mailCliente;
    }

    public void setMailCliente(String mailCliente) {
        this.mailCliente = mailCliente;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public Long getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(Long tiendaId) {
        this.tiendaId = tiendaId;
    }

    public Long getReclamoId() {
		return reclamoId;
	}

	public void setReclamoId(Long reclamoId) {
		this.reclamoId = reclamoId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PedidoDTO pedidoDTO = (PedidoDTO) o;
        if (pedidoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pedidoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
            "id=" + getId() +
            ", fechaEntrega='" + getFechaEntrega() + "'" +
            ", montoCompra=" + getMontoCompra() +
            ", dniCliente='" + getDniCliente() + "'" +
            ", nombreCliente='" + getNombreCliente() + "'" +
            ", mailCliente='" + getMailCliente() + "'" +
            ", idProducto='" + getIdProducto() + "'" +
            ", descripcionProducto='" + getDescripcionProducto() + "'" +
            ", tienda=" + getTiendaId() +
            "}";
    }
}
