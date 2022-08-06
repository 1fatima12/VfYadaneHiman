package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandeFournisseurMapperTest {

    private CommandeFournisseurMapper commandeFournisseurMapper;

    @BeforeEach
    public void setUp() {
        commandeFournisseurMapper = new CommandeFournisseurMapperImpl();
    }
}
