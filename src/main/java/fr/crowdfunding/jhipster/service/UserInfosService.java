package fr.crowdfunding.jhipster.service;

import fr.crowdfunding.jhipster.domain.User;
import fr.crowdfunding.jhipster.domain.UserInfos;
import fr.crowdfunding.jhipster.repository.UserInfosRepository;
import fr.crowdfunding.jhipster.repository.UserRepository;
import fr.crowdfunding.jhipster.service.dto.UserInfosDTO;
import fr.crowdfunding.jhipster.service.mapper.UserInfosMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserInfos}.
 */
@Service
@Transactional
public class UserInfosService {

    private final Logger log = LoggerFactory.getLogger(UserInfosService.class);

    private final UserInfosRepository userInfosRepository;

    private final UserInfosMapper userInfosMapper;

    private final UserRepository userRepository;

    public UserInfosService(UserInfosRepository userInfosRepository, UserInfosMapper userInfosMapper, UserRepository userRepository) {
        this.userInfosRepository = userInfosRepository;
        this.userInfosMapper = userInfosMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a userInfos.
     *
     * @param userInfosDTO the entity to save.
     * @return the persisted entity.
     */
    public UserInfosDTO save(UserInfosDTO userInfosDTO) {
        log.debug("Request to save UserInfos : {}", userInfosDTO);
        UserInfos userInfos = userInfosMapper.toEntity(userInfosDTO);
        Long userId = userInfosDTO.getUser().getId();
        userRepository.findById(userId).ifPresent(userInfos::user);
        userInfos = userInfosRepository.save(userInfos);
        return userInfosMapper.toDto(userInfos);
    }

    /**
     * Partially update a userInfos.
     *
     * @param userInfosDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserInfosDTO> partialUpdate(UserInfosDTO userInfosDTO) {
        log.debug("Request to partially update UserInfos : {}", userInfosDTO);

        return userInfosRepository
            .findById(userInfosDTO.getId())
            .map(
                existingUserInfos -> {
                    userInfosMapper.partialUpdate(existingUserInfos, userInfosDTO);

                    return existingUserInfos;
                }
            )
            .map(userInfosRepository::save)
            .map(userInfosMapper::toDto);
    }

    /**
     * Get all the userInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserInfosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserInfos");
        return userInfosRepository.findAll(pageable).map(userInfosMapper::toDto);
    }

    /**
     *  Get all the userInfos where Community is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserInfosDTO> findAllWhereCommunityIsNull() {
        log.debug("Request to get all userInfos where Community is null");
        return StreamSupport
            .stream(userInfosRepository.findAll().spliterator(), false)
            .filter(userInfos -> userInfos.getCommunity() == null)
            .map(userInfosMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the userInfos where Address is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserInfosDTO> findAllWhereAddressIsNull() {
        log.debug("Request to get all userInfos where Address is null");
        return StreamSupport
            .stream(userInfosRepository.findAll().spliterator(), false)
            .filter(userInfos -> userInfos.getAddress() == null)
            .map(userInfosMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the userInfos where BalanceTransfer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserInfosDTO> findAllWhereBalanceTransferIsNull() {
        log.debug("Request to get all userInfos where BalanceTransfer is null");
        return StreamSupport
            .stream(userInfosRepository.findAll().spliterator(), false)
            .filter(userInfos -> userInfos.getBalanceTransfer() == null)
            .map(userInfosMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the userInfos where CreditCard is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserInfosDTO> findAllWhereCreditCardIsNull() {
        log.debug("Request to get all userInfos where CreditCard is null");
        return StreamSupport
            .stream(userInfosRepository.findAll().spliterator(), false)
            .filter(userInfos -> userInfos.getCreditCard() == null)
            .map(userInfosMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userInfos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserInfosDTO> findOne(Long id) {
        log.debug("Request to get UserInfos : {}", id);
        return userInfosRepository.findById(id).map(userInfosMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<UserInfos> getUser(String login) {
        return userInfosRepository.findOneUseInfosrByPublicName(login);
    }

    /**
     * Delete the userInfos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserInfos : {}", id);
        userInfosRepository.deleteById(id);
    }
}
