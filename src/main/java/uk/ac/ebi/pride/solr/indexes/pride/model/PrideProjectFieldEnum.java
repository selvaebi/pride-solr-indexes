package uk.ac.ebi.pride.solr.indexes.pride.model;

/**
 * Enum for the PRIDE Projects Fields. This enums heps to iterate and validate the fields.
 *
 * @author ypriverol
 * @version $Id$
 */
public enum PrideProjectFieldEnum {

    ACCESSION(PrideProjectField.ACCESSION, false),
    PROJECT_TILE(PrideProjectField.PROJECT_TILE, false),
    ADDITIONAL_ATTRIBUTES(PrideProjectField.ADDITIONAL_ATTRIBUTES, false),
    PROJECT_DESCRIPTION(PrideProjectField.PROJECT_DESCRIPTION, false ),
    PROJECT_SAMPLE_PROTOCOL(PrideProjectField.PROJECT_SAMPLE_PROTOCOL, false ),
    PROJECT_DATA_PROTOCOL(PrideProjectField.PROJECT_DATA_PROTOCOL, false),
    PROJECT_TAGS(PrideProjectField.PROJECT_TAGS, true),
    PROJECT_KEYWORDS(PrideProjectField.PROJECT_KEYWORDS, true),
    PROJECT_DOI(PrideProjectField.PROJECT_DOI, false),
    PROJECT_OMICS_LINKS(PrideProjectField.PROJECT_OMICS_LINKS, false ),
    PROJECT_SUBMISSION_TYPE(PrideProjectField.PROJECT_SUBMISSION_TYPE, true),
    PROJECT_SUBMISSION_DATE(PrideProjectField.PROJECT_SUBMISSION_DATE, true ),
    PROJECT_PUBLICATION_DATE(PrideProjectField.PROJECT_PUBLICATION_DATE, true ),
    PROJECT_UPDATED_DATE(PrideProjectField.PROJECT_UPDATED_DATE, true),
    PROJECT_SUBMITTER(PrideProjectField.PROJECT_SUBMITTER, false ),
    PROJECT_PI_NAMES(PrideProjectField.PROJECT_PI_NAMES, true),
    AFFILIATIONS(PrideProjectField.AFFILIATIONS, true),
    INSTRUMENTS(PrideProjectField.INSTRUMENTS, true),
    COUNTRIES(PrideProjectField.COUNTRIES, true ),
    EXPERIMENTAL_FACTORS_NAMES(PrideProjectField.EXPERIMENTAL_FACTORS_NAMES, true ),
    SPECIES(PrideProjectField.SPECIES, true ),
    PROJECT_REFERENCES(PrideProjectField.PROJECT_REFERENCES, false ),
    PROJECT_PUBLIC(PrideProjectField.PROJECT_PUBLIC, false ),
    PROTEIN_IDENTIFICATIONS(PrideProjectField.PROTEIN_IDENTIFICATIONS, true),
    PEPTIDE_SEQUENCES(PrideProjectField.PEPTIDE_SEQUENCES, false ),
    PROJECT_IDENTIFIED_PTM_STRING(PrideProjectField.PROJECT_IDENTIFIED_PTM_STRING, true );

    private String value;
    private Boolean facet;

    PrideProjectFieldEnum(String value, Boolean facet) {
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
