package com.techvg.shrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LeaveTranscationMapperTest {

    private LeaveTranscationMapper leaveTranscationMapper;

    @BeforeEach
    public void setUp() {
        leaveTranscationMapper = new LeaveTranscationMapperImpl();
    }
}
