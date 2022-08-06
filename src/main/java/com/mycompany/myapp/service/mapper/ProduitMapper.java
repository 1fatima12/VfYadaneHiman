package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Categorie;
import com.mycompany.myapp.domain.Marque;
import com.mycompany.myapp.domain.Produit;
import com.mycompany.myapp.service.dto.CategorieDTO;
import com.mycompany.myapp.service.dto.MarqueDTO;
import com.mycompany.myapp.service.dto.ProduitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produit} and its DTO {@link ProduitDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProduitMapper extends EntityMapper<ProduitDTO, Produit> {
    @Mapping(target = "categorie", source = "categorie", qualifiedByName = "categorieId")
    @Mapping(target = "marque", source = "marque", qualifiedByName = "marqueId")
    ProduitDTO toDto(Produit s);

    @Named("categorieId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategorieDTO toDtoCategorieId(Categorie categorie);

    @Named("marqueId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MarqueDTO toDtoMarqueId(Marque marque);
}
