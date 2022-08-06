package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.BonCommande;
import com.mycompany.myapp.repository.BonCommandeRepository;
import com.mycompany.myapp.service.BonCommandeService;
import com.mycompany.myapp.service.dto.BonCommandeDTO;
import com.mycompany.myapp.service.mapper.BonCommandeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BonCommande}.
 */
@Service
@Transactional
public class BonCommandeServiceImpl implements BonCommandeService {

    private final Logger log = LoggerFactory.getLogger(BonCommandeServiceImpl.class);

    private final BonCommandeRepository bonCommandeRepository;

    private final BonCommandeMapper bonCommandeMapper;

    public BonCommandeServiceImpl(BonCommandeRepository bonCommandeRepository, BonCommandeMapper bonCommandeMapper) {
        this.bonCommandeRepository = bonCommandeRepository;
        this.bonCommandeMapper = bonCommandeMapper;
    }

    @Override
    public BonCommandeDTO save(BonCommandeDTO bonCommandeDTO) {
        log.debug("Request to save BonCommande : {}", bonCommandeDTO);
        BonCommande bonCommande = bonCommandeMapper.toEntity(bonCommandeDTO);
        bonCommande = bonCommandeRepository.save(bonCommande);
        return bonCommandeMapper.toDto(bonCommande);
    }

    @Override
    public BonCommandeDTO update(BonCommandeDTO bonCommandeDTO) {
        log.debug("Request to save BonCommande : {}", bonCommandeDTO);
        BonCommande bonCommande = bonCommandeMapper.toEntity(bonCommandeDTO);
        bonCommande = bonCommandeRepository.save(bonCommande);
        return bonCommandeMapper.toDto(bonCommande);
    }

    @Override
    public Optional<BonCommandeDTO> partialUpdate(BonCommandeDTO bonCommandeDTO) {
        log.debug("Request to partially update BonCommande : {}", bonCommandeDTO);

        return bonCommandeRepository
            .findById(bonCommandeDTO.getId())
            .map(existingBonCommande -> {
                bonCommandeMapper.partialUpdate(existingBonCommande, bonCommandeDTO);

                return existingBonCommande;
            })
            .map(bonCommandeRepository::save)
            .map(bonCommandeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BonCommandeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BonCommandes");
        return bonCommandeRepository.findAll(pageable).map(bonCommandeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BonCommandeDTO> findOne(Long id) {
        log.debug("Request to get BonCommande : {}", id);
        return bonCommandeRepository.findById(id).map(bonCommandeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BonCommande : {}", id);
        bonCommandeRepository.deleteById(id);
    }
}
