package uk.ac.ebi.pride.solr.indexes.pride.utils;

import org.springframework.data.solr.core.convert.DateTimeConverters;
import org.springframework.data.solr.core.query.*;
import org.springframework.util.MultiValueMap;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectField;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectFieldEnum;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Query Builder helps to build the query for the solr services and repositories.
 *
 * @author ypriverol
 */
public class QueryBuilder {

    /**
     *
     * @param keywords List of Keywords to be combined. with OR.
     * @param filters Key, Value pair Map where the key is the name of the property and the value if the value to filter.
     * @return HighlightQuery
     */
    public static HighlightQuery keywordORQuery(List<String> keywords, MultiValueMap<String, String> filters){
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
        if(!filters.isEmpty()){
            for(String filter: filters.keySet()){
                conditions = convertStringToCriteria(conditions, filter, filters.getFirst(filter));
            }
        }
        highlightQuery.addCriteria(conditions);
        return highlightQuery;
    }

    /**
     * This function helps to convert filters from dates into proper Date.
     * @param key key of the field
     * @param value value of the filter
     * @return Object to Filter
     */
    public static Criteria convertStringToCriteria(Criteria conditions, String key, String value){
        for(PrideProjectFieldEnum field: PrideProjectFieldEnum.values()){
            if(field.getValue().equalsIgnoreCase(key) && field.getType().getType().equalsIgnoreCase(PrideSolrConstants.ConstantsSolrTypes.DATE.getType())){
                try {
                    Date date = new SimpleDateFormat("yyyyy-mm-dd").parse(value);
                    String start = DateTimeConverters.JavaDateConverter.INSTANCE.convert(date)+"/DAY";
                    String end = DateTimeConverters.JavaDateConverter.INSTANCE.convert(date)+"/DAY";
                    if(conditions == null){
                        conditions = new Criteria(key).between(start, end);
                    }else{
                        conditions = conditions.and(new Criteria(key).between(start, end));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else if(field.getValue().equalsIgnoreCase(key)){
                if(conditions == null){
                    conditions = new Criteria(key).is(value);
                }else{
                    conditions = conditions.and(new Criteria(key).is(value));
                }
            }
        }
        return conditions;
    }
}
