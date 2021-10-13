package fr.crowdfunding.jhipster.service.dto;

import fr.crowdfunding.jhipster.domain.enumeration.ProjectStatuts;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class ProjectCardDTO implements Serializable {

    private BigInteger id;

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

    private BigInteger participants;
    private Double amount;
    private BigInteger nbRows;

    private UUID commonId;

    private CommunityDTO community;

    private UserInfosDTO userInfos;

    private CategoryCardDTO category;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
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

    public BigInteger getParticipants() {
        return participants;
    }

    public void setParticipants(BigInteger participants) {
        this.participants = participants;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public BigInteger getNbRows() {
        return nbRows;
    }

    public void setNbRows(BigInteger nbRows) {
        this.nbRows = nbRows;
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

    public CategoryCardDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryCardDTO category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectCardDTO that = (ProjectCardDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(goal, that.goal) && Objects.equals(headline, that.headline) && Objects.equals(videoUrl, that.videoUrl) && Objects.equals(location, that.location) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(description, that.description) && Objects.equals(moreLinks, that.moreLinks) && Objects.equals(budgetDescription, that.budgetDescription) && Objects.equals(duration, that.duration) && Objects.equals(adminNotes, that.adminNotes) && Arrays.equals(coverImage, that.coverImage) && Objects.equals(coverImageContentType, that.coverImageContentType) && status == that.status && Objects.equals(participants, that.participants) && Objects.equals(amount, that.amount) && Objects.equals(commonId, that.commonId) && Objects.equals(community, that.community) && Objects.equals(userInfos, that.userInfos) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, title, goal, headline, videoUrl, location, createdAt, updatedAt, description, moreLinks, budgetDescription, duration, adminNotes, coverImageContentType, status, participants, amount, commonId, community, userInfos, category);
        result = 31 * result + Arrays.hashCode(coverImage);
        return result;
    }

    @Override
    public String toString() {
        return "ProjectCardDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", goal=" + goal +
            ", headline='" + headline + '\'' +
            ", videoUrl='" + videoUrl + '\'' +
            ", location='" + location + '\'' +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            ", description='" + description + '\'' +
            ", moreLinks='" + moreLinks + '\'' +
            ", budgetDescription='" + budgetDescription + '\'' +
            ", duration=" + duration +
            ", adminNotes='" + adminNotes + '\'' +
            ", coverImage=" + Arrays.toString(coverImage) +
            ", coverImageContentType='" + coverImageContentType + '\'' +
            ", status=" + status +
            ", participants=" + participants +
            ", amount=" + amount +
            ", commonId=" + commonId +
            ", community=" + community +
            ", userInfos=" + userInfos +
            ", category=" + category +
            '}';
    }
}
