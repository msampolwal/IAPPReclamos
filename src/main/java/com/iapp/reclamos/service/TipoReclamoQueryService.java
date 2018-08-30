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

import com.iapp.reclamos.domain.TipoReclamo;
import com.iapp.reclamos.domain.*; // for static metamodels
import com.iapp.reclamos.repository.TipoReclamoRepository;
import com.iapp.reclamos.service.dto.TipoReclamoCriteria;

import com.iapp.reclamos.service.dto.TipoReclamoDTO;
import com.iapp.reclamos.service.mapper.TipoReclamoMapper;

/**
 * Service for executing complex queries for TipoReclamo entities in the database.
 * The main input is a {@link TipoReclamoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoReclamoDTO} or a {@link Page} of {@link TipoReclamoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoReclamoQueryService extends QueryService<TipoReclamo> {

    private final Logger log = LoggerFactory.getLogger(TipoReclamoQueryService.class);

    private final TipoReclamoRepository tipoReclamoRepository;

    private final TipoReclamoMapper tipoReclamoMapper;

    public TipoReclamoQueryService(TipoReclamoRepository tipoReclamoRepository, TipoReclamoMapper tipoReclamoMapper) {
        this.tipoReclamoRepository = tipoReclamoRepository;
        this.tipoReclamoMapper = tipoReclamoMapper;
    }

    /**
     * Return a {@link List} of {@link TipoReclamoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoReclamoDTO> findByCriteria(TipoReclamoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoReclamo> specification = createSpecification(criteria);
        return tipoReclamoMapper.toDto(tipoReclamoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TipoReclamoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoReclamoDTO> findByCriteria(TipoReclamoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoReclamo> specification = createSpecification(criteria);
        return tipoReclamoRepository.findAll(specification, page)
            .map(tipoReclamoMapper::toDto);
    }

    /**
     * Function to convert TipoReclamoCriteria to a {@link Specification}
     */
    private Specification<TipoReclamo> createSpecification(TipoReclamoCriteria criteria) {
        Specification<TipoReclamo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TipoReclamo_.id));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), TipoReclamo_.descripcion));
            }
            if (criteria.getNotificaALogistica() != null) {
                specification = specification.and(buildSpecification(criteria.getNotificaALogistica(), TipoReclamo_.notificaALogistica));
            }
            if (criteria.getNotificaATienda() != null) {
                specification = specification.and(buildSpecification(criteria.getNotificaATienda(), TipoReclamo_.notificaATienda));
            }
        }
        return specification;
    }

}
