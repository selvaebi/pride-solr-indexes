package uk.ac.ebi.pride.solr.indexes.pride.model;

import org.springframework.data.solr.core.query.Field;

/**
 * Definition of terms for PRIDE Project. This class is a helper for the Model and query builders.
 *
 * @author ypriverol
 * @version $Id$
 *
 */
public interface PrideProjectField {

    /** Project Accession **/
    public String ACCESSION = "accession";

     /** Project Title **/
    public String PROJECT_TILE = "project_title";

    /** Additional Attributes Accessions **/
    public String ADDITIONAL_ATTRIBUTES = "additional_attributes_*";

    /** Project Description **/
    public String PROJECT_DESCRIPTION = "project_description";

    /** Sample Protocol **/
    public String PROJECT_SAMPLE_PROTOCOL = "project_sample_protocol";

    /** Data Processing Protocol **/
    public String PROJECT_DATA_PROTOCOL = "project_data_protocol";

    /** Project Tags **/
    public String PROJECT_TAGS = "project_tags";

    /** Keywords **/
    public String PROJECT_KEYWORDS = "project_keywords";

    /** PROJECT DOI **/
    public String PROJECT_DOI = "project_doi";

    /** PROJECT OMICS **/
    public String PROJECT_OMICS_LINKS = "project_other_omics";

    /** Submission Type **/
    public String PROJECT_SUBMISSION_TYPE = "project_submission_type";

    /** Submission Date **/
    public String PROJECT_SUBMISSION_DATE = "submission_date";

    /** Publication Date **/
    public String PROJECT_PUBLICATION_DATE = "publication_date";

    /** Update Date **/
    public String PROJECT_UPDATED_DATE = "updated_date";

    /** Submitter FirstName **/
    public String PROJECT_SUBMITTER = "submitters";

    /** List of Lab Head Names **/
    public String PROJECT_PI_NAMES = "lab_heads";

    /** Lab Head Affiliation  */
    public String AFFILIATIONS = "affiliations";

    /** List of instruments Ids*/
    public String INSTRUMENTS = "instruments_*";

     /** This field store all the countries associated with the experiment **/
    public String COUNTRIES = "project_countries";

    /** Experimental Factor Names **/
    public String EXPERIMENTAL_FACTORS_NAMES = "experimental_factors_*";

    /** Species ***/
    public String SPECIES = "species";

    /** References related with the project **/
    public String PROJECT_REFERENCES = "project_references";

    /** Is public or Private **/
    public String PROJECT_PUBLIC = "project_public";


    /* This field is not store, so when you retrieve the value from solr is always null */
    public String PROTEIN_IDENTIFICATIONS = "protein_identifications";

    /** This field is not store, so when you retrieve the value from solr is always null  **/
    public String PEPTIDE_SEQUENCES  = "peptide_sequences";

    /** Identified PTMs in the Project**/
    public String PROJECT_IDENTIFIED_PTM_STRING = "project_identified_ptms";


}
