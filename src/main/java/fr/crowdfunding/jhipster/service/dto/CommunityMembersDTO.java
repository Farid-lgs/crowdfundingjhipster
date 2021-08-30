package fr.crowdfunding.jhipster.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.crowdfunding.jhipster.domain.CommunityMembers} entity.
 */
public class CommunityMembersDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommunityMembersDTO)) {
            return false;
        }

        CommunityMembersDTO communityMembersDTO = (CommunityMembersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, communityMembersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommunityMembersDTO{" +
            "id=" + getId() +
            "}";
    }
}
