package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Arrivage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Arrivage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArrivageRepository extends JpaRepository<Arrivage, Long> {}
