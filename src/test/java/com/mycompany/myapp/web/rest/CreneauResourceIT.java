package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Creneau;
import com.mycompany.myapp.domain.enumeration.JourSemaine;
import com.mycompany.myapp.domain.enumeration.TYPECRENEAU;
import com.mycompany.myapp.repository.CreneauRepository;
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
 * Integration tests for the {@link CreneauResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CreneauResourceIT {

    private static final TYPECRENEAU DEFAULT_TYPE_CRENEAU = TYPECRENEAU.PONCTUEL;
    private static final TYPECRENEAU UPDATED_TYPE_CRENEAU = TYPECRENEAU.REPETETIF;

    private static final JourSemaine DEFAULT_JOUR = JourSemaine.LUNDI;
    private static final JourSemaine UPDATED_JOUR = JourSemaine.MARDI;

    private static final Instant DEFAULT_DEB = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEB = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/creneaus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CreneauRepository creneauRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCreneauMockMvc;

    private Creneau creneau;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Creneau createEntity(EntityManager em) {
        Creneau creneau = new Creneau().typeCreneau(DEFAULT_TYPE_CRENEAU).jour(DEFAULT_JOUR).deb(DEFAULT_DEB).fin(DEFAULT_FIN);
        return creneau;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Creneau createUpdatedEntity(EntityManager em) {
        Creneau creneau = new Creneau().typeCreneau(UPDATED_TYPE_CRENEAU).jour(UPDATED_JOUR).deb(UPDATED_DEB).fin(UPDATED_FIN);
        return creneau;
    }

    @BeforeEach
    public void initTest() {
        creneau = createEntity(em);
    }

    @Test
    @Transactional
    void createCreneau() throws Exception {
        int databaseSizeBeforeCreate = creneauRepository.findAll().size();
        // Create the Creneau
        restCreneauMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creneau)))
            .andExpect(status().isCreated());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeCreate + 1);
        Creneau testCreneau = creneauList.get(creneauList.size() - 1);
        assertThat(testCreneau.getTypeCreneau()).isEqualTo(DEFAULT_TYPE_CRENEAU);
        assertThat(testCreneau.getJour()).isEqualTo(DEFAULT_JOUR);
        assertThat(testCreneau.getDeb()).isEqualTo(DEFAULT_DEB);
        assertThat(testCreneau.getFin()).isEqualTo(DEFAULT_FIN);
    }

    @Test
    @Transactional
    void createCreneauWithExistingId() throws Exception {
        // Create the Creneau with an existing ID
        creneau.setId(1L);

        int databaseSizeBeforeCreate = creneauRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreneauMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creneau)))
            .andExpect(status().isBadRequest());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeCreneauIsRequired() throws Exception {
        int databaseSizeBeforeTest = creneauRepository.findAll().size();
        // set the field null
        creneau.setTypeCreneau(null);

        // Create the Creneau, which fails.

        restCreneauMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creneau)))
            .andExpect(status().isBadRequest());

        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCreneaus() throws Exception {
        // Initialize the database
        creneauRepository.saveAndFlush(creneau);

        // Get all the creneauList
        restCreneauMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creneau.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeCreneau").value(hasItem(DEFAULT_TYPE_CRENEAU.toString())))
            .andExpect(jsonPath("$.[*].jour").value(hasItem(DEFAULT_JOUR.toString())))
            .andExpect(jsonPath("$.[*].deb").value(hasItem(DEFAULT_DEB.toString())))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN.toString())));
    }

    @Test
    @Transactional
    void getCreneau() throws Exception {
        // Initialize the database
        creneauRepository.saveAndFlush(creneau);

        // Get the creneau
        restCreneauMockMvc
            .perform(get(ENTITY_API_URL_ID, creneau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(creneau.getId().intValue()))
            .andExpect(jsonPath("$.typeCreneau").value(DEFAULT_TYPE_CRENEAU.toString()))
            .andExpect(jsonPath("$.jour").value(DEFAULT_JOUR.toString()))
            .andExpect(jsonPath("$.deb").value(DEFAULT_DEB.toString()))
            .andExpect(jsonPath("$.fin").value(DEFAULT_FIN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCreneau() throws Exception {
        // Get the creneau
        restCreneauMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCreneau() throws Exception {
        // Initialize the database
        creneauRepository.saveAndFlush(creneau);

        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();

        // Update the creneau
        Creneau updatedCreneau = creneauRepository.findById(creneau.getId()).get();
        // Disconnect from session so that the updates on updatedCreneau are not directly saved in db
        em.detach(updatedCreneau);
        updatedCreneau.typeCreneau(UPDATED_TYPE_CRENEAU).jour(UPDATED_JOUR).deb(UPDATED_DEB).fin(UPDATED_FIN);

        restCreneauMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCreneau.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCreneau))
            )
            .andExpect(status().isOk());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
        Creneau testCreneau = creneauList.get(creneauList.size() - 1);
        assertThat(testCreneau.getTypeCreneau()).isEqualTo(UPDATED_TYPE_CRENEAU);
        assertThat(testCreneau.getJour()).isEqualTo(UPDATED_JOUR);
        assertThat(testCreneau.getDeb()).isEqualTo(UPDATED_DEB);
        assertThat(testCreneau.getFin()).isEqualTo(UPDATED_FIN);
    }

    @Test
    @Transactional
    void putNonExistingCreneau() throws Exception {
        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();
        creneau.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreneauMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creneau.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creneau))
            )
            .andExpect(status().isBadRequest());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCreneau() throws Exception {
        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();
        creneau.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreneauMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creneau))
            )
            .andExpect(status().isBadRequest());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCreneau() throws Exception {
        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();
        creneau.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreneauMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creneau)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCreneauWithPatch() throws Exception {
        // Initialize the database
        creneauRepository.saveAndFlush(creneau);

        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();

        // Update the creneau using partial update
        Creneau partialUpdatedCreneau = new Creneau();
        partialUpdatedCreneau.setId(creneau.getId());

        partialUpdatedCreneau.typeCreneau(UPDATED_TYPE_CRENEAU).deb(UPDATED_DEB);

        restCreneauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreneau.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreneau))
            )
            .andExpect(status().isOk());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
        Creneau testCreneau = creneauList.get(creneauList.size() - 1);
        assertThat(testCreneau.getTypeCreneau()).isEqualTo(UPDATED_TYPE_CRENEAU);
        assertThat(testCreneau.getJour()).isEqualTo(DEFAULT_JOUR);
        assertThat(testCreneau.getDeb()).isEqualTo(UPDATED_DEB);
        assertThat(testCreneau.getFin()).isEqualTo(DEFAULT_FIN);
    }

    @Test
    @Transactional
    void fullUpdateCreneauWithPatch() throws Exception {
        // Initialize the database
        creneauRepository.saveAndFlush(creneau);

        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();

        // Update the creneau using partial update
        Creneau partialUpdatedCreneau = new Creneau();
        partialUpdatedCreneau.setId(creneau.getId());

        partialUpdatedCreneau.typeCreneau(UPDATED_TYPE_CRENEAU).jour(UPDATED_JOUR).deb(UPDATED_DEB).fin(UPDATED_FIN);

        restCreneauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreneau.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreneau))
            )
            .andExpect(status().isOk());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
        Creneau testCreneau = creneauList.get(creneauList.size() - 1);
        assertThat(testCreneau.getTypeCreneau()).isEqualTo(UPDATED_TYPE_CRENEAU);
        assertThat(testCreneau.getJour()).isEqualTo(UPDATED_JOUR);
        assertThat(testCreneau.getDeb()).isEqualTo(UPDATED_DEB);
        assertThat(testCreneau.getFin()).isEqualTo(UPDATED_FIN);
    }

    @Test
    @Transactional
    void patchNonExistingCreneau() throws Exception {
        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();
        creneau.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreneauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, creneau.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creneau))
            )
            .andExpect(status().isBadRequest());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCreneau() throws Exception {
        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();
        creneau.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreneauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creneau))
            )
            .andExpect(status().isBadRequest());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCreneau() throws Exception {
        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();
        creneau.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreneauMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(creneau)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCreneau() throws Exception {
        // Initialize the database
        creneauRepository.saveAndFlush(creneau);

        int databaseSizeBeforeDelete = creneauRepository.findAll().size();

        // Delete the creneau
        restCreneauMockMvc
            .perform(delete(ENTITY_API_URL_ID, creneau.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
