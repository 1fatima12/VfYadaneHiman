package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaiementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaiementDTO.class);
        PaiementDTO paiementDTO1 = new PaiementDTO();
        paiementDTO1.setId(1L);
        PaiementDTO paiementDTO2 = new PaiementDTO();
        assertThat(paiementDTO1).isNotEqualTo(paiementDTO2);
        paiementDTO2.setId(paiementDTO1.getId());
        assertThat(paiementDTO1).isEqualTo(paiementDTO2);
        paiementDTO2.setId(2L);
        assertThat(paiementDTO1).isNotEqualTo(paiementDTO2);
        paiementDTO1.setId(null);
        assertThat(paiementDTO1).isNotEqualTo(paiementDTO2);
    }
}
