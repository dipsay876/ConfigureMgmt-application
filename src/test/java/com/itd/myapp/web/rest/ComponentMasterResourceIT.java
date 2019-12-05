package com.itd.myapp.web.rest;

import com.itd.myapp.ConfigureMgmtSampleApplicationApp;
import com.itd.myapp.domain.ComponentMaster;
import com.itd.myapp.repository.ComponentMasterRepository;
import com.itd.myapp.service.ComponentMasterService;
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

/**
 * Integration tests for the {@link ComponentMasterResource} REST controller.
 */
@SpringBootTest(classes = ConfigureMgmtSampleApplicationApp.class)
public class ComponentMasterResourceIT {

    private static final String DEFAULT_COMPONENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPONENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPONENT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_COMPONENT_DESC = "BBBBBBBBBB";

    @Autowired
    private ComponentMasterRepository componentMasterRepository;

    @Autowired
    private ComponentMasterService componentMasterService;

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

    private MockMvc restComponentMasterMockMvc;

    private ComponentMaster componentMaster;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComponentMasterResource componentMasterResource = new ComponentMasterResource(componentMasterService);
        this.restComponentMasterMockMvc = MockMvcBuilders.standaloneSetup(componentMasterResource)
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
    public static ComponentMaster createEntity(EntityManager em) {
        ComponentMaster componentMaster = new ComponentMaster()
            .componentName(DEFAULT_COMPONENT_NAME)
            .componentDesc(DEFAULT_COMPONENT_DESC);
        return componentMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComponentMaster createUpdatedEntity(EntityManager em) {
        ComponentMaster componentMaster = new ComponentMaster()
            .componentName(UPDATED_COMPONENT_NAME)
            .componentDesc(UPDATED_COMPONENT_DESC);
        return componentMaster;
    }

    @BeforeEach
    public void initTest() {
        componentMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createComponentMaster() throws Exception {
        int databaseSizeBeforeCreate = componentMasterRepository.findAll().size();

        // Create the ComponentMaster
        restComponentMasterMockMvc.perform(post("/api/component-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(componentMaster)))
            .andExpect(status().isCreated());

        // Validate the ComponentMaster in the database
        List<ComponentMaster> componentMasterList = componentMasterRepository.findAll();
        assertThat(componentMasterList).hasSize(databaseSizeBeforeCreate + 1);
        ComponentMaster testComponentMaster = componentMasterList.get(componentMasterList.size() - 1);
        assertThat(testComponentMaster.getComponentName()).isEqualTo(DEFAULT_COMPONENT_NAME);
        assertThat(testComponentMaster.getComponentDesc()).isEqualTo(DEFAULT_COMPONENT_DESC);
    }

    @Test
    @Transactional
    public void createComponentMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = componentMasterRepository.findAll().size();

        // Create the ComponentMaster with an existing ID
        componentMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComponentMasterMockMvc.perform(post("/api/component-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(componentMaster)))
            .andExpect(status().isBadRequest());

        // Validate the ComponentMaster in the database
        List<ComponentMaster> componentMasterList = componentMasterRepository.findAll();
        assertThat(componentMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkComponentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = componentMasterRepository.findAll().size();
        // set the field null
        componentMaster.setComponentName(null);

        // Create the ComponentMaster, which fails.

        restComponentMasterMockMvc.perform(post("/api/component-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(componentMaster)))
            .andExpect(status().isBadRequest());

        List<ComponentMaster> componentMasterList = componentMasterRepository.findAll();
        assertThat(componentMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComponentMasters() throws Exception {
        // Initialize the database
        componentMasterRepository.saveAndFlush(componentMaster);

        // Get all the componentMasterList
        restComponentMasterMockMvc.perform(get("/api/component-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(componentMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].componentName").value(hasItem(DEFAULT_COMPONENT_NAME)))
            .andExpect(jsonPath("$.[*].componentDesc").value(hasItem(DEFAULT_COMPONENT_DESC)));
    }
    
    @Test
    @Transactional
    public void getComponentMaster() throws Exception {
        // Initialize the database
        componentMasterRepository.saveAndFlush(componentMaster);

        // Get the componentMaster
        restComponentMasterMockMvc.perform(get("/api/component-masters/{id}", componentMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(componentMaster.getId().intValue()))
            .andExpect(jsonPath("$.componentName").value(DEFAULT_COMPONENT_NAME))
            .andExpect(jsonPath("$.componentDesc").value(DEFAULT_COMPONENT_DESC));
    }

    @Test
    @Transactional
    public void getNonExistingComponentMaster() throws Exception {
        // Get the componentMaster
        restComponentMasterMockMvc.perform(get("/api/component-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComponentMaster() throws Exception {
        // Initialize the database
        componentMasterService.save(componentMaster);

        int databaseSizeBeforeUpdate = componentMasterRepository.findAll().size();

        // Update the componentMaster
        ComponentMaster updatedComponentMaster = componentMasterRepository.findById(componentMaster.getId()).get();
        // Disconnect from session so that the updates on updatedComponentMaster are not directly saved in db
        em.detach(updatedComponentMaster);
        updatedComponentMaster
            .componentName(UPDATED_COMPONENT_NAME)
            .componentDesc(UPDATED_COMPONENT_DESC);

        restComponentMasterMockMvc.perform(put("/api/component-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComponentMaster)))
            .andExpect(status().isOk());

        // Validate the ComponentMaster in the database
        List<ComponentMaster> componentMasterList = componentMasterRepository.findAll();
        assertThat(componentMasterList).hasSize(databaseSizeBeforeUpdate);
        ComponentMaster testComponentMaster = componentMasterList.get(componentMasterList.size() - 1);
        assertThat(testComponentMaster.getComponentName()).isEqualTo(UPDATED_COMPONENT_NAME);
        assertThat(testComponentMaster.getComponentDesc()).isEqualTo(UPDATED_COMPONENT_DESC);
    }

    @Test
    @Transactional
    public void updateNonExistingComponentMaster() throws Exception {
        int databaseSizeBeforeUpdate = componentMasterRepository.findAll().size();

        // Create the ComponentMaster

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComponentMasterMockMvc.perform(put("/api/component-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(componentMaster)))
            .andExpect(status().isBadRequest());

        // Validate the ComponentMaster in the database
        List<ComponentMaster> componentMasterList = componentMasterRepository.findAll();
        assertThat(componentMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComponentMaster() throws Exception {
        // Initialize the database
        componentMasterService.save(componentMaster);

        int databaseSizeBeforeDelete = componentMasterRepository.findAll().size();

        // Delete the componentMaster
        restComponentMasterMockMvc.perform(delete("/api/component-masters/{id}", componentMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ComponentMaster> componentMasterList = componentMasterRepository.findAll();
        assertThat(componentMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
