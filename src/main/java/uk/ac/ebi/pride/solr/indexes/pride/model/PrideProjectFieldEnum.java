package uk.ac.ebi.pride.solr.indexes.pride.model;

import uk.ac.ebi.pride.solr.indexes.pride.utils.PrideSolrConstants.ConstantsSolrTypes;

/**
 * Enum for the PRIDE Projects Fields. This enums heps to iterate and validate the fields.
 * This enum is used by the {@link uk.ac.ebi.pride.solr.indexes.pride.utils.SolrAPIHelper} to
 * update and refine the schema. With this definition not manual annotation is needed.
 *
 * @author ypriverol
 * @version $Id$
 */
public enum PrideProjectFieldEnum {

    ACCESSION(PrideProjectField.ACCESSION, false, false, false, true,
            ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.ACCESSION, true),
    PROJECT_TILE(PrideProjectField.PROJECT_TILE, false, false, false, true,
            ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_TILE, true),

    ADDITIONAL_ATTRIBUTES(PrideProjectField.ADDITIONAL_ATTRIBUTES, false, true,
            false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.ADDITIONAL_ATTRIBUTES, true),

    ADDITIONAL_ATTRIBUTES_FACET(PrideProjectField.ADDITIONAL_ATTRIBUTES_FACET, true,true,
            false, false ,ConstantsSolrTypes.STRING, PrideProjectField.ADDITIONAL_ATTRIBUTES, true),

    PROJECT_DESCRIPTION(PrideProjectField.PROJECT_DESCRIPTION, false, false, false,
            true, ConstantsSolrTypes.TEXT_GENERAL,PrideProjectField.PROJECT_DESCRIPTION, true),

    PROJECT_SAMPLE_PROTOCOL(PrideProjectField.PROJECT_SAMPLE_PROTOCOL, false ,false, false, true, ConstantsSolrTypes.TEXT_GENERAL,PrideProjectField.PROJECT_SAMPLE_PROTOCOL, true),
    PROJECT_DATA_PROTOCOL(PrideProjectField.PROJECT_DATA_PROTOCOL, false, false, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_DATA_PROTOCOL, true),

    PROJECT_TAGS(PrideProjectField.PROJECT_TAGS, false , true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_TAGS, true),
    PROJECT_TAGS_FACET(PrideProjectField.PROJECT_TAGS_FACET, true , true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.PROJECT_TAGS_FACET, true),

    PROJECT_KEYWORDS(PrideProjectField.PROJECT_KEYWORDS, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL_HYPHEN, PrideProjectField.PROJECT_KEYWORDS, true),
    PROJECT_KEYWORDS_FACET(PrideProjectField.PROJECT_KEYWORDS_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.PROJECT_KEYWORDS_FACET, true),

    PROJECT_DOI(PrideProjectField.PROJECT_DOI, false, false, false, false, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_DOI, true),
    PROJECT_OMICS_LINKS(PrideProjectField.PROJECT_OMICS_LINKS, false, false, false, false, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_OMICS_LINKS,true ),
    PROJECT_SUBMISSION_TYPE(PrideProjectField.PROJECT_SUBMISSION_TYPE, true, false, false, false, ConstantsSolrTypes.TEXT_GENERAL,PrideProjectField.PROJECT_SUBMISSION_TYPE, true),

    PROJECT_SUBMISSION_DATE(PrideProjectField.PROJECT_SUBMISSION_DATE, true, false, false, false, ConstantsSolrTypes.DATE, PrideProjectField.PROJECT_SUBMISSION_DATE, true),
    PROJECT_PUBLICATION_DATE(PrideProjectField.PROJECT_PUBLICATION_DATE, true, false, false, false, ConstantsSolrTypes.DATE, PrideProjectField.PROJECT_PUBLICATION_DATE, true),
    PROJECT_UPDATED_DATE(PrideProjectField.PROJECT_UPDATED_DATE, true, false, false, false, ConstantsSolrTypes.DATE, PrideProjectField.PROJECT_UPDATED_DATE, true),

    PROJECT_SUBMITTER(PrideProjectField.PROJECT_SUBMITTER, false, false, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_SUBMITTER, true),

    PROJECT_PI_NAMES(PrideProjectField.PROJECT_PI_NAMES, false, false, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_PI_NAMES, true),
    PROJECT_PI_NAMES_FACET(PrideProjectField.PROJECT_PI_NAMES_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.PROJECT_PI_NAMES_FACET, true),

    AFFILIATIONS(PrideProjectField.AFFILIATIONS, false ,true, false, true, ConstantsSolrTypes.TEXT_GENERAL,  PrideProjectField.AFFILIATIONS, true),
    AFFILIATIONS_FACET(PrideProjectField.AFFILIATIONS_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.AFFILIATIONS_FACET, true),

    INSTRUMENTS(PrideProjectField.INSTRUMENTS, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.INSTRUMENTS, true),
    INSTRUMENTS_FACET(PrideProjectField.INSTRUMENTS_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.INSTRUMENTS_FACET, true),

