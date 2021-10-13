package fr.crowdfunding.jhipster.repository;

import fr.crowdfunding.jhipster.domain.CreditCard;
import fr.crowdfunding.jhipster.domain.UserInfos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the CreditCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    Optional<CreditCard> findOneCreditCardByUserInfos(UserInfos id);
}
