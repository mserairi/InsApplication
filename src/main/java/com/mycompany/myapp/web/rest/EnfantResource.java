package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Enfant;
import com.mycompany.myapp.repository.EnfantRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Enfant}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EnfantResource {

    private final Logger log = LoggerFactory.getLogger(EnfantResource.class);

    private static final String ENTITY_NAME = "enfant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnfantRepository enfantRepository;

    public EnfantResource(EnfantRepository enfantRepository) {
        this.enfantRepository = enfantRepository;
    }

    /**
     * {@code POST  /enfants} : Create a new enfant.
     *
     * @param enfant the enfant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enfant, or with status {@code 400 (Bad Request)} if the enfant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enfants")
    public ResponseEntity<Enfant> createEnfant(@Valid @RequestBody Enfant enfant) throws URISyntaxException {
        log.debug("REST request to save Enfant : {}", enfant);
        if (enfant.getId() != null) {
            throw new BadRequestAlertException("A new enfant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Enfant result = enfantRepository.save(enfant);
        return ResponseEntity
            .created(new URI("/api/enfants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enfants/:id} : Updates an existing enfant.
     *
     * @param id the id of the enfant to save.
     * @param enfant the enfant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enfant,
     * or with status {@code 400 (Bad Request)} if the enfant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enfant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enfants/{id}")
    public ResponseEntity<Enfant> updateEnfant(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Enfant enfant
    ) throws URISyntaxException {
        log.debug("REST request to update Enfant : {}, {}", id, enfant);
        if (enfant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enfant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enfantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Enfant result = enfantRepository.save(enfant);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enfant.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /enfants/:id} : Partial updates given fields of an existing enfant, field will ignore if it is null
     *
     * @param id the id of the enfant to save.
     * @param enfant the enfant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enfant,
     * or with status {@code 400 (Bad Request)} if the enfant is not valid,
     * or with status {@code 404 (Not Found)} if the enfant is not found,
     * or with status {@code 500 (Internal Server Error)} if the enfant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/enfants/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Enfant> partialUpdateEnfant(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Enfant enfant
    ) throws URISyntaxException {
        log.debug("REST request to partial update Enfant partially : {}, {}", id, enfant);
        if (enfant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enfant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enfantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Enfant> result = enfantRepository
            .findById(enfant.getId())
            .map(
                existingEnfant -> {
                    if (enfant.getNom() != null) {
                        existingEnfant.setNom(enfant.getNom());
                    }
                    if (enfant.getPrenom() != null) {
                        existingEnfant.setPrenom(enfant.getPrenom());
                    }
                    if (enfant.getDateNaissance() != null) {
                        existingEnfant.setDateNaissance(enfant.getDateNaissance());
                    }
                    if (enfant.getGenre() != null) {
                        existingEnfant.setGenre(enfant.getGenre());
                    }
                    if (enfant.getNomParent2() != null) {
                        existingEnfant.setNomParent2(enfant.getNomParent2());
                    }
                    if (enfant.getPrenomParent2() != null) {
                        existingEnfant.setPrenomParent2(enfant.getPrenomParent2());
                    }
                    if (enfant.getMobParent2() != null) {
                        existingEnfant.setMobParent2(enfant.getMobParent2());
                    }
                    if (enfant.getEmailParent2() != null) {
                        existingEnfant.setEmailParent2(enfant.getEmailParent2());
                    }
                    if (enfant.getInfoSante() != null) {
                        existingEnfant.setInfoSante(enfant.getInfoSante());
                    }
                    if (enfant.getAutorisationImage() != null) {
                        existingEnfant.setAutorisationImage(enfant.getAutorisationImage());
                    }
                    if (enfant.getNomContact() != null) {
                        existingEnfant.setNomContact(enfant.getNomContact());
                    }
                    if (enfant.getMobContact() != null) {
                        existingEnfant.setMobContact(enfant.getMobContact());
                    }

                    return existingEnfant;
                }
            )
            .map(enfantRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enfant.getId().toString())
        );
    }

    /**
     * {@code GET  /enfants} : get all the enfants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enfants in body.
     */
    @GetMapping("/enfants")
    public List<Enfant> getAllEnfants() {
        log.debug("REST request to get all Enfants");
        return enfantRepository.findAll();
    }

    /**
     * {@code GET  /enfants/:id} : get the "id" enfant.
     *
     * @param id the id of the enfant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enfant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enfants/{id}")
    public ResponseEntity<Enfant> getEnfant(@PathVariable Long id) {
        log.debug("REST request to get Enfant : {}", id);
        Optional<Enfant> enfant = enfantRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(enfant);
    }

    /**
     * {@code DELETE  /enfants/:id} : delete the "id" enfant.
     *
     * @param id the id of the enfant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enfants/{id}")
    public ResponseEntity<Void> deleteEnfant(@PathVariable Long id) {
        log.debug("REST request to delete Enfant : {}", id);
        enfantRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
