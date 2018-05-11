package uk.ac.ebi.pride.solr.indexes.pride.model;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.repository.Score;
import uk.ac.ebi.pride.archive.dataprovider.dataset.ProjectProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.user.ContactProvider;
import uk.ac.ebi.pride.solr.indexes.pride.utils.StringUtils;
import uk.ac.ebi.pride.utilities.term.CvTermReference;
import uk.ac.ebi.pride.utilities.util.Tuple;

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
@SolrDocument(solrCoreName = PrideProjectField.PRIDE_PROJECTS_COLLECTION_NAME)
public class PrideSolrProject implements ProjectProvider, PrideProjectField {

    /** Project accession is used in solr to identified the document, The accession will be bosst compare with any other field **/

    @Id
    String id;

    @Indexed(name = ACCESSION, boost = 1.0f, required = true)
    private String accession;

    /** Experiment Title **/
    @Indexed(name = PROJECT_TILE, boost = 0.8f)
    private String title;

    /** Additional Attributes Identifiers **/
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @Indexed(name = ADDITIONAL_ATTRIBUTES, boost = 0.4f)
    private List<String> additionalAttributes;

    /** Additional Attributes Identifiers **/
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @Indexed(name = ADDITIONAL_ATTRIBUTES_FACET, boost = 0.4f)
    private Set<String> additionalAttributesFacet;

    /** Project Description **/
    @Indexed(name = PROJECT_DESCRIPTION, boost = 0.7f)
    private String projectDescription;

    /** Sample Protocol **/
    @Indexed(name = PROJECT_SAMPLE_PROTOCOL, boost = 0.6f)
    private String sampleProcessingProtocol;

    /** Data Processing Protocol **/
    @Indexed(name = PROJECT_DATA_PROTOCOL, boost = 0.6f)
    private String dataProcessingProtocol;

    /** Project Tags **/
    @Indexed(name = PROJECT_TAGS, boost = 0.2f)
    private List<String> projectTags;

    /** Project tags facet **/
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    @Indexed(name = PROJECT_TAGS_FACET, type = "string")
    private List<String> projectTagsFacets;

    /** Keywords **/
    @Indexed(name = PROJECT_KEYWORDS, boost = 0.2f)
    private List<String> keywords;

    /** Projects keywords facet **/
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    @Indexed(name = PROJECT_KEYWORDS_FACET, type = "string")
    private List<String> keywordsFacets;


    /** Original Doi of the dataset. The actual Doi is not needed in the Dataset **/
    @Indexed(name = PROJECT_DOI, boost = 0.2f)
    private String doi;

    /** otherOmicsLinks **/
    @Indexed(name = PROJECT_OMICS_LINKS, boost = 0.2f)
    private List<String> otherOmicsLinks;

    /** Submission Type **/
    @Indexed(name = PROJECT_SUBMISSION_TYPE, boost = 0.2f)
    private String submissionType;

    /** Submission Date **/
    @Indexed(name = PROJECT_SUBMISSION_DATE, boost = 0.2f)
    private Date submissionDate;

    /** Publication Date **/
    @Indexed(name = PROJECT_PUBLICATION_DATE, boost = 0.2f)
    private Date publicationDate;

    /** Updated Date **/
    @Indexed(name = PROJECT_UPDATED_DATE, boost = 0.2f)
    private Date updatedDate;

    /** Submitter FirstName **/
    @Indexed(name = PROJECT_SUBMITTER, boost = 0.2f)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Set<String> submitters;

    /** List of Lab Head Names **/
    @Indexed(name = PROJECT_PI_NAMES, boost = 0.2f)
    private List<String> labPIs;

    /** PI facets **/
    @Indexed(name = PROJECT_PI_NAMES_FACET)
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private List<String> labPIsFacet;

    /** Affiliations */
    @Indexed(name = AFFILIATIONS, boost = 0.2f)
    private List<String> affiliations;

    /** Affiliations facet **/
    @Indexed(name = AFFILIATIONS_FACET)
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private Set<String> affiliationsFacet;

    /** List of instruments as key value pair*/
    @Indexed(name = INSTRUMENTS, boost = 0.1f)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private List<String> instruments;

