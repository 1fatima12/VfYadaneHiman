package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Arrivage;
import com.mycompany.myapp.repository.ArrivageRepository;
import com.mycompany.myapp.service.ArrivageService;
import com.mycompany.myapp.service.dto.ArrivageDTO;
import com.mycompany.myapp.service.mapper.ArrivageMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Arrivage}.
 */
@Service
@Transactional
public class ArrivageServiceImpl implements ArrivageService {

    private final Logger log = LoggerFactory.getLogger(ArrivageServiceImpl.class);

    private final ArrivageRepository arrivageRepository;

    private final ArrivageMapper arrivageMapper;

    public ArrivageServiceImpl(ArrivageRepository arrivageRepository, ArrivageMapper arrivageMapper) {
        this.arrivageRepository = arrivageRepository;
        this.arrivageMapper = arrivageMapper;
    }

    @Override
    public ArrivageDTO save(ArrivageDTO arrivageDTO) {
        log.debug("Request to save Arrivage : {}", arrivageDTO);
        Arrivage arrivage = arrivageMapper.toEntity(arrivageDTO);
        arrivage = arrivageRepository.save(arrivage);
        return arrivageMapper.toDto(arrivage);
    }

    @Override
    public ArrivageDTO update(ArrivageDTO arrivageDTO) {
        log.debug("Request to save Arrivage : {}", arrivageDTO);
        Arrivage arrivage = arrivageMapper.toEntity(arrivageDTO);
        arrivage = arrivageRepository.save(arrivage);
        return arrivageMapper.toDto(arrivage);
    }

    @Override
    public Optional<ArrivageDTO> partialUpdate(ArrivageDTO arrivageDTO) {
        log.debug("Request to partially update Arrivage : {}", arrivageDTO);

        return arrivageRepository
            .findById(arrivageDTO.getId())
            .map(existingArrivage -> {
                arrivageMapper.partialUpdate(existingArrivage, arrivageDTO);

                return existingArrivage;
            })
            .map(arrivageRepository::save)
            .map(arrivageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArrivageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Arrivages");
        return arrivageRepository.findAll(pageable).map(arrivageMapper::toDto);
    }

    /**
     *  Get all the arrivages where Facture is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ArrivageDTO> findAllWhereFactureIsNull() {
        log.debug("Request to get all arrivages where Facture is null");
        return StreamSupport
            .stream(arrivageRepository.findAll().spliterator(), false)
            .filter(arrivage -> arrivage.getFacture() == null)
            .map(arrivageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArrivageDTO> findOne(Long id) {
        log.debug("Request to get Arrivage : {}", id);
        return arrivageRepository.findById(id).map(arrivageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Arrivage : {}", id);
        arrivageRepository.deleteById(id);
    }
}
