package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Arrivage;
import com.mycompany.myapp.domain.Facture;
import com.mycompany.myapp.service.dto.ArrivageDTO;
import com.mycompany.myapp.service.dto.FactureDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Facture} and its DTO {@link FactureDTO}.
 */
@Mapper(componentModel = "spring")
public interface FactureMapper extends EntityMapper<FactureDTO, Facture> {
    @Mapping(target = "arrivage", source = "arrivage", qualifiedByName = "arrivageId")
    FactureDTO toDto(Facture s);

    @Named("arrivageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArrivageDTO toDtoArrivageId(Arrivage arrivage);
}
