package fr.crowdfunding.jhipster.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectCommentMapperTest {

    private ProjectCommentMapper projectCommentMapper;

    @BeforeEach
    public void setUp() {
        projectCommentMapper = new ProjectCommentMapperImpl();
    }
}
