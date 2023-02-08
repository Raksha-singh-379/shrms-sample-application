package com.techvg.shrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.shrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveTranscationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveTranscationDTO.class);
        LeaveTranscationDTO leaveTranscationDTO1 = new LeaveTranscationDTO();
        leaveTranscationDTO1.setId(1L);
        LeaveTranscationDTO leaveTranscationDTO2 = new LeaveTranscationDTO();
        assertThat(leaveTranscationDTO1).isNotEqualTo(leaveTranscationDTO2);
        leaveTranscationDTO2.setId(leaveTranscationDTO1.getId());
        assertThat(leaveTranscationDTO1).isEqualTo(leaveTranscationDTO2);
        leaveTranscationDTO2.setId(2L);
        assertThat(leaveTranscationDTO1).isNotEqualTo(leaveTranscationDTO2);
        leaveTranscationDTO1.setId(null);
        assertThat(leaveTranscationDTO1).isNotEqualTo(leaveTranscationDTO2);
    }
}
