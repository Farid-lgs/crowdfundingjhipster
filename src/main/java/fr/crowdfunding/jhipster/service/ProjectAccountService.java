package fr.crowdfunding.jhipster.service;

import fr.crowdfunding.jhipster.domain.ProjectAccount;
import fr.crowdfunding.jhipster.repository.ProjectAccountRepository;
import fr.crowdfunding.jhipster.service.dto.ProjectAccountDTO;
import fr.crowdfunding.jhipster.service.mapper.ProjectAccountMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjectAccount}.
 */
@Service
@Transactional
public class ProjectAccountService {

    private final Logger log = LoggerFactory.getLogger(ProjectAccountService.class);

    private final ProjectAccountRepository projectAccountRepository;

    private final ProjectAccountMapper projectAccountMapper;

    public ProjectAccountService(ProjectAccountRepository projectAccountRepository, ProjectAccountMapper projectAccountMapper) {
        this.projectAccountRepository = projectAccountRepository;
        this.projectAccountMapper = projectAccountMapper;
    }

    /**
     * Save a projectAccount.
     *
     * @param projectAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjectAccountDTO save(ProjectAccountDTO projectAccountDTO) {
        log.debug("Request to save ProjectAccount : {}", projectAccountDTO);
        ProjectAccount projectAccount = projectAccountMapper.toEntity(projectAccountDTO);
        projectAccount = projectAccountRepository.save(projectAccount);
        return projectAccountMapper.toDto(projectAccount);
    }

    /**
     * Partially update a projectAccount.
     *
     * @param projectAccountDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProjectAccountDTO> partialUpdate(ProjectAccountDTO projectAccountDTO) {
        log.debug("Request to partially update ProjectAccount : {}", projectAccountDTO);

        return projectAccountRepository
            .findById(projectAccountDTO.getId())
            .map(
                existingProjectAccount -> {
                    projectAccountMapper.partialUpdate(existingProjectAccount, projectAccountDTO);

                    return existingProjectAccount;
                }
            )
            .map(projectAccountRepository::save)
            .map(projectAccountMapper::toDto);
    }

    /**
     * Get all the projectAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectAccounts");
        return projectAccountRepository.findAll(pageable).map(projectAccountMapper::toDto);
    }

    /**
     * Get one projectAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectAccountDTO> findOne(Long id) {
        log.debug("Request to get ProjectAccount : {}", id);
        return projectAccountRepository.findById(id).map(projectAccountMapper::toDto);
    }

    /**
     * Delete the projectAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectAccount : {}", id);
        projectAccountRepository.deleteById(id);
    }
}
