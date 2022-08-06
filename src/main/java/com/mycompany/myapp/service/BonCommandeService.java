package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.BonCommandeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.BonCommande}.
 */
public interface BonCommandeService {
    /**
     * Save a bonCommande.
     *
     * @param bonCommandeDTO the entity to save.
     * @return the persisted entity.
     */
    BonCommandeDTO save(BonCommandeDTO bonCommandeDTO);

    /**
     * Updates a bonCommande.
     *
     * @param bonCommandeDTO the entity to update.
     * @return the persisted entity.
     */
    BonCommandeDTO update(BonCommandeDTO bonCommandeDTO);

    /**
     * Partially updates a bonCommande.
     *
     * @param bonCommandeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BonCommandeDTO> partialUpdate(BonCommandeDTO bonCommandeDTO);

    /**
     * Get all the bonCommandes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BonCommandeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bonCommande.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BonCommandeDTO> findOne(Long id);

    /**
     * Delete the "id" bonCommande.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
