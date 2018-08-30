package com.iapp.reclamos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Pedido.
 */
@Entity
@Table(name = "pedido")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    @Column(name = "monto_compra")
    private Float montoCompra;

    @Column(name = "dni_cliente")
    private String dniCliente;

    @Column(name = "nombre_cliente")
    private String nombreCliente;

    @Column(name = "mail_cliente")
    private String mailCliente;

    @Column(name = "id_producto")
    private String idProducto;

    @Column(name = "descripcion_producto")
    private String descripcionProducto;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Tienda tienda;

    @OneToOne(mappedBy = "pedido")
    @JsonIgnore
    private Reclamo reclamo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public Pedido fechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
        return this;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Float getMontoCompra() {
        return montoCompra;
    }

    public Pedido montoCompra(Float montoCompra) {
        this.montoCompra = montoCompra;
        return this;
    }

    public void setMontoCompra(Float montoCompra) {
        this.montoCompra = montoCompra;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public Pedido dniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
        return this;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public Pedido nombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        return this;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getMailCliente() {
        return mailCliente;
    }

    public Pedido mailCliente(String mailCliente) {
        this.mailCliente = mailCliente;
        return this;
    }

    public void setMailCliente(String mailCliente) {
        this.mailCliente = mailCliente;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public Pedido idProducto(String idProducto) {
        this.idProducto = idProducto;
        return this;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public Pedido descripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
        return this;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public Pedido tienda(Tienda tienda) {
        this.tienda = tienda;
        return this;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public Reclamo getReclamo() {
        return reclamo;
    }

    public Pedido reclamo(Reclamo reclamo) {
        this.reclamo = reclamo;
        return this;
    }

    public void setReclamo(Reclamo reclamo) {
        this.reclamo = reclamo;
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
        Pedido pedido = (Pedido) o;
        if (pedido.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pedido.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pedido{" +
            "id=" + getId() +
            ", fechaEntrega='" + getFechaEntrega() + "'" +
            ", montoCompra=" + getMontoCompra() +
            ", dniCliente='" + getDniCliente() + "'" +
            ", nombreCliente='" + getNombreCliente() + "'" +
            ", mailCliente='" + getMailCliente() + "'" +
            ", idProducto='" + getIdProducto() + "'" +
            ", descripcionProducto='" + getDescripcionProducto() + "'" +
            "}";
    }
}
