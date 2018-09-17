package com.iapp.reclamos.service.dto;

import java.io.Serializable;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;



/**
 * Criteria class for the Pedido entity. This class is used in PedidoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /pedidos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PedidoCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LocalDateFilter fechaEntrega;

    private FloatFilter montoCompra;

    private StringFilter dniCliente;

    private StringFilter nombreCliente;

    private StringFilter mailCliente;

    private StringFilter idProducto;

    private StringFilter descripcionProducto;

    private LongFilter tiendaId;

    private LongFilter reclamoId;

    public PedidoCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateFilter fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public FloatFilter getMontoCompra() {
        return montoCompra;
    }

    public void setMontoCompra(FloatFilter montoCompra) {
        this.montoCompra = montoCompra;
    }

    public StringFilter getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(StringFilter dniCliente) {
        this.dniCliente = dniCliente;
    }

    public StringFilter getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(StringFilter nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public StringFilter getMailCliente() {
        return mailCliente;
    }

    public void setMailCliente(StringFilter mailCliente) {
        this.mailCliente = mailCliente;
    }

    public StringFilter getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(StringFilter idProducto) {
        this.idProducto = idProducto;
    }

    public StringFilter getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(StringFilter descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public LongFilter getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(LongFilter tiendaId) {
        this.tiendaId = tiendaId;
    }

    public LongFilter getReclamoId() {
        return reclamoId;
    }

    public void setReclamoId(LongFilter reclamoId) {
        this.reclamoId = reclamoId;
    }

    @Override
    public String toString() {
        return "PedidoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fechaEntrega != null ? "fechaEntrega=" + fechaEntrega + ", " : "") +
                (montoCompra != null ? "montoCompra=" + montoCompra + ", " : "") +
                (dniCliente != null ? "dniCliente=" + dniCliente + ", " : "") +
                (nombreCliente != null ? "nombreCliente=" + nombreCliente + ", " : "") +
                (mailCliente != null ? "mailCliente=" + mailCliente + ", " : "") +
                (idProducto != null ? "idProducto=" + idProducto + ", " : "") +
                (descripcionProducto != null ? "descripcionProducto=" + descripcionProducto + ", " : "") +
                (tiendaId != null ? "tiendaId=" + tiendaId + ", " : "") +
                (reclamoId != null ? "reclamoId=" + reclamoId + ", " : "") +
            "}";
    }

}
