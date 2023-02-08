package com.techvg.shrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.shrms.IntegrationTest;
import com.techvg.shrms.domain.Employee;
import com.techvg.shrms.domain.FamilyInfo;
import com.techvg.shrms.domain.enumeration.Relationship;
import com.techvg.shrms.repository.FamilyInfoRepository;
import com.techvg.shrms.service.criteria.FamilyInfoCriteria;
import com.techvg.shrms.service.dto.FamilyInfoDTO;
import com.techvg.shrms.service.mapper.FamilyInfoMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link FamilyInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FamilyInfoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOB = LocalDate.ofEpochDay(-1L);

    private static final Relationship DEFAULT_RELATION = Relationship.FATHER;
    private static final Relationship UPDATED_RELATION = Relationship.MOTHER;

    private static final String DEFAULT_PHONE_NO = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/family-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FamilyInfoRepository familyInfoRepository;

    @Autowired
    private FamilyInfoMapper familyInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFamilyInfoMockMvc;

    private FamilyInfo familyInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilyInfo createEntity(EntityManager em) {
        FamilyInfo familyInfo = new FamilyInfo()
            .name(DEFAULT_NAME)
            .dob(DEFAULT_DOB)
            .relation(DEFAULT_RELATION)
            .phoneNo(DEFAULT_PHONE_NO)
            .address(DEFAULT_ADDRESS)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .deleted(DEFAULT_DELETED)
            .employeeId(DEFAULT_EMPLOYEE_ID);
        return familyInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilyInfo createUpdatedEntity(EntityManager em) {
        FamilyInfo familyInfo = new FamilyInfo()
            .name(UPDATED_NAME)
            .dob(UPDATED_DOB)
            .relation(UPDATED_RELATION)
            .phoneNo(UPDATED_PHONE_NO)
            .address(UPDATED_ADDRESS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID);
        return familyInfo;
    }

    @BeforeEach
    public void initTest() {
        familyInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createFamilyInfo() throws Exception {
        int databaseSizeBeforeCreate = familyInfoRepository.findAll().size();
        // Create the FamilyInfo
        FamilyInfoDTO familyInfoDTO = familyInfoMapper.toDto(familyInfo);
        restFamilyInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(familyInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the FamilyInfo in the database
        List<FamilyInfo> familyInfoList = familyInfoRepository.findAll();
        assertThat(familyInfoList).hasSize(databaseSizeBeforeCreate + 1);
        FamilyInfo testFamilyInfo = familyInfoList.get(familyInfoList.size() - 1);
        assertThat(testFamilyInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFamilyInfo.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testFamilyInfo.getRelation()).isEqualTo(DEFAULT_RELATION);
        assertThat(testFamilyInfo.getPhoneNo()).isEqualTo(DEFAULT_PHONE_NO);
        assertThat(testFamilyInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testFamilyInfo.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testFamilyInfo.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testFamilyInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFamilyInfo.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testFamilyInfo.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testFamilyInfo.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void createFamilyInfoWithExistingId() throws Exception {
        // Create the FamilyInfo with an existing ID
        familyInfo.setId(1L);
        FamilyInfoDTO familyInfoDTO = familyInfoMapper.toDto(familyInfo);

        int databaseSizeBeforeCreate = familyInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilyInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(familyInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FamilyInfo in the database
        List<FamilyInfo> familyInfoList = familyInfoRepository.findAll();
        assertThat(familyInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFamilyInfos() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList
        restFamilyInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION.toString())))
            .andExpect(jsonPath("$.[*].phoneNo").value(hasItem(DEFAULT_PHONE_NO)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())));
    }

    @Test
    @Transactional
    void getFamilyInfo() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get the familyInfo
        restFamilyInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, familyInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(familyInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.relation").value(DEFAULT_RELATION.toString()))
            .andExpect(jsonPath("$.phoneNo").value(DEFAULT_PHONE_NO))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()));
    }

    @Test
    @Transactional
    void getFamilyInfosByIdFiltering() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        Long id = familyInfo.getId();

        defaultFamilyInfoShouldBeFound("id.equals=" + id);
        defaultFamilyInfoShouldNotBeFound("id.notEquals=" + id);

        defaultFamilyInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFamilyInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultFamilyInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFamilyInfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where name equals to DEFAULT_NAME
        defaultFamilyInfoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the familyInfoList where name equals to UPDATED_NAME
        defaultFamilyInfoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultFamilyInfoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the familyInfoList where name equals to UPDATED_NAME
        defaultFamilyInfoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where name is not null
        defaultFamilyInfoShouldBeFound("name.specified=true");

        // Get all the familyInfoList where name is null
        defaultFamilyInfoShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllFamilyInfosByNameContainsSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where name contains DEFAULT_NAME
        defaultFamilyInfoShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the familyInfoList where name contains UPDATED_NAME
        defaultFamilyInfoShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where name does not contain DEFAULT_NAME
        defaultFamilyInfoShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the familyInfoList where name does not contain UPDATED_NAME
        defaultFamilyInfoShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByDobIsEqualToSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where dob equals to DEFAULT_DOB
        defaultFamilyInfoShouldBeFound("dob.equals=" + DEFAULT_DOB);

        // Get all the familyInfoList where dob equals to UPDATED_DOB
        defaultFamilyInfoShouldNotBeFound("dob.equals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByDobIsInShouldWork() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where dob in DEFAULT_DOB or UPDATED_DOB
        defaultFamilyInfoShouldBeFound("dob.in=" + DEFAULT_DOB + "," + UPDATED_DOB);

        // Get all the familyInfoList where dob equals to UPDATED_DOB
        defaultFamilyInfoShouldNotBeFound("dob.in=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByDobIsNullOrNotNull() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where dob is not null
        defaultFamilyInfoShouldBeFound("dob.specified=true");

        // Get all the familyInfoList where dob is null
        defaultFamilyInfoShouldNotBeFound("dob.specified=false");
    }

    @Test
    @Transactional
    void getAllFamilyInfosByDobIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where dob is greater than or equal to DEFAULT_DOB
        defaultFamilyInfoShouldBeFound("dob.greaterThanOrEqual=" + DEFAULT_DOB);

        // Get all the familyInfoList where dob is greater than or equal to UPDATED_DOB
        defaultFamilyInfoShouldNotBeFound("dob.greaterThanOrEqual=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByDobIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where dob is less than or equal to DEFAULT_DOB
        defaultFamilyInfoShouldBeFound("dob.lessThanOrEqual=" + DEFAULT_DOB);

        // Get all the familyInfoList where dob is less than or equal to SMALLER_DOB
        defaultFamilyInfoShouldNotBeFound("dob.lessThanOrEqual=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByDobIsLessThanSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where dob is less than DEFAULT_DOB
        defaultFamilyInfoShouldNotBeFound("dob.lessThan=" + DEFAULT_DOB);

        // Get all the familyInfoList where dob is less than UPDATED_DOB
        defaultFamilyInfoShouldBeFound("dob.lessThan=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByDobIsGreaterThanSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where dob is greater than DEFAULT_DOB
        defaultFamilyInfoShouldNotBeFound("dob.greaterThan=" + DEFAULT_DOB);

        // Get all the familyInfoList where dob is greater than SMALLER_DOB
        defaultFamilyInfoShouldBeFound("dob.greaterThan=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByRelationIsEqualToSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where relation equals to DEFAULT_RELATION
        defaultFamilyInfoShouldBeFound("relation.equals=" + DEFAULT_RELATION);

        // Get all the familyInfoList where relation equals to UPDATED_RELATION
        defaultFamilyInfoShouldNotBeFound("relation.equals=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByRelationIsInShouldWork() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where relation in DEFAULT_RELATION or UPDATED_RELATION
        defaultFamilyInfoShouldBeFound("relation.in=" + DEFAULT_RELATION + "," + UPDATED_RELATION);

        // Get all the familyInfoList where relation equals to UPDATED_RELATION
        defaultFamilyInfoShouldNotBeFound("relation.in=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByRelationIsNullOrNotNull() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where relation is not null
        defaultFamilyInfoShouldBeFound("relation.specified=true");

        // Get all the familyInfoList where relation is null
        defaultFamilyInfoShouldNotBeFound("relation.specified=false");
    }

    @Test
    @Transactional
    void getAllFamilyInfosByPhoneNoIsEqualToSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where phoneNo equals to DEFAULT_PHONE_NO
        defaultFamilyInfoShouldBeFound("phoneNo.equals=" + DEFAULT_PHONE_NO);

        // Get all the familyInfoList where phoneNo equals to UPDATED_PHONE_NO
        defaultFamilyInfoShouldNotBeFound("phoneNo.equals=" + UPDATED_PHONE_NO);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByPhoneNoIsInShouldWork() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where phoneNo in DEFAULT_PHONE_NO or UPDATED_PHONE_NO
        defaultFamilyInfoShouldBeFound("phoneNo.in=" + DEFAULT_PHONE_NO + "," + UPDATED_PHONE_NO);

        // Get all the familyInfoList where phoneNo equals to UPDATED_PHONE_NO
        defaultFamilyInfoShouldNotBeFound("phoneNo.in=" + UPDATED_PHONE_NO);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByPhoneNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where phoneNo is not null
        defaultFamilyInfoShouldBeFound("phoneNo.specified=true");

        // Get all the familyInfoList where phoneNo is null
        defaultFamilyInfoShouldNotBeFound("phoneNo.specified=false");
    }

    @Test
    @Transactional
    void getAllFamilyInfosByPhoneNoContainsSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where phoneNo contains DEFAULT_PHONE_NO
        defaultFamilyInfoShouldBeFound("phoneNo.contains=" + DEFAULT_PHONE_NO);

        // Get all the familyInfoList where phoneNo contains UPDATED_PHONE_NO
        defaultFamilyInfoShouldNotBeFound("phoneNo.contains=" + UPDATED_PHONE_NO);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByPhoneNoNotContainsSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where phoneNo does not contain DEFAULT_PHONE_NO
        defaultFamilyInfoShouldNotBeFound("phoneNo.doesNotContain=" + DEFAULT_PHONE_NO);

        // Get all the familyInfoList where phoneNo does not contain UPDATED_PHONE_NO
        defaultFamilyInfoShouldBeFound("phoneNo.doesNotContain=" + UPDATED_PHONE_NO);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where address equals to DEFAULT_ADDRESS
        defaultFamilyInfoShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the familyInfoList where address equals to UPDATED_ADDRESS
        defaultFamilyInfoShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultFamilyInfoShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the familyInfoList where address equals to UPDATED_ADDRESS
        defaultFamilyInfoShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where address is not null
        defaultFamilyInfoShouldBeFound("address.specified=true");

        // Get all the familyInfoList where address is null
        defaultFamilyInfoShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllFamilyInfosByAddressContainsSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where address contains DEFAULT_ADDRESS
        defaultFamilyInfoShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the familyInfoList where address contains UPDATED_ADDRESS
        defaultFamilyInfoShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where address does not contain DEFAULT_ADDRESS
        defaultFamilyInfoShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the familyInfoList where address does not contain UPDATED_ADDRESS
        defaultFamilyInfoShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultFamilyInfoShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the familyInfoList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultFamilyInfoShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultFamilyInfoShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the familyInfoList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultFamilyInfoShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where lastModified is not null
        defaultFamilyInfoShouldBeFound("lastModified.specified=true");

        // Get all the familyInfoList where lastModified is null
        defaultFamilyInfoShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllFamilyInfosByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultFamilyInfoShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the familyInfoList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultFamilyInfoShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultFamilyInfoShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the familyInfoList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultFamilyInfoShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where lastModifiedBy is not null
        defaultFamilyInfoShouldBeFound("lastModifiedBy.specified=true");

        // Get all the familyInfoList where lastModifiedBy is null
        defaultFamilyInfoShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFamilyInfosByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultFamilyInfoShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the familyInfoList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultFamilyInfoShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultFamilyInfoShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the familyInfoList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultFamilyInfoShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where createdBy equals to DEFAULT_CREATED_BY
        defaultFamilyInfoShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the familyInfoList where createdBy equals to UPDATED_CREATED_BY
        defaultFamilyInfoShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultFamilyInfoShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the familyInfoList where createdBy equals to UPDATED_CREATED_BY
        defaultFamilyInfoShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where createdBy is not null
        defaultFamilyInfoShouldBeFound("createdBy.specified=true");

        // Get all the familyInfoList where createdBy is null
        defaultFamilyInfoShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFamilyInfosByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where createdBy contains DEFAULT_CREATED_BY
        defaultFamilyInfoShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the familyInfoList where createdBy contains UPDATED_CREATED_BY
        defaultFamilyInfoShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where createdBy does not contain DEFAULT_CREATED_BY
        defaultFamilyInfoShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the familyInfoList where createdBy does not contain UPDATED_CREATED_BY
        defaultFamilyInfoShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where createdOn equals to DEFAULT_CREATED_ON
        defaultFamilyInfoShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the familyInfoList where createdOn equals to UPDATED_CREATED_ON
        defaultFamilyInfoShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultFamilyInfoShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the familyInfoList where createdOn equals to UPDATED_CREATED_ON
        defaultFamilyInfoShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where createdOn is not null
        defaultFamilyInfoShouldBeFound("createdOn.specified=true");

        // Get all the familyInfoList where createdOn is null
        defaultFamilyInfoShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllFamilyInfosByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where deleted equals to DEFAULT_DELETED
        defaultFamilyInfoShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the familyInfoList where deleted equals to UPDATED_DELETED
        defaultFamilyInfoShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultFamilyInfoShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the familyInfoList where deleted equals to UPDATED_DELETED
        defaultFamilyInfoShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where deleted is not null
        defaultFamilyInfoShouldBeFound("deleted.specified=true");

        // Get all the familyInfoList where deleted is null
        defaultFamilyInfoShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllFamilyInfosByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultFamilyInfoShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the familyInfoList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultFamilyInfoShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultFamilyInfoShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the familyInfoList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultFamilyInfoShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where employeeId is not null
        defaultFamilyInfoShouldBeFound("employeeId.specified=true");

        // Get all the familyInfoList where employeeId is null
        defaultFamilyInfoShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllFamilyInfosByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultFamilyInfoShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the familyInfoList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultFamilyInfoShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultFamilyInfoShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the familyInfoList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultFamilyInfoShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultFamilyInfoShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the familyInfoList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultFamilyInfoShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        // Get all the familyInfoList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultFamilyInfoShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the familyInfoList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultFamilyInfoShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllFamilyInfosByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            familyInfoRepository.saveAndFlush(familyInfo);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        familyInfo.setEmployee(employee);
        familyInfoRepository.saveAndFlush(familyInfo);
        Long employeeId = employee.getId();

        // Get all the familyInfoList where employee equals to employeeId
        defaultFamilyInfoShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the familyInfoList where employee equals to (employeeId + 1)
        defaultFamilyInfoShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFamilyInfoShouldBeFound(String filter) throws Exception {
        restFamilyInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION.toString())))
            .andExpect(jsonPath("$.[*].phoneNo").value(hasItem(DEFAULT_PHONE_NO)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())));

        // Check, that the count call also returns 1
        restFamilyInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFamilyInfoShouldNotBeFound(String filter) throws Exception {
        restFamilyInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFamilyInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFamilyInfo() throws Exception {
        // Get the familyInfo
        restFamilyInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFamilyInfo() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        int databaseSizeBeforeUpdate = familyInfoRepository.findAll().size();

        // Update the familyInfo
        FamilyInfo updatedFamilyInfo = familyInfoRepository.findById(familyInfo.getId()).get();
        // Disconnect from session so that the updates on updatedFamilyInfo are not directly saved in db
        em.detach(updatedFamilyInfo);
        updatedFamilyInfo
            .name(UPDATED_NAME)
            .dob(UPDATED_DOB)
            .relation(UPDATED_RELATION)
            .phoneNo(UPDATED_PHONE_NO)
            .address(UPDATED_ADDRESS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID);
        FamilyInfoDTO familyInfoDTO = familyInfoMapper.toDto(updatedFamilyInfo);

        restFamilyInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, familyInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(familyInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the FamilyInfo in the database
        List<FamilyInfo> familyInfoList = familyInfoRepository.findAll();
        assertThat(familyInfoList).hasSize(databaseSizeBeforeUpdate);
        FamilyInfo testFamilyInfo = familyInfoList.get(familyInfoList.size() - 1);
        assertThat(testFamilyInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFamilyInfo.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testFamilyInfo.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testFamilyInfo.getPhoneNo()).isEqualTo(UPDATED_PHONE_NO);
        assertThat(testFamilyInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testFamilyInfo.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testFamilyInfo.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testFamilyInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFamilyInfo.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testFamilyInfo.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testFamilyInfo.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void putNonExistingFamilyInfo() throws Exception {
        int databaseSizeBeforeUpdate = familyInfoRepository.findAll().size();
        familyInfo.setId(count.incrementAndGet());

        // Create the FamilyInfo
        FamilyInfoDTO familyInfoDTO = familyInfoMapper.toDto(familyInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, familyInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(familyInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyInfo in the database
        List<FamilyInfo> familyInfoList = familyInfoRepository.findAll();
        assertThat(familyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFamilyInfo() throws Exception {
        int databaseSizeBeforeUpdate = familyInfoRepository.findAll().size();
        familyInfo.setId(count.incrementAndGet());

        // Create the FamilyInfo
        FamilyInfoDTO familyInfoDTO = familyInfoMapper.toDto(familyInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(familyInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyInfo in the database
        List<FamilyInfo> familyInfoList = familyInfoRepository.findAll();
        assertThat(familyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFamilyInfo() throws Exception {
        int databaseSizeBeforeUpdate = familyInfoRepository.findAll().size();
        familyInfo.setId(count.incrementAndGet());

        // Create the FamilyInfo
        FamilyInfoDTO familyInfoDTO = familyInfoMapper.toDto(familyInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(familyInfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FamilyInfo in the database
        List<FamilyInfo> familyInfoList = familyInfoRepository.findAll();
        assertThat(familyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFamilyInfoWithPatch() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        int databaseSizeBeforeUpdate = familyInfoRepository.findAll().size();

        // Update the familyInfo using partial update
        FamilyInfo partialUpdatedFamilyInfo = new FamilyInfo();
        partialUpdatedFamilyInfo.setId(familyInfo.getId());

        partialUpdatedFamilyInfo
            .name(UPDATED_NAME)
            .dob(UPDATED_DOB)
            .relation(UPDATED_RELATION)
            .phoneNo(UPDATED_PHONE_NO)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY);

        restFamilyInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFamilyInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFamilyInfo))
            )
            .andExpect(status().isOk());

        // Validate the FamilyInfo in the database
        List<FamilyInfo> familyInfoList = familyInfoRepository.findAll();
        assertThat(familyInfoList).hasSize(databaseSizeBeforeUpdate);
        FamilyInfo testFamilyInfo = familyInfoList.get(familyInfoList.size() - 1);
        assertThat(testFamilyInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFamilyInfo.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testFamilyInfo.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testFamilyInfo.getPhoneNo()).isEqualTo(UPDATED_PHONE_NO);
        assertThat(testFamilyInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testFamilyInfo.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testFamilyInfo.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testFamilyInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFamilyInfo.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testFamilyInfo.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testFamilyInfo.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void fullUpdateFamilyInfoWithPatch() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        int databaseSizeBeforeUpdate = familyInfoRepository.findAll().size();

        // Update the familyInfo using partial update
        FamilyInfo partialUpdatedFamilyInfo = new FamilyInfo();
        partialUpdatedFamilyInfo.setId(familyInfo.getId());

        partialUpdatedFamilyInfo
            .name(UPDATED_NAME)
            .dob(UPDATED_DOB)
            .relation(UPDATED_RELATION)
            .phoneNo(UPDATED_PHONE_NO)
            .address(UPDATED_ADDRESS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID);

        restFamilyInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFamilyInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFamilyInfo))
            )
            .andExpect(status().isOk());

        // Validate the FamilyInfo in the database
        List<FamilyInfo> familyInfoList = familyInfoRepository.findAll();
        assertThat(familyInfoList).hasSize(databaseSizeBeforeUpdate);
        FamilyInfo testFamilyInfo = familyInfoList.get(familyInfoList.size() - 1);
        assertThat(testFamilyInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFamilyInfo.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testFamilyInfo.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testFamilyInfo.getPhoneNo()).isEqualTo(UPDATED_PHONE_NO);
        assertThat(testFamilyInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testFamilyInfo.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testFamilyInfo.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testFamilyInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFamilyInfo.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testFamilyInfo.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testFamilyInfo.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingFamilyInfo() throws Exception {
        int databaseSizeBeforeUpdate = familyInfoRepository.findAll().size();
        familyInfo.setId(count.incrementAndGet());

        // Create the FamilyInfo
        FamilyInfoDTO familyInfoDTO = familyInfoMapper.toDto(familyInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, familyInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(familyInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyInfo in the database
        List<FamilyInfo> familyInfoList = familyInfoRepository.findAll();
        assertThat(familyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFamilyInfo() throws Exception {
        int databaseSizeBeforeUpdate = familyInfoRepository.findAll().size();
        familyInfo.setId(count.incrementAndGet());

        // Create the FamilyInfo
        FamilyInfoDTO familyInfoDTO = familyInfoMapper.toDto(familyInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(familyInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyInfo in the database
        List<FamilyInfo> familyInfoList = familyInfoRepository.findAll();
        assertThat(familyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFamilyInfo() throws Exception {
        int databaseSizeBeforeUpdate = familyInfoRepository.findAll().size();
        familyInfo.setId(count.incrementAndGet());

        // Create the FamilyInfo
        FamilyInfoDTO familyInfoDTO = familyInfoMapper.toDto(familyInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(familyInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FamilyInfo in the database
        List<FamilyInfo> familyInfoList = familyInfoRepository.findAll();
        assertThat(familyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFamilyInfo() throws Exception {
        // Initialize the database
        familyInfoRepository.saveAndFlush(familyInfo);

        int databaseSizeBeforeDelete = familyInfoRepository.findAll().size();

        // Delete the familyInfo
        restFamilyInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, familyInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FamilyInfo> familyInfoList = familyInfoRepository.findAll();
        assertThat(familyInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
