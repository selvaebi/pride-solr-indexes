package uk.ac.ebi.pride.solr.indexes.pride.config;


import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;


@Configuration
@EnableSolrRepositories(basePackages = "uk.ac.ebi.pride.solr.indexes.pride.repository", schemaCreationSupport = false)
@ComponentScan(basePackages = "uk.ac.ebi.pride.solr.indexes.pride.services")
public class HttpSolrConfiguration {

    @Value("${solr.url}")
    private String solrURL;

    /** LOGGER to trace all the error and messages **/
    static Logger LOGGER = LoggerFactory.getLogger(HttpSolrConfiguration.class);

    @Bean
    public SolrTemplate solrTemplate() {
        return new SolrTemplate(new HttpSolrClient.Builder().withBaseSolrUrl(solrURL).build());
    }
}
