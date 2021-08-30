package fr.crowdfunding.jhipster.repository;

import fr.crowdfunding.jhipster.domain.Contribution;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Contribution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {}
