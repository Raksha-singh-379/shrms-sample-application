package com.techvg.shrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.shrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmpSalaryInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpSalaryInfo.class);
        EmpSalaryInfo empSalaryInfo1 = new EmpSalaryInfo();
        empSalaryInfo1.setId(1L);
        EmpSalaryInfo empSalaryInfo2 = new EmpSalaryInfo();
        empSalaryInfo2.setId(empSalaryInfo1.getId());
        assertThat(empSalaryInfo1).isEqualTo(empSalaryInfo2);
        empSalaryInfo2.setId(2L);
        assertThat(empSalaryInfo1).isNotEqualTo(empSalaryInfo2);
        empSalaryInfo1.setId(null);
        assertThat(empSalaryInfo1).isNotEqualTo(empSalaryInfo2);
    }
}
