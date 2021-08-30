package fr.crowdfunding.jhipster.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.crowdfunding.jhipster.IntegrationTest;
import fr.crowdfunding.jhipster.domain.Contribution;
import fr.crowdfunding.jhipster.repository.ContributionRepository;
import fr.crowdfunding.jhipster.service.dto.ContributionDTO;
import fr.crowdfunding.jhipster.service.mapper.ContributionMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ContributionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContributionResourceIT {

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_PAYER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PAYER_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ANONYMOUS = false;
    private static final Boolean UPDATED_ANONYMOUS = true;

    private static final Boolean DEFAULT_REWARDED = false;
    private static final Boolean UPDATED_REWARDED = true;

    private static final String ENTITY_API_URL = "/api/contributions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private ContributionMapper contributionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContributionMockMvc;

    private Contribution contribution;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contribution createEntity(EntityManager em) {
        Contribution contribution = new Contribution()
            .amount(DEFAULT_AMOUNT)
            .payerName(DEFAULT_PAYER_NAME)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .anonymous(DEFAULT_ANONYMOUS)
            .rewarded(DEFAULT_REWARDED);
        return contribution;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contribution createUpdatedEntity(EntityManager em) {
        Contribution contribution = new Contribution()
            .amount(UPDATED_AMOUNT)
            .payerName(UPDATED_PAYER_NAME)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .anonymous(UPDATED_ANONYMOUS)
            .rewarded(UPDATED_REWARDED);
        return contribution;
    }

    @BeforeEach
    public void initTest() {
        contribution = createEntity(em);
    }

    @Test
    @Transactional
    void createContribution() throws Exception {
        int databaseSizeBeforeCreate = contributionRepository.findAll().size();
        // Create the Contribution
        ContributionDTO contributionDTO = contributionMapper.toDto(contribution);
        restContributionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contributionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeCreate + 1);
        Contribution testContribution = contributionList.get(contributionList.size() - 1);
        assertThat(testContribution.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testContribution.getPayerName()).isEqualTo(DEFAULT_PAYER_NAME);
        assertThat(testContribution.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testContribution.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testContribution.getAnonymous()).isEqualTo(DEFAULT_ANONYMOUS);
        assertThat(testContribution.getRewarded()).isEqualTo(DEFAULT_REWARDED);
    }

    @Test
    @Transactional
    void createContributionWithExistingId() throws Exception {
        // Create the Contribution with an existing ID
        contribution.setId(1L);
        ContributionDTO contributionDTO = contributionMapper.toDto(contribution);

        int databaseSizeBeforeCreate = contributionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContributionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contributionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = contributionRepository.findAll().size();
        // set the field null
        contribution.setAmount(null);

        // Create the Contribution, which fails.
        ContributionDTO contributionDTO = contributionMapper.toDto(contribution);

        restContributionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contributionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPayerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contributionRepository.findAll().size();
        // set the field null
        contribution.setPayerName(null);

        // Create the Contribution, which fails.
        ContributionDTO contributionDTO = contributionMapper.toDto(contribution);

        restContributionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contributionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnonymousIsRequired() throws Exception {
        int databaseSizeBeforeTest = contributionRepository.findAll().size();
        // set the field null
        contribution.setAnonymous(null);

        // Create the Contribution, which fails.
        ContributionDTO contributionDTO = contributionMapper.toDto(contribution);

        restContributionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contributionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRewardedIsRequired() throws Exception {
        int databaseSizeBeforeTest = contributionRepository.findAll().size();
        // set the field null
        contribution.setRewarded(null);

        // Create the Contribution, which fails.
        ContributionDTO contributionDTO = contributionMapper.toDto(contribution);

        restContributionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contributionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContributions() throws Exception {
        // Initialize the database
        contributionRepository.saveAndFlush(contribution);

        // Get all the contributionList
        restContributionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contribution.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].payerName").value(hasItem(DEFAULT_PAYER_NAME)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].anonymous").value(hasItem(DEFAULT_ANONYMOUS.booleanValue())))
            .andExpect(jsonPath("$.[*].rewarded").value(hasItem(DEFAULT_REWARDED.booleanValue())));
    }

    @Test
    @Transactional
    void getContribution() throws Exception {
        // Initialize the database
        contributionRepository.saveAndFlush(contribution);

        // Get the contribution
        restContributionMockMvc
            .perform(get(ENTITY_API_URL_ID, contribution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contribution.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.payerName").value(DEFAULT_PAYER_NAME))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.anonymous").value(DEFAULT_ANONYMOUS.booleanValue()))
            .andExpect(jsonPath("$.rewarded").value(DEFAULT_REWARDED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingContribution() throws Exception {
        // Get the contribution
        restContributionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContribution() throws Exception {
        // Initialize the database
        contributionRepository.saveAndFlush(contribution);

        int databaseSizeBeforeUpdate = contributionRepository.findAll().size();

        // Update the contribution
        Contribution updatedContribution = contributionRepository.findById(contribution.getId()).get();
        // Disconnect from session so that the updates on updatedContribution are not directly saved in db
        em.detach(updatedContribution);
        updatedContribution
            .amount(UPDATED_AMOUNT)
            .payerName(UPDATED_PAYER_NAME)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .anonymous(UPDATED_ANONYMOUS)
            .rewarded(UPDATED_REWARDED);
        ContributionDTO contributionDTO = contributionMapper.toDto(updatedContribution);

        restContributionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contributionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contributionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeUpdate);
        Contribution testContribution = contributionList.get(contributionList.size() - 1);
        assertThat(testContribution.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testContribution.getPayerName()).isEqualTo(UPDATED_PAYER_NAME);
        assertThat(testContribution.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testContribution.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testContribution.getAnonymous()).isEqualTo(UPDATED_ANONYMOUS);
        assertThat(testContribution.getRewarded()).isEqualTo(UPDATED_REWARDED);
    }

    @Test
    @Transactional
    void putNonExistingContribution() throws Exception {
        int databaseSizeBeforeUpdate = contributionRepository.findAll().size();
        contribution.setId(count.incrementAndGet());

        // Create the Contribution
        ContributionDTO contributionDTO = contributionMapper.toDto(contribution);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContributionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contributionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contributionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContribution() throws Exception {
        int databaseSizeBeforeUpdate = contributionRepository.findAll().size();
        contribution.setId(count.incrementAndGet());

        // Create the Contribution
        ContributionDTO contributionDTO = contributionMapper.toDto(contribution);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContributionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contributionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContribution() throws Exception {
        int databaseSizeBeforeUpdate = contributionRepository.findAll().size();
        contribution.setId(count.incrementAndGet());

        // Create the Contribution
        ContributionDTO contributionDTO = contributionMapper.toDto(contribution);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContributionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contributionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContributionWithPatch() throws Exception {
        // Initialize the database
        contributionRepository.saveAndFlush(contribution);

        int databaseSizeBeforeUpdate = contributionRepository.findAll().size();

        // Update the contribution using partial update
        Contribution partialUpdatedContribution = new Contribution();
        partialUpdatedContribution.setId(contribution.getId());

        partialUpdatedContribution.payerName(UPDATED_PAYER_NAME).updatedAt(UPDATED_UPDATED_AT).anonymous(UPDATED_ANONYMOUS);

        restContributionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContribution.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContribution))
            )
            .andExpect(status().isOk());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeUpdate);
        Contribution testContribution = contributionList.get(contributionList.size() - 1);
        assertThat(testContribution.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testContribution.getPayerName()).isEqualTo(UPDATED_PAYER_NAME);
        assertThat(testContribution.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testContribution.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testContribution.getAnonymous()).isEqualTo(UPDATED_ANONYMOUS);
        assertThat(testContribution.getRewarded()).isEqualTo(DEFAULT_REWARDED);
    }

    @Test
    @Transactional
    void fullUpdateContributionWithPatch() throws Exception {
        // Initialize the database
        contributionRepository.saveAndFlush(contribution);

        int databaseSizeBeforeUpdate = contributionRepository.findAll().size();

        // Update the contribution using partial update
        Contribution partialUpdatedContribution = new Contribution();
        partialUpdatedContribution.setId(contribution.getId());

        partialUpdatedContribution
            .amount(UPDATED_AMOUNT)
            .payerName(UPDATED_PAYER_NAME)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .anonymous(UPDATED_ANONYMOUS)
            .rewarded(UPDATED_REWARDED);

        restContributionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContribution.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContribution))
            )
            .andExpect(status().isOk());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeUpdate);
        Contribution testContribution = contributionList.get(contributionList.size() - 1);
        assertThat(testContribution.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testContribution.getPayerName()).isEqualTo(UPDATED_PAYER_NAME);
        assertThat(testContribution.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testContribution.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testContribution.getAnonymous()).isEqualTo(UPDATED_ANONYMOUS);
        assertThat(testContribution.getRewarded()).isEqualTo(UPDATED_REWARDED);
    }

    @Test
    @Transactional
    void patchNonExistingContribution() throws Exception {
        int databaseSizeBeforeUpdate = contributionRepository.findAll().size();
        contribution.setId(count.incrementAndGet());

        // Create the Contribution
        ContributionDTO contributionDTO = contributionMapper.toDto(contribution);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContributionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contributionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contributionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContribution() throws Exception {
        int databaseSizeBeforeUpdate = contributionRepository.findAll().size();
        contribution.setId(count.incrementAndGet());

        // Create the Contribution
        ContributionDTO contributionDTO = contributionMapper.toDto(contribution);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContributionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contributionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContribution() throws Exception {
        int databaseSizeBeforeUpdate = contributionRepository.findAll().size();
        contribution.setId(count.incrementAndGet());

        // Create the Contribution
        ContributionDTO contributionDTO = contributionMapper.toDto(contribution);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContributionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contributionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContribution() throws Exception {
        // Initialize the database
        contributionRepository.saveAndFlush(contribution);

        int databaseSizeBeforeDelete = contributionRepository.findAll().size();

        // Delete the contribution
        restContributionMockMvc
            .perform(delete(ENTITY_API_URL_ID, contribution.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
