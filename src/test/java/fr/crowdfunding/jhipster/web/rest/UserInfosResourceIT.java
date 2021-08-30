package fr.crowdfunding.jhipster.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.crowdfunding.jhipster.IntegrationTest;
import fr.crowdfunding.jhipster.domain.User;
import fr.crowdfunding.jhipster.domain.UserInfos;
import fr.crowdfunding.jhipster.repository.UserInfosRepository;
import fr.crowdfunding.jhipster.service.dto.UserInfosDTO;
import fr.crowdfunding.jhipster.service.mapper.UserInfosMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.UUID;
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
 * Integration tests for the {@link UserInfosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserInfosResourceIT {

    private static final String DEFAULT_PUBLIC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PUBLIC_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TWITTER = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_LINKED_IN = "AAAAAAAAAA";
    private static final String UPDATED_LINKED_IN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COVER_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COVER_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_COVER_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COVER_IMAGE_CONTENT_TYPE = "image/png";

    private static final UUID DEFAULT_COMMON_ID = UUID.randomUUID();
    private static final UUID UPDATED_COMMON_ID = UUID.randomUUID();

    private static final String ENTITY_API_URL = "/api/user-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserInfosRepository userInfosRepository;

    @Autowired
    private UserInfosMapper userInfosMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserInfosMockMvc;

    private UserInfos userInfos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserInfos createEntity(EntityManager em) {
        UserInfos userInfos = new UserInfos()
            .publicName(DEFAULT_PUBLIC_NAME)
            .birthDate(DEFAULT_BIRTH_DATE)
            .twitter(DEFAULT_TWITTER)
            .facebook(DEFAULT_FACEBOOK)
            .linkedIn(DEFAULT_LINKED_IN)
            .description(DEFAULT_DESCRIPTION)
            .coverImage(DEFAULT_COVER_IMAGE)
            .coverImageContentType(DEFAULT_COVER_IMAGE_CONTENT_TYPE)
            .commonId(DEFAULT_COMMON_ID);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userInfos.setUser(user);
        return userInfos;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserInfos createUpdatedEntity(EntityManager em) {
        UserInfos userInfos = new UserInfos()
            .publicName(UPDATED_PUBLIC_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .twitter(UPDATED_TWITTER)
            .facebook(UPDATED_FACEBOOK)
            .linkedIn(UPDATED_LINKED_IN)
            .description(UPDATED_DESCRIPTION)
            .coverImage(UPDATED_COVER_IMAGE)
            .coverImageContentType(UPDATED_COVER_IMAGE_CONTENT_TYPE)
            .commonId(UPDATED_COMMON_ID);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userInfos.setUser(user);
        return userInfos;
    }

    @BeforeEach
    public void initTest() {
        userInfos = createEntity(em);
    }

    @Test
    @Transactional
    void createUserInfos() throws Exception {
        int databaseSizeBeforeCreate = userInfosRepository.findAll().size();
        // Create the UserInfos
        UserInfosDTO userInfosDTO = userInfosMapper.toDto(userInfos);
        restUserInfosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userInfosDTO)))
            .andExpect(status().isCreated());

        // Validate the UserInfos in the database
        List<UserInfos> userInfosList = userInfosRepository.findAll();
        assertThat(userInfosList).hasSize(databaseSizeBeforeCreate + 1);
        UserInfos testUserInfos = userInfosList.get(userInfosList.size() - 1);
        assertThat(testUserInfos.getPublicName()).isEqualTo(DEFAULT_PUBLIC_NAME);
        assertThat(testUserInfos.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testUserInfos.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testUserInfos.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testUserInfos.getLinkedIn()).isEqualTo(DEFAULT_LINKED_IN);
        assertThat(testUserInfos.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUserInfos.getCoverImage()).isEqualTo(DEFAULT_COVER_IMAGE);
        assertThat(testUserInfos.getCoverImageContentType()).isEqualTo(DEFAULT_COVER_IMAGE_CONTENT_TYPE);
        assertThat(testUserInfos.getCommonId()).isEqualTo(DEFAULT_COMMON_ID);

        // Validate the id for MapsId, the ids must be same
        assertThat(testUserInfos.getId()).isEqualTo(testUserInfos.getUser().getId());
    }

    @Test
    @Transactional
    void createUserInfosWithExistingId() throws Exception {
        // Create the UserInfos with an existing ID
        userInfos.setId(1L);
        UserInfosDTO userInfosDTO = userInfosMapper.toDto(userInfos);

        int databaseSizeBeforeCreate = userInfosRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserInfosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userInfosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserInfos in the database
        List<UserInfos> userInfosList = userInfosRepository.findAll();
        assertThat(userInfosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void updateUserInfosMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        userInfosRepository.saveAndFlush(userInfos);
        int databaseSizeBeforeCreate = userInfosRepository.findAll().size();

        // Add a new parent entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();

        // Load the userInfos
        UserInfos updatedUserInfos = userInfosRepository.findById(userInfos.getId()).get();
        assertThat(updatedUserInfos).isNotNull();
        // Disconnect from session so that the updates on updatedUserInfos are not directly saved in db
        em.detach(updatedUserInfos);

        // Update the User with new association value
        updatedUserInfos.setUser(user);
        UserInfosDTO updatedUserInfosDTO = userInfosMapper.toDto(updatedUserInfos);
        assertThat(updatedUserInfosDTO).isNotNull();

        // Update the entity
        restUserInfosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserInfosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserInfosDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserInfos in the database
        List<UserInfos> userInfosList = userInfosRepository.findAll();
        assertThat(userInfosList).hasSize(databaseSizeBeforeCreate);
        UserInfos testUserInfos = userInfosList.get(userInfosList.size() - 1);
        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testUserInfos.getId()).isEqualTo(testUserInfos.getUser().getId());
    }

    @Test
    @Transactional
    void checkPublicNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfosRepository.findAll().size();
        // set the field null
        userInfos.setPublicName(null);

        // Create the UserInfos, which fails.
        UserInfosDTO userInfosDTO = userInfosMapper.toDto(userInfos);

        restUserInfosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userInfosDTO)))
            .andExpect(status().isBadRequest());

        List<UserInfos> userInfosList = userInfosRepository.findAll();
        assertThat(userInfosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserInfos() throws Exception {
        // Initialize the database
        userInfosRepository.saveAndFlush(userInfos);

        // Get all the userInfosList
        restUserInfosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userInfos.getId().intValue())))
            .andExpect(jsonPath("$.[*].publicName").value(hasItem(DEFAULT_PUBLIC_NAME)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].linkedIn").value(hasItem(DEFAULT_LINKED_IN)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].coverImageContentType").value(hasItem(DEFAULT_COVER_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].coverImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_COVER_IMAGE))))
            .andExpect(jsonPath("$.[*].commonId").value(hasItem(DEFAULT_COMMON_ID.toString())));
    }

    @Test
    @Transactional
    void getUserInfos() throws Exception {
        // Initialize the database
        userInfosRepository.saveAndFlush(userInfos);

        // Get the userInfos
        restUserInfosMockMvc
            .perform(get(ENTITY_API_URL_ID, userInfos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userInfos.getId().intValue()))
            .andExpect(jsonPath("$.publicName").value(DEFAULT_PUBLIC_NAME))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK))
            .andExpect(jsonPath("$.linkedIn").value(DEFAULT_LINKED_IN))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.coverImageContentType").value(DEFAULT_COVER_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.coverImage").value(Base64Utils.encodeToString(DEFAULT_COVER_IMAGE)))
            .andExpect(jsonPath("$.commonId").value(DEFAULT_COMMON_ID.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserInfos() throws Exception {
        // Get the userInfos
        restUserInfosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserInfos() throws Exception {
        // Initialize the database
        userInfosRepository.saveAndFlush(userInfos);

        int databaseSizeBeforeUpdate = userInfosRepository.findAll().size();

        // Update the userInfos
        UserInfos updatedUserInfos = userInfosRepository.findById(userInfos.getId()).get();
        // Disconnect from session so that the updates on updatedUserInfos are not directly saved in db
        em.detach(updatedUserInfos);
        updatedUserInfos
            .publicName(UPDATED_PUBLIC_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .twitter(UPDATED_TWITTER)
            .facebook(UPDATED_FACEBOOK)
            .linkedIn(UPDATED_LINKED_IN)
            .description(UPDATED_DESCRIPTION)
            .coverImage(UPDATED_COVER_IMAGE)
            .coverImageContentType(UPDATED_COVER_IMAGE_CONTENT_TYPE)
            .commonId(UPDATED_COMMON_ID);
        UserInfosDTO userInfosDTO = userInfosMapper.toDto(updatedUserInfos);

        restUserInfosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userInfosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userInfosDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserInfos in the database
        List<UserInfos> userInfosList = userInfosRepository.findAll();
        assertThat(userInfosList).hasSize(databaseSizeBeforeUpdate);
        UserInfos testUserInfos = userInfosList.get(userInfosList.size() - 1);
        assertThat(testUserInfos.getPublicName()).isEqualTo(UPDATED_PUBLIC_NAME);
        assertThat(testUserInfos.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testUserInfos.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testUserInfos.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testUserInfos.getLinkedIn()).isEqualTo(UPDATED_LINKED_IN);
        assertThat(testUserInfos.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUserInfos.getCoverImage()).isEqualTo(UPDATED_COVER_IMAGE);
        assertThat(testUserInfos.getCoverImageContentType()).isEqualTo(UPDATED_COVER_IMAGE_CONTENT_TYPE);
        assertThat(testUserInfos.getCommonId()).isEqualTo(UPDATED_COMMON_ID);
    }

    @Test
    @Transactional
    void putNonExistingUserInfos() throws Exception {
        int databaseSizeBeforeUpdate = userInfosRepository.findAll().size();
        userInfos.setId(count.incrementAndGet());

        // Create the UserInfos
        UserInfosDTO userInfosDTO = userInfosMapper.toDto(userInfos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserInfosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userInfosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userInfosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserInfos in the database
        List<UserInfos> userInfosList = userInfosRepository.findAll();
        assertThat(userInfosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserInfos() throws Exception {
        int databaseSizeBeforeUpdate = userInfosRepository.findAll().size();
        userInfos.setId(count.incrementAndGet());

        // Create the UserInfos
        UserInfosDTO userInfosDTO = userInfosMapper.toDto(userInfos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserInfosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userInfosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserInfos in the database
        List<UserInfos> userInfosList = userInfosRepository.findAll();
        assertThat(userInfosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserInfos() throws Exception {
        int databaseSizeBeforeUpdate = userInfosRepository.findAll().size();
        userInfos.setId(count.incrementAndGet());

        // Create the UserInfos
        UserInfosDTO userInfosDTO = userInfosMapper.toDto(userInfos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserInfosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userInfosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserInfos in the database
        List<UserInfos> userInfosList = userInfosRepository.findAll();
        assertThat(userInfosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserInfosWithPatch() throws Exception {
        // Initialize the database
        userInfosRepository.saveAndFlush(userInfos);

        int databaseSizeBeforeUpdate = userInfosRepository.findAll().size();

        // Update the userInfos using partial update
        UserInfos partialUpdatedUserInfos = new UserInfos();
        partialUpdatedUserInfos.setId(userInfos.getId());

        partialUpdatedUserInfos
            .twitter(UPDATED_TWITTER)
            .linkedIn(UPDATED_LINKED_IN)
            .description(UPDATED_DESCRIPTION)
            .commonId(UPDATED_COMMON_ID);

        restUserInfosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserInfos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserInfos))
            )
            .andExpect(status().isOk());

        // Validate the UserInfos in the database
        List<UserInfos> userInfosList = userInfosRepository.findAll();
        assertThat(userInfosList).hasSize(databaseSizeBeforeUpdate);
        UserInfos testUserInfos = userInfosList.get(userInfosList.size() - 1);
        assertThat(testUserInfos.getPublicName()).isEqualTo(DEFAULT_PUBLIC_NAME);
        assertThat(testUserInfos.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testUserInfos.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testUserInfos.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testUserInfos.getLinkedIn()).isEqualTo(UPDATED_LINKED_IN);
        assertThat(testUserInfos.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUserInfos.getCoverImage()).isEqualTo(DEFAULT_COVER_IMAGE);
        assertThat(testUserInfos.getCoverImageContentType()).isEqualTo(DEFAULT_COVER_IMAGE_CONTENT_TYPE);
        assertThat(testUserInfos.getCommonId()).isEqualTo(UPDATED_COMMON_ID);
    }

    @Test
    @Transactional
    void fullUpdateUserInfosWithPatch() throws Exception {
        // Initialize the database
        userInfosRepository.saveAndFlush(userInfos);

        int databaseSizeBeforeUpdate = userInfosRepository.findAll().size();

        // Update the userInfos using partial update
        UserInfos partialUpdatedUserInfos = new UserInfos();
        partialUpdatedUserInfos.setId(userInfos.getId());

        partialUpdatedUserInfos
            .publicName(UPDATED_PUBLIC_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .twitter(UPDATED_TWITTER)
            .facebook(UPDATED_FACEBOOK)
            .linkedIn(UPDATED_LINKED_IN)
            .description(UPDATED_DESCRIPTION)
            .coverImage(UPDATED_COVER_IMAGE)
            .coverImageContentType(UPDATED_COVER_IMAGE_CONTENT_TYPE)
            .commonId(UPDATED_COMMON_ID);

        restUserInfosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserInfos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserInfos))
            )
            .andExpect(status().isOk());

        // Validate the UserInfos in the database
        List<UserInfos> userInfosList = userInfosRepository.findAll();
        assertThat(userInfosList).hasSize(databaseSizeBeforeUpdate);
        UserInfos testUserInfos = userInfosList.get(userInfosList.size() - 1);
        assertThat(testUserInfos.getPublicName()).isEqualTo(UPDATED_PUBLIC_NAME);
        assertThat(testUserInfos.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testUserInfos.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testUserInfos.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testUserInfos.getLinkedIn()).isEqualTo(UPDATED_LINKED_IN);
        assertThat(testUserInfos.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUserInfos.getCoverImage()).isEqualTo(UPDATED_COVER_IMAGE);
        assertThat(testUserInfos.getCoverImageContentType()).isEqualTo(UPDATED_COVER_IMAGE_CONTENT_TYPE);
        assertThat(testUserInfos.getCommonId()).isEqualTo(UPDATED_COMMON_ID);
    }

    @Test
    @Transactional
    void patchNonExistingUserInfos() throws Exception {
        int databaseSizeBeforeUpdate = userInfosRepository.findAll().size();
        userInfos.setId(count.incrementAndGet());

        // Create the UserInfos
        UserInfosDTO userInfosDTO = userInfosMapper.toDto(userInfos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserInfosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userInfosDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userInfosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserInfos in the database
        List<UserInfos> userInfosList = userInfosRepository.findAll();
        assertThat(userInfosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserInfos() throws Exception {
        int databaseSizeBeforeUpdate = userInfosRepository.findAll().size();
        userInfos.setId(count.incrementAndGet());

        // Create the UserInfos
        UserInfosDTO userInfosDTO = userInfosMapper.toDto(userInfos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserInfosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userInfosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserInfos in the database
        List<UserInfos> userInfosList = userInfosRepository.findAll();
        assertThat(userInfosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserInfos() throws Exception {
        int databaseSizeBeforeUpdate = userInfosRepository.findAll().size();
        userInfos.setId(count.incrementAndGet());

        // Create the UserInfos
        UserInfosDTO userInfosDTO = userInfosMapper.toDto(userInfos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserInfosMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userInfosDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserInfos in the database
        List<UserInfos> userInfosList = userInfosRepository.findAll();
        assertThat(userInfosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserInfos() throws Exception {
        // Initialize the database
        userInfosRepository.saveAndFlush(userInfos);

        int databaseSizeBeforeDelete = userInfosRepository.findAll().size();

        // Delete the userInfos
        restUserInfosMockMvc
            .perform(delete(ENTITY_API_URL_ID, userInfos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserInfos> userInfosList = userInfosRepository.findAll();
        assertThat(userInfosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
