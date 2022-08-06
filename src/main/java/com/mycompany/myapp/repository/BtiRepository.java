package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Bti;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Bti entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BtiRepository extends JpaRepository<Bti, Long> {}
