package fr.crowdfunding.jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A UserInfos.
 */
@Entity
@Table(name = "user_infos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserInfos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Column(name = "public_name", nullable = false)
    private String publicName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "linked_in")
    private String linkedIn;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "cover_image")
    private byte[] coverImage;

    @Column(name = "cover_image_content_type")
    private String coverImageContentType;

    @Column(name = "common_id")
    private UUID commonId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToMany(mappedBy = "userInfos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contributionNotifications", "userInfos", "project" }, allowSetters = true)
    private Set<Contribution> contributions = new HashSet<>();

    @OneToMany(mappedBy = "userInfos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Project> projects = new HashSet<>();

    @OneToMany(mappedBy = "userInfos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userInfos", "project" }, allowSetters = true)
    private Set<ProjectComment> projectPosts = new HashSet<>();

    @JsonIgnoreProperties(value = { "communityMembers", "admin", "projects", "communityNotifications" }, allowSetters = true)
    @OneToOne(mappedBy = "admin")
    private Community community;

    @JsonIgnoreProperties(value = { "userInfos", "country" }, allowSetters = true)
    @OneToOne(mappedBy = "userInfos")
    private Address address;

    @JsonIgnoreProperties(value = { "userInfos", "project" }, allowSetters = true)
    @OneToOne(mappedBy = "userInfos")
    private BalanceTransfer balanceTransfer;

    @JsonIgnoreProperties(value = { "userInfos" }, allowSetters = true)
    @OneToOne(mappedBy = "userInfos")
    private CreditCard creditCard;

    @ManyToOne
    @JsonIgnoreProperties(value = { "userInfos" }, allowSetters = true)
    private CommunityMembers communityMembers;

    public UserInfos() {
    }
