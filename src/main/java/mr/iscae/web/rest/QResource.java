package mr.iscae.web.rest;

import mr.iscae.domain.Q;
import mr.iscae.service.QService;
import mr.iscae.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link mr.iscae.domain.Q}.
 */
@RestController
@RequestMapping("/api")
public class QResource {

    private final Logger log = LoggerFactory.getLogger(QResource.class);

    private static final String ENTITY_NAME = "q";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QService qService;

    public QResource(QService qService) {
        this.qService = qService;
    }

    /**
     * {@code POST  /qs} : Create a new q.
     *
     * @param q the q to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new q, or with status {@code 400 (Bad Request)} if the q has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/qs")
    public ResponseEntity<Q> createQ(@RequestBody Q q) throws URISyntaxException {
        log.debug("REST request to save Q : {}", q);
        if (q.getId() != null) {
            throw new BadRequestAlertException("A new q cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Q result = qService.save(q);
        return ResponseEntity.created(new URI("/api/qs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /qs} : Updates an existing q.
     *
     * @param q the q to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated q,
     * or with status {@code 400 (Bad Request)} if the q is not valid,
     * or with status {@code 500 (Internal Server Error)} if the q couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/qs")
    public ResponseEntity<Q> updateQ(@RequestBody Q q) throws URISyntaxException {
        log.debug("REST request to update Q : {}", q);
        if (q.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Q result = qService.save(q);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, q.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /qs} : get all the qs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qs in body.
     */
    @GetMapping("/qs")
    public List<Q> getAllQS() {
        log.debug("REST request to get all QS");
        return qService.findAll();
    }

    /**
     * {@code GET  /qs/:id} : get the "id" q.
     *
     * @param id the id of the q to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the q, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/qs/{id}")
    public ResponseEntity<Q> getQ(@PathVariable Long id) {
        log.debug("REST request to get Q : {}", id);
        Optional<Q> q = qService.findOne(id);
        return ResponseUtil.wrapOrNotFound(q);
    }

    /**
     * {@code DELETE  /qs/:id} : delete the "id" q.
     *
     * @param id the id of the q to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/qs/{id}")
    public ResponseEntity<Void> deleteQ(@PathVariable Long id) {
        log.debug("REST request to delete Q : {}", id);
        qService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/qs?query=:query} : search for the q corresponding
     * to the query.
     *
     * @param query the query of the q search.
     * @return the result of the search.
     */
    @GetMapping("/_search/qs")
    public List<Q> searchQS(@RequestParam String query) {
        log.debug("REST request to search QS for query {}", query);
        return qService.search(query);
    }
}
