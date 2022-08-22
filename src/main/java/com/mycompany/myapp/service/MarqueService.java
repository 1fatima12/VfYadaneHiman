package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Marque;
import com.mycompany.myapp.service.dto.MarqueDTO;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Marque}.
 */
public interface MarqueService {
    /**
     * Save a marque.
     *
     * @param marqueDTO the entity to save.
     * @return the persisted entity.
     */
    MarqueDTO save(MarqueDTO marqueDTO);

    /**
     * Updates a marque.
     *
     * @param marqueDTO the entity to update.
     * @return the persisted entity.
     */
    MarqueDTO update(MarqueDTO marqueDTO);

    /**
     * Partially updates a marque.
     *
     * @param marqueDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MarqueDTO> partialUpdate(MarqueDTO marqueDTO);

    /**
     * Get all the marques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MarqueDTO> findAll(Pageable pageable);

    /**
     * Get the "id" marque.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MarqueDTO> findOne(Long id);

    /**
     * Delete the "id" marque.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    List<Marque> getAll();
}
