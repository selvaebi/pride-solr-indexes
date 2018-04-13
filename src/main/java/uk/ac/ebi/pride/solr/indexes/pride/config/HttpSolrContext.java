package uk.ac.ebi.pride.solr.indexes.pride.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories("uk.ac.ebi.pride.solr.indexes.pride.repository")
@Profile("solr-prod")
public class HttpSolrContext {

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
