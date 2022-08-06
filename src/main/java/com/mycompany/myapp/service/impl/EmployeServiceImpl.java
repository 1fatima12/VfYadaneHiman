package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Employe;
import com.mycompany.myapp.repository.EmployeRepository;
import com.mycompany.myapp.service.EmployeService;
import com.mycompany.myapp.service.dto.EmployeDTO;
import com.mycompany.myapp.service.mapper.EmployeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Employe}.
 */
@Service
@Transactional
public class EmployeServiceImpl implements EmployeService {

    private final Logger log = LoggerFactory.getLogger(EmployeServiceImpl.class);

    private final EmployeRepository employeRepository;

    private final EmployeMapper employeMapper;

    public EmployeServiceImpl(EmployeRepository employeRepository, EmployeMapper employeMapper) {
        this.employeRepository = employeRepository;
        this.employeMapper = employeMapper;
    }

    @Override
    public EmployeDTO save(EmployeDTO employeDTO) {
        log.debug("Request to save Employe : {}", employeDTO);
        Employe employe = employeMapper.toEntity(employeDTO);
        employe = employeRepository.save(employe);
        return employeMapper.toDto(employe);
    }

    @Override
    public EmployeDTO update(EmployeDTO employeDTO) {
        log.debug("Request to save Employe : {}", employeDTO);
        Employe employe = employeMapper.toEntity(employeDTO);
        employe = employeRepository.save(employe);
        return employeMapper.toDto(employe);
    }

    @Override
    public Optional<EmployeDTO> partialUpdate(EmployeDTO employeDTO) {
        log.debug("Request to partially update Employe : {}", employeDTO);

        return employeRepository
            .findById(employeDTO.getId())
            .map(existingEmploye -> {
                employeMapper.partialUpdate(existingEmploye, employeDTO);

                return existingEmploye;
            })
            .map(employeRepository::save)
            .map(employeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Employes");
        return employeRepository.findAll(pageable).map(employeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeDTO> findOne(Long id) {
        log.debug("Request to get Employe : {}", id);
        return employeRepository.findById(id).map(employeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Employe : {}", id);
        employeRepository.deleteById(id);
    }
}
