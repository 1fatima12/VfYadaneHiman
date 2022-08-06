package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Arrivage;
import com.mycompany.myapp.domain.Fournisseur;
import com.mycompany.myapp.domain.Produit;
import com.mycompany.myapp.service.dto.ArrivageDTO;
import com.mycompany.myapp.service.dto.FournisseurDTO;
import com.mycompany.myapp.service.dto.ProduitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Arrivage} and its DTO {@link ArrivageDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArrivageMapper extends EntityMapper<ArrivageDTO, Arrivage> {
    @Mapping(target = "produit", source = "produit", qualifiedByName = "produitId")
    @Mapping(target = "fournisseur", source = "fournisseur", qualifiedByName = "fournisseurId")
    ArrivageDTO toDto(Arrivage s);

    @Named("produitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProduitDTO toDtoProduitId(Produit produit);

    @Named("fournisseurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FournisseurDTO toDtoFournisseurId(Fournisseur fournisseur);
}