    @Indexed(name = INSTRUMENTS_FACET)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Set<String> instrumentsFacet;

    /** List of softwares as key value pair*/
    @Indexed(name = SOFTWARES, boost = 0.1f)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private List<String> softwares;

    @Indexed(name = SOFTWARES_FACET)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Set<String> softwaresFacet;

    /** List of quantification methods as key value pair*/
    @Indexed(name = QUANTIFICATION_METHODS, boost = 0.1f)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private List<String> quantificationMethods;

    @Indexed(name = QUANTIFICATION_METHODS_FACET)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Set<String> quantificationMethodsFacet;


    /** This field store all the countries associated with the experiment **/
    @Indexed(name = COUNTRIES, boost = 0.4f)
    private Set<String> allCountries;

    @Indexed(name = COUNTRIES_FACET)
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private Set<String> allCountriesFacet;

    /** Experimental Factor Names **/
    @Indexed(name = EXPERIMENTAL_FACTORS_NAMES, boost = 0.5f)
    @Setter(AccessLevel.PRIVATE)
    private List<String> experimentalFactors;

    /** All additional experimental factors **/
    @Indexed(name = EXPERIMENTAL_FACTORS_FACET)
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private List<String> experimentalFactorFacets;

    /** Sample attributes names **/
    @Indexed(name = SAMPLE_ATTRIBUTES_NAMES, boost = 0.5f)
    @Setter(AccessLevel.PRIVATE)
    private List<String> sampleAttributes;

    /** sample attributes facet **/
    @Indexed(name = SAMPLE_ATTRIBUTES_FACET)
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private List<String> sampleAttributesFacets;

    /** Organisms **/
    @Indexed(name = ORGANISMS)
    @Setter(AccessLevel.PRIVATE)
    private Set<String> organisms;

    /** organism parts **/
    @Indexed(name = ORGANISMS_PART)
    @Setter(AccessLevel.PRIVATE)
    private Set<String> organismPart;

    /** diseases **/
    @Indexed(name = DISEASES)
    @Setter(AccessLevel.PRIVATE)
    private Set<String> diseases;

    /** References related with the project **/
    @Indexed(name = PROJECT_REFERENCES, boost = 0.7f)
    private Set<String> references;

    /* This field is not store, so when you retrieve the value from solr is always null */
    @Indexed(name = PROTEIN_IDENTIFICATIONS, boost = 0.6f, stored = false)
    private Set<String> proteinIdentifications;

    @Indexed(name = PROTEIN_IDENTIFICATIONS_FACET)
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private Set<String> proteinIdentificationFacets;

    /** This field is not store, so when you retrieve the value from solr is always null  **/
    @Indexed(name = PEPTIDE_SEQUENCES, boost = 0.6f, stored = false)
    private Set<String> peptideSequences;

    /** Highlights of values that has been found for the Solr Search **/
    @Indexed(name = PROJECT_IDENTIFIED_PTM_STRING, boost = 0.6f)
    private Set<String> identifiedPTMStrings;

    @Indexed(name = PROJECT_IDENTIFIED_PTM_STRING_FACET)
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private Set<String> identifiedPTMStringsFacet;

    /** Score for the search results **/
    @Score
    private Float score;

    /** highlights f the search **/
    private Map<String, List<String>> highlights;

    /**
     * This method set the list of additional attributes taking as a parameter a list of {@link CvParamProvider}
     * @param params List of params
     */
    public void setAdditionalAttributesFromCvParams(List<CvParamProvider> params){
        this.additionalAttributes = params.stream().map(CvParamProvider::getName).collect(Collectors.toList());
        this.additionalAttributesFacet = params.stream().map(CvParamProvider::getName).collect(Collectors.toSet());
    }

    /**
     * This method add the to the current list of additional attributes the given list of Cvparams
     * @param params List of params
     */
    public void addAdditionalAttributesFromCvParams(List<CvParamProvider> params){
        if(additionalAttributes == null)
            additionalAttributes = new ArrayList<>();
        this.additionalAttributes.addAll(params.stream().map(CvParamProvider::getName).collect(Collectors.toList()));
        if(additionalAttributesFacet == null)
            additionalAttributesFacet = new HashSet<>();
        this.additionalAttributesFacet.addAll(params.stream().map(CvParamProvider::getName).collect(Collectors.toSet()));
    }


