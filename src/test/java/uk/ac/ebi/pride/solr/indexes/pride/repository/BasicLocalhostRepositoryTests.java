package uk.ac.ebi.pride.solr.indexes.pride.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.query.result.FacetAndHighlightPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.pride.solr.indexes.pride.config.SolrLocalhostTestConfiguration;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectField;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrDataset;
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

	@Autowired



	/** Finds all entries using a single request. */
	@Test
	public void findAll() {
		repository.findAll().forEach(x -> System.out.println("Accession: " + x.getAccession() + " -- Title: " + x.getTitle()));

	}

	/** Finds all entries using a single request by Cursor */
	@Test
	public void findCursorAll() {
		repository.findAllUsingCursor().forEachRemaining(x -> System.out.println("Accession: " + x.getAccession() + " -- Title: " + x.getTitle()));
	}

	/** Find the dataset for specific accession **/
	@Test
	public void findByAccession(){
		PrideSolrDataset x = repository.findByAccession("PXD000001");
		System.out.println("Accession: " + x.getAccession() + " -- Title: " + x.getTitle());
	}

	@Test
	public void findProjectsByKey(){
		HighlightPage<PrideSolrDataset> page = repository.findByKeyword("*:*", null, PrideProjectField.ACCESSION, new PageRequest(1, 10));
		page.forEach(System.out::println);
	}

	@Test
	public void findProjectsByKeyFacet(){

		FacetAndHighlightPage<PrideSolrDataset> page = repository.findAllWithFacetIgnoreCase(new PageRequest(0, 10));

		// Print all the projects search
		page.forEach( x-> {
			System.out.println(x.toString());
		});

		//Print Facets
		page.getAllFacets().stream().forEach(x -> {
			System.out.println(x.getContent());
		});
	}

	@Test
	public void finadAllAndaFacet(){
		FacetAndHighlightPage<PrideSolrDataset> projects = repository.findAllWithFacetIgnoreCase(new PageRequest(1, 10));
		Assert.assertEquals(projects.getFacetResultPage(PrideProjectField.PROJECT_PUBLICATION_DATE).getContent().size(), 1);
		Assert.assertEquals(projects.getFacetResultPage(PrideProjectField.PROJECT_SUBMISSION_DATE).getContent().size(), 1);
		Assert.assertEquals(projects.getFacetResultPage(PrideProjectField.PROJECT_UPDATED_DATE).getContent().size(), 1);
	}
}
