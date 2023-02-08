package com.techvg.shrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.shrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectTeamsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectTeams.class);
        ProjectTeams projectTeams1 = new ProjectTeams();
        projectTeams1.setId(1L);
        ProjectTeams projectTeams2 = new ProjectTeams();
        projectTeams2.setId(projectTeams1.getId());
        assertThat(projectTeams1).isEqualTo(projectTeams2);
        projectTeams2.setId(2L);
        assertThat(projectTeams1).isNotEqualTo(projectTeams2);
        projectTeams1.setId(null);
        assertThat(projectTeams1).isNotEqualTo(projectTeams2);
    }
}
