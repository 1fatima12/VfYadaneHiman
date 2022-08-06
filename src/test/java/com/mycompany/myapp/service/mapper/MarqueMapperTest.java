package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MarqueMapperTest {

    private MarqueMapper marqueMapper;

    @BeforeEach
    public void setUp() {
        marqueMapper = new MarqueMapperImpl();
    }
}
