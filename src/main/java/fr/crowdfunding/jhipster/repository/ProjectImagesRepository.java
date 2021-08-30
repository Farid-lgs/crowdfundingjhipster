package fr.crowdfunding.jhipster.repository;

import fr.crowdfunding.jhipster.domain.ProjectImages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProjectImages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectImagesRepository extends JpaRepository<ProjectImages, Long> {}
