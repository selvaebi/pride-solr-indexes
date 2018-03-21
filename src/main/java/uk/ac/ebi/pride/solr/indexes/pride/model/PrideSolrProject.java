package uk.ac.ebi.pride.solr.indexes.pride.model;


import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Dynamic;
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
 * The {@link PrideSolrProject} provides a mechanism to retrieve from SolrCloud the project information. For the composite testdata types such as {@link CvParamProvider} the testdata structure should be partitioned in Arrays values:
 *   - Accession Array
 *   - Name Array
 *  In some cases is relevant to add the parent Terms for the original terms into the search. For example in the Taxonomy or Instrument, it isi important to add to the Project the parent term.
 *  values: Accession
 * @author ypriverol
 * @version $Id$
 */

@SolrDocument(solrCoreName = "projects")
public class PrideSolrProject implements ProjectProvider, PrideProjectField {

    /** Project accession is used in solr to identified the document, The accession will be bosst compare with any other field **/
    @Id
    @Field(value = ACCESSION)
    @Indexed(boost = 1.0f, stored = true, searchable = true)
    private String accession;

    /** Experiment Title **/
    @Field(PROJECT_TILE)
    @Indexed(boost = 0.8f, stored = true, searchable = true)
    private String title;

    /** Additional Attributes Identifiers **/
    @Field(ADDITIONAL_ATTRIBUTES)
    @Dynamic
    @Indexed(boost = 0.4f, stored = true, searchable = true)
    private Map<String, String> additionalAttributes;

    /** Project Description **/
    @Field(PROJECT_DESCRIPTION)
    @Indexed(boost = 0.7f, stored = true, searchable = true)
    private String projectDescription;

    /** Sample Protocol **/
    @Field(PROJECT_SAMPLE_PROTOCOL)
    @Indexed(boost = 0.6f, stored = true, searchable = true)
    private String sampleProcessingProtocol;

    /** Data Processing Protocol **/
    @Field(PROJECT_DATA_PROTOCOL)
    @Indexed(boost = 0.6f, stored = true, searchable = true)
    private String dataProcessingProtocol;

    /** Project Tags **/
    @Field(PROJECT_TAGS)
    @Indexed(boost = 0.2f, stored = true, searchable = true)
    private List<String> projectTags;

    /** Keywords **/
    @Field(PROJECT_KEYWORDS)
    @Indexed(boost = 0.2f, stored = true, searchable = true)
    private List<String> keywords;

    /** Original Doi of the dataset. The actual Doi is not needed in the Dataaset **/
    @Field(PROJECT_DOI)
    @Indexed(boost = 0.2f, searchable = true)
    private String doi;

    /** otherOmicsLinks **/
    @Field(PROJECT_OMICS_LINKS)
    @Indexed(boost = 0.2f, searchable = true)
    private List<String> otherOmicsLinks;

    /** Submission Type **/
    @Field(PROJECT_SUBMISSION_TYPE)
    @Indexed(boost = 0.2f, searchable = true, stored = true)
    private String submissionType;

    /** Submission Date **/
    @Field(PROJECT_SUBMISSION_DATE)
    @Indexed(boost = 0.2f, searchable = true, stored = true)
    private Date submissionDate;

    /** Publication Date **/
    @Field(PROJECT_PUBLICATION_DATE)
    @Indexed(boost = 0.2f, searchable = true, stored = true)
    private Date publicationDate;

    /** Updated Date **/
    @Field(PROJECT_UPDATED_DATE)
    @Indexed(boost = 0.2f, searchable = true)
    private Date updatedDate;

    /** Submitter FirstName **/
    @Field(PROJECT_SUBMITTER)
    @Dynamic
    @Indexed(boost = 0.2f, searchable = true, stored = true )
    private String submitters;

    /** List of Lab Head Names **/
    @Field(PROJECT_PI_NAMES)
    @Dynamic
    @Indexed(boost = 0.2f, searchable = true, stored = true )
    private Map<String, String> labPIs;

    /** Affiliations  */
    @Field(AFFILIATIONS)
    @Indexed(boost = 0.2f, searchable = true, stored = true )
    private List<String> affiliations;

