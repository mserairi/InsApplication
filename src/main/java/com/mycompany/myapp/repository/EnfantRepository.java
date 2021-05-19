package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Enfant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Enfant entity.
 */
@Repository
public interface EnfantRepository extends JpaRepository<Enfant, Long> {
    @Query(
        value = "select distinct enfant from Enfant enfant left join fetch enfant.suivres left join fetch enfant.parents",
        countQuery = "select count(distinct enfant) from Enfant enfant"
    )
    Page<Enfant> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct enfant from Enfant enfant left join fetch enfant.suivres left join fetch enfant.parents")
    List<Enfant> findAllWithEagerRelationships();

    @Query("select enfant from Enfant enfant left join fetch enfant.suivres left join fetch enfant.parents where enfant.id =:id")
    Optional<Enfant> findOneWithEagerRelationships(@Param("id") Long id);
}
