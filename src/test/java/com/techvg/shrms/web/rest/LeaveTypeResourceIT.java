package com.techvg.shrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.shrms.IntegrationTest;
import com.techvg.shrms.domain.LeaveType;
import com.techvg.shrms.domain.enumeration.Status;
import com.techvg.shrms.repository.LeaveTypeRepository;
import com.techvg.shrms.service.criteria.LeaveTypeCriteria;
import com.techvg.shrms.service.dto.LeaveTypeDTO;
import com.techvg.shrms.service.mapper.LeaveTypeMapper;
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
 * Integration tests for the {@link LeaveTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveTypeResourceIT {

    private static final String DEFAULT_LEAVE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_LEAVE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NO_OF_DAYS = "AAAAAAAAAA";
    private static final String UPDATED_NO_OF_DAYS = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

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

    private static final String ENTITY_API_URL = "/api/leave-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Autowired
    private LeaveTypeMapper leaveTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveTypeMockMvc;

    private LeaveType leaveType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveType createEntity(EntityManager em) {
        LeaveType leaveType = new LeaveType()
            .leaveType(DEFAULT_LEAVE_TYPE)
            .noOfDays(DEFAULT_NO_OF_DAYS)
            .status(DEFAULT_STATUS)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .deleted(DEFAULT_DELETED);
        return leaveType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveType createUpdatedEntity(EntityManager em) {
        LeaveType leaveType = new LeaveType()
            .leaveType(UPDATED_LEAVE_TYPE)
            .noOfDays(UPDATED_NO_OF_DAYS)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);
        return leaveType;
    }

    @BeforeEach
    public void initTest() {
        leaveType = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveType() throws Exception {
        int databaseSizeBeforeCreate = leaveTypeRepository.findAll().size();
        // Create the LeaveType
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);
        restLeaveTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveType testLeaveType = leaveTypeList.get(leaveTypeList.size() - 1);
        assertThat(testLeaveType.getLeaveType()).isEqualTo(DEFAULT_LEAVE_TYPE);
        assertThat(testLeaveType.getNoOfDays()).isEqualTo(DEFAULT_NO_OF_DAYS);
        assertThat(testLeaveType.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLeaveType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLeaveType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLeaveType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLeaveType.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testLeaveType.getDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    void createLeaveTypeWithExistingId() throws Exception {
        // Create the LeaveType with an existing ID
        leaveType.setId(1L);
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);

        int databaseSizeBeforeCreate = leaveTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeaveTypes() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList
        restLeaveTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveType.getId().intValue())))
            .andExpect(jsonPath("$.[*].leaveType").value(hasItem(DEFAULT_LEAVE_TYPE)))
            .andExpect(jsonPath("$.[*].noOfDays").value(hasItem(DEFAULT_NO_OF_DAYS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getLeaveType() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get the leaveType
        restLeaveTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveType.getId().intValue()))
            .andExpect(jsonPath("$.leaveType").value(DEFAULT_LEAVE_TYPE))
            .andExpect(jsonPath("$.noOfDays").value(DEFAULT_NO_OF_DAYS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getLeaveTypesByIdFiltering() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        Long id = leaveType.getId();

        defaultLeaveTypeShouldBeFound("id.equals=" + id);
        defaultLeaveTypeShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLeaveTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where leaveType equals to DEFAULT_LEAVE_TYPE
        defaultLeaveTypeShouldBeFound("leaveType.equals=" + DEFAULT_LEAVE_TYPE);

        // Get all the leaveTypeList where leaveType equals to UPDATED_LEAVE_TYPE
        defaultLeaveTypeShouldNotBeFound("leaveType.equals=" + UPDATED_LEAVE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLeaveTypeIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where leaveType in DEFAULT_LEAVE_TYPE or UPDATED_LEAVE_TYPE
        defaultLeaveTypeShouldBeFound("leaveType.in=" + DEFAULT_LEAVE_TYPE + "," + UPDATED_LEAVE_TYPE);

        // Get all the leaveTypeList where leaveType equals to UPDATED_LEAVE_TYPE
        defaultLeaveTypeShouldNotBeFound("leaveType.in=" + UPDATED_LEAVE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLeaveTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where leaveType is not null
        defaultLeaveTypeShouldBeFound("leaveType.specified=true");

        // Get all the leaveTypeList where leaveType is null
        defaultLeaveTypeShouldNotBeFound("leaveType.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLeaveTypeContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where leaveType contains DEFAULT_LEAVE_TYPE
        defaultLeaveTypeShouldBeFound("leaveType.contains=" + DEFAULT_LEAVE_TYPE);

        // Get all the leaveTypeList where leaveType contains UPDATED_LEAVE_TYPE
        defaultLeaveTypeShouldNotBeFound("leaveType.contains=" + UPDATED_LEAVE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLeaveTypeNotContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where leaveType does not contain DEFAULT_LEAVE_TYPE
        defaultLeaveTypeShouldNotBeFound("leaveType.doesNotContain=" + DEFAULT_LEAVE_TYPE);

        // Get all the leaveTypeList where leaveType does not contain UPDATED_LEAVE_TYPE
        defaultLeaveTypeShouldBeFound("leaveType.doesNotContain=" + UPDATED_LEAVE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByNoOfDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where noOfDays equals to DEFAULT_NO_OF_DAYS
        defaultLeaveTypeShouldBeFound("noOfDays.equals=" + DEFAULT_NO_OF_DAYS);

        // Get all the leaveTypeList where noOfDays equals to UPDATED_NO_OF_DAYS
        defaultLeaveTypeShouldNotBeFound("noOfDays.equals=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByNoOfDaysIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where noOfDays in DEFAULT_NO_OF_DAYS or UPDATED_NO_OF_DAYS
        defaultLeaveTypeShouldBeFound("noOfDays.in=" + DEFAULT_NO_OF_DAYS + "," + UPDATED_NO_OF_DAYS);

        // Get all the leaveTypeList where noOfDays equals to UPDATED_NO_OF_DAYS
        defaultLeaveTypeShouldNotBeFound("noOfDays.in=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByNoOfDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where noOfDays is not null
        defaultLeaveTypeShouldBeFound("noOfDays.specified=true");

        // Get all the leaveTypeList where noOfDays is null
        defaultLeaveTypeShouldNotBeFound("noOfDays.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByNoOfDaysContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where noOfDays contains DEFAULT_NO_OF_DAYS
        defaultLeaveTypeShouldBeFound("noOfDays.contains=" + DEFAULT_NO_OF_DAYS);

        // Get all the leaveTypeList where noOfDays contains UPDATED_NO_OF_DAYS
        defaultLeaveTypeShouldNotBeFound("noOfDays.contains=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByNoOfDaysNotContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where noOfDays does not contain DEFAULT_NO_OF_DAYS
        defaultLeaveTypeShouldNotBeFound("noOfDays.doesNotContain=" + DEFAULT_NO_OF_DAYS);

        // Get all the leaveTypeList where noOfDays does not contain UPDATED_NO_OF_DAYS
        defaultLeaveTypeShouldBeFound("noOfDays.doesNotContain=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where status equals to DEFAULT_STATUS
        defaultLeaveTypeShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the leaveTypeList where status equals to UPDATED_STATUS
        defaultLeaveTypeShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultLeaveTypeShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the leaveTypeList where status equals to UPDATED_STATUS
        defaultLeaveTypeShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where status is not null
        defaultLeaveTypeShouldBeFound("status.specified=true");

        // Get all the leaveTypeList where status is null
        defaultLeaveTypeShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultLeaveTypeShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the leaveTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLeaveTypeShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultLeaveTypeShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the leaveTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLeaveTypeShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where lastModified is not null
        defaultLeaveTypeShouldBeFound("lastModified.specified=true");

        // Get all the leaveTypeList where lastModified is null
        defaultLeaveTypeShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultLeaveTypeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the leaveTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLeaveTypeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultLeaveTypeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the leaveTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLeaveTypeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where lastModifiedBy is not null
        defaultLeaveTypeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the leaveTypeList where lastModifiedBy is null
        defaultLeaveTypeShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultLeaveTypeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the leaveTypeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultLeaveTypeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultLeaveTypeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the leaveTypeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultLeaveTypeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where createdBy equals to DEFAULT_CREATED_BY
        defaultLeaveTypeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the leaveTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultLeaveTypeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultLeaveTypeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the leaveTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultLeaveTypeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where createdBy is not null
        defaultLeaveTypeShouldBeFound("createdBy.specified=true");

        // Get all the leaveTypeList where createdBy is null
        defaultLeaveTypeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where createdBy contains DEFAULT_CREATED_BY
        defaultLeaveTypeShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the leaveTypeList where createdBy contains UPDATED_CREATED_BY
        defaultLeaveTypeShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where createdBy does not contain DEFAULT_CREATED_BY
        defaultLeaveTypeShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the leaveTypeList where createdBy does not contain UPDATED_CREATED_BY
        defaultLeaveTypeShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where createdOn equals to DEFAULT_CREATED_ON
        defaultLeaveTypeShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the leaveTypeList where createdOn equals to UPDATED_CREATED_ON
        defaultLeaveTypeShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultLeaveTypeShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the leaveTypeList where createdOn equals to UPDATED_CREATED_ON
        defaultLeaveTypeShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where createdOn is not null
        defaultLeaveTypeShouldBeFound("createdOn.specified=true");

        // Get all the leaveTypeList where createdOn is null
        defaultLeaveTypeShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where deleted equals to DEFAULT_DELETED
        defaultLeaveTypeShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the leaveTypeList where deleted equals to UPDATED_DELETED
        defaultLeaveTypeShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultLeaveTypeShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the leaveTypeList where deleted equals to UPDATED_DELETED
        defaultLeaveTypeShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where deleted is not null
        defaultLeaveTypeShouldBeFound("deleted.specified=true");

        // Get all the leaveTypeList where deleted is null
        defaultLeaveTypeShouldNotBeFound("deleted.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveTypeShouldBeFound(String filter) throws Exception {
        restLeaveTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveType.getId().intValue())))
            .andExpect(jsonPath("$.[*].leaveType").value(hasItem(DEFAULT_LEAVE_TYPE)))
            .andExpect(jsonPath("$.[*].noOfDays").value(hasItem(DEFAULT_NO_OF_DAYS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));

        // Check, that the count call also returns 1
        restLeaveTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveTypeShouldNotBeFound(String filter) throws Exception {
        restLeaveTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveType() throws Exception {
        // Get the leaveType
        restLeaveTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveType() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();

        // Update the leaveType
        LeaveType updatedLeaveType = leaveTypeRepository.findById(leaveType.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveType are not directly saved in db
        em.detach(updatedLeaveType);
        updatedLeaveType
            .leaveType(UPDATED_LEAVE_TYPE)
            .noOfDays(UPDATED_NO_OF_DAYS)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(updatedLeaveType);

        restLeaveTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
        LeaveType testLeaveType = leaveTypeList.get(leaveTypeList.size() - 1);
        assertThat(testLeaveType.getLeaveType()).isEqualTo(UPDATED_LEAVE_TYPE);
        assertThat(testLeaveType.getNoOfDays()).isEqualTo(UPDATED_NO_OF_DAYS);
        assertThat(testLeaveType.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLeaveType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLeaveType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLeaveType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLeaveType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLeaveType.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingLeaveType() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();
        leaveType.setId(count.incrementAndGet());

        // Create the LeaveType
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveType() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();
        leaveType.setId(count.incrementAndGet());

        // Create the LeaveType
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveType() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();
        leaveType.setId(count.incrementAndGet());

        // Create the LeaveType
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveTypeWithPatch() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();

        // Update the leaveType using partial update
        LeaveType partialUpdatedLeaveType = new LeaveType();
        partialUpdatedLeaveType.setId(leaveType.getId());

        partialUpdatedLeaveType
            .leaveType(UPDATED_LEAVE_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);

        restLeaveTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveType))
            )
            .andExpect(status().isOk());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
        LeaveType testLeaveType = leaveTypeList.get(leaveTypeList.size() - 1);
        assertThat(testLeaveType.getLeaveType()).isEqualTo(UPDATED_LEAVE_TYPE);
        assertThat(testLeaveType.getNoOfDays()).isEqualTo(DEFAULT_NO_OF_DAYS);
        assertThat(testLeaveType.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLeaveType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLeaveType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLeaveType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLeaveType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLeaveType.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateLeaveTypeWithPatch() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();

        // Update the leaveType using partial update
        LeaveType partialUpdatedLeaveType = new LeaveType();
        partialUpdatedLeaveType.setId(leaveType.getId());

        partialUpdatedLeaveType
            .leaveType(UPDATED_LEAVE_TYPE)
            .noOfDays(UPDATED_NO_OF_DAYS)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);

        restLeaveTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveType))
            )
            .andExpect(status().isOk());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
        LeaveType testLeaveType = leaveTypeList.get(leaveTypeList.size() - 1);
        assertThat(testLeaveType.getLeaveType()).isEqualTo(UPDATED_LEAVE_TYPE);
        assertThat(testLeaveType.getNoOfDays()).isEqualTo(UPDATED_NO_OF_DAYS);
        assertThat(testLeaveType.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLeaveType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLeaveType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLeaveType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLeaveType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLeaveType.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveType() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();
        leaveType.setId(count.incrementAndGet());

        // Create the LeaveType
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveType() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();
        leaveType.setId(count.incrementAndGet());

        // Create the LeaveType
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveType() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();
        leaveType.setId(count.incrementAndGet());

        // Create the LeaveType
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveType() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        int databaseSizeBeforeDelete = leaveTypeRepository.findAll().size();

        // Delete the leaveType
        restLeaveTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
