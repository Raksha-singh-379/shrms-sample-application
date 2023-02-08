package com.techvg.shrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.shrms.IntegrationTest;
import com.techvg.shrms.domain.Contacts;
import com.techvg.shrms.domain.Employee;
import com.techvg.shrms.domain.enumeration.ContactType;
import com.techvg.shrms.domain.enumeration.Relationship;
import com.techvg.shrms.repository.ContactsRepository;
import com.techvg.shrms.service.criteria.ContactsCriteria;
import com.techvg.shrms.service.dto.ContactsDTO;
import com.techvg.shrms.service.mapper.ContactsMapper;
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
 * Integration tests for the {@link ContactsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContactsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ContactType DEFAULT_TYPE = ContactType.PRIMARY;
    private static final ContactType UPDATED_TYPE = ContactType.SECONDARY;

    private static final Relationship DEFAULT_RELATION = Relationship.FATHER;
    private static final Relationship UPDATED_RELATION = Relationship.MOTHER;

    private static final String DEFAULT_PHONE_NO_1 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NO_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NO_2 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NO_2 = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContactsRepository contactsRepository;

    @Autowired
    private ContactsMapper contactsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactsMockMvc;

    private Contacts contacts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contacts createEntity(EntityManager em) {
        Contacts contacts = new Contacts()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .relation(DEFAULT_RELATION)
            .phoneNo1(DEFAULT_PHONE_NO_1)
            .phoneNo2(DEFAULT_PHONE_NO_2)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .deleted(DEFAULT_DELETED)
            .employeeId(DEFAULT_EMPLOYEE_ID);
        return contacts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contacts createUpdatedEntity(EntityManager em) {
        Contacts contacts = new Contacts()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .relation(UPDATED_RELATION)
            .phoneNo1(UPDATED_PHONE_NO_1)
            .phoneNo2(UPDATED_PHONE_NO_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID);
        return contacts;
    }

    @BeforeEach
    public void initTest() {
        contacts = createEntity(em);
    }

    @Test
    @Transactional
    void createContacts() throws Exception {
        int databaseSizeBeforeCreate = contactsRepository.findAll().size();
        // Create the Contacts
        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);
        restContactsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactsDTO)))
            .andExpect(status().isCreated());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeCreate + 1);
        Contacts testContacts = contactsList.get(contactsList.size() - 1);
        assertThat(testContacts.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContacts.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testContacts.getRelation()).isEqualTo(DEFAULT_RELATION);
        assertThat(testContacts.getPhoneNo1()).isEqualTo(DEFAULT_PHONE_NO_1);
        assertThat(testContacts.getPhoneNo2()).isEqualTo(DEFAULT_PHONE_NO_2);
        assertThat(testContacts.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testContacts.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testContacts.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testContacts.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testContacts.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testContacts.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void createContactsWithExistingId() throws Exception {
        // Create the Contacts with an existing ID
        contacts.setId(1L);
        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);

        int databaseSizeBeforeCreate = contactsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContacts() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList
        restContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contacts.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION.toString())))
            .andExpect(jsonPath("$.[*].phoneNo1").value(hasItem(DEFAULT_PHONE_NO_1)))
            .andExpect(jsonPath("$.[*].phoneNo2").value(hasItem(DEFAULT_PHONE_NO_2)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())));
    }

    @Test
    @Transactional
    void getContacts() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get the contacts
        restContactsMockMvc
            .perform(get(ENTITY_API_URL_ID, contacts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contacts.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.relation").value(DEFAULT_RELATION.toString()))
            .andExpect(jsonPath("$.phoneNo1").value(DEFAULT_PHONE_NO_1))
            .andExpect(jsonPath("$.phoneNo2").value(DEFAULT_PHONE_NO_2))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()));
    }

    @Test
    @Transactional
    void getContactsByIdFiltering() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        Long id = contacts.getId();

        defaultContactsShouldBeFound("id.equals=" + id);
        defaultContactsShouldNotBeFound("id.notEquals=" + id);

        defaultContactsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContactsShouldNotBeFound("id.greaterThan=" + id);

        defaultContactsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContactsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllContactsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where name equals to DEFAULT_NAME
        defaultContactsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the contactsList where name equals to UPDATED_NAME
        defaultContactsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultContactsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the contactsList where name equals to UPDATED_NAME
        defaultContactsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where name is not null
        defaultContactsShouldBeFound("name.specified=true");

        // Get all the contactsList where name is null
        defaultContactsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByNameContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where name contains DEFAULT_NAME
        defaultContactsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the contactsList where name contains UPDATED_NAME
        defaultContactsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where name does not contain DEFAULT_NAME
        defaultContactsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the contactsList where name does not contain UPDATED_NAME
        defaultContactsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where type equals to DEFAULT_TYPE
        defaultContactsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the contactsList where type equals to UPDATED_TYPE
        defaultContactsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllContactsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultContactsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the contactsList where type equals to UPDATED_TYPE
        defaultContactsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllContactsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where type is not null
        defaultContactsShouldBeFound("type.specified=true");

        // Get all the contactsList where type is null
        defaultContactsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByRelationIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where relation equals to DEFAULT_RELATION
        defaultContactsShouldBeFound("relation.equals=" + DEFAULT_RELATION);

        // Get all the contactsList where relation equals to UPDATED_RELATION
        defaultContactsShouldNotBeFound("relation.equals=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllContactsByRelationIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where relation in DEFAULT_RELATION or UPDATED_RELATION
        defaultContactsShouldBeFound("relation.in=" + DEFAULT_RELATION + "," + UPDATED_RELATION);

        // Get all the contactsList where relation equals to UPDATED_RELATION
        defaultContactsShouldNotBeFound("relation.in=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllContactsByRelationIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where relation is not null
        defaultContactsShouldBeFound("relation.specified=true");

        // Get all the contactsList where relation is null
        defaultContactsShouldNotBeFound("relation.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByPhoneNo1IsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where phoneNo1 equals to DEFAULT_PHONE_NO_1
        defaultContactsShouldBeFound("phoneNo1.equals=" + DEFAULT_PHONE_NO_1);

        // Get all the contactsList where phoneNo1 equals to UPDATED_PHONE_NO_1
        defaultContactsShouldNotBeFound("phoneNo1.equals=" + UPDATED_PHONE_NO_1);
    }

    @Test
    @Transactional
    void getAllContactsByPhoneNo1IsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where phoneNo1 in DEFAULT_PHONE_NO_1 or UPDATED_PHONE_NO_1
        defaultContactsShouldBeFound("phoneNo1.in=" + DEFAULT_PHONE_NO_1 + "," + UPDATED_PHONE_NO_1);

        // Get all the contactsList where phoneNo1 equals to UPDATED_PHONE_NO_1
        defaultContactsShouldNotBeFound("phoneNo1.in=" + UPDATED_PHONE_NO_1);
    }

    @Test
    @Transactional
    void getAllContactsByPhoneNo1IsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where phoneNo1 is not null
        defaultContactsShouldBeFound("phoneNo1.specified=true");

        // Get all the contactsList where phoneNo1 is null
        defaultContactsShouldNotBeFound("phoneNo1.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByPhoneNo1ContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where phoneNo1 contains DEFAULT_PHONE_NO_1
        defaultContactsShouldBeFound("phoneNo1.contains=" + DEFAULT_PHONE_NO_1);

        // Get all the contactsList where phoneNo1 contains UPDATED_PHONE_NO_1
        defaultContactsShouldNotBeFound("phoneNo1.contains=" + UPDATED_PHONE_NO_1);
    }

    @Test
    @Transactional
    void getAllContactsByPhoneNo1NotContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where phoneNo1 does not contain DEFAULT_PHONE_NO_1
        defaultContactsShouldNotBeFound("phoneNo1.doesNotContain=" + DEFAULT_PHONE_NO_1);

        // Get all the contactsList where phoneNo1 does not contain UPDATED_PHONE_NO_1
        defaultContactsShouldBeFound("phoneNo1.doesNotContain=" + UPDATED_PHONE_NO_1);
    }

    @Test
    @Transactional
    void getAllContactsByPhoneNo2IsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where phoneNo2 equals to DEFAULT_PHONE_NO_2
        defaultContactsShouldBeFound("phoneNo2.equals=" + DEFAULT_PHONE_NO_2);

        // Get all the contactsList where phoneNo2 equals to UPDATED_PHONE_NO_2
        defaultContactsShouldNotBeFound("phoneNo2.equals=" + UPDATED_PHONE_NO_2);
    }

    @Test
    @Transactional
    void getAllContactsByPhoneNo2IsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where phoneNo2 in DEFAULT_PHONE_NO_2 or UPDATED_PHONE_NO_2
        defaultContactsShouldBeFound("phoneNo2.in=" + DEFAULT_PHONE_NO_2 + "," + UPDATED_PHONE_NO_2);

        // Get all the contactsList where phoneNo2 equals to UPDATED_PHONE_NO_2
        defaultContactsShouldNotBeFound("phoneNo2.in=" + UPDATED_PHONE_NO_2);
    }

    @Test
    @Transactional
    void getAllContactsByPhoneNo2IsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where phoneNo2 is not null
        defaultContactsShouldBeFound("phoneNo2.specified=true");

        // Get all the contactsList where phoneNo2 is null
        defaultContactsShouldNotBeFound("phoneNo2.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByPhoneNo2ContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where phoneNo2 contains DEFAULT_PHONE_NO_2
        defaultContactsShouldBeFound("phoneNo2.contains=" + DEFAULT_PHONE_NO_2);

        // Get all the contactsList where phoneNo2 contains UPDATED_PHONE_NO_2
        defaultContactsShouldNotBeFound("phoneNo2.contains=" + UPDATED_PHONE_NO_2);
    }

    @Test
    @Transactional
    void getAllContactsByPhoneNo2NotContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where phoneNo2 does not contain DEFAULT_PHONE_NO_2
        defaultContactsShouldNotBeFound("phoneNo2.doesNotContain=" + DEFAULT_PHONE_NO_2);

        // Get all the contactsList where phoneNo2 does not contain UPDATED_PHONE_NO_2
        defaultContactsShouldBeFound("phoneNo2.doesNotContain=" + UPDATED_PHONE_NO_2);
    }

    @Test
    @Transactional
    void getAllContactsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultContactsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the contactsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultContactsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllContactsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultContactsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the contactsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultContactsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllContactsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where lastModified is not null
        defaultContactsShouldBeFound("lastModified.specified=true");

        // Get all the contactsList where lastModified is null
        defaultContactsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultContactsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the contactsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultContactsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllContactsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultContactsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the contactsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultContactsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllContactsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where lastModifiedBy is not null
        defaultContactsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the contactsList where lastModifiedBy is null
        defaultContactsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultContactsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the contactsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultContactsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllContactsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultContactsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the contactsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultContactsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllContactsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where createdBy equals to DEFAULT_CREATED_BY
        defaultContactsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the contactsList where createdBy equals to UPDATED_CREATED_BY
        defaultContactsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllContactsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultContactsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the contactsList where createdBy equals to UPDATED_CREATED_BY
        defaultContactsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllContactsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where createdBy is not null
        defaultContactsShouldBeFound("createdBy.specified=true");

        // Get all the contactsList where createdBy is null
        defaultContactsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where createdBy contains DEFAULT_CREATED_BY
        defaultContactsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the contactsList where createdBy contains UPDATED_CREATED_BY
        defaultContactsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllContactsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultContactsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the contactsList where createdBy does not contain UPDATED_CREATED_BY
        defaultContactsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllContactsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where createdOn equals to DEFAULT_CREATED_ON
        defaultContactsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the contactsList where createdOn equals to UPDATED_CREATED_ON
        defaultContactsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllContactsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultContactsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the contactsList where createdOn equals to UPDATED_CREATED_ON
        defaultContactsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllContactsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where createdOn is not null
        defaultContactsShouldBeFound("createdOn.specified=true");

        // Get all the contactsList where createdOn is null
        defaultContactsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where deleted equals to DEFAULT_DELETED
        defaultContactsShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the contactsList where deleted equals to UPDATED_DELETED
        defaultContactsShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllContactsByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultContactsShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the contactsList where deleted equals to UPDATED_DELETED
        defaultContactsShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllContactsByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where deleted is not null
        defaultContactsShouldBeFound("deleted.specified=true");

        // Get all the contactsList where deleted is null
        defaultContactsShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultContactsShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the contactsList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultContactsShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllContactsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultContactsShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the contactsList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultContactsShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllContactsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where employeeId is not null
        defaultContactsShouldBeFound("employeeId.specified=true");

        // Get all the contactsList where employeeId is null
        defaultContactsShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultContactsShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the contactsList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultContactsShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllContactsByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultContactsShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the contactsList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultContactsShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllContactsByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultContactsShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the contactsList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultContactsShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllContactsByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultContactsShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the contactsList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultContactsShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllContactsByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            contactsRepository.saveAndFlush(contacts);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        contacts.setEmployee(employee);
        contactsRepository.saveAndFlush(contacts);
        Long employeeId = employee.getId();

        // Get all the contactsList where employee equals to employeeId
        defaultContactsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the contactsList where employee equals to (employeeId + 1)
        defaultContactsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContactsShouldBeFound(String filter) throws Exception {
        restContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contacts.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION.toString())))
            .andExpect(jsonPath("$.[*].phoneNo1").value(hasItem(DEFAULT_PHONE_NO_1)))
            .andExpect(jsonPath("$.[*].phoneNo2").value(hasItem(DEFAULT_PHONE_NO_2)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())));

        // Check, that the count call also returns 1
        restContactsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContactsShouldNotBeFound(String filter) throws Exception {
        restContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContactsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingContacts() throws Exception {
        // Get the contacts
        restContactsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContacts() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();

        // Update the contacts
        Contacts updatedContacts = contactsRepository.findById(contacts.getId()).get();
        // Disconnect from session so that the updates on updatedContacts are not directly saved in db
        em.detach(updatedContacts);
        updatedContacts
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .relation(UPDATED_RELATION)
            .phoneNo1(UPDATED_PHONE_NO_1)
            .phoneNo2(UPDATED_PHONE_NO_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID);
        ContactsDTO contactsDTO = contactsMapper.toDto(updatedContacts);

        restContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
        Contacts testContacts = contactsList.get(contactsList.size() - 1);
        assertThat(testContacts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContacts.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testContacts.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testContacts.getPhoneNo1()).isEqualTo(UPDATED_PHONE_NO_1);
        assertThat(testContacts.getPhoneNo2()).isEqualTo(UPDATED_PHONE_NO_2);
        assertThat(testContacts.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testContacts.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testContacts.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testContacts.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testContacts.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testContacts.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void putNonExistingContacts() throws Exception {
        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
        contacts.setId(count.incrementAndGet());

        // Create the Contacts
        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContacts() throws Exception {
        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
        contacts.setId(count.incrementAndGet());

        // Create the Contacts
        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContacts() throws Exception {
        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
        contacts.setId(count.incrementAndGet());

        // Create the Contacts
        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactsWithPatch() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();

        // Update the contacts using partial update
        Contacts partialUpdatedContacts = new Contacts();
        partialUpdatedContacts.setId(contacts.getId());

        partialUpdatedContacts
            .name(UPDATED_NAME)
            .relation(UPDATED_RELATION)
            .phoneNo1(UPDATED_PHONE_NO_1)
            .lastModified(UPDATED_LAST_MODIFIED);

        restContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContacts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContacts))
            )
            .andExpect(status().isOk());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
        Contacts testContacts = contactsList.get(contactsList.size() - 1);
        assertThat(testContacts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContacts.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testContacts.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testContacts.getPhoneNo1()).isEqualTo(UPDATED_PHONE_NO_1);
        assertThat(testContacts.getPhoneNo2()).isEqualTo(DEFAULT_PHONE_NO_2);
        assertThat(testContacts.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testContacts.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testContacts.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testContacts.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testContacts.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testContacts.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void fullUpdateContactsWithPatch() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();

        // Update the contacts using partial update
        Contacts partialUpdatedContacts = new Contacts();
        partialUpdatedContacts.setId(contacts.getId());

        partialUpdatedContacts
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .relation(UPDATED_RELATION)
            .phoneNo1(UPDATED_PHONE_NO_1)
            .phoneNo2(UPDATED_PHONE_NO_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .deleted(UPDATED_DELETED)
            .employeeId(UPDATED_EMPLOYEE_ID);

        restContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContacts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContacts))
            )
            .andExpect(status().isOk());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
        Contacts testContacts = contactsList.get(contactsList.size() - 1);
        assertThat(testContacts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContacts.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testContacts.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testContacts.getPhoneNo1()).isEqualTo(UPDATED_PHONE_NO_1);
        assertThat(testContacts.getPhoneNo2()).isEqualTo(UPDATED_PHONE_NO_2);
        assertThat(testContacts.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testContacts.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testContacts.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testContacts.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testContacts.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testContacts.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingContacts() throws Exception {
        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
        contacts.setId(count.incrementAndGet());

        // Create the Contacts
        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contactsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContacts() throws Exception {
        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
        contacts.setId(count.incrementAndGet());

        // Create the Contacts
        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContacts() throws Exception {
        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
        contacts.setId(count.incrementAndGet());

        // Create the Contacts
        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contactsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContacts() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        int databaseSizeBeforeDelete = contactsRepository.findAll().size();

        // Delete the contacts
        restContactsMockMvc
            .perform(delete(ENTITY_API_URL_ID, contacts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
