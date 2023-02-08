package com.techvg.shrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.shrms.IntegrationTest;
import com.techvg.shrms.domain.LeaveTranscation;
import com.techvg.shrms.domain.enumeration.Status;
import com.techvg.shrms.repository.LeaveTranscationRepository;
import com.techvg.shrms.service.criteria.LeaveTranscationCriteria;
import com.techvg.shrms.service.dto.LeaveTranscationDTO;
import com.techvg.shrms.service.mapper.LeaveTranscationMapper;
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
 * Integration tests for the {@link LeaveTranscationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveTranscationResourceIT {

    private static final String DEFAULT_LEAVE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_LEAVE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EMP_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMP_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_MONTH_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MONTH_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    private static final Long DEFAULT_YEAR = 1L;
    private static final Long UPDATED_YEAR = 2L;
    private static final Long SMALLER_YEAR = 1L - 1L;

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

    private static final String ENTITY_API_URL = "/api/leave-transcations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveTranscationRepository leaveTranscationRepository;

    @Autowired
    private LeaveTranscationMapper leaveTranscationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveTranscationMockMvc;

    private LeaveTranscation leaveTranscation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveTranscation createEntity(EntityManager em) {
        LeaveTranscation leaveTranscation = new LeaveTranscation()
            .leaveType(DEFAULT_LEAVE_TYPE)
            .empId(DEFAULT_EMP_ID)
            .monthDate(DEFAULT_MONTH_DATE)
            .status(DEFAULT_STATUS)
            .year(DEFAULT_YEAR)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .deleted(DEFAULT_DELETED);
        return leaveTranscation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveTranscation createUpdatedEntity(EntityManager em) {
        LeaveTranscation leaveTranscation = new LeaveTranscation()
            .leaveType(UPDATED_LEAVE_TYPE)
            .empId(UPDATED_EMP_ID)
            .monthDate(UPDATED_MONTH_DATE)
            .status(UPDATED_STATUS)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);
        return leaveTranscation;
    }

    @BeforeEach
    public void initTest() {
        leaveTranscation = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveTranscation() throws Exception {
        int databaseSizeBeforeCreate = leaveTranscationRepository.findAll().size();
        // Create the LeaveTranscation
        LeaveTranscationDTO leaveTranscationDTO = leaveTranscationMapper.toDto(leaveTranscation);
        restLeaveTranscationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaveTranscationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveTranscation in the database
        List<LeaveTranscation> leaveTranscationList = leaveTranscationRepository.findAll();
        assertThat(leaveTranscationList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveTranscation testLeaveTranscation = leaveTranscationList.get(leaveTranscationList.size() - 1);
        assertThat(testLeaveTranscation.getLeaveType()).isEqualTo(DEFAULT_LEAVE_TYPE);
        assertThat(testLeaveTranscation.getEmpId()).isEqualTo(DEFAULT_EMP_ID);
        assertThat(testLeaveTranscation.getMonthDate()).isEqualTo(DEFAULT_MONTH_DATE);
        assertThat(testLeaveTranscation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLeaveTranscation.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testLeaveTranscation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLeaveTranscation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLeaveTranscation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLeaveTranscation.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testLeaveTranscation.getDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    void createLeaveTranscationWithExistingId() throws Exception {
        // Create the LeaveTranscation with an existing ID
        leaveTranscation.setId(1L);
        LeaveTranscationDTO leaveTranscationDTO = leaveTranscationMapper.toDto(leaveTranscation);

        int databaseSizeBeforeCreate = leaveTranscationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveTranscationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaveTranscationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTranscation in the database
        List<LeaveTranscation> leaveTranscationList = leaveTranscationRepository.findAll();
        assertThat(leaveTranscationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeaveTranscations() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList
        restLeaveTranscationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveTranscation.getId().intValue())))
            .andExpect(jsonPath("$.[*].leaveType").value(hasItem(DEFAULT_LEAVE_TYPE)))
            .andExpect(jsonPath("$.[*].empId").value(hasItem(DEFAULT_EMP_ID)))
            .andExpect(jsonPath("$.[*].monthDate").value(hasItem(DEFAULT_MONTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getLeaveTranscation() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get the leaveTranscation
        restLeaveTranscationMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveTranscation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveTranscation.getId().intValue()))
            .andExpect(jsonPath("$.leaveType").value(DEFAULT_LEAVE_TYPE))
            .andExpect(jsonPath("$.empId").value(DEFAULT_EMP_ID))
            .andExpect(jsonPath("$.monthDate").value(DEFAULT_MONTH_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getLeaveTranscationsByIdFiltering() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        Long id = leaveTranscation.getId();

        defaultLeaveTranscationShouldBeFound("id.equals=" + id);
        defaultLeaveTranscationShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveTranscationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveTranscationShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveTranscationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveTranscationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByLeaveTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where leaveType equals to DEFAULT_LEAVE_TYPE
        defaultLeaveTranscationShouldBeFound("leaveType.equals=" + DEFAULT_LEAVE_TYPE);

        // Get all the leaveTranscationList where leaveType equals to UPDATED_LEAVE_TYPE
        defaultLeaveTranscationShouldNotBeFound("leaveType.equals=" + UPDATED_LEAVE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByLeaveTypeIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where leaveType in DEFAULT_LEAVE_TYPE or UPDATED_LEAVE_TYPE
        defaultLeaveTranscationShouldBeFound("leaveType.in=" + DEFAULT_LEAVE_TYPE + "," + UPDATED_LEAVE_TYPE);

        // Get all the leaveTranscationList where leaveType equals to UPDATED_LEAVE_TYPE
        defaultLeaveTranscationShouldNotBeFound("leaveType.in=" + UPDATED_LEAVE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByLeaveTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where leaveType is not null
        defaultLeaveTranscationShouldBeFound("leaveType.specified=true");

        // Get all the leaveTranscationList where leaveType is null
        defaultLeaveTranscationShouldNotBeFound("leaveType.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByLeaveTypeContainsSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where leaveType contains DEFAULT_LEAVE_TYPE
        defaultLeaveTranscationShouldBeFound("leaveType.contains=" + DEFAULT_LEAVE_TYPE);

        // Get all the leaveTranscationList where leaveType contains UPDATED_LEAVE_TYPE
        defaultLeaveTranscationShouldNotBeFound("leaveType.contains=" + UPDATED_LEAVE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByLeaveTypeNotContainsSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where leaveType does not contain DEFAULT_LEAVE_TYPE
        defaultLeaveTranscationShouldNotBeFound("leaveType.doesNotContain=" + DEFAULT_LEAVE_TYPE);

        // Get all the leaveTranscationList where leaveType does not contain UPDATED_LEAVE_TYPE
        defaultLeaveTranscationShouldBeFound("leaveType.doesNotContain=" + UPDATED_LEAVE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByEmpIdIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where empId equals to DEFAULT_EMP_ID
        defaultLeaveTranscationShouldBeFound("empId.equals=" + DEFAULT_EMP_ID);

        // Get all the leaveTranscationList where empId equals to UPDATED_EMP_ID
        defaultLeaveTranscationShouldNotBeFound("empId.equals=" + UPDATED_EMP_ID);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByEmpIdIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where empId in DEFAULT_EMP_ID or UPDATED_EMP_ID
        defaultLeaveTranscationShouldBeFound("empId.in=" + DEFAULT_EMP_ID + "," + UPDATED_EMP_ID);

        // Get all the leaveTranscationList where empId equals to UPDATED_EMP_ID
        defaultLeaveTranscationShouldNotBeFound("empId.in=" + UPDATED_EMP_ID);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByEmpIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where empId is not null
        defaultLeaveTranscationShouldBeFound("empId.specified=true");

        // Get all the leaveTranscationList where empId is null
        defaultLeaveTranscationShouldNotBeFound("empId.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByEmpIdContainsSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where empId contains DEFAULT_EMP_ID
        defaultLeaveTranscationShouldBeFound("empId.contains=" + DEFAULT_EMP_ID);

        // Get all the leaveTranscationList where empId contains UPDATED_EMP_ID
        defaultLeaveTranscationShouldNotBeFound("empId.contains=" + UPDATED_EMP_ID);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByEmpIdNotContainsSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where empId does not contain DEFAULT_EMP_ID
        defaultLeaveTranscationShouldNotBeFound("empId.doesNotContain=" + DEFAULT_EMP_ID);

        // Get all the leaveTranscationList where empId does not contain UPDATED_EMP_ID
        defaultLeaveTranscationShouldBeFound("empId.doesNotContain=" + UPDATED_EMP_ID);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByMonthDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where monthDate equals to DEFAULT_MONTH_DATE
        defaultLeaveTranscationShouldBeFound("monthDate.equals=" + DEFAULT_MONTH_DATE);

        // Get all the leaveTranscationList where monthDate equals to UPDATED_MONTH_DATE
        defaultLeaveTranscationShouldNotBeFound("monthDate.equals=" + UPDATED_MONTH_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByMonthDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where monthDate in DEFAULT_MONTH_DATE or UPDATED_MONTH_DATE
        defaultLeaveTranscationShouldBeFound("monthDate.in=" + DEFAULT_MONTH_DATE + "," + UPDATED_MONTH_DATE);

        // Get all the leaveTranscationList where monthDate equals to UPDATED_MONTH_DATE
        defaultLeaveTranscationShouldNotBeFound("monthDate.in=" + UPDATED_MONTH_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByMonthDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where monthDate is not null
        defaultLeaveTranscationShouldBeFound("monthDate.specified=true");

        // Get all the leaveTranscationList where monthDate is null
        defaultLeaveTranscationShouldNotBeFound("monthDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where status equals to DEFAULT_STATUS
        defaultLeaveTranscationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the leaveTranscationList where status equals to UPDATED_STATUS
        defaultLeaveTranscationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultLeaveTranscationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the leaveTranscationList where status equals to UPDATED_STATUS
        defaultLeaveTranscationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where status is not null
        defaultLeaveTranscationShouldBeFound("status.specified=true");

        // Get all the leaveTranscationList where status is null
        defaultLeaveTranscationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where year equals to DEFAULT_YEAR
        defaultLeaveTranscationShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the leaveTranscationList where year equals to UPDATED_YEAR
        defaultLeaveTranscationShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultLeaveTranscationShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the leaveTranscationList where year equals to UPDATED_YEAR
        defaultLeaveTranscationShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where year is not null
        defaultLeaveTranscationShouldBeFound("year.specified=true");

        // Get all the leaveTranscationList where year is null
        defaultLeaveTranscationShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where year is greater than or equal to DEFAULT_YEAR
        defaultLeaveTranscationShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the leaveTranscationList where year is greater than or equal to UPDATED_YEAR
        defaultLeaveTranscationShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where year is less than or equal to DEFAULT_YEAR
        defaultLeaveTranscationShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the leaveTranscationList where year is less than or equal to SMALLER_YEAR
        defaultLeaveTranscationShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where year is less than DEFAULT_YEAR
        defaultLeaveTranscationShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the leaveTranscationList where year is less than UPDATED_YEAR
        defaultLeaveTranscationShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where year is greater than DEFAULT_YEAR
        defaultLeaveTranscationShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the leaveTranscationList where year is greater than SMALLER_YEAR
        defaultLeaveTranscationShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultLeaveTranscationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the leaveTranscationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLeaveTranscationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultLeaveTranscationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the leaveTranscationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLeaveTranscationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where lastModified is not null
        defaultLeaveTranscationShouldBeFound("lastModified.specified=true");

        // Get all the leaveTranscationList where lastModified is null
        defaultLeaveTranscationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultLeaveTranscationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the leaveTranscationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLeaveTranscationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultLeaveTranscationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the leaveTranscationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLeaveTranscationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where lastModifiedBy is not null
        defaultLeaveTranscationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the leaveTranscationList where lastModifiedBy is null
        defaultLeaveTranscationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultLeaveTranscationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the leaveTranscationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultLeaveTranscationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultLeaveTranscationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the leaveTranscationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultLeaveTranscationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where createdBy equals to DEFAULT_CREATED_BY
        defaultLeaveTranscationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the leaveTranscationList where createdBy equals to UPDATED_CREATED_BY
        defaultLeaveTranscationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultLeaveTranscationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the leaveTranscationList where createdBy equals to UPDATED_CREATED_BY
        defaultLeaveTranscationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where createdBy is not null
        defaultLeaveTranscationShouldBeFound("createdBy.specified=true");

        // Get all the leaveTranscationList where createdBy is null
        defaultLeaveTranscationShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where createdBy contains DEFAULT_CREATED_BY
        defaultLeaveTranscationShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the leaveTranscationList where createdBy contains UPDATED_CREATED_BY
        defaultLeaveTranscationShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where createdBy does not contain DEFAULT_CREATED_BY
        defaultLeaveTranscationShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the leaveTranscationList where createdBy does not contain UPDATED_CREATED_BY
        defaultLeaveTranscationShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where createdOn equals to DEFAULT_CREATED_ON
        defaultLeaveTranscationShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the leaveTranscationList where createdOn equals to UPDATED_CREATED_ON
        defaultLeaveTranscationShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultLeaveTranscationShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the leaveTranscationList where createdOn equals to UPDATED_CREATED_ON
        defaultLeaveTranscationShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where createdOn is not null
        defaultLeaveTranscationShouldBeFound("createdOn.specified=true");

        // Get all the leaveTranscationList where createdOn is null
        defaultLeaveTranscationShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where deleted equals to DEFAULT_DELETED
        defaultLeaveTranscationShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the leaveTranscationList where deleted equals to UPDATED_DELETED
        defaultLeaveTranscationShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultLeaveTranscationShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the leaveTranscationList where deleted equals to UPDATED_DELETED
        defaultLeaveTranscationShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllLeaveTranscationsByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        // Get all the leaveTranscationList where deleted is not null
        defaultLeaveTranscationShouldBeFound("deleted.specified=true");

        // Get all the leaveTranscationList where deleted is null
        defaultLeaveTranscationShouldNotBeFound("deleted.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveTranscationShouldBeFound(String filter) throws Exception {
        restLeaveTranscationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveTranscation.getId().intValue())))
            .andExpect(jsonPath("$.[*].leaveType").value(hasItem(DEFAULT_LEAVE_TYPE)))
            .andExpect(jsonPath("$.[*].empId").value(hasItem(DEFAULT_EMP_ID)))
            .andExpect(jsonPath("$.[*].monthDate").value(hasItem(DEFAULT_MONTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));

        // Check, that the count call also returns 1
        restLeaveTranscationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveTranscationShouldNotBeFound(String filter) throws Exception {
        restLeaveTranscationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveTranscationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveTranscation() throws Exception {
        // Get the leaveTranscation
        restLeaveTranscationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveTranscation() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        int databaseSizeBeforeUpdate = leaveTranscationRepository.findAll().size();

        // Update the leaveTranscation
        LeaveTranscation updatedLeaveTranscation = leaveTranscationRepository.findById(leaveTranscation.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveTranscation are not directly saved in db
        em.detach(updatedLeaveTranscation);
        updatedLeaveTranscation
            .leaveType(UPDATED_LEAVE_TYPE)
            .empId(UPDATED_EMP_ID)
            .monthDate(UPDATED_MONTH_DATE)
            .status(UPDATED_STATUS)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);
        LeaveTranscationDTO leaveTranscationDTO = leaveTranscationMapper.toDto(updatedLeaveTranscation);

        restLeaveTranscationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveTranscationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTranscationDTO))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTranscation in the database
        List<LeaveTranscation> leaveTranscationList = leaveTranscationRepository.findAll();
        assertThat(leaveTranscationList).hasSize(databaseSizeBeforeUpdate);
        LeaveTranscation testLeaveTranscation = leaveTranscationList.get(leaveTranscationList.size() - 1);
        assertThat(testLeaveTranscation.getLeaveType()).isEqualTo(UPDATED_LEAVE_TYPE);
        assertThat(testLeaveTranscation.getEmpId()).isEqualTo(UPDATED_EMP_ID);
        assertThat(testLeaveTranscation.getMonthDate()).isEqualTo(UPDATED_MONTH_DATE);
        assertThat(testLeaveTranscation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLeaveTranscation.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testLeaveTranscation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLeaveTranscation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLeaveTranscation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLeaveTranscation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLeaveTranscation.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingLeaveTranscation() throws Exception {
        int databaseSizeBeforeUpdate = leaveTranscationRepository.findAll().size();
        leaveTranscation.setId(count.incrementAndGet());

        // Create the LeaveTranscation
        LeaveTranscationDTO leaveTranscationDTO = leaveTranscationMapper.toDto(leaveTranscation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTranscationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveTranscationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTranscationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTranscation in the database
        List<LeaveTranscation> leaveTranscationList = leaveTranscationRepository.findAll();
        assertThat(leaveTranscationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveTranscation() throws Exception {
        int databaseSizeBeforeUpdate = leaveTranscationRepository.findAll().size();
        leaveTranscation.setId(count.incrementAndGet());

        // Create the LeaveTranscation
        LeaveTranscationDTO leaveTranscationDTO = leaveTranscationMapper.toDto(leaveTranscation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTranscationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTranscationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTranscation in the database
        List<LeaveTranscation> leaveTranscationList = leaveTranscationRepository.findAll();
        assertThat(leaveTranscationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveTranscation() throws Exception {
        int databaseSizeBeforeUpdate = leaveTranscationRepository.findAll().size();
        leaveTranscation.setId(count.incrementAndGet());

        // Create the LeaveTranscation
        LeaveTranscationDTO leaveTranscationDTO = leaveTranscationMapper.toDto(leaveTranscation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTranscationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaveTranscationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveTranscation in the database
        List<LeaveTranscation> leaveTranscationList = leaveTranscationRepository.findAll();
        assertThat(leaveTranscationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveTranscationWithPatch() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        int databaseSizeBeforeUpdate = leaveTranscationRepository.findAll().size();

        // Update the leaveTranscation using partial update
        LeaveTranscation partialUpdatedLeaveTranscation = new LeaveTranscation();
        partialUpdatedLeaveTranscation.setId(leaveTranscation.getId());

        partialUpdatedLeaveTranscation
            .leaveType(UPDATED_LEAVE_TYPE)
            .empId(UPDATED_EMP_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON);

        restLeaveTranscationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveTranscation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveTranscation))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTranscation in the database
        List<LeaveTranscation> leaveTranscationList = leaveTranscationRepository.findAll();
        assertThat(leaveTranscationList).hasSize(databaseSizeBeforeUpdate);
        LeaveTranscation testLeaveTranscation = leaveTranscationList.get(leaveTranscationList.size() - 1);
        assertThat(testLeaveTranscation.getLeaveType()).isEqualTo(UPDATED_LEAVE_TYPE);
        assertThat(testLeaveTranscation.getEmpId()).isEqualTo(UPDATED_EMP_ID);
        assertThat(testLeaveTranscation.getMonthDate()).isEqualTo(DEFAULT_MONTH_DATE);
        assertThat(testLeaveTranscation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLeaveTranscation.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testLeaveTranscation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLeaveTranscation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLeaveTranscation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLeaveTranscation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLeaveTranscation.getDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateLeaveTranscationWithPatch() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        int databaseSizeBeforeUpdate = leaveTranscationRepository.findAll().size();

        // Update the leaveTranscation using partial update
        LeaveTranscation partialUpdatedLeaveTranscation = new LeaveTranscation();
        partialUpdatedLeaveTranscation.setId(leaveTranscation.getId());

        partialUpdatedLeaveTranscation
            .leaveType(UPDATED_LEAVE_TYPE)
            .empId(UPDATED_EMP_ID)
            .monthDate(UPDATED_MONTH_DATE)
            .status(UPDATED_STATUS)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);

        restLeaveTranscationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveTranscation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveTranscation))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTranscation in the database
        List<LeaveTranscation> leaveTranscationList = leaveTranscationRepository.findAll();
        assertThat(leaveTranscationList).hasSize(databaseSizeBeforeUpdate);
        LeaveTranscation testLeaveTranscation = leaveTranscationList.get(leaveTranscationList.size() - 1);
        assertThat(testLeaveTranscation.getLeaveType()).isEqualTo(UPDATED_LEAVE_TYPE);
        assertThat(testLeaveTranscation.getEmpId()).isEqualTo(UPDATED_EMP_ID);
        assertThat(testLeaveTranscation.getMonthDate()).isEqualTo(UPDATED_MONTH_DATE);
        assertThat(testLeaveTranscation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLeaveTranscation.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testLeaveTranscation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLeaveTranscation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLeaveTranscation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLeaveTranscation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLeaveTranscation.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveTranscation() throws Exception {
        int databaseSizeBeforeUpdate = leaveTranscationRepository.findAll().size();
        leaveTranscation.setId(count.incrementAndGet());

        // Create the LeaveTranscation
        LeaveTranscationDTO leaveTranscationDTO = leaveTranscationMapper.toDto(leaveTranscation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTranscationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveTranscationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTranscationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTranscation in the database
        List<LeaveTranscation> leaveTranscationList = leaveTranscationRepository.findAll();
        assertThat(leaveTranscationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveTranscation() throws Exception {
        int databaseSizeBeforeUpdate = leaveTranscationRepository.findAll().size();
        leaveTranscation.setId(count.incrementAndGet());

        // Create the LeaveTranscation
        LeaveTranscationDTO leaveTranscationDTO = leaveTranscationMapper.toDto(leaveTranscation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTranscationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTranscationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTranscation in the database
        List<LeaveTranscation> leaveTranscationList = leaveTranscationRepository.findAll();
        assertThat(leaveTranscationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveTranscation() throws Exception {
        int databaseSizeBeforeUpdate = leaveTranscationRepository.findAll().size();
        leaveTranscation.setId(count.incrementAndGet());

        // Create the LeaveTranscation
        LeaveTranscationDTO leaveTranscationDTO = leaveTranscationMapper.toDto(leaveTranscation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTranscationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTranscationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveTranscation in the database
        List<LeaveTranscation> leaveTranscationList = leaveTranscationRepository.findAll();
        assertThat(leaveTranscationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveTranscation() throws Exception {
        // Initialize the database
        leaveTranscationRepository.saveAndFlush(leaveTranscation);

        int databaseSizeBeforeDelete = leaveTranscationRepository.findAll().size();

        // Delete the leaveTranscation
        restLeaveTranscationMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveTranscation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveTranscation> leaveTranscationList = leaveTranscationRepository.findAll();
        assertThat(leaveTranscationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
