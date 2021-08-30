package fr.crowdfunding.jhipster.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.crowdfunding.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectAccountDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectAccountDTO.class);
        ProjectAccountDTO projectAccountDTO1 = new ProjectAccountDTO();
        projectAccountDTO1.setId(1L);
        ProjectAccountDTO projectAccountDTO2 = new ProjectAccountDTO();
        assertThat(projectAccountDTO1).isNotEqualTo(projectAccountDTO2);
        projectAccountDTO2.setId(projectAccountDTO1.getId());
        assertThat(projectAccountDTO1).isEqualTo(projectAccountDTO2);
        projectAccountDTO2.setId(2L);
        assertThat(projectAccountDTO1).isNotEqualTo(projectAccountDTO2);
        projectAccountDTO1.setId(null);
        assertThat(projectAccountDTO1).isNotEqualTo(projectAccountDTO2);
    }
}
