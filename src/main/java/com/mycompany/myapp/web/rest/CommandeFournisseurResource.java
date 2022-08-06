package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CommandeFournisseur;
import com.mycompany.myapp.repository.CommandeFournisseurRepository;
import com.mycompany.myapp.service.CommandeFournisseurService;
import com.mycompany.myapp.service.dto.CommandeFournisseurDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CommandeFournisseur}.
 */
@RestController
@RequestMapping("/api")
public class CommandeFournisseurResource {

    private final Logger log = LoggerFactory.getLogger(CommandeFournisseurResource.class);

    private static final String ENTITY_NAME = "commandeFournisseur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommandeFournisseurService commandeFournisseurService;

    private final CommandeFournisseurRepository commandeFournisseurRepository;

    public CommandeFournisseurResource(
        CommandeFournisseurService commandeFournisseurService,
        CommandeFournisseurRepository commandeFournisseurRepository
    ) {
        this.commandeFournisseurService = commandeFournisseurService;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
    }

    /**
     * {@code POST  /commande-fournisseurs} : Create a new commandeFournisseur.
     *
     * @param commandeFournisseurDTO the commandeFournisseurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commandeFournisseurDTO, or with status {@code 400 (Bad Request)} if the commandeFournisseur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commande-fournisseurs")
    public ResponseEntity<CommandeFournisseurDTO> createCommandeFournisseur(@RequestBody CommandeFournisseurDTO commandeFournisseurDTO)
        throws URISyntaxException {
        log.debug("REST request to save CommandeFournisseur : {}", commandeFournisseurDTO);
        if (commandeFournisseurDTO.getId() != null) {
            throw new BadRequestAlertException("A new commandeFournisseur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommandeFournisseurDTO result = commandeFournisseurService.save(commandeFournisseurDTO);
        return ResponseEntity
            .created(new URI("/api/commande-fournisseurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commande-fournisseurs/:id} : Updates an existing commandeFournisseur.
     *
     * @param id the id of the commandeFournisseurDTO to save.
     * @param commandeFournisseurDTO the commandeFournisseurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandeFournisseurDTO,
     * or with status {@code 400 (Bad Request)} if the commandeFournisseurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commandeFournisseurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commande-fournisseurs/{id}")
    public ResponseEntity<CommandeFournisseurDTO> updateCommandeFournisseur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommandeFournisseurDTO commandeFournisseurDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CommandeFournisseur : {}, {}", id, commandeFournisseurDTO);
        if (commandeFournisseurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commandeFournisseurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commandeFournisseurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommandeFournisseurDTO result = commandeFournisseurService.update(commandeFournisseurDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commandeFournisseurDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /commande-fournisseurs/:id} : Partial updates given fields of an existing commandeFournisseur, field will ignore if it is null
     *
     * @param id the id of the commandeFournisseurDTO to save.
     * @param commandeFournisseurDTO the commandeFournisseurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandeFournisseurDTO,
     * or with status {@code 400 (Bad Request)} if the commandeFournisseurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the commandeFournisseurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the commandeFournisseurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/commande-fournisseurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CommandeFournisseurDTO> partialUpdateCommandeFournisseur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommandeFournisseurDTO commandeFournisseurDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CommandeFournisseur partially : {}, {}", id, commandeFournisseurDTO);
        if (commandeFournisseurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commandeFournisseurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commandeFournisseurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommandeFournisseurDTO> result = commandeFournisseurService.partialUpdate(commandeFournisseurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commandeFournisseurDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /commande-fournisseurs} : get all the commandeFournisseurs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commandeFournisseurs in body.
     */
    @GetMapping("/commande-fournisseurs")
    public ResponseEntity<List<CommandeFournisseurDTO>> getAllCommandeFournisseurs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CommandeFournisseurs");
        Page<CommandeFournisseurDTO> page = commandeFournisseurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    } 
   

    /**
     * {@code GET  /commande-fournisseurs/:id} : get the "id" commandeFournisseur.
     *
     * @param id the id of the commandeFournisseurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commandeFournisseurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commande-fournisseurs/{id}")
    public ResponseEntity<CommandeFournisseurDTO> getCommandeFournisseur(@PathVariable Long id) {
        log.debug("REST request to get CommandeFournisseur : {}", id);
        Optional<CommandeFournisseurDTO> commandeFournisseurDTO = commandeFournisseurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commandeFournisseurDTO);
    }

    /**
     * {@code DELETE  /commande-fournisseurs/:id} : delete the "id" commandeFournisseur.
     *
     * @param id the id of the commandeFournisseurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commande-fournisseurs/{id}")
    public ResponseEntity<Void> deleteCommandeFournisseur(@PathVariable Long id) {
        log.debug("REST request to delete CommandeFournisseur : {}", id);
        commandeFournisseurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
