package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Remise;
import com.mycompany.myapp.repository.RemiseRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Remise}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RemiseResource {

    private final Logger log = LoggerFactory.getLogger(RemiseResource.class);

    private static final String ENTITY_NAME = "remise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RemiseRepository remiseRepository;

    public RemiseResource(RemiseRepository remiseRepository) {
        this.remiseRepository = remiseRepository;
    }

    /**
     * {@code POST  /remises} : Create a new remise.
     *
     * @param remise the remise to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new remise, or with status {@code 400 (Bad Request)} if the remise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/remises")
    public ResponseEntity<Remise> createRemise(@RequestBody Remise remise) throws URISyntaxException {
        log.debug("REST request to save Remise : {}", remise);
        if (remise.getId() != null) {
            throw new BadRequestAlertException("A new remise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Remise result = remiseRepository.save(remise);
        return ResponseEntity
            .created(new URI("/api/remises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /remises/:id} : Updates an existing remise.
     *
     * @param id the id of the remise to save.
     * @param remise the remise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remise,
     * or with status {@code 400 (Bad Request)} if the remise is not valid,
     * or with status {@code 500 (Internal Server Error)} if the remise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/remises/{id}")
    public ResponseEntity<Remise> updateRemise(@PathVariable(value = "id", required = false) final Long id, @RequestBody Remise remise)
        throws URISyntaxException {
        log.debug("REST request to update Remise : {}, {}", id, remise);
        if (remise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, remise.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!remiseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Remise result = remiseRepository.save(remise);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, remise.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /remises/:id} : Partial updates given fields of an existing remise, field will ignore if it is null
     *
     * @param id the id of the remise to save.
     * @param remise the remise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remise,
     * or with status {@code 400 (Bad Request)} if the remise is not valid,
     * or with status {@code 404 (Not Found)} if the remise is not found,
     * or with status {@code 500 (Internal Server Error)} if the remise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/remises/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Remise> partialUpdateRemise(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Remise remise
    ) throws URISyntaxException {
        log.debug("REST request to partial update Remise partially : {}, {}", id, remise);
        if (remise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, remise.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!remiseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Remise> result = remiseRepository
            .findById(remise.getId())
            .map(
                existingRemise -> {
                    if (remise.getNumero() != null) {
                        existingRemise.setNumero(remise.getNumero());
                    }
                    if (remise.getMontant() != null) {
                        existingRemise.setMontant(remise.getMontant());
                    }
                    if (remise.getDescreption() != null) {
                        existingRemise.setDescreption(remise.getDescreption());
                    }

                    return existingRemise;
                }
            )
            .map(remiseRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, remise.getId().toString())
        );
    }

    /**
     * {@code GET  /remises} : get all the remises.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of remises in body.
     */
    @GetMapping("/remises")
    public List<Remise> getAllRemises() {
        log.debug("REST request to get all Remises");
        return remiseRepository.findAll();
    }

    /**
     * {@code GET  /remises/:id} : get the "id" remise.
     *
     * @param id the id of the remise to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the remise, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/remises/{id}")
    public ResponseEntity<Remise> getRemise(@PathVariable Long id) {
        log.debug("REST request to get Remise : {}", id);
        Optional<Remise> remise = remiseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(remise);
    }

    /**
     * {@code DELETE  /remises/:id} : delete the "id" remise.
     *
     * @param id the id of the remise to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/remises/{id}")
    public ResponseEntity<Void> deleteRemise(@PathVariable Long id) {
        log.debug("REST request to delete Remise : {}", id);
        remiseRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
