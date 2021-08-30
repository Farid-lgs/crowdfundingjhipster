package fr.crowdfunding.jhipster.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.crowdfunding.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectAccountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectAccount.class);
        ProjectAccount projectAccount1 = new ProjectAccount();
        projectAccount1.setId(1L);
        ProjectAccount projectAccount2 = new ProjectAccount();
        projectAccount2.setId(projectAccount1.getId());
        assertThat(projectAccount1).isEqualTo(projectAccount2);
        projectAccount2.setId(2L);
        assertThat(projectAccount1).isNotEqualTo(projectAccount2);
        projectAccount1.setId(null);
        assertThat(projectAccount1).isNotEqualTo(projectAccount2);
    }
}