//    public UserInfos(UserInfos userInfos) {
//
//    }

    public UserInfos(UserInfos userInfos) {
        this.id = userInfos.getId();
        this.publicName = userInfos.getPublicName();
        this.birthDate = userInfos.getBirthDate();
        this.twitter = userInfos.getTwitter();
        this.facebook = userInfos.getFacebook();
        this.linkedIn = userInfos.getLinkedIn();
        this.description = userInfos.getDescription();
        this.coverImage = userInfos.getCoverImage();
        this.coverImageContentType = userInfos.getCoverImageContentType();
        this.commonId = userInfos.getCommonId();
        this.user = userInfos.getUser();
        this.contributions = userInfos.getContributions();
        this.projects = userInfos.getProjects();
        this.projectPosts = userInfos.getProjectPosts();
        this.community = userInfos.getCommunity();
        this.address = userInfos.getAddress();
        this.balanceTransfer = userInfos.getBalanceTransfer();
        this.creditCard = userInfos.getCreditCard();
        this.communityMembers = userInfos.getCommunityMembers();
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserInfos id(Long id) {
        this.id = id;
        return this;
    }

    public String getPublicName() {
        return this.publicName;
    }

    public UserInfos publicName(String publicName) {
        this.publicName = publicName;
        return this;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public UserInfos birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getTwitter() {
        return this.twitter;
    }

    public UserInfos twitter(String twitter) {
        this.twitter = twitter;
        return this;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return this.facebook;
    }

    public UserInfos facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getLinkedIn() {
        return this.linkedIn;
    }

    public UserInfos linkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
        return this;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getDescription() {
        return this.description;
    }

    public UserInfos description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getCoverImage() {
        return this.coverImage;
    }

    public UserInfos coverImage(byte[] coverImage) {
        this.coverImage = coverImage;
        return this;
    }

    public void setCoverImage(byte[] coverImage) {
        this.coverImage = coverImage;
    }

    public String getCoverImageContentType() {
        return this.coverImageContentType;
    }

    public UserInfos coverImageContentType(String coverImageContentType) {
        this.coverImageContentType = coverImageContentType;
        return this;
    }

    public void setCoverImageContentType(String coverImageContentType) {
        this.coverImageContentType = coverImageContentType;
    }

    public UUID getCommonId() {
        return this.commonId;
    }

    public UserInfos commonId(UUID commonId) {
        this.commonId = commonId;
        return this;
    }

    public void setCommonId(UUID commonId) {
        this.commonId = commonId;
    }

    public User getUser() {
        return this.user;
    }

    public UserInfos user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Contribution> getContributions() {
        return this.contributions;
    }

    public UserInfos contributions(Set<Contribution> contributions) {
        this.setContributions(contributions);
        return this;
    }

    public UserInfos addContributions(Contribution contribution) {
        this.contributions.add(contribution);
        contribution.setUserInfos(this);
        return this;
    }

    public UserInfos removeContributions(Contribution contribution) {
        this.contributions.remove(contribution);
        contribution.setUserInfos(null);
        return this;
    }

    public void setContributions(Set<Contribution> contributions) {
        if (this.contributions != null) {
            this.contributions.forEach(i -> i.setUserInfos(null));
        }
        if (contributions != null) {
            contributions.forEach(i -> i.setUserInfos(this));
        }
        this.contributions = contributions;
    }

    public Set<Project> getProjects() {
        return this.projects;
    }

    public UserInfos projects(Set<Project> projects) {
        this.setProjects(projects);
        return this;
    }

    public UserInfos addProject(Project project) {
        this.projects.add(project);
        project.setUserInfos(this);
        return this;
    }

    public UserInfos removeProject(Project project) {
        this.projects.remove(project);
        project.setUserInfos(null);
        return this;
    }

    public void setProjects(Set<Project> projects) {
        if (this.projects != null) {
            this.projects.forEach(i -> i.setUserInfos(null));
        }
        if (projects != null) {
            projects.forEach(i -> i.setUserInfos(this));
        }
        this.projects = projects;
    }

    public Set<ProjectComment> getProjectPosts() {
        return this.projectPosts;
    }

    public UserInfos projectPosts(Set<ProjectComment> projectComments) {
        this.setProjectPosts(projectComments);
        return this;
    }

    public UserInfos addProjectPosts(ProjectComment projectComment) {
        this.projectPosts.add(projectComment);
        projectComment.setUserInfos(this);
        return this;
    }

    public UserInfos removeProjectPosts(ProjectComment projectComment) {
        this.projectPosts.remove(projectComment);
        projectComment.setUserInfos(null);
        return this;
    }

    public void setProjectPosts(Set<ProjectComment> projectComments) {
        if (this.projectPosts != null) {
            this.projectPosts.forEach(i -> i.setUserInfos(null));
        }
        if (projectComments != null) {
            projectComments.forEach(i -> i.setUserInfos(this));
        }
        this.projectPosts = projectComments;
    }

    public Community getCommunity() {
        return this.community;
    }

    public UserInfos community(Community community) {
        this.setCommunity(community);
        return this;
    }

    public void setCommunity(Community community) {
        if (this.community != null) {
            this.community.setAdmin(null);
        }
        if (community != null) {
            community.setAdmin(this);
        }
        this.community = community;
    }

    public Address getAddress() {
        return this.address;
    }

    public UserInfos address(Address address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(Address address) {
        if (this.address != null) {
            this.address.setUserInfos(null);
        }
        if (address != null) {
            address.setUserInfos(this);
        }
        this.address = address;
    }

    public BalanceTransfer getBalanceTransfer() {
        return this.balanceTransfer;
    }

    public UserInfos balanceTransfer(BalanceTransfer balanceTransfer) {
        this.setBalanceTransfer(balanceTransfer);
        return this;
    }

    public void setBalanceTransfer(BalanceTransfer balanceTransfer) {
        if (this.balanceTransfer != null) {
            this.balanceTransfer.setUserInfos(null);
        }
        if (balanceTransfer != null) {
            balanceTransfer.setUserInfos(this);
        }
        this.balanceTransfer = balanceTransfer;
    }

    public CreditCard getCreditCard() {
        return this.creditCard;
    }

    public UserInfos creditCard(CreditCard creditCard) {
        this.setCreditCard(creditCard);
        return this;
    }

    public void setCreditCard(CreditCard creditCard) {
        if (this.creditCard != null) {
            this.creditCard.setUserInfos(null);
        }
        if (creditCard != null) {
            creditCard.setUserInfos(this);
        }
        this.creditCard = creditCard;
    }

    public CommunityMembers getCommunityMembers() {
        return this.communityMembers;
    }

    public UserInfos communityMembers(CommunityMembers communityMembers) {
        this.setCommunityMembers(communityMembers);
        return this;
    }

    public void setCommunityMembers(CommunityMembers communityMembers) {
        this.communityMembers = communityMembers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInfos)) {
            return false;
        }
        return id != null && id.equals(((UserInfos) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInfos{" +
            "id=" + getId() +
            ", publicName='" + getPublicName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", twitter='" + getTwitter() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", linkedIn='" + getLinkedIn() + "'" +
            ", description='" + getDescription() + "'" +
            ", coverImage='" + getCoverImage() + "'" +
            ", coverImageContentType='" + getCoverImageContentType() + "'" +
            ", commonId='" + getCommonId() + "'" +
            "}";
    }
}
