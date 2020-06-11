package mr.iscae.repository.search;

import mr.iscae.domain.Department;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Department} entity.
 */
public interface DepartmentSearchRepository extends ElasticsearchRepository<Department, Long> {
}
