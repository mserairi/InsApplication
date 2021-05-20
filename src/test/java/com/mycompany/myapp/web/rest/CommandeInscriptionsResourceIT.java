package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CommandeInscriptions;
import com.mycompany.myapp.domain.enumeration.Etatcommande;
import com.mycompany.myapp.repository.CommandeInscriptionsRepository;
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
 * Integration tests for the {@link CommandeInscriptionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommandeInscriptionsResourceIT {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final Float DEFAULT_TOTAL_AVANT_REMISE = 1F;
    private static final Float UPDATED_TOTAL_AVANT_REMISE = 2F;

    private static final Float DEFAULT_TOTAL_REMISE = 1F;
    private static final Float UPDATED_TOTAL_REMISE = 2F;

    private static final Etatcommande DEFAULT_STATUS = Etatcommande.ENCOURS;
    private static final Etatcommande UPDATED_STATUS = Etatcommande.TRAITEE;

    private static final String ENTITY_API_URL = "/api/commande-inscriptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CommandeInscriptionsRepository commandeInscriptionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommandeInscriptionsMockMvc;

    private CommandeInscriptions commandeInscriptions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeInscriptions createEntity(EntityManager em) {
        CommandeInscriptions commandeInscriptions = new CommandeInscriptions()
            .numero(DEFAULT_NUMERO)
            .totalAvantRemise(DEFAULT_TOTAL_AVANT_REMISE)
            .totalRemise(DEFAULT_TOTAL_REMISE)
            .status(DEFAULT_STATUS);
        return commandeInscriptions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeInscriptions createUpdatedEntity(EntityManager em) {
        CommandeInscriptions commandeInscriptions = new CommandeInscriptions()
            .numero(UPDATED_NUMERO)
            .totalAvantRemise(UPDATED_TOTAL_AVANT_REMISE)
            .totalRemise(UPDATED_TOTAL_REMISE)
            .status(UPDATED_STATUS);
        return commandeInscriptions;
    }

    @BeforeEach
    public void initTest() {
        commandeInscriptions = createEntity(em);
    }

    @Test
    @Transactional
    void createCommandeInscriptions() throws Exception {
        int databaseSizeBeforeCreate = commandeInscriptionsRepository.findAll().size();
        // Create the CommandeInscriptions
        restCommandeInscriptionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commandeInscriptions))
            )
            .andExpect(status().isCreated());

        // Validate the CommandeInscriptions in the database
        List<CommandeInscriptions> commandeInscriptionsList = commandeInscriptionsRepository.findAll();
        assertThat(commandeInscriptionsList).hasSize(databaseSizeBeforeCreate + 1);
        CommandeInscriptions testCommandeInscriptions = commandeInscriptionsList.get(commandeInscriptionsList.size() - 1);
        assertThat(testCommandeInscriptions.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCommandeInscriptions.getTotalAvantRemise()).isEqualTo(DEFAULT_TOTAL_AVANT_REMISE);
        assertThat(testCommandeInscriptions.getTotalRemise()).isEqualTo(DEFAULT_TOTAL_REMISE);
        assertThat(testCommandeInscriptions.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createCommandeInscriptionsWithExistingId() throws Exception {
        // Create the CommandeInscriptions with an existing ID
        commandeInscriptions.setId(1L);

        int databaseSizeBeforeCreate = commandeInscriptionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandeInscriptionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commandeInscriptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommandeInscriptions in the database
        List<CommandeInscriptions> commandeInscriptionsList = commandeInscriptionsRepository.findAll();
        assertThat(commandeInscriptionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCommandeInscriptions() throws Exception {
        // Initialize the database
        commandeInscriptionsRepository.saveAndFlush(commandeInscriptions);

        // Get all the commandeInscriptionsList
        restCommandeInscriptionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandeInscriptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].totalAvantRemise").value(hasItem(DEFAULT_TOTAL_AVANT_REMISE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalRemise").value(hasItem(DEFAULT_TOTAL_REMISE.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getCommandeInscriptions() throws Exception {
        // Initialize the database
        commandeInscriptionsRepository.saveAndFlush(commandeInscriptions);

        // Get the commandeInscriptions
        restCommandeInscriptionsMockMvc
            .perform(get(ENTITY_API_URL_ID, commandeInscriptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commandeInscriptions.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.totalAvantRemise").value(DEFAULT_TOTAL_AVANT_REMISE.doubleValue()))
            .andExpect(jsonPath("$.totalRemise").value(DEFAULT_TOTAL_REMISE.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCommandeInscriptions() throws Exception {
        // Get the commandeInscriptions
        restCommandeInscriptionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCommandeInscriptions() throws Exception {
        // Initialize the database
        commandeInscriptionsRepository.saveAndFlush(commandeInscriptions);

        int databaseSizeBeforeUpdate = commandeInscriptionsRepository.findAll().size();

        // Update the commandeInscriptions
        CommandeInscriptions updatedCommandeInscriptions = commandeInscriptionsRepository.findById(commandeInscriptions.getId()).get();
        // Disconnect from session so that the updates on updatedCommandeInscriptions are not directly saved in db
        em.detach(updatedCommandeInscriptions);
        updatedCommandeInscriptions
            .numero(UPDATED_NUMERO)
            .totalAvantRemise(UPDATED_TOTAL_AVANT_REMISE)
            .totalRemise(UPDATED_TOTAL_REMISE)
            .status(UPDATED_STATUS);

        restCommandeInscriptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCommandeInscriptions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCommandeInscriptions))
            )
            .andExpect(status().isOk());

        // Validate the CommandeInscriptions in the database
        List<CommandeInscriptions> commandeInscriptionsList = commandeInscriptionsRepository.findAll();
        assertThat(commandeInscriptionsList).hasSize(databaseSizeBeforeUpdate);
        CommandeInscriptions testCommandeInscriptions = commandeInscriptionsList.get(commandeInscriptionsList.size() - 1);
        assertThat(testCommandeInscriptions.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCommandeInscriptions.getTotalAvantRemise()).isEqualTo(UPDATED_TOTAL_AVANT_REMISE);
        assertThat(testCommandeInscriptions.getTotalRemise()).isEqualTo(UPDATED_TOTAL_REMISE);
        assertThat(testCommandeInscriptions.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingCommandeInscriptions() throws Exception {
        int databaseSizeBeforeUpdate = commandeInscriptionsRepository.findAll().size();
        commandeInscriptions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandeInscriptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commandeInscriptions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commandeInscriptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommandeInscriptions in the database
        List<CommandeInscriptions> commandeInscriptionsList = commandeInscriptionsRepository.findAll();
        assertThat(commandeInscriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommandeInscriptions() throws Exception {
        int databaseSizeBeforeUpdate = commandeInscriptionsRepository.findAll().size();
        commandeInscriptions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommandeInscriptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commandeInscriptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommandeInscriptions in the database
        List<CommandeInscriptions> commandeInscriptionsList = commandeInscriptionsRepository.findAll();
        assertThat(commandeInscriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommandeInscriptions() throws Exception {
        int databaseSizeBeforeUpdate = commandeInscriptionsRepository.findAll().size();
        commandeInscriptions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommandeInscriptionsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commandeInscriptions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommandeInscriptions in the database
        List<CommandeInscriptions> commandeInscriptionsList = commandeInscriptionsRepository.findAll();
        assertThat(commandeInscriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommandeInscriptionsWithPatch() throws Exception {
        // Initialize the database
        commandeInscriptionsRepository.saveAndFlush(commandeInscriptions);

        int databaseSizeBeforeUpdate = commandeInscriptionsRepository.findAll().size();

        // Update the commandeInscriptions using partial update
        CommandeInscriptions partialUpdatedCommandeInscriptions = new CommandeInscriptions();
        partialUpdatedCommandeInscriptions.setId(commandeInscriptions.getId());

        partialUpdatedCommandeInscriptions.numero(UPDATED_NUMERO);

        restCommandeInscriptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommandeInscriptions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommandeInscriptions))
            )
            .andExpect(status().isOk());

        // Validate the CommandeInscriptions in the database
        List<CommandeInscriptions> commandeInscriptionsList = commandeInscriptionsRepository.findAll();
        assertThat(commandeInscriptionsList).hasSize(databaseSizeBeforeUpdate);
        CommandeInscriptions testCommandeInscriptions = commandeInscriptionsList.get(commandeInscriptionsList.size() - 1);
        assertThat(testCommandeInscriptions.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCommandeInscriptions.getTotalAvantRemise()).isEqualTo(DEFAULT_TOTAL_AVANT_REMISE);
        assertThat(testCommandeInscriptions.getTotalRemise()).isEqualTo(DEFAULT_TOTAL_REMISE);
        assertThat(testCommandeInscriptions.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateCommandeInscriptionsWithPatch() throws Exception {
        // Initialize the database
        commandeInscriptionsRepository.saveAndFlush(commandeInscriptions);

        int databaseSizeBeforeUpdate = commandeInscriptionsRepository.findAll().size();

        // Update the commandeInscriptions using partial update
        CommandeInscriptions partialUpdatedCommandeInscriptions = new CommandeInscriptions();
        partialUpdatedCommandeInscriptions.setId(commandeInscriptions.getId());

        partialUpdatedCommandeInscriptions
            .numero(UPDATED_NUMERO)
            .totalAvantRemise(UPDATED_TOTAL_AVANT_REMISE)
            .totalRemise(UPDATED_TOTAL_REMISE)
            .status(UPDATED_STATUS);

        restCommandeInscriptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommandeInscriptions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommandeInscriptions))
            )
            .andExpect(status().isOk());

        // Validate the CommandeInscriptions in the database
        List<CommandeInscriptions> commandeInscriptionsList = commandeInscriptionsRepository.findAll();
        assertThat(commandeInscriptionsList).hasSize(databaseSizeBeforeUpdate);
        CommandeInscriptions testCommandeInscriptions = commandeInscriptionsList.get(commandeInscriptionsList.size() - 1);
        assertThat(testCommandeInscriptions.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCommandeInscriptions.getTotalAvantRemise()).isEqualTo(UPDATED_TOTAL_AVANT_REMISE);
        assertThat(testCommandeInscriptions.getTotalRemise()).isEqualTo(UPDATED_TOTAL_REMISE);
        assertThat(testCommandeInscriptions.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingCommandeInscriptions() throws Exception {
        int databaseSizeBeforeUpdate = commandeInscriptionsRepository.findAll().size();
        commandeInscriptions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandeInscriptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commandeInscriptions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commandeInscriptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommandeInscriptions in the database
        List<CommandeInscriptions> commandeInscriptionsList = commandeInscriptionsRepository.findAll();
        assertThat(commandeInscriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommandeInscriptions() throws Exception {
        int databaseSizeBeforeUpdate = commandeInscriptionsRepository.findAll().size();
        commandeInscriptions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommandeInscriptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commandeInscriptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommandeInscriptions in the database
        List<CommandeInscriptions> commandeInscriptionsList = commandeInscriptionsRepository.findAll();
        assertThat(commandeInscriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommandeInscriptions() throws Exception {
        int databaseSizeBeforeUpdate = commandeInscriptionsRepository.findAll().size();
        commandeInscriptions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommandeInscriptionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commandeInscriptions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommandeInscriptions in the database
        List<CommandeInscriptions> commandeInscriptionsList = commandeInscriptionsRepository.findAll();
        assertThat(commandeInscriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommandeInscriptions() throws Exception {
        // Initialize the database
        commandeInscriptionsRepository.saveAndFlush(commandeInscriptions);

        int databaseSizeBeforeDelete = commandeInscriptionsRepository.findAll().size();

        // Delete the commandeInscriptions
        restCommandeInscriptionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, commandeInscriptions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommandeInscriptions> commandeInscriptionsList = commandeInscriptionsRepository.findAll();
        assertThat(commandeInscriptionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
