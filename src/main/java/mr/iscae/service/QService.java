package mr.iscae.service;

import mr.iscae.domain.Q;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Q}.
 */
public interface QService {

    /**
     * Save a q.
     *
     * @param q the entity to save.
     * @return the persisted entity.
     */
    Q save(Q q);

    /**
     * Get all the qs.
     *
     * @return the list of entities.
     */
    List<Q> findAll();


    /**
     * Get the "id" q.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Q> findOne(Long id);

    /**
     * Delete the "id" q.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the q corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Q> search(String query);
}
