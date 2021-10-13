package fr.crowdfunding.jhipster.service.dto;

import fr.crowdfunding.jhipster.domain.enumeration.ProjectStatuts;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.crowdfunding.jhipster.domain.Project} entity.
 */
public class ProjectDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private Double goal;

    @NotNull
    private String headline;

    private String videoUrl;

    private String location;

    private Instant createdAt;

    private Instant updatedAt;

    @Lob
    private String description;

    private String moreLinks;

    @Lob
    private String budgetDescription;

    @NotNull
    private Integer duration;

    private String adminNotes;

    @Lob
    private byte[] coverImage;

    private String coverImageContentType;
    private ProjectStatuts status;

    private UUID commonId;

    private CommunityDTO community;

    private UserInfosDTO userInfos;

    private CategoryDTO category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getGoal() {
        return goal;
    }

    public void setGoal(Double goal) {
        this.goal = goal;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMoreLinks() {
        return moreLinks;
    }

    public void setMoreLinks(String moreLinks) {
        this.moreLinks = moreLinks;
    }

    public String getBudgetDescription() {
        return budgetDescription;
    }

    public void setBudgetDescription(String budgetDescription) {
        this.budgetDescription = budgetDescription;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getAdminNotes() {
        return adminNotes;
    }

    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }

    public byte[] getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(byte[] coverImage) {
        this.coverImage = coverImage;
    }

    public String getCoverImageContentType() {
        return coverImageContentType;
    }

    public void setCoverImageContentType(String coverImageContentType) {
        this.coverImageContentType = coverImageContentType;
    }

    public ProjectStatuts getStatus() {
        return status;
    }

    public void setStatus(ProjectStatuts status) {
        this.status = status;
    }

    public UUID getCommonId() {
        return commonId;
    }

    public void setCommonId(UUID commonId) {
        this.commonId = commonId;
    }

    public CommunityDTO getCommunity() {
        return community;
    }

    public void setCommunity(CommunityDTO community) {
        this.community = community;
    }

    public UserInfosDTO getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(UserInfosDTO userInfos) {
        this.userInfos = userInfos;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectDTO)) {
            return false;
        }

        ProjectDTO projectDTO = (ProjectDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", goal=" + getGoal() +
            ", headline='" + getHeadline() + "'" +
            ", videoUrl='" + getVideoUrl() + "'" +
            ", location='" + getLocation() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", description='" + getDescription() + "'" +
            ", moreLinks='" + getMoreLinks() + "'" +
            ", budgetDescription='" + getBudgetDescription() + "'" +
            ", duration=" + getDuration() +
            ", adminNotes='" + getAdminNotes() + "'" +
            ", coverImage='" + getCoverImage() + "'" +
            ", status='" + getStatus() + "'" +
            ", commonId='" + getCommonId() + "'" +
            ", community=" + getCommunity() +
            ", userInfos=" + getUserInfos() +
            ", category=" + getCategory() +
            "}";
    }
}
