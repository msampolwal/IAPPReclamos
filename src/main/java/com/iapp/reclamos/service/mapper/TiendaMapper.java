package com.iapp.reclamos.service.mapper;

import com.iapp.reclamos.domain.*;
import com.iapp.reclamos.service.dto.TiendaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tienda and its DTO TiendaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TiendaMapper extends EntityMapper<TiendaDTO, Tienda> {



    default Tienda fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tienda tienda = new Tienda();
        tienda.setId(id);
        return tienda;
    }
}
