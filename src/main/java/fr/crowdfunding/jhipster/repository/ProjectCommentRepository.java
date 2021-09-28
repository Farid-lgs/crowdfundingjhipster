package fr.crowdfunding.jhipster.repository;

import fr.crowdfunding.jhipster.domain.Project;
import fr.crowdfunding.jhipster.domain.ProjectComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProjectComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectCommentRepository extends JpaRepository<ProjectComment, Long> {
    Page<ProjectComment> findByProjectOrderByIdDesc(Pageable pageable, Project project);
}
