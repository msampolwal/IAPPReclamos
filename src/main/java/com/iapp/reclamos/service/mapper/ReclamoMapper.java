package com.iapp.reclamos.service.mapper;

import com.iapp.reclamos.domain.*;
import com.iapp.reclamos.service.dto.ReclamoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Reclamo and its DTO ReclamoDTO.
 */
@Mapper(componentModel = "spring", uses = {PedidoMapper.class, TipoReclamoMapper.class})
public interface ReclamoMapper extends EntityMapper<ReclamoDTO, Reclamo> {

    @Mapping(source = "pedido.id", target = "pedidoId")
    @Mapping(source = "tipo.id", target = "tipoId")
    @Mapping(source = "tipo.notificaALogistica", target = "notificaLogistica")
    @Mapping(source = "tipo.descripcion", target = "tipoNombre")
    ReclamoDTO toDto(Reclamo reclamo);

    @Mapping(source = "pedidoId", target = "pedido")
    @Mapping(source = "tipoId", target = "tipo")
    Reclamo toEntity(ReclamoDTO reclamoDTO);

    default Reclamo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reclamo reclamo = new Reclamo();
        reclamo.setId(id);
        return reclamo;
    }
}
