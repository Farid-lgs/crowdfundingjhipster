package fr.crowdfunding.jhipster.repository;

import fr.crowdfunding.jhipster.domain.Address;
import fr.crowdfunding.jhipster.domain.UserInfos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findOneAddressByUserInfos(UserInfos id);
}
