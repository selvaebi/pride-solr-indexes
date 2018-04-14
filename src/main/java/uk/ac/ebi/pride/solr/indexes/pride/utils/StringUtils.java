package uk.ac.ebi.pride.solr.indexes.pride.utils;

import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.utilities.term.CvTermReference;

/**
 * String Utils helps to convert the
 * @author ypriverol
 * @version $Id$
 */
public class StringUtils {

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

}
