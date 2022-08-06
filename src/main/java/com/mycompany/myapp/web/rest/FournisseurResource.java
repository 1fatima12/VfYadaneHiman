package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.FournisseurRepository;
import com.mycompany.myapp.service.FournisseurService;
import com.mycompany.myapp.service.dto.FournisseurDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Fournisseur}.
 */
@RestController
@RequestMapping("/api")
public class FournisseurResource {

    private final Logger log = LoggerFactory.getLogger(FournisseurResource.class);

    private static final String ENTITY_NAME = "fournisseur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FournisseurService fournisseurService;

    private final FournisseurRepository fournisseurRepository;

    public FournisseurResource(FournisseurService fournisseurService, FournisseurRepository fournisseurRepository) {
        this.fournisseurService = fournisseurService;
        this.fournisseurRepository = fournisseurRepository;
    }

    /**
     * {@code POST  /fournisseurs} : Create a new fournisseur.
     *
     * @param fournisseurDTO the fournisseurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fournisseurDTO, or with status {@code 400 (Bad Request)} if the fournisseur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fournisseurs")
    public ResponseEntity<FournisseurDTO> createFournisseur(@RequestBody FournisseurDTO fournisseurDTO) throws URISyntaxException {
        log.debug("REST request to save Fournisseur : {}", fournisseurDTO);
        if (fournisseurDTO.getId() != null) {
            throw new BadRequestAlertException("A new fournisseur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FournisseurDTO result = fournisseurService.save(fournisseurDTO);
        return ResponseEntity
            .created(new URI("/api/fournisseurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fournisseurs/:id} : Updates an existing fournisseur.
     *
     * @param id the id of the fournisseurDTO to save.
     * @param fournisseurDTO the fournisseurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fournisseurDTO,
     * or with status {@code 400 (Bad Request)} if the fournisseurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fournisseurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fournisseurs/{id}")
    public ResponseEntity<FournisseurDTO> updateFournisseur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FournisseurDTO fournisseurDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Fournisseur : {}, {}", id, fournisseurDTO);
        if (fournisseurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fournisseurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fournisseurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FournisseurDTO result = fournisseurService.update(fournisseurDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fournisseurDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fournisseurs/:id} : Partial updates given fields of an existing fournisseur, field will ignore if it is null
     *
     * @param id the id of the fournisseurDTO to save.
     * @param fournisseurDTO the fournisseurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fournisseurDTO,
     * or with status {@code 400 (Bad Request)} if the fournisseurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fournisseurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fournisseurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fournisseurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FournisseurDTO> partialUpdateFournisseur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FournisseurDTO fournisseurDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Fournisseur partially : {}, {}", id, fournisseurDTO);
        if (fournisseurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fournisseurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fournisseurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FournisseurDTO> result = fournisseurService.partialUpdate(fournisseurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fournisseurDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fournisseurs} : get all the fournisseurs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fournisseurs in body.
     */
    @GetMapping("/fournisseurs")
    public ResponseEntity<List<FournisseurDTO>> getAllFournisseurs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Fournisseurs");
        Page<FournisseurDTO> page = fournisseurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fournisseurs/:id} : get the "id" fournisseur.
     *
     * @param id the id of the fournisseurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fournisseurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fournisseurs/{id}")
    public ResponseEntity<FournisseurDTO> getFournisseur(@PathVariable Long id) {
        log.debug("REST request to get Fournisseur : {}", id);
        Optional<FournisseurDTO> fournisseurDTO = fournisseurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fournisseurDTO);
    }

    /**
     * {@code DELETE  /fournisseurs/:id} : delete the "id" fournisseur.
     *
     * @param id the id of the fournisseurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fournisseurs/{id}")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable Long id) {
        log.debug("REST request to delete Fournisseur : {}", id);
        fournisseurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
