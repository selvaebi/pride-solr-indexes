package uk.ac.ebi.pride.solr.indexes.pride.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.pride.archive.dataprovider.utils.Tuple;
import uk.ac.ebi.pride.solr.indexes.pride.config.SolrLocalhostTestConfiguration;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectField;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;
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

	/** Finds all entries using a single request. */
	@Test
	public void findAll() {
		repository.findAll().forEach(x -> {
			System.out.println("Accession: " + x.getAccession() + " -- Title: " + x.getTitle());});

	}

	/** Finds all entries using a single request by Cursor */
	@Test
	public void findCursorAll() {
		repository.findAllUsingCursor().forEachRemaining(x -> {
			System.out.println("Accession: " + x.getAccession() + " -- Title: " + x.getTitle());
		});
	}

	/** Find the dataset for specific accession **/
	@Test
	public void findByAccession(){
		PrideSolrProject x = repository.findByAccession("PXD000001");
		System.out.println("Accession: " + x.getAccession() + " -- Title: " + x.getTitle());
	}

	@Test
	public void findProjectsByKey(){
		repository.findByKeyword("*:*", null, PrideProjectField.ACCESSION, new PageRequest(1,10));
	}


}
