package fr.crowdfunding.jhipster.service;

import fr.crowdfunding.jhipster.domain.CommunityMembers;
import fr.crowdfunding.jhipster.repository.CommunityMembersRepository;
import fr.crowdfunding.jhipster.service.dto.CommunityMembersDTO;
import fr.crowdfunding.jhipster.service.mapper.CommunityMembersMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CommunityMembers}.
 */
@Service
@Transactional
public class CommunityMembersService {

    private final Logger log = LoggerFactory.getLogger(CommunityMembersService.class);

    private final CommunityMembersRepository communityMembersRepository;

    private final CommunityMembersMapper communityMembersMapper;

    public CommunityMembersService(CommunityMembersRepository communityMembersRepository, CommunityMembersMapper communityMembersMapper) {
        this.communityMembersRepository = communityMembersRepository;
        this.communityMembersMapper = communityMembersMapper;
    }

    /**
     * Save a communityMembers.
     *
     * @param communityMembersDTO the entity to save.
     * @return the persisted entity.
     */
    public CommunityMembersDTO save(CommunityMembersDTO communityMembersDTO) {
        log.debug("Request to save CommunityMembers : {}", communityMembersDTO);
        CommunityMembers communityMembers = communityMembersMapper.toEntity(communityMembersDTO);
        communityMembers = communityMembersRepository.save(communityMembers);
        return communityMembersMapper.toDto(communityMembers);
    }

    /**
     * Partially update a communityMembers.
     *
     * @param communityMembersDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CommunityMembersDTO> partialUpdate(CommunityMembersDTO communityMembersDTO) {
        log.debug("Request to partially update CommunityMembers : {}", communityMembersDTO);

        return communityMembersRepository
            .findById(communityMembersDTO.getId())
            .map(
                existingCommunityMembers -> {
                    communityMembersMapper.partialUpdate(existingCommunityMembers, communityMembersDTO);

                    return existingCommunityMembers;
                }
            )
            .map(communityMembersRepository::save)
            .map(communityMembersMapper::toDto);
    }

    /**
     * Get all the communityMembers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommunityMembersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommunityMembers");
        return communityMembersRepository.findAll(pageable).map(communityMembersMapper::toDto);
    }

    /**
     * Get one communityMembers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommunityMembersDTO> findOne(Long id) {
        log.debug("Request to get CommunityMembers : {}", id);
        return communityMembersRepository.findById(id).map(communityMembersMapper::toDto);
    }

    /**
     * Delete the communityMembers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommunityMembers : {}", id);
        communityMembersRepository.deleteById(id);
    }
}
