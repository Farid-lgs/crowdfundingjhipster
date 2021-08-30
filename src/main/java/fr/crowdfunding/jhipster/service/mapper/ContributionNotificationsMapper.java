package fr.crowdfunding.jhipster.service.mapper;

import fr.crowdfunding.jhipster.domain.*;
import fr.crowdfunding.jhipster.service.dto.ContributionNotificationsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContributionNotifications} and its DTO {@link ContributionNotificationsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContributionNotificationsMapper extends EntityMapper<ContributionNotificationsDTO, ContributionNotifications> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContributionNotificationsDTO toDtoId(ContributionNotifications contributionNotifications);
}
