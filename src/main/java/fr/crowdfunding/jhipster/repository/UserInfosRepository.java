package fr.crowdfunding.jhipster.repository;

import fr.crowdfunding.jhipster.domain.UserInfos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserInfos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserInfosRepository extends JpaRepository<UserInfos, Long> {}
