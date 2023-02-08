package com.techvg.shrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.shrms.IntegrationTest;
import com.techvg.shrms.domain.Address;
import com.techvg.shrms.domain.Branch;
import com.techvg.shrms.domain.Company;
import com.techvg.shrms.domain.Region;
import com.techvg.shrms.domain.enumeration.BranchType;
import com.techvg.shrms.repository.BranchRepository;
import com.techvg.shrms.service.criteria.BranchCriteria;
import com.techvg.shrms.service.dto.BranchDTO;
import com.techvg.shrms.service.mapper.BranchMapper;
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
 * Integration tests for the {@link BranchResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BranchResourceIT {

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCHCODE = "AAAAAAAAAA";
    private static final String UPDATED_BRANCHCODE = "BBBBBBBBBB";

    private static final String DEFAULT_IFSC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IFSC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MICR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MICR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SWIFT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SWIFT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_IBAN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IBAN_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_HEAD_OFFICE = false;
    private static final Boolean UPDATED_IS_HEAD_OFFICE = true;

    private static final String DEFAULT_ROUTING_TRANSIT_NO = "AAAAAAAAAA";
    private static final String UPDATED_ROUTING_TRANSIT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_SITE = "AAAAAAAAAA";
    private static final String UPDATED_WEB_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVATE = false;
    private static final Boolean UPDATED_IS_ACTIVATE = true;

    private static final BranchType DEFAULT_BRANCH_TYPE = BranchType.ZONAL_OFFICE;
    private static final BranchType UPDATED_BRANCH_TYPE = BranchType.BRANCH;

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

    private static final String ENTITY_API_URL = "/api/branches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BranchMapper branchMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBranchMockMvc;

    private Branch branch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Branch createEntity(EntityManager em) {
        Branch branch = new Branch()
            .branchName(DEFAULT_BRANCH_NAME)
            .description(DEFAULT_DESCRIPTION)
            .branchcode(DEFAULT_BRANCHCODE)
            .ifscCode(DEFAULT_IFSC_CODE)
            .micrCode(DEFAULT_MICR_CODE)
            .swiftCode(DEFAULT_SWIFT_CODE)
            .ibanCode(DEFAULT_IBAN_CODE)
            .isHeadOffice(DEFAULT_IS_HEAD_OFFICE)
            .routingTransitNo(DEFAULT_ROUTING_TRANSIT_NO)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .webSite(DEFAULT_WEB_SITE)
            .fax(DEFAULT_FAX)
            .isActivate(DEFAULT_IS_ACTIVATE)
            .branchType(DEFAULT_BRANCH_TYPE)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .deleted(DEFAULT_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return branch;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Branch createUpdatedEntity(EntityManager em) {
        Branch branch = new Branch()
            .branchName(UPDATED_BRANCH_NAME)
            .description(UPDATED_DESCRIPTION)
            .branchcode(UPDATED_BRANCHCODE)
            .ifscCode(UPDATED_IFSC_CODE)
            .micrCode(UPDATED_MICR_CODE)
            .swiftCode(UPDATED_SWIFT_CODE)
            .ibanCode(UPDATED_IBAN_CODE)
            .isHeadOffice(UPDATED_IS_HEAD_OFFICE)
            .routingTransitNo(UPDATED_ROUTING_TRANSIT_NO)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .webSite(UPDATED_WEB_SITE)
            .fax(UPDATED_FAX)
            .isActivate(UPDATED_IS_ACTIVATE)
            .branchType(UPDATED_BRANCH_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return branch;
    }

    @BeforeEach
    public void initTest() {
        branch = createEntity(em);
    }

    @Test
    @Transactional
    void createBranch() throws Exception {
        int databaseSizeBeforeCreate = branchRepository.findAll().size();
        // Create the Branch
        BranchDTO branchDTO = branchMapper.toDto(branch);
        restBranchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(branchDTO)))
            .andExpect(status().isCreated());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeCreate + 1);
        Branch testBranch = branchList.get(branchList.size() - 1);
        assertThat(testBranch.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testBranch.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBranch.getBranchcode()).isEqualTo(DEFAULT_BRANCHCODE);
        assertThat(testBranch.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testBranch.getMicrCode()).isEqualTo(DEFAULT_MICR_CODE);
        assertThat(testBranch.getSwiftCode()).isEqualTo(DEFAULT_SWIFT_CODE);
        assertThat(testBranch.getIbanCode()).isEqualTo(DEFAULT_IBAN_CODE);
        assertThat(testBranch.getIsHeadOffice()).isEqualTo(DEFAULT_IS_HEAD_OFFICE);
        assertThat(testBranch.getRoutingTransitNo()).isEqualTo(DEFAULT_ROUTING_TRANSIT_NO);
        assertThat(testBranch.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testBranch.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testBranch.getWebSite()).isEqualTo(DEFAULT_WEB_SITE);
        assertThat(testBranch.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testBranch.getIsActivate()).isEqualTo(DEFAULT_IS_ACTIVATE);
        assertThat(testBranch.getBranchType()).isEqualTo(DEFAULT_BRANCH_TYPE);
        assertThat(testBranch.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testBranch.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBranch.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testBranch.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testBranch.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createBranchWithExistingId() throws Exception {
        // Create the Branch with an existing ID
        branch.setId(1L);
        BranchDTO branchDTO = branchMapper.toDto(branch);

        int databaseSizeBeforeCreate = branchRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBranchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(branchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBranchNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchRepository.findAll().size();
        // set the field null
        branch.setBranchName(null);

        // Create the Branch, which fails.
        BranchDTO branchDTO = branchMapper.toDto(branch);

        restBranchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(branchDTO)))
            .andExpect(status().isBadRequest());

        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBranches() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList
        restBranchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(branch.getId().intValue())))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].branchcode").value(hasItem(DEFAULT_BRANCHCODE)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].micrCode").value(hasItem(DEFAULT_MICR_CODE)))
            .andExpect(jsonPath("$.[*].swiftCode").value(hasItem(DEFAULT_SWIFT_CODE)))
            .andExpect(jsonPath("$.[*].ibanCode").value(hasItem(DEFAULT_IBAN_CODE)))
            .andExpect(jsonPath("$.[*].isHeadOffice").value(hasItem(DEFAULT_IS_HEAD_OFFICE.booleanValue())))
            .andExpect(jsonPath("$.[*].routingTransitNo").value(hasItem(DEFAULT_ROUTING_TRANSIT_NO)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].webSite").value(hasItem(DEFAULT_WEB_SITE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].isActivate").value(hasItem(DEFAULT_IS_ACTIVATE.booleanValue())))
            .andExpect(jsonPath("$.[*].branchType").value(hasItem(DEFAULT_BRANCH_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getBranch() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get the branch
        restBranchMockMvc
            .perform(get(ENTITY_API_URL_ID, branch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(branch.getId().intValue()))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.branchcode").value(DEFAULT_BRANCHCODE))
            .andExpect(jsonPath("$.ifscCode").value(DEFAULT_IFSC_CODE))
            .andExpect(jsonPath("$.micrCode").value(DEFAULT_MICR_CODE))
            .andExpect(jsonPath("$.swiftCode").value(DEFAULT_SWIFT_CODE))
            .andExpect(jsonPath("$.ibanCode").value(DEFAULT_IBAN_CODE))
            .andExpect(jsonPath("$.isHeadOffice").value(DEFAULT_IS_HEAD_OFFICE.booleanValue()))
            .andExpect(jsonPath("$.routingTransitNo").value(DEFAULT_ROUTING_TRANSIT_NO))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.webSite").value(DEFAULT_WEB_SITE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.isActivate").value(DEFAULT_IS_ACTIVATE.booleanValue()))
            .andExpect(jsonPath("$.branchType").value(DEFAULT_BRANCH_TYPE.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getBranchesByIdFiltering() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        Long id = branch.getId();

        defaultBranchShouldBeFound("id.equals=" + id);
        defaultBranchShouldNotBeFound("id.notEquals=" + id);

        defaultBranchShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBranchShouldNotBeFound("id.greaterThan=" + id);

        defaultBranchShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBranchShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchNameIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchName equals to DEFAULT_BRANCH_NAME
        defaultBranchShouldBeFound("branchName.equals=" + DEFAULT_BRANCH_NAME);

        // Get all the branchList where branchName equals to UPDATED_BRANCH_NAME
        defaultBranchShouldNotBeFound("branchName.equals=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchNameIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchName in DEFAULT_BRANCH_NAME or UPDATED_BRANCH_NAME
        defaultBranchShouldBeFound("branchName.in=" + DEFAULT_BRANCH_NAME + "," + UPDATED_BRANCH_NAME);

        // Get all the branchList where branchName equals to UPDATED_BRANCH_NAME
        defaultBranchShouldNotBeFound("branchName.in=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchName is not null
        defaultBranchShouldBeFound("branchName.specified=true");

        // Get all the branchList where branchName is null
        defaultBranchShouldNotBeFound("branchName.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByBranchNameContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchName contains DEFAULT_BRANCH_NAME
        defaultBranchShouldBeFound("branchName.contains=" + DEFAULT_BRANCH_NAME);

        // Get all the branchList where branchName contains UPDATED_BRANCH_NAME
        defaultBranchShouldNotBeFound("branchName.contains=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchNameNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchName does not contain DEFAULT_BRANCH_NAME
        defaultBranchShouldNotBeFound("branchName.doesNotContain=" + DEFAULT_BRANCH_NAME);

        // Get all the branchList where branchName does not contain UPDATED_BRANCH_NAME
        defaultBranchShouldBeFound("branchName.doesNotContain=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllBranchesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where description equals to DEFAULT_DESCRIPTION
        defaultBranchShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the branchList where description equals to UPDATED_DESCRIPTION
        defaultBranchShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBranchesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultBranchShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the branchList where description equals to UPDATED_DESCRIPTION
        defaultBranchShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBranchesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where description is not null
        defaultBranchShouldBeFound("description.specified=true");

        // Get all the branchList where description is null
        defaultBranchShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where description contains DEFAULT_DESCRIPTION
        defaultBranchShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the branchList where description contains UPDATED_DESCRIPTION
        defaultBranchShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBranchesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where description does not contain DEFAULT_DESCRIPTION
        defaultBranchShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the branchList where description does not contain UPDATED_DESCRIPTION
        defaultBranchShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchcode equals to DEFAULT_BRANCHCODE
        defaultBranchShouldBeFound("branchcode.equals=" + DEFAULT_BRANCHCODE);

        // Get all the branchList where branchcode equals to UPDATED_BRANCHCODE
        defaultBranchShouldNotBeFound("branchcode.equals=" + UPDATED_BRANCHCODE);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchcodeIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchcode in DEFAULT_BRANCHCODE or UPDATED_BRANCHCODE
        defaultBranchShouldBeFound("branchcode.in=" + DEFAULT_BRANCHCODE + "," + UPDATED_BRANCHCODE);

        // Get all the branchList where branchcode equals to UPDATED_BRANCHCODE
        defaultBranchShouldNotBeFound("branchcode.in=" + UPDATED_BRANCHCODE);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchcode is not null
        defaultBranchShouldBeFound("branchcode.specified=true");

        // Get all the branchList where branchcode is null
        defaultBranchShouldNotBeFound("branchcode.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByBranchcodeContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchcode contains DEFAULT_BRANCHCODE
        defaultBranchShouldBeFound("branchcode.contains=" + DEFAULT_BRANCHCODE);

        // Get all the branchList where branchcode contains UPDATED_BRANCHCODE
        defaultBranchShouldNotBeFound("branchcode.contains=" + UPDATED_BRANCHCODE);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchcodeNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchcode does not contain DEFAULT_BRANCHCODE
        defaultBranchShouldNotBeFound("branchcode.doesNotContain=" + DEFAULT_BRANCHCODE);

        // Get all the branchList where branchcode does not contain UPDATED_BRANCHCODE
        defaultBranchShouldBeFound("branchcode.doesNotContain=" + UPDATED_BRANCHCODE);
    }

    @Test
    @Transactional
    void getAllBranchesByIfscCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where ifscCode equals to DEFAULT_IFSC_CODE
        defaultBranchShouldBeFound("ifscCode.equals=" + DEFAULT_IFSC_CODE);

        // Get all the branchList where ifscCode equals to UPDATED_IFSC_CODE
        defaultBranchShouldNotBeFound("ifscCode.equals=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllBranchesByIfscCodeIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where ifscCode in DEFAULT_IFSC_CODE or UPDATED_IFSC_CODE
        defaultBranchShouldBeFound("ifscCode.in=" + DEFAULT_IFSC_CODE + "," + UPDATED_IFSC_CODE);

        // Get all the branchList where ifscCode equals to UPDATED_IFSC_CODE
        defaultBranchShouldNotBeFound("ifscCode.in=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllBranchesByIfscCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where ifscCode is not null
        defaultBranchShouldBeFound("ifscCode.specified=true");

        // Get all the branchList where ifscCode is null
        defaultBranchShouldNotBeFound("ifscCode.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByIfscCodeContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where ifscCode contains DEFAULT_IFSC_CODE
        defaultBranchShouldBeFound("ifscCode.contains=" + DEFAULT_IFSC_CODE);

        // Get all the branchList where ifscCode contains UPDATED_IFSC_CODE
        defaultBranchShouldNotBeFound("ifscCode.contains=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllBranchesByIfscCodeNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where ifscCode does not contain DEFAULT_IFSC_CODE
        defaultBranchShouldNotBeFound("ifscCode.doesNotContain=" + DEFAULT_IFSC_CODE);

        // Get all the branchList where ifscCode does not contain UPDATED_IFSC_CODE
        defaultBranchShouldBeFound("ifscCode.doesNotContain=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllBranchesByMicrCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where micrCode equals to DEFAULT_MICR_CODE
        defaultBranchShouldBeFound("micrCode.equals=" + DEFAULT_MICR_CODE);

        // Get all the branchList where micrCode equals to UPDATED_MICR_CODE
        defaultBranchShouldNotBeFound("micrCode.equals=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    void getAllBranchesByMicrCodeIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where micrCode in DEFAULT_MICR_CODE or UPDATED_MICR_CODE
        defaultBranchShouldBeFound("micrCode.in=" + DEFAULT_MICR_CODE + "," + UPDATED_MICR_CODE);

        // Get all the branchList where micrCode equals to UPDATED_MICR_CODE
        defaultBranchShouldNotBeFound("micrCode.in=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    void getAllBranchesByMicrCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where micrCode is not null
        defaultBranchShouldBeFound("micrCode.specified=true");

        // Get all the branchList where micrCode is null
        defaultBranchShouldNotBeFound("micrCode.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByMicrCodeContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where micrCode contains DEFAULT_MICR_CODE
        defaultBranchShouldBeFound("micrCode.contains=" + DEFAULT_MICR_CODE);

        // Get all the branchList where micrCode contains UPDATED_MICR_CODE
        defaultBranchShouldNotBeFound("micrCode.contains=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    void getAllBranchesByMicrCodeNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where micrCode does not contain DEFAULT_MICR_CODE
        defaultBranchShouldNotBeFound("micrCode.doesNotContain=" + DEFAULT_MICR_CODE);

        // Get all the branchList where micrCode does not contain UPDATED_MICR_CODE
        defaultBranchShouldBeFound("micrCode.doesNotContain=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    void getAllBranchesBySwiftCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where swiftCode equals to DEFAULT_SWIFT_CODE
        defaultBranchShouldBeFound("swiftCode.equals=" + DEFAULT_SWIFT_CODE);

        // Get all the branchList where swiftCode equals to UPDATED_SWIFT_CODE
        defaultBranchShouldNotBeFound("swiftCode.equals=" + UPDATED_SWIFT_CODE);
    }

    @Test
    @Transactional
    void getAllBranchesBySwiftCodeIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where swiftCode in DEFAULT_SWIFT_CODE or UPDATED_SWIFT_CODE
        defaultBranchShouldBeFound("swiftCode.in=" + DEFAULT_SWIFT_CODE + "," + UPDATED_SWIFT_CODE);

        // Get all the branchList where swiftCode equals to UPDATED_SWIFT_CODE
        defaultBranchShouldNotBeFound("swiftCode.in=" + UPDATED_SWIFT_CODE);
    }

    @Test
    @Transactional
    void getAllBranchesBySwiftCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where swiftCode is not null
        defaultBranchShouldBeFound("swiftCode.specified=true");

        // Get all the branchList where swiftCode is null
        defaultBranchShouldNotBeFound("swiftCode.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesBySwiftCodeContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where swiftCode contains DEFAULT_SWIFT_CODE
        defaultBranchShouldBeFound("swiftCode.contains=" + DEFAULT_SWIFT_CODE);

        // Get all the branchList where swiftCode contains UPDATED_SWIFT_CODE
        defaultBranchShouldNotBeFound("swiftCode.contains=" + UPDATED_SWIFT_CODE);
    }

    @Test
    @Transactional
    void getAllBranchesBySwiftCodeNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where swiftCode does not contain DEFAULT_SWIFT_CODE
        defaultBranchShouldNotBeFound("swiftCode.doesNotContain=" + DEFAULT_SWIFT_CODE);

        // Get all the branchList where swiftCode does not contain UPDATED_SWIFT_CODE
        defaultBranchShouldBeFound("swiftCode.doesNotContain=" + UPDATED_SWIFT_CODE);
    }

    @Test
    @Transactional
    void getAllBranchesByIbanCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where ibanCode equals to DEFAULT_IBAN_CODE
        defaultBranchShouldBeFound("ibanCode.equals=" + DEFAULT_IBAN_CODE);

        // Get all the branchList where ibanCode equals to UPDATED_IBAN_CODE
        defaultBranchShouldNotBeFound("ibanCode.equals=" + UPDATED_IBAN_CODE);
    }

    @Test
    @Transactional
    void getAllBranchesByIbanCodeIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where ibanCode in DEFAULT_IBAN_CODE or UPDATED_IBAN_CODE
        defaultBranchShouldBeFound("ibanCode.in=" + DEFAULT_IBAN_CODE + "," + UPDATED_IBAN_CODE);

        // Get all the branchList where ibanCode equals to UPDATED_IBAN_CODE
        defaultBranchShouldNotBeFound("ibanCode.in=" + UPDATED_IBAN_CODE);
    }

    @Test
    @Transactional
    void getAllBranchesByIbanCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where ibanCode is not null
        defaultBranchShouldBeFound("ibanCode.specified=true");

        // Get all the branchList where ibanCode is null
        defaultBranchShouldNotBeFound("ibanCode.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByIbanCodeContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where ibanCode contains DEFAULT_IBAN_CODE
        defaultBranchShouldBeFound("ibanCode.contains=" + DEFAULT_IBAN_CODE);

        // Get all the branchList where ibanCode contains UPDATED_IBAN_CODE
        defaultBranchShouldNotBeFound("ibanCode.contains=" + UPDATED_IBAN_CODE);
    }

    @Test
    @Transactional
    void getAllBranchesByIbanCodeNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where ibanCode does not contain DEFAULT_IBAN_CODE
        defaultBranchShouldNotBeFound("ibanCode.doesNotContain=" + DEFAULT_IBAN_CODE);

        // Get all the branchList where ibanCode does not contain UPDATED_IBAN_CODE
        defaultBranchShouldBeFound("ibanCode.doesNotContain=" + UPDATED_IBAN_CODE);
    }

    @Test
    @Transactional
    void getAllBranchesByIsHeadOfficeIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where isHeadOffice equals to DEFAULT_IS_HEAD_OFFICE
        defaultBranchShouldBeFound("isHeadOffice.equals=" + DEFAULT_IS_HEAD_OFFICE);

        // Get all the branchList where isHeadOffice equals to UPDATED_IS_HEAD_OFFICE
        defaultBranchShouldNotBeFound("isHeadOffice.equals=" + UPDATED_IS_HEAD_OFFICE);
    }

    @Test
    @Transactional
    void getAllBranchesByIsHeadOfficeIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where isHeadOffice in DEFAULT_IS_HEAD_OFFICE or UPDATED_IS_HEAD_OFFICE
        defaultBranchShouldBeFound("isHeadOffice.in=" + DEFAULT_IS_HEAD_OFFICE + "," + UPDATED_IS_HEAD_OFFICE);

        // Get all the branchList where isHeadOffice equals to UPDATED_IS_HEAD_OFFICE
        defaultBranchShouldNotBeFound("isHeadOffice.in=" + UPDATED_IS_HEAD_OFFICE);
    }

    @Test
    @Transactional
    void getAllBranchesByIsHeadOfficeIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where isHeadOffice is not null
        defaultBranchShouldBeFound("isHeadOffice.specified=true");

        // Get all the branchList where isHeadOffice is null
        defaultBranchShouldNotBeFound("isHeadOffice.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByRoutingTransitNoIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where routingTransitNo equals to DEFAULT_ROUTING_TRANSIT_NO
        defaultBranchShouldBeFound("routingTransitNo.equals=" + DEFAULT_ROUTING_TRANSIT_NO);

        // Get all the branchList where routingTransitNo equals to UPDATED_ROUTING_TRANSIT_NO
        defaultBranchShouldNotBeFound("routingTransitNo.equals=" + UPDATED_ROUTING_TRANSIT_NO);
    }

    @Test
    @Transactional
    void getAllBranchesByRoutingTransitNoIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where routingTransitNo in DEFAULT_ROUTING_TRANSIT_NO or UPDATED_ROUTING_TRANSIT_NO
        defaultBranchShouldBeFound("routingTransitNo.in=" + DEFAULT_ROUTING_TRANSIT_NO + "," + UPDATED_ROUTING_TRANSIT_NO);

        // Get all the branchList where routingTransitNo equals to UPDATED_ROUTING_TRANSIT_NO
        defaultBranchShouldNotBeFound("routingTransitNo.in=" + UPDATED_ROUTING_TRANSIT_NO);
    }

    @Test
    @Transactional
    void getAllBranchesByRoutingTransitNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where routingTransitNo is not null
        defaultBranchShouldBeFound("routingTransitNo.specified=true");

        // Get all the branchList where routingTransitNo is null
        defaultBranchShouldNotBeFound("routingTransitNo.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByRoutingTransitNoContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where routingTransitNo contains DEFAULT_ROUTING_TRANSIT_NO
        defaultBranchShouldBeFound("routingTransitNo.contains=" + DEFAULT_ROUTING_TRANSIT_NO);

        // Get all the branchList where routingTransitNo contains UPDATED_ROUTING_TRANSIT_NO
        defaultBranchShouldNotBeFound("routingTransitNo.contains=" + UPDATED_ROUTING_TRANSIT_NO);
    }

    @Test
    @Transactional
    void getAllBranchesByRoutingTransitNoNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where routingTransitNo does not contain DEFAULT_ROUTING_TRANSIT_NO
        defaultBranchShouldNotBeFound("routingTransitNo.doesNotContain=" + DEFAULT_ROUTING_TRANSIT_NO);

        // Get all the branchList where routingTransitNo does not contain UPDATED_ROUTING_TRANSIT_NO
        defaultBranchShouldBeFound("routingTransitNo.doesNotContain=" + UPDATED_ROUTING_TRANSIT_NO);
    }

    @Test
    @Transactional
    void getAllBranchesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where phone equals to DEFAULT_PHONE
        defaultBranchShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the branchList where phone equals to UPDATED_PHONE
        defaultBranchShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllBranchesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultBranchShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the branchList where phone equals to UPDATED_PHONE
        defaultBranchShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllBranchesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where phone is not null
        defaultBranchShouldBeFound("phone.specified=true");

        // Get all the branchList where phone is null
        defaultBranchShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByPhoneContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where phone contains DEFAULT_PHONE
        defaultBranchShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the branchList where phone contains UPDATED_PHONE
        defaultBranchShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllBranchesByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where phone does not contain DEFAULT_PHONE
        defaultBranchShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the branchList where phone does not contain UPDATED_PHONE
        defaultBranchShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllBranchesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where email equals to DEFAULT_EMAIL
        defaultBranchShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the branchList where email equals to UPDATED_EMAIL
        defaultBranchShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllBranchesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultBranchShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the branchList where email equals to UPDATED_EMAIL
        defaultBranchShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllBranchesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where email is not null
        defaultBranchShouldBeFound("email.specified=true");

        // Get all the branchList where email is null
        defaultBranchShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByEmailContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where email contains DEFAULT_EMAIL
        defaultBranchShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the branchList where email contains UPDATED_EMAIL
        defaultBranchShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllBranchesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where email does not contain DEFAULT_EMAIL
        defaultBranchShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the branchList where email does not contain UPDATED_EMAIL
        defaultBranchShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllBranchesByWebSiteIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where webSite equals to DEFAULT_WEB_SITE
        defaultBranchShouldBeFound("webSite.equals=" + DEFAULT_WEB_SITE);

        // Get all the branchList where webSite equals to UPDATED_WEB_SITE
        defaultBranchShouldNotBeFound("webSite.equals=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    void getAllBranchesByWebSiteIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where webSite in DEFAULT_WEB_SITE or UPDATED_WEB_SITE
        defaultBranchShouldBeFound("webSite.in=" + DEFAULT_WEB_SITE + "," + UPDATED_WEB_SITE);

        // Get all the branchList where webSite equals to UPDATED_WEB_SITE
        defaultBranchShouldNotBeFound("webSite.in=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    void getAllBranchesByWebSiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where webSite is not null
        defaultBranchShouldBeFound("webSite.specified=true");

        // Get all the branchList where webSite is null
        defaultBranchShouldNotBeFound("webSite.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByWebSiteContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where webSite contains DEFAULT_WEB_SITE
        defaultBranchShouldBeFound("webSite.contains=" + DEFAULT_WEB_SITE);

        // Get all the branchList where webSite contains UPDATED_WEB_SITE
        defaultBranchShouldNotBeFound("webSite.contains=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    void getAllBranchesByWebSiteNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where webSite does not contain DEFAULT_WEB_SITE
        defaultBranchShouldNotBeFound("webSite.doesNotContain=" + DEFAULT_WEB_SITE);

        // Get all the branchList where webSite does not contain UPDATED_WEB_SITE
        defaultBranchShouldBeFound("webSite.doesNotContain=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    void getAllBranchesByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where fax equals to DEFAULT_FAX
        defaultBranchShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the branchList where fax equals to UPDATED_FAX
        defaultBranchShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllBranchesByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultBranchShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the branchList where fax equals to UPDATED_FAX
        defaultBranchShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllBranchesByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where fax is not null
        defaultBranchShouldBeFound("fax.specified=true");

        // Get all the branchList where fax is null
        defaultBranchShouldNotBeFound("fax.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByFaxContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where fax contains DEFAULT_FAX
        defaultBranchShouldBeFound("fax.contains=" + DEFAULT_FAX);

        // Get all the branchList where fax contains UPDATED_FAX
        defaultBranchShouldNotBeFound("fax.contains=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllBranchesByFaxNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where fax does not contain DEFAULT_FAX
        defaultBranchShouldNotBeFound("fax.doesNotContain=" + DEFAULT_FAX);

        // Get all the branchList where fax does not contain UPDATED_FAX
        defaultBranchShouldBeFound("fax.doesNotContain=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllBranchesByIsActivateIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where isActivate equals to DEFAULT_IS_ACTIVATE
        defaultBranchShouldBeFound("isActivate.equals=" + DEFAULT_IS_ACTIVATE);

        // Get all the branchList where isActivate equals to UPDATED_IS_ACTIVATE
        defaultBranchShouldNotBeFound("isActivate.equals=" + UPDATED_IS_ACTIVATE);
    }

    @Test
    @Transactional
    void getAllBranchesByIsActivateIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where isActivate in DEFAULT_IS_ACTIVATE or UPDATED_IS_ACTIVATE
        defaultBranchShouldBeFound("isActivate.in=" + DEFAULT_IS_ACTIVATE + "," + UPDATED_IS_ACTIVATE);

        // Get all the branchList where isActivate equals to UPDATED_IS_ACTIVATE
        defaultBranchShouldNotBeFound("isActivate.in=" + UPDATED_IS_ACTIVATE);
    }

    @Test
    @Transactional
    void getAllBranchesByIsActivateIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where isActivate is not null
        defaultBranchShouldBeFound("isActivate.specified=true");

        // Get all the branchList where isActivate is null
        defaultBranchShouldNotBeFound("isActivate.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByBranchTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchType equals to DEFAULT_BRANCH_TYPE
        defaultBranchShouldBeFound("branchType.equals=" + DEFAULT_BRANCH_TYPE);

        // Get all the branchList where branchType equals to UPDATED_BRANCH_TYPE
        defaultBranchShouldNotBeFound("branchType.equals=" + UPDATED_BRANCH_TYPE);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchTypeIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchType in DEFAULT_BRANCH_TYPE or UPDATED_BRANCH_TYPE
        defaultBranchShouldBeFound("branchType.in=" + DEFAULT_BRANCH_TYPE + "," + UPDATED_BRANCH_TYPE);

        // Get all the branchList where branchType equals to UPDATED_BRANCH_TYPE
        defaultBranchShouldNotBeFound("branchType.in=" + UPDATED_BRANCH_TYPE);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchType is not null
        defaultBranchShouldBeFound("branchType.specified=true");

        // Get all the branchList where branchType is null
        defaultBranchShouldNotBeFound("branchType.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where createdOn equals to DEFAULT_CREATED_ON
        defaultBranchShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the branchList where createdOn equals to UPDATED_CREATED_ON
        defaultBranchShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllBranchesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultBranchShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the branchList where createdOn equals to UPDATED_CREATED_ON
        defaultBranchShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllBranchesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where createdOn is not null
        defaultBranchShouldBeFound("createdOn.specified=true");

        // Get all the branchList where createdOn is null
        defaultBranchShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where createdBy equals to DEFAULT_CREATED_BY
        defaultBranchShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the branchList where createdBy equals to UPDATED_CREATED_BY
        defaultBranchShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllBranchesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultBranchShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the branchList where createdBy equals to UPDATED_CREATED_BY
        defaultBranchShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllBranchesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where createdBy is not null
        defaultBranchShouldBeFound("createdBy.specified=true");

        // Get all the branchList where createdBy is null
        defaultBranchShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where createdBy contains DEFAULT_CREATED_BY
        defaultBranchShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the branchList where createdBy contains UPDATED_CREATED_BY
        defaultBranchShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllBranchesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where createdBy does not contain DEFAULT_CREATED_BY
        defaultBranchShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the branchList where createdBy does not contain UPDATED_CREATED_BY
        defaultBranchShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllBranchesByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where deleted equals to DEFAULT_DELETED
        defaultBranchShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the branchList where deleted equals to UPDATED_DELETED
        defaultBranchShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllBranchesByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultBranchShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the branchList where deleted equals to UPDATED_DELETED
        defaultBranchShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllBranchesByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where deleted is not null
        defaultBranchShouldBeFound("deleted.specified=true");

        // Get all the branchList where deleted is null
        defaultBranchShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByDeletedContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where deleted contains DEFAULT_DELETED
        defaultBranchShouldBeFound("deleted.contains=" + DEFAULT_DELETED);

        // Get all the branchList where deleted contains UPDATED_DELETED
        defaultBranchShouldNotBeFound("deleted.contains=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllBranchesByDeletedNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where deleted does not contain DEFAULT_DELETED
        defaultBranchShouldNotBeFound("deleted.doesNotContain=" + DEFAULT_DELETED);

        // Get all the branchList where deleted does not contain UPDATED_DELETED
        defaultBranchShouldBeFound("deleted.doesNotContain=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllBranchesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultBranchShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the branchList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBranchShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBranchesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultBranchShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the branchList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBranchShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBranchesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where lastModified is not null
        defaultBranchShouldBeFound("lastModified.specified=true");

        // Get all the branchList where lastModified is null
        defaultBranchShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultBranchShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the branchList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBranchShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBranchesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultBranchShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the branchList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBranchShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBranchesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where lastModifiedBy is not null
        defaultBranchShouldBeFound("lastModifiedBy.specified=true");

        // Get all the branchList where lastModifiedBy is null
        defaultBranchShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultBranchShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the branchList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultBranchShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBranchesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultBranchShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the branchList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultBranchShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBranchesByAddressIsEqualToSomething() throws Exception {
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            branchRepository.saveAndFlush(branch);
            address = AddressResourceIT.createEntity(em);
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        em.persist(address);
        em.flush();
        branch.setAddress(address);
        branchRepository.saveAndFlush(branch);
        Long addressId = address.getId();

        // Get all the branchList where address equals to addressId
        defaultBranchShouldBeFound("addressId.equals=" + addressId);

        // Get all the branchList where address equals to (addressId + 1)
        defaultBranchShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }

    @Test
    @Transactional
    void getAllBranchesByCompanyIsEqualToSomething() throws Exception {
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            branchRepository.saveAndFlush(branch);
            company = CompanyResourceIT.createEntity(em);
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(company);
        em.flush();
        branch.setCompany(company);
        branchRepository.saveAndFlush(branch);
        Long companyId = company.getId();

        // Get all the branchList where company equals to companyId
        defaultBranchShouldBeFound("companyId.equals=" + companyId);

        // Get all the branchList where company equals to (companyId + 1)
        defaultBranchShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    @Test
    @Transactional
    void getAllBranchesByBranchIsEqualToSomething() throws Exception {
        Region branch;
        if (TestUtil.findAll(em, Region.class).isEmpty()) {
            branchRepository.saveAndFlush(branch);
            branch = RegionResourceIT.createEntity(em);
        } else {
            branch = TestUtil.findAll(em, Region.class).get(0);
        }
        em.persist(branch);
        em.flush();
        branch.setBranch(branch);
        branchRepository.saveAndFlush(branch);
        Long branchId = branch.getId();

        // Get all the branchList where branch equals to branchId
        defaultBranchShouldBeFound("branchId.equals=" + branchId);

        // Get all the branchList where branch equals to (branchId + 1)
        defaultBranchShouldNotBeFound("branchId.equals=" + (branchId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBranchShouldBeFound(String filter) throws Exception {
        restBranchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(branch.getId().intValue())))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].branchcode").value(hasItem(DEFAULT_BRANCHCODE)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].micrCode").value(hasItem(DEFAULT_MICR_CODE)))
            .andExpect(jsonPath("$.[*].swiftCode").value(hasItem(DEFAULT_SWIFT_CODE)))
            .andExpect(jsonPath("$.[*].ibanCode").value(hasItem(DEFAULT_IBAN_CODE)))
            .andExpect(jsonPath("$.[*].isHeadOffice").value(hasItem(DEFAULT_IS_HEAD_OFFICE.booleanValue())))
            .andExpect(jsonPath("$.[*].routingTransitNo").value(hasItem(DEFAULT_ROUTING_TRANSIT_NO)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].webSite").value(hasItem(DEFAULT_WEB_SITE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].isActivate").value(hasItem(DEFAULT_IS_ACTIVATE.booleanValue())))
            .andExpect(jsonPath("$.[*].branchType").value(hasItem(DEFAULT_BRANCH_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restBranchMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBranchShouldNotBeFound(String filter) throws Exception {
        restBranchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBranchMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBranch() throws Exception {
        // Get the branch
        restBranchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBranch() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        int databaseSizeBeforeUpdate = branchRepository.findAll().size();

        // Update the branch
        Branch updatedBranch = branchRepository.findById(branch.getId()).get();
        // Disconnect from session so that the updates on updatedBranch are not directly saved in db
        em.detach(updatedBranch);
        updatedBranch
            .branchName(UPDATED_BRANCH_NAME)
            .description(UPDATED_DESCRIPTION)
            .branchcode(UPDATED_BRANCHCODE)
            .ifscCode(UPDATED_IFSC_CODE)
            .micrCode(UPDATED_MICR_CODE)
            .swiftCode(UPDATED_SWIFT_CODE)
            .ibanCode(UPDATED_IBAN_CODE)
            .isHeadOffice(UPDATED_IS_HEAD_OFFICE)
            .routingTransitNo(UPDATED_ROUTING_TRANSIT_NO)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .webSite(UPDATED_WEB_SITE)
            .fax(UPDATED_FAX)
            .isActivate(UPDATED_IS_ACTIVATE)
            .branchType(UPDATED_BRANCH_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        BranchDTO branchDTO = branchMapper.toDto(updatedBranch);

        restBranchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, branchDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(branchDTO))
            )
            .andExpect(status().isOk());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
        Branch testBranch = branchList.get(branchList.size() - 1);
        assertThat(testBranch.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testBranch.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBranch.getBranchcode()).isEqualTo(UPDATED_BRANCHCODE);
        assertThat(testBranch.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testBranch.getMicrCode()).isEqualTo(UPDATED_MICR_CODE);
        assertThat(testBranch.getSwiftCode()).isEqualTo(UPDATED_SWIFT_CODE);
        assertThat(testBranch.getIbanCode()).isEqualTo(UPDATED_IBAN_CODE);
        assertThat(testBranch.getIsHeadOffice()).isEqualTo(UPDATED_IS_HEAD_OFFICE);
        assertThat(testBranch.getRoutingTransitNo()).isEqualTo(UPDATED_ROUTING_TRANSIT_NO);
        assertThat(testBranch.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testBranch.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBranch.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testBranch.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testBranch.getIsActivate()).isEqualTo(UPDATED_IS_ACTIVATE);
        assertThat(testBranch.getBranchType()).isEqualTo(UPDATED_BRANCH_TYPE);
        assertThat(testBranch.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testBranch.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBranch.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testBranch.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBranch.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingBranch() throws Exception {
        int databaseSizeBeforeUpdate = branchRepository.findAll().size();
        branch.setId(count.incrementAndGet());

        // Create the Branch
        BranchDTO branchDTO = branchMapper.toDto(branch);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBranchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, branchDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(branchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBranch() throws Exception {
        int databaseSizeBeforeUpdate = branchRepository.findAll().size();
        branch.setId(count.incrementAndGet());

        // Create the Branch
        BranchDTO branchDTO = branchMapper.toDto(branch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBranchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(branchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBranch() throws Exception {
        int databaseSizeBeforeUpdate = branchRepository.findAll().size();
        branch.setId(count.incrementAndGet());

        // Create the Branch
        BranchDTO branchDTO = branchMapper.toDto(branch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBranchMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(branchDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBranchWithPatch() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        int databaseSizeBeforeUpdate = branchRepository.findAll().size();

        // Update the branch using partial update
        Branch partialUpdatedBranch = new Branch();
        partialUpdatedBranch.setId(branch.getId());

        partialUpdatedBranch
            .description(UPDATED_DESCRIPTION)
            .branchcode(UPDATED_BRANCHCODE)
            .micrCode(UPDATED_MICR_CODE)
            .swiftCode(UPDATED_SWIFT_CODE)
            .ibanCode(UPDATED_IBAN_CODE)
            .email(UPDATED_EMAIL)
            .isActivate(UPDATED_IS_ACTIVATE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .lastModified(UPDATED_LAST_MODIFIED);

        restBranchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBranch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBranch))
            )
            .andExpect(status().isOk());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
        Branch testBranch = branchList.get(branchList.size() - 1);
        assertThat(testBranch.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testBranch.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBranch.getBranchcode()).isEqualTo(UPDATED_BRANCHCODE);
        assertThat(testBranch.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testBranch.getMicrCode()).isEqualTo(UPDATED_MICR_CODE);
        assertThat(testBranch.getSwiftCode()).isEqualTo(UPDATED_SWIFT_CODE);
        assertThat(testBranch.getIbanCode()).isEqualTo(UPDATED_IBAN_CODE);
        assertThat(testBranch.getIsHeadOffice()).isEqualTo(DEFAULT_IS_HEAD_OFFICE);
        assertThat(testBranch.getRoutingTransitNo()).isEqualTo(DEFAULT_ROUTING_TRANSIT_NO);
        assertThat(testBranch.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testBranch.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBranch.getWebSite()).isEqualTo(DEFAULT_WEB_SITE);
        assertThat(testBranch.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testBranch.getIsActivate()).isEqualTo(UPDATED_IS_ACTIVATE);
        assertThat(testBranch.getBranchType()).isEqualTo(DEFAULT_BRANCH_TYPE);
        assertThat(testBranch.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testBranch.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBranch.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testBranch.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBranch.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateBranchWithPatch() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        int databaseSizeBeforeUpdate = branchRepository.findAll().size();

        // Update the branch using partial update
        Branch partialUpdatedBranch = new Branch();
        partialUpdatedBranch.setId(branch.getId());

        partialUpdatedBranch
            .branchName(UPDATED_BRANCH_NAME)
            .description(UPDATED_DESCRIPTION)
            .branchcode(UPDATED_BRANCHCODE)
            .ifscCode(UPDATED_IFSC_CODE)
            .micrCode(UPDATED_MICR_CODE)
            .swiftCode(UPDATED_SWIFT_CODE)
            .ibanCode(UPDATED_IBAN_CODE)
            .isHeadOffice(UPDATED_IS_HEAD_OFFICE)
            .routingTransitNo(UPDATED_ROUTING_TRANSIT_NO)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .webSite(UPDATED_WEB_SITE)
            .fax(UPDATED_FAX)
            .isActivate(UPDATED_IS_ACTIVATE)
            .branchType(UPDATED_BRANCH_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restBranchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBranch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBranch))
            )
            .andExpect(status().isOk());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
        Branch testBranch = branchList.get(branchList.size() - 1);
        assertThat(testBranch.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testBranch.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBranch.getBranchcode()).isEqualTo(UPDATED_BRANCHCODE);
        assertThat(testBranch.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testBranch.getMicrCode()).isEqualTo(UPDATED_MICR_CODE);
        assertThat(testBranch.getSwiftCode()).isEqualTo(UPDATED_SWIFT_CODE);
        assertThat(testBranch.getIbanCode()).isEqualTo(UPDATED_IBAN_CODE);
        assertThat(testBranch.getIsHeadOffice()).isEqualTo(UPDATED_IS_HEAD_OFFICE);
        assertThat(testBranch.getRoutingTransitNo()).isEqualTo(UPDATED_ROUTING_TRANSIT_NO);
        assertThat(testBranch.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testBranch.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBranch.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testBranch.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testBranch.getIsActivate()).isEqualTo(UPDATED_IS_ACTIVATE);
        assertThat(testBranch.getBranchType()).isEqualTo(UPDATED_BRANCH_TYPE);
        assertThat(testBranch.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testBranch.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBranch.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testBranch.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBranch.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingBranch() throws Exception {
        int databaseSizeBeforeUpdate = branchRepository.findAll().size();
        branch.setId(count.incrementAndGet());

        // Create the Branch
        BranchDTO branchDTO = branchMapper.toDto(branch);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBranchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, branchDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(branchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBranch() throws Exception {
        int databaseSizeBeforeUpdate = branchRepository.findAll().size();
        branch.setId(count.incrementAndGet());

        // Create the Branch
        BranchDTO branchDTO = branchMapper.toDto(branch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBranchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(branchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBranch() throws Exception {
        int databaseSizeBeforeUpdate = branchRepository.findAll().size();
        branch.setId(count.incrementAndGet());

        // Create the Branch
        BranchDTO branchDTO = branchMapper.toDto(branch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBranchMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(branchDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBranch() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        int databaseSizeBeforeDelete = branchRepository.findAll().size();

        // Delete the branch
        restBranchMockMvc
            .perform(delete(ENTITY_API_URL_ID, branch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
