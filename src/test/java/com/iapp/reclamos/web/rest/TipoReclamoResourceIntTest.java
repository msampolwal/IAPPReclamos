package com.iapp.reclamos.web.rest;

import com.iapp.reclamos.IappReclamosApp;

import com.iapp.reclamos.domain.TipoReclamo;
import com.iapp.reclamos.repository.TipoReclamoRepository;
import com.iapp.reclamos.service.TipoReclamoService;
import com.iapp.reclamos.service.dto.TipoReclamoDTO;
import com.iapp.reclamos.service.mapper.TipoReclamoMapper;
import com.iapp.reclamos.web.rest.errors.ExceptionTranslator;
import com.iapp.reclamos.service.dto.TipoReclamoCriteria;
import com.iapp.reclamos.service.TipoReclamoQueryService;

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

/**
 * Test class for the TipoReclamoResource REST controller.
 *
 * @see TipoReclamoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IappReclamosApp.class)
public class TipoReclamoResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NOTIFICA_A_LOGISTICA = false;
    private static final Boolean UPDATED_NOTIFICA_A_LOGISTICA = true;

    private static final Boolean DEFAULT_NOTIFICA_A_TIENDA = false;
    private static final Boolean UPDATED_NOTIFICA_A_TIENDA = true;

    @Autowired
    private TipoReclamoRepository tipoReclamoRepository;


    @Autowired
    private TipoReclamoMapper tipoReclamoMapper;
    

    @Autowired
    private TipoReclamoService tipoReclamoService;

    @Autowired
    private TipoReclamoQueryService tipoReclamoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoReclamoMockMvc;

    private TipoReclamo tipoReclamo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoReclamoResource tipoReclamoResource = new TipoReclamoResource(tipoReclamoService, tipoReclamoQueryService);
        this.restTipoReclamoMockMvc = MockMvcBuilders.standaloneSetup(tipoReclamoResource)
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
    public static TipoReclamo createEntity(EntityManager em) {
        TipoReclamo tipoReclamo = new TipoReclamo()
            .descripcion(DEFAULT_DESCRIPCION)
            .notificaALogistica(DEFAULT_NOTIFICA_A_LOGISTICA)
            .notificaATienda(DEFAULT_NOTIFICA_A_TIENDA);
        return tipoReclamo;
    }

    @Before
    public void initTest() {
        tipoReclamo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoReclamo() throws Exception {
        int databaseSizeBeforeCreate = tipoReclamoRepository.findAll().size();

        // Create the TipoReclamo
        TipoReclamoDTO tipoReclamoDTO = tipoReclamoMapper.toDto(tipoReclamo);
        restTipoReclamoMockMvc.perform(post("/api/tipo-reclamos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoReclamoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoReclamo in the database
        List<TipoReclamo> tipoReclamoList = tipoReclamoRepository.findAll();
        assertThat(tipoReclamoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoReclamo testTipoReclamo = tipoReclamoList.get(tipoReclamoList.size() - 1);
        assertThat(testTipoReclamo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTipoReclamo.isNotificaALogistica()).isEqualTo(DEFAULT_NOTIFICA_A_LOGISTICA);
        assertThat(testTipoReclamo.isNotificaATienda()).isEqualTo(DEFAULT_NOTIFICA_A_TIENDA);
    }

    @Test
    @Transactional
    public void createTipoReclamoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoReclamoRepository.findAll().size();

        // Create the TipoReclamo with an existing ID
        tipoReclamo.setId(1L);
        TipoReclamoDTO tipoReclamoDTO = tipoReclamoMapper.toDto(tipoReclamo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoReclamoMockMvc.perform(post("/api/tipo-reclamos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoReclamoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoReclamo in the database
        List<TipoReclamo> tipoReclamoList = tipoReclamoRepository.findAll();
        assertThat(tipoReclamoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoReclamoRepository.findAll().size();
        // set the field null
        tipoReclamo.setDescripcion(null);

        // Create the TipoReclamo, which fails.
        TipoReclamoDTO tipoReclamoDTO = tipoReclamoMapper.toDto(tipoReclamo);

        restTipoReclamoMockMvc.perform(post("/api/tipo-reclamos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoReclamoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoReclamo> tipoReclamoList = tipoReclamoRepository.findAll();
        assertThat(tipoReclamoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificaALogisticaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoReclamoRepository.findAll().size();
        // set the field null
        tipoReclamo.setNotificaALogistica(null);

        // Create the TipoReclamo, which fails.
        TipoReclamoDTO tipoReclamoDTO = tipoReclamoMapper.toDto(tipoReclamo);

        restTipoReclamoMockMvc.perform(post("/api/tipo-reclamos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoReclamoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoReclamo> tipoReclamoList = tipoReclamoRepository.findAll();
        assertThat(tipoReclamoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificaATiendaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoReclamoRepository.findAll().size();
        // set the field null
        tipoReclamo.setNotificaATienda(null);

        // Create the TipoReclamo, which fails.
        TipoReclamoDTO tipoReclamoDTO = tipoReclamoMapper.toDto(tipoReclamo);

        restTipoReclamoMockMvc.perform(post("/api/tipo-reclamos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoReclamoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoReclamo> tipoReclamoList = tipoReclamoRepository.findAll();
        assertThat(tipoReclamoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoReclamos() throws Exception {
        // Initialize the database
        tipoReclamoRepository.saveAndFlush(tipoReclamo);

        // Get all the tipoReclamoList
        restTipoReclamoMockMvc.perform(get("/api/tipo-reclamos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoReclamo.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].notificaALogistica").value(hasItem(DEFAULT_NOTIFICA_A_LOGISTICA.booleanValue())))
            .andExpect(jsonPath("$.[*].notificaATienda").value(hasItem(DEFAULT_NOTIFICA_A_TIENDA.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getTipoReclamo() throws Exception {
        // Initialize the database
        tipoReclamoRepository.saveAndFlush(tipoReclamo);

        // Get the tipoReclamo
        restTipoReclamoMockMvc.perform(get("/api/tipo-reclamos/{id}", tipoReclamo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoReclamo.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.notificaALogistica").value(DEFAULT_NOTIFICA_A_LOGISTICA.booleanValue()))
            .andExpect(jsonPath("$.notificaATienda").value(DEFAULT_NOTIFICA_A_TIENDA.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllTipoReclamosByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoReclamoRepository.saveAndFlush(tipoReclamo);

        // Get all the tipoReclamoList where descripcion equals to DEFAULT_DESCRIPCION
        defaultTipoReclamoShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the tipoReclamoList where descripcion equals to UPDATED_DESCRIPCION
        defaultTipoReclamoShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTipoReclamosByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        tipoReclamoRepository.saveAndFlush(tipoReclamo);

        // Get all the tipoReclamoList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultTipoReclamoShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the tipoReclamoList where descripcion equals to UPDATED_DESCRIPCION
        defaultTipoReclamoShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTipoReclamosByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoReclamoRepository.saveAndFlush(tipoReclamo);

        // Get all the tipoReclamoList where descripcion is not null
        defaultTipoReclamoShouldBeFound("descripcion.specified=true");

        // Get all the tipoReclamoList where descripcion is null
        defaultTipoReclamoShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipoReclamosByNotificaALogisticaIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoReclamoRepository.saveAndFlush(tipoReclamo);

        // Get all the tipoReclamoList where notificaALogistica equals to DEFAULT_NOTIFICA_A_LOGISTICA
        defaultTipoReclamoShouldBeFound("notificaALogistica.equals=" + DEFAULT_NOTIFICA_A_LOGISTICA);

        // Get all the tipoReclamoList where notificaALogistica equals to UPDATED_NOTIFICA_A_LOGISTICA
        defaultTipoReclamoShouldNotBeFound("notificaALogistica.equals=" + UPDATED_NOTIFICA_A_LOGISTICA);
    }

    @Test
    @Transactional
    public void getAllTipoReclamosByNotificaALogisticaIsInShouldWork() throws Exception {
        // Initialize the database
        tipoReclamoRepository.saveAndFlush(tipoReclamo);

        // Get all the tipoReclamoList where notificaALogistica in DEFAULT_NOTIFICA_A_LOGISTICA or UPDATED_NOTIFICA_A_LOGISTICA
        defaultTipoReclamoShouldBeFound("notificaALogistica.in=" + DEFAULT_NOTIFICA_A_LOGISTICA + "," + UPDATED_NOTIFICA_A_LOGISTICA);

        // Get all the tipoReclamoList where notificaALogistica equals to UPDATED_NOTIFICA_A_LOGISTICA
        defaultTipoReclamoShouldNotBeFound("notificaALogistica.in=" + UPDATED_NOTIFICA_A_LOGISTICA);
    }

    @Test
    @Transactional
    public void getAllTipoReclamosByNotificaALogisticaIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoReclamoRepository.saveAndFlush(tipoReclamo);

        // Get all the tipoReclamoList where notificaALogistica is not null
        defaultTipoReclamoShouldBeFound("notificaALogistica.specified=true");

        // Get all the tipoReclamoList where notificaALogistica is null
        defaultTipoReclamoShouldNotBeFound("notificaALogistica.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipoReclamosByNotificaATiendaIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoReclamoRepository.saveAndFlush(tipoReclamo);

        // Get all the tipoReclamoList where notificaATienda equals to DEFAULT_NOTIFICA_A_TIENDA
        defaultTipoReclamoShouldBeFound("notificaATienda.equals=" + DEFAULT_NOTIFICA_A_TIENDA);

        // Get all the tipoReclamoList where notificaATienda equals to UPDATED_NOTIFICA_A_TIENDA
        defaultTipoReclamoShouldNotBeFound("notificaATienda.equals=" + UPDATED_NOTIFICA_A_TIENDA);
    }

    @Test
    @Transactional
    public void getAllTipoReclamosByNotificaATiendaIsInShouldWork() throws Exception {
        // Initialize the database
        tipoReclamoRepository.saveAndFlush(tipoReclamo);

        // Get all the tipoReclamoList where notificaATienda in DEFAULT_NOTIFICA_A_TIENDA or UPDATED_NOTIFICA_A_TIENDA
        defaultTipoReclamoShouldBeFound("notificaATienda.in=" + DEFAULT_NOTIFICA_A_TIENDA + "," + UPDATED_NOTIFICA_A_TIENDA);

        // Get all the tipoReclamoList where notificaATienda equals to UPDATED_NOTIFICA_A_TIENDA
        defaultTipoReclamoShouldNotBeFound("notificaATienda.in=" + UPDATED_NOTIFICA_A_TIENDA);
    }

    @Test
    @Transactional
    public void getAllTipoReclamosByNotificaATiendaIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoReclamoRepository.saveAndFlush(tipoReclamo);

        // Get all the tipoReclamoList where notificaATienda is not null
        defaultTipoReclamoShouldBeFound("notificaATienda.specified=true");

        // Get all the tipoReclamoList where notificaATienda is null
        defaultTipoReclamoShouldNotBeFound("notificaATienda.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTipoReclamoShouldBeFound(String filter) throws Exception {
        restTipoReclamoMockMvc.perform(get("/api/tipo-reclamos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoReclamo.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].notificaALogistica").value(hasItem(DEFAULT_NOTIFICA_A_LOGISTICA.booleanValue())))
            .andExpect(jsonPath("$.[*].notificaATienda").value(hasItem(DEFAULT_NOTIFICA_A_TIENDA.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTipoReclamoShouldNotBeFound(String filter) throws Exception {
        restTipoReclamoMockMvc.perform(get("/api/tipo-reclamos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingTipoReclamo() throws Exception {
        // Get the tipoReclamo
        restTipoReclamoMockMvc.perform(get("/api/tipo-reclamos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoReclamo() throws Exception {
        // Initialize the database
        tipoReclamoRepository.saveAndFlush(tipoReclamo);

        int databaseSizeBeforeUpdate = tipoReclamoRepository.findAll().size();

        // Update the tipoReclamo
        TipoReclamo updatedTipoReclamo = tipoReclamoRepository.findById(tipoReclamo.getId()).get();
        // Disconnect from session so that the updates on updatedTipoReclamo are not directly saved in db
        em.detach(updatedTipoReclamo);
        updatedTipoReclamo
            .descripcion(UPDATED_DESCRIPCION)
            .notificaALogistica(UPDATED_NOTIFICA_A_LOGISTICA)
            .notificaATienda(UPDATED_NOTIFICA_A_TIENDA);
        TipoReclamoDTO tipoReclamoDTO = tipoReclamoMapper.toDto(updatedTipoReclamo);

        restTipoReclamoMockMvc.perform(put("/api/tipo-reclamos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoReclamoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoReclamo in the database
        List<TipoReclamo> tipoReclamoList = tipoReclamoRepository.findAll();
        assertThat(tipoReclamoList).hasSize(databaseSizeBeforeUpdate);
        TipoReclamo testTipoReclamo = tipoReclamoList.get(tipoReclamoList.size() - 1);
        assertThat(testTipoReclamo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTipoReclamo.isNotificaALogistica()).isEqualTo(UPDATED_NOTIFICA_A_LOGISTICA);
        assertThat(testTipoReclamo.isNotificaATienda()).isEqualTo(UPDATED_NOTIFICA_A_TIENDA);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoReclamo() throws Exception {
        int databaseSizeBeforeUpdate = tipoReclamoRepository.findAll().size();

        // Create the TipoReclamo
        TipoReclamoDTO tipoReclamoDTO = tipoReclamoMapper.toDto(tipoReclamo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoReclamoMockMvc.perform(put("/api/tipo-reclamos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoReclamoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoReclamo in the database
        List<TipoReclamo> tipoReclamoList = tipoReclamoRepository.findAll();
        assertThat(tipoReclamoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoReclamo() throws Exception {
        // Initialize the database
        tipoReclamoRepository.saveAndFlush(tipoReclamo);

        int databaseSizeBeforeDelete = tipoReclamoRepository.findAll().size();

        // Get the tipoReclamo
        restTipoReclamoMockMvc.perform(delete("/api/tipo-reclamos/{id}", tipoReclamo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoReclamo> tipoReclamoList = tipoReclamoRepository.findAll();
        assertThat(tipoReclamoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoReclamo.class);
        TipoReclamo tipoReclamo1 = new TipoReclamo();
        tipoReclamo1.setId(1L);
        TipoReclamo tipoReclamo2 = new TipoReclamo();
        tipoReclamo2.setId(tipoReclamo1.getId());
        assertThat(tipoReclamo1).isEqualTo(tipoReclamo2);
        tipoReclamo2.setId(2L);
        assertThat(tipoReclamo1).isNotEqualTo(tipoReclamo2);
        tipoReclamo1.setId(null);
        assertThat(tipoReclamo1).isNotEqualTo(tipoReclamo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoReclamoDTO.class);
        TipoReclamoDTO tipoReclamoDTO1 = new TipoReclamoDTO();
        tipoReclamoDTO1.setId(1L);
        TipoReclamoDTO tipoReclamoDTO2 = new TipoReclamoDTO();
        assertThat(tipoReclamoDTO1).isNotEqualTo(tipoReclamoDTO2);
        tipoReclamoDTO2.setId(tipoReclamoDTO1.getId());
        assertThat(tipoReclamoDTO1).isEqualTo(tipoReclamoDTO2);
        tipoReclamoDTO2.setId(2L);
        assertThat(tipoReclamoDTO1).isNotEqualTo(tipoReclamoDTO2);
        tipoReclamoDTO1.setId(null);
        assertThat(tipoReclamoDTO1).isNotEqualTo(tipoReclamoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoReclamoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoReclamoMapper.fromId(null)).isNull();
    }
}
