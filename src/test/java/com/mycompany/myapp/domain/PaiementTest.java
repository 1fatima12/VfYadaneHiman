package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaiementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paiement.class);
        Paiement paiement1 = new Paiement();
        paiement1.setId(1L);
        Paiement paiement2 = new Paiement();
        paiement2.setId(paiement1.getId());
        assertThat(paiement1).isEqualTo(paiement2);
        paiement2.setId(2L);
        assertThat(paiement1).isNotEqualTo(paiement2);
        paiement1.setId(null);
        assertThat(paiement1).isNotEqualTo(paiement2);
    }
}
