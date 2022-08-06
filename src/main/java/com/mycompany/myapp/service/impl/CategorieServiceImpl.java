package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Categorie;
import com.mycompany.myapp.repository.CategorieRepository;
import com.mycompany.myapp.service.CategorieService;
import com.mycompany.myapp.service.dto.CategorieDTO;
import com.mycompany.myapp.service.mapper.CategorieMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Categorie}.
 */
@Service
@Transactional
public class CategorieServiceImpl implements CategorieService {

    private final Logger log = LoggerFactory.getLogger(CategorieServiceImpl.class);

    private final CategorieRepository categorieRepository;

    private final CategorieMapper categorieMapper;

    public CategorieServiceImpl(CategorieRepository categorieRepository, CategorieMapper categorieMapper) {
        this.categorieRepository = categorieRepository;
        this.categorieMapper = categorieMapper;
    }

    @Override
    public CategorieDTO save(CategorieDTO categorieDTO) {
        log.debug("Request to save Categorie : {}", categorieDTO);
        Categorie categorie = categorieMapper.toEntity(categorieDTO);
        categorie = categorieRepository.save(categorie);
        return categorieMapper.toDto(categorie);
    }

    @Override
    public CategorieDTO update(CategorieDTO categorieDTO) {
        log.debug("Request to save Categorie : {}", categorieDTO);
        Categorie categorie = categorieMapper.toEntity(categorieDTO);
        categorie = categorieRepository.save(categorie);
        return categorieMapper.toDto(categorie);
    }

    @Override
    public Optional<CategorieDTO> partialUpdate(CategorieDTO categorieDTO) {
        log.debug("Request to partially update Categorie : {}", categorieDTO);

        return categorieRepository
            .findById(categorieDTO.getId())
            .map(existingCategorie -> {
                categorieMapper.partialUpdate(existingCategorie, categorieDTO);

                return existingCategorie;
            })
            .map(categorieRepository::save)
            .map(categorieMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategorieDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return categorieRepository.findAll(pageable).map(categorieMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategorieDTO> findOne(Long id) {
        log.debug("Request to get Categorie : {}", id);
        return categorieRepository.findById(id).map(categorieMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Categorie : {}", id);
        categorieRepository.deleteById(id);
    }
}
