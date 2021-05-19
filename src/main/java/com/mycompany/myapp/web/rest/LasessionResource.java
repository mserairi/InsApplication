package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Lasession;
import com.mycompany.myapp.repository.LasessionRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Lasession}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LasessionResource {

    private final Logger log = LoggerFactory.getLogger(LasessionResource.class);

    private static final String ENTITY_NAME = "lasession";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LasessionRepository lasessionRepository;

    public LasessionResource(LasessionRepository lasessionRepository) {
        this.lasessionRepository = lasessionRepository;
    }

    /**
     * {@code POST  /lasessions} : Create a new lasession.
     *
     * @param lasession the lasession to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lasession, or with status {@code 400 (Bad Request)} if the lasession has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lasessions")
    public ResponseEntity<Lasession> createLasession(@Valid @RequestBody Lasession lasession) throws URISyntaxException {
        log.debug("REST request to save Lasession : {}", lasession);
        if (lasession.getId() != null) {
            throw new BadRequestAlertException("A new lasession cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Lasession result = lasessionRepository.save(lasession);
        return ResponseEntity
            .created(new URI("/api/lasessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lasessions/:id} : Updates an existing lasession.
     *
     * @param id the id of the lasession to save.
     * @param lasession the lasession to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lasession,
     * or with status {@code 400 (Bad Request)} if the lasession is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lasession couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lasessions/{id}")
    public ResponseEntity<Lasession> updateLasession(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Lasession lasession
    ) throws URISyntaxException {
        log.debug("REST request to update Lasession : {}, {}", id, lasession);
        if (lasession.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lasession.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lasessionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Lasession result = lasessionRepository.save(lasession);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lasession.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lasessions/:id} : Partial updates given fields of an existing lasession, field will ignore if it is null
     *
     * @param id the id of the lasession to save.
     * @param lasession the lasession to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lasession,
     * or with status {@code 400 (Bad Request)} if the lasession is not valid,
     * or with status {@code 404 (Not Found)} if the lasession is not found,
     * or with status {@code 500 (Internal Server Error)} if the lasession couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lasessions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Lasession> partialUpdateLasession(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Lasession lasession
    ) throws URISyntaxException {
        log.debug("REST request to partial update Lasession partially : {}, {}", id, lasession);
        if (lasession.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lasession.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lasessionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Lasession> result = lasessionRepository
            .findById(lasession.getId())
            .map(
                existingLasession -> {
                    if (lasession.getCode() != null) {
                        existingLasession.setCode(lasession.getCode());
                    }
                    if (lasession.getDescription() != null) {
                        existingLasession.setDescription(lasession.getDescription());
                    }
                    if (lasession.getTarif() != null) {
                        existingLasession.setTarif(lasession.getTarif());
                    }
                    if (lasession.getDebut() != null) {
                        existingLasession.setDebut(lasession.getDebut());
                    }
                    if (lasession.getFin() != null) {
                        existingLasession.setFin(lasession.getFin());
                    }

                    return existingLasession;
                }
            )
            .map(lasessionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lasession.getId().toString())
        );
    }

    /**
     * {@code GET  /lasessions} : get all the lasessions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lasessions in body.
     */
    @GetMapping("/lasessions")
    public List<Lasession> getAllLasessions() {
        log.debug("REST request to get all Lasessions");
        return lasessionRepository.findAll();
    }

    /**
     * {@code GET  /lasessions/:id} : get the "id" lasession.
     *
     * @param id the id of the lasession to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lasession, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lasessions/{id}")
    public ResponseEntity<Lasession> getLasession(@PathVariable Long id) {
        log.debug("REST request to get Lasession : {}", id);
        Optional<Lasession> lasession = lasessionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lasession);
    }

    /**
     * {@code DELETE  /lasessions/:id} : delete the "id" lasession.
     *
     * @param id the id of the lasession to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lasessions/{id}")
    public ResponseEntity<Void> deleteLasession(@PathVariable Long id) {
        log.debug("REST request to delete Lasession : {}", id);
        lasessionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
