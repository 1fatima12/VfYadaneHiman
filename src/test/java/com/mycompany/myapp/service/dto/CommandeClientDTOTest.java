package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommandeClientDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandeClientDTO.class);
        CommandeClientDTO commandeClientDTO1 = new CommandeClientDTO();
        commandeClientDTO1.setId(1L);
        CommandeClientDTO commandeClientDTO2 = new CommandeClientDTO();
        assertThat(commandeClientDTO1).isNotEqualTo(commandeClientDTO2);
        commandeClientDTO2.setId(commandeClientDTO1.getId());
        assertThat(commandeClientDTO1).isEqualTo(commandeClientDTO2);
        commandeClientDTO2.setId(2L);
        assertThat(commandeClientDTO1).isNotEqualTo(commandeClientDTO2);
        commandeClientDTO1.setId(null);
        assertThat(commandeClientDTO1).isNotEqualTo(commandeClientDTO2);
    }
}