    /**
     * This method set the list of quantification methods taking as a parameter a list of {@link CvParamProvider}
     * @param params List of params
     */
    public void setQuantificationMethodsFromCvParams(List<CvParamProvider> params){
        this.quantificationMethods = params.stream().map(CvParamProvider::getName).collect(Collectors.toList());
        this.quantificationMethodsFacet = params.stream().map(CvParamProvider::getName).collect(Collectors.toSet());
    }

    /**
     * This method add the to the current list of quantification methods the given list of Cvparams
     * @param params List of params
     */
    public void addQuantificationMethodsFromCvParams(List<CvParamProvider> params){
        if(quantificationMethods == null)
            quantificationMethods = new ArrayList<>();
        this.quantificationMethods.addAll(params.stream().map(CvParamProvider::getName).collect(Collectors.toList()));
        if(quantificationMethodsFacet == null)
            quantificationMethodsFacet = new HashSet<>();
        this.quantificationMethodsFacet.addAll(params.stream().map(CvParamProvider::getName).collect(Collectors.toSet()));
    }

    /**
     * The implementation of the Project Tags is needed to set also the Facet values.
     * @param projectTags projectsTags
     */
    public void setProjectTags(List<String> projectTags) {
        this.projectTags = projectTags;
        this.projectTagsFacets = projectTags.stream().map(StringUtils::convertSentenceStyle).collect(Collectors.toList());
    }

