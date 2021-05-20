package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TypeRemise;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TypeRemise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeRemiseRepository extends JpaRepository<TypeRemise, Long> {}
