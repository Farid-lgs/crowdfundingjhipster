package fr.crowdfunding.jhipster.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.crowdfunding.jhipster.domain.Community} entity.
 */
public class CommunityDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String nameFr;

    private Instant createdAt;

    @Lob
    private String about;

    @Lob
    private byte[] coverImage;

    private String coverImageContentType;
    private CommunityMembersDTO communityMembers;

    private UserInfosDTO admin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public byte[] getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(byte[] coverImage) {
        this.coverImage = coverImage;
    }

    public String getCoverImageContentType() {
        return coverImageContentType;
    }

    public void setCoverImageContentType(String coverImageContentType) {
        this.coverImageContentType = coverImageContentType;
    }

    public CommunityMembersDTO getCommunityMembers() {
        return communityMembers;
    }

    public void setCommunityMembers(CommunityMembersDTO communityMembers) {
        this.communityMembers = communityMembers;
    }

    public UserInfosDTO getAdmin() {
        return admin;
    }

    public void setAdmin(UserInfosDTO admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommunityDTO)) {
            return false;
        }

        CommunityDTO communityDTO = (CommunityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, communityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommunityDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nameFr='" + getNameFr() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", about='" + getAbout() + "'" +
            ", coverImage='" + getCoverImage() + "'" +
            ", communityMembers=" + getCommunityMembers() +
            ", admin=" + getAdmin() +
            "}";
    }
}
