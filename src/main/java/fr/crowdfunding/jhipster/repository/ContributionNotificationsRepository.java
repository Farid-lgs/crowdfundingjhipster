package fr.crowdfunding.jhipster.repository;

import fr.crowdfunding.jhipster.domain.ContributionNotifications;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ContributionNotifications entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContributionNotificationsRepository extends JpaRepository<ContributionNotifications, Long> {}
