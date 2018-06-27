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

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import org.springframework.data.solr.core.query.result.Cursor;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.util.MultiValueMap;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;

import java.util.List;

/**
 * Custom repository implementation to show special solr functions without {@link Repository} abstraction.
 *
 * @author Christoph Strobl
 */
interface SolrProjectRepositoryCustom {

	/**
	 * Use a {@link Cursor} to scroll through documents in index. <br />
	 * <strong>NOTE:</strong> Requires at least Solr 4.7.
	 *
	 * @return
	 */
	Cursor<PrideSolrProject> findAllUsingCursor();

	@Highlight(fragsize = 20, snipplets = 3)
	HighlightPage<PrideSolrProject> findByKeyword(List<String> keywords, MultiValueMap<String, String> filters, Pageable page);

	FacetPage<PrideSolrProject> findFacetByKeyword(List<String> keywords, MultiValueMap<String, String> filters, Pageable page, Pageable facetPage, String gap);

	/**
	 * Find all Projects with Facets
	 * @param pageRequest
	 * @return
	 */
	FacetPage<PrideSolrProject> findAllFacetIgnoreCase(Pageable pageRequest);
}
