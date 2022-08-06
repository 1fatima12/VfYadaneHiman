package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArrivageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Arrivage.class);
        Arrivage arrivage1 = new Arrivage();
        arrivage1.setId(1L);
        Arrivage arrivage2 = new Arrivage();
        arrivage2.setId(arrivage1.getId());
        assertThat(arrivage1).isEqualTo(arrivage2);
        arrivage2.setId(2L);
        assertThat(arrivage1).isNotEqualTo(arrivage2);
        arrivage1.setId(null);
        assertThat(arrivage1).isNotEqualTo(arrivage2);
    }
}
