package com.itd.myapp.web.rest;

import com.itd.myapp.ConfigureMgmtSampleApplicationApp;
import com.itd.myapp.domain.OSVersion;
import com.itd.myapp.repository.OSVersionRepository;
import com.itd.myapp.service.OSVersionService;
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

import com.itd.myapp.domain.enumeration.OS;
/**
 * Integration tests for the {@link OSVersionResource} REST controller.
 */
@SpringBootTest(classes = ConfigureMgmtSampleApplicationApp.class)
public class OSVersionResourceIT {

    private static final OS DEFAULT_OS_NAME = OS.REDHATLINUX;
    private static final OS UPDATED_OS_NAME = OS.WIN;

    private static final String DEFAULT_OS_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_OS_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_OS_VER_DESC = "AAAAAAAAAA";
    private static final String UPDATED_OS_VER_DESC = "BBBBBBBBBB";

    @Autowired
    private OSVersionRepository oSVersionRepository;

    @Autowired
    private OSVersionService oSVersionService;

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

    private MockMvc restOSVersionMockMvc;

    private OSVersion oSVersion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OSVersionResource oSVersionResource = new OSVersionResource(oSVersionService);
        this.restOSVersionMockMvc = MockMvcBuilders.standaloneSetup(oSVersionResource)
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
    public static OSVersion createEntity(EntityManager em) {
        OSVersion oSVersion = new OSVersion()
            .osName(DEFAULT_OS_NAME)
            .osVersion(DEFAULT_OS_VERSION)
            .osVerDesc(DEFAULT_OS_VER_DESC);
        return oSVersion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OSVersion createUpdatedEntity(EntityManager em) {
        OSVersion oSVersion = new OSVersion()
            .osName(UPDATED_OS_NAME)
            .osVersion(UPDATED_OS_VERSION)
            .osVerDesc(UPDATED_OS_VER_DESC);
        return oSVersion;
    }

    @BeforeEach
    public void initTest() {
        oSVersion = createEntity(em);
    }

    @Test
    @Transactional
    public void createOSVersion() throws Exception {
        int databaseSizeBeforeCreate = oSVersionRepository.findAll().size();

        // Create the OSVersion
        restOSVersionMockMvc.perform(post("/api/os-versions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oSVersion)))
            .andExpect(status().isCreated());

        // Validate the OSVersion in the database
        List<OSVersion> oSVersionList = oSVersionRepository.findAll();
        assertThat(oSVersionList).hasSize(databaseSizeBeforeCreate + 1);
        OSVersion testOSVersion = oSVersionList.get(oSVersionList.size() - 1);
        assertThat(testOSVersion.getOsName()).isEqualTo(DEFAULT_OS_NAME);
        assertThat(testOSVersion.getOsVersion()).isEqualTo(DEFAULT_OS_VERSION);
        assertThat(testOSVersion.getOsVerDesc()).isEqualTo(DEFAULT_OS_VER_DESC);
    }

    @Test
    @Transactional
    public void createOSVersionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = oSVersionRepository.findAll().size();

        // Create the OSVersion with an existing ID
        oSVersion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOSVersionMockMvc.perform(post("/api/os-versions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oSVersion)))
            .andExpect(status().isBadRequest());

        // Validate the OSVersion in the database
        List<OSVersion> oSVersionList = oSVersionRepository.findAll();
        assertThat(oSVersionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOsNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = oSVersionRepository.findAll().size();
        // set the field null
        oSVersion.setOsName(null);

        // Create the OSVersion, which fails.

        restOSVersionMockMvc.perform(post("/api/os-versions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oSVersion)))
            .andExpect(status().isBadRequest());

        List<OSVersion> oSVersionList = oSVersionRepository.findAll();
        assertThat(oSVersionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOsVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = oSVersionRepository.findAll().size();
        // set the field null
        oSVersion.setOsVersion(null);

        // Create the OSVersion, which fails.

        restOSVersionMockMvc.perform(post("/api/os-versions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oSVersion)))
            .andExpect(status().isBadRequest());

        List<OSVersion> oSVersionList = oSVersionRepository.findAll();
        assertThat(oSVersionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOSVersions() throws Exception {
        // Initialize the database
        oSVersionRepository.saveAndFlush(oSVersion);

        // Get all the oSVersionList
        restOSVersionMockMvc.perform(get("/api/os-versions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oSVersion.getId().intValue())))
            .andExpect(jsonPath("$.[*].osName").value(hasItem(DEFAULT_OS_NAME.toString())))
            .andExpect(jsonPath("$.[*].osVersion").value(hasItem(DEFAULT_OS_VERSION)))
            .andExpect(jsonPath("$.[*].osVerDesc").value(hasItem(DEFAULT_OS_VER_DESC)));
    }
    
    @Test
    @Transactional
    public void getOSVersion() throws Exception {
        // Initialize the database
        oSVersionRepository.saveAndFlush(oSVersion);

        // Get the oSVersion
        restOSVersionMockMvc.perform(get("/api/os-versions/{id}", oSVersion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(oSVersion.getId().intValue()))
            .andExpect(jsonPath("$.osName").value(DEFAULT_OS_NAME.toString()))
            .andExpect(jsonPath("$.osVersion").value(DEFAULT_OS_VERSION))
            .andExpect(jsonPath("$.osVerDesc").value(DEFAULT_OS_VER_DESC));
    }

    @Test
    @Transactional
    public void getNonExistingOSVersion() throws Exception {
        // Get the oSVersion
        restOSVersionMockMvc.perform(get("/api/os-versions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOSVersion() throws Exception {
        // Initialize the database
        oSVersionService.save(oSVersion);

        int databaseSizeBeforeUpdate = oSVersionRepository.findAll().size();

        // Update the oSVersion
        OSVersion updatedOSVersion = oSVersionRepository.findById(oSVersion.getId()).get();
        // Disconnect from session so that the updates on updatedOSVersion are not directly saved in db
        em.detach(updatedOSVersion);
        updatedOSVersion
            .osName(UPDATED_OS_NAME)
            .osVersion(UPDATED_OS_VERSION)
            .osVerDesc(UPDATED_OS_VER_DESC);

        restOSVersionMockMvc.perform(put("/api/os-versions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOSVersion)))
            .andExpect(status().isOk());

        // Validate the OSVersion in the database
        List<OSVersion> oSVersionList = oSVersionRepository.findAll();
        assertThat(oSVersionList).hasSize(databaseSizeBeforeUpdate);
        OSVersion testOSVersion = oSVersionList.get(oSVersionList.size() - 1);
        assertThat(testOSVersion.getOsName()).isEqualTo(UPDATED_OS_NAME);
        assertThat(testOSVersion.getOsVersion()).isEqualTo(UPDATED_OS_VERSION);
        assertThat(testOSVersion.getOsVerDesc()).isEqualTo(UPDATED_OS_VER_DESC);
    }

    @Test
    @Transactional
    public void updateNonExistingOSVersion() throws Exception {
        int databaseSizeBeforeUpdate = oSVersionRepository.findAll().size();

        // Create the OSVersion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOSVersionMockMvc.perform(put("/api/os-versions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oSVersion)))
            .andExpect(status().isBadRequest());

        // Validate the OSVersion in the database
        List<OSVersion> oSVersionList = oSVersionRepository.findAll();
        assertThat(oSVersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOSVersion() throws Exception {
        // Initialize the database
        oSVersionService.save(oSVersion);

        int databaseSizeBeforeDelete = oSVersionRepository.findAll().size();

        // Delete the oSVersion
        restOSVersionMockMvc.perform(delete("/api/os-versions/{id}", oSVersion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OSVersion> oSVersionList = oSVersionRepository.findAll();
        assertThat(oSVersionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
