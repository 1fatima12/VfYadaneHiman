package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Fournisseur;
import com.mycompany.myapp.domain.Location;
import com.mycompany.myapp.service.dto.FournisseurDTO;
import com.mycompany.myapp.service.dto.LocationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fournisseur} and its DTO {@link FournisseurDTO}.
 */
@Mapper(componentModel = "spring")
public interface FournisseurMapper extends EntityMapper<FournisseurDTO, Fournisseur> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    FournisseurDTO toDto(Fournisseur s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);
}
