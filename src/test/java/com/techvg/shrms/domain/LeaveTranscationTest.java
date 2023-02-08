package com.techvg.shrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.shrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveTranscationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveTranscation.class);
        LeaveTranscation leaveTranscation1 = new LeaveTranscation();
        leaveTranscation1.setId(1L);
        LeaveTranscation leaveTranscation2 = new LeaveTranscation();
        leaveTranscation2.setId(leaveTranscation1.getId());
        assertThat(leaveTranscation1).isEqualTo(leaveTranscation2);
        leaveTranscation2.setId(2L);
        assertThat(leaveTranscation1).isNotEqualTo(leaveTranscation2);
        leaveTranscation1.setId(null);
        assertThat(leaveTranscation1).isNotEqualTo(leaveTranscation2);
    }
}
