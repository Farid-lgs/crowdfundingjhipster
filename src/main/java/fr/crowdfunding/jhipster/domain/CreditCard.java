package fr.crowdfunding.jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CreditCard.
 */
@Entity
@Table(name = "credit_card")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CreditCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "owner_name")
    private String ownerName;

    @NotNull
    @Column(name = "key", nullable = false)
    private Integer key;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

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
    private UserInfos userInfos;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CreditCard id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getNumber() {
        return this.number;
    }

    public CreditCard number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public CreditCard ownerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getKey() {
        return this.key;
    }

    public CreditCard key(Integer key) {
        this.key = key;
        return this;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    public CreditCard expirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public UserInfos getUserInfos() {
        return this.userInfos;
    }

    public CreditCard userInfos(UserInfos userInfos) {
        this.setUserInfos(userInfos);
        return this;
    }

    public void setUserInfos(UserInfos userInfos) {
        this.userInfos = userInfos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreditCard)) {
            return false;
        }
        return id != null && id.equals(((CreditCard) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreditCard{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", ownerName='" + getOwnerName() + "'" +
            ", key=" + getKey() +
            ", expirationDate='" + getExpirationDate() + "'" +
            "}";
    }
}
