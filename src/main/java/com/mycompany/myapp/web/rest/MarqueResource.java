package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Categorie;
import com.mycompany.myapp.domain.Marque;
import com.mycompany.myapp.repository.MarqueRepository;
import com.mycompany.myapp.service.MarqueService;
import com.mycompany.myapp.service.dto.MarqueDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Marque}.
 */
@RestController
@RequestMapping("/api")
public class MarqueResource {

    private final Logger log = LoggerFactory.getLogger(MarqueResource.class);

    private static final String ENTITY_NAME = "marque";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MarqueService marqueService;

    private final MarqueRepository marqueRepository;

    public MarqueResource(MarqueService marqueService, MarqueRepository marqueRepository) {
        this.marqueService = marqueService;
        this.marqueRepository = marqueRepository;
    }

    /**
     * {@code POST  /marques} : Create a new marque.
     *
     * @param marqueDTO the marqueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new marqueDTO, or with status {@code 400 (Bad Request)} if the marque has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/marques")
    public ResponseEntity<MarqueDTO> createMarque(@RequestBody MarqueDTO marqueDTO) throws URISyntaxException {
        log.debug("REST request to save Marque : {}", marqueDTO);
        if (marqueDTO.getId() != null) {
            throw new BadRequestAlertException("A new marque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarqueDTO result = marqueService.save(marqueDTO);
        return ResponseEntity
            .created(new URI("/api/marques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /marques/:id} : Updates an existing marque.
     *
     * @param id the id of the marqueDTO to save.
     * @param marqueDTO the marqueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marqueDTO,
     * or with status {@code 400 (Bad Request)} if the marqueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the marqueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/marques/{id}")
    public ResponseEntity<MarqueDTO> updateMarque(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MarqueDTO marqueDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Marque : {}, {}", id, marqueDTO);
        if (marqueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marqueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MarqueDTO result = marqueService.update(marqueDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, marqueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /marques/:id} : Partial updates given fields of an existing marque, field will ignore if it is null
     *
     * @param id the id of the marqueDTO to save.
     * @param marqueDTO the marqueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marqueDTO,
     * or with status {@code 400 (Bad Request)} if the marqueDTO is not valid,
     * or with status {@code 404 (Not Found)} if the marqueDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the marqueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/marques/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MarqueDTO> partialUpdateMarque(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MarqueDTO marqueDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Marque partially : {}, {}", id, marqueDTO);
        if (marqueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marqueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MarqueDTO> result = marqueService.partialUpdate(marqueDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, marqueDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /marques} : get all the marques.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of marques in body.
     */
    @GetMapping("/marques")
    public ResponseEntity<List<MarqueDTO>> getAllMarques(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Marques");
        Page<MarqueDTO> page = marqueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /marques/:id} : get the "id" marque.
     *
     * @param id the id of the marqueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marqueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/marques/{id}")
    public ResponseEntity<MarqueDTO> getMarque(@PathVariable Long id) {
        log.debug("REST request to get Marque : {}", id);
        Optional<MarqueDTO> marqueDTO = marqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marqueDTO);
    }

    /**
     * {@code DELETE  /marques/:id} : delete the "id" marque.
     *
     * @param id the id of the marqueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/marques/{id}")
    public ResponseEntity<Void> deleteMarque(@PathVariable Long id) {
        log.debug("REST request to delete Marque : {}", id);
        marqueService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
    @GetMapping("/marques/getAll")
    public List<Marque> getAll() {
    	return  marqueService.getAll();
    }
}
