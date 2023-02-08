package com.techvg.shrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.shrms.IntegrationTest;
import com.techvg.shrms.domain.Address;
import com.techvg.shrms.domain.City;
import com.techvg.shrms.domain.District;
import com.techvg.shrms.domain.Employee;
import com.techvg.shrms.domain.State;
import com.techvg.shrms.domain.Taluka;
import com.techvg.shrms.domain.enumeration.AddressType;
import com.techvg.shrms.repository.AddressRepository;
import com.techvg.shrms.service.criteria.AddressCriteria;
import com.techvg.shrms.service.dto.AddressDTO;
import com.techvg.shrms.service.mapper.AddressMapper;
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
 * Integration tests for the {@link AddressResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AddressResourceIT {

    private static final AddressType DEFAULT_TYPE = AddressType.CURRENT_ADDRESS;
    private static final AddressType UPDATED_TYPE = AddressType.PERMANENT_ADDRESS;

    private static final String DEFAULT_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_PINCODE = "AAAAAAAAAA";
    private static final String UPDATED_PINCODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_PREFERED = false;
    private static final Boolean UPDATED_HAS_PREFERED = true;

    private static final String DEFAULT_LAND_MARK = "AAAAAAAAAA";
    private static final String UPDATED_LAND_MARK = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddressMockMvc;

    private Address address;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createEntity(EntityManager em) {
        Address address = new Address()
            .type(DEFAULT_TYPE)
            .line1(DEFAULT_LINE_1)
            .line2(DEFAULT_LINE_2)
            .country(DEFAULT_COUNTRY)
            .state(DEFAULT_STATE)
            .pincode(DEFAULT_PINCODE)
            .hasPrefered(DEFAULT_HAS_PREFERED)
            .landMark(DEFAULT_LAND_MARK)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .deleted(DEFAULT_DELETED)
            .employeeId(DEFAULT_EMPLOYEE_ID);
        return address;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createUpdatedEntity(EntityManager em) {
        Address address = new Address()
            .type(UPDATED_TYPE)
            .line1(UPDATED_LINE_1)
            .line2(UPDATED_LINE_2)
            .country(UPDATED_COUNTRY)
            .state(UPDATED_STATE)
            .pincode(UPDATED_PINCODE)
            .hasPrefered(UPDATED_HAS_PREFERED)
            .landMark(UPDATED_LAND_MARK)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID);
        return address;
    }

    @BeforeEach
    public void initTest() {
        address = createEntity(em);
    }

    @Test
    @Transactional
    void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();
        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);
        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAddress.getLine1()).isEqualTo(DEFAULT_LINE_1);
        assertThat(testAddress.getLine2()).isEqualTo(DEFAULT_LINE_2);
        assertThat(testAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testAddress.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testAddress.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testAddress.getHasPrefered()).isEqualTo(DEFAULT_HAS_PREFERED);
        assertThat(testAddress.getLandMark()).isEqualTo(DEFAULT_LAND_MARK);
        assertThat(testAddress.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAddress.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testAddress.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAddress.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAddress.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testAddress.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void createAddressWithExistingId() throws Exception {
        // Create the Address with an existing ID
        address.setId(1L);
        AddressDTO addressDTO = addressMapper.toDto(address);

        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAddresses() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].line1").value(hasItem(DEFAULT_LINE_1)))
            .andExpect(jsonPath("$.[*].line2").value(hasItem(DEFAULT_LINE_2)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].hasPrefered").value(hasItem(DEFAULT_HAS_PREFERED.booleanValue())))
            .andExpect(jsonPath("$.[*].landMark").value(hasItem(DEFAULT_LAND_MARK)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())));
    }

    @Test
    @Transactional
    void getAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get the address
        restAddressMockMvc
            .perform(get(ENTITY_API_URL_ID, address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(address.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.line1").value(DEFAULT_LINE_1))
            .andExpect(jsonPath("$.line2").value(DEFAULT_LINE_2))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE))
            .andExpect(jsonPath("$.hasPrefered").value(DEFAULT_HAS_PREFERED.booleanValue()))
            .andExpect(jsonPath("$.landMark").value(DEFAULT_LAND_MARK))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()));
    }

    @Test
    @Transactional
    void getAddressesByIdFiltering() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        Long id = address.getId();

        defaultAddressShouldBeFound("id.equals=" + id);
        defaultAddressShouldNotBeFound("id.notEquals=" + id);

        defaultAddressShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAddressShouldNotBeFound("id.greaterThan=" + id);

        defaultAddressShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAddressShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAddressesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where type equals to DEFAULT_TYPE
        defaultAddressShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the addressList where type equals to UPDATED_TYPE
        defaultAddressShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAddressesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultAddressShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the addressList where type equals to UPDATED_TYPE
        defaultAddressShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAddressesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where type is not null
        defaultAddressShouldBeFound("type.specified=true");

        // Get all the addressList where type is null
        defaultAddressShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLine1IsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where line1 equals to DEFAULT_LINE_1
        defaultAddressShouldBeFound("line1.equals=" + DEFAULT_LINE_1);

        // Get all the addressList where line1 equals to UPDATED_LINE_1
        defaultAddressShouldNotBeFound("line1.equals=" + UPDATED_LINE_1);
    }

    @Test
    @Transactional
    void getAllAddressesByLine1IsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where line1 in DEFAULT_LINE_1 or UPDATED_LINE_1
        defaultAddressShouldBeFound("line1.in=" + DEFAULT_LINE_1 + "," + UPDATED_LINE_1);

        // Get all the addressList where line1 equals to UPDATED_LINE_1
        defaultAddressShouldNotBeFound("line1.in=" + UPDATED_LINE_1);
    }

    @Test
    @Transactional
    void getAllAddressesByLine1IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where line1 is not null
        defaultAddressShouldBeFound("line1.specified=true");

        // Get all the addressList where line1 is null
        defaultAddressShouldNotBeFound("line1.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLine1ContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where line1 contains DEFAULT_LINE_1
        defaultAddressShouldBeFound("line1.contains=" + DEFAULT_LINE_1);

        // Get all the addressList where line1 contains UPDATED_LINE_1
        defaultAddressShouldNotBeFound("line1.contains=" + UPDATED_LINE_1);
    }

    @Test
    @Transactional
    void getAllAddressesByLine1NotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where line1 does not contain DEFAULT_LINE_1
        defaultAddressShouldNotBeFound("line1.doesNotContain=" + DEFAULT_LINE_1);

        // Get all the addressList where line1 does not contain UPDATED_LINE_1
        defaultAddressShouldBeFound("line1.doesNotContain=" + UPDATED_LINE_1);
    }

    @Test
    @Transactional
    void getAllAddressesByLine2IsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where line2 equals to DEFAULT_LINE_2
        defaultAddressShouldBeFound("line2.equals=" + DEFAULT_LINE_2);

        // Get all the addressList where line2 equals to UPDATED_LINE_2
        defaultAddressShouldNotBeFound("line2.equals=" + UPDATED_LINE_2);
    }

    @Test
    @Transactional
    void getAllAddressesByLine2IsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where line2 in DEFAULT_LINE_2 or UPDATED_LINE_2
        defaultAddressShouldBeFound("line2.in=" + DEFAULT_LINE_2 + "," + UPDATED_LINE_2);

        // Get all the addressList where line2 equals to UPDATED_LINE_2
        defaultAddressShouldNotBeFound("line2.in=" + UPDATED_LINE_2);
    }

    @Test
    @Transactional
    void getAllAddressesByLine2IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where line2 is not null
        defaultAddressShouldBeFound("line2.specified=true");

        // Get all the addressList where line2 is null
        defaultAddressShouldNotBeFound("line2.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLine2ContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where line2 contains DEFAULT_LINE_2
        defaultAddressShouldBeFound("line2.contains=" + DEFAULT_LINE_2);

        // Get all the addressList where line2 contains UPDATED_LINE_2
        defaultAddressShouldNotBeFound("line2.contains=" + UPDATED_LINE_2);
    }

    @Test
    @Transactional
    void getAllAddressesByLine2NotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where line2 does not contain DEFAULT_LINE_2
        defaultAddressShouldNotBeFound("line2.doesNotContain=" + DEFAULT_LINE_2);

        // Get all the addressList where line2 does not contain UPDATED_LINE_2
        defaultAddressShouldBeFound("line2.doesNotContain=" + UPDATED_LINE_2);
    }

    @Test
    @Transactional
    void getAllAddressesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where country equals to DEFAULT_COUNTRY
        defaultAddressShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the addressList where country equals to UPDATED_COUNTRY
        defaultAddressShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllAddressesByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultAddressShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the addressList where country equals to UPDATED_COUNTRY
        defaultAddressShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllAddressesByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where country is not null
        defaultAddressShouldBeFound("country.specified=true");

        // Get all the addressList where country is null
        defaultAddressShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByCountryContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where country contains DEFAULT_COUNTRY
        defaultAddressShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the addressList where country contains UPDATED_COUNTRY
        defaultAddressShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllAddressesByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where country does not contain DEFAULT_COUNTRY
        defaultAddressShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the addressList where country does not contain UPDATED_COUNTRY
        defaultAddressShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllAddressesByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where state equals to DEFAULT_STATE
        defaultAddressShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the addressList where state equals to UPDATED_STATE
        defaultAddressShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllAddressesByStateIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where state in DEFAULT_STATE or UPDATED_STATE
        defaultAddressShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the addressList where state equals to UPDATED_STATE
        defaultAddressShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllAddressesByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where state is not null
        defaultAddressShouldBeFound("state.specified=true");

        // Get all the addressList where state is null
        defaultAddressShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByStateContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where state contains DEFAULT_STATE
        defaultAddressShouldBeFound("state.contains=" + DEFAULT_STATE);

        // Get all the addressList where state contains UPDATED_STATE
        defaultAddressShouldNotBeFound("state.contains=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllAddressesByStateNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where state does not contain DEFAULT_STATE
        defaultAddressShouldNotBeFound("state.doesNotContain=" + DEFAULT_STATE);

        // Get all the addressList where state does not contain UPDATED_STATE
        defaultAddressShouldBeFound("state.doesNotContain=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode equals to DEFAULT_PINCODE
        defaultAddressShouldBeFound("pincode.equals=" + DEFAULT_PINCODE);

        // Get all the addressList where pincode equals to UPDATED_PINCODE
        defaultAddressShouldNotBeFound("pincode.equals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode in DEFAULT_PINCODE or UPDATED_PINCODE
        defaultAddressShouldBeFound("pincode.in=" + DEFAULT_PINCODE + "," + UPDATED_PINCODE);

        // Get all the addressList where pincode equals to UPDATED_PINCODE
        defaultAddressShouldNotBeFound("pincode.in=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode is not null
        defaultAddressShouldBeFound("pincode.specified=true");

        // Get all the addressList where pincode is null
        defaultAddressShouldNotBeFound("pincode.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode contains DEFAULT_PINCODE
        defaultAddressShouldBeFound("pincode.contains=" + DEFAULT_PINCODE);

        // Get all the addressList where pincode contains UPDATED_PINCODE
        defaultAddressShouldNotBeFound("pincode.contains=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode does not contain DEFAULT_PINCODE
        defaultAddressShouldNotBeFound("pincode.doesNotContain=" + DEFAULT_PINCODE);

        // Get all the addressList where pincode does not contain UPDATED_PINCODE
        defaultAddressShouldBeFound("pincode.doesNotContain=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByHasPreferedIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where hasPrefered equals to DEFAULT_HAS_PREFERED
        defaultAddressShouldBeFound("hasPrefered.equals=" + DEFAULT_HAS_PREFERED);

        // Get all the addressList where hasPrefered equals to UPDATED_HAS_PREFERED
        defaultAddressShouldNotBeFound("hasPrefered.equals=" + UPDATED_HAS_PREFERED);
    }

    @Test
    @Transactional
    void getAllAddressesByHasPreferedIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where hasPrefered in DEFAULT_HAS_PREFERED or UPDATED_HAS_PREFERED
        defaultAddressShouldBeFound("hasPrefered.in=" + DEFAULT_HAS_PREFERED + "," + UPDATED_HAS_PREFERED);

        // Get all the addressList where hasPrefered equals to UPDATED_HAS_PREFERED
        defaultAddressShouldNotBeFound("hasPrefered.in=" + UPDATED_HAS_PREFERED);
    }

    @Test
    @Transactional
    void getAllAddressesByHasPreferedIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where hasPrefered is not null
        defaultAddressShouldBeFound("hasPrefered.specified=true");

        // Get all the addressList where hasPrefered is null
        defaultAddressShouldNotBeFound("hasPrefered.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLandMarkIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landMark equals to DEFAULT_LAND_MARK
        defaultAddressShouldBeFound("landMark.equals=" + DEFAULT_LAND_MARK);

        // Get all the addressList where landMark equals to UPDATED_LAND_MARK
        defaultAddressShouldNotBeFound("landMark.equals=" + UPDATED_LAND_MARK);
    }

    @Test
    @Transactional
    void getAllAddressesByLandMarkIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landMark in DEFAULT_LAND_MARK or UPDATED_LAND_MARK
        defaultAddressShouldBeFound("landMark.in=" + DEFAULT_LAND_MARK + "," + UPDATED_LAND_MARK);

        // Get all the addressList where landMark equals to UPDATED_LAND_MARK
        defaultAddressShouldNotBeFound("landMark.in=" + UPDATED_LAND_MARK);
    }

    @Test
    @Transactional
    void getAllAddressesByLandMarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landMark is not null
        defaultAddressShouldBeFound("landMark.specified=true");

        // Get all the addressList where landMark is null
        defaultAddressShouldNotBeFound("landMark.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLandMarkContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landMark contains DEFAULT_LAND_MARK
        defaultAddressShouldBeFound("landMark.contains=" + DEFAULT_LAND_MARK);

        // Get all the addressList where landMark contains UPDATED_LAND_MARK
        defaultAddressShouldNotBeFound("landMark.contains=" + UPDATED_LAND_MARK);
    }

    @Test
    @Transactional
    void getAllAddressesByLandMarkNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landMark does not contain DEFAULT_LAND_MARK
        defaultAddressShouldNotBeFound("landMark.doesNotContain=" + DEFAULT_LAND_MARK);

        // Get all the addressList where landMark does not contain UPDATED_LAND_MARK
        defaultAddressShouldBeFound("landMark.doesNotContain=" + UPDATED_LAND_MARK);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultAddressShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the addressList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAddressShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultAddressShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the addressList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAddressShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModified is not null
        defaultAddressShouldBeFound("lastModified.specified=true");

        // Get all the addressList where lastModified is null
        defaultAddressShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultAddressShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the addressList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the addressList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy is not null
        defaultAddressShouldBeFound("lastModifiedBy.specified=true");

        // Get all the addressList where lastModifiedBy is null
        defaultAddressShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultAddressShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the addressList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultAddressShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the addressList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdBy equals to DEFAULT_CREATED_BY
        defaultAddressShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the addressList where createdBy equals to UPDATED_CREATED_BY
        defaultAddressShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultAddressShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the addressList where createdBy equals to UPDATED_CREATED_BY
        defaultAddressShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdBy is not null
        defaultAddressShouldBeFound("createdBy.specified=true");

        // Get all the addressList where createdBy is null
        defaultAddressShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdBy contains DEFAULT_CREATED_BY
        defaultAddressShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the addressList where createdBy contains UPDATED_CREATED_BY
        defaultAddressShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdBy does not contain DEFAULT_CREATED_BY
        defaultAddressShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the addressList where createdBy does not contain UPDATED_CREATED_BY
        defaultAddressShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdOn equals to DEFAULT_CREATED_ON
        defaultAddressShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the addressList where createdOn equals to UPDATED_CREATED_ON
        defaultAddressShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultAddressShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the addressList where createdOn equals to UPDATED_CREATED_ON
        defaultAddressShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdOn is not null
        defaultAddressShouldBeFound("createdOn.specified=true");

        // Get all the addressList where createdOn is null
        defaultAddressShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where deleted equals to DEFAULT_DELETED
        defaultAddressShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the addressList where deleted equals to UPDATED_DELETED
        defaultAddressShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllAddressesByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultAddressShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the addressList where deleted equals to UPDATED_DELETED
        defaultAddressShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllAddressesByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where deleted is not null
        defaultAddressShouldBeFound("deleted.specified=true");

        // Get all the addressList where deleted is null
        defaultAddressShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultAddressShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the addressList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultAddressShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAddressesByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultAddressShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the addressList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultAddressShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAddressesByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where employeeId is not null
        defaultAddressShouldBeFound("employeeId.specified=true");

        // Get all the addressList where employeeId is null
        defaultAddressShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultAddressShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the addressList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultAddressShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAddressesByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultAddressShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the addressList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultAddressShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAddressesByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultAddressShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the addressList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultAddressShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAddressesByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultAddressShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the addressList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultAddressShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAddressesByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            addressRepository.saveAndFlush(address);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        address.setEmployee(employee);
        addressRepository.saveAndFlush(address);
        Long employeeId = employee.getId();

        // Get all the addressList where employee equals to employeeId
        defaultAddressShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the addressList where employee equals to (employeeId + 1)
        defaultAddressShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllAddressesByStateIsEqualToSomething() throws Exception {
        State state;
        if (TestUtil.findAll(em, State.class).isEmpty()) {
            addressRepository.saveAndFlush(address);
            state = StateResourceIT.createEntity(em);
        } else {
            state = TestUtil.findAll(em, State.class).get(0);
        }
        em.persist(state);
        em.flush();
        address.setState(state);
        addressRepository.saveAndFlush(address);
        Long stateId = state.getId();

        // Get all the addressList where state equals to stateId
        defaultAddressShouldBeFound("stateId.equals=" + stateId);

        // Get all the addressList where state equals to (stateId + 1)
        defaultAddressShouldNotBeFound("stateId.equals=" + (stateId + 1));
    }

    @Test
    @Transactional
    void getAllAddressesByDistrictIsEqualToSomething() throws Exception {
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            addressRepository.saveAndFlush(address);
            district = DistrictResourceIT.createEntity(em);
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        em.persist(district);
        em.flush();
        address.setDistrict(district);
        addressRepository.saveAndFlush(address);
        Long districtId = district.getId();

        // Get all the addressList where district equals to districtId
        defaultAddressShouldBeFound("districtId.equals=" + districtId);

        // Get all the addressList where district equals to (districtId + 1)
        defaultAddressShouldNotBeFound("districtId.equals=" + (districtId + 1));
    }

    @Test
    @Transactional
    void getAllAddressesByTalukaIsEqualToSomething() throws Exception {
        Taluka taluka;
        if (TestUtil.findAll(em, Taluka.class).isEmpty()) {
            addressRepository.saveAndFlush(address);
            taluka = TalukaResourceIT.createEntity(em);
        } else {
            taluka = TestUtil.findAll(em, Taluka.class).get(0);
        }
        em.persist(taluka);
        em.flush();
        address.setTaluka(taluka);
        addressRepository.saveAndFlush(address);
        Long talukaId = taluka.getId();

        // Get all the addressList where taluka equals to talukaId
        defaultAddressShouldBeFound("talukaId.equals=" + talukaId);

        // Get all the addressList where taluka equals to (talukaId + 1)
        defaultAddressShouldNotBeFound("talukaId.equals=" + (talukaId + 1));
    }

    @Test
    @Transactional
    void getAllAddressesByCityIsEqualToSomething() throws Exception {
        City city;
        if (TestUtil.findAll(em, City.class).isEmpty()) {
            addressRepository.saveAndFlush(address);
            city = CityResourceIT.createEntity(em);
        } else {
            city = TestUtil.findAll(em, City.class).get(0);
        }
        em.persist(city);
        em.flush();
        address.setCity(city);
        addressRepository.saveAndFlush(address);
        Long cityId = city.getId();

        // Get all the addressList where city equals to cityId
        defaultAddressShouldBeFound("cityId.equals=" + cityId);

        // Get all the addressList where city equals to (cityId + 1)
        defaultAddressShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAddressShouldBeFound(String filter) throws Exception {
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].line1").value(hasItem(DEFAULT_LINE_1)))
            .andExpect(jsonPath("$.[*].line2").value(hasItem(DEFAULT_LINE_2)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].hasPrefered").value(hasItem(DEFAULT_HAS_PREFERED.booleanValue())))
            .andExpect(jsonPath("$.[*].landMark").value(hasItem(DEFAULT_LAND_MARK)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())));

        // Check, that the count call also returns 1
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAddressShouldNotBeFound(String filter) throws Exception {
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address
        Address updatedAddress = addressRepository.findById(address.getId()).get();
        // Disconnect from session so that the updates on updatedAddress are not directly saved in db
        em.detach(updatedAddress);
        updatedAddress
            .type(UPDATED_TYPE)
            .line1(UPDATED_LINE_1)
            .line2(UPDATED_LINE_2)
            .country(UPDATED_COUNTRY)
            .state(UPDATED_STATE)
            .pincode(UPDATED_PINCODE)
            .hasPrefered(UPDATED_HAS_PREFERED)
            .landMark(UPDATED_LAND_MARK)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID);
        AddressDTO addressDTO = addressMapper.toDto(updatedAddress);

        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAddress.getLine1()).isEqualTo(UPDATED_LINE_1);
        assertThat(testAddress.getLine2()).isEqualTo(UPDATED_LINE_2);
        assertThat(testAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddress.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testAddress.getHasPrefered()).isEqualTo(UPDATED_HAS_PREFERED);
        assertThat(testAddress.getLandMark()).isEqualTo(UPDATED_LAND_MARK);
        assertThat(testAddress.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAddress.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAddress.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAddress.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAddress.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testAddress.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void putNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAddressWithPatch() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address using partial update
        Address partialUpdatedAddress = new Address();
        partialUpdatedAddress.setId(address.getId());

        partialUpdatedAddress
            .line1(UPDATED_LINE_1)
            .line2(UPDATED_LINE_2)
            .country(UPDATED_COUNTRY)
            .state(UPDATED_STATE)
            .hasPrefered(UPDATED_HAS_PREFERED)
            .landMark(UPDATED_LAND_MARK)
            .createdBy(UPDATED_CREATED_BY);

        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddress))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAddress.getLine1()).isEqualTo(UPDATED_LINE_1);
        assertThat(testAddress.getLine2()).isEqualTo(UPDATED_LINE_2);
        assertThat(testAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddress.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testAddress.getHasPrefered()).isEqualTo(UPDATED_HAS_PREFERED);
        assertThat(testAddress.getLandMark()).isEqualTo(UPDATED_LAND_MARK);
        assertThat(testAddress.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAddress.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testAddress.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAddress.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAddress.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testAddress.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void fullUpdateAddressWithPatch() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address using partial update
        Address partialUpdatedAddress = new Address();
        partialUpdatedAddress.setId(address.getId());

        partialUpdatedAddress
            .type(UPDATED_TYPE)
            .line1(UPDATED_LINE_1)
            .line2(UPDATED_LINE_2)
            .country(UPDATED_COUNTRY)
            .state(UPDATED_STATE)
            .pincode(UPDATED_PINCODE)
            .hasPrefered(UPDATED_HAS_PREFERED)
            .landMark(UPDATED_LAND_MARK)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID);

        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddress))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAddress.getLine1()).isEqualTo(UPDATED_LINE_1);
        assertThat(testAddress.getLine2()).isEqualTo(UPDATED_LINE_2);
        assertThat(testAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddress.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testAddress.getHasPrefered()).isEqualTo(UPDATED_HAS_PREFERED);
        assertThat(testAddress.getLandMark()).isEqualTo(UPDATED_LAND_MARK);
        assertThat(testAddress.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAddress.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAddress.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAddress.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAddress.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testAddress.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, addressDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeDelete = addressRepository.findAll().size();

        // Delete the address
        restAddressMockMvc
            .perform(delete(ENTITY_API_URL_ID, address.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
