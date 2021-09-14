package fr.crowdfunding.jhipster.repository;

import fr.crowdfunding.jhipster.domain.UserInfos;
import fr.crowdfunding.jhipster.service.dto.UserInfosDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the UserInfos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserInfosRepository extends JpaRepository<UserInfos, Long> {
    String USERS_BY_LOGIN_CACHE = "usersByLogin";

//    @EntityGraph(attributePaths = "authorities")
//    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
//    @Query(value = "SELECT user_infos.id FROM public.user_infos LEFT JOIN public.jhi_user ON jhi_user.id = user_infos.id WHERE jhi_user.login = :login", nativeQuery = true)
//    @Query(value = "SELECT jhi_user.id, jhi_user.login, jhi_user.first_name, jhi_user.last_name, jhi_user.email, jhi_user.activated, jhi_user.lang_key, jhi_user.activation_key, jhi_user.reset_key, jhi_user.created_date, user_infos.public_name, user_infos.birth_date, user_infos.facebook,user_infos.twitter, user_infos.linked_in, user_infos.description, user_infos.cover_image, user_infos.cover_image_content_type, user_infos.common_id, user_infos.community_members_id FROM public.user_infos LEFT JOIN public.jhi_user ON jhi_user.id = user_infos.id WHERE jhi_user.login = :login", nativeQuery = true)
//    @Query(value = "SELECT user_infos.id, user_infos.public_name, user_infos.birth_date, user_infos.facebook, user_infos.twitter, user_infos.linked_in, user_infos.description, user_infos.cover_image, user_infos.cover_image_content_type, user_infos.community_members_id FROM public.user_infos LEFT JOIN public.jhi_user ON jhi_user.id = user_infos.id WHERE jhi_user.login = :login", nativeQuery = true)
    @Query(value = "SELECT user_infos.id, user_infos.public_name, user_infos.birth_date, user_infos.facebook, user_infos.twitter, user_infos.linked_in, user_infos.description, user_infos.cover_image, user_infos.cover_image_content_type, user_infos.community_members_id FROM public.user_infos LEFT JOIN public.jhi_user ON jhi_user.id = user_infos.id WHERE jhi_user.login = :login", nativeQuery = true)
    List<Object[]> find(@Param("login") String login);
}
