package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Magazin;
import com.mycompany.myapp.repository.MagazinRepository;
import com.mycompany.myapp.service.MagazinService;
import com.mycompany.myapp.service.dto.MagazinDTO;
import com.mycompany.myapp.service.mapper.MagazinMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Magazin}.
 */
@Service
@Transactional
public class MagazinServiceImpl implements MagazinService {

    private final Logger log = LoggerFactory.getLogger(MagazinServiceImpl.class);

    private final MagazinRepository magazinRepository;

    private final MagazinMapper magazinMapper;

    public MagazinServiceImpl(MagazinRepository magazinRepository, MagazinMapper magazinMapper) {
        this.magazinRepository = magazinRepository;
        this.magazinMapper = magazinMapper;
    }

    @Override
    public MagazinDTO save(MagazinDTO magazinDTO) {
        log.debug("Request to save Magazin : {}", magazinDTO);
        Magazin magazin = magazinMapper.toEntity(magazinDTO);
        magazin = magazinRepository.save(magazin);
        return magazinMapper.toDto(magazin);
    }

    @Override
    public MagazinDTO update(MagazinDTO magazinDTO) {
        log.debug("Request to save Magazin : {}", magazinDTO);
        Magazin magazin = magazinMapper.toEntity(magazinDTO);
        magazin = magazinRepository.save(magazin);
        return magazinMapper.toDto(magazin);
    }

    @Override
    public Optional<MagazinDTO> partialUpdate(MagazinDTO magazinDTO) {
        log.debug("Request to partially update Magazin : {}", magazinDTO);

        return magazinRepository
            .findById(magazinDTO.getId())
            .map(existingMagazin -> {
                magazinMapper.partialUpdate(existingMagazin, magazinDTO);

                return existingMagazin;
            })
            .map(magazinRepository::save)
            .map(magazinMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MagazinDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Magazins");
        return magazinRepository.findAll(pageable).map(magazinMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MagazinDTO> findOne(Long id) {
        log.debug("Request to get Magazin : {}", id);
        return magazinRepository.findById(id).map(magazinMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Magazin : {}", id);
        magazinRepository.deleteById(id);
    }
}
