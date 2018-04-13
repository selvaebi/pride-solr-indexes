package uk.ac.ebi.pride.solr.indexes.pride.model;

/**
 * Enum for the PRIDE Projects Fields. This enums heps to iterate and validate the fields.
 *
 * @author ypriverol
 * @version $Id$
 */
public enum PrideProjectFieldEnum {

    ACCESSION(PrideProjectField.ACCESSION, false, false),
    PROJECT_TILE(PrideProjectField.PROJECT_TILE, false, false),
    ADDITIONAL_ATTRIBUTES(PrideProjectField.ADDITIONAL_ATTRIBUTES, false, false),
    PROJECT_DESCRIPTION(PrideProjectField.PROJECT_DESCRIPTION, false, false ),
    PROJECT_SAMPLE_PROTOCOL(PrideProjectField.PROJECT_SAMPLE_PROTOCOL, false ,false),
    PROJECT_DATA_PROTOCOL(PrideProjectField.PROJECT_DATA_PROTOCOL, false, false),
    PROJECT_TAGS(PrideProjectField.PROJECT_TAGS, true , true),
    PROJECT_KEYWORDS(PrideProjectField.PROJECT_KEYWORDS, true, false),
    PROJECT_DOI(PrideProjectField.PROJECT_DOI, false, false ),
    PROJECT_OMICS_LINKS(PrideProjectField.PROJECT_OMICS_LINKS, false, false ),
    PROJECT_SUBMISSION_TYPE(PrideProjectField.PROJECT_SUBMISSION_TYPE, true, false),
    PROJECT_SUBMISSION_DATE(PrideProjectField.PROJECT_SUBMISSION_DATE, true, false ),
    PROJECT_PUBLICATION_DATE(PrideProjectField.PROJECT_PUBLICATION_DATE, true, false ),
    PROJECT_UPDATED_DATE(PrideProjectField.PROJECT_UPDATED_DATE, true, false),
    PROJECT_SUBMITTER(PrideProjectField.PROJECT_SUBMITTER, false, false ),
    PROJECT_PI_NAMES(PrideProjectField.PROJECT_PI_NAMES, true, false),
    AFFILIATIONS(PrideProjectField.AFFILIATIONS, true ,false),
    INSTRUMENTS(PrideProjectField.INSTRUMENTS, true, false ),
    COUNTRIES(PrideProjectField.COUNTRIES, true, false),
    EXPERIMENTAL_FACTORS_NAMES(PrideProjectField.EXPERIMENTAL_FACTORS_NAMES, true, false ),
    SPECIES(PrideProjectField.SPECIES, true, false ),
    PROJECT_REFERENCES(PrideProjectField.PROJECT_REFERENCES, false, false ),
    PROJECT_PUBLIC(PrideProjectField.PROJECT_PUBLIC, false, false  ),
    PROTEIN_IDENTIFICATIONS(PrideProjectField.PROTEIN_IDENTIFICATIONS, true, false ),
    PEPTIDE_SEQUENCES(PrideProjectField.PEPTIDE_SEQUENCES, false, false),
    PROJECT_IDENTIFIED_PTM_STRING(PrideProjectField.PROJECT_IDENTIFIED_PTM_STRING, true, false );

    private String  value;
    private Boolean facet;
    private Boolean multiValue;

    PrideProjectFieldEnum(String value, Boolean facet, Boolean multiValue) {
        this.value = value;
        this.facet = facet;
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
}
