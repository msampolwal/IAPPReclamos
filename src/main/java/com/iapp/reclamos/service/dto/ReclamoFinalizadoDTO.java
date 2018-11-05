package com.iapp.reclamos.service.dto;

import java.io.Serializable;
import java.util.Date;

public class ReclamoFinalizadoDTO implements Serializable {

	private Long id_pedido;

	private Date fecha_entrega;

	public Long getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(Long id_pedido) {
		this.id_pedido = id_pedido;
	}

	public Date getFecha_entrega() {
		return fecha_entrega;
	}

	public void setFecha_entrega(Date fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}

}
