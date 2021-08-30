package fr.crowdfunding.jhipster.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserInfosMapperTest {

    private UserInfosMapper userInfosMapper;

    @BeforeEach
    public void setUp() {
        userInfosMapper = new UserInfosMapperImpl();
    }
}
