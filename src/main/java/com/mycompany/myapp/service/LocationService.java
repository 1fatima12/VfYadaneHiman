package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.LocationDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Location}.
 */
public interface LocationService {
    /**
     * Save a location.
     *
     * @param locationDTO the entity to save.
     * @return the persisted entity.
     */
    LocationDTO save(LocationDTO locationDTO);

    /**
     * Updates a location.
     *
     * @param locationDTO the entity to update.
     * @return the persisted entity.
     */
    LocationDTO update(LocationDTO locationDTO);

    /**
     * Partially updates a location.
     *
     * @param locationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LocationDTO> partialUpdate(LocationDTO locationDTO);

    /**
     * Get all the locations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LocationDTO> findAll(Pageable pageable);
    /**
     * Get all the LocationDTO where Employe is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<LocationDTO> findAllWhereEmployeIsNull();
    /**
     * Get all the LocationDTO where Client is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<LocationDTO> findAllWhereClientIsNull();
    /**
     * Get all the LocationDTO where Fournisseur is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<LocationDTO> findAllWhereFournisseurIsNull();
    /**
     * Get all the LocationDTO where Magazin is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<LocationDTO> findAllWhereMagazinIsNull();

    /**
     * Get the "id" location.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LocationDTO> findOne(Long id);

    /**
     * Delete the "id" location.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
