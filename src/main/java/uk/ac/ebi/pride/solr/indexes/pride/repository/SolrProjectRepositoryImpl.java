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

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SuggesterResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.params.FacetParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.Cursor;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.util.MultiValueMap;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectField;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectFieldEnum;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;
import uk.ac.ebi.pride.solr.indexes.pride.utils.PrideSolrConstants;
import uk.ac.ebi.pride.solr.indexes.pride.utils.QueryBuilder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
	 * @param page
	 * @return
	 */
	@Override
	public HighlightPage<PrideSolrProject> findByKeyword(List<String> keywords, MultiValueMap<String, String> filters, Pageable page, String dateGap) {
		HighlightQuery highlightQuery = new SimpleHighlightQuery();
		highlightQuery = (HighlightQuery) QueryBuilder.keywordORQuery(highlightQuery, keywords, filters, dateGap);
		highlightQuery.setPageRequest(page);

		HighlightOptions highlightOptions = new HighlightOptions();
        Arrays.stream(PrideProjectFieldEnum.values()).filter(PrideProjectFieldEnum::getHighlight).forEach(x-> highlightOptions.addField(x.getValue()));
		highlightOptions.setFragsize(PrideSolrConstants.DEFAULT_FRAGMENT_SIZE);
		highlightOptions.setNrSnipplets(PrideSolrConstants.DEFAULT_NRSNIPPLETS_SIZE);
        highlightQuery.setHighlightOptions(highlightOptions);


        if(highlightQuery.getSort() == null)
		    highlightQuery.addSort(new Sort(Sort.Direction.DESC, PrideProjectFieldEnum.ACCESSION.getValue()));

		return solrTemplate.queryForHighlightPage(PrideProjectField.PRIDE_PROJECTS_COLLECTION_NAME, highlightQuery , PrideSolrProject.class);
	}

    @Override
    public FacetPage<PrideSolrProject> findFacetByKeyword(List<String> keywords, MultiValueMap<String, String> filters, Pageable page, Pageable facetPage, String dateGap) {

		FacetQuery facetQuery = new SimpleFacetQuery();
        facetQuery = (FacetQuery) QueryBuilder.keywordORQuery(facetQuery, keywords, filters, dateGap);
        facetQuery.setPageRequest(page);

        FacetOptions facetOptions = new FacetOptions();
        facetOptions.setPageable(facetPage);
		PrideSolrConstants.AllowedDateGapConstants gapConstants = PrideSolrConstants.AllowedDateGapConstants.findByString(dateGap);
        if( gapConstants != PrideSolrConstants.AllowedDateGapConstants.UNKONWN){
			Arrays.asList(PrideProjectFieldEnum.values())
					.stream()
					.filter(PrideProjectFieldEnum::getFacet)
					.forEach(facetField -> {
						if(facetField.getType() == PrideSolrConstants.ConstantsSolrTypes.DATE){
							try {
								Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2004-01-01");
								FacetOptions.FieldWithDateRangeParameters dateRange = new FacetOptions.FieldWithDateRangeParameters(facetField.getValue(), startDate, new Date(), gapConstants.value);
								dateRange.setHardEnd(true).setInclude(FacetParams.FacetRangeInclude.LOWER).setOther(FacetParams.FacetRangeOther.BEFORE);
								facetOptions.addFacetByRange(dateRange);
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}else{
							facetOptions.addFacetOnField(facetField.getValue());
						}

					});
		}else{
			Arrays.asList(PrideProjectFieldEnum.values())
					.stream()
					.filter(PrideProjectFieldEnum::getFacet)
					.forEach(facetField -> facetOptions.addFacetOnField(facetField.getValue()));
			facetOptions.setFacetMinCount(1);
		}


        facetQuery.setFacetOptions(facetOptions);


        if(facetQuery.getSort() == null)
            facetQuery.addSort(new Sort(Sort.Direction.DESC, PrideProjectFieldEnum.ACCESSION.getValue()));

        return solrTemplate.queryForFacetPage(PrideProjectField.PRIDE_PROJECTS_COLLECTION_NAME, facetQuery , PrideSolrProject.class);
    }

    @Override
	public FacetPage<PrideSolrProject> findAllFacetIgnoreCase(Pageable pageRequest) {
		FacetOptions facets = new FacetOptions().setPageable(pageRequest);
		for(PrideProjectFieldEnum field: PrideProjectFieldEnum.values())
			if(field.getFacet())
				facets.addFacetOnField(field.getValue());
		FacetQuery query = new SimpleFacetQuery(new SimpleStringCriteria("*:*")).setFacetOptions(facets);
		return solrTemplate.queryForFacetPage(PrideProjectField.PRIDE_PROJECTS_COLLECTION_NAME, query, PrideSolrProject.class);
	}

	@Override
	public Map<String,Double> findMoreLikeThisIds(String accession, Integer pageSize, Integer page) {
		Map<String, Double> solrProjectIds = new HashMap<>();
		SolrQuery queryParams = new SolrQuery();
		queryParams.setRows(pageSize);
		queryParams.setStart(page*pageSize);
     	queryParams.setMoreLikeThisFields(PrideProjectField.PROJECT_TILE, PrideProjectField.PROJECT_DESCRIPTION, PrideProjectField.INSTRUMENTS, PrideProjectField.ORGANISM,
				PrideProjectField.ORGANISM_PART, PrideProjectField.DISEASES, PrideProjectField.PROJECT_IDENTIFIED_PTM_STRING, PrideProjectField.PROJECT_DATA_PROTOCOL,
				PrideProjectField.PROJECT_SAMPLE_PROTOCOL, PrideProjectField.TEXT);
		queryParams.setQuery(PrideProjectField.ACCESSION + ":" + accession);
		try {
			QueryResponse q = solrTemplate.getSolrClient().query(PrideProjectField.PRIDE_PROJECTS_COLLECTION_NAME, queryParams);
			q.getMoreLikeThis().asShallowMap().values()
					.stream()
					.forEach(x -> {
						Iterator<SolrDocument> it = x.iterator();
						while(it.hasNext()){
							SolrDocument doc = it.next();
							solrProjectIds.put((String)doc.getFirstValue(PrideProjectField.ID), ((Float)doc.getFirstValue("score")).doubleValue());
						}
					});
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		return solrProjectIds;
	}

	@Override
	public Map<String, List<String>> findAutoComplete(String keyword){
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setRequestHandler("/suggest");
		solrQuery.setRows(10);
		solrQuery.set("suggest", "true");
		solrQuery.set("suggest.dictionary", "textSuggester");
		solrQuery.set("suggest.q", keyword);

		try {
			QueryResponse re = solrTemplate.getSolrClient().query(PrideProjectField.PRIDE_PROJECTS_COLLECTION_NAME, solrQuery);
			SuggesterResponse sr = re.getSuggesterResponse();
			return sr.getSuggestedTerms();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		return Collections.emptySortedMap();
	}
}
