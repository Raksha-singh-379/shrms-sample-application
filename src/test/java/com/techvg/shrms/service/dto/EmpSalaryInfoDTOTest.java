package com.techvg.shrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.shrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmpSalaryInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpSalaryInfoDTO.class);
        EmpSalaryInfoDTO empSalaryInfoDTO1 = new EmpSalaryInfoDTO();
        empSalaryInfoDTO1.setId(1L);
        EmpSalaryInfoDTO empSalaryInfoDTO2 = new EmpSalaryInfoDTO();
        assertThat(empSalaryInfoDTO1).isNotEqualTo(empSalaryInfoDTO2);
        empSalaryInfoDTO2.setId(empSalaryInfoDTO1.getId());
        assertThat(empSalaryInfoDTO1).isEqualTo(empSalaryInfoDTO2);
        empSalaryInfoDTO2.setId(2L);
        assertThat(empSalaryInfoDTO1).isNotEqualTo(empSalaryInfoDTO2);
        empSalaryInfoDTO1.setId(null);
        assertThat(empSalaryInfoDTO1).isNotEqualTo(empSalaryInfoDTO2);
    }
}
