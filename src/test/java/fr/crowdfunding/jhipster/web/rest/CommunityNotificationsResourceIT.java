package fr.crowdfunding.jhipster.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.crowdfunding.jhipster.IntegrationTest;
import fr.crowdfunding.jhipster.domain.CommunityNotifications;
import fr.crowdfunding.jhipster.repository.CommunityNotificationsRepository;
import fr.crowdfunding.jhipster.service.dto.CommunityNotificationsDTO;
import fr.crowdfunding.jhipster.service.mapper.CommunityNotificationsMapper;
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
 * Integration tests for the {@link CommunityNotificationsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommunityNotificationsResourceIT {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_SENT_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SENT_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/community-notifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CommunityNotificationsRepository communityNotificationsRepository;

    @Autowired
    private CommunityNotificationsMapper communityNotificationsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommunityNotificationsMockMvc;

    private CommunityNotifications communityNotifications;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommunityNotifications createEntity(EntityManager em) {
        CommunityNotifications communityNotifications = new CommunityNotifications().content(DEFAULT_CONTENT).sentAt(DEFAULT_SENT_AT);
        return communityNotifications;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommunityNotifications createUpdatedEntity(EntityManager em) {
        CommunityNotifications communityNotifications = new CommunityNotifications().content(UPDATED_CONTENT).sentAt(UPDATED_SENT_AT);
        return communityNotifications;
    }

    @BeforeEach
    public void initTest() {
        communityNotifications = createEntity(em);
    }

    @Test
    @Transactional
    void createCommunityNotifications() throws Exception {
        int databaseSizeBeforeCreate = communityNotificationsRepository.findAll().size();
        // Create the CommunityNotifications
        CommunityNotificationsDTO communityNotificationsDTO = communityNotificationsMapper.toDto(communityNotifications);
        restCommunityNotificationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(communityNotificationsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CommunityNotifications in the database
        List<CommunityNotifications> communityNotificationsList = communityNotificationsRepository.findAll();
        assertThat(communityNotificationsList).hasSize(databaseSizeBeforeCreate + 1);
        CommunityNotifications testCommunityNotifications = communityNotificationsList.get(communityNotificationsList.size() - 1);
        assertThat(testCommunityNotifications.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testCommunityNotifications.getSentAt()).isEqualTo(DEFAULT_SENT_AT);
    }

    @Test
    @Transactional
    void createCommunityNotificationsWithExistingId() throws Exception {
        // Create the CommunityNotifications with an existing ID
        communityNotifications.setId(1L);
        CommunityNotificationsDTO communityNotificationsDTO = communityNotificationsMapper.toDto(communityNotifications);

        int databaseSizeBeforeCreate = communityNotificationsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommunityNotificationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(communityNotificationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommunityNotifications in the database
        List<CommunityNotifications> communityNotificationsList = communityNotificationsRepository.findAll();
        assertThat(communityNotificationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCommunityNotifications() throws Exception {
        // Initialize the database
        communityNotificationsRepository.saveAndFlush(communityNotifications);

        // Get all the communityNotificationsList
        restCommunityNotificationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(communityNotifications.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].sentAt").value(hasItem(DEFAULT_SENT_AT.toString())));
    }

    @Test
    @Transactional
    void getCommunityNotifications() throws Exception {
        // Initialize the database
        communityNotificationsRepository.saveAndFlush(communityNotifications);

        // Get the communityNotifications
        restCommunityNotificationsMockMvc
            .perform(get(ENTITY_API_URL_ID, communityNotifications.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(communityNotifications.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.sentAt").value(DEFAULT_SENT_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCommunityNotifications() throws Exception {
        // Get the communityNotifications
        restCommunityNotificationsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCommunityNotifications() throws Exception {
        // Initialize the database
        communityNotificationsRepository.saveAndFlush(communityNotifications);

        int databaseSizeBeforeUpdate = communityNotificationsRepository.findAll().size();

        // Update the communityNotifications
        CommunityNotifications updatedCommunityNotifications = communityNotificationsRepository
            .findById(communityNotifications.getId())
            .get();
        // Disconnect from session so that the updates on updatedCommunityNotifications are not directly saved in db
        em.detach(updatedCommunityNotifications);
        updatedCommunityNotifications.content(UPDATED_CONTENT).sentAt(UPDATED_SENT_AT);
        CommunityNotificationsDTO communityNotificationsDTO = communityNotificationsMapper.toDto(updatedCommunityNotifications);

        restCommunityNotificationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, communityNotificationsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(communityNotificationsDTO))
            )
            .andExpect(status().isOk());

        // Validate the CommunityNotifications in the database
        List<CommunityNotifications> communityNotificationsList = communityNotificationsRepository.findAll();
        assertThat(communityNotificationsList).hasSize(databaseSizeBeforeUpdate);
        CommunityNotifications testCommunityNotifications = communityNotificationsList.get(communityNotificationsList.size() - 1);
        assertThat(testCommunityNotifications.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testCommunityNotifications.getSentAt()).isEqualTo(UPDATED_SENT_AT);
    }

    @Test
    @Transactional
    void putNonExistingCommunityNotifications() throws Exception {
        int databaseSizeBeforeUpdate = communityNotificationsRepository.findAll().size();
        communityNotifications.setId(count.incrementAndGet());

        // Create the CommunityNotifications
        CommunityNotificationsDTO communityNotificationsDTO = communityNotificationsMapper.toDto(communityNotifications);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommunityNotificationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, communityNotificationsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(communityNotificationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommunityNotifications in the database
        List<CommunityNotifications> communityNotificationsList = communityNotificationsRepository.findAll();
        assertThat(communityNotificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommunityNotifications() throws Exception {
        int databaseSizeBeforeUpdate = communityNotificationsRepository.findAll().size();
        communityNotifications.setId(count.incrementAndGet());

        // Create the CommunityNotifications
        CommunityNotificationsDTO communityNotificationsDTO = communityNotificationsMapper.toDto(communityNotifications);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunityNotificationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(communityNotificationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommunityNotifications in the database
        List<CommunityNotifications> communityNotificationsList = communityNotificationsRepository.findAll();
        assertThat(communityNotificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommunityNotifications() throws Exception {
        int databaseSizeBeforeUpdate = communityNotificationsRepository.findAll().size();
        communityNotifications.setId(count.incrementAndGet());

        // Create the CommunityNotifications
        CommunityNotificationsDTO communityNotificationsDTO = communityNotificationsMapper.toDto(communityNotifications);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunityNotificationsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(communityNotificationsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommunityNotifications in the database
        List<CommunityNotifications> communityNotificationsList = communityNotificationsRepository.findAll();
        assertThat(communityNotificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommunityNotificationsWithPatch() throws Exception {
        // Initialize the database
        communityNotificationsRepository.saveAndFlush(communityNotifications);

        int databaseSizeBeforeUpdate = communityNotificationsRepository.findAll().size();

        // Update the communityNotifications using partial update
        CommunityNotifications partialUpdatedCommunityNotifications = new CommunityNotifications();
        partialUpdatedCommunityNotifications.setId(communityNotifications.getId());

        partialUpdatedCommunityNotifications.sentAt(UPDATED_SENT_AT);

        restCommunityNotificationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommunityNotifications.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommunityNotifications))
            )
            .andExpect(status().isOk());

        // Validate the CommunityNotifications in the database
        List<CommunityNotifications> communityNotificationsList = communityNotificationsRepository.findAll();
        assertThat(communityNotificationsList).hasSize(databaseSizeBeforeUpdate);
        CommunityNotifications testCommunityNotifications = communityNotificationsList.get(communityNotificationsList.size() - 1);
        assertThat(testCommunityNotifications.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testCommunityNotifications.getSentAt()).isEqualTo(UPDATED_SENT_AT);
    }

    @Test
    @Transactional
    void fullUpdateCommunityNotificationsWithPatch() throws Exception {
        // Initialize the database
        communityNotificationsRepository.saveAndFlush(communityNotifications);

        int databaseSizeBeforeUpdate = communityNotificationsRepository.findAll().size();

        // Update the communityNotifications using partial update
        CommunityNotifications partialUpdatedCommunityNotifications = new CommunityNotifications();
        partialUpdatedCommunityNotifications.setId(communityNotifications.getId());

        partialUpdatedCommunityNotifications.content(UPDATED_CONTENT).sentAt(UPDATED_SENT_AT);

        restCommunityNotificationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommunityNotifications.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommunityNotifications))
            )
            .andExpect(status().isOk());

        // Validate the CommunityNotifications in the database
        List<CommunityNotifications> communityNotificationsList = communityNotificationsRepository.findAll();
        assertThat(communityNotificationsList).hasSize(databaseSizeBeforeUpdate);
        CommunityNotifications testCommunityNotifications = communityNotificationsList.get(communityNotificationsList.size() - 1);
        assertThat(testCommunityNotifications.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testCommunityNotifications.getSentAt()).isEqualTo(UPDATED_SENT_AT);
    }

    @Test
    @Transactional
    void patchNonExistingCommunityNotifications() throws Exception {
        int databaseSizeBeforeUpdate = communityNotificationsRepository.findAll().size();
        communityNotifications.setId(count.incrementAndGet());

        // Create the CommunityNotifications
        CommunityNotificationsDTO communityNotificationsDTO = communityNotificationsMapper.toDto(communityNotifications);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommunityNotificationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, communityNotificationsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(communityNotificationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommunityNotifications in the database
        List<CommunityNotifications> communityNotificationsList = communityNotificationsRepository.findAll();
        assertThat(communityNotificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommunityNotifications() throws Exception {
        int databaseSizeBeforeUpdate = communityNotificationsRepository.findAll().size();
        communityNotifications.setId(count.incrementAndGet());

        // Create the CommunityNotifications
        CommunityNotificationsDTO communityNotificationsDTO = communityNotificationsMapper.toDto(communityNotifications);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunityNotificationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(communityNotificationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommunityNotifications in the database
        List<CommunityNotifications> communityNotificationsList = communityNotificationsRepository.findAll();
        assertThat(communityNotificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommunityNotifications() throws Exception {
        int databaseSizeBeforeUpdate = communityNotificationsRepository.findAll().size();
        communityNotifications.setId(count.incrementAndGet());

        // Create the CommunityNotifications
        CommunityNotificationsDTO communityNotificationsDTO = communityNotificationsMapper.toDto(communityNotifications);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunityNotificationsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(communityNotificationsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommunityNotifications in the database
        List<CommunityNotifications> communityNotificationsList = communityNotificationsRepository.findAll();
        assertThat(communityNotificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommunityNotifications() throws Exception {
        // Initialize the database
        communityNotificationsRepository.saveAndFlush(communityNotifications);

        int databaseSizeBeforeDelete = communityNotificationsRepository.findAll().size();

        // Delete the communityNotifications
        restCommunityNotificationsMockMvc
            .perform(delete(ENTITY_API_URL_ID, communityNotifications.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommunityNotifications> communityNotificationsList = communityNotificationsRepository.findAll();
        assertThat(communityNotificationsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
