package fr.crowdfunding.jhipster.web.rest;

import fr.crowdfunding.jhipster.repository.ContributionNotificationsRepository;
import fr.crowdfunding.jhipster.service.ContributionNotificationsService;
import fr.crowdfunding.jhipster.service.dto.ContributionNotificationsDTO;
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
 * REST controller for managing {@link fr.crowdfunding.jhipster.domain.ContributionNotifications}.
 */
@RestController
@RequestMapping("/api")
public class ContributionNotificationsResource {

    private final Logger log = LoggerFactory.getLogger(ContributionNotificationsResource.class);

    private static final String ENTITY_NAME = "contributionNotifications";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContributionNotificationsService contributionNotificationsService;

    private final ContributionNotificationsRepository contributionNotificationsRepository;

    public ContributionNotificationsResource(
        ContributionNotificationsService contributionNotificationsService,
        ContributionNotificationsRepository contributionNotificationsRepository
    ) {
        this.contributionNotificationsService = contributionNotificationsService;
        this.contributionNotificationsRepository = contributionNotificationsRepository;
    }

    /**
     * {@code POST  /contribution-notifications} : Create a new contributionNotifications.
     *
     * @param contributionNotificationsDTO the contributionNotificationsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contributionNotificationsDTO, or with status {@code 400 (Bad Request)} if the contributionNotifications has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contribution-notifications")
    public ResponseEntity<ContributionNotificationsDTO> createContributionNotifications(
        @RequestBody ContributionNotificationsDTO contributionNotificationsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ContributionNotifications : {}", contributionNotificationsDTO);
        if (contributionNotificationsDTO.getId() != null) {
            throw new BadRequestAlertException("A new contributionNotifications cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContributionNotificationsDTO result = contributionNotificationsService.save(contributionNotificationsDTO);
        return ResponseEntity
            .created(new URI("/api/contribution-notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contribution-notifications/:id} : Updates an existing contributionNotifications.
     *
     * @param id the id of the contributionNotificationsDTO to save.
     * @param contributionNotificationsDTO the contributionNotificationsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contributionNotificationsDTO,
     * or with status {@code 400 (Bad Request)} if the contributionNotificationsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contributionNotificationsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contribution-notifications/{id}")
    public ResponseEntity<ContributionNotificationsDTO> updateContributionNotifications(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContributionNotificationsDTO contributionNotificationsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ContributionNotifications : {}, {}", id, contributionNotificationsDTO);
        if (contributionNotificationsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contributionNotificationsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contributionNotificationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContributionNotificationsDTO result = contributionNotificationsService.save(contributionNotificationsDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contributionNotificationsDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /contribution-notifications/:id} : Partial updates given fields of an existing contributionNotifications, field will ignore if it is null
     *
     * @param id the id of the contributionNotificationsDTO to save.
     * @param contributionNotificationsDTO the contributionNotificationsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contributionNotificationsDTO,
     * or with status {@code 400 (Bad Request)} if the contributionNotificationsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contributionNotificationsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contributionNotificationsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contribution-notifications/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ContributionNotificationsDTO> partialUpdateContributionNotifications(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContributionNotificationsDTO contributionNotificationsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContributionNotifications partially : {}, {}", id, contributionNotificationsDTO);
        if (contributionNotificationsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contributionNotificationsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contributionNotificationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContributionNotificationsDTO> result = contributionNotificationsService.partialUpdate(contributionNotificationsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contributionNotificationsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /contribution-notifications} : get all the contributionNotifications.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contributionNotifications in body.
     */
    @GetMapping("/contribution-notifications")
    public ResponseEntity<List<ContributionNotificationsDTO>> getAllContributionNotifications(
        Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("contribution-is-null".equals(filter)) {
            log.debug("REST request to get all ContributionNotificationss where contribution is null");
            return new ResponseEntity<>(contributionNotificationsService.findAllWhereContributionIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of ContributionNotifications");
        Page<ContributionNotificationsDTO> page = contributionNotificationsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contribution-notifications/:id} : get the "id" contributionNotifications.
     *
     * @param id the id of the contributionNotificationsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contributionNotificationsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contribution-notifications/{id}")
    public ResponseEntity<ContributionNotificationsDTO> getContributionNotifications(@PathVariable Long id) {
        log.debug("REST request to get ContributionNotifications : {}", id);
        Optional<ContributionNotificationsDTO> contributionNotificationsDTO = contributionNotificationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contributionNotificationsDTO);
    }

    /**
     * {@code DELETE  /contribution-notifications/:id} : delete the "id" contributionNotifications.
     *
     * @param id the id of the contributionNotificationsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contribution-notifications/{id}")
    public ResponseEntity<Void> deleteContributionNotifications(@PathVariable Long id) {
        log.debug("REST request to delete ContributionNotifications : {}", id);
        contributionNotificationsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
