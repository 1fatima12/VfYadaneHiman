package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CommandeFournisseurDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CommandeFournisseur}.
 */
public interface CommandeFournisseurService {
    /**
     * Save a commandeFournisseur.
     *
     * @param commandeFournisseurDTO the entity to save.
     * @return the persisted entity.
     */
    CommandeFournisseurDTO save(CommandeFournisseurDTO commandeFournisseurDTO);

    /**
     * Updates a commandeFournisseur.
     *
     * @param commandeFournisseurDTO the entity to update.
     * @return the persisted entity.
     */
    CommandeFournisseurDTO update(CommandeFournisseurDTO commandeFournisseurDTO);

    /**
     * Partially updates a commandeFournisseur.
     *
     * @param commandeFournisseurDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CommandeFournisseurDTO> partialUpdate(CommandeFournisseurDTO commandeFournisseurDTO);

    /**
     * Get all the commandeFournisseurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CommandeFournisseurDTO> findAll(Pageable pageable);

    /**
     * Get the "id" commandeFournisseur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommandeFournisseurDTO> findOne(Long id);

    /**
     * Delete the "id" commandeFournisseur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
