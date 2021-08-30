package fr.crowdfunding.jhipster.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectImagesMapperTest {

    private ProjectImagesMapper projectImagesMapper;

    @BeforeEach
    public void setUp() {
        projectImagesMapper = new ProjectImagesMapperImpl();
    }
}
