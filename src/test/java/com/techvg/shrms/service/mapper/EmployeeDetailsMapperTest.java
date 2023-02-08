package com.techvg.shrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeDetailsMapperTest {

    private EmployeeDetailsMapper employeeDetailsMapper;

    @BeforeEach
    public void setUp() {
        employeeDetailsMapper = new EmployeeDetailsMapperImpl();
    }
}
