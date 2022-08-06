package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Paiement;
import com.mycompany.myapp.repository.PaiementRepository;
import com.mycompany.myapp.service.PaiementService;
import com.mycompany.myapp.service.dto.PaiementDTO;
import com.mycompany.myapp.service.mapper.PaiementMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Paiement}.
 */
@Service
@Transactional
public class PaiementServiceImpl implements PaiementService {

    private final Logger log = LoggerFactory.getLogger(PaiementServiceImpl.class);

    private final PaiementRepository paiementRepository;

    private final PaiementMapper paiementMapper;

    public PaiementServiceImpl(PaiementRepository paiementRepository, PaiementMapper paiementMapper) {
        this.paiementRepository = paiementRepository;
        this.paiementMapper = paiementMapper;
    }

    @Override
    public PaiementDTO save(PaiementDTO paiementDTO) {
        log.debug("Request to save Paiement : {}", paiementDTO);
        Paiement paiement = paiementMapper.toEntity(paiementDTO);
        paiement = paiementRepository.save(paiement);
        return paiementMapper.toDto(paiement);
    }

    @Override
    public PaiementDTO update(PaiementDTO paiementDTO) {
        log.debug("Request to save Paiement : {}", paiementDTO);
        Paiement paiement = paiementMapper.toEntity(paiementDTO);
        paiement = paiementRepository.save(paiement);
        return paiementMapper.toDto(paiement);
    }

    @Override
    public Optional<PaiementDTO> partialUpdate(PaiementDTO paiementDTO) {
        log.debug("Request to partially update Paiement : {}", paiementDTO);

        return paiementRepository
            .findById(paiementDTO.getId())
            .map(existingPaiement -> {
                paiementMapper.partialUpdate(existingPaiement, paiementDTO);

                return existingPaiement;
            })
            .map(paiementRepository::save)
            .map(paiementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaiementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Paiements");
        return paiementRepository.findAll(pageable).map(paiementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaiementDTO> findOne(Long id) {
        log.debug("Request to get Paiement : {}", id);
        return paiementRepository.findById(id).map(paiementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Paiement : {}", id);
        paiementRepository.deleteById(id);
    }
}
