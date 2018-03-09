package uk.ac.ebi.pridesolr.indexes.pride.repository;

import org.apache.solr.client.solrj.SolrServer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.support.EmbeddedSolrServerFactory;
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

@Configuration
@EnableSolrRepositories(multicoreSupport = true)
@SpringBootTest

public class SolrProjectEmbeddedServer {





}
