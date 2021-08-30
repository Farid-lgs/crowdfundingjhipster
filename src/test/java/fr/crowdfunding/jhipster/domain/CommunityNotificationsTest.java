package fr.crowdfunding.jhipster.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.crowdfunding.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommunityNotificationsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommunityNotifications.class);
        CommunityNotifications communityNotifications1 = new CommunityNotifications();
        communityNotifications1.setId(1L);
        CommunityNotifications communityNotifications2 = new CommunityNotifications();
        communityNotifications2.setId(communityNotifications1.getId());
        assertThat(communityNotifications1).isEqualTo(communityNotifications2);
        communityNotifications2.setId(2L);
        assertThat(communityNotifications1).isNotEqualTo(communityNotifications2);
        communityNotifications1.setId(null);
        assertThat(communityNotifications1).isNotEqualTo(communityNotifications2);
    }
}
