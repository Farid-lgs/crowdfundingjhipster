package fr.crowdfunding.jhipster.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.crowdfunding.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContributionNotificationsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContributionNotifications.class);
        ContributionNotifications contributionNotifications1 = new ContributionNotifications();
        contributionNotifications1.setId(1L);
        ContributionNotifications contributionNotifications2 = new ContributionNotifications();
        contributionNotifications2.setId(contributionNotifications1.getId());
        assertThat(contributionNotifications1).isEqualTo(contributionNotifications2);
        contributionNotifications2.setId(2L);
        assertThat(contributionNotifications1).isNotEqualTo(contributionNotifications2);
        contributionNotifications1.setId(null);
        assertThat(contributionNotifications1).isNotEqualTo(contributionNotifications2);
    }
}
