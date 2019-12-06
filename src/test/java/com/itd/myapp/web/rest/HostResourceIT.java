package com.itd.myapp.web.rest;

import com.itd.myapp.ConfigureMgmtSampleApplicationApp;
import com.itd.myapp.domain.Host;
import com.itd.myapp.repository.HostRepository;
import com.itd.myapp.service.HostService;
import com.itd.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.itd.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.itd.myapp.domain.enumeration.Layer;
import com.itd.myapp.domain.enumeration.OS;
/**
 * Integration tests for the {@link HostResource} REST controller.
 */
@SpringBootTest(classes = ConfigureMgmtSampleApplicationApp.class)
public class HostResourceIT {

    private static final Layer DEFAULT_LAYER_NAME = Layer.WEB;
    private static final Layer UPDATED_LAYER_NAME = Layer.MS;

    private static final OS DEFAULT_OS_NAME = OS.REDHATLINUX;
    private static final OS UPDATED_OS_NAME = OS.WIN;

    private static final String DEFAULT_OS_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_OS_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_HOST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HOST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HOST_IP = "AAAAAAAAAA";
    private static final String UPDATED_HOST_IP = "BBBBBBBBBB";

    private static final Long DEFAULT_HOST_CPU = 1L;
    private static final Long UPDATED_HOST_CPU = 2L;

    private static final Long DEFAULT_HOST_MEMORY_MB = 1L;
    private static final Long UPDATED_HOST_MEMORY_MB = 2L;

    private static final Long DEFAULT_HOST_HDDGB = 1L;
    private static final Long UPDATED_HOST_HDDGB = 2L;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private HostService hostService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restHostMockMvc;

