package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Enfant;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Enfant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnfantRepository extends JpaRepository<Enfant, Long> {
    @Query("select enfant from Enfant enfant where enfant.parent.login = ?#{principal.username}")
    List<Enfant> findByParentIsCurrentUser();
}
