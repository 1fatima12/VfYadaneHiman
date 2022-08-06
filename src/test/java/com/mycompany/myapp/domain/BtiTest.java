package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BtiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bti.class);
        Bti bti1 = new Bti();
        bti1.setId(1L);
        Bti bti2 = new Bti();
        bti2.setId(bti1.getId());
        assertThat(bti1).isEqualTo(bti2);
        bti2.setId(2L);
        assertThat(bti1).isNotEqualTo(bti2);
        bti1.setId(null);
        assertThat(bti1).isNotEqualTo(bti2);
    }
}
