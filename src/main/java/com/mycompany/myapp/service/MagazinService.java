package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Magazin;
import com.mycompany.myapp.service.dto.MagazinDTO;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Magazin}.
 */
public interface MagazinService {
    /**
     * Save a magazin.
     *
     * @param magazinDTO the entity to save.
     * @return the persisted entity.
     */
    MagazinDTO save(MagazinDTO magazinDTO);

    /**
     * Updates a magazin.
     *
     * @param magazinDTO the entity to update.
     * @return the persisted entity.
     */
    MagazinDTO update(MagazinDTO magazinDTO);

    /**
     * Partially updates a magazin.
     *
     * @param magazinDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MagazinDTO> partialUpdate(MagazinDTO magazinDTO);

    /**
     * Get all the magazins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MagazinDTO> findAll(Pageable pageable);

    /**
     * Get the "id" magazin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MagazinDTO> findOne(Long id);

    /**
     * Delete the "id" magazin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    List<Magazin> getAll();
}
