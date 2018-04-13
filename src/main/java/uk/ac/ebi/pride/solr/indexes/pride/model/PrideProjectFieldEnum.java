package uk.ac.ebi.pride.solr.indexes.pride.model;

import uk.ac.ebi.pride.solr.indexes.pride.utils.ConstantsSolrTypes;

/**
 * Enum for the PRIDE Projects Fields. This enums heps to iterate and validate the fields.
 *
 * @author ypriverol
 * @version $Id$
 */
public enum PrideProjectFieldEnum {

    ACCESSION(PrideProjectField.ACCESSION, false, false, ConstantsSolrTypes.DEFAULT),
    PROJECT_TILE(PrideProjectField.PROJECT_TILE, false, false, ConstantsSolrTypes.DEFAULT),
    ADDITIONAL_ATTRIBUTES(PrideProjectField.ADDITIONAL_ATTRIBUTES, false, false, ConstantsSolrTypes.DEFAULT),
    PROJECT_DESCRIPTION(PrideProjectField.PROJECT_DESCRIPTION, false, false, ConstantsSolrTypes.DEFAULT),
    PROJECT_SAMPLE_PROTOCOL(PrideProjectField.PROJECT_SAMPLE_PROTOCOL, false ,false, ConstantsSolrTypes.DEFAULT),
    PROJECT_DATA_PROTOCOL(PrideProjectField.PROJECT_DATA_PROTOCOL, false, false, ConstantsSolrTypes.DEFAULT),
    PROJECT_TAGS(PrideProjectField.PROJECT_TAGS, false , true, ConstantsSolrTypes.DEFAULT),
    PROJECT_TAGS_FACET(PrideProjectField.PROJECT_TAGS_FACET, true , true, ConstantsSolrTypes.STRING),
    PROJECT_KEYWORDS(PrideProjectField.PROJECT_KEYWORDS, true, false, ConstantsSolrTypes.DEFAULT),
    PROJECT_KEYWORDS_FACET(PrideProjectField.PROJECT_KEYWORDS_FACET, true, true, ConstantsSolrTypes.STRING),
    PROJECT_DOI(PrideProjectField.PROJECT_DOI, false, false, ConstantsSolrTypes.DEFAULT ),
    PROJECT_OMICS_LINKS(PrideProjectField.PROJECT_OMICS_LINKS, false, false, ConstantsSolrTypes.DEFAULT ),
    PROJECT_SUBMISSION_TYPE(PrideProjectField.PROJECT_SUBMISSION_TYPE, true, false, ConstantsSolrTypes.DEFAULT),
    PROJECT_SUBMISSION_DATE(PrideProjectField.PROJECT_SUBMISSION_DATE, true, false,ConstantsSolrTypes.DEFAULT ),
    PROJECT_PUBLICATION_DATE(PrideProjectField.PROJECT_PUBLICATION_DATE, true, false,ConstantsSolrTypes.DEFAULT ),
    PROJECT_UPDATED_DATE(PrideProjectField.PROJECT_UPDATED_DATE, true, false, ConstantsSolrTypes.DEFAULT),
    PROJECT_SUBMITTER(PrideProjectField.PROJECT_SUBMITTER, false, false, ConstantsSolrTypes.DEFAULT ),
    PROJECT_PI_NAMES(PrideProjectField.PROJECT_PI_NAMES, false, false, ConstantsSolrTypes.DEFAULT),
    PROJECT_PI_NAMES_FACET(PrideProjectField.PROJECT_PI_NAMES_FACET, true, true, ConstantsSolrTypes.STRING),
    AFFILIATIONS(PrideProjectField.AFFILIATIONS, false ,true, ConstantsSolrTypes.DEFAULT),
    AFFILIATIONS_FACET(PrideProjectField.AFFILIATIONS_FACET, true, true, ConstantsSolrTypes.STRING),
    INSTRUMENTS(PrideProjectField.INSTRUMENTS, false, false, ConstantsSolrTypes.DEFAULT ),
    INSTRUMENTS_FACET(PrideProjectField.INSTRUMENTS_FACET, true, true, ConstantsSolrTypes.STRING),
    COUNTRIES(PrideProjectField.COUNTRIES, true, false, ConstantsSolrTypes.DEFAULT),
    COUNTRIES_FACET(PrideProjectField.COUNTRIES_FACET, true, true, ConstantsSolrTypes.STRING),

    EXPERIMENTAL_FACTORS_NAMES(PrideProjectField.EXPERIMENTAL_FACTORS_NAMES, false, true, ConstantsSolrTypes.DEFAULT ),
    SPECIES(PrideProjectField.SPECIES, true, false, ConstantsSolrTypes.DEFAULT ),
    PROJECT_REFERENCES(PrideProjectField.PROJECT_REFERENCES, false, true, ConstantsSolrTypes.DEFAULT ),
    PROTEIN_IDENTIFICATIONS(PrideProjectField.PROTEIN_IDENTIFICATIONS, true, true, ConstantsSolrTypes.DEFAULT ),
    PROTEIN_IDENTIFICATIONS_FACET(PrideProjectField.PROTEIN_IDENTIFICATIONS_FACET, true, true, ConstantsSolrTypes.STRING ),

    PEPTIDE_SEQUENCES(PrideProjectField.PEPTIDE_SEQUENCES, false, true, ConstantsSolrTypes.DEFAULT),
    PROJECT_IDENTIFIED_PTM_STRING(PrideProjectField.PROJECT_IDENTIFIED_PTM_STRING, false, true, ConstantsSolrTypes.DEFAULT),
    PROJECT_IDENTIFIED_PTM_STRING_FACET(PrideProjectField.PROJECT_IDENTIFIED_PTM_STRING_FACET, true, true, ConstantsSolrTypes.STRING);

    private String  value;
    private Boolean facet;
    private Boolean multiValue;
    private ConstantsSolrTypes type;

    PrideProjectFieldEnum(String value, Boolean facet, Boolean multiValue, ConstantsSolrTypes type) {
        this.value = value;
        this.facet = facet;
        this.multiValue = multiValue;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static PrideProjectFieldEnum findKey(String key){
        for(PrideProjectFieldEnum value: values()){
            if(value.getValue().equalsIgnoreCase(key))
                return value;
        }
        return null;
    }

    public Boolean getFacet() {
        return facet;
    }

    public void setFacet(Boolean facet) {
        this.facet = facet;
    }

    public Boolean getMultiValue() {
        return multiValue;
    }

    public void setMultiValue(Boolean multiValue) {
        this.multiValue = multiValue;
    }

    public ConstantsSolrTypes getType() {
        return type;
    }

    public void setType(ConstantsSolrTypes type) {
        this.type = type;
    }
}
