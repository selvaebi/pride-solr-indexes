package uk.ac.ebi.pridesolr.indexes.pride.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.pride.solr.indexes.pride.model.SolrProject;
import uk.ac.ebi.pride.solr.indexes.pride.repository.SolrProjectRepository;

import java.util.Collection;

/**
 * The SolrprojectRepository Test of repository.
 *
 * @author Yasset Perez-Riverol
 * @version $Id$
 *
 */

@RunWith(SpringRunner.class)
public class SolrProjectRepositoryTest {

    @Autowired
    SolrProjectRepository projectRepository;

    @Test
    public void queryProjects(){
        Iterable<SolrProject> projects = projectRepository.findAll();
        if (projects instanceof Collection<?>) {
            Assert.assertEquals("Number of projects is 0", 0, ((Collection<?>)projects).size());
        }

    }

}
