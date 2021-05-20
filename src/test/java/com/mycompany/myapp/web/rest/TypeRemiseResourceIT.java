package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TypeRemise;
import com.mycompany.myapp.repository.TypeRemiseRepository;
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
 * Integration tests for the {@link TypeRemiseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeRemiseResourceIT {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_LIBILLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBILLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONDITION = "AAAAAAAAAA";
    private static final String UPDATED_CONDITION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MANTANT_LIBRE = false;
    private static final Boolean UPDATED_MANTANT_LIBRE = true;

    private static final Float DEFAULT_MONTANT_UNITAIRE = 1F;
    private static final Float UPDATED_MONTANT_UNITAIRE = 2F;

    private static final String ENTITY_API_URL = "/api/type-remises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypeRemiseRepository typeRemiseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeRemiseMockMvc;

    private TypeRemise typeRemise;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeRemise createEntity(EntityManager em) {
        TypeRemise typeRemise = new TypeRemise()
            .numero(DEFAULT_NUMERO)
            .libille(DEFAULT_LIBILLE)
            .condition(DEFAULT_CONDITION)
            .mantantLibre(DEFAULT_MANTANT_LIBRE)
            .montantUnitaire(DEFAULT_MONTANT_UNITAIRE);
        return typeRemise;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeRemise createUpdatedEntity(EntityManager em) {
        TypeRemise typeRemise = new TypeRemise()
            .numero(UPDATED_NUMERO)
            .libille(UPDATED_LIBILLE)
            .condition(UPDATED_CONDITION)
            .mantantLibre(UPDATED_MANTANT_LIBRE)
            .montantUnitaire(UPDATED_MONTANT_UNITAIRE);
        return typeRemise;
    }

    @BeforeEach
    public void initTest() {
        typeRemise = createEntity(em);
    }

    @Test
    @Transactional
    void createTypeRemise() throws Exception {
        int databaseSizeBeforeCreate = typeRemiseRepository.findAll().size();
        // Create the TypeRemise
        restTypeRemiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeRemise)))
            .andExpect(status().isCreated());

        // Validate the TypeRemise in the database
        List<TypeRemise> typeRemiseList = typeRemiseRepository.findAll();
        assertThat(typeRemiseList).hasSize(databaseSizeBeforeCreate + 1);
        TypeRemise testTypeRemise = typeRemiseList.get(typeRemiseList.size() - 1);
        assertThat(testTypeRemise.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testTypeRemise.getLibille()).isEqualTo(DEFAULT_LIBILLE);
        assertThat(testTypeRemise.getCondition()).isEqualTo(DEFAULT_CONDITION);
        assertThat(testTypeRemise.getMantantLibre()).isEqualTo(DEFAULT_MANTANT_LIBRE);
        assertThat(testTypeRemise.getMontantUnitaire()).isEqualTo(DEFAULT_MONTANT_UNITAIRE);
    }

    @Test
    @Transactional
    void createTypeRemiseWithExistingId() throws Exception {
        // Create the TypeRemise with an existing ID
        typeRemise.setId(1L);

        int databaseSizeBeforeCreate = typeRemiseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeRemiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeRemise)))
            .andExpect(status().isBadRequest());

        // Validate the TypeRemise in the database
        List<TypeRemise> typeRemiseList = typeRemiseRepository.findAll();
        assertThat(typeRemiseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTypeRemises() throws Exception {
        // Initialize the database
        typeRemiseRepository.saveAndFlush(typeRemise);

        // Get all the typeRemiseList
        restTypeRemiseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeRemise.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].libille").value(hasItem(DEFAULT_LIBILLE)))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION)))
            .andExpect(jsonPath("$.[*].mantantLibre").value(hasItem(DEFAULT_MANTANT_LIBRE.booleanValue())))
            .andExpect(jsonPath("$.[*].montantUnitaire").value(hasItem(DEFAULT_MONTANT_UNITAIRE.doubleValue())));
    }

    @Test
    @Transactional
    void getTypeRemise() throws Exception {
        // Initialize the database
        typeRemiseRepository.saveAndFlush(typeRemise);

        // Get the typeRemise
        restTypeRemiseMockMvc
            .perform(get(ENTITY_API_URL_ID, typeRemise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeRemise.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.libille").value(DEFAULT_LIBILLE))
            .andExpect(jsonPath("$.condition").value(DEFAULT_CONDITION))
            .andExpect(jsonPath("$.mantantLibre").value(DEFAULT_MANTANT_LIBRE.booleanValue()))
            .andExpect(jsonPath("$.montantUnitaire").value(DEFAULT_MONTANT_UNITAIRE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingTypeRemise() throws Exception {
        // Get the typeRemise
        restTypeRemiseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTypeRemise() throws Exception {
        // Initialize the database
        typeRemiseRepository.saveAndFlush(typeRemise);

        int databaseSizeBeforeUpdate = typeRemiseRepository.findAll().size();

        // Update the typeRemise
        TypeRemise updatedTypeRemise = typeRemiseRepository.findById(typeRemise.getId()).get();
        // Disconnect from session so that the updates on updatedTypeRemise are not directly saved in db
        em.detach(updatedTypeRemise);
        updatedTypeRemise
            .numero(UPDATED_NUMERO)
            .libille(UPDATED_LIBILLE)
            .condition(UPDATED_CONDITION)
            .mantantLibre(UPDATED_MANTANT_LIBRE)
            .montantUnitaire(UPDATED_MONTANT_UNITAIRE);

        restTypeRemiseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTypeRemise.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTypeRemise))
            )
            .andExpect(status().isOk());

        // Validate the TypeRemise in the database
        List<TypeRemise> typeRemiseList = typeRemiseRepository.findAll();
        assertThat(typeRemiseList).hasSize(databaseSizeBeforeUpdate);
        TypeRemise testTypeRemise = typeRemiseList.get(typeRemiseList.size() - 1);
        assertThat(testTypeRemise.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTypeRemise.getLibille()).isEqualTo(UPDATED_LIBILLE);
        assertThat(testTypeRemise.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testTypeRemise.getMantantLibre()).isEqualTo(UPDATED_MANTANT_LIBRE);
        assertThat(testTypeRemise.getMontantUnitaire()).isEqualTo(UPDATED_MONTANT_UNITAIRE);
    }

    @Test
    @Transactional
    void putNonExistingTypeRemise() throws Exception {
        int databaseSizeBeforeUpdate = typeRemiseRepository.findAll().size();
        typeRemise.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeRemiseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeRemise.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeRemise))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeRemise in the database
        List<TypeRemise> typeRemiseList = typeRemiseRepository.findAll();
        assertThat(typeRemiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeRemise() throws Exception {
        int databaseSizeBeforeUpdate = typeRemiseRepository.findAll().size();
        typeRemise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeRemiseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeRemise))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeRemise in the database
        List<TypeRemise> typeRemiseList = typeRemiseRepository.findAll();
        assertThat(typeRemiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeRemise() throws Exception {
        int databaseSizeBeforeUpdate = typeRemiseRepository.findAll().size();
        typeRemise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeRemiseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeRemise)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeRemise in the database
        List<TypeRemise> typeRemiseList = typeRemiseRepository.findAll();
        assertThat(typeRemiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeRemiseWithPatch() throws Exception {
        // Initialize the database
        typeRemiseRepository.saveAndFlush(typeRemise);

        int databaseSizeBeforeUpdate = typeRemiseRepository.findAll().size();

        // Update the typeRemise using partial update
        TypeRemise partialUpdatedTypeRemise = new TypeRemise();
        partialUpdatedTypeRemise.setId(typeRemise.getId());

        partialUpdatedTypeRemise.mantantLibre(UPDATED_MANTANT_LIBRE).montantUnitaire(UPDATED_MONTANT_UNITAIRE);

        restTypeRemiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeRemise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeRemise))
            )
            .andExpect(status().isOk());

        // Validate the TypeRemise in the database
        List<TypeRemise> typeRemiseList = typeRemiseRepository.findAll();
        assertThat(typeRemiseList).hasSize(databaseSizeBeforeUpdate);
        TypeRemise testTypeRemise = typeRemiseList.get(typeRemiseList.size() - 1);
        assertThat(testTypeRemise.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testTypeRemise.getLibille()).isEqualTo(DEFAULT_LIBILLE);
        assertThat(testTypeRemise.getCondition()).isEqualTo(DEFAULT_CONDITION);
        assertThat(testTypeRemise.getMantantLibre()).isEqualTo(UPDATED_MANTANT_LIBRE);
        assertThat(testTypeRemise.getMontantUnitaire()).isEqualTo(UPDATED_MONTANT_UNITAIRE);
    }

    @Test
    @Transactional
    void fullUpdateTypeRemiseWithPatch() throws Exception {
        // Initialize the database
        typeRemiseRepository.saveAndFlush(typeRemise);

        int databaseSizeBeforeUpdate = typeRemiseRepository.findAll().size();

        // Update the typeRemise using partial update
        TypeRemise partialUpdatedTypeRemise = new TypeRemise();
        partialUpdatedTypeRemise.setId(typeRemise.getId());

        partialUpdatedTypeRemise
            .numero(UPDATED_NUMERO)
            .libille(UPDATED_LIBILLE)
            .condition(UPDATED_CONDITION)
            .mantantLibre(UPDATED_MANTANT_LIBRE)
            .montantUnitaire(UPDATED_MONTANT_UNITAIRE);

        restTypeRemiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeRemise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeRemise))
            )
            .andExpect(status().isOk());

        // Validate the TypeRemise in the database
        List<TypeRemise> typeRemiseList = typeRemiseRepository.findAll();
        assertThat(typeRemiseList).hasSize(databaseSizeBeforeUpdate);
        TypeRemise testTypeRemise = typeRemiseList.get(typeRemiseList.size() - 1);
        assertThat(testTypeRemise.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTypeRemise.getLibille()).isEqualTo(UPDATED_LIBILLE);
        assertThat(testTypeRemise.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testTypeRemise.getMantantLibre()).isEqualTo(UPDATED_MANTANT_LIBRE);
        assertThat(testTypeRemise.getMontantUnitaire()).isEqualTo(UPDATED_MONTANT_UNITAIRE);
    }

    @Test
    @Transactional
    void patchNonExistingTypeRemise() throws Exception {
        int databaseSizeBeforeUpdate = typeRemiseRepository.findAll().size();
        typeRemise.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeRemiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeRemise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeRemise))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeRemise in the database
        List<TypeRemise> typeRemiseList = typeRemiseRepository.findAll();
        assertThat(typeRemiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeRemise() throws Exception {
        int databaseSizeBeforeUpdate = typeRemiseRepository.findAll().size();
        typeRemise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeRemiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeRemise))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeRemise in the database
        List<TypeRemise> typeRemiseList = typeRemiseRepository.findAll();
        assertThat(typeRemiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeRemise() throws Exception {
        int databaseSizeBeforeUpdate = typeRemiseRepository.findAll().size();
        typeRemise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeRemiseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(typeRemise))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeRemise in the database
        List<TypeRemise> typeRemiseList = typeRemiseRepository.findAll();
        assertThat(typeRemiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeRemise() throws Exception {
        // Initialize the database
        typeRemiseRepository.saveAndFlush(typeRemise);

        int databaseSizeBeforeDelete = typeRemiseRepository.findAll().size();

        // Delete the typeRemise
        restTypeRemiseMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeRemise.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeRemise> typeRemiseList = typeRemiseRepository.findAll();
        assertThat(typeRemiseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
