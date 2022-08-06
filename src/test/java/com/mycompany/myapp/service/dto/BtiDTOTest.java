package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BtiDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BtiDTO.class);
        BtiDTO btiDTO1 = new BtiDTO();
        btiDTO1.setId(1L);
        BtiDTO btiDTO2 = new BtiDTO();
        assertThat(btiDTO1).isNotEqualTo(btiDTO2);
        btiDTO2.setId(btiDTO1.getId());
        assertThat(btiDTO1).isEqualTo(btiDTO2);
        btiDTO2.setId(2L);
        assertThat(btiDTO1).isNotEqualTo(btiDTO2);
        btiDTO1.setId(null);
        assertThat(btiDTO1).isNotEqualTo(btiDTO2);
    }
}
