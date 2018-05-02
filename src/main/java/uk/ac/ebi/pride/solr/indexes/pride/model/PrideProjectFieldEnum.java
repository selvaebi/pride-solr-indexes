package uk.ac.ebi.pride.solr.indexes.pride.model;

import uk.ac.ebi.pride.solr.indexes.pride.utils.ConstantsSolrTypes;

/**
 * Enum for the PRIDE Projects Fields. This enums heps to iterate and validate the fields.
 * This enum is used by the {@link uk.ac.ebi.pride.solr.indexes.pride.utils.SolrAPIHelper} to
 * update and refine the schema. With this definition not manual annotation is needed.
 *
 * @author ypriverol
 * @version $Id$
 */
public enum PrideProjectFieldEnum {

    ACCESSION(PrideProjectField.ACCESSION, false, false, false, ConstantsSolrTypes.TEXT_GENERAL),
    PROJECT_TILE(PrideProjectField.PROJECT_TILE, false, false, false, ConstantsSolrTypes.TEXT_GENERAL),
    ADDITIONAL_ATTRIBUTES(PrideProjectField.ADDITIONAL_ATTRIBUTES, false, true, false, ConstantsSolrTypes.TEXT_GENERAL),
    ADDITIONAL_ATTRIBUTES_FACET(PrideProjectField.ADDITIONAL_ATTRIBUTES_FACET, true, true, false, ConstantsSolrTypes.STRING),
    PROJECT_DESCRIPTION(PrideProjectField.PROJECT_DESCRIPTION, false, false, false, ConstantsSolrTypes.TEXT_GENERAL),
    PROJECT_SAMPLE_PROTOCOL(PrideProjectField.PROJECT_SAMPLE_PROTOCOL, false ,false, false, ConstantsSolrTypes.TEXT_GENERAL),
    PROJECT_DATA_PROTOCOL(PrideProjectField.PROJECT_DATA_PROTOCOL, false, false, false, ConstantsSolrTypes.TEXT_GENERAL),
    PROJECT_TAGS(PrideProjectField.PROJECT_TAGS, false , true, false, ConstantsSolrTypes.TEXT_GENERAL),
    PROJECT_TAGS_FACET(PrideProjectField.PROJECT_TAGS_FACET, true , true, false, ConstantsSolrTypes.STRING),
    PROJECT_KEYWORDS(PrideProjectField.PROJECT_KEYWORDS, true, true, false, ConstantsSolrTypes.TEXT_GENERAL),
    PROJECT_KEYWORDS_FACET(PrideProjectField.PROJECT_KEYWORDS_FACET, true, true, false, ConstantsSolrTypes.STRING),
    PROJECT_DOI(PrideProjectField.PROJECT_DOI, false, false, false, ConstantsSolrTypes.TEXT_GENERAL),
    PROJECT_OMICS_LINKS(PrideProjectField.PROJECT_OMICS_LINKS, false, false, false, ConstantsSolrTypes.TEXT_GENERAL ),
    PROJECT_SUBMISSION_TYPE(PrideProjectField.PROJECT_SUBMISSION_TYPE, true, false, false, ConstantsSolrTypes.TEXT_GENERAL),
    PROJECT_SUBMISSION_DATE(PrideProjectField.PROJECT_SUBMISSION_DATE, true, false, false, ConstantsSolrTypes.DATE ),
    PROJECT_PUBLICATION_DATE(PrideProjectField.PROJECT_PUBLICATION_DATE, true, false, false, ConstantsSolrTypes.DATE ),
    PROJECT_UPDATED_DATE(PrideProjectField.PROJECT_UPDATED_DATE, true, false, false, ConstantsSolrTypes.DATE),
    PROJECT_SUBMITTER(PrideProjectField.PROJECT_SUBMITTER, false, false, false, ConstantsSolrTypes.TEXT_GENERAL ),
    PROJECT_PI_NAMES(PrideProjectField.PROJECT_PI_NAMES, false, false, false, ConstantsSolrTypes.TEXT_GENERAL),
    PROJECT_PI_NAMES_FACET(PrideProjectField.PROJECT_PI_NAMES_FACET, true, true, false, ConstantsSolrTypes.STRING),
    AFFILIATIONS(PrideProjectField.AFFILIATIONS, false ,true, false, ConstantsSolrTypes.TEXT_GENERAL),
    AFFILIATIONS_FACET(PrideProjectField.AFFILIATIONS_FACET, true, true, false, ConstantsSolrTypes.STRING),
    INSTRUMENTS(PrideProjectField.INSTRUMENTS, false, true, false, ConstantsSolrTypes.TEXT_GENERAL ),
    INSTRUMENTS_FACET(PrideProjectField.INSTRUMENTS_FACET, true, true, false, ConstantsSolrTypes.STRING),
    SOFTWARES(PrideProjectField.SOFTWARES, false, true, false, ConstantsSolrTypes.TEXT_GENERAL ),
    SOFTWARES_FACET(PrideProjectField.SOFTWARES_FACET, true, true, false, ConstantsSolrTypes.STRING),
    QUANTIFICATION_METHOD(PrideProjectField.QUANTIFICATION_METHODS, false, true, false, ConstantsSolrTypes.TEXT_GENERAL ),
    QUANTIFICATION_METHOD_FACET(PrideProjectField.QUANTIFICATION_METHODS_FACET, true, true, false, ConstantsSolrTypes.STRING),
    COUNTRIES(PrideProjectField.COUNTRIES, true, false, false, ConstantsSolrTypes.TEXT_GENERAL),
    COUNTRIES_FACET(PrideProjectField.COUNTRIES_FACET, true, true, false, ConstantsSolrTypes.STRING),

