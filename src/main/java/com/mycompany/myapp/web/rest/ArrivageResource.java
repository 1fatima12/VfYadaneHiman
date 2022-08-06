package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ArrivageRepository;
import com.mycompany.myapp.service.ArrivageService;
import com.mycompany.myapp.service.dto.ArrivageDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Arrivage}.
 */
@RestController
@RequestMapping("/api")
public class ArrivageResource {

    private final Logger log = LoggerFactory.getLogger(ArrivageResource.class);

    private static final String ENTITY_NAME = "arrivage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArrivageService arrivageService;

    private final ArrivageRepository arrivageRepository;

    public ArrivageResource(ArrivageService arrivageService, ArrivageRepository arrivageRepository) {
        this.arrivageService = arrivageService;
        this.arrivageRepository = arrivageRepository;
    }

    /**
     * {@code POST  /arrivages} : Create a new arrivage.
     *
     * @param arrivageDTO the arrivageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new arrivageDTO, or with status {@code 400 (Bad Request)} if the arrivage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/arrivages")
    public ResponseEntity<ArrivageDTO> createArrivage(@RequestBody ArrivageDTO arrivageDTO) throws URISyntaxException {
        log.debug("REST request to save Arrivage : {}", arrivageDTO);
        if (arrivageDTO.getId() != null) {
            throw new BadRequestAlertException("A new arrivage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArrivageDTO result = arrivageService.save(arrivageDTO);
        return ResponseEntity
            .created(new URI("/api/arrivages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /arrivages/:id} : Updates an existing arrivage.
     *
     * @param id the id of the arrivageDTO to save.
     * @param arrivageDTO the arrivageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arrivageDTO,
     * or with status {@code 400 (Bad Request)} if the arrivageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the arrivageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/arrivages/{id}")
    public ResponseEntity<ArrivageDTO> updateArrivage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArrivageDTO arrivageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Arrivage : {}, {}", id, arrivageDTO);
        if (arrivageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, arrivageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!arrivageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArrivageDTO result = arrivageService.update(arrivageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, arrivageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /arrivages/:id} : Partial updates given fields of an existing arrivage, field will ignore if it is null
     *
     * @param id the id of the arrivageDTO to save.
     * @param arrivageDTO the arrivageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arrivageDTO,
     * or with status {@code 400 (Bad Request)} if the arrivageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the arrivageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the arrivageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/arrivages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArrivageDTO> partialUpdateArrivage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArrivageDTO arrivageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Arrivage partially : {}, {}", id, arrivageDTO);
        if (arrivageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, arrivageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!arrivageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArrivageDTO> result = arrivageService.partialUpdate(arrivageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, arrivageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /arrivages} : get all the arrivages.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of arrivages in body.
     */
    @GetMapping("/arrivages")
    public ResponseEntity<List<ArrivageDTO>> getAllArrivages(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("facture-is-null".equals(filter)) {
            log.debug("REST request to get all Arrivages where facture is null");
            return new ResponseEntity<>(arrivageService.findAllWhereFactureIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Arrivages");
        Page<ArrivageDTO> page = arrivageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /arrivages/:id} : get the "id" arrivage.
     *
     * @param id the id of the arrivageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the arrivageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/arrivages/{id}")
    public ResponseEntity<ArrivageDTO> getArrivage(@PathVariable Long id) {
        log.debug("REST request to get Arrivage : {}", id);
        Optional<ArrivageDTO> arrivageDTO = arrivageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(arrivageDTO);
    }

    /**
     * {@code DELETE  /arrivages/:id} : delete the "id" arrivage.
     *
     * @param id the id of the arrivageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/arrivages/{id}")
    public ResponseEntity<Void> deleteArrivage(@PathVariable Long id) {
        log.debug("REST request to delete Arrivage : {}", id);
        arrivageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
