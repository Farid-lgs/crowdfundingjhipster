package fr.crowdfunding.jhipster.service.mapper;

import fr.crowdfunding.jhipster.domain.*;
import fr.crowdfunding.jhipster.service.dto.ProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Project} and its DTO {@link ProjectDTO}.
 */
@Mapper(componentModel = "spring", uses = { CommunityMapper.class, UserInfosMapper.class, CategoryMapper.class })
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {
    @Mapping(target = "community", source = "community", qualifiedByName = "id")
    @Mapping(target = "userInfos", source = "userInfos", qualifiedByName = "id")
    @Mapping(target = "category", source = "category", qualifiedByName = "id")
    ProjectDTO toDto(Project s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectDTO toDtoId(Project project);
}
