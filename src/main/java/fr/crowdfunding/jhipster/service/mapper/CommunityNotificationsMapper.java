package fr.crowdfunding.jhipster.service.mapper;

import fr.crowdfunding.jhipster.domain.*;
import fr.crowdfunding.jhipster.service.dto.CommunityNotificationsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommunityNotifications} and its DTO {@link CommunityNotificationsDTO}.
 */
@Mapper(componentModel = "spring", uses = { CommunityMapper.class })
public interface CommunityNotificationsMapper extends EntityMapper<CommunityNotificationsDTO, CommunityNotifications> {
    @Mapping(target = "community", source = "community", qualifiedByName = "id")
    CommunityNotificationsDTO toDto(CommunityNotifications s);
}
