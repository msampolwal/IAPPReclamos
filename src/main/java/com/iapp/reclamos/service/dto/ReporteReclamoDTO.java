package com.iapp.reclamos.service.dto;

import java.io.Serializable;

public class ReporteReclamoDTO implements Serializable {

	private Long idPedido;

	private String descripcion;

	private String estado;
	
	public ReporteReclamoDTO() {
	}
	
	public ReporteReclamoDTO(Long idPedido, String descripcion, String estado) {
		super();
		this.idPedido = idPedido;
		this.descripcion = descripcion;
		this.estado = estado;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
