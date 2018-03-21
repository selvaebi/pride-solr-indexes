package uk.ac.ebi.pride.solr.indexes.pride.config;


import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.support.EmbeddedSolrServerFactory;

import javax.xml.parsers.ParserConfigurationException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Configuration
@EnableSolrRepositories("uk.ac.ebi.pride.solr.indexes.pride.repository")
@Profile("test")
public class EmbeddedSolrContext {

	@Value("${solr.embedded.url}")
	private String embeddedSolrUrl;

	@Value("${solr.embedded.core}")
	private String core;

	@Bean
	public SolrClient embeddedSolrServer() throws Exception {
		EmbeddedSolrServerFactory factory = new EmbeddedSolrServerFactory(embeddedSolrUrl);
		return factory.getSolrClient(core);
	}

	@Bean
	public SolrTemplate solrTemplate() throws Exception {
		return new SolrTemplate(embeddedSolrServer());
	}
}
