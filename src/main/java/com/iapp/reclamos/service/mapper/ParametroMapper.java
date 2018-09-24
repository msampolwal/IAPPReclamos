package com.iapp.reclamos.service.mapper;

import com.iapp.reclamos.domain.*;
import com.iapp.reclamos.service.dto.ParametroDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Parametro and its DTO ParametroDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ParametroMapper extends EntityMapper<ParametroDTO, Parametro> {



    default Parametro fromId(Long id) {
        if (id == null) {
            return null;
        }
        Parametro parametro = new Parametro();
        parametro.setId(id);
        return parametro;
    }
}
