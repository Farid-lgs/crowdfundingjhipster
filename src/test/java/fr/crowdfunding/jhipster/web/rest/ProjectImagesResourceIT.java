package fr.crowdfunding.jhipster.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.crowdfunding.jhipster.IntegrationTest;
import fr.crowdfunding.jhipster.domain.ProjectImages;
import fr.crowdfunding.jhipster.repository.ProjectImagesRepository;
import fr.crowdfunding.jhipster.service.dto.ProjectImagesDTO;
import fr.crowdfunding.jhipster.service.mapper.ProjectImagesMapper;
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
 * Integration tests for the {@link ProjectImagesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectImagesResourceIT {

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/project-images";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectImagesRepository projectImagesRepository;

    @Autowired
    private ProjectImagesMapper projectImagesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectImagesMockMvc;

    private ProjectImages projectImages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectImages createEntity(EntityManager em) {
        ProjectImages projectImages = new ProjectImages().image(DEFAULT_IMAGE).imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return projectImages;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectImages createUpdatedEntity(EntityManager em) {
        ProjectImages projectImages = new ProjectImages().image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return projectImages;
    }

    @BeforeEach
    public void initTest() {
        projectImages = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectImages() throws Exception {
        int databaseSizeBeforeCreate = projectImagesRepository.findAll().size();
        // Create the ProjectImages
        ProjectImagesDTO projectImagesDTO = projectImagesMapper.toDto(projectImages);
        restProjectImagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectImagesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectImages in the database
        List<ProjectImages> projectImagesList = projectImagesRepository.findAll();
        assertThat(projectImagesList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectImages testProjectImages = projectImagesList.get(projectImagesList.size() - 1);
        assertThat(testProjectImages.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testProjectImages.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createProjectImagesWithExistingId() throws Exception {
        // Create the ProjectImages with an existing ID
        projectImages.setId(1L);
        ProjectImagesDTO projectImagesDTO = projectImagesMapper.toDto(projectImages);

        int databaseSizeBeforeCreate = projectImagesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectImagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectImagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectImages in the database
        List<ProjectImages> projectImagesList = projectImagesRepository.findAll();
        assertThat(projectImagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectImages() throws Exception {
        // Initialize the database
        projectImagesRepository.saveAndFlush(projectImages);

        // Get all the projectImagesList
        restProjectImagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectImages.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    void getProjectImages() throws Exception {
        // Initialize the database
        projectImagesRepository.saveAndFlush(projectImages);

        // Get the projectImages
        restProjectImagesMockMvc
            .perform(get(ENTITY_API_URL_ID, projectImages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectImages.getId().intValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getNonExistingProjectImages() throws Exception {
        // Get the projectImages
        restProjectImagesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProjectImages() throws Exception {
        // Initialize the database
        projectImagesRepository.saveAndFlush(projectImages);

        int databaseSizeBeforeUpdate = projectImagesRepository.findAll().size();

        // Update the projectImages
        ProjectImages updatedProjectImages = projectImagesRepository.findById(projectImages.getId()).get();
        // Disconnect from session so that the updates on updatedProjectImages are not directly saved in db
        em.detach(updatedProjectImages);
        updatedProjectImages.image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        ProjectImagesDTO projectImagesDTO = projectImagesMapper.toDto(updatedProjectImages);

        restProjectImagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectImagesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectImagesDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProjectImages in the database
        List<ProjectImages> projectImagesList = projectImagesRepository.findAll();
        assertThat(projectImagesList).hasSize(databaseSizeBeforeUpdate);
        ProjectImages testProjectImages = projectImagesList.get(projectImagesList.size() - 1);
        assertThat(testProjectImages.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProjectImages.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingProjectImages() throws Exception {
        int databaseSizeBeforeUpdate = projectImagesRepository.findAll().size();
        projectImages.setId(count.incrementAndGet());

        // Create the ProjectImages
        ProjectImagesDTO projectImagesDTO = projectImagesMapper.toDto(projectImages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectImagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectImagesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectImagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectImages in the database
        List<ProjectImages> projectImagesList = projectImagesRepository.findAll();
        assertThat(projectImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectImages() throws Exception {
        int databaseSizeBeforeUpdate = projectImagesRepository.findAll().size();
        projectImages.setId(count.incrementAndGet());

        // Create the ProjectImages
        ProjectImagesDTO projectImagesDTO = projectImagesMapper.toDto(projectImages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectImagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectImagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectImages in the database
        List<ProjectImages> projectImagesList = projectImagesRepository.findAll();
        assertThat(projectImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectImages() throws Exception {
        int databaseSizeBeforeUpdate = projectImagesRepository.findAll().size();
        projectImages.setId(count.incrementAndGet());

        // Create the ProjectImages
        ProjectImagesDTO projectImagesDTO = projectImagesMapper.toDto(projectImages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectImagesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectImagesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectImages in the database
        List<ProjectImages> projectImagesList = projectImagesRepository.findAll();
        assertThat(projectImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectImagesWithPatch() throws Exception {
        // Initialize the database
        projectImagesRepository.saveAndFlush(projectImages);

        int databaseSizeBeforeUpdate = projectImagesRepository.findAll().size();

        // Update the projectImages using partial update
        ProjectImages partialUpdatedProjectImages = new ProjectImages();
        partialUpdatedProjectImages.setId(projectImages.getId());

        partialUpdatedProjectImages.image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restProjectImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectImages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectImages))
            )
            .andExpect(status().isOk());

        // Validate the ProjectImages in the database
        List<ProjectImages> projectImagesList = projectImagesRepository.findAll();
        assertThat(projectImagesList).hasSize(databaseSizeBeforeUpdate);
        ProjectImages testProjectImages = projectImagesList.get(projectImagesList.size() - 1);
        assertThat(testProjectImages.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProjectImages.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateProjectImagesWithPatch() throws Exception {
        // Initialize the database
        projectImagesRepository.saveAndFlush(projectImages);

        int databaseSizeBeforeUpdate = projectImagesRepository.findAll().size();

        // Update the projectImages using partial update
        ProjectImages partialUpdatedProjectImages = new ProjectImages();
        partialUpdatedProjectImages.setId(projectImages.getId());

        partialUpdatedProjectImages.image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restProjectImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectImages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectImages))
            )
            .andExpect(status().isOk());

        // Validate the ProjectImages in the database
        List<ProjectImages> projectImagesList = projectImagesRepository.findAll();
        assertThat(projectImagesList).hasSize(databaseSizeBeforeUpdate);
        ProjectImages testProjectImages = projectImagesList.get(projectImagesList.size() - 1);
        assertThat(testProjectImages.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProjectImages.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingProjectImages() throws Exception {
        int databaseSizeBeforeUpdate = projectImagesRepository.findAll().size();
        projectImages.setId(count.incrementAndGet());

        // Create the ProjectImages
        ProjectImagesDTO projectImagesDTO = projectImagesMapper.toDto(projectImages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectImagesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectImagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectImages in the database
        List<ProjectImages> projectImagesList = projectImagesRepository.findAll();
        assertThat(projectImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectImages() throws Exception {
        int databaseSizeBeforeUpdate = projectImagesRepository.findAll().size();
        projectImages.setId(count.incrementAndGet());

        // Create the ProjectImages
        ProjectImagesDTO projectImagesDTO = projectImagesMapper.toDto(projectImages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectImagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectImages in the database
        List<ProjectImages> projectImagesList = projectImagesRepository.findAll();
        assertThat(projectImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectImages() throws Exception {
        int databaseSizeBeforeUpdate = projectImagesRepository.findAll().size();
        projectImages.setId(count.incrementAndGet());

        // Create the ProjectImages
        ProjectImagesDTO projectImagesDTO = projectImagesMapper.toDto(projectImages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectImagesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectImagesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectImages in the database
        List<ProjectImages> projectImagesList = projectImagesRepository.findAll();
        assertThat(projectImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectImages() throws Exception {
        // Initialize the database
        projectImagesRepository.saveAndFlush(projectImages);

        int databaseSizeBeforeDelete = projectImagesRepository.findAll().size();

        // Delete the projectImages
        restProjectImagesMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectImages.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectImages> projectImagesList = projectImagesRepository.findAll();
        assertThat(projectImagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
