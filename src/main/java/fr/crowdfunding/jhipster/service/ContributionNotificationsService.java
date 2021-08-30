package fr.crowdfunding.jhipster.service;

import fr.crowdfunding.jhipster.domain.ContributionNotifications;
import fr.crowdfunding.jhipster.repository.ContributionNotificationsRepository;
import fr.crowdfunding.jhipster.service.dto.ContributionNotificationsDTO;
import fr.crowdfunding.jhipster.service.mapper.ContributionNotificationsMapper;
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
 * Service Implementation for managing {@link ContributionNotifications}.
 */
@Service
@Transactional
public class ContributionNotificationsService {

    private final Logger log = LoggerFactory.getLogger(ContributionNotificationsService.class);

    private final ContributionNotificationsRepository contributionNotificationsRepository;

    private final ContributionNotificationsMapper contributionNotificationsMapper;

    public ContributionNotificationsService(
        ContributionNotificationsRepository contributionNotificationsRepository,
        ContributionNotificationsMapper contributionNotificationsMapper
    ) {
        this.contributionNotificationsRepository = contributionNotificationsRepository;
        this.contributionNotificationsMapper = contributionNotificationsMapper;
    }

    /**
     * Save a contributionNotifications.
     *
     * @param contributionNotificationsDTO the entity to save.
     * @return the persisted entity.
     */
    public ContributionNotificationsDTO save(ContributionNotificationsDTO contributionNotificationsDTO) {
        log.debug("Request to save ContributionNotifications : {}", contributionNotificationsDTO);
        ContributionNotifications contributionNotifications = contributionNotificationsMapper.toEntity(contributionNotificationsDTO);
        contributionNotifications = contributionNotificationsRepository.save(contributionNotifications);
        return contributionNotificationsMapper.toDto(contributionNotifications);
    }

    /**
     * Partially update a contributionNotifications.
     *
     * @param contributionNotificationsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ContributionNotificationsDTO> partialUpdate(ContributionNotificationsDTO contributionNotificationsDTO) {
        log.debug("Request to partially update ContributionNotifications : {}", contributionNotificationsDTO);

        return contributionNotificationsRepository
            .findById(contributionNotificationsDTO.getId())
            .map(
                existingContributionNotifications -> {
                    contributionNotificationsMapper.partialUpdate(existingContributionNotifications, contributionNotificationsDTO);

                    return existingContributionNotifications;
                }
            )
            .map(contributionNotificationsRepository::save)
            .map(contributionNotificationsMapper::toDto);
    }

    /**
     * Get all the contributionNotifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContributionNotificationsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContributionNotifications");
        return contributionNotificationsRepository.findAll(pageable).map(contributionNotificationsMapper::toDto);
    }

    /**
     *  Get all the contributionNotifications where Contribution is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ContributionNotificationsDTO> findAllWhereContributionIsNull() {
        log.debug("Request to get all contributionNotifications where Contribution is null");
        return StreamSupport
            .stream(contributionNotificationsRepository.findAll().spliterator(), false)
            .filter(contributionNotifications -> contributionNotifications.getContribution() == null)
            .map(contributionNotificationsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one contributionNotifications by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContributionNotificationsDTO> findOne(Long id) {
        log.debug("Request to get ContributionNotifications : {}", id);
        return contributionNotificationsRepository.findById(id).map(contributionNotificationsMapper::toDto);
    }

    /**
     * Delete the contributionNotifications by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContributionNotifications : {}", id);
        contributionNotificationsRepository.deleteById(id);
    }
}
