package fr.crowdfunding.jhipster.service.mapper;

import fr.crowdfunding.jhipster.domain.*;
import fr.crowdfunding.jhipster.service.dto.CommunityMembersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommunityMembers} and its DTO {@link CommunityMembersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommunityMembersMapper extends EntityMapper<CommunityMembersDTO, CommunityMembers> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommunityMembersDTO toDtoId(CommunityMembers communityMembers);
}
