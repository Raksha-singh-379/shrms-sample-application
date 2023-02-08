package com.techvg.shrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.shrms.IntegrationTest;
import com.techvg.shrms.domain.EmpSalaryInfo;
import com.techvg.shrms.domain.Employee;
import com.techvg.shrms.domain.enumeration.SalaryBasis;
import com.techvg.shrms.repository.EmpSalaryInfoRepository;
import com.techvg.shrms.service.criteria.EmpSalaryInfoCriteria;
import com.techvg.shrms.service.dto.EmpSalaryInfoDTO;
import com.techvg.shrms.service.mapper.EmpSalaryInfoMapper;
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
 * Integration tests for the {@link EmpSalaryInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmpSalaryInfoResourceIT {

    private static final SalaryBasis DEFAULT_SALARY_BASIS = SalaryBasis.HOURLY;
    private static final SalaryBasis UPDATED_SALARY_BASIS = SalaryBasis.DAILY;

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;
    private static final Double SMALLER_AMOUNT = 1D - 1D;

    private static final String DEFAULT_PAYMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PF_CONTRIBUTION = false;
    private static final Boolean UPDATED_IS_PF_CONTRIBUTION = true;

    private static final String DEFAULT_PF_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PF_NUMBER = "BBBBBBBBBB";

    private static final Double DEFAULT_PF_RATE = 1D;
    private static final Double UPDATED_PF_RATE = 2D;
    private static final Double SMALLER_PF_RATE = 1D - 1D;

    private static final String DEFAULT_ADDITIONAL_PF_RATE = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_PF_RATE = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_PF_RATE = 1D;
    private static final Double UPDATED_TOTAL_PF_RATE = 2D;
    private static final Double SMALLER_TOTAL_PF_RATE = 1D - 1D;

    private static final Boolean DEFAULT_IS_ESI_CONTRIBUTION = false;
    private static final Boolean UPDATED_IS_ESI_CONTRIBUTION = true;

    private static final String DEFAULT_ESI_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ESI_NUMBER = "BBBBBBBBBB";

    private static final Double DEFAULT_ESI_RATE = 1D;
    private static final Double UPDATED_ESI_RATE = 2D;
    private static final Double SMALLER_ESI_RATE = 1D - 1D;

    private static final String DEFAULT_ADDITIONAL_ESI_RATE = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_ESI_RATE = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_ESI_RATE = 1D;
    private static final Double UPDATED_TOTAL_ESI_RATE = 2D;
    private static final Double SMALLER_TOTAL_ESI_RATE = 1D - 1D;

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

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final String ENTITY_API_URL = "/api/emp-salary-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmpSalaryInfoRepository empSalaryInfoRepository;

    @Autowired
    private EmpSalaryInfoMapper empSalaryInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpSalaryInfoMockMvc;

    private EmpSalaryInfo empSalaryInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpSalaryInfo createEntity(EntityManager em) {
        EmpSalaryInfo empSalaryInfo = new EmpSalaryInfo()
            .salaryBasis(DEFAULT_SALARY_BASIS)
            .amount(DEFAULT_AMOUNT)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .isPfContribution(DEFAULT_IS_PF_CONTRIBUTION)
            .pfNumber(DEFAULT_PF_NUMBER)
            .pfRate(DEFAULT_PF_RATE)
            .additionalPfRate(DEFAULT_ADDITIONAL_PF_RATE)
            .totalPfRate(DEFAULT_TOTAL_PF_RATE)
            .isEsiContribution(DEFAULT_IS_ESI_CONTRIBUTION)
            .esiNumber(DEFAULT_ESI_NUMBER)
            .esiRate(DEFAULT_ESI_RATE)
            .additionalEsiRate(DEFAULT_ADDITIONAL_ESI_RATE)
            .totalEsiRate(DEFAULT_TOTAL_ESI_RATE)
            .employeId(DEFAULT_EMPLOYE_ID)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .deleted(DEFAULT_DELETED);
        return empSalaryInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpSalaryInfo createUpdatedEntity(EntityManager em) {
        EmpSalaryInfo empSalaryInfo = new EmpSalaryInfo()
            .salaryBasis(UPDATED_SALARY_BASIS)
            .amount(UPDATED_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .isPfContribution(UPDATED_IS_PF_CONTRIBUTION)
            .pfNumber(UPDATED_PF_NUMBER)
            .pfRate(UPDATED_PF_RATE)
            .additionalPfRate(UPDATED_ADDITIONAL_PF_RATE)
            .totalPfRate(UPDATED_TOTAL_PF_RATE)
            .isEsiContribution(UPDATED_IS_ESI_CONTRIBUTION)
            .esiNumber(UPDATED_ESI_NUMBER)
            .esiRate(UPDATED_ESI_RATE)
            .additionalEsiRate(UPDATED_ADDITIONAL_ESI_RATE)
            .totalEsiRate(UPDATED_TOTAL_ESI_RATE)
            .employeId(UPDATED_EMPLOYE_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);
        return empSalaryInfo;
    }

    @BeforeEach
    public void initTest() {
        empSalaryInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createEmpSalaryInfo() throws Exception {
        int databaseSizeBeforeCreate = empSalaryInfoRepository.findAll().size();
        // Create the EmpSalaryInfo
        EmpSalaryInfoDTO empSalaryInfoDTO = empSalaryInfoMapper.toDto(empSalaryInfo);
        restEmpSalaryInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empSalaryInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmpSalaryInfo in the database
        List<EmpSalaryInfo> empSalaryInfoList = empSalaryInfoRepository.findAll();
        assertThat(empSalaryInfoList).hasSize(databaseSizeBeforeCreate + 1);
        EmpSalaryInfo testEmpSalaryInfo = empSalaryInfoList.get(empSalaryInfoList.size() - 1);
        assertThat(testEmpSalaryInfo.getSalaryBasis()).isEqualTo(DEFAULT_SALARY_BASIS);
        assertThat(testEmpSalaryInfo.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testEmpSalaryInfo.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testEmpSalaryInfo.getIsPfContribution()).isEqualTo(DEFAULT_IS_PF_CONTRIBUTION);
        assertThat(testEmpSalaryInfo.getPfNumber()).isEqualTo(DEFAULT_PF_NUMBER);
        assertThat(testEmpSalaryInfo.getPfRate()).isEqualTo(DEFAULT_PF_RATE);
        assertThat(testEmpSalaryInfo.getAdditionalPfRate()).isEqualTo(DEFAULT_ADDITIONAL_PF_RATE);
        assertThat(testEmpSalaryInfo.getTotalPfRate()).isEqualTo(DEFAULT_TOTAL_PF_RATE);
        assertThat(testEmpSalaryInfo.getIsEsiContribution()).isEqualTo(DEFAULT_IS_ESI_CONTRIBUTION);
        assertThat(testEmpSalaryInfo.getEsiNumber()).isEqualTo(DEFAULT_ESI_NUMBER);
        assertThat(testEmpSalaryInfo.getEsiRate()).isEqualTo(DEFAULT_ESI_RATE);
        assertThat(testEmpSalaryInfo.getAdditionalEsiRate()).isEqualTo(DEFAULT_ADDITIONAL_ESI_RATE);
        assertThat(testEmpSalaryInfo.getTotalEsiRate()).isEqualTo(DEFAULT_TOTAL_ESI_RATE);
        assertThat(testEmpSalaryInfo.getEmployeId()).isEqualTo(DEFAULT_EMPLOYE_ID);
        assertThat(testEmpSalaryInfo.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testEmpSalaryInfo.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testEmpSalaryInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEmpSalaryInfo.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEmpSalaryInfo.getDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    void createEmpSalaryInfoWithExistingId() throws Exception {
        // Create the EmpSalaryInfo with an existing ID
        empSalaryInfo.setId(1L);
        EmpSalaryInfoDTO empSalaryInfoDTO = empSalaryInfoMapper.toDto(empSalaryInfo);

        int databaseSizeBeforeCreate = empSalaryInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpSalaryInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empSalaryInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpSalaryInfo in the database
        List<EmpSalaryInfo> empSalaryInfoList = empSalaryInfoRepository.findAll();
        assertThat(empSalaryInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfos() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList
        restEmpSalaryInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empSalaryInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].salaryBasis").value(hasItem(DEFAULT_SALARY_BASIS.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE)))
            .andExpect(jsonPath("$.[*].isPfContribution").value(hasItem(DEFAULT_IS_PF_CONTRIBUTION.booleanValue())))
            .andExpect(jsonPath("$.[*].pfNumber").value(hasItem(DEFAULT_PF_NUMBER)))
            .andExpect(jsonPath("$.[*].pfRate").value(hasItem(DEFAULT_PF_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalPfRate").value(hasItem(DEFAULT_ADDITIONAL_PF_RATE)))
            .andExpect(jsonPath("$.[*].totalPfRate").value(hasItem(DEFAULT_TOTAL_PF_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].isEsiContribution").value(hasItem(DEFAULT_IS_ESI_CONTRIBUTION.booleanValue())))
            .andExpect(jsonPath("$.[*].esiNumber").value(hasItem(DEFAULT_ESI_NUMBER)))
            .andExpect(jsonPath("$.[*].esiRate").value(hasItem(DEFAULT_ESI_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalEsiRate").value(hasItem(DEFAULT_ADDITIONAL_ESI_RATE)))
            .andExpect(jsonPath("$.[*].totalEsiRate").value(hasItem(DEFAULT_TOTAL_ESI_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].employeId").value(hasItem(DEFAULT_EMPLOYE_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getEmpSalaryInfo() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get the empSalaryInfo
        restEmpSalaryInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, empSalaryInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(empSalaryInfo.getId().intValue()))
            .andExpect(jsonPath("$.salaryBasis").value(DEFAULT_SALARY_BASIS.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE))
            .andExpect(jsonPath("$.isPfContribution").value(DEFAULT_IS_PF_CONTRIBUTION.booleanValue()))
            .andExpect(jsonPath("$.pfNumber").value(DEFAULT_PF_NUMBER))
            .andExpect(jsonPath("$.pfRate").value(DEFAULT_PF_RATE.doubleValue()))
            .andExpect(jsonPath("$.additionalPfRate").value(DEFAULT_ADDITIONAL_PF_RATE))
            .andExpect(jsonPath("$.totalPfRate").value(DEFAULT_TOTAL_PF_RATE.doubleValue()))
            .andExpect(jsonPath("$.isEsiContribution").value(DEFAULT_IS_ESI_CONTRIBUTION.booleanValue()))
            .andExpect(jsonPath("$.esiNumber").value(DEFAULT_ESI_NUMBER))
            .andExpect(jsonPath("$.esiRate").value(DEFAULT_ESI_RATE.doubleValue()))
            .andExpect(jsonPath("$.additionalEsiRate").value(DEFAULT_ADDITIONAL_ESI_RATE))
            .andExpect(jsonPath("$.totalEsiRate").value(DEFAULT_TOTAL_ESI_RATE.doubleValue()))
            .andExpect(jsonPath("$.employeId").value(DEFAULT_EMPLOYE_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getEmpSalaryInfosByIdFiltering() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        Long id = empSalaryInfo.getId();

        defaultEmpSalaryInfoShouldBeFound("id.equals=" + id);
        defaultEmpSalaryInfoShouldNotBeFound("id.notEquals=" + id);

        defaultEmpSalaryInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmpSalaryInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultEmpSalaryInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmpSalaryInfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosBySalaryBasisIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where salaryBasis equals to DEFAULT_SALARY_BASIS
        defaultEmpSalaryInfoShouldBeFound("salaryBasis.equals=" + DEFAULT_SALARY_BASIS);

        // Get all the empSalaryInfoList where salaryBasis equals to UPDATED_SALARY_BASIS
        defaultEmpSalaryInfoShouldNotBeFound("salaryBasis.equals=" + UPDATED_SALARY_BASIS);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosBySalaryBasisIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where salaryBasis in DEFAULT_SALARY_BASIS or UPDATED_SALARY_BASIS
        defaultEmpSalaryInfoShouldBeFound("salaryBasis.in=" + DEFAULT_SALARY_BASIS + "," + UPDATED_SALARY_BASIS);

        // Get all the empSalaryInfoList where salaryBasis equals to UPDATED_SALARY_BASIS
        defaultEmpSalaryInfoShouldNotBeFound("salaryBasis.in=" + UPDATED_SALARY_BASIS);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosBySalaryBasisIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where salaryBasis is not null
        defaultEmpSalaryInfoShouldBeFound("salaryBasis.specified=true");

        // Get all the empSalaryInfoList where salaryBasis is null
        defaultEmpSalaryInfoShouldNotBeFound("salaryBasis.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where amount equals to DEFAULT_AMOUNT
        defaultEmpSalaryInfoShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the empSalaryInfoList where amount equals to UPDATED_AMOUNT
        defaultEmpSalaryInfoShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultEmpSalaryInfoShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the empSalaryInfoList where amount equals to UPDATED_AMOUNT
        defaultEmpSalaryInfoShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where amount is not null
        defaultEmpSalaryInfoShouldBeFound("amount.specified=true");

        // Get all the empSalaryInfoList where amount is null
        defaultEmpSalaryInfoShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultEmpSalaryInfoShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the empSalaryInfoList where amount is greater than or equal to UPDATED_AMOUNT
        defaultEmpSalaryInfoShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where amount is less than or equal to DEFAULT_AMOUNT
        defaultEmpSalaryInfoShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the empSalaryInfoList where amount is less than or equal to SMALLER_AMOUNT
        defaultEmpSalaryInfoShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where amount is less than DEFAULT_AMOUNT
        defaultEmpSalaryInfoShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the empSalaryInfoList where amount is less than UPDATED_AMOUNT
        defaultEmpSalaryInfoShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where amount is greater than DEFAULT_AMOUNT
        defaultEmpSalaryInfoShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the empSalaryInfoList where amount is greater than SMALLER_AMOUNT
        defaultEmpSalaryInfoShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByPaymentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where paymentType equals to DEFAULT_PAYMENT_TYPE
        defaultEmpSalaryInfoShouldBeFound("paymentType.equals=" + DEFAULT_PAYMENT_TYPE);

        // Get all the empSalaryInfoList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultEmpSalaryInfoShouldNotBeFound("paymentType.equals=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByPaymentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where paymentType in DEFAULT_PAYMENT_TYPE or UPDATED_PAYMENT_TYPE
        defaultEmpSalaryInfoShouldBeFound("paymentType.in=" + DEFAULT_PAYMENT_TYPE + "," + UPDATED_PAYMENT_TYPE);

        // Get all the empSalaryInfoList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultEmpSalaryInfoShouldNotBeFound("paymentType.in=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByPaymentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where paymentType is not null
        defaultEmpSalaryInfoShouldBeFound("paymentType.specified=true");

        // Get all the empSalaryInfoList where paymentType is null
        defaultEmpSalaryInfoShouldNotBeFound("paymentType.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByPaymentTypeContainsSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where paymentType contains DEFAULT_PAYMENT_TYPE
        defaultEmpSalaryInfoShouldBeFound("paymentType.contains=" + DEFAULT_PAYMENT_TYPE);

        // Get all the empSalaryInfoList where paymentType contains UPDATED_PAYMENT_TYPE
        defaultEmpSalaryInfoShouldNotBeFound("paymentType.contains=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByPaymentTypeNotContainsSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where paymentType does not contain DEFAULT_PAYMENT_TYPE
        defaultEmpSalaryInfoShouldNotBeFound("paymentType.doesNotContain=" + DEFAULT_PAYMENT_TYPE);

        // Get all the empSalaryInfoList where paymentType does not contain UPDATED_PAYMENT_TYPE
        defaultEmpSalaryInfoShouldBeFound("paymentType.doesNotContain=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByIsPfContributionIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where isPfContribution equals to DEFAULT_IS_PF_CONTRIBUTION
        defaultEmpSalaryInfoShouldBeFound("isPfContribution.equals=" + DEFAULT_IS_PF_CONTRIBUTION);

        // Get all the empSalaryInfoList where isPfContribution equals to UPDATED_IS_PF_CONTRIBUTION
        defaultEmpSalaryInfoShouldNotBeFound("isPfContribution.equals=" + UPDATED_IS_PF_CONTRIBUTION);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByIsPfContributionIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where isPfContribution in DEFAULT_IS_PF_CONTRIBUTION or UPDATED_IS_PF_CONTRIBUTION
        defaultEmpSalaryInfoShouldBeFound("isPfContribution.in=" + DEFAULT_IS_PF_CONTRIBUTION + "," + UPDATED_IS_PF_CONTRIBUTION);

        // Get all the empSalaryInfoList where isPfContribution equals to UPDATED_IS_PF_CONTRIBUTION
        defaultEmpSalaryInfoShouldNotBeFound("isPfContribution.in=" + UPDATED_IS_PF_CONTRIBUTION);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByIsPfContributionIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where isPfContribution is not null
        defaultEmpSalaryInfoShouldBeFound("isPfContribution.specified=true");

        // Get all the empSalaryInfoList where isPfContribution is null
        defaultEmpSalaryInfoShouldNotBeFound("isPfContribution.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByPfNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where pfNumber equals to DEFAULT_PF_NUMBER
        defaultEmpSalaryInfoShouldBeFound("pfNumber.equals=" + DEFAULT_PF_NUMBER);

        // Get all the empSalaryInfoList where pfNumber equals to UPDATED_PF_NUMBER
        defaultEmpSalaryInfoShouldNotBeFound("pfNumber.equals=" + UPDATED_PF_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByPfNumberIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where pfNumber in DEFAULT_PF_NUMBER or UPDATED_PF_NUMBER
        defaultEmpSalaryInfoShouldBeFound("pfNumber.in=" + DEFAULT_PF_NUMBER + "," + UPDATED_PF_NUMBER);

        // Get all the empSalaryInfoList where pfNumber equals to UPDATED_PF_NUMBER
        defaultEmpSalaryInfoShouldNotBeFound("pfNumber.in=" + UPDATED_PF_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByPfNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where pfNumber is not null
        defaultEmpSalaryInfoShouldBeFound("pfNumber.specified=true");

        // Get all the empSalaryInfoList where pfNumber is null
        defaultEmpSalaryInfoShouldNotBeFound("pfNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByPfNumberContainsSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where pfNumber contains DEFAULT_PF_NUMBER
        defaultEmpSalaryInfoShouldBeFound("pfNumber.contains=" + DEFAULT_PF_NUMBER);

        // Get all the empSalaryInfoList where pfNumber contains UPDATED_PF_NUMBER
        defaultEmpSalaryInfoShouldNotBeFound("pfNumber.contains=" + UPDATED_PF_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByPfNumberNotContainsSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where pfNumber does not contain DEFAULT_PF_NUMBER
        defaultEmpSalaryInfoShouldNotBeFound("pfNumber.doesNotContain=" + DEFAULT_PF_NUMBER);

        // Get all the empSalaryInfoList where pfNumber does not contain UPDATED_PF_NUMBER
        defaultEmpSalaryInfoShouldBeFound("pfNumber.doesNotContain=" + UPDATED_PF_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByPfRateIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where pfRate equals to DEFAULT_PF_RATE
        defaultEmpSalaryInfoShouldBeFound("pfRate.equals=" + DEFAULT_PF_RATE);

        // Get all the empSalaryInfoList where pfRate equals to UPDATED_PF_RATE
        defaultEmpSalaryInfoShouldNotBeFound("pfRate.equals=" + UPDATED_PF_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByPfRateIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where pfRate in DEFAULT_PF_RATE or UPDATED_PF_RATE
        defaultEmpSalaryInfoShouldBeFound("pfRate.in=" + DEFAULT_PF_RATE + "," + UPDATED_PF_RATE);

        // Get all the empSalaryInfoList where pfRate equals to UPDATED_PF_RATE
        defaultEmpSalaryInfoShouldNotBeFound("pfRate.in=" + UPDATED_PF_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByPfRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where pfRate is not null
        defaultEmpSalaryInfoShouldBeFound("pfRate.specified=true");

        // Get all the empSalaryInfoList where pfRate is null
        defaultEmpSalaryInfoShouldNotBeFound("pfRate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByPfRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where pfRate is greater than or equal to DEFAULT_PF_RATE
        defaultEmpSalaryInfoShouldBeFound("pfRate.greaterThanOrEqual=" + DEFAULT_PF_RATE);

        // Get all the empSalaryInfoList where pfRate is greater than or equal to UPDATED_PF_RATE
        defaultEmpSalaryInfoShouldNotBeFound("pfRate.greaterThanOrEqual=" + UPDATED_PF_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByPfRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where pfRate is less than or equal to DEFAULT_PF_RATE
        defaultEmpSalaryInfoShouldBeFound("pfRate.lessThanOrEqual=" + DEFAULT_PF_RATE);

        // Get all the empSalaryInfoList where pfRate is less than or equal to SMALLER_PF_RATE
        defaultEmpSalaryInfoShouldNotBeFound("pfRate.lessThanOrEqual=" + SMALLER_PF_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByPfRateIsLessThanSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where pfRate is less than DEFAULT_PF_RATE
        defaultEmpSalaryInfoShouldNotBeFound("pfRate.lessThan=" + DEFAULT_PF_RATE);

        // Get all the empSalaryInfoList where pfRate is less than UPDATED_PF_RATE
        defaultEmpSalaryInfoShouldBeFound("pfRate.lessThan=" + UPDATED_PF_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByPfRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where pfRate is greater than DEFAULT_PF_RATE
        defaultEmpSalaryInfoShouldNotBeFound("pfRate.greaterThan=" + DEFAULT_PF_RATE);

        // Get all the empSalaryInfoList where pfRate is greater than SMALLER_PF_RATE
        defaultEmpSalaryInfoShouldBeFound("pfRate.greaterThan=" + SMALLER_PF_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByAdditionalPfRateIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where additionalPfRate equals to DEFAULT_ADDITIONAL_PF_RATE
        defaultEmpSalaryInfoShouldBeFound("additionalPfRate.equals=" + DEFAULT_ADDITIONAL_PF_RATE);

        // Get all the empSalaryInfoList where additionalPfRate equals to UPDATED_ADDITIONAL_PF_RATE
        defaultEmpSalaryInfoShouldNotBeFound("additionalPfRate.equals=" + UPDATED_ADDITIONAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByAdditionalPfRateIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where additionalPfRate in DEFAULT_ADDITIONAL_PF_RATE or UPDATED_ADDITIONAL_PF_RATE
        defaultEmpSalaryInfoShouldBeFound("additionalPfRate.in=" + DEFAULT_ADDITIONAL_PF_RATE + "," + UPDATED_ADDITIONAL_PF_RATE);

        // Get all the empSalaryInfoList where additionalPfRate equals to UPDATED_ADDITIONAL_PF_RATE
        defaultEmpSalaryInfoShouldNotBeFound("additionalPfRate.in=" + UPDATED_ADDITIONAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByAdditionalPfRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where additionalPfRate is not null
        defaultEmpSalaryInfoShouldBeFound("additionalPfRate.specified=true");

        // Get all the empSalaryInfoList where additionalPfRate is null
        defaultEmpSalaryInfoShouldNotBeFound("additionalPfRate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByAdditionalPfRateContainsSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where additionalPfRate contains DEFAULT_ADDITIONAL_PF_RATE
        defaultEmpSalaryInfoShouldBeFound("additionalPfRate.contains=" + DEFAULT_ADDITIONAL_PF_RATE);

        // Get all the empSalaryInfoList where additionalPfRate contains UPDATED_ADDITIONAL_PF_RATE
        defaultEmpSalaryInfoShouldNotBeFound("additionalPfRate.contains=" + UPDATED_ADDITIONAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByAdditionalPfRateNotContainsSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where additionalPfRate does not contain DEFAULT_ADDITIONAL_PF_RATE
        defaultEmpSalaryInfoShouldNotBeFound("additionalPfRate.doesNotContain=" + DEFAULT_ADDITIONAL_PF_RATE);

        // Get all the empSalaryInfoList where additionalPfRate does not contain UPDATED_ADDITIONAL_PF_RATE
        defaultEmpSalaryInfoShouldBeFound("additionalPfRate.doesNotContain=" + UPDATED_ADDITIONAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByTotalPfRateIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where totalPfRate equals to DEFAULT_TOTAL_PF_RATE
        defaultEmpSalaryInfoShouldBeFound("totalPfRate.equals=" + DEFAULT_TOTAL_PF_RATE);

        // Get all the empSalaryInfoList where totalPfRate equals to UPDATED_TOTAL_PF_RATE
        defaultEmpSalaryInfoShouldNotBeFound("totalPfRate.equals=" + UPDATED_TOTAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByTotalPfRateIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where totalPfRate in DEFAULT_TOTAL_PF_RATE or UPDATED_TOTAL_PF_RATE
        defaultEmpSalaryInfoShouldBeFound("totalPfRate.in=" + DEFAULT_TOTAL_PF_RATE + "," + UPDATED_TOTAL_PF_RATE);

        // Get all the empSalaryInfoList where totalPfRate equals to UPDATED_TOTAL_PF_RATE
        defaultEmpSalaryInfoShouldNotBeFound("totalPfRate.in=" + UPDATED_TOTAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByTotalPfRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where totalPfRate is not null
        defaultEmpSalaryInfoShouldBeFound("totalPfRate.specified=true");

        // Get all the empSalaryInfoList where totalPfRate is null
        defaultEmpSalaryInfoShouldNotBeFound("totalPfRate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByTotalPfRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where totalPfRate is greater than or equal to DEFAULT_TOTAL_PF_RATE
        defaultEmpSalaryInfoShouldBeFound("totalPfRate.greaterThanOrEqual=" + DEFAULT_TOTAL_PF_RATE);

        // Get all the empSalaryInfoList where totalPfRate is greater than or equal to UPDATED_TOTAL_PF_RATE
        defaultEmpSalaryInfoShouldNotBeFound("totalPfRate.greaterThanOrEqual=" + UPDATED_TOTAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByTotalPfRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where totalPfRate is less than or equal to DEFAULT_TOTAL_PF_RATE
        defaultEmpSalaryInfoShouldBeFound("totalPfRate.lessThanOrEqual=" + DEFAULT_TOTAL_PF_RATE);

        // Get all the empSalaryInfoList where totalPfRate is less than or equal to SMALLER_TOTAL_PF_RATE
        defaultEmpSalaryInfoShouldNotBeFound("totalPfRate.lessThanOrEqual=" + SMALLER_TOTAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByTotalPfRateIsLessThanSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where totalPfRate is less than DEFAULT_TOTAL_PF_RATE
        defaultEmpSalaryInfoShouldNotBeFound("totalPfRate.lessThan=" + DEFAULT_TOTAL_PF_RATE);

        // Get all the empSalaryInfoList where totalPfRate is less than UPDATED_TOTAL_PF_RATE
        defaultEmpSalaryInfoShouldBeFound("totalPfRate.lessThan=" + UPDATED_TOTAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByTotalPfRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where totalPfRate is greater than DEFAULT_TOTAL_PF_RATE
        defaultEmpSalaryInfoShouldNotBeFound("totalPfRate.greaterThan=" + DEFAULT_TOTAL_PF_RATE);

        // Get all the empSalaryInfoList where totalPfRate is greater than SMALLER_TOTAL_PF_RATE
        defaultEmpSalaryInfoShouldBeFound("totalPfRate.greaterThan=" + SMALLER_TOTAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByIsEsiContributionIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where isEsiContribution equals to DEFAULT_IS_ESI_CONTRIBUTION
        defaultEmpSalaryInfoShouldBeFound("isEsiContribution.equals=" + DEFAULT_IS_ESI_CONTRIBUTION);

        // Get all the empSalaryInfoList where isEsiContribution equals to UPDATED_IS_ESI_CONTRIBUTION
        defaultEmpSalaryInfoShouldNotBeFound("isEsiContribution.equals=" + UPDATED_IS_ESI_CONTRIBUTION);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByIsEsiContributionIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where isEsiContribution in DEFAULT_IS_ESI_CONTRIBUTION or UPDATED_IS_ESI_CONTRIBUTION
        defaultEmpSalaryInfoShouldBeFound("isEsiContribution.in=" + DEFAULT_IS_ESI_CONTRIBUTION + "," + UPDATED_IS_ESI_CONTRIBUTION);

        // Get all the empSalaryInfoList where isEsiContribution equals to UPDATED_IS_ESI_CONTRIBUTION
        defaultEmpSalaryInfoShouldNotBeFound("isEsiContribution.in=" + UPDATED_IS_ESI_CONTRIBUTION);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByIsEsiContributionIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where isEsiContribution is not null
        defaultEmpSalaryInfoShouldBeFound("isEsiContribution.specified=true");

        // Get all the empSalaryInfoList where isEsiContribution is null
        defaultEmpSalaryInfoShouldNotBeFound("isEsiContribution.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEsiNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where esiNumber equals to DEFAULT_ESI_NUMBER
        defaultEmpSalaryInfoShouldBeFound("esiNumber.equals=" + DEFAULT_ESI_NUMBER);

        // Get all the empSalaryInfoList where esiNumber equals to UPDATED_ESI_NUMBER
        defaultEmpSalaryInfoShouldNotBeFound("esiNumber.equals=" + UPDATED_ESI_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEsiNumberIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where esiNumber in DEFAULT_ESI_NUMBER or UPDATED_ESI_NUMBER
        defaultEmpSalaryInfoShouldBeFound("esiNumber.in=" + DEFAULT_ESI_NUMBER + "," + UPDATED_ESI_NUMBER);

        // Get all the empSalaryInfoList where esiNumber equals to UPDATED_ESI_NUMBER
        defaultEmpSalaryInfoShouldNotBeFound("esiNumber.in=" + UPDATED_ESI_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEsiNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where esiNumber is not null
        defaultEmpSalaryInfoShouldBeFound("esiNumber.specified=true");

        // Get all the empSalaryInfoList where esiNumber is null
        defaultEmpSalaryInfoShouldNotBeFound("esiNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEsiNumberContainsSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where esiNumber contains DEFAULT_ESI_NUMBER
        defaultEmpSalaryInfoShouldBeFound("esiNumber.contains=" + DEFAULT_ESI_NUMBER);

        // Get all the empSalaryInfoList where esiNumber contains UPDATED_ESI_NUMBER
        defaultEmpSalaryInfoShouldNotBeFound("esiNumber.contains=" + UPDATED_ESI_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEsiNumberNotContainsSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where esiNumber does not contain DEFAULT_ESI_NUMBER
        defaultEmpSalaryInfoShouldNotBeFound("esiNumber.doesNotContain=" + DEFAULT_ESI_NUMBER);

        // Get all the empSalaryInfoList where esiNumber does not contain UPDATED_ESI_NUMBER
        defaultEmpSalaryInfoShouldBeFound("esiNumber.doesNotContain=" + UPDATED_ESI_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEsiRateIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where esiRate equals to DEFAULT_ESI_RATE
        defaultEmpSalaryInfoShouldBeFound("esiRate.equals=" + DEFAULT_ESI_RATE);

        // Get all the empSalaryInfoList where esiRate equals to UPDATED_ESI_RATE
        defaultEmpSalaryInfoShouldNotBeFound("esiRate.equals=" + UPDATED_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEsiRateIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where esiRate in DEFAULT_ESI_RATE or UPDATED_ESI_RATE
        defaultEmpSalaryInfoShouldBeFound("esiRate.in=" + DEFAULT_ESI_RATE + "," + UPDATED_ESI_RATE);

        // Get all the empSalaryInfoList where esiRate equals to UPDATED_ESI_RATE
        defaultEmpSalaryInfoShouldNotBeFound("esiRate.in=" + UPDATED_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEsiRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where esiRate is not null
        defaultEmpSalaryInfoShouldBeFound("esiRate.specified=true");

        // Get all the empSalaryInfoList where esiRate is null
        defaultEmpSalaryInfoShouldNotBeFound("esiRate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEsiRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where esiRate is greater than or equal to DEFAULT_ESI_RATE
        defaultEmpSalaryInfoShouldBeFound("esiRate.greaterThanOrEqual=" + DEFAULT_ESI_RATE);

        // Get all the empSalaryInfoList where esiRate is greater than or equal to UPDATED_ESI_RATE
        defaultEmpSalaryInfoShouldNotBeFound("esiRate.greaterThanOrEqual=" + UPDATED_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEsiRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where esiRate is less than or equal to DEFAULT_ESI_RATE
        defaultEmpSalaryInfoShouldBeFound("esiRate.lessThanOrEqual=" + DEFAULT_ESI_RATE);

        // Get all the empSalaryInfoList where esiRate is less than or equal to SMALLER_ESI_RATE
        defaultEmpSalaryInfoShouldNotBeFound("esiRate.lessThanOrEqual=" + SMALLER_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEsiRateIsLessThanSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where esiRate is less than DEFAULT_ESI_RATE
        defaultEmpSalaryInfoShouldNotBeFound("esiRate.lessThan=" + DEFAULT_ESI_RATE);

        // Get all the empSalaryInfoList where esiRate is less than UPDATED_ESI_RATE
        defaultEmpSalaryInfoShouldBeFound("esiRate.lessThan=" + UPDATED_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEsiRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where esiRate is greater than DEFAULT_ESI_RATE
        defaultEmpSalaryInfoShouldNotBeFound("esiRate.greaterThan=" + DEFAULT_ESI_RATE);

        // Get all the empSalaryInfoList where esiRate is greater than SMALLER_ESI_RATE
        defaultEmpSalaryInfoShouldBeFound("esiRate.greaterThan=" + SMALLER_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByAdditionalEsiRateIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where additionalEsiRate equals to DEFAULT_ADDITIONAL_ESI_RATE
        defaultEmpSalaryInfoShouldBeFound("additionalEsiRate.equals=" + DEFAULT_ADDITIONAL_ESI_RATE);

        // Get all the empSalaryInfoList where additionalEsiRate equals to UPDATED_ADDITIONAL_ESI_RATE
        defaultEmpSalaryInfoShouldNotBeFound("additionalEsiRate.equals=" + UPDATED_ADDITIONAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByAdditionalEsiRateIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where additionalEsiRate in DEFAULT_ADDITIONAL_ESI_RATE or UPDATED_ADDITIONAL_ESI_RATE
        defaultEmpSalaryInfoShouldBeFound("additionalEsiRate.in=" + DEFAULT_ADDITIONAL_ESI_RATE + "," + UPDATED_ADDITIONAL_ESI_RATE);

        // Get all the empSalaryInfoList where additionalEsiRate equals to UPDATED_ADDITIONAL_ESI_RATE
        defaultEmpSalaryInfoShouldNotBeFound("additionalEsiRate.in=" + UPDATED_ADDITIONAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByAdditionalEsiRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where additionalEsiRate is not null
        defaultEmpSalaryInfoShouldBeFound("additionalEsiRate.specified=true");

        // Get all the empSalaryInfoList where additionalEsiRate is null
        defaultEmpSalaryInfoShouldNotBeFound("additionalEsiRate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByAdditionalEsiRateContainsSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where additionalEsiRate contains DEFAULT_ADDITIONAL_ESI_RATE
        defaultEmpSalaryInfoShouldBeFound("additionalEsiRate.contains=" + DEFAULT_ADDITIONAL_ESI_RATE);

        // Get all the empSalaryInfoList where additionalEsiRate contains UPDATED_ADDITIONAL_ESI_RATE
        defaultEmpSalaryInfoShouldNotBeFound("additionalEsiRate.contains=" + UPDATED_ADDITIONAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByAdditionalEsiRateNotContainsSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where additionalEsiRate does not contain DEFAULT_ADDITIONAL_ESI_RATE
        defaultEmpSalaryInfoShouldNotBeFound("additionalEsiRate.doesNotContain=" + DEFAULT_ADDITIONAL_ESI_RATE);

        // Get all the empSalaryInfoList where additionalEsiRate does not contain UPDATED_ADDITIONAL_ESI_RATE
        defaultEmpSalaryInfoShouldBeFound("additionalEsiRate.doesNotContain=" + UPDATED_ADDITIONAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByTotalEsiRateIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where totalEsiRate equals to DEFAULT_TOTAL_ESI_RATE
        defaultEmpSalaryInfoShouldBeFound("totalEsiRate.equals=" + DEFAULT_TOTAL_ESI_RATE);

        // Get all the empSalaryInfoList where totalEsiRate equals to UPDATED_TOTAL_ESI_RATE
        defaultEmpSalaryInfoShouldNotBeFound("totalEsiRate.equals=" + UPDATED_TOTAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByTotalEsiRateIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where totalEsiRate in DEFAULT_TOTAL_ESI_RATE or UPDATED_TOTAL_ESI_RATE
        defaultEmpSalaryInfoShouldBeFound("totalEsiRate.in=" + DEFAULT_TOTAL_ESI_RATE + "," + UPDATED_TOTAL_ESI_RATE);

        // Get all the empSalaryInfoList where totalEsiRate equals to UPDATED_TOTAL_ESI_RATE
        defaultEmpSalaryInfoShouldNotBeFound("totalEsiRate.in=" + UPDATED_TOTAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByTotalEsiRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where totalEsiRate is not null
        defaultEmpSalaryInfoShouldBeFound("totalEsiRate.specified=true");

        // Get all the empSalaryInfoList where totalEsiRate is null
        defaultEmpSalaryInfoShouldNotBeFound("totalEsiRate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByTotalEsiRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where totalEsiRate is greater than or equal to DEFAULT_TOTAL_ESI_RATE
        defaultEmpSalaryInfoShouldBeFound("totalEsiRate.greaterThanOrEqual=" + DEFAULT_TOTAL_ESI_RATE);

        // Get all the empSalaryInfoList where totalEsiRate is greater than or equal to UPDATED_TOTAL_ESI_RATE
        defaultEmpSalaryInfoShouldNotBeFound("totalEsiRate.greaterThanOrEqual=" + UPDATED_TOTAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByTotalEsiRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where totalEsiRate is less than or equal to DEFAULT_TOTAL_ESI_RATE
        defaultEmpSalaryInfoShouldBeFound("totalEsiRate.lessThanOrEqual=" + DEFAULT_TOTAL_ESI_RATE);

        // Get all the empSalaryInfoList where totalEsiRate is less than or equal to SMALLER_TOTAL_ESI_RATE
        defaultEmpSalaryInfoShouldNotBeFound("totalEsiRate.lessThanOrEqual=" + SMALLER_TOTAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByTotalEsiRateIsLessThanSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where totalEsiRate is less than DEFAULT_TOTAL_ESI_RATE
        defaultEmpSalaryInfoShouldNotBeFound("totalEsiRate.lessThan=" + DEFAULT_TOTAL_ESI_RATE);

        // Get all the empSalaryInfoList where totalEsiRate is less than UPDATED_TOTAL_ESI_RATE
        defaultEmpSalaryInfoShouldBeFound("totalEsiRate.lessThan=" + UPDATED_TOTAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByTotalEsiRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where totalEsiRate is greater than DEFAULT_TOTAL_ESI_RATE
        defaultEmpSalaryInfoShouldNotBeFound("totalEsiRate.greaterThan=" + DEFAULT_TOTAL_ESI_RATE);

        // Get all the empSalaryInfoList where totalEsiRate is greater than SMALLER_TOTAL_ESI_RATE
        defaultEmpSalaryInfoShouldBeFound("totalEsiRate.greaterThan=" + SMALLER_TOTAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEmployeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where employeId equals to DEFAULT_EMPLOYE_ID
        defaultEmpSalaryInfoShouldBeFound("employeId.equals=" + DEFAULT_EMPLOYE_ID);

        // Get all the empSalaryInfoList where employeId equals to UPDATED_EMPLOYE_ID
        defaultEmpSalaryInfoShouldNotBeFound("employeId.equals=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEmployeIdIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where employeId in DEFAULT_EMPLOYE_ID or UPDATED_EMPLOYE_ID
        defaultEmpSalaryInfoShouldBeFound("employeId.in=" + DEFAULT_EMPLOYE_ID + "," + UPDATED_EMPLOYE_ID);

        // Get all the empSalaryInfoList where employeId equals to UPDATED_EMPLOYE_ID
        defaultEmpSalaryInfoShouldNotBeFound("employeId.in=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEmployeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where employeId is not null
        defaultEmpSalaryInfoShouldBeFound("employeId.specified=true");

        // Get all the empSalaryInfoList where employeId is null
        defaultEmpSalaryInfoShouldNotBeFound("employeId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEmployeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where employeId is greater than or equal to DEFAULT_EMPLOYE_ID
        defaultEmpSalaryInfoShouldBeFound("employeId.greaterThanOrEqual=" + DEFAULT_EMPLOYE_ID);

        // Get all the empSalaryInfoList where employeId is greater than or equal to UPDATED_EMPLOYE_ID
        defaultEmpSalaryInfoShouldNotBeFound("employeId.greaterThanOrEqual=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEmployeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where employeId is less than or equal to DEFAULT_EMPLOYE_ID
        defaultEmpSalaryInfoShouldBeFound("employeId.lessThanOrEqual=" + DEFAULT_EMPLOYE_ID);

        // Get all the empSalaryInfoList where employeId is less than or equal to SMALLER_EMPLOYE_ID
        defaultEmpSalaryInfoShouldNotBeFound("employeId.lessThanOrEqual=" + SMALLER_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEmployeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where employeId is less than DEFAULT_EMPLOYE_ID
        defaultEmpSalaryInfoShouldNotBeFound("employeId.lessThan=" + DEFAULT_EMPLOYE_ID);

        // Get all the empSalaryInfoList where employeId is less than UPDATED_EMPLOYE_ID
        defaultEmpSalaryInfoShouldBeFound("employeId.lessThan=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEmployeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where employeId is greater than DEFAULT_EMPLOYE_ID
        defaultEmpSalaryInfoShouldNotBeFound("employeId.greaterThan=" + DEFAULT_EMPLOYE_ID);

        // Get all the empSalaryInfoList where employeId is greater than SMALLER_EMPLOYE_ID
        defaultEmpSalaryInfoShouldBeFound("employeId.greaterThan=" + SMALLER_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultEmpSalaryInfoShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the empSalaryInfoList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEmpSalaryInfoShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultEmpSalaryInfoShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the empSalaryInfoList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEmpSalaryInfoShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where lastModified is not null
        defaultEmpSalaryInfoShouldBeFound("lastModified.specified=true");

        // Get all the empSalaryInfoList where lastModified is null
        defaultEmpSalaryInfoShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultEmpSalaryInfoShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the empSalaryInfoList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEmpSalaryInfoShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultEmpSalaryInfoShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the empSalaryInfoList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEmpSalaryInfoShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where lastModifiedBy is not null
        defaultEmpSalaryInfoShouldBeFound("lastModifiedBy.specified=true");

        // Get all the empSalaryInfoList where lastModifiedBy is null
        defaultEmpSalaryInfoShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultEmpSalaryInfoShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the empSalaryInfoList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultEmpSalaryInfoShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultEmpSalaryInfoShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the empSalaryInfoList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultEmpSalaryInfoShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where createdBy equals to DEFAULT_CREATED_BY
        defaultEmpSalaryInfoShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the empSalaryInfoList where createdBy equals to UPDATED_CREATED_BY
        defaultEmpSalaryInfoShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultEmpSalaryInfoShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the empSalaryInfoList where createdBy equals to UPDATED_CREATED_BY
        defaultEmpSalaryInfoShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where createdBy is not null
        defaultEmpSalaryInfoShouldBeFound("createdBy.specified=true");

        // Get all the empSalaryInfoList where createdBy is null
        defaultEmpSalaryInfoShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where createdBy contains DEFAULT_CREATED_BY
        defaultEmpSalaryInfoShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the empSalaryInfoList where createdBy contains UPDATED_CREATED_BY
        defaultEmpSalaryInfoShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where createdBy does not contain DEFAULT_CREATED_BY
        defaultEmpSalaryInfoShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the empSalaryInfoList where createdBy does not contain UPDATED_CREATED_BY
        defaultEmpSalaryInfoShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where createdOn equals to DEFAULT_CREATED_ON
        defaultEmpSalaryInfoShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the empSalaryInfoList where createdOn equals to UPDATED_CREATED_ON
        defaultEmpSalaryInfoShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultEmpSalaryInfoShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the empSalaryInfoList where createdOn equals to UPDATED_CREATED_ON
        defaultEmpSalaryInfoShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where createdOn is not null
        defaultEmpSalaryInfoShouldBeFound("createdOn.specified=true");

        // Get all the empSalaryInfoList where createdOn is null
        defaultEmpSalaryInfoShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where deleted equals to DEFAULT_DELETED
        defaultEmpSalaryInfoShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the empSalaryInfoList where deleted equals to UPDATED_DELETED
        defaultEmpSalaryInfoShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultEmpSalaryInfoShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the empSalaryInfoList where deleted equals to UPDATED_DELETED
        defaultEmpSalaryInfoShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        // Get all the empSalaryInfoList where deleted is not null
        defaultEmpSalaryInfoShouldBeFound("deleted.specified=true");

        // Get all the empSalaryInfoList where deleted is null
        defaultEmpSalaryInfoShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpSalaryInfosByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            empSalaryInfoRepository.saveAndFlush(empSalaryInfo);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        empSalaryInfo.setEmployee(employee);
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);
        Long employeeId = employee.getId();

        // Get all the empSalaryInfoList where employee equals to employeeId
        defaultEmpSalaryInfoShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the empSalaryInfoList where employee equals to (employeeId + 1)
        defaultEmpSalaryInfoShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmpSalaryInfoShouldBeFound(String filter) throws Exception {
        restEmpSalaryInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empSalaryInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].salaryBasis").value(hasItem(DEFAULT_SALARY_BASIS.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE)))
            .andExpect(jsonPath("$.[*].isPfContribution").value(hasItem(DEFAULT_IS_PF_CONTRIBUTION.booleanValue())))
            .andExpect(jsonPath("$.[*].pfNumber").value(hasItem(DEFAULT_PF_NUMBER)))
            .andExpect(jsonPath("$.[*].pfRate").value(hasItem(DEFAULT_PF_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalPfRate").value(hasItem(DEFAULT_ADDITIONAL_PF_RATE)))
            .andExpect(jsonPath("$.[*].totalPfRate").value(hasItem(DEFAULT_TOTAL_PF_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].isEsiContribution").value(hasItem(DEFAULT_IS_ESI_CONTRIBUTION.booleanValue())))
            .andExpect(jsonPath("$.[*].esiNumber").value(hasItem(DEFAULT_ESI_NUMBER)))
            .andExpect(jsonPath("$.[*].esiRate").value(hasItem(DEFAULT_ESI_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalEsiRate").value(hasItem(DEFAULT_ADDITIONAL_ESI_RATE)))
            .andExpect(jsonPath("$.[*].totalEsiRate").value(hasItem(DEFAULT_TOTAL_ESI_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].employeId").value(hasItem(DEFAULT_EMPLOYE_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));

        // Check, that the count call also returns 1
        restEmpSalaryInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmpSalaryInfoShouldNotBeFound(String filter) throws Exception {
        restEmpSalaryInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmpSalaryInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmpSalaryInfo() throws Exception {
        // Get the empSalaryInfo
        restEmpSalaryInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmpSalaryInfo() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        int databaseSizeBeforeUpdate = empSalaryInfoRepository.findAll().size();

        // Update the empSalaryInfo
        EmpSalaryInfo updatedEmpSalaryInfo = empSalaryInfoRepository.findById(empSalaryInfo.getId()).get();
        // Disconnect from session so that the updates on updatedEmpSalaryInfo are not directly saved in db
        em.detach(updatedEmpSalaryInfo);
        updatedEmpSalaryInfo
            .salaryBasis(UPDATED_SALARY_BASIS)
            .amount(UPDATED_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .isPfContribution(UPDATED_IS_PF_CONTRIBUTION)
            .pfNumber(UPDATED_PF_NUMBER)
            .pfRate(UPDATED_PF_RATE)
            .additionalPfRate(UPDATED_ADDITIONAL_PF_RATE)
            .totalPfRate(UPDATED_TOTAL_PF_RATE)
            .isEsiContribution(UPDATED_IS_ESI_CONTRIBUTION)
            .esiNumber(UPDATED_ESI_NUMBER)
            .esiRate(UPDATED_ESI_RATE)
            .additionalEsiRate(UPDATED_ADDITIONAL_ESI_RATE)
            .totalEsiRate(UPDATED_TOTAL_ESI_RATE)
            .employeId(UPDATED_EMPLOYE_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);
        EmpSalaryInfoDTO empSalaryInfoDTO = empSalaryInfoMapper.toDto(updatedEmpSalaryInfo);

        restEmpSalaryInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empSalaryInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empSalaryInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmpSalaryInfo in the database
        List<EmpSalaryInfo> empSalaryInfoList = empSalaryInfoRepository.findAll();
        assertThat(empSalaryInfoList).hasSize(databaseSizeBeforeUpdate);
        EmpSalaryInfo testEmpSalaryInfo = empSalaryInfoList.get(empSalaryInfoList.size() - 1);
        assertThat(testEmpSalaryInfo.getSalaryBasis()).isEqualTo(UPDATED_SALARY_BASIS);
        assertThat(testEmpSalaryInfo.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testEmpSalaryInfo.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testEmpSalaryInfo.getIsPfContribution()).isEqualTo(UPDATED_IS_PF_CONTRIBUTION);
        assertThat(testEmpSalaryInfo.getPfNumber()).isEqualTo(UPDATED_PF_NUMBER);
        assertThat(testEmpSalaryInfo.getPfRate()).isEqualTo(UPDATED_PF_RATE);
        assertThat(testEmpSalaryInfo.getAdditionalPfRate()).isEqualTo(UPDATED_ADDITIONAL_PF_RATE);
        assertThat(testEmpSalaryInfo.getTotalPfRate()).isEqualTo(UPDATED_TOTAL_PF_RATE);
        assertThat(testEmpSalaryInfo.getIsEsiContribution()).isEqualTo(UPDATED_IS_ESI_CONTRIBUTION);
        assertThat(testEmpSalaryInfo.getEsiNumber()).isEqualTo(UPDATED_ESI_NUMBER);
        assertThat(testEmpSalaryInfo.getEsiRate()).isEqualTo(UPDATED_ESI_RATE);
        assertThat(testEmpSalaryInfo.getAdditionalEsiRate()).isEqualTo(UPDATED_ADDITIONAL_ESI_RATE);
        assertThat(testEmpSalaryInfo.getTotalEsiRate()).isEqualTo(UPDATED_TOTAL_ESI_RATE);
        assertThat(testEmpSalaryInfo.getEmployeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testEmpSalaryInfo.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEmpSalaryInfo.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEmpSalaryInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmpSalaryInfo.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEmpSalaryInfo.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingEmpSalaryInfo() throws Exception {
        int databaseSizeBeforeUpdate = empSalaryInfoRepository.findAll().size();
        empSalaryInfo.setId(count.incrementAndGet());

        // Create the EmpSalaryInfo
        EmpSalaryInfoDTO empSalaryInfoDTO = empSalaryInfoMapper.toDto(empSalaryInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpSalaryInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empSalaryInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empSalaryInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpSalaryInfo in the database
        List<EmpSalaryInfo> empSalaryInfoList = empSalaryInfoRepository.findAll();
        assertThat(empSalaryInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmpSalaryInfo() throws Exception {
        int databaseSizeBeforeUpdate = empSalaryInfoRepository.findAll().size();
        empSalaryInfo.setId(count.incrementAndGet());

        // Create the EmpSalaryInfo
        EmpSalaryInfoDTO empSalaryInfoDTO = empSalaryInfoMapper.toDto(empSalaryInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpSalaryInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empSalaryInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpSalaryInfo in the database
        List<EmpSalaryInfo> empSalaryInfoList = empSalaryInfoRepository.findAll();
        assertThat(empSalaryInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmpSalaryInfo() throws Exception {
        int databaseSizeBeforeUpdate = empSalaryInfoRepository.findAll().size();
        empSalaryInfo.setId(count.incrementAndGet());

        // Create the EmpSalaryInfo
        EmpSalaryInfoDTO empSalaryInfoDTO = empSalaryInfoMapper.toDto(empSalaryInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpSalaryInfoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empSalaryInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmpSalaryInfo in the database
        List<EmpSalaryInfo> empSalaryInfoList = empSalaryInfoRepository.findAll();
        assertThat(empSalaryInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmpSalaryInfoWithPatch() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        int databaseSizeBeforeUpdate = empSalaryInfoRepository.findAll().size();

        // Update the empSalaryInfo using partial update
        EmpSalaryInfo partialUpdatedEmpSalaryInfo = new EmpSalaryInfo();
        partialUpdatedEmpSalaryInfo.setId(empSalaryInfo.getId());

        partialUpdatedEmpSalaryInfo
            .paymentType(UPDATED_PAYMENT_TYPE)
            .pfNumber(UPDATED_PF_NUMBER)
            .esiRate(UPDATED_ESI_RATE)
            .employeId(UPDATED_EMPLOYE_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .createdBy(UPDATED_CREATED_BY)
            .deleted(UPDATED_DELETED);

        restEmpSalaryInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpSalaryInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmpSalaryInfo))
            )
            .andExpect(status().isOk());

        // Validate the EmpSalaryInfo in the database
        List<EmpSalaryInfo> empSalaryInfoList = empSalaryInfoRepository.findAll();
        assertThat(empSalaryInfoList).hasSize(databaseSizeBeforeUpdate);
        EmpSalaryInfo testEmpSalaryInfo = empSalaryInfoList.get(empSalaryInfoList.size() - 1);
        assertThat(testEmpSalaryInfo.getSalaryBasis()).isEqualTo(DEFAULT_SALARY_BASIS);
        assertThat(testEmpSalaryInfo.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testEmpSalaryInfo.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testEmpSalaryInfo.getIsPfContribution()).isEqualTo(DEFAULT_IS_PF_CONTRIBUTION);
        assertThat(testEmpSalaryInfo.getPfNumber()).isEqualTo(UPDATED_PF_NUMBER);
        assertThat(testEmpSalaryInfo.getPfRate()).isEqualTo(DEFAULT_PF_RATE);
        assertThat(testEmpSalaryInfo.getAdditionalPfRate()).isEqualTo(DEFAULT_ADDITIONAL_PF_RATE);
        assertThat(testEmpSalaryInfo.getTotalPfRate()).isEqualTo(DEFAULT_TOTAL_PF_RATE);
        assertThat(testEmpSalaryInfo.getIsEsiContribution()).isEqualTo(DEFAULT_IS_ESI_CONTRIBUTION);
        assertThat(testEmpSalaryInfo.getEsiNumber()).isEqualTo(DEFAULT_ESI_NUMBER);
        assertThat(testEmpSalaryInfo.getEsiRate()).isEqualTo(UPDATED_ESI_RATE);
        assertThat(testEmpSalaryInfo.getAdditionalEsiRate()).isEqualTo(DEFAULT_ADDITIONAL_ESI_RATE);
        assertThat(testEmpSalaryInfo.getTotalEsiRate()).isEqualTo(DEFAULT_TOTAL_ESI_RATE);
        assertThat(testEmpSalaryInfo.getEmployeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testEmpSalaryInfo.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEmpSalaryInfo.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testEmpSalaryInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmpSalaryInfo.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEmpSalaryInfo.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateEmpSalaryInfoWithPatch() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        int databaseSizeBeforeUpdate = empSalaryInfoRepository.findAll().size();

        // Update the empSalaryInfo using partial update
        EmpSalaryInfo partialUpdatedEmpSalaryInfo = new EmpSalaryInfo();
        partialUpdatedEmpSalaryInfo.setId(empSalaryInfo.getId());

        partialUpdatedEmpSalaryInfo
            .salaryBasis(UPDATED_SALARY_BASIS)
            .amount(UPDATED_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .isPfContribution(UPDATED_IS_PF_CONTRIBUTION)
            .pfNumber(UPDATED_PF_NUMBER)
            .pfRate(UPDATED_PF_RATE)
            .additionalPfRate(UPDATED_ADDITIONAL_PF_RATE)
            .totalPfRate(UPDATED_TOTAL_PF_RATE)
            .isEsiContribution(UPDATED_IS_ESI_CONTRIBUTION)
            .esiNumber(UPDATED_ESI_NUMBER)
            .esiRate(UPDATED_ESI_RATE)
            .additionalEsiRate(UPDATED_ADDITIONAL_ESI_RATE)
            .totalEsiRate(UPDATED_TOTAL_ESI_RATE)
            .employeId(UPDATED_EMPLOYE_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);

        restEmpSalaryInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpSalaryInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmpSalaryInfo))
            )
            .andExpect(status().isOk());

        // Validate the EmpSalaryInfo in the database
        List<EmpSalaryInfo> empSalaryInfoList = empSalaryInfoRepository.findAll();
        assertThat(empSalaryInfoList).hasSize(databaseSizeBeforeUpdate);
        EmpSalaryInfo testEmpSalaryInfo = empSalaryInfoList.get(empSalaryInfoList.size() - 1);
        assertThat(testEmpSalaryInfo.getSalaryBasis()).isEqualTo(UPDATED_SALARY_BASIS);
        assertThat(testEmpSalaryInfo.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testEmpSalaryInfo.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testEmpSalaryInfo.getIsPfContribution()).isEqualTo(UPDATED_IS_PF_CONTRIBUTION);
        assertThat(testEmpSalaryInfo.getPfNumber()).isEqualTo(UPDATED_PF_NUMBER);
        assertThat(testEmpSalaryInfo.getPfRate()).isEqualTo(UPDATED_PF_RATE);
        assertThat(testEmpSalaryInfo.getAdditionalPfRate()).isEqualTo(UPDATED_ADDITIONAL_PF_RATE);
        assertThat(testEmpSalaryInfo.getTotalPfRate()).isEqualTo(UPDATED_TOTAL_PF_RATE);
        assertThat(testEmpSalaryInfo.getIsEsiContribution()).isEqualTo(UPDATED_IS_ESI_CONTRIBUTION);
        assertThat(testEmpSalaryInfo.getEsiNumber()).isEqualTo(UPDATED_ESI_NUMBER);
        assertThat(testEmpSalaryInfo.getEsiRate()).isEqualTo(UPDATED_ESI_RATE);
        assertThat(testEmpSalaryInfo.getAdditionalEsiRate()).isEqualTo(UPDATED_ADDITIONAL_ESI_RATE);
        assertThat(testEmpSalaryInfo.getTotalEsiRate()).isEqualTo(UPDATED_TOTAL_ESI_RATE);
        assertThat(testEmpSalaryInfo.getEmployeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testEmpSalaryInfo.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEmpSalaryInfo.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEmpSalaryInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmpSalaryInfo.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEmpSalaryInfo.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingEmpSalaryInfo() throws Exception {
        int databaseSizeBeforeUpdate = empSalaryInfoRepository.findAll().size();
        empSalaryInfo.setId(count.incrementAndGet());

        // Create the EmpSalaryInfo
        EmpSalaryInfoDTO empSalaryInfoDTO = empSalaryInfoMapper.toDto(empSalaryInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpSalaryInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, empSalaryInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empSalaryInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpSalaryInfo in the database
        List<EmpSalaryInfo> empSalaryInfoList = empSalaryInfoRepository.findAll();
        assertThat(empSalaryInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmpSalaryInfo() throws Exception {
        int databaseSizeBeforeUpdate = empSalaryInfoRepository.findAll().size();
        empSalaryInfo.setId(count.incrementAndGet());

        // Create the EmpSalaryInfo
        EmpSalaryInfoDTO empSalaryInfoDTO = empSalaryInfoMapper.toDto(empSalaryInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpSalaryInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empSalaryInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpSalaryInfo in the database
        List<EmpSalaryInfo> empSalaryInfoList = empSalaryInfoRepository.findAll();
        assertThat(empSalaryInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmpSalaryInfo() throws Exception {
        int databaseSizeBeforeUpdate = empSalaryInfoRepository.findAll().size();
        empSalaryInfo.setId(count.incrementAndGet());

        // Create the EmpSalaryInfo
        EmpSalaryInfoDTO empSalaryInfoDTO = empSalaryInfoMapper.toDto(empSalaryInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpSalaryInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empSalaryInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmpSalaryInfo in the database
        List<EmpSalaryInfo> empSalaryInfoList = empSalaryInfoRepository.findAll();
        assertThat(empSalaryInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmpSalaryInfo() throws Exception {
        // Initialize the database
        empSalaryInfoRepository.saveAndFlush(empSalaryInfo);

        int databaseSizeBeforeDelete = empSalaryInfoRepository.findAll().size();

        // Delete the empSalaryInfo
        restEmpSalaryInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, empSalaryInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmpSalaryInfo> empSalaryInfoList = empSalaryInfoRepository.findAll();
        assertThat(empSalaryInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
