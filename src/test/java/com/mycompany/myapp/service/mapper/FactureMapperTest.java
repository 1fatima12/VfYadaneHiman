package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FactureMapperTest {

    private FactureMapper factureMapper;

    @BeforeEach
    public void setUp() {
        factureMapper = new FactureMapperImpl();
    }
}
