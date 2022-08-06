package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandeClientMapperTest {

    private CommandeClientMapper commandeClientMapper;

    @BeforeEach
    public void setUp() {
        commandeClientMapper = new CommandeClientMapperImpl();
    }
}
