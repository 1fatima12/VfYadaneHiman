package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Location;
import com.mycompany.myapp.domain.Magazin;
import com.mycompany.myapp.service.dto.LocationDTO;
import com.mycompany.myapp.service.dto.MagazinDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Magazin} and its DTO {@link MagazinDTO}.
 */
@Mapper(componentModel = "spring")
public interface MagazinMapper extends EntityMapper<MagazinDTO, Magazin> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    MagazinDTO toDto(Magazin s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);
}
