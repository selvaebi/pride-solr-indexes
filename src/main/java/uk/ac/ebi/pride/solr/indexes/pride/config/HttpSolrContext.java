package uk.ac.ebi.pride.solr.indexes.pride.config;


import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories("uk.ac.ebi.pride.solr.indexes.pride.repository")
@Profile("solr-prod")
public class HttpSolrContext extends SolrContext{

//    @Value("${solr.url}")
//    private String solrURL;
//
//    @Bean
//    public SolrClient embeddedSolrServer() throws Exception {
//        return new HttpSolrClient(solrURL);
//    }
//
//    @Bean
//    public SolrTemplate solrTemplate() throws Exception {
//        return new SolrTemplate(embeddedSolrServer());
//    }
}
