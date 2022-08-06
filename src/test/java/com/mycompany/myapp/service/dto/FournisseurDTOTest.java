package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FournisseurDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FournisseurDTO.class);
        FournisseurDTO fournisseurDTO1 = new FournisseurDTO();
        fournisseurDTO1.setId(1L);
        FournisseurDTO fournisseurDTO2 = new FournisseurDTO();
        assertThat(fournisseurDTO1).isNotEqualTo(fournisseurDTO2);
        fournisseurDTO2.setId(fournisseurDTO1.getId());
        assertThat(fournisseurDTO1).isEqualTo(fournisseurDTO2);
        fournisseurDTO2.setId(2L);
        assertThat(fournisseurDTO1).isNotEqualTo(fournisseurDTO2);
        fournisseurDTO1.setId(null);
        assertThat(fournisseurDTO1).isNotEqualTo(fournisseurDTO2);
    }
}
