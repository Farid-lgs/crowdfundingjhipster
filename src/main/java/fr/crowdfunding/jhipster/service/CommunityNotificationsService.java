package fr.crowdfunding.jhipster.service;

import fr.crowdfunding.jhipster.domain.CommunityNotifications;
import fr.crowdfunding.jhipster.repository.CommunityNotificationsRepository;
import fr.crowdfunding.jhipster.service.dto.CommunityNotificationsDTO;
import fr.crowdfunding.jhipster.service.mapper.CommunityNotificationsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CommunityNotifications}.
 */
@Service
@Transactional
public class CommunityNotificationsService {

    private final Logger log = LoggerFactory.getLogger(CommunityNotificationsService.class);

    private final CommunityNotificationsRepository communityNotificationsRepository;

    private final CommunityNotificationsMapper communityNotificationsMapper;

    public CommunityNotificationsService(
        CommunityNotificationsRepository communityNotificationsRepository,
        CommunityNotificationsMapper communityNotificationsMapper
    ) {
        this.communityNotificationsRepository = communityNotificationsRepository;
        this.communityNotificationsMapper = communityNotificationsMapper;
    }

    /**
     * Save a communityNotifications.
     *
     * @param communityNotificationsDTO the entity to save.
     * @return the persisted entity.
     */
    public CommunityNotificationsDTO save(CommunityNotificationsDTO communityNotificationsDTO) {
        log.debug("Request to save CommunityNotifications : {}", communityNotificationsDTO);
        CommunityNotifications communityNotifications = communityNotificationsMapper.toEntity(communityNotificationsDTO);
        communityNotifications = communityNotificationsRepository.save(communityNotifications);
        return communityNotificationsMapper.toDto(communityNotifications);
    }

    /**
     * Partially update a communityNotifications.
     *
     * @param communityNotificationsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CommunityNotificationsDTO> partialUpdate(CommunityNotificationsDTO communityNotificationsDTO) {
        log.debug("Request to partially update CommunityNotifications : {}", communityNotificationsDTO);

        return communityNotificationsRepository
            .findById(communityNotificationsDTO.getId())
            .map(
                existingCommunityNotifications -> {
                    communityNotificationsMapper.partialUpdate(existingCommunityNotifications, communityNotificationsDTO);

                    return existingCommunityNotifications;
                }
            )
            .map(communityNotificationsRepository::save)
            .map(communityNotificationsMapper::toDto);
    }

    /**
     * Get all the communityNotifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommunityNotificationsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommunityNotifications");
        return communityNotificationsRepository.findAll(pageable).map(communityNotificationsMapper::toDto);
    }

    /**
     * Get one communityNotifications by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommunityNotificationsDTO> findOne(Long id) {
        log.debug("Request to get CommunityNotifications : {}", id);
        return communityNotificationsRepository.findById(id).map(communityNotificationsMapper::toDto);
    }

    /**
     * Delete the communityNotifications by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommunityNotifications : {}", id);
        communityNotificationsRepository.deleteById(id);
    }
}
