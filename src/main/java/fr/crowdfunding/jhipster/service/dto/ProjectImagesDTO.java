package fr.crowdfunding.jhipster.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link fr.crowdfunding.jhipster.domain.ProjectImages} entity.
 */
public class ProjectImagesDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] image;

    private String imageContentType;
    private ProjectDTO project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
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
        if (!(o instanceof ProjectImagesDTO)) {
            return false;
        }

        ProjectImagesDTO projectImagesDTO = (ProjectImagesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectImagesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectImagesDTO{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            ", project=" + getProject() +
            "}";
    }
}
