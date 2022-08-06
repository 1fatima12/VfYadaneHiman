package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Marque;
import com.mycompany.myapp.service.dto.MarqueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Marque} and its DTO {@link MarqueDTO}.
 */
@Mapper(componentModel = "spring")
public interface MarqueMapper extends EntityMapper<MarqueDTO, Marque> {}
