package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.EmployeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Employe}.
 */
public interface EmployeService {
    /**
     * Save a employe.
     *
     * @param employeDTO the entity to save.
     * @return the persisted entity.
     */
    EmployeDTO save(EmployeDTO employeDTO);

    /**
     * Updates a employe.
     *
     * @param employeDTO the entity to update.
     * @return the persisted entity.
     */
    EmployeDTO update(EmployeDTO employeDTO);

    /**
     * Partially updates a employe.
     *
     * @param employeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EmployeDTO> partialUpdate(EmployeDTO employeDTO);

    /**
     * Get all the employes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmployeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" employe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmployeDTO> findOne(Long id);

    /**
     * Delete the "id" employe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
