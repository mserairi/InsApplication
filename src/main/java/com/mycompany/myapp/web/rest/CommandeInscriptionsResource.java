package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CommandeInscriptions;
import com.mycompany.myapp.repository.CommandeInscriptionsRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.CommandeInscriptions}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CommandeInscriptionsResource {

    private final Logger log = LoggerFactory.getLogger(CommandeInscriptionsResource.class);

    private static final String ENTITY_NAME = "commandeInscriptions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommandeInscriptionsRepository commandeInscriptionsRepository;

    public CommandeInscriptionsResource(CommandeInscriptionsRepository commandeInscriptionsRepository) {
        this.commandeInscriptionsRepository = commandeInscriptionsRepository;
    }

    /**
     * {@code POST  /commande-inscriptions} : Create a new commandeInscriptions.
     *
     * @param commandeInscriptions the commandeInscriptions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commandeInscriptions, or with status {@code 400 (Bad Request)} if the commandeInscriptions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commande-inscriptions")
    public ResponseEntity<CommandeInscriptions> createCommandeInscriptions(@RequestBody CommandeInscriptions commandeInscriptions)
        throws URISyntaxException {
        log.debug("REST request to save CommandeInscriptions : {}", commandeInscriptions);
        if (commandeInscriptions.getId() != null) {
            throw new BadRequestAlertException("A new commandeInscriptions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommandeInscriptions result = commandeInscriptionsRepository.save(commandeInscriptions);
        return ResponseEntity
            .created(new URI("/api/commande-inscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commande-inscriptions/:id} : Updates an existing commandeInscriptions.
     *
     * @param id the id of the commandeInscriptions to save.
     * @param commandeInscriptions the commandeInscriptions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandeInscriptions,
     * or with status {@code 400 (Bad Request)} if the commandeInscriptions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commandeInscriptions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commande-inscriptions/{id}")
    public ResponseEntity<CommandeInscriptions> updateCommandeInscriptions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommandeInscriptions commandeInscriptions
    ) throws URISyntaxException {
        log.debug("REST request to update CommandeInscriptions : {}, {}", id, commandeInscriptions);
        if (commandeInscriptions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commandeInscriptions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commandeInscriptionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommandeInscriptions result = commandeInscriptionsRepository.save(commandeInscriptions);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commandeInscriptions.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /commande-inscriptions/:id} : Partial updates given fields of an existing commandeInscriptions, field will ignore if it is null
     *
     * @param id the id of the commandeInscriptions to save.
     * @param commandeInscriptions the commandeInscriptions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandeInscriptions,
     * or with status {@code 400 (Bad Request)} if the commandeInscriptions is not valid,
     * or with status {@code 404 (Not Found)} if the commandeInscriptions is not found,
     * or with status {@code 500 (Internal Server Error)} if the commandeInscriptions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/commande-inscriptions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CommandeInscriptions> partialUpdateCommandeInscriptions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommandeInscriptions commandeInscriptions
    ) throws URISyntaxException {
        log.debug("REST request to partial update CommandeInscriptions partially : {}, {}", id, commandeInscriptions);
        if (commandeInscriptions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commandeInscriptions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commandeInscriptionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommandeInscriptions> result = commandeInscriptionsRepository
            .findById(commandeInscriptions.getId())
            .map(
                existingCommandeInscriptions -> {
                    if (commandeInscriptions.getNumero() != null) {
                        existingCommandeInscriptions.setNumero(commandeInscriptions.getNumero());
                    }
                    if (commandeInscriptions.getTotalAvantRemise() != null) {
                        existingCommandeInscriptions.setTotalAvantRemise(commandeInscriptions.getTotalAvantRemise());
                    }
                    if (commandeInscriptions.getTotalRemise() != null) {
                        existingCommandeInscriptions.setTotalRemise(commandeInscriptions.getTotalRemise());
                    }
                    if (commandeInscriptions.getStatus() != null) {
                        existingCommandeInscriptions.setStatus(commandeInscriptions.getStatus());
                    }

                    return existingCommandeInscriptions;
                }
            )
            .map(commandeInscriptionsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commandeInscriptions.getId().toString())
        );
    }

    /**
     * {@code GET  /commande-inscriptions} : get all the commandeInscriptions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commandeInscriptions in body.
     */
    @GetMapping("/commande-inscriptions")
    public List<CommandeInscriptions> getAllCommandeInscriptions() {
        log.debug("REST request to get all CommandeInscriptions");
        return commandeInscriptionsRepository.findAll();
    }

    /**
     * {@code GET  /commande-inscriptions/:id} : get the "id" commandeInscriptions.
     *
     * @param id the id of the commandeInscriptions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commandeInscriptions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commande-inscriptions/{id}")
    public ResponseEntity<CommandeInscriptions> getCommandeInscriptions(@PathVariable Long id) {
        log.debug("REST request to get CommandeInscriptions : {}", id);
        Optional<CommandeInscriptions> commandeInscriptions = commandeInscriptionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(commandeInscriptions);
    }

    /**
     * {@code DELETE  /commande-inscriptions/:id} : delete the "id" commandeInscriptions.
     *
     * @param id the id of the commandeInscriptions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commande-inscriptions/{id}")
    public ResponseEntity<Void> deleteCommandeInscriptions(@PathVariable Long id) {
        log.debug("REST request to delete CommandeInscriptions : {}", id);
        commandeInscriptionsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
