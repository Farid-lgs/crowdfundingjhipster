package fr.crowdfunding.jhipster.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.crowdfunding.jhipster.domain.ProjectAccount} entity.
 */
public class ProjectAccountDTO implements Serializable {

    private Long id;

    private String balance;

    private Integer number;

    private String bank;

    private ProjectDTO project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectAccountDTO)) {
            return false;
        }

        ProjectAccountDTO projectAccountDTO = (ProjectAccountDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectAccountDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectAccountDTO{" +
            "id=" + getId() +
            ", balance='" + getBalance() + "'" +
            ", number=" + getNumber() +
            ", bank='" + getBank() + "'" +
            ", project=" + getProject() +
            "}";
    }
}
