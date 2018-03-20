package uk.ac.ebi.pridesolr.indexes.pride.repository;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

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
