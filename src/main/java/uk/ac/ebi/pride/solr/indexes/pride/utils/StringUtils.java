package uk.ac.ebi.pride.solr.indexes.pride.utils;

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
}
