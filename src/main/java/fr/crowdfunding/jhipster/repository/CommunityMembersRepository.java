package fr.crowdfunding.jhipster.repository;

import fr.crowdfunding.jhipster.domain.CommunityMembers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CommunityMembers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommunityMembersRepository extends JpaRepository<CommunityMembers, Long> {}
