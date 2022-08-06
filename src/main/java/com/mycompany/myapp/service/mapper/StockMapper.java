package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Magazin;
import com.mycompany.myapp.domain.Produit;
import com.mycompany.myapp.domain.Stock;
import com.mycompany.myapp.service.dto.MagazinDTO;
import com.mycompany.myapp.service.dto.ProduitDTO;
import com.mycompany.myapp.service.dto.StockDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Stock} and its DTO {@link StockDTO}.
 */
@Mapper(componentModel = "spring")
public interface StockMapper extends EntityMapper<StockDTO, Stock> {
    @Mapping(target = "produit", source = "produit", qualifiedByName = "produitId")
    @Mapping(target = "magazin", source = "magazin", qualifiedByName = "magazinId")
    StockDTO toDto(Stock s);

    @Named("produitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProduitDTO toDtoProduitId(Produit produit);

    @Named("magazinId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MagazinDTO toDtoMagazinId(Magazin magazin);
}
