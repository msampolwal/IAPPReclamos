package com.iapp.reclamos.service.impl;

import com.iapp.reclamos.service.ReclamoService;
import com.iapp.reclamos.domain.Reclamo;
import com.iapp.reclamos.repository.ReclamoRepository;
import com.iapp.reclamos.service.dto.ReclamoDTO;
import com.iapp.reclamos.service.mapper.ReclamoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Reclamo.
 */
@Service
@Transactional
public class ReclamoServiceImpl implements ReclamoService {

    private final Logger log = LoggerFactory.getLogger(ReclamoServiceImpl.class);

    private final ReclamoRepository reclamoRepository;

    private final ReclamoMapper reclamoMapper;

    public ReclamoServiceImpl(ReclamoRepository reclamoRepository, ReclamoMapper reclamoMapper) {
        this.reclamoRepository = reclamoRepository;
        this.reclamoMapper = reclamoMapper;
    }

    /**
     * Save a reclamo.
     *
     * @param reclamoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReclamoDTO save(ReclamoDTO reclamoDTO) {
        log.debug("Request to save Reclamo : {}", reclamoDTO);
        Reclamo reclamo = reclamoMapper.toEntity(reclamoDTO);
        reclamo = reclamoRepository.save(reclamo);
        return reclamoMapper.toDto(reclamo);
    }

    /**
     * Get all the reclamos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReclamoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reclamos");
        return reclamoRepository.findAll(pageable)
            .map(reclamoMapper::toDto);
    }


    /**
     * Get one reclamo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReclamoDTO> findOne(Long id) {
        log.debug("Request to get Reclamo : {}", id);
        return reclamoRepository.findById(id)
            .map(reclamoMapper::toDto);
    }

    /**
     * Delete the reclamo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reclamo : {}", id);
        reclamoRepository.deleteById(id);
    }
}
