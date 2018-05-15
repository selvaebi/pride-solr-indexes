package uk.ac.ebi.pride.solr.indexes.pride.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.utilities.term.CvTermReference;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

/**
 * String Utils helps to convert the
 * @author ypriverol
 * @version $Id$
 */
public class StringUtils {

    /** Logger use to query and filter the data **/
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);


    /**
     * Get convert sentence to Capitalize Style
     * @param sentence original sentence
     * @return Capitalize sentence
     */
    public static String convertSentenceStyle(String sentence){
        sentence = sentence.toLowerCase().trim();
        return org.apache.commons.lang3.StringUtils.capitalize(sentence);
    }

    /**
     * Compare a CvTerm with a Reference Term
     * @param cvParam CvParam
     * @param cvTermReference CvTerm Reference
     * @return
     */
    public static boolean isCvTerm(CvParamProvider cvParam, CvTermReference cvTermReference){
        return cvParam.getName().equalsIgnoreCase(cvTermReference.getName()) || cvParam.getAccession().equalsIgnoreCase(cvTermReference.getAccession());
    }

    /**
     * Compare a CvTerm with a Reference Term
     * @param accession
     * @param cvTermReference CvTerm Reference
     * @return
     */
    public static boolean isCvTerm(String accession, CvTermReference cvTermReference){
        return accession.equalsIgnoreCase(cvTermReference.getAccession());
    }


    public static MultiValueMap<String, String> parseFilterParameters(String filterQuery){
        MultiValueMap<String, String> filters = new LinkedMultiValueMap<>();
        if(filterQuery != null && !filterQuery.trim().isEmpty()){
            String[] filtersString = (filterQuery + ",").split(",");
            if(filtersString.length > 0){
                Arrays.asList(filtersString).forEach(filter ->{
                    String[] filterString = filter.split(":");
                    if(filterString.length == 2)
                        filters.add(filterString[0], filterString[1]);
                    else
                        LOGGER.debug("The filter provided is not well-formatted, please format the filter in field:value -- " + filter);

                });
            }
        }
        return filters;
    }

    /**
     * Returns the {@link Date} of midnight at the start of the given {@link Date}.
     *
     * <p>This returns a {@link Date} formed from the given {@link Date} at the time of midnight,
     * 00:00, at the start of this {@link Date}.
     *
     * @return the {@link Date} of midnight at the start of the given {@link Date}
     */
    public static Date atStartOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    /**
     * Returns the {@link Date} at the end of day of the given {@link Date}.
     *
     * <p>This returns a {@link Date} formed from the given {@link Date} at the time of 1 millisecond
     * prior to midnight the next day.
     *
     * @return the {@link Date} at the end of day of the given {@link Date}j
     */
    public static Date atEndOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

    private static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
