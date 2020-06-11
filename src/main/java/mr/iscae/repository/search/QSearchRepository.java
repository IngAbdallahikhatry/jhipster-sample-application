package mr.iscae.repository.search;

import mr.iscae.domain.Q;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Q} entity.
 */
public interface QSearchRepository extends ElasticsearchRepository<Q, Long> {
}
