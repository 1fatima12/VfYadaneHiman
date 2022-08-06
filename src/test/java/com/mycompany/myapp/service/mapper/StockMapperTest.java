package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StockMapperTest {

    private StockMapper stockMapper;

    @BeforeEach
    public void setUp() {
        stockMapper = new StockMapperImpl();
    }
}
