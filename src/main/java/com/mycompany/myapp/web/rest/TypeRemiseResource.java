package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.TypeRemise;
import com.mycompany.myapp.repository.TypeRemiseRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.TypeRemise}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TypeRemiseResource {

    private final Logger log = LoggerFactory.getLogger(TypeRemiseResource.class);

    private static final String ENTITY_NAME = "typeRemise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeRemiseRepository typeRemiseRepository;

    public TypeRemiseResource(TypeRemiseRepository typeRemiseRepository) {
        this.typeRemiseRepository = typeRemiseRepository;
    }

    /**
     * {@code POST  /type-remises} : Create a new typeRemise.
     *
     * @param typeRemise the typeRemise to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeRemise, or with status {@code 400 (Bad Request)} if the typeRemise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-remises")
    public ResponseEntity<TypeRemise> createTypeRemise(@RequestBody TypeRemise typeRemise) throws URISyntaxException {
        log.debug("REST request to save TypeRemise : {}", typeRemise);
        if (typeRemise.getId() != null) {
            throw new BadRequestAlertException("A new typeRemise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeRemise result = typeRemiseRepository.save(typeRemise);
        return ResponseEntity
            .created(new URI("/api/type-remises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-remises/:id} : Updates an existing typeRemise.
     *
     * @param id the id of the typeRemise to save.
     * @param typeRemise the typeRemise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeRemise,
     * or with status {@code 400 (Bad Request)} if the typeRemise is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeRemise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-remises/{id}")
    public ResponseEntity<TypeRemise> updateTypeRemise(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeRemise typeRemise
    ) throws URISyntaxException {
        log.debug("REST request to update TypeRemise : {}, {}", id, typeRemise);
        if (typeRemise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeRemise.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeRemiseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypeRemise result = typeRemiseRepository.save(typeRemise);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeRemise.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /type-remises/:id} : Partial updates given fields of an existing typeRemise, field will ignore if it is null
     *
     * @param id the id of the typeRemise to save.
     * @param typeRemise the typeRemise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeRemise,
     * or with status {@code 400 (Bad Request)} if the typeRemise is not valid,
     * or with status {@code 404 (Not Found)} if the typeRemise is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeRemise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/type-remises/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TypeRemise> partialUpdateTypeRemise(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeRemise typeRemise
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeRemise partially : {}, {}", id, typeRemise);
        if (typeRemise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeRemise.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeRemiseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeRemise> result = typeRemiseRepository
            .findById(typeRemise.getId())
            .map(
                existingTypeRemise -> {
                    if (typeRemise.getNumero() != null) {
                        existingTypeRemise.setNumero(typeRemise.getNumero());
                    }
                    if (typeRemise.getLibille() != null) {
                        existingTypeRemise.setLibille(typeRemise.getLibille());
                    }
                    if (typeRemise.getCondition() != null) {
                        existingTypeRemise.setCondition(typeRemise.getCondition());
                    }
                    if (typeRemise.getMantantLibre() != null) {
                        existingTypeRemise.setMantantLibre(typeRemise.getMantantLibre());
                    }
                    if (typeRemise.getMontantUnitaire() != null) {
                        existingTypeRemise.setMontantUnitaire(typeRemise.getMontantUnitaire());
                    }

                    return existingTypeRemise;
                }
            )
            .map(typeRemiseRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeRemise.getId().toString())
        );
    }

    /**
     * {@code GET  /type-remises} : get all the typeRemises.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeRemises in body.
     */
    @GetMapping("/type-remises")
    public List<TypeRemise> getAllTypeRemises() {
        log.debug("REST request to get all TypeRemises");
        return typeRemiseRepository.findAll();
    }

    /**
     * {@code GET  /type-remises/:id} : get the "id" typeRemise.
     *
     * @param id the id of the typeRemise to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeRemise, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-remises/{id}")
    public ResponseEntity<TypeRemise> getTypeRemise(@PathVariable Long id) {
        log.debug("REST request to get TypeRemise : {}", id);
        Optional<TypeRemise> typeRemise = typeRemiseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeRemise);
    }

    /**
     * {@code DELETE  /type-remises/:id} : delete the "id" typeRemise.
     *
     * @param id the id of the typeRemise to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-remises/{id}")
    public ResponseEntity<Void> deleteTypeRemise(@PathVariable Long id) {
        log.debug("REST request to delete TypeRemise : {}", id);
        typeRemiseRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
