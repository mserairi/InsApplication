package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Remise;
import com.mycompany.myapp.repository.RemiseRepository;
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
 * Integration tests for the {@link RemiseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RemiseResourceIT {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final Integer DEFAULT_MONTANT = 1;
    private static final Integer UPDATED_MONTANT = 2;

    private static final String DEFAULT_DESCREPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCREPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/remises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RemiseRepository remiseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRemiseMockMvc;

    private Remise remise;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Remise createEntity(EntityManager em) {
        Remise remise = new Remise().numero(DEFAULT_NUMERO).montant(DEFAULT_MONTANT).descreption(DEFAULT_DESCREPTION);
        return remise;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Remise createUpdatedEntity(EntityManager em) {
        Remise remise = new Remise().numero(UPDATED_NUMERO).montant(UPDATED_MONTANT).descreption(UPDATED_DESCREPTION);
        return remise;
    }

    @BeforeEach
    public void initTest() {
        remise = createEntity(em);
    }

    @Test
    @Transactional
    void createRemise() throws Exception {
        int databaseSizeBeforeCreate = remiseRepository.findAll().size();
        // Create the Remise
        restRemiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(remise)))
            .andExpect(status().isCreated());

        // Validate the Remise in the database
        List<Remise> remiseList = remiseRepository.findAll();
        assertThat(remiseList).hasSize(databaseSizeBeforeCreate + 1);
        Remise testRemise = remiseList.get(remiseList.size() - 1);
        assertThat(testRemise.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testRemise.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testRemise.getDescreption()).isEqualTo(DEFAULT_DESCREPTION);
    }

    @Test
    @Transactional
    void createRemiseWithExistingId() throws Exception {
        // Create the Remise with an existing ID
        remise.setId(1L);

        int databaseSizeBeforeCreate = remiseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRemiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(remise)))
            .andExpect(status().isBadRequest());

        // Validate the Remise in the database
        List<Remise> remiseList = remiseRepository.findAll();
        assertThat(remiseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRemises() throws Exception {
        // Initialize the database
        remiseRepository.saveAndFlush(remise);

        // Get all the remiseList
        restRemiseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remise.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT)))
            .andExpect(jsonPath("$.[*].descreption").value(hasItem(DEFAULT_DESCREPTION)));
    }

    @Test
    @Transactional
    void getRemise() throws Exception {
        // Initialize the database
        remiseRepository.saveAndFlush(remise);

        // Get the remise
        restRemiseMockMvc
            .perform(get(ENTITY_API_URL_ID, remise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(remise.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT))
            .andExpect(jsonPath("$.descreption").value(DEFAULT_DESCREPTION));
    }

    @Test
    @Transactional
    void getNonExistingRemise() throws Exception {
        // Get the remise
        restRemiseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRemise() throws Exception {
        // Initialize the database
        remiseRepository.saveAndFlush(remise);

        int databaseSizeBeforeUpdate = remiseRepository.findAll().size();

        // Update the remise
        Remise updatedRemise = remiseRepository.findById(remise.getId()).get();
        // Disconnect from session so that the updates on updatedRemise are not directly saved in db
        em.detach(updatedRemise);
        updatedRemise.numero(UPDATED_NUMERO).montant(UPDATED_MONTANT).descreption(UPDATED_DESCREPTION);

        restRemiseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRemise.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRemise))
            )
            .andExpect(status().isOk());

        // Validate the Remise in the database
        List<Remise> remiseList = remiseRepository.findAll();
        assertThat(remiseList).hasSize(databaseSizeBeforeUpdate);
        Remise testRemise = remiseList.get(remiseList.size() - 1);
        assertThat(testRemise.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testRemise.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testRemise.getDescreption()).isEqualTo(UPDATED_DESCREPTION);
    }

    @Test
    @Transactional
    void putNonExistingRemise() throws Exception {
        int databaseSizeBeforeUpdate = remiseRepository.findAll().size();
        remise.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemiseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, remise.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remise in the database
        List<Remise> remiseList = remiseRepository.findAll();
        assertThat(remiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRemise() throws Exception {
        int databaseSizeBeforeUpdate = remiseRepository.findAll().size();
        remise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemiseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remise in the database
        List<Remise> remiseList = remiseRepository.findAll();
        assertThat(remiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRemise() throws Exception {
        int databaseSizeBeforeUpdate = remiseRepository.findAll().size();
        remise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemiseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(remise)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Remise in the database
        List<Remise> remiseList = remiseRepository.findAll();
        assertThat(remiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRemiseWithPatch() throws Exception {
        // Initialize the database
        remiseRepository.saveAndFlush(remise);

        int databaseSizeBeforeUpdate = remiseRepository.findAll().size();

        // Update the remise using partial update
        Remise partialUpdatedRemise = new Remise();
        partialUpdatedRemise.setId(remise.getId());

        partialUpdatedRemise.numero(UPDATED_NUMERO).montant(UPDATED_MONTANT).descreption(UPDATED_DESCREPTION);

        restRemiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRemise))
            )
            .andExpect(status().isOk());

        // Validate the Remise in the database
        List<Remise> remiseList = remiseRepository.findAll();
        assertThat(remiseList).hasSize(databaseSizeBeforeUpdate);
        Remise testRemise = remiseList.get(remiseList.size() - 1);
        assertThat(testRemise.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testRemise.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testRemise.getDescreption()).isEqualTo(UPDATED_DESCREPTION);
    }

    @Test
    @Transactional
    void fullUpdateRemiseWithPatch() throws Exception {
        // Initialize the database
        remiseRepository.saveAndFlush(remise);

        int databaseSizeBeforeUpdate = remiseRepository.findAll().size();

        // Update the remise using partial update
        Remise partialUpdatedRemise = new Remise();
        partialUpdatedRemise.setId(remise.getId());

        partialUpdatedRemise.numero(UPDATED_NUMERO).montant(UPDATED_MONTANT).descreption(UPDATED_DESCREPTION);

        restRemiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRemise))
            )
            .andExpect(status().isOk());

        // Validate the Remise in the database
        List<Remise> remiseList = remiseRepository.findAll();
        assertThat(remiseList).hasSize(databaseSizeBeforeUpdate);
        Remise testRemise = remiseList.get(remiseList.size() - 1);
        assertThat(testRemise.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testRemise.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testRemise.getDescreption()).isEqualTo(UPDATED_DESCREPTION);
    }

    @Test
    @Transactional
    void patchNonExistingRemise() throws Exception {
        int databaseSizeBeforeUpdate = remiseRepository.findAll().size();
        remise.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, remise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(remise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remise in the database
        List<Remise> remiseList = remiseRepository.findAll();
        assertThat(remiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRemise() throws Exception {
        int databaseSizeBeforeUpdate = remiseRepository.findAll().size();
        remise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(remise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remise in the database
        List<Remise> remiseList = remiseRepository.findAll();
        assertThat(remiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRemise() throws Exception {
        int databaseSizeBeforeUpdate = remiseRepository.findAll().size();
        remise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemiseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(remise)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Remise in the database
        List<Remise> remiseList = remiseRepository.findAll();
        assertThat(remiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRemise() throws Exception {
        // Initialize the database
        remiseRepository.saveAndFlush(remise);

        int databaseSizeBeforeDelete = remiseRepository.findAll().size();

        // Delete the remise
        restRemiseMockMvc
            .perform(delete(ENTITY_API_URL_ID, remise.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Remise> remiseList = remiseRepository.findAll();
        assertThat(remiseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
