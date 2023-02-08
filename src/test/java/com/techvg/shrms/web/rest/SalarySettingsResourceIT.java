package com.techvg.shrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.shrms.IntegrationTest;
import com.techvg.shrms.domain.SalarySettings;
import com.techvg.shrms.repository.SalarySettingsRepository;
import com.techvg.shrms.service.criteria.SalarySettingsCriteria;
import com.techvg.shrms.service.dto.SalarySettingsDTO;
import com.techvg.shrms.service.mapper.SalarySettingsMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SalarySettingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SalarySettingsResourceIT {

    private static final Double DEFAULT_DA = 1D;
    private static final Double UPDATED_DA = 2D;
    private static final Double SMALLER_DA = 1D - 1D;

    private static final Double DEFAULT_HRA = 1D;
    private static final Double UPDATED_HRA = 2D;
    private static final Double SMALLER_HRA = 1D - 1D;

    private static final Double DEFAULT_EMPLOYEESHARE = 1D;
    private static final Double UPDATED_EMPLOYEESHARE = 2D;
    private static final Double SMALLER_EMPLOYEESHARE = 1D - 1D;

    private static final Double DEFAULT_COMPANYSHARE = 1D;
    private static final Double UPDATED_COMPANYSHARE = 2D;
    private static final Double SMALLER_COMPANYSHARE = 1D - 1D;

    private static final Instant DEFAULT_SALARY_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SALARY_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SALARY_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SALARY_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final String ENTITY_API_URL = "/api/salary-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SalarySettingsRepository salarySettingsRepository;

    @Autowired
    private SalarySettingsMapper salarySettingsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalarySettingsMockMvc;

    private SalarySettings salarySettings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalarySettings createEntity(EntityManager em) {
        SalarySettings salarySettings = new SalarySettings()
            .da(DEFAULT_DA)
            .hra(DEFAULT_HRA)
            .employeeshare(DEFAULT_EMPLOYEESHARE)
            .companyshare(DEFAULT_COMPANYSHARE)
            .salaryFrom(DEFAULT_SALARY_FROM)
            .salaryTo(DEFAULT_SALARY_TO)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .deleted(DEFAULT_DELETED);
        return salarySettings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalarySettings createUpdatedEntity(EntityManager em) {
        SalarySettings salarySettings = new SalarySettings()
            .da(UPDATED_DA)
            .hra(UPDATED_HRA)
            .employeeshare(UPDATED_EMPLOYEESHARE)
            .companyshare(UPDATED_COMPANYSHARE)
            .salaryFrom(UPDATED_SALARY_FROM)
            .salaryTo(UPDATED_SALARY_TO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);
        return salarySettings;
    }

    @BeforeEach
    public void initTest() {
        salarySettings = createEntity(em);
    }

    @Test
    @Transactional
    void createSalarySettings() throws Exception {
        int databaseSizeBeforeCreate = salarySettingsRepository.findAll().size();
        // Create the SalarySettings
        SalarySettingsDTO salarySettingsDTO = salarySettingsMapper.toDto(salarySettings);
        restSalarySettingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salarySettingsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeCreate + 1);
        SalarySettings testSalarySettings = salarySettingsList.get(salarySettingsList.size() - 1);
        assertThat(testSalarySettings.getDa()).isEqualTo(DEFAULT_DA);
        assertThat(testSalarySettings.getHra()).isEqualTo(DEFAULT_HRA);
        assertThat(testSalarySettings.getEmployeeshare()).isEqualTo(DEFAULT_EMPLOYEESHARE);
        assertThat(testSalarySettings.getCompanyshare()).isEqualTo(DEFAULT_COMPANYSHARE);
        assertThat(testSalarySettings.getSalaryFrom()).isEqualTo(DEFAULT_SALARY_FROM);
        assertThat(testSalarySettings.getSalaryTo()).isEqualTo(DEFAULT_SALARY_TO);
        assertThat(testSalarySettings.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSalarySettings.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSalarySettings.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSalarySettings.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSalarySettings.getDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    void createSalarySettingsWithExistingId() throws Exception {
        // Create the SalarySettings with an existing ID
        salarySettings.setId(1L);
        SalarySettingsDTO salarySettingsDTO = salarySettingsMapper.toDto(salarySettings);

        int databaseSizeBeforeCreate = salarySettingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalarySettingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salarySettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSalarySettings() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList
        restSalarySettingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salarySettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].da").value(hasItem(DEFAULT_DA.doubleValue())))
            .andExpect(jsonPath("$.[*].hra").value(hasItem(DEFAULT_HRA.doubleValue())))
            .andExpect(jsonPath("$.[*].employeeshare").value(hasItem(DEFAULT_EMPLOYEESHARE.doubleValue())))
            .andExpect(jsonPath("$.[*].companyshare").value(hasItem(DEFAULT_COMPANYSHARE.doubleValue())))
            .andExpect(jsonPath("$.[*].salaryFrom").value(hasItem(DEFAULT_SALARY_FROM.toString())))
            .andExpect(jsonPath("$.[*].salaryTo").value(hasItem(DEFAULT_SALARY_TO.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getSalarySettings() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get the salarySettings
        restSalarySettingsMockMvc
            .perform(get(ENTITY_API_URL_ID, salarySettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salarySettings.getId().intValue()))
            .andExpect(jsonPath("$.da").value(DEFAULT_DA.doubleValue()))
            .andExpect(jsonPath("$.hra").value(DEFAULT_HRA.doubleValue()))
            .andExpect(jsonPath("$.employeeshare").value(DEFAULT_EMPLOYEESHARE.doubleValue()))
            .andExpect(jsonPath("$.companyshare").value(DEFAULT_COMPANYSHARE.doubleValue()))
            .andExpect(jsonPath("$.salaryFrom").value(DEFAULT_SALARY_FROM.toString()))
            .andExpect(jsonPath("$.salaryTo").value(DEFAULT_SALARY_TO.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getSalarySettingsByIdFiltering() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        Long id = salarySettings.getId();

        defaultSalarySettingsShouldBeFound("id.equals=" + id);
        defaultSalarySettingsShouldNotBeFound("id.notEquals=" + id);

        defaultSalarySettingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSalarySettingsShouldNotBeFound("id.greaterThan=" + id);

        defaultSalarySettingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSalarySettingsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByDaIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where da equals to DEFAULT_DA
        defaultSalarySettingsShouldBeFound("da.equals=" + DEFAULT_DA);

        // Get all the salarySettingsList where da equals to UPDATED_DA
        defaultSalarySettingsShouldNotBeFound("da.equals=" + UPDATED_DA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByDaIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where da in DEFAULT_DA or UPDATED_DA
        defaultSalarySettingsShouldBeFound("da.in=" + DEFAULT_DA + "," + UPDATED_DA);

        // Get all the salarySettingsList where da equals to UPDATED_DA
        defaultSalarySettingsShouldNotBeFound("da.in=" + UPDATED_DA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByDaIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where da is not null
        defaultSalarySettingsShouldBeFound("da.specified=true");

        // Get all the salarySettingsList where da is null
        defaultSalarySettingsShouldNotBeFound("da.specified=false");
    }

    @Test
    @Transactional
    void getAllSalarySettingsByDaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where da is greater than or equal to DEFAULT_DA
        defaultSalarySettingsShouldBeFound("da.greaterThanOrEqual=" + DEFAULT_DA);

        // Get all the salarySettingsList where da is greater than or equal to UPDATED_DA
        defaultSalarySettingsShouldNotBeFound("da.greaterThanOrEqual=" + UPDATED_DA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByDaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where da is less than or equal to DEFAULT_DA
        defaultSalarySettingsShouldBeFound("da.lessThanOrEqual=" + DEFAULT_DA);

        // Get all the salarySettingsList where da is less than or equal to SMALLER_DA
        defaultSalarySettingsShouldNotBeFound("da.lessThanOrEqual=" + SMALLER_DA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByDaIsLessThanSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where da is less than DEFAULT_DA
        defaultSalarySettingsShouldNotBeFound("da.lessThan=" + DEFAULT_DA);

        // Get all the salarySettingsList where da is less than UPDATED_DA
        defaultSalarySettingsShouldBeFound("da.lessThan=" + UPDATED_DA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByDaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where da is greater than DEFAULT_DA
        defaultSalarySettingsShouldNotBeFound("da.greaterThan=" + DEFAULT_DA);

        // Get all the salarySettingsList where da is greater than SMALLER_DA
        defaultSalarySettingsShouldBeFound("da.greaterThan=" + SMALLER_DA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByHraIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where hra equals to DEFAULT_HRA
        defaultSalarySettingsShouldBeFound("hra.equals=" + DEFAULT_HRA);

        // Get all the salarySettingsList where hra equals to UPDATED_HRA
        defaultSalarySettingsShouldNotBeFound("hra.equals=" + UPDATED_HRA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByHraIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where hra in DEFAULT_HRA or UPDATED_HRA
        defaultSalarySettingsShouldBeFound("hra.in=" + DEFAULT_HRA + "," + UPDATED_HRA);

        // Get all the salarySettingsList where hra equals to UPDATED_HRA
        defaultSalarySettingsShouldNotBeFound("hra.in=" + UPDATED_HRA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByHraIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where hra is not null
        defaultSalarySettingsShouldBeFound("hra.specified=true");

        // Get all the salarySettingsList where hra is null
        defaultSalarySettingsShouldNotBeFound("hra.specified=false");
    }

    @Test
    @Transactional
    void getAllSalarySettingsByHraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where hra is greater than or equal to DEFAULT_HRA
        defaultSalarySettingsShouldBeFound("hra.greaterThanOrEqual=" + DEFAULT_HRA);

        // Get all the salarySettingsList where hra is greater than or equal to UPDATED_HRA
        defaultSalarySettingsShouldNotBeFound("hra.greaterThanOrEqual=" + UPDATED_HRA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByHraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where hra is less than or equal to DEFAULT_HRA
        defaultSalarySettingsShouldBeFound("hra.lessThanOrEqual=" + DEFAULT_HRA);

        // Get all the salarySettingsList where hra is less than or equal to SMALLER_HRA
        defaultSalarySettingsShouldNotBeFound("hra.lessThanOrEqual=" + SMALLER_HRA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByHraIsLessThanSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where hra is less than DEFAULT_HRA
        defaultSalarySettingsShouldNotBeFound("hra.lessThan=" + DEFAULT_HRA);

        // Get all the salarySettingsList where hra is less than UPDATED_HRA
        defaultSalarySettingsShouldBeFound("hra.lessThan=" + UPDATED_HRA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByHraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where hra is greater than DEFAULT_HRA
        defaultSalarySettingsShouldNotBeFound("hra.greaterThan=" + DEFAULT_HRA);

        // Get all the salarySettingsList where hra is greater than SMALLER_HRA
        defaultSalarySettingsShouldBeFound("hra.greaterThan=" + SMALLER_HRA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByEmployeeshareIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where employeeshare equals to DEFAULT_EMPLOYEESHARE
        defaultSalarySettingsShouldBeFound("employeeshare.equals=" + DEFAULT_EMPLOYEESHARE);

        // Get all the salarySettingsList where employeeshare equals to UPDATED_EMPLOYEESHARE
        defaultSalarySettingsShouldNotBeFound("employeeshare.equals=" + UPDATED_EMPLOYEESHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByEmployeeshareIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where employeeshare in DEFAULT_EMPLOYEESHARE or UPDATED_EMPLOYEESHARE
        defaultSalarySettingsShouldBeFound("employeeshare.in=" + DEFAULT_EMPLOYEESHARE + "," + UPDATED_EMPLOYEESHARE);

        // Get all the salarySettingsList where employeeshare equals to UPDATED_EMPLOYEESHARE
        defaultSalarySettingsShouldNotBeFound("employeeshare.in=" + UPDATED_EMPLOYEESHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByEmployeeshareIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where employeeshare is not null
        defaultSalarySettingsShouldBeFound("employeeshare.specified=true");

        // Get all the salarySettingsList where employeeshare is null
        defaultSalarySettingsShouldNotBeFound("employeeshare.specified=false");
    }

    @Test
    @Transactional
    void getAllSalarySettingsByEmployeeshareIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where employeeshare is greater than or equal to DEFAULT_EMPLOYEESHARE
        defaultSalarySettingsShouldBeFound("employeeshare.greaterThanOrEqual=" + DEFAULT_EMPLOYEESHARE);

        // Get all the salarySettingsList where employeeshare is greater than or equal to UPDATED_EMPLOYEESHARE
        defaultSalarySettingsShouldNotBeFound("employeeshare.greaterThanOrEqual=" + UPDATED_EMPLOYEESHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByEmployeeshareIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where employeeshare is less than or equal to DEFAULT_EMPLOYEESHARE
        defaultSalarySettingsShouldBeFound("employeeshare.lessThanOrEqual=" + DEFAULT_EMPLOYEESHARE);

        // Get all the salarySettingsList where employeeshare is less than or equal to SMALLER_EMPLOYEESHARE
        defaultSalarySettingsShouldNotBeFound("employeeshare.lessThanOrEqual=" + SMALLER_EMPLOYEESHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByEmployeeshareIsLessThanSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where employeeshare is less than DEFAULT_EMPLOYEESHARE
        defaultSalarySettingsShouldNotBeFound("employeeshare.lessThan=" + DEFAULT_EMPLOYEESHARE);

        // Get all the salarySettingsList where employeeshare is less than UPDATED_EMPLOYEESHARE
        defaultSalarySettingsShouldBeFound("employeeshare.lessThan=" + UPDATED_EMPLOYEESHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByEmployeeshareIsGreaterThanSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where employeeshare is greater than DEFAULT_EMPLOYEESHARE
        defaultSalarySettingsShouldNotBeFound("employeeshare.greaterThan=" + DEFAULT_EMPLOYEESHARE);

        // Get all the salarySettingsList where employeeshare is greater than SMALLER_EMPLOYEESHARE
        defaultSalarySettingsShouldBeFound("employeeshare.greaterThan=" + SMALLER_EMPLOYEESHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyshareIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyshare equals to DEFAULT_COMPANYSHARE
        defaultSalarySettingsShouldBeFound("companyshare.equals=" + DEFAULT_COMPANYSHARE);

        // Get all the salarySettingsList where companyshare equals to UPDATED_COMPANYSHARE
        defaultSalarySettingsShouldNotBeFound("companyshare.equals=" + UPDATED_COMPANYSHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyshareIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyshare in DEFAULT_COMPANYSHARE or UPDATED_COMPANYSHARE
        defaultSalarySettingsShouldBeFound("companyshare.in=" + DEFAULT_COMPANYSHARE + "," + UPDATED_COMPANYSHARE);

        // Get all the salarySettingsList where companyshare equals to UPDATED_COMPANYSHARE
        defaultSalarySettingsShouldNotBeFound("companyshare.in=" + UPDATED_COMPANYSHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyshareIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyshare is not null
        defaultSalarySettingsShouldBeFound("companyshare.specified=true");

        // Get all the salarySettingsList where companyshare is null
        defaultSalarySettingsShouldNotBeFound("companyshare.specified=false");
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyshareIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyshare is greater than or equal to DEFAULT_COMPANYSHARE
        defaultSalarySettingsShouldBeFound("companyshare.greaterThanOrEqual=" + DEFAULT_COMPANYSHARE);

        // Get all the salarySettingsList where companyshare is greater than or equal to UPDATED_COMPANYSHARE
        defaultSalarySettingsShouldNotBeFound("companyshare.greaterThanOrEqual=" + UPDATED_COMPANYSHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyshareIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyshare is less than or equal to DEFAULT_COMPANYSHARE
        defaultSalarySettingsShouldBeFound("companyshare.lessThanOrEqual=" + DEFAULT_COMPANYSHARE);

        // Get all the salarySettingsList where companyshare is less than or equal to SMALLER_COMPANYSHARE
        defaultSalarySettingsShouldNotBeFound("companyshare.lessThanOrEqual=" + SMALLER_COMPANYSHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyshareIsLessThanSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyshare is less than DEFAULT_COMPANYSHARE
        defaultSalarySettingsShouldNotBeFound("companyshare.lessThan=" + DEFAULT_COMPANYSHARE);

        // Get all the salarySettingsList where companyshare is less than UPDATED_COMPANYSHARE
        defaultSalarySettingsShouldBeFound("companyshare.lessThan=" + UPDATED_COMPANYSHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyshareIsGreaterThanSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyshare is greater than DEFAULT_COMPANYSHARE
        defaultSalarySettingsShouldNotBeFound("companyshare.greaterThan=" + DEFAULT_COMPANYSHARE);

        // Get all the salarySettingsList where companyshare is greater than SMALLER_COMPANYSHARE
        defaultSalarySettingsShouldBeFound("companyshare.greaterThan=" + SMALLER_COMPANYSHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsBySalaryFromIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where salaryFrom equals to DEFAULT_SALARY_FROM
        defaultSalarySettingsShouldBeFound("salaryFrom.equals=" + DEFAULT_SALARY_FROM);

        // Get all the salarySettingsList where salaryFrom equals to UPDATED_SALARY_FROM
        defaultSalarySettingsShouldNotBeFound("salaryFrom.equals=" + UPDATED_SALARY_FROM);
    }

    @Test
    @Transactional
    void getAllSalarySettingsBySalaryFromIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where salaryFrom in DEFAULT_SALARY_FROM or UPDATED_SALARY_FROM
        defaultSalarySettingsShouldBeFound("salaryFrom.in=" + DEFAULT_SALARY_FROM + "," + UPDATED_SALARY_FROM);

        // Get all the salarySettingsList where salaryFrom equals to UPDATED_SALARY_FROM
        defaultSalarySettingsShouldNotBeFound("salaryFrom.in=" + UPDATED_SALARY_FROM);
    }

    @Test
    @Transactional
    void getAllSalarySettingsBySalaryFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where salaryFrom is not null
        defaultSalarySettingsShouldBeFound("salaryFrom.specified=true");

        // Get all the salarySettingsList where salaryFrom is null
        defaultSalarySettingsShouldNotBeFound("salaryFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllSalarySettingsBySalaryToIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where salaryTo equals to DEFAULT_SALARY_TO
        defaultSalarySettingsShouldBeFound("salaryTo.equals=" + DEFAULT_SALARY_TO);

        // Get all the salarySettingsList where salaryTo equals to UPDATED_SALARY_TO
        defaultSalarySettingsShouldNotBeFound("salaryTo.equals=" + UPDATED_SALARY_TO);
    }

    @Test
    @Transactional
    void getAllSalarySettingsBySalaryToIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where salaryTo in DEFAULT_SALARY_TO or UPDATED_SALARY_TO
        defaultSalarySettingsShouldBeFound("salaryTo.in=" + DEFAULT_SALARY_TO + "," + UPDATED_SALARY_TO);

        // Get all the salarySettingsList where salaryTo equals to UPDATED_SALARY_TO
        defaultSalarySettingsShouldNotBeFound("salaryTo.in=" + UPDATED_SALARY_TO);
    }

    @Test
    @Transactional
    void getAllSalarySettingsBySalaryToIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where salaryTo is not null
        defaultSalarySettingsShouldBeFound("salaryTo.specified=true");

        // Get all the salarySettingsList where salaryTo is null
        defaultSalarySettingsShouldNotBeFound("salaryTo.specified=false");
    }

    @Test
    @Transactional
    void getAllSalarySettingsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultSalarySettingsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the salarySettingsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSalarySettingsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultSalarySettingsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the salarySettingsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSalarySettingsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where lastModified is not null
        defaultSalarySettingsShouldBeFound("lastModified.specified=true");

        // Get all the salarySettingsList where lastModified is null
        defaultSalarySettingsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllSalarySettingsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSalarySettingsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the salarySettingsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSalarySettingsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSalarySettingsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the salarySettingsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSalarySettingsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where lastModifiedBy is not null
        defaultSalarySettingsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the salarySettingsList where lastModifiedBy is null
        defaultSalarySettingsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSalarySettingsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSalarySettingsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the salarySettingsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSalarySettingsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSalarySettingsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the salarySettingsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSalarySettingsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where createdBy equals to DEFAULT_CREATED_BY
        defaultSalarySettingsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the salarySettingsList where createdBy equals to UPDATED_CREATED_BY
        defaultSalarySettingsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSalarySettingsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the salarySettingsList where createdBy equals to UPDATED_CREATED_BY
        defaultSalarySettingsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where createdBy is not null
        defaultSalarySettingsShouldBeFound("createdBy.specified=true");

        // Get all the salarySettingsList where createdBy is null
        defaultSalarySettingsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where createdBy contains DEFAULT_CREATED_BY
        defaultSalarySettingsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the salarySettingsList where createdBy contains UPDATED_CREATED_BY
        defaultSalarySettingsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultSalarySettingsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the salarySettingsList where createdBy does not contain UPDATED_CREATED_BY
        defaultSalarySettingsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where createdOn equals to DEFAULT_CREATED_ON
        defaultSalarySettingsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the salarySettingsList where createdOn equals to UPDATED_CREATED_ON
        defaultSalarySettingsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSalarySettingsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the salarySettingsList where createdOn equals to UPDATED_CREATED_ON
        defaultSalarySettingsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where createdOn is not null
        defaultSalarySettingsShouldBeFound("createdOn.specified=true");

        // Get all the salarySettingsList where createdOn is null
        defaultSalarySettingsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllSalarySettingsByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where deleted equals to DEFAULT_DELETED
        defaultSalarySettingsShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the salarySettingsList where deleted equals to UPDATED_DELETED
        defaultSalarySettingsShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultSalarySettingsShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the salarySettingsList where deleted equals to UPDATED_DELETED
        defaultSalarySettingsShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where deleted is not null
        defaultSalarySettingsShouldBeFound("deleted.specified=true");

        // Get all the salarySettingsList where deleted is null
        defaultSalarySettingsShouldNotBeFound("deleted.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSalarySettingsShouldBeFound(String filter) throws Exception {
        restSalarySettingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salarySettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].da").value(hasItem(DEFAULT_DA.doubleValue())))
            .andExpect(jsonPath("$.[*].hra").value(hasItem(DEFAULT_HRA.doubleValue())))
            .andExpect(jsonPath("$.[*].employeeshare").value(hasItem(DEFAULT_EMPLOYEESHARE.doubleValue())))
            .andExpect(jsonPath("$.[*].companyshare").value(hasItem(DEFAULT_COMPANYSHARE.doubleValue())))
            .andExpect(jsonPath("$.[*].salaryFrom").value(hasItem(DEFAULT_SALARY_FROM.toString())))
            .andExpect(jsonPath("$.[*].salaryTo").value(hasItem(DEFAULT_SALARY_TO.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));

        // Check, that the count call also returns 1
        restSalarySettingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSalarySettingsShouldNotBeFound(String filter) throws Exception {
        restSalarySettingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSalarySettingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSalarySettings() throws Exception {
        // Get the salarySettings
        restSalarySettingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSalarySettings() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        int databaseSizeBeforeUpdate = salarySettingsRepository.findAll().size();

        // Update the salarySettings
        SalarySettings updatedSalarySettings = salarySettingsRepository.findById(salarySettings.getId()).get();
        // Disconnect from session so that the updates on updatedSalarySettings are not directly saved in db
        em.detach(updatedSalarySettings);
        updatedSalarySettings
            .da(UPDATED_DA)
            .hra(UPDATED_HRA)
            .employeeshare(UPDATED_EMPLOYEESHARE)
            .companyshare(UPDATED_COMPANYSHARE)
            .salaryFrom(UPDATED_SALARY_FROM)
            .salaryTo(UPDATED_SALARY_TO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);
        SalarySettingsDTO salarySettingsDTO = salarySettingsMapper.toDto(updatedSalarySettings);

        restSalarySettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salarySettingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salarySettingsDTO))
            )
            .andExpect(status().isOk());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeUpdate);
        SalarySettings testSalarySettings = salarySettingsList.get(salarySettingsList.size() - 1);
        assertThat(testSalarySettings.getDa()).isEqualTo(UPDATED_DA);
        assertThat(testSalarySettings.getHra()).isEqualTo(UPDATED_HRA);
        assertThat(testSalarySettings.getEmployeeshare()).isEqualTo(UPDATED_EMPLOYEESHARE);
        assertThat(testSalarySettings.getCompanyshare()).isEqualTo(UPDATED_COMPANYSHARE);
        assertThat(testSalarySettings.getSalaryFrom()).isEqualTo(UPDATED_SALARY_FROM);
        assertThat(testSalarySettings.getSalaryTo()).isEqualTo(UPDATED_SALARY_TO);
        assertThat(testSalarySettings.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSalarySettings.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSalarySettings.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSalarySettings.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSalarySettings.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingSalarySettings() throws Exception {
        int databaseSizeBeforeUpdate = salarySettingsRepository.findAll().size();
        salarySettings.setId(count.incrementAndGet());

        // Create the SalarySettings
        SalarySettingsDTO salarySettingsDTO = salarySettingsMapper.toDto(salarySettings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalarySettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salarySettingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salarySettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalarySettings() throws Exception {
        int databaseSizeBeforeUpdate = salarySettingsRepository.findAll().size();
        salarySettings.setId(count.incrementAndGet());

        // Create the SalarySettings
        SalarySettingsDTO salarySettingsDTO = salarySettingsMapper.toDto(salarySettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalarySettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salarySettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalarySettings() throws Exception {
        int databaseSizeBeforeUpdate = salarySettingsRepository.findAll().size();
        salarySettings.setId(count.incrementAndGet());

        // Create the SalarySettings
        SalarySettingsDTO salarySettingsDTO = salarySettingsMapper.toDto(salarySettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalarySettingsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salarySettingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalarySettingsWithPatch() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        int databaseSizeBeforeUpdate = salarySettingsRepository.findAll().size();

        // Update the salarySettings using partial update
        SalarySettings partialUpdatedSalarySettings = new SalarySettings();
        partialUpdatedSalarySettings.setId(salarySettings.getId());

        partialUpdatedSalarySettings
            .da(UPDATED_DA)
            .hra(UPDATED_HRA)
            .salaryFrom(UPDATED_SALARY_FROM)
            .salaryTo(UPDATED_SALARY_TO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .deleted(UPDATED_DELETED);

        restSalarySettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalarySettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalarySettings))
            )
            .andExpect(status().isOk());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeUpdate);
        SalarySettings testSalarySettings = salarySettingsList.get(salarySettingsList.size() - 1);
        assertThat(testSalarySettings.getDa()).isEqualTo(UPDATED_DA);
        assertThat(testSalarySettings.getHra()).isEqualTo(UPDATED_HRA);
        assertThat(testSalarySettings.getEmployeeshare()).isEqualTo(DEFAULT_EMPLOYEESHARE);
        assertThat(testSalarySettings.getCompanyshare()).isEqualTo(DEFAULT_COMPANYSHARE);
        assertThat(testSalarySettings.getSalaryFrom()).isEqualTo(UPDATED_SALARY_FROM);
        assertThat(testSalarySettings.getSalaryTo()).isEqualTo(UPDATED_SALARY_TO);
        assertThat(testSalarySettings.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSalarySettings.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSalarySettings.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSalarySettings.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSalarySettings.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateSalarySettingsWithPatch() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        int databaseSizeBeforeUpdate = salarySettingsRepository.findAll().size();

        // Update the salarySettings using partial update
        SalarySettings partialUpdatedSalarySettings = new SalarySettings();
        partialUpdatedSalarySettings.setId(salarySettings.getId());

        partialUpdatedSalarySettings
            .da(UPDATED_DA)
            .hra(UPDATED_HRA)
            .employeeshare(UPDATED_EMPLOYEESHARE)
            .companyshare(UPDATED_COMPANYSHARE)
            .salaryFrom(UPDATED_SALARY_FROM)
            .salaryTo(UPDATED_SALARY_TO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);

        restSalarySettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalarySettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalarySettings))
            )
            .andExpect(status().isOk());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeUpdate);
        SalarySettings testSalarySettings = salarySettingsList.get(salarySettingsList.size() - 1);
        assertThat(testSalarySettings.getDa()).isEqualTo(UPDATED_DA);
        assertThat(testSalarySettings.getHra()).isEqualTo(UPDATED_HRA);
        assertThat(testSalarySettings.getEmployeeshare()).isEqualTo(UPDATED_EMPLOYEESHARE);
        assertThat(testSalarySettings.getCompanyshare()).isEqualTo(UPDATED_COMPANYSHARE);
        assertThat(testSalarySettings.getSalaryFrom()).isEqualTo(UPDATED_SALARY_FROM);
        assertThat(testSalarySettings.getSalaryTo()).isEqualTo(UPDATED_SALARY_TO);
        assertThat(testSalarySettings.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSalarySettings.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSalarySettings.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSalarySettings.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSalarySettings.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingSalarySettings() throws Exception {
        int databaseSizeBeforeUpdate = salarySettingsRepository.findAll().size();
        salarySettings.setId(count.incrementAndGet());

        // Create the SalarySettings
        SalarySettingsDTO salarySettingsDTO = salarySettingsMapper.toDto(salarySettings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalarySettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salarySettingsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salarySettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalarySettings() throws Exception {
        int databaseSizeBeforeUpdate = salarySettingsRepository.findAll().size();
        salarySettings.setId(count.incrementAndGet());

        // Create the SalarySettings
        SalarySettingsDTO salarySettingsDTO = salarySettingsMapper.toDto(salarySettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalarySettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salarySettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalarySettings() throws Exception {
        int databaseSizeBeforeUpdate = salarySettingsRepository.findAll().size();
        salarySettings.setId(count.incrementAndGet());

        // Create the SalarySettings
        SalarySettingsDTO salarySettingsDTO = salarySettingsMapper.toDto(salarySettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalarySettingsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salarySettingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalarySettings() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        int databaseSizeBeforeDelete = salarySettingsRepository.findAll().size();

        // Delete the salarySettings
        restSalarySettingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, salarySettings.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
