package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.Formation;
import com.mycompany.myapp.repository.FormationRepository;
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
 * Integration tests for the {@link FormationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FormationResourceIT {

    private static final String DEFAULT_LIBILLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBILLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_OUVERTURE_INSCRIPTION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OUVERTURE_INSCRIPTION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FERMETURE_INSCRIPTION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FERMETURE_INSCRIPTION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SEUIL_INSCRITS = 1;
    private static final Integer UPDATED_SEUIL_INSCRITS = 2;

    private static final Float DEFAULT_TARIF = 1F;
    private static final Float UPDATED_TARIF = 2F;

    private static final String ENTITY_API_URL = "/api/formations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormationMockMvc;

    private Formation formation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formation createEntity(EntityManager em) {
        Formation formation = new Formation()
            .libille(DEFAULT_LIBILLE)
            .description(DEFAULT_DESCRIPTION)
            .ouvertureInscription(DEFAULT_OUVERTURE_INSCRIPTION)
            .fermetureInscription(DEFAULT_FERMETURE_INSCRIPTION)
            .seuilInscrits(DEFAULT_SEUIL_INSCRITS)
            .tarif(DEFAULT_TARIF);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        formation.setCategory(category);
        return formation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formation createUpdatedEntity(EntityManager em) {
        Formation formation = new Formation()
            .libille(UPDATED_LIBILLE)
            .description(UPDATED_DESCRIPTION)
            .ouvertureInscription(UPDATED_OUVERTURE_INSCRIPTION)
            .fermetureInscription(UPDATED_FERMETURE_INSCRIPTION)
            .seuilInscrits(UPDATED_SEUIL_INSCRITS)
            .tarif(UPDATED_TARIF);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createUpdatedEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        formation.setCategory(category);
        return formation;
    }

    @BeforeEach
    public void initTest() {
        formation = createEntity(em);
    }

    @Test
    @Transactional
    void createFormation() throws Exception {
        int databaseSizeBeforeCreate = formationRepository.findAll().size();
        // Create the Formation
        restFormationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formation)))
            .andExpect(status().isCreated());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeCreate + 1);
        Formation testFormation = formationList.get(formationList.size() - 1);
        assertThat(testFormation.getLibille()).isEqualTo(DEFAULT_LIBILLE);
        assertThat(testFormation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFormation.getOuvertureInscription()).isEqualTo(DEFAULT_OUVERTURE_INSCRIPTION);
        assertThat(testFormation.getFermetureInscription()).isEqualTo(DEFAULT_FERMETURE_INSCRIPTION);
        assertThat(testFormation.getSeuilInscrits()).isEqualTo(DEFAULT_SEUIL_INSCRITS);
        assertThat(testFormation.getTarif()).isEqualTo(DEFAULT_TARIF);
    }

    @Test
    @Transactional
    void createFormationWithExistingId() throws Exception {
        // Create the Formation with an existing ID
        formation.setId(1L);

        int databaseSizeBeforeCreate = formationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formation)))
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLibilleIsRequired() throws Exception {
        int databaseSizeBeforeTest = formationRepository.findAll().size();
        // set the field null
        formation.setLibille(null);

        // Create the Formation, which fails.

        restFormationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formation)))
            .andExpect(status().isBadRequest());

        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = formationRepository.findAll().size();
        // set the field null
        formation.setDescription(null);

        // Create the Formation, which fails.

        restFormationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formation)))
            .andExpect(status().isBadRequest());

        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFormations() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        // Get all the formationList
        restFormationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formation.getId().intValue())))
            .andExpect(jsonPath("$.[*].libille").value(hasItem(DEFAULT_LIBILLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].ouvertureInscription").value(hasItem(DEFAULT_OUVERTURE_INSCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].fermetureInscription").value(hasItem(DEFAULT_FERMETURE_INSCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].seuilInscrits").value(hasItem(DEFAULT_SEUIL_INSCRITS)))
            .andExpect(jsonPath("$.[*].tarif").value(hasItem(DEFAULT_TARIF.doubleValue())));
    }

    @Test
    @Transactional
    void getFormation() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        // Get the formation
        restFormationMockMvc
            .perform(get(ENTITY_API_URL_ID, formation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formation.getId().intValue()))
            .andExpect(jsonPath("$.libille").value(DEFAULT_LIBILLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.ouvertureInscription").value(DEFAULT_OUVERTURE_INSCRIPTION.toString()))
            .andExpect(jsonPath("$.fermetureInscription").value(DEFAULT_FERMETURE_INSCRIPTION.toString()))
            .andExpect(jsonPath("$.seuilInscrits").value(DEFAULT_SEUIL_INSCRITS))
            .andExpect(jsonPath("$.tarif").value(DEFAULT_TARIF.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingFormation() throws Exception {
        // Get the formation
        restFormationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFormation() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        int databaseSizeBeforeUpdate = formationRepository.findAll().size();

        // Update the formation
        Formation updatedFormation = formationRepository.findById(formation.getId()).get();
        // Disconnect from session so that the updates on updatedFormation are not directly saved in db
        em.detach(updatedFormation);
        updatedFormation
            .libille(UPDATED_LIBILLE)
            .description(UPDATED_DESCRIPTION)
            .ouvertureInscription(UPDATED_OUVERTURE_INSCRIPTION)
            .fermetureInscription(UPDATED_FERMETURE_INSCRIPTION)
            .seuilInscrits(UPDATED_SEUIL_INSCRITS)
            .tarif(UPDATED_TARIF);

        restFormationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFormation))
            )
            .andExpect(status().isOk());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
        Formation testFormation = formationList.get(formationList.size() - 1);
        assertThat(testFormation.getLibille()).isEqualTo(UPDATED_LIBILLE);
        assertThat(testFormation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFormation.getOuvertureInscription()).isEqualTo(UPDATED_OUVERTURE_INSCRIPTION);
        assertThat(testFormation.getFermetureInscription()).isEqualTo(UPDATED_FERMETURE_INSCRIPTION);
        assertThat(testFormation.getSeuilInscrits()).isEqualTo(UPDATED_SEUIL_INSCRITS);
        assertThat(testFormation.getTarif()).isEqualTo(UPDATED_TARIF);
    }

    @Test
    @Transactional
    void putNonExistingFormation() throws Exception {
        int databaseSizeBeforeUpdate = formationRepository.findAll().size();
        formation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormation() throws Exception {
        int databaseSizeBeforeUpdate = formationRepository.findAll().size();
        formation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormation() throws Exception {
        int databaseSizeBeforeUpdate = formationRepository.findAll().size();
        formation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormationWithPatch() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        int databaseSizeBeforeUpdate = formationRepository.findAll().size();

        // Update the formation using partial update
        Formation partialUpdatedFormation = new Formation();
        partialUpdatedFormation.setId(formation.getId());

        partialUpdatedFormation.fermetureInscription(UPDATED_FERMETURE_INSCRIPTION).tarif(UPDATED_TARIF);

        restFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormation))
            )
            .andExpect(status().isOk());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
        Formation testFormation = formationList.get(formationList.size() - 1);
        assertThat(testFormation.getLibille()).isEqualTo(DEFAULT_LIBILLE);
        assertThat(testFormation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFormation.getOuvertureInscription()).isEqualTo(DEFAULT_OUVERTURE_INSCRIPTION);
        assertThat(testFormation.getFermetureInscription()).isEqualTo(UPDATED_FERMETURE_INSCRIPTION);
        assertThat(testFormation.getSeuilInscrits()).isEqualTo(DEFAULT_SEUIL_INSCRITS);
        assertThat(testFormation.getTarif()).isEqualTo(UPDATED_TARIF);
    }

    @Test
    @Transactional
    void fullUpdateFormationWithPatch() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        int databaseSizeBeforeUpdate = formationRepository.findAll().size();

        // Update the formation using partial update
        Formation partialUpdatedFormation = new Formation();
        partialUpdatedFormation.setId(formation.getId());

        partialUpdatedFormation
            .libille(UPDATED_LIBILLE)
            .description(UPDATED_DESCRIPTION)
            .ouvertureInscription(UPDATED_OUVERTURE_INSCRIPTION)
            .fermetureInscription(UPDATED_FERMETURE_INSCRIPTION)
            .seuilInscrits(UPDATED_SEUIL_INSCRITS)
            .tarif(UPDATED_TARIF);

        restFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormation))
            )
            .andExpect(status().isOk());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
        Formation testFormation = formationList.get(formationList.size() - 1);
        assertThat(testFormation.getLibille()).isEqualTo(UPDATED_LIBILLE);
        assertThat(testFormation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFormation.getOuvertureInscription()).isEqualTo(UPDATED_OUVERTURE_INSCRIPTION);
        assertThat(testFormation.getFermetureInscription()).isEqualTo(UPDATED_FERMETURE_INSCRIPTION);
        assertThat(testFormation.getSeuilInscrits()).isEqualTo(UPDATED_SEUIL_INSCRITS);
        assertThat(testFormation.getTarif()).isEqualTo(UPDATED_TARIF);
    }

    @Test
    @Transactional
    void patchNonExistingFormation() throws Exception {
        int databaseSizeBeforeUpdate = formationRepository.findAll().size();
        formation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormation() throws Exception {
        int databaseSizeBeforeUpdate = formationRepository.findAll().size();
        formation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormation() throws Exception {
        int databaseSizeBeforeUpdate = formationRepository.findAll().size();
        formation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(formation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormation() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        int databaseSizeBeforeDelete = formationRepository.findAll().size();

        // Delete the formation
        restFormationMockMvc
            .perform(delete(ENTITY_API_URL_ID, formation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
