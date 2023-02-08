package com.techvg.shrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.shrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeavePolicyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeavePolicy.class);
        LeavePolicy leavePolicy1 = new LeavePolicy();
        leavePolicy1.setId(1L);
        LeavePolicy leavePolicy2 = new LeavePolicy();
        leavePolicy2.setId(leavePolicy1.getId());
        assertThat(leavePolicy1).isEqualTo(leavePolicy2);
        leavePolicy2.setId(2L);
        assertThat(leavePolicy1).isNotEqualTo(leavePolicy2);
        leavePolicy1.setId(null);
        assertThat(leavePolicy1).isNotEqualTo(leavePolicy2);
    }
}
