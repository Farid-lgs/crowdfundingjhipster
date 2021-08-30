package fr.crowdfunding.jhipster.service;

import fr.crowdfunding.jhipster.domain.ProjectComment;
import fr.crowdfunding.jhipster.repository.ProjectCommentRepository;
import fr.crowdfunding.jhipster.service.dto.ProjectCommentDTO;
import fr.crowdfunding.jhipster.service.mapper.ProjectCommentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjectComment}.
 */
@Service
@Transactional
public class ProjectCommentService {

    private final Logger log = LoggerFactory.getLogger(ProjectCommentService.class);

    private final ProjectCommentRepository projectCommentRepository;

    private final ProjectCommentMapper projectCommentMapper;

    public ProjectCommentService(ProjectCommentRepository projectCommentRepository, ProjectCommentMapper projectCommentMapper) {
        this.projectCommentRepository = projectCommentRepository;
        this.projectCommentMapper = projectCommentMapper;
    }

    /**
     * Save a projectComment.
     *
     * @param projectCommentDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjectCommentDTO save(ProjectCommentDTO projectCommentDTO) {
        log.debug("Request to save ProjectComment : {}", projectCommentDTO);
        ProjectComment projectComment = projectCommentMapper.toEntity(projectCommentDTO);
        projectComment = projectCommentRepository.save(projectComment);
        return projectCommentMapper.toDto(projectComment);
    }

    /**
     * Partially update a projectComment.
     *
     * @param projectCommentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProjectCommentDTO> partialUpdate(ProjectCommentDTO projectCommentDTO) {
        log.debug("Request to partially update ProjectComment : {}", projectCommentDTO);

        return projectCommentRepository
            .findById(projectCommentDTO.getId())
            .map(
                existingProjectComment -> {
                    projectCommentMapper.partialUpdate(existingProjectComment, projectCommentDTO);

                    return existingProjectComment;
                }
            )
            .map(projectCommentRepository::save)
            .map(projectCommentMapper::toDto);
    }

    /**
     * Get all the projectComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectCommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectComments");
        return projectCommentRepository.findAll(pageable).map(projectCommentMapper::toDto);
    }

    /**
     * Get one projectComment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectCommentDTO> findOne(Long id) {
        log.debug("Request to get ProjectComment : {}", id);
        return projectCommentRepository.findById(id).map(projectCommentMapper::toDto);
    }

    /**
     * Delete the projectComment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectComment : {}", id);
        projectCommentRepository.deleteById(id);
    }
}
