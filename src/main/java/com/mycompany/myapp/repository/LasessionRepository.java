package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Lasession;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Lasession entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LasessionRepository extends JpaRepository<Lasession, Long> {}
