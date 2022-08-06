package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ArrivageDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Arrivage}.
 */
public interface ArrivageService {
    /**
     * Save a arrivage.
     *
     * @param arrivageDTO the entity to save.
     * @return the persisted entity.
     */
    ArrivageDTO save(ArrivageDTO arrivageDTO);

    /**
     * Updates a arrivage.
     *
     * @param arrivageDTO the entity to update.
     * @return the persisted entity.
     */
    ArrivageDTO update(ArrivageDTO arrivageDTO);

    /**
     * Partially updates a arrivage.
     *
     * @param arrivageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArrivageDTO> partialUpdate(ArrivageDTO arrivageDTO);

    /**
     * Get all the arrivages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArrivageDTO> findAll(Pageable pageable);
    /**
     * Get all the ArrivageDTO where Facture is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ArrivageDTO> findAllWhereFactureIsNull();

    /**
     * Get the "id" arrivage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArrivageDTO> findOne(Long id);

    /**
     * Delete the "id" arrivage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
