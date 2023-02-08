package com.techvg.shrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.shrms.IntegrationTest;
import com.techvg.shrms.domain.BanksDetails;
import com.techvg.shrms.domain.Employee;
import com.techvg.shrms.repository.BanksDetailsRepository;
import com.techvg.shrms.service.criteria.BanksDetailsCriteria;
import com.techvg.shrms.service.dto.BanksDetailsDTO;
import com.techvg.shrms.service.mapper.BanksDetailsMapper;
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
 * Integration tests for the {@link BanksDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BanksDetailsResourceIT {

    private static final Long DEFAULT_ACCOUNT_NO = 1L;
    private static final Long UPDATED_ACCOUNT_NO = 2L;
    private static final Long SMALLER_ACCOUNT_NO = 1L - 1L;

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IFSC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IFSC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PAN = "AAAAAAAAAA";
    private static final String UPDATED_PAN = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH = "BBBBBBBBBB";

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

    private static final Long DEFAULT_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_EMPLOYEE_ID = 2L;
    private static final Long SMALLER_EMPLOYEE_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/banks-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BanksDetailsRepository banksDetailsRepository;

    @Autowired
    private BanksDetailsMapper banksDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBanksDetailsMockMvc;

    private BanksDetails banksDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BanksDetails createEntity(EntityManager em) {
        BanksDetails banksDetails = new BanksDetails()
            .accountNo(DEFAULT_ACCOUNT_NO)
            .bankName(DEFAULT_BANK_NAME)
            .ifscCode(DEFAULT_IFSC_CODE)
            .pan(DEFAULT_PAN)
            .branch(DEFAULT_BRANCH)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .deleted(DEFAULT_DELETED)
            .employeeId(DEFAULT_EMPLOYEE_ID);
        return banksDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BanksDetails createUpdatedEntity(EntityManager em) {
        BanksDetails banksDetails = new BanksDetails()
            .accountNo(UPDATED_ACCOUNT_NO)
            .bankName(UPDATED_BANK_NAME)
            .ifscCode(UPDATED_IFSC_CODE)
            .pan(UPDATED_PAN)
            .branch(UPDATED_BRANCH)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID);
        return banksDetails;
    }

    @BeforeEach
    public void initTest() {
        banksDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createBanksDetails() throws Exception {
        int databaseSizeBeforeCreate = banksDetailsRepository.findAll().size();
        // Create the BanksDetails
        BanksDetailsDTO banksDetailsDTO = banksDetailsMapper.toDto(banksDetails);
        restBanksDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banksDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        BanksDetails testBanksDetails = banksDetailsList.get(banksDetailsList.size() - 1);
        assertThat(testBanksDetails.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testBanksDetails.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testBanksDetails.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testBanksDetails.getPan()).isEqualTo(DEFAULT_PAN);
        assertThat(testBanksDetails.getBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testBanksDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testBanksDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testBanksDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBanksDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testBanksDetails.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testBanksDetails.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void createBanksDetailsWithExistingId() throws Exception {
        // Create the BanksDetails with an existing ID
        banksDetails.setId(1L);
        BanksDetailsDTO banksDetailsDTO = banksDetailsMapper.toDto(banksDetails);

        int databaseSizeBeforeCreate = banksDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBanksDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banksDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBanksDetails() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList
        restBanksDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banksDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO.intValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].pan").value(hasItem(DEFAULT_PAN)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())));
    }

    @Test
    @Transactional
    void getBanksDetails() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get the banksDetails
        restBanksDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, banksDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(banksDetails.getId().intValue()))
            .andExpect(jsonPath("$.accountNo").value(DEFAULT_ACCOUNT_NO.intValue()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.ifscCode").value(DEFAULT_IFSC_CODE))
            .andExpect(jsonPath("$.pan").value(DEFAULT_PAN))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()));
    }

    @Test
    @Transactional
    void getBanksDetailsByIdFiltering() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        Long id = banksDetails.getId();

        defaultBanksDetailsShouldBeFound("id.equals=" + id);
        defaultBanksDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultBanksDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBanksDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultBanksDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBanksDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByAccountNoIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where accountNo equals to DEFAULT_ACCOUNT_NO
        defaultBanksDetailsShouldBeFound("accountNo.equals=" + DEFAULT_ACCOUNT_NO);

        // Get all the banksDetailsList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultBanksDetailsShouldNotBeFound("accountNo.equals=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByAccountNoIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where accountNo in DEFAULT_ACCOUNT_NO or UPDATED_ACCOUNT_NO
        defaultBanksDetailsShouldBeFound("accountNo.in=" + DEFAULT_ACCOUNT_NO + "," + UPDATED_ACCOUNT_NO);

        // Get all the banksDetailsList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultBanksDetailsShouldNotBeFound("accountNo.in=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByAccountNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where accountNo is not null
        defaultBanksDetailsShouldBeFound("accountNo.specified=true");

        // Get all the banksDetailsList where accountNo is null
        defaultBanksDetailsShouldNotBeFound("accountNo.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByAccountNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where accountNo is greater than or equal to DEFAULT_ACCOUNT_NO
        defaultBanksDetailsShouldBeFound("accountNo.greaterThanOrEqual=" + DEFAULT_ACCOUNT_NO);

        // Get all the banksDetailsList where accountNo is greater than or equal to UPDATED_ACCOUNT_NO
        defaultBanksDetailsShouldNotBeFound("accountNo.greaterThanOrEqual=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByAccountNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where accountNo is less than or equal to DEFAULT_ACCOUNT_NO
        defaultBanksDetailsShouldBeFound("accountNo.lessThanOrEqual=" + DEFAULT_ACCOUNT_NO);

        // Get all the banksDetailsList where accountNo is less than or equal to SMALLER_ACCOUNT_NO
        defaultBanksDetailsShouldNotBeFound("accountNo.lessThanOrEqual=" + SMALLER_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByAccountNoIsLessThanSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where accountNo is less than DEFAULT_ACCOUNT_NO
        defaultBanksDetailsShouldNotBeFound("accountNo.lessThan=" + DEFAULT_ACCOUNT_NO);

        // Get all the banksDetailsList where accountNo is less than UPDATED_ACCOUNT_NO
        defaultBanksDetailsShouldBeFound("accountNo.lessThan=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByAccountNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where accountNo is greater than DEFAULT_ACCOUNT_NO
        defaultBanksDetailsShouldNotBeFound("accountNo.greaterThan=" + DEFAULT_ACCOUNT_NO);

        // Get all the banksDetailsList where accountNo is greater than SMALLER_ACCOUNT_NO
        defaultBanksDetailsShouldBeFound("accountNo.greaterThan=" + SMALLER_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where bankName equals to DEFAULT_BANK_NAME
        defaultBanksDetailsShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the banksDetailsList where bankName equals to UPDATED_BANK_NAME
        defaultBanksDetailsShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultBanksDetailsShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the banksDetailsList where bankName equals to UPDATED_BANK_NAME
        defaultBanksDetailsShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where bankName is not null
        defaultBanksDetailsShouldBeFound("bankName.specified=true");

        // Get all the banksDetailsList where bankName is null
        defaultBanksDetailsShouldNotBeFound("bankName.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBankNameContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where bankName contains DEFAULT_BANK_NAME
        defaultBanksDetailsShouldBeFound("bankName.contains=" + DEFAULT_BANK_NAME);

        // Get all the banksDetailsList where bankName contains UPDATED_BANK_NAME
        defaultBanksDetailsShouldNotBeFound("bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where bankName does not contain DEFAULT_BANK_NAME
        defaultBanksDetailsShouldNotBeFound("bankName.doesNotContain=" + DEFAULT_BANK_NAME);

        // Get all the banksDetailsList where bankName does not contain UPDATED_BANK_NAME
        defaultBanksDetailsShouldBeFound("bankName.doesNotContain=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByIfscCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where ifscCode equals to DEFAULT_IFSC_CODE
        defaultBanksDetailsShouldBeFound("ifscCode.equals=" + DEFAULT_IFSC_CODE);

        // Get all the banksDetailsList where ifscCode equals to UPDATED_IFSC_CODE
        defaultBanksDetailsShouldNotBeFound("ifscCode.equals=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByIfscCodeIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where ifscCode in DEFAULT_IFSC_CODE or UPDATED_IFSC_CODE
        defaultBanksDetailsShouldBeFound("ifscCode.in=" + DEFAULT_IFSC_CODE + "," + UPDATED_IFSC_CODE);

        // Get all the banksDetailsList where ifscCode equals to UPDATED_IFSC_CODE
        defaultBanksDetailsShouldNotBeFound("ifscCode.in=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByIfscCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where ifscCode is not null
        defaultBanksDetailsShouldBeFound("ifscCode.specified=true");

        // Get all the banksDetailsList where ifscCode is null
        defaultBanksDetailsShouldNotBeFound("ifscCode.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByIfscCodeContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where ifscCode contains DEFAULT_IFSC_CODE
        defaultBanksDetailsShouldBeFound("ifscCode.contains=" + DEFAULT_IFSC_CODE);

        // Get all the banksDetailsList where ifscCode contains UPDATED_IFSC_CODE
        defaultBanksDetailsShouldNotBeFound("ifscCode.contains=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByIfscCodeNotContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where ifscCode does not contain DEFAULT_IFSC_CODE
        defaultBanksDetailsShouldNotBeFound("ifscCode.doesNotContain=" + DEFAULT_IFSC_CODE);

        // Get all the banksDetailsList where ifscCode does not contain UPDATED_IFSC_CODE
        defaultBanksDetailsShouldBeFound("ifscCode.doesNotContain=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByPanIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where pan equals to DEFAULT_PAN
        defaultBanksDetailsShouldBeFound("pan.equals=" + DEFAULT_PAN);

        // Get all the banksDetailsList where pan equals to UPDATED_PAN
        defaultBanksDetailsShouldNotBeFound("pan.equals=" + UPDATED_PAN);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByPanIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where pan in DEFAULT_PAN or UPDATED_PAN
        defaultBanksDetailsShouldBeFound("pan.in=" + DEFAULT_PAN + "," + UPDATED_PAN);

        // Get all the banksDetailsList where pan equals to UPDATED_PAN
        defaultBanksDetailsShouldNotBeFound("pan.in=" + UPDATED_PAN);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByPanIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where pan is not null
        defaultBanksDetailsShouldBeFound("pan.specified=true");

        // Get all the banksDetailsList where pan is null
        defaultBanksDetailsShouldNotBeFound("pan.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByPanContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where pan contains DEFAULT_PAN
        defaultBanksDetailsShouldBeFound("pan.contains=" + DEFAULT_PAN);

        // Get all the banksDetailsList where pan contains UPDATED_PAN
        defaultBanksDetailsShouldNotBeFound("pan.contains=" + UPDATED_PAN);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByPanNotContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where pan does not contain DEFAULT_PAN
        defaultBanksDetailsShouldNotBeFound("pan.doesNotContain=" + DEFAULT_PAN);

        // Get all the banksDetailsList where pan does not contain UPDATED_PAN
        defaultBanksDetailsShouldBeFound("pan.doesNotContain=" + UPDATED_PAN);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where branch equals to DEFAULT_BRANCH
        defaultBanksDetailsShouldBeFound("branch.equals=" + DEFAULT_BRANCH);

        // Get all the banksDetailsList where branch equals to UPDATED_BRANCH
        defaultBanksDetailsShouldNotBeFound("branch.equals=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBranchIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where branch in DEFAULT_BRANCH or UPDATED_BRANCH
        defaultBanksDetailsShouldBeFound("branch.in=" + DEFAULT_BRANCH + "," + UPDATED_BRANCH);

        // Get all the banksDetailsList where branch equals to UPDATED_BRANCH
        defaultBanksDetailsShouldNotBeFound("branch.in=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where branch is not null
        defaultBanksDetailsShouldBeFound("branch.specified=true");

        // Get all the banksDetailsList where branch is null
        defaultBanksDetailsShouldNotBeFound("branch.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBranchContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where branch contains DEFAULT_BRANCH
        defaultBanksDetailsShouldBeFound("branch.contains=" + DEFAULT_BRANCH);

        // Get all the banksDetailsList where branch contains UPDATED_BRANCH
        defaultBanksDetailsShouldNotBeFound("branch.contains=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBranchNotContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where branch does not contain DEFAULT_BRANCH
        defaultBanksDetailsShouldNotBeFound("branch.doesNotContain=" + DEFAULT_BRANCH);

        // Get all the banksDetailsList where branch does not contain UPDATED_BRANCH
        defaultBanksDetailsShouldBeFound("branch.doesNotContain=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultBanksDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the banksDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBanksDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultBanksDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the banksDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBanksDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where lastModified is not null
        defaultBanksDetailsShouldBeFound("lastModified.specified=true");

        // Get all the banksDetailsList where lastModified is null
        defaultBanksDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultBanksDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the banksDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBanksDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultBanksDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the banksDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBanksDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where lastModifiedBy is not null
        defaultBanksDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the banksDetailsList where lastModifiedBy is null
        defaultBanksDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultBanksDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the banksDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultBanksDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultBanksDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the banksDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultBanksDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where createdBy equals to DEFAULT_CREATED_BY
        defaultBanksDetailsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the banksDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultBanksDetailsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultBanksDetailsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the banksDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultBanksDetailsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where createdBy is not null
        defaultBanksDetailsShouldBeFound("createdBy.specified=true");

        // Get all the banksDetailsList where createdBy is null
        defaultBanksDetailsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where createdBy contains DEFAULT_CREATED_BY
        defaultBanksDetailsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the banksDetailsList where createdBy contains UPDATED_CREATED_BY
        defaultBanksDetailsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultBanksDetailsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the banksDetailsList where createdBy does not contain UPDATED_CREATED_BY
        defaultBanksDetailsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where createdOn equals to DEFAULT_CREATED_ON
        defaultBanksDetailsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the banksDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultBanksDetailsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultBanksDetailsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the banksDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultBanksDetailsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where createdOn is not null
        defaultBanksDetailsShouldBeFound("createdOn.specified=true");

        // Get all the banksDetailsList where createdOn is null
        defaultBanksDetailsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where deleted equals to DEFAULT_DELETED
        defaultBanksDetailsShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the banksDetailsList where deleted equals to UPDATED_DELETED
        defaultBanksDetailsShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultBanksDetailsShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the banksDetailsList where deleted equals to UPDATED_DELETED
        defaultBanksDetailsShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where deleted is not null
        defaultBanksDetailsShouldBeFound("deleted.specified=true");

        // Get all the banksDetailsList where deleted is null
        defaultBanksDetailsShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultBanksDetailsShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the banksDetailsList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultBanksDetailsShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultBanksDetailsShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the banksDetailsList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultBanksDetailsShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where employeeId is not null
        defaultBanksDetailsShouldBeFound("employeeId.specified=true");

        // Get all the banksDetailsList where employeeId is null
        defaultBanksDetailsShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultBanksDetailsShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the banksDetailsList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultBanksDetailsShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultBanksDetailsShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the banksDetailsList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultBanksDetailsShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultBanksDetailsShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the banksDetailsList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultBanksDetailsShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultBanksDetailsShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the banksDetailsList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultBanksDetailsShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            banksDetailsRepository.saveAndFlush(banksDetails);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        banksDetails.setEmployee(employee);
        banksDetailsRepository.saveAndFlush(banksDetails);
        Long employeeId = employee.getId();

        // Get all the banksDetailsList where employee equals to employeeId
        defaultBanksDetailsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the banksDetailsList where employee equals to (employeeId + 1)
        defaultBanksDetailsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBanksDetailsShouldBeFound(String filter) throws Exception {
        restBanksDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banksDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO.intValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].pan").value(hasItem(DEFAULT_PAN)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())));

        // Check, that the count call also returns 1
        restBanksDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBanksDetailsShouldNotBeFound(String filter) throws Exception {
        restBanksDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBanksDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBanksDetails() throws Exception {
        // Get the banksDetails
        restBanksDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBanksDetails() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        int databaseSizeBeforeUpdate = banksDetailsRepository.findAll().size();

        // Update the banksDetails
        BanksDetails updatedBanksDetails = banksDetailsRepository.findById(banksDetails.getId()).get();
        // Disconnect from session so that the updates on updatedBanksDetails are not directly saved in db
        em.detach(updatedBanksDetails);
        updatedBanksDetails
            .accountNo(UPDATED_ACCOUNT_NO)
            .bankName(UPDATED_BANK_NAME)
            .ifscCode(UPDATED_IFSC_CODE)
            .pan(UPDATED_PAN)
            .branch(UPDATED_BRANCH)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID);
        BanksDetailsDTO banksDetailsDTO = banksDetailsMapper.toDto(updatedBanksDetails);

        restBanksDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, banksDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banksDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeUpdate);
        BanksDetails testBanksDetails = banksDetailsList.get(banksDetailsList.size() - 1);
        assertThat(testBanksDetails.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testBanksDetails.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testBanksDetails.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testBanksDetails.getPan()).isEqualTo(UPDATED_PAN);
        assertThat(testBanksDetails.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testBanksDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBanksDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testBanksDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBanksDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testBanksDetails.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testBanksDetails.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void putNonExistingBanksDetails() throws Exception {
        int databaseSizeBeforeUpdate = banksDetailsRepository.findAll().size();
        banksDetails.setId(count.incrementAndGet());

        // Create the BanksDetails
        BanksDetailsDTO banksDetailsDTO = banksDetailsMapper.toDto(banksDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanksDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, banksDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banksDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBanksDetails() throws Exception {
        int databaseSizeBeforeUpdate = banksDetailsRepository.findAll().size();
        banksDetails.setId(count.incrementAndGet());

        // Create the BanksDetails
        BanksDetailsDTO banksDetailsDTO = banksDetailsMapper.toDto(banksDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banksDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBanksDetails() throws Exception {
        int databaseSizeBeforeUpdate = banksDetailsRepository.findAll().size();
        banksDetails.setId(count.incrementAndGet());

        // Create the BanksDetails
        BanksDetailsDTO banksDetailsDTO = banksDetailsMapper.toDto(banksDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banksDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBanksDetailsWithPatch() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        int databaseSizeBeforeUpdate = banksDetailsRepository.findAll().size();

        // Update the banksDetails using partial update
        BanksDetails partialUpdatedBanksDetails = new BanksDetails();
        partialUpdatedBanksDetails.setId(banksDetails.getId());

        partialUpdatedBanksDetails
            .accountNo(UPDATED_ACCOUNT_NO)
            .bankName(UPDATED_BANK_NAME)
            .branch(UPDATED_BRANCH)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .employeeId(UPDATED_EMPLOYEE_ID);

        restBanksDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanksDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBanksDetails))
            )
            .andExpect(status().isOk());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeUpdate);
        BanksDetails testBanksDetails = banksDetailsList.get(banksDetailsList.size() - 1);
        assertThat(testBanksDetails.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testBanksDetails.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testBanksDetails.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testBanksDetails.getPan()).isEqualTo(DEFAULT_PAN);
        assertThat(testBanksDetails.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testBanksDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testBanksDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testBanksDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBanksDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testBanksDetails.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testBanksDetails.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void fullUpdateBanksDetailsWithPatch() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        int databaseSizeBeforeUpdate = banksDetailsRepository.findAll().size();

        // Update the banksDetails using partial update
        BanksDetails partialUpdatedBanksDetails = new BanksDetails();
        partialUpdatedBanksDetails.setId(banksDetails.getId());

        partialUpdatedBanksDetails
            .accountNo(UPDATED_ACCOUNT_NO)
            .bankName(UPDATED_BANK_NAME)
            .ifscCode(UPDATED_IFSC_CODE)
            .pan(UPDATED_PAN)
            .branch(UPDATED_BRANCH)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID);

        restBanksDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanksDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBanksDetails))
            )
            .andExpect(status().isOk());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeUpdate);
        BanksDetails testBanksDetails = banksDetailsList.get(banksDetailsList.size() - 1);
        assertThat(testBanksDetails.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testBanksDetails.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testBanksDetails.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testBanksDetails.getPan()).isEqualTo(UPDATED_PAN);
        assertThat(testBanksDetails.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testBanksDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBanksDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testBanksDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBanksDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testBanksDetails.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testBanksDetails.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingBanksDetails() throws Exception {
        int databaseSizeBeforeUpdate = banksDetailsRepository.findAll().size();
        banksDetails.setId(count.incrementAndGet());

        // Create the BanksDetails
        BanksDetailsDTO banksDetailsDTO = banksDetailsMapper.toDto(banksDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanksDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, banksDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(banksDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBanksDetails() throws Exception {
        int databaseSizeBeforeUpdate = banksDetailsRepository.findAll().size();
        banksDetails.setId(count.incrementAndGet());

        // Create the BanksDetails
        BanksDetailsDTO banksDetailsDTO = banksDetailsMapper.toDto(banksDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(banksDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBanksDetails() throws Exception {
        int databaseSizeBeforeUpdate = banksDetailsRepository.findAll().size();
        banksDetails.setId(count.incrementAndGet());

        // Create the BanksDetails
        BanksDetailsDTO banksDetailsDTO = banksDetailsMapper.toDto(banksDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(banksDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBanksDetails() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        int databaseSizeBeforeDelete = banksDetailsRepository.findAll().size();

        // Delete the banksDetails
        restBanksDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, banksDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
