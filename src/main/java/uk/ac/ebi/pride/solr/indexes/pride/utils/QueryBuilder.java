package uk.ac.ebi.pride.solr.indexes.pride.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.solr.core.query.*;
import org.springframework.util.MultiValueMap;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectFieldEnum;
import uk.ac.ebi.pride.utilities.util.DateUtils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * Query Builder helps to build the query for the solr services and repositories.
 *
 * @author ypriverol
 */
@Slf4j
public class QueryBuilder {

    private final static SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    /**
     *
     * @param keywords List of Keywords to be combined. with OR.
     * @param filters Key, Value pair Map where the key is the name of the property and the value if the value to filter.
     * @return HighlightQuery
     */
    public static Query keywordORQuery(Query query, List<String> keywords, MultiValueMap<String, String> filters, String dateGap){

        if(query == null)
            query = new SimpleFacetAndHighlightQuery();

        keywords =  processKeywords(keywords);
        boolean isAndConditionRequired=false;
        Criteria conditions = null;
        if(keywords.contains("*")){
            conditions = new SimpleStringCriteria("*:*");
        }else {
            for (String word: keywords) {
                for(PrideProjectFieldEnum field: PrideProjectFieldEnum.values()){
                    if(field.getType() != PrideSolrConstants.ConstantsSolrTypes.DATE){
                        if(conditions == null){
                            if(word.contains(" ")) {
                                conditions = new Criteria(field.getValue()).contains(word.split(" "));
                                isAndConditionRequired=true;
                            }
                            else
                                conditions = new Criteria(field.getValue()).contains(word);
                        }else{
                            if(word.contains(" ")) {
                                conditions = conditions.or(new Criteria(field.getValue()).contains(word.split(" ")));
                                isAndConditionRequired=true;
                            }
                            else
                                conditions = conditions.or(new Criteria(field.getValue()).contains(word));
                        }
                    }

                }
            }
        }
        if(conditions != null) {
            /*
                Default solr operation is OR.
                Example => field:(a b) translates into field:(a OR b)
                we need to make this  field:(a AND b)
             */
            if (isAndConditionRequired)
                query.setDefaultOperator(Query.Operator.AND);

            query.addCriteria(conditions);
        }

        Criteria filterCriteria = null;

        if(!filters.isEmpty()){
            for(String filter: filters.keySet()){
                filterCriteria = convertStringToCriteria(filterCriteria, filter, filters.get(filter), dateGap);
            }
        }
        if(filterCriteria != null)
            query.addFilterQuery(new SimpleFilterQuery(filterCriteria));

        return query;
    }

    /**
     * This function process all the keywords provided to the service to remove un-wanted characters.
     * If the keyword contains the following character : we skip the word.
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
     * @param values value of the filter
     * @return Object to Filter
     */
    public static Criteria convertStringToCriteria(Criteria conditions, String key, List<String> values, String dateGap){

        Criteria currentCriteria = null;
        for(String value: values){
            for(PrideProjectFieldEnum field: PrideProjectFieldEnum.values()){
                if(field.getValue().equalsIgnoreCase(key) && field.getType().getType().equalsIgnoreCase(PrideSolrConstants.ConstantsSolrTypes.DATE.getType())){
                    try {
                        Date date = parseInitialDate(value, dateGap);
                        Date endDate = transformEndDate(date, dateGap);
                        Date startDate = DateUtils.atStartOfDay(date);
                        if(currentCriteria == null){
                            currentCriteria = new Criteria(key).between(startDate, endDate);
                        }else{
                            currentCriteria = currentCriteria.or(new Criteria(key).between(startDate, endDate));
                        }
                    } catch (ParseException e) {
                        log.error("The format provided by the Date filter is not allowed, it should follow the the schema: yyyy-MM-dd");
                        e.printStackTrace();

                    }
                }else if(field.getValue().equalsIgnoreCase(key)){
                    value = value.trim();
                    if(currentCriteria == null){
                        currentCriteria = new Criteria(key).contains(value);
                    }else{
                        currentCriteria = conditions.or(new Criteria(key).contains(value));
                    }
                }
            }
        }
        if(currentCriteria != null){
            if(conditions == null)
                conditions = currentCriteria;
            else
                conditions.and(currentCriteria);
        }
        return conditions;
    }

    private static Date parseInitialDate(String value, String dateGap) throws ParseException {
        Date date;
        if(PrideSolrConstants.AllowedDateGapConstants.YEARLY.value.equalsIgnoreCase(dateGap)) {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(value.substring(0, 4) + "-" + "01" + "-" + "01");
        }else if(PrideSolrConstants.AllowedDateGapConstants.MONTHLY.value.equalsIgnoreCase(dateGap)){
            date = new SimpleDateFormat("yyyy-MM-dd").parse(value.substring(0, 7) + "-" + "01");
        }else{
            date = new SimpleDateFormat("yyyy-MM-dd").parse(value);
        }
        return dateFormatUTC.parse(dateFormatUTC.format(date));

    }

    private static Date transformEndDate(Date date, String dateGap) throws ParseException {
        LocalDateTime dateTime = date.toInstant().atZone(TimeZone.getTimeZone("UTC").toZoneId()).toLocalDateTime();
        if(PrideSolrConstants.AllowedDateGapConstants.DAILY.value.equalsIgnoreCase(dateGap))
            dateTime = dateTime.plusDays(1);
        else if(PrideSolrConstants.AllowedDateGapConstants.MONTHLY.value.equalsIgnoreCase(dateGap))
            dateTime = dateTime.plusMonths(1);
        else if(PrideSolrConstants.AllowedDateGapConstants.YEARLY.value.equalsIgnoreCase(dateGap))
            dateTime = dateTime.plusYears(1);
        date = java.util.Date.from(dateTime
                .atZone(TimeZone.getTimeZone("UTC").toZoneId())
                .toInstant());
        return dateFormatUTC.parse(dateFormatUTC.format(date));
    }
}
