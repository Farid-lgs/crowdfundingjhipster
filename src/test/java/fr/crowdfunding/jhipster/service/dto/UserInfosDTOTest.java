package fr.crowdfunding.jhipster.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.crowdfunding.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserInfosDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserInfosDTO.class);
        UserInfosDTO userInfosDTO1 = new UserInfosDTO();
        userInfosDTO1.setId(1L);
        UserInfosDTO userInfosDTO2 = new UserInfosDTO();
        assertThat(userInfosDTO1).isNotEqualTo(userInfosDTO2);
        userInfosDTO2.setId(userInfosDTO1.getId());
        assertThat(userInfosDTO1).isEqualTo(userInfosDTO2);
        userInfosDTO2.setId(2L);
        assertThat(userInfosDTO1).isNotEqualTo(userInfosDTO2);
        userInfosDTO1.setId(null);
        assertThat(userInfosDTO1).isNotEqualTo(userInfosDTO2);
    }
}
