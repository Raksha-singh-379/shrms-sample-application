package com.techvg.shrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.shrms.IntegrationTest;
import com.techvg.shrms.domain.ProjectTeams;
import com.techvg.shrms.repository.ProjectTeamsRepository;
import com.techvg.shrms.service.criteria.ProjectTeamsCriteria;
import com.techvg.shrms.service.dto.ProjectTeamsDTO;
import com.techvg.shrms.service.mapper.ProjectTeamsMapper;
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
 * Integration tests for the {@link ProjectTeamsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectTeamsResourceIT {

    private static final String DEFAULT_TEAM_MEMBER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_MEMBER_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long UPDATED_PROJECT_ID = 2L;
    private static final Long SMALLER_PROJECT_ID = 1L - 1L;

    private static final Long DEFAULT_EMPLOYE_ID = 1L;
    private static final Long UPDATED_EMPLOYE_ID = 2L;
    private static final Long SMALLER_EMPLOYE_ID = 1L - 1L;

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

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/project-teams";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectTeamsRepository projectTeamsRepository;

    @Autowired
    private ProjectTeamsMapper projectTeamsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectTeamsMockMvc;

    private ProjectTeams projectTeams;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectTeams createEntity(EntityManager em) {
        ProjectTeams projectTeams = new ProjectTeams()
            .teamMemberType(DEFAULT_TEAM_MEMBER_TYPE)
            .projectId(DEFAULT_PROJECT_ID)
            .employeId(DEFAULT_EMPLOYE_ID)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .companyId(DEFAULT_COMPANY_ID);
        return projectTeams;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectTeams createUpdatedEntity(EntityManager em) {
        ProjectTeams projectTeams = new ProjectTeams()
            .teamMemberType(UPDATED_TEAM_MEMBER_TYPE)
            .projectId(UPDATED_PROJECT_ID)
            .employeId(UPDATED_EMPLOYE_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .companyId(UPDATED_COMPANY_ID);
        return projectTeams;
    }

    @BeforeEach
    public void initTest() {
        projectTeams = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectTeams() throws Exception {
        int databaseSizeBeforeCreate = projectTeamsRepository.findAll().size();
        // Create the ProjectTeams
        ProjectTeamsDTO projectTeamsDTO = projectTeamsMapper.toDto(projectTeams);
        restProjectTeamsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectTeamsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectTeams in the database
        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectTeams testProjectTeams = projectTeamsList.get(projectTeamsList.size() - 1);
        assertThat(testProjectTeams.getTeamMemberType()).isEqualTo(DEFAULT_TEAM_MEMBER_TYPE);
        assertThat(testProjectTeams.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testProjectTeams.getEmployeId()).isEqualTo(DEFAULT_EMPLOYE_ID);
        assertThat(testProjectTeams.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testProjectTeams.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProjectTeams.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProjectTeams.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testProjectTeams.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testProjectTeams.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
    }

    @Test
    @Transactional
    void createProjectTeamsWithExistingId() throws Exception {
        // Create the ProjectTeams with an existing ID
        projectTeams.setId(1L);
        ProjectTeamsDTO projectTeamsDTO = projectTeamsMapper.toDto(projectTeams);

        int databaseSizeBeforeCreate = projectTeamsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectTeamsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectTeamsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectTeams in the database
        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectTeams() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList
        restProjectTeamsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectTeams.getId().intValue())))
            .andExpect(jsonPath("$.[*].teamMemberType").value(hasItem(DEFAULT_TEAM_MEMBER_TYPE)))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].employeId").value(hasItem(DEFAULT_EMPLOYE_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())));
    }

    @Test
    @Transactional
    void getProjectTeams() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get the projectTeams
        restProjectTeamsMockMvc
            .perform(get(ENTITY_API_URL_ID, projectTeams.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectTeams.getId().intValue()))
            .andExpect(jsonPath("$.teamMemberType").value(DEFAULT_TEAM_MEMBER_TYPE))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.intValue()))
            .andExpect(jsonPath("$.employeId").value(DEFAULT_EMPLOYE_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()));
    }

    @Test
    @Transactional
    void getProjectTeamsByIdFiltering() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        Long id = projectTeams.getId();

        defaultProjectTeamsShouldBeFound("id.equals=" + id);
        defaultProjectTeamsShouldNotBeFound("id.notEquals=" + id);

        defaultProjectTeamsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProjectTeamsShouldNotBeFound("id.greaterThan=" + id);

        defaultProjectTeamsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProjectTeamsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByTeamMemberTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where teamMemberType equals to DEFAULT_TEAM_MEMBER_TYPE
        defaultProjectTeamsShouldBeFound("teamMemberType.equals=" + DEFAULT_TEAM_MEMBER_TYPE);

        // Get all the projectTeamsList where teamMemberType equals to UPDATED_TEAM_MEMBER_TYPE
        defaultProjectTeamsShouldNotBeFound("teamMemberType.equals=" + UPDATED_TEAM_MEMBER_TYPE);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByTeamMemberTypeIsInShouldWork() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where teamMemberType in DEFAULT_TEAM_MEMBER_TYPE or UPDATED_TEAM_MEMBER_TYPE
        defaultProjectTeamsShouldBeFound("teamMemberType.in=" + DEFAULT_TEAM_MEMBER_TYPE + "," + UPDATED_TEAM_MEMBER_TYPE);

        // Get all the projectTeamsList where teamMemberType equals to UPDATED_TEAM_MEMBER_TYPE
        defaultProjectTeamsShouldNotBeFound("teamMemberType.in=" + UPDATED_TEAM_MEMBER_TYPE);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByTeamMemberTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where teamMemberType is not null
        defaultProjectTeamsShouldBeFound("teamMemberType.specified=true");

        // Get all the projectTeamsList where teamMemberType is null
        defaultProjectTeamsShouldNotBeFound("teamMemberType.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectTeamsByTeamMemberTypeContainsSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where teamMemberType contains DEFAULT_TEAM_MEMBER_TYPE
        defaultProjectTeamsShouldBeFound("teamMemberType.contains=" + DEFAULT_TEAM_MEMBER_TYPE);

        // Get all the projectTeamsList where teamMemberType contains UPDATED_TEAM_MEMBER_TYPE
        defaultProjectTeamsShouldNotBeFound("teamMemberType.contains=" + UPDATED_TEAM_MEMBER_TYPE);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByTeamMemberTypeNotContainsSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where teamMemberType does not contain DEFAULT_TEAM_MEMBER_TYPE
        defaultProjectTeamsShouldNotBeFound("teamMemberType.doesNotContain=" + DEFAULT_TEAM_MEMBER_TYPE);

        // Get all the projectTeamsList where teamMemberType does not contain UPDATED_TEAM_MEMBER_TYPE
        defaultProjectTeamsShouldBeFound("teamMemberType.doesNotContain=" + UPDATED_TEAM_MEMBER_TYPE);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByProjectIdIsEqualToSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where projectId equals to DEFAULT_PROJECT_ID
        defaultProjectTeamsShouldBeFound("projectId.equals=" + DEFAULT_PROJECT_ID);

        // Get all the projectTeamsList where projectId equals to UPDATED_PROJECT_ID
        defaultProjectTeamsShouldNotBeFound("projectId.equals=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByProjectIdIsInShouldWork() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where projectId in DEFAULT_PROJECT_ID or UPDATED_PROJECT_ID
        defaultProjectTeamsShouldBeFound("projectId.in=" + DEFAULT_PROJECT_ID + "," + UPDATED_PROJECT_ID);

        // Get all the projectTeamsList where projectId equals to UPDATED_PROJECT_ID
        defaultProjectTeamsShouldNotBeFound("projectId.in=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByProjectIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where projectId is not null
        defaultProjectTeamsShouldBeFound("projectId.specified=true");

        // Get all the projectTeamsList where projectId is null
        defaultProjectTeamsShouldNotBeFound("projectId.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectTeamsByProjectIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where projectId is greater than or equal to DEFAULT_PROJECT_ID
        defaultProjectTeamsShouldBeFound("projectId.greaterThanOrEqual=" + DEFAULT_PROJECT_ID);

        // Get all the projectTeamsList where projectId is greater than or equal to UPDATED_PROJECT_ID
        defaultProjectTeamsShouldNotBeFound("projectId.greaterThanOrEqual=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByProjectIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where projectId is less than or equal to DEFAULT_PROJECT_ID
        defaultProjectTeamsShouldBeFound("projectId.lessThanOrEqual=" + DEFAULT_PROJECT_ID);

        // Get all the projectTeamsList where projectId is less than or equal to SMALLER_PROJECT_ID
        defaultProjectTeamsShouldNotBeFound("projectId.lessThanOrEqual=" + SMALLER_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByProjectIdIsLessThanSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where projectId is less than DEFAULT_PROJECT_ID
        defaultProjectTeamsShouldNotBeFound("projectId.lessThan=" + DEFAULT_PROJECT_ID);

        // Get all the projectTeamsList where projectId is less than UPDATED_PROJECT_ID
        defaultProjectTeamsShouldBeFound("projectId.lessThan=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByProjectIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where projectId is greater than DEFAULT_PROJECT_ID
        defaultProjectTeamsShouldNotBeFound("projectId.greaterThan=" + DEFAULT_PROJECT_ID);

        // Get all the projectTeamsList where projectId is greater than SMALLER_PROJECT_ID
        defaultProjectTeamsShouldBeFound("projectId.greaterThan=" + SMALLER_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByEmployeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where employeId equals to DEFAULT_EMPLOYE_ID
        defaultProjectTeamsShouldBeFound("employeId.equals=" + DEFAULT_EMPLOYE_ID);

        // Get all the projectTeamsList where employeId equals to UPDATED_EMPLOYE_ID
        defaultProjectTeamsShouldNotBeFound("employeId.equals=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByEmployeIdIsInShouldWork() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where employeId in DEFAULT_EMPLOYE_ID or UPDATED_EMPLOYE_ID
        defaultProjectTeamsShouldBeFound("employeId.in=" + DEFAULT_EMPLOYE_ID + "," + UPDATED_EMPLOYE_ID);

        // Get all the projectTeamsList where employeId equals to UPDATED_EMPLOYE_ID
        defaultProjectTeamsShouldNotBeFound("employeId.in=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByEmployeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where employeId is not null
        defaultProjectTeamsShouldBeFound("employeId.specified=true");

        // Get all the projectTeamsList where employeId is null
        defaultProjectTeamsShouldNotBeFound("employeId.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectTeamsByEmployeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where employeId is greater than or equal to DEFAULT_EMPLOYE_ID
        defaultProjectTeamsShouldBeFound("employeId.greaterThanOrEqual=" + DEFAULT_EMPLOYE_ID);

        // Get all the projectTeamsList where employeId is greater than or equal to UPDATED_EMPLOYE_ID
        defaultProjectTeamsShouldNotBeFound("employeId.greaterThanOrEqual=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByEmployeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where employeId is less than or equal to DEFAULT_EMPLOYE_ID
        defaultProjectTeamsShouldBeFound("employeId.lessThanOrEqual=" + DEFAULT_EMPLOYE_ID);

        // Get all the projectTeamsList where employeId is less than or equal to SMALLER_EMPLOYE_ID
        defaultProjectTeamsShouldNotBeFound("employeId.lessThanOrEqual=" + SMALLER_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByEmployeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where employeId is less than DEFAULT_EMPLOYE_ID
        defaultProjectTeamsShouldNotBeFound("employeId.lessThan=" + DEFAULT_EMPLOYE_ID);

        // Get all the projectTeamsList where employeId is less than UPDATED_EMPLOYE_ID
        defaultProjectTeamsShouldBeFound("employeId.lessThan=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByEmployeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where employeId is greater than DEFAULT_EMPLOYE_ID
        defaultProjectTeamsShouldNotBeFound("employeId.greaterThan=" + DEFAULT_EMPLOYE_ID);

        // Get all the projectTeamsList where employeId is greater than SMALLER_EMPLOYE_ID
        defaultProjectTeamsShouldBeFound("employeId.greaterThan=" + SMALLER_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultProjectTeamsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the projectTeamsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultProjectTeamsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultProjectTeamsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the projectTeamsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultProjectTeamsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where lastModified is not null
        defaultProjectTeamsShouldBeFound("lastModified.specified=true");

        // Get all the projectTeamsList where lastModified is null
        defaultProjectTeamsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectTeamsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProjectTeamsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the projectTeamsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProjectTeamsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProjectTeamsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the projectTeamsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProjectTeamsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where lastModifiedBy is not null
        defaultProjectTeamsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the projectTeamsList where lastModifiedBy is null
        defaultProjectTeamsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectTeamsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProjectTeamsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the projectTeamsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProjectTeamsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProjectTeamsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the projectTeamsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProjectTeamsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where createdBy equals to DEFAULT_CREATED_BY
        defaultProjectTeamsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the projectTeamsList where createdBy equals to UPDATED_CREATED_BY
        defaultProjectTeamsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProjectTeamsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the projectTeamsList where createdBy equals to UPDATED_CREATED_BY
        defaultProjectTeamsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where createdBy is not null
        defaultProjectTeamsShouldBeFound("createdBy.specified=true");

        // Get all the projectTeamsList where createdBy is null
        defaultProjectTeamsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectTeamsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where createdBy contains DEFAULT_CREATED_BY
        defaultProjectTeamsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the projectTeamsList where createdBy contains UPDATED_CREATED_BY
        defaultProjectTeamsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProjectTeamsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the projectTeamsList where createdBy does not contain UPDATED_CREATED_BY
        defaultProjectTeamsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where createdOn equals to DEFAULT_CREATED_ON
        defaultProjectTeamsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the projectTeamsList where createdOn equals to UPDATED_CREATED_ON
        defaultProjectTeamsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultProjectTeamsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the projectTeamsList where createdOn equals to UPDATED_CREATED_ON
        defaultProjectTeamsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where createdOn is not null
        defaultProjectTeamsShouldBeFound("createdOn.specified=true");

        // Get all the projectTeamsList where createdOn is null
        defaultProjectTeamsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectTeamsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where isDeleted equals to DEFAULT_IS_DELETED
        defaultProjectTeamsShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the projectTeamsList where isDeleted equals to UPDATED_IS_DELETED
        defaultProjectTeamsShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultProjectTeamsShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the projectTeamsList where isDeleted equals to UPDATED_IS_DELETED
        defaultProjectTeamsShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where isDeleted is not null
        defaultProjectTeamsShouldBeFound("isDeleted.specified=true");

        // Get all the projectTeamsList where isDeleted is null
        defaultProjectTeamsShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectTeamsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where companyId equals to DEFAULT_COMPANY_ID
        defaultProjectTeamsShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the projectTeamsList where companyId equals to UPDATED_COMPANY_ID
        defaultProjectTeamsShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultProjectTeamsShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the projectTeamsList where companyId equals to UPDATED_COMPANY_ID
        defaultProjectTeamsShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where companyId is not null
        defaultProjectTeamsShouldBeFound("companyId.specified=true");

        // Get all the projectTeamsList where companyId is null
        defaultProjectTeamsShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectTeamsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultProjectTeamsShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the projectTeamsList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultProjectTeamsShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultProjectTeamsShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the projectTeamsList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultProjectTeamsShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where companyId is less than DEFAULT_COMPANY_ID
        defaultProjectTeamsShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the projectTeamsList where companyId is less than UPDATED_COMPANY_ID
        defaultProjectTeamsShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllProjectTeamsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        // Get all the projectTeamsList where companyId is greater than DEFAULT_COMPANY_ID
        defaultProjectTeamsShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the projectTeamsList where companyId is greater than SMALLER_COMPANY_ID
        defaultProjectTeamsShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectTeamsShouldBeFound(String filter) throws Exception {
        restProjectTeamsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectTeams.getId().intValue())))
            .andExpect(jsonPath("$.[*].teamMemberType").value(hasItem(DEFAULT_TEAM_MEMBER_TYPE)))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].employeId").value(hasItem(DEFAULT_EMPLOYE_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())));

        // Check, that the count call also returns 1
        restProjectTeamsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectTeamsShouldNotBeFound(String filter) throws Exception {
        restProjectTeamsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectTeamsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProjectTeams() throws Exception {
        // Get the projectTeams
        restProjectTeamsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjectTeams() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        int databaseSizeBeforeUpdate = projectTeamsRepository.findAll().size();

        // Update the projectTeams
        ProjectTeams updatedProjectTeams = projectTeamsRepository.findById(projectTeams.getId()).get();
        // Disconnect from session so that the updates on updatedProjectTeams are not directly saved in db
        em.detach(updatedProjectTeams);
        updatedProjectTeams
            .teamMemberType(UPDATED_TEAM_MEMBER_TYPE)
            .projectId(UPDATED_PROJECT_ID)
            .employeId(UPDATED_EMPLOYE_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .companyId(UPDATED_COMPANY_ID);
        ProjectTeamsDTO projectTeamsDTO = projectTeamsMapper.toDto(updatedProjectTeams);

        restProjectTeamsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectTeamsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectTeamsDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProjectTeams in the database
        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeUpdate);
        ProjectTeams testProjectTeams = projectTeamsList.get(projectTeamsList.size() - 1);
        assertThat(testProjectTeams.getTeamMemberType()).isEqualTo(UPDATED_TEAM_MEMBER_TYPE);
        assertThat(testProjectTeams.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testProjectTeams.getEmployeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testProjectTeams.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProjectTeams.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProjectTeams.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProjectTeams.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testProjectTeams.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testProjectTeams.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void putNonExistingProjectTeams() throws Exception {
        int databaseSizeBeforeUpdate = projectTeamsRepository.findAll().size();
        projectTeams.setId(count.incrementAndGet());

        // Create the ProjectTeams
        ProjectTeamsDTO projectTeamsDTO = projectTeamsMapper.toDto(projectTeams);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectTeamsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectTeamsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectTeamsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectTeams in the database
        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectTeams() throws Exception {
        int databaseSizeBeforeUpdate = projectTeamsRepository.findAll().size();
        projectTeams.setId(count.incrementAndGet());

        // Create the ProjectTeams
        ProjectTeamsDTO projectTeamsDTO = projectTeamsMapper.toDto(projectTeams);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectTeamsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectTeamsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectTeams in the database
        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectTeams() throws Exception {
        int databaseSizeBeforeUpdate = projectTeamsRepository.findAll().size();
        projectTeams.setId(count.incrementAndGet());

        // Create the ProjectTeams
        ProjectTeamsDTO projectTeamsDTO = projectTeamsMapper.toDto(projectTeams);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectTeamsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectTeamsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectTeams in the database
        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectTeamsWithPatch() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        int databaseSizeBeforeUpdate = projectTeamsRepository.findAll().size();

        // Update the projectTeams using partial update
        ProjectTeams partialUpdatedProjectTeams = new ProjectTeams();
        partialUpdatedProjectTeams.setId(projectTeams.getId());

        partialUpdatedProjectTeams
            .employeId(UPDATED_EMPLOYE_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON);

        restProjectTeamsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectTeams.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectTeams))
            )
            .andExpect(status().isOk());

        // Validate the ProjectTeams in the database
        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeUpdate);
        ProjectTeams testProjectTeams = projectTeamsList.get(projectTeamsList.size() - 1);
        assertThat(testProjectTeams.getTeamMemberType()).isEqualTo(DEFAULT_TEAM_MEMBER_TYPE);
        assertThat(testProjectTeams.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testProjectTeams.getEmployeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testProjectTeams.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProjectTeams.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProjectTeams.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProjectTeams.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testProjectTeams.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testProjectTeams.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
    }

    @Test
    @Transactional
    void fullUpdateProjectTeamsWithPatch() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        int databaseSizeBeforeUpdate = projectTeamsRepository.findAll().size();

        // Update the projectTeams using partial update
        ProjectTeams partialUpdatedProjectTeams = new ProjectTeams();
        partialUpdatedProjectTeams.setId(projectTeams.getId());

        partialUpdatedProjectTeams
            .teamMemberType(UPDATED_TEAM_MEMBER_TYPE)
            .projectId(UPDATED_PROJECT_ID)
            .employeId(UPDATED_EMPLOYE_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .companyId(UPDATED_COMPANY_ID);

        restProjectTeamsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectTeams.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectTeams))
            )
            .andExpect(status().isOk());

        // Validate the ProjectTeams in the database
        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeUpdate);
        ProjectTeams testProjectTeams = projectTeamsList.get(projectTeamsList.size() - 1);
        assertThat(testProjectTeams.getTeamMemberType()).isEqualTo(UPDATED_TEAM_MEMBER_TYPE);
        assertThat(testProjectTeams.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testProjectTeams.getEmployeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testProjectTeams.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProjectTeams.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProjectTeams.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProjectTeams.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testProjectTeams.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testProjectTeams.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void patchNonExistingProjectTeams() throws Exception {
        int databaseSizeBeforeUpdate = projectTeamsRepository.findAll().size();
        projectTeams.setId(count.incrementAndGet());

        // Create the ProjectTeams
        ProjectTeamsDTO projectTeamsDTO = projectTeamsMapper.toDto(projectTeams);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectTeamsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectTeamsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectTeamsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectTeams in the database
        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectTeams() throws Exception {
        int databaseSizeBeforeUpdate = projectTeamsRepository.findAll().size();
        projectTeams.setId(count.incrementAndGet());

        // Create the ProjectTeams
        ProjectTeamsDTO projectTeamsDTO = projectTeamsMapper.toDto(projectTeams);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectTeamsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectTeamsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectTeams in the database
        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectTeams() throws Exception {
        int databaseSizeBeforeUpdate = projectTeamsRepository.findAll().size();
        projectTeams.setId(count.incrementAndGet());

        // Create the ProjectTeams
        ProjectTeamsDTO projectTeamsDTO = projectTeamsMapper.toDto(projectTeams);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectTeamsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectTeamsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectTeams in the database
        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectTeams() throws Exception {
        // Initialize the database
        projectTeamsRepository.saveAndFlush(projectTeams);

        int databaseSizeBeforeDelete = projectTeamsRepository.findAll().size();

        // Delete the projectTeams
        restProjectTeamsMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectTeams.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
