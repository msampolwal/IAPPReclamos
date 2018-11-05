package com.iapp.reclamos.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import com.iapp.reclamos.domain.Parametro;
import com.iapp.reclamos.domain.enumeration.Estado;
import com.iapp.reclamos.repository.ParametroRepository;
import com.iapp.reclamos.service.ReclamoQueryService;
import com.iapp.reclamos.service.ReclamoService;
import com.iapp.reclamos.service.dto.ReclamoCriteria;
import com.iapp.reclamos.service.dto.ReclamoDTO;
import com.iapp.reclamos.service.dto.ReclamoFinalizadoDTO;
import com.iapp.reclamos.web.rest.errors.BadRequestAlertException;
import com.iapp.reclamos.web.rest.util.HeaderUtil;
import com.iapp.reclamos.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Reclamo.
 */
@RestController
@RequestMapping("/api")
public class ReclamoResource {

    private final Logger log = LoggerFactory.getLogger(ReclamoResource.class);

    private static final String ENTITY_NAME = "reclamo";

    private final ReclamoService reclamoService;
    
    private final ReclamoQueryService reclamoQueryService;
    
    private final ParametroRepository parametroRepository;

    public ReclamoResource(ReclamoService reclamoService, ReclamoQueryService reclamoQueryService, ParametroRepository parametroRepository) {
        this.reclamoService = reclamoService;
        this.reclamoQueryService = reclamoQueryService;
        this.parametroRepository = parametroRepository;
    }

    /**
     * POST  /reclamos : Create a new reclamo.
     *
     * @param reclamoDTO the reclamoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reclamoDTO, or with status 400 (Bad Request) if the reclamo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reclamos")
    @Timed
    public ResponseEntity<ReclamoDTO> createReclamo(@Valid @RequestBody ReclamoDTO reclamoDTO) throws URISyntaxException {
        log.debug("REST request to save Reclamo : {}", reclamoDTO);
        if (reclamoDTO.getId() != null) {
            throw new BadRequestAlertException("A new reclamo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        ReclamoDTO result = reclamoService.createReclamo(reclamoDTO);
        return ResponseEntity.created(new URI("/api/reclamos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reclamos : Updates an existing reclamo.
     *
     * @param reclamoDTO the reclamoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reclamoDTO,
     * or with status 400 (Bad Request) if the reclamoDTO is not valid,
     * or with status 500 (Internal Server Error) if the reclamoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reclamos")
    @Timed
    public ResponseEntity<ReclamoDTO> updateReclamo(@Valid @RequestBody ReclamoDTO reclamoDTO) throws URISyntaxException {
        log.debug("REST request to update Reclamo : {}", reclamoDTO);
        if (reclamoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReclamoDTO result = reclamoService.save(reclamoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reclamoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reclamos : get all the reclamos.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of reclamos in body
     */
    @GetMapping("/reclamos")
    @Timed
    public ResponseEntity<List<ReclamoDTO>> getAllReclamos(ReclamoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Reclamos by criteria: {}", criteria);
        Page<ReclamoDTO> page = reclamoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reclamos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /reclamos/:id : get the "id" reclamo.
     *
     * @param id the id of the reclamoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reclamoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reclamos/{id}")
    @Timed
    public ResponseEntity<ReclamoDTO> getReclamo(@PathVariable Long id) {
        log.debug("REST request to get Reclamo : {}", id);
        Optional<ReclamoDTO> reclamoDTO = reclamoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reclamoDTO);
    }

    /**
     * DELETE  /reclamos/:id : delete the "id" reclamo.
     *
     * @param id the id of the reclamoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reclamos/{id}")
    @Timed
    public ResponseEntity<Void> deleteReclamo(@PathVariable Long id) {
        log.debug("REST request to delete Reclamo : {}", id);
        reclamoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * GET  /reclamos/finalizar/:id : finalizar the "id" reclamo.
     *
     * @param id the id of the reclamoDTO to finalizar
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("/reclamos/finalizar/{id}")
    @Timed
    public ResponseEntity<ReclamoDTO> finalizarReclamo(@PathVariable Long id) {
        log.debug("REST request to finalizar Reclamo : {}", id);
        Optional<ReclamoDTO> reclamoDTO = reclamoService.findOne(id);
        ReclamoDTO dto = null;
        if (reclamoDTO.isPresent() && reclamoDTO.get().getEstado().equals(Estado.PENDIENTE)) {
        		dto = reclamoDTO.get();
        		reclamoService.finalizar(dto);
        } else {
        		throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "No se puede finalizar el reclamo con ese id");
        }
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dto.getId().toString()))
                .body(dto);
    }
    
    @PostMapping("/public/reclamos/finalizar/{id}")
    @Timed
    public ResponseEntity<ReclamoDTO> finalizarReclamoLogistica(@Valid @RequestBody ReclamoFinalizadoDTO r) throws URISyntaxException {
        Long id_pedido = r.getId_pedido();
		log.debug("REST request to finalizar Reclamo : {}", id_pedido);
        Optional<ReclamoDTO> reclamoDTO = reclamoService.findOne(id_pedido);
        ReclamoDTO dto = null;
        if (reclamoDTO.isPresent() && reclamoDTO.get().getEstado().equals(Estado.PENDIENTE_LOGISTICA)) {
        		dto = reclamoDTO.get();
        		reclamoService.finalizar(dto);
        } else {
        		throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "No se puede finalizar el reclamo con ese id");
        }
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dto.getId().toString()))
                .body(dto);
    }
    
    /**
     * GET  /public/reclamos/:id : get the "id" reclamo.
     *
     * @param id the id of the reclamoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reclamoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/public/reclamos/{id}")
    @Timed
    public ResponseEntity<ReclamoDTO> getReclamoPublic(@PathVariable Long id) {
        return this.getReclamo(id);
    }
}