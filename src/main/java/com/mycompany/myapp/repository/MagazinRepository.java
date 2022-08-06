package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Magazin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Magazin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MagazinRepository extends JpaRepository<Magazin, Long> {}
