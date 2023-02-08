package com.techvg.shrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.shrms.IntegrationTest;
import com.techvg.shrms.domain.Education;
import com.techvg.shrms.domain.Employee;
import com.techvg.shrms.domain.enumeration.Degree;
import com.techvg.shrms.repository.EducationRepository;
import com.techvg.shrms.service.criteria.EducationCriteria;
import com.techvg.shrms.service.dto.EducationDTO;
import com.techvg.shrms.service.mapper.EducationMapper;
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
 * Integration tests for the {@link EducationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EducationResourceIT {

    private static final String DEFAULT_INSTITUTION = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUTION = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Degree DEFAULT_DEGREE = Degree.PG;
    private static final Degree UPDATED_DEGREE = Degree.GRADUATION;

    private static final String DEFAULT_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_GRADE = "BBBBBBBBBB";

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

    private static final Long DEFAULT_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_EMPLOYEE_ID = 2L;
    private static final Long SMALLER_EMPLOYEE_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/educations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private EducationMapper educationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEducationMockMvc;

    private Education education;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Education createEntity(EntityManager em) {
        Education education = new Education()
            .institution(DEFAULT_INSTITUTION)
            .subject(DEFAULT_SUBJECT)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .degree(DEFAULT_DEGREE)
            .grade(DEFAULT_GRADE)
            .description(DEFAULT_DESCRIPTION)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .deleted(DEFAULT_DELETED)
            .employeeId(DEFAULT_EMPLOYEE_ID);
        return education;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Education createUpdatedEntity(EntityManager em) {
        Education education = new Education()
            .institution(UPDATED_INSTITUTION)
            .subject(UPDATED_SUBJECT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .degree(UPDATED_DEGREE)
            .grade(UPDATED_GRADE)
            .description(UPDATED_DESCRIPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID);
        return education;
    }

    @BeforeEach
    public void initTest() {
        education = createEntity(em);
    }

    @Test
    @Transactional
    void createEducation() throws Exception {
        int databaseSizeBeforeCreate = educationRepository.findAll().size();
        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);
        restEducationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isCreated());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeCreate + 1);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getInstitution()).isEqualTo(DEFAULT_INSTITUTION);
        assertThat(testEducation.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testEducation.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEducation.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEducation.getDegree()).isEqualTo(DEFAULT_DEGREE);
        assertThat(testEducation.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testEducation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEducation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testEducation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testEducation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEducation.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEducation.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testEducation.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void createEducationWithExistingId() throws Exception {
        // Create the Education with an existing ID
        education.setId(1L);
        EducationDTO educationDTO = educationMapper.toDto(education);

        int databaseSizeBeforeCreate = educationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEducations() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(education.getId().intValue())))
            .andExpect(jsonPath("$.[*].institution").value(hasItem(DEFAULT_INSTITUTION)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE.toString())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())));
    }

    @Test
    @Transactional
    void getEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get the education
        restEducationMockMvc
            .perform(get(ENTITY_API_URL_ID, education.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(education.getId().intValue()))
            .andExpect(jsonPath("$.institution").value(DEFAULT_INSTITUTION))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE.toString()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()));
    }

    @Test
    @Transactional
    void getEducationsByIdFiltering() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        Long id = education.getId();

        defaultEducationShouldBeFound("id.equals=" + id);
        defaultEducationShouldNotBeFound("id.notEquals=" + id);

        defaultEducationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEducationShouldNotBeFound("id.greaterThan=" + id);

        defaultEducationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEducationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEducationsByInstitutionIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where institution equals to DEFAULT_INSTITUTION
        defaultEducationShouldBeFound("institution.equals=" + DEFAULT_INSTITUTION);

        // Get all the educationList where institution equals to UPDATED_INSTITUTION
        defaultEducationShouldNotBeFound("institution.equals=" + UPDATED_INSTITUTION);
    }

    @Test
    @Transactional
    void getAllEducationsByInstitutionIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where institution in DEFAULT_INSTITUTION or UPDATED_INSTITUTION
        defaultEducationShouldBeFound("institution.in=" + DEFAULT_INSTITUTION + "," + UPDATED_INSTITUTION);

        // Get all the educationList where institution equals to UPDATED_INSTITUTION
        defaultEducationShouldNotBeFound("institution.in=" + UPDATED_INSTITUTION);
    }

    @Test
    @Transactional
    void getAllEducationsByInstitutionIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where institution is not null
        defaultEducationShouldBeFound("institution.specified=true");

        // Get all the educationList where institution is null
        defaultEducationShouldNotBeFound("institution.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByInstitutionContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where institution contains DEFAULT_INSTITUTION
        defaultEducationShouldBeFound("institution.contains=" + DEFAULT_INSTITUTION);

        // Get all the educationList where institution contains UPDATED_INSTITUTION
        defaultEducationShouldNotBeFound("institution.contains=" + UPDATED_INSTITUTION);
    }

    @Test
    @Transactional
    void getAllEducationsByInstitutionNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where institution does not contain DEFAULT_INSTITUTION
        defaultEducationShouldNotBeFound("institution.doesNotContain=" + DEFAULT_INSTITUTION);

        // Get all the educationList where institution does not contain UPDATED_INSTITUTION
        defaultEducationShouldBeFound("institution.doesNotContain=" + UPDATED_INSTITUTION);
    }

    @Test
    @Transactional
    void getAllEducationsBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where subject equals to DEFAULT_SUBJECT
        defaultEducationShouldBeFound("subject.equals=" + DEFAULT_SUBJECT);

        // Get all the educationList where subject equals to UPDATED_SUBJECT
        defaultEducationShouldNotBeFound("subject.equals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllEducationsBySubjectIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where subject in DEFAULT_SUBJECT or UPDATED_SUBJECT
        defaultEducationShouldBeFound("subject.in=" + DEFAULT_SUBJECT + "," + UPDATED_SUBJECT);

        // Get all the educationList where subject equals to UPDATED_SUBJECT
        defaultEducationShouldNotBeFound("subject.in=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllEducationsBySubjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where subject is not null
        defaultEducationShouldBeFound("subject.specified=true");

        // Get all the educationList where subject is null
        defaultEducationShouldNotBeFound("subject.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsBySubjectContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where subject contains DEFAULT_SUBJECT
        defaultEducationShouldBeFound("subject.contains=" + DEFAULT_SUBJECT);

        // Get all the educationList where subject contains UPDATED_SUBJECT
        defaultEducationShouldNotBeFound("subject.contains=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllEducationsBySubjectNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where subject does not contain DEFAULT_SUBJECT
        defaultEducationShouldNotBeFound("subject.doesNotContain=" + DEFAULT_SUBJECT);

        // Get all the educationList where subject does not contain UPDATED_SUBJECT
        defaultEducationShouldBeFound("subject.doesNotContain=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllEducationsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startDate equals to DEFAULT_START_DATE
        defaultEducationShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the educationList where startDate equals to UPDATED_START_DATE
        defaultEducationShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultEducationShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the educationList where startDate equals to UPDATED_START_DATE
        defaultEducationShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startDate is not null
        defaultEducationShouldBeFound("startDate.specified=true");

        // Get all the educationList where startDate is null
        defaultEducationShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate equals to DEFAULT_END_DATE
        defaultEducationShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the educationList where endDate equals to UPDATED_END_DATE
        defaultEducationShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultEducationShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the educationList where endDate equals to UPDATED_END_DATE
        defaultEducationShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate is not null
        defaultEducationShouldBeFound("endDate.specified=true");

        // Get all the educationList where endDate is null
        defaultEducationShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByDegreeIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where degree equals to DEFAULT_DEGREE
        defaultEducationShouldBeFound("degree.equals=" + DEFAULT_DEGREE);

        // Get all the educationList where degree equals to UPDATED_DEGREE
        defaultEducationShouldNotBeFound("degree.equals=" + UPDATED_DEGREE);
    }

    @Test
    @Transactional
    void getAllEducationsByDegreeIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where degree in DEFAULT_DEGREE or UPDATED_DEGREE
        defaultEducationShouldBeFound("degree.in=" + DEFAULT_DEGREE + "," + UPDATED_DEGREE);

        // Get all the educationList where degree equals to UPDATED_DEGREE
        defaultEducationShouldNotBeFound("degree.in=" + UPDATED_DEGREE);
    }

    @Test
    @Transactional
    void getAllEducationsByDegreeIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where degree is not null
        defaultEducationShouldBeFound("degree.specified=true");

        // Get all the educationList where degree is null
        defaultEducationShouldNotBeFound("degree.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where grade equals to DEFAULT_GRADE
        defaultEducationShouldBeFound("grade.equals=" + DEFAULT_GRADE);

        // Get all the educationList where grade equals to UPDATED_GRADE
        defaultEducationShouldNotBeFound("grade.equals=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEducationsByGradeIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where grade in DEFAULT_GRADE or UPDATED_GRADE
        defaultEducationShouldBeFound("grade.in=" + DEFAULT_GRADE + "," + UPDATED_GRADE);

        // Get all the educationList where grade equals to UPDATED_GRADE
        defaultEducationShouldNotBeFound("grade.in=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEducationsByGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where grade is not null
        defaultEducationShouldBeFound("grade.specified=true");

        // Get all the educationList where grade is null
        defaultEducationShouldNotBeFound("grade.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByGradeContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where grade contains DEFAULT_GRADE
        defaultEducationShouldBeFound("grade.contains=" + DEFAULT_GRADE);

        // Get all the educationList where grade contains UPDATED_GRADE
        defaultEducationShouldNotBeFound("grade.contains=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEducationsByGradeNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where grade does not contain DEFAULT_GRADE
        defaultEducationShouldNotBeFound("grade.doesNotContain=" + DEFAULT_GRADE);

        // Get all the educationList where grade does not contain UPDATED_GRADE
        defaultEducationShouldBeFound("grade.doesNotContain=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEducationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where description equals to DEFAULT_DESCRIPTION
        defaultEducationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the educationList where description equals to UPDATED_DESCRIPTION
        defaultEducationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEducationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultEducationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the educationList where description equals to UPDATED_DESCRIPTION
        defaultEducationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEducationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where description is not null
        defaultEducationShouldBeFound("description.specified=true");

        // Get all the educationList where description is null
        defaultEducationShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where description contains DEFAULT_DESCRIPTION
        defaultEducationShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the educationList where description contains UPDATED_DESCRIPTION
        defaultEducationShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEducationsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where description does not contain DEFAULT_DESCRIPTION
        defaultEducationShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the educationList where description does not contain UPDATED_DESCRIPTION
        defaultEducationShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultEducationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the educationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEducationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultEducationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the educationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEducationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModified is not null
        defaultEducationShouldBeFound("lastModified.specified=true");

        // Get all the educationList where lastModified is null
        defaultEducationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultEducationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the educationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEducationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultEducationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the educationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEducationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModifiedBy is not null
        defaultEducationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the educationList where lastModifiedBy is null
        defaultEducationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultEducationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the educationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultEducationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultEducationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the educationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultEducationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where createdBy equals to DEFAULT_CREATED_BY
        defaultEducationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the educationList where createdBy equals to UPDATED_CREATED_BY
        defaultEducationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultEducationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the educationList where createdBy equals to UPDATED_CREATED_BY
        defaultEducationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where createdBy is not null
        defaultEducationShouldBeFound("createdBy.specified=true");

        // Get all the educationList where createdBy is null
        defaultEducationShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where createdBy contains DEFAULT_CREATED_BY
        defaultEducationShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the educationList where createdBy contains UPDATED_CREATED_BY
        defaultEducationShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where createdBy does not contain DEFAULT_CREATED_BY
        defaultEducationShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the educationList where createdBy does not contain UPDATED_CREATED_BY
        defaultEducationShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where createdOn equals to DEFAULT_CREATED_ON
        defaultEducationShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the educationList where createdOn equals to UPDATED_CREATED_ON
        defaultEducationShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllEducationsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultEducationShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the educationList where createdOn equals to UPDATED_CREATED_ON
        defaultEducationShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllEducationsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where createdOn is not null
        defaultEducationShouldBeFound("createdOn.specified=true");

        // Get all the educationList where createdOn is null
        defaultEducationShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where deleted equals to DEFAULT_DELETED
        defaultEducationShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the educationList where deleted equals to UPDATED_DELETED
        defaultEducationShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllEducationsByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultEducationShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the educationList where deleted equals to UPDATED_DELETED
        defaultEducationShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllEducationsByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where deleted is not null
        defaultEducationShouldBeFound("deleted.specified=true");

        // Get all the educationList where deleted is null
        defaultEducationShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultEducationShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the educationList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultEducationShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEducationsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultEducationShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the educationList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultEducationShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEducationsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where employeeId is not null
        defaultEducationShouldBeFound("employeeId.specified=true");

        // Get all the educationList where employeeId is null
        defaultEducationShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultEducationShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the educationList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultEducationShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEducationsByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultEducationShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the educationList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultEducationShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEducationsByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultEducationShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the educationList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultEducationShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEducationsByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultEducationShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the educationList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultEducationShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEducationsByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            educationRepository.saveAndFlush(education);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        education.setEmployee(employee);
        educationRepository.saveAndFlush(education);
        Long employeeId = employee.getId();

        // Get all the educationList where employee equals to employeeId
        defaultEducationShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the educationList where employee equals to (employeeId + 1)
        defaultEducationShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEducationShouldBeFound(String filter) throws Exception {
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(education.getId().intValue())))
            .andExpect(jsonPath("$.[*].institution").value(hasItem(DEFAULT_INSTITUTION)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE.toString())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())));

        // Check, that the count call also returns 1
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEducationShouldNotBeFound(String filter) throws Exception {
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEducation() throws Exception {
        // Get the education
        restEducationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education
        Education updatedEducation = educationRepository.findById(education.getId()).get();
        // Disconnect from session so that the updates on updatedEducation are not directly saved in db
        em.detach(updatedEducation);
        updatedEducation
            .institution(UPDATED_INSTITUTION)
            .subject(UPDATED_SUBJECT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .degree(UPDATED_DEGREE)
            .grade(UPDATED_GRADE)
            .description(UPDATED_DESCRIPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID);
        EducationDTO educationDTO = educationMapper.toDto(updatedEducation);

        restEducationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, educationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getInstitution()).isEqualTo(UPDATED_INSTITUTION);
        assertThat(testEducation.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEducation.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEducation.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEducation.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testEducation.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEducation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEducation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEducation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEducation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEducation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEducation.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testEducation.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void putNonExistingEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(count.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, educationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(count.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(count.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEducationWithPatch() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education using partial update
        Education partialUpdatedEducation = new Education();
        partialUpdatedEducation.setId(education.getId());

        partialUpdatedEducation
            .endDate(UPDATED_END_DATE)
            .degree(UPDATED_DEGREE)
            .description(UPDATED_DESCRIPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .employeeId(UPDATED_EMPLOYEE_ID);

        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducation))
            )
            .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getInstitution()).isEqualTo(DEFAULT_INSTITUTION);
        assertThat(testEducation.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testEducation.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEducation.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEducation.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testEducation.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testEducation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEducation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEducation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testEducation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEducation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEducation.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testEducation.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void fullUpdateEducationWithPatch() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education using partial update
        Education partialUpdatedEducation = new Education();
        partialUpdatedEducation.setId(education.getId());

        partialUpdatedEducation
            .institution(UPDATED_INSTITUTION)
            .subject(UPDATED_SUBJECT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .degree(UPDATED_DEGREE)
            .grade(UPDATED_GRADE)
            .description(UPDATED_DESCRIPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID);

        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducation))
            )
            .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getInstitution()).isEqualTo(UPDATED_INSTITUTION);
        assertThat(testEducation.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEducation.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEducation.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEducation.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testEducation.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEducation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEducation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEducation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEducation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEducation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEducation.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testEducation.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(count.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, educationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(count.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(count.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeDelete = educationRepository.findAll().size();

        // Delete the education
        restEducationMockMvc
            .perform(delete(ENTITY_API_URL_ID, education.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
