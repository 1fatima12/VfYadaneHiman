package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Categorie;
import com.mycompany.myapp.service.dto.CategorieDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categorie} and its DTO {@link CategorieDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategorieMapper extends EntityMapper<CategorieDTO, Categorie> {}
