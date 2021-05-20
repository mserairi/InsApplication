package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeRemiseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeRemise.class);
        TypeRemise typeRemise1 = new TypeRemise();
        typeRemise1.setId(1L);
        TypeRemise typeRemise2 = new TypeRemise();
        typeRemise2.setId(typeRemise1.getId());
        assertThat(typeRemise1).isEqualTo(typeRemise2);
        typeRemise2.setId(2L);
        assertThat(typeRemise1).isNotEqualTo(typeRemise2);
        typeRemise1.setId(null);
        assertThat(typeRemise1).isNotEqualTo(typeRemise2);
    }
}
