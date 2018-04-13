package uk.ac.ebi.pride.solr.indexes.pride.utils;

import javax.print.DocFlavor;

/**
 * Solr types definition.
 * @author ypriverol
 * @version $Id$
 *
 */
public enum ConstantsSolrTypes {

    STRING("string"),
    DEFAULT("default");

    private String type;

    ConstantsSolrTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
