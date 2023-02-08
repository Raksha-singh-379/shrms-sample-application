package com.techvg.shrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.shrms.IntegrationTest;
import com.techvg.shrms.domain.Company;
import com.techvg.shrms.domain.Region;
import com.techvg.shrms.repository.RegionRepository;
import com.techvg.shrms.service.criteria.RegionCriteria;
import com.techvg.shrms.service.dto.RegionDTO;
import com.techvg.shrms.service.mapper.RegionMapper;
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
 * Integration tests for the {@link RegionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RegionResourceIT {

    private static final String DEFAULT_REGION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REGION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/regions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private RegionMapper regionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegionMockMvc;

    private Region region;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Region createEntity(EntityManager em) {
        Region region = new Region()
            .regionName(DEFAULT_REGION_NAME)
            .description(DEFAULT_DESCRIPTION)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .deleted(DEFAULT_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return region;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Region createUpdatedEntity(EntityManager em) {
        Region region = new Region()
            .regionName(UPDATED_REGION_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return region;
    }

    @BeforeEach
    public void initTest() {
        region = createEntity(em);
    }

    @Test
    @Transactional
    void createRegion() throws Exception {
        int databaseSizeBeforeCreate = regionRepository.findAll().size();
        // Create the Region
        RegionDTO regionDTO = regionMapper.toDto(region);
        restRegionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regionDTO)))
            .andExpect(status().isCreated());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeCreate + 1);
        Region testRegion = regionList.get(regionList.size() - 1);
        assertThat(testRegion.getRegionName()).isEqualTo(DEFAULT_REGION_NAME);
        assertThat(testRegion.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRegion.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testRegion.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRegion.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testRegion.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testRegion.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createRegionWithExistingId() throws Exception {
        // Create the Region with an existing ID
        region.setId(1L);
        RegionDTO regionDTO = regionMapper.toDto(region);

        int databaseSizeBeforeCreate = regionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRegionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = regionRepository.findAll().size();
        // set the field null
        region.setRegionName(null);

        // Create the Region, which fails.
        RegionDTO regionDTO = regionMapper.toDto(region);

        restRegionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regionDTO)))
            .andExpect(status().isBadRequest());

        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRegions() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList
        restRegionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(region.getId().intValue())))
            .andExpect(jsonPath("$.[*].regionName").value(hasItem(DEFAULT_REGION_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getRegion() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get the region
        restRegionMockMvc
            .perform(get(ENTITY_API_URL_ID, region.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(region.getId().intValue()))
            .andExpect(jsonPath("$.regionName").value(DEFAULT_REGION_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getRegionsByIdFiltering() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        Long id = region.getId();

        defaultRegionShouldBeFound("id.equals=" + id);
        defaultRegionShouldNotBeFound("id.notEquals=" + id);

        defaultRegionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRegionShouldNotBeFound("id.greaterThan=" + id);

        defaultRegionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRegionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRegionsByRegionNameIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where regionName equals to DEFAULT_REGION_NAME
        defaultRegionShouldBeFound("regionName.equals=" + DEFAULT_REGION_NAME);

        // Get all the regionList where regionName equals to UPDATED_REGION_NAME
        defaultRegionShouldNotBeFound("regionName.equals=" + UPDATED_REGION_NAME);
    }

    @Test
    @Transactional
    void getAllRegionsByRegionNameIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where regionName in DEFAULT_REGION_NAME or UPDATED_REGION_NAME
        defaultRegionShouldBeFound("regionName.in=" + DEFAULT_REGION_NAME + "," + UPDATED_REGION_NAME);

        // Get all the regionList where regionName equals to UPDATED_REGION_NAME
        defaultRegionShouldNotBeFound("regionName.in=" + UPDATED_REGION_NAME);
    }

    @Test
    @Transactional
    void getAllRegionsByRegionNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where regionName is not null
        defaultRegionShouldBeFound("regionName.specified=true");

        // Get all the regionList where regionName is null
        defaultRegionShouldNotBeFound("regionName.specified=false");
    }

    @Test
    @Transactional
    void getAllRegionsByRegionNameContainsSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where regionName contains DEFAULT_REGION_NAME
        defaultRegionShouldBeFound("regionName.contains=" + DEFAULT_REGION_NAME);

        // Get all the regionList where regionName contains UPDATED_REGION_NAME
        defaultRegionShouldNotBeFound("regionName.contains=" + UPDATED_REGION_NAME);
    }

    @Test
    @Transactional
    void getAllRegionsByRegionNameNotContainsSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where regionName does not contain DEFAULT_REGION_NAME
        defaultRegionShouldNotBeFound("regionName.doesNotContain=" + DEFAULT_REGION_NAME);

        // Get all the regionList where regionName does not contain UPDATED_REGION_NAME
        defaultRegionShouldBeFound("regionName.doesNotContain=" + UPDATED_REGION_NAME);
    }

    @Test
    @Transactional
    void getAllRegionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where description equals to DEFAULT_DESCRIPTION
        defaultRegionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the regionList where description equals to UPDATED_DESCRIPTION
        defaultRegionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRegionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultRegionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the regionList where description equals to UPDATED_DESCRIPTION
        defaultRegionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRegionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where description is not null
        defaultRegionShouldBeFound("description.specified=true");

        // Get all the regionList where description is null
        defaultRegionShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllRegionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where description contains DEFAULT_DESCRIPTION
        defaultRegionShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the regionList where description contains UPDATED_DESCRIPTION
        defaultRegionShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRegionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where description does not contain DEFAULT_DESCRIPTION
        defaultRegionShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the regionList where description does not contain UPDATED_DESCRIPTION
        defaultRegionShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRegionsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where createdOn equals to DEFAULT_CREATED_ON
        defaultRegionShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the regionList where createdOn equals to UPDATED_CREATED_ON
        defaultRegionShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllRegionsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultRegionShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the regionList where createdOn equals to UPDATED_CREATED_ON
        defaultRegionShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllRegionsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where createdOn is not null
        defaultRegionShouldBeFound("createdOn.specified=true");

        // Get all the regionList where createdOn is null
        defaultRegionShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllRegionsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where createdBy equals to DEFAULT_CREATED_BY
        defaultRegionShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the regionList where createdBy equals to UPDATED_CREATED_BY
        defaultRegionShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRegionsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultRegionShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the regionList where createdBy equals to UPDATED_CREATED_BY
        defaultRegionShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRegionsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where createdBy is not null
        defaultRegionShouldBeFound("createdBy.specified=true");

        // Get all the regionList where createdBy is null
        defaultRegionShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRegionsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where createdBy contains DEFAULT_CREATED_BY
        defaultRegionShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the regionList where createdBy contains UPDATED_CREATED_BY
        defaultRegionShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRegionsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where createdBy does not contain DEFAULT_CREATED_BY
        defaultRegionShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the regionList where createdBy does not contain UPDATED_CREATED_BY
        defaultRegionShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRegionsByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where deleted equals to DEFAULT_DELETED
        defaultRegionShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the regionList where deleted equals to UPDATED_DELETED
        defaultRegionShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllRegionsByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultRegionShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the regionList where deleted equals to UPDATED_DELETED
        defaultRegionShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllRegionsByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where deleted is not null
        defaultRegionShouldBeFound("deleted.specified=true");

        // Get all the regionList where deleted is null
        defaultRegionShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllRegionsByDeletedContainsSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where deleted contains DEFAULT_DELETED
        defaultRegionShouldBeFound("deleted.contains=" + DEFAULT_DELETED);

        // Get all the regionList where deleted contains UPDATED_DELETED
        defaultRegionShouldNotBeFound("deleted.contains=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllRegionsByDeletedNotContainsSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where deleted does not contain DEFAULT_DELETED
        defaultRegionShouldNotBeFound("deleted.doesNotContain=" + DEFAULT_DELETED);

        // Get all the regionList where deleted does not contain UPDATED_DELETED
        defaultRegionShouldBeFound("deleted.doesNotContain=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllRegionsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultRegionShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the regionList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultRegionShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRegionsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultRegionShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the regionList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultRegionShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRegionsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where lastModified is not null
        defaultRegionShouldBeFound("lastModified.specified=true");

        // Get all the regionList where lastModified is null
        defaultRegionShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllRegionsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultRegionShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the regionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRegionShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRegionsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultRegionShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the regionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRegionShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRegionsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where lastModifiedBy is not null
        defaultRegionShouldBeFound("lastModifiedBy.specified=true");

        // Get all the regionList where lastModifiedBy is null
        defaultRegionShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRegionsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultRegionShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the regionList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultRegionShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRegionsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultRegionShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the regionList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultRegionShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRegionsByCompanyIsEqualToSomething() throws Exception {
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            regionRepository.saveAndFlush(region);
            company = CompanyResourceIT.createEntity(em);
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(company);
        em.flush();
        region.setCompany(company);
        regionRepository.saveAndFlush(region);
        Long companyId = company.getId();

        // Get all the regionList where company equals to companyId
        defaultRegionShouldBeFound("companyId.equals=" + companyId);

        // Get all the regionList where company equals to (companyId + 1)
        defaultRegionShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRegionShouldBeFound(String filter) throws Exception {
        restRegionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(region.getId().intValue())))
            .andExpect(jsonPath("$.[*].regionName").value(hasItem(DEFAULT_REGION_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restRegionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRegionShouldNotBeFound(String filter) throws Exception {
        restRegionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRegionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRegion() throws Exception {
        // Get the region
        restRegionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRegion() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        int databaseSizeBeforeUpdate = regionRepository.findAll().size();

        // Update the region
        Region updatedRegion = regionRepository.findById(region.getId()).get();
        // Disconnect from session so that the updates on updatedRegion are not directly saved in db
        em.detach(updatedRegion);
        updatedRegion
            .regionName(UPDATED_REGION_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        RegionDTO regionDTO = regionMapper.toDto(updatedRegion);

        restRegionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, regionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
        Region testRegion = regionList.get(regionList.size() - 1);
        assertThat(testRegion.getRegionName()).isEqualTo(UPDATED_REGION_NAME);
        assertThat(testRegion.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRegion.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRegion.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRegion.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testRegion.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testRegion.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingRegion() throws Exception {
        int databaseSizeBeforeUpdate = regionRepository.findAll().size();
        region.setId(count.incrementAndGet());

        // Create the Region
        RegionDTO regionDTO = regionMapper.toDto(region);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, regionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegion() throws Exception {
        int databaseSizeBeforeUpdate = regionRepository.findAll().size();
        region.setId(count.incrementAndGet());

        // Create the Region
        RegionDTO regionDTO = regionMapper.toDto(region);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegion() throws Exception {
        int databaseSizeBeforeUpdate = regionRepository.findAll().size();
        region.setId(count.incrementAndGet());

        // Create the Region
        RegionDTO regionDTO = regionMapper.toDto(region);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegionWithPatch() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        int databaseSizeBeforeUpdate = regionRepository.findAll().size();

        // Update the region using partial update
        Region partialUpdatedRegion = new Region();
        partialUpdatedRegion.setId(region.getId());

        partialUpdatedRegion
            .regionName(UPDATED_REGION_NAME)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .deleted(UPDATED_DELETED);

        restRegionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegion))
            )
            .andExpect(status().isOk());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
        Region testRegion = regionList.get(regionList.size() - 1);
        assertThat(testRegion.getRegionName()).isEqualTo(UPDATED_REGION_NAME);
        assertThat(testRegion.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRegion.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRegion.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRegion.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testRegion.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testRegion.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateRegionWithPatch() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        int databaseSizeBeforeUpdate = regionRepository.findAll().size();

        // Update the region using partial update
        Region partialUpdatedRegion = new Region();
        partialUpdatedRegion.setId(region.getId());

        partialUpdatedRegion
            .regionName(UPDATED_REGION_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restRegionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegion))
            )
            .andExpect(status().isOk());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
        Region testRegion = regionList.get(regionList.size() - 1);
        assertThat(testRegion.getRegionName()).isEqualTo(UPDATED_REGION_NAME);
        assertThat(testRegion.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRegion.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRegion.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRegion.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testRegion.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testRegion.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingRegion() throws Exception {
        int databaseSizeBeforeUpdate = regionRepository.findAll().size();
        region.setId(count.incrementAndGet());

        // Create the Region
        RegionDTO regionDTO = regionMapper.toDto(region);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, regionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(regionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegion() throws Exception {
        int databaseSizeBeforeUpdate = regionRepository.findAll().size();
        region.setId(count.incrementAndGet());

        // Create the Region
        RegionDTO regionDTO = regionMapper.toDto(region);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(regionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegion() throws Exception {
        int databaseSizeBeforeUpdate = regionRepository.findAll().size();
        region.setId(count.incrementAndGet());

        // Create the Region
        RegionDTO regionDTO = regionMapper.toDto(region);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(regionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegion() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        int databaseSizeBeforeDelete = regionRepository.findAll().size();

        // Delete the region
        restRegionMockMvc
            .perform(delete(ENTITY_API_URL_ID, region.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
