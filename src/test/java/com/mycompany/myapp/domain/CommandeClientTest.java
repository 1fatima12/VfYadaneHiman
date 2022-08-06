package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommandeClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandeClient.class);
        CommandeClient commandeClient1 = new CommandeClient();
        commandeClient1.setId(1L);
        CommandeClient commandeClient2 = new CommandeClient();
        commandeClient2.setId(commandeClient1.getId());
        assertThat(commandeClient1).isEqualTo(commandeClient2);
        commandeClient2.setId(2L);
        assertThat(commandeClient1).isNotEqualTo(commandeClient2);
        commandeClient1.setId(null);
        assertThat(commandeClient1).isNotEqualTo(commandeClient2);
    }
}
