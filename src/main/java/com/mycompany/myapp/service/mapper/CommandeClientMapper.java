package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Client;
import com.mycompany.myapp.domain.CommandeClient;
import com.mycompany.myapp.service.dto.ClientDTO;
import com.mycompany.myapp.service.dto.CommandeClientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommandeClient} and its DTO {@link CommandeClientDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommandeClientMapper extends EntityMapper<CommandeClientDTO, CommandeClient> {
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    CommandeClientDTO toDto(CommandeClient s);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);
}
