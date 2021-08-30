package fr.crowdfunding.jhipster.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.crowdfunding.jhipster.IntegrationTest;
import fr.crowdfunding.jhipster.domain.ProjectComment;
import fr.crowdfunding.jhipster.repository.ProjectCommentRepository;
import fr.crowdfunding.jhipster.service.dto.ProjectCommentDTO;
import fr.crowdfunding.jhipster.service.mapper.ProjectCommentMapper;
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
 * Integration tests for the {@link ProjectCommentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectCommentResourceIT {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/project-comments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectCommentRepository projectCommentRepository;

    @Autowired
    private ProjectCommentMapper projectCommentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectCommentMockMvc;

    private ProjectComment projectComment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectComment createEntity(EntityManager em) {
        ProjectComment projectComment = new ProjectComment().comment(DEFAULT_COMMENT);
        return projectComment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectComment createUpdatedEntity(EntityManager em) {
        ProjectComment projectComment = new ProjectComment().comment(UPDATED_COMMENT);
        return projectComment;
    }

    @BeforeEach
    public void initTest() {
        projectComment = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectComment() throws Exception {
        int databaseSizeBeforeCreate = projectCommentRepository.findAll().size();
        // Create the ProjectComment
        ProjectCommentDTO projectCommentDTO = projectCommentMapper.toDto(projectComment);
        restProjectCommentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectCommentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectComment in the database
        List<ProjectComment> projectCommentList = projectCommentRepository.findAll();
        assertThat(projectCommentList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectComment testProjectComment = projectCommentList.get(projectCommentList.size() - 1);
        assertThat(testProjectComment.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    void createProjectCommentWithExistingId() throws Exception {
        // Create the ProjectComment with an existing ID
        projectComment.setId(1L);
        ProjectCommentDTO projectCommentDTO = projectCommentMapper.toDto(projectComment);

        int databaseSizeBeforeCreate = projectCommentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectCommentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectComment in the database
        List<ProjectComment> projectCommentList = projectCommentRepository.findAll();
        assertThat(projectCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectCommentRepository.findAll().size();
        // set the field null
        projectComment.setComment(null);

        // Create the ProjectComment, which fails.
        ProjectCommentDTO projectCommentDTO = projectCommentMapper.toDto(projectComment);

        restProjectCommentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectCommentDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProjectComment> projectCommentList = projectCommentRepository.findAll();
        assertThat(projectCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProjectComments() throws Exception {
        // Initialize the database
        projectCommentRepository.saveAndFlush(projectComment);

        // Get all the projectCommentList
        restProjectCommentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));
    }

    @Test
    @Transactional
    void getProjectComment() throws Exception {
        // Initialize the database
        projectCommentRepository.saveAndFlush(projectComment);

        // Get the projectComment
        restProjectCommentMockMvc
            .perform(get(ENTITY_API_URL_ID, projectComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectComment.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT));
    }

    @Test
    @Transactional
    void getNonExistingProjectComment() throws Exception {
        // Get the projectComment
        restProjectCommentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProjectComment() throws Exception {
        // Initialize the database
        projectCommentRepository.saveAndFlush(projectComment);

        int databaseSizeBeforeUpdate = projectCommentRepository.findAll().size();

        // Update the projectComment
        ProjectComment updatedProjectComment = projectCommentRepository.findById(projectComment.getId()).get();
        // Disconnect from session so that the updates on updatedProjectComment are not directly saved in db
        em.detach(updatedProjectComment);
        updatedProjectComment.comment(UPDATED_COMMENT);
        ProjectCommentDTO projectCommentDTO = projectCommentMapper.toDto(updatedProjectComment);

        restProjectCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectCommentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectCommentDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProjectComment in the database
        List<ProjectComment> projectCommentList = projectCommentRepository.findAll();
        assertThat(projectCommentList).hasSize(databaseSizeBeforeUpdate);
        ProjectComment testProjectComment = projectCommentList.get(projectCommentList.size() - 1);
        assertThat(testProjectComment.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void putNonExistingProjectComment() throws Exception {
        int databaseSizeBeforeUpdate = projectCommentRepository.findAll().size();
        projectComment.setId(count.incrementAndGet());

        // Create the ProjectComment
        ProjectCommentDTO projectCommentDTO = projectCommentMapper.toDto(projectComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectCommentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectComment in the database
        List<ProjectComment> projectCommentList = projectCommentRepository.findAll();
        assertThat(projectCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectComment() throws Exception {
        int databaseSizeBeforeUpdate = projectCommentRepository.findAll().size();
        projectComment.setId(count.incrementAndGet());

        // Create the ProjectComment
        ProjectCommentDTO projectCommentDTO = projectCommentMapper.toDto(projectComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectComment in the database
        List<ProjectComment> projectCommentList = projectCommentRepository.findAll();
        assertThat(projectCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectComment() throws Exception {
        int databaseSizeBeforeUpdate = projectCommentRepository.findAll().size();
        projectComment.setId(count.incrementAndGet());

        // Create the ProjectComment
        ProjectCommentDTO projectCommentDTO = projectCommentMapper.toDto(projectComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectCommentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectCommentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectComment in the database
        List<ProjectComment> projectCommentList = projectCommentRepository.findAll();
        assertThat(projectCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectCommentWithPatch() throws Exception {
        // Initialize the database
        projectCommentRepository.saveAndFlush(projectComment);

        int databaseSizeBeforeUpdate = projectCommentRepository.findAll().size();

        // Update the projectComment using partial update
        ProjectComment partialUpdatedProjectComment = new ProjectComment();
        partialUpdatedProjectComment.setId(projectComment.getId());

        restProjectCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectComment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectComment))
            )
            .andExpect(status().isOk());

        // Validate the ProjectComment in the database
        List<ProjectComment> projectCommentList = projectCommentRepository.findAll();
        assertThat(projectCommentList).hasSize(databaseSizeBeforeUpdate);
        ProjectComment testProjectComment = projectCommentList.get(projectCommentList.size() - 1);
        assertThat(testProjectComment.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    void fullUpdateProjectCommentWithPatch() throws Exception {
        // Initialize the database
        projectCommentRepository.saveAndFlush(projectComment);

        int databaseSizeBeforeUpdate = projectCommentRepository.findAll().size();

        // Update the projectComment using partial update
        ProjectComment partialUpdatedProjectComment = new ProjectComment();
        partialUpdatedProjectComment.setId(projectComment.getId());

        partialUpdatedProjectComment.comment(UPDATED_COMMENT);

        restProjectCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectComment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectComment))
            )
            .andExpect(status().isOk());

        // Validate the ProjectComment in the database
        List<ProjectComment> projectCommentList = projectCommentRepository.findAll();
        assertThat(projectCommentList).hasSize(databaseSizeBeforeUpdate);
        ProjectComment testProjectComment = projectCommentList.get(projectCommentList.size() - 1);
        assertThat(testProjectComment.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void patchNonExistingProjectComment() throws Exception {
        int databaseSizeBeforeUpdate = projectCommentRepository.findAll().size();
        projectComment.setId(count.incrementAndGet());

        // Create the ProjectComment
        ProjectCommentDTO projectCommentDTO = projectCommentMapper.toDto(projectComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectCommentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectComment in the database
        List<ProjectComment> projectCommentList = projectCommentRepository.findAll();
        assertThat(projectCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectComment() throws Exception {
        int databaseSizeBeforeUpdate = projectCommentRepository.findAll().size();
        projectComment.setId(count.incrementAndGet());

        // Create the ProjectComment
        ProjectCommentDTO projectCommentDTO = projectCommentMapper.toDto(projectComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectComment in the database
        List<ProjectComment> projectCommentList = projectCommentRepository.findAll();
        assertThat(projectCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectComment() throws Exception {
        int databaseSizeBeforeUpdate = projectCommentRepository.findAll().size();
        projectComment.setId(count.incrementAndGet());

        // Create the ProjectComment
        ProjectCommentDTO projectCommentDTO = projectCommentMapper.toDto(projectComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectCommentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectCommentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectComment in the database
        List<ProjectComment> projectCommentList = projectCommentRepository.findAll();
        assertThat(projectCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectComment() throws Exception {
        // Initialize the database
        projectCommentRepository.saveAndFlush(projectComment);

        int databaseSizeBeforeDelete = projectCommentRepository.findAll().size();

        // Delete the projectComment
        restProjectCommentMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectComment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectComment> projectCommentList = projectCommentRepository.findAll();
        assertThat(projectCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
