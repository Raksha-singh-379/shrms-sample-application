package com.techvg.shrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.shrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalarySettingsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalarySettingsDTO.class);
        SalarySettingsDTO salarySettingsDTO1 = new SalarySettingsDTO();
        salarySettingsDTO1.setId(1L);
        SalarySettingsDTO salarySettingsDTO2 = new SalarySettingsDTO();
        assertThat(salarySettingsDTO1).isNotEqualTo(salarySettingsDTO2);
        salarySettingsDTO2.setId(salarySettingsDTO1.getId());
        assertThat(salarySettingsDTO1).isEqualTo(salarySettingsDTO2);
        salarySettingsDTO2.setId(2L);
        assertThat(salarySettingsDTO1).isNotEqualTo(salarySettingsDTO2);
        salarySettingsDTO1.setId(null);
        assertThat(salarySettingsDTO1).isNotEqualTo(salarySettingsDTO2);
    }
}
