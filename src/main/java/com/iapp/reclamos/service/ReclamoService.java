package com.iapp.reclamos.service;

import com.iapp.reclamos.service.dto.ReclamoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Reclamo.
 */
public interface ReclamoService {

    /**
     * Save a reclamo.
     *
     * @param reclamoDTO the entity to save
     * @return the persisted entity
     */
    ReclamoDTO save(ReclamoDTO reclamoDTO);

    /**
     * Get all the reclamos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ReclamoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" reclamo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ReclamoDTO> findOne(Long id);

    /**
     * Delete the "id" reclamo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
