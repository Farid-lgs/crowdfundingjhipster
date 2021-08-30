package fr.crowdfunding.jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProjectAccount.
 */
@Entity
@Table(name = "project_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "balance")
    private String balance;

    @Column(name = "number")
    private Integer number;

    @Column(name = "bank")
    private String bank;

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

    public ProjectAccount id(Long id) {
        this.id = id;
        return this;
    }

    public String getBalance() {
        return this.balance;
    }

    public ProjectAccount balance(String balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Integer getNumber() {
        return this.number;
    }

    public ProjectAccount number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getBank() {
        return this.bank;
    }

    public ProjectAccount bank(String bank) {
        this.bank = bank;
        return this;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Project getProject() {
        return this.project;
    }

    public ProjectAccount project(Project project) {
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
        if (!(o instanceof ProjectAccount)) {
            return false;
        }
        return id != null && id.equals(((ProjectAccount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectAccount{" +
            "id=" + getId() +
            ", balance='" + getBalance() + "'" +
            ", number=" + getNumber() +
            ", bank='" + getBank() + "'" +
            "}";
    }
}
