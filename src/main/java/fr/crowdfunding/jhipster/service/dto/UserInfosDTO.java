package fr.crowdfunding.jhipster.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.crowdfunding.jhipster.domain.UserInfos} entity.
 */
public class UserInfosDTO implements Serializable {

    private Long id;

    @NotNull
    private String publicName;

    private LocalDate birthDate;

    private String twitter;

    private String facebook;

    private String linkedIn;

    @Lob
    private String description;

    @Lob
    private byte[] coverImage;

    private String coverImageContentType;
    private UUID commonId;

    private UserDTO user;

    private CommunityMembersDTO communityMembers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public UUID getCommonId() {
        return commonId;
    }

    public void setCommonId(UUID commonId) {
        this.commonId = commonId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CommunityMembersDTO getCommunityMembers() {
        return communityMembers;
    }

    public void setCommunityMembers(CommunityMembersDTO communityMembers) {
        this.communityMembers = communityMembers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInfosDTO)) {
            return false;
        }

        UserInfosDTO userInfosDTO = (UserInfosDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userInfosDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInfosDTO{" +
            "id=" + getId() +
            ", publicName='" + getPublicName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", twitter='" + getTwitter() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", linkedIn='" + getLinkedIn() + "'" +
            ", description='" + getDescription() + "'" +
            ", coverImage='" + getCoverImage() + "'" +
            ", commonId='" + getCommonId() + "'" +
            ", user=" + getUser() +
            ", communityMembers=" + getCommunityMembers() +
            "}";
    }
}
