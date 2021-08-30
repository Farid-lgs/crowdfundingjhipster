package fr.crowdfunding.jhipster.web.rest;

import fr.crowdfunding.jhipster.repository.CommunityNotificationsRepository;
import fr.crowdfunding.jhipster.service.CommunityNotificationsService;
import fr.crowdfunding.jhipster.service.dto.CommunityNotificationsDTO;
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
 * REST controller for managing {@link fr.crowdfunding.jhipster.domain.CommunityNotifications}.
 */
@RestController
@RequestMapping("/api")
public class CommunityNotificationsResource {

    private final Logger log = LoggerFactory.getLogger(CommunityNotificationsResource.class);

    private static final String ENTITY_NAME = "communityNotifications";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommunityNotificationsService communityNotificationsService;

    private final CommunityNotificationsRepository communityNotificationsRepository;

    public CommunityNotificationsResource(
        CommunityNotificationsService communityNotificationsService,
        CommunityNotificationsRepository communityNotificationsRepository
    ) {
        this.communityNotificationsService = communityNotificationsService;
        this.communityNotificationsRepository = communityNotificationsRepository;
    }

    /**
     * {@code POST  /community-notifications} : Create a new communityNotifications.
     *
     * @param communityNotificationsDTO the communityNotificationsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new communityNotificationsDTO, or with status {@code 400 (Bad Request)} if the communityNotifications has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/community-notifications")
    public ResponseEntity<CommunityNotificationsDTO> createCommunityNotifications(
        @RequestBody CommunityNotificationsDTO communityNotificationsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CommunityNotifications : {}", communityNotificationsDTO);
        if (communityNotificationsDTO.getId() != null) {
            throw new BadRequestAlertException("A new communityNotifications cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommunityNotificationsDTO result = communityNotificationsService.save(communityNotificationsDTO);
        return ResponseEntity
            .created(new URI("/api/community-notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /community-notifications/:id} : Updates an existing communityNotifications.
     *
     * @param id the id of the communityNotificationsDTO to save.
     * @param communityNotificationsDTO the communityNotificationsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated communityNotificationsDTO,
     * or with status {@code 400 (Bad Request)} if the communityNotificationsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the communityNotificationsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/community-notifications/{id}")
    public ResponseEntity<CommunityNotificationsDTO> updateCommunityNotifications(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommunityNotificationsDTO communityNotificationsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CommunityNotifications : {}, {}", id, communityNotificationsDTO);
        if (communityNotificationsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, communityNotificationsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!communityNotificationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommunityNotificationsDTO result = communityNotificationsService.save(communityNotificationsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, communityNotificationsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /community-notifications/:id} : Partial updates given fields of an existing communityNotifications, field will ignore if it is null
     *
     * @param id the id of the communityNotificationsDTO to save.
     * @param communityNotificationsDTO the communityNotificationsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated communityNotificationsDTO,
     * or with status {@code 400 (Bad Request)} if the communityNotificationsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the communityNotificationsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the communityNotificationsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/community-notifications/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CommunityNotificationsDTO> partialUpdateCommunityNotifications(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommunityNotificationsDTO communityNotificationsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CommunityNotifications partially : {}, {}", id, communityNotificationsDTO);
        if (communityNotificationsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, communityNotificationsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!communityNotificationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommunityNotificationsDTO> result = communityNotificationsService.partialUpdate(communityNotificationsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, communityNotificationsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /community-notifications} : get all the communityNotifications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of communityNotifications in body.
     */
    @GetMapping("/community-notifications")
    public ResponseEntity<List<CommunityNotificationsDTO>> getAllCommunityNotifications(Pageable pageable) {
        log.debug("REST request to get a page of CommunityNotifications");
        Page<CommunityNotificationsDTO> page = communityNotificationsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /community-notifications/:id} : get the "id" communityNotifications.
     *
     * @param id the id of the communityNotificationsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the communityNotificationsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/community-notifications/{id}")
    public ResponseEntity<CommunityNotificationsDTO> getCommunityNotifications(@PathVariable Long id) {
        log.debug("REST request to get CommunityNotifications : {}", id);
        Optional<CommunityNotificationsDTO> communityNotificationsDTO = communityNotificationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(communityNotificationsDTO);
    }

    /**
     * {@code DELETE  /community-notifications/:id} : delete the "id" communityNotifications.
     *
     * @param id the id of the communityNotificationsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/community-notifications/{id}")
    public ResponseEntity<Void> deleteCommunityNotifications(@PathVariable Long id) {
        log.debug("REST request to delete CommunityNotifications : {}", id);
        communityNotificationsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
