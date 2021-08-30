package fr.crowdfunding.jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Community.
 */
@Entity
@Table(name = "community")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Community implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name_fr")
    private String nameFr;

    @Column(name = "created_at")
    private Instant createdAt;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "about", nullable = false)
    private String about;

    @Lob
    @Column(name = "cover_image")
    private byte[] coverImage;

    @Column(name = "cover_image_content_type")
    private String coverImageContentType;

    @JsonIgnoreProperties(value = { "userInfos" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private CommunityMembers communityMembers;

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
    @OneToOne
    @JoinColumn(unique = true)
    private UserInfos admin;

    @OneToMany(mappedBy = "community")
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

    @OneToMany(mappedBy = "community")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "community" }, allowSetters = true)
    private Set<CommunityNotifications> communityNotifications = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Community id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Community name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameFr() {
        return this.nameFr;
    }

    public Community nameFr(String nameFr) {
        this.nameFr = nameFr;
        return this;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Community createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getAbout() {
        return this.about;
    }

    public Community about(String about) {
        this.about = about;
        return this;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public byte[] getCoverImage() {
        return this.coverImage;
    }

    public Community coverImage(byte[] coverImage) {
        this.coverImage = coverImage;
        return this;
    }

    public void setCoverImage(byte[] coverImage) {
        this.coverImage = coverImage;
    }

    public String getCoverImageContentType() {
        return this.coverImageContentType;
    }

    public Community coverImageContentType(String coverImageContentType) {
        this.coverImageContentType = coverImageContentType;
        return this;
    }

    public void setCoverImageContentType(String coverImageContentType) {
        this.coverImageContentType = coverImageContentType;
    }

    public CommunityMembers getCommunityMembers() {
        return this.communityMembers;
    }

    public Community communityMembers(CommunityMembers communityMembers) {
        this.setCommunityMembers(communityMembers);
        return this;
    }

    public void setCommunityMembers(CommunityMembers communityMembers) {
        this.communityMembers = communityMembers;
    }

    public UserInfos getAdmin() {
        return this.admin;
    }

    public Community admin(UserInfos userInfos) {
        this.setAdmin(userInfos);
        return this;
    }

    public void setAdmin(UserInfos userInfos) {
        this.admin = userInfos;
    }

    public Set<Project> getProjects() {
        return this.projects;
    }

    public Community projects(Set<Project> projects) {
        this.setProjects(projects);
        return this;
    }

    public Community addProjects(Project project) {
        this.projects.add(project);
        project.setCommunity(this);
        return this;
    }

    public Community removeProjects(Project project) {
        this.projects.remove(project);
        project.setCommunity(null);
        return this;
    }

    public void setProjects(Set<Project> projects) {
        if (this.projects != null) {
            this.projects.forEach(i -> i.setCommunity(null));
        }
        if (projects != null) {
            projects.forEach(i -> i.setCommunity(this));
        }
        this.projects = projects;
    }

    public Set<CommunityNotifications> getCommunityNotifications() {
        return this.communityNotifications;
    }

    public Community communityNotifications(Set<CommunityNotifications> communityNotifications) {
        this.setCommunityNotifications(communityNotifications);
        return this;
    }

    public Community addCommunityNotifications(CommunityNotifications communityNotifications) {
        this.communityNotifications.add(communityNotifications);
        communityNotifications.setCommunity(this);
        return this;
    }

    public Community removeCommunityNotifications(CommunityNotifications communityNotifications) {
        this.communityNotifications.remove(communityNotifications);
        communityNotifications.setCommunity(null);
        return this;
    }

    public void setCommunityNotifications(Set<CommunityNotifications> communityNotifications) {
        if (this.communityNotifications != null) {
            this.communityNotifications.forEach(i -> i.setCommunity(null));
        }
        if (communityNotifications != null) {
            communityNotifications.forEach(i -> i.setCommunity(this));
        }
        this.communityNotifications = communityNotifications;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Community)) {
            return false;
        }
        return id != null && id.equals(((Community) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Community{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nameFr='" + getNameFr() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", about='" + getAbout() + "'" +
            ", coverImage='" + getCoverImage() + "'" +
            ", coverImageContentType='" + getCoverImageContentType() + "'" +
            "}";
    }
}
