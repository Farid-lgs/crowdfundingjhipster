package fr.crowdfunding.jhipster.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.crowdfunding.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommunityNotificationsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommunityNotificationsDTO.class);
        CommunityNotificationsDTO communityNotificationsDTO1 = new CommunityNotificationsDTO();
        communityNotificationsDTO1.setId(1L);
        CommunityNotificationsDTO communityNotificationsDTO2 = new CommunityNotificationsDTO();
        assertThat(communityNotificationsDTO1).isNotEqualTo(communityNotificationsDTO2);
        communityNotificationsDTO2.setId(communityNotificationsDTO1.getId());
        assertThat(communityNotificationsDTO1).isEqualTo(communityNotificationsDTO2);
        communityNotificationsDTO2.setId(2L);
        assertThat(communityNotificationsDTO1).isNotEqualTo(communityNotificationsDTO2);
        communityNotificationsDTO1.setId(null);
        assertThat(communityNotificationsDTO1).isNotEqualTo(communityNotificationsDTO2);
    }
}
