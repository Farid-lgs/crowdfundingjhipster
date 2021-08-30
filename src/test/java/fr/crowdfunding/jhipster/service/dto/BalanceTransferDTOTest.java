package fr.crowdfunding.jhipster.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.crowdfunding.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BalanceTransferDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BalanceTransferDTO.class);
        BalanceTransferDTO balanceTransferDTO1 = new BalanceTransferDTO();
        balanceTransferDTO1.setId(1L);
        BalanceTransferDTO balanceTransferDTO2 = new BalanceTransferDTO();
        assertThat(balanceTransferDTO1).isNotEqualTo(balanceTransferDTO2);
        balanceTransferDTO2.setId(balanceTransferDTO1.getId());
        assertThat(balanceTransferDTO1).isEqualTo(balanceTransferDTO2);
        balanceTransferDTO2.setId(2L);
        assertThat(balanceTransferDTO1).isNotEqualTo(balanceTransferDTO2);
        balanceTransferDTO1.setId(null);
        assertThat(balanceTransferDTO1).isNotEqualTo(balanceTransferDTO2);
    }
}
