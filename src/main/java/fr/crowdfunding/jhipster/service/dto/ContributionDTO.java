package fr.crowdfunding.jhipster.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.crowdfunding.jhipster.domain.Contribution} entity.
 */
public class ContributionDTO implements Serializable {

    private Long id;

    @NotNull
    private Double amount;

    @NotNull
    private String payerName;

    private Instant createdAt;

    private Instant updatedAt;

    @NotNull
    private Boolean anonymous;

    @NotNull
    private Boolean rewarded;

    private ContributionNotificationsDTO contributionNotifications;

    private UserInfosDTO userInfos;

    private ProjectDTO project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
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

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Boolean getRewarded() {
        return rewarded;
    }

    public void setRewarded(Boolean rewarded) {
        this.rewarded = rewarded;
    }

    public ContributionNotificationsDTO getContributionNotifications() {
        return contributionNotifications;
    }

    public void setContributionNotifications(ContributionNotificationsDTO contributionNotifications) {
        this.contributionNotifications = contributionNotifications;
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
        if (!(o instanceof ContributionDTO)) {
            return false;
        }

        ContributionDTO contributionDTO = (ContributionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contributionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContributionDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", payerName='" + getPayerName() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", anonymous='" + getAnonymous() + "'" +
            ", rewarded='" + getRewarded() + "'" +
            ", contributionNotifications=" + getContributionNotifications() +
            ", userInfos=" + getUserInfos() +
            ", project=" + getProject() +
            "}";
    }
}
