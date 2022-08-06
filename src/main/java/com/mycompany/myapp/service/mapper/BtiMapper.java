package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Bti;
import com.mycompany.myapp.domain.Magazin;
import com.mycompany.myapp.domain.Produit;
import com.mycompany.myapp.service.dto.BtiDTO;
import com.mycompany.myapp.service.dto.MagazinDTO;
import com.mycompany.myapp.service.dto.ProduitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bti} and its DTO {@link BtiDTO}.
 */
@Mapper(componentModel = "spring")
public interface BtiMapper extends EntityMapper<BtiDTO, Bti> {
    @Mapping(target = "produit", source = "produit", qualifiedByName = "produitId")
    @Mapping(target = "magazin", source = "magazin", qualifiedByName = "magazinId")
    BtiDTO toDto(Bti s);

    @Named("produitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProduitDTO toDtoProduitId(Produit produit);

    @Named("magazinId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MagazinDTO toDtoMagazinId(Magazin magazin);
}
