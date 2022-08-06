package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BonCommandeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BonCommande.class);
        BonCommande bonCommande1 = new BonCommande();
        bonCommande1.setId(1L);
        BonCommande bonCommande2 = new BonCommande();
        bonCommande2.setId(bonCommande1.getId());
        assertThat(bonCommande1).isEqualTo(bonCommande2);
        bonCommande2.setId(2L);
        assertThat(bonCommande1).isNotEqualTo(bonCommande2);
        bonCommande1.setId(null);
        assertThat(bonCommande1).isNotEqualTo(bonCommande2);
    }
}