    SOFTWARES(PrideProjectField.SOFTWARES, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.SOFTWARES, true),
    SOFTWARES_FACET(PrideProjectField.SOFTWARES_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.SOFTWARES_FACET, true),

    QUANTIFICATION_METHOD(PrideProjectField.QUANTIFICATION_METHODS, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.QUANTIFICATION_METHODS, true ),
    QUANTIFICATION_METHOD_FACET(PrideProjectField.QUANTIFICATION_METHODS_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.QUANTIFICATION_METHODS_FACET, true),

    COUNTRIES(PrideProjectField.COUNTRIES, false, false, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.COUNTRIES, true),
    COUNTRIES_FACET(PrideProjectField.COUNTRIES_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.COUNTRIES_FACET, true),

    SAMPLE_ATTRIBUTES_NAMES(PrideProjectField.SAMPLE_ATTRIBUTES_NAMES, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.SAMPLE_ATTRIBUTES_NAMES, true),

    EXPERIMENTAL_FACTORS_NAMES(PrideProjectField.EXPERIMENTAL_FACTORS_NAMES, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.EXPERIMENTAL_FACTORS_NAMES, true ),
    EXPERIMENTAL_FACTORS_FACET(PrideProjectField.EXPERIMENTAL_FACTORS_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.EXPERIMENTAL_FACTORS_FACET, true),

    ORGANISM_FACET(PrideProjectField.ORGANISMS_FACET, true, true, false, true, ConstantsSolrTypes.STRING,PrideProjectField.ORGANISMS_FACET,true ),
    ORGANISM(PrideProjectField.ORGANISM, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL,  PrideProjectField.ORGANISM, true),

    ORGANISM_PART_FACET(PrideProjectField.ORGANISMS_PART_FACET, true, true, false, true, ConstantsSolrTypes.STRING,PrideProjectField.ORGANISMS_PART_FACET, true ),
    ORGANISM_PART(PrideProjectField.ORGANISM_PART,false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL,PrideProjectField.ORGANISM_PART, true ),

    DISEASES_FACET(PrideProjectField.DISEASES_FACET, true, true, false, true, ConstantsSolrTypes.STRING, PrideProjectField.DISEASES_FACET, true),
    DISEASES(PrideProjectField.DISEASES, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.DISEASES, true),

    PROJECT_REFERENCES(PrideProjectField.PROJECT_REFERENCES, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_REFERENCES,true ),

    PROTEIN_IDENTIFICATIONS(PrideProjectField.PROTEIN_IDENTIFICATIONS, true, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROTEIN_IDENTIFICATIONS, false ),
    PROTEIN_IDENTIFICATIONS_FACET(PrideProjectField.PROTEIN_IDENTIFICATIONS_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.PROTEIN_IDENTIFICATIONS_FACET, false ),

    PEPTIDE_SEQUENCES(PrideProjectField.PEPTIDE_SEQUENCES, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PEPTIDE_SEQUENCES, false),

    PROJECT_FILE_NAMES(PrideProjectField.PROJECT_FILE_NAMES, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_FILE_NAMES, true),

    PROJECT_IDENTIFIED_PTM_STRING(PrideProjectField.PROJECT_IDENTIFIED_PTM_STRING, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_IDENTIFIED_PTM_STRING, true),
    PROJECT_IDENTIFIED_PTM_STRING_FACET(PrideProjectField.PROJECT_IDENTIFIED_PTM_STRING_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.PROJECT_IDENTIFIED_PTM_STRING_FACET, true);

    private String  value;
    private Boolean facet;
    private Boolean multiValue;
    private Boolean dynamic;
    private Boolean highlight;
    private ConstantsSolrTypes type;
    private String filterField;
    private Boolean stored;

    PrideProjectFieldEnum(String value, Boolean facet, Boolean multiValue, Boolean dynamic, Boolean highlight, ConstantsSolrTypes type,  String filterField, Boolean stored) {
        this.value = value;
        this.facet = facet;
        this.multiValue = multiValue;
        this.type = type;
        this.dynamic = dynamic;
        this.highlight = highlight;
        this.filterField = filterField;
        this.stored = stored;
    }

    /**
     * THis method takes a field query and returns the corresponding filter field name. This method faciliates the projection
     * from facet fields into TEXT fields.
     *
     * @param filterField filter Field
     * @return Filter Field
     */
    public static String returnFilterField(String filterField) {
        for(PrideProjectFieldEnum enumField: values()){
            if(enumField.getValue().equalsIgnoreCase(filterField)){
                return enumField.getFilterField();
            }
        }
        return filterField;
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

    public ConstantsSolrTypes getType() {
        return type;
    }

    public void setType(ConstantsSolrTypes type) {
        this.type = type;
    }

    public Boolean getDynamic() {
        return dynamic;
    }

    public Boolean getHighlight() {
        return highlight;
    }

    public String getFilterField() {
        return filterField;
    }

    public Boolean getStored() {
        return stored;
    }}
