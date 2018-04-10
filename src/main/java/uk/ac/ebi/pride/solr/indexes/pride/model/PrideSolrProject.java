package uk.ac.ebi.pride.solr.indexes.pride.model;


import lombok.Data;
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
import java.util.stream.Collectors;

/**
 * The {@link PrideSolrProject} provides a mechanism to retrieve from SolrCloud the project information. For the composite testdata types such as {@link CvParamProvider} the testdata structure should be partitioned in Arrays values:
 *   - Accession Array
 *   - Name Array
 * In some cases is relevant to add the parent Terms for the original terms into the search. For example in the Taxonomy or Instrument, it isi important to add to the Project the parent term.
 *
 * @author ypriverol
 * @version $Id$
 */

@Data
@SolrDocument(solrCoreName = "pride_projects")
public class PrideSolrProject implements ProjectProvider, PrideProjectField {

    /** Project accession is used in solr to identified the document, The accession will be bosst compare with any other field **/


    @Id
    @Indexed(name = ACCESSION, boost = 1.0f, stored = true, searchable = true)
    private String accession;

    /** Experiment Title **/
    @Indexed(name = PROJECT_TILE, boost = 0.8f, stored = true, searchable = true)
    private String title;

    /** Additional Attributes Identifiers **/
    @Dynamic
    @Indexed(name = ADDITIONAL_ATTRIBUTES, boost = 0.4f, stored = true, searchable = true)
    private Map<String, String> additionalAttributes;

    /** Project Description **/
    @Indexed(name = PROJECT_DESCRIPTION, boost = 0.7f, stored = true, searchable = true)
    private String projectDescription;

    /** Sample Protocol **/
    @Indexed(name = PROJECT_SAMPLE_PROTOCOL, boost = 0.6f, stored = true, searchable = true)
    private String sampleProcessingProtocol;

    /** Data Processing Protocol **/
    @Indexed(name = PROJECT_DATA_PROTOCOL, boost = 0.6f, stored = true, searchable = true)
    private String dataProcessingProtocol;

    /** Project Tags **/
    @Indexed(name = PROJECT_TAGS, boost = 0.2f, stored = true, searchable = true)
    private List<String> projectTags;

    /** Keywords **/
    @Indexed(name = PROJECT_KEYWORDS, boost = 0.2f, stored = true, searchable = true)
    private List<String> keywords;

    /** Original Doi of the dataset. The actual Doi is not needed in the Dataaset **/
    @Indexed(name = PROJECT_DOI, boost = 0.2f, searchable = true)
    private String doi;

    /** otherOmicsLinks **/
    @Indexed(name = PROJECT_OMICS_LINKS, boost = 0.2f, searchable = true)
    private List<String> otherOmicsLinks;

    /** Submission Type **/
    @Indexed(name = PROJECT_SUBMISSION_TYPE, boost = 0.2f, searchable = true, stored = true)
    private String submissionType;

    /** Submission Date **/
    @Indexed(name = PROJECT_SUBMISSION_DATE, boost = 0.2f, searchable = true, stored = true)
    private Date submissionDate;

    /** Publication Date **/
    @Indexed(name = PROJECT_PUBLICATION_DATE, boost = 0.2f, searchable = true, stored = true)
    private Date publicationDate;

    /** Updated Date **/
    @Indexed(name = PROJECT_UPDATED_DATE, boost = 0.2f, searchable = true)
    private Date updatedDate;

    /** Submitter FirstName **/
    @Indexed(name = PROJECT_SUBMITTER, boost = 0.2f, searchable = true, stored = true )
    private List<String> submitters;

    /** List of Lab Head Names **/
    @Indexed(name = PROJECT_PI_NAMES, boost = 0.2f, searchable = true, stored = true )
    private List<String> labPIs;

    /** Affiliations  */
    @Indexed(name = AFFILIATIONS, boost = 0.2f, searchable = true, stored = true)
    private List<String> affiliations;

    /** List of instruments Ids*/
    @Dynamic
    @Indexed(name = INSTRUMENTS, boost = 0.1f, searchable = true, stored = true)
    private Map<String, String> instruments;

    /** This field store all the countries associated with the experiment **/
    @Indexed(name = COUNTRIES, boost = 0.4f, searchable = true, stored = true)
    private List<String> allCountries;

    /** Experimental Factor Names **/
    @Dynamic
    @Indexed(name = EXPERIMENTAL_FACTORS_NAMES, boost = 0.5f, searchable = true, stored = true)
    private Map<String, List<String>> experimentalFactors;

    /** References related with the project **/
    @Indexed(name = PROJECT_REFERENCES, boost = 0.7f, searchable = true)
    private List<String> references;

    /** Public project or private   **/
    @Indexed(name = PROJECT_PUBLIC, boost = 0.7f, searchable = true)
    private boolean publicProject;

    /* This field is not store, so when you retrieve the value from solr is always null */
    @Indexed(name = PROTEIN_IDENTIFICATIONS, boost = 0.6f, stored = false, searchable = true)
    private Set<String> proteinIdentifications;

    /** This field is not store, so when you retrieve the value from solr is always null  **/
    @Indexed(name = PEPTIDE_SEQUENCES, boost = 0.6f, stored = false, searchable = true)
    private Set<String> peptideSequences;

    /** Highlights of values that has been found for the Solr Search **/
    @Indexed(name = PROJECT_IDENTIFIED_PTM, boost = 0.6f, stored = true, searchable = true )
    private List<String> identifiedPTMs;

    /** highlights f the search **/
    private Map<String, List<String>> highlights;

    /** Return the accession for the Project **/
    @Override
    public Comparable getId() {
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
        return experimentalFactors.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
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
    public String toString() {
        return "PrideSolrProject{" +
                "accession='" + accession + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