    private Host host;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HostResource hostResource = new HostResource(hostService);
        this.restHostMockMvc = MockMvcBuilders.standaloneSetup(hostResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Host createEntity(EntityManager em) {
        Host host = new Host()
            .layerName(DEFAULT_LAYER_NAME)
            .osName(DEFAULT_OS_NAME)
            .osVersion(DEFAULT_OS_VERSION)
            .hostName(DEFAULT_HOST_NAME)
            .hostIP(DEFAULT_HOST_IP)
            .hostCPU(DEFAULT_HOST_CPU)
            .hostMemoryMB(DEFAULT_HOST_MEMORY_MB)
            .hostHDDGB(DEFAULT_HOST_HDDGB);
        return host;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Host createUpdatedEntity(EntityManager em) {
        Host host = new Host()
            .layerName(UPDATED_LAYER_NAME)
            .osName(UPDATED_OS_NAME)
            .osVersion(UPDATED_OS_VERSION)
            .hostName(UPDATED_HOST_NAME)
            .hostIP(UPDATED_HOST_IP)
            .hostCPU(UPDATED_HOST_CPU)
            .hostMemoryMB(UPDATED_HOST_MEMORY_MB)
            .hostHDDGB(UPDATED_HOST_HDDGB);
        return host;
    }

    @BeforeEach
    public void initTest() {
        host = createEntity(em);
    }

    @Test
    @Transactional
    public void createHost() throws Exception {
        int databaseSizeBeforeCreate = hostRepository.findAll().size();

        // Create the Host
        restHostMockMvc.perform(post("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(host)))
            .andExpect(status().isCreated());

        // Validate the Host in the database
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeCreate + 1);
        Host testHost = hostList.get(hostList.size() - 1);
        assertThat(testHost.getLayerName()).isEqualTo(DEFAULT_LAYER_NAME);
        assertThat(testHost.getOsName()).isEqualTo(DEFAULT_OS_NAME);
        assertThat(testHost.getOsVersion()).isEqualTo(DEFAULT_OS_VERSION);
        assertThat(testHost.getHostName()).isEqualTo(DEFAULT_HOST_NAME);
        assertThat(testHost.getHostIP()).isEqualTo(DEFAULT_HOST_IP);
        assertThat(testHost.getHostCPU()).isEqualTo(DEFAULT_HOST_CPU);
        assertThat(testHost.getHostMemoryMB()).isEqualTo(DEFAULT_HOST_MEMORY_MB);
        assertThat(testHost.getHostHDDGB()).isEqualTo(DEFAULT_HOST_HDDGB);
    }

    @Test
    @Transactional
    public void createHostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hostRepository.findAll().size();

        // Create the Host with an existing ID
        host.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHostMockMvc.perform(post("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(host)))
            .andExpect(status().isBadRequest());

        // Validate the Host in the database
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLayerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hostRepository.findAll().size();
        // set the field null
        host.setLayerName(null);

        // Create the Host, which fails.

        restHostMockMvc.perform(post("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(host)))
            .andExpect(status().isBadRequest());

        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOsNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hostRepository.findAll().size();
        // set the field null
        host.setOsName(null);

        // Create the Host, which fails.

        restHostMockMvc.perform(post("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(host)))
            .andExpect(status().isBadRequest());

        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOsVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = hostRepository.findAll().size();
        // set the field null
        host.setOsVersion(null);

        // Create the Host, which fails.

        restHostMockMvc.perform(post("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(host)))
            .andExpect(status().isBadRequest());

        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHostNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hostRepository.findAll().size();
        // set the field null
        host.setHostName(null);

        // Create the Host, which fails.

        restHostMockMvc.perform(post("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(host)))
            .andExpect(status().isBadRequest());

        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHosts() throws Exception {
        // Initialize the database
        hostRepository.saveAndFlush(host);

        // Get all the hostList
        restHostMockMvc.perform(get("/api/hosts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(host.getId().intValue())))
            .andExpect(jsonPath("$.[*].layerName").value(hasItem(DEFAULT_LAYER_NAME.toString())))
            .andExpect(jsonPath("$.[*].osName").value(hasItem(DEFAULT_OS_NAME.toString())))
            .andExpect(jsonPath("$.[*].osVersion").value(hasItem(DEFAULT_OS_VERSION)))
            .andExpect(jsonPath("$.[*].hostName").value(hasItem(DEFAULT_HOST_NAME)))
            .andExpect(jsonPath("$.[*].hostIP").value(hasItem(DEFAULT_HOST_IP)))
            .andExpect(jsonPath("$.[*].hostCPU").value(hasItem(DEFAULT_HOST_CPU.intValue())))
            .andExpect(jsonPath("$.[*].hostMemoryMB").value(hasItem(DEFAULT_HOST_MEMORY_MB.intValue())))
            .andExpect(jsonPath("$.[*].hostHDDGB").value(hasItem(DEFAULT_HOST_HDDGB.intValue())));
    }
    
    @Test
    @Transactional
    public void getHost() throws Exception {
        // Initialize the database
        hostRepository.saveAndFlush(host);

        // Get the host
        restHostMockMvc.perform(get("/api/hosts/{id}", host.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(host.getId().intValue()))
            .andExpect(jsonPath("$.layerName").value(DEFAULT_LAYER_NAME.toString()))
            .andExpect(jsonPath("$.osName").value(DEFAULT_OS_NAME.toString()))
            .andExpect(jsonPath("$.osVersion").value(DEFAULT_OS_VERSION))
            .andExpect(jsonPath("$.hostName").value(DEFAULT_HOST_NAME))
            .andExpect(jsonPath("$.hostIP").value(DEFAULT_HOST_IP))
            .andExpect(jsonPath("$.hostCPU").value(DEFAULT_HOST_CPU.intValue()))
            .andExpect(jsonPath("$.hostMemoryMB").value(DEFAULT_HOST_MEMORY_MB.intValue()))
            .andExpect(jsonPath("$.hostHDDGB").value(DEFAULT_HOST_HDDGB.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHost() throws Exception {
        // Get the host
        restHostMockMvc.perform(get("/api/hosts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHost() throws Exception {
        // Initialize the database
        hostService.save(host);

        int databaseSizeBeforeUpdate = hostRepository.findAll().size();

        // Update the host
        Host updatedHost = hostRepository.findById(host.getId()).get();
        // Disconnect from session so that the updates on updatedHost are not directly saved in db
        em.detach(updatedHost);
        updatedHost
            .layerName(UPDATED_LAYER_NAME)
            .osName(UPDATED_OS_NAME)
            .osVersion(UPDATED_OS_VERSION)
            .hostName(UPDATED_HOST_NAME)
            .hostIP(UPDATED_HOST_IP)
            .hostCPU(UPDATED_HOST_CPU)
            .hostMemoryMB(UPDATED_HOST_MEMORY_MB)
            .hostHDDGB(UPDATED_HOST_HDDGB);

        restHostMockMvc.perform(put("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHost)))
            .andExpect(status().isOk());

        // Validate the Host in the database
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeUpdate);
        Host testHost = hostList.get(hostList.size() - 1);
        assertThat(testHost.getLayerName()).isEqualTo(UPDATED_LAYER_NAME);
        assertThat(testHost.getOsName()).isEqualTo(UPDATED_OS_NAME);
        assertThat(testHost.getOsVersion()).isEqualTo(UPDATED_OS_VERSION);
        assertThat(testHost.getHostName()).isEqualTo(UPDATED_HOST_NAME);
        assertThat(testHost.getHostIP()).isEqualTo(UPDATED_HOST_IP);
        assertThat(testHost.getHostCPU()).isEqualTo(UPDATED_HOST_CPU);
        assertThat(testHost.getHostMemoryMB()).isEqualTo(UPDATED_HOST_MEMORY_MB);
        assertThat(testHost.getHostHDDGB()).isEqualTo(UPDATED_HOST_HDDGB);
    }

    @Test
    @Transactional
    public void updateNonExistingHost() throws Exception {
        int databaseSizeBeforeUpdate = hostRepository.findAll().size();

        // Create the Host

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHostMockMvc.perform(put("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(host)))
            .andExpect(status().isBadRequest());

        // Validate the Host in the database
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHost() throws Exception {
        // Initialize the database
        hostService.save(host);

        int databaseSizeBeforeDelete = hostRepository.findAll().size();

        // Delete the host
        restHostMockMvc.perform(delete("/api/hosts/{id}", host.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
