package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SousCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SousCategory.class);
        SousCategory sousCategory1 = new SousCategory();
        sousCategory1.setId(1L);
        SousCategory sousCategory2 = new SousCategory();
        sousCategory2.setId(sousCategory1.getId());
        assertThat(sousCategory1).isEqualTo(sousCategory2);
        sousCategory2.setId(2L);
        assertThat(sousCategory1).isNotEqualTo(sousCategory2);
        sousCategory1.setId(null);
        assertThat(sousCategory1).isNotEqualTo(sousCategory2);
    }
}
