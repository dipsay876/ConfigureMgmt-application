package com.itd.myapp.web.rest;

import com.itd.myapp.ConfigureMgmtSampleApplicationApp;
import com.itd.myapp.domain.ConfigurationFile;
import com.itd.myapp.repository.ConfigurationFileRepository;
import com.itd.myapp.service.ConfigurationFileService;
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

import com.itd.myapp.domain.enumeration.EnvironmentMaster;
import com.itd.myapp.domain.enumeration.Layer;
/**
 * Integration tests for the {@link ConfigurationFileResource} REST controller.
 */
@SpringBootTest(classes = ConfigureMgmtSampleApplicationApp.class)
public class ConfigurationFileResourceIT {

    private static final EnvironmentMaster DEFAULT_ENVIRONMENT_NAME = EnvironmentMaster.DEV;
    private static final EnvironmentMaster UPDATED_ENVIRONMENT_NAME = EnvironmentMaster.UAT;

    private static final Layer DEFAULT_LAYER_NAME = Layer.WEB;
    private static final Layer UPDATED_LAYER_NAME = Layer.MS;

    private static final String DEFAULT_COMPONENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPONENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HOST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HOST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONF_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONF_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONF_FILE_INSTALL_PATH = "AAAAAAAAAA";
    private static final String UPDATED_CONF_FILE_INSTALL_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_CONF_FILE_UPLOAD_PATH = "AAAAAAAAAA";
    private static final String UPDATED_CONF_FILE_UPLOAD_PATH = "BBBBBBBBBB";

    @Autowired
    private ConfigurationFileRepository configurationFileRepository;

    @Autowired
    private ConfigurationFileService configurationFileService;

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

    private MockMvc restConfigurationFileMockMvc;

