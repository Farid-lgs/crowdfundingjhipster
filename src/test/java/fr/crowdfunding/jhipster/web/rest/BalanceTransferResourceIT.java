package fr.crowdfunding.jhipster.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.crowdfunding.jhipster.IntegrationTest;
import fr.crowdfunding.jhipster.domain.BalanceTransfer;
import fr.crowdfunding.jhipster.repository.BalanceTransferRepository;
import fr.crowdfunding.jhipster.service.dto.BalanceTransferDTO;
import fr.crowdfunding.jhipster.service.mapper.BalanceTransferMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BalanceTransferResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BalanceTransferResourceIT {

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String ENTITY_API_URL = "/api/balance-transfers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BalanceTransferRepository balanceTransferRepository;

    @Autowired
    private BalanceTransferMapper balanceTransferMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBalanceTransferMockMvc;

    private BalanceTransfer balanceTransfer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BalanceTransfer createEntity(EntityManager em) {
        BalanceTransfer balanceTransfer = new BalanceTransfer().amount(DEFAULT_AMOUNT);
        return balanceTransfer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BalanceTransfer createUpdatedEntity(EntityManager em) {
        BalanceTransfer balanceTransfer = new BalanceTransfer().amount(UPDATED_AMOUNT);
        return balanceTransfer;
    }

    @BeforeEach
    public void initTest() {
        balanceTransfer = createEntity(em);
    }

    @Test
    @Transactional
    void createBalanceTransfer() throws Exception {
        int databaseSizeBeforeCreate = balanceTransferRepository.findAll().size();
        // Create the BalanceTransfer
        BalanceTransferDTO balanceTransferDTO = balanceTransferMapper.toDto(balanceTransfer);
        restBalanceTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(balanceTransferDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BalanceTransfer in the database
        List<BalanceTransfer> balanceTransferList = balanceTransferRepository.findAll();
        assertThat(balanceTransferList).hasSize(databaseSizeBeforeCreate + 1);
        BalanceTransfer testBalanceTransfer = balanceTransferList.get(balanceTransferList.size() - 1);
        assertThat(testBalanceTransfer.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void createBalanceTransferWithExistingId() throws Exception {
        // Create the BalanceTransfer with an existing ID
        balanceTransfer.setId(1L);
        BalanceTransferDTO balanceTransferDTO = balanceTransferMapper.toDto(balanceTransfer);

        int databaseSizeBeforeCreate = balanceTransferRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBalanceTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(balanceTransferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BalanceTransfer in the database
        List<BalanceTransfer> balanceTransferList = balanceTransferRepository.findAll();
        assertThat(balanceTransferList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = balanceTransferRepository.findAll().size();
        // set the field null
        balanceTransfer.setAmount(null);

        // Create the BalanceTransfer, which fails.
        BalanceTransferDTO balanceTransferDTO = balanceTransferMapper.toDto(balanceTransfer);

        restBalanceTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(balanceTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<BalanceTransfer> balanceTransferList = balanceTransferRepository.findAll();
        assertThat(balanceTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBalanceTransfers() throws Exception {
        // Initialize the database
        balanceTransferRepository.saveAndFlush(balanceTransfer);

        // Get all the balanceTransferList
        restBalanceTransferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(balanceTransfer.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    void getBalanceTransfer() throws Exception {
        // Initialize the database
        balanceTransferRepository.saveAndFlush(balanceTransfer);

        // Get the balanceTransfer
        restBalanceTransferMockMvc
            .perform(get(ENTITY_API_URL_ID, balanceTransfer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(balanceTransfer.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingBalanceTransfer() throws Exception {
        // Get the balanceTransfer
        restBalanceTransferMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBalanceTransfer() throws Exception {
        // Initialize the database
        balanceTransferRepository.saveAndFlush(balanceTransfer);

        int databaseSizeBeforeUpdate = balanceTransferRepository.findAll().size();

        // Update the balanceTransfer
        BalanceTransfer updatedBalanceTransfer = balanceTransferRepository.findById(balanceTransfer.getId()).get();
        // Disconnect from session so that the updates on updatedBalanceTransfer are not directly saved in db
        em.detach(updatedBalanceTransfer);
        updatedBalanceTransfer.amount(UPDATED_AMOUNT);
        BalanceTransferDTO balanceTransferDTO = balanceTransferMapper.toDto(updatedBalanceTransfer);

        restBalanceTransferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, balanceTransferDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(balanceTransferDTO))
            )
            .andExpect(status().isOk());

        // Validate the BalanceTransfer in the database
        List<BalanceTransfer> balanceTransferList = balanceTransferRepository.findAll();
        assertThat(balanceTransferList).hasSize(databaseSizeBeforeUpdate);
        BalanceTransfer testBalanceTransfer = balanceTransferList.get(balanceTransferList.size() - 1);
        assertThat(testBalanceTransfer.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void putNonExistingBalanceTransfer() throws Exception {
        int databaseSizeBeforeUpdate = balanceTransferRepository.findAll().size();
        balanceTransfer.setId(count.incrementAndGet());

        // Create the BalanceTransfer
        BalanceTransferDTO balanceTransferDTO = balanceTransferMapper.toDto(balanceTransfer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBalanceTransferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, balanceTransferDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(balanceTransferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BalanceTransfer in the database
        List<BalanceTransfer> balanceTransferList = balanceTransferRepository.findAll();
        assertThat(balanceTransferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBalanceTransfer() throws Exception {
        int databaseSizeBeforeUpdate = balanceTransferRepository.findAll().size();
        balanceTransfer.setId(count.incrementAndGet());

        // Create the BalanceTransfer
        BalanceTransferDTO balanceTransferDTO = balanceTransferMapper.toDto(balanceTransfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalanceTransferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(balanceTransferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BalanceTransfer in the database
        List<BalanceTransfer> balanceTransferList = balanceTransferRepository.findAll();
        assertThat(balanceTransferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBalanceTransfer() throws Exception {
        int databaseSizeBeforeUpdate = balanceTransferRepository.findAll().size();
        balanceTransfer.setId(count.incrementAndGet());

        // Create the BalanceTransfer
        BalanceTransferDTO balanceTransferDTO = balanceTransferMapper.toDto(balanceTransfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalanceTransferMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(balanceTransferDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BalanceTransfer in the database
        List<BalanceTransfer> balanceTransferList = balanceTransferRepository.findAll();
        assertThat(balanceTransferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBalanceTransferWithPatch() throws Exception {
        // Initialize the database
        balanceTransferRepository.saveAndFlush(balanceTransfer);

        int databaseSizeBeforeUpdate = balanceTransferRepository.findAll().size();

        // Update the balanceTransfer using partial update
        BalanceTransfer partialUpdatedBalanceTransfer = new BalanceTransfer();
        partialUpdatedBalanceTransfer.setId(balanceTransfer.getId());

        restBalanceTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBalanceTransfer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBalanceTransfer))
            )
            .andExpect(status().isOk());

        // Validate the BalanceTransfer in the database
        List<BalanceTransfer> balanceTransferList = balanceTransferRepository.findAll();
        assertThat(balanceTransferList).hasSize(databaseSizeBeforeUpdate);
        BalanceTransfer testBalanceTransfer = balanceTransferList.get(balanceTransferList.size() - 1);
        assertThat(testBalanceTransfer.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdateBalanceTransferWithPatch() throws Exception {
        // Initialize the database
        balanceTransferRepository.saveAndFlush(balanceTransfer);

        int databaseSizeBeforeUpdate = balanceTransferRepository.findAll().size();

        // Update the balanceTransfer using partial update
        BalanceTransfer partialUpdatedBalanceTransfer = new BalanceTransfer();
        partialUpdatedBalanceTransfer.setId(balanceTransfer.getId());

        partialUpdatedBalanceTransfer.amount(UPDATED_AMOUNT);

        restBalanceTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBalanceTransfer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBalanceTransfer))
            )
            .andExpect(status().isOk());

        // Validate the BalanceTransfer in the database
        List<BalanceTransfer> balanceTransferList = balanceTransferRepository.findAll();
        assertThat(balanceTransferList).hasSize(databaseSizeBeforeUpdate);
        BalanceTransfer testBalanceTransfer = balanceTransferList.get(balanceTransferList.size() - 1);
        assertThat(testBalanceTransfer.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingBalanceTransfer() throws Exception {
        int databaseSizeBeforeUpdate = balanceTransferRepository.findAll().size();
        balanceTransfer.setId(count.incrementAndGet());

        // Create the BalanceTransfer
        BalanceTransferDTO balanceTransferDTO = balanceTransferMapper.toDto(balanceTransfer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBalanceTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, balanceTransferDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(balanceTransferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BalanceTransfer in the database
        List<BalanceTransfer> balanceTransferList = balanceTransferRepository.findAll();
        assertThat(balanceTransferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBalanceTransfer() throws Exception {
        int databaseSizeBeforeUpdate = balanceTransferRepository.findAll().size();
        balanceTransfer.setId(count.incrementAndGet());

        // Create the BalanceTransfer
        BalanceTransferDTO balanceTransferDTO = balanceTransferMapper.toDto(balanceTransfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalanceTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(balanceTransferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BalanceTransfer in the database
        List<BalanceTransfer> balanceTransferList = balanceTransferRepository.findAll();
        assertThat(balanceTransferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBalanceTransfer() throws Exception {
        int databaseSizeBeforeUpdate = balanceTransferRepository.findAll().size();
        balanceTransfer.setId(count.incrementAndGet());

        // Create the BalanceTransfer
        BalanceTransferDTO balanceTransferDTO = balanceTransferMapper.toDto(balanceTransfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalanceTransferMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(balanceTransferDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BalanceTransfer in the database
        List<BalanceTransfer> balanceTransferList = balanceTransferRepository.findAll();
        assertThat(balanceTransferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBalanceTransfer() throws Exception {
        // Initialize the database
        balanceTransferRepository.saveAndFlush(balanceTransfer);

        int databaseSizeBeforeDelete = balanceTransferRepository.findAll().size();

        // Delete the balanceTransfer
        restBalanceTransferMockMvc
            .perform(delete(ENTITY_API_URL_ID, balanceTransfer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BalanceTransfer> balanceTransferList = balanceTransferRepository.findAll();
        assertThat(balanceTransferList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
