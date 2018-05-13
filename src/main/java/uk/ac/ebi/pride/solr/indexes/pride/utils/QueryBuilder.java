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

//        //The query starts wit the first key + Accession
//        Criteria simpleCriteria = Criteria.where(PrideProjectFieldEnum.ACCESSION.getValue()).contains(keywords.get(0));
//        for(int i=1; i<keywords.size(); i++)
//            simpleCriteria.or(PrideProjectFieldEnum.ACCESSION.getValue()).contains(keywords.get(i));

        Criteria conditions = null;

        for (String word: keywords) {
            if (conditions == null) {
                conditions = new Criteria(PrideProjectFieldEnum.ACCESSION.getValue()).contains(word)
                        .or(new Criteria(PrideProjectFieldEnum.PROJECT_TILE.getValue()).contains(word));
            }
            else {
                conditions = conditions.or(new Criteria(PrideProjectFieldEnum.ACCESSION.getValue()).contains(word))
                        .or(new Criteria(PrideProjectFieldEnum.PROJECT_TILE.getValue()).contains(word));
            }
        }
        highlightQuery.addCriteria(conditions);
        return highlightQuery;
    }
}
