package com.iapp.reclamos.service.impl;

import com.iapp.reclamos.service.TipoReclamoService;
import com.iapp.reclamos.domain.TipoReclamo;
import com.iapp.reclamos.repository.TipoReclamoRepository;
import com.iapp.reclamos.service.dto.TipoReclamoDTO;
import com.iapp.reclamos.service.mapper.TipoReclamoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing TipoReclamo.
 */
@Service
@Transactional
public class TipoReclamoServiceImpl implements TipoReclamoService {

    private final Logger log = LoggerFactory.getLogger(TipoReclamoServiceImpl.class);

    private final TipoReclamoRepository tipoReclamoRepository;

    private final TipoReclamoMapper tipoReclamoMapper;

    public TipoReclamoServiceImpl(TipoReclamoRepository tipoReclamoRepository, TipoReclamoMapper tipoReclamoMapper) {
        this.tipoReclamoRepository = tipoReclamoRepository;
        this.tipoReclamoMapper = tipoReclamoMapper;
    }

    /**
     * Save a tipoReclamo.
     *
     * @param tipoReclamoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoReclamoDTO save(TipoReclamoDTO tipoReclamoDTO) {
        log.debug("Request to save TipoReclamo : {}", tipoReclamoDTO);
        TipoReclamo tipoReclamo = tipoReclamoMapper.toEntity(tipoReclamoDTO);
        tipoReclamo = tipoReclamoRepository.save(tipoReclamo);
        return tipoReclamoMapper.toDto(tipoReclamo);
    }

    /**
     * Get all the tipoReclamos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoReclamoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoReclamos");
        return tipoReclamoRepository.findAll(pageable)
            .map(tipoReclamoMapper::toDto);
    }


    /**
     * Get one tipoReclamo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoReclamoDTO> findOne(Long id) {
        log.debug("Request to get TipoReclamo : {}", id);
        return tipoReclamoRepository.findById(id)
            .map(tipoReclamoMapper::toDto);
    }

    /**
     * Delete the tipoReclamo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoReclamo : {}", id);
        tipoReclamoRepository.deleteById(id);
    }
}
