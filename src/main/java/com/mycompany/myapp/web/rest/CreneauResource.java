package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Creneau;
import com.mycompany.myapp.repository.CreneauRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Creneau}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CreneauResource {

    private final Logger log = LoggerFactory.getLogger(CreneauResource.class);

    private static final String ENTITY_NAME = "creneau";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreneauRepository creneauRepository;

    public CreneauResource(CreneauRepository creneauRepository) {
        this.creneauRepository = creneauRepository;
    }

    /**
     * {@code POST  /creneaus} : Create a new creneau.
     *
     * @param creneau the creneau to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new creneau, or with status {@code 400 (Bad Request)} if the creneau has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/creneaus")
    public ResponseEntity<Creneau> createCreneau(@Valid @RequestBody Creneau creneau) throws URISyntaxException {
        log.debug("REST request to save Creneau : {}", creneau);
        if (creneau.getId() != null) {
            throw new BadRequestAlertException("A new creneau cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Creneau result = creneauRepository.save(creneau);
        return ResponseEntity
            .created(new URI("/api/creneaus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /creneaus/:id} : Updates an existing creneau.
     *
     * @param id the id of the creneau to save.
     * @param creneau the creneau to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creneau,
     * or with status {@code 400 (Bad Request)} if the creneau is not valid,
     * or with status {@code 500 (Internal Server Error)} if the creneau couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/creneaus/{id}")
    public ResponseEntity<Creneau> updateCreneau(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Creneau creneau
    ) throws URISyntaxException {
        log.debug("REST request to update Creneau : {}, {}", id, creneau);
        if (creneau.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creneau.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creneauRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Creneau result = creneauRepository.save(creneau);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creneau.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /creneaus/:id} : Partial updates given fields of an existing creneau, field will ignore if it is null
     *
     * @param id the id of the creneau to save.
     * @param creneau the creneau to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creneau,
     * or with status {@code 400 (Bad Request)} if the creneau is not valid,
     * or with status {@code 404 (Not Found)} if the creneau is not found,
     * or with status {@code 500 (Internal Server Error)} if the creneau couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/creneaus/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Creneau> partialUpdateCreneau(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Creneau creneau
    ) throws URISyntaxException {
        log.debug("REST request to partial update Creneau partially : {}, {}", id, creneau);
        if (creneau.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creneau.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creneauRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Creneau> result = creneauRepository
            .findById(creneau.getId())
            .map(
                existingCreneau -> {
                    if (creneau.getTypeCreneau() != null) {
                        existingCreneau.setTypeCreneau(creneau.getTypeCreneau());
                    }
                    if (creneau.getJour() != null) {
                        existingCreneau.setJour(creneau.getJour());
                    }
                    if (creneau.getDeb() != null) {
                        existingCreneau.setDeb(creneau.getDeb());
                    }
                    if (creneau.getFin() != null) {
                        existingCreneau.setFin(creneau.getFin());
                    }

                    return existingCreneau;
                }
            )
            .map(creneauRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creneau.getId().toString())
        );
    }

    /**
     * {@code GET  /creneaus} : get all the creneaus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of creneaus in body.
     */
    @GetMapping("/creneaus")
    public List<Creneau> getAllCreneaus() {
        log.debug("REST request to get all Creneaus");
        return creneauRepository.findAll();
    }

    /**
     * {@code GET  /creneaus/:id} : get the "id" creneau.
     *
     * @param id the id of the creneau to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the creneau, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/creneaus/{id}")
    public ResponseEntity<Creneau> getCreneau(@PathVariable Long id) {
        log.debug("REST request to get Creneau : {}", id);
        Optional<Creneau> creneau = creneauRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(creneau);
    }

    /**
     * {@code DELETE  /creneaus/:id} : delete the "id" creneau.
     *
     * @param id the id of the creneau to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/creneaus/{id}")
    public ResponseEntity<Void> deleteCreneau(@PathVariable Long id) {
        log.debug("REST request to delete Creneau : {}", id);
        creneauRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
