package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Client;
import com.mycompany.myapp.domain.Location;
import com.mycompany.myapp.service.dto.ClientDTO;
import com.mycompany.myapp.service.dto.LocationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    ClientDTO toDto(Client s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);
}
