package fr.crowdfunding.jhipster.service.mapper;

import fr.crowdfunding.jhipster.domain.*;
import fr.crowdfunding.jhipster.service.dto.ContributionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contribution} and its DTO {@link ContributionDTO}.
 */
@Mapper(componentModel = "spring", uses = { ContributionNotificationsMapper.class, UserInfosMapper.class, ProjectMapper.class })
public interface ContributionMapper extends EntityMapper<ContributionDTO, Contribution> {
    @Mapping(target = "contributionNotifications", source = "contributionNotifications", qualifiedByName = "id")
    @Mapping(target = "userInfos", source = "userInfos", qualifiedByName = "id")
    @Mapping(target = "project", source = "project", qualifiedByName = "id")
    ContributionDTO toDto(Contribution s);
}
