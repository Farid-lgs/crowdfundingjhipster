package fr.crowdfunding.jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CommunityMembers.
 */
@Entity
@Table(name = "community_members")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CommunityMembers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToMany(mappedBy = "communityMembers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<UserInfos> userInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CommunityMembers id(Long id) {
        this.id = id;
        return this;
    }

    public Set<UserInfos> getUserInfos() {
        return this.userInfos;
    }

    public CommunityMembers userInfos(Set<UserInfos> userInfos) {
        this.setUserInfos(userInfos);
        return this;
    }

    public CommunityMembers addUserInfos(UserInfos userInfos) {
        this.userInfos.add(userInfos);
        userInfos.setCommunityMembers(this);
        return this;
    }

    public CommunityMembers removeUserInfos(UserInfos userInfos) {
        this.userInfos.remove(userInfos);
        userInfos.setCommunityMembers(null);
        return this;
    }

    public void setUserInfos(Set<UserInfos> userInfos) {
        if (this.userInfos != null) {
            this.userInfos.forEach(i -> i.setCommunityMembers(null));
        }
        if (userInfos != null) {
            userInfos.forEach(i -> i.setCommunityMembers(this));
        }
        this.userInfos = userInfos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommunityMembers)) {
            return false;
        }
        return id != null && id.equals(((CommunityMembers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommunityMembers{" +
            "id=" + getId() +
            "}";
    }
}
