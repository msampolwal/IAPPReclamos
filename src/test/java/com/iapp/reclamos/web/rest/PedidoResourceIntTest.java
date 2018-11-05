package com.iapp.reclamos.web.rest;

import static com.iapp.reclamos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.persistence.EntityManager;

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

import com.iapp.reclamos.IappReclamosApp;
import com.iapp.reclamos.domain.Pedido;
import com.iapp.reclamos.domain.Reclamo;
import com.iapp.reclamos.domain.Tienda;
import com.iapp.reclamos.repository.PedidoRepository;
import com.iapp.reclamos.service.PedidoQueryService;
import com.iapp.reclamos.service.PedidoService;
import com.iapp.reclamos.service.dto.PedidoDTO;
import com.iapp.reclamos.service.mapper.PedidoMapper;
import com.iapp.reclamos.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the PedidoResource REST controller.
 *
 * @see PedidoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IappReclamosApp.class)
public class PedidoResourceIntTest {

    private static final LocalDate DEFAULT_FECHA_ENTREGA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ENTREGA = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_MONTO_COMPRA = 1F;
    private static final Float UPDATED_MONTO_COMPRA = 2F;

    private static final String DEFAULT_DNI_CLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_DNI_CLIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_CLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_CLIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL_CLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_MAIL_CLIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_ID_PRODUCTO = "AAAAAAAAAA";
    private static final String UPDATED_ID_PRODUCTO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION_PRODUCTO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_PRODUCTO = "BBBBBBBBBB";

    @Autowired
    private PedidoRepository pedidoRepository;


    @Autowired
    private PedidoMapper pedidoMapper;
    

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoQueryService pedidoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPedidoMockMvc;

    private Pedido pedido;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PedidoResource pedidoResource = new PedidoResource(pedidoService, pedidoQueryService);
        this.restPedidoMockMvc = MockMvcBuilders.standaloneSetup(pedidoResource)
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
    public static Pedido createEntity(EntityManager em) {
        Pedido pedido = new Pedido()
            .fechaEntrega(DEFAULT_FECHA_ENTREGA)
            .montoCompra(DEFAULT_MONTO_COMPRA)
            .dniCliente(DEFAULT_DNI_CLIENTE)
            .nombreCliente(DEFAULT_NOMBRE_CLIENTE)
            .mailCliente(DEFAULT_MAIL_CLIENTE)
            .idProducto(DEFAULT_ID_PRODUCTO)
            .descripcionProducto(DEFAULT_DESCRIPCION_PRODUCTO);
        return pedido;
    }

    @Before
    public void initTest() {
        pedido = createEntity(em);
    }

