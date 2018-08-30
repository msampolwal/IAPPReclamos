package com.iapp.reclamos.service.mapper;

import com.iapp.reclamos.domain.*;
import com.iapp.reclamos.service.dto.PedidoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pedido and its DTO PedidoDTO.
 */
@Mapper(componentModel = "spring", uses = {TiendaMapper.class, ReclamoMapper.class})
public interface PedidoMapper extends EntityMapper<PedidoDTO, Pedido> {

    @Mapping(source = "tienda.id", target = "tiendaId")
    @Mapping(source = "reclamo.id", target = "reclamoId")
    PedidoDTO toDto(Pedido pedido);

    @Mapping(source = "tiendaId", target = "tienda")
    @Mapping(source = "reclamoId", target = "reclamo")
    Pedido toEntity(PedidoDTO pedidoDTO);

    default Pedido fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pedido pedido = new Pedido();
        pedido.setId(id);
        return pedido;
    }
}
