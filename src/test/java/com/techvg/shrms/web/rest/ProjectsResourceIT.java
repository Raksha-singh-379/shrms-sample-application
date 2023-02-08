package com.techvg.shrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.shrms.IntegrationTest;
import com.techvg.shrms.domain.Projects;
import com.techvg.shrms.domain.enumeration.ProjectStatus;
import com.techvg.shrms.repository.ProjectsRepository;
import com.techvg.shrms.service.criteria.ProjectsCriteria;
import com.techvg.shrms.service.dto.ProjectsDTO;
import com.techvg.shrms.service.mapper.ProjectsMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ProjectsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectsResourceIT {

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_COST = 1D;
    private static final Double UPDATED_COST = 2D;
    private static final Double SMALLER_COST = 1D - 1D;

    private static final String DEFAULT_COST_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COST_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIORITY = "AAAAAAAAAA";
    private static final String UPDATED_PRIORITY = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DEAD_LINE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEAD_LINE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ProjectStatus DEFAULT_STATUS = ProjectStatus.INPROGRESS;
    private static final ProjectStatus UPDATED_STATUS = ProjectStatus.COMPLETED;

    private static final String DEFAULT_PROJECT_LEAD = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_LEAD = "BBBBBBBBBB";

    private static final Double DEFAULT_PROGRESS_PERCENT = 1D;
    private static final Double UPDATED_PROGRESS_PERCENT = 2D;
    private static final Double SMALLER_PROGRESS_PERCENT = 1D - 1D;

    private static final Long DEFAULT_OPEN_TASKS_NO = 1L;
    private static final Long UPDATED_OPEN_TASKS_NO = 2L;
    private static final Long SMALLER_OPEN_TASKS_NO = 1L - 1L;

    private static final Long DEFAULT_COMPLETE_TASKS_NO = 1L;
    private static final Long UPDATED_COMPLETE_TASKS_NO = 2L;
    private static final Long SMALLER_COMPLETE_TASKS_NO = 1L - 1L;

    private static final byte[] DEFAULT_PROJECT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PROJECT_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PROJECT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PROJECT_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PROJECT_FILE = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_FILE = "BBBBBBBBBB";

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

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final Long DEFAULT_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_EMPLOYEE_ID = 2L;
    private static final Long SMALLER_EMPLOYEE_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/projects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private ProjectsMapper projectsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectsMockMvc;

    private Projects projects;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projects createEntity(EntityManager em) {
        Projects projects = new Projects()
            .projectName(DEFAULT_PROJECT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .clientName(DEFAULT_CLIENT_NAME)
            .cost(DEFAULT_COST)
            .costType(DEFAULT_COST_TYPE)
            .priority(DEFAULT_PRIORITY)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .deadLine(DEFAULT_DEAD_LINE)
            .status(DEFAULT_STATUS)
            .projectLead(DEFAULT_PROJECT_LEAD)
            .progressPercent(DEFAULT_PROGRESS_PERCENT)
            .openTasksNo(DEFAULT_OPEN_TASKS_NO)
            .completeTasksNo(DEFAULT_COMPLETE_TASKS_NO)
            .projectLogo(DEFAULT_PROJECT_LOGO)
            .projectLogoContentType(DEFAULT_PROJECT_LOGO_CONTENT_TYPE)
            .projectFile(DEFAULT_PROJECT_FILE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .deleted(DEFAULT_DELETED)
            .companyId(DEFAULT_COMPANY_ID)
            .employeeId(DEFAULT_EMPLOYEE_ID);
        return projects;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projects createUpdatedEntity(EntityManager em) {
        Projects projects = new Projects()
            .projectName(UPDATED_PROJECT_NAME)
            .description(UPDATED_DESCRIPTION)
            .clientName(UPDATED_CLIENT_NAME)
            .cost(UPDATED_COST)
            .costType(UPDATED_COST_TYPE)
            .priority(UPDATED_PRIORITY)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .deadLine(UPDATED_DEAD_LINE)
            .status(UPDATED_STATUS)
            .projectLead(UPDATED_PROJECT_LEAD)
            .progressPercent(UPDATED_PROGRESS_PERCENT)
            .openTasksNo(UPDATED_OPEN_TASKS_NO)
            .completeTasksNo(UPDATED_COMPLETE_TASKS_NO)
            .projectLogo(UPDATED_PROJECT_LOGO)
            .projectLogoContentType(UPDATED_PROJECT_LOGO_CONTENT_TYPE)
            .projectFile(UPDATED_PROJECT_FILE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED)
            .companyId(UPDATED_COMPANY_ID)
            .employeeId(UPDATED_EMPLOYEE_ID);
        return projects;
    }

    @BeforeEach
    public void initTest() {
        projects = createEntity(em);
    }

    @Test
    @Transactional
    void createProjects() throws Exception {
        int databaseSizeBeforeCreate = projectsRepository.findAll().size();
        // Create the Projects
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);
        restProjectsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isCreated());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeCreate + 1);
        Projects testProjects = projectsList.get(projectsList.size() - 1);
        assertThat(testProjects.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testProjects.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjects.getClientName()).isEqualTo(DEFAULT_CLIENT_NAME);
        assertThat(testProjects.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testProjects.getCostType()).isEqualTo(DEFAULT_COST_TYPE);
        assertThat(testProjects.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testProjects.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProjects.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testProjects.getDeadLine()).isEqualTo(DEFAULT_DEAD_LINE);
        assertThat(testProjects.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProjects.getProjectLead()).isEqualTo(DEFAULT_PROJECT_LEAD);
        assertThat(testProjects.getProgressPercent()).isEqualTo(DEFAULT_PROGRESS_PERCENT);
        assertThat(testProjects.getOpenTasksNo()).isEqualTo(DEFAULT_OPEN_TASKS_NO);
        assertThat(testProjects.getCompleteTasksNo()).isEqualTo(DEFAULT_COMPLETE_TASKS_NO);
        assertThat(testProjects.getProjectLogo()).isEqualTo(DEFAULT_PROJECT_LOGO);
        assertThat(testProjects.getProjectLogoContentType()).isEqualTo(DEFAULT_PROJECT_LOGO_CONTENT_TYPE);
        assertThat(testProjects.getProjectFile()).isEqualTo(DEFAULT_PROJECT_FILE);
        assertThat(testProjects.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testProjects.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProjects.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProjects.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testProjects.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testProjects.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testProjects.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void createProjectsWithExistingId() throws Exception {
        // Create the Projects with an existing ID
        projects.setId(1L);
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);

        int databaseSizeBeforeCreate = projectsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList
        restProjectsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projects.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME)))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].costType").value(hasItem(DEFAULT_COST_TYPE)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].deadLine").value(hasItem(DEFAULT_DEAD_LINE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].projectLead").value(hasItem(DEFAULT_PROJECT_LEAD)))
            .andExpect(jsonPath("$.[*].progressPercent").value(hasItem(DEFAULT_PROGRESS_PERCENT.doubleValue())))
            .andExpect(jsonPath("$.[*].openTasksNo").value(hasItem(DEFAULT_OPEN_TASKS_NO.intValue())))
            .andExpect(jsonPath("$.[*].completeTasksNo").value(hasItem(DEFAULT_COMPLETE_TASKS_NO.intValue())))
            .andExpect(jsonPath("$.[*].projectLogoContentType").value(hasItem(DEFAULT_PROJECT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].projectLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROJECT_LOGO))))
            .andExpect(jsonPath("$.[*].projectFile").value(hasItem(DEFAULT_PROJECT_FILE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())));
    }

    @Test
    @Transactional
    void getProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get the projects
        restProjectsMockMvc
            .perform(get(ENTITY_API_URL_ID, projects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projects.getId().intValue()))
            .andExpect(jsonPath("$.projectName").value(DEFAULT_PROJECT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.clientName").value(DEFAULT_CLIENT_NAME))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()))
            .andExpect(jsonPath("$.costType").value(DEFAULT_COST_TYPE))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.deadLine").value(DEFAULT_DEAD_LINE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.projectLead").value(DEFAULT_PROJECT_LEAD))
            .andExpect(jsonPath("$.progressPercent").value(DEFAULT_PROGRESS_PERCENT.doubleValue()))
            .andExpect(jsonPath("$.openTasksNo").value(DEFAULT_OPEN_TASKS_NO.intValue()))
            .andExpect(jsonPath("$.completeTasksNo").value(DEFAULT_COMPLETE_TASKS_NO.intValue()))
            .andExpect(jsonPath("$.projectLogoContentType").value(DEFAULT_PROJECT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.projectLogo").value(Base64Utils.encodeToString(DEFAULT_PROJECT_LOGO)))
            .andExpect(jsonPath("$.projectFile").value(DEFAULT_PROJECT_FILE))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()));
    }

    @Test
    @Transactional
    void getProjectsByIdFiltering() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        Long id = projects.getId();

        defaultProjectsShouldBeFound("id.equals=" + id);
        defaultProjectsShouldNotBeFound("id.notEquals=" + id);

        defaultProjectsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProjectsShouldNotBeFound("id.greaterThan=" + id);

        defaultProjectsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProjectsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProjectsByProjectNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectName equals to DEFAULT_PROJECT_NAME
        defaultProjectsShouldBeFound("projectName.equals=" + DEFAULT_PROJECT_NAME);

        // Get all the projectsList where projectName equals to UPDATED_PROJECT_NAME
        defaultProjectsShouldNotBeFound("projectName.equals=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByProjectNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectName in DEFAULT_PROJECT_NAME or UPDATED_PROJECT_NAME
        defaultProjectsShouldBeFound("projectName.in=" + DEFAULT_PROJECT_NAME + "," + UPDATED_PROJECT_NAME);

        // Get all the projectsList where projectName equals to UPDATED_PROJECT_NAME
        defaultProjectsShouldNotBeFound("projectName.in=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByProjectNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectName is not null
        defaultProjectsShouldBeFound("projectName.specified=true");

        // Get all the projectsList where projectName is null
        defaultProjectsShouldNotBeFound("projectName.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByProjectNameContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectName contains DEFAULT_PROJECT_NAME
        defaultProjectsShouldBeFound("projectName.contains=" + DEFAULT_PROJECT_NAME);

        // Get all the projectsList where projectName contains UPDATED_PROJECT_NAME
        defaultProjectsShouldNotBeFound("projectName.contains=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByProjectNameNotContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectName does not contain DEFAULT_PROJECT_NAME
        defaultProjectsShouldNotBeFound("projectName.doesNotContain=" + DEFAULT_PROJECT_NAME);

        // Get all the projectsList where projectName does not contain UPDATED_PROJECT_NAME
        defaultProjectsShouldBeFound("projectName.doesNotContain=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where description equals to DEFAULT_DESCRIPTION
        defaultProjectsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the projectsList where description equals to UPDATED_DESCRIPTION
        defaultProjectsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProjectsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProjectsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the projectsList where description equals to UPDATED_DESCRIPTION
        defaultProjectsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProjectsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where description is not null
        defaultProjectsShouldBeFound("description.specified=true");

        // Get all the projectsList where description is null
        defaultProjectsShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where description contains DEFAULT_DESCRIPTION
        defaultProjectsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the projectsList where description contains UPDATED_DESCRIPTION
        defaultProjectsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProjectsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where description does not contain DEFAULT_DESCRIPTION
        defaultProjectsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the projectsList where description does not contain UPDATED_DESCRIPTION
        defaultProjectsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProjectsByClientNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where clientName equals to DEFAULT_CLIENT_NAME
        defaultProjectsShouldBeFound("clientName.equals=" + DEFAULT_CLIENT_NAME);

        // Get all the projectsList where clientName equals to UPDATED_CLIENT_NAME
        defaultProjectsShouldNotBeFound("clientName.equals=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByClientNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where clientName in DEFAULT_CLIENT_NAME or UPDATED_CLIENT_NAME
        defaultProjectsShouldBeFound("clientName.in=" + DEFAULT_CLIENT_NAME + "," + UPDATED_CLIENT_NAME);

        // Get all the projectsList where clientName equals to UPDATED_CLIENT_NAME
        defaultProjectsShouldNotBeFound("clientName.in=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByClientNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where clientName is not null
        defaultProjectsShouldBeFound("clientName.specified=true");

        // Get all the projectsList where clientName is null
        defaultProjectsShouldNotBeFound("clientName.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByClientNameContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where clientName contains DEFAULT_CLIENT_NAME
        defaultProjectsShouldBeFound("clientName.contains=" + DEFAULT_CLIENT_NAME);

        // Get all the projectsList where clientName contains UPDATED_CLIENT_NAME
        defaultProjectsShouldNotBeFound("clientName.contains=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByClientNameNotContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where clientName does not contain DEFAULT_CLIENT_NAME
        defaultProjectsShouldNotBeFound("clientName.doesNotContain=" + DEFAULT_CLIENT_NAME);

        // Get all the projectsList where clientName does not contain UPDATED_CLIENT_NAME
        defaultProjectsShouldBeFound("clientName.doesNotContain=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByCostIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where cost equals to DEFAULT_COST
        defaultProjectsShouldBeFound("cost.equals=" + DEFAULT_COST);

        // Get all the projectsList where cost equals to UPDATED_COST
        defaultProjectsShouldNotBeFound("cost.equals=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllProjectsByCostIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where cost in DEFAULT_COST or UPDATED_COST
        defaultProjectsShouldBeFound("cost.in=" + DEFAULT_COST + "," + UPDATED_COST);

        // Get all the projectsList where cost equals to UPDATED_COST
        defaultProjectsShouldNotBeFound("cost.in=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllProjectsByCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where cost is not null
        defaultProjectsShouldBeFound("cost.specified=true");

        // Get all the projectsList where cost is null
        defaultProjectsShouldNotBeFound("cost.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where cost is greater than or equal to DEFAULT_COST
        defaultProjectsShouldBeFound("cost.greaterThanOrEqual=" + DEFAULT_COST);

        // Get all the projectsList where cost is greater than or equal to UPDATED_COST
        defaultProjectsShouldNotBeFound("cost.greaterThanOrEqual=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllProjectsByCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where cost is less than or equal to DEFAULT_COST
        defaultProjectsShouldBeFound("cost.lessThanOrEqual=" + DEFAULT_COST);

        // Get all the projectsList where cost is less than or equal to SMALLER_COST
        defaultProjectsShouldNotBeFound("cost.lessThanOrEqual=" + SMALLER_COST);
    }

    @Test
    @Transactional
    void getAllProjectsByCostIsLessThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where cost is less than DEFAULT_COST
        defaultProjectsShouldNotBeFound("cost.lessThan=" + DEFAULT_COST);

        // Get all the projectsList where cost is less than UPDATED_COST
        defaultProjectsShouldBeFound("cost.lessThan=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllProjectsByCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where cost is greater than DEFAULT_COST
        defaultProjectsShouldNotBeFound("cost.greaterThan=" + DEFAULT_COST);

        // Get all the projectsList where cost is greater than SMALLER_COST
        defaultProjectsShouldBeFound("cost.greaterThan=" + SMALLER_COST);
    }

    @Test
    @Transactional
    void getAllProjectsByCostTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where costType equals to DEFAULT_COST_TYPE
        defaultProjectsShouldBeFound("costType.equals=" + DEFAULT_COST_TYPE);

        // Get all the projectsList where costType equals to UPDATED_COST_TYPE
        defaultProjectsShouldNotBeFound("costType.equals=" + UPDATED_COST_TYPE);
    }

    @Test
    @Transactional
    void getAllProjectsByCostTypeIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where costType in DEFAULT_COST_TYPE or UPDATED_COST_TYPE
        defaultProjectsShouldBeFound("costType.in=" + DEFAULT_COST_TYPE + "," + UPDATED_COST_TYPE);

        // Get all the projectsList where costType equals to UPDATED_COST_TYPE
        defaultProjectsShouldNotBeFound("costType.in=" + UPDATED_COST_TYPE);
    }

    @Test
    @Transactional
    void getAllProjectsByCostTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where costType is not null
        defaultProjectsShouldBeFound("costType.specified=true");

        // Get all the projectsList where costType is null
        defaultProjectsShouldNotBeFound("costType.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByCostTypeContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where costType contains DEFAULT_COST_TYPE
        defaultProjectsShouldBeFound("costType.contains=" + DEFAULT_COST_TYPE);

        // Get all the projectsList where costType contains UPDATED_COST_TYPE
        defaultProjectsShouldNotBeFound("costType.contains=" + UPDATED_COST_TYPE);
    }

    @Test
    @Transactional
    void getAllProjectsByCostTypeNotContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where costType does not contain DEFAULT_COST_TYPE
        defaultProjectsShouldNotBeFound("costType.doesNotContain=" + DEFAULT_COST_TYPE);

        // Get all the projectsList where costType does not contain UPDATED_COST_TYPE
        defaultProjectsShouldBeFound("costType.doesNotContain=" + UPDATED_COST_TYPE);
    }

    @Test
    @Transactional
    void getAllProjectsByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where priority equals to DEFAULT_PRIORITY
        defaultProjectsShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the projectsList where priority equals to UPDATED_PRIORITY
        defaultProjectsShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllProjectsByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultProjectsShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the projectsList where priority equals to UPDATED_PRIORITY
        defaultProjectsShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllProjectsByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where priority is not null
        defaultProjectsShouldBeFound("priority.specified=true");

        // Get all the projectsList where priority is null
        defaultProjectsShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByPriorityContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where priority contains DEFAULT_PRIORITY
        defaultProjectsShouldBeFound("priority.contains=" + DEFAULT_PRIORITY);

        // Get all the projectsList where priority contains UPDATED_PRIORITY
        defaultProjectsShouldNotBeFound("priority.contains=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllProjectsByPriorityNotContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where priority does not contain DEFAULT_PRIORITY
        defaultProjectsShouldNotBeFound("priority.doesNotContain=" + DEFAULT_PRIORITY);

        // Get all the projectsList where priority does not contain UPDATED_PRIORITY
        defaultProjectsShouldBeFound("priority.doesNotContain=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllProjectsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where startDate equals to DEFAULT_START_DATE
        defaultProjectsShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the projectsList where startDate equals to UPDATED_START_DATE
        defaultProjectsShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultProjectsShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the projectsList where startDate equals to UPDATED_START_DATE
        defaultProjectsShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where startDate is not null
        defaultProjectsShouldBeFound("startDate.specified=true");

        // Get all the projectsList where startDate is null
        defaultProjectsShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where endDate equals to DEFAULT_END_DATE
        defaultProjectsShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the projectsList where endDate equals to UPDATED_END_DATE
        defaultProjectsShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultProjectsShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the projectsList where endDate equals to UPDATED_END_DATE
        defaultProjectsShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where endDate is not null
        defaultProjectsShouldBeFound("endDate.specified=true");

        // Get all the projectsList where endDate is null
        defaultProjectsShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByDeadLineIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where deadLine equals to DEFAULT_DEAD_LINE
        defaultProjectsShouldBeFound("deadLine.equals=" + DEFAULT_DEAD_LINE);

        // Get all the projectsList where deadLine equals to UPDATED_DEAD_LINE
        defaultProjectsShouldNotBeFound("deadLine.equals=" + UPDATED_DEAD_LINE);
    }

    @Test
    @Transactional
    void getAllProjectsByDeadLineIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where deadLine in DEFAULT_DEAD_LINE or UPDATED_DEAD_LINE
        defaultProjectsShouldBeFound("deadLine.in=" + DEFAULT_DEAD_LINE + "," + UPDATED_DEAD_LINE);

        // Get all the projectsList where deadLine equals to UPDATED_DEAD_LINE
        defaultProjectsShouldNotBeFound("deadLine.in=" + UPDATED_DEAD_LINE);
    }

    @Test
    @Transactional
    void getAllProjectsByDeadLineIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where deadLine is not null
        defaultProjectsShouldBeFound("deadLine.specified=true");

        // Get all the projectsList where deadLine is null
        defaultProjectsShouldNotBeFound("deadLine.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where status equals to DEFAULT_STATUS
        defaultProjectsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the projectsList where status equals to UPDATED_STATUS
        defaultProjectsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllProjectsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultProjectsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the projectsList where status equals to UPDATED_STATUS
        defaultProjectsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllProjectsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where status is not null
        defaultProjectsShouldBeFound("status.specified=true");

        // Get all the projectsList where status is null
        defaultProjectsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByProjectLeadIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectLead equals to DEFAULT_PROJECT_LEAD
        defaultProjectsShouldBeFound("projectLead.equals=" + DEFAULT_PROJECT_LEAD);

        // Get all the projectsList where projectLead equals to UPDATED_PROJECT_LEAD
        defaultProjectsShouldNotBeFound("projectLead.equals=" + UPDATED_PROJECT_LEAD);
    }

    @Test
    @Transactional
    void getAllProjectsByProjectLeadIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectLead in DEFAULT_PROJECT_LEAD or UPDATED_PROJECT_LEAD
        defaultProjectsShouldBeFound("projectLead.in=" + DEFAULT_PROJECT_LEAD + "," + UPDATED_PROJECT_LEAD);

        // Get all the projectsList where projectLead equals to UPDATED_PROJECT_LEAD
        defaultProjectsShouldNotBeFound("projectLead.in=" + UPDATED_PROJECT_LEAD);
    }

    @Test
    @Transactional
    void getAllProjectsByProjectLeadIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectLead is not null
        defaultProjectsShouldBeFound("projectLead.specified=true");

        // Get all the projectsList where projectLead is null
        defaultProjectsShouldNotBeFound("projectLead.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByProjectLeadContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectLead contains DEFAULT_PROJECT_LEAD
        defaultProjectsShouldBeFound("projectLead.contains=" + DEFAULT_PROJECT_LEAD);

        // Get all the projectsList where projectLead contains UPDATED_PROJECT_LEAD
        defaultProjectsShouldNotBeFound("projectLead.contains=" + UPDATED_PROJECT_LEAD);
    }

    @Test
    @Transactional
    void getAllProjectsByProjectLeadNotContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectLead does not contain DEFAULT_PROJECT_LEAD
        defaultProjectsShouldNotBeFound("projectLead.doesNotContain=" + DEFAULT_PROJECT_LEAD);

        // Get all the projectsList where projectLead does not contain UPDATED_PROJECT_LEAD
        defaultProjectsShouldBeFound("projectLead.doesNotContain=" + UPDATED_PROJECT_LEAD);
    }

    @Test
    @Transactional
    void getAllProjectsByProgressPercentIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where progressPercent equals to DEFAULT_PROGRESS_PERCENT
        defaultProjectsShouldBeFound("progressPercent.equals=" + DEFAULT_PROGRESS_PERCENT);

        // Get all the projectsList where progressPercent equals to UPDATED_PROGRESS_PERCENT
        defaultProjectsShouldNotBeFound("progressPercent.equals=" + UPDATED_PROGRESS_PERCENT);
    }

    @Test
    @Transactional
    void getAllProjectsByProgressPercentIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where progressPercent in DEFAULT_PROGRESS_PERCENT or UPDATED_PROGRESS_PERCENT
        defaultProjectsShouldBeFound("progressPercent.in=" + DEFAULT_PROGRESS_PERCENT + "," + UPDATED_PROGRESS_PERCENT);

        // Get all the projectsList where progressPercent equals to UPDATED_PROGRESS_PERCENT
        defaultProjectsShouldNotBeFound("progressPercent.in=" + UPDATED_PROGRESS_PERCENT);
    }

    @Test
    @Transactional
    void getAllProjectsByProgressPercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where progressPercent is not null
        defaultProjectsShouldBeFound("progressPercent.specified=true");

        // Get all the projectsList where progressPercent is null
        defaultProjectsShouldNotBeFound("progressPercent.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByProgressPercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where progressPercent is greater than or equal to DEFAULT_PROGRESS_PERCENT
        defaultProjectsShouldBeFound("progressPercent.greaterThanOrEqual=" + DEFAULT_PROGRESS_PERCENT);

        // Get all the projectsList where progressPercent is greater than or equal to UPDATED_PROGRESS_PERCENT
        defaultProjectsShouldNotBeFound("progressPercent.greaterThanOrEqual=" + UPDATED_PROGRESS_PERCENT);
    }

    @Test
    @Transactional
    void getAllProjectsByProgressPercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where progressPercent is less than or equal to DEFAULT_PROGRESS_PERCENT
        defaultProjectsShouldBeFound("progressPercent.lessThanOrEqual=" + DEFAULT_PROGRESS_PERCENT);

        // Get all the projectsList where progressPercent is less than or equal to SMALLER_PROGRESS_PERCENT
        defaultProjectsShouldNotBeFound("progressPercent.lessThanOrEqual=" + SMALLER_PROGRESS_PERCENT);
    }

    @Test
    @Transactional
    void getAllProjectsByProgressPercentIsLessThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where progressPercent is less than DEFAULT_PROGRESS_PERCENT
        defaultProjectsShouldNotBeFound("progressPercent.lessThan=" + DEFAULT_PROGRESS_PERCENT);

        // Get all the projectsList where progressPercent is less than UPDATED_PROGRESS_PERCENT
        defaultProjectsShouldBeFound("progressPercent.lessThan=" + UPDATED_PROGRESS_PERCENT);
    }

    @Test
    @Transactional
    void getAllProjectsByProgressPercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where progressPercent is greater than DEFAULT_PROGRESS_PERCENT
        defaultProjectsShouldNotBeFound("progressPercent.greaterThan=" + DEFAULT_PROGRESS_PERCENT);

        // Get all the projectsList where progressPercent is greater than SMALLER_PROGRESS_PERCENT
        defaultProjectsShouldBeFound("progressPercent.greaterThan=" + SMALLER_PROGRESS_PERCENT);
    }

    @Test
    @Transactional
    void getAllProjectsByOpenTasksNoIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where openTasksNo equals to DEFAULT_OPEN_TASKS_NO
        defaultProjectsShouldBeFound("openTasksNo.equals=" + DEFAULT_OPEN_TASKS_NO);

        // Get all the projectsList where openTasksNo equals to UPDATED_OPEN_TASKS_NO
        defaultProjectsShouldNotBeFound("openTasksNo.equals=" + UPDATED_OPEN_TASKS_NO);
    }

    @Test
    @Transactional
    void getAllProjectsByOpenTasksNoIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where openTasksNo in DEFAULT_OPEN_TASKS_NO or UPDATED_OPEN_TASKS_NO
        defaultProjectsShouldBeFound("openTasksNo.in=" + DEFAULT_OPEN_TASKS_NO + "," + UPDATED_OPEN_TASKS_NO);

        // Get all the projectsList where openTasksNo equals to UPDATED_OPEN_TASKS_NO
        defaultProjectsShouldNotBeFound("openTasksNo.in=" + UPDATED_OPEN_TASKS_NO);
    }

    @Test
    @Transactional
    void getAllProjectsByOpenTasksNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where openTasksNo is not null
        defaultProjectsShouldBeFound("openTasksNo.specified=true");

        // Get all the projectsList where openTasksNo is null
        defaultProjectsShouldNotBeFound("openTasksNo.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByOpenTasksNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where openTasksNo is greater than or equal to DEFAULT_OPEN_TASKS_NO
        defaultProjectsShouldBeFound("openTasksNo.greaterThanOrEqual=" + DEFAULT_OPEN_TASKS_NO);

        // Get all the projectsList where openTasksNo is greater than or equal to UPDATED_OPEN_TASKS_NO
        defaultProjectsShouldNotBeFound("openTasksNo.greaterThanOrEqual=" + UPDATED_OPEN_TASKS_NO);
    }

    @Test
    @Transactional
    void getAllProjectsByOpenTasksNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where openTasksNo is less than or equal to DEFAULT_OPEN_TASKS_NO
        defaultProjectsShouldBeFound("openTasksNo.lessThanOrEqual=" + DEFAULT_OPEN_TASKS_NO);

        // Get all the projectsList where openTasksNo is less than or equal to SMALLER_OPEN_TASKS_NO
        defaultProjectsShouldNotBeFound("openTasksNo.lessThanOrEqual=" + SMALLER_OPEN_TASKS_NO);
    }

    @Test
    @Transactional
    void getAllProjectsByOpenTasksNoIsLessThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where openTasksNo is less than DEFAULT_OPEN_TASKS_NO
        defaultProjectsShouldNotBeFound("openTasksNo.lessThan=" + DEFAULT_OPEN_TASKS_NO);

        // Get all the projectsList where openTasksNo is less than UPDATED_OPEN_TASKS_NO
        defaultProjectsShouldBeFound("openTasksNo.lessThan=" + UPDATED_OPEN_TASKS_NO);
    }

    @Test
    @Transactional
    void getAllProjectsByOpenTasksNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where openTasksNo is greater than DEFAULT_OPEN_TASKS_NO
        defaultProjectsShouldNotBeFound("openTasksNo.greaterThan=" + DEFAULT_OPEN_TASKS_NO);

        // Get all the projectsList where openTasksNo is greater than SMALLER_OPEN_TASKS_NO
        defaultProjectsShouldBeFound("openTasksNo.greaterThan=" + SMALLER_OPEN_TASKS_NO);
    }

    @Test
    @Transactional
    void getAllProjectsByCompleteTasksNoIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where completeTasksNo equals to DEFAULT_COMPLETE_TASKS_NO
        defaultProjectsShouldBeFound("completeTasksNo.equals=" + DEFAULT_COMPLETE_TASKS_NO);

        // Get all the projectsList where completeTasksNo equals to UPDATED_COMPLETE_TASKS_NO
        defaultProjectsShouldNotBeFound("completeTasksNo.equals=" + UPDATED_COMPLETE_TASKS_NO);
    }

    @Test
    @Transactional
    void getAllProjectsByCompleteTasksNoIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where completeTasksNo in DEFAULT_COMPLETE_TASKS_NO or UPDATED_COMPLETE_TASKS_NO
        defaultProjectsShouldBeFound("completeTasksNo.in=" + DEFAULT_COMPLETE_TASKS_NO + "," + UPDATED_COMPLETE_TASKS_NO);

        // Get all the projectsList where completeTasksNo equals to UPDATED_COMPLETE_TASKS_NO
        defaultProjectsShouldNotBeFound("completeTasksNo.in=" + UPDATED_COMPLETE_TASKS_NO);
    }

    @Test
    @Transactional
    void getAllProjectsByCompleteTasksNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where completeTasksNo is not null
        defaultProjectsShouldBeFound("completeTasksNo.specified=true");

        // Get all the projectsList where completeTasksNo is null
        defaultProjectsShouldNotBeFound("completeTasksNo.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByCompleteTasksNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where completeTasksNo is greater than or equal to DEFAULT_COMPLETE_TASKS_NO
        defaultProjectsShouldBeFound("completeTasksNo.greaterThanOrEqual=" + DEFAULT_COMPLETE_TASKS_NO);

        // Get all the projectsList where completeTasksNo is greater than or equal to UPDATED_COMPLETE_TASKS_NO
        defaultProjectsShouldNotBeFound("completeTasksNo.greaterThanOrEqual=" + UPDATED_COMPLETE_TASKS_NO);
    }

    @Test
    @Transactional
    void getAllProjectsByCompleteTasksNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where completeTasksNo is less than or equal to DEFAULT_COMPLETE_TASKS_NO
        defaultProjectsShouldBeFound("completeTasksNo.lessThanOrEqual=" + DEFAULT_COMPLETE_TASKS_NO);

        // Get all the projectsList where completeTasksNo is less than or equal to SMALLER_COMPLETE_TASKS_NO
        defaultProjectsShouldNotBeFound("completeTasksNo.lessThanOrEqual=" + SMALLER_COMPLETE_TASKS_NO);
    }

    @Test
    @Transactional
    void getAllProjectsByCompleteTasksNoIsLessThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where completeTasksNo is less than DEFAULT_COMPLETE_TASKS_NO
        defaultProjectsShouldNotBeFound("completeTasksNo.lessThan=" + DEFAULT_COMPLETE_TASKS_NO);

        // Get all the projectsList where completeTasksNo is less than UPDATED_COMPLETE_TASKS_NO
        defaultProjectsShouldBeFound("completeTasksNo.lessThan=" + UPDATED_COMPLETE_TASKS_NO);
    }

    @Test
    @Transactional
    void getAllProjectsByCompleteTasksNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where completeTasksNo is greater than DEFAULT_COMPLETE_TASKS_NO
        defaultProjectsShouldNotBeFound("completeTasksNo.greaterThan=" + DEFAULT_COMPLETE_TASKS_NO);

        // Get all the projectsList where completeTasksNo is greater than SMALLER_COMPLETE_TASKS_NO
        defaultProjectsShouldBeFound("completeTasksNo.greaterThan=" + SMALLER_COMPLETE_TASKS_NO);
    }

    @Test
    @Transactional
    void getAllProjectsByProjectFileIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectFile equals to DEFAULT_PROJECT_FILE
        defaultProjectsShouldBeFound("projectFile.equals=" + DEFAULT_PROJECT_FILE);

        // Get all the projectsList where projectFile equals to UPDATED_PROJECT_FILE
        defaultProjectsShouldNotBeFound("projectFile.equals=" + UPDATED_PROJECT_FILE);
    }

    @Test
    @Transactional
    void getAllProjectsByProjectFileIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectFile in DEFAULT_PROJECT_FILE or UPDATED_PROJECT_FILE
        defaultProjectsShouldBeFound("projectFile.in=" + DEFAULT_PROJECT_FILE + "," + UPDATED_PROJECT_FILE);

        // Get all the projectsList where projectFile equals to UPDATED_PROJECT_FILE
        defaultProjectsShouldNotBeFound("projectFile.in=" + UPDATED_PROJECT_FILE);
    }

    @Test
    @Transactional
    void getAllProjectsByProjectFileIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectFile is not null
        defaultProjectsShouldBeFound("projectFile.specified=true");

        // Get all the projectsList where projectFile is null
        defaultProjectsShouldNotBeFound("projectFile.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByProjectFileContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectFile contains DEFAULT_PROJECT_FILE
        defaultProjectsShouldBeFound("projectFile.contains=" + DEFAULT_PROJECT_FILE);

        // Get all the projectsList where projectFile contains UPDATED_PROJECT_FILE
        defaultProjectsShouldNotBeFound("projectFile.contains=" + UPDATED_PROJECT_FILE);
    }

    @Test
    @Transactional
    void getAllProjectsByProjectFileNotContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectFile does not contain DEFAULT_PROJECT_FILE
        defaultProjectsShouldNotBeFound("projectFile.doesNotContain=" + DEFAULT_PROJECT_FILE);

        // Get all the projectsList where projectFile does not contain UPDATED_PROJECT_FILE
        defaultProjectsShouldBeFound("projectFile.doesNotContain=" + UPDATED_PROJECT_FILE);
    }

    @Test
    @Transactional
    void getAllProjectsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultProjectsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the projectsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultProjectsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProjectsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultProjectsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the projectsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultProjectsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProjectsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where lastModified is not null
        defaultProjectsShouldBeFound("lastModified.specified=true");

        // Get all the projectsList where lastModified is null
        defaultProjectsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProjectsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the projectsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProjectsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProjectsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProjectsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the projectsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProjectsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProjectsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where lastModifiedBy is not null
        defaultProjectsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the projectsList where lastModifiedBy is null
        defaultProjectsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProjectsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the projectsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProjectsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProjectsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProjectsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the projectsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProjectsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProjectsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdBy equals to DEFAULT_CREATED_BY
        defaultProjectsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the projectsList where createdBy equals to UPDATED_CREATED_BY
        defaultProjectsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProjectsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProjectsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the projectsList where createdBy equals to UPDATED_CREATED_BY
        defaultProjectsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProjectsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdBy is not null
        defaultProjectsShouldBeFound("createdBy.specified=true");

        // Get all the projectsList where createdBy is null
        defaultProjectsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdBy contains DEFAULT_CREATED_BY
        defaultProjectsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the projectsList where createdBy contains UPDATED_CREATED_BY
        defaultProjectsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProjectsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProjectsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the projectsList where createdBy does not contain UPDATED_CREATED_BY
        defaultProjectsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProjectsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdOn equals to DEFAULT_CREATED_ON
        defaultProjectsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the projectsList where createdOn equals to UPDATED_CREATED_ON
        defaultProjectsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllProjectsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultProjectsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the projectsList where createdOn equals to UPDATED_CREATED_ON
        defaultProjectsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllProjectsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdOn is not null
        defaultProjectsShouldBeFound("createdOn.specified=true");

        // Get all the projectsList where createdOn is null
        defaultProjectsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where deleted equals to DEFAULT_DELETED
        defaultProjectsShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the projectsList where deleted equals to UPDATED_DELETED
        defaultProjectsShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllProjectsByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultProjectsShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the projectsList where deleted equals to UPDATED_DELETED
        defaultProjectsShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllProjectsByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where deleted is not null
        defaultProjectsShouldBeFound("deleted.specified=true");

        // Get all the projectsList where deleted is null
        defaultProjectsShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where companyId equals to DEFAULT_COMPANY_ID
        defaultProjectsShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the projectsList where companyId equals to UPDATED_COMPANY_ID
        defaultProjectsShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllProjectsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultProjectsShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the projectsList where companyId equals to UPDATED_COMPANY_ID
        defaultProjectsShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllProjectsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where companyId is not null
        defaultProjectsShouldBeFound("companyId.specified=true");

        // Get all the projectsList where companyId is null
        defaultProjectsShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultProjectsShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the projectsList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultProjectsShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllProjectsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultProjectsShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the projectsList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultProjectsShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllProjectsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where companyId is less than DEFAULT_COMPANY_ID
        defaultProjectsShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the projectsList where companyId is less than UPDATED_COMPANY_ID
        defaultProjectsShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllProjectsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where companyId is greater than DEFAULT_COMPANY_ID
        defaultProjectsShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the projectsList where companyId is greater than SMALLER_COMPANY_ID
        defaultProjectsShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllProjectsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultProjectsShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the projectsList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultProjectsShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllProjectsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultProjectsShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the projectsList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultProjectsShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllProjectsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where employeeId is not null
        defaultProjectsShouldBeFound("employeeId.specified=true");

        // Get all the projectsList where employeeId is null
        defaultProjectsShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultProjectsShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the projectsList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultProjectsShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllProjectsByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultProjectsShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the projectsList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultProjectsShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllProjectsByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultProjectsShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the projectsList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultProjectsShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllProjectsByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultProjectsShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the projectsList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultProjectsShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectsShouldBeFound(String filter) throws Exception {
        restProjectsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projects.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME)))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].costType").value(hasItem(DEFAULT_COST_TYPE)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].deadLine").value(hasItem(DEFAULT_DEAD_LINE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].projectLead").value(hasItem(DEFAULT_PROJECT_LEAD)))
            .andExpect(jsonPath("$.[*].progressPercent").value(hasItem(DEFAULT_PROGRESS_PERCENT.doubleValue())))
            .andExpect(jsonPath("$.[*].openTasksNo").value(hasItem(DEFAULT_OPEN_TASKS_NO.intValue())))
            .andExpect(jsonPath("$.[*].completeTasksNo").value(hasItem(DEFAULT_COMPLETE_TASKS_NO.intValue())))
            .andExpect(jsonPath("$.[*].projectLogoContentType").value(hasItem(DEFAULT_PROJECT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].projectLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROJECT_LOGO))))
            .andExpect(jsonPath("$.[*].projectFile").value(hasItem(DEFAULT_PROJECT_FILE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())));

        // Check, that the count call also returns 1
        restProjectsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectsShouldNotBeFound(String filter) throws Exception {
        restProjectsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProjects() throws Exception {
        // Get the projects
        restProjectsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();

        // Update the projects
        Projects updatedProjects = projectsRepository.findById(projects.getId()).get();
        // Disconnect from session so that the updates on updatedProjects are not directly saved in db
        em.detach(updatedProjects);
        updatedProjects
            .projectName(UPDATED_PROJECT_NAME)
            .description(UPDATED_DESCRIPTION)
            .clientName(UPDATED_CLIENT_NAME)
            .cost(UPDATED_COST)
            .costType(UPDATED_COST_TYPE)
            .priority(UPDATED_PRIORITY)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .deadLine(UPDATED_DEAD_LINE)
            .status(UPDATED_STATUS)
            .projectLead(UPDATED_PROJECT_LEAD)
            .progressPercent(UPDATED_PROGRESS_PERCENT)
            .openTasksNo(UPDATED_OPEN_TASKS_NO)
            .completeTasksNo(UPDATED_COMPLETE_TASKS_NO)
            .projectLogo(UPDATED_PROJECT_LOGO)
            .projectLogoContentType(UPDATED_PROJECT_LOGO_CONTENT_TYPE)
            .projectFile(UPDATED_PROJECT_FILE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED)
            .companyId(UPDATED_COMPANY_ID)
            .employeeId(UPDATED_EMPLOYEE_ID);
        ProjectsDTO projectsDTO = projectsMapper.toDto(updatedProjects);

        restProjectsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
        Projects testProjects = projectsList.get(projectsList.size() - 1);
        assertThat(testProjects.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testProjects.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjects.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testProjects.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testProjects.getCostType()).isEqualTo(UPDATED_COST_TYPE);
        assertThat(testProjects.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testProjects.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProjects.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testProjects.getDeadLine()).isEqualTo(UPDATED_DEAD_LINE);
        assertThat(testProjects.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProjects.getProjectLead()).isEqualTo(UPDATED_PROJECT_LEAD);
        assertThat(testProjects.getProgressPercent()).isEqualTo(UPDATED_PROGRESS_PERCENT);
        assertThat(testProjects.getOpenTasksNo()).isEqualTo(UPDATED_OPEN_TASKS_NO);
        assertThat(testProjects.getCompleteTasksNo()).isEqualTo(UPDATED_COMPLETE_TASKS_NO);
        assertThat(testProjects.getProjectLogo()).isEqualTo(UPDATED_PROJECT_LOGO);
        assertThat(testProjects.getProjectLogoContentType()).isEqualTo(UPDATED_PROJECT_LOGO_CONTENT_TYPE);
        assertThat(testProjects.getProjectFile()).isEqualTo(UPDATED_PROJECT_FILE);
        assertThat(testProjects.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProjects.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProjects.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProjects.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testProjects.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testProjects.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testProjects.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void putNonExistingProjects() throws Exception {
        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();
        projects.setId(count.incrementAndGet());

        // Create the Projects
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjects() throws Exception {
        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();
        projects.setId(count.incrementAndGet());

        // Create the Projects
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjects() throws Exception {
        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();
        projects.setId(count.incrementAndGet());

        // Create the Projects
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectsWithPatch() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();

        // Update the projects using partial update
        Projects partialUpdatedProjects = new Projects();
        partialUpdatedProjects.setId(projects.getId());

        partialUpdatedProjects
            .projectName(UPDATED_PROJECT_NAME)
            .cost(UPDATED_COST)
            .deadLine(UPDATED_DEAD_LINE)
            .projectLead(UPDATED_PROJECT_LEAD)
            .completeTasksNo(UPDATED_COMPLETE_TASKS_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjects.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjects))
            )
            .andExpect(status().isOk());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
        Projects testProjects = projectsList.get(projectsList.size() - 1);
        assertThat(testProjects.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testProjects.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjects.getClientName()).isEqualTo(DEFAULT_CLIENT_NAME);
        assertThat(testProjects.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testProjects.getCostType()).isEqualTo(DEFAULT_COST_TYPE);
        assertThat(testProjects.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testProjects.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProjects.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testProjects.getDeadLine()).isEqualTo(UPDATED_DEAD_LINE);
        assertThat(testProjects.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProjects.getProjectLead()).isEqualTo(UPDATED_PROJECT_LEAD);
        assertThat(testProjects.getProgressPercent()).isEqualTo(DEFAULT_PROGRESS_PERCENT);
        assertThat(testProjects.getOpenTasksNo()).isEqualTo(DEFAULT_OPEN_TASKS_NO);
        assertThat(testProjects.getCompleteTasksNo()).isEqualTo(UPDATED_COMPLETE_TASKS_NO);
        assertThat(testProjects.getProjectLogo()).isEqualTo(DEFAULT_PROJECT_LOGO);
        assertThat(testProjects.getProjectLogoContentType()).isEqualTo(DEFAULT_PROJECT_LOGO_CONTENT_TYPE);
        assertThat(testProjects.getProjectFile()).isEqualTo(DEFAULT_PROJECT_FILE);
        assertThat(testProjects.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProjects.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProjects.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProjects.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testProjects.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testProjects.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testProjects.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void fullUpdateProjectsWithPatch() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();

        // Update the projects using partial update
        Projects partialUpdatedProjects = new Projects();
        partialUpdatedProjects.setId(projects.getId());

        partialUpdatedProjects
            .projectName(UPDATED_PROJECT_NAME)
            .description(UPDATED_DESCRIPTION)
            .clientName(UPDATED_CLIENT_NAME)
            .cost(UPDATED_COST)
            .costType(UPDATED_COST_TYPE)
            .priority(UPDATED_PRIORITY)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .deadLine(UPDATED_DEAD_LINE)
            .status(UPDATED_STATUS)
            .projectLead(UPDATED_PROJECT_LEAD)
            .progressPercent(UPDATED_PROGRESS_PERCENT)
            .openTasksNo(UPDATED_OPEN_TASKS_NO)
            .completeTasksNo(UPDATED_COMPLETE_TASKS_NO)
            .projectLogo(UPDATED_PROJECT_LOGO)
            .projectLogoContentType(UPDATED_PROJECT_LOGO_CONTENT_TYPE)
            .projectFile(UPDATED_PROJECT_FILE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED)
            .companyId(UPDATED_COMPANY_ID)
            .employeeId(UPDATED_EMPLOYEE_ID);

        restProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjects.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjects))
            )
            .andExpect(status().isOk());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
        Projects testProjects = projectsList.get(projectsList.size() - 1);
        assertThat(testProjects.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testProjects.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjects.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testProjects.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testProjects.getCostType()).isEqualTo(UPDATED_COST_TYPE);
        assertThat(testProjects.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testProjects.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProjects.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testProjects.getDeadLine()).isEqualTo(UPDATED_DEAD_LINE);
        assertThat(testProjects.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProjects.getProjectLead()).isEqualTo(UPDATED_PROJECT_LEAD);
        assertThat(testProjects.getProgressPercent()).isEqualTo(UPDATED_PROGRESS_PERCENT);
        assertThat(testProjects.getOpenTasksNo()).isEqualTo(UPDATED_OPEN_TASKS_NO);
        assertThat(testProjects.getCompleteTasksNo()).isEqualTo(UPDATED_COMPLETE_TASKS_NO);
        assertThat(testProjects.getProjectLogo()).isEqualTo(UPDATED_PROJECT_LOGO);
        assertThat(testProjects.getProjectLogoContentType()).isEqualTo(UPDATED_PROJECT_LOGO_CONTENT_TYPE);
        assertThat(testProjects.getProjectFile()).isEqualTo(UPDATED_PROJECT_FILE);
        assertThat(testProjects.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProjects.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProjects.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProjects.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testProjects.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testProjects.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testProjects.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingProjects() throws Exception {
        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();
        projects.setId(count.incrementAndGet());

        // Create the Projects
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjects() throws Exception {
        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();
        projects.setId(count.incrementAndGet());

        // Create the Projects
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjects() throws Exception {
        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();
        projects.setId(count.incrementAndGet());

        // Create the Projects
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(projectsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        int databaseSizeBeforeDelete = projectsRepository.findAll().size();

        // Delete the projects
        restProjectsMockMvc
            .perform(delete(ENTITY_API_URL_ID, projects.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
