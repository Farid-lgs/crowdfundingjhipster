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

//    @Query(value = "SELECT user_infos.id, user_infos.public_name, user_infos.birth_date, user_infos.facebook, user_infos.twitter, user_infos.linked_in, user_infos.description, user_infos.cover_image, user_infos.cover_image_content_type, user_infos.community_members_id, address.address, address.city, address.state, address.zip_code, address.country_id FROM public.user_infos LEFT JOIN public.jhi_user ON jhi_user.id = user_infos.id LEFT JOIN address ON address.user_infos_id = user_infos.id WHERE jhi_user.login = :login", nativeQuery = true)
    @Query(value = "SELECT user_infos.id, user_infos.public_name, user_infos.birth_date, user_infos.facebook, user_infos.twitter, user_infos.linked_in, user_infos.description, user_infos.cover_image, user_infos.cover_image_content_type, user_infos.community_members_id, address.address, address.city, address.state, address.zip_code, address.country_id, credit_card.number, credit_card.owner_name, credit_card.key, credit_card.expiration_date FROM public.user_infos LEFT JOIN public.jhi_user ON jhi_user.id = user_infos.id LEFT JOIN address ON address.user_infos_id = user_infos.id LEFT JOIN credit_card ON credit_card.user_infos_id = user_infos.id WHERE jhi_user.login = :login", nativeQuery = true)
    List<Object[]> find(@Param("login") String login);
}
