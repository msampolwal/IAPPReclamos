package com.iapp.reclamos.service.impl;

import com.iapp.reclamos.service.ParametroService;
import com.iapp.reclamos.domain.Parametro;
import com.iapp.reclamos.repository.ParametroRepository;
import com.iapp.reclamos.service.dto.ParametroDTO;
import com.iapp.reclamos.service.mapper.ParametroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Parametro.
 */
@Service
@Transactional
public class ParametroServiceImpl implements ParametroService {

    private final Logger log = LoggerFactory.getLogger(ParametroServiceImpl.class);

    private final ParametroRepository parametroRepository;

    private final ParametroMapper parametroMapper;

    public ParametroServiceImpl(ParametroRepository parametroRepository, ParametroMapper parametroMapper) {
        this.parametroRepository = parametroRepository;
        this.parametroMapper = parametroMapper;
    }

    /**
     * Save a parametro.
     *
     * @param parametroDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParametroDTO save(ParametroDTO parametroDTO) {
        log.debug("Request to save Parametro : {}", parametroDTO);
        Parametro parametro = parametroMapper.toEntity(parametroDTO);
        parametro = parametroRepository.save(parametro);
        return parametroMapper.toDto(parametro);
    }

    /**
     * Get all the parametros.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParametroDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Parametros");
        return parametroRepository.findAll(pageable)
            .map(parametroMapper::toDto);
    }


    /**
     * Get one parametro by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParametroDTO> findOne(Long id) {
        log.debug("Request to get Parametro : {}", id);
        return parametroRepository.findById(id)
            .map(parametroMapper::toDto);
    }

    /**
     * Delete the parametro by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Parametro : {}", id);
        parametroRepository.deleteById(id);
    }
}
