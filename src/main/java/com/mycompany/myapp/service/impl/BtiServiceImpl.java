package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Bti;
import com.mycompany.myapp.repository.BtiRepository;
import com.mycompany.myapp.service.BtiService;
import com.mycompany.myapp.service.dto.BtiDTO;
import com.mycompany.myapp.service.mapper.BtiMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Bti}.
 */
@Service
@Transactional
public class BtiServiceImpl implements BtiService {

    private final Logger log = LoggerFactory.getLogger(BtiServiceImpl.class);

    private final BtiRepository btiRepository;

    private final BtiMapper btiMapper;

    public BtiServiceImpl(BtiRepository btiRepository, BtiMapper btiMapper) {
        this.btiRepository = btiRepository;
        this.btiMapper = btiMapper;
    }

    @Override
    public BtiDTO save(BtiDTO btiDTO) {
        log.debug("Request to save Bti : {}", btiDTO);
        Bti bti = btiMapper.toEntity(btiDTO);
        bti = btiRepository.save(bti);
        return btiMapper.toDto(bti);
    }

    @Override
    public BtiDTO update(BtiDTO btiDTO) {
        log.debug("Request to save Bti : {}", btiDTO);
        Bti bti = btiMapper.toEntity(btiDTO);
        bti = btiRepository.save(bti);
        return btiMapper.toDto(bti);
    }

    @Override
    public Optional<BtiDTO> partialUpdate(BtiDTO btiDTO) {
        log.debug("Request to partially update Bti : {}", btiDTO);

        return btiRepository
            .findById(btiDTO.getId())
            .map(existingBti -> {
                btiMapper.partialUpdate(existingBti, btiDTO);

                return existingBti;
            })
            .map(btiRepository::save)
            .map(btiMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BtiDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Btis");
        return btiRepository.findAll(pageable).map(btiMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BtiDTO> findOne(Long id) {
        log.debug("Request to get Bti : {}", id);
        return btiRepository.findById(id).map(btiMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bti : {}", id);
        btiRepository.deleteById(id);
    }
}
