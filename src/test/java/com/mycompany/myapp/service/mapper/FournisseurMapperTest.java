package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FournisseurMapperTest {

    private FournisseurMapper fournisseurMapper;

    @BeforeEach
    public void setUp() {
        fournisseurMapper = new FournisseurMapperImpl();
    }
}
