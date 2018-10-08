package com.iapp.reclamos.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.iapp.reclamos.service.PedidoQueryService;
import com.iapp.reclamos.service.PedidoService;
import com.iapp.reclamos.service.dto.PedidoCriteria;
import com.iapp.reclamos.service.dto.PedidoDTO;
import com.iapp.reclamos.web.rest.errors.BadRequestAlertException;
import com.iapp.reclamos.web.rest.util.HeaderUtil;
import com.iapp.reclamos.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Pedido.
 */
@RestController
@RequestMapping("/api")
public class PedidoResource {

    private final Logger log = LoggerFactory.getLogger(PedidoResource.class);

    private static final String ENTITY_NAME = "pedido";

    private final PedidoService pedidoService;

    private final PedidoQueryService pedidoQueryService;

    public PedidoResource(PedidoService pedidoService, PedidoQueryService pedidoQueryService) {
        this.pedidoService = pedidoService;
        this.pedidoQueryService = pedidoQueryService;
    }

    /**
     * POST  /pedidos : Create a new pedido.
     *
     * @param pedidoDTO the pedidoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pedidoDTO, or with status 400 (Bad Request) if the pedido has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pedidos")
    @Timed
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody PedidoDTO pedidoDTO) throws URISyntaxException {
        log.debug("REST request to save Pedido : {}", pedidoDTO);
        if (pedidoDTO.getId() != null) {
            throw new BadRequestAlertException("A new pedido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PedidoDTO result = pedidoService.save(pedidoDTO);
        return ResponseEntity.created(new URI("/api/pedidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pedidos : Updates an existing pedido.
     *
     * @param pedidoDTO the pedidoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pedidoDTO,
     * or with status 400 (Bad Request) if the pedidoDTO is not valid,
     * or with status 500 (Internal Server Error) if the pedidoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pedidos")
    @Timed
    public ResponseEntity<PedidoDTO> updatePedido(@RequestBody PedidoDTO pedidoDTO) throws URISyntaxException {
        log.debug("REST request to update Pedido : {}", pedidoDTO);
        if (pedidoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PedidoDTO result = pedidoService.save(pedidoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pedidoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pedidos : get all the pedidos.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of pedidos in body
     */
    @GetMapping("/pedidos")
    @Timed
    public ResponseEntity<List<PedidoDTO>> getAllPedidos(PedidoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Pedidos by criteria: {}", criteria);
        Page<PedidoDTO> page = pedidoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pedidos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pedidos/:id : get the "id" pedido.
     *
     * @param id the id of the pedidoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pedidoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pedidos/{id}")
    @Timed
    public ResponseEntity<PedidoDTO> getPedido(@PathVariable Long id) {
        log.debug("REST request to get Pedido : {}", id);
        Optional<PedidoDTO> pedidoDTO = pedidoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pedidoDTO);
    }

    /**
     * DELETE  /pedidos/:id : delete the "id" pedido.
     *
     * @param id the id of the pedidoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pedidos/{id}")
    @Timed
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        log.debug("REST request to delete Pedido : {}", id);
        pedidoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
