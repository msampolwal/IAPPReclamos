package com.iapp.reclamos.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tienda.
 */
@Entity
@Table(name = "tienda")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tienda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "nombre_logistica")
    private String nombreLogistica;

    @Column(name = "url_logistica")
    private String urlLogistica;
    
    @OneToMany(mappedBy="tienda", fetch= FetchType.LAZY)
    private List<Pedido> pedidos;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Tienda nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public Tienda url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombreLogistica() {
        return nombreLogistica;
    }

    public Tienda nombreLogistica(String nombreLogistica) {
        this.nombreLogistica = nombreLogistica;
        return this;
    }

    public void setNombreLogistica(String nombreLogistica) {
        this.nombreLogistica = nombreLogistica;
    }

    public String getUrlLogistica() {
        return urlLogistica;
    }

    public Tienda urlLogistica(String urlLogistica) {
        this.urlLogistica = urlLogistica;
        return this;
    }

    public void setUrlLogistica(String urlLogistica) {
        this.urlLogistica = urlLogistica;
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
        Tienda tienda = (Tienda) o;
        if (tienda.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tienda.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tienda{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", url='" + getUrl() + "'" +
            ", nombreLogistica='" + getNombreLogistica() + "'" +
            ", urlLogistica='" + getUrlLogistica() + "'" +
            "}";
    }

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
    
}
