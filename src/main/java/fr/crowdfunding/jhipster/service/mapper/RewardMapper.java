package fr.crowdfunding.jhipster.service.mapper;

import fr.crowdfunding.jhipster.domain.*;
import fr.crowdfunding.jhipster.service.dto.RewardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reward} and its DTO {@link RewardDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProjectMapper.class })
public interface RewardMapper extends EntityMapper<RewardDTO, Reward> {
    @Mapping(target = "project", source = "project", qualifiedByName = "id")
    RewardDTO toDto(Reward s);
}
