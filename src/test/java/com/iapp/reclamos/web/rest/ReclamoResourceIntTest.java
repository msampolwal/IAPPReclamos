package com.iapp.reclamos.web.rest;

import com.iapp.reclamos.IappReclamosApp;

import com.iapp.reclamos.domain.Reclamo;
import com.iapp.reclamos.domain.Pedido;
import com.iapp.reclamos.domain.TipoReclamo;
import com.iapp.reclamos.repository.ParametroRepository;
import com.iapp.reclamos.repository.ReclamoRepository;
import com.iapp.reclamos.service.ReclamoService;
import com.iapp.reclamos.service.dto.ReclamoDTO;
import com.iapp.reclamos.service.mapper.ReclamoMapper;
import com.iapp.reclamos.web.rest.errors.ExceptionTranslator;
import com.iapp.reclamos.service.dto.ReclamoCriteria;
import com.iapp.reclamos.service.ReclamoQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.iapp.reclamos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iapp.reclamos.domain.enumeration.Estado;
/**
 * Test class for the ReclamoResource REST controller.
 *
 * @see ReclamoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IappReclamosApp.class)
public class ReclamoResourceIntTest {

    private static final String DEFAULT_OBSERVACION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACION = "BBBBBBBBBB";

    private static final Estado DEFAULT_ESTADO = Estado.PENDIENTE;
    private static final Estado UPDATED_ESTADO = Estado.PENDIENTE_LOGISTICA;

    @Autowired
    private ReclamoRepository reclamoRepository;


    @Autowired
    private ReclamoMapper reclamoMapper;
    

    @Autowired
    private ReclamoService reclamoService;

    @Autowired
    private ReclamoQueryService reclamoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReclamoMockMvc;

    private Reclamo reclamo;
    
    @Autowired
    private ParametroRepository parametroRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReclamoResource reclamoResource = new ReclamoResource(reclamoService, reclamoQueryService, parametroRepository);
        this.restReclamoMockMvc = MockMvcBuilders.standaloneSetup(reclamoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reclamo createEntity(EntityManager em) {
        Reclamo reclamo = new Reclamo()
            .observacion(DEFAULT_OBSERVACION)
            .estado(DEFAULT_ESTADO);
        return reclamo;
    }

    @Before
    public void initTest() {
        reclamo = createEntity(em);
    }

    @Test
    @Transactional
    public void createReclamo() throws Exception {
        int databaseSizeBeforeCreate = reclamoRepository.findAll().size();

        // Create the Reclamo
        ReclamoDTO reclamoDTO = reclamoMapper.toDto(reclamo);
        restReclamoMockMvc.perform(post("/api/reclamos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reclamoDTO)))
            .andExpect(status().isCreated());

        // Validate the Reclamo in the database
        List<Reclamo> reclamoList = reclamoRepository.findAll();
        assertThat(reclamoList).hasSize(databaseSizeBeforeCreate + 1);
        Reclamo testReclamo = reclamoList.get(reclamoList.size() - 1);
        assertThat(testReclamo.getObservacion()).isEqualTo(DEFAULT_OBSERVACION);
        assertThat(testReclamo.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createReclamoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reclamoRepository.findAll().size();

        // Create the Reclamo with an existing ID
        reclamo.setId(1L);
        ReclamoDTO reclamoDTO = reclamoMapper.toDto(reclamo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReclamoMockMvc.perform(post("/api/reclamos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reclamoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reclamo in the database
        List<Reclamo> reclamoList = reclamoRepository.findAll();
        assertThat(reclamoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkObservacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = reclamoRepository.findAll().size();
        // set the field null
        reclamo.setObservacion(null);

        // Create the Reclamo, which fails.
        ReclamoDTO reclamoDTO = reclamoMapper.toDto(reclamo);

        restReclamoMockMvc.perform(post("/api/reclamos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reclamoDTO)))
            .andExpect(status().isBadRequest());

        List<Reclamo> reclamoList = reclamoRepository.findAll();
        assertThat(reclamoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReclamos() throws Exception {
        // Initialize the database
        reclamoRepository.saveAndFlush(reclamo);

        // Get all the reclamoList
        restReclamoMockMvc.perform(get("/api/reclamos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reclamo.getId().intValue())))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }
    

    @Test
    @Transactional
    public void getReclamo() throws Exception {
        // Initialize the database
        reclamoRepository.saveAndFlush(reclamo);

        // Get the reclamo
        restReclamoMockMvc.perform(get("/api/reclamos/{id}", reclamo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reclamo.getId().intValue()))
            .andExpect(jsonPath("$.observacion").value(DEFAULT_OBSERVACION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getAllReclamosByObservacionIsEqualToSomething() throws Exception {
        // Initialize the database
        reclamoRepository.saveAndFlush(reclamo);

        // Get all the reclamoList where observacion equals to DEFAULT_OBSERVACION
        defaultReclamoShouldBeFound("observacion.equals=" + DEFAULT_OBSERVACION);

        // Get all the reclamoList where observacion equals to UPDATED_OBSERVACION
        defaultReclamoShouldNotBeFound("observacion.equals=" + UPDATED_OBSERVACION);
    }

    @Test
    @Transactional
    public void getAllReclamosByObservacionIsInShouldWork() throws Exception {
        // Initialize the database
        reclamoRepository.saveAndFlush(reclamo);

        // Get all the reclamoList where observacion in DEFAULT_OBSERVACION or UPDATED_OBSERVACION
        defaultReclamoShouldBeFound("observacion.in=" + DEFAULT_OBSERVACION + "," + UPDATED_OBSERVACION);

        // Get all the reclamoList where observacion equals to UPDATED_OBSERVACION
        defaultReclamoShouldNotBeFound("observacion.in=" + UPDATED_OBSERVACION);
    }

    @Test
    @Transactional
    public void getAllReclamosByObservacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        reclamoRepository.saveAndFlush(reclamo);

        // Get all the reclamoList where observacion is not null
        defaultReclamoShouldBeFound("observacion.specified=true");

        // Get all the reclamoList where observacion is null
        defaultReclamoShouldNotBeFound("observacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllReclamosByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        reclamoRepository.saveAndFlush(reclamo);

        // Get all the reclamoList where estado equals to DEFAULT_ESTADO
        defaultReclamoShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the reclamoList where estado equals to UPDATED_ESTADO
        defaultReclamoShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllReclamosByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        reclamoRepository.saveAndFlush(reclamo);

        // Get all the reclamoList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultReclamoShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the reclamoList where estado equals to UPDATED_ESTADO
        defaultReclamoShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllReclamosByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        reclamoRepository.saveAndFlush(reclamo);

        // Get all the reclamoList where estado is not null
        defaultReclamoShouldBeFound("estado.specified=true");

        // Get all the reclamoList where estado is null
        defaultReclamoShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    public void getAllReclamosByPedidoIsEqualToSomething() throws Exception {
        // Initialize the database
        Pedido pedido = PedidoResourceIntTest.createEntity(em);
        em.persist(pedido);
        em.flush();
        reclamo.setPedido(pedido);
        reclamoRepository.saveAndFlush(reclamo);
        Long pedidoId = pedido.getId();

        // Get all the reclamoList where pedido equals to pedidoId
        defaultReclamoShouldBeFound("pedidoId.equals=" + pedidoId);

        // Get all the reclamoList where pedido equals to pedidoId + 1
        defaultReclamoShouldNotBeFound("pedidoId.equals=" + (pedidoId + 1));
    }


    @Test
    @Transactional
    public void getAllReclamosByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        TipoReclamo tipo = TipoReclamoResourceIntTest.createEntity(em);
        em.persist(tipo);
        em.flush();
        reclamo.setTipo(tipo);
        reclamoRepository.saveAndFlush(reclamo);
        Long tipoId = tipo.getId();

        // Get all the reclamoList where tipo equals to tipoId
        defaultReclamoShouldBeFound("tipoId.equals=" + tipoId);

        // Get all the reclamoList where tipo equals to tipoId + 1
        defaultReclamoShouldNotBeFound("tipoId.equals=" + (tipoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultReclamoShouldBeFound(String filter) throws Exception {
        restReclamoMockMvc.perform(get("/api/reclamos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reclamo.getId().intValue())))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultReclamoShouldNotBeFound(String filter) throws Exception {
        restReclamoMockMvc.perform(get("/api/reclamos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingReclamo() throws Exception {
        // Get the reclamo
        restReclamoMockMvc.perform(get("/api/reclamos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReclamo() throws Exception {
        // Initialize the database
        reclamoRepository.saveAndFlush(reclamo);

        int databaseSizeBeforeUpdate = reclamoRepository.findAll().size();

        // Update the reclamo
        Reclamo updatedReclamo = reclamoRepository.findById(reclamo.getId()).get();
        // Disconnect from session so that the updates on updatedReclamo are not directly saved in db
        em.detach(updatedReclamo);
        updatedReclamo
            .observacion(UPDATED_OBSERVACION)
            .estado(UPDATED_ESTADO);
        ReclamoDTO reclamoDTO = reclamoMapper.toDto(updatedReclamo);

        restReclamoMockMvc.perform(put("/api/reclamos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reclamoDTO)))
            .andExpect(status().isOk());

        // Validate the Reclamo in the database
        List<Reclamo> reclamoList = reclamoRepository.findAll();
        assertThat(reclamoList).hasSize(databaseSizeBeforeUpdate);
        Reclamo testReclamo = reclamoList.get(reclamoList.size() - 1);
        assertThat(testReclamo.getObservacion()).isEqualTo(UPDATED_OBSERVACION);
        assertThat(testReclamo.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingReclamo() throws Exception {
        int databaseSizeBeforeUpdate = reclamoRepository.findAll().size();

        // Create the Reclamo
        ReclamoDTO reclamoDTO = reclamoMapper.toDto(reclamo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReclamoMockMvc.perform(put("/api/reclamos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reclamoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reclamo in the database
        List<Reclamo> reclamoList = reclamoRepository.findAll();
        assertThat(reclamoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReclamo() throws Exception {
        // Initialize the database
        reclamoRepository.saveAndFlush(reclamo);

        int databaseSizeBeforeDelete = reclamoRepository.findAll().size();

        // Get the reclamo
        restReclamoMockMvc.perform(delete("/api/reclamos/{id}", reclamo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Reclamo> reclamoList = reclamoRepository.findAll();
        assertThat(reclamoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reclamo.class);
        Reclamo reclamo1 = new Reclamo();
        reclamo1.setId(1L);
        Reclamo reclamo2 = new Reclamo();
        reclamo2.setId(reclamo1.getId());
        assertThat(reclamo1).isEqualTo(reclamo2);
        reclamo2.setId(2L);
        assertThat(reclamo1).isNotEqualTo(reclamo2);
        reclamo1.setId(null);
        assertThat(reclamo1).isNotEqualTo(reclamo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReclamoDTO.class);
        ReclamoDTO reclamoDTO1 = new ReclamoDTO();
        reclamoDTO1.setId(1L);
        ReclamoDTO reclamoDTO2 = new ReclamoDTO();
        assertThat(reclamoDTO1).isNotEqualTo(reclamoDTO2);
        reclamoDTO2.setId(reclamoDTO1.getId());
        assertThat(reclamoDTO1).isEqualTo(reclamoDTO2);
        reclamoDTO2.setId(2L);
        assertThat(reclamoDTO1).isNotEqualTo(reclamoDTO2);
        reclamoDTO1.setId(null);
        assertThat(reclamoDTO1).isNotEqualTo(reclamoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reclamoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reclamoMapper.fromId(null)).isNull();
    }
}
