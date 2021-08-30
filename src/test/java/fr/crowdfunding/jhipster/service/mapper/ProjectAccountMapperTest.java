package fr.crowdfunding.jhipster.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectAccountMapperTest {

    private ProjectAccountMapper projectAccountMapper;

    @BeforeEach
    public void setUp() {
        projectAccountMapper = new ProjectAccountMapperImpl();
    }
}