    SAMPLE_ATTRIBUTES_NAMES(PrideProjectField.SAMPLE_ATTRIBUTES_NAMES, false, true, false, ConstantsSolrTypes.TEXT_GENERAL ),
    SAMPLE_ATTRIBUTES_FACET(PrideProjectField.SOFTWARES_FACET, true, true, false, ConstantsSolrTypes.STRING),


    EXPERIMENTAL_FACTORS_NAMES(PrideProjectField.EXPERIMENTAL_FACTORS_NAMES, false, true, false, ConstantsSolrTypes.TEXT_GENERAL ),
    EXPERIMENTAL_FACTORS_FACET(PrideProjectField.EXPERIMENTAL_FACTORS_FACET, true, true, false, ConstantsSolrTypes.STRING ),

    ORGANISM(PrideProjectField.ORGANISMS, true, true, false, ConstantsSolrTypes.STRING ),
    CELL_TYPE(PrideProjectField.ORGANISMS_PART, true, true, false, ConstantsSolrTypes.STRING ),
    DISEASES(PrideProjectField.DISEASES, true, true, false, ConstantsSolrTypes.STRING ),

    PROJECT_REFERENCES(PrideProjectField.PROJECT_REFERENCES, false, true, false, ConstantsSolrTypes.TEXT_GENERAL ),
    PROTEIN_IDENTIFICATIONS(PrideProjectField.PROTEIN_IDENTIFICATIONS, true, true, false, ConstantsSolrTypes.TEXT_GENERAL ),
    PROTEIN_IDENTIFICATIONS_FACET(PrideProjectField.PROTEIN_IDENTIFICATIONS_FACET, true, true, false, ConstantsSolrTypes.STRING ),

    PEPTIDE_SEQUENCES(PrideProjectField.PEPTIDE_SEQUENCES, false, true, false, ConstantsSolrTypes.TEXT_GENERAL),
    PROJECT_IDENTIFIED_PTM_STRING(PrideProjectField.PROJECT_IDENTIFIED_PTM_STRING, false, true, false, ConstantsSolrTypes.TEXT_GENERAL),
    PROJECT_IDENTIFIED_PTM_STRING_FACET(PrideProjectField.PROJECT_IDENTIFIED_PTM_STRING_FACET, true, true, false, ConstantsSolrTypes.STRING);

    private String  value;
    private Boolean facet;
    private Boolean multiValue;
    private Boolean dynamic;
    private ConstantsSolrTypes type;

    PrideProjectFieldEnum(String value, Boolean facet, Boolean multiValue, Boolean dynamic, ConstantsSolrTypes type) {
        this.value = value;
        this.facet = facet;
        this.multiValue = multiValue;
        this.type = type;
        this.dynamic = dynamic;
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

    public Boolean getDynamic() {
        return dynamic;
    }

    public void setDynamic(Boolean dynamic) {
        this.dynamic = dynamic;
    }
}
