package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BonCommandeMapperTest {

    private BonCommandeMapper bonCommandeMapper;

    @BeforeEach
    public void setUp() {
        bonCommandeMapper = new BonCommandeMapperImpl();
    }
}
