package fr.crowdfunding.jhipster.web.rest;

import fr.crowdfunding.jhipster.repository.CreditCardRepository;
import fr.crowdfunding.jhipster.service.CreditCardService;
import fr.crowdfunding.jhipster.service.dto.CreditCardDTO;
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
 * REST controller for managing {@link fr.crowdfunding.jhipster.domain.CreditCard}.
 */
@RestController
@RequestMapping("/api")
public class CreditCardResource {

    private final Logger log = LoggerFactory.getLogger(CreditCardResource.class);

    private static final String ENTITY_NAME = "creditCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreditCardService creditCardService;

    private final CreditCardRepository creditCardRepository;

    public CreditCardResource(CreditCardService creditCardService, CreditCardRepository creditCardRepository) {
        this.creditCardService = creditCardService;
        this.creditCardRepository = creditCardRepository;
    }

    /**
     * {@code POST  /credit-cards} : Create a new creditCard.
     *
     * @param creditCardDTO the creditCardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new creditCardDTO, or with status {@code 400 (Bad Request)} if the creditCard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/credit-cards")
    public ResponseEntity<CreditCardDTO> createCreditCard(@Valid @RequestBody CreditCardDTO creditCardDTO) throws URISyntaxException {
        log.debug("REST request to save CreditCard : {}", creditCardDTO);
        if (creditCardDTO.getId() != null) {
            throw new BadRequestAlertException("A new creditCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditCardDTO result = creditCardService.save(creditCardDTO);
        return ResponseEntity
            .created(new URI("/api/credit-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /credit-cards/:id} : Updates an existing creditCard.
     *
     * @param id the id of the creditCardDTO to save.
     * @param creditCardDTO the creditCardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditCardDTO,
     * or with status {@code 400 (Bad Request)} if the creditCardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the creditCardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/credit-cards/{id}")
    public ResponseEntity<CreditCardDTO> updateCreditCard(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CreditCardDTO creditCardDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CreditCard : {}, {}", id, creditCardDTO);
        if (creditCardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creditCardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creditCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CreditCardDTO result = creditCardService.save(creditCardDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creditCardDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /credit-cards/:id} : Partial updates given fields of an existing creditCard, field will ignore if it is null
     *
     * @param id the id of the creditCardDTO to save.
     * @param creditCardDTO the creditCardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditCardDTO,
     * or with status {@code 400 (Bad Request)} if the creditCardDTO is not valid,
     * or with status {@code 404 (Not Found)} if the creditCardDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the creditCardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/credit-cards/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CreditCardDTO> partialUpdateCreditCard(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CreditCardDTO creditCardDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CreditCard partially : {}, {}", id, creditCardDTO);
        if (creditCardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creditCardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creditCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CreditCardDTO> result = creditCardService.partialUpdate(creditCardDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creditCardDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /credit-cards} : get all the creditCards.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of creditCards in body.
     */
    @GetMapping("/credit-cards")
    public ResponseEntity<List<CreditCardDTO>> getAllCreditCards(Pageable pageable) {
        log.debug("REST request to get a page of CreditCards");
        Page<CreditCardDTO> page = creditCardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /credit-cards/:id} : get the "id" creditCard.
     *
     * @param id the id of the creditCardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the creditCardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/credit-cards/{id}")
    public ResponseEntity<CreditCardDTO> getCreditCard(@PathVariable Long id) {
        log.debug("REST request to get CreditCard : {}", id);
        Optional<CreditCardDTO> creditCardDTO = creditCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditCardDTO);
    }

    /**
     * {@code DELETE  /credit-cards/:id} : delete the "id" creditCard.
     *
     * @param id the id of the creditCardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/credit-cards/{id}")
    public ResponseEntity<Void> deleteCreditCard(@PathVariable Long id) {
        log.debug("REST request to delete CreditCard : {}", id);
        creditCardService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
