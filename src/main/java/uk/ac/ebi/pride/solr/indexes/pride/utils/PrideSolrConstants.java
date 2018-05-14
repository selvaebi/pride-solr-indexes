package uk.ac.ebi.pride.solr.indexes.pride.utils;

/**
 * Solr types definition.
 * @author ypriverol
 * @version $Id$
 *
 */

public class PrideSolrConstants{

    public static final int DEFAULT_NRSNIPPLETS_SIZE = 10;
    public static int DEFAULT_FRAGMENT_SIZE = 20;
    public static final String[] SIMPLE_OPERATION_SET = { ":", "!", ">", "<", "~" };


public enum ConstantsSolrTypes {

    STRING("string"),
    TEXT_GENERAL("text_general"),
    DATE("pdate"),
    DEFAULT("text_general");

    private final String type;

    ConstantsSolrTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

}
