package uk.ac.ebi.pride.solr.indexes.pride.utils;

/**
 * Solr types definition.
 * @author ypriverol
 * @version $Id$
 *
 */
public enum ConstantsSolrTypes {

    STRING("string"),
    TEXT_GENERAL("text_general"),
    DATE("date"),
    DEFAULT("default");

    private final String type;

    ConstantsSolrTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
