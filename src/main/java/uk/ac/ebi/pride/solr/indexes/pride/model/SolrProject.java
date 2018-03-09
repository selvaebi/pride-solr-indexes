package uk.ac.ebi.pride.solr.indexes.pride.model;



import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.DefaultCvParam;
import uk.ac.ebi.pride.archive.dataprovider.param.ParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.project.ProjectProvider;
import uk.ac.ebi.pride.archive.dataprovider.reference.ReferenceProvider;
import uk.ac.ebi.pride.archive.dataprovider.user.ContactProvider;

import java.util.*;

/**
 * The {@link SolrProject} provides a mechanism to retrieve from SolrCloud the project information. For the composite data types such as {@link CvParamProvider} the data structure should be partitioned in Arrays values:
 *  - Accession Array
 *  - Name Array
 *  In some cases is relevant to add the parent Terms for the original terms into the search. For example in the Taxonomy or Instrument, it isi important to add to the Project the parent term.
 *  values: Accession
 * @author @author
 * @version $Id$
 */

@SolrDocument(solrCoreName = "prideprojects")
public class SolrProject implements ProjectProvider {

    /** Project accession is used in solr to identified the document, The accession will be bosst compare with any other field **/
    @Id
    @Indexed(boost = 1.0f, stored = true, searchable = true)
    @Field("accession")
    private String accession;

    /** Experiment Title **/
    @Field("project_title")
    @Indexed(boost = 0.8f, stored = true, searchable = true)
    private String title;

    /** Additional Attributes Identifiers **/
    @Field("additional_attributes_ids")
    @Indexed(boost = 0.4f, stored = true, searchable = true)
    private List<String> additionalAttributesIds;

    /** Additional Attributes Names **/
    @Field("additional_attributes_names")
    @Indexed(boost = 0.4f, stored = true, searchable = true)
    private List<String> additionalAttributesNames;

    /** Project Description **/
    @Field("project_description")
    @Indexed(boost = 0.7f, stored = true, searchable = true)
    private String projectDescription;

    /** Sample Protocol **/
    @Field("project_sample_protocol")
    @Indexed(boost = 0.6f, stored = true, searchable = true)
    private String sampleProcessingProtocol;

    /** Data Processing Protocol **/
    @Field("project_data_protocol")
    @Indexed(boost = 0.6f, stored = true, searchable = true)
    private String dataProcessingProtocol;

    /** Project Tags **/
    @Field("project_tags")
    @Indexed(boost = 0.2f, stored = true, searchable = true)
    private List<String> projectTags;

    /** Keywords **/
    @Field("project_keywords")
    @Indexed(boost = 0.2f, stored = true, searchable = true)
    private List<String> keywords;

    /** Original Doi of the dataset. The actual Doi is not needed in the Dataaset **/
    @Field("project_doi")
    @Indexed(boost = 0.2f, searchable = true)
    private String doi;

    /** otherOmicsLinks **/
    @Field("project_other_omics")
    @Indexed(boost = 0.2f, searchable = true)
    private List<String> otherOmicsLinks;

    /** Submission Type **/
    @Field("project_submission_type")
    @Indexed(boost = 0.2f, searchable = true, stored = true)
    private String submissionType;

    /** Submission Date **/
    @Field("submission_date")
    @Indexed(boost = 0.2f, searchable = true, stored = true)
    private Date submissionDate;

    /** Publication Date **/
    @Field("publication_date")
    @Indexed(boost = 0.2f, searchable = true, stored = true)
    private Date publicationDate;

    /** Updated Date **/
    @Field("updated_date")
    @Indexed(boost = 0.2f, searchable = true)
    private Date updatedDate;

    /** Submitter FirstName **/
    @Field("submitter_first_name")
    @Indexed(boost = 0.2f, searchable = true, stored = true )
    private String submitterFirstName;

    /** Submitter Last Name **/
    @Field("submitter_last_name")
    @Indexed(boost = 0.2f, searchable = true, stored = true )
    private String submitterLastName;

    /** Submitter Affiliation **/
    @Field("submitter_affiliation")
    @Indexed(boost = 0.2f, searchable = true, stored = true )
    private String submitterAffiliation;

