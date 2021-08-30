package fr.crowdfunding.jhipster.repository;

import fr.crowdfunding.jhipster.domain.CommunityNotifications;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CommunityNotifications entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommunityNotificationsRepository extends JpaRepository<CommunityNotifications, Long> {}
