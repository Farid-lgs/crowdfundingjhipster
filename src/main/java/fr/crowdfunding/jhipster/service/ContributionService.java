package fr.crowdfunding.jhipster.service;

import fr.crowdfunding.jhipster.domain.Contribution;
import fr.crowdfunding.jhipster.repository.ContributionRepository;
import fr.crowdfunding.jhipster.service.dto.ContributionDTO;
import fr.crowdfunding.jhipster.service.mapper.ContributionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Contribution}.
 */
@Service
@Transactional
public class ContributionService {

    private final Logger log = LoggerFactory.getLogger(ContributionService.class);

    private final ContributionRepository contributionRepository;

    private final ContributionMapper contributionMapper;

    public ContributionService(ContributionRepository contributionRepository, ContributionMapper contributionMapper) {
        this.contributionRepository = contributionRepository;
        this.contributionMapper = contributionMapper;
    }

    /**
     * Save a contribution.
     *
     * @param contributionDTO the entity to save.
     * @return the persisted entity.
     */
    public ContributionDTO save(ContributionDTO contributionDTO) {
        log.debug("Request to save Contribution : {}", contributionDTO);
        Contribution contribution = contributionMapper.toEntity(contributionDTO);
        contribution = contributionRepository.save(contribution);
        return contributionMapper.toDto(contribution);
    }

    /**
     * Partially update a contribution.
     *
     * @param contributionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ContributionDTO> partialUpdate(ContributionDTO contributionDTO) {
        log.debug("Request to partially update Contribution : {}", contributionDTO);

        return contributionRepository
            .findById(contributionDTO.getId())
            .map(
                existingContribution -> {
                    contributionMapper.partialUpdate(existingContribution, contributionDTO);

                    return existingContribution;
                }
            )
            .map(contributionRepository::save)
            .map(contributionMapper::toDto);
    }

    /**
     * Get all the contributions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContributionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contributions");
        return contributionRepository.findAll(pageable).map(contributionMapper::toDto);
    }

    /**
     * Get one contribution by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContributionDTO> findOne(Long id) {
        log.debug("Request to get Contribution : {}", id);
        return contributionRepository.findById(id).map(contributionMapper::toDto);
    }

    /**
     * Delete the contribution by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Contribution : {}", id);
        contributionRepository.deleteById(id);
    }
}
