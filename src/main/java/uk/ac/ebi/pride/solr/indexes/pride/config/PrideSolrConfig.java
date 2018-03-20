package uk.ac.ebi.pride.solr.indexes.pride.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import javax.annotation.Resource;
import java.net.MalformedURLException;

/**
 *
 * @author Yasset Perez-Riverol
 * @version $Id$
 */

@Configuration
@EnableSolrRepositories(basePackages = {"uk.ac.ebi.pride.solr.indexes.pride.repository"})
public class PrideSolrConfig {

    private @Resource

    Environment env;

    @Bean
    public SolrClient solrClient() throws MalformedURLException, IllegalStateException {
        return new HttpSolrClient(env.getRequiredProperty("solr.host"));
    }
}
