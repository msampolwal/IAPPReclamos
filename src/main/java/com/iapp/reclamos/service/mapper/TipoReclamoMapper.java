package com.iapp.reclamos.service.mapper;

import com.iapp.reclamos.domain.*;
import com.iapp.reclamos.service.dto.TipoReclamoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TipoReclamo and its DTO TipoReclamoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoReclamoMapper extends EntityMapper<TipoReclamoDTO, TipoReclamo> {



    default TipoReclamo fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoReclamo tipoReclamo = new TipoReclamo();
        tipoReclamo.setId(id);
        return tipoReclamo;
    }
}
