package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.BonCommande;
import com.mycompany.myapp.repository.BonCommandeRepository;
import com.mycompany.myapp.service.BonCommandeService;
import com.mycompany.myapp.service.dto.BonCommandeDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.BonCommande}.
 */
@RestController
@RequestMapping("/api")
public class BonCommandeResource {

    private final Logger log = LoggerFactory.getLogger(BonCommandeResource.class);

    private static final String ENTITY_NAME = "bonCommande";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BonCommandeService bonCommandeService;

    private final BonCommandeRepository bonCommandeRepository;

    public BonCommandeResource(BonCommandeService bonCommandeService, BonCommandeRepository bonCommandeRepository) {
        this.bonCommandeService = bonCommandeService;
        this.bonCommandeRepository = bonCommandeRepository;
    }

    /**
     * {@code POST  /bon-commandes} : Create a new bonCommande.
     *
     * @param bonCommandeDTO the bonCommandeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bonCommandeDTO, or with status {@code 400 (Bad Request)} if the bonCommande has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bon-commandes")
    public ResponseEntity<BonCommandeDTO> createBonCommande(@RequestBody BonCommandeDTO bonCommandeDTO) throws URISyntaxException {
        log.debug("REST request to save BonCommande : {}", bonCommandeDTO);
        if (bonCommandeDTO.getId() != null) {
            throw new BadRequestAlertException("A new bonCommande cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BonCommandeDTO result = bonCommandeService.save(bonCommandeDTO);
        return ResponseEntity
            .created(new URI("/api/bon-commandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    @PostMapping("/bon-commandes/save")
   public BonCommande saveBC(@RequestBody BonCommande bc) {
	   return bonCommandeRepository.save(bc);
   }
    /**
     * {@code PUT  /bon-commandes/:id} : Updates an existing bonCommande.
     *
     * @param id the id of the bonCommandeDTO to save.
     * @param bonCommandeDTO the bonCommandeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonCommandeDTO,
     * or with status {@code 400 (Bad Request)} if the bonCommandeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bonCommandeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bon-commandes/{id}")
    public ResponseEntity<BonCommandeDTO> updateBonCommande(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BonCommandeDTO bonCommandeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BonCommande : {}, {}", id, bonCommandeDTO);
        if (bonCommandeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bonCommandeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bonCommandeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BonCommandeDTO result = bonCommandeService.update(bonCommandeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bonCommandeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bon-commandes/:id} : Partial updates given fields of an existing bonCommande, field will ignore if it is null
     *
     * @param id the id of the bonCommandeDTO to save.
     * @param bonCommandeDTO the bonCommandeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonCommandeDTO,
     * or with status {@code 400 (Bad Request)} if the bonCommandeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bonCommandeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bonCommandeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bon-commandes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BonCommandeDTO> partialUpdateBonCommande(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BonCommandeDTO bonCommandeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BonCommande partially : {}, {}", id, bonCommandeDTO);
        if (bonCommandeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bonCommandeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bonCommandeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BonCommandeDTO> result = bonCommandeService.partialUpdate(bonCommandeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bonCommandeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bon-commandes} : get all the bonCommandes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bonCommandes in body.
     */
    @GetMapping("/bon-commandes")
    public ResponseEntity<List<BonCommandeDTO>> getAllBonCommandes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BonCommandes");
        Page<BonCommandeDTO> page = bonCommandeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bon-commandes/:id} : get the "id" bonCommande.
     *
     * @param id the id of the bonCommandeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bonCommandeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bon-commandes/{id}")
    public ResponseEntity<BonCommandeDTO> getBonCommande(@PathVariable Long id) {
        log.debug("REST request to get BonCommande : {}", id);
        Optional<BonCommandeDTO> bonCommandeDTO = bonCommandeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bonCommandeDTO);
    }

    /**
     * {@code DELETE  /bon-commandes/:id} : delete the "id" bonCommande.
     *
     * @param id the id of the bonCommandeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bon-commandes/{id}")
    public ResponseEntity<Void> deleteBonCommande(@PathVariable Long id) {
        log.debug("REST request to delete BonCommande : {}", id);
        bonCommandeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
