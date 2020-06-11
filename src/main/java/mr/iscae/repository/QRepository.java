package mr.iscae.repository;

import mr.iscae.domain.Q;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Q entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QRepository extends JpaRepository<Q, Long> {
}
