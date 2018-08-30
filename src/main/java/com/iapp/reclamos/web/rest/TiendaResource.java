package com.iapp.reclamos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.iapp.reclamos.service.TiendaService;
import com.iapp.reclamos.web.rest.errors.BadRequestAlertException;
import com.iapp.reclamos.web.rest.util.HeaderUtil;
import com.iapp.reclamos.web.rest.util.PaginationUtil;
import com.iapp.reclamos.service.dto.TiendaDTO;
import com.iapp.reclamos.service.dto.TiendaCriteria;
import com.iapp.reclamos.service.TiendaQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tienda.
 */
@RestController
@RequestMapping("/api")
public class TiendaResource {

    private final Logger log = LoggerFactory.getLogger(TiendaResource.class);

    private static final String ENTITY_NAME = "tienda";

    private final TiendaService tiendaService;

    private final TiendaQueryService tiendaQueryService;

    public TiendaResource(TiendaService tiendaService, TiendaQueryService tiendaQueryService) {
        this.tiendaService = tiendaService;
        this.tiendaQueryService = tiendaQueryService;
    }

    /**
     * POST  /tiendas : Create a new tienda.
     *
     * @param tiendaDTO the tiendaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tiendaDTO, or with status 400 (Bad Request) if the tienda has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tiendas")
    @Timed
    public ResponseEntity<TiendaDTO> createTienda(@Valid @RequestBody TiendaDTO tiendaDTO) throws URISyntaxException {
        log.debug("REST request to save Tienda : {}", tiendaDTO);
        if (tiendaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tienda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TiendaDTO result = tiendaService.save(tiendaDTO);
        return ResponseEntity.created(new URI("/api/tiendas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tiendas : Updates an existing tienda.
     *
     * @param tiendaDTO the tiendaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tiendaDTO,
     * or with status 400 (Bad Request) if the tiendaDTO is not valid,
     * or with status 500 (Internal Server Error) if the tiendaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tiendas")
    @Timed
    public ResponseEntity<TiendaDTO> updateTienda(@Valid @RequestBody TiendaDTO tiendaDTO) throws URISyntaxException {
        log.debug("REST request to update Tienda : {}", tiendaDTO);
        if (tiendaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TiendaDTO result = tiendaService.save(tiendaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tiendaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tiendas : get all the tiendas.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of tiendas in body
     */
    @GetMapping("/tiendas")
    @Timed
    public ResponseEntity<List<TiendaDTO>> getAllTiendas(TiendaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Tiendas by criteria: {}", criteria);
        Page<TiendaDTO> page = tiendaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tiendas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tiendas/:id : get the "id" tienda.
     *
     * @param id the id of the tiendaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tiendaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tiendas/{id}")
    @Timed
    public ResponseEntity<TiendaDTO> getTienda(@PathVariable Long id) {
        log.debug("REST request to get Tienda : {}", id);
        Optional<TiendaDTO> tiendaDTO = tiendaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tiendaDTO);
    }

    /**
     * DELETE  /tiendas/:id : delete the "id" tienda.
     *
     * @param id the id of the tiendaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tiendas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTienda(@PathVariable Long id) {
        log.debug("REST request to delete Tienda : {}", id);
        tiendaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
