package fr.crowdfunding.jhipster.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceTransferMapperTest {

    private BalanceTransferMapper balanceTransferMapper;

    @BeforeEach
    public void setUp() {
        balanceTransferMapper = new BalanceTransferMapperImpl();
    }
}
