package com.techvg.shrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmpSalaryInfoMapperTest {

    private EmpSalaryInfoMapper empSalaryInfoMapper;

    @BeforeEach
    public void setUp() {
        empSalaryInfoMapper = new EmpSalaryInfoMapperImpl();
    }
}
