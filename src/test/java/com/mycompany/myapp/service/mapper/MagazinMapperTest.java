package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MagazinMapperTest {

    private MagazinMapper magazinMapper;

    @BeforeEach
    public void setUp() {
        magazinMapper = new MagazinMapperImpl();
    }
}
