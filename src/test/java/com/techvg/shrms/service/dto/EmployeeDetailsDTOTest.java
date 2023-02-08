package com.techvg.shrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.shrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeDetailsDTO.class);
        EmployeeDetailsDTO employeeDetailsDTO1 = new EmployeeDetailsDTO();
        employeeDetailsDTO1.setId(1L);
        EmployeeDetailsDTO employeeDetailsDTO2 = new EmployeeDetailsDTO();
        assertThat(employeeDetailsDTO1).isNotEqualTo(employeeDetailsDTO2);
        employeeDetailsDTO2.setId(employeeDetailsDTO1.getId());
        assertThat(employeeDetailsDTO1).isEqualTo(employeeDetailsDTO2);
        employeeDetailsDTO2.setId(2L);
        assertThat(employeeDetailsDTO1).isNotEqualTo(employeeDetailsDTO2);
        employeeDetailsDTO1.setId(null);
        assertThat(employeeDetailsDTO1).isNotEqualTo(employeeDetailsDTO2);
    }
}
