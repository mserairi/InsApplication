package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.SousCategory;
import com.mycompany.myapp.repository.SousCategoryRepository;
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
 * Integration tests for the {@link SousCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SousCategoryResourceIT {

    private static final String DEFAULT_LIBILLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBILLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sous-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SousCategoryRepository sousCategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSousCategoryMockMvc;

    private SousCategory sousCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SousCategory createEntity(EntityManager em) {
        SousCategory sousCategory = new SousCategory().libille(DEFAULT_LIBILLE).description(DEFAULT_DESCRIPTION);
        return sousCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SousCategory createUpdatedEntity(EntityManager em) {
        SousCategory sousCategory = new SousCategory().libille(UPDATED_LIBILLE).description(UPDATED_DESCRIPTION);
        return sousCategory;
    }

    @BeforeEach
    public void initTest() {
        sousCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createSousCategory() throws Exception {
        int databaseSizeBeforeCreate = sousCategoryRepository.findAll().size();
        // Create the SousCategory
        restSousCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sousCategory)))
            .andExpect(status().isCreated());

        // Validate the SousCategory in the database
        List<SousCategory> sousCategoryList = sousCategoryRepository.findAll();
        assertThat(sousCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        SousCategory testSousCategory = sousCategoryList.get(sousCategoryList.size() - 1);
        assertThat(testSousCategory.getLibille()).isEqualTo(DEFAULT_LIBILLE);
        assertThat(testSousCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createSousCategoryWithExistingId() throws Exception {
        // Create the SousCategory with an existing ID
        sousCategory.setId(1L);

        int databaseSizeBeforeCreate = sousCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSousCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sousCategory)))
            .andExpect(status().isBadRequest());

        // Validate the SousCategory in the database
        List<SousCategory> sousCategoryList = sousCategoryRepository.findAll();
        assertThat(sousCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLibilleIsRequired() throws Exception {
        int databaseSizeBeforeTest = sousCategoryRepository.findAll().size();
        // set the field null
        sousCategory.setLibille(null);

        // Create the SousCategory, which fails.

        restSousCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sousCategory)))
            .andExpect(status().isBadRequest());

        List<SousCategory> sousCategoryList = sousCategoryRepository.findAll();
        assertThat(sousCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sousCategoryRepository.findAll().size();
        // set the field null
        sousCategory.setDescription(null);

        // Create the SousCategory, which fails.

        restSousCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sousCategory)))
            .andExpect(status().isBadRequest());

        List<SousCategory> sousCategoryList = sousCategoryRepository.findAll();
        assertThat(sousCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSousCategories() throws Exception {
        // Initialize the database
        sousCategoryRepository.saveAndFlush(sousCategory);

        // Get all the sousCategoryList
        restSousCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sousCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].libille").value(hasItem(DEFAULT_LIBILLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getSousCategory() throws Exception {
        // Initialize the database
        sousCategoryRepository.saveAndFlush(sousCategory);

        // Get the sousCategory
        restSousCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, sousCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sousCategory.getId().intValue()))
            .andExpect(jsonPath("$.libille").value(DEFAULT_LIBILLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingSousCategory() throws Exception {
        // Get the sousCategory
        restSousCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSousCategory() throws Exception {
        // Initialize the database
        sousCategoryRepository.saveAndFlush(sousCategory);

        int databaseSizeBeforeUpdate = sousCategoryRepository.findAll().size();

        // Update the sousCategory
        SousCategory updatedSousCategory = sousCategoryRepository.findById(sousCategory.getId()).get();
        // Disconnect from session so that the updates on updatedSousCategory are not directly saved in db
        em.detach(updatedSousCategory);
        updatedSousCategory.libille(UPDATED_LIBILLE).description(UPDATED_DESCRIPTION);

        restSousCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSousCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSousCategory))
            )
            .andExpect(status().isOk());

        // Validate the SousCategory in the database
        List<SousCategory> sousCategoryList = sousCategoryRepository.findAll();
        assertThat(sousCategoryList).hasSize(databaseSizeBeforeUpdate);
        SousCategory testSousCategory = sousCategoryList.get(sousCategoryList.size() - 1);
        assertThat(testSousCategory.getLibille()).isEqualTo(UPDATED_LIBILLE);
        assertThat(testSousCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingSousCategory() throws Exception {
        int databaseSizeBeforeUpdate = sousCategoryRepository.findAll().size();
        sousCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSousCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sousCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sousCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the SousCategory in the database
        List<SousCategory> sousCategoryList = sousCategoryRepository.findAll();
        assertThat(sousCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSousCategory() throws Exception {
        int databaseSizeBeforeUpdate = sousCategoryRepository.findAll().size();
        sousCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSousCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sousCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the SousCategory in the database
        List<SousCategory> sousCategoryList = sousCategoryRepository.findAll();
        assertThat(sousCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSousCategory() throws Exception {
        int databaseSizeBeforeUpdate = sousCategoryRepository.findAll().size();
        sousCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSousCategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sousCategory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SousCategory in the database
        List<SousCategory> sousCategoryList = sousCategoryRepository.findAll();
        assertThat(sousCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSousCategoryWithPatch() throws Exception {
        // Initialize the database
        sousCategoryRepository.saveAndFlush(sousCategory);

        int databaseSizeBeforeUpdate = sousCategoryRepository.findAll().size();

        // Update the sousCategory using partial update
        SousCategory partialUpdatedSousCategory = new SousCategory();
        partialUpdatedSousCategory.setId(sousCategory.getId());

        partialUpdatedSousCategory.libille(UPDATED_LIBILLE);

        restSousCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSousCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSousCategory))
            )
            .andExpect(status().isOk());

        // Validate the SousCategory in the database
        List<SousCategory> sousCategoryList = sousCategoryRepository.findAll();
        assertThat(sousCategoryList).hasSize(databaseSizeBeforeUpdate);
        SousCategory testSousCategory = sousCategoryList.get(sousCategoryList.size() - 1);
        assertThat(testSousCategory.getLibille()).isEqualTo(UPDATED_LIBILLE);
        assertThat(testSousCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateSousCategoryWithPatch() throws Exception {
        // Initialize the database
        sousCategoryRepository.saveAndFlush(sousCategory);

        int databaseSizeBeforeUpdate = sousCategoryRepository.findAll().size();

        // Update the sousCategory using partial update
        SousCategory partialUpdatedSousCategory = new SousCategory();
        partialUpdatedSousCategory.setId(sousCategory.getId());

        partialUpdatedSousCategory.libille(UPDATED_LIBILLE).description(UPDATED_DESCRIPTION);

        restSousCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSousCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSousCategory))
            )
            .andExpect(status().isOk());

        // Validate the SousCategory in the database
        List<SousCategory> sousCategoryList = sousCategoryRepository.findAll();
        assertThat(sousCategoryList).hasSize(databaseSizeBeforeUpdate);
        SousCategory testSousCategory = sousCategoryList.get(sousCategoryList.size() - 1);
        assertThat(testSousCategory.getLibille()).isEqualTo(UPDATED_LIBILLE);
        assertThat(testSousCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingSousCategory() throws Exception {
        int databaseSizeBeforeUpdate = sousCategoryRepository.findAll().size();
        sousCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSousCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sousCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sousCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the SousCategory in the database
        List<SousCategory> sousCategoryList = sousCategoryRepository.findAll();
        assertThat(sousCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSousCategory() throws Exception {
        int databaseSizeBeforeUpdate = sousCategoryRepository.findAll().size();
        sousCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSousCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sousCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the SousCategory in the database
        List<SousCategory> sousCategoryList = sousCategoryRepository.findAll();
        assertThat(sousCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSousCategory() throws Exception {
        int databaseSizeBeforeUpdate = sousCategoryRepository.findAll().size();
        sousCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSousCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sousCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SousCategory in the database
        List<SousCategory> sousCategoryList = sousCategoryRepository.findAll();
        assertThat(sousCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSousCategory() throws Exception {
        // Initialize the database
        sousCategoryRepository.saveAndFlush(sousCategory);

        int databaseSizeBeforeDelete = sousCategoryRepository.findAll().size();

        // Delete the sousCategory
        restSousCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, sousCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SousCategory> sousCategoryList = sousCategoryRepository.findAll();
        assertThat(sousCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
