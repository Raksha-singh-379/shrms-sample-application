package com.techvg.shrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.shrms.IntegrationTest;
import com.techvg.shrms.domain.District;
import com.techvg.shrms.repository.DistrictRepository;
import com.techvg.shrms.service.criteria.DistrictCriteria;
import com.techvg.shrms.service.dto.DistrictDTO;
import com.techvg.shrms.service.mapper.DistrictMapper;
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
 * Integration tests for the {@link DistrictResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DistrictResourceIT {

    private static final String DEFAULT_DISTRICT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_LGD_CODE = 1L;
    private static final Long UPDATED_LGD_CODE = 2L;
    private static final Long SMALLER_LGD_CODE = 1L - 1L;

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_DELETED = "AAAAAAAAAA";
    private static final String UPDATED_DELETED = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/districts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private DistrictMapper districtMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDistrictMockMvc;

    private District district;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static District createEntity(EntityManager em) {
        District district = new District()
            .districtName(DEFAULT_DISTRICT_NAME)
            .lgdCode(DEFAULT_LGD_CODE)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .deleted(DEFAULT_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return district;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static District createUpdatedEntity(EntityManager em) {
        District district = new District()
            .districtName(UPDATED_DISTRICT_NAME)
            .lgdCode(UPDATED_LGD_CODE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return district;
    }

    @BeforeEach
    public void initTest() {
        district = createEntity(em);
    }

    @Test
    @Transactional
    void createDistrict() throws Exception {
        int databaseSizeBeforeCreate = districtRepository.findAll().size();
        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);
        restDistrictMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(districtDTO)))
            .andExpect(status().isCreated());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeCreate + 1);
        District testDistrict = districtList.get(districtList.size() - 1);
        assertThat(testDistrict.getDistrictName()).isEqualTo(DEFAULT_DISTRICT_NAME);
        assertThat(testDistrict.getLgdCode()).isEqualTo(DEFAULT_LGD_CODE);
        assertThat(testDistrict.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDistrict.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDistrict.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testDistrict.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testDistrict.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createDistrictWithExistingId() throws Exception {
        // Create the District with an existing ID
        district.setId(1L);
        DistrictDTO districtDTO = districtMapper.toDto(district);

        int databaseSizeBeforeCreate = districtRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistrictMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(districtDTO)))
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDistrictNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = districtRepository.findAll().size();
        // set the field null
        district.setDistrictName(null);

        // Create the District, which fails.
        DistrictDTO districtDTO = districtMapper.toDto(district);

        restDistrictMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(districtDTO)))
            .andExpect(status().isBadRequest());

        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDistricts() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList
        restDistrictMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(district.getId().intValue())))
            .andExpect(jsonPath("$.[*].districtName").value(hasItem(DEFAULT_DISTRICT_NAME)))
            .andExpect(jsonPath("$.[*].lgdCode").value(hasItem(DEFAULT_LGD_CODE.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getDistrict() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get the district
        restDistrictMockMvc
            .perform(get(ENTITY_API_URL_ID, district.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(district.getId().intValue()))
            .andExpect(jsonPath("$.districtName").value(DEFAULT_DISTRICT_NAME))
            .andExpect(jsonPath("$.lgdCode").value(DEFAULT_LGD_CODE.intValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getDistrictsByIdFiltering() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        Long id = district.getId();

        defaultDistrictShouldBeFound("id.equals=" + id);
        defaultDistrictShouldNotBeFound("id.notEquals=" + id);

        defaultDistrictShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDistrictShouldNotBeFound("id.greaterThan=" + id);

        defaultDistrictShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDistrictShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDistrictsByDistrictNameIsEqualToSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where districtName equals to DEFAULT_DISTRICT_NAME
        defaultDistrictShouldBeFound("districtName.equals=" + DEFAULT_DISTRICT_NAME);

        // Get all the districtList where districtName equals to UPDATED_DISTRICT_NAME
        defaultDistrictShouldNotBeFound("districtName.equals=" + UPDATED_DISTRICT_NAME);
    }

    @Test
    @Transactional
    void getAllDistrictsByDistrictNameIsInShouldWork() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where districtName in DEFAULT_DISTRICT_NAME or UPDATED_DISTRICT_NAME
        defaultDistrictShouldBeFound("districtName.in=" + DEFAULT_DISTRICT_NAME + "," + UPDATED_DISTRICT_NAME);

        // Get all the districtList where districtName equals to UPDATED_DISTRICT_NAME
        defaultDistrictShouldNotBeFound("districtName.in=" + UPDATED_DISTRICT_NAME);
    }

    @Test
    @Transactional
    void getAllDistrictsByDistrictNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where districtName is not null
        defaultDistrictShouldBeFound("districtName.specified=true");

        // Get all the districtList where districtName is null
        defaultDistrictShouldNotBeFound("districtName.specified=false");
    }

    @Test
    @Transactional
    void getAllDistrictsByDistrictNameContainsSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where districtName contains DEFAULT_DISTRICT_NAME
        defaultDistrictShouldBeFound("districtName.contains=" + DEFAULT_DISTRICT_NAME);

        // Get all the districtList where districtName contains UPDATED_DISTRICT_NAME
        defaultDistrictShouldNotBeFound("districtName.contains=" + UPDATED_DISTRICT_NAME);
    }

    @Test
    @Transactional
    void getAllDistrictsByDistrictNameNotContainsSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where districtName does not contain DEFAULT_DISTRICT_NAME
        defaultDistrictShouldNotBeFound("districtName.doesNotContain=" + DEFAULT_DISTRICT_NAME);

        // Get all the districtList where districtName does not contain UPDATED_DISTRICT_NAME
        defaultDistrictShouldBeFound("districtName.doesNotContain=" + UPDATED_DISTRICT_NAME);
    }

    @Test
    @Transactional
    void getAllDistrictsByLgdCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where lgdCode equals to DEFAULT_LGD_CODE
        defaultDistrictShouldBeFound("lgdCode.equals=" + DEFAULT_LGD_CODE);

        // Get all the districtList where lgdCode equals to UPDATED_LGD_CODE
        defaultDistrictShouldNotBeFound("lgdCode.equals=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllDistrictsByLgdCodeIsInShouldWork() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where lgdCode in DEFAULT_LGD_CODE or UPDATED_LGD_CODE
        defaultDistrictShouldBeFound("lgdCode.in=" + DEFAULT_LGD_CODE + "," + UPDATED_LGD_CODE);

        // Get all the districtList where lgdCode equals to UPDATED_LGD_CODE
        defaultDistrictShouldNotBeFound("lgdCode.in=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllDistrictsByLgdCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where lgdCode is not null
        defaultDistrictShouldBeFound("lgdCode.specified=true");

        // Get all the districtList where lgdCode is null
        defaultDistrictShouldNotBeFound("lgdCode.specified=false");
    }

    @Test
    @Transactional
    void getAllDistrictsByLgdCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where lgdCode is greater than or equal to DEFAULT_LGD_CODE
        defaultDistrictShouldBeFound("lgdCode.greaterThanOrEqual=" + DEFAULT_LGD_CODE);

        // Get all the districtList where lgdCode is greater than or equal to UPDATED_LGD_CODE
        defaultDistrictShouldNotBeFound("lgdCode.greaterThanOrEqual=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllDistrictsByLgdCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where lgdCode is less than or equal to DEFAULT_LGD_CODE
        defaultDistrictShouldBeFound("lgdCode.lessThanOrEqual=" + DEFAULT_LGD_CODE);

        // Get all the districtList where lgdCode is less than or equal to SMALLER_LGD_CODE
        defaultDistrictShouldNotBeFound("lgdCode.lessThanOrEqual=" + SMALLER_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllDistrictsByLgdCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where lgdCode is less than DEFAULT_LGD_CODE
        defaultDistrictShouldNotBeFound("lgdCode.lessThan=" + DEFAULT_LGD_CODE);

        // Get all the districtList where lgdCode is less than UPDATED_LGD_CODE
        defaultDistrictShouldBeFound("lgdCode.lessThan=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllDistrictsByLgdCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where lgdCode is greater than DEFAULT_LGD_CODE
        defaultDistrictShouldNotBeFound("lgdCode.greaterThan=" + DEFAULT_LGD_CODE);

        // Get all the districtList where lgdCode is greater than SMALLER_LGD_CODE
        defaultDistrictShouldBeFound("lgdCode.greaterThan=" + SMALLER_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllDistrictsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where createdOn equals to DEFAULT_CREATED_ON
        defaultDistrictShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the districtList where createdOn equals to UPDATED_CREATED_ON
        defaultDistrictShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllDistrictsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultDistrictShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the districtList where createdOn equals to UPDATED_CREATED_ON
        defaultDistrictShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllDistrictsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where createdOn is not null
        defaultDistrictShouldBeFound("createdOn.specified=true");

        // Get all the districtList where createdOn is null
        defaultDistrictShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllDistrictsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where createdBy equals to DEFAULT_CREATED_BY
        defaultDistrictShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the districtList where createdBy equals to UPDATED_CREATED_BY
        defaultDistrictShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDistrictsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultDistrictShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the districtList where createdBy equals to UPDATED_CREATED_BY
        defaultDistrictShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDistrictsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where createdBy is not null
        defaultDistrictShouldBeFound("createdBy.specified=true");

        // Get all the districtList where createdBy is null
        defaultDistrictShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllDistrictsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where createdBy contains DEFAULT_CREATED_BY
        defaultDistrictShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the districtList where createdBy contains UPDATED_CREATED_BY
        defaultDistrictShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDistrictsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where createdBy does not contain DEFAULT_CREATED_BY
        defaultDistrictShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the districtList where createdBy does not contain UPDATED_CREATED_BY
        defaultDistrictShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDistrictsByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where deleted equals to DEFAULT_DELETED
        defaultDistrictShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the districtList where deleted equals to UPDATED_DELETED
        defaultDistrictShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllDistrictsByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultDistrictShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the districtList where deleted equals to UPDATED_DELETED
        defaultDistrictShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllDistrictsByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where deleted is not null
        defaultDistrictShouldBeFound("deleted.specified=true");

        // Get all the districtList where deleted is null
        defaultDistrictShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllDistrictsByDeletedContainsSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where deleted contains DEFAULT_DELETED
        defaultDistrictShouldBeFound("deleted.contains=" + DEFAULT_DELETED);

        // Get all the districtList where deleted contains UPDATED_DELETED
        defaultDistrictShouldNotBeFound("deleted.contains=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllDistrictsByDeletedNotContainsSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where deleted does not contain DEFAULT_DELETED
        defaultDistrictShouldNotBeFound("deleted.doesNotContain=" + DEFAULT_DELETED);

        // Get all the districtList where deleted does not contain UPDATED_DELETED
        defaultDistrictShouldBeFound("deleted.doesNotContain=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllDistrictsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultDistrictShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the districtList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDistrictShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllDistrictsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultDistrictShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the districtList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDistrictShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllDistrictsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where lastModified is not null
        defaultDistrictShouldBeFound("lastModified.specified=true");

        // Get all the districtList where lastModified is null
        defaultDistrictShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllDistrictsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultDistrictShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the districtList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDistrictShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDistrictsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultDistrictShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the districtList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDistrictShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDistrictsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where lastModifiedBy is not null
        defaultDistrictShouldBeFound("lastModifiedBy.specified=true");

        // Get all the districtList where lastModifiedBy is null
        defaultDistrictShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllDistrictsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultDistrictShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the districtList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultDistrictShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDistrictsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultDistrictShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the districtList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultDistrictShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDistrictShouldBeFound(String filter) throws Exception {
        restDistrictMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(district.getId().intValue())))
            .andExpect(jsonPath("$.[*].districtName").value(hasItem(DEFAULT_DISTRICT_NAME)))
            .andExpect(jsonPath("$.[*].lgdCode").value(hasItem(DEFAULT_LGD_CODE.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restDistrictMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDistrictShouldNotBeFound(String filter) throws Exception {
        restDistrictMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDistrictMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDistrict() throws Exception {
        // Get the district
        restDistrictMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDistrict() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        int databaseSizeBeforeUpdate = districtRepository.findAll().size();

        // Update the district
        District updatedDistrict = districtRepository.findById(district.getId()).get();
        // Disconnect from session so that the updates on updatedDistrict are not directly saved in db
        em.detach(updatedDistrict);
        updatedDistrict
            .districtName(UPDATED_DISTRICT_NAME)
            .lgdCode(UPDATED_LGD_CODE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        DistrictDTO districtDTO = districtMapper.toDto(updatedDistrict);

        restDistrictMockMvc
            .perform(
                put(ENTITY_API_URL_ID, districtDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districtDTO))
            )
            .andExpect(status().isOk());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeUpdate);
        District testDistrict = districtList.get(districtList.size() - 1);
        assertThat(testDistrict.getDistrictName()).isEqualTo(UPDATED_DISTRICT_NAME);
        assertThat(testDistrict.getLgdCode()).isEqualTo(UPDATED_LGD_CODE);
        assertThat(testDistrict.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDistrict.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDistrict.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testDistrict.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDistrict.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingDistrict() throws Exception {
        int databaseSizeBeforeUpdate = districtRepository.findAll().size();
        district.setId(count.incrementAndGet());

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictMockMvc
            .perform(
                put(ENTITY_API_URL_ID, districtDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDistrict() throws Exception {
        int databaseSizeBeforeUpdate = districtRepository.findAll().size();
        district.setId(count.incrementAndGet());

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDistrict() throws Exception {
        int databaseSizeBeforeUpdate = districtRepository.findAll().size();
        district.setId(count.incrementAndGet());

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(districtDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDistrictWithPatch() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        int databaseSizeBeforeUpdate = districtRepository.findAll().size();

        // Update the district using partial update
        District partialUpdatedDistrict = new District();
        partialUpdatedDistrict.setId(district.getId());

        partialUpdatedDistrict
            .districtName(UPDATED_DISTRICT_NAME)
            .lgdCode(UPDATED_LGD_CODE)
            .createdOn(UPDATED_CREATED_ON)
            .lastModified(UPDATED_LAST_MODIFIED);

        restDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistrict.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDistrict))
            )
            .andExpect(status().isOk());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeUpdate);
        District testDistrict = districtList.get(districtList.size() - 1);
        assertThat(testDistrict.getDistrictName()).isEqualTo(UPDATED_DISTRICT_NAME);
        assertThat(testDistrict.getLgdCode()).isEqualTo(UPDATED_LGD_CODE);
        assertThat(testDistrict.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDistrict.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDistrict.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testDistrict.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDistrict.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateDistrictWithPatch() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        int databaseSizeBeforeUpdate = districtRepository.findAll().size();

        // Update the district using partial update
        District partialUpdatedDistrict = new District();
        partialUpdatedDistrict.setId(district.getId());

        partialUpdatedDistrict
            .districtName(UPDATED_DISTRICT_NAME)
            .lgdCode(UPDATED_LGD_CODE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistrict.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDistrict))
            )
            .andExpect(status().isOk());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeUpdate);
        District testDistrict = districtList.get(districtList.size() - 1);
        assertThat(testDistrict.getDistrictName()).isEqualTo(UPDATED_DISTRICT_NAME);
        assertThat(testDistrict.getLgdCode()).isEqualTo(UPDATED_LGD_CODE);
        assertThat(testDistrict.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDistrict.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDistrict.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testDistrict.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDistrict.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingDistrict() throws Exception {
        int databaseSizeBeforeUpdate = districtRepository.findAll().size();
        district.setId(count.incrementAndGet());

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, districtDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(districtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDistrict() throws Exception {
        int databaseSizeBeforeUpdate = districtRepository.findAll().size();
        district.setId(count.incrementAndGet());

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(districtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDistrict() throws Exception {
        int databaseSizeBeforeUpdate = districtRepository.findAll().size();
        district.setId(count.incrementAndGet());

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(districtDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDistrict() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        int databaseSizeBeforeDelete = districtRepository.findAll().size();

        // Delete the district
        restDistrictMockMvc
            .perform(delete(ENTITY_API_URL_ID, district.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
