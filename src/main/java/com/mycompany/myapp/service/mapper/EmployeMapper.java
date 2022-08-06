package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Employe;
import com.mycompany.myapp.domain.Location;
import com.mycompany.myapp.domain.Magazin;
import com.mycompany.myapp.service.dto.EmployeDTO;
import com.mycompany.myapp.service.dto.LocationDTO;
import com.mycompany.myapp.service.dto.MagazinDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employe} and its DTO {@link EmployeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeMapper extends EntityMapper<EmployeDTO, Employe> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    @Mapping(target = "magzin", source = "magzin", qualifiedByName = "magazinId")
    EmployeDTO toDto(Employe s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);

    @Named("magazinId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MagazinDTO toDtoMagazinId(Magazin magazin);
}
