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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import uk.ac.ebi.pride.solr.indexes.pride.repository.SolrProjectRepository;

import java.io.File;
import java.net.URISyntaxException;

/**
 * @author ypriverol
 */
@Configuration
@EnableSolrRepositories(basePackages = "uk.ac.ebi.pride.solr.indexes.pride.repository", schemaCreationSupport = false)
public class SolrLocalhostTestConfiguration extends AbstractSolrConfiguration {

	@Autowired
	SolrProjectRepository repo;

	/** LOGGER to trace all the error and messages **/
	static Logger LOGGER = LoggerFactory.getLogger(SolrLocalhostTestConfiguration.class);

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
			String filePathTwo = new File(SolrLocalhostTestConfiguration.class.getClassLoader().getResource("submissions/pride-submission-two.px").toURI()).getAbsolutePath();
			String filePathThree = new File(SolrLocalhostTestConfiguration.class.getClassLoader().getResource("submissions/pride-submission-three.px").toURI()).getAbsolutePath();
			doInitTestData(repo, filePathOne, filePathTwo, filePathThree);
		}catch (URISyntaxException e){
			/** LOGGER to trace all the error and meessages **/
			LOGGER.error("The provided files for testing are wrong -- " + e.getMessage(), e);
		}

	}

}
