package fr.crowdfunding.jhipster.service.mapper;

import fr.crowdfunding.jhipster.domain.*;
import fr.crowdfunding.jhipster.service.dto.CommunityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Community} and its DTO {@link CommunityDTO}.
 */
@Mapper(componentModel = "spring", uses = { CommunityMembersMapper.class, UserInfosMapper.class })
public interface CommunityMapper extends EntityMapper<CommunityDTO, Community> {
    @Mapping(target = "communityMembers", source = "communityMembers", qualifiedByName = "id")
    @Mapping(target = "admin", source = "admin", qualifiedByName = "id")
    CommunityDTO toDto(Community s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommunityDTO toDtoId(Community community);
}
