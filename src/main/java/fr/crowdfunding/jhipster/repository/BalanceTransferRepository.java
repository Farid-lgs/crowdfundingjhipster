package fr.crowdfunding.jhipster.repository;

import fr.crowdfunding.jhipster.domain.BalanceTransfer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BalanceTransfer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BalanceTransferRepository extends JpaRepository<BalanceTransfer, Long> {}
