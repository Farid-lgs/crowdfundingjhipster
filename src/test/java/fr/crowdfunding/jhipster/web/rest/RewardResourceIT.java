package fr.crowdfunding.jhipster.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.crowdfunding.jhipster.IntegrationTest;
import fr.crowdfunding.jhipster.domain.Reward;
import fr.crowdfunding.jhipster.repository.RewardRepository;
import fr.crowdfunding.jhipster.service.dto.RewardDTO;
import fr.crowdfunding.jhipster.service.mapper.RewardMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link RewardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RewardResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_MINIMUM_VALUE = 1D;
    private static final Double UPDATED_MINIMUM_VALUE = 2D;

    private static final Instant DEFAULT_DELIVER_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELIVER_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPIRES_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRES_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_MAXIMUM_CONTRIBUTIONS = 1;
    private static final Integer UPDATED_MAXIMUM_CONTRIBUTIONS = 2;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/rewards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private RewardMapper rewardMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRewardMockMvc;

    private Reward reward;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reward createEntity(EntityManager em) {
        Reward reward = new Reward()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .minimumValue(DEFAULT_MINIMUM_VALUE)
            .deliverAt(DEFAULT_DELIVER_AT)
            .expiresAt(DEFAULT_EXPIRES_AT)
            .maximumContributions(DEFAULT_MAXIMUM_CONTRIBUTIONS)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return reward;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reward createUpdatedEntity(EntityManager em) {
        Reward reward = new Reward()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .minimumValue(UPDATED_MINIMUM_VALUE)
            .deliverAt(UPDATED_DELIVER_AT)
            .expiresAt(UPDATED_EXPIRES_AT)
            .maximumContributions(UPDATED_MAXIMUM_CONTRIBUTIONS)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return reward;
    }

    @BeforeEach
    public void initTest() {
        reward = createEntity(em);
    }

    @Test
    @Transactional
    void createReward() throws Exception {
        int databaseSizeBeforeCreate = rewardRepository.findAll().size();
        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);
        restRewardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rewardDTO)))
            .andExpect(status().isCreated());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeCreate + 1);
        Reward testReward = rewardList.get(rewardList.size() - 1);
        assertThat(testReward.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testReward.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testReward.getMinimumValue()).isEqualTo(DEFAULT_MINIMUM_VALUE);
        assertThat(testReward.getDeliverAt()).isEqualTo(DEFAULT_DELIVER_AT);
        assertThat(testReward.getExpiresAt()).isEqualTo(DEFAULT_EXPIRES_AT);
        assertThat(testReward.getMaximumContributions()).isEqualTo(DEFAULT_MAXIMUM_CONTRIBUTIONS);
        assertThat(testReward.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testReward.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createRewardWithExistingId() throws Exception {
        // Create the Reward with an existing ID
        reward.setId(1L);
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        int databaseSizeBeforeCreate = rewardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRewardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rewardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = rewardRepository.findAll().size();
        // set the field null
        reward.setTitle(null);

        // Create the Reward, which fails.
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        restRewardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rewardDTO)))
            .andExpect(status().isBadRequest());

        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = rewardRepository.findAll().size();
        // set the field null
        reward.setDescription(null);

        // Create the Reward, which fails.
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        restRewardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rewardDTO)))
            .andExpect(status().isBadRequest());

        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMinimumValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = rewardRepository.findAll().size();
        // set the field null
        reward.setMinimumValue(null);

        // Create the Reward, which fails.
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        restRewardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rewardDTO)))
            .andExpect(status().isBadRequest());

        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRewards() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        // Get all the rewardList
        restRewardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reward.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].minimumValue").value(hasItem(DEFAULT_MINIMUM_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].deliverAt").value(hasItem(DEFAULT_DELIVER_AT.toString())))
            .andExpect(jsonPath("$.[*].expiresAt").value(hasItem(DEFAULT_EXPIRES_AT.toString())))
            .andExpect(jsonPath("$.[*].maximumContributions").value(hasItem(DEFAULT_MAXIMUM_CONTRIBUTIONS)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    void getReward() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        // Get the reward
        restRewardMockMvc
            .perform(get(ENTITY_API_URL_ID, reward.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reward.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.minimumValue").value(DEFAULT_MINIMUM_VALUE.doubleValue()))
            .andExpect(jsonPath("$.deliverAt").value(DEFAULT_DELIVER_AT.toString()))
            .andExpect(jsonPath("$.expiresAt").value(DEFAULT_EXPIRES_AT.toString()))
            .andExpect(jsonPath("$.maximumContributions").value(DEFAULT_MAXIMUM_CONTRIBUTIONS))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getNonExistingReward() throws Exception {
        // Get the reward
        restRewardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReward() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();

        // Update the reward
        Reward updatedReward = rewardRepository.findById(reward.getId()).get();
        // Disconnect from session so that the updates on updatedReward are not directly saved in db
        em.detach(updatedReward);
        updatedReward
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .minimumValue(UPDATED_MINIMUM_VALUE)
            .deliverAt(UPDATED_DELIVER_AT)
            .expiresAt(UPDATED_EXPIRES_AT)
            .maximumContributions(UPDATED_MAXIMUM_CONTRIBUTIONS)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        RewardDTO rewardDTO = rewardMapper.toDto(updatedReward);

        restRewardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rewardDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rewardDTO))
            )
            .andExpect(status().isOk());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
        Reward testReward = rewardList.get(rewardList.size() - 1);
        assertThat(testReward.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testReward.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testReward.getMinimumValue()).isEqualTo(UPDATED_MINIMUM_VALUE);
        assertThat(testReward.getDeliverAt()).isEqualTo(UPDATED_DELIVER_AT);
        assertThat(testReward.getExpiresAt()).isEqualTo(UPDATED_EXPIRES_AT);
        assertThat(testReward.getMaximumContributions()).isEqualTo(UPDATED_MAXIMUM_CONTRIBUTIONS);
        assertThat(testReward.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testReward.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingReward() throws Exception {
        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();
        reward.setId(count.incrementAndGet());

        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRewardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rewardDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rewardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReward() throws Exception {
        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();
        reward.setId(count.incrementAndGet());

        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rewardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReward() throws Exception {
        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();
        reward.setId(count.incrementAndGet());

        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rewardDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRewardWithPatch() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();

        // Update the reward using partial update
        Reward partialUpdatedReward = new Reward();
        partialUpdatedReward.setId(reward.getId());

        partialUpdatedReward
            .title(UPDATED_TITLE)
            .minimumValue(UPDATED_MINIMUM_VALUE)
            .deliverAt(UPDATED_DELIVER_AT)
            .expiresAt(UPDATED_EXPIRES_AT)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restRewardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReward.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReward))
            )
            .andExpect(status().isOk());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
        Reward testReward = rewardList.get(rewardList.size() - 1);
        assertThat(testReward.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testReward.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testReward.getMinimumValue()).isEqualTo(UPDATED_MINIMUM_VALUE);
        assertThat(testReward.getDeliverAt()).isEqualTo(UPDATED_DELIVER_AT);
        assertThat(testReward.getExpiresAt()).isEqualTo(UPDATED_EXPIRES_AT);
        assertThat(testReward.getMaximumContributions()).isEqualTo(DEFAULT_MAXIMUM_CONTRIBUTIONS);
        assertThat(testReward.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testReward.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateRewardWithPatch() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();

        // Update the reward using partial update
        Reward partialUpdatedReward = new Reward();
        partialUpdatedReward.setId(reward.getId());

        partialUpdatedReward
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .minimumValue(UPDATED_MINIMUM_VALUE)
            .deliverAt(UPDATED_DELIVER_AT)
            .expiresAt(UPDATED_EXPIRES_AT)
            .maximumContributions(UPDATED_MAXIMUM_CONTRIBUTIONS)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restRewardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReward.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReward))
            )
            .andExpect(status().isOk());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
        Reward testReward = rewardList.get(rewardList.size() - 1);
        assertThat(testReward.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testReward.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testReward.getMinimumValue()).isEqualTo(UPDATED_MINIMUM_VALUE);
        assertThat(testReward.getDeliverAt()).isEqualTo(UPDATED_DELIVER_AT);
        assertThat(testReward.getExpiresAt()).isEqualTo(UPDATED_EXPIRES_AT);
        assertThat(testReward.getMaximumContributions()).isEqualTo(UPDATED_MAXIMUM_CONTRIBUTIONS);
        assertThat(testReward.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testReward.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingReward() throws Exception {
        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();
        reward.setId(count.incrementAndGet());

        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRewardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rewardDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rewardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReward() throws Exception {
        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();
        reward.setId(count.incrementAndGet());

        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rewardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReward() throws Exception {
        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();
        reward.setId(count.incrementAndGet());

        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rewardDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReward() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        int databaseSizeBeforeDelete = rewardRepository.findAll().size();

        // Delete the reward
        restRewardMockMvc
            .perform(delete(ENTITY_API_URL_ID, reward.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
