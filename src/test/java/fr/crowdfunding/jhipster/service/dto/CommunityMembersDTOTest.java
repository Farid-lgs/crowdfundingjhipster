package fr.crowdfunding.jhipster.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.crowdfunding.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommunityMembersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommunityMembersDTO.class);
        CommunityMembersDTO communityMembersDTO1 = new CommunityMembersDTO();
        communityMembersDTO1.setId(1L);
        CommunityMembersDTO communityMembersDTO2 = new CommunityMembersDTO();
        assertThat(communityMembersDTO1).isNotEqualTo(communityMembersDTO2);
        communityMembersDTO2.setId(communityMembersDTO1.getId());
        assertThat(communityMembersDTO1).isEqualTo(communityMembersDTO2);
        communityMembersDTO2.setId(2L);
        assertThat(communityMembersDTO1).isNotEqualTo(communityMembersDTO2);
        communityMembersDTO1.setId(null);
        assertThat(communityMembersDTO1).isNotEqualTo(communityMembersDTO2);
    }
}
