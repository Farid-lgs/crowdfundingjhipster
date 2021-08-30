package fr.crowdfunding.jhipster.service;

import fr.crowdfunding.jhipster.domain.ProjectImages;
import fr.crowdfunding.jhipster.repository.ProjectImagesRepository;
import fr.crowdfunding.jhipster.service.dto.ProjectImagesDTO;
import fr.crowdfunding.jhipster.service.mapper.ProjectImagesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjectImages}.
 */
@Service
@Transactional
public class ProjectImagesService {

    private final Logger log = LoggerFactory.getLogger(ProjectImagesService.class);

    private final ProjectImagesRepository projectImagesRepository;

    private final ProjectImagesMapper projectImagesMapper;

    public ProjectImagesService(ProjectImagesRepository projectImagesRepository, ProjectImagesMapper projectImagesMapper) {
        this.projectImagesRepository = projectImagesRepository;
        this.projectImagesMapper = projectImagesMapper;
    }

    /**
     * Save a projectImages.
     *
     * @param projectImagesDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjectImagesDTO save(ProjectImagesDTO projectImagesDTO) {
        log.debug("Request to save ProjectImages : {}", projectImagesDTO);
        ProjectImages projectImages = projectImagesMapper.toEntity(projectImagesDTO);
        projectImages = projectImagesRepository.save(projectImages);
        return projectImagesMapper.toDto(projectImages);
    }

    /**
     * Partially update a projectImages.
     *
     * @param projectImagesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProjectImagesDTO> partialUpdate(ProjectImagesDTO projectImagesDTO) {
        log.debug("Request to partially update ProjectImages : {}", projectImagesDTO);

        return projectImagesRepository
            .findById(projectImagesDTO.getId())
            .map(
                existingProjectImages -> {
                    projectImagesMapper.partialUpdate(existingProjectImages, projectImagesDTO);

                    return existingProjectImages;
                }
            )
            .map(projectImagesRepository::save)
            .map(projectImagesMapper::toDto);
    }

    /**
     * Get all the projectImages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectImagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectImages");
        return projectImagesRepository.findAll(pageable).map(projectImagesMapper::toDto);
    }

    /**
     * Get one projectImages by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectImagesDTO> findOne(Long id) {
        log.debug("Request to get ProjectImages : {}", id);
        return projectImagesRepository.findById(id).map(projectImagesMapper::toDto);
    }

    /**
     * Delete the projectImages by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectImages : {}", id);
        projectImagesRepository.deleteById(id);
    }
}
