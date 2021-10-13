package fr.crowdfunding.jhipster.web.rest;

import fr.crowdfunding.jhipster.repository.ProjectCommentRepository;
import fr.crowdfunding.jhipster.service.ProjectCommentService;
import fr.crowdfunding.jhipster.service.ProjectService;
import fr.crowdfunding.jhipster.service.UserInfosService;
import fr.crowdfunding.jhipster.service.dto.ProjectCommentDTO;
import fr.crowdfunding.jhipster.service.dto.ProjectDTO;
import fr.crowdfunding.jhipster.service.dto.UserInfosDTO;
import fr.crowdfunding.jhipster.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link fr.crowdfunding.jhipster.domain.ProjectComment}.
 */
@RestController
@RequestMapping("/api")
public class ProjectCommentResource {

    private final Logger log = LoggerFactory.getLogger(ProjectCommentResource.class);

    private static final String ENTITY_NAME = "projectComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectCommentService projectCommentService;
    private final UserInfosService userInfosService;
    private final ProjectService projectService;

    private final ProjectCommentRepository projectCommentRepository;

    public ProjectCommentResource(ProjectCommentService projectCommentService, ProjectCommentRepository projectCommentRepository, UserInfosService userInfosService, ProjectService projectService) {
        this.projectCommentService = projectCommentService;
        this.projectCommentRepository = projectCommentRepository;
        this.userInfosService = userInfosService;
        this.projectService = projectService;
    }

    /**
     * {@code POST  /project-comments} : Create a new projectComment.
     *
     * @param projectCommentDTO the projectCommentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectCommentDTO, or with status {@code 400 (Bad Request)} if the projectComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-comments")
    public ResponseEntity<ProjectCommentDTO> createProjectComment(@Valid @RequestBody ProjectCommentDTO projectCommentDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProjectComment : {}", projectCommentDTO);
        if (projectCommentDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectComment cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Optional<UserInfosDTO> userInfosDTO = userInfosService.findOne(projectCommentDTO.getUserInfos().getId());
        projectCommentDTO.setUserInfos(userInfosDTO.get());

        Optional<ProjectDTO> projectDTO = projectService.findOne(projectCommentDTO.getProject().getId());
        projectCommentDTO.setProject(projectDTO.get());

        ProjectCommentDTO result = projectCommentService.save(projectCommentDTO);
        return ResponseEntity
            .created(new URI("/api/project-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-comments/:id} : Updates an existing projectComment.
     *
     * @param id the id of the projectCommentDTO to save.
     * @param projectCommentDTO the projectCommentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectCommentDTO,
     * or with status {@code 400 (Bad Request)} if the projectCommentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectCommentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-comments/{id}")
    public ResponseEntity<ProjectCommentDTO> updateProjectComment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProjectCommentDTO projectCommentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectComment : {}, {}", id, projectCommentDTO);
        if (projectCommentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectCommentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectCommentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectCommentDTO result = projectCommentService.save(projectCommentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectCommentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-comments/:id} : Partial updates given fields of an existing projectComment, field will ignore if it is null
     *
     * @param id the id of the projectCommentDTO to save.
     * @param projectCommentDTO the projectCommentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectCommentDTO,
     * or with status {@code 400 (Bad Request)} if the projectCommentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projectCommentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectCommentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-comments/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProjectCommentDTO> partialUpdateProjectComment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProjectCommentDTO projectCommentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectComment partially : {}, {}", id, projectCommentDTO);
        if (projectCommentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectCommentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectCommentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectCommentDTO> result = projectCommentService.partialUpdate(projectCommentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectCommentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /project-comments} : get all the projectComments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectComments in body.
     */
    @GetMapping("/project-comments/project/{id}")
    public ResponseEntity<List<ProjectCommentDTO>> getAllProjectComments(Pageable pageable, @PathVariable Long id) {
        log.debug("REST request to get a page of ProjectComments");
        Page<ProjectCommentDTO> page = projectCommentService.findAll(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-comments/:id} : get the "id" projectComment.
     *
     * @param id the id of the projectCommentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectCommentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-comments/{id}")
    public ResponseEntity<ProjectCommentDTO> getProjectComment(@PathVariable Long id) {
        log.debug("REST request to get ProjectComment : {}", id);
        Optional<ProjectCommentDTO> projectCommentDTO = projectCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectCommentDTO);
    }

    /**
     * {@code DELETE  /project-comments/:id} : delete the "id" projectComment.
     *
     * @param id the id of the projectCommentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-comments/{id}")
    public ResponseEntity<Void> deleteProjectComment(@PathVariable Long id) {
        log.debug("REST request to delete ProjectComment : {}", id);
        projectCommentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
