package uk.ac.ebi.pride.solr.indexes.pride.model;

/**
 * Definition of terms for PRIDE Project. This class is a helper for the Model and query builders.
 *
 * @author ypriverol
 * @version $Id$
 *
 */
public interface PrideProjectField {

    String ID = "id";

    /** Project Accession **/
    String ACCESSION = "accession";

     /** Project Title **/
    String PROJECT_TILE = "project_title";

    /** Additional Attributes Accessions **/
    String ADDITIONAL_ATTRIBUTES = "additional_attributes";
    String ADDITIONAL_ATTRIBUTES_FACET = "additional_attributes_facet";

    /** Project Description **/
    String PROJECT_DESCRIPTION = "project_description";

    /** Sample Protocol **/
    String PROJECT_SAMPLE_PROTOCOL = "project_sample_protocol";

    /** Data Processing Protocol **/
    String PROJECT_DATA_PROTOCOL = "project_data_protocol";

    /** Project Tags **/
    String PROJECT_TAGS = "project_tags";
    String PROJECT_TAGS_FACET = "project_tags_facet";

    /** Keywords **/
    String PROJECT_KEYWORDS = "project_keywords";
    String PROJECT_KEYWORDS_FACET = "project_keywords_facet";

    /** PROJECT DOI **/
    String PROJECT_DOI = "project_doi";

    /** PROJECT OMICS **/
    String PROJECT_OMICS_LINKS = "project_other_omics";

    /** Submission Type **/
    String PROJECT_SUBMISSION_TYPE = "project_submission_type";

    /** Submission Date **/
    String PROJECT_SUBMISSION_DATE = "submission_date";

    /** Publication Date **/
    String PROJECT_PUBLICATION_DATE = "publication_date";

    /** Update Date **/
    String PROJECT_UPDATED_DATE = "updated_date";

    /** Submitter FirstName **/
    String PROJECT_SUBMITTER = "submitters";

    /** List of Lab Head Names **/
    String PROJECT_PI_NAMES = "lab_heads";
    String PROJECT_PI_NAMES_FACET = "lab_heads_facet";

    /** Lab Head Affiliation  */
    String AFFILIATIONS = "affiliations";
    String AFFILIATIONS_FACET = "affiliations_facet";

    /** List of instruments Ids*/
    String INSTRUMENTS = "instruments";
    String INSTRUMENTS_FACET = "instruments_facet";

    String SOFTWARES = "softwares";
    String SOFTWARES_FACET = "softwares_facet";

    String QUANTIFICATION_METHODS = "quantification_methods";
    String QUANTIFICATION_METHODS_FACET = "quantification_methods_facet";


    /** This field store all the countries associated with the experiment **/
    String COUNTRIES = "countries";
    String COUNTRIES_FACET = "countries_facet";

    /** Experimental Factor Names **/
    String EXPERIMENTAL_FACTORS_NAMES = "experimental_factors";
    String EXPERIMENTAL_FACTORS_FACET = "experimental_factors_facet";

    /** Sample metadata names and facet **/
    String SAMPLE_ATTRIBUTES_NAMES = "sample_attributes";

    /** Species , cell types, tissues, diseases ***/
    String ORGANISMS_FACET = "organisms_facet";
    String ORGANISMS_PART_FACET = "organisms_part_facet";
    String DISEASES_FACET  = "diseases_facet";

    String ORGANISM = "organisms";
    String ORGANISM_PART = "organisms_part";
    String DISEASES = "diseases";

    /** References related with the project **/
    String PROJECT_REFERENCES = "project_references";

    /* This field is not store, so when you retrieve the value from solr is always null */
    String PROTEIN_IDENTIFICATIONS = "protein_identifications";
    String PROTEIN_IDENTIFICATIONS_FACET = "protein_identifications_facet";

    /** This field is not store, so when you retrieve the value from solr is always null  **/
    String PEPTIDE_SEQUENCES  = "peptide_sequences";

    /** Identified PTMs in the Project**/
    String PROJECT_IDENTIFIED_PTM_STRING = "project_identified_ptms";
    String PROJECT_IDENTIFIED_PTM_STRING_FACET = "project_identified_ptms_facet";

    String PRIDE_PROJECTS_COLLECTION_NAME = "pride_projects";
    String PRIDE_PROJECTS_CONFIG_NAME = "_default_pride_projects";

    /** Pride Field text **/
    String TEXT = "text";
}
