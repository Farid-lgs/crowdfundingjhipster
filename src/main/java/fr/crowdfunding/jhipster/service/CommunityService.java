package fr.crowdfunding.jhipster.service;

import fr.crowdfunding.jhipster.domain.Community;
import fr.crowdfunding.jhipster.repository.CommunityRepository;
import fr.crowdfunding.jhipster.service.dto.CommunityDTO;
import fr.crowdfunding.jhipster.service.mapper.CommunityMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Community}.
 */
@Service
@Transactional
public class CommunityService {

    private final Logger log = LoggerFactory.getLogger(CommunityService.class);

    private final CommunityRepository communityRepository;

    private final CommunityMapper communityMapper;

    public CommunityService(CommunityRepository communityRepository, CommunityMapper communityMapper) {
        this.communityRepository = communityRepository;
        this.communityMapper = communityMapper;
    }

    /**
     * Save a community.
     *
     * @param communityDTO the entity to save.
     * @return the persisted entity.
     */
    public CommunityDTO save(CommunityDTO communityDTO) {
        log.debug("Request to save Community : {}", communityDTO);
        Community community = communityMapper.toEntity(communityDTO);
        community = communityRepository.save(community);
        return communityMapper.toDto(community);
    }

    /**
     * Partially update a community.
     *
     * @param communityDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CommunityDTO> partialUpdate(CommunityDTO communityDTO) {
        log.debug("Request to partially update Community : {}", communityDTO);

        return communityRepository
            .findById(communityDTO.getId())
            .map(
                existingCommunity -> {
                    communityMapper.partialUpdate(existingCommunity, communityDTO);

                    return existingCommunity;
                }
            )
            .map(communityRepository::save)
            .map(communityMapper::toDto);
    }

    /**
     * Get all the communities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommunityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Communities");
        return communityRepository.findAll(pageable).map(communityMapper::toDto);
    }

    /**
     * Get one community by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommunityDTO> findOne(Long id) {
        log.debug("Request to get Community : {}", id);
        return communityRepository.findById(id).map(communityMapper::toDto);
    }

    /**
     * Delete the community by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Community : {}", id);
        communityRepository.deleteById(id);
    }
}
