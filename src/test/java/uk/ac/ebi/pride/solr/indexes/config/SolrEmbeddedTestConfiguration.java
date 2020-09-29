package uk.ac.ebi.pride.solr.indexes.config;


import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.support.EmbeddedSolrServerFactory;
import org.springframework.test.context.TestPropertySource;
import org.xml.sax.SAXException;
import uk.ac.ebi.pride.solr.indexes.services.SolrProjectService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 *
 * @author ypriverol
 * @version $Id$
 */
@SpringBootApplication
@EnableSolrRepositories(basePackages = "uk.ac.ebi.pride.solr.indexes.repository")
@TestPropertySource(locations = "classpath:application.properties")
public class SolrEmbeddedTestConfiguration extends AbstractSolrConfiguration {

    @Value("${solr.Home}")
    private String solrConfDir;

    SolrProjectService repo;

    @Bean
    public EmbeddedSolrServer solrServerEmbedded() throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
        Path solrTempDirectory = Files.createTempDirectory("");
        String testURL = new File(Objects.requireNonNull(SolrEmbeddedTestConfiguration.class.getClassLoader().getResource(solrConfDir)).toURI()).getAbsolutePath();
        FileUtils.copyDirectory(new File(testURL), solrTempDirectory.toFile());
        final EmbeddedSolrServerFactory embeddedSolrServerFactory = new EmbeddedSolrServerFactory(solrTempDirectory.toString());
        return embeddedSolrServerFactory.getSolrClient();
    }

    @Bean
    public SolrTemplate solrTemplate() throws ParserConfigurationException, SAXException, IOException, URISyntaxException {
        return new SolrTemplate(solrServerEmbedded());
    }

    /**
     * Remove test data when context is shut down.
     */
    @PreDestroy
    public void deleteDocumentsOnShutdown() {
        repo.deleteAll();
    }

    /**
     * Initialize Solr instance with test data once context has started.
     */
    @PostConstruct
    public void initWithTestData() {
        repo.deleteAll(); // This needs to be added here to avoid
        doInitTestData(repo);
    }


    @Test
    public void simpleCreation(){
        repo.findAll().forEach(System.out::println);
    }

}
