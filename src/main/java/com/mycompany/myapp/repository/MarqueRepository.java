package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Marque;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Marque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarqueRepository extends JpaRepository<Marque, Long> {}
