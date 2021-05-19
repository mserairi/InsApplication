package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Inscription;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Inscription entity.
 */
@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    @Query(
        value = "select distinct inscription from Inscription inscription left join fetch inscription.inscrits",
        countQuery = "select count(distinct inscription) from Inscription inscription"
    )
    Page<Inscription> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct inscription from Inscription inscription left join fetch inscription.inscrits")
    List<Inscription> findAllWithEagerRelationships();

    @Query("select inscription from Inscription inscription left join fetch inscription.inscrits where inscription.id =:id")
    Optional<Inscription> findOneWithEagerRelationships(@Param("id") Long id);
}
