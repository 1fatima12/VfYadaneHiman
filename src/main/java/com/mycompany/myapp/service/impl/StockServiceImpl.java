package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Stock;
import com.mycompany.myapp.repository.StockRepository;
import com.mycompany.myapp.service.StockService;
import com.mycompany.myapp.service.dto.StockDTO;
import com.mycompany.myapp.service.mapper.StockMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Stock}.
 */
@Service
@Transactional
public class StockServiceImpl implements StockService {

    private final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

    private final StockRepository stockRepository;

    private final StockMapper stockMapper;

    public StockServiceImpl(StockRepository stockRepository, StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

    @Override
    public StockDTO save(StockDTO stockDTO) {
        log.debug("Request to save Stock : {}", stockDTO);
        Stock stock = stockMapper.toEntity(stockDTO);
        stock = stockRepository.save(stock);
        return stockMapper.toDto(stock);
    }

    @Override
    public StockDTO update(StockDTO stockDTO) {
        log.debug("Request to save Stock : {}", stockDTO);
        Stock stock = stockMapper.toEntity(stockDTO);
        stock = stockRepository.save(stock);
        return stockMapper.toDto(stock);
    }

    @Override
    public Optional<StockDTO> partialUpdate(StockDTO stockDTO) {
        log.debug("Request to partially update Stock : {}", stockDTO);

        return stockRepository
            .findById(stockDTO.getId())
            .map(existingStock -> {
                stockMapper.partialUpdate(existingStock, stockDTO);

                return existingStock;
            })
            .map(stockRepository::save)
            .map(stockMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Stocks");
        return stockRepository.findAll(pageable).map(stockMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StockDTO> findOne(Long id) {
        log.debug("Request to get Stock : {}", id);
        return stockRepository.findById(id).map(stockMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stock : {}", id);
        stockRepository.deleteById(id);
    }
}
