package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Groupe;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Groupe entity.
 */
@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Long> {
    @Query(
        value = "select distinct groupe from Groupe groupe left join fetch groupe.enfants",
        countQuery = "select count(distinct groupe) from Groupe groupe"
    )
    Page<Groupe> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct groupe from Groupe groupe left join fetch groupe.enfants")
    List<Groupe> findAllWithEagerRelationships();

    @Query("select groupe from Groupe groupe left join fetch groupe.enfants where groupe.id =:id")
    Optional<Groupe> findOneWithEagerRelationships(@Param("id") Long id);
}
