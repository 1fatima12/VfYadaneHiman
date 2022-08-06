package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CommandeFournisseur;
import com.mycompany.myapp.domain.Fournisseur;
import com.mycompany.myapp.service.dto.CommandeFournisseurDTO;
import com.mycompany.myapp.service.dto.FournisseurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommandeFournisseur} and its DTO {@link CommandeFournisseurDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommandeFournisseurMapper extends EntityMapper<CommandeFournisseurDTO, CommandeFournisseur> {
    @Mapping(target = "fournisseur", source = "fournisseur", qualifiedByName = "fournisseurId")
    CommandeFournisseurDTO toDto(CommandeFournisseur s);

    @Named("fournisseurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FournisseurDTO toDtoFournisseurId(Fournisseur fournisseur);
}
