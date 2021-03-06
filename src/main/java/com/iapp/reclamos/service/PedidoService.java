package com.iapp.reclamos.service;

import java.io.Reader;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iapp.reclamos.service.dto.PedidoDTO;

/**
 * Service Interface for managing Pedido.
 */
public interface PedidoService {

    /**
     * Save a pedido.
     *
     * @param pedidoDTO the entity to save
     * @return the persisted entity
     */
    PedidoDTO save(PedidoDTO pedidoDTO);

    /**
     * Get all the pedidos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PedidoDTO> findAll(Pageable pageable);
    /**
     * Get all the PedidoDTO where Reclamo is null.
     *
     * @return the list of entities
     */
    List<PedidoDTO> findAllWhereReclamoIsNull();


    /**
     * Get the "id" pedido.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PedidoDTO> findOne(Long id);

    /**
     * Delete the "id" pedido.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    /**
     * Migracion de CSV
     */
    void migrarPedidosDesdeCSV(Long idTienda, LocalDate fechaPedidos, Reader reader);
}
