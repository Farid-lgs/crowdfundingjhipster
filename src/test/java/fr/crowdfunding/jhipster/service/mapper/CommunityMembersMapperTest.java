package fr.crowdfunding.jhipster.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommunityMembersMapperTest {

    private CommunityMembersMapper communityMembersMapper;

    @BeforeEach
    public void setUp() {
        communityMembersMapper = new CommunityMembersMapperImpl();
    }
}
