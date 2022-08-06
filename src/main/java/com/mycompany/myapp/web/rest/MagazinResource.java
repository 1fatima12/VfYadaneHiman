package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.MagazinRepository;
import com.mycompany.myapp.service.MagazinService;
import com.mycompany.myapp.service.dto.MagazinDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Magazin}.
 */
@RestController
@RequestMapping("/api")
public class MagazinResource {

    private final Logger log = LoggerFactory.getLogger(MagazinResource.class);

    private static final String ENTITY_NAME = "magazin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MagazinService magazinService;

    private final MagazinRepository magazinRepository;

    public MagazinResource(MagazinService magazinService, MagazinRepository magazinRepository) {
        this.magazinService = magazinService;
        this.magazinRepository = magazinRepository;
    }

    /**
     * {@code POST  /magazins} : Create a new magazin.
     *
     * @param magazinDTO the magazinDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new magazinDTO, or with status {@code 400 (Bad Request)} if the magazin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/magazins")
    public ResponseEntity<MagazinDTO> createMagazin(@RequestBody MagazinDTO magazinDTO) throws URISyntaxException {
        log.debug("REST request to save Magazin : {}", magazinDTO);
        if (magazinDTO.getId() != null) {
            throw new BadRequestAlertException("A new magazin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MagazinDTO result = magazinService.save(magazinDTO);
        return ResponseEntity
            .created(new URI("/api/magazins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /magazins/:id} : Updates an existing magazin.
     *
     * @param id the id of the magazinDTO to save.
     * @param magazinDTO the magazinDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated magazinDTO,
     * or with status {@code 400 (Bad Request)} if the magazinDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the magazinDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/magazins/{id}")
    public ResponseEntity<MagazinDTO> updateMagazin(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MagazinDTO magazinDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Magazin : {}, {}", id, magazinDTO);
        if (magazinDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, magazinDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!magazinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MagazinDTO result = magazinService.update(magazinDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, magazinDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /magazins/:id} : Partial updates given fields of an existing magazin, field will ignore if it is null
     *
     * @param id the id of the magazinDTO to save.
     * @param magazinDTO the magazinDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated magazinDTO,
     * or with status {@code 400 (Bad Request)} if the magazinDTO is not valid,
     * or with status {@code 404 (Not Found)} if the magazinDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the magazinDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/magazins/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MagazinDTO> partialUpdateMagazin(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MagazinDTO magazinDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Magazin partially : {}, {}", id, magazinDTO);
        if (magazinDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, magazinDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!magazinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MagazinDTO> result = magazinService.partialUpdate(magazinDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, magazinDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /magazins} : get all the magazins.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of magazins in body.
     */
    @GetMapping("/magazins")
    public ResponseEntity<List<MagazinDTO>> getAllMagazins(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Magazins");
        Page<MagazinDTO> page = magazinService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /magazins/:id} : get the "id" magazin.
     *
     * @param id the id of the magazinDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the magazinDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/magazins/{id}")
    public ResponseEntity<MagazinDTO> getMagazin(@PathVariable Long id) {
        log.debug("REST request to get Magazin : {}", id);
        Optional<MagazinDTO> magazinDTO = magazinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(magazinDTO);
    }

    /**
     * {@code DELETE  /magazins/:id} : delete the "id" magazin.
     *
     * @param id the id of the magazinDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/magazins/{id}")
    public ResponseEntity<Void> deleteMagazin(@PathVariable Long id) {
        log.debug("REST request to delete Magazin : {}", id);
        magazinService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
