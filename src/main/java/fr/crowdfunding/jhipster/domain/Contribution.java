package fr.crowdfunding.jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Contribution.
 */
@Entity
@Table(name = "contribution")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contribution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "payer_name", nullable = false)
    private String payerName;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @NotNull
    @Column(name = "anonymous", nullable = false)
    private Boolean anonymous = false;

    @NotNull
    @Column(name = "rewarded", nullable = false)
    private Boolean rewarded = false;

    @JsonIgnoreProperties(value = { "contribution" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ContributionNotifications contributionNotifications;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "user",
            "contributions",
            "projects",
            "projectPosts",
            "community",
            "address",
            "balanceTransfer",
            "creditCard",
            "communityMembers",
        },
        allowSetters = true
    )
    private UserInfos userInfos;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "projectImages",
            "contributions",
            "projectAccounts",
            "projectPosts",
            "rewards",
            "balanceTransfer",
            "community",
            "userInfos",
            "category",
        },
        allowSetters = true
    )
    private Project project;

    public Contribution() {
    }

    public Contribution(Double amount, String payerName, UserInfos userInfos, Project project) {
        this.amount = amount;
        this.payerName = payerName;
        this.userInfos = userInfos;
        this.project = project;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contribution id(Long id) {
        this.id = id;
        return this;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Contribution amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPayerName() {
        return this.payerName;
    }

    public Contribution payerName(String payerName) {
        this.payerName = payerName;
        return this;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Contribution createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Contribution updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getAnonymous() {
        return this.anonymous;
    }

    public Contribution anonymous(Boolean anonymous) {
        this.anonymous = anonymous;
        return this;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Boolean getRewarded() {
        return this.rewarded;
    }

    public Contribution rewarded(Boolean rewarded) {
        this.rewarded = rewarded;
        return this;
    }

    public void setRewarded(Boolean rewarded) {
        this.rewarded = rewarded;
    }

    public ContributionNotifications getContributionNotifications() {
        return this.contributionNotifications;
    }

    public Contribution contributionNotifications(ContributionNotifications contributionNotifications) {
        this.setContributionNotifications(contributionNotifications);
        return this;
    }

    public void setContributionNotifications(ContributionNotifications contributionNotifications) {
        this.contributionNotifications = contributionNotifications;
    }

    public UserInfos getUserInfos() {
        return this.userInfos;
    }

    public Contribution userInfos(UserInfos userInfos) {
        this.setUserInfos(userInfos);
        return this;
    }

    public void setUserInfos(UserInfos userInfos) {
        this.userInfos = userInfos;
    }

    public Project getProject() {
        return this.project;
    }

    public Contribution project(Project project) {
        this.setProject(project);
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contribution)) {
            return false;
        }
        return id != null && id.equals(((Contribution) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contribution{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", payerName='" + getPayerName() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", anonymous='" + getAnonymous() + "'" +
            ", rewarded='" + getRewarded() + "'" +
            "}";
    }
}
