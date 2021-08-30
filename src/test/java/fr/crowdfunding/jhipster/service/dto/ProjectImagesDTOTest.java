package fr.crowdfunding.jhipster.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.crowdfunding.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectImagesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectImagesDTO.class);
        ProjectImagesDTO projectImagesDTO1 = new ProjectImagesDTO();
        projectImagesDTO1.setId(1L);
        ProjectImagesDTO projectImagesDTO2 = new ProjectImagesDTO();
        assertThat(projectImagesDTO1).isNotEqualTo(projectImagesDTO2);
        projectImagesDTO2.setId(projectImagesDTO1.getId());
        assertThat(projectImagesDTO1).isEqualTo(projectImagesDTO2);
        projectImagesDTO2.setId(2L);
        assertThat(projectImagesDTO1).isNotEqualTo(projectImagesDTO2);
        projectImagesDTO1.setId(null);
        assertThat(projectImagesDTO1).isNotEqualTo(projectImagesDTO2);
    }
}
