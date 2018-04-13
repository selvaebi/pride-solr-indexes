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
package uk.ac.ebi.pride.solr.indexes.pride.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.Cursor;
import org.springframework.data.solr.core.query.result.HighlightPage;
import uk.ac.ebi.pride.archive.dataprovider.utils.Tuple;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectField;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectFieldEnum;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;
import uk.ac.ebi.pride.solr.indexes.pride.utils.RequiresSolrServer;

/**
 * Implementation of {@link SolrProjectRepositoryCustom}.
 *
 * @author ypriverol
 * @version $Id$
 *
 */
class SolrProjectRepositoryImpl implements SolrProjectRepositoryCustom {

	/** Solr Template **/
	final SolrTemplate solrTemplate;

	/** Logger use to query and filter the data **/
	static Logger LOGGER = LoggerFactory.getLogger(RequiresSolrServer.class);

	@Autowired
	public SolrProjectRepositoryImpl(SolrTemplate solrTemplate) {
		this.solrTemplate = solrTemplate;
	}

	@Override
	public Cursor<PrideSolrProject> findAllUsingCursor() {
		return solrTemplate.queryForCursor(PrideProjectField.PRIDE_PROJECTS_COLLECTION_NAME, new SimpleQuery("*:*").addSort(Sort.by("id")), PrideSolrProject.class);
	}

	/**
	 * This method will search Pride projects using the keywords defined by pride and will filter but only a set of fields
	 *
	 * @param keyword
	 * @param sortField
	 * @param page
	 * @return
	 */
	@Override
	public HighlightPage<PrideSolrProject> findByKeyword(String keyword, Tuple<String, String> filters, String sortField, Pageable page) {

		PrideProjectFieldEnum field = PrideProjectFieldEnum.findKey(sortField);
		if(field == null){
			LOGGER.warn("The following field for sorting ids not found -- " + sortField + " Accession will be use by Default");
			sortField = PrideProjectFieldEnum.ACCESSION.getValue();
		}

		Query query = new SimpleQuery(keyword).addSort(Sort.by(sortField));
		return solrTemplate.query(PrideProjectField.PRIDE_PROJECTS_COLLECTION_NAME, query , PrideSolrProject.class);
	}
}
