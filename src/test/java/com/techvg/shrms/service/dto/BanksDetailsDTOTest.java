package com.techvg.shrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.shrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BanksDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BanksDetailsDTO.class);
        BanksDetailsDTO banksDetailsDTO1 = new BanksDetailsDTO();
        banksDetailsDTO1.setId(1L);
        BanksDetailsDTO banksDetailsDTO2 = new BanksDetailsDTO();
        assertThat(banksDetailsDTO1).isNotEqualTo(banksDetailsDTO2);
        banksDetailsDTO2.setId(banksDetailsDTO1.getId());
        assertThat(banksDetailsDTO1).isEqualTo(banksDetailsDTO2);
        banksDetailsDTO2.setId(2L);
        assertThat(banksDetailsDTO1).isNotEqualTo(banksDetailsDTO2);
        banksDetailsDTO1.setId(null);
        assertThat(banksDetailsDTO1).isNotEqualTo(banksDetailsDTO2);
    }
}
