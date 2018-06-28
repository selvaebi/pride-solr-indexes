package uk.ac.ebi.pride.solr.indexes.pride.utils;

/**
 * Solr types definition.
 * @author ypriverol
 * @version $Id$
 *
 */

public class PrideSolrConstants{

    public static final int DEFAULT_NRSNIPPLETS_SIZE = 20;
    public static final int DEFAULT_FRAGMENT_SIZE = 30;

    public enum AllowedDateGapConstants {
        MONTHLY("+1MONTH"),
        YEARLY("+1YEAR"),
        UNKONWN("");

        public String value;

        AllowedDateGapConstants(String value) {
            this.value = value;
        }

        public static AllowedDateGapConstants findByString(String value){
            AllowedDateGapConstants defaultConstant = UNKONWN;
            for(AllowedDateGapConstants constant: AllowedDateGapConstants.values()){
                if(constant.value.equalsIgnoreCase(value))
                    defaultConstant = constant;
            }
            return defaultConstant;
        }
    }


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