    @Test
    @Transactional
    public void createPedido() throws Exception {
        int databaseSizeBeforeCreate = pedidoRepository.findAll().size();

        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);
        restPedidoMockMvc.perform(post("/api/pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isCreated());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeCreate + 1);
        Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
        assertThat(testPedido.getFechaEntrega()).isEqualTo(DEFAULT_FECHA_ENTREGA);
        assertThat(testPedido.getMontoCompra()).isEqualTo(DEFAULT_MONTO_COMPRA);
        assertThat(testPedido.getDniCliente()).isEqualTo(DEFAULT_DNI_CLIENTE);
        assertThat(testPedido.getNombreCliente()).isEqualTo(DEFAULT_NOMBRE_CLIENTE);
        assertThat(testPedido.getMailCliente()).isEqualTo(DEFAULT_MAIL_CLIENTE);
        assertThat(testPedido.getIdProducto()).isEqualTo(DEFAULT_ID_PRODUCTO);
        assertThat(testPedido.getDescripcionProducto()).isEqualTo(DEFAULT_DESCRIPCION_PRODUCTO);
    }

    @Test
    @Transactional
    public void createPedidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pedidoRepository.findAll().size();

        // Create the Pedido with an existing ID
        pedido.setId(1L);
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPedidoMockMvc.perform(post("/api/pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPedidos() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList
        restPedidoMockMvc.perform(get("/api/pedidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pedido.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaEntrega").value(hasItem(DEFAULT_FECHA_ENTREGA.toString())))
            .andExpect(jsonPath("$.[*].montoCompra").value(hasItem(DEFAULT_MONTO_COMPRA.doubleValue())))
            .andExpect(jsonPath("$.[*].dniCliente").value(hasItem(DEFAULT_DNI_CLIENTE.toString())))
            .andExpect(jsonPath("$.[*].nombreCliente").value(hasItem(DEFAULT_NOMBRE_CLIENTE.toString())))
            .andExpect(jsonPath("$.[*].mailCliente").value(hasItem(DEFAULT_MAIL_CLIENTE.toString())))
            .andExpect(jsonPath("$.[*].idProducto").value(hasItem(DEFAULT_ID_PRODUCTO.toString())))
            .andExpect(jsonPath("$.[*].descripcionProducto").value(hasItem(DEFAULT_DESCRIPCION_PRODUCTO.toString())));
    }
    

    @Test
    @Transactional
    public void getPedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get the pedido
        restPedidoMockMvc.perform(get("/api/pedidos/{id}", pedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pedido.getId().intValue()))
            .andExpect(jsonPath("$.fechaEntrega").value(DEFAULT_FECHA_ENTREGA.toString()))
            .andExpect(jsonPath("$.montoCompra").value(DEFAULT_MONTO_COMPRA.doubleValue()))
            .andExpect(jsonPath("$.dniCliente").value(DEFAULT_DNI_CLIENTE.toString()))
            .andExpect(jsonPath("$.nombreCliente").value(DEFAULT_NOMBRE_CLIENTE.toString()))
            .andExpect(jsonPath("$.mailCliente").value(DEFAULT_MAIL_CLIENTE.toString()))
            .andExpect(jsonPath("$.idProducto").value(DEFAULT_ID_PRODUCTO.toString()))
            .andExpect(jsonPath("$.descripcionProducto").value(DEFAULT_DESCRIPCION_PRODUCTO.toString()));
    }

    @Test
    @Transactional
    public void getAllPedidosByFechaEntregaIsEqualToSomething() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where fechaEntrega equals to DEFAULT_FECHA_ENTREGA
        defaultPedidoShouldBeFound("fechaEntrega.equals=" + DEFAULT_FECHA_ENTREGA);

        // Get all the pedidoList where fechaEntrega equals to UPDATED_FECHA_ENTREGA
        defaultPedidoShouldNotBeFound("fechaEntrega.equals=" + UPDATED_FECHA_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllPedidosByFechaEntregaIsInShouldWork() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where fechaEntrega in DEFAULT_FECHA_ENTREGA or UPDATED_FECHA_ENTREGA
        defaultPedidoShouldBeFound("fechaEntrega.in=" + DEFAULT_FECHA_ENTREGA + "," + UPDATED_FECHA_ENTREGA);

        // Get all the pedidoList where fechaEntrega equals to UPDATED_FECHA_ENTREGA
        defaultPedidoShouldNotBeFound("fechaEntrega.in=" + UPDATED_FECHA_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllPedidosByFechaEntregaIsNullOrNotNull() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where fechaEntrega is not null
        defaultPedidoShouldBeFound("fechaEntrega.specified=true");

        // Get all the pedidoList where fechaEntrega is null
        defaultPedidoShouldNotBeFound("fechaEntrega.specified=false");
    }

    @Test
    @Transactional
    public void getAllPedidosByFechaEntregaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where fechaEntrega greater than or equals to DEFAULT_FECHA_ENTREGA
        defaultPedidoShouldBeFound("fechaEntrega.greaterOrEqualThan=" + DEFAULT_FECHA_ENTREGA);

        // Get all the pedidoList where fechaEntrega greater than or equals to UPDATED_FECHA_ENTREGA
        defaultPedidoShouldNotBeFound("fechaEntrega.greaterOrEqualThan=" + UPDATED_FECHA_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllPedidosByFechaEntregaIsLessThanSomething() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where fechaEntrega less than or equals to DEFAULT_FECHA_ENTREGA
        defaultPedidoShouldNotBeFound("fechaEntrega.lessThan=" + DEFAULT_FECHA_ENTREGA);

        // Get all the pedidoList where fechaEntrega less than or equals to UPDATED_FECHA_ENTREGA
        defaultPedidoShouldBeFound("fechaEntrega.lessThan=" + UPDATED_FECHA_ENTREGA);
    }


    @Test
    @Transactional
    public void getAllPedidosByMontoCompraIsEqualToSomething() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where montoCompra equals to DEFAULT_MONTO_COMPRA
        defaultPedidoShouldBeFound("montoCompra.equals=" + DEFAULT_MONTO_COMPRA);

        // Get all the pedidoList where montoCompra equals to UPDATED_MONTO_COMPRA
        defaultPedidoShouldNotBeFound("montoCompra.equals=" + UPDATED_MONTO_COMPRA);
    }

    @Test
    @Transactional
    public void getAllPedidosByMontoCompraIsInShouldWork() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where montoCompra in DEFAULT_MONTO_COMPRA or UPDATED_MONTO_COMPRA
        defaultPedidoShouldBeFound("montoCompra.in=" + DEFAULT_MONTO_COMPRA + "," + UPDATED_MONTO_COMPRA);

        // Get all the pedidoList where montoCompra equals to UPDATED_MONTO_COMPRA
        defaultPedidoShouldNotBeFound("montoCompra.in=" + UPDATED_MONTO_COMPRA);
    }

