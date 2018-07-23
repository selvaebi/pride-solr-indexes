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

    ACCESSION(PrideProjectField.ACCESSION, false, false, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.ACCESSION),
    PROJECT_TILE(PrideProjectField.PROJECT_TILE, false, false, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_TILE),

    ADDITIONAL_ATTRIBUTES(PrideProjectField.ADDITIONAL_ATTRIBUTES, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.ADDITIONAL_ATTRIBUTES),
    ADDITIONAL_ATTRIBUTES_FACET(PrideProjectField.ADDITIONAL_ATTRIBUTES_FACET, true, true, false, false ,ConstantsSolrTypes.STRING, PrideProjectField.ADDITIONAL_ATTRIBUTES),

    PROJECT_DESCRIPTION(PrideProjectField.PROJECT_DESCRIPTION, false, false, false, true, ConstantsSolrTypes.TEXT_GENERAL,PrideProjectField.PROJECT_DESCRIPTION),
    PROJECT_SAMPLE_PROTOCOL(PrideProjectField.PROJECT_SAMPLE_PROTOCOL, false ,false, false, true, ConstantsSolrTypes.TEXT_GENERAL,PrideProjectField.PROJECT_SAMPLE_PROTOCOL),
    PROJECT_DATA_PROTOCOL(PrideProjectField.PROJECT_DATA_PROTOCOL, false, false, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_DATA_PROTOCOL),

    PROJECT_TAGS(PrideProjectField.PROJECT_TAGS, false , true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_TAGS),
    PROJECT_TAGS_FACET(PrideProjectField.PROJECT_TAGS_FACET, true , true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.PROJECT_TAGS),

    PROJECT_KEYWORDS(PrideProjectField.PROJECT_KEYWORDS, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_KEYWORDS),
    PROJECT_KEYWORDS_FACET(PrideProjectField.PROJECT_KEYWORDS_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.PROJECT_KEYWORDS),

    PROJECT_DOI(PrideProjectField.PROJECT_DOI, false, false, false, false, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_DOI),
    PROJECT_OMICS_LINKS(PrideProjectField.PROJECT_OMICS_LINKS, false, false, false, false, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_OMICS_LINKS ),
    PROJECT_SUBMISSION_TYPE(PrideProjectField.PROJECT_SUBMISSION_TYPE, true, false, false, false, ConstantsSolrTypes.TEXT_GENERAL,PrideProjectField.PROJECT_SUBMISSION_TYPE),

    PROJECT_SUBMISSION_DATE(PrideProjectField.PROJECT_SUBMISSION_DATE, true, false, false, false, ConstantsSolrTypes.DATE, PrideProjectField.PROJECT_SUBMISSION_DATE ),
    PROJECT_PUBLICATION_DATE(PrideProjectField.PROJECT_PUBLICATION_DATE, true, false, false, false, ConstantsSolrTypes.DATE, PrideProjectField.PROJECT_PUBLICATION_DATE ),
    PROJECT_UPDATED_DATE(PrideProjectField.PROJECT_UPDATED_DATE, true, false, false, false, ConstantsSolrTypes.DATE, PrideProjectField.PROJECT_UPDATED_DATE),

    PROJECT_SUBMITTER(PrideProjectField.PROJECT_SUBMITTER, false, false, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_SUBMITTER),

    PROJECT_PI_NAMES(PrideProjectField.PROJECT_PI_NAMES, false, false, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_PI_NAMES),
    PROJECT_PI_NAMES_FACET(PrideProjectField.PROJECT_PI_NAMES_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.PROJECT_PI_NAMES),

    AFFILIATIONS(PrideProjectField.AFFILIATIONS, false ,true, false, true, ConstantsSolrTypes.TEXT_GENERAL,  PrideProjectField.AFFILIATIONS),
    AFFILIATIONS_FACET(PrideProjectField.AFFILIATIONS_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.AFFILIATIONS),

    INSTRUMENTS(PrideProjectField.INSTRUMENTS, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.INSTRUMENTS),
    INSTRUMENTS_FACET(PrideProjectField.INSTRUMENTS_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.INSTRUMENTS),

    SOFTWARES(PrideProjectField.SOFTWARES, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.SOFTWARES),
    SOFTWARES_FACET(PrideProjectField.SOFTWARES_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.SOFTWARES),

    QUANTIFICATION_METHOD(PrideProjectField.QUANTIFICATION_METHODS, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.QUANTIFICATION_METHODS ),
    QUANTIFICATION_METHOD_FACET(PrideProjectField.QUANTIFICATION_METHODS_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.QUANTIFICATION_METHODS),

    COUNTRIES(PrideProjectField.COUNTRIES, false, false, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.COUNTRIES),
    COUNTRIES_FACET(PrideProjectField.COUNTRIES_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.COUNTRIES),

    SAMPLE_ATTRIBUTES_NAMES(PrideProjectField.SAMPLE_ATTRIBUTES_NAMES, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.SAMPLE_ATTRIBUTES_NAMES),

    EXPERIMENTAL_FACTORS_NAMES(PrideProjectField.EXPERIMENTAL_FACTORS_NAMES, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.EXPERIMENTAL_FACTORS_NAMES ),
    EXPERIMENTAL_FACTORS_FACET(PrideProjectField.EXPERIMENTAL_FACTORS_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.EXPERIMENTAL_FACTORS_NAMES),

    ORGANISM_FACET(PrideProjectField.ORGANISMS_FACET, true, true, false, true, ConstantsSolrTypes.STRING,PrideProjectField.ORGANISM ),
    ORGANISM(PrideProjectField.ORGANISM, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL,  PrideProjectField.ORGANISM),

    ORGANISM_PART_FACET(PrideProjectField.ORGANISMS_PART_FACET, true, true, false, true, ConstantsSolrTypes.STRING,PrideProjectField.ORGANISM_PART ),
    ORGANISM_PART(PrideProjectField.ORGANISM_PART,false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL,PrideProjectField.ORGANISM_PART ),

    DISEASES_FACET(PrideProjectField.DISEASES_FACET, true, true, false, true, ConstantsSolrTypes.STRING, PrideProjectField.DISEASES),
    DISEASES(PrideProjectField.DISEASES, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.DISEASES),

    PROJECT_REFERENCES(PrideProjectField.PROJECT_REFERENCES, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_REFERENCES ),

    PROTEIN_IDENTIFICATIONS(PrideProjectField.PROTEIN_IDENTIFICATIONS, true, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROTEIN_IDENTIFICATIONS ),
    PROTEIN_IDENTIFICATIONS_FACET(PrideProjectField.PROTEIN_IDENTIFICATIONS_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.PROTEIN_IDENTIFICATIONS ),

    PEPTIDE_SEQUENCES(PrideProjectField.PEPTIDE_SEQUENCES, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PEPTIDE_SEQUENCES),

    PROJECT_FILE_NAMES(PrideProjectField.PROJECT_FILE_NAMES, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_FILE_NAMES),

    PROJECT_IDENTIFIED_PTM_STRING(PrideProjectField.PROJECT_IDENTIFIED_PTM_STRING, false, true, false, true, ConstantsSolrTypes.TEXT_GENERAL, PrideProjectField.PROJECT_IDENTIFIED_PTM_STRING),
    PROJECT_IDENTIFIED_PTM_STRING_FACET(PrideProjectField.PROJECT_IDENTIFIED_PTM_STRING_FACET, true, true, false, false, ConstantsSolrTypes.STRING, PrideProjectField.PROJECT_IDENTIFIED_PTM_STRING);

    private String  value;
    private Boolean facet;
    private Boolean multiValue;
    private Boolean dynamic;
    private Boolean highlight;
    private ConstantsSolrTypes type;
    private String filterField;

    PrideProjectFieldEnum(String value, Boolean facet, Boolean multiValue, Boolean dynamic, Boolean highlight, ConstantsSolrTypes type,  String filterField) {
        this.value = value;
        this.facet = facet;
        this.multiValue = multiValue;
        this.type = type;
        this.dynamic = dynamic;
        this.highlight = highlight;
        this.filterField = filterField;
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
}
