package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.BonCommande;
import com.mycompany.myapp.domain.CommandeClient;
import com.mycompany.myapp.domain.CommandeFournisseur;
import com.mycompany.myapp.domain.Produit;
import com.mycompany.myapp.service.dto.BonCommandeDTO;
import com.mycompany.myapp.service.dto.CommandeClientDTO;
import com.mycompany.myapp.service.dto.CommandeFournisseurDTO;
import com.mycompany.myapp.service.dto.ProduitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BonCommande} and its DTO {@link BonCommandeDTO}.
 */
@Mapper(componentModel = "spring")
public interface BonCommandeMapper extends EntityMapper<BonCommandeDTO, BonCommande> {
    @Mapping(target = "commandeF", source = "commandeF", qualifiedByName = "commandeFournisseurId")
    @Mapping(target = "commandeC", source = "commandeC", qualifiedByName = "commandeClientId")
    @Mapping(target = "produit", source = "produit", qualifiedByName = "produitId")
    BonCommandeDTO toDto(BonCommande s);

    @Named("commandeFournisseurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommandeFournisseurDTO toDtoCommandeFournisseurId(CommandeFournisseur commandeFournisseur);

    @Named("commandeClientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommandeClientDTO toDtoCommandeClientId(CommandeClient commandeClient);

    @Named("produitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProduitDTO toDtoProduitId(Produit produit);
}