    /** List of Lab Head Names **/
    @Field("lab_head_names")
    @Indexed(boost = 0.2f, searchable = true, stored = true )
    private List<String> labHeadNames;

    /** List of Head LastName **/
    @Field("lab_head_last_names")
    @Indexed(boost = 0.2f, searchable = true, stored = true )
    private List<String> labHeadLastNames;

    /** Lab Head Affiliation  */
    @Field("lab_head_affiliation")
    @Indexed(boost = 0.2f, searchable = true, stored = true )
    private List<String> labHeadAffiliations;

    /** List of instruments Ids*/
    @Field("instrument_ids")
    @Indexed(boost = 0.1f, searchable = true, stored = true)
    private List<String> instrumentIds;

    /** List of instrument Names **/
    @Field("instrument_names")
    @Indexed(boost = 0.4f, searchable = true, stored = true)
    private List<String> instrumentNames;

    /** This field store all the countries associated with the experiment **/
    @Field("project_countries")
    @Indexed(boost = 0.4f, searchable = true, stored = true)
    private List<String> allCountries;

    /** All affiliations **/
    @Field("project_affiliations")
    @Indexed(boost = 0.4f, searchable = true, stored = true)
    private List<String> allAffiliations;

    /** Experimental Factor Names **/
    @Field("experimental_factor_names")
    @Indexed(boost = 0.5f, searchable = true, stored = true)
    private List<String> experimentalFactors;

    /** References related with the project **/
    @Field("project_references")
    @Indexed(boost = 0.7f, searchable = true)
    private List<String> references;

    /** Public project or private   **/
    @Field("project_public")
    private boolean publicProject;

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAdditionalAttributesIds(List<String> additionalAttributesIds) {
        this.additionalAttributesIds = additionalAttributesIds;
    }

