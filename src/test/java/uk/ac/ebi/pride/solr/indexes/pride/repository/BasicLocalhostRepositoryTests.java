package uk.ac.ebi.pride.solr.indexes.pride.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.pride.solr.indexes.pride.config.SolrLocalhostTestConfiguration;
import uk.ac.ebi.pride.solr.indexes.pride.utils.RequiresSolrServer;

/**
 * @author ypriverol
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SolrLocalhostTestConfiguration.class)
public class BasicLocalhostRepositoryTests {

	public static RequiresSolrServer requiresRunningServer = RequiresSolrServer.onLocalhost();

	@Autowired
	SolrProjectRepository repository;

	/**
	 * Finds all entries using a single request.
	 */
	@Test
	public void findAll() {
		repository.findAll().forEach(x -> {
			System.out.println("Accession: " + x.getAccession() + " -- Title: " + x.getTitle());});
	}

	/**
	 * Pages through all entries using cursor marks. Have a look at the Solr console output to see iteration steps.
	 */
	@Test
	public void findAllUsingDeepPagination() {
		//repository.findAllUsingCursor().forEachRemaining(System.out::println);
	}
}
