package fr.crowdfunding.jhipster.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.crowdfunding.jhipster.domain.CreditCard} entity.
 */
public class CreditCardDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer number;

    private String ownerName;

    @NotNull
    private Integer key;

    private LocalDate expirationDate;

    private UserInfosDTO userInfos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public UserInfosDTO getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(UserInfosDTO userInfos) {
        this.userInfos = userInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreditCardDTO)) {
            return false;
        }

        CreditCardDTO creditCardDTO = (CreditCardDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, creditCardDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreditCardDTO{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", ownerName='" + getOwnerName() + "'" +
            ", key=" + getKey() +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", userInfos=" + getUserInfos() +
            "}";
    }
}