    public void setAdditionalAttributesNames(List<String> additionalAttributesNames) {
        this.additionalAttributesNames = additionalAttributesNames;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public void setSampleProcessingProtocol(String sampleProcessingProtocol) {
        this.sampleProcessingProtocol = sampleProcessingProtocol;
    }

    public void setDataProcessingProtocol(String dataProcessingProtocol) {
        this.dataProcessingProtocol = dataProcessingProtocol;
    }

    public void setProjectTags(List<String> projectTags) {
        this.projectTags = projectTags;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public void setOtherOmicsLinks(List<String> otherOmicsLinks) {
        this.otherOmicsLinks = otherOmicsLinks;
    }

    public void setSubmissionType(String submissionType) {
        this.submissionType = submissionType;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setSubmitterFirstName(String submitterFirstName) {
        this.submitterFirstName = submitterFirstName;
    }

    public void setSubmitterLastName(String submitterLastName) {
        this.submitterLastName = submitterLastName;
    }

    public void setSubmitterAffiliation(String submitterAffiliation) {
        this.submitterAffiliation = submitterAffiliation;
    }

    public void setLabHeadNames(List<String> labHeadNames) {
        this.labHeadNames = labHeadNames;
    }

    public void setLabHeadLastNames(List<String> labHeadLastNames) {
        this.labHeadLastNames = labHeadLastNames;
    }

    public void setLabHeadAffiliations(List<String> labHeadAffiliations) {
        this.labHeadAffiliations = labHeadAffiliations;
    }

    public void setInstrumentIds(List<String> instrumentIds) {
        this.instrumentIds = instrumentIds;
    }

    public void setInstrumentNames(List<String> instrumentNames) {
        this.instrumentNames = instrumentNames;
    }

    public void setAllCountries(List<String> allCountries) {
        this.allCountries = allCountries;
    }

    public void setExperimentalFactors(List<String> experimentalFactors) {
        this.experimentalFactors = experimentalFactors;
    }

    public void setReferences(List<String> references) {
        this.references = references;
    }

    public void setPublicProject(boolean publicProject) {
        this.publicProject = publicProject;
    }

    /** Return the accession for the Project **/
    @Override
    public Comparable getId() {
        return accession;
    }

    /** Return the accession for the Project **/
    @Override
    public String getAccession() {
        return accession;
    }

    /** Get the title of the project **/
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Additional attributes are used to handle more metadata around the Project not only the mandatory fields.
     * @return Collection of ParamProvider
     */
    @Override
    public Collection<? extends ParamProvider> getAdditionalAttributes() {
        List<CvParamProvider> additionalCvParams = new ArrayList<CvParamProvider>();
        if (additionalAttributesIds!=null && additionalAttributesNames!= null && additionalAttributesIds.size() == additionalAttributesNames.size()) {
            for (int i = 0; i < additionalAttributesIds.size(); i++) {
                CvParamProvider cvParam = new DefaultCvParam(additionalAttributesIds.get(i), additionalAttributesNames.get(i));
                additionalCvParams.add(cvParam);
            }
        }
        return additionalCvParams;
    }

    /**
     * @return Get Project Description
     */
    @Override
    public String getProjectDescription() {
        return projectDescription;
    }

    /**
     * @return Get Sample Processing
     */
    @Override
    public String getSampleProcessingProtocol() {
        return sampleProcessingProtocol;
    }

    /**
     * @return Get Data Processing
     */
    @Override
    public String getDataProcessingProtocol() {
        return dataProcessingProtocol;
    }

    @Override
    public Collection<? extends String> getKeywords() {
        return keywords;
    }

    @Override
    public Collection<? extends String> getProjectTags() {
        return projectTags;
    }

    /**
     * The experiment types is better to store the information in List of terms rather
     * than CVParams. In this implementation of solr the list is always EMPTY_LIST
     * @return empty list
     */
    @Override
    public Collection<? extends CvParamProvider> getExperimentTypes() {
        return Collections.emptyList();
    }

    @Override
    public Collection<? extends CvParamProvider> getPtms() {
        return Collections.emptyList();
    }

    /**
     * Return the list of Instruments related with the project. The Project returns the information
     * @return
     */
    @Override
    public Collection<? extends CvParamProvider> getInstruments() {
        List<CvParamProvider> instruments = new ArrayList<CvParamProvider>();
        if (instrumentIds !=null && instrumentNames != null && instrumentIds.size() == instrumentNames.size()) {
            for (int i = 0; i < instrumentIds.size(); i++) {
                CvParamProvider cvParam = new DefaultCvParam(instrumentIds.get(i), instrumentNames.get(i));
                instruments.add(cvParam);
            }
        }
        return instruments;
    }

    @Override
    public Optional<String> getDoi() {
        return Optional.ofNullable(doi);
    }

    @Override
    public Collection<? extends String> getOtherOmicsLink() {
        return otherOmicsLinks;
    }

    @Override
    public boolean isPublicProject() {
        return publicProject;
    }

    @Override
    public String getSubmissionType() {
        return submissionType;
    }

    @Override
    public Date getSubmissionDate() {
        return submissionDate;
    }

    @Override
    public Date getPublicationDate() {
        return publicationDate;
    }

    @Override
    public Date getUpdateDate() {
        return updatedDate;
    }

    @Override
    public Collection<? extends String> getCountriesAsString(){
        return allCountries;
    };

    /**
     * The List of terms Experimental Factors Names
     * @return Experimental factor names.
     */
    @Override
    public Collection<? extends String> getExperimentalFactorNamesAsString(){
        return experimentalFactors;
    }

    /**
     * Each Reference is Converted into an String and is added to a List
     * @return List of references.
     */
    @Override
    public Collection<? extends String> getReferencesAsString(){
        return references;
    }

    /** In the Solr implementation some of the {@link ProjectProvider} methods are returning empty Collections. This can be
     * extended in the future depending on Solr performance.
     * */

    @Override
    public Map<? extends CvParamProvider, ? extends CvParamProvider> getExperimentalFactors() {
        return Collections.emptyMap();
    }

    @Override
    public Collection<? extends CvParamProvider> getSoftwares() {
        return Collections.emptyList();
    }

    @Override
    public Collection<? extends CvParamProvider> getQuantificationMethods() {
        return Collections.emptyList();
    }

    @Override
    public Collection<? extends ReferenceProvider> getReferences() {
        return Collections.emptyList();
    }

    @Override
    public <T extends ContactProvider> Optional<ContactProvider> getSubmitter() {
        return Optional.empty();
    }

    @Override
    public Collection<? extends ContactProvider> getHeadLab() {
        return Collections.emptyList();
    }
}
