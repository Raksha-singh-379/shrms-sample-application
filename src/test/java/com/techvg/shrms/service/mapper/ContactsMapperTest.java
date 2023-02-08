package com.techvg.shrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactsMapperTest {

    private ContactsMapper contactsMapper;

    @BeforeEach
    public void setUp() {
        contactsMapper = new ContactsMapperImpl();
    }
}
