package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.UserExtras;
import com.mycompany.myapp.domain.enumeration.TypeGenre;
import com.mycompany.myapp.repository.UserExtrasRepository;
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
 * Integration tests for the {@link UserExtrasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserExtrasResourceIT {

    private static final String DEFAULT_MOB = "1285321817";
    private static final String UPDATED_MOB = "7825779308";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final TypeGenre DEFAULT_GENRE = TypeGenre.MASCULIN;
    private static final TypeGenre UPDATED_GENRE = TypeGenre.FEMININ;

    private static final String ENTITY_API_URL = "/api/user-extras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserExtrasRepository userExtrasRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserExtrasMockMvc;

    private UserExtras userExtras;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtras createEntity(EntityManager em) {
        UserExtras userExtras = new UserExtras().mob(DEFAULT_MOB).adresse(DEFAULT_ADRESSE).genre(DEFAULT_GENRE);
        return userExtras;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtras createUpdatedEntity(EntityManager em) {
        UserExtras userExtras = new UserExtras().mob(UPDATED_MOB).adresse(UPDATED_ADRESSE).genre(UPDATED_GENRE);
        return userExtras;
    }

    @BeforeEach
    public void initTest() {
        userExtras = createEntity(em);
    }

    @Test
    @Transactional
    void createUserExtras() throws Exception {
        int databaseSizeBeforeCreate = userExtrasRepository.findAll().size();
        // Create the UserExtras
        restUserExtrasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtras)))
            .andExpect(status().isCreated());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeCreate + 1);
        UserExtras testUserExtras = userExtrasList.get(userExtrasList.size() - 1);
        assertThat(testUserExtras.getMob()).isEqualTo(DEFAULT_MOB);
        assertThat(testUserExtras.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testUserExtras.getGenre()).isEqualTo(DEFAULT_GENRE);
    }

    @Test
    @Transactional
    void createUserExtrasWithExistingId() throws Exception {
        // Create the UserExtras with an existing ID
        userExtras.setId(1L);

        int databaseSizeBeforeCreate = userExtrasRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExtrasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtras)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserExtras() throws Exception {
        // Initialize the database
        userExtrasRepository.saveAndFlush(userExtras);

        // Get all the userExtrasList
        restUserExtrasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtras.getId().intValue())))
            .andExpect(jsonPath("$.[*].mob").value(hasItem(DEFAULT_MOB)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].genre").value(hasItem(DEFAULT_GENRE.toString())));
    }

    @Test
    @Transactional
    void getUserExtras() throws Exception {
        // Initialize the database
        userExtrasRepository.saveAndFlush(userExtras);

        // Get the userExtras
        restUserExtrasMockMvc
            .perform(get(ENTITY_API_URL_ID, userExtras.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userExtras.getId().intValue()))
            .andExpect(jsonPath("$.mob").value(DEFAULT_MOB))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.genre").value(DEFAULT_GENRE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserExtras() throws Exception {
        // Get the userExtras
        restUserExtrasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserExtras() throws Exception {
        // Initialize the database
        userExtrasRepository.saveAndFlush(userExtras);

        int databaseSizeBeforeUpdate = userExtrasRepository.findAll().size();

        // Update the userExtras
        UserExtras updatedUserExtras = userExtrasRepository.findById(userExtras.getId()).get();
        // Disconnect from session so that the updates on updatedUserExtras are not directly saved in db
        em.detach(updatedUserExtras);
        updatedUserExtras.mob(UPDATED_MOB).adresse(UPDATED_ADRESSE).genre(UPDATED_GENRE);

        restUserExtrasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserExtras.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserExtras))
            )
            .andExpect(status().isOk());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeUpdate);
        UserExtras testUserExtras = userExtrasList.get(userExtrasList.size() - 1);
        assertThat(testUserExtras.getMob()).isEqualTo(UPDATED_MOB);
        assertThat(testUserExtras.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testUserExtras.getGenre()).isEqualTo(UPDATED_GENRE);
    }

    @Test
    @Transactional
    void putNonExistingUserExtras() throws Exception {
        int databaseSizeBeforeUpdate = userExtrasRepository.findAll().size();
        userExtras.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtrasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userExtras.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userExtras))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserExtras() throws Exception {
        int databaseSizeBeforeUpdate = userExtrasRepository.findAll().size();
        userExtras.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtrasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userExtras))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserExtras() throws Exception {
        int databaseSizeBeforeUpdate = userExtrasRepository.findAll().size();
        userExtras.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtrasMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtras)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserExtrasWithPatch() throws Exception {
        // Initialize the database
        userExtrasRepository.saveAndFlush(userExtras);

        int databaseSizeBeforeUpdate = userExtrasRepository.findAll().size();

        // Update the userExtras using partial update
        UserExtras partialUpdatedUserExtras = new UserExtras();
        partialUpdatedUserExtras.setId(userExtras.getId());

        partialUpdatedUserExtras.adresse(UPDATED_ADRESSE).genre(UPDATED_GENRE);

        restUserExtrasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserExtras.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserExtras))
            )
            .andExpect(status().isOk());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeUpdate);
        UserExtras testUserExtras = userExtrasList.get(userExtrasList.size() - 1);
        assertThat(testUserExtras.getMob()).isEqualTo(DEFAULT_MOB);
        assertThat(testUserExtras.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testUserExtras.getGenre()).isEqualTo(UPDATED_GENRE);
    }

    @Test
    @Transactional
    void fullUpdateUserExtrasWithPatch() throws Exception {
        // Initialize the database
        userExtrasRepository.saveAndFlush(userExtras);

        int databaseSizeBeforeUpdate = userExtrasRepository.findAll().size();

        // Update the userExtras using partial update
        UserExtras partialUpdatedUserExtras = new UserExtras();
        partialUpdatedUserExtras.setId(userExtras.getId());

        partialUpdatedUserExtras.mob(UPDATED_MOB).adresse(UPDATED_ADRESSE).genre(UPDATED_GENRE);

        restUserExtrasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserExtras.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserExtras))
            )
            .andExpect(status().isOk());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeUpdate);
        UserExtras testUserExtras = userExtrasList.get(userExtrasList.size() - 1);
        assertThat(testUserExtras.getMob()).isEqualTo(UPDATED_MOB);
        assertThat(testUserExtras.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testUserExtras.getGenre()).isEqualTo(UPDATED_GENRE);
    }

    @Test
    @Transactional
    void patchNonExistingUserExtras() throws Exception {
        int databaseSizeBeforeUpdate = userExtrasRepository.findAll().size();
        userExtras.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtrasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userExtras.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userExtras))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserExtras() throws Exception {
        int databaseSizeBeforeUpdate = userExtrasRepository.findAll().size();
        userExtras.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtrasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userExtras))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserExtras() throws Exception {
        int databaseSizeBeforeUpdate = userExtrasRepository.findAll().size();
        userExtras.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtrasMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userExtras))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserExtras in the database
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserExtras() throws Exception {
        // Initialize the database
        userExtrasRepository.saveAndFlush(userExtras);

        int databaseSizeBeforeDelete = userExtrasRepository.findAll().size();

        // Delete the userExtras
        restUserExtrasMockMvc
            .perform(delete(ENTITY_API_URL_ID, userExtras.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserExtras> userExtrasList = userExtrasRepository.findAll();
        assertThat(userExtrasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
