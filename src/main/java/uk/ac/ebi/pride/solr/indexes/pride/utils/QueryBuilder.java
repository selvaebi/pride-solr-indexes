package uk.ac.ebi.pride.solr.indexes.pride.utils;

import org.springframework.data.solr.core.DefaultQueryParser;
import org.springframework.data.solr.core.QueryParser;
import org.springframework.data.solr.core.TermsQueryParser;
import org.springframework.data.solr.core.query.*;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectFieldEnum;

import java.util.Arrays;
import java.util.List;

/**
 * Query Builder helps to build the query for the solr services and repositories.
 *
 * @author ypriverol
 */
public class QueryBuilder {

    /**
     *
     * @param keywords List of Keywords to be combined.
     * @return HighlightQuery
     */
    public static HighlightQuery prideProjectQueryBuild(List<String> keywords){
        HighlightQuery highlightQuery = new SimpleHighlightQuery();

        //The query starts wit the first key + Accession
        Criteria simpleCriteria = Criteria.where(PrideProjectFieldEnum.ACCESSION.getValue()).contains(keywords.get(0));
        for(int i=1; i<keywords.size(); i++)
            simpleCriteria.or(PrideProjectFieldEnum.ACCESSION.getValue()).contains(keywords.get(i));

//        final Criteria criteriaGlobal = new Criteria(PrideProjectFieldEnum.ACCESSION.getValue()).contains(keywords);
//        Arrays.stream(PrideProjectFieldEnum.values()).filter(x-> !x.getValue().equals(PrideProjectFieldEnum.ACCESSION.getValue())).forEach(attribute -> {
//            Criteria criteria = new Criteria(attribute.getValue()).in(keywords);
//            criteriaGlobal.or(criteria);
//
//        });
        highlightQuery.addCriteria(simpleCriteria);
        return highlightQuery;
    }
}
