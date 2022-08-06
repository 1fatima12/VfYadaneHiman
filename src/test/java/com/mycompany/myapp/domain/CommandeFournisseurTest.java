package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommandeFournisseurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandeFournisseur.class);
        CommandeFournisseur commandeFournisseur1 = new CommandeFournisseur();
        commandeFournisseur1.setId(1L);
        CommandeFournisseur commandeFournisseur2 = new CommandeFournisseur();
        commandeFournisseur2.setId(commandeFournisseur1.getId());
        assertThat(commandeFournisseur1).isEqualTo(commandeFournisseur2);
        commandeFournisseur2.setId(2L);
        assertThat(commandeFournisseur1).isNotEqualTo(commandeFournisseur2);
        commandeFournisseur1.setId(null);
        assertThat(commandeFournisseur1).isNotEqualTo(commandeFournisseur2);
    }
}
