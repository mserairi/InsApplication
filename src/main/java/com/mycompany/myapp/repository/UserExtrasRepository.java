package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserExtras;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserExtras entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserExtrasRepository extends JpaRepository<UserExtras, Long> {}
