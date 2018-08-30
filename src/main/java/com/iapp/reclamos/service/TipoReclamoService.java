package com.iapp.reclamos.service;

import com.iapp.reclamos.service.dto.TipoReclamoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TipoReclamo.
 */
public interface TipoReclamoService {

    /**
     * Save a tipoReclamo.
     *
     * @param tipoReclamoDTO the entity to save
     * @return the persisted entity
     */
    TipoReclamoDTO save(TipoReclamoDTO tipoReclamoDTO);

    /**
     * Get all the tipoReclamos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoReclamoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoReclamo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TipoReclamoDTO> findOne(Long id);

    /**
     * Delete the "id" tipoReclamo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
