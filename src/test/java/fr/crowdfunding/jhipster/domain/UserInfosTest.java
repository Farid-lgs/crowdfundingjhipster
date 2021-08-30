package fr.crowdfunding.jhipster.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.crowdfunding.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserInfosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserInfos.class);
        UserInfos userInfos1 = new UserInfos();
        userInfos1.setId(1L);
        UserInfos userInfos2 = new UserInfos();
        userInfos2.setId(userInfos1.getId());
        assertThat(userInfos1).isEqualTo(userInfos2);
        userInfos2.setId(2L);
        assertThat(userInfos1).isNotEqualTo(userInfos2);
        userInfos1.setId(null);
        assertThat(userInfos1).isNotEqualTo(userInfos2);
    }
}
