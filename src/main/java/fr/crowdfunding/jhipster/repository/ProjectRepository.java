package fr.crowdfunding.jhipster.repository;

import fr.crowdfunding.jhipster.domain.Project;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Project entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(value = "SELECT COUNT(contribution.project_id) AS participants, SUM(contribution.amount) AS amount,  project.title, project.id, project.goal, project.headline, project.duration, project.created_at, project.category_id, category.name, (SELECT COUNT(*) FROM project) AS nb_rows FROM public.project LEFT JOIN contribution ON project.id = contribution.project_id LEFT JOIN category ON project.category_id = category.id GROUP BY project.id, category.id OFFSET :start_value ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<Object[]> find(@Param("size") int pageSize, @Param("start_value") int startValue);

    // Sort Title
    @Query(value = "SELECT COUNT(contribution.project_id) AS participants, SUM(contribution.amount) AS amount,  project.title, project.id, project.goal, project.headline, project.duration, project.created_at, project.category_id, category.name, (SELECT COUNT(*) FROM project) AS nb_rows FROM public.project LEFT JOIN contribution ON project.id = contribution.project_id LEFT JOIN category ON project.category_id = category.id GROUP BY project.id, category.id ORDER BY project.title ASC OFFSET :start_value ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<Object[]> findAllByTitleAsc(@Param("size") int pageSize, @Param("start_value") int startValue);

    @Query(value = "SELECT COUNT(contribution.project_id) AS participants, SUM(contribution.amount) AS amount,  project.title, project.id, project.goal, project.headline, project.duration, project.created_at, project.category_id, category.name, (SELECT COUNT(*) FROM project) AS nb_rows FROM public.project LEFT JOIN contribution ON project.id = contribution.project_id LEFT JOIN category ON project.category_id = category.id GROUP BY project.id, category.id ORDER BY project.title DESC OFFSET :start_value ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<Object[]> findAllByTitleDesc(@Param("size") int pageSize, @Param("start_value") int startValue);

    @Query(value = "SELECT COUNT(contribution.project_id) AS participants, SUM(contribution.amount) AS amount,  project.title, project.id, project.goal, project.headline, project.duration, project.created_at, project.category_id, category.name, (SELECT COUNT(*) FROM project WHERE user_infos_id = :user_id) AS nb_rows FROM public.project LEFT JOIN contribution ON project.id = contribution.project_id LEFT JOIN category ON project.category_id = category.id WHERE project.user_infos_id = :user_id GROUP BY project.id, category.id ORDER BY project.title ASC OFFSET :start_value ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<Object[]> findAllByUserIdAndTitleAsc(@Param("size") int pageSize, @Param("start_value") int startValue, @Param("user_id") Long userId);

    @Query(value = "SELECT COUNT(contribution.project_id) AS participants, SUM(contribution.amount) AS amount,  project.title, project.id, project.goal, project.headline, project.duration, project.created_at, project.category_id, category.name, (SELECT COUNT(*) FROM project WHERE user_infos_id = :user_id) AS nb_rows FROM public.project LEFT JOIN contribution ON project.id = contribution.project_id LEFT JOIN category ON project.category_id = category.id WHERE project.user_infos_id = :user_id GROUP BY project.id, category.id ORDER BY project.title DESC OFFSET :start_value ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<Object[]> findAllByUserIdAndTitleDesc(@Param("size") int pageSize, @Param("start_value") int startValue, @Param("user_id") Long userId);

    // Sort Goal
    @Query(value = "SELECT COUNT(contribution.project_id) AS participants, SUM(contribution.amount) AS amount,  project.title, project.id, project.goal, project.headline, project.duration, project.created_at, project.category_id, category.name, (SELECT COUNT(*) FROM project) AS nb_rows FROM public.project LEFT JOIN contribution ON project.id = contribution.project_id LEFT JOIN category ON project.category_id = category.id GROUP BY project.id, category.id ORDER BY project.goal ASC OFFSET :start_value ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<Object[]> findAllByGoalAsc(@Param("size") int pageSize, @Param("start_value") int startValue);

    @Query(value = "SELECT COUNT(contribution.project_id) AS participants, SUM(contribution.amount) AS amount,  project.title, project.id, project.goal, project.headline, project.duration, project.created_at, project.category_id, category.name, (SELECT COUNT(*) FROM project) AS nb_rows FROM public.project LEFT JOIN contribution ON project.id = contribution.project_id LEFT JOIN category ON project.category_id = category.id GROUP BY project.id, category.id ORDER BY project.goal DESC OFFSET :start_value ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<Object[]> findAllByGoalDesc(@Param("size") int pageSize, @Param("start_value") int startValue);

    @Query(value = "SELECT COUNT(contribution.project_id) AS participants, SUM(contribution.amount) AS amount,  project.title, project.id, project.goal, project.headline, project.duration, project.created_at, project.category_id, category.name, (SELECT COUNT(*) FROM project WHERE user_infos_id = :user_id) AS nb_rows FROM public.project LEFT JOIN contribution ON project.id = contribution.project_id LEFT JOIN category ON project.category_id = category.id WHERE project.user_infos_id = :user_id GROUP BY project.id, category.id ORDER BY project.goal ASC OFFSET :start_value ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<Object[]> findAllByUserIdAndGoalAsc(@Param("size") int pageSize, @Param("start_value") int startValue, @Param("user_id") Long userId);

    @Query(value = "SELECT COUNT(contribution.project_id) AS participants, SUM(contribution.amount) AS amount,  project.title, project.id, project.goal, project.headline, project.duration, project.created_at, project.category_id, category.name, (SELECT COUNT(*) FROM project WHERE user_infos_id = :user_id) AS nb_rows FROM public.project LEFT JOIN contribution ON project.id = contribution.project_id LEFT JOIN category ON project.category_id = category.id WHERE project.user_infos_id = :user_id GROUP BY project.id, category.id ORDER BY project.goal DESC OFFSET :start_value ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<Object[]> findAllByUserIdAndGoalDesc(@Param("size") int pageSize, @Param("start_value") int startValue, @Param("user_id") Long userId);

    // Sort Category
    @Query(value = "SELECT COUNT(contribution.project_id) AS participants, SUM(contribution.amount) AS amount,  project.title, project.id, project.goal, project.headline, project.duration, project.created_at, project.category_id, category.name, (SELECT COUNT(*) FROM project) AS nb_rows FROM public.project LEFT JOIN contribution ON project.id = contribution.project_id LEFT JOIN category ON project.category_id = category.id GROUP BY project.id, category.id ORDER BY project.category_id ASC OFFSET :start_value ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<Object[]> findAllByCategoryIdAsc(@Param("size") int pageSize, @Param("start_value") int startValue);

    @Query(value = "SELECT COUNT(contribution.project_id) AS participants, SUM(contribution.amount) AS amount,  project.title, project.id, project.goal, project.headline, project.duration, project.created_at, project.category_id, category.name, (SELECT COUNT(*) FROM project) AS nb_rows FROM public.project LEFT JOIN contribution ON project.id = contribution.project_id LEFT JOIN category ON project.category_id = category.id GROUP BY project.id, category.id ORDER BY project.category_id DESC OFFSET :start_value ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<Object[]> findAllByCategoryIdDesc(@Param("size") int pageSize, @Param("start_value") int startValue);

    @Query(value = "SELECT COUNT(contribution.project_id) AS participants, SUM(contribution.amount) AS amount,  project.title, project.id, project.goal, project.headline, project.duration, project.created_at, project.category_id, category.name, (SELECT COUNT(*) FROM project WHERE user_infos_id = :user_id) AS nb_rows FROM public.project LEFT JOIN contribution ON project.id = contribution.project_id LEFT JOIN category ON project.category_id = category.id WHERE project.user_infos_id = :user_id GROUP BY project.id, category.id ORDER BY project.category_id ASC OFFSET :start_value ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<Object[]> findAllByUserIdAndCategoryIdAsc(@Param("size") int pageSize, @Param("start_value") int startValue, @Param("user_id") Long userId);

    @Query(value = "SELECT COUNT(contribution.project_id) AS participants, SUM(contribution.amount) AS amount,  project.title, project.id, project.goal, project.headline, project.duration, project.created_at, project.category_id, category.name, (SELECT COUNT(*) FROM project WHERE user_infos_id = :user_id) AS nb_rows FROM public.project LEFT JOIN contribution ON project.id = contribution.project_id LEFT JOIN category ON project.category_id = category.id WHERE project.user_infos_id = :user_id GROUP BY project.id, category.id ORDER BY project.category_id DESC OFFSET :start_value ROWS FETCH NEXT :size ROWS ONLY", nativeQuery = true)
    List<Object[]> findAllByUserIdAndCategoryIdDesc(@Param("size") int pageSize, @Param("start_value") int startValue, @Param("user_id") Long userId);
}
