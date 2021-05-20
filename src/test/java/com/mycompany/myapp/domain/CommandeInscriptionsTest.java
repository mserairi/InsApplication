package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommandeInscriptionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandeInscriptions.class);
        CommandeInscriptions commandeInscriptions1 = new CommandeInscriptions();
        commandeInscriptions1.setId(1L);
        CommandeInscriptions commandeInscriptions2 = new CommandeInscriptions();
        commandeInscriptions2.setId(commandeInscriptions1.getId());
        assertThat(commandeInscriptions1).isEqualTo(commandeInscriptions2);
        commandeInscriptions2.setId(2L);
        assertThat(commandeInscriptions1).isNotEqualTo(commandeInscriptions2);
        commandeInscriptions1.setId(null);
        assertThat(commandeInscriptions1).isNotEqualTo(commandeInscriptions2);
    }
}
