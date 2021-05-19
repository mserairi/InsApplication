package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SousCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SousCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SousCategoryRepository extends JpaRepository<SousCategory, Long> {}
