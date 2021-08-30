package fr.crowdfunding.jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContributionNotifications.
 */
@Entity
@Table(name = "contribution_notifications")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContributionNotifications implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "sent_at")
    private Instant sentAt;

    @JsonIgnoreProperties(value = { "contributionNotifications", "userInfos", "project" }, allowSetters = true)
    @OneToOne(mappedBy = "contributionNotifications")
    private Contribution contribution;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContributionNotifications id(Long id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return this.content;
    }

    public ContributionNotifications content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getSentAt() {
        return this.sentAt;
    }

    public ContributionNotifications sentAt(Instant sentAt) {
        this.sentAt = sentAt;
        return this;
    }

    public void setSentAt(Instant sentAt) {
        this.sentAt = sentAt;
    }

    public Contribution getContribution() {
        return this.contribution;
    }

    public ContributionNotifications contribution(Contribution contribution) {
        this.setContribution(contribution);
        return this;
    }

    public void setContribution(Contribution contribution) {
        if (this.contribution != null) {
            this.contribution.setContributionNotifications(null);
        }
        if (contribution != null) {
            contribution.setContributionNotifications(this);
        }
        this.contribution = contribution;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContributionNotifications)) {
            return false;
        }
        return id != null && id.equals(((ContributionNotifications) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContributionNotifications{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", sentAt='" + getSentAt() + "'" +
            "}";
    }
}
