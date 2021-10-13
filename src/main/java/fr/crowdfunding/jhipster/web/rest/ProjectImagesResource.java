package fr.crowdfunding.jhipster.web.rest;

import fr.crowdfunding.jhipster.repository.ProjectImagesRepository;
import fr.crowdfunding.jhipster.service.ProjectImagesService;
import fr.crowdfunding.jhipster.service.dto.ProjectImagesDTO;
import fr.crowdfunding.jhipster.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.crowdfunding.jhipster.domain.ProjectImages}.
 */
@RestController
@RequestMapping("/api")
public class ProjectImagesResource {

    private final Logger log = LoggerFactory.getLogger(ProjectImagesResource.class);

    private static final String ENTITY_NAME = "projectImages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectImagesService projectImagesService;

    private final ProjectImagesRepository projectImagesRepository;

    public ProjectImagesResource(ProjectImagesService projectImagesService, ProjectImagesRepository projectImagesRepository) {
        this.projectImagesService = projectImagesService;
        this.projectImagesRepository = projectImagesRepository;
    }

    /**
     * {@code POST  /project-images} : Create a new projectImages.
     *
     * @param projectImagesDTO the projectImagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectImagesDTO, or with status {@code 400 (Bad Request)} if the projectImages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-images")
    public ResponseEntity<ProjectImagesDTO> createProjectImages(@RequestBody ProjectImagesDTO projectImagesDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectImages : {}", projectImagesDTO);
        if (projectImagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectImages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectImagesDTO result = projectImagesService.save(projectImagesDTO);
        return ResponseEntity
            .created(new URI("/api/project-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-images/:id} : Updates an existing projectImages.
     *
     * @param id the id of the projectImagesDTO to save.
     * @param projectImagesDTO the projectImagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectImagesDTO,
     * or with status {@code 400 (Bad Request)} if the projectImagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectImagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-images/{id}")
    public ResponseEntity<ProjectImagesDTO> updateProjectImages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectImagesDTO projectImagesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectImages : {}, {}", id, projectImagesDTO);
        if (projectImagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectImagesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectImagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectImagesDTO result = projectImagesService.save(projectImagesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectImagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-images/:id} : Partial updates given fields of an existing projectImages, field will ignore if it is null
     *
     * @param id the id of the projectImagesDTO to save.
     * @param projectImagesDTO the projectImagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectImagesDTO,
     * or with status {@code 400 (Bad Request)} if the projectImagesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projectImagesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectImagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-images/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProjectImagesDTO> partialUpdateProjectImages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectImagesDTO projectImagesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectImages partially : {}, {}", id, projectImagesDTO);
        if (projectImagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectImagesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectImagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectImagesDTO> result = projectImagesService.partialUpdate(projectImagesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectImagesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /project-images} : get all the projectImages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectImages in body.
     */
    @GetMapping("/project-images")
    public ResponseEntity<List<ProjectImagesDTO>> getAllProjectImages(Pageable pageable) {
        log.debug("REST request to get a page of ProjectImages");
        Page<ProjectImagesDTO> page = projectImagesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-images/:id} : get the "id" projectImages.
     *
     * @param id the id of the projectImagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectImagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-images/{id}")
    public ResponseEntity<ProjectImagesDTO> getProjectImages(@PathVariable Long id) {
        log.debug("REST request to get ProjectImages : {}", id);
        Optional<ProjectImagesDTO> projectImagesDTO = projectImagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectImagesDTO);
    }

    /**
     * {@code DELETE  /project-images/:id} : delete the "id" projectImages.
     *
     * @param id the id of the projectImagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-images/{id}")
    public ResponseEntity<Void> deleteProjectImages(@PathVariable Long id) {
        log.debug("REST request to delete ProjectImages : {}", id);
        projectImagesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
