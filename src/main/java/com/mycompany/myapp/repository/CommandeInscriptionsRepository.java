package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CommandeInscriptions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CommandeInscriptions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandeInscriptionsRepository extends JpaRepository<CommandeInscriptions, Long> {}
