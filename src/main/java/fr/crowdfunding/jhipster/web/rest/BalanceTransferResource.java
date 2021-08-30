package fr.crowdfunding.jhipster.web.rest;

import fr.crowdfunding.jhipster.repository.BalanceTransferRepository;
import fr.crowdfunding.jhipster.service.BalanceTransferService;
import fr.crowdfunding.jhipster.service.dto.BalanceTransferDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.crowdfunding.jhipster.domain.BalanceTransfer}.
 */
@RestController
@RequestMapping("/api")
public class BalanceTransferResource {

    private final Logger log = LoggerFactory.getLogger(BalanceTransferResource.class);

    private static final String ENTITY_NAME = "balanceTransfer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BalanceTransferService balanceTransferService;

    private final BalanceTransferRepository balanceTransferRepository;

    public BalanceTransferResource(BalanceTransferService balanceTransferService, BalanceTransferRepository balanceTransferRepository) {
        this.balanceTransferService = balanceTransferService;
        this.balanceTransferRepository = balanceTransferRepository;
    }

    /**
     * {@code POST  /balance-transfers} : Create a new balanceTransfer.
     *
     * @param balanceTransferDTO the balanceTransferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new balanceTransferDTO, or with status {@code 400 (Bad Request)} if the balanceTransfer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/balance-transfers")
    public ResponseEntity<BalanceTransferDTO> createBalanceTransfer(@Valid @RequestBody BalanceTransferDTO balanceTransferDTO)
        throws URISyntaxException {
        log.debug("REST request to save BalanceTransfer : {}", balanceTransferDTO);
        if (balanceTransferDTO.getId() != null) {
            throw new BadRequestAlertException("A new balanceTransfer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BalanceTransferDTO result = balanceTransferService.save(balanceTransferDTO);
        return ResponseEntity
            .created(new URI("/api/balance-transfers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /balance-transfers/:id} : Updates an existing balanceTransfer.
     *
     * @param id the id of the balanceTransferDTO to save.
     * @param balanceTransferDTO the balanceTransferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated balanceTransferDTO,
     * or with status {@code 400 (Bad Request)} if the balanceTransferDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the balanceTransferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/balance-transfers/{id}")
    public ResponseEntity<BalanceTransferDTO> updateBalanceTransfer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BalanceTransferDTO balanceTransferDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BalanceTransfer : {}, {}", id, balanceTransferDTO);
        if (balanceTransferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, balanceTransferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!balanceTransferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BalanceTransferDTO result = balanceTransferService.save(balanceTransferDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, balanceTransferDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /balance-transfers/:id} : Partial updates given fields of an existing balanceTransfer, field will ignore if it is null
     *
     * @param id the id of the balanceTransferDTO to save.
     * @param balanceTransferDTO the balanceTransferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated balanceTransferDTO,
     * or with status {@code 400 (Bad Request)} if the balanceTransferDTO is not valid,
     * or with status {@code 404 (Not Found)} if the balanceTransferDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the balanceTransferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/balance-transfers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BalanceTransferDTO> partialUpdateBalanceTransfer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BalanceTransferDTO balanceTransferDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BalanceTransfer partially : {}, {}", id, balanceTransferDTO);
        if (balanceTransferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, balanceTransferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!balanceTransferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BalanceTransferDTO> result = balanceTransferService.partialUpdate(balanceTransferDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, balanceTransferDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /balance-transfers} : get all the balanceTransfers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of balanceTransfers in body.
     */
    @GetMapping("/balance-transfers")
    public ResponseEntity<List<BalanceTransferDTO>> getAllBalanceTransfers(Pageable pageable) {
        log.debug("REST request to get a page of BalanceTransfers");
        Page<BalanceTransferDTO> page = balanceTransferService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /balance-transfers/:id} : get the "id" balanceTransfer.
     *
     * @param id the id of the balanceTransferDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the balanceTransferDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/balance-transfers/{id}")
    public ResponseEntity<BalanceTransferDTO> getBalanceTransfer(@PathVariable Long id) {
        log.debug("REST request to get BalanceTransfer : {}", id);
        Optional<BalanceTransferDTO> balanceTransferDTO = balanceTransferService.findOne(id);
        return ResponseUtil.wrapOrNotFound(balanceTransferDTO);
    }

    /**
     * {@code DELETE  /balance-transfers/:id} : delete the "id" balanceTransfer.
     *
     * @param id the id of the balanceTransferDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/balance-transfers/{id}")
    public ResponseEntity<Void> deleteBalanceTransfer(@PathVariable Long id) {
        log.debug("REST request to delete BalanceTransfer : {}", id);
        balanceTransferService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
