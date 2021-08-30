package fr.crowdfunding.jhipster.repository;

import fr.crowdfunding.jhipster.domain.ProjectAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProjectAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectAccountRepository extends JpaRepository<ProjectAccount, Long> {}
