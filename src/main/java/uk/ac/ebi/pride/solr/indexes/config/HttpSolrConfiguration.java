package uk.ac.ebi.pride.solr.indexes.config;


import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;


@Configuration
@EnableSolrRepositories(basePackages = "uk.ac.ebi.pride.solr.indexes.repository")
@ComponentScan(basePackages = "uk.ac.ebi.pride.solr.indexes.services")
public class HttpSolrConfiguration {

    @Value("${spring.data.solr.host}")
    private String solrURL;

    @Bean
    public SolrTemplate solrTemplate() {
        return new SolrTemplate(new HttpSolrClient.Builder().withBaseSolrUrl(solrURL).build());
    }
}
