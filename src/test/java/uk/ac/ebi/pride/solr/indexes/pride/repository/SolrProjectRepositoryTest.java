package uk.ac.ebi.pride.solr.indexes.pride.repository;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;
import uk.ac.ebi.pride.solr.indexes.pride.model.SolrProject;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author Yasset Perez-Riverol
 * @version $Id$
 */

@RunWith(MockitoJUnitRunner.class)
public class SolrProjectRepositoryTest {


    private SimpleSolrRepository<SolrProject, String> repository;

    @Mock
    private SolrOperations solrOperationsMock;

    @Before
    public void setUp() {
        repository = new SimpleSolrRepository<>(solrOperationsMock, SolrProject.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInitRepositoryWithNullSolrOperations() {
        new SimpleSolrRepository<SolrProject, String>(null, (Class) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInitRepositoryWithNullEntityClass() {
        new SimpleSolrRepository<SolrProject, String>(
                new SolrTemplate(new HttpSolrClient("http://localhost:8080/solr"), null), (Class) null);
    }

    @Test
    public void testInitRepository() {
        repository = new SimpleSolrRepository<>(new SolrTemplate(new HttpSolrClient("http://localhost:8080/solr"), null),
                SolrProject.class);
        Assert.assertEquals(SolrProject.class, repository.getEntityClass());
    }

    @Test
    public void testFindAllByIdQuery() {
        Mockito.when(solrOperationsMock.count(Mockito.any(SolrDataQuery.class))).thenReturn(12345l);

        repository.findAll(Arrays.asList("id-1", "id-2", "id-3"));
        ArgumentCaptor<Query> captor = ArgumentCaptor.forClass(Query.class);

        Mockito.verify(solrOperationsMock, Mockito.times(1)).count(captor.capture());
        Mockito.verify(solrOperationsMock, Mockito.times(1)).queryForPage(captor.capture(),
                Mockito.eq(SolrProject.class));

        Assert.assertNull(captor.getAllValues().get(0).getPageRequest());
        Assert.assertEquals(12345, captor.getAllValues().get(1).getPageRequest().getPageSize());

    }


}