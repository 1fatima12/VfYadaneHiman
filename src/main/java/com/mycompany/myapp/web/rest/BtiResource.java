package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.BtiRepository;
import com.mycompany.myapp.service.BtiService;
import com.mycompany.myapp.service.dto.BtiDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Bti}.
 */
@RestController
@RequestMapping("/api")
public class BtiResource {

    private final Logger log = LoggerFactory.getLogger(BtiResource.class);

    private static final String ENTITY_NAME = "bti";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BtiService btiService;

    private final BtiRepository btiRepository;

    public BtiResource(BtiService btiService, BtiRepository btiRepository) {
        this.btiService = btiService;
        this.btiRepository = btiRepository;
    }

    /**
     * {@code POST  /btis} : Create a new bti.
     *
     * @param btiDTO the btiDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new btiDTO, or with status {@code 400 (Bad Request)} if the bti has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/btis")
    public ResponseEntity<BtiDTO> createBti(@RequestBody BtiDTO btiDTO) throws URISyntaxException {
        log.debug("REST request to save Bti : {}", btiDTO);
        if (btiDTO.getId() != null) {
            throw new BadRequestAlertException("A new bti cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BtiDTO result = btiService.save(btiDTO);
        return ResponseEntity
            .created(new URI("/api/btis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /btis/:id} : Updates an existing bti.
     *
     * @param id the id of the btiDTO to save.
     * @param btiDTO the btiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated btiDTO,
     * or with status {@code 400 (Bad Request)} if the btiDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the btiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/btis/{id}")
    public ResponseEntity<BtiDTO> updateBti(@PathVariable(value = "id", required = false) final Long id, @RequestBody BtiDTO btiDTO)
        throws URISyntaxException {
        log.debug("REST request to update Bti : {}, {}", id, btiDTO);
        if (btiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, btiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!btiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BtiDTO result = btiService.update(btiDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, btiDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /btis/:id} : Partial updates given fields of an existing bti, field will ignore if it is null
     *
     * @param id the id of the btiDTO to save.
     * @param btiDTO the btiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated btiDTO,
     * or with status {@code 400 (Bad Request)} if the btiDTO is not valid,
     * or with status {@code 404 (Not Found)} if the btiDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the btiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/btis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BtiDTO> partialUpdateBti(@PathVariable(value = "id", required = false) final Long id, @RequestBody BtiDTO btiDTO)
        throws URISyntaxException {
        log.debug("REST request to partial update Bti partially : {}, {}", id, btiDTO);
        if (btiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, btiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!btiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BtiDTO> result = btiService.partialUpdate(btiDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, btiDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /btis} : get all the btis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of btis in body.
     */
    @GetMapping("/btis")
    public ResponseEntity<List<BtiDTO>> getAllBtis(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Btis");
        Page<BtiDTO> page = btiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /btis/:id} : get the "id" bti.
     *
     * @param id the id of the btiDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the btiDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/btis/{id}")
    public ResponseEntity<BtiDTO> getBti(@PathVariable Long id) {
        log.debug("REST request to get Bti : {}", id);
        Optional<BtiDTO> btiDTO = btiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(btiDTO);
    }

    /**
     * {@code DELETE  /btis/:id} : delete the "id" bti.
     *
     * @param id the id of the btiDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/btis/{id}")
    public ResponseEntity<Void> deleteBti(@PathVariable Long id) {
        log.debug("REST request to delete Bti : {}", id);
        btiService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
