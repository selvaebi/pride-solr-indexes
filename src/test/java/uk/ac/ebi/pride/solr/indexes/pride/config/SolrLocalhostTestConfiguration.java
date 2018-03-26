/*
 * Copyright 2014-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ebi.pride.solr.indexes.pride.config;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import uk.ac.ebi.pride.solr.indexes.pride.config.AbstractSolrConfiguration;
import uk.ac.ebi.pride.solr.indexes.pride.repository.SolrProjectRepository;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author ypriverol
 */
@SpringBootApplication
@EnableSolrRepositories(basePackages = "uk.ac.ebi.pride.solr.indexes.pride.repository", schemaCreationSupport = true)
public class SolrLocalhostTestConfiguration extends AbstractSolrConfiguration {

	@Autowired
	SolrProjectRepository repo;

	@Bean
	public SolrTemplate solrTemplate() {
		return new SolrTemplate(new HttpSolrClient.Builder().withBaseSolrUrl("http://localhost:8983/solr").build());
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

		try{
			String filePathOne = new File(SolrLocalhostTestConfiguration.class.getClassLoader().getResource("submissions/pride-submission-one.px").toURI()).getAbsolutePath();
			doInitTestData(repo);
		}catch (URISyntaxException e){

		}

	}

}
