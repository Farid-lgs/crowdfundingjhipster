package fr.crowdfunding.jhipster.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link fr.crowdfunding.jhipster.domain.CommunityNotifications} entity.
 */
public class CommunityNotificationsDTO implements Serializable {

    private Long id;

    private String content;

    private Instant sentAt;

    private CommunityDTO community;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public void setSentAt(Instant sentAt) {
        this.sentAt = sentAt;
    }

    public CommunityDTO getCommunity() {
        return community;
    }

    public void setCommunity(CommunityDTO community) {
        this.community = community;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommunityNotificationsDTO)) {
            return false;
        }

        CommunityNotificationsDTO communityNotificationsDTO = (CommunityNotificationsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, communityNotificationsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommunityNotificationsDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", sentAt='" + getSentAt() + "'" +
            ", community=" + getCommunity() +
            "}";
    }
}
