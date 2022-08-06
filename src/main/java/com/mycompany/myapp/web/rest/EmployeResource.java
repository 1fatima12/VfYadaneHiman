package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.EmployeRepository;
import com.mycompany.myapp.service.EmployeService;
import com.mycompany.myapp.service.dto.EmployeDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Employe}.
 */
@RestController
@RequestMapping("/api")
public class EmployeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeResource.class);

    private static final String ENTITY_NAME = "employe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeService employeService;

    private final EmployeRepository employeRepository;

    public EmployeResource(EmployeService employeService, EmployeRepository employeRepository) {
        this.employeService = employeService;
        this.employeRepository = employeRepository;
    }

    /**
     * {@code POST  /employes} : Create a new employe.
     *
     * @param employeDTO the employeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeDTO, or with status {@code 400 (Bad Request)} if the employe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employes")
    public ResponseEntity<EmployeDTO> createEmploye(@RequestBody EmployeDTO employeDTO) throws URISyntaxException {
        log.debug("REST request to save Employe : {}", employeDTO);
        if (employeDTO.getId() != null) {
            throw new BadRequestAlertException("A new employe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeDTO result = employeService.save(employeDTO);
        return ResponseEntity
            .created(new URI("/api/employes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employes/:id} : Updates an existing employe.
     *
     * @param id the id of the employeDTO to save.
     * @param employeDTO the employeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeDTO,
     * or with status {@code 400 (Bad Request)} if the employeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employes/{id}")
    public ResponseEntity<EmployeDTO> updateEmploye(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeDTO employeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Employe : {}, {}", id, employeDTO);
        if (employeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeDTO result = employeService.update(employeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employes/:id} : Partial updates given fields of an existing employe, field will ignore if it is null
     *
     * @param id the id of the employeDTO to save.
     * @param employeDTO the employeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeDTO,
     * or with status {@code 400 (Bad Request)} if the employeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the employeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeDTO> partialUpdateEmploye(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeDTO employeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Employe partially : {}, {}", id, employeDTO);
        if (employeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeDTO> result = employeService.partialUpdate(employeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /employes} : get all the employes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employes in body.
     */
    @GetMapping("/employes")
    public ResponseEntity<List<EmployeDTO>> getAllEmployes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Employes");
        Page<EmployeDTO> page = employeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employes/:id} : get the "id" employe.
     *
     * @param id the id of the employeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employes/{id}")
    public ResponseEntity<EmployeDTO> getEmploye(@PathVariable Long id) {
        log.debug("REST request to get Employe : {}", id);
        Optional<EmployeDTO> employeDTO = employeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeDTO);
    }

    /**
     * {@code DELETE  /employes/:id} : delete the "id" employe.
     *
     * @param id the id of the employeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employes/{id}")
    public ResponseEntity<Void> deleteEmploye(@PathVariable Long id) {
        log.debug("REST request to delete Employe : {}", id);
        employeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
