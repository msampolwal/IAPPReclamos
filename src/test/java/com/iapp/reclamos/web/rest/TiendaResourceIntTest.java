package com.iapp.reclamos.web.rest;

import com.iapp.reclamos.IappReclamosApp;

import com.iapp.reclamos.domain.Tienda;
import com.iapp.reclamos.repository.TiendaRepository;
import com.iapp.reclamos.service.TiendaService;
import com.iapp.reclamos.service.dto.TiendaDTO;
import com.iapp.reclamos.service.mapper.TiendaMapper;
import com.iapp.reclamos.web.rest.errors.ExceptionTranslator;
import com.iapp.reclamos.service.dto.TiendaCriteria;
import com.iapp.reclamos.service.TiendaQueryService;

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
 * Test class for the TiendaResource REST controller.
 *
 * @see TiendaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IappReclamosApp.class)
public class TiendaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_LOGISTICA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_LOGISTICA = "BBBBBBBBBB";

    private static final String DEFAULT_URL_LOGISTICA = "AAAAAAAAAA";
    private static final String UPDATED_URL_LOGISTICA = "BBBBBBBBBB";

    @Autowired
    private TiendaRepository tiendaRepository;


    @Autowired
    private TiendaMapper tiendaMapper;
    

    @Autowired
    private TiendaService tiendaService;

    @Autowired
    private TiendaQueryService tiendaQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTiendaMockMvc;

    private Tienda tienda;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TiendaResource tiendaResource = new TiendaResource(tiendaService, tiendaQueryService);
        this.restTiendaMockMvc = MockMvcBuilders.standaloneSetup(tiendaResource)
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
    public static Tienda createEntity(EntityManager em) {
        Tienda tienda = new Tienda()
            .nombre(DEFAULT_NOMBRE)
            .url(DEFAULT_URL)
            .nombreLogistica(DEFAULT_NOMBRE_LOGISTICA)
            .urlLogistica(DEFAULT_URL_LOGISTICA);
        return tienda;
    }

    @Before
    public void initTest() {
        tienda = createEntity(em);
    }

    @Test
    @Transactional
    public void createTienda() throws Exception {
        int databaseSizeBeforeCreate = tiendaRepository.findAll().size();

        // Create the Tienda
        TiendaDTO tiendaDTO = tiendaMapper.toDto(tienda);
        restTiendaMockMvc.perform(post("/api/tiendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiendaDTO)))
            .andExpect(status().isCreated());

        // Validate the Tienda in the database
        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeCreate + 1);
        Tienda testTienda = tiendaList.get(tiendaList.size() - 1);
        assertThat(testTienda.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTienda.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testTienda.getNombreLogistica()).isEqualTo(DEFAULT_NOMBRE_LOGISTICA);
        assertThat(testTienda.getUrlLogistica()).isEqualTo(DEFAULT_URL_LOGISTICA);
    }

    @Test
    @Transactional
    public void createTiendaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tiendaRepository.findAll().size();

        // Create the Tienda with an existing ID
        tienda.setId(1L);
        TiendaDTO tiendaDTO = tiendaMapper.toDto(tienda);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTiendaMockMvc.perform(post("/api/tiendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tienda in the database
        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiendaRepository.findAll().size();
        // set the field null
        tienda.setNombre(null);

        // Create the Tienda, which fails.
        TiendaDTO tiendaDTO = tiendaMapper.toDto(tienda);

        restTiendaMockMvc.perform(post("/api/tiendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiendaDTO)))
            .andExpect(status().isBadRequest());

        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiendaRepository.findAll().size();
        // set the field null
        tienda.setUrl(null);

        // Create the Tienda, which fails.
        TiendaDTO tiendaDTO = tiendaMapper.toDto(tienda);

        restTiendaMockMvc.perform(post("/api/tiendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiendaDTO)))
            .andExpect(status().isBadRequest());

        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTiendas() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        // Get all the tiendaList
        restTiendaMockMvc.perform(get("/api/tiendas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tienda.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].nombreLogistica").value(hasItem(DEFAULT_NOMBRE_LOGISTICA.toString())))
            .andExpect(jsonPath("$.[*].urlLogistica").value(hasItem(DEFAULT_URL_LOGISTICA.toString())));
    }
    

    @Test
    @Transactional
    public void getTienda() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        // Get the tienda
        restTiendaMockMvc.perform(get("/api/tiendas/{id}", tienda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tienda.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.nombreLogistica").value(DEFAULT_NOMBRE_LOGISTICA.toString()))
            .andExpect(jsonPath("$.urlLogistica").value(DEFAULT_URL_LOGISTICA.toString()));
    }

    @Test
    @Transactional
    public void getAllTiendasByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        // Get all the tiendaList where nombre equals to DEFAULT_NOMBRE
        defaultTiendaShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the tiendaList where nombre equals to UPDATED_NOMBRE
        defaultTiendaShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllTiendasByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        // Get all the tiendaList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultTiendaShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the tiendaList where nombre equals to UPDATED_NOMBRE
        defaultTiendaShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllTiendasByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        // Get all the tiendaList where nombre is not null
        defaultTiendaShouldBeFound("nombre.specified=true");

        // Get all the tiendaList where nombre is null
        defaultTiendaShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllTiendasByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        // Get all the tiendaList where url equals to DEFAULT_URL
        defaultTiendaShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the tiendaList where url equals to UPDATED_URL
        defaultTiendaShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllTiendasByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        // Get all the tiendaList where url in DEFAULT_URL or UPDATED_URL
        defaultTiendaShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the tiendaList where url equals to UPDATED_URL
        defaultTiendaShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllTiendasByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        // Get all the tiendaList where url is not null
        defaultTiendaShouldBeFound("url.specified=true");

        // Get all the tiendaList where url is null
        defaultTiendaShouldNotBeFound("url.specified=false");
    }

    @Test
    @Transactional
    public void getAllTiendasByNombreLogisticaIsEqualToSomething() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        // Get all the tiendaList where nombreLogistica equals to DEFAULT_NOMBRE_LOGISTICA
        defaultTiendaShouldBeFound("nombreLogistica.equals=" + DEFAULT_NOMBRE_LOGISTICA);

        // Get all the tiendaList where nombreLogistica equals to UPDATED_NOMBRE_LOGISTICA
        defaultTiendaShouldNotBeFound("nombreLogistica.equals=" + UPDATED_NOMBRE_LOGISTICA);
    }

    @Test
    @Transactional
    public void getAllTiendasByNombreLogisticaIsInShouldWork() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        // Get all the tiendaList where nombreLogistica in DEFAULT_NOMBRE_LOGISTICA or UPDATED_NOMBRE_LOGISTICA
        defaultTiendaShouldBeFound("nombreLogistica.in=" + DEFAULT_NOMBRE_LOGISTICA + "," + UPDATED_NOMBRE_LOGISTICA);

        // Get all the tiendaList where nombreLogistica equals to UPDATED_NOMBRE_LOGISTICA
        defaultTiendaShouldNotBeFound("nombreLogistica.in=" + UPDATED_NOMBRE_LOGISTICA);
    }

    @Test
    @Transactional
    public void getAllTiendasByNombreLogisticaIsNullOrNotNull() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        // Get all the tiendaList where nombreLogistica is not null
        defaultTiendaShouldBeFound("nombreLogistica.specified=true");

        // Get all the tiendaList where nombreLogistica is null
        defaultTiendaShouldNotBeFound("nombreLogistica.specified=false");
    }

    @Test
    @Transactional
    public void getAllTiendasByUrlLogisticaIsEqualToSomething() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        // Get all the tiendaList where urlLogistica equals to DEFAULT_URL_LOGISTICA
        defaultTiendaShouldBeFound("urlLogistica.equals=" + DEFAULT_URL_LOGISTICA);

        // Get all the tiendaList where urlLogistica equals to UPDATED_URL_LOGISTICA
        defaultTiendaShouldNotBeFound("urlLogistica.equals=" + UPDATED_URL_LOGISTICA);
    }

    @Test
    @Transactional
    public void getAllTiendasByUrlLogisticaIsInShouldWork() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        // Get all the tiendaList where urlLogistica in DEFAULT_URL_LOGISTICA or UPDATED_URL_LOGISTICA
        defaultTiendaShouldBeFound("urlLogistica.in=" + DEFAULT_URL_LOGISTICA + "," + UPDATED_URL_LOGISTICA);

        // Get all the tiendaList where urlLogistica equals to UPDATED_URL_LOGISTICA
        defaultTiendaShouldNotBeFound("urlLogistica.in=" + UPDATED_URL_LOGISTICA);
    }

    @Test
    @Transactional
    public void getAllTiendasByUrlLogisticaIsNullOrNotNull() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        // Get all the tiendaList where urlLogistica is not null
        defaultTiendaShouldBeFound("urlLogistica.specified=true");

        // Get all the tiendaList where urlLogistica is null
        defaultTiendaShouldNotBeFound("urlLogistica.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTiendaShouldBeFound(String filter) throws Exception {
        restTiendaMockMvc.perform(get("/api/tiendas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tienda.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].nombreLogistica").value(hasItem(DEFAULT_NOMBRE_LOGISTICA.toString())))
            .andExpect(jsonPath("$.[*].urlLogistica").value(hasItem(DEFAULT_URL_LOGISTICA.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTiendaShouldNotBeFound(String filter) throws Exception {
        restTiendaMockMvc.perform(get("/api/tiendas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingTienda() throws Exception {
        // Get the tienda
        restTiendaMockMvc.perform(get("/api/tiendas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTienda() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        int databaseSizeBeforeUpdate = tiendaRepository.findAll().size();

        // Update the tienda
        Tienda updatedTienda = tiendaRepository.findById(tienda.getId()).get();
        // Disconnect from session so that the updates on updatedTienda are not directly saved in db
        em.detach(updatedTienda);
        updatedTienda
            .nombre(UPDATED_NOMBRE)
            .url(UPDATED_URL)
            .nombreLogistica(UPDATED_NOMBRE_LOGISTICA)
            .urlLogistica(UPDATED_URL_LOGISTICA);
        TiendaDTO tiendaDTO = tiendaMapper.toDto(updatedTienda);

        restTiendaMockMvc.perform(put("/api/tiendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiendaDTO)))
            .andExpect(status().isOk());

        // Validate the Tienda in the database
        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeUpdate);
        Tienda testTienda = tiendaList.get(tiendaList.size() - 1);
        assertThat(testTienda.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTienda.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testTienda.getNombreLogistica()).isEqualTo(UPDATED_NOMBRE_LOGISTICA);
        assertThat(testTienda.getUrlLogistica()).isEqualTo(UPDATED_URL_LOGISTICA);
    }

    @Test
    @Transactional
    public void updateNonExistingTienda() throws Exception {
        int databaseSizeBeforeUpdate = tiendaRepository.findAll().size();

        // Create the Tienda
        TiendaDTO tiendaDTO = tiendaMapper.toDto(tienda);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTiendaMockMvc.perform(put("/api/tiendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tienda in the database
        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTienda() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        int databaseSizeBeforeDelete = tiendaRepository.findAll().size();

        // Get the tienda
        restTiendaMockMvc.perform(delete("/api/tiendas/{id}", tienda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tienda.class);
        Tienda tienda1 = new Tienda();
        tienda1.setId(1L);
        Tienda tienda2 = new Tienda();
        tienda2.setId(tienda1.getId());
        assertThat(tienda1).isEqualTo(tienda2);
        tienda2.setId(2L);
        assertThat(tienda1).isNotEqualTo(tienda2);
        tienda1.setId(null);
        assertThat(tienda1).isNotEqualTo(tienda2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TiendaDTO.class);
        TiendaDTO tiendaDTO1 = new TiendaDTO();
        tiendaDTO1.setId(1L);
        TiendaDTO tiendaDTO2 = new TiendaDTO();
        assertThat(tiendaDTO1).isNotEqualTo(tiendaDTO2);
        tiendaDTO2.setId(tiendaDTO1.getId());
        assertThat(tiendaDTO1).isEqualTo(tiendaDTO2);
        tiendaDTO2.setId(2L);
        assertThat(tiendaDTO1).isNotEqualTo(tiendaDTO2);
        tiendaDTO1.setId(null);
        assertThat(tiendaDTO1).isNotEqualTo(tiendaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tiendaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tiendaMapper.fromId(null)).isNull();
    }
}