    private ConfigurationFile configurationFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConfigurationFileResource configurationFileResource = new ConfigurationFileResource(configurationFileService);
        this.restConfigurationFileMockMvc = MockMvcBuilders.standaloneSetup(configurationFileResource)
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
    public static ConfigurationFile createEntity(EntityManager em) {
        ConfigurationFile configurationFile = new ConfigurationFile()
            .environmentName(DEFAULT_ENVIRONMENT_NAME)
            .layerName(DEFAULT_LAYER_NAME)
            .componentName(DEFAULT_COMPONENT_NAME)
            .hostName(DEFAULT_HOST_NAME)
            .confFileName(DEFAULT_CONF_FILE_NAME)
            .confFileInstallPath(DEFAULT_CONF_FILE_INSTALL_PATH)
            .confFileUploadPath(DEFAULT_CONF_FILE_UPLOAD_PATH);
        return configurationFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConfigurationFile createUpdatedEntity(EntityManager em) {
        ConfigurationFile configurationFile = new ConfigurationFile()
            .environmentName(UPDATED_ENVIRONMENT_NAME)
            .layerName(UPDATED_LAYER_NAME)
            .componentName(UPDATED_COMPONENT_NAME)
            .hostName(UPDATED_HOST_NAME)
            .confFileName(UPDATED_CONF_FILE_NAME)
            .confFileInstallPath(UPDATED_CONF_FILE_INSTALL_PATH)
            .confFileUploadPath(UPDATED_CONF_FILE_UPLOAD_PATH);
        return configurationFile;
    }

    @BeforeEach
    public void initTest() {
        configurationFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createConfigurationFile() throws Exception {
        int databaseSizeBeforeCreate = configurationFileRepository.findAll().size();

        // Create the ConfigurationFile
        restConfigurationFileMockMvc.perform(post("/api/configuration-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurationFile)))
            .andExpect(status().isCreated());

        // Validate the ConfigurationFile in the database
        List<ConfigurationFile> configurationFileList = configurationFileRepository.findAll();
        assertThat(configurationFileList).hasSize(databaseSizeBeforeCreate + 1);
        ConfigurationFile testConfigurationFile = configurationFileList.get(configurationFileList.size() - 1);
        assertThat(testConfigurationFile.getEnvironmentName()).isEqualTo(DEFAULT_ENVIRONMENT_NAME);
        assertThat(testConfigurationFile.getLayerName()).isEqualTo(DEFAULT_LAYER_NAME);
        assertThat(testConfigurationFile.getComponentName()).isEqualTo(DEFAULT_COMPONENT_NAME);
        assertThat(testConfigurationFile.getHostName()).isEqualTo(DEFAULT_HOST_NAME);
        assertThat(testConfigurationFile.getConfFileName()).isEqualTo(DEFAULT_CONF_FILE_NAME);
        assertThat(testConfigurationFile.getConfFileInstallPath()).isEqualTo(DEFAULT_CONF_FILE_INSTALL_PATH);
        assertThat(testConfigurationFile.getConfFileUploadPath()).isEqualTo(DEFAULT_CONF_FILE_UPLOAD_PATH);
    }

    @Test
    @Transactional
    public void createConfigurationFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = configurationFileRepository.findAll().size();

        // Create the ConfigurationFile with an existing ID
        configurationFile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfigurationFileMockMvc.perform(post("/api/configuration-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurationFile)))
            .andExpect(status().isBadRequest());

        // Validate the ConfigurationFile in the database
        List<ConfigurationFile> configurationFileList = configurationFileRepository.findAll();
        assertThat(configurationFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEnvironmentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = configurationFileRepository.findAll().size();
        // set the field null
        configurationFile.setEnvironmentName(null);

        // Create the ConfigurationFile, which fails.

        restConfigurationFileMockMvc.perform(post("/api/configuration-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurationFile)))
            .andExpect(status().isBadRequest());

        List<ConfigurationFile> configurationFileList = configurationFileRepository.findAll();
        assertThat(configurationFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLayerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = configurationFileRepository.findAll().size();
        // set the field null
        configurationFile.setLayerName(null);

        // Create the ConfigurationFile, which fails.

        restConfigurationFileMockMvc.perform(post("/api/configuration-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurationFile)))
            .andExpect(status().isBadRequest());

        List<ConfigurationFile> configurationFileList = configurationFileRepository.findAll();
        assertThat(configurationFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkComponentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = configurationFileRepository.findAll().size();
        // set the field null
        configurationFile.setComponentName(null);

        // Create the ConfigurationFile, which fails.

        restConfigurationFileMockMvc.perform(post("/api/configuration-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurationFile)))
            .andExpect(status().isBadRequest());

        List<ConfigurationFile> configurationFileList = configurationFileRepository.findAll();
        assertThat(configurationFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHostNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = configurationFileRepository.findAll().size();
        // set the field null
        configurationFile.setHostName(null);

        // Create the ConfigurationFile, which fails.

        restConfigurationFileMockMvc.perform(post("/api/configuration-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurationFile)))
            .andExpect(status().isBadRequest());

        List<ConfigurationFile> configurationFileList = configurationFileRepository.findAll();
        assertThat(configurationFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConfFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = configurationFileRepository.findAll().size();
        // set the field null
        configurationFile.setConfFileName(null);

        // Create the ConfigurationFile, which fails.

        restConfigurationFileMockMvc.perform(post("/api/configuration-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurationFile)))
            .andExpect(status().isBadRequest());

        List<ConfigurationFile> configurationFileList = configurationFileRepository.findAll();
        assertThat(configurationFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConfigurationFiles() throws Exception {
        // Initialize the database
        configurationFileRepository.saveAndFlush(configurationFile);

        // Get all the configurationFileList
        restConfigurationFileMockMvc.perform(get("/api/configuration-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configurationFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].environmentName").value(hasItem(DEFAULT_ENVIRONMENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].layerName").value(hasItem(DEFAULT_LAYER_NAME.toString())))
            .andExpect(jsonPath("$.[*].componentName").value(hasItem(DEFAULT_COMPONENT_NAME)))
            .andExpect(jsonPath("$.[*].hostName").value(hasItem(DEFAULT_HOST_NAME)))
            .andExpect(jsonPath("$.[*].confFileName").value(hasItem(DEFAULT_CONF_FILE_NAME)))
            .andExpect(jsonPath("$.[*].confFileInstallPath").value(hasItem(DEFAULT_CONF_FILE_INSTALL_PATH)))
            .andExpect(jsonPath("$.[*].confFileUploadPath").value(hasItem(DEFAULT_CONF_FILE_UPLOAD_PATH)));
    }
    
    @Test
    @Transactional
    public void getConfigurationFile() throws Exception {
        // Initialize the database
        configurationFileRepository.saveAndFlush(configurationFile);

        // Get the configurationFile
        restConfigurationFileMockMvc.perform(get("/api/configuration-files/{id}", configurationFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(configurationFile.getId().intValue()))
            .andExpect(jsonPath("$.environmentName").value(DEFAULT_ENVIRONMENT_NAME.toString()))
            .andExpect(jsonPath("$.layerName").value(DEFAULT_LAYER_NAME.toString()))
            .andExpect(jsonPath("$.componentName").value(DEFAULT_COMPONENT_NAME))
            .andExpect(jsonPath("$.hostName").value(DEFAULT_HOST_NAME))
            .andExpect(jsonPath("$.confFileName").value(DEFAULT_CONF_FILE_NAME))
            .andExpect(jsonPath("$.confFileInstallPath").value(DEFAULT_CONF_FILE_INSTALL_PATH))
            .andExpect(jsonPath("$.confFileUploadPath").value(DEFAULT_CONF_FILE_UPLOAD_PATH));
    }

    @Test
    @Transactional
    public void getNonExistingConfigurationFile() throws Exception {
        // Get the configurationFile
        restConfigurationFileMockMvc.perform(get("/api/configuration-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConfigurationFile() throws Exception {
        // Initialize the database
        configurationFileService.save(configurationFile);

        int databaseSizeBeforeUpdate = configurationFileRepository.findAll().size();

        // Update the configurationFile
        ConfigurationFile updatedConfigurationFile = configurationFileRepository.findById(configurationFile.getId()).get();
        // Disconnect from session so that the updates on updatedConfigurationFile are not directly saved in db
        em.detach(updatedConfigurationFile);
        updatedConfigurationFile
            .environmentName(UPDATED_ENVIRONMENT_NAME)
            .layerName(UPDATED_LAYER_NAME)
            .componentName(UPDATED_COMPONENT_NAME)
            .hostName(UPDATED_HOST_NAME)
            .confFileName(UPDATED_CONF_FILE_NAME)
            .confFileInstallPath(UPDATED_CONF_FILE_INSTALL_PATH)
            .confFileUploadPath(UPDATED_CONF_FILE_UPLOAD_PATH);

        restConfigurationFileMockMvc.perform(put("/api/configuration-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConfigurationFile)))
            .andExpect(status().isOk());

        // Validate the ConfigurationFile in the database
        List<ConfigurationFile> configurationFileList = configurationFileRepository.findAll();
        assertThat(configurationFileList).hasSize(databaseSizeBeforeUpdate);
        ConfigurationFile testConfigurationFile = configurationFileList.get(configurationFileList.size() - 1);
        assertThat(testConfigurationFile.getEnvironmentName()).isEqualTo(UPDATED_ENVIRONMENT_NAME);
        assertThat(testConfigurationFile.getLayerName()).isEqualTo(UPDATED_LAYER_NAME);
        assertThat(testConfigurationFile.getComponentName()).isEqualTo(UPDATED_COMPONENT_NAME);
        assertThat(testConfigurationFile.getHostName()).isEqualTo(UPDATED_HOST_NAME);
        assertThat(testConfigurationFile.getConfFileName()).isEqualTo(UPDATED_CONF_FILE_NAME);
        assertThat(testConfigurationFile.getConfFileInstallPath()).isEqualTo(UPDATED_CONF_FILE_INSTALL_PATH);
        assertThat(testConfigurationFile.getConfFileUploadPath()).isEqualTo(UPDATED_CONF_FILE_UPLOAD_PATH);
    }

    @Test
    @Transactional
    public void updateNonExistingConfigurationFile() throws Exception {
        int databaseSizeBeforeUpdate = configurationFileRepository.findAll().size();

        // Create the ConfigurationFile

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigurationFileMockMvc.perform(put("/api/configuration-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurationFile)))
            .andExpect(status().isBadRequest());

        // Validate the ConfigurationFile in the database
        List<ConfigurationFile> configurationFileList = configurationFileRepository.findAll();
        assertThat(configurationFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConfigurationFile() throws Exception {
        // Initialize the database
        configurationFileService.save(configurationFile);

        int databaseSizeBeforeDelete = configurationFileRepository.findAll().size();

        // Delete the configurationFile
        restConfigurationFileMockMvc.perform(delete("/api/configuration-files/{id}", configurationFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConfigurationFile> configurationFileList = configurationFileRepository.findAll();
        assertThat(configurationFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
