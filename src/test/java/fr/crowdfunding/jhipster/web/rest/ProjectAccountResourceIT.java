package fr.crowdfunding.jhipster.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.crowdfunding.jhipster.IntegrationTest;
import fr.crowdfunding.jhipster.domain.ProjectAccount;
import fr.crowdfunding.jhipster.repository.ProjectAccountRepository;
import fr.crowdfunding.jhipster.service.dto.ProjectAccountDTO;
import fr.crowdfunding.jhipster.service.mapper.ProjectAccountMapper;
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
 * Integration tests for the {@link ProjectAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectAccountResourceIT {

    private static final String DEFAULT_BALANCE = "AAAAAAAAAA";
    private static final String UPDATED_BALANCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final String DEFAULT_BANK = "AAAAAAAAAA";
    private static final String UPDATED_BANK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/project-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectAccountRepository projectAccountRepository;

    @Autowired
    private ProjectAccountMapper projectAccountMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectAccountMockMvc;

    private ProjectAccount projectAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectAccount createEntity(EntityManager em) {
        ProjectAccount projectAccount = new ProjectAccount().balance(DEFAULT_BALANCE).number(DEFAULT_NUMBER).bank(DEFAULT_BANK);
        return projectAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectAccount createUpdatedEntity(EntityManager em) {
        ProjectAccount projectAccount = new ProjectAccount().balance(UPDATED_BALANCE).number(UPDATED_NUMBER).bank(UPDATED_BANK);
        return projectAccount;
    }

    @BeforeEach
    public void initTest() {
        projectAccount = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectAccount() throws Exception {
        int databaseSizeBeforeCreate = projectAccountRepository.findAll().size();
        // Create the ProjectAccount
        ProjectAccountDTO projectAccountDTO = projectAccountMapper.toDto(projectAccount);
        restProjectAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectAccountDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectAccount in the database
        List<ProjectAccount> projectAccountList = projectAccountRepository.findAll();
        assertThat(projectAccountList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectAccount testProjectAccount = projectAccountList.get(projectAccountList.size() - 1);
        assertThat(testProjectAccount.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testProjectAccount.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testProjectAccount.getBank()).isEqualTo(DEFAULT_BANK);
    }

    @Test
    @Transactional
    void createProjectAccountWithExistingId() throws Exception {
        // Create the ProjectAccount with an existing ID
        projectAccount.setId(1L);
        ProjectAccountDTO projectAccountDTO = projectAccountMapper.toDto(projectAccount);

        int databaseSizeBeforeCreate = projectAccountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectAccount in the database
        List<ProjectAccount> projectAccountList = projectAccountRepository.findAll();
        assertThat(projectAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectAccounts() throws Exception {
        // Initialize the database
        projectAccountRepository.saveAndFlush(projectAccount);

        // Get all the projectAccountList
        restProjectAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].bank").value(hasItem(DEFAULT_BANK)));
    }

    @Test
    @Transactional
    void getProjectAccount() throws Exception {
        // Initialize the database
        projectAccountRepository.saveAndFlush(projectAccount);

        // Get the projectAccount
        restProjectAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, projectAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectAccount.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.bank").value(DEFAULT_BANK));
    }

    @Test
    @Transactional
    void getNonExistingProjectAccount() throws Exception {
        // Get the projectAccount
        restProjectAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProjectAccount() throws Exception {
        // Initialize the database
        projectAccountRepository.saveAndFlush(projectAccount);

        int databaseSizeBeforeUpdate = projectAccountRepository.findAll().size();

        // Update the projectAccount
        ProjectAccount updatedProjectAccount = projectAccountRepository.findById(projectAccount.getId()).get();
        // Disconnect from session so that the updates on updatedProjectAccount are not directly saved in db
        em.detach(updatedProjectAccount);
        updatedProjectAccount.balance(UPDATED_BALANCE).number(UPDATED_NUMBER).bank(UPDATED_BANK);
        ProjectAccountDTO projectAccountDTO = projectAccountMapper.toDto(updatedProjectAccount);

        restProjectAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectAccountDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProjectAccount in the database
        List<ProjectAccount> projectAccountList = projectAccountRepository.findAll();
        assertThat(projectAccountList).hasSize(databaseSizeBeforeUpdate);
        ProjectAccount testProjectAccount = projectAccountList.get(projectAccountList.size() - 1);
        assertThat(testProjectAccount.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testProjectAccount.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testProjectAccount.getBank()).isEqualTo(UPDATED_BANK);
    }

    @Test
    @Transactional
    void putNonExistingProjectAccount() throws Exception {
        int databaseSizeBeforeUpdate = projectAccountRepository.findAll().size();
        projectAccount.setId(count.incrementAndGet());

        // Create the ProjectAccount
        ProjectAccountDTO projectAccountDTO = projectAccountMapper.toDto(projectAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectAccount in the database
        List<ProjectAccount> projectAccountList = projectAccountRepository.findAll();
        assertThat(projectAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectAccount() throws Exception {
        int databaseSizeBeforeUpdate = projectAccountRepository.findAll().size();
        projectAccount.setId(count.incrementAndGet());

        // Create the ProjectAccount
        ProjectAccountDTO projectAccountDTO = projectAccountMapper.toDto(projectAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectAccount in the database
        List<ProjectAccount> projectAccountList = projectAccountRepository.findAll();
        assertThat(projectAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectAccount() throws Exception {
        int databaseSizeBeforeUpdate = projectAccountRepository.findAll().size();
        projectAccount.setId(count.incrementAndGet());

        // Create the ProjectAccount
        ProjectAccountDTO projectAccountDTO = projectAccountMapper.toDto(projectAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectAccountMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectAccount in the database
        List<ProjectAccount> projectAccountList = projectAccountRepository.findAll();
        assertThat(projectAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectAccountWithPatch() throws Exception {
        // Initialize the database
        projectAccountRepository.saveAndFlush(projectAccount);

        int databaseSizeBeforeUpdate = projectAccountRepository.findAll().size();

        // Update the projectAccount using partial update
        ProjectAccount partialUpdatedProjectAccount = new ProjectAccount();
        partialUpdatedProjectAccount.setId(projectAccount.getId());

        partialUpdatedProjectAccount.balance(UPDATED_BALANCE);

        restProjectAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectAccount))
            )
            .andExpect(status().isOk());

        // Validate the ProjectAccount in the database
        List<ProjectAccount> projectAccountList = projectAccountRepository.findAll();
        assertThat(projectAccountList).hasSize(databaseSizeBeforeUpdate);
        ProjectAccount testProjectAccount = projectAccountList.get(projectAccountList.size() - 1);
        assertThat(testProjectAccount.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testProjectAccount.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testProjectAccount.getBank()).isEqualTo(DEFAULT_BANK);
    }

    @Test
    @Transactional
    void fullUpdateProjectAccountWithPatch() throws Exception {
        // Initialize the database
        projectAccountRepository.saveAndFlush(projectAccount);

        int databaseSizeBeforeUpdate = projectAccountRepository.findAll().size();

        // Update the projectAccount using partial update
        ProjectAccount partialUpdatedProjectAccount = new ProjectAccount();
        partialUpdatedProjectAccount.setId(projectAccount.getId());

        partialUpdatedProjectAccount.balance(UPDATED_BALANCE).number(UPDATED_NUMBER).bank(UPDATED_BANK);

        restProjectAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectAccount))
            )
            .andExpect(status().isOk());

        // Validate the ProjectAccount in the database
        List<ProjectAccount> projectAccountList = projectAccountRepository.findAll();
        assertThat(projectAccountList).hasSize(databaseSizeBeforeUpdate);
        ProjectAccount testProjectAccount = projectAccountList.get(projectAccountList.size() - 1);
        assertThat(testProjectAccount.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testProjectAccount.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testProjectAccount.getBank()).isEqualTo(UPDATED_BANK);
    }

    @Test
    @Transactional
    void patchNonExistingProjectAccount() throws Exception {
        int databaseSizeBeforeUpdate = projectAccountRepository.findAll().size();
        projectAccount.setId(count.incrementAndGet());

        // Create the ProjectAccount
        ProjectAccountDTO projectAccountDTO = projectAccountMapper.toDto(projectAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectAccountDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectAccount in the database
        List<ProjectAccount> projectAccountList = projectAccountRepository.findAll();
        assertThat(projectAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectAccount() throws Exception {
        int databaseSizeBeforeUpdate = projectAccountRepository.findAll().size();
        projectAccount.setId(count.incrementAndGet());

        // Create the ProjectAccount
        ProjectAccountDTO projectAccountDTO = projectAccountMapper.toDto(projectAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectAccount in the database
        List<ProjectAccount> projectAccountList = projectAccountRepository.findAll();
        assertThat(projectAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectAccount() throws Exception {
        int databaseSizeBeforeUpdate = projectAccountRepository.findAll().size();
        projectAccount.setId(count.incrementAndGet());

        // Create the ProjectAccount
        ProjectAccountDTO projectAccountDTO = projectAccountMapper.toDto(projectAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectAccountMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectAccount in the database
        List<ProjectAccount> projectAccountList = projectAccountRepository.findAll();
        assertThat(projectAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectAccount() throws Exception {
        // Initialize the database
        projectAccountRepository.saveAndFlush(projectAccount);

        int databaseSizeBeforeDelete = projectAccountRepository.findAll().size();

        // Delete the projectAccount
        restProjectAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectAccount> projectAccountList = projectAccountRepository.findAll();
        assertThat(projectAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
