package com.iapp.reclamos.service.impl;

import com.iapp.reclamos.service.TiendaService;
import com.iapp.reclamos.domain.Tienda;
import com.iapp.reclamos.repository.TiendaRepository;
import com.iapp.reclamos.service.dto.TiendaDTO;
import com.iapp.reclamos.service.mapper.TiendaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Tienda.
 */
@Service
@Transactional
public class TiendaServiceImpl implements TiendaService {

    private final Logger log = LoggerFactory.getLogger(TiendaServiceImpl.class);

    private final TiendaRepository tiendaRepository;

    private final TiendaMapper tiendaMapper;

    public TiendaServiceImpl(TiendaRepository tiendaRepository, TiendaMapper tiendaMapper) {
        this.tiendaRepository = tiendaRepository;
        this.tiendaMapper = tiendaMapper;
    }

    /**
     * Save a tienda.
     *
     * @param tiendaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TiendaDTO save(TiendaDTO tiendaDTO) {
        log.debug("Request to save Tienda : {}", tiendaDTO);
        Tienda tienda = tiendaMapper.toEntity(tiendaDTO);
        tienda = tiendaRepository.save(tienda);
        return tiendaMapper.toDto(tienda);
    }

    /**
     * Get all the tiendas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TiendaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tiendas");
        return tiendaRepository.findAll(pageable)
            .map(tiendaMapper::toDto);
    }


    /**
     * Get one tienda by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TiendaDTO> findOne(Long id) {
        log.debug("Request to get Tienda : {}", id);
        return tiendaRepository.findById(id)
            .map(tiendaMapper::toDto);
    }

    /**
     * Delete the tienda by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tienda : {}", id);
        tiendaRepository.deleteById(id);
    }
}
