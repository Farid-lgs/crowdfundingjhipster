package fr.crowdfunding.jhipster.web.rest;

import fr.crowdfunding.jhipster.repository.CommunityMembersRepository;
import fr.crowdfunding.jhipster.service.CommunityMembersService;
import fr.crowdfunding.jhipster.service.dto.CommunityMembersDTO;
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
 * REST controller for managing {@link fr.crowdfunding.jhipster.domain.CommunityMembers}.
 */
@RestController
@RequestMapping("/api")
public class CommunityMembersResource {

    private final Logger log = LoggerFactory.getLogger(CommunityMembersResource.class);

    private static final String ENTITY_NAME = "communityMembers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommunityMembersService communityMembersService;

    private final CommunityMembersRepository communityMembersRepository;

    public CommunityMembersResource(
        CommunityMembersService communityMembersService,
        CommunityMembersRepository communityMembersRepository
    ) {
        this.communityMembersService = communityMembersService;
        this.communityMembersRepository = communityMembersRepository;
    }

    /**
     * {@code POST  /community-members} : Create a new communityMembers.
     *
     * @param communityMembersDTO the communityMembersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new communityMembersDTO, or with status {@code 400 (Bad Request)} if the communityMembers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/community-members")
    public ResponseEntity<CommunityMembersDTO> createCommunityMembers(@RequestBody CommunityMembersDTO communityMembersDTO)
        throws URISyntaxException {
        log.debug("REST request to save CommunityMembers : {}", communityMembersDTO);
        if (communityMembersDTO.getId() != null) {
            throw new BadRequestAlertException("A new communityMembers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommunityMembersDTO result = communityMembersService.save(communityMembersDTO);
        return ResponseEntity
            .created(new URI("/api/community-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /community-members/:id} : Updates an existing communityMembers.
     *
     * @param id the id of the communityMembersDTO to save.
     * @param communityMembersDTO the communityMembersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated communityMembersDTO,
     * or with status {@code 400 (Bad Request)} if the communityMembersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the communityMembersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/community-members/{id}")
    public ResponseEntity<CommunityMembersDTO> updateCommunityMembers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommunityMembersDTO communityMembersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CommunityMembers : {}, {}", id, communityMembersDTO);
        if (communityMembersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, communityMembersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!communityMembersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommunityMembersDTO result = communityMembersService.save(communityMembersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, communityMembersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /community-members/:id} : Partial updates given fields of an existing communityMembers, field will ignore if it is null
     *
     * @param id the id of the communityMembersDTO to save.
     * @param communityMembersDTO the communityMembersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated communityMembersDTO,
     * or with status {@code 400 (Bad Request)} if the communityMembersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the communityMembersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the communityMembersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/community-members/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CommunityMembersDTO> partialUpdateCommunityMembers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommunityMembersDTO communityMembersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CommunityMembers partially : {}, {}", id, communityMembersDTO);
        if (communityMembersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, communityMembersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!communityMembersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommunityMembersDTO> result = communityMembersService.partialUpdate(communityMembersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, communityMembersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /community-members} : get all the communityMembers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of communityMembers in body.
     */
    @GetMapping("/community-members")
    public ResponseEntity<List<CommunityMembersDTO>> getAllCommunityMembers(Pageable pageable) {
        log.debug("REST request to get a page of CommunityMembers");
        Page<CommunityMembersDTO> page = communityMembersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /community-members/:id} : get the "id" communityMembers.
     *
     * @param id the id of the communityMembersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the communityMembersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/community-members/{id}")
    public ResponseEntity<CommunityMembersDTO> getCommunityMembers(@PathVariable Long id) {
        log.debug("REST request to get CommunityMembers : {}", id);
        Optional<CommunityMembersDTO> communityMembersDTO = communityMembersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(communityMembersDTO);
    }

    /**
     * {@code DELETE  /community-members/:id} : delete the "id" communityMembers.
     *
     * @param id the id of the communityMembersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/community-members/{id}")
    public ResponseEntity<Void> deleteCommunityMembers(@PathVariable Long id) {
        log.debug("REST request to delete CommunityMembers : {}", id);
        communityMembersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
