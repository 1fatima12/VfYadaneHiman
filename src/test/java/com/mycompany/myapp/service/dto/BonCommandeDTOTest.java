package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BonCommandeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BonCommandeDTO.class);
        BonCommandeDTO bonCommandeDTO1 = new BonCommandeDTO();
        bonCommandeDTO1.setId(1L);
        BonCommandeDTO bonCommandeDTO2 = new BonCommandeDTO();
        assertThat(bonCommandeDTO1).isNotEqualTo(bonCommandeDTO2);
        bonCommandeDTO2.setId(bonCommandeDTO1.getId());
        assertThat(bonCommandeDTO1).isEqualTo(bonCommandeDTO2);
        bonCommandeDTO2.setId(2L);
        assertThat(bonCommandeDTO1).isNotEqualTo(bonCommandeDTO2);
        bonCommandeDTO1.setId(null);
        assertThat(bonCommandeDTO1).isNotEqualTo(bonCommandeDTO2);
    }
}
