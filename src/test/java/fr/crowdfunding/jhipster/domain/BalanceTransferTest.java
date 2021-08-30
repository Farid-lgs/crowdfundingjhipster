package fr.crowdfunding.jhipster.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.crowdfunding.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BalanceTransferTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BalanceTransfer.class);
        BalanceTransfer balanceTransfer1 = new BalanceTransfer();
        balanceTransfer1.setId(1L);
        BalanceTransfer balanceTransfer2 = new BalanceTransfer();
        balanceTransfer2.setId(balanceTransfer1.getId());
        assertThat(balanceTransfer1).isEqualTo(balanceTransfer2);
        balanceTransfer2.setId(2L);
        assertThat(balanceTransfer1).isNotEqualTo(balanceTransfer2);
        balanceTransfer1.setId(null);
        assertThat(balanceTransfer1).isNotEqualTo(balanceTransfer2);
    }
}
