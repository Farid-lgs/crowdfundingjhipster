package fr.crowdfunding.jhipster.service;

import fr.crowdfunding.jhipster.domain.Reward;
import fr.crowdfunding.jhipster.repository.RewardRepository;
import fr.crowdfunding.jhipster.service.dto.RewardDTO;
import fr.crowdfunding.jhipster.service.mapper.RewardMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Reward}.
 */
@Service
@Transactional
public class RewardService {

    private final Logger log = LoggerFactory.getLogger(RewardService.class);

    private final RewardRepository rewardRepository;

    private final RewardMapper rewardMapper;

    public RewardService(RewardRepository rewardRepository, RewardMapper rewardMapper) {
        this.rewardRepository = rewardRepository;
        this.rewardMapper = rewardMapper;
    }

    /**
     * Save a reward.
     *
     * @param rewardDTO the entity to save.
     * @return the persisted entity.
     */
    public RewardDTO save(RewardDTO rewardDTO) {
        log.debug("Request to save Reward : {}", rewardDTO);
        Reward reward = rewardMapper.toEntity(rewardDTO);
        reward = rewardRepository.save(reward);
        return rewardMapper.toDto(reward);
    }

    /**
     * Partially update a reward.
     *
     * @param rewardDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RewardDTO> partialUpdate(RewardDTO rewardDTO) {
        log.debug("Request to partially update Reward : {}", rewardDTO);

        return rewardRepository
            .findById(rewardDTO.getId())
            .map(
                existingReward -> {
                    rewardMapper.partialUpdate(existingReward, rewardDTO);

                    return existingReward;
                }
            )
            .map(rewardRepository::save)
            .map(rewardMapper::toDto);
    }

    /**
     * Get all the rewards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RewardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rewards");
        return rewardRepository.findAll(pageable).map(rewardMapper::toDto);
    }

    /**
     * Get one reward by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RewardDTO> findOne(Long id) {
        log.debug("Request to get Reward : {}", id);
        return rewardRepository.findById(id).map(rewardMapper::toDto);
    }

    /**
     * Delete the reward by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Reward : {}", id);
        rewardRepository.deleteById(id);
    }
}
