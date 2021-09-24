package fr.crowdfunding.jhipster.repository;

import fr.crowdfunding.jhipster.domain.Project;
import fr.crowdfunding.jhipster.domain.Reward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the Reward entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
    Page<Reward> findByProject(Pageable pageable, Project project);
}
