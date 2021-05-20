package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.SousCategory;
import com.mycompany.myapp.repository.SousCategoryRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.SousCategory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SousCategoryResource {

    private final Logger log = LoggerFactory.getLogger(SousCategoryResource.class);

    private static final String ENTITY_NAME = "sousCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SousCategoryRepository sousCategoryRepository;

    public SousCategoryResource(SousCategoryRepository sousCategoryRepository) {
        this.sousCategoryRepository = sousCategoryRepository;
    }

    /**
     * {@code POST  /sous-categories} : Create a new sousCategory.
     *
     * @param sousCategory the sousCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sousCategory, or with status {@code 400 (Bad Request)} if the sousCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sous-categories")
    public ResponseEntity<SousCategory> createSousCategory(@Valid @RequestBody SousCategory sousCategory) throws URISyntaxException {
        log.debug("REST request to save SousCategory : {}", sousCategory);
        if (sousCategory.getId() != null) {
            throw new BadRequestAlertException("A new sousCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SousCategory result = sousCategoryRepository.save(sousCategory);
        return ResponseEntity
            .created(new URI("/api/sous-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sous-categories/:id} : Updates an existing sousCategory.
     *
     * @param id the id of the sousCategory to save.
     * @param sousCategory the sousCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sousCategory,
     * or with status {@code 400 (Bad Request)} if the sousCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sousCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sous-categories/{id}")
    public ResponseEntity<SousCategory> updateSousCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SousCategory sousCategory
    ) throws URISyntaxException {
        log.debug("REST request to update SousCategory : {}, {}", id, sousCategory);
        if (sousCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sousCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sousCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SousCategory result = sousCategoryRepository.save(sousCategory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sousCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sous-categories/:id} : Partial updates given fields of an existing sousCategory, field will ignore if it is null
     *
     * @param id the id of the sousCategory to save.
     * @param sousCategory the sousCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sousCategory,
     * or with status {@code 400 (Bad Request)} if the sousCategory is not valid,
     * or with status {@code 404 (Not Found)} if the sousCategory is not found,
     * or with status {@code 500 (Internal Server Error)} if the sousCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sous-categories/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SousCategory> partialUpdateSousCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SousCategory sousCategory
    ) throws URISyntaxException {
        log.debug("REST request to partial update SousCategory partially : {}, {}", id, sousCategory);
        if (sousCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sousCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sousCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SousCategory> result = sousCategoryRepository
            .findById(sousCategory.getId())
            .map(
                existingSousCategory -> {
                    if (sousCategory.getLibille() != null) {
                        existingSousCategory.setLibille(sousCategory.getLibille());
                    }
                    if (sousCategory.getDescription() != null) {
                        existingSousCategory.setDescription(sousCategory.getDescription());
                    }

                    return existingSousCategory;
                }
            )
            .map(sousCategoryRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sousCategory.getId().toString())
        );
    }

    /**
     * {@code GET  /sous-categories} : get all the sousCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sousCategories in body.
     */
    @GetMapping("/sous-categories")
    public List<SousCategory> getAllSousCategories() {
        log.debug("REST request to get all SousCategories");
        return sousCategoryRepository.findAll();
    }

    /**
     * {@code GET  /sous-categories/:id} : get the "id" sousCategory.
     *
     * @param id the id of the sousCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sousCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sous-categories/{id}")
    public ResponseEntity<SousCategory> getSousCategory(@PathVariable Long id) {
        log.debug("REST request to get SousCategory : {}", id);
        Optional<SousCategory> sousCategory = sousCategoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sousCategory);
    }

    /**
     * {@code DELETE  /sous-categories/:id} : delete the "id" sousCategory.
     *
     * @param id the id of the sousCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sous-categories/{id}")
    public ResponseEntity<Void> deleteSousCategory(@PathVariable Long id) {
        log.debug("REST request to delete SousCategory : {}", id);
        sousCategoryRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
