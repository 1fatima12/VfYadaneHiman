package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CommandeClientDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CommandeClient}.
 */
public interface CommandeClientService {
    /**
     * Save a commandeClient.
     *
     * @param commandeClientDTO the entity to save.
     * @return the persisted entity.
     */
    CommandeClientDTO save(CommandeClientDTO commandeClientDTO);

    /**
     * Updates a commandeClient.
     *
     * @param commandeClientDTO the entity to update.
     * @return the persisted entity.
     */
    CommandeClientDTO update(CommandeClientDTO commandeClientDTO);

    /**
     * Partially updates a commandeClient.
     *
     * @param commandeClientDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CommandeClientDTO> partialUpdate(CommandeClientDTO commandeClientDTO);

    /**
     * Get all the commandeClients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CommandeClientDTO> findAll(Pageable pageable);

    /**
     * Get the "id" commandeClient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommandeClientDTO> findOne(Long id);

    /**
     * Delete the "id" commandeClient.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
