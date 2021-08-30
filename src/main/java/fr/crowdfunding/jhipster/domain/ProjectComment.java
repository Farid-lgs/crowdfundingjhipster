package fr.crowdfunding.jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProjectComment.
 */
@Entity
@Table(name = "project_comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "comment", nullable = false)
    private String comment;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectComment id(Long id) {
        this.id = id;
        return this;
    }

    public String getComment() {
        return this.comment;
    }

    public ProjectComment comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserInfos getUserInfos() {
        return this.userInfos;
    }

    public ProjectComment userInfos(UserInfos userInfos) {
        this.setUserInfos(userInfos);
        return this;
    }

    public void setUserInfos(UserInfos userInfos) {
        this.userInfos = userInfos;
    }

    public Project getProject() {
        return this.project;
    }

    public ProjectComment project(Project project) {
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
        if (!(o instanceof ProjectComment)) {
            return false;
        }
        return id != null && id.equals(((ProjectComment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectComment{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
