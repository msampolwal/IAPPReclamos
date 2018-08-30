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

import com.iapp.reclamos.domain.Tienda;
import com.iapp.reclamos.domain.*; // for static metamodels
import com.iapp.reclamos.repository.TiendaRepository;
import com.iapp.reclamos.service.dto.TiendaCriteria;

import com.iapp.reclamos.service.dto.TiendaDTO;
import com.iapp.reclamos.service.mapper.TiendaMapper;

/**
 * Service for executing complex queries for Tienda entities in the database.
 * The main input is a {@link TiendaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TiendaDTO} or a {@link Page} of {@link TiendaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TiendaQueryService extends QueryService<Tienda> {

    private final Logger log = LoggerFactory.getLogger(TiendaQueryService.class);

    private final TiendaRepository tiendaRepository;

    private final TiendaMapper tiendaMapper;

    public TiendaQueryService(TiendaRepository tiendaRepository, TiendaMapper tiendaMapper) {
        this.tiendaRepository = tiendaRepository;
        this.tiendaMapper = tiendaMapper;
    }

    /**
     * Return a {@link List} of {@link TiendaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TiendaDTO> findByCriteria(TiendaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tienda> specification = createSpecification(criteria);
        return tiendaMapper.toDto(tiendaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TiendaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TiendaDTO> findByCriteria(TiendaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tienda> specification = createSpecification(criteria);
        return tiendaRepository.findAll(specification, page)
            .map(tiendaMapper::toDto);
    }

    /**
     * Function to convert TiendaCriteria to a {@link Specification}
     */
    private Specification<Tienda> createSpecification(TiendaCriteria criteria) {
        Specification<Tienda> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Tienda_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Tienda_.nombre));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), Tienda_.url));
            }
        }
        return specification;
    }

}
