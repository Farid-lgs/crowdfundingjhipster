package fr.crowdfunding.jhipster.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.crowdfunding.jhipster.IntegrationTest;
import fr.crowdfunding.jhipster.domain.CommunityMembers;
import fr.crowdfunding.jhipster.repository.CommunityMembersRepository;
import fr.crowdfunding.jhipster.service.dto.CommunityMembersDTO;
import fr.crowdfunding.jhipster.service.mapper.CommunityMembersMapper;
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
 * Integration tests for the {@link CommunityMembersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommunityMembersResourceIT {

    private static final String ENTITY_API_URL = "/api/community-members";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CommunityMembersRepository communityMembersRepository;

    @Autowired
    private CommunityMembersMapper communityMembersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommunityMembersMockMvc;

    private CommunityMembers communityMembers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommunityMembers createEntity(EntityManager em) {
        CommunityMembers communityMembers = new CommunityMembers();
        return communityMembers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommunityMembers createUpdatedEntity(EntityManager em) {
        CommunityMembers communityMembers = new CommunityMembers();
        return communityMembers;
    }

    @BeforeEach
    public void initTest() {
        communityMembers = createEntity(em);
    }

    @Test
    @Transactional
    void createCommunityMembers() throws Exception {
        int databaseSizeBeforeCreate = communityMembersRepository.findAll().size();
        // Create the CommunityMembers
        CommunityMembersDTO communityMembersDTO = communityMembersMapper.toDto(communityMembers);
        restCommunityMembersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(communityMembersDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CommunityMembers in the database
        List<CommunityMembers> communityMembersList = communityMembersRepository.findAll();
        assertThat(communityMembersList).hasSize(databaseSizeBeforeCreate + 1);
        CommunityMembers testCommunityMembers = communityMembersList.get(communityMembersList.size() - 1);
    }

    @Test
    @Transactional
    void createCommunityMembersWithExistingId() throws Exception {
        // Create the CommunityMembers with an existing ID
        communityMembers.setId(1L);
        CommunityMembersDTO communityMembersDTO = communityMembersMapper.toDto(communityMembers);

        int databaseSizeBeforeCreate = communityMembersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommunityMembersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(communityMembersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommunityMembers in the database
        List<CommunityMembers> communityMembersList = communityMembersRepository.findAll();
        assertThat(communityMembersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCommunityMembers() throws Exception {
        // Initialize the database
        communityMembersRepository.saveAndFlush(communityMembers);

        // Get all the communityMembersList
        restCommunityMembersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(communityMembers.getId().intValue())));
    }

    @Test
    @Transactional
    void getCommunityMembers() throws Exception {
        // Initialize the database
        communityMembersRepository.saveAndFlush(communityMembers);

        // Get the communityMembers
        restCommunityMembersMockMvc
            .perform(get(ENTITY_API_URL_ID, communityMembers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(communityMembers.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCommunityMembers() throws Exception {
        // Get the communityMembers
        restCommunityMembersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCommunityMembers() throws Exception {
        // Initialize the database
        communityMembersRepository.saveAndFlush(communityMembers);

        int databaseSizeBeforeUpdate = communityMembersRepository.findAll().size();

        // Update the communityMembers
        CommunityMembers updatedCommunityMembers = communityMembersRepository.findById(communityMembers.getId()).get();
        // Disconnect from session so that the updates on updatedCommunityMembers are not directly saved in db
        em.detach(updatedCommunityMembers);
        CommunityMembersDTO communityMembersDTO = communityMembersMapper.toDto(updatedCommunityMembers);

        restCommunityMembersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, communityMembersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(communityMembersDTO))
            )
            .andExpect(status().isOk());

        // Validate the CommunityMembers in the database
        List<CommunityMembers> communityMembersList = communityMembersRepository.findAll();
        assertThat(communityMembersList).hasSize(databaseSizeBeforeUpdate);
        CommunityMembers testCommunityMembers = communityMembersList.get(communityMembersList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingCommunityMembers() throws Exception {
        int databaseSizeBeforeUpdate = communityMembersRepository.findAll().size();
        communityMembers.setId(count.incrementAndGet());

        // Create the CommunityMembers
        CommunityMembersDTO communityMembersDTO = communityMembersMapper.toDto(communityMembers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommunityMembersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, communityMembersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(communityMembersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommunityMembers in the database
        List<CommunityMembers> communityMembersList = communityMembersRepository.findAll();
        assertThat(communityMembersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommunityMembers() throws Exception {
        int databaseSizeBeforeUpdate = communityMembersRepository.findAll().size();
        communityMembers.setId(count.incrementAndGet());

        // Create the CommunityMembers
        CommunityMembersDTO communityMembersDTO = communityMembersMapper.toDto(communityMembers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunityMembersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(communityMembersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommunityMembers in the database
        List<CommunityMembers> communityMembersList = communityMembersRepository.findAll();
        assertThat(communityMembersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommunityMembers() throws Exception {
        int databaseSizeBeforeUpdate = communityMembersRepository.findAll().size();
        communityMembers.setId(count.incrementAndGet());

        // Create the CommunityMembers
        CommunityMembersDTO communityMembersDTO = communityMembersMapper.toDto(communityMembers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunityMembersMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(communityMembersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommunityMembers in the database
        List<CommunityMembers> communityMembersList = communityMembersRepository.findAll();
        assertThat(communityMembersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommunityMembersWithPatch() throws Exception {
        // Initialize the database
        communityMembersRepository.saveAndFlush(communityMembers);

        int databaseSizeBeforeUpdate = communityMembersRepository.findAll().size();

        // Update the communityMembers using partial update
        CommunityMembers partialUpdatedCommunityMembers = new CommunityMembers();
        partialUpdatedCommunityMembers.setId(communityMembers.getId());

        restCommunityMembersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommunityMembers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommunityMembers))
            )
            .andExpect(status().isOk());

        // Validate the CommunityMembers in the database
        List<CommunityMembers> communityMembersList = communityMembersRepository.findAll();
        assertThat(communityMembersList).hasSize(databaseSizeBeforeUpdate);
        CommunityMembers testCommunityMembers = communityMembersList.get(communityMembersList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateCommunityMembersWithPatch() throws Exception {
        // Initialize the database
        communityMembersRepository.saveAndFlush(communityMembers);

        int databaseSizeBeforeUpdate = communityMembersRepository.findAll().size();

        // Update the communityMembers using partial update
        CommunityMembers partialUpdatedCommunityMembers = new CommunityMembers();
        partialUpdatedCommunityMembers.setId(communityMembers.getId());

        restCommunityMembersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommunityMembers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommunityMembers))
            )
            .andExpect(status().isOk());

        // Validate the CommunityMembers in the database
        List<CommunityMembers> communityMembersList = communityMembersRepository.findAll();
        assertThat(communityMembersList).hasSize(databaseSizeBeforeUpdate);
        CommunityMembers testCommunityMembers = communityMembersList.get(communityMembersList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingCommunityMembers() throws Exception {
        int databaseSizeBeforeUpdate = communityMembersRepository.findAll().size();
        communityMembers.setId(count.incrementAndGet());

        // Create the CommunityMembers
        CommunityMembersDTO communityMembersDTO = communityMembersMapper.toDto(communityMembers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommunityMembersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, communityMembersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(communityMembersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommunityMembers in the database
        List<CommunityMembers> communityMembersList = communityMembersRepository.findAll();
        assertThat(communityMembersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommunityMembers() throws Exception {
        int databaseSizeBeforeUpdate = communityMembersRepository.findAll().size();
        communityMembers.setId(count.incrementAndGet());

        // Create the CommunityMembers
        CommunityMembersDTO communityMembersDTO = communityMembersMapper.toDto(communityMembers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunityMembersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(communityMembersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommunityMembers in the database
        List<CommunityMembers> communityMembersList = communityMembersRepository.findAll();
        assertThat(communityMembersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommunityMembers() throws Exception {
        int databaseSizeBeforeUpdate = communityMembersRepository.findAll().size();
        communityMembers.setId(count.incrementAndGet());

        // Create the CommunityMembers
        CommunityMembersDTO communityMembersDTO = communityMembersMapper.toDto(communityMembers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunityMembersMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(communityMembersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommunityMembers in the database
        List<CommunityMembers> communityMembersList = communityMembersRepository.findAll();
        assertThat(communityMembersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommunityMembers() throws Exception {
        // Initialize the database
        communityMembersRepository.saveAndFlush(communityMembers);

        int databaseSizeBeforeDelete = communityMembersRepository.findAll().size();

        // Delete the communityMembers
        restCommunityMembersMockMvc
            .perform(delete(ENTITY_API_URL_ID, communityMembers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommunityMembers> communityMembersList = communityMembersRepository.findAll();
        assertThat(communityMembersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
