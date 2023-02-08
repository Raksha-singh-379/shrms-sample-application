package com.techvg.shrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.shrms.IntegrationTest;
import com.techvg.shrms.domain.Employee;
import com.techvg.shrms.domain.WorkExperience;
import com.techvg.shrms.repository.WorkExperienceRepository;
import com.techvg.shrms.service.criteria.WorkExperienceCriteria;
import com.techvg.shrms.service.dto.WorkExperienceDTO;
import com.techvg.shrms.service.mapper.WorkExperienceMapper;
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
 * Integration tests for the {@link WorkExperienceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkExperienceResourceIT {

    private static final String DEFAULT_JOB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Long DEFAULT_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_EMPLOYEE_ID = 2L;
    private static final Long SMALLER_EMPLOYEE_ID = 1L - 1L;

    private static final Double DEFAULT_YEAR_OF_EXP = 1D;
    private static final Double UPDATED_YEAR_OF_EXP = 2D;
    private static final Double SMALLER_YEAR_OF_EXP = 1D - 1D;

    private static final String DEFAULT_JOB_DESC = "AAAAAAAAAA";
    private static final String UPDATED_JOB_DESC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/work-experiences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkExperienceRepository workExperienceRepository;

    @Autowired
    private WorkExperienceMapper workExperienceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkExperienceMockMvc;

    private WorkExperience workExperience;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkExperience createEntity(EntityManager em) {
        WorkExperience workExperience = new WorkExperience()
            .jobTitle(DEFAULT_JOB_TITLE)
            .companyName(DEFAULT_COMPANY_NAME)
            .address(DEFAULT_ADDRESS)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .yearOfExp(DEFAULT_YEAR_OF_EXP)
            .jobDesc(DEFAULT_JOB_DESC);
        return workExperience;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkExperience createUpdatedEntity(EntityManager em) {
        WorkExperience workExperience = new WorkExperience()
            .jobTitle(UPDATED_JOB_TITLE)
            .companyName(UPDATED_COMPANY_NAME)
            .address(UPDATED_ADDRESS)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .yearOfExp(UPDATED_YEAR_OF_EXP)
            .jobDesc(UPDATED_JOB_DESC);
        return workExperience;
    }

    @BeforeEach
    public void initTest() {
        workExperience = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkExperience() throws Exception {
        int databaseSizeBeforeCreate = workExperienceRepository.findAll().size();
        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);
        restWorkExperienceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeCreate + 1);
        WorkExperience testWorkExperience = workExperienceList.get(workExperienceList.size() - 1);
        assertThat(testWorkExperience.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testWorkExperience.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testWorkExperience.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testWorkExperience.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testWorkExperience.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testWorkExperience.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testWorkExperience.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testWorkExperience.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testWorkExperience.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testWorkExperience.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testWorkExperience.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testWorkExperience.getYearOfExp()).isEqualTo(DEFAULT_YEAR_OF_EXP);
        assertThat(testWorkExperience.getJobDesc()).isEqualTo(DEFAULT_JOB_DESC);
    }

    @Test
    @Transactional
    void createWorkExperienceWithExistingId() throws Exception {
        // Create the WorkExperience with an existing ID
        workExperience.setId(1L);
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        int databaseSizeBeforeCreate = workExperienceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkExperienceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkExperiences() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].yearOfExp").value(hasItem(DEFAULT_YEAR_OF_EXP.doubleValue())))
            .andExpect(jsonPath("$.[*].jobDesc").value(hasItem(DEFAULT_JOB_DESC)));
    }

    @Test
    @Transactional
    void getWorkExperience() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get the workExperience
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL_ID, workExperience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workExperience.getId().intValue()))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.yearOfExp").value(DEFAULT_YEAR_OF_EXP.doubleValue()))
            .andExpect(jsonPath("$.jobDesc").value(DEFAULT_JOB_DESC));
    }

    @Test
    @Transactional
    void getWorkExperiencesByIdFiltering() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        Long id = workExperience.getId();

        defaultWorkExperienceShouldBeFound("id.equals=" + id);
        defaultWorkExperienceShouldNotBeFound("id.notEquals=" + id);

        defaultWorkExperienceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWorkExperienceShouldNotBeFound("id.greaterThan=" + id);

        defaultWorkExperienceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWorkExperienceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobTitle equals to DEFAULT_JOB_TITLE
        defaultWorkExperienceShouldBeFound("jobTitle.equals=" + DEFAULT_JOB_TITLE);

        // Get all the workExperienceList where jobTitle equals to UPDATED_JOB_TITLE
        defaultWorkExperienceShouldNotBeFound("jobTitle.equals=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobTitleIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobTitle in DEFAULT_JOB_TITLE or UPDATED_JOB_TITLE
        defaultWorkExperienceShouldBeFound("jobTitle.in=" + DEFAULT_JOB_TITLE + "," + UPDATED_JOB_TITLE);

        // Get all the workExperienceList where jobTitle equals to UPDATED_JOB_TITLE
        defaultWorkExperienceShouldNotBeFound("jobTitle.in=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobTitle is not null
        defaultWorkExperienceShouldBeFound("jobTitle.specified=true");

        // Get all the workExperienceList where jobTitle is null
        defaultWorkExperienceShouldNotBeFound("jobTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobTitleContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobTitle contains DEFAULT_JOB_TITLE
        defaultWorkExperienceShouldBeFound("jobTitle.contains=" + DEFAULT_JOB_TITLE);

        // Get all the workExperienceList where jobTitle contains UPDATED_JOB_TITLE
        defaultWorkExperienceShouldNotBeFound("jobTitle.contains=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobTitleNotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobTitle does not contain DEFAULT_JOB_TITLE
        defaultWorkExperienceShouldNotBeFound("jobTitle.doesNotContain=" + DEFAULT_JOB_TITLE);

        // Get all the workExperienceList where jobTitle does not contain UPDATED_JOB_TITLE
        defaultWorkExperienceShouldBeFound("jobTitle.doesNotContain=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyName equals to DEFAULT_COMPANY_NAME
        defaultWorkExperienceShouldBeFound("companyName.equals=" + DEFAULT_COMPANY_NAME);

        // Get all the workExperienceList where companyName equals to UPDATED_COMPANY_NAME
        defaultWorkExperienceShouldNotBeFound("companyName.equals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyNameIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyName in DEFAULT_COMPANY_NAME or UPDATED_COMPANY_NAME
        defaultWorkExperienceShouldBeFound("companyName.in=" + DEFAULT_COMPANY_NAME + "," + UPDATED_COMPANY_NAME);

        // Get all the workExperienceList where companyName equals to UPDATED_COMPANY_NAME
        defaultWorkExperienceShouldNotBeFound("companyName.in=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyName is not null
        defaultWorkExperienceShouldBeFound("companyName.specified=true");

        // Get all the workExperienceList where companyName is null
        defaultWorkExperienceShouldNotBeFound("companyName.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyNameContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyName contains DEFAULT_COMPANY_NAME
        defaultWorkExperienceShouldBeFound("companyName.contains=" + DEFAULT_COMPANY_NAME);

        // Get all the workExperienceList where companyName contains UPDATED_COMPANY_NAME
        defaultWorkExperienceShouldNotBeFound("companyName.contains=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyNameNotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyName does not contain DEFAULT_COMPANY_NAME
        defaultWorkExperienceShouldNotBeFound("companyName.doesNotContain=" + DEFAULT_COMPANY_NAME);

        // Get all the workExperienceList where companyName does not contain UPDATED_COMPANY_NAME
        defaultWorkExperienceShouldBeFound("companyName.doesNotContain=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where address equals to DEFAULT_ADDRESS
        defaultWorkExperienceShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the workExperienceList where address equals to UPDATED_ADDRESS
        defaultWorkExperienceShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultWorkExperienceShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the workExperienceList where address equals to UPDATED_ADDRESS
        defaultWorkExperienceShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where address is not null
        defaultWorkExperienceShouldBeFound("address.specified=true");

        // Get all the workExperienceList where address is null
        defaultWorkExperienceShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByAddressContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where address contains DEFAULT_ADDRESS
        defaultWorkExperienceShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the workExperienceList where address contains UPDATED_ADDRESS
        defaultWorkExperienceShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where address does not contain DEFAULT_ADDRESS
        defaultWorkExperienceShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the workExperienceList where address does not contain UPDATED_ADDRESS
        defaultWorkExperienceShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where startDate equals to DEFAULT_START_DATE
        defaultWorkExperienceShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the workExperienceList where startDate equals to UPDATED_START_DATE
        defaultWorkExperienceShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultWorkExperienceShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the workExperienceList where startDate equals to UPDATED_START_DATE
        defaultWorkExperienceShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where startDate is not null
        defaultWorkExperienceShouldBeFound("startDate.specified=true");

        // Get all the workExperienceList where startDate is null
        defaultWorkExperienceShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where endDate equals to DEFAULT_END_DATE
        defaultWorkExperienceShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the workExperienceList where endDate equals to UPDATED_END_DATE
        defaultWorkExperienceShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultWorkExperienceShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the workExperienceList where endDate equals to UPDATED_END_DATE
        defaultWorkExperienceShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where endDate is not null
        defaultWorkExperienceShouldBeFound("endDate.specified=true");

        // Get all the workExperienceList where endDate is null
        defaultWorkExperienceShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultWorkExperienceShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the workExperienceList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultWorkExperienceShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultWorkExperienceShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the workExperienceList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultWorkExperienceShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModified is not null
        defaultWorkExperienceShouldBeFound("lastModified.specified=true");

        // Get all the workExperienceList where lastModified is null
        defaultWorkExperienceShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultWorkExperienceShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workExperienceList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWorkExperienceShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultWorkExperienceShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the workExperienceList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWorkExperienceShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModifiedBy is not null
        defaultWorkExperienceShouldBeFound("lastModifiedBy.specified=true");

        // Get all the workExperienceList where lastModifiedBy is null
        defaultWorkExperienceShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultWorkExperienceShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workExperienceList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultWorkExperienceShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultWorkExperienceShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workExperienceList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultWorkExperienceShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where createdBy equals to DEFAULT_CREATED_BY
        defaultWorkExperienceShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the workExperienceList where createdBy equals to UPDATED_CREATED_BY
        defaultWorkExperienceShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultWorkExperienceShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the workExperienceList where createdBy equals to UPDATED_CREATED_BY
        defaultWorkExperienceShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where createdBy is not null
        defaultWorkExperienceShouldBeFound("createdBy.specified=true");

        // Get all the workExperienceList where createdBy is null
        defaultWorkExperienceShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where createdBy contains DEFAULT_CREATED_BY
        defaultWorkExperienceShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the workExperienceList where createdBy contains UPDATED_CREATED_BY
        defaultWorkExperienceShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where createdBy does not contain DEFAULT_CREATED_BY
        defaultWorkExperienceShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the workExperienceList where createdBy does not contain UPDATED_CREATED_BY
        defaultWorkExperienceShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where createdOn equals to DEFAULT_CREATED_ON
        defaultWorkExperienceShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the workExperienceList where createdOn equals to UPDATED_CREATED_ON
        defaultWorkExperienceShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultWorkExperienceShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the workExperienceList where createdOn equals to UPDATED_CREATED_ON
        defaultWorkExperienceShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where createdOn is not null
        defaultWorkExperienceShouldBeFound("createdOn.specified=true");

        // Get all the workExperienceList where createdOn is null
        defaultWorkExperienceShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where isDeleted equals to DEFAULT_IS_DELETED
        defaultWorkExperienceShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the workExperienceList where isDeleted equals to UPDATED_IS_DELETED
        defaultWorkExperienceShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultWorkExperienceShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the workExperienceList where isDeleted equals to UPDATED_IS_DELETED
        defaultWorkExperienceShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where isDeleted is not null
        defaultWorkExperienceShouldBeFound("isDeleted.specified=true");

        // Get all the workExperienceList where isDeleted is null
        defaultWorkExperienceShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultWorkExperienceShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the workExperienceList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultWorkExperienceShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultWorkExperienceShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the workExperienceList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultWorkExperienceShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where employeeId is not null
        defaultWorkExperienceShouldBeFound("employeeId.specified=true");

        // Get all the workExperienceList where employeeId is null
        defaultWorkExperienceShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultWorkExperienceShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the workExperienceList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultWorkExperienceShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultWorkExperienceShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the workExperienceList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultWorkExperienceShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultWorkExperienceShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the workExperienceList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultWorkExperienceShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultWorkExperienceShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the workExperienceList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultWorkExperienceShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByYearOfExpIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where yearOfExp equals to DEFAULT_YEAR_OF_EXP
        defaultWorkExperienceShouldBeFound("yearOfExp.equals=" + DEFAULT_YEAR_OF_EXP);

        // Get all the workExperienceList where yearOfExp equals to UPDATED_YEAR_OF_EXP
        defaultWorkExperienceShouldNotBeFound("yearOfExp.equals=" + UPDATED_YEAR_OF_EXP);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByYearOfExpIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where yearOfExp in DEFAULT_YEAR_OF_EXP or UPDATED_YEAR_OF_EXP
        defaultWorkExperienceShouldBeFound("yearOfExp.in=" + DEFAULT_YEAR_OF_EXP + "," + UPDATED_YEAR_OF_EXP);

        // Get all the workExperienceList where yearOfExp equals to UPDATED_YEAR_OF_EXP
        defaultWorkExperienceShouldNotBeFound("yearOfExp.in=" + UPDATED_YEAR_OF_EXP);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByYearOfExpIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where yearOfExp is not null
        defaultWorkExperienceShouldBeFound("yearOfExp.specified=true");

        // Get all the workExperienceList where yearOfExp is null
        defaultWorkExperienceShouldNotBeFound("yearOfExp.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByYearOfExpIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where yearOfExp is greater than or equal to DEFAULT_YEAR_OF_EXP
        defaultWorkExperienceShouldBeFound("yearOfExp.greaterThanOrEqual=" + DEFAULT_YEAR_OF_EXP);

        // Get all the workExperienceList where yearOfExp is greater than or equal to UPDATED_YEAR_OF_EXP
        defaultWorkExperienceShouldNotBeFound("yearOfExp.greaterThanOrEqual=" + UPDATED_YEAR_OF_EXP);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByYearOfExpIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where yearOfExp is less than or equal to DEFAULT_YEAR_OF_EXP
        defaultWorkExperienceShouldBeFound("yearOfExp.lessThanOrEqual=" + DEFAULT_YEAR_OF_EXP);

        // Get all the workExperienceList where yearOfExp is less than or equal to SMALLER_YEAR_OF_EXP
        defaultWorkExperienceShouldNotBeFound("yearOfExp.lessThanOrEqual=" + SMALLER_YEAR_OF_EXP);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByYearOfExpIsLessThanSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where yearOfExp is less than DEFAULT_YEAR_OF_EXP
        defaultWorkExperienceShouldNotBeFound("yearOfExp.lessThan=" + DEFAULT_YEAR_OF_EXP);

        // Get all the workExperienceList where yearOfExp is less than UPDATED_YEAR_OF_EXP
        defaultWorkExperienceShouldBeFound("yearOfExp.lessThan=" + UPDATED_YEAR_OF_EXP);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByYearOfExpIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where yearOfExp is greater than DEFAULT_YEAR_OF_EXP
        defaultWorkExperienceShouldNotBeFound("yearOfExp.greaterThan=" + DEFAULT_YEAR_OF_EXP);

        // Get all the workExperienceList where yearOfExp is greater than SMALLER_YEAR_OF_EXP
        defaultWorkExperienceShouldBeFound("yearOfExp.greaterThan=" + SMALLER_YEAR_OF_EXP);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobDescIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobDesc equals to DEFAULT_JOB_DESC
        defaultWorkExperienceShouldBeFound("jobDesc.equals=" + DEFAULT_JOB_DESC);

        // Get all the workExperienceList where jobDesc equals to UPDATED_JOB_DESC
        defaultWorkExperienceShouldNotBeFound("jobDesc.equals=" + UPDATED_JOB_DESC);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobDescIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobDesc in DEFAULT_JOB_DESC or UPDATED_JOB_DESC
        defaultWorkExperienceShouldBeFound("jobDesc.in=" + DEFAULT_JOB_DESC + "," + UPDATED_JOB_DESC);

        // Get all the workExperienceList where jobDesc equals to UPDATED_JOB_DESC
        defaultWorkExperienceShouldNotBeFound("jobDesc.in=" + UPDATED_JOB_DESC);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobDesc is not null
        defaultWorkExperienceShouldBeFound("jobDesc.specified=true");

        // Get all the workExperienceList where jobDesc is null
        defaultWorkExperienceShouldNotBeFound("jobDesc.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobDescContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobDesc contains DEFAULT_JOB_DESC
        defaultWorkExperienceShouldBeFound("jobDesc.contains=" + DEFAULT_JOB_DESC);

        // Get all the workExperienceList where jobDesc contains UPDATED_JOB_DESC
        defaultWorkExperienceShouldNotBeFound("jobDesc.contains=" + UPDATED_JOB_DESC);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobDescNotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobDesc does not contain DEFAULT_JOB_DESC
        defaultWorkExperienceShouldNotBeFound("jobDesc.doesNotContain=" + DEFAULT_JOB_DESC);

        // Get all the workExperienceList where jobDesc does not contain UPDATED_JOB_DESC
        defaultWorkExperienceShouldBeFound("jobDesc.doesNotContain=" + UPDATED_JOB_DESC);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            workExperienceRepository.saveAndFlush(workExperience);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        workExperience.setEmployee(employee);
        workExperienceRepository.saveAndFlush(workExperience);
        Long employeeId = employee.getId();

        // Get all the workExperienceList where employee equals to employeeId
        defaultWorkExperienceShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the workExperienceList where employee equals to (employeeId + 1)
        defaultWorkExperienceShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkExperienceShouldBeFound(String filter) throws Exception {
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].yearOfExp").value(hasItem(DEFAULT_YEAR_OF_EXP.doubleValue())))
            .andExpect(jsonPath("$.[*].jobDesc").value(hasItem(DEFAULT_JOB_DESC)));

        // Check, that the count call also returns 1
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkExperienceShouldNotBeFound(String filter) throws Exception {
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWorkExperience() throws Exception {
        // Get the workExperience
        restWorkExperienceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkExperience() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();

        // Update the workExperience
        WorkExperience updatedWorkExperience = workExperienceRepository.findById(workExperience.getId()).get();
        // Disconnect from session so that the updates on updatedWorkExperience are not directly saved in db
        em.detach(updatedWorkExperience);
        updatedWorkExperience
            .jobTitle(UPDATED_JOB_TITLE)
            .companyName(UPDATED_COMPANY_NAME)
            .address(UPDATED_ADDRESS)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .yearOfExp(UPDATED_YEAR_OF_EXP)
            .jobDesc(UPDATED_JOB_DESC);
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(updatedWorkExperience);

        restWorkExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workExperienceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isOk());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
        WorkExperience testWorkExperience = workExperienceList.get(workExperienceList.size() - 1);
        assertThat(testWorkExperience.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testWorkExperience.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testWorkExperience.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testWorkExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testWorkExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testWorkExperience.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWorkExperience.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testWorkExperience.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWorkExperience.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testWorkExperience.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testWorkExperience.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testWorkExperience.getYearOfExp()).isEqualTo(UPDATED_YEAR_OF_EXP);
        assertThat(testWorkExperience.getJobDesc()).isEqualTo(UPDATED_JOB_DESC);
    }

    @Test
    @Transactional
    void putNonExistingWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workExperienceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkExperienceWithPatch() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();

        // Update the workExperience using partial update
        WorkExperience partialUpdatedWorkExperience = new WorkExperience();
        partialUpdatedWorkExperience.setId(workExperience.getId());

        partialUpdatedWorkExperience
            .companyName(UPDATED_COMPANY_NAME)
            .address(UPDATED_ADDRESS)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .yearOfExp(UPDATED_YEAR_OF_EXP);

        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkExperience.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkExperience))
            )
            .andExpect(status().isOk());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
        WorkExperience testWorkExperience = workExperienceList.get(workExperienceList.size() - 1);
        assertThat(testWorkExperience.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testWorkExperience.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testWorkExperience.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testWorkExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testWorkExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testWorkExperience.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWorkExperience.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testWorkExperience.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWorkExperience.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testWorkExperience.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testWorkExperience.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testWorkExperience.getYearOfExp()).isEqualTo(UPDATED_YEAR_OF_EXP);
        assertThat(testWorkExperience.getJobDesc()).isEqualTo(DEFAULT_JOB_DESC);
    }

    @Test
    @Transactional
    void fullUpdateWorkExperienceWithPatch() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();

        // Update the workExperience using partial update
        WorkExperience partialUpdatedWorkExperience = new WorkExperience();
        partialUpdatedWorkExperience.setId(workExperience.getId());

        partialUpdatedWorkExperience
            .jobTitle(UPDATED_JOB_TITLE)
            .companyName(UPDATED_COMPANY_NAME)
            .address(UPDATED_ADDRESS)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .yearOfExp(UPDATED_YEAR_OF_EXP)
            .jobDesc(UPDATED_JOB_DESC);

        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkExperience.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkExperience))
            )
            .andExpect(status().isOk());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
        WorkExperience testWorkExperience = workExperienceList.get(workExperienceList.size() - 1);
        assertThat(testWorkExperience.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testWorkExperience.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testWorkExperience.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testWorkExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testWorkExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testWorkExperience.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWorkExperience.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testWorkExperience.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWorkExperience.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testWorkExperience.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testWorkExperience.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testWorkExperience.getYearOfExp()).isEqualTo(UPDATED_YEAR_OF_EXP);
        assertThat(testWorkExperience.getJobDesc()).isEqualTo(UPDATED_JOB_DESC);
    }

    @Test
    @Transactional
    void patchNonExistingWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workExperienceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkExperience() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        int databaseSizeBeforeDelete = workExperienceRepository.findAll().size();

        // Delete the workExperience
        restWorkExperienceMockMvc
            .perform(delete(ENTITY_API_URL_ID, workExperience.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
