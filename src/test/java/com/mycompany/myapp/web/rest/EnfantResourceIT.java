package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Enfant;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.enumeration.TypeGenre;
import com.mycompany.myapp.repository.EnfantRepository;
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
 * Integration tests for the {@link EnfantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EnfantResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_NAISSANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_NAISSANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final TypeGenre DEFAULT_GENRE = TypeGenre.MASCULIN;
    private static final TypeGenre UPDATED_GENRE = TypeGenre.FEMININ;

    private static final String DEFAULT_NOM_PARENT_2 = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PARENT_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_PARENT_2 = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_PARENT_2 = "BBBBBBBBBB";

    private static final String DEFAULT_MOB_PARENT_2 = "0936836063";
    private static final String UPDATED_MOB_PARENT_2 = "7149864378";

    private static final String DEFAULT_EMAIL_PARENT_2 = "=@|[";
    private static final String UPDATED_EMAIL_PARENT_2 = "[D@mtoX";

    private static final String DEFAULT_INFO_SANTE = "AAAAAAAAAA";
    private static final String UPDATED_INFO_SANTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUTORISATION_IMAGE = false;
    private static final Boolean UPDATED_AUTORISATION_IMAGE = true;

    private static final String DEFAULT_NOM_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_MOB_CONTACT = "9839537977";
    private static final String UPDATED_MOB_CONTACT = "1093681536";

    private static final String ENTITY_API_URL = "/api/enfants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EnfantRepository enfantRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnfantMockMvc;

    private Enfant enfant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enfant createEntity(EntityManager em) {
        Enfant enfant = new Enfant()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .genre(DEFAULT_GENRE)
            .nomParent2(DEFAULT_NOM_PARENT_2)
            .prenomParent2(DEFAULT_PRENOM_PARENT_2)
            .mobParent2(DEFAULT_MOB_PARENT_2)
            .emailParent2(DEFAULT_EMAIL_PARENT_2)
            .infoSante(DEFAULT_INFO_SANTE)
            .autorisationImage(DEFAULT_AUTORISATION_IMAGE)
            .nomContact(DEFAULT_NOM_CONTACT)
            .mobContact(DEFAULT_MOB_CONTACT);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        enfant.setParent(user);
        return enfant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enfant createUpdatedEntity(EntityManager em) {
        Enfant enfant = new Enfant()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .genre(UPDATED_GENRE)
            .nomParent2(UPDATED_NOM_PARENT_2)
            .prenomParent2(UPDATED_PRENOM_PARENT_2)
            .mobParent2(UPDATED_MOB_PARENT_2)
            .emailParent2(UPDATED_EMAIL_PARENT_2)
            .infoSante(UPDATED_INFO_SANTE)
            .autorisationImage(UPDATED_AUTORISATION_IMAGE)
            .nomContact(UPDATED_NOM_CONTACT)
            .mobContact(UPDATED_MOB_CONTACT);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        enfant.setParent(user);
        return enfant;
    }

    @BeforeEach
    public void initTest() {
        enfant = createEntity(em);
    }

    @Test
    @Transactional
    void createEnfant() throws Exception {
        int databaseSizeBeforeCreate = enfantRepository.findAll().size();
        // Create the Enfant
        restEnfantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enfant)))
            .andExpect(status().isCreated());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeCreate + 1);
        Enfant testEnfant = enfantList.get(enfantList.size() - 1);
        assertThat(testEnfant.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEnfant.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testEnfant.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testEnfant.getGenre()).isEqualTo(DEFAULT_GENRE);
        assertThat(testEnfant.getNomParent2()).isEqualTo(DEFAULT_NOM_PARENT_2);
        assertThat(testEnfant.getPrenomParent2()).isEqualTo(DEFAULT_PRENOM_PARENT_2);
        assertThat(testEnfant.getMobParent2()).isEqualTo(DEFAULT_MOB_PARENT_2);
        assertThat(testEnfant.getEmailParent2()).isEqualTo(DEFAULT_EMAIL_PARENT_2);
        assertThat(testEnfant.getInfoSante()).isEqualTo(DEFAULT_INFO_SANTE);
        assertThat(testEnfant.getAutorisationImage()).isEqualTo(DEFAULT_AUTORISATION_IMAGE);
        assertThat(testEnfant.getNomContact()).isEqualTo(DEFAULT_NOM_CONTACT);
        assertThat(testEnfant.getMobContact()).isEqualTo(DEFAULT_MOB_CONTACT);
    }

    @Test
    @Transactional
    void createEnfantWithExistingId() throws Exception {
        // Create the Enfant with an existing ID
        enfant.setId(1L);

        int databaseSizeBeforeCreate = enfantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnfantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enfant)))
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = enfantRepository.findAll().size();
        // set the field null
        enfant.setNom(null);

        // Create the Enfant, which fails.

        restEnfantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enfant)))
            .andExpect(status().isBadRequest());

        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = enfantRepository.findAll().size();
        // set the field null
        enfant.setPrenom(null);

        // Create the Enfant, which fails.

        restEnfantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enfant)))
            .andExpect(status().isBadRequest());

        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEnfants() throws Exception {
        // Initialize the database
        enfantRepository.saveAndFlush(enfant);

        // Get all the enfantList
        restEnfantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enfant.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].genre").value(hasItem(DEFAULT_GENRE.toString())))
            .andExpect(jsonPath("$.[*].nomParent2").value(hasItem(DEFAULT_NOM_PARENT_2)))
            .andExpect(jsonPath("$.[*].prenomParent2").value(hasItem(DEFAULT_PRENOM_PARENT_2)))
            .andExpect(jsonPath("$.[*].mobParent2").value(hasItem(DEFAULT_MOB_PARENT_2)))
            .andExpect(jsonPath("$.[*].emailParent2").value(hasItem(DEFAULT_EMAIL_PARENT_2)))
            .andExpect(jsonPath("$.[*].infoSante").value(hasItem(DEFAULT_INFO_SANTE)))
            .andExpect(jsonPath("$.[*].autorisationImage").value(hasItem(DEFAULT_AUTORISATION_IMAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].nomContact").value(hasItem(DEFAULT_NOM_CONTACT)))
            .andExpect(jsonPath("$.[*].mobContact").value(hasItem(DEFAULT_MOB_CONTACT)));
    }

    @Test
    @Transactional
    void getEnfant() throws Exception {
        // Initialize the database
        enfantRepository.saveAndFlush(enfant);

        // Get the enfant
        restEnfantMockMvc
            .perform(get(ENTITY_API_URL_ID, enfant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enfant.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.genre").value(DEFAULT_GENRE.toString()))
            .andExpect(jsonPath("$.nomParent2").value(DEFAULT_NOM_PARENT_2))
            .andExpect(jsonPath("$.prenomParent2").value(DEFAULT_PRENOM_PARENT_2))
            .andExpect(jsonPath("$.mobParent2").value(DEFAULT_MOB_PARENT_2))
            .andExpect(jsonPath("$.emailParent2").value(DEFAULT_EMAIL_PARENT_2))
            .andExpect(jsonPath("$.infoSante").value(DEFAULT_INFO_SANTE))
            .andExpect(jsonPath("$.autorisationImage").value(DEFAULT_AUTORISATION_IMAGE.booleanValue()))
            .andExpect(jsonPath("$.nomContact").value(DEFAULT_NOM_CONTACT))
            .andExpect(jsonPath("$.mobContact").value(DEFAULT_MOB_CONTACT));
    }

    @Test
    @Transactional
    void getNonExistingEnfant() throws Exception {
        // Get the enfant
        restEnfantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEnfant() throws Exception {
        // Initialize the database
        enfantRepository.saveAndFlush(enfant);

        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();

        // Update the enfant
        Enfant updatedEnfant = enfantRepository.findById(enfant.getId()).get();
        // Disconnect from session so that the updates on updatedEnfant are not directly saved in db
        em.detach(updatedEnfant);
        updatedEnfant
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .genre(UPDATED_GENRE)
            .nomParent2(UPDATED_NOM_PARENT_2)
            .prenomParent2(UPDATED_PRENOM_PARENT_2)
            .mobParent2(UPDATED_MOB_PARENT_2)
            .emailParent2(UPDATED_EMAIL_PARENT_2)
            .infoSante(UPDATED_INFO_SANTE)
            .autorisationImage(UPDATED_AUTORISATION_IMAGE)
            .nomContact(UPDATED_NOM_CONTACT)
            .mobContact(UPDATED_MOB_CONTACT);

        restEnfantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEnfant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEnfant))
            )
            .andExpect(status().isOk());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
        Enfant testEnfant = enfantList.get(enfantList.size() - 1);
        assertThat(testEnfant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEnfant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testEnfant.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testEnfant.getGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testEnfant.getNomParent2()).isEqualTo(UPDATED_NOM_PARENT_2);
        assertThat(testEnfant.getPrenomParent2()).isEqualTo(UPDATED_PRENOM_PARENT_2);
        assertThat(testEnfant.getMobParent2()).isEqualTo(UPDATED_MOB_PARENT_2);
        assertThat(testEnfant.getEmailParent2()).isEqualTo(UPDATED_EMAIL_PARENT_2);
        assertThat(testEnfant.getInfoSante()).isEqualTo(UPDATED_INFO_SANTE);
        assertThat(testEnfant.getAutorisationImage()).isEqualTo(UPDATED_AUTORISATION_IMAGE);
        assertThat(testEnfant.getNomContact()).isEqualTo(UPDATED_NOM_CONTACT);
        assertThat(testEnfant.getMobContact()).isEqualTo(UPDATED_MOB_CONTACT);
    }

    @Test
    @Transactional
    void putNonExistingEnfant() throws Exception {
        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();
        enfant.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enfant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enfant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnfant() throws Exception {
        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();
        enfant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enfant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnfant() throws Exception {
        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();
        enfant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enfant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnfantWithPatch() throws Exception {
        // Initialize the database
        enfantRepository.saveAndFlush(enfant);

        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();

        // Update the enfant using partial update
        Enfant partialUpdatedEnfant = new Enfant();
        partialUpdatedEnfant.setId(enfant.getId());

        partialUpdatedEnfant
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .genre(UPDATED_GENRE)
            .nomParent2(UPDATED_NOM_PARENT_2)
            .prenomParent2(UPDATED_PRENOM_PARENT_2)
            .mobParent2(UPDATED_MOB_PARENT_2)
            .autorisationImage(UPDATED_AUTORISATION_IMAGE)
            .nomContact(UPDATED_NOM_CONTACT)
            .mobContact(UPDATED_MOB_CONTACT);

        restEnfantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnfant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnfant))
            )
            .andExpect(status().isOk());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
        Enfant testEnfant = enfantList.get(enfantList.size() - 1);
        assertThat(testEnfant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEnfant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testEnfant.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testEnfant.getGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testEnfant.getNomParent2()).isEqualTo(UPDATED_NOM_PARENT_2);
        assertThat(testEnfant.getPrenomParent2()).isEqualTo(UPDATED_PRENOM_PARENT_2);
        assertThat(testEnfant.getMobParent2()).isEqualTo(UPDATED_MOB_PARENT_2);
        assertThat(testEnfant.getEmailParent2()).isEqualTo(DEFAULT_EMAIL_PARENT_2);
        assertThat(testEnfant.getInfoSante()).isEqualTo(DEFAULT_INFO_SANTE);
        assertThat(testEnfant.getAutorisationImage()).isEqualTo(UPDATED_AUTORISATION_IMAGE);
        assertThat(testEnfant.getNomContact()).isEqualTo(UPDATED_NOM_CONTACT);
        assertThat(testEnfant.getMobContact()).isEqualTo(UPDATED_MOB_CONTACT);
    }

    @Test
    @Transactional
    void fullUpdateEnfantWithPatch() throws Exception {
        // Initialize the database
        enfantRepository.saveAndFlush(enfant);

        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();

        // Update the enfant using partial update
        Enfant partialUpdatedEnfant = new Enfant();
        partialUpdatedEnfant.setId(enfant.getId());

        partialUpdatedEnfant
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .genre(UPDATED_GENRE)
            .nomParent2(UPDATED_NOM_PARENT_2)
            .prenomParent2(UPDATED_PRENOM_PARENT_2)
            .mobParent2(UPDATED_MOB_PARENT_2)
            .emailParent2(UPDATED_EMAIL_PARENT_2)
            .infoSante(UPDATED_INFO_SANTE)
            .autorisationImage(UPDATED_AUTORISATION_IMAGE)
            .nomContact(UPDATED_NOM_CONTACT)
            .mobContact(UPDATED_MOB_CONTACT);

        restEnfantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnfant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnfant))
            )
            .andExpect(status().isOk());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
        Enfant testEnfant = enfantList.get(enfantList.size() - 1);
        assertThat(testEnfant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEnfant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testEnfant.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testEnfant.getGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testEnfant.getNomParent2()).isEqualTo(UPDATED_NOM_PARENT_2);
        assertThat(testEnfant.getPrenomParent2()).isEqualTo(UPDATED_PRENOM_PARENT_2);
        assertThat(testEnfant.getMobParent2()).isEqualTo(UPDATED_MOB_PARENT_2);
        assertThat(testEnfant.getEmailParent2()).isEqualTo(UPDATED_EMAIL_PARENT_2);
        assertThat(testEnfant.getInfoSante()).isEqualTo(UPDATED_INFO_SANTE);
        assertThat(testEnfant.getAutorisationImage()).isEqualTo(UPDATED_AUTORISATION_IMAGE);
        assertThat(testEnfant.getNomContact()).isEqualTo(UPDATED_NOM_CONTACT);
        assertThat(testEnfant.getMobContact()).isEqualTo(UPDATED_MOB_CONTACT);
    }

    @Test
    @Transactional
    void patchNonExistingEnfant() throws Exception {
        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();
        enfant.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, enfant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enfant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnfant() throws Exception {
        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();
        enfant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enfant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnfant() throws Exception {
        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();
        enfant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(enfant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnfant() throws Exception {
        // Initialize the database
        enfantRepository.saveAndFlush(enfant);

        int databaseSizeBeforeDelete = enfantRepository.findAll().size();

        // Delete the enfant
        restEnfantMockMvc
            .perform(delete(ENTITY_API_URL_ID, enfant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
