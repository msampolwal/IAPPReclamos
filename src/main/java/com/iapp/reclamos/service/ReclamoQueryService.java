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

import com.iapp.reclamos.domain.Reclamo;
import com.iapp.reclamos.domain.*; // for static metamodels
import com.iapp.reclamos.repository.ReclamoRepository;
import com.iapp.reclamos.service.dto.ReclamoCriteria;

import com.iapp.reclamos.service.dto.ReclamoDTO;
import com.iapp.reclamos.service.mapper.ReclamoMapper;

/**
 * Service for executing complex queries for Reclamo entities in the database.
 * The main input is a {@link ReclamoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReclamoDTO} or a {@link Page} of {@link ReclamoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReclamoQueryService extends QueryService<Reclamo> {

    private final Logger log = LoggerFactory.getLogger(ReclamoQueryService.class);

    private final ReclamoRepository reclamoRepository;

    private final ReclamoMapper reclamoMapper;

    public ReclamoQueryService(ReclamoRepository reclamoRepository, ReclamoMapper reclamoMapper) {
        this.reclamoRepository = reclamoRepository;
        this.reclamoMapper = reclamoMapper;
    }

    /**
     * Return a {@link List} of {@link ReclamoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReclamoDTO> findByCriteria(ReclamoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Reclamo> specification = createSpecification(criteria);
        return reclamoMapper.toDto(reclamoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReclamoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReclamoDTO> findByCriteria(ReclamoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Reclamo> specification = createSpecification(criteria);
        return reclamoRepository.findAll(specification, page)
            .map(reclamoMapper::toDto);
    }

    /**
     * Function to convert ReclamoCriteria to a {@link Specification}
     */
    private Specification<Reclamo> createSpecification(ReclamoCriteria criteria) {
        Specification<Reclamo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Reclamo_.id));
            }
            if (criteria.getObservacion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservacion(), Reclamo_.observacion));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildSpecification(criteria.getEstado(), Reclamo_.estado));
            }
            if (criteria.getPedidoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPedidoId(), Reclamo_.pedido, Pedido_.id));
            }
            if (criteria.getTipoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTipoId(), Reclamo_.tipo, TipoReclamo_.id));
            }
        }
        return specification;
    }

}
