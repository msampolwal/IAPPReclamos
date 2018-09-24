package com.iapp.reclamos.web.rest;

import com.iapp.reclamos.IappReclamosApp;

import com.iapp.reclamos.domain.Parametro;
import com.iapp.reclamos.repository.ParametroRepository;
import com.iapp.reclamos.service.ParametroService;
import com.iapp.reclamos.service.dto.ParametroDTO;
import com.iapp.reclamos.service.mapper.ParametroMapper;
import com.iapp.reclamos.web.rest.errors.ExceptionTranslator;
import com.iapp.reclamos.service.dto.ParametroCriteria;
import com.iapp.reclamos.service.ParametroQueryService;

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
 * Test class for the ParametroResource REST controller.
 *
 * @see ParametroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IappReclamosApp.class)
public class ParametroResourceIntTest {

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private ParametroRepository parametroRepository;


    @Autowired
    private ParametroMapper parametroMapper;
    

    @Autowired
    private ParametroService parametroService;

    @Autowired
    private ParametroQueryService parametroQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParametroMockMvc;

    private Parametro parametro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParametroResource parametroResource = new ParametroResource(parametroService, parametroQueryService);
        this.restParametroMockMvc = MockMvcBuilders.standaloneSetup(parametroResource)
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
    public static Parametro createEntity(EntityManager em) {
        Parametro parametro = new Parametro()
            .clave(DEFAULT_CLAVE)
            .valor(DEFAULT_VALOR);
        return parametro;
    }

    @Before
    public void initTest() {
        parametro = createEntity(em);
    }

    @Test
    @Transactional
    public void createParametro() throws Exception {
        int databaseSizeBeforeCreate = parametroRepository.findAll().size();

        // Create the Parametro
        ParametroDTO parametroDTO = parametroMapper.toDto(parametro);
        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isCreated());

        // Validate the Parametro in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeCreate + 1);
        Parametro testParametro = parametroList.get(parametroList.size() - 1);
        assertThat(testParametro.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testParametro.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createParametroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parametroRepository.findAll().size();

        // Create the Parametro with an existing ID
        parametro.setId(1L);
        ParametroDTO parametroDTO = parametroMapper.toDto(parametro);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Parametro in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkClaveIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametroRepository.findAll().size();
        // set the field null
        parametro.setClave(null);

        // Create the Parametro, which fails.
        ParametroDTO parametroDTO = parametroMapper.toDto(parametro);

        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isBadRequest());

        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametroRepository.findAll().size();
        // set the field null
        parametro.setValor(null);

        // Create the Parametro, which fails.
        ParametroDTO parametroDTO = parametroMapper.toDto(parametro);

        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isBadRequest());

        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParametros() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        // Get all the parametroList
        restParametroMockMvc.perform(get("/api/parametros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametro.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.toString())));
    }
    

    @Test
    @Transactional
    public void getParametro() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        // Get the parametro
        restParametroMockMvc.perform(get("/api/parametros/{id}", parametro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parametro.getId().intValue()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.toString()));
    }

    @Test
    @Transactional
    public void getAllParametrosByClaveIsEqualToSomething() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        // Get all the parametroList where clave equals to DEFAULT_CLAVE
        defaultParametroShouldBeFound("clave.equals=" + DEFAULT_CLAVE);

        // Get all the parametroList where clave equals to UPDATED_CLAVE
        defaultParametroShouldNotBeFound("clave.equals=" + UPDATED_CLAVE);
    }

    @Test
    @Transactional
    public void getAllParametrosByClaveIsInShouldWork() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        // Get all the parametroList where clave in DEFAULT_CLAVE or UPDATED_CLAVE
        defaultParametroShouldBeFound("clave.in=" + DEFAULT_CLAVE + "," + UPDATED_CLAVE);

        // Get all the parametroList where clave equals to UPDATED_CLAVE
        defaultParametroShouldNotBeFound("clave.in=" + UPDATED_CLAVE);
    }

    @Test
    @Transactional
    public void getAllParametrosByClaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        // Get all the parametroList where clave is not null
        defaultParametroShouldBeFound("clave.specified=true");

        // Get all the parametroList where clave is null
        defaultParametroShouldNotBeFound("clave.specified=false");
    }

    @Test
    @Transactional
    public void getAllParametrosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        // Get all the parametroList where valor equals to DEFAULT_VALOR
        defaultParametroShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the parametroList where valor equals to UPDATED_VALOR
        defaultParametroShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllParametrosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        // Get all the parametroList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultParametroShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the parametroList where valor equals to UPDATED_VALOR
        defaultParametroShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllParametrosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        // Get all the parametroList where valor is not null
        defaultParametroShouldBeFound("valor.specified=true");

        // Get all the parametroList where valor is null
        defaultParametroShouldNotBeFound("valor.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultParametroShouldBeFound(String filter) throws Exception {
        restParametroMockMvc.perform(get("/api/parametros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametro.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultParametroShouldNotBeFound(String filter) throws Exception {
        restParametroMockMvc.perform(get("/api/parametros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingParametro() throws Exception {
        // Get the parametro
        restParametroMockMvc.perform(get("/api/parametros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParametro() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        int databaseSizeBeforeUpdate = parametroRepository.findAll().size();

        // Update the parametro
        Parametro updatedParametro = parametroRepository.findById(parametro.getId()).get();
        // Disconnect from session so that the updates on updatedParametro are not directly saved in db
        em.detach(updatedParametro);
        updatedParametro
            .clave(UPDATED_CLAVE)
            .valor(UPDATED_VALOR);
        ParametroDTO parametroDTO = parametroMapper.toDto(updatedParametro);

        restParametroMockMvc.perform(put("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isOk());

        // Validate the Parametro in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeUpdate);
        Parametro testParametro = parametroList.get(parametroList.size() - 1);
        assertThat(testParametro.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testParametro.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingParametro() throws Exception {
        int databaseSizeBeforeUpdate = parametroRepository.findAll().size();

        // Create the Parametro
        ParametroDTO parametroDTO = parametroMapper.toDto(parametro);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParametroMockMvc.perform(put("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Parametro in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParametro() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        int databaseSizeBeforeDelete = parametroRepository.findAll().size();

        // Get the parametro
        restParametroMockMvc.perform(delete("/api/parametros/{id}", parametro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parametro.class);
        Parametro parametro1 = new Parametro();
        parametro1.setId(1L);
        Parametro parametro2 = new Parametro();
        parametro2.setId(parametro1.getId());
        assertThat(parametro1).isEqualTo(parametro2);
        parametro2.setId(2L);
        assertThat(parametro1).isNotEqualTo(parametro2);
        parametro1.setId(null);
        assertThat(parametro1).isNotEqualTo(parametro2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParametroDTO.class);
        ParametroDTO parametroDTO1 = new ParametroDTO();
        parametroDTO1.setId(1L);
        ParametroDTO parametroDTO2 = new ParametroDTO();
        assertThat(parametroDTO1).isNotEqualTo(parametroDTO2);
        parametroDTO2.setId(parametroDTO1.getId());
        assertThat(parametroDTO1).isEqualTo(parametroDTO2);
        parametroDTO2.setId(2L);
        assertThat(parametroDTO1).isNotEqualTo(parametroDTO2);
        parametroDTO1.setId(null);
        assertThat(parametroDTO1).isNotEqualTo(parametroDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(parametroMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(parametroMapper.fromId(null)).isNull();
    }
}
