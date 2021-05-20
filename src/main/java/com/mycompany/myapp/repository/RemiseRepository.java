package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Remise;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Remise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RemiseRepository extends JpaRepository<Remise, Long> {}
