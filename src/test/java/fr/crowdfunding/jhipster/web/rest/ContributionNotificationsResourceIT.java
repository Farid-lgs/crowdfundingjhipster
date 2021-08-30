package fr.crowdfunding.jhipster.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.crowdfunding.jhipster.IntegrationTest;
import fr.crowdfunding.jhipster.domain.ContributionNotifications;
import fr.crowdfunding.jhipster.repository.ContributionNotificationsRepository;
import fr.crowdfunding.jhipster.service.dto.ContributionNotificationsDTO;
import fr.crowdfunding.jhipster.service.mapper.ContributionNotificationsMapper;
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
 * Integration tests for the {@link ContributionNotificationsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContributionNotificationsResourceIT {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_SENT_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SENT_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/contribution-notifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContributionNotificationsRepository contributionNotificationsRepository;

    @Autowired
    private ContributionNotificationsMapper contributionNotificationsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContributionNotificationsMockMvc;

    private ContributionNotifications contributionNotifications;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContributionNotifications createEntity(EntityManager em) {
        ContributionNotifications contributionNotifications = new ContributionNotifications()
            .content(DEFAULT_CONTENT)
            .sentAt(DEFAULT_SENT_AT);
        return contributionNotifications;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContributionNotifications createUpdatedEntity(EntityManager em) {
        ContributionNotifications contributionNotifications = new ContributionNotifications()
            .content(UPDATED_CONTENT)
            .sentAt(UPDATED_SENT_AT);
        return contributionNotifications;
    }

    @BeforeEach
    public void initTest() {
        contributionNotifications = createEntity(em);
    }

    @Test
    @Transactional
    void createContributionNotifications() throws Exception {
        int databaseSizeBeforeCreate = contributionNotificationsRepository.findAll().size();
        // Create the ContributionNotifications
        ContributionNotificationsDTO contributionNotificationsDTO = contributionNotificationsMapper.toDto(contributionNotifications);
        restContributionNotificationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contributionNotificationsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ContributionNotifications in the database
        List<ContributionNotifications> contributionNotificationsList = contributionNotificationsRepository.findAll();
        assertThat(contributionNotificationsList).hasSize(databaseSizeBeforeCreate + 1);
        ContributionNotifications testContributionNotifications = contributionNotificationsList.get(
            contributionNotificationsList.size() - 1
        );
        assertThat(testContributionNotifications.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testContributionNotifications.getSentAt()).isEqualTo(DEFAULT_SENT_AT);
    }

    @Test
    @Transactional
    void createContributionNotificationsWithExistingId() throws Exception {
        // Create the ContributionNotifications with an existing ID
        contributionNotifications.setId(1L);
        ContributionNotificationsDTO contributionNotificationsDTO = contributionNotificationsMapper.toDto(contributionNotifications);

        int databaseSizeBeforeCreate = contributionNotificationsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContributionNotificationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contributionNotificationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContributionNotifications in the database
        List<ContributionNotifications> contributionNotificationsList = contributionNotificationsRepository.findAll();
        assertThat(contributionNotificationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContributionNotifications() throws Exception {
        // Initialize the database
        contributionNotificationsRepository.saveAndFlush(contributionNotifications);

        // Get all the contributionNotificationsList
        restContributionNotificationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contributionNotifications.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].sentAt").value(hasItem(DEFAULT_SENT_AT.toString())));
    }

    @Test
    @Transactional
    void getContributionNotifications() throws Exception {
        // Initialize the database
        contributionNotificationsRepository.saveAndFlush(contributionNotifications);

        // Get the contributionNotifications
        restContributionNotificationsMockMvc
            .perform(get(ENTITY_API_URL_ID, contributionNotifications.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contributionNotifications.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.sentAt").value(DEFAULT_SENT_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingContributionNotifications() throws Exception {
        // Get the contributionNotifications
        restContributionNotificationsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContributionNotifications() throws Exception {
        // Initialize the database
        contributionNotificationsRepository.saveAndFlush(contributionNotifications);

        int databaseSizeBeforeUpdate = contributionNotificationsRepository.findAll().size();

        // Update the contributionNotifications
        ContributionNotifications updatedContributionNotifications = contributionNotificationsRepository
            .findById(contributionNotifications.getId())
            .get();
        // Disconnect from session so that the updates on updatedContributionNotifications are not directly saved in db
        em.detach(updatedContributionNotifications);
        updatedContributionNotifications.content(UPDATED_CONTENT).sentAt(UPDATED_SENT_AT);
        ContributionNotificationsDTO contributionNotificationsDTO = contributionNotificationsMapper.toDto(updatedContributionNotifications);

        restContributionNotificationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contributionNotificationsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contributionNotificationsDTO))
            )
            .andExpect(status().isOk());

        // Validate the ContributionNotifications in the database
        List<ContributionNotifications> contributionNotificationsList = contributionNotificationsRepository.findAll();
        assertThat(contributionNotificationsList).hasSize(databaseSizeBeforeUpdate);
        ContributionNotifications testContributionNotifications = contributionNotificationsList.get(
            contributionNotificationsList.size() - 1
        );
        assertThat(testContributionNotifications.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testContributionNotifications.getSentAt()).isEqualTo(UPDATED_SENT_AT);
    }

    @Test
    @Transactional
    void putNonExistingContributionNotifications() throws Exception {
        int databaseSizeBeforeUpdate = contributionNotificationsRepository.findAll().size();
        contributionNotifications.setId(count.incrementAndGet());

        // Create the ContributionNotifications
        ContributionNotificationsDTO contributionNotificationsDTO = contributionNotificationsMapper.toDto(contributionNotifications);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContributionNotificationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contributionNotificationsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contributionNotificationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContributionNotifications in the database
        List<ContributionNotifications> contributionNotificationsList = contributionNotificationsRepository.findAll();
        assertThat(contributionNotificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContributionNotifications() throws Exception {
        int databaseSizeBeforeUpdate = contributionNotificationsRepository.findAll().size();
        contributionNotifications.setId(count.incrementAndGet());

        // Create the ContributionNotifications
        ContributionNotificationsDTO contributionNotificationsDTO = contributionNotificationsMapper.toDto(contributionNotifications);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContributionNotificationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contributionNotificationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContributionNotifications in the database
        List<ContributionNotifications> contributionNotificationsList = contributionNotificationsRepository.findAll();
        assertThat(contributionNotificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContributionNotifications() throws Exception {
        int databaseSizeBeforeUpdate = contributionNotificationsRepository.findAll().size();
        contributionNotifications.setId(count.incrementAndGet());

        // Create the ContributionNotifications
        ContributionNotificationsDTO contributionNotificationsDTO = contributionNotificationsMapper.toDto(contributionNotifications);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContributionNotificationsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contributionNotificationsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContributionNotifications in the database
        List<ContributionNotifications> contributionNotificationsList = contributionNotificationsRepository.findAll();
        assertThat(contributionNotificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContributionNotificationsWithPatch() throws Exception {
        // Initialize the database
        contributionNotificationsRepository.saveAndFlush(contributionNotifications);

        int databaseSizeBeforeUpdate = contributionNotificationsRepository.findAll().size();

        // Update the contributionNotifications using partial update
        ContributionNotifications partialUpdatedContributionNotifications = new ContributionNotifications();
        partialUpdatedContributionNotifications.setId(contributionNotifications.getId());

        partialUpdatedContributionNotifications.content(UPDATED_CONTENT).sentAt(UPDATED_SENT_AT);

        restContributionNotificationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContributionNotifications.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContributionNotifications))
            )
            .andExpect(status().isOk());

        // Validate the ContributionNotifications in the database
        List<ContributionNotifications> contributionNotificationsList = contributionNotificationsRepository.findAll();
        assertThat(contributionNotificationsList).hasSize(databaseSizeBeforeUpdate);
        ContributionNotifications testContributionNotifications = contributionNotificationsList.get(
            contributionNotificationsList.size() - 1
        );
        assertThat(testContributionNotifications.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testContributionNotifications.getSentAt()).isEqualTo(UPDATED_SENT_AT);
    }

    @Test
    @Transactional
    void fullUpdateContributionNotificationsWithPatch() throws Exception {
        // Initialize the database
        contributionNotificationsRepository.saveAndFlush(contributionNotifications);

        int databaseSizeBeforeUpdate = contributionNotificationsRepository.findAll().size();

        // Update the contributionNotifications using partial update
        ContributionNotifications partialUpdatedContributionNotifications = new ContributionNotifications();
        partialUpdatedContributionNotifications.setId(contributionNotifications.getId());

        partialUpdatedContributionNotifications.content(UPDATED_CONTENT).sentAt(UPDATED_SENT_AT);

        restContributionNotificationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContributionNotifications.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContributionNotifications))
            )
            .andExpect(status().isOk());

        // Validate the ContributionNotifications in the database
        List<ContributionNotifications> contributionNotificationsList = contributionNotificationsRepository.findAll();
        assertThat(contributionNotificationsList).hasSize(databaseSizeBeforeUpdate);
        ContributionNotifications testContributionNotifications = contributionNotificationsList.get(
            contributionNotificationsList.size() - 1
        );
        assertThat(testContributionNotifications.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testContributionNotifications.getSentAt()).isEqualTo(UPDATED_SENT_AT);
    }

    @Test
    @Transactional
    void patchNonExistingContributionNotifications() throws Exception {
        int databaseSizeBeforeUpdate = contributionNotificationsRepository.findAll().size();
        contributionNotifications.setId(count.incrementAndGet());

        // Create the ContributionNotifications
        ContributionNotificationsDTO contributionNotificationsDTO = contributionNotificationsMapper.toDto(contributionNotifications);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContributionNotificationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contributionNotificationsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contributionNotificationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContributionNotifications in the database
        List<ContributionNotifications> contributionNotificationsList = contributionNotificationsRepository.findAll();
        assertThat(contributionNotificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContributionNotifications() throws Exception {
        int databaseSizeBeforeUpdate = contributionNotificationsRepository.findAll().size();
        contributionNotifications.setId(count.incrementAndGet());

        // Create the ContributionNotifications
        ContributionNotificationsDTO contributionNotificationsDTO = contributionNotificationsMapper.toDto(contributionNotifications);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContributionNotificationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contributionNotificationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContributionNotifications in the database
        List<ContributionNotifications> contributionNotificationsList = contributionNotificationsRepository.findAll();
        assertThat(contributionNotificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContributionNotifications() throws Exception {
        int databaseSizeBeforeUpdate = contributionNotificationsRepository.findAll().size();
        contributionNotifications.setId(count.incrementAndGet());

        // Create the ContributionNotifications
        ContributionNotificationsDTO contributionNotificationsDTO = contributionNotificationsMapper.toDto(contributionNotifications);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContributionNotificationsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contributionNotificationsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContributionNotifications in the database
        List<ContributionNotifications> contributionNotificationsList = contributionNotificationsRepository.findAll();
        assertThat(contributionNotificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContributionNotifications() throws Exception {
        // Initialize the database
        contributionNotificationsRepository.saveAndFlush(contributionNotifications);

        int databaseSizeBeforeDelete = contributionNotificationsRepository.findAll().size();

        // Delete the contributionNotifications
        restContributionNotificationsMockMvc
            .perform(delete(ENTITY_API_URL_ID, contributionNotifications.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContributionNotifications> contributionNotificationsList = contributionNotificationsRepository.findAll();
        assertThat(contributionNotificationsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
