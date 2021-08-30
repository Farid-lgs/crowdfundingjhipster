package fr.crowdfunding.jhipster.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.crowdfunding.jhipster.domain.ProjectComment} entity.
 */
public class ProjectCommentDTO implements Serializable {

    private Long id;

    @NotNull
    private String comment;

    private UserInfosDTO userInfos;

    private ProjectDTO project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserInfosDTO getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(UserInfosDTO userInfos) {
        this.userInfos = userInfos;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectCommentDTO)) {
            return false;
        }

        ProjectCommentDTO projectCommentDTO = (ProjectCommentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectCommentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectCommentDTO{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", userInfos=" + getUserInfos() +
            ", project=" + getProject() +
            "}";
    }
}
