package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Lasession;
import com.mycompany.myapp.repository.LasessionRepository;
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
 * Integration tests for the {@link LasessionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LasessionResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_TARIF = 1F;
    private static final Float UPDATED_TARIF = 2F;

    private static final Instant DEFAULT_DEBUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEBUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/lasessions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LasessionRepository lasessionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLasessionMockMvc;

    private Lasession lasession;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lasession createEntity(EntityManager em) {
        Lasession lasession = new Lasession()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .tarif(DEFAULT_TARIF)
            .debut(DEFAULT_DEBUT)
            .fin(DEFAULT_FIN);
        return lasession;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lasession createUpdatedEntity(EntityManager em) {
        Lasession lasession = new Lasession()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .tarif(UPDATED_TARIF)
            .debut(UPDATED_DEBUT)
            .fin(UPDATED_FIN);
        return lasession;
    }

    @BeforeEach
    public void initTest() {
        lasession = createEntity(em);
    }

    @Test
    @Transactional
    void createLasession() throws Exception {
        int databaseSizeBeforeCreate = lasessionRepository.findAll().size();
        // Create the Lasession
        restLasessionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lasession)))
            .andExpect(status().isCreated());

        // Validate the Lasession in the database
        List<Lasession> lasessionList = lasessionRepository.findAll();
        assertThat(lasessionList).hasSize(databaseSizeBeforeCreate + 1);
        Lasession testLasession = lasessionList.get(lasessionList.size() - 1);
        assertThat(testLasession.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLasession.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLasession.getTarif()).isEqualTo(DEFAULT_TARIF);
        assertThat(testLasession.getDebut()).isEqualTo(DEFAULT_DEBUT);
        assertThat(testLasession.getFin()).isEqualTo(DEFAULT_FIN);
    }

    @Test
    @Transactional
    void createLasessionWithExistingId() throws Exception {
        // Create the Lasession with an existing ID
        lasession.setId(1L);

        int databaseSizeBeforeCreate = lasessionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLasessionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lasession)))
            .andExpect(status().isBadRequest());

        // Validate the Lasession in the database
        List<Lasession> lasessionList = lasessionRepository.findAll();
        assertThat(lasessionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lasessionRepository.findAll().size();
        // set the field null
        lasession.setCode(null);

        // Create the Lasession, which fails.

        restLasessionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lasession)))
            .andExpect(status().isBadRequest());

        List<Lasession> lasessionList = lasessionRepository.findAll();
        assertThat(lasessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = lasessionRepository.findAll().size();
        // set the field null
        lasession.setDescription(null);

        // Create the Lasession, which fails.

        restLasessionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lasession)))
            .andExpect(status().isBadRequest());

        List<Lasession> lasessionList = lasessionRepository.findAll();
        assertThat(lasessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLasessions() throws Exception {
        // Initialize the database
        lasessionRepository.saveAndFlush(lasession);

        // Get all the lasessionList
        restLasessionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lasession.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].tarif").value(hasItem(DEFAULT_TARIF.doubleValue())))
            .andExpect(jsonPath("$.[*].debut").value(hasItem(DEFAULT_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN.toString())));
    }

    @Test
    @Transactional
    void getLasession() throws Exception {
        // Initialize the database
        lasessionRepository.saveAndFlush(lasession);

        // Get the lasession
        restLasessionMockMvc
            .perform(get(ENTITY_API_URL_ID, lasession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lasession.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.tarif").value(DEFAULT_TARIF.doubleValue()))
            .andExpect(jsonPath("$.debut").value(DEFAULT_DEBUT.toString()))
            .andExpect(jsonPath("$.fin").value(DEFAULT_FIN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLasession() throws Exception {
        // Get the lasession
        restLasessionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLasession() throws Exception {
        // Initialize the database
        lasessionRepository.saveAndFlush(lasession);

        int databaseSizeBeforeUpdate = lasessionRepository.findAll().size();

        // Update the lasession
        Lasession updatedLasession = lasessionRepository.findById(lasession.getId()).get();
        // Disconnect from session so that the updates on updatedLasession are not directly saved in db
        em.detach(updatedLasession);
        updatedLasession.code(UPDATED_CODE).description(UPDATED_DESCRIPTION).tarif(UPDATED_TARIF).debut(UPDATED_DEBUT).fin(UPDATED_FIN);

        restLasessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLasession.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLasession))
            )
            .andExpect(status().isOk());

        // Validate the Lasession in the database
        List<Lasession> lasessionList = lasessionRepository.findAll();
        assertThat(lasessionList).hasSize(databaseSizeBeforeUpdate);
        Lasession testLasession = lasessionList.get(lasessionList.size() - 1);
        assertThat(testLasession.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLasession.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLasession.getTarif()).isEqualTo(UPDATED_TARIF);
        assertThat(testLasession.getDebut()).isEqualTo(UPDATED_DEBUT);
        assertThat(testLasession.getFin()).isEqualTo(UPDATED_FIN);
    }

    @Test
    @Transactional
    void putNonExistingLasession() throws Exception {
        int databaseSizeBeforeUpdate = lasessionRepository.findAll().size();
        lasession.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLasessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lasession.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lasession))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lasession in the database
        List<Lasession> lasessionList = lasessionRepository.findAll();
        assertThat(lasessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLasession() throws Exception {
        int databaseSizeBeforeUpdate = lasessionRepository.findAll().size();
        lasession.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLasessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lasession))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lasession in the database
        List<Lasession> lasessionList = lasessionRepository.findAll();
        assertThat(lasessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLasession() throws Exception {
        int databaseSizeBeforeUpdate = lasessionRepository.findAll().size();
        lasession.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLasessionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lasession)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lasession in the database
        List<Lasession> lasessionList = lasessionRepository.findAll();
        assertThat(lasessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLasessionWithPatch() throws Exception {
        // Initialize the database
        lasessionRepository.saveAndFlush(lasession);

        int databaseSizeBeforeUpdate = lasessionRepository.findAll().size();

        // Update the lasession using partial update
        Lasession partialUpdatedLasession = new Lasession();
        partialUpdatedLasession.setId(lasession.getId());

        partialUpdatedLasession.code(UPDATED_CODE).description(UPDATED_DESCRIPTION).tarif(UPDATED_TARIF).debut(UPDATED_DEBUT);

        restLasessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLasession.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLasession))
            )
            .andExpect(status().isOk());

        // Validate the Lasession in the database
        List<Lasession> lasessionList = lasessionRepository.findAll();
        assertThat(lasessionList).hasSize(databaseSizeBeforeUpdate);
        Lasession testLasession = lasessionList.get(lasessionList.size() - 1);
        assertThat(testLasession.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLasession.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLasession.getTarif()).isEqualTo(UPDATED_TARIF);
        assertThat(testLasession.getDebut()).isEqualTo(UPDATED_DEBUT);
        assertThat(testLasession.getFin()).isEqualTo(DEFAULT_FIN);
    }

    @Test
    @Transactional
    void fullUpdateLasessionWithPatch() throws Exception {
        // Initialize the database
        lasessionRepository.saveAndFlush(lasession);

        int databaseSizeBeforeUpdate = lasessionRepository.findAll().size();

        // Update the lasession using partial update
        Lasession partialUpdatedLasession = new Lasession();
        partialUpdatedLasession.setId(lasession.getId());

        partialUpdatedLasession
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .tarif(UPDATED_TARIF)
            .debut(UPDATED_DEBUT)
            .fin(UPDATED_FIN);

        restLasessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLasession.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLasession))
            )
            .andExpect(status().isOk());

        // Validate the Lasession in the database
        List<Lasession> lasessionList = lasessionRepository.findAll();
        assertThat(lasessionList).hasSize(databaseSizeBeforeUpdate);
        Lasession testLasession = lasessionList.get(lasessionList.size() - 1);
        assertThat(testLasession.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLasession.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLasession.getTarif()).isEqualTo(UPDATED_TARIF);
        assertThat(testLasession.getDebut()).isEqualTo(UPDATED_DEBUT);
        assertThat(testLasession.getFin()).isEqualTo(UPDATED_FIN);
    }

    @Test
    @Transactional
    void patchNonExistingLasession() throws Exception {
        int databaseSizeBeforeUpdate = lasessionRepository.findAll().size();
        lasession.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLasessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lasession.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lasession))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lasession in the database
        List<Lasession> lasessionList = lasessionRepository.findAll();
        assertThat(lasessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLasession() throws Exception {
        int databaseSizeBeforeUpdate = lasessionRepository.findAll().size();
        lasession.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLasessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lasession))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lasession in the database
        List<Lasession> lasessionList = lasessionRepository.findAll();
        assertThat(lasessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLasession() throws Exception {
        int databaseSizeBeforeUpdate = lasessionRepository.findAll().size();
        lasession.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLasessionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(lasession))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lasession in the database
        List<Lasession> lasessionList = lasessionRepository.findAll();
        assertThat(lasessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLasession() throws Exception {
        // Initialize the database
        lasessionRepository.saveAndFlush(lasession);

        int databaseSizeBeforeDelete = lasessionRepository.findAll().size();

        // Delete the lasession
        restLasessionMockMvc
            .perform(delete(ENTITY_API_URL_ID, lasession.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lasession> lasessionList = lasessionRepository.findAll();
        assertThat(lasessionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
