package fr.crowdfunding.jhipster.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreditCardMapperTest {

    private CreditCardMapper creditCardMapper;

    @BeforeEach
    public void setUp() {
        creditCardMapper = new CreditCardMapperImpl();
    }
}
