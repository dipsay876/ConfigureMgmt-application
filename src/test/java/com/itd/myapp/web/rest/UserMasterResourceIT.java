package com.itd.myapp.web.rest;

import com.itd.myapp.ConfigureMgmtSampleApplicationApp;
import com.itd.myapp.domain.UserMaster;
import com.itd.myapp.repository.UserMasterRepository;
import com.itd.myapp.service.UserMasterService;
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

import com.itd.myapp.domain.enumeration.Role;
/**
 * Integration tests for the {@link UserMasterResource} REST controller.
 */
@SpringBootTest(classes = ConfigureMgmtSampleApplicationApp.class)
public class UserMasterResourceIT {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_USER_PASSWORD = "BBBBBBBBBB";

    private static final Role DEFAULT_USER_ROLE = Role.Developer;
    private static final Role UPDATED_USER_ROLE = Role.Tester;

    @Autowired
    private UserMasterRepository userMasterRepository;

    @Autowired
    private UserMasterService userMasterService;

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

    private MockMvc restUserMasterMockMvc;

    private UserMaster userMaster;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserMasterResource userMasterResource = new UserMasterResource(userMasterService);
        this.restUserMasterMockMvc = MockMvcBuilders.standaloneSetup(userMasterResource)
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
    public static UserMaster createEntity(EntityManager em) {
        UserMaster userMaster = new UserMaster()
            .userName(DEFAULT_USER_NAME)
            .userPassword(DEFAULT_USER_PASSWORD)
            .userRole(DEFAULT_USER_ROLE);
        return userMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserMaster createUpdatedEntity(EntityManager em) {
        UserMaster userMaster = new UserMaster()
            .userName(UPDATED_USER_NAME)
            .userPassword(UPDATED_USER_PASSWORD)
            .userRole(UPDATED_USER_ROLE);
        return userMaster;
    }

    @BeforeEach
    public void initTest() {
        userMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserMaster() throws Exception {
        int databaseSizeBeforeCreate = userMasterRepository.findAll().size();

        // Create the UserMaster
        restUserMasterMockMvc.perform(post("/api/user-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMaster)))
            .andExpect(status().isCreated());

        // Validate the UserMaster in the database
        List<UserMaster> userMasterList = userMasterRepository.findAll();
        assertThat(userMasterList).hasSize(databaseSizeBeforeCreate + 1);
        UserMaster testUserMaster = userMasterList.get(userMasterList.size() - 1);
        assertThat(testUserMaster.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testUserMaster.getUserPassword()).isEqualTo(DEFAULT_USER_PASSWORD);
        assertThat(testUserMaster.getUserRole()).isEqualTo(DEFAULT_USER_ROLE);
    }

    @Test
    @Transactional
    public void createUserMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userMasterRepository.findAll().size();

        // Create the UserMaster with an existing ID
        userMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserMasterMockMvc.perform(post("/api/user-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMaster)))
            .andExpect(status().isBadRequest());

        // Validate the UserMaster in the database
        List<UserMaster> userMasterList = userMasterRepository.findAll();
        assertThat(userMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userMasterRepository.findAll().size();
        // set the field null
        userMaster.setUserName(null);

        // Create the UserMaster, which fails.

        restUserMasterMockMvc.perform(post("/api/user-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMaster)))
            .andExpect(status().isBadRequest());

        List<UserMaster> userMasterList = userMasterRepository.findAll();
        assertThat(userMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = userMasterRepository.findAll().size();
        // set the field null
        userMaster.setUserPassword(null);

        // Create the UserMaster, which fails.

        restUserMasterMockMvc.perform(post("/api/user-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMaster)))
            .andExpect(status().isBadRequest());

        List<UserMaster> userMasterList = userMasterRepository.findAll();
        assertThat(userMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserMasters() throws Exception {
        // Initialize the database
        userMasterRepository.saveAndFlush(userMaster);

        // Get all the userMasterList
        restUserMasterMockMvc.perform(get("/api/user-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].userPassword").value(hasItem(DEFAULT_USER_PASSWORD)))
            .andExpect(jsonPath("$.[*].userRole").value(hasItem(DEFAULT_USER_ROLE.toString())));
    }
    
    @Test
    @Transactional
    public void getUserMaster() throws Exception {
        // Initialize the database
        userMasterRepository.saveAndFlush(userMaster);

        // Get the userMaster
        restUserMasterMockMvc.perform(get("/api/user-masters/{id}", userMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userMaster.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.userPassword").value(DEFAULT_USER_PASSWORD))
            .andExpect(jsonPath("$.userRole").value(DEFAULT_USER_ROLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserMaster() throws Exception {
        // Get the userMaster
        restUserMasterMockMvc.perform(get("/api/user-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserMaster() throws Exception {
        // Initialize the database
        userMasterService.save(userMaster);

        int databaseSizeBeforeUpdate = userMasterRepository.findAll().size();

        // Update the userMaster
        UserMaster updatedUserMaster = userMasterRepository.findById(userMaster.getId()).get();
        // Disconnect from session so that the updates on updatedUserMaster are not directly saved in db
        em.detach(updatedUserMaster);
        updatedUserMaster
            .userName(UPDATED_USER_NAME)
            .userPassword(UPDATED_USER_PASSWORD)
            .userRole(UPDATED_USER_ROLE);

        restUserMasterMockMvc.perform(put("/api/user-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserMaster)))
            .andExpect(status().isOk());

        // Validate the UserMaster in the database
        List<UserMaster> userMasterList = userMasterRepository.findAll();
        assertThat(userMasterList).hasSize(databaseSizeBeforeUpdate);
        UserMaster testUserMaster = userMasterList.get(userMasterList.size() - 1);
        assertThat(testUserMaster.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testUserMaster.getUserPassword()).isEqualTo(UPDATED_USER_PASSWORD);
        assertThat(testUserMaster.getUserRole()).isEqualTo(UPDATED_USER_ROLE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserMaster() throws Exception {
        int databaseSizeBeforeUpdate = userMasterRepository.findAll().size();

        // Create the UserMaster

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserMasterMockMvc.perform(put("/api/user-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMaster)))
            .andExpect(status().isBadRequest());

        // Validate the UserMaster in the database
        List<UserMaster> userMasterList = userMasterRepository.findAll();
        assertThat(userMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserMaster() throws Exception {
        // Initialize the database
        userMasterService.save(userMaster);

        int databaseSizeBeforeDelete = userMasterRepository.findAll().size();

        // Delete the userMaster
        restUserMasterMockMvc.perform(delete("/api/user-masters/{id}", userMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserMaster> userMasterList = userMasterRepository.findAll();
        assertThat(userMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
