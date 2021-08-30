package fr.crowdfunding.jhipster.service.mapper;

import fr.crowdfunding.jhipster.domain.*;
import fr.crowdfunding.jhipster.service.dto.ProjectCommentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectComment} and its DTO {@link ProjectCommentDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfosMapper.class, ProjectMapper.class })
public interface ProjectCommentMapper extends EntityMapper<ProjectCommentDTO, ProjectComment> {
    @Mapping(target = "userInfos", source = "userInfos", qualifiedByName = "id")
    @Mapping(target = "project", source = "project", qualifiedByName = "id")
    ProjectCommentDTO toDto(ProjectComment s);
}
