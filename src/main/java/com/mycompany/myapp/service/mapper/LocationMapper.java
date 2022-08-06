package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Location;
import com.mycompany.myapp.service.dto.LocationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
@Mapper(componentModel = "spring")
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {}
