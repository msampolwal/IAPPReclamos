package com.iapp.reclamos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.iapp.reclamos.service.TipoReclamoService;
import com.iapp.reclamos.web.rest.errors.BadRequestAlertException;
import com.iapp.reclamos.web.rest.util.HeaderUtil;
import com.iapp.reclamos.web.rest.util.PaginationUtil;
import com.iapp.reclamos.service.dto.TipoReclamoDTO;
import com.iapp.reclamos.service.dto.TipoReclamoCriteria;
import com.iapp.reclamos.service.TipoReclamoQueryService;
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
 * REST controller for managing TipoReclamo.
 */
@RestController
@RequestMapping("/api")
public class TipoReclamoResource {

    private final Logger log = LoggerFactory.getLogger(TipoReclamoResource.class);

    private static final String ENTITY_NAME = "tipoReclamo";

    private final TipoReclamoService tipoReclamoService;

    private final TipoReclamoQueryService tipoReclamoQueryService;

    public TipoReclamoResource(TipoReclamoService tipoReclamoService, TipoReclamoQueryService tipoReclamoQueryService) {
        this.tipoReclamoService = tipoReclamoService;
        this.tipoReclamoQueryService = tipoReclamoQueryService;
    }

    /**
     * POST  /tipo-reclamos : Create a new tipoReclamo.
     *
     * @param tipoReclamoDTO the tipoReclamoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoReclamoDTO, or with status 400 (Bad Request) if the tipoReclamo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-reclamos")
    @Timed
    public ResponseEntity<TipoReclamoDTO> createTipoReclamo(@Valid @RequestBody TipoReclamoDTO tipoReclamoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoReclamo : {}", tipoReclamoDTO);
        if (tipoReclamoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoReclamo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoReclamoDTO result = tipoReclamoService.save(tipoReclamoDTO);
        return ResponseEntity.created(new URI("/api/tipo-reclamos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-reclamos : Updates an existing tipoReclamo.
     *
     * @param tipoReclamoDTO the tipoReclamoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoReclamoDTO,
     * or with status 400 (Bad Request) if the tipoReclamoDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoReclamoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-reclamos")
    @Timed
    public ResponseEntity<TipoReclamoDTO> updateTipoReclamo(@Valid @RequestBody TipoReclamoDTO tipoReclamoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoReclamo : {}", tipoReclamoDTO);
        if (tipoReclamoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoReclamoDTO result = tipoReclamoService.save(tipoReclamoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoReclamoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-reclamos : get all the tipoReclamos.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of tipoReclamos in body
     */
    @GetMapping("/tipo-reclamos")
    @Timed
    public ResponseEntity<List<TipoReclamoDTO>> getAllTipoReclamos(TipoReclamoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TipoReclamos by criteria: {}", criteria);
        Page<TipoReclamoDTO> page = tipoReclamoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-reclamos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipo-reclamos/:id : get the "id" tipoReclamo.
     *
     * @param id the id of the tipoReclamoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoReclamoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-reclamos/{id}")
    @Timed
    public ResponseEntity<TipoReclamoDTO> getTipoReclamo(@PathVariable Long id) {
        log.debug("REST request to get TipoReclamo : {}", id);
        Optional<TipoReclamoDTO> tipoReclamoDTO = tipoReclamoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoReclamoDTO);
    }

    /**
     * DELETE  /tipo-reclamos/:id : delete the "id" tipoReclamo.
     *
     * @param id the id of the tipoReclamoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-reclamos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoReclamo(@PathVariable Long id) {
        log.debug("REST request to delete TipoReclamo : {}", id);
        tipoReclamoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
