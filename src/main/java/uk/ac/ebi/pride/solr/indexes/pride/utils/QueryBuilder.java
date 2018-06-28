package uk.ac.ebi.pride.solr.indexes.pride.utils;

import org.springframework.data.solr.core.query.*;
import org.springframework.util.MultiValueMap;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectFieldEnum;
import uk.ac.ebi.pride.utilities.util.DateUtils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public static Query keywordORQuery(Query query, List<String> keywords, MultiValueMap<String, String> filters){

        if(query == null)
            query = new SimpleFacetAndHighlightQuery();

        keywords =  processKeywords(keywords);

        Criteria conditions = null;
        if(keywords.contains("*")){
            conditions = new SimpleStringCriteria("*:*");
        }else {
            for (String word: keywords) {
                for(PrideProjectFieldEnum field: PrideProjectFieldEnum.values()){
                    if(conditions == null){
                        conditions = new Criteria(field.getValue()).contains(word);
                    }else{
                        conditions = conditions.or(new Criteria(field.getValue()).contains(word));
                    }
                }
            }
        }
        if(conditions != null)
            query.addCriteria(conditions);

        Criteria filterCriteria = null;

        if(!filters.isEmpty()){
            for(String filter: filters.keySet()){
                filterCriteria = convertStringToCriteria(filterCriteria, filter, filters.getFirst(filter));
            }
        }
        if(filterCriteria != null)
            query.addFilterQuery(new SimpleFilterQuery(filterCriteria));

        return query;
    }

    /**
     * This function process all the keywords provided to the service to remove un-wanted characters.
     * If the keyword containes the following character : we skip the word.
     *
     * @param keywords Keywords List
     * @return Return keyword List
     */
    private static List<String> processKeywords(List<String> keywords) {
        keywords = keywords.stream().map( x-> {
            String[] keywordValues = x.split(":");
            if(keywordValues.length == 2){
                return keywordValues[1];
            }else if(keywordValues.length > 2)
                return null;
            return x;
        }).filter(x -> (x!=null && !x.trim().isEmpty())).collect(Collectors.toList());
        return keywords;
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
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(value);
                    Date startDate = DateUtils.atStartOfDay(date);
                    if(conditions == null){
                        conditions = new Criteria(key).is(startDate);
                    }else{
                        conditions = conditions.and(new Criteria(key).between(date, date));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else if(field.getValue().equalsIgnoreCase(key)){
                String[] values = value.split("\\s+");
                if(conditions == null){
                    conditions = new Criteria(key).contains(values);
                }else{
                    conditions = conditions.and(new Criteria(key).contains(values));
                }
            }
        }
        return conditions;
    }
}
