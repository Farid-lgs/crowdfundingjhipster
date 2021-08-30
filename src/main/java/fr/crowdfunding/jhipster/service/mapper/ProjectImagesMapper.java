package fr.crowdfunding.jhipster.service.mapper;

import fr.crowdfunding.jhipster.domain.*;
import fr.crowdfunding.jhipster.service.dto.ProjectImagesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectImages} and its DTO {@link ProjectImagesDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProjectMapper.class })
public interface ProjectImagesMapper extends EntityMapper<ProjectImagesDTO, ProjectImages> {
    @Mapping(target = "project", source = "project", qualifiedByName = "id")
    ProjectImagesDTO toDto(ProjectImages s);
}
