package fr.crowdfunding.jhipster.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.crowdfunding.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreditCardDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditCardDTO.class);
        CreditCardDTO creditCardDTO1 = new CreditCardDTO();
        creditCardDTO1.setId(1L);
        CreditCardDTO creditCardDTO2 = new CreditCardDTO();
        assertThat(creditCardDTO1).isNotEqualTo(creditCardDTO2);
        creditCardDTO2.setId(creditCardDTO1.getId());
        assertThat(creditCardDTO1).isEqualTo(creditCardDTO2);
        creditCardDTO2.setId(2L);
        assertThat(creditCardDTO1).isNotEqualTo(creditCardDTO2);
        creditCardDTO1.setId(null);
        assertThat(creditCardDTO1).isNotEqualTo(creditCardDTO2);
    }
}
