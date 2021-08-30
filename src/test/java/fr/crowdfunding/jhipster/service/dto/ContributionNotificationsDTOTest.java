package fr.crowdfunding.jhipster.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.crowdfunding.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContributionNotificationsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContributionNotificationsDTO.class);
        ContributionNotificationsDTO contributionNotificationsDTO1 = new ContributionNotificationsDTO();
        contributionNotificationsDTO1.setId(1L);
        ContributionNotificationsDTO contributionNotificationsDTO2 = new ContributionNotificationsDTO();
        assertThat(contributionNotificationsDTO1).isNotEqualTo(contributionNotificationsDTO2);
        contributionNotificationsDTO2.setId(contributionNotificationsDTO1.getId());
        assertThat(contributionNotificationsDTO1).isEqualTo(contributionNotificationsDTO2);
        contributionNotificationsDTO2.setId(2L);
        assertThat(contributionNotificationsDTO1).isNotEqualTo(contributionNotificationsDTO2);
        contributionNotificationsDTO1.setId(null);
        assertThat(contributionNotificationsDTO1).isNotEqualTo(contributionNotificationsDTO2);
    }
}
