package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommandeFournisseurDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandeFournisseurDTO.class);
        CommandeFournisseurDTO commandeFournisseurDTO1 = new CommandeFournisseurDTO();
        commandeFournisseurDTO1.setId(1L);
        CommandeFournisseurDTO commandeFournisseurDTO2 = new CommandeFournisseurDTO();
        assertThat(commandeFournisseurDTO1).isNotEqualTo(commandeFournisseurDTO2);
        commandeFournisseurDTO2.setId(commandeFournisseurDTO1.getId());
        assertThat(commandeFournisseurDTO1).isEqualTo(commandeFournisseurDTO2);
        commandeFournisseurDTO2.setId(2L);
        assertThat(commandeFournisseurDTO1).isNotEqualTo(commandeFournisseurDTO2);
        commandeFournisseurDTO1.setId(null);
        assertThat(commandeFournisseurDTO1).isNotEqualTo(commandeFournisseurDTO2);
    }
}
