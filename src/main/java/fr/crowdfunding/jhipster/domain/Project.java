package fr.crowdfunding.jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.crowdfunding.jhipster.domain.enumeration.ProjectStatuts;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "goal", nullable = false)
    private Double goal;

    @NotNull
    @Column(name = "headline", nullable = false)
    private String headline;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "location")
    private String location;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Column(name = "more_links")
    private String moreLinks;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "budget_description")
    private String budgetDescription;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "admin_notes")
    private String adminNotes;

    @Lob
    @Column(name = "cover_image")
    private byte[] coverImage;

    @Column(name = "cover_image_content_type")
    private String coverImageContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProjectStatuts status;

    @Column(name = "common_id")
    private UUID commonId;

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "project" }, allowSetters = true)
    private Set<ProjectImages> projectImages = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contributionNotifications", "userInfos", "project" }, allowSetters = true)
    private Set<Contribution> contributions = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "project" }, allowSetters = true)
    private Set<ProjectAccount> projectAccounts = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userInfos", "project" }, allowSetters = true)
    private Set<ProjectComment> projectPosts = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "project" }, allowSetters = true)
    private Set<Reward> rewards = new HashSet<>();

    @JsonIgnoreProperties(value = { "userInfos", "project" }, allowSetters = true)
    @OneToOne(mappedBy = "project")
    private BalanceTransfer balanceTransfer;

    @ManyToOne
    @JsonIgnoreProperties(value = { "communityMembers", "admin", "projects", "communityNotifications" }, allowSetters = true)
    private Community community;

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
    @JsonIgnoreProperties(value = { "projects" }, allowSetters = true)
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Project title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getGoal() {
        return this.goal;
    }

    public Project goal(Double goal) {
        this.goal = goal;
        return this;
    }

    public void setGoal(Double goal) {
        this.goal = goal;
    }

    public String getHeadline() {
        return this.headline;
    }

    public Project headline(String headline) {
        this.headline = headline;
        return this;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public Project videoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getLocation() {
        return this.location;
    }

    public Project location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Project createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Project updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDescription() {
        return this.description;
    }

    public Project description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMoreLinks() {
        return this.moreLinks;
    }

    public Project moreLinks(String moreLinks) {
        this.moreLinks = moreLinks;
        return this;
    }

    public void setMoreLinks(String moreLinks) {
        this.moreLinks = moreLinks;
    }

    public String getBudgetDescription() {
        return this.budgetDescription;
    }

    public Project budgetDescription(String budgetDescription) {
        this.budgetDescription = budgetDescription;
        return this;
    }

    public void setBudgetDescription(String budgetDescription) {
        this.budgetDescription = budgetDescription;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Project duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getAdminNotes() {
        return this.adminNotes;
    }

    public Project adminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
        return this;
    }

    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }

    public byte[] getCoverImage() {
        return this.coverImage;
    }

    public Project coverImage(byte[] coverImage) {
        this.coverImage = coverImage;
        return this;
    }

    public void setCoverImage(byte[] coverImage) {
        this.coverImage = coverImage;
    }

    public String getCoverImageContentType() {
        return this.coverImageContentType;
    }

    public Project coverImageContentType(String coverImageContentType) {
        this.coverImageContentType = coverImageContentType;
        return this;
    }

    public void setCoverImageContentType(String coverImageContentType) {
        this.coverImageContentType = coverImageContentType;
    }

    public ProjectStatuts getStatus() {
        return this.status;
    }

    public Project status(ProjectStatuts status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProjectStatuts status) {
        this.status = status;
    }

    public UUID getCommonId() {
        return this.commonId;
    }

    public Project commonId(UUID commonId) {
        this.commonId = commonId;
        return this;
    }

    public void setCommonId(UUID commonId) {
        this.commonId = commonId;
    }

    public Set<ProjectImages> getProjectImages() {
        return this.projectImages;
    }

    public Project projectImages(Set<ProjectImages> projectImages) {
        this.setProjectImages(projectImages);
        return this;
    }

    public Project addProjectImages(ProjectImages projectImages) {
        this.projectImages.add(projectImages);
        projectImages.setProject(this);
        return this;
    }

    public Project removeProjectImages(ProjectImages projectImages) {
        this.projectImages.remove(projectImages);
        projectImages.setProject(null);
        return this;
    }

    public void setProjectImages(Set<ProjectImages> projectImages) {
        if (this.projectImages != null) {
            this.projectImages.forEach(i -> i.setProject(null));
        }
        if (projectImages != null) {
            projectImages.forEach(i -> i.setProject(this));
        }
        this.projectImages = projectImages;
    }

    public Set<Contribution> getContributions() {
        return this.contributions;
    }

    public Project contributions(Set<Contribution> contributions) {
        this.setContributions(contributions);
        return this;
    }

    public Project addContributions(Contribution contribution) {
        this.contributions.add(contribution);
        contribution.setProject(this);
        return this;
    }

    public Project removeContributions(Contribution contribution) {
        this.contributions.remove(contribution);
        contribution.setProject(null);
        return this;
    }

    public void setContributions(Set<Contribution> contributions) {
        if (this.contributions != null) {
            this.contributions.forEach(i -> i.setProject(null));
        }
        if (contributions != null) {
            contributions.forEach(i -> i.setProject(this));
        }
        this.contributions = contributions;
    }

    public Set<ProjectAccount> getProjectAccounts() {
        return this.projectAccounts;
    }

    public Project projectAccounts(Set<ProjectAccount> projectAccounts) {
        this.setProjectAccounts(projectAccounts);
        return this;
    }

    public Project addProjectAccount(ProjectAccount projectAccount) {
        this.projectAccounts.add(projectAccount);
        projectAccount.setProject(this);
        return this;
    }

    public Project removeProjectAccount(ProjectAccount projectAccount) {
        this.projectAccounts.remove(projectAccount);
        projectAccount.setProject(null);
        return this;
    }

    public void setProjectAccounts(Set<ProjectAccount> projectAccounts) {
        if (this.projectAccounts != null) {
            this.projectAccounts.forEach(i -> i.setProject(null));
        }
        if (projectAccounts != null) {
            projectAccounts.forEach(i -> i.setProject(this));
        }
        this.projectAccounts = projectAccounts;
    }

    public Set<ProjectComment> getProjectPosts() {
        return this.projectPosts;
    }

    public Project projectPosts(Set<ProjectComment> projectComments) {
        this.setProjectPosts(projectComments);
        return this;
    }

    public Project addProjectPost(ProjectComment projectComment) {
        this.projectPosts.add(projectComment);
        projectComment.setProject(this);
        return this;
    }

    public Project removeProjectPost(ProjectComment projectComment) {
        this.projectPosts.remove(projectComment);
        projectComment.setProject(null);
        return this;
    }

    public void setProjectPosts(Set<ProjectComment> projectComments) {
        if (this.projectPosts != null) {
            this.projectPosts.forEach(i -> i.setProject(null));
        }
        if (projectComments != null) {
            projectComments.forEach(i -> i.setProject(this));
        }
        this.projectPosts = projectComments;
    }

    public Set<Reward> getRewards() {
        return this.rewards;
    }

    public Project rewards(Set<Reward> rewards) {
        this.setRewards(rewards);
        return this;
    }

    public Project addReward(Reward reward) {
        this.rewards.add(reward);
        reward.setProject(this);
        return this;
    }

    public Project removeReward(Reward reward) {
        this.rewards.remove(reward);
        reward.setProject(null);
        return this;
    }

    public void setRewards(Set<Reward> rewards) {
        if (this.rewards != null) {
            this.rewards.forEach(i -> i.setProject(null));
        }
        if (rewards != null) {
            rewards.forEach(i -> i.setProject(this));
        }
        this.rewards = rewards;
    }

    public BalanceTransfer getBalanceTransfer() {
        return this.balanceTransfer;
    }

    public Project balanceTransfer(BalanceTransfer balanceTransfer) {
        this.setBalanceTransfer(balanceTransfer);
        return this;
    }

    public void setBalanceTransfer(BalanceTransfer balanceTransfer) {
        if (this.balanceTransfer != null) {
            this.balanceTransfer.setProject(null);
        }
        if (balanceTransfer != null) {
            balanceTransfer.setProject(this);
        }
        this.balanceTransfer = balanceTransfer;
    }

    public Community getCommunity() {
        return this.community;
    }

    public Project community(Community community) {
        this.setCommunity(community);
        return this;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public UserInfos getUserInfos() {
        return this.userInfos;
    }

    public Project userInfos(UserInfos userInfos) {
        this.setUserInfos(userInfos);
        return this;
    }

    public void setUserInfos(UserInfos userInfos) {
        this.userInfos = userInfos;
    }

    public Category getCategory() {
        return this.category;
    }

    public Project category(Category category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
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
            ", coverImageContentType='" + getCoverImageContentType() + "'" +
            ", status='" + getStatus() + "'" +
            ", commonId='" + getCommonId() + "'" +
            "}";
    }
}
