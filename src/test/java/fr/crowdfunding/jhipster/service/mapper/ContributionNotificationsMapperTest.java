package fr.crowdfunding.jhipster.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContributionNotificationsMapperTest {

    private ContributionNotificationsMapper contributionNotificationsMapper;

    @BeforeEach
    public void setUp() {
        contributionNotificationsMapper = new ContributionNotificationsMapperImpl();
    }
}
