package fr.crowdfunding.jhipster.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.crowdfunding.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectImagesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectImages.class);
        ProjectImages projectImages1 = new ProjectImages();
        projectImages1.setId(1L);
        ProjectImages projectImages2 = new ProjectImages();
        projectImages2.setId(projectImages1.getId());
        assertThat(projectImages1).isEqualTo(projectImages2);
        projectImages2.setId(2L);
        assertThat(projectImages1).isNotEqualTo(projectImages2);
        projectImages1.setId(null);
        assertThat(projectImages1).isNotEqualTo(projectImages2);
    }
}
