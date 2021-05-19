package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LasessionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lasession.class);
        Lasession lasession1 = new Lasession();
        lasession1.setId(1L);
        Lasession lasession2 = new Lasession();
        lasession2.setId(lasession1.getId());
        assertThat(lasession1).isEqualTo(lasession2);
        lasession2.setId(2L);
        assertThat(lasession1).isNotEqualTo(lasession2);
        lasession1.setId(null);
        assertThat(lasession1).isNotEqualTo(lasession2);
    }
}
