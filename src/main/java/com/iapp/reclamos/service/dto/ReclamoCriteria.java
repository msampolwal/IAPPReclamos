package com.iapp.reclamos.service.dto;

import java.io.Serializable;
import com.iapp.reclamos.domain.enumeration.Estado;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Reclamo entity. This class is used in ReclamoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /reclamos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReclamoCriteria implements Serializable {
    /**
     * Class for filtering Estado
     */
    public static class EstadoFilter extends Filter<Estado> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter observacion;

    private EstadoFilter estado;

    private LongFilter pedidoId;

    private LongFilter tipoId;

    public ReclamoCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getObservacion() {
        return observacion;
    }

    public void setObservacion(StringFilter observacion) {
        this.observacion = observacion;
    }

    public EstadoFilter getEstado() {
        return estado;
    }

    public void setEstado(EstadoFilter estado) {
        this.estado = estado;
    }

    public LongFilter getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(LongFilter pedidoId) {
        this.pedidoId = pedidoId;
    }

    public LongFilter getTipoId() {
        return tipoId;
    }

    public void setTipoId(LongFilter tipoId) {
        this.tipoId = tipoId;
    }

    @Override
    public String toString() {
        return "ReclamoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (observacion != null ? "observacion=" + observacion + ", " : "") +
                (estado != null ? "estado=" + estado + ", " : "") +
                (pedidoId != null ? "pedidoId=" + pedidoId + ", " : "") +
                (tipoId != null ? "tipoId=" + tipoId + ", " : "") +
            "}";
    }

}
