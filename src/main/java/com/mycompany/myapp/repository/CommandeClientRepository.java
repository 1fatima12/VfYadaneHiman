package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CommandeClient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CommandeClient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandeClientRepository extends JpaRepository<CommandeClient, Long> {}
