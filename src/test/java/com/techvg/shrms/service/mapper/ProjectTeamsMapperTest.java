package com.techvg.shrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectTeamsMapperTest {

    private ProjectTeamsMapper projectTeamsMapper;

    @BeforeEach
    public void setUp() {
        projectTeamsMapper = new ProjectTeamsMapperImpl();
    }
}
