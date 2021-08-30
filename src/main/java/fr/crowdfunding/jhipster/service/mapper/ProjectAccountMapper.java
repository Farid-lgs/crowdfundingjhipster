package fr.crowdfunding.jhipster.service.mapper;

import fr.crowdfunding.jhipster.domain.*;
import fr.crowdfunding.jhipster.service.dto.ProjectAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectAccount} and its DTO {@link ProjectAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProjectMapper.class })
public interface ProjectAccountMapper extends EntityMapper<ProjectAccountDTO, ProjectAccount> {
    @Mapping(target = "project", source = "project", qualifiedByName = "id")
    ProjectAccountDTO toDto(ProjectAccount s);
}
