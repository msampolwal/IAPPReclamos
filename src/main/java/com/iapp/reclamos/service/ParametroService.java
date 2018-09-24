package com.iapp.reclamos.service;

import com.iapp.reclamos.service.dto.ParametroDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Parametro.
 */
public interface ParametroService {

    /**
     * Save a parametro.
     *
     * @param parametroDTO the entity to save
     * @return the persisted entity
     */
    ParametroDTO save(ParametroDTO parametroDTO);

    /**
     * Get all the parametros.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParametroDTO> findAll(Pageable pageable);


    /**
     * Get the "id" parametro.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParametroDTO> findOne(Long id);

    /**
     * Delete the "id" parametro.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
