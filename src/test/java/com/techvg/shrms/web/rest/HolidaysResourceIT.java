package com.techvg.shrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.shrms.IntegrationTest;
import com.techvg.shrms.domain.Company;
import com.techvg.shrms.domain.Holidays;
import com.techvg.shrms.repository.HolidaysRepository;
import com.techvg.shrms.service.criteria.HolidaysCriteria;
import com.techvg.shrms.service.dto.HolidaysDTO;
import com.techvg.shrms.service.mapper.HolidaysMapper;
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
 * Integration tests for the {@link HolidaysResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HolidaysResourceIT {

    private static final String DEFAULT_HOLIDAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HOLIDAY_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_HOLIDAY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HOLIDAY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DAY = "AAAAAAAAAA";
    private static final String UPDATED_DAY = "BBBBBBBBBB";

    private static final Instant DEFAULT_YEAR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_YEAR = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/holidays";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HolidaysRepository holidaysRepository;

    @Autowired
    private HolidaysMapper holidaysMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHolidaysMockMvc;

    private Holidays holidays;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Holidays createEntity(EntityManager em) {
        Holidays holidays = new Holidays()
            .holidayName(DEFAULT_HOLIDAY_NAME)
            .holidayDate(DEFAULT_HOLIDAY_DATE)
            .day(DEFAULT_DAY)
            .year(DEFAULT_YEAR)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .deleted(DEFAULT_DELETED);
        return holidays;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Holidays createUpdatedEntity(EntityManager em) {
        Holidays holidays = new Holidays()
            .holidayName(UPDATED_HOLIDAY_NAME)
            .holidayDate(UPDATED_HOLIDAY_DATE)
            .day(UPDATED_DAY)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);
        return holidays;
    }

    @BeforeEach
    public void initTest() {
        holidays = createEntity(em);
    }

    @Test
    @Transactional
    void createHolidays() throws Exception {
        int databaseSizeBeforeCreate = holidaysRepository.findAll().size();
        // Create the Holidays
        HolidaysDTO holidaysDTO = holidaysMapper.toDto(holidays);
        restHolidaysMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(holidaysDTO)))
            .andExpect(status().isCreated());

        // Validate the Holidays in the database
        List<Holidays> holidaysList = holidaysRepository.findAll();
        assertThat(holidaysList).hasSize(databaseSizeBeforeCreate + 1);
        Holidays testHolidays = holidaysList.get(holidaysList.size() - 1);
        assertThat(testHolidays.getHolidayName()).isEqualTo(DEFAULT_HOLIDAY_NAME);
        assertThat(testHolidays.getHolidayDate()).isEqualTo(DEFAULT_HOLIDAY_DATE);
        assertThat(testHolidays.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testHolidays.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testHolidays.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testHolidays.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testHolidays.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testHolidays.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testHolidays.getDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    void createHolidaysWithExistingId() throws Exception {
        // Create the Holidays with an existing ID
        holidays.setId(1L);
        HolidaysDTO holidaysDTO = holidaysMapper.toDto(holidays);

        int databaseSizeBeforeCreate = holidaysRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHolidaysMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(holidaysDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Holidays in the database
        List<Holidays> holidaysList = holidaysRepository.findAll();
        assertThat(holidaysList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkHolidayNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = holidaysRepository.findAll().size();
        // set the field null
        holidays.setHolidayName(null);

        // Create the Holidays, which fails.
        HolidaysDTO holidaysDTO = holidaysMapper.toDto(holidays);

        restHolidaysMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(holidaysDTO)))
            .andExpect(status().isBadRequest());

        List<Holidays> holidaysList = holidaysRepository.findAll();
        assertThat(holidaysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHolidays() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList
        restHolidaysMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(holidays.getId().intValue())))
            .andExpect(jsonPath("$.[*].holidayName").value(hasItem(DEFAULT_HOLIDAY_NAME)))
            .andExpect(jsonPath("$.[*].holidayDate").value(hasItem(DEFAULT_HOLIDAY_DATE.toString())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getHolidays() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get the holidays
        restHolidaysMockMvc
            .perform(get(ENTITY_API_URL_ID, holidays.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(holidays.getId().intValue()))
            .andExpect(jsonPath("$.holidayName").value(DEFAULT_HOLIDAY_NAME))
            .andExpect(jsonPath("$.holidayDate").value(DEFAULT_HOLIDAY_DATE.toString()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getHolidaysByIdFiltering() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        Long id = holidays.getId();

        defaultHolidaysShouldBeFound("id.equals=" + id);
        defaultHolidaysShouldNotBeFound("id.notEquals=" + id);

        defaultHolidaysShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHolidaysShouldNotBeFound("id.greaterThan=" + id);

        defaultHolidaysShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHolidaysShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllHolidaysByHolidayNameIsEqualToSomething() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where holidayName equals to DEFAULT_HOLIDAY_NAME
        defaultHolidaysShouldBeFound("holidayName.equals=" + DEFAULT_HOLIDAY_NAME);

        // Get all the holidaysList where holidayName equals to UPDATED_HOLIDAY_NAME
        defaultHolidaysShouldNotBeFound("holidayName.equals=" + UPDATED_HOLIDAY_NAME);
    }

    @Test
    @Transactional
    void getAllHolidaysByHolidayNameIsInShouldWork() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where holidayName in DEFAULT_HOLIDAY_NAME or UPDATED_HOLIDAY_NAME
        defaultHolidaysShouldBeFound("holidayName.in=" + DEFAULT_HOLIDAY_NAME + "," + UPDATED_HOLIDAY_NAME);

        // Get all the holidaysList where holidayName equals to UPDATED_HOLIDAY_NAME
        defaultHolidaysShouldNotBeFound("holidayName.in=" + UPDATED_HOLIDAY_NAME);
    }

    @Test
    @Transactional
    void getAllHolidaysByHolidayNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where holidayName is not null
        defaultHolidaysShouldBeFound("holidayName.specified=true");

        // Get all the holidaysList where holidayName is null
        defaultHolidaysShouldNotBeFound("holidayName.specified=false");
    }

    @Test
    @Transactional
    void getAllHolidaysByHolidayNameContainsSomething() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where holidayName contains DEFAULT_HOLIDAY_NAME
        defaultHolidaysShouldBeFound("holidayName.contains=" + DEFAULT_HOLIDAY_NAME);

        // Get all the holidaysList where holidayName contains UPDATED_HOLIDAY_NAME
        defaultHolidaysShouldNotBeFound("holidayName.contains=" + UPDATED_HOLIDAY_NAME);
    }

    @Test
    @Transactional
    void getAllHolidaysByHolidayNameNotContainsSomething() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where holidayName does not contain DEFAULT_HOLIDAY_NAME
        defaultHolidaysShouldNotBeFound("holidayName.doesNotContain=" + DEFAULT_HOLIDAY_NAME);

        // Get all the holidaysList where holidayName does not contain UPDATED_HOLIDAY_NAME
        defaultHolidaysShouldBeFound("holidayName.doesNotContain=" + UPDATED_HOLIDAY_NAME);
    }

    @Test
    @Transactional
    void getAllHolidaysByHolidayDateIsEqualToSomething() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where holidayDate equals to DEFAULT_HOLIDAY_DATE
        defaultHolidaysShouldBeFound("holidayDate.equals=" + DEFAULT_HOLIDAY_DATE);

        // Get all the holidaysList where holidayDate equals to UPDATED_HOLIDAY_DATE
        defaultHolidaysShouldNotBeFound("holidayDate.equals=" + UPDATED_HOLIDAY_DATE);
    }

    @Test
    @Transactional
    void getAllHolidaysByHolidayDateIsInShouldWork() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where holidayDate in DEFAULT_HOLIDAY_DATE or UPDATED_HOLIDAY_DATE
        defaultHolidaysShouldBeFound("holidayDate.in=" + DEFAULT_HOLIDAY_DATE + "," + UPDATED_HOLIDAY_DATE);

        // Get all the holidaysList where holidayDate equals to UPDATED_HOLIDAY_DATE
        defaultHolidaysShouldNotBeFound("holidayDate.in=" + UPDATED_HOLIDAY_DATE);
    }

    @Test
    @Transactional
    void getAllHolidaysByHolidayDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where holidayDate is not null
        defaultHolidaysShouldBeFound("holidayDate.specified=true");

        // Get all the holidaysList where holidayDate is null
        defaultHolidaysShouldNotBeFound("holidayDate.specified=false");
    }

    @Test
    @Transactional
    void getAllHolidaysByDayIsEqualToSomething() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where day equals to DEFAULT_DAY
        defaultHolidaysShouldBeFound("day.equals=" + DEFAULT_DAY);

        // Get all the holidaysList where day equals to UPDATED_DAY
        defaultHolidaysShouldNotBeFound("day.equals=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllHolidaysByDayIsInShouldWork() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where day in DEFAULT_DAY or UPDATED_DAY
        defaultHolidaysShouldBeFound("day.in=" + DEFAULT_DAY + "," + UPDATED_DAY);

        // Get all the holidaysList where day equals to UPDATED_DAY
        defaultHolidaysShouldNotBeFound("day.in=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllHolidaysByDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where day is not null
        defaultHolidaysShouldBeFound("day.specified=true");

        // Get all the holidaysList where day is null
        defaultHolidaysShouldNotBeFound("day.specified=false");
    }

    @Test
    @Transactional
    void getAllHolidaysByDayContainsSomething() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where day contains DEFAULT_DAY
        defaultHolidaysShouldBeFound("day.contains=" + DEFAULT_DAY);

        // Get all the holidaysList where day contains UPDATED_DAY
        defaultHolidaysShouldNotBeFound("day.contains=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllHolidaysByDayNotContainsSomething() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where day does not contain DEFAULT_DAY
        defaultHolidaysShouldNotBeFound("day.doesNotContain=" + DEFAULT_DAY);

        // Get all the holidaysList where day does not contain UPDATED_DAY
        defaultHolidaysShouldBeFound("day.doesNotContain=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllHolidaysByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where year equals to DEFAULT_YEAR
        defaultHolidaysShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the holidaysList where year equals to UPDATED_YEAR
        defaultHolidaysShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllHolidaysByYearIsInShouldWork() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultHolidaysShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the holidaysList where year equals to UPDATED_YEAR
        defaultHolidaysShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllHolidaysByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where year is not null
        defaultHolidaysShouldBeFound("year.specified=true");

        // Get all the holidaysList where year is null
        defaultHolidaysShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllHolidaysByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultHolidaysShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the holidaysList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultHolidaysShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllHolidaysByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultHolidaysShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the holidaysList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultHolidaysShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllHolidaysByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where lastModified is not null
        defaultHolidaysShouldBeFound("lastModified.specified=true");

        // Get all the holidaysList where lastModified is null
        defaultHolidaysShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllHolidaysByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultHolidaysShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the holidaysList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultHolidaysShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllHolidaysByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultHolidaysShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the holidaysList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultHolidaysShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllHolidaysByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where lastModifiedBy is not null
        defaultHolidaysShouldBeFound("lastModifiedBy.specified=true");

        // Get all the holidaysList where lastModifiedBy is null
        defaultHolidaysShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllHolidaysByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultHolidaysShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the holidaysList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultHolidaysShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllHolidaysByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultHolidaysShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the holidaysList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultHolidaysShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllHolidaysByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where createdBy equals to DEFAULT_CREATED_BY
        defaultHolidaysShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the holidaysList where createdBy equals to UPDATED_CREATED_BY
        defaultHolidaysShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllHolidaysByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultHolidaysShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the holidaysList where createdBy equals to UPDATED_CREATED_BY
        defaultHolidaysShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllHolidaysByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where createdBy is not null
        defaultHolidaysShouldBeFound("createdBy.specified=true");

        // Get all the holidaysList where createdBy is null
        defaultHolidaysShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllHolidaysByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where createdBy contains DEFAULT_CREATED_BY
        defaultHolidaysShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the holidaysList where createdBy contains UPDATED_CREATED_BY
        defaultHolidaysShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllHolidaysByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where createdBy does not contain DEFAULT_CREATED_BY
        defaultHolidaysShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the holidaysList where createdBy does not contain UPDATED_CREATED_BY
        defaultHolidaysShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllHolidaysByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where createdOn equals to DEFAULT_CREATED_ON
        defaultHolidaysShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the holidaysList where createdOn equals to UPDATED_CREATED_ON
        defaultHolidaysShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllHolidaysByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultHolidaysShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the holidaysList where createdOn equals to UPDATED_CREATED_ON
        defaultHolidaysShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllHolidaysByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where createdOn is not null
        defaultHolidaysShouldBeFound("createdOn.specified=true");

        // Get all the holidaysList where createdOn is null
        defaultHolidaysShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllHolidaysByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where deleted equals to DEFAULT_DELETED
        defaultHolidaysShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the holidaysList where deleted equals to UPDATED_DELETED
        defaultHolidaysShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllHolidaysByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultHolidaysShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the holidaysList where deleted equals to UPDATED_DELETED
        defaultHolidaysShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllHolidaysByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList where deleted is not null
        defaultHolidaysShouldBeFound("deleted.specified=true");

        // Get all the holidaysList where deleted is null
        defaultHolidaysShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllHolidaysByCompanyIsEqualToSomething() throws Exception {
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            holidaysRepository.saveAndFlush(holidays);
            company = CompanyResourceIT.createEntity(em);
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(company);
        em.flush();
        holidays.setCompany(company);
        holidaysRepository.saveAndFlush(holidays);
        Long companyId = company.getId();

        // Get all the holidaysList where company equals to companyId
        defaultHolidaysShouldBeFound("companyId.equals=" + companyId);

        // Get all the holidaysList where company equals to (companyId + 1)
        defaultHolidaysShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHolidaysShouldBeFound(String filter) throws Exception {
        restHolidaysMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(holidays.getId().intValue())))
            .andExpect(jsonPath("$.[*].holidayName").value(hasItem(DEFAULT_HOLIDAY_NAME)))
            .andExpect(jsonPath("$.[*].holidayDate").value(hasItem(DEFAULT_HOLIDAY_DATE.toString())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));

        // Check, that the count call also returns 1
        restHolidaysMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHolidaysShouldNotBeFound(String filter) throws Exception {
        restHolidaysMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHolidaysMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingHolidays() throws Exception {
        // Get the holidays
        restHolidaysMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHolidays() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        int databaseSizeBeforeUpdate = holidaysRepository.findAll().size();

        // Update the holidays
        Holidays updatedHolidays = holidaysRepository.findById(holidays.getId()).get();
        // Disconnect from session so that the updates on updatedHolidays are not directly saved in db
        em.detach(updatedHolidays);
        updatedHolidays
            .holidayName(UPDATED_HOLIDAY_NAME)
            .holidayDate(UPDATED_HOLIDAY_DATE)
            .day(UPDATED_DAY)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);
        HolidaysDTO holidaysDTO = holidaysMapper.toDto(updatedHolidays);

        restHolidaysMockMvc
            .perform(
                put(ENTITY_API_URL_ID, holidaysDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(holidaysDTO))
            )
            .andExpect(status().isOk());

        // Validate the Holidays in the database
        List<Holidays> holidaysList = holidaysRepository.findAll();
        assertThat(holidaysList).hasSize(databaseSizeBeforeUpdate);
        Holidays testHolidays = holidaysList.get(holidaysList.size() - 1);
        assertThat(testHolidays.getHolidayName()).isEqualTo(UPDATED_HOLIDAY_NAME);
        assertThat(testHolidays.getHolidayDate()).isEqualTo(UPDATED_HOLIDAY_DATE);
        assertThat(testHolidays.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testHolidays.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testHolidays.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testHolidays.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testHolidays.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testHolidays.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testHolidays.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingHolidays() throws Exception {
        int databaseSizeBeforeUpdate = holidaysRepository.findAll().size();
        holidays.setId(count.incrementAndGet());

        // Create the Holidays
        HolidaysDTO holidaysDTO = holidaysMapper.toDto(holidays);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHolidaysMockMvc
            .perform(
                put(ENTITY_API_URL_ID, holidaysDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(holidaysDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Holidays in the database
        List<Holidays> holidaysList = holidaysRepository.findAll();
        assertThat(holidaysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHolidays() throws Exception {
        int databaseSizeBeforeUpdate = holidaysRepository.findAll().size();
        holidays.setId(count.incrementAndGet());

        // Create the Holidays
        HolidaysDTO holidaysDTO = holidaysMapper.toDto(holidays);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHolidaysMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(holidaysDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Holidays in the database
        List<Holidays> holidaysList = holidaysRepository.findAll();
        assertThat(holidaysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHolidays() throws Exception {
        int databaseSizeBeforeUpdate = holidaysRepository.findAll().size();
        holidays.setId(count.incrementAndGet());

        // Create the Holidays
        HolidaysDTO holidaysDTO = holidaysMapper.toDto(holidays);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHolidaysMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(holidaysDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Holidays in the database
        List<Holidays> holidaysList = holidaysRepository.findAll();
        assertThat(holidaysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHolidaysWithPatch() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        int databaseSizeBeforeUpdate = holidaysRepository.findAll().size();

        // Update the holidays using partial update
        Holidays partialUpdatedHolidays = new Holidays();
        partialUpdatedHolidays.setId(holidays.getId());

        partialUpdatedHolidays
            .holidayName(UPDATED_HOLIDAY_NAME)
            .day(UPDATED_DAY)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdOn(UPDATED_CREATED_ON);

        restHolidaysMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHolidays.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHolidays))
            )
            .andExpect(status().isOk());

        // Validate the Holidays in the database
        List<Holidays> holidaysList = holidaysRepository.findAll();
        assertThat(holidaysList).hasSize(databaseSizeBeforeUpdate);
        Holidays testHolidays = holidaysList.get(holidaysList.size() - 1);
        assertThat(testHolidays.getHolidayName()).isEqualTo(UPDATED_HOLIDAY_NAME);
        assertThat(testHolidays.getHolidayDate()).isEqualTo(DEFAULT_HOLIDAY_DATE);
        assertThat(testHolidays.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testHolidays.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testHolidays.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testHolidays.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testHolidays.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testHolidays.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testHolidays.getDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateHolidaysWithPatch() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        int databaseSizeBeforeUpdate = holidaysRepository.findAll().size();

        // Update the holidays using partial update
        Holidays partialUpdatedHolidays = new Holidays();
        partialUpdatedHolidays.setId(holidays.getId());

        partialUpdatedHolidays
            .holidayName(UPDATED_HOLIDAY_NAME)
            .holidayDate(UPDATED_HOLIDAY_DATE)
            .day(UPDATED_DAY)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED);

        restHolidaysMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHolidays.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHolidays))
            )
            .andExpect(status().isOk());

        // Validate the Holidays in the database
        List<Holidays> holidaysList = holidaysRepository.findAll();
        assertThat(holidaysList).hasSize(databaseSizeBeforeUpdate);
        Holidays testHolidays = holidaysList.get(holidaysList.size() - 1);
        assertThat(testHolidays.getHolidayName()).isEqualTo(UPDATED_HOLIDAY_NAME);
        assertThat(testHolidays.getHolidayDate()).isEqualTo(UPDATED_HOLIDAY_DATE);
        assertThat(testHolidays.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testHolidays.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testHolidays.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testHolidays.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testHolidays.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testHolidays.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testHolidays.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingHolidays() throws Exception {
        int databaseSizeBeforeUpdate = holidaysRepository.findAll().size();
        holidays.setId(count.incrementAndGet());

        // Create the Holidays
        HolidaysDTO holidaysDTO = holidaysMapper.toDto(holidays);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHolidaysMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, holidaysDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(holidaysDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Holidays in the database
        List<Holidays> holidaysList = holidaysRepository.findAll();
        assertThat(holidaysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHolidays() throws Exception {
        int databaseSizeBeforeUpdate = holidaysRepository.findAll().size();
        holidays.setId(count.incrementAndGet());

        // Create the Holidays
        HolidaysDTO holidaysDTO = holidaysMapper.toDto(holidays);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHolidaysMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(holidaysDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Holidays in the database
        List<Holidays> holidaysList = holidaysRepository.findAll();
        assertThat(holidaysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHolidays() throws Exception {
        int databaseSizeBeforeUpdate = holidaysRepository.findAll().size();
        holidays.setId(count.incrementAndGet());

        // Create the Holidays
        HolidaysDTO holidaysDTO = holidaysMapper.toDto(holidays);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHolidaysMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(holidaysDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Holidays in the database
        List<Holidays> holidaysList = holidaysRepository.findAll();
        assertThat(holidaysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHolidays() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        int databaseSizeBeforeDelete = holidaysRepository.findAll().size();

        // Delete the holidays
        restHolidaysMockMvc
            .perform(delete(ENTITY_API_URL_ID, holidays.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Holidays> holidaysList = holidaysRepository.findAll();
        assertThat(holidaysList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
