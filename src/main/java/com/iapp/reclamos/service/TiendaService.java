package com.iapp.reclamos.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iapp.reclamos.service.dto.ReclamoDTO;
import com.iapp.reclamos.service.dto.TiendaDTO;

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
    
    /**
     * Notifies to Tienda
     * @param id
     */
    void notificarTienda(ReclamoDTO reclamo);
    
    
    /**
     * Notifies Logistica
     * @param reclamo
     */
    void notificarLogistica(ReclamoDTO reclamo);
}
