package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BonCommande;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BonCommande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BonCommandeRepository extends JpaRepository<BonCommande, Long> {}
