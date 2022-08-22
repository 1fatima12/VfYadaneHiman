package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Magazin;
import com.mycompany.myapp.domain.Produit;
import com.mycompany.myapp.domain.Stock;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Stock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
	List<Stock> findByMagazinId(long id);
	List<Stock> findByProduitId(Long id);
	
	
}
