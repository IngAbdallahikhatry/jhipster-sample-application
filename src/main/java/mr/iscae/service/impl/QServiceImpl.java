package mr.iscae.service.impl;

import mr.iscae.service.QService;
import mr.iscae.domain.Q;
import mr.iscae.repository.QRepository;
import mr.iscae.repository.search.QSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Q}.
 */
@Service
@Transactional
public class QServiceImpl implements QService {

    private final Logger log = LoggerFactory.getLogger(QServiceImpl.class);

    private final QRepository qRepository;

    private final QSearchRepository qSearchRepository;

    public QServiceImpl(QRepository qRepository, QSearchRepository qSearchRepository) {
        this.qRepository = qRepository;
        this.qSearchRepository = qSearchRepository;
    }

    /**
     * Save a q.
     *
     * @param q the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Q save(Q q) {
        log.debug("Request to save Q : {}", q);
        Q result = qRepository.save(q);
        qSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the qs.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Q> findAll() {
        log.debug("Request to get all QS");
        return qRepository.findAll();
    }


    /**
     * Get one q by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Q> findOne(Long id) {
        log.debug("Request to get Q : {}", id);
        return qRepository.findById(id);
    }

    /**
     * Delete the q by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Q : {}", id);
        qRepository.deleteById(id);
        qSearchRepository.deleteById(id);
    }

    /**
     * Search for the q corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Q> search(String query) {
        log.debug("Request to search QS for query {}", query);
        return StreamSupport
            .stream(qSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
