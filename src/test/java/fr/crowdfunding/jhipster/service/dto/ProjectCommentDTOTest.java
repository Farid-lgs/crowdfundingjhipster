package fr.crowdfunding.jhipster.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.crowdfunding.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectCommentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectCommentDTO.class);
        ProjectCommentDTO projectCommentDTO1 = new ProjectCommentDTO();
        projectCommentDTO1.setId(1L);
        ProjectCommentDTO projectCommentDTO2 = new ProjectCommentDTO();
        assertThat(projectCommentDTO1).isNotEqualTo(projectCommentDTO2);
        projectCommentDTO2.setId(projectCommentDTO1.getId());
        assertThat(projectCommentDTO1).isEqualTo(projectCommentDTO2);
        projectCommentDTO2.setId(2L);
        assertThat(projectCommentDTO1).isNotEqualTo(projectCommentDTO2);
        projectCommentDTO1.setId(null);
        assertThat(projectCommentDTO1).isNotEqualTo(projectCommentDTO2);
    }
}
