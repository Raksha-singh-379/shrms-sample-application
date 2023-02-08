package com.techvg.shrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.shrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalarySettingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalarySettings.class);
        SalarySettings salarySettings1 = new SalarySettings();
        salarySettings1.setId(1L);
        SalarySettings salarySettings2 = new SalarySettings();
        salarySettings2.setId(salarySettings1.getId());
        assertThat(salarySettings1).isEqualTo(salarySettings2);
        salarySettings2.setId(2L);
        assertThat(salarySettings1).isNotEqualTo(salarySettings2);
        salarySettings1.setId(null);
        assertThat(salarySettings1).isNotEqualTo(salarySettings2);
    }
}