    /** List of instruments Ids*/
    @Field(INSTRUMENTS)
    @Dynamic
    @Indexed(boost = 0.1f, searchable = true, stored = true)
    private Map<String, String> instruments;

    /** This field store all the countries associated with the experiment **/
    @Field(COUNTRIES)
    @Indexed(boost = 0.4f, searchable = true, stored = true)
    private List<String> allCountries;

    /** Experimental Factor Names **/
    @Field(EXPERIMENTAL_FACTORS_NAMES)
    @Dynamic
    @Indexed(boost = 0.5f, searchable = true, stored = true)
    private Map<String, String> experimentalFactors;

    /** References related with the project **/
    @Field(PROJECT_REFERENCES)
    @Indexed(boost = 0.7f, searchable = true)
    private List<String> references;

    /** Public project or private   **/
    @Field(PROJECT_PUBLIC)
    @Indexed(boost = 0.7f, searchable = true)
    private boolean publicProject;

    /* This field is not store, so when you retrieve the value from solr is always null */
    @Field(PROTEIN_IDENTIFICATIONS)
    @Indexed(boost = 0.6f, stored = false, searchable = true)
    private Set<String> proteinIdentifications;

    /** This field is not store, so when you retrieve the value from solr is always null  **/
    @Field(PEPTIDE_SEQUENCES)
    @Indexed(boost = 0.6f, stored = false, searchable = true)
    private Set<String> peptideSequences;

    /** Highligths of values that has been found for the Solr Search **/

    @Field(PROJECT_IDENTIFIED_PTM)
    @Indexed(boost = 0.6f, stored = true, searchable = true )
    @Dynamic
    private Map<String, String> identifiedPTMs;

    private Map<String, List<String>> highlights;

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setReferences(List<String> references) {
        this.references = references;
    }

    public void setPublicProject(boolean publicProject) {
        this.publicProject = publicProject;
    }

    public void setAdditionalAttributes(Map<String, String> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    public List<String> getOtherOmicsLinks() {
        return otherOmicsLinks;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public String getSubmitters() {
        return submitters;
    }

    public void setSubmitters(String submitters) {
        this.submitters = submitters;
    }

    public Map<String, String> getLabPIs() {
        return labPIs;
    }

    public void setLabPIs(Map<String, String> labPIs) {
        this.labPIs = labPIs;
    }

    public List<String> getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(List<String> affiliations) {
        this.affiliations = affiliations;
    }

    public void setInstruments(Map<String, String> instruments) {
        this.instruments = instruments;
    }

    public List<String> getAllCountries() {
        return allCountries;
    }

    public void setAllCountries(List<String> allCountries) {
        this.allCountries = allCountries;
    }

    public void setExperimentalFactors(Map<String, String> experimentalFactors) {
        this.experimentalFactors = experimentalFactors;
    }

    public Map<String, String> getIdentifiedPTMs() {
        return identifiedPTMs;
    }

    public void setIdentifiedPTMs(Map<String, String> identifiedPTMs) {
        this.identifiedPTMs = identifiedPTMs;
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
        if (additionalAttributes != null) {
            additionalAttributes.forEach((key, value) -> additionalCvParams.add(new DefaultCvParam(key, value)));
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
        if (this.instruments != null ) {
           this.instruments.forEach((x, y) -> instruments.add(new DefaultCvParam(x, y)));
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
        return experimentalFactors.values();
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

    public Set<String> getProteinIdentifications() {
        return proteinIdentifications;
    }

    public void setProteinIdentifications(Set<String> proteinIdentifications) {
        this.proteinIdentifications = proteinIdentifications;
    }

    public Set<String> getPeptideSequences() {
        return peptideSequences;
    }

    public void setPeptideSequences(Set<String> peptideSequences) {
        this.peptideSequences = peptideSequences;
    }

    public Map<String, List<String>> getHighlights() {
        return highlights;
    }

    public void setHighlights(Map<String, List<String>> highlights) {
        this.highlights = highlights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrideSolrProject that = (PrideSolrProject) o;

        return accession != null ? accession.equals(that.accession) : that.accession == null;
    }

    @Override
    public int hashCode() {
        return accession != null ? accession.hashCode() : 0;
    }


    @Override
    public String getName() {
        return null;
    }
}
