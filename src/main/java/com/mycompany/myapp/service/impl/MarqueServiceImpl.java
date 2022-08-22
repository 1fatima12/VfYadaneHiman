package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Marque;
import com.mycompany.myapp.repository.MarqueRepository;
import com.mycompany.myapp.service.MarqueService;
import com.mycompany.myapp.service.dto.MarqueDTO;
import com.mycompany.myapp.service.mapper.MarqueMapper;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Marque}.
 */
@Service
@Transactional
public class MarqueServiceImpl implements MarqueService {

    private final Logger log = LoggerFactory.getLogger(MarqueServiceImpl.class);

    private final MarqueRepository marqueRepository;

    private final MarqueMapper marqueMapper;

    public MarqueServiceImpl(MarqueRepository marqueRepository, MarqueMapper marqueMapper) {
        this.marqueRepository = marqueRepository;
        this.marqueMapper = marqueMapper;
    }

    @Override
    public MarqueDTO save(MarqueDTO marqueDTO) {
        log.debug("Request to save Marque : {}", marqueDTO);
        Marque marque = marqueMapper.toEntity(marqueDTO);
        marque = marqueRepository.save(marque);
        return marqueMapper.toDto(marque);
    }

    @Override
    public MarqueDTO update(MarqueDTO marqueDTO) {
        log.debug("Request to save Marque : {}", marqueDTO);
        Marque marque = marqueMapper.toEntity(marqueDTO);
        marque = marqueRepository.save(marque);
        return marqueMapper.toDto(marque);
    }

    @Override
    public Optional<MarqueDTO> partialUpdate(MarqueDTO marqueDTO) {
        log.debug("Request to partially update Marque : {}", marqueDTO);

        return marqueRepository
            .findById(marqueDTO.getId())
            .map(existingMarque -> {
                marqueMapper.partialUpdate(existingMarque, marqueDTO);

                return existingMarque;
            })
            .map(marqueRepository::save)
            .map(marqueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MarqueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Marques");
        return marqueRepository.findAll(pageable).map(marqueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MarqueDTO> findOne(Long id) {
        log.debug("Request to get Marque : {}", id);
        return marqueRepository.findById(id).map(marqueMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Marque : {}", id);
        marqueRepository.deleteById(id);
    }

	@Override
	public List<Marque> getAll() {
		// TODO Auto-generated method stub
		return marqueRepository.findAll();
	}
}
