package uk.ac.ebi.pride.solr.indexes.pride.utils;

import org.springframework.data.solr.core.query.*;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectField;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectFieldEnum;


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
    public static HighlightQuery keywordORQuery(List<String> keywords){
        HighlightQuery highlightQuery = new SimpleHighlightQuery();

        Criteria conditions = null;

        for (String word: keywords) {
            for(PrideProjectFieldEnum field: PrideProjectFieldEnum.values()){
                if(conditions == null){
                    conditions = new Criteria(field.getValue()).contains(word);
                }else{
                    conditions = conditions.or(new Criteria(field.getValue()).contains(word));
                }
            }
        }
        highlightQuery.addCriteria(conditions);
        return highlightQuery;
    }
}
