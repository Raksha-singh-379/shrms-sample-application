package com.techvg.shrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.shrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeavePolicyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeavePolicyDTO.class);
        LeavePolicyDTO leavePolicyDTO1 = new LeavePolicyDTO();
        leavePolicyDTO1.setId(1L);
        LeavePolicyDTO leavePolicyDTO2 = new LeavePolicyDTO();
        assertThat(leavePolicyDTO1).isNotEqualTo(leavePolicyDTO2);
        leavePolicyDTO2.setId(leavePolicyDTO1.getId());
        assertThat(leavePolicyDTO1).isEqualTo(leavePolicyDTO2);
        leavePolicyDTO2.setId(2L);
        assertThat(leavePolicyDTO1).isNotEqualTo(leavePolicyDTO2);
        leavePolicyDTO1.setId(null);
        assertThat(leavePolicyDTO1).isNotEqualTo(leavePolicyDTO2);
    }
}
