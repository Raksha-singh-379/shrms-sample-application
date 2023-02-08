package com.techvg.shrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.shrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectTeamsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectTeamsDTO.class);
        ProjectTeamsDTO projectTeamsDTO1 = new ProjectTeamsDTO();
        projectTeamsDTO1.setId(1L);
        ProjectTeamsDTO projectTeamsDTO2 = new ProjectTeamsDTO();
        assertThat(projectTeamsDTO1).isNotEqualTo(projectTeamsDTO2);
        projectTeamsDTO2.setId(projectTeamsDTO1.getId());
        assertThat(projectTeamsDTO1).isEqualTo(projectTeamsDTO2);
        projectTeamsDTO2.setId(2L);
        assertThat(projectTeamsDTO1).isNotEqualTo(projectTeamsDTO2);
        projectTeamsDTO1.setId(null);
        assertThat(projectTeamsDTO1).isNotEqualTo(projectTeamsDTO2);
    }
}
