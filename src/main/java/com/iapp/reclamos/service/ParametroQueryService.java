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

import com.iapp.reclamos.domain.Parametro;
import com.iapp.reclamos.domain.*; // for static metamodels
import com.iapp.reclamos.repository.ParametroRepository;
import com.iapp.reclamos.service.dto.ParametroCriteria;

import com.iapp.reclamos.service.dto.ParametroDTO;
import com.iapp.reclamos.service.mapper.ParametroMapper;

/**
 * Service for executing complex queries for Parametro entities in the database.
 * The main input is a {@link ParametroCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParametroDTO} or a {@link Page} of {@link ParametroDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParametroQueryService extends QueryService<Parametro> {

    private final Logger log = LoggerFactory.getLogger(ParametroQueryService.class);

    private final ParametroRepository parametroRepository;

    private final ParametroMapper parametroMapper;

    public ParametroQueryService(ParametroRepository parametroRepository, ParametroMapper parametroMapper) {
        this.parametroRepository = parametroRepository;
        this.parametroMapper = parametroMapper;
    }

    /**
     * Return a {@link List} of {@link ParametroDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParametroDTO> findByCriteria(ParametroCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Parametro> specification = createSpecification(criteria);
        return parametroMapper.toDto(parametroRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ParametroDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParametroDTO> findByCriteria(ParametroCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Parametro> specification = createSpecification(criteria);
        return parametroRepository.findAll(specification, page)
            .map(parametroMapper::toDto);
    }

    /**
     * Function to convert ParametroCriteria to a {@link Specification}
     */
    private Specification<Parametro> createSpecification(ParametroCriteria criteria) {
        Specification<Parametro> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Parametro_.id));
            }
            if (criteria.getClave() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClave(), Parametro_.clave));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValor(), Parametro_.valor));
            }
        }
        return specification;
    }

}
