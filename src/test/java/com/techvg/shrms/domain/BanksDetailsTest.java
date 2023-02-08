package com.techvg.shrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.shrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BanksDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BanksDetails.class);
        BanksDetails banksDetails1 = new BanksDetails();
        banksDetails1.setId(1L);
        BanksDetails banksDetails2 = new BanksDetails();
        banksDetails2.setId(banksDetails1.getId());
        assertThat(banksDetails1).isEqualTo(banksDetails2);
        banksDetails2.setId(2L);
        assertThat(banksDetails1).isNotEqualTo(banksDetails2);
        banksDetails1.setId(null);
        assertThat(banksDetails1).isNotEqualTo(banksDetails2);
    }
}
