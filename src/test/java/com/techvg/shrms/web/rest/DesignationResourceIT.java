package com.techvg.shrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.shrms.IntegrationTest;
import com.techvg.shrms.domain.Company;
import com.techvg.shrms.domain.Designation;
import com.techvg.shrms.repository.DesignationRepository;
import com.techvg.shrms.service.criteria.DesignationCriteria;
import com.techvg.shrms.service.dto.DesignationDTO;
import com.techvg.shrms.service.mapper.DesignationMapper;
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
 * Integration tests for the {@link DesignationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DesignationResourceIT {

    private static final String DEFAULT_DESIGNATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/designations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private DesignationMapper designationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDesignationMockMvc;

    private Designation designation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Designation createEntity(EntityManager em) {
        Designation designation = new Designation()
            .designationName(DEFAULT_DESIGNATION_NAME)
            .description(DEFAULT_DESCRIPTION)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .deleted(DEFAULT_DELETED);
        return designation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Designation createUpdatedEntity(EntityManager em) {
        Designation designation = new Designation()
            .designationName(UPDATED_DESIGNATION_NAME)
            .description(UPDATED_DESCRIPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);
        return designation;
    }

    @BeforeEach
    public void initTest() {
        designation = createEntity(em);
    }

    @Test
    @Transactional
    void createDesignation() throws Exception {
        int databaseSizeBeforeCreate = designationRepository.findAll().size();
        // Create the Designation
        DesignationDTO designationDTO = designationMapper.toDto(designation);
        restDesignationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(designationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeCreate + 1);
        Designation testDesignation = designationList.get(designationList.size() - 1);
        assertThat(testDesignation.getDesignationName()).isEqualTo(DEFAULT_DESIGNATION_NAME);
        assertThat(testDesignation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDesignation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testDesignation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testDesignation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDesignation.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDesignation.getDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    void createDesignationWithExistingId() throws Exception {
        // Create the Designation with an existing ID
        designation.setId(1L);
        DesignationDTO designationDTO = designationMapper.toDto(designation);

        int databaseSizeBeforeCreate = designationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(designationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDesignations() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList
        restDesignationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designation.getId().intValue())))
            .andExpect(jsonPath("$.[*].designationName").value(hasItem(DEFAULT_DESIGNATION_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getDesignation() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get the designation
        restDesignationMockMvc
            .perform(get(ENTITY_API_URL_ID, designation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(designation.getId().intValue()))
            .andExpect(jsonPath("$.designationName").value(DEFAULT_DESIGNATION_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getDesignationsByIdFiltering() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        Long id = designation.getId();

        defaultDesignationShouldBeFound("id.equals=" + id);
        defaultDesignationShouldNotBeFound("id.notEquals=" + id);

        defaultDesignationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDesignationShouldNotBeFound("id.greaterThan=" + id);

        defaultDesignationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDesignationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDesignationsByDesignationNameIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where designationName equals to DEFAULT_DESIGNATION_NAME
        defaultDesignationShouldBeFound("designationName.equals=" + DEFAULT_DESIGNATION_NAME);

        // Get all the designationList where designationName equals to UPDATED_DESIGNATION_NAME
        defaultDesignationShouldNotBeFound("designationName.equals=" + UPDATED_DESIGNATION_NAME);
    }

    @Test
    @Transactional
    void getAllDesignationsByDesignationNameIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where designationName in DEFAULT_DESIGNATION_NAME or UPDATED_DESIGNATION_NAME
        defaultDesignationShouldBeFound("designationName.in=" + DEFAULT_DESIGNATION_NAME + "," + UPDATED_DESIGNATION_NAME);

        // Get all the designationList where designationName equals to UPDATED_DESIGNATION_NAME
        defaultDesignationShouldNotBeFound("designationName.in=" + UPDATED_DESIGNATION_NAME);
    }

    @Test
    @Transactional
    void getAllDesignationsByDesignationNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where designationName is not null
        defaultDesignationShouldBeFound("designationName.specified=true");

        // Get all the designationList where designationName is null
        defaultDesignationShouldNotBeFound("designationName.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationsByDesignationNameContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where designationName contains DEFAULT_DESIGNATION_NAME
        defaultDesignationShouldBeFound("designationName.contains=" + DEFAULT_DESIGNATION_NAME);

        // Get all the designationList where designationName contains UPDATED_DESIGNATION_NAME
        defaultDesignationShouldNotBeFound("designationName.contains=" + UPDATED_DESIGNATION_NAME);
    }

    @Test
    @Transactional
    void getAllDesignationsByDesignationNameNotContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where designationName does not contain DEFAULT_DESIGNATION_NAME
        defaultDesignationShouldNotBeFound("designationName.doesNotContain=" + DEFAULT_DESIGNATION_NAME);

        // Get all the designationList where designationName does not contain UPDATED_DESIGNATION_NAME
        defaultDesignationShouldBeFound("designationName.doesNotContain=" + UPDATED_DESIGNATION_NAME);
    }

    @Test
    @Transactional
    void getAllDesignationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where description equals to DEFAULT_DESCRIPTION
        defaultDesignationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the designationList where description equals to UPDATED_DESCRIPTION
        defaultDesignationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDesignationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultDesignationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the designationList where description equals to UPDATED_DESCRIPTION
        defaultDesignationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDesignationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where description is not null
        defaultDesignationShouldBeFound("description.specified=true");

        // Get all the designationList where description is null
        defaultDesignationShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where description contains DEFAULT_DESCRIPTION
        defaultDesignationShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the designationList where description contains UPDATED_DESCRIPTION
        defaultDesignationShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDesignationsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where description does not contain DEFAULT_DESCRIPTION
        defaultDesignationShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the designationList where description does not contain UPDATED_DESCRIPTION
        defaultDesignationShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDesignationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultDesignationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the designationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDesignationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllDesignationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultDesignationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the designationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDesignationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllDesignationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where lastModified is not null
        defaultDesignationShouldBeFound("lastModified.specified=true");

        // Get all the designationList where lastModified is null
        defaultDesignationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultDesignationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the designationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDesignationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDesignationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultDesignationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the designationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDesignationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDesignationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where lastModifiedBy is not null
        defaultDesignationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the designationList where lastModifiedBy is null
        defaultDesignationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultDesignationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the designationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultDesignationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDesignationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultDesignationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the designationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultDesignationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDesignationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where createdBy equals to DEFAULT_CREATED_BY
        defaultDesignationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the designationList where createdBy equals to UPDATED_CREATED_BY
        defaultDesignationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDesignationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultDesignationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the designationList where createdBy equals to UPDATED_CREATED_BY
        defaultDesignationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDesignationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where createdBy is not null
        defaultDesignationShouldBeFound("createdBy.specified=true");

        // Get all the designationList where createdBy is null
        defaultDesignationShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where createdBy contains DEFAULT_CREATED_BY
        defaultDesignationShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the designationList where createdBy contains UPDATED_CREATED_BY
        defaultDesignationShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDesignationsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where createdBy does not contain DEFAULT_CREATED_BY
        defaultDesignationShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the designationList where createdBy does not contain UPDATED_CREATED_BY
        defaultDesignationShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDesignationsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where createdOn equals to DEFAULT_CREATED_ON
        defaultDesignationShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the designationList where createdOn equals to UPDATED_CREATED_ON
        defaultDesignationShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllDesignationsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultDesignationShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the designationList where createdOn equals to UPDATED_CREATED_ON
        defaultDesignationShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllDesignationsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where createdOn is not null
        defaultDesignationShouldBeFound("createdOn.specified=true");

        // Get all the designationList where createdOn is null
        defaultDesignationShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationsByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where deleted equals to DEFAULT_DELETED
        defaultDesignationShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the designationList where deleted equals to UPDATED_DELETED
        defaultDesignationShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllDesignationsByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultDesignationShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the designationList where deleted equals to UPDATED_DELETED
        defaultDesignationShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllDesignationsByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where deleted is not null
        defaultDesignationShouldBeFound("deleted.specified=true");

        // Get all the designationList where deleted is null
        defaultDesignationShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationsByCompanyIsEqualToSomething() throws Exception {
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            designationRepository.saveAndFlush(designation);
            company = CompanyResourceIT.createEntity(em);
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(company);
        em.flush();
        designation.setCompany(company);
        designationRepository.saveAndFlush(designation);
        Long companyId = company.getId();

        // Get all the designationList where company equals to companyId
        defaultDesignationShouldBeFound("companyId.equals=" + companyId);

        // Get all the designationList where company equals to (companyId + 1)
        defaultDesignationShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDesignationShouldBeFound(String filter) throws Exception {
        restDesignationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designation.getId().intValue())))
            .andExpect(jsonPath("$.[*].designationName").value(hasItem(DEFAULT_DESIGNATION_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));

        // Check, that the count call also returns 1
        restDesignationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDesignationShouldNotBeFound(String filter) throws Exception {
        restDesignationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDesignationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDesignation() throws Exception {
        // Get the designation
        restDesignationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDesignation() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        int databaseSizeBeforeUpdate = designationRepository.findAll().size();

        // Update the designation
        Designation updatedDesignation = designationRepository.findById(designation.getId()).get();
        // Disconnect from session so that the updates on updatedDesignation are not directly saved in db
        em.detach(updatedDesignation);
        updatedDesignation
            .designationName(UPDATED_DESIGNATION_NAME)
            .description(UPDATED_DESCRIPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);
        DesignationDTO designationDTO = designationMapper.toDto(updatedDesignation);

        restDesignationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, designationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
        Designation testDesignation = designationList.get(designationList.size() - 1);
        assertThat(testDesignation.getDesignationName()).isEqualTo(UPDATED_DESIGNATION_NAME);
        assertThat(testDesignation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDesignation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDesignation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testDesignation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDesignation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDesignation.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingDesignation() throws Exception {
        int databaseSizeBeforeUpdate = designationRepository.findAll().size();
        designation.setId(count.incrementAndGet());

        // Create the Designation
        DesignationDTO designationDTO = designationMapper.toDto(designation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesignationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, designationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDesignation() throws Exception {
        int databaseSizeBeforeUpdate = designationRepository.findAll().size();
        designation.setId(count.incrementAndGet());

        // Create the Designation
        DesignationDTO designationDTO = designationMapper.toDto(designation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDesignation() throws Exception {
        int databaseSizeBeforeUpdate = designationRepository.findAll().size();
        designation.setId(count.incrementAndGet());

        // Create the Designation
        DesignationDTO designationDTO = designationMapper.toDto(designation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(designationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDesignationWithPatch() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        int databaseSizeBeforeUpdate = designationRepository.findAll().size();

        // Update the designation using partial update
        Designation partialUpdatedDesignation = new Designation();
        partialUpdatedDesignation.setId(designation.getId());

        partialUpdatedDesignation
            .designationName(UPDATED_DESIGNATION_NAME)
            .description(UPDATED_DESCRIPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY);

        restDesignationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDesignation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDesignation))
            )
            .andExpect(status().isOk());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
        Designation testDesignation = designationList.get(designationList.size() - 1);
        assertThat(testDesignation.getDesignationName()).isEqualTo(UPDATED_DESIGNATION_NAME);
        assertThat(testDesignation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDesignation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDesignation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testDesignation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDesignation.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDesignation.getDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateDesignationWithPatch() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        int databaseSizeBeforeUpdate = designationRepository.findAll().size();

        // Update the designation using partial update
        Designation partialUpdatedDesignation = new Designation();
        partialUpdatedDesignation.setId(designation.getId());

        partialUpdatedDesignation
            .designationName(UPDATED_DESIGNATION_NAME)
            .description(UPDATED_DESCRIPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);

        restDesignationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDesignation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDesignation))
            )
            .andExpect(status().isOk());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
        Designation testDesignation = designationList.get(designationList.size() - 1);
        assertThat(testDesignation.getDesignationName()).isEqualTo(UPDATED_DESIGNATION_NAME);
        assertThat(testDesignation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDesignation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDesignation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testDesignation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDesignation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDesignation.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingDesignation() throws Exception {
        int databaseSizeBeforeUpdate = designationRepository.findAll().size();
        designation.setId(count.incrementAndGet());

        // Create the Designation
        DesignationDTO designationDTO = designationMapper.toDto(designation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesignationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, designationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(designationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDesignation() throws Exception {
        int databaseSizeBeforeUpdate = designationRepository.findAll().size();
        designation.setId(count.incrementAndGet());

        // Create the Designation
        DesignationDTO designationDTO = designationMapper.toDto(designation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(designationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDesignation() throws Exception {
        int databaseSizeBeforeUpdate = designationRepository.findAll().size();
        designation.setId(count.incrementAndGet());

        // Create the Designation
        DesignationDTO designationDTO = designationMapper.toDto(designation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(designationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDesignation() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        int databaseSizeBeforeDelete = designationRepository.findAll().size();

        // Delete the designation
        restDesignationMockMvc
            .perform(delete(ENTITY_API_URL_ID, designation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
