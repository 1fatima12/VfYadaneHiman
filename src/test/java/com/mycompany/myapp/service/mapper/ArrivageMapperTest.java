package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrivageMapperTest {

    private ArrivageMapper arrivageMapper;

    @BeforeEach
    public void setUp() {
        arrivageMapper = new ArrivageMapperImpl();
    }
}
