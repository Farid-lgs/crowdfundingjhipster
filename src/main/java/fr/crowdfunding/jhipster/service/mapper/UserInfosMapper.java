package fr.crowdfunding.jhipster.service.mapper;

import fr.crowdfunding.jhipster.domain.*;
import fr.crowdfunding.jhipster.service.dto.UserInfosDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserInfos} and its DTO {@link UserInfosDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, CommunityMembersMapper.class })
public interface UserInfosMapper extends EntityMapper<UserInfosDTO, UserInfos> {
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    @Mapping(target = "communityMembers", source = "communityMembers", qualifiedByName = "id")
    UserInfosDTO toDto(UserInfos s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "user", source = "user")
    UserInfosDTO toDtoId(UserInfos userInfos);
}
