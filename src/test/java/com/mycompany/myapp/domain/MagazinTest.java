package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MagazinTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Magazin.class);
        Magazin magazin1 = new Magazin();
        magazin1.setId(1L);
        Magazin magazin2 = new Magazin();
        magazin2.setId(magazin1.getId());
        assertThat(magazin1).isEqualTo(magazin2);
        magazin2.setId(2L);
        assertThat(magazin1).isNotEqualTo(magazin2);
        magazin1.setId(null);
        assertThat(magazin1).isNotEqualTo(magazin2);
    }
}
