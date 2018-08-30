package com.iapp.reclamos.service;

import com.iapp.reclamos.service.dto.TiendaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Tienda.
 */
public interface TiendaService {

    /**
     * Save a tienda.
     *
     * @param tiendaDTO the entity to save
     * @return the persisted entity
     */
    TiendaDTO save(TiendaDTO tiendaDTO);

    /**
     * Get all the tiendas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TiendaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tienda.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TiendaDTO> findOne(Long id);

    /**
     * Delete the "id" tienda.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
