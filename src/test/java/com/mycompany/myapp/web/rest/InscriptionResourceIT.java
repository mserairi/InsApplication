package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Inscription;
import com.mycompany.myapp.repository.InscriptionRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InscriptionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class InscriptionResourceIT {

    private static final Instant DEFAULT_DATEINSCRIPTION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATEINSCRIPTION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LASESSION = "AAAAAAAAAA";
    private static final String UPDATED_LASESSION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/inscriptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Mock
    private InscriptionRepository inscriptionRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInscriptionMockMvc;

    private Inscription inscription;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inscription createEntity(EntityManager em) {
        Inscription inscription = new Inscription().dateinscription(DEFAULT_DATEINSCRIPTION).lasession(DEFAULT_LASESSION);
        return inscription;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inscription createUpdatedEntity(EntityManager em) {
        Inscription inscription = new Inscription().dateinscription(UPDATED_DATEINSCRIPTION).lasession(UPDATED_LASESSION);
        return inscription;
    }

    @BeforeEach
    public void initTest() {
        inscription = createEntity(em);
    }

    @Test
    @Transactional
    void createInscription() throws Exception {
        int databaseSizeBeforeCreate = inscriptionRepository.findAll().size();
        // Create the Inscription
        restInscriptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inscription)))
            .andExpect(status().isCreated());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeCreate + 1);
        Inscription testInscription = inscriptionList.get(inscriptionList.size() - 1);
        assertThat(testInscription.getDateinscription()).isEqualTo(DEFAULT_DATEINSCRIPTION);
        assertThat(testInscription.getLasession()).isEqualTo(DEFAULT_LASESSION);
    }

    @Test
    @Transactional
    void createInscriptionWithExistingId() throws Exception {
        // Create the Inscription with an existing ID
        inscription.setId(1L);

        int databaseSizeBeforeCreate = inscriptionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInscriptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inscription)))
            .andExpect(status().isBadRequest());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLasessionIsRequired() throws Exception {
        int databaseSizeBeforeTest = inscriptionRepository.findAll().size();
        // set the field null
        inscription.setLasession(null);

        // Create the Inscription, which fails.

        restInscriptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inscription)))
            .andExpect(status().isBadRequest());

        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInscriptions() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList
        restInscriptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateinscription").value(hasItem(DEFAULT_DATEINSCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].lasession").value(hasItem(DEFAULT_LASESSION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInscriptionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(inscriptionRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInscriptionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(inscriptionRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInscriptionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(inscriptionRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInscriptionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(inscriptionRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getInscription() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get the inscription
        restInscriptionMockMvc
            .perform(get(ENTITY_API_URL_ID, inscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inscription.getId().intValue()))
            .andExpect(jsonPath("$.dateinscription").value(DEFAULT_DATEINSCRIPTION.toString()))
            .andExpect(jsonPath("$.lasession").value(DEFAULT_LASESSION));
    }

    @Test
    @Transactional
    void getNonExistingInscription() throws Exception {
        // Get the inscription
        restInscriptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInscription() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        int databaseSizeBeforeUpdate = inscriptionRepository.findAll().size();

        // Update the inscription
        Inscription updatedInscription = inscriptionRepository.findById(inscription.getId()).get();
        // Disconnect from session so that the updates on updatedInscription are not directly saved in db
        em.detach(updatedInscription);
        updatedInscription.dateinscription(UPDATED_DATEINSCRIPTION).lasession(UPDATED_LASESSION);

        restInscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInscription.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInscription))
            )
            .andExpect(status().isOk());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeUpdate);
        Inscription testInscription = inscriptionList.get(inscriptionList.size() - 1);
        assertThat(testInscription.getDateinscription()).isEqualTo(UPDATED_DATEINSCRIPTION);
        assertThat(testInscription.getLasession()).isEqualTo(UPDATED_LASESSION);
    }

    @Test
    @Transactional
    void putNonExistingInscription() throws Exception {
        int databaseSizeBeforeUpdate = inscriptionRepository.findAll().size();
        inscription.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inscription.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inscription))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInscription() throws Exception {
        int databaseSizeBeforeUpdate = inscriptionRepository.findAll().size();
        inscription.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inscription))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInscription() throws Exception {
        int databaseSizeBeforeUpdate = inscriptionRepository.findAll().size();
        inscription.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscriptionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inscription)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInscriptionWithPatch() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        int databaseSizeBeforeUpdate = inscriptionRepository.findAll().size();

        // Update the inscription using partial update
        Inscription partialUpdatedInscription = new Inscription();
        partialUpdatedInscription.setId(inscription.getId());

        partialUpdatedInscription.dateinscription(UPDATED_DATEINSCRIPTION);

        restInscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInscription.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInscription))
            )
            .andExpect(status().isOk());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeUpdate);
        Inscription testInscription = inscriptionList.get(inscriptionList.size() - 1);
        assertThat(testInscription.getDateinscription()).isEqualTo(UPDATED_DATEINSCRIPTION);
        assertThat(testInscription.getLasession()).isEqualTo(DEFAULT_LASESSION);
    }

    @Test
    @Transactional
    void fullUpdateInscriptionWithPatch() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        int databaseSizeBeforeUpdate = inscriptionRepository.findAll().size();

        // Update the inscription using partial update
        Inscription partialUpdatedInscription = new Inscription();
        partialUpdatedInscription.setId(inscription.getId());

        partialUpdatedInscription.dateinscription(UPDATED_DATEINSCRIPTION).lasession(UPDATED_LASESSION);

        restInscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInscription.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInscription))
            )
            .andExpect(status().isOk());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeUpdate);
        Inscription testInscription = inscriptionList.get(inscriptionList.size() - 1);
        assertThat(testInscription.getDateinscription()).isEqualTo(UPDATED_DATEINSCRIPTION);
        assertThat(testInscription.getLasession()).isEqualTo(UPDATED_LASESSION);
    }

    @Test
    @Transactional
    void patchNonExistingInscription() throws Exception {
        int databaseSizeBeforeUpdate = inscriptionRepository.findAll().size();
        inscription.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inscription.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inscription))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInscription() throws Exception {
        int databaseSizeBeforeUpdate = inscriptionRepository.findAll().size();
        inscription.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inscription))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInscription() throws Exception {
        int databaseSizeBeforeUpdate = inscriptionRepository.findAll().size();
        inscription.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(inscription))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInscription() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        int databaseSizeBeforeDelete = inscriptionRepository.findAll().size();

        // Delete the inscription
        restInscriptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, inscription.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}