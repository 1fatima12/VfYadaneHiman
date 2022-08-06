package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MagazinDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MagazinDTO.class);
        MagazinDTO magazinDTO1 = new MagazinDTO();
        magazinDTO1.setId(1L);
        MagazinDTO magazinDTO2 = new MagazinDTO();
        assertThat(magazinDTO1).isNotEqualTo(magazinDTO2);
        magazinDTO2.setId(magazinDTO1.getId());
        assertThat(magazinDTO1).isEqualTo(magazinDTO2);
        magazinDTO2.setId(2L);
        assertThat(magazinDTO1).isNotEqualTo(magazinDTO2);
        magazinDTO1.setId(null);
        assertThat(magazinDTO1).isNotEqualTo(magazinDTO2);
    }
}
