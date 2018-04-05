package uk.ac.ebi.pride.solr.indexes.pride.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;


@Configuration
@EnableSolrRepositories("uk.ac.ebi.pride.solr.indexes.pride.repository")
@Profile("solr-test")
@ComponentScan(basePackages = "uk.ac.ebi.pride.solr.indexes.pride.service")
public class EmbeddedSolrContext{

	@Value("${solr.embedded.url}")
	private String embeddedSolrUrl;

	@Value("${solr.embedded.core}")
	private String core;

//	@Bean
//	public SolrClient solrServer() throws Exception {
//		EmbeddedSolrServerFactory factory = new EmbeddedSolrServerFactory(embeddedSolrUrl);
//        EmbeddedSolrServer server = factory.getSolrClient();
//		return ;
//	}

//	@Bean
//	public SolrTemplate solrTemplate() throws Exception {
//		return new SolrTemplate(solrServer());
//	}
}
