package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.BtiDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Bti}.
 */
public interface BtiService {
    /**
     * Save a bti.
     *
     * @param btiDTO the entity to save.
     * @return the persisted entity.
     */
    BtiDTO save(BtiDTO btiDTO);

    /**
     * Updates a bti.
     *
     * @param btiDTO the entity to update.
     * @return the persisted entity.
     */
    BtiDTO update(BtiDTO btiDTO);

    /**
     * Partially updates a bti.
     *
     * @param btiDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BtiDTO> partialUpdate(BtiDTO btiDTO);

    /**
     * Get all the btis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BtiDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bti.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BtiDTO> findOne(Long id);

    /**
     * Delete the "id" bti.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
