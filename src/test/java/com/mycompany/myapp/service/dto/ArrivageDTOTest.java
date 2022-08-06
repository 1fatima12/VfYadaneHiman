package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArrivageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArrivageDTO.class);
        ArrivageDTO arrivageDTO1 = new ArrivageDTO();
        arrivageDTO1.setId(1L);
        ArrivageDTO arrivageDTO2 = new ArrivageDTO();
        assertThat(arrivageDTO1).isNotEqualTo(arrivageDTO2);
        arrivageDTO2.setId(arrivageDTO1.getId());
        assertThat(arrivageDTO1).isEqualTo(arrivageDTO2);
        arrivageDTO2.setId(2L);
        assertThat(arrivageDTO1).isNotEqualTo(arrivageDTO2);
        arrivageDTO1.setId(null);
        assertThat(arrivageDTO1).isNotEqualTo(arrivageDTO2);
    }
}
