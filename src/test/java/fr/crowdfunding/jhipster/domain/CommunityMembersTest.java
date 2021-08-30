package fr.crowdfunding.jhipster.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.crowdfunding.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommunityMembersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommunityMembers.class);
        CommunityMembers communityMembers1 = new CommunityMembers();
        communityMembers1.setId(1L);
        CommunityMembers communityMembers2 = new CommunityMembers();
        communityMembers2.setId(communityMembers1.getId());
        assertThat(communityMembers1).isEqualTo(communityMembers2);
        communityMembers2.setId(2L);
        assertThat(communityMembers1).isNotEqualTo(communityMembers2);
        communityMembers1.setId(null);
        assertThat(communityMembers1).isNotEqualTo(communityMembers2);
    }
}
