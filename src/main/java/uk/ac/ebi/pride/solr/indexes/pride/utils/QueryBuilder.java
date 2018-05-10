package uk.ac.ebi.pride.solr.indexes.pride.utils;

import org.springframework.data.solr.core.DefaultQueryParser;
import org.springframework.data.solr.core.QueryParser;
import org.springframework.data.solr.core.TermsQueryParser;
import org.springframework.data.solr.core.query.*;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectFieldEnum;

import java.util.Arrays;
import java.util.List;

/**
 * @author ypriverol
 */
public class QueryBuilder {

    /**
     *
     * @param keywords List of keywords
     * @return
     */
    public static HighlightQuery prideProjectQueryBuild(List<String> keywords){
        HighlightQuery highlightQuery = new SimpleHighlightQuery();
        Criteria simpleCriteria = new SimpleStringCriteria("*:*");
        final Criteria criteriaGlobal = new Criteria(PrideProjectFieldEnum.ACCESSION.getValue()).contains(keywords);
        Arrays.asList(PrideProjectFieldEnum.values()).stream().filter( x-> x.getValue() != PrideProjectFieldEnum.ACCESSION.getValue()).forEach(attribute -> {
            Criteria criteria = new Criteria(attribute.getValue()).in(keywords);
            criteriaGlobal.or(criteria);

        });

//        Criteria criteria = new Criteria();
//        Arrays.asList(PrideProjectFieldEnum.values()).forEach(x -> {
//            criteria.or(x.getValue()).is(keywords.get(0));
//            for(int i =1; i < keywords.size(); i++){
//                criteria.or(x.getValue()).is(keywords.get(i));
//            }
//        });
        highlightQuery.addCriteria(simpleCriteria.and(criteriaGlobal));
        return highlightQuery;
    }
}
