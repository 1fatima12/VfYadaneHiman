package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MarqueDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarqueDTO.class);
        MarqueDTO marqueDTO1 = new MarqueDTO();
        marqueDTO1.setId(1L);
        MarqueDTO marqueDTO2 = new MarqueDTO();
        assertThat(marqueDTO1).isNotEqualTo(marqueDTO2);
        marqueDTO2.setId(marqueDTO1.getId());
        assertThat(marqueDTO1).isEqualTo(marqueDTO2);
        marqueDTO2.setId(2L);
        assertThat(marqueDTO1).isNotEqualTo(marqueDTO2);
        marqueDTO1.setId(null);
        assertThat(marqueDTO1).isNotEqualTo(marqueDTO2);
    }
}
