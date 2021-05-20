package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Creneau;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Creneau entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreneauRepository extends JpaRepository<Creneau, Long> {}
