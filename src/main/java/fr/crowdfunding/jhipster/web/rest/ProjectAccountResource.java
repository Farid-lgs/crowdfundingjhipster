package fr.crowdfunding.jhipster.web.rest;

import fr.crowdfunding.jhipster.repository.ProjectAccountRepository;
import fr.crowdfunding.jhipster.service.ProjectAccountService;
import fr.crowdfunding.jhipster.service.dto.ProjectAccountDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.crowdfunding.jhipster.domain.ProjectAccount}.
 */
@RestController
@RequestMapping("/api")
public class ProjectAccountResource {

    private final Logger log = LoggerFactory.getLogger(ProjectAccountResource.class);

    private static final String ENTITY_NAME = "projectAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectAccountService projectAccountService;

    private final ProjectAccountRepository projectAccountRepository;

    public ProjectAccountResource(ProjectAccountService projectAccountService, ProjectAccountRepository projectAccountRepository) {
        this.projectAccountService = projectAccountService;
        this.projectAccountRepository = projectAccountRepository;
    }

    /**
     * {@code POST  /project-accounts} : Create a new projectAccount.
     *
     * @param projectAccountDTO the projectAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectAccountDTO, or with status {@code 400 (Bad Request)} if the projectAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-accounts")
    public ResponseEntity<ProjectAccountDTO> createProjectAccount(@RequestBody ProjectAccountDTO projectAccountDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProjectAccount : {}", projectAccountDTO);
        if (projectAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectAccountDTO result = projectAccountService.save(projectAccountDTO);
        return ResponseEntity
            .created(new URI("/api/project-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-accounts/:id} : Updates an existing projectAccount.
     *
     * @param id the id of the projectAccountDTO to save.
     * @param projectAccountDTO the projectAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectAccountDTO,
     * or with status {@code 400 (Bad Request)} if the projectAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-accounts/{id}")
    public ResponseEntity<ProjectAccountDTO> updateProjectAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectAccountDTO projectAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectAccount : {}, {}", id, projectAccountDTO);
        if (projectAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectAccountDTO result = projectAccountService.save(projectAccountDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-accounts/:id} : Partial updates given fields of an existing projectAccount, field will ignore if it is null
     *
     * @param id the id of the projectAccountDTO to save.
     * @param projectAccountDTO the projectAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectAccountDTO,
     * or with status {@code 400 (Bad Request)} if the projectAccountDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projectAccountDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-accounts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProjectAccountDTO> partialUpdateProjectAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectAccountDTO projectAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectAccount partially : {}, {}", id, projectAccountDTO);
        if (projectAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectAccountDTO> result = projectAccountService.partialUpdate(projectAccountDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectAccountDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /project-accounts} : get all the projectAccounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectAccounts in body.
     */
    @GetMapping("/project-accounts")
    public ResponseEntity<List<ProjectAccountDTO>> getAllProjectAccounts(Pageable pageable) {
        log.debug("REST request to get a page of ProjectAccounts");
        Page<ProjectAccountDTO> page = projectAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-accounts/:id} : get the "id" projectAccount.
     *
     * @param id the id of the projectAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-accounts/{id}")
    public ResponseEntity<ProjectAccountDTO> getProjectAccount(@PathVariable Long id) {
        log.debug("REST request to get ProjectAccount : {}", id);
        Optional<ProjectAccountDTO> projectAccountDTO = projectAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectAccountDTO);
    }

    /**
     * {@code DELETE  /project-accounts/:id} : delete the "id" projectAccount.
     *
     * @param id the id of the projectAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-accounts/{id}")
    public ResponseEntity<Void> deleteProjectAccount(@PathVariable Long id) {
        log.debug("REST request to delete ProjectAccount : {}", id);
        projectAccountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
