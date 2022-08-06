package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CommandeClientRepository;
import com.mycompany.myapp.service.CommandeClientService;
import com.mycompany.myapp.service.dto.CommandeClientDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CommandeClient}.
 */
@RestController
@RequestMapping("/api")
public class CommandeClientResource {

    private final Logger log = LoggerFactory.getLogger(CommandeClientResource.class);

    private static final String ENTITY_NAME = "commandeClient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommandeClientService commandeClientService;

    private final CommandeClientRepository commandeClientRepository;

    public CommandeClientResource(CommandeClientService commandeClientService, CommandeClientRepository commandeClientRepository) {
        this.commandeClientService = commandeClientService;
        this.commandeClientRepository = commandeClientRepository;
    }

    /**
     * {@code POST  /commande-clients} : Create a new commandeClient.
     *
     * @param commandeClientDTO the commandeClientDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commandeClientDTO, or with status {@code 400 (Bad Request)} if the commandeClient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commande-clients")
    public ResponseEntity<CommandeClientDTO> createCommandeClient(@RequestBody CommandeClientDTO commandeClientDTO)
        throws URISyntaxException {
        log.debug("REST request to save CommandeClient : {}", commandeClientDTO);
        if (commandeClientDTO.getId() != null) {
            throw new BadRequestAlertException("A new commandeClient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommandeClientDTO result = commandeClientService.save(commandeClientDTO);
        return ResponseEntity
            .created(new URI("/api/commande-clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commande-clients/:id} : Updates an existing commandeClient.
     *
     * @param id the id of the commandeClientDTO to save.
     * @param commandeClientDTO the commandeClientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandeClientDTO,
     * or with status {@code 400 (Bad Request)} if the commandeClientDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commandeClientDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commande-clients/{id}")
    public ResponseEntity<CommandeClientDTO> updateCommandeClient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommandeClientDTO commandeClientDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CommandeClient : {}, {}", id, commandeClientDTO);
        if (commandeClientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commandeClientDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commandeClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommandeClientDTO result = commandeClientService.update(commandeClientDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commandeClientDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /commande-clients/:id} : Partial updates given fields of an existing commandeClient, field will ignore if it is null
     *
     * @param id the id of the commandeClientDTO to save.
     * @param commandeClientDTO the commandeClientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandeClientDTO,
     * or with status {@code 400 (Bad Request)} if the commandeClientDTO is not valid,
     * or with status {@code 404 (Not Found)} if the commandeClientDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the commandeClientDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/commande-clients/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CommandeClientDTO> partialUpdateCommandeClient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommandeClientDTO commandeClientDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CommandeClient partially : {}, {}", id, commandeClientDTO);
        if (commandeClientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commandeClientDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commandeClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommandeClientDTO> result = commandeClientService.partialUpdate(commandeClientDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commandeClientDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /commande-clients} : get all the commandeClients.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commandeClients in body.
     */
    @GetMapping("/commande-clients")
    public ResponseEntity<List<CommandeClientDTO>> getAllCommandeClients(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CommandeClients");
        Page<CommandeClientDTO> page = commandeClientService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commande-clients/:id} : get the "id" commandeClient.
     *
     * @param id the id of the commandeClientDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commandeClientDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commande-clients/{id}")
    public ResponseEntity<CommandeClientDTO> getCommandeClient(@PathVariable Long id) {
        log.debug("REST request to get CommandeClient : {}", id);
        Optional<CommandeClientDTO> commandeClientDTO = commandeClientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commandeClientDTO);
    }

    /**
     * {@code DELETE  /commande-clients/:id} : delete the "id" commandeClient.
     *
     * @param id the id of the commandeClientDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commande-clients/{id}")
    public ResponseEntity<Void> deleteCommandeClient(@PathVariable Long id) {
        log.debug("REST request to delete CommandeClient : {}", id);
        commandeClientService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