    /**
     * Set the keywords and the corresponding facets.
     * @param keywords Project Keywords
     */
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
        this.keywordsFacets = keywords.stream().map(StringUtils::convertSentenceStyle).collect(Collectors.toList());
    }

    /**
     * Set the Head and PIs of the project
     * @param labPIs {@link ContactProvider} of Lab PIs
     */
    public void setLabPIFromContacts(List<ContactProvider> labPIs) {
        this.labPIs = labPIs.stream().map(ContactProvider::getName).collect(Collectors.toList());
        this.labPIsFacet = this.labPIs.stream().map(StringUtils::convertSentenceStyle).collect(Collectors.toList());
    }

    /**
     * Set affiliations from String List
     * @param affiliations PIs and Submitters affiliations
     */
    public void setAffiliationsFromContacts(List<ContactProvider> affiliations) {
        this.affiliations = affiliations.stream().map(ContactProvider::getAffiliation).collect(Collectors.toList());
        this.affiliationsFacet = this.affiliations.stream().map(StringUtils::convertSentenceStyle).collect(Collectors.toSet());
    }

    /**
     * Set instruments from and instruments list
     * @param instrumentCvParams
     */
    public void setInstrumentsFromCvParam(List<CvParamProvider> instrumentCvParams) {
        this.instruments = instrumentCvParams.stream().map(CvParamProvider::getName).collect(Collectors.toList());
        this.instrumentsFacet = instrumentCvParams.stream().map(CvParamProvider::getName).collect(Collectors.toSet());
    }

    /**
     * Set Softwares information
     * @param softwares
     */
    public void setSoftwaresFromCvParam(List<CvParamProvider> softwares) {
        this.softwares = softwares.stream().map(CvParamProvider::getName).collect(Collectors.toList());
        this.softwaresFacet = softwares.stream().map(CvParamProvider::getName).collect(Collectors.toSet());
    }

    /**
     * Set all countries
     * @param allCountries
     */
    public void setAllCountries(Set<String> allCountries) {
        this.allCountries = allCountries;
        this.allCountriesFacet = allCountries.stream().map(StringUtils::convertSentenceStyle).collect(Collectors.toSet());
    }

    /**
     * Set Experimental Factors from the list of CVParams that are specify as a {@link Tuple}. The current
     * function takes the {@link CvTermReference} Organism, celltype and diseases
     * for the facet.
     *
     * @param experimentalFactors List of Experimental Factors.
     */
    public void setExperimentalFactors(List<Tuple<CvParamProvider, CvParamProvider>> experimentalFactors) {
        this.experimentalFactors = experimentalFactors.stream().map(Tuple::getKey).map(CvParamProvider::getName).collect(Collectors.toList());
        experimentalFactorFacets = this.experimentalFactors;

    }

    /**
     * Set sample Atributes from the list of CVParams that are specify as a {@link Tuple}. The current
     * function takes the {@link CvTermReference} Organism, celltype and diseases
     * for the facet.
     *
     * @param sampleAttributes List of Experimental Factors.
     */
    public void setSampleAttributes(List<CvParamProvider> sampleAttributes) {

        this.sampleAttributes = sampleAttributes.stream().map(CvParamProvider::getName).collect(Collectors.toList());
        this.sampleAttributesFacets = new ArrayList<>();

        organisms = new HashSet<>();
        organismPart = new HashSet<>();
        diseases  = new HashSet<>();

        sampleAttributes.forEach(x -> {
            if(StringUtils.isCvTerm(x.getAccession(), CvTermReference.EFO_ORGANISM))
                organisms.add(x.getName());
            else if(StringUtils.isCvTerm(x.getAccession(), CvTermReference.EFO_ORGANISM_PART))
                organismPart.add(x.getName());
            else if(StringUtils.isCvTerm(x.getAccession(), CvTermReference.EFO_DISEASE))
                diseases.add(x.getName());
            else
                sampleAttributesFacets.add(x.getName());
        });
    }

    /**
     * Set the PTMs
     * @param identifiedPTMS
     */
    public void setIdentifiedPTMStringsFromCvParam(List<CvParamProvider> identifiedPTMS) {
        this.identifiedPTMStrings = identifiedPTMS.stream().map(CvParamProvider::getName).collect(Collectors.toSet());
        this.identifiedPTMStringsFacet = this.identifiedPTMStrings.stream().map(StringUtils::convertSentenceStyle).collect(Collectors.toSet());
    }

    /** Return the accession for the Project **/
    @Override
    public Comparable getId() {
        return id;
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
    public Collection<String> getAdditionalAttributes() {
        return additionalAttributes;
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
    public Collection<String> getSubmitters() {
        return submitters;
    }

    @Override
    public Collection<String> getHeadLab() {
        return labPIs;
    }

    @Override
    public Collection<String> getKeywords() {
        return keywords;
    }

    @Override
    public Collection<String> getProjectTags() {
        return projectTags;
    }

    @Override
    public Collection<String> getPtms() {
        return null;
    }

    @Override
    public Collection<String> getInstruments() {
        return instruments;
    }

    @Override
    public Collection<String> getSoftwares() {
        return softwares;
    }

    @Override
    public Collection<String> getQuantificationMethods() {
        return quantificationMethods;
    }

    @Override
    public Optional<String> getDoi() {
        return Optional.ofNullable(doi);
    }

    @Override
    public Collection<String> getOtherOmicsLink() {
        return otherOmicsLinks;
    }

    @Override
    public boolean isPublicProject() {
        return true;
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


    /** In the Solr implementation some of the {@link ProjectProvider} methods are returning empty Collections. This can be
     * extended in the future depending on Solr performance.
     * */

    @Override
    public Collection<String> getExperimentalFactors() {
        return experimentalFactors;
    }

    @Override
    public Collection<String> getCountries() {
        return null;
    }

    @Override
    public Collection<String> getAllAffiliations() {
        return null;
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

    /**
     * Set the submitters to a new submitters value. This function create a new Set and
     * add the submitter.
     * @param submitter Submitter information
     */
    public void setSubmittersFromContacts(ContactProvider submitter) {
       this.submitters = new HashSet<>();
       addSubmittersFromContacts(submitter);
    }

    /**
     * Add a submitter, if the list is null it create as new list of submitters.
     * @param submitter Submitter information
     */
    public void addSubmittersFromContacts(ContactProvider submitter) {
        if(this.submitters == null)
            this.submitters = new HashSet<>();
        submitters.add(submitter.getName());
    }
}
