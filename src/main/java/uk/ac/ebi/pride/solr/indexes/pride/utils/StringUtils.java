package uk.ac.ebi.pride.solr.indexes.pride.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.utilities.term.CvTermReference;

import java.util.Arrays;


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
                    String[] filterString = filter.split("==");
                    if(filterString.length == 2)
                        filters.add(filterString[0], filterString[1]);
                    else
                        LOGGER.debug("The filter provided is not well-formatted, please format the filter in field:value -- " + filter);

                });
            }
        }
        return filters;
    }

}
