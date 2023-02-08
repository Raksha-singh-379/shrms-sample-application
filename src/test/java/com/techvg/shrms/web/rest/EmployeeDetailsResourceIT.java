package com.techvg.shrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.shrms.IntegrationTest;
import com.techvg.shrms.domain.Employee;
import com.techvg.shrms.domain.EmployeeDetails;
import com.techvg.shrms.domain.enumeration.BloodGroup;
import com.techvg.shrms.domain.enumeration.MaritalStatus;
import com.techvg.shrms.repository.EmployeeDetailsRepository;
import com.techvg.shrms.service.criteria.EmployeeDetailsCriteria;
import com.techvg.shrms.service.dto.EmployeeDetailsDTO;
import com.techvg.shrms.service.mapper.EmployeeDetailsMapper;
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
 * Integration tests for the {@link EmployeeDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeDetailsResourceIT {

    private static final String DEFAULT_PASSPORT_NO = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_NO = "BBBBBBBBBB";

    private static final Instant DEFAULT_PASSPORT_EXP_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PASSPORT_EXP_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TELEPHONE_NO = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final MaritalStatus DEFAULT_MARITAL_STATUS = MaritalStatus.MARRIED;
    private static final MaritalStatus UPDATED_MARITAL_STATUS = MaritalStatus.SINGLE;

    private static final String DEFAULT_RELIGION = "AAAAAAAAAA";
    private static final String UPDATED_RELIGION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SPOUS_EMPLOYED = false;
    private static final Boolean UPDATED_IS_SPOUS_EMPLOYED = true;

    private static final Long DEFAULT_NO_OF_CHILDREN = 1L;
    private static final Long UPDATED_NO_OF_CHILDREN = 2L;
    private static final Long SMALLER_NO_OF_CHILDREN = 1L - 1L;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_EMPLOYEE_ID = 2L;
    private static final Long SMALLER_EMPLOYEE_ID = 1L - 1L;

    private static final Long DEFAULT_AGE = 1L;
    private static final Long UPDATED_AGE = 2L;
    private static final Long SMALLER_AGE = 1L - 1L;

    private static final String DEFAULT_FATHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOTHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AADHAR_NO = "AAAAAAAAAA";
    private static final String UPDATED_AADHAR_NO = "BBBBBBBBBB";

    private static final BloodGroup DEFAULT_BLOOD_GROUP = BloodGroup.A_POSITIVE;
    private static final BloodGroup UPDATED_BLOOD_GROUP = BloodGroup.B_POSITIVE;

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOB = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_EXPERTISE = "AAAAAAAAAA";
    private static final String UPDATED_EXPERTISE = "BBBBBBBBBB";

    private static final String DEFAULT_HOBBIES = "AAAAAAAAAA";
    private static final String UPDATED_HOBBIES = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_INTEREST = "AAAAAAAAAA";
    private static final String UPDATED_AREA_INTEREST = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE_KNOWN = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE_KNOWN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/employee-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;

    @Autowired
    private EmployeeDetailsMapper employeeDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeDetailsMockMvc;

    private EmployeeDetails employeeDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeDetails createEntity(EntityManager em) {
        EmployeeDetails employeeDetails = new EmployeeDetails()
            .passportNo(DEFAULT_PASSPORT_NO)
            .passportExpDate(DEFAULT_PASSPORT_EXP_DATE)
            .telephoneNo(DEFAULT_TELEPHONE_NO)
            .nationality(DEFAULT_NATIONALITY)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .religion(DEFAULT_RELIGION)
            .isSpousEmployed(DEFAULT_IS_SPOUS_EMPLOYED)
            .noOfChildren(DEFAULT_NO_OF_CHILDREN)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .deleted(DEFAULT_DELETED)
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .age(DEFAULT_AGE)
            .fatherName(DEFAULT_FATHER_NAME)
            .motherName(DEFAULT_MOTHER_NAME)
            .aadharNo(DEFAULT_AADHAR_NO)
            .bloodGroup(DEFAULT_BLOOD_GROUP)
            .dob(DEFAULT_DOB)
            .expertise(DEFAULT_EXPERTISE)
            .hobbies(DEFAULT_HOBBIES)
            .areaInterest(DEFAULT_AREA_INTEREST)
            .languageKnown(DEFAULT_LANGUAGE_KNOWN)
            .description(DEFAULT_DESCRIPTION);
        return employeeDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeDetails createUpdatedEntity(EntityManager em) {
        EmployeeDetails employeeDetails = new EmployeeDetails()
            .passportNo(UPDATED_PASSPORT_NO)
            .passportExpDate(UPDATED_PASSPORT_EXP_DATE)
            .telephoneNo(UPDATED_TELEPHONE_NO)
            .nationality(UPDATED_NATIONALITY)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .religion(UPDATED_RELIGION)
            .isSpousEmployed(UPDATED_IS_SPOUS_EMPLOYED)
            .noOfChildren(UPDATED_NO_OF_CHILDREN)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .age(UPDATED_AGE)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .aadharNo(UPDATED_AADHAR_NO)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .dob(UPDATED_DOB)
            .expertise(UPDATED_EXPERTISE)
            .hobbies(UPDATED_HOBBIES)
            .areaInterest(UPDATED_AREA_INTEREST)
            .languageKnown(UPDATED_LANGUAGE_KNOWN)
            .description(UPDATED_DESCRIPTION);
        return employeeDetails;
    }

    @BeforeEach
    public void initTest() {
        employeeDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeDetails() throws Exception {
        int databaseSizeBeforeCreate = employeeDetailsRepository.findAll().size();
        // Create the EmployeeDetails
        EmployeeDetailsDTO employeeDetailsDTO = employeeDetailsMapper.toDto(employeeDetails);
        restEmployeeDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeDetails testEmployeeDetails = employeeDetailsList.get(employeeDetailsList.size() - 1);
        assertThat(testEmployeeDetails.getPassportNo()).isEqualTo(DEFAULT_PASSPORT_NO);
        assertThat(testEmployeeDetails.getPassportExpDate()).isEqualTo(DEFAULT_PASSPORT_EXP_DATE);
        assertThat(testEmployeeDetails.getTelephoneNo()).isEqualTo(DEFAULT_TELEPHONE_NO);
        assertThat(testEmployeeDetails.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testEmployeeDetails.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testEmployeeDetails.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testEmployeeDetails.getIsSpousEmployed()).isEqualTo(DEFAULT_IS_SPOUS_EMPLOYED);
        assertThat(testEmployeeDetails.getNoOfChildren()).isEqualTo(DEFAULT_NO_OF_CHILDREN);
        assertThat(testEmployeeDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEmployeeDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEmployeeDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testEmployeeDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testEmployeeDetails.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testEmployeeDetails.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testEmployeeDetails.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testEmployeeDetails.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testEmployeeDetails.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testEmployeeDetails.getAadharNo()).isEqualTo(DEFAULT_AADHAR_NO);
        assertThat(testEmployeeDetails.getBloodGroup()).isEqualTo(DEFAULT_BLOOD_GROUP);
        assertThat(testEmployeeDetails.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testEmployeeDetails.getExpertise()).isEqualTo(DEFAULT_EXPERTISE);
        assertThat(testEmployeeDetails.getHobbies()).isEqualTo(DEFAULT_HOBBIES);
        assertThat(testEmployeeDetails.getAreaInterest()).isEqualTo(DEFAULT_AREA_INTEREST);
        assertThat(testEmployeeDetails.getLanguageKnown()).isEqualTo(DEFAULT_LANGUAGE_KNOWN);
        assertThat(testEmployeeDetails.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createEmployeeDetailsWithExistingId() throws Exception {
        // Create the EmployeeDetails with an existing ID
        employeeDetails.setId(1L);
        EmployeeDetailsDTO employeeDetailsDTO = employeeDetailsMapper.toDto(employeeDetails);

        int databaseSizeBeforeCreate = employeeDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployeeDetails() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList
        restEmployeeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].passportNo").value(hasItem(DEFAULT_PASSPORT_NO)))
            .andExpect(jsonPath("$.[*].passportExpDate").value(hasItem(DEFAULT_PASSPORT_EXP_DATE.toString())))
            .andExpect(jsonPath("$.[*].telephoneNo").value(hasItem(DEFAULT_TELEPHONE_NO)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION)))
            .andExpect(jsonPath("$.[*].isSpousEmployed").value(hasItem(DEFAULT_IS_SPOUS_EMPLOYED.booleanValue())))
            .andExpect(jsonPath("$.[*].noOfChildren").value(hasItem(DEFAULT_NO_OF_CHILDREN.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.intValue())))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME)))
            .andExpect(jsonPath("$.[*].aadharNo").value(hasItem(DEFAULT_AADHAR_NO)))
            .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].expertise").value(hasItem(DEFAULT_EXPERTISE)))
            .andExpect(jsonPath("$.[*].hobbies").value(hasItem(DEFAULT_HOBBIES)))
            .andExpect(jsonPath("$.[*].areaInterest").value(hasItem(DEFAULT_AREA_INTEREST)))
            .andExpect(jsonPath("$.[*].languageKnown").value(hasItem(DEFAULT_LANGUAGE_KNOWN)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getEmployeeDetails() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get the employeeDetails
        restEmployeeDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeDetails.getId().intValue()))
            .andExpect(jsonPath("$.passportNo").value(DEFAULT_PASSPORT_NO))
            .andExpect(jsonPath("$.passportExpDate").value(DEFAULT_PASSPORT_EXP_DATE.toString()))
            .andExpect(jsonPath("$.telephoneNo").value(DEFAULT_TELEPHONE_NO))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION))
            .andExpect(jsonPath("$.isSpousEmployed").value(DEFAULT_IS_SPOUS_EMPLOYED.booleanValue()))
            .andExpect(jsonPath("$.noOfChildren").value(DEFAULT_NO_OF_CHILDREN.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.intValue()))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME))
            .andExpect(jsonPath("$.motherName").value(DEFAULT_MOTHER_NAME))
            .andExpect(jsonPath("$.aadharNo").value(DEFAULT_AADHAR_NO))
            .andExpect(jsonPath("$.bloodGroup").value(DEFAULT_BLOOD_GROUP.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.expertise").value(DEFAULT_EXPERTISE))
            .andExpect(jsonPath("$.hobbies").value(DEFAULT_HOBBIES))
            .andExpect(jsonPath("$.areaInterest").value(DEFAULT_AREA_INTEREST))
            .andExpect(jsonPath("$.languageKnown").value(DEFAULT_LANGUAGE_KNOWN))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getEmployeeDetailsByIdFiltering() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        Long id = employeeDetails.getId();

        defaultEmployeeDetailsShouldBeFound("id.equals=" + id);
        defaultEmployeeDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByPassportNoIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where passportNo equals to DEFAULT_PASSPORT_NO
        defaultEmployeeDetailsShouldBeFound("passportNo.equals=" + DEFAULT_PASSPORT_NO);

        // Get all the employeeDetailsList where passportNo equals to UPDATED_PASSPORT_NO
        defaultEmployeeDetailsShouldNotBeFound("passportNo.equals=" + UPDATED_PASSPORT_NO);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByPassportNoIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where passportNo in DEFAULT_PASSPORT_NO or UPDATED_PASSPORT_NO
        defaultEmployeeDetailsShouldBeFound("passportNo.in=" + DEFAULT_PASSPORT_NO + "," + UPDATED_PASSPORT_NO);

        // Get all the employeeDetailsList where passportNo equals to UPDATED_PASSPORT_NO
        defaultEmployeeDetailsShouldNotBeFound("passportNo.in=" + UPDATED_PASSPORT_NO);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByPassportNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where passportNo is not null
        defaultEmployeeDetailsShouldBeFound("passportNo.specified=true");

        // Get all the employeeDetailsList where passportNo is null
        defaultEmployeeDetailsShouldNotBeFound("passportNo.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByPassportNoContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where passportNo contains DEFAULT_PASSPORT_NO
        defaultEmployeeDetailsShouldBeFound("passportNo.contains=" + DEFAULT_PASSPORT_NO);

        // Get all the employeeDetailsList where passportNo contains UPDATED_PASSPORT_NO
        defaultEmployeeDetailsShouldNotBeFound("passportNo.contains=" + UPDATED_PASSPORT_NO);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByPassportNoNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where passportNo does not contain DEFAULT_PASSPORT_NO
        defaultEmployeeDetailsShouldNotBeFound("passportNo.doesNotContain=" + DEFAULT_PASSPORT_NO);

        // Get all the employeeDetailsList where passportNo does not contain UPDATED_PASSPORT_NO
        defaultEmployeeDetailsShouldBeFound("passportNo.doesNotContain=" + UPDATED_PASSPORT_NO);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByPassportExpDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where passportExpDate equals to DEFAULT_PASSPORT_EXP_DATE
        defaultEmployeeDetailsShouldBeFound("passportExpDate.equals=" + DEFAULT_PASSPORT_EXP_DATE);

        // Get all the employeeDetailsList where passportExpDate equals to UPDATED_PASSPORT_EXP_DATE
        defaultEmployeeDetailsShouldNotBeFound("passportExpDate.equals=" + UPDATED_PASSPORT_EXP_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByPassportExpDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where passportExpDate in DEFAULT_PASSPORT_EXP_DATE or UPDATED_PASSPORT_EXP_DATE
        defaultEmployeeDetailsShouldBeFound("passportExpDate.in=" + DEFAULT_PASSPORT_EXP_DATE + "," + UPDATED_PASSPORT_EXP_DATE);

        // Get all the employeeDetailsList where passportExpDate equals to UPDATED_PASSPORT_EXP_DATE
        defaultEmployeeDetailsShouldNotBeFound("passportExpDate.in=" + UPDATED_PASSPORT_EXP_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByPassportExpDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where passportExpDate is not null
        defaultEmployeeDetailsShouldBeFound("passportExpDate.specified=true");

        // Get all the employeeDetailsList where passportExpDate is null
        defaultEmployeeDetailsShouldNotBeFound("passportExpDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByTelephoneNoIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where telephoneNo equals to DEFAULT_TELEPHONE_NO
        defaultEmployeeDetailsShouldBeFound("telephoneNo.equals=" + DEFAULT_TELEPHONE_NO);

        // Get all the employeeDetailsList where telephoneNo equals to UPDATED_TELEPHONE_NO
        defaultEmployeeDetailsShouldNotBeFound("telephoneNo.equals=" + UPDATED_TELEPHONE_NO);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByTelephoneNoIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where telephoneNo in DEFAULT_TELEPHONE_NO or UPDATED_TELEPHONE_NO
        defaultEmployeeDetailsShouldBeFound("telephoneNo.in=" + DEFAULT_TELEPHONE_NO + "," + UPDATED_TELEPHONE_NO);

        // Get all the employeeDetailsList where telephoneNo equals to UPDATED_TELEPHONE_NO
        defaultEmployeeDetailsShouldNotBeFound("telephoneNo.in=" + UPDATED_TELEPHONE_NO);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByTelephoneNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where telephoneNo is not null
        defaultEmployeeDetailsShouldBeFound("telephoneNo.specified=true");

        // Get all the employeeDetailsList where telephoneNo is null
        defaultEmployeeDetailsShouldNotBeFound("telephoneNo.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByTelephoneNoContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where telephoneNo contains DEFAULT_TELEPHONE_NO
        defaultEmployeeDetailsShouldBeFound("telephoneNo.contains=" + DEFAULT_TELEPHONE_NO);

        // Get all the employeeDetailsList where telephoneNo contains UPDATED_TELEPHONE_NO
        defaultEmployeeDetailsShouldNotBeFound("telephoneNo.contains=" + UPDATED_TELEPHONE_NO);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByTelephoneNoNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where telephoneNo does not contain DEFAULT_TELEPHONE_NO
        defaultEmployeeDetailsShouldNotBeFound("telephoneNo.doesNotContain=" + DEFAULT_TELEPHONE_NO);

        // Get all the employeeDetailsList where telephoneNo does not contain UPDATED_TELEPHONE_NO
        defaultEmployeeDetailsShouldBeFound("telephoneNo.doesNotContain=" + UPDATED_TELEPHONE_NO);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNationalityIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where nationality equals to DEFAULT_NATIONALITY
        defaultEmployeeDetailsShouldBeFound("nationality.equals=" + DEFAULT_NATIONALITY);

        // Get all the employeeDetailsList where nationality equals to UPDATED_NATIONALITY
        defaultEmployeeDetailsShouldNotBeFound("nationality.equals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNationalityIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where nationality in DEFAULT_NATIONALITY or UPDATED_NATIONALITY
        defaultEmployeeDetailsShouldBeFound("nationality.in=" + DEFAULT_NATIONALITY + "," + UPDATED_NATIONALITY);

        // Get all the employeeDetailsList where nationality equals to UPDATED_NATIONALITY
        defaultEmployeeDetailsShouldNotBeFound("nationality.in=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNationalityIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where nationality is not null
        defaultEmployeeDetailsShouldBeFound("nationality.specified=true");

        // Get all the employeeDetailsList where nationality is null
        defaultEmployeeDetailsShouldNotBeFound("nationality.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNationalityContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where nationality contains DEFAULT_NATIONALITY
        defaultEmployeeDetailsShouldBeFound("nationality.contains=" + DEFAULT_NATIONALITY);

        // Get all the employeeDetailsList where nationality contains UPDATED_NATIONALITY
        defaultEmployeeDetailsShouldNotBeFound("nationality.contains=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNationalityNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where nationality does not contain DEFAULT_NATIONALITY
        defaultEmployeeDetailsShouldNotBeFound("nationality.doesNotContain=" + DEFAULT_NATIONALITY);

        // Get all the employeeDetailsList where nationality does not contain UPDATED_NATIONALITY
        defaultEmployeeDetailsShouldBeFound("nationality.doesNotContain=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMaritalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where maritalStatus equals to DEFAULT_MARITAL_STATUS
        defaultEmployeeDetailsShouldBeFound("maritalStatus.equals=" + DEFAULT_MARITAL_STATUS);

        // Get all the employeeDetailsList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultEmployeeDetailsShouldNotBeFound("maritalStatus.equals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMaritalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where maritalStatus in DEFAULT_MARITAL_STATUS or UPDATED_MARITAL_STATUS
        defaultEmployeeDetailsShouldBeFound("maritalStatus.in=" + DEFAULT_MARITAL_STATUS + "," + UPDATED_MARITAL_STATUS);

        // Get all the employeeDetailsList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultEmployeeDetailsShouldNotBeFound("maritalStatus.in=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMaritalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where maritalStatus is not null
        defaultEmployeeDetailsShouldBeFound("maritalStatus.specified=true");

        // Get all the employeeDetailsList where maritalStatus is null
        defaultEmployeeDetailsShouldNotBeFound("maritalStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByReligionIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where religion equals to DEFAULT_RELIGION
        defaultEmployeeDetailsShouldBeFound("religion.equals=" + DEFAULT_RELIGION);

        // Get all the employeeDetailsList where religion equals to UPDATED_RELIGION
        defaultEmployeeDetailsShouldNotBeFound("religion.equals=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByReligionIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where religion in DEFAULT_RELIGION or UPDATED_RELIGION
        defaultEmployeeDetailsShouldBeFound("religion.in=" + DEFAULT_RELIGION + "," + UPDATED_RELIGION);

        // Get all the employeeDetailsList where religion equals to UPDATED_RELIGION
        defaultEmployeeDetailsShouldNotBeFound("religion.in=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByReligionIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where religion is not null
        defaultEmployeeDetailsShouldBeFound("religion.specified=true");

        // Get all the employeeDetailsList where religion is null
        defaultEmployeeDetailsShouldNotBeFound("religion.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByReligionContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where religion contains DEFAULT_RELIGION
        defaultEmployeeDetailsShouldBeFound("religion.contains=" + DEFAULT_RELIGION);

        // Get all the employeeDetailsList where religion contains UPDATED_RELIGION
        defaultEmployeeDetailsShouldNotBeFound("religion.contains=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByReligionNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where religion does not contain DEFAULT_RELIGION
        defaultEmployeeDetailsShouldNotBeFound("religion.doesNotContain=" + DEFAULT_RELIGION);

        // Get all the employeeDetailsList where religion does not contain UPDATED_RELIGION
        defaultEmployeeDetailsShouldBeFound("religion.doesNotContain=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByIsSpousEmployedIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where isSpousEmployed equals to DEFAULT_IS_SPOUS_EMPLOYED
        defaultEmployeeDetailsShouldBeFound("isSpousEmployed.equals=" + DEFAULT_IS_SPOUS_EMPLOYED);

        // Get all the employeeDetailsList where isSpousEmployed equals to UPDATED_IS_SPOUS_EMPLOYED
        defaultEmployeeDetailsShouldNotBeFound("isSpousEmployed.equals=" + UPDATED_IS_SPOUS_EMPLOYED);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByIsSpousEmployedIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where isSpousEmployed in DEFAULT_IS_SPOUS_EMPLOYED or UPDATED_IS_SPOUS_EMPLOYED
        defaultEmployeeDetailsShouldBeFound("isSpousEmployed.in=" + DEFAULT_IS_SPOUS_EMPLOYED + "," + UPDATED_IS_SPOUS_EMPLOYED);

        // Get all the employeeDetailsList where isSpousEmployed equals to UPDATED_IS_SPOUS_EMPLOYED
        defaultEmployeeDetailsShouldNotBeFound("isSpousEmployed.in=" + UPDATED_IS_SPOUS_EMPLOYED);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByIsSpousEmployedIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where isSpousEmployed is not null
        defaultEmployeeDetailsShouldBeFound("isSpousEmployed.specified=true");

        // Get all the employeeDetailsList where isSpousEmployed is null
        defaultEmployeeDetailsShouldNotBeFound("isSpousEmployed.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNoOfChildrenIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where noOfChildren equals to DEFAULT_NO_OF_CHILDREN
        defaultEmployeeDetailsShouldBeFound("noOfChildren.equals=" + DEFAULT_NO_OF_CHILDREN);

        // Get all the employeeDetailsList where noOfChildren equals to UPDATED_NO_OF_CHILDREN
        defaultEmployeeDetailsShouldNotBeFound("noOfChildren.equals=" + UPDATED_NO_OF_CHILDREN);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNoOfChildrenIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where noOfChildren in DEFAULT_NO_OF_CHILDREN or UPDATED_NO_OF_CHILDREN
        defaultEmployeeDetailsShouldBeFound("noOfChildren.in=" + DEFAULT_NO_OF_CHILDREN + "," + UPDATED_NO_OF_CHILDREN);

        // Get all the employeeDetailsList where noOfChildren equals to UPDATED_NO_OF_CHILDREN
        defaultEmployeeDetailsShouldNotBeFound("noOfChildren.in=" + UPDATED_NO_OF_CHILDREN);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNoOfChildrenIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where noOfChildren is not null
        defaultEmployeeDetailsShouldBeFound("noOfChildren.specified=true");

        // Get all the employeeDetailsList where noOfChildren is null
        defaultEmployeeDetailsShouldNotBeFound("noOfChildren.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNoOfChildrenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where noOfChildren is greater than or equal to DEFAULT_NO_OF_CHILDREN
        defaultEmployeeDetailsShouldBeFound("noOfChildren.greaterThanOrEqual=" + DEFAULT_NO_OF_CHILDREN);

        // Get all the employeeDetailsList where noOfChildren is greater than or equal to UPDATED_NO_OF_CHILDREN
        defaultEmployeeDetailsShouldNotBeFound("noOfChildren.greaterThanOrEqual=" + UPDATED_NO_OF_CHILDREN);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNoOfChildrenIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where noOfChildren is less than or equal to DEFAULT_NO_OF_CHILDREN
        defaultEmployeeDetailsShouldBeFound("noOfChildren.lessThanOrEqual=" + DEFAULT_NO_OF_CHILDREN);

        // Get all the employeeDetailsList where noOfChildren is less than or equal to SMALLER_NO_OF_CHILDREN
        defaultEmployeeDetailsShouldNotBeFound("noOfChildren.lessThanOrEqual=" + SMALLER_NO_OF_CHILDREN);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNoOfChildrenIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where noOfChildren is less than DEFAULT_NO_OF_CHILDREN
        defaultEmployeeDetailsShouldNotBeFound("noOfChildren.lessThan=" + DEFAULT_NO_OF_CHILDREN);

        // Get all the employeeDetailsList where noOfChildren is less than UPDATED_NO_OF_CHILDREN
        defaultEmployeeDetailsShouldBeFound("noOfChildren.lessThan=" + UPDATED_NO_OF_CHILDREN);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNoOfChildrenIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where noOfChildren is greater than DEFAULT_NO_OF_CHILDREN
        defaultEmployeeDetailsShouldNotBeFound("noOfChildren.greaterThan=" + DEFAULT_NO_OF_CHILDREN);

        // Get all the employeeDetailsList where noOfChildren is greater than SMALLER_NO_OF_CHILDREN
        defaultEmployeeDetailsShouldBeFound("noOfChildren.greaterThan=" + SMALLER_NO_OF_CHILDREN);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdBy equals to DEFAULT_CREATED_BY
        defaultEmployeeDetailsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the employeeDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultEmployeeDetailsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultEmployeeDetailsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the employeeDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultEmployeeDetailsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdBy is not null
        defaultEmployeeDetailsShouldBeFound("createdBy.specified=true");

        // Get all the employeeDetailsList where createdBy is null
        defaultEmployeeDetailsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdBy contains DEFAULT_CREATED_BY
        defaultEmployeeDetailsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the employeeDetailsList where createdBy contains UPDATED_CREATED_BY
        defaultEmployeeDetailsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultEmployeeDetailsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the employeeDetailsList where createdBy does not contain UPDATED_CREATED_BY
        defaultEmployeeDetailsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdOn equals to DEFAULT_CREATED_ON
        defaultEmployeeDetailsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the employeeDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultEmployeeDetailsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultEmployeeDetailsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the employeeDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultEmployeeDetailsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdOn is not null
        defaultEmployeeDetailsShouldBeFound("createdOn.specified=true");

        // Get all the employeeDetailsList where createdOn is null
        defaultEmployeeDetailsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultEmployeeDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the employeeDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEmployeeDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultEmployeeDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the employeeDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEmployeeDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where lastModified is not null
        defaultEmployeeDetailsShouldBeFound("lastModified.specified=true");

        // Get all the employeeDetailsList where lastModified is null
        defaultEmployeeDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultEmployeeDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employeeDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEmployeeDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultEmployeeDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the employeeDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEmployeeDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where lastModifiedBy is not null
        defaultEmployeeDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the employeeDetailsList where lastModifiedBy is null
        defaultEmployeeDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultEmployeeDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employeeDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultEmployeeDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultEmployeeDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employeeDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultEmployeeDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where deleted equals to DEFAULT_DELETED
        defaultEmployeeDetailsShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the employeeDetailsList where deleted equals to UPDATED_DELETED
        defaultEmployeeDetailsShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultEmployeeDetailsShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the employeeDetailsList where deleted equals to UPDATED_DELETED
        defaultEmployeeDetailsShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where deleted is not null
        defaultEmployeeDetailsShouldBeFound("deleted.specified=true");

        // Get all the employeeDetailsList where deleted is null
        defaultEmployeeDetailsShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultEmployeeDetailsShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the employeeDetailsList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultEmployeeDetailsShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultEmployeeDetailsShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the employeeDetailsList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultEmployeeDetailsShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where employeeId is not null
        defaultEmployeeDetailsShouldBeFound("employeeId.specified=true");

        // Get all the employeeDetailsList where employeeId is null
        defaultEmployeeDetailsShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultEmployeeDetailsShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the employeeDetailsList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultEmployeeDetailsShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultEmployeeDetailsShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the employeeDetailsList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultEmployeeDetailsShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultEmployeeDetailsShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the employeeDetailsList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultEmployeeDetailsShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultEmployeeDetailsShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the employeeDetailsList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultEmployeeDetailsShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where age equals to DEFAULT_AGE
        defaultEmployeeDetailsShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the employeeDetailsList where age equals to UPDATED_AGE
        defaultEmployeeDetailsShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where age in DEFAULT_AGE or UPDATED_AGE
        defaultEmployeeDetailsShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the employeeDetailsList where age equals to UPDATED_AGE
        defaultEmployeeDetailsShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where age is not null
        defaultEmployeeDetailsShouldBeFound("age.specified=true");

        // Get all the employeeDetailsList where age is null
        defaultEmployeeDetailsShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where age is greater than or equal to DEFAULT_AGE
        defaultEmployeeDetailsShouldBeFound("age.greaterThanOrEqual=" + DEFAULT_AGE);

        // Get all the employeeDetailsList where age is greater than or equal to UPDATED_AGE
        defaultEmployeeDetailsShouldNotBeFound("age.greaterThanOrEqual=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where age is less than or equal to DEFAULT_AGE
        defaultEmployeeDetailsShouldBeFound("age.lessThanOrEqual=" + DEFAULT_AGE);

        // Get all the employeeDetailsList where age is less than or equal to SMALLER_AGE
        defaultEmployeeDetailsShouldNotBeFound("age.lessThanOrEqual=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAgeIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where age is less than DEFAULT_AGE
        defaultEmployeeDetailsShouldNotBeFound("age.lessThan=" + DEFAULT_AGE);

        // Get all the employeeDetailsList where age is less than UPDATED_AGE
        defaultEmployeeDetailsShouldBeFound("age.lessThan=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where age is greater than DEFAULT_AGE
        defaultEmployeeDetailsShouldNotBeFound("age.greaterThan=" + DEFAULT_AGE);

        // Get all the employeeDetailsList where age is greater than SMALLER_AGE
        defaultEmployeeDetailsShouldBeFound("age.greaterThan=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFatherNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where fatherName equals to DEFAULT_FATHER_NAME
        defaultEmployeeDetailsShouldBeFound("fatherName.equals=" + DEFAULT_FATHER_NAME);

        // Get all the employeeDetailsList where fatherName equals to UPDATED_FATHER_NAME
        defaultEmployeeDetailsShouldNotBeFound("fatherName.equals=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFatherNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where fatherName in DEFAULT_FATHER_NAME or UPDATED_FATHER_NAME
        defaultEmployeeDetailsShouldBeFound("fatherName.in=" + DEFAULT_FATHER_NAME + "," + UPDATED_FATHER_NAME);

        // Get all the employeeDetailsList where fatherName equals to UPDATED_FATHER_NAME
        defaultEmployeeDetailsShouldNotBeFound("fatherName.in=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFatherNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where fatherName is not null
        defaultEmployeeDetailsShouldBeFound("fatherName.specified=true");

        // Get all the employeeDetailsList where fatherName is null
        defaultEmployeeDetailsShouldNotBeFound("fatherName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFatherNameContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where fatherName contains DEFAULT_FATHER_NAME
        defaultEmployeeDetailsShouldBeFound("fatherName.contains=" + DEFAULT_FATHER_NAME);

        // Get all the employeeDetailsList where fatherName contains UPDATED_FATHER_NAME
        defaultEmployeeDetailsShouldNotBeFound("fatherName.contains=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFatherNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where fatherName does not contain DEFAULT_FATHER_NAME
        defaultEmployeeDetailsShouldNotBeFound("fatherName.doesNotContain=" + DEFAULT_FATHER_NAME);

        // Get all the employeeDetailsList where fatherName does not contain UPDATED_FATHER_NAME
        defaultEmployeeDetailsShouldBeFound("fatherName.doesNotContain=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMotherNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where motherName equals to DEFAULT_MOTHER_NAME
        defaultEmployeeDetailsShouldBeFound("motherName.equals=" + DEFAULT_MOTHER_NAME);

        // Get all the employeeDetailsList where motherName equals to UPDATED_MOTHER_NAME
        defaultEmployeeDetailsShouldNotBeFound("motherName.equals=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMotherNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where motherName in DEFAULT_MOTHER_NAME or UPDATED_MOTHER_NAME
        defaultEmployeeDetailsShouldBeFound("motherName.in=" + DEFAULT_MOTHER_NAME + "," + UPDATED_MOTHER_NAME);

        // Get all the employeeDetailsList where motherName equals to UPDATED_MOTHER_NAME
        defaultEmployeeDetailsShouldNotBeFound("motherName.in=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMotherNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where motherName is not null
        defaultEmployeeDetailsShouldBeFound("motherName.specified=true");

        // Get all the employeeDetailsList where motherName is null
        defaultEmployeeDetailsShouldNotBeFound("motherName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMotherNameContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where motherName contains DEFAULT_MOTHER_NAME
        defaultEmployeeDetailsShouldBeFound("motherName.contains=" + DEFAULT_MOTHER_NAME);

        // Get all the employeeDetailsList where motherName contains UPDATED_MOTHER_NAME
        defaultEmployeeDetailsShouldNotBeFound("motherName.contains=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMotherNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where motherName does not contain DEFAULT_MOTHER_NAME
        defaultEmployeeDetailsShouldNotBeFound("motherName.doesNotContain=" + DEFAULT_MOTHER_NAME);

        // Get all the employeeDetailsList where motherName does not contain UPDATED_MOTHER_NAME
        defaultEmployeeDetailsShouldBeFound("motherName.doesNotContain=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAadharNoIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where aadharNo equals to DEFAULT_AADHAR_NO
        defaultEmployeeDetailsShouldBeFound("aadharNo.equals=" + DEFAULT_AADHAR_NO);

        // Get all the employeeDetailsList where aadharNo equals to UPDATED_AADHAR_NO
        defaultEmployeeDetailsShouldNotBeFound("aadharNo.equals=" + UPDATED_AADHAR_NO);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAadharNoIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where aadharNo in DEFAULT_AADHAR_NO or UPDATED_AADHAR_NO
        defaultEmployeeDetailsShouldBeFound("aadharNo.in=" + DEFAULT_AADHAR_NO + "," + UPDATED_AADHAR_NO);

        // Get all the employeeDetailsList where aadharNo equals to UPDATED_AADHAR_NO
        defaultEmployeeDetailsShouldNotBeFound("aadharNo.in=" + UPDATED_AADHAR_NO);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAadharNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where aadharNo is not null
        defaultEmployeeDetailsShouldBeFound("aadharNo.specified=true");

        // Get all the employeeDetailsList where aadharNo is null
        defaultEmployeeDetailsShouldNotBeFound("aadharNo.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAadharNoContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where aadharNo contains DEFAULT_AADHAR_NO
        defaultEmployeeDetailsShouldBeFound("aadharNo.contains=" + DEFAULT_AADHAR_NO);

        // Get all the employeeDetailsList where aadharNo contains UPDATED_AADHAR_NO
        defaultEmployeeDetailsShouldNotBeFound("aadharNo.contains=" + UPDATED_AADHAR_NO);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAadharNoNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where aadharNo does not contain DEFAULT_AADHAR_NO
        defaultEmployeeDetailsShouldNotBeFound("aadharNo.doesNotContain=" + DEFAULT_AADHAR_NO);

        // Get all the employeeDetailsList where aadharNo does not contain UPDATED_AADHAR_NO
        defaultEmployeeDetailsShouldBeFound("aadharNo.doesNotContain=" + UPDATED_AADHAR_NO);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByBloodGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where bloodGroup equals to DEFAULT_BLOOD_GROUP
        defaultEmployeeDetailsShouldBeFound("bloodGroup.equals=" + DEFAULT_BLOOD_GROUP);

        // Get all the employeeDetailsList where bloodGroup equals to UPDATED_BLOOD_GROUP
        defaultEmployeeDetailsShouldNotBeFound("bloodGroup.equals=" + UPDATED_BLOOD_GROUP);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByBloodGroupIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where bloodGroup in DEFAULT_BLOOD_GROUP or UPDATED_BLOOD_GROUP
        defaultEmployeeDetailsShouldBeFound("bloodGroup.in=" + DEFAULT_BLOOD_GROUP + "," + UPDATED_BLOOD_GROUP);

        // Get all the employeeDetailsList where bloodGroup equals to UPDATED_BLOOD_GROUP
        defaultEmployeeDetailsShouldNotBeFound("bloodGroup.in=" + UPDATED_BLOOD_GROUP);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByBloodGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where bloodGroup is not null
        defaultEmployeeDetailsShouldBeFound("bloodGroup.specified=true");

        // Get all the employeeDetailsList where bloodGroup is null
        defaultEmployeeDetailsShouldNotBeFound("bloodGroup.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDobIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where dob equals to DEFAULT_DOB
        defaultEmployeeDetailsShouldBeFound("dob.equals=" + DEFAULT_DOB);

        // Get all the employeeDetailsList where dob equals to UPDATED_DOB
        defaultEmployeeDetailsShouldNotBeFound("dob.equals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDobIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where dob in DEFAULT_DOB or UPDATED_DOB
        defaultEmployeeDetailsShouldBeFound("dob.in=" + DEFAULT_DOB + "," + UPDATED_DOB);

        // Get all the employeeDetailsList where dob equals to UPDATED_DOB
        defaultEmployeeDetailsShouldNotBeFound("dob.in=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDobIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where dob is not null
        defaultEmployeeDetailsShouldBeFound("dob.specified=true");

        // Get all the employeeDetailsList where dob is null
        defaultEmployeeDetailsShouldNotBeFound("dob.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDobIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where dob is greater than or equal to DEFAULT_DOB
        defaultEmployeeDetailsShouldBeFound("dob.greaterThanOrEqual=" + DEFAULT_DOB);

        // Get all the employeeDetailsList where dob is greater than or equal to UPDATED_DOB
        defaultEmployeeDetailsShouldNotBeFound("dob.greaterThanOrEqual=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDobIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where dob is less than or equal to DEFAULT_DOB
        defaultEmployeeDetailsShouldBeFound("dob.lessThanOrEqual=" + DEFAULT_DOB);

        // Get all the employeeDetailsList where dob is less than or equal to SMALLER_DOB
        defaultEmployeeDetailsShouldNotBeFound("dob.lessThanOrEqual=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDobIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where dob is less than DEFAULT_DOB
        defaultEmployeeDetailsShouldNotBeFound("dob.lessThan=" + DEFAULT_DOB);

        // Get all the employeeDetailsList where dob is less than UPDATED_DOB
        defaultEmployeeDetailsShouldBeFound("dob.lessThan=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDobIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where dob is greater than DEFAULT_DOB
        defaultEmployeeDetailsShouldNotBeFound("dob.greaterThan=" + DEFAULT_DOB);

        // Get all the employeeDetailsList where dob is greater than SMALLER_DOB
        defaultEmployeeDetailsShouldBeFound("dob.greaterThan=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByExpertiseIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where expertise equals to DEFAULT_EXPERTISE
        defaultEmployeeDetailsShouldBeFound("expertise.equals=" + DEFAULT_EXPERTISE);

        // Get all the employeeDetailsList where expertise equals to UPDATED_EXPERTISE
        defaultEmployeeDetailsShouldNotBeFound("expertise.equals=" + UPDATED_EXPERTISE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByExpertiseIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where expertise in DEFAULT_EXPERTISE or UPDATED_EXPERTISE
        defaultEmployeeDetailsShouldBeFound("expertise.in=" + DEFAULT_EXPERTISE + "," + UPDATED_EXPERTISE);

        // Get all the employeeDetailsList where expertise equals to UPDATED_EXPERTISE
        defaultEmployeeDetailsShouldNotBeFound("expertise.in=" + UPDATED_EXPERTISE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByExpertiseIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where expertise is not null
        defaultEmployeeDetailsShouldBeFound("expertise.specified=true");

        // Get all the employeeDetailsList where expertise is null
        defaultEmployeeDetailsShouldNotBeFound("expertise.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByExpertiseContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where expertise contains DEFAULT_EXPERTISE
        defaultEmployeeDetailsShouldBeFound("expertise.contains=" + DEFAULT_EXPERTISE);

        // Get all the employeeDetailsList where expertise contains UPDATED_EXPERTISE
        defaultEmployeeDetailsShouldNotBeFound("expertise.contains=" + UPDATED_EXPERTISE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByExpertiseNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where expertise does not contain DEFAULT_EXPERTISE
        defaultEmployeeDetailsShouldNotBeFound("expertise.doesNotContain=" + DEFAULT_EXPERTISE);

        // Get all the employeeDetailsList where expertise does not contain UPDATED_EXPERTISE
        defaultEmployeeDetailsShouldBeFound("expertise.doesNotContain=" + UPDATED_EXPERTISE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByHobbiesIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where hobbies equals to DEFAULT_HOBBIES
        defaultEmployeeDetailsShouldBeFound("hobbies.equals=" + DEFAULT_HOBBIES);

        // Get all the employeeDetailsList where hobbies equals to UPDATED_HOBBIES
        defaultEmployeeDetailsShouldNotBeFound("hobbies.equals=" + UPDATED_HOBBIES);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByHobbiesIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where hobbies in DEFAULT_HOBBIES or UPDATED_HOBBIES
        defaultEmployeeDetailsShouldBeFound("hobbies.in=" + DEFAULT_HOBBIES + "," + UPDATED_HOBBIES);

        // Get all the employeeDetailsList where hobbies equals to UPDATED_HOBBIES
        defaultEmployeeDetailsShouldNotBeFound("hobbies.in=" + UPDATED_HOBBIES);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByHobbiesIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where hobbies is not null
        defaultEmployeeDetailsShouldBeFound("hobbies.specified=true");

        // Get all the employeeDetailsList where hobbies is null
        defaultEmployeeDetailsShouldNotBeFound("hobbies.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByHobbiesContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where hobbies contains DEFAULT_HOBBIES
        defaultEmployeeDetailsShouldBeFound("hobbies.contains=" + DEFAULT_HOBBIES);

        // Get all the employeeDetailsList where hobbies contains UPDATED_HOBBIES
        defaultEmployeeDetailsShouldNotBeFound("hobbies.contains=" + UPDATED_HOBBIES);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByHobbiesNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where hobbies does not contain DEFAULT_HOBBIES
        defaultEmployeeDetailsShouldNotBeFound("hobbies.doesNotContain=" + DEFAULT_HOBBIES);

        // Get all the employeeDetailsList where hobbies does not contain UPDATED_HOBBIES
        defaultEmployeeDetailsShouldBeFound("hobbies.doesNotContain=" + UPDATED_HOBBIES);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAreaInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where areaInterest equals to DEFAULT_AREA_INTEREST
        defaultEmployeeDetailsShouldBeFound("areaInterest.equals=" + DEFAULT_AREA_INTEREST);

        // Get all the employeeDetailsList where areaInterest equals to UPDATED_AREA_INTEREST
        defaultEmployeeDetailsShouldNotBeFound("areaInterest.equals=" + UPDATED_AREA_INTEREST);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAreaInterestIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where areaInterest in DEFAULT_AREA_INTEREST or UPDATED_AREA_INTEREST
        defaultEmployeeDetailsShouldBeFound("areaInterest.in=" + DEFAULT_AREA_INTEREST + "," + UPDATED_AREA_INTEREST);

        // Get all the employeeDetailsList where areaInterest equals to UPDATED_AREA_INTEREST
        defaultEmployeeDetailsShouldNotBeFound("areaInterest.in=" + UPDATED_AREA_INTEREST);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAreaInterestIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where areaInterest is not null
        defaultEmployeeDetailsShouldBeFound("areaInterest.specified=true");

        // Get all the employeeDetailsList where areaInterest is null
        defaultEmployeeDetailsShouldNotBeFound("areaInterest.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAreaInterestContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where areaInterest contains DEFAULT_AREA_INTEREST
        defaultEmployeeDetailsShouldBeFound("areaInterest.contains=" + DEFAULT_AREA_INTEREST);

        // Get all the employeeDetailsList where areaInterest contains UPDATED_AREA_INTEREST
        defaultEmployeeDetailsShouldNotBeFound("areaInterest.contains=" + UPDATED_AREA_INTEREST);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAreaInterestNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where areaInterest does not contain DEFAULT_AREA_INTEREST
        defaultEmployeeDetailsShouldNotBeFound("areaInterest.doesNotContain=" + DEFAULT_AREA_INTEREST);

        // Get all the employeeDetailsList where areaInterest does not contain UPDATED_AREA_INTEREST
        defaultEmployeeDetailsShouldBeFound("areaInterest.doesNotContain=" + UPDATED_AREA_INTEREST);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByLanguageKnownIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where languageKnown equals to DEFAULT_LANGUAGE_KNOWN
        defaultEmployeeDetailsShouldBeFound("languageKnown.equals=" + DEFAULT_LANGUAGE_KNOWN);

        // Get all the employeeDetailsList where languageKnown equals to UPDATED_LANGUAGE_KNOWN
        defaultEmployeeDetailsShouldNotBeFound("languageKnown.equals=" + UPDATED_LANGUAGE_KNOWN);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByLanguageKnownIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where languageKnown in DEFAULT_LANGUAGE_KNOWN or UPDATED_LANGUAGE_KNOWN
        defaultEmployeeDetailsShouldBeFound("languageKnown.in=" + DEFAULT_LANGUAGE_KNOWN + "," + UPDATED_LANGUAGE_KNOWN);

        // Get all the employeeDetailsList where languageKnown equals to UPDATED_LANGUAGE_KNOWN
        defaultEmployeeDetailsShouldNotBeFound("languageKnown.in=" + UPDATED_LANGUAGE_KNOWN);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByLanguageKnownIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where languageKnown is not null
        defaultEmployeeDetailsShouldBeFound("languageKnown.specified=true");

        // Get all the employeeDetailsList where languageKnown is null
        defaultEmployeeDetailsShouldNotBeFound("languageKnown.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByLanguageKnownContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where languageKnown contains DEFAULT_LANGUAGE_KNOWN
        defaultEmployeeDetailsShouldBeFound("languageKnown.contains=" + DEFAULT_LANGUAGE_KNOWN);

        // Get all the employeeDetailsList where languageKnown contains UPDATED_LANGUAGE_KNOWN
        defaultEmployeeDetailsShouldNotBeFound("languageKnown.contains=" + UPDATED_LANGUAGE_KNOWN);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByLanguageKnownNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where languageKnown does not contain DEFAULT_LANGUAGE_KNOWN
        defaultEmployeeDetailsShouldNotBeFound("languageKnown.doesNotContain=" + DEFAULT_LANGUAGE_KNOWN);

        // Get all the employeeDetailsList where languageKnown does not contain UPDATED_LANGUAGE_KNOWN
        defaultEmployeeDetailsShouldBeFound("languageKnown.doesNotContain=" + UPDATED_LANGUAGE_KNOWN);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where description equals to DEFAULT_DESCRIPTION
        defaultEmployeeDetailsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the employeeDetailsList where description equals to UPDATED_DESCRIPTION
        defaultEmployeeDetailsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultEmployeeDetailsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the employeeDetailsList where description equals to UPDATED_DESCRIPTION
        defaultEmployeeDetailsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where description is not null
        defaultEmployeeDetailsShouldBeFound("description.specified=true");

        // Get all the employeeDetailsList where description is null
        defaultEmployeeDetailsShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where description contains DEFAULT_DESCRIPTION
        defaultEmployeeDetailsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the employeeDetailsList where description contains UPDATED_DESCRIPTION
        defaultEmployeeDetailsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where description does not contain DEFAULT_DESCRIPTION
        defaultEmployeeDetailsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the employeeDetailsList where description does not contain UPDATED_DESCRIPTION
        defaultEmployeeDetailsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employeeDetailsRepository.saveAndFlush(employeeDetails);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeDetails.setEmployee(employee);
        employeeDetailsRepository.saveAndFlush(employeeDetails);
        Long employeeId = employee.getId();

        // Get all the employeeDetailsList where employee equals to employeeId
        defaultEmployeeDetailsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeDetailsList where employee equals to (employeeId + 1)
        defaultEmployeeDetailsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeDetailsShouldBeFound(String filter) throws Exception {
        restEmployeeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].passportNo").value(hasItem(DEFAULT_PASSPORT_NO)))
            .andExpect(jsonPath("$.[*].passportExpDate").value(hasItem(DEFAULT_PASSPORT_EXP_DATE.toString())))
            .andExpect(jsonPath("$.[*].telephoneNo").value(hasItem(DEFAULT_TELEPHONE_NO)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION)))
            .andExpect(jsonPath("$.[*].isSpousEmployed").value(hasItem(DEFAULT_IS_SPOUS_EMPLOYED.booleanValue())))
            .andExpect(jsonPath("$.[*].noOfChildren").value(hasItem(DEFAULT_NO_OF_CHILDREN.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.intValue())))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME)))
            .andExpect(jsonPath("$.[*].aadharNo").value(hasItem(DEFAULT_AADHAR_NO)))
            .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].expertise").value(hasItem(DEFAULT_EXPERTISE)))
            .andExpect(jsonPath("$.[*].hobbies").value(hasItem(DEFAULT_HOBBIES)))
            .andExpect(jsonPath("$.[*].areaInterest").value(hasItem(DEFAULT_AREA_INTEREST)))
            .andExpect(jsonPath("$.[*].languageKnown").value(hasItem(DEFAULT_LANGUAGE_KNOWN)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restEmployeeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeDetailsShouldNotBeFound(String filter) throws Exception {
        restEmployeeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeDetails() throws Exception {
        // Get the employeeDetails
        restEmployeeDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeDetails() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();

        // Update the employeeDetails
        EmployeeDetails updatedEmployeeDetails = employeeDetailsRepository.findById(employeeDetails.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeDetails are not directly saved in db
        em.detach(updatedEmployeeDetails);
        updatedEmployeeDetails
            .passportNo(UPDATED_PASSPORT_NO)
            .passportExpDate(UPDATED_PASSPORT_EXP_DATE)
            .telephoneNo(UPDATED_TELEPHONE_NO)
            .nationality(UPDATED_NATIONALITY)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .religion(UPDATED_RELIGION)
            .isSpousEmployed(UPDATED_IS_SPOUS_EMPLOYED)
            .noOfChildren(UPDATED_NO_OF_CHILDREN)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .age(UPDATED_AGE)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .aadharNo(UPDATED_AADHAR_NO)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .dob(UPDATED_DOB)
            .expertise(UPDATED_EXPERTISE)
            .hobbies(UPDATED_HOBBIES)
            .areaInterest(UPDATED_AREA_INTEREST)
            .languageKnown(UPDATED_LANGUAGE_KNOWN)
            .description(UPDATED_DESCRIPTION);
        EmployeeDetailsDTO employeeDetailsDTO = employeeDetailsMapper.toDto(updatedEmployeeDetails);

        restEmployeeDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeDetails testEmployeeDetails = employeeDetailsList.get(employeeDetailsList.size() - 1);
        assertThat(testEmployeeDetails.getPassportNo()).isEqualTo(UPDATED_PASSPORT_NO);
        assertThat(testEmployeeDetails.getPassportExpDate()).isEqualTo(UPDATED_PASSPORT_EXP_DATE);
        assertThat(testEmployeeDetails.getTelephoneNo()).isEqualTo(UPDATED_TELEPHONE_NO);
        assertThat(testEmployeeDetails.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testEmployeeDetails.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testEmployeeDetails.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testEmployeeDetails.getIsSpousEmployed()).isEqualTo(UPDATED_IS_SPOUS_EMPLOYED);
        assertThat(testEmployeeDetails.getNoOfChildren()).isEqualTo(UPDATED_NO_OF_CHILDREN);
        assertThat(testEmployeeDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployeeDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEmployeeDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEmployeeDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEmployeeDetails.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testEmployeeDetails.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEmployeeDetails.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testEmployeeDetails.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testEmployeeDetails.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testEmployeeDetails.getAadharNo()).isEqualTo(UPDATED_AADHAR_NO);
        assertThat(testEmployeeDetails.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testEmployeeDetails.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testEmployeeDetails.getExpertise()).isEqualTo(UPDATED_EXPERTISE);
        assertThat(testEmployeeDetails.getHobbies()).isEqualTo(UPDATED_HOBBIES);
        assertThat(testEmployeeDetails.getAreaInterest()).isEqualTo(UPDATED_AREA_INTEREST);
        assertThat(testEmployeeDetails.getLanguageKnown()).isEqualTo(UPDATED_LANGUAGE_KNOWN);
        assertThat(testEmployeeDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();
        employeeDetails.setId(count.incrementAndGet());

        // Create the EmployeeDetails
        EmployeeDetailsDTO employeeDetailsDTO = employeeDetailsMapper.toDto(employeeDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();
        employeeDetails.setId(count.incrementAndGet());

        // Create the EmployeeDetails
        EmployeeDetailsDTO employeeDetailsDTO = employeeDetailsMapper.toDto(employeeDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();
        employeeDetails.setId(count.incrementAndGet());

        // Create the EmployeeDetails
        EmployeeDetailsDTO employeeDetailsDTO = employeeDetailsMapper.toDto(employeeDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeDetailsWithPatch() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();

        // Update the employeeDetails using partial update
        EmployeeDetails partialUpdatedEmployeeDetails = new EmployeeDetails();
        partialUpdatedEmployeeDetails.setId(employeeDetails.getId());

        partialUpdatedEmployeeDetails
            .telephoneNo(UPDATED_TELEPHONE_NO)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .age(UPDATED_AGE)
            .description(UPDATED_DESCRIPTION);

        restEmployeeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeDetails))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeDetails testEmployeeDetails = employeeDetailsList.get(employeeDetailsList.size() - 1);
        assertThat(testEmployeeDetails.getPassportNo()).isEqualTo(DEFAULT_PASSPORT_NO);
        assertThat(testEmployeeDetails.getPassportExpDate()).isEqualTo(DEFAULT_PASSPORT_EXP_DATE);
        assertThat(testEmployeeDetails.getTelephoneNo()).isEqualTo(UPDATED_TELEPHONE_NO);
        assertThat(testEmployeeDetails.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testEmployeeDetails.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testEmployeeDetails.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testEmployeeDetails.getIsSpousEmployed()).isEqualTo(DEFAULT_IS_SPOUS_EMPLOYED);
        assertThat(testEmployeeDetails.getNoOfChildren()).isEqualTo(DEFAULT_NO_OF_CHILDREN);
        assertThat(testEmployeeDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployeeDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEmployeeDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEmployeeDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testEmployeeDetails.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testEmployeeDetails.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEmployeeDetails.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testEmployeeDetails.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testEmployeeDetails.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testEmployeeDetails.getAadharNo()).isEqualTo(DEFAULT_AADHAR_NO);
        assertThat(testEmployeeDetails.getBloodGroup()).isEqualTo(DEFAULT_BLOOD_GROUP);
        assertThat(testEmployeeDetails.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testEmployeeDetails.getExpertise()).isEqualTo(DEFAULT_EXPERTISE);
        assertThat(testEmployeeDetails.getHobbies()).isEqualTo(DEFAULT_HOBBIES);
        assertThat(testEmployeeDetails.getAreaInterest()).isEqualTo(DEFAULT_AREA_INTEREST);
        assertThat(testEmployeeDetails.getLanguageKnown()).isEqualTo(DEFAULT_LANGUAGE_KNOWN);
        assertThat(testEmployeeDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeDetailsWithPatch() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();

        // Update the employeeDetails using partial update
        EmployeeDetails partialUpdatedEmployeeDetails = new EmployeeDetails();
        partialUpdatedEmployeeDetails.setId(employeeDetails.getId());

        partialUpdatedEmployeeDetails
            .passportNo(UPDATED_PASSPORT_NO)
            .passportExpDate(UPDATED_PASSPORT_EXP_DATE)
            .telephoneNo(UPDATED_TELEPHONE_NO)
            .nationality(UPDATED_NATIONALITY)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .religion(UPDATED_RELIGION)
            .isSpousEmployed(UPDATED_IS_SPOUS_EMPLOYED)
            .noOfChildren(UPDATED_NO_OF_CHILDREN)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .age(UPDATED_AGE)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .aadharNo(UPDATED_AADHAR_NO)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .dob(UPDATED_DOB)
            .expertise(UPDATED_EXPERTISE)
            .hobbies(UPDATED_HOBBIES)
            .areaInterest(UPDATED_AREA_INTEREST)
            .languageKnown(UPDATED_LANGUAGE_KNOWN)
            .description(UPDATED_DESCRIPTION);

        restEmployeeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeDetails))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeDetails testEmployeeDetails = employeeDetailsList.get(employeeDetailsList.size() - 1);
        assertThat(testEmployeeDetails.getPassportNo()).isEqualTo(UPDATED_PASSPORT_NO);
        assertThat(testEmployeeDetails.getPassportExpDate()).isEqualTo(UPDATED_PASSPORT_EXP_DATE);
        assertThat(testEmployeeDetails.getTelephoneNo()).isEqualTo(UPDATED_TELEPHONE_NO);
        assertThat(testEmployeeDetails.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testEmployeeDetails.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testEmployeeDetails.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testEmployeeDetails.getIsSpousEmployed()).isEqualTo(UPDATED_IS_SPOUS_EMPLOYED);
        assertThat(testEmployeeDetails.getNoOfChildren()).isEqualTo(UPDATED_NO_OF_CHILDREN);
        assertThat(testEmployeeDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployeeDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEmployeeDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEmployeeDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEmployeeDetails.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testEmployeeDetails.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEmployeeDetails.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testEmployeeDetails.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testEmployeeDetails.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testEmployeeDetails.getAadharNo()).isEqualTo(UPDATED_AADHAR_NO);
        assertThat(testEmployeeDetails.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testEmployeeDetails.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testEmployeeDetails.getExpertise()).isEqualTo(UPDATED_EXPERTISE);
        assertThat(testEmployeeDetails.getHobbies()).isEqualTo(UPDATED_HOBBIES);
        assertThat(testEmployeeDetails.getAreaInterest()).isEqualTo(UPDATED_AREA_INTEREST);
        assertThat(testEmployeeDetails.getLanguageKnown()).isEqualTo(UPDATED_LANGUAGE_KNOWN);
        assertThat(testEmployeeDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();
        employeeDetails.setId(count.incrementAndGet());

        // Create the EmployeeDetails
        EmployeeDetailsDTO employeeDetailsDTO = employeeDetailsMapper.toDto(employeeDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();
        employeeDetails.setId(count.incrementAndGet());

        // Create the EmployeeDetails
        EmployeeDetailsDTO employeeDetailsDTO = employeeDetailsMapper.toDto(employeeDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();
        employeeDetails.setId(count.incrementAndGet());

        // Create the EmployeeDetails
        EmployeeDetailsDTO employeeDetailsDTO = employeeDetailsMapper.toDto(employeeDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeDetails() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        int databaseSizeBeforeDelete = employeeDetailsRepository.findAll().size();

        // Delete the employeeDetails
        restEmployeeDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
