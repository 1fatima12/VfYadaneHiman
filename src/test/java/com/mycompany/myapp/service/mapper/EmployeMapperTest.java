package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeMapperTest {

    private EmployeMapper employeMapper;

    @BeforeEach
    public void setUp() {
        employeMapper = new EmployeMapperImpl();
    }
}