    @Test
    @Transactional
    public void getAllPedidosByMontoCompraIsNullOrNotNull() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where montoCompra is not null
        defaultPedidoShouldBeFound("montoCompra.specified=true");

        // Get all the pedidoList where montoCompra is null
        defaultPedidoShouldNotBeFound("montoCompra.specified=false");
    }

    @Test
    @Transactional
    public void getAllPedidosByDniClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where dniCliente equals to DEFAULT_DNI_CLIENTE
        defaultPedidoShouldBeFound("dniCliente.equals=" + DEFAULT_DNI_CLIENTE);

        // Get all the pedidoList where dniCliente equals to UPDATED_DNI_CLIENTE
        defaultPedidoShouldNotBeFound("dniCliente.equals=" + UPDATED_DNI_CLIENTE);
    }

    @Test
    @Transactional
    public void getAllPedidosByDniClienteIsInShouldWork() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where dniCliente in DEFAULT_DNI_CLIENTE or UPDATED_DNI_CLIENTE
        defaultPedidoShouldBeFound("dniCliente.in=" + DEFAULT_DNI_CLIENTE + "," + UPDATED_DNI_CLIENTE);

        // Get all the pedidoList where dniCliente equals to UPDATED_DNI_CLIENTE
        defaultPedidoShouldNotBeFound("dniCliente.in=" + UPDATED_DNI_CLIENTE);
    }

    @Test
    @Transactional
    public void getAllPedidosByDniClienteIsNullOrNotNull() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where dniCliente is not null
        defaultPedidoShouldBeFound("dniCliente.specified=true");

        // Get all the pedidoList where dniCliente is null
        defaultPedidoShouldNotBeFound("dniCliente.specified=false");
    }

    @Test
    @Transactional
    public void getAllPedidosByNombreClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where nombreCliente equals to DEFAULT_NOMBRE_CLIENTE
        defaultPedidoShouldBeFound("nombreCliente.equals=" + DEFAULT_NOMBRE_CLIENTE);

        // Get all the pedidoList where nombreCliente equals to UPDATED_NOMBRE_CLIENTE
        defaultPedidoShouldNotBeFound("nombreCliente.equals=" + UPDATED_NOMBRE_CLIENTE);
    }

    @Test
    @Transactional
    public void getAllPedidosByNombreClienteIsInShouldWork() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where nombreCliente in DEFAULT_NOMBRE_CLIENTE or UPDATED_NOMBRE_CLIENTE
        defaultPedidoShouldBeFound("nombreCliente.in=" + DEFAULT_NOMBRE_CLIENTE + "," + UPDATED_NOMBRE_CLIENTE);

        // Get all the pedidoList where nombreCliente equals to UPDATED_NOMBRE_CLIENTE
        defaultPedidoShouldNotBeFound("nombreCliente.in=" + UPDATED_NOMBRE_CLIENTE);
    }

    @Test
    @Transactional
    public void getAllPedidosByNombreClienteIsNullOrNotNull() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where nombreCliente is not null
        defaultPedidoShouldBeFound("nombreCliente.specified=true");

        // Get all the pedidoList where nombreCliente is null
        defaultPedidoShouldNotBeFound("nombreCliente.specified=false");
    }

    @Test
    @Transactional
    public void getAllPedidosByMailClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where mailCliente equals to DEFAULT_MAIL_CLIENTE
        defaultPedidoShouldBeFound("mailCliente.equals=" + DEFAULT_MAIL_CLIENTE);

        // Get all the pedidoList where mailCliente equals to UPDATED_MAIL_CLIENTE
        defaultPedidoShouldNotBeFound("mailCliente.equals=" + UPDATED_MAIL_CLIENTE);
    }

    @Test
    @Transactional
    public void getAllPedidosByMailClienteIsInShouldWork() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where mailCliente in DEFAULT_MAIL_CLIENTE or UPDATED_MAIL_CLIENTE
        defaultPedidoShouldBeFound("mailCliente.in=" + DEFAULT_MAIL_CLIENTE + "," + UPDATED_MAIL_CLIENTE);

        // Get all the pedidoList where mailCliente equals to UPDATED_MAIL_CLIENTE
        defaultPedidoShouldNotBeFound("mailCliente.in=" + UPDATED_MAIL_CLIENTE);
    }

    @Test
    @Transactional
    public void getAllPedidosByMailClienteIsNullOrNotNull() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where mailCliente is not null
        defaultPedidoShouldBeFound("mailCliente.specified=true");

        // Get all the pedidoList where mailCliente is null
        defaultPedidoShouldNotBeFound("mailCliente.specified=false");
    }

    @Test
    @Transactional
    public void getAllPedidosByIdProductoIsEqualToSomething() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where idProducto equals to DEFAULT_ID_PRODUCTO
        defaultPedidoShouldBeFound("idProducto.equals=" + DEFAULT_ID_PRODUCTO);

        // Get all the pedidoList where idProducto equals to UPDATED_ID_PRODUCTO
        defaultPedidoShouldNotBeFound("idProducto.equals=" + UPDATED_ID_PRODUCTO);
    }

    @Test
    @Transactional
    public void getAllPedidosByIdProductoIsInShouldWork() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where idProducto in DEFAULT_ID_PRODUCTO or UPDATED_ID_PRODUCTO
        defaultPedidoShouldBeFound("idProducto.in=" + DEFAULT_ID_PRODUCTO + "," + UPDATED_ID_PRODUCTO);

        // Get all the pedidoList where idProducto equals to UPDATED_ID_PRODUCTO
        defaultPedidoShouldNotBeFound("idProducto.in=" + UPDATED_ID_PRODUCTO);
    }

    @Test
    @Transactional
    public void getAllPedidosByIdProductoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where idProducto is not null
        defaultPedidoShouldBeFound("idProducto.specified=true");

        // Get all the pedidoList where idProducto is null
        defaultPedidoShouldNotBeFound("idProducto.specified=false");
    }

    @Test
    @Transactional
    public void getAllPedidosByDescripcionProductoIsEqualToSomething() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where descripcionProducto equals to DEFAULT_DESCRIPCION_PRODUCTO
        defaultPedidoShouldBeFound("descripcionProducto.equals=" + DEFAULT_DESCRIPCION_PRODUCTO);

        // Get all the pedidoList where descripcionProducto equals to UPDATED_DESCRIPCION_PRODUCTO
        defaultPedidoShouldNotBeFound("descripcionProducto.equals=" + UPDATED_DESCRIPCION_PRODUCTO);
    }

    @Test
    @Transactional
    public void getAllPedidosByDescripcionProductoIsInShouldWork() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where descripcionProducto in DEFAULT_DESCRIPCION_PRODUCTO or UPDATED_DESCRIPCION_PRODUCTO
        defaultPedidoShouldBeFound("descripcionProducto.in=" + DEFAULT_DESCRIPCION_PRODUCTO + "," + UPDATED_DESCRIPCION_PRODUCTO);

        // Get all the pedidoList where descripcionProducto equals to UPDATED_DESCRIPCION_PRODUCTO
        defaultPedidoShouldNotBeFound("descripcionProducto.in=" + UPDATED_DESCRIPCION_PRODUCTO);
    }

    @Test
    @Transactional
    public void getAllPedidosByDescripcionProductoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList where descripcionProducto is not null
        defaultPedidoShouldBeFound("descripcionProducto.specified=true");

        // Get all the pedidoList where descripcionProducto is null
        defaultPedidoShouldNotBeFound("descripcionProducto.specified=false");
    }

    @Test
    @Transactional
    public void getAllPedidosByTiendaIsEqualToSomething() throws Exception {
        // Initialize the database
        Tienda tienda = TiendaResourceIntTest.createEntity(em);
        em.persist(tienda);
        em.flush();
        pedido.setTienda(tienda);
        pedidoRepository.saveAndFlush(pedido);
        Long tiendaId = tienda.getId();

        // Get all the pedidoList where tienda equals to tiendaId
        defaultPedidoShouldBeFound("tiendaId.equals=" + tiendaId);

        // Get all the pedidoList where tienda equals to tiendaId + 1
        defaultPedidoShouldNotBeFound("tiendaId.equals=" + (tiendaId + 1));
    }


    @Test
    @Transactional
    public void getAllPedidosByReclamoIsEqualToSomething() throws Exception {
        // Initialize the database
        Reclamo reclamo = ReclamoResourceIntTest.createEntity(em);
        em.persist(reclamo);
        em.flush();
        pedido.setReclamo(reclamo);
        reclamo.setPedido(pedido);
        pedidoRepository.saveAndFlush(pedido);
        Long reclamoId = reclamo.getId();

        // Get all the pedidoList where reclamo equals to reclamoId
        defaultPedidoShouldBeFound("reclamoId.equals=" + reclamoId);

        // Get all the pedidoList where reclamo equals to reclamoId + 1
        defaultPedidoShouldNotBeFound("reclamoId.equals=" + (reclamoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPedidoShouldBeFound(String filter) throws Exception {
        restPedidoMockMvc.perform(get("/api/pedidos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pedido.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaEntrega").value(hasItem(DEFAULT_FECHA_ENTREGA.toString())))
            .andExpect(jsonPath("$.[*].montoCompra").value(hasItem(DEFAULT_MONTO_COMPRA.doubleValue())))
            .andExpect(jsonPath("$.[*].dniCliente").value(hasItem(DEFAULT_DNI_CLIENTE.toString())))
            .andExpect(jsonPath("$.[*].nombreCliente").value(hasItem(DEFAULT_NOMBRE_CLIENTE.toString())))
            .andExpect(jsonPath("$.[*].mailCliente").value(hasItem(DEFAULT_MAIL_CLIENTE.toString())))
            .andExpect(jsonPath("$.[*].idProducto").value(hasItem(DEFAULT_ID_PRODUCTO.toString())))
            .andExpect(jsonPath("$.[*].descripcionProducto").value(hasItem(DEFAULT_DESCRIPCION_PRODUCTO.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPedidoShouldNotBeFound(String filter) throws Exception {
        restPedidoMockMvc.perform(get("/api/pedidos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingPedido() throws Exception {
        // Get the pedido
        restPedidoMockMvc.perform(get("/api/pedidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();

        // Update the pedido
        Pedido updatedPedido = pedidoRepository.findById(pedido.getId()).get();
        // Disconnect from session so that the updates on updatedPedido are not directly saved in db
        em.detach(updatedPedido);
        updatedPedido
            .fechaEntrega(UPDATED_FECHA_ENTREGA)
            .montoCompra(UPDATED_MONTO_COMPRA)
            .dniCliente(UPDATED_DNI_CLIENTE)
            .nombreCliente(UPDATED_NOMBRE_CLIENTE)
            .mailCliente(UPDATED_MAIL_CLIENTE)
            .idProducto(UPDATED_ID_PRODUCTO)
            .descripcionProducto(UPDATED_DESCRIPCION_PRODUCTO);
        PedidoDTO pedidoDTO = pedidoMapper.toDto(updatedPedido);

        restPedidoMockMvc.perform(put("/api/pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isOk());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
        Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
        assertThat(testPedido.getFechaEntrega()).isEqualTo(UPDATED_FECHA_ENTREGA);
        assertThat(testPedido.getMontoCompra()).isEqualTo(UPDATED_MONTO_COMPRA);
        assertThat(testPedido.getDniCliente()).isEqualTo(UPDATED_DNI_CLIENTE);
        assertThat(testPedido.getNombreCliente()).isEqualTo(UPDATED_NOMBRE_CLIENTE);
        assertThat(testPedido.getMailCliente()).isEqualTo(UPDATED_MAIL_CLIENTE);
        assertThat(testPedido.getIdProducto()).isEqualTo(UPDATED_ID_PRODUCTO);
        assertThat(testPedido.getDescripcionProducto()).isEqualTo(UPDATED_DESCRIPCION_PRODUCTO);
    }

    @Test
    @Transactional
    public void updateNonExistingPedido() throws Exception {
        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();

        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPedidoMockMvc.perform(put("/api/pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        int databaseSizeBeforeDelete = pedidoRepository.findAll().size();

        // Get the pedido
        restPedidoMockMvc.perform(delete("/api/pedidos/{id}", pedido.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pedido.class);
        Pedido pedido1 = new Pedido();
        pedido1.setId(1L);
        Pedido pedido2 = new Pedido();
        pedido2.setId(pedido1.getId());
        assertThat(pedido1).isEqualTo(pedido2);
        pedido2.setId(2L);
        assertThat(pedido1).isNotEqualTo(pedido2);
        pedido1.setId(null);
        assertThat(pedido1).isNotEqualTo(pedido2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PedidoDTO.class);
        PedidoDTO pedidoDTO1 = new PedidoDTO();
        pedidoDTO1.setId(1L);
        PedidoDTO pedidoDTO2 = new PedidoDTO();
        assertThat(pedidoDTO1).isNotEqualTo(pedidoDTO2);
        pedidoDTO2.setId(pedidoDTO1.getId());
        assertThat(pedidoDTO1).isEqualTo(pedidoDTO2);
        pedidoDTO2.setId(2L);
        assertThat(pedidoDTO1).isNotEqualTo(pedidoDTO2);
        pedidoDTO1.setId(null);
        assertThat(pedidoDTO1).isNotEqualTo(pedidoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pedidoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pedidoMapper.fromId(null)).isNull();
    }
    
    @Test
    @Transactional
    public void testMigracionCSV() {
        pedidoService.migrarPedidosDesdeCSV(null);
    }
}
