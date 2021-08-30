package fr.crowdfunding.jhipster.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RewardMapperTest {

    private RewardMapper rewardMapper;

    @BeforeEach
    public void setUp() {
        rewardMapper = new RewardMapperImpl();
    }
}
