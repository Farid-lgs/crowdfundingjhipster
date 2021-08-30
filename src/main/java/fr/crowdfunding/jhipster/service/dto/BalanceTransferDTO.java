package fr.crowdfunding.jhipster.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.crowdfunding.jhipster.domain.BalanceTransfer} entity.
 */
public class BalanceTransferDTO implements Serializable {

    private Long id;

    @NotNull
    private Double amount;

    private UserInfosDTO userInfos;

    private ProjectDTO project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public UserInfosDTO getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(UserInfosDTO userInfos) {
        this.userInfos = userInfos;
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
        if (!(o instanceof BalanceTransferDTO)) {
            return false;
        }

        BalanceTransferDTO balanceTransferDTO = (BalanceTransferDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, balanceTransferDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BalanceTransferDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", userInfos=" + getUserInfos() +
            ", project=" + getProject() +
            "}";
    }
}
