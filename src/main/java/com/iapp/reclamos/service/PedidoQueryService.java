package com.iapp.reclamos.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.iapp.reclamos.domain.Pedido;
import com.iapp.reclamos.domain.*; // for static metamodels
import com.iapp.reclamos.repository.PedidoRepository;
import com.iapp.reclamos.service.dto.PedidoCriteria;

import com.iapp.reclamos.service.dto.PedidoDTO;
import com.iapp.reclamos.service.mapper.PedidoMapper;

/**
 * Service for executing complex queries for Pedido entities in the database.
 * The main input is a {@link PedidoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PedidoDTO} or a {@link Page} of {@link PedidoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PedidoQueryService extends QueryService<Pedido> {

    private final Logger log = LoggerFactory.getLogger(PedidoQueryService.class);

    private final PedidoRepository pedidoRepository;

    private final PedidoMapper pedidoMapper;

    public PedidoQueryService(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
    }

    /**
     * Return a {@link List} of {@link PedidoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PedidoDTO> findByCriteria(PedidoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Pedido> specification = createSpecification(criteria);
        return pedidoMapper.toDto(pedidoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PedidoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PedidoDTO> findByCriteria(PedidoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Pedido> specification = createSpecification(criteria);
        return pedidoRepository.findAll(specification, page)
            .map(pedidoMapper::toDto);
    }

    /**
     * Function to convert PedidoCriteria to a {@link Specification}
     */
    private Specification<Pedido> createSpecification(PedidoCriteria criteria) {
        Specification<Pedido> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Pedido_.id));
            }
            if (criteria.getFechaEntrega() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaEntrega(), Pedido_.fechaEntrega));
            }
            if (criteria.getMontoCompra() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMontoCompra(), Pedido_.montoCompra));
            }
            if (criteria.getDniCliente() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDniCliente(), Pedido_.dniCliente));
            }
            if (criteria.getNombreCliente() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombreCliente(), Pedido_.nombreCliente));
            }
            if (criteria.getMailCliente() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMailCliente(), Pedido_.mailCliente));
            }
            if (criteria.getIdProducto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdProducto(), Pedido_.idProducto));
            }
            if (criteria.getDescripcionProducto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcionProducto(), Pedido_.descripcionProducto));
            }
            if (criteria.getTiendaId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTiendaId(), Pedido_.tienda, Tienda_.id));
            }
            if (criteria.getReclamoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getReclamoId(), Pedido_.reclamo, Reclamo_.id));
            }
        }
        return specification;
    }

}
