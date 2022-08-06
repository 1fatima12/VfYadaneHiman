package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaiementMapperTest {

    private PaiementMapper paiementMapper;

    @BeforeEach
    public void setUp() {
        paiementMapper = new PaiementMapperImpl();
    }
}
