package mr.iscae.web.rest;

import mr.iscae.FintechProject1App;
import mr.iscae.domain.Q;
import mr.iscae.repository.QRepository;
import mr.iscae.repository.search.QSearchRepository;
import mr.iscae.service.QService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link QResource} REST controller.
 */
@SpringBootTest(classes = FintechProject1App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class QResourceIT {

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    @Autowired
    private QRepository qRepository;

    @Autowired
    private QService qService;

    /**
     * This repository is mocked in the mr.iscae.repository.search test package.
     *
     * @see mr.iscae.repository.search.QSearchRepositoryMockConfiguration
     */
    @Autowired
    private QSearchRepository mockQSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQMockMvc;

    private Q q;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Q createEntity(EntityManager em) {
        Q q = new Q()
            .countryName(DEFAULT_COUNTRY_NAME);
        return q;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Q createUpdatedEntity(EntityManager em) {
        Q q = new Q()
            .countryName(UPDATED_COUNTRY_NAME);
        return q;
    }

    @BeforeEach
    public void initTest() {
        q = createEntity(em);
    }

    @Test
    @Transactional
    public void createQ() throws Exception {
        int databaseSizeBeforeCreate = qRepository.findAll().size();
        // Create the Q
        restQMockMvc.perform(post("/api/qs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(q)))
            .andExpect(status().isCreated());

        // Validate the Q in the database
        List<Q> qList = qRepository.findAll();
        assertThat(qList).hasSize(databaseSizeBeforeCreate + 1);
        Q testQ = qList.get(qList.size() - 1);
        assertThat(testQ.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);

        // Validate the Q in Elasticsearch
        verify(mockQSearchRepository, times(1)).save(testQ);
    }

    @Test
    @Transactional
    public void createQWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = qRepository.findAll().size();

        // Create the Q with an existing ID
        q.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQMockMvc.perform(post("/api/qs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(q)))
            .andExpect(status().isBadRequest());

        // Validate the Q in the database
        List<Q> qList = qRepository.findAll();
        assertThat(qList).hasSize(databaseSizeBeforeCreate);

        // Validate the Q in Elasticsearch
        verify(mockQSearchRepository, times(0)).save(q);
    }


    @Test
    @Transactional
    public void getAllQS() throws Exception {
        // Initialize the database
        qRepository.saveAndFlush(q);

        // Get all the qList
        restQMockMvc.perform(get("/api/qs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(q.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)));
    }
    
    @Test
    @Transactional
    public void getQ() throws Exception {
        // Initialize the database
        qRepository.saveAndFlush(q);

        // Get the q
        restQMockMvc.perform(get("/api/qs/{id}", q.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(q.getId().intValue()))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingQ() throws Exception {
        // Get the q
        restQMockMvc.perform(get("/api/qs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQ() throws Exception {
        // Initialize the database
        qService.save(q);

        int databaseSizeBeforeUpdate = qRepository.findAll().size();

        // Update the q
        Q updatedQ = qRepository.findById(q.getId()).get();
        // Disconnect from session so that the updates on updatedQ are not directly saved in db
        em.detach(updatedQ);
        updatedQ
            .countryName(UPDATED_COUNTRY_NAME);

        restQMockMvc.perform(put("/api/qs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQ)))
            .andExpect(status().isOk());

        // Validate the Q in the database
        List<Q> qList = qRepository.findAll();
        assertThat(qList).hasSize(databaseSizeBeforeUpdate);
        Q testQ = qList.get(qList.size() - 1);
        assertThat(testQ.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);

        // Validate the Q in Elasticsearch
        verify(mockQSearchRepository, times(2)).save(testQ);
    }

    @Test
    @Transactional
    public void updateNonExistingQ() throws Exception {
        int databaseSizeBeforeUpdate = qRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQMockMvc.perform(put("/api/qs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(q)))
            .andExpect(status().isBadRequest());

        // Validate the Q in the database
        List<Q> qList = qRepository.findAll();
        assertThat(qList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Q in Elasticsearch
        verify(mockQSearchRepository, times(0)).save(q);
    }

    @Test
    @Transactional
    public void deleteQ() throws Exception {
        // Initialize the database
        qService.save(q);

        int databaseSizeBeforeDelete = qRepository.findAll().size();

        // Delete the q
        restQMockMvc.perform(delete("/api/qs/{id}", q.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Q> qList = qRepository.findAll();
        assertThat(qList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Q in Elasticsearch
        verify(mockQSearchRepository, times(1)).deleteById(q.getId());
    }

    @Test
    @Transactional
    public void searchQ() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        qService.save(q);
        when(mockQSearchRepository.search(queryStringQuery("id:" + q.getId())))
            .thenReturn(Collections.singletonList(q));

        // Search the q
        restQMockMvc.perform(get("/api/_search/qs?query=id:" + q.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(q.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)));
    }
}
