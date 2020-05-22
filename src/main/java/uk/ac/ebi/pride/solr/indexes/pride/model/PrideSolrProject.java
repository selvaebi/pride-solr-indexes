package uk.ac.ebi.pride.solr.indexes.pride.model;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.repository.Score;
import uk.ac.ebi.pride.archive.dataprovider.common.Tuple;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.ParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.project.ProjectProvider;
import uk.ac.ebi.pride.archive.dataprovider.user.ContactProvider;
import uk.ac.ebi.pride.solr.indexes.pride.utils.StringUtils;
import uk.ac.ebi.pride.utilities.term.CvTermReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The {@link PrideSolrProject} provides a mechanism to retrieve from SolrCloud the project information. For the composite testdata types such as {@link uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider} the testdata structure should be partitioned in Arrays values:
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
    @Indexed(name = ID)
    @Getter(AccessLevel.NONE)
    String id;

    @Field(ACCESSION)
    @Indexed(name = ACCESSION, boost = 1.0f, required = true)
    private String accession;

    /** Experiment Title **/
    @Field(PROJECT_TILE)
    @Indexed(name = PROJECT_TILE, boost = 0.8f)
    private String title;

    /** Additional Attributes Identifiers **/
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
//    @Field(ADDITIONAL_ATTRIBUTES)
    @Indexed(name = ADDITIONAL_ATTRIBUTES, boost = 0.4f)
    private Set<String> additionalAttributes;

    /** Additional Attributes Identifiers **/
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
//    @Field(ADDITIONAL_ATTRIBUTES_FACET)
    @Indexed(name = ADDITIONAL_ATTRIBUTES_FACET, boost = 0.4f)
    private Set<String> additionalAttributesFacet;

    /** Project Description **/
//    @Field(PROJECT_DESCRIPTION)
    @Indexed(name = PROJECT_DESCRIPTION, boost = 0.7f)
    private String projectDescription;

    /** Sample Protocol **/
//    @Field(PROJECT_SAMPLE_PROTOCOL)
    @Indexed(name = PROJECT_SAMPLE_PROTOCOL, boost = 0.6f)
    private String sampleProcessingProtocol;

    /** Data Processing Protocol **/
//    @Field(PROJECT_DATA_PROTOCOL)
    @Indexed(name = PROJECT_DATA_PROTOCOL, boost = 0.6f)
    private String dataProcessingProtocol;

    /** Project Tags **/
//    @Field(PROJECT_TAGS)
    @Indexed(name = PROJECT_TAGS, boost = 0.2f)
    private Set<String> projectTags;

    /** Project tags facet **/
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
//    @Field(PROJECT_TAGS_FACET)
    @Indexed(name = PROJECT_TAGS_FACET, type = "string")
    private Set<String> projectTagsFacets;

    /** Keywords **/
//    @Field(PROJECT_KEYWORDS)
    @Indexed(name = PROJECT_KEYWORDS, boost = 0.2f)
    private Set<String> keywords;

    /** Projects keywords facet **/
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
//    @Field(PROJECT_KEYWORDS_FACET)
    @Indexed(name = PROJECT_KEYWORDS_FACET, type = "string")
    private Set<String> keywordsFacets;


    /** Original Doi of the dataset. The actual Doi is not needed in the Dataset **/
//    @Field(PROJECT_DOI)
    @Indexed(name = PROJECT_DOI, boost = 0.2f)
    private String doi;

    /** otherOmicsLinks **/
//    @Field(PROJECT_OMICS_LINKS)
    @Indexed(name = PROJECT_OMICS_LINKS, boost = 0.2f)
    private Set<String> otherOmicsLinks;

    /** Submission Type **/
//    @Field(PROJECT_SUBMISSION_TYPE)
    @Indexed(name = PROJECT_SUBMISSION_TYPE, boost = 0.2f)
    private String submissionType;

    /** Submission Date **/
//    @Field(PROJECT_SUBMISSION_DATE)
    @Indexed(name = PROJECT_SUBMISSION_DATE, boost = 0.2f)
    private Date submissionDate;

    /** Publication Date **/
//    @Field(PROJECT_PUBLICATION_DATE)
    @Indexed(name = PROJECT_PUBLICATION_DATE, boost = 0.2f)
    private Date publicationDate;

    /** Updated Date **/
//    @Field(PROJECT_UPDATED_DATE)
    @Indexed(name = PROJECT_UPDATED_DATE, boost = 0.2f)
    private Date updatedDate;

    /** Submitter FirstName **/
//    @Field(PROJECT_SUBMITTER)
    @Indexed(name = PROJECT_SUBMITTER, boost = 0.2f)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Set<String> submitters;

    /** List of Lab Head Names **/
//    @Field(PROJECT_PI_NAMES)
    @Indexed(name = PROJECT_PI_NAMES, boost = 0.2f)
    private Set<String> labPIs;

    /** PI facets **/
//    @Field(PROJECT_PI_NAMES_FACET)
    @Indexed(name = PROJECT_PI_NAMES_FACET)
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private Set<String> labPIsFacet;

    /** Affiliations */
//    @Field(AFFILIATIONS)
    @Indexed(name = AFFILIATIONS, boost = 0.2f)
    private Set<String> affiliations;

    /** Affiliations facet **/
//    @Field(AFFILIATIONS_FACET)
    @Indexed(name = AFFILIATIONS_FACET)
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private Set<String> affiliationsFacet;

    /** List of instruments as key value pair*/
//    @Field(INSTRUMENTS)
    @Indexed(name = INSTRUMENTS, boost = 0.1f)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Set<String> instruments;

//    @Field(INSTRUMENTS_FACET)
    @Indexed(name = INSTRUMENTS_FACET)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Set<String> instrumentsFacet;

    /** List of softwares as key value pair*/
//    @Field(SOFTWARES)
    @Indexed(name = SOFTWARES, boost = 0.1f)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Set<String> softwares;

//    @Field(SOFTWARES_FACET)
    @Indexed(name = SOFTWARES_FACET)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Set<String> softwaresFacet;

    /** List of quantification methods as key value pair*/
//    @Field(QUANTIFICATION_METHODS)
    @Indexed(name = QUANTIFICATION_METHODS, boost = 0.1f)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Set<String> quantificationMethods;

//    @Field(QUANTIFICATION_METHODS_FACET)
    @Indexed(name = QUANTIFICATION_METHODS_FACET)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Set<String> quantificationMethodsFacet;


    /** This field store all the countries associated with the experiment **/
//    @Field(COUNTRIES)
    @Indexed(name = COUNTRIES, boost = 0.4f)
    private Set<String> allCountries;

//    @Field(COUNTRIES_FACET)
    @Indexed(name = COUNTRIES_FACET)
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private Set<String> allCountriesFacet;

    /** Experimental Factor Names **/
//    @Field(EXPERIMENTAL_FACTORS_NAMES)
    @Indexed(name = EXPERIMENTAL_FACTORS_NAMES, boost = 0.5f)
    @Setter(AccessLevel.NONE)
    private Set<String> experimentalFactors;

    /** All additional experimental factors **/
//    @Field(EXPERIMENTAL_FACTORS_FACET)
    @Indexed(name = EXPERIMENTAL_FACTORS_FACET)
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private Set<String> experimentalFactorFacets;

    /** Sample attributes names **/
//    @Field(SAMPLE_ATTRIBUTES_NAMES)
    @Indexed(name = SAMPLE_ATTRIBUTES_NAMES, boost = 0.5f)
    @Setter(AccessLevel.NONE)
    private Set<String> sampleAttributes;

    /** Organisms **/
//    @Field(ORGANISMS_FACET)
    @Indexed(name = ORGANISMS_FACET)
//    @Setter(AccessLevel.PRIVATE)
    private Set<String> organisms_facet;

    /** organism parts **/
//    @Field(ORGANISMS_PART_FACET)
    @Indexed(name = ORGANISMS_PART_FACET)
//    @Setter(AccessLevel.PRIVATE)
    private Set<String> organismPart_facet;

    /** diseases **/
//    @Field(DISEASES_FACET)
    @Indexed(name = DISEASES_FACET)
//    @Setter(AccessLevel.PRIVATE)
    private Set<String> diseases_facet;

    /** Organisms **/
//    @Field(ORGANISM)
    @Indexed(name = ORGANISM)
//    @Setter(AccessLevel.PRIVATE)
    private Set<String> organisms;

    /** organism parts **/
//    @Field(ORGANISM_PART)
    @Indexed(name = ORGANISM_PART)
//    @Setter(AccessLevel.PRIVATE)
    private Set<String> organismPart;

    /** diseases **/
//    @Field(DISEASES)
    @Indexed(name = DISEASES)
//    @Setter(AccessLevel.PRIVATE)
    private Set<String> diseases;

    /** References related with the project **/
//    @Field(PROJECT_REFERENCES)
    @Indexed(name = PROJECT_REFERENCES, boost = 0.7f)
    private Set<String> references;

    /* This field is not store, so when you retrieve the value from solr is always null */
//    @Field(PROTEIN_IDENTIFICATIONS)
    @Indexed(name = PROTEIN_IDENTIFICATIONS, boost = 0.6f, stored = false)
    private Set<String> proteinIdentifications;

//    @Field(PROTEIN_IDENTIFICATIONS_FACET)
    @Indexed(name = PROTEIN_IDENTIFICATIONS_FACET)
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private Set<String> proteinIdentificationFacets;

    /** This field is not store, so when you retrieve the value from solr is always null  **/
//    @Field(PEPTIDE_SEQUENCES)
    @Indexed(name = PEPTIDE_SEQUENCES, boost = 0.6f, stored = false)
    private Set<String> peptideSequences;

    /** Highlights of values that has been found for the Solr Search **/
//    @Field(PROJECT_IDENTIFIED_PTM_STRING)
    @Indexed(name = PROJECT_IDENTIFIED_PTM_STRING, boost = 0.6f)
    private Set<String> identifiedPTMStrings;

//    @Field(PROJECT_IDENTIFIED_PTM_STRING_FACET)
    @Indexed(name = PROJECT_IDENTIFIED_PTM_STRING_FACET)
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private Set<String> identifiedPTMStringsFacet;


//    @Field(PROJECT_FILE_NAMES)
    @Indexed( name = PROJECT_FILE_NAMES)
    private Set<String> projectFileNames;

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
        this.additionalAttributes = params.stream().map(CvParamProvider::getName).collect(Collectors.toSet());
        this.additionalAttributesFacet = params.stream().map(CvParamProvider::getName).collect(Collectors.toSet());
    }

    /**
     * This method add the to the current list of additional attributes the given list of Cvparams
     * @param params List of params
     */
    public void addAdditionalAttributesFromCvParams(List<CvParamProvider> params){
        if(additionalAttributes == null)
            additionalAttributes = new HashSet<>();
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

        this.quantificationMethods = params
                .stream()
                .map(x -> StringUtils.convertSentenceStyle(x.getName())).collect(Collectors.toSet());
        this.quantificationMethodsFacet = params
                .stream()
                .map(x -> StringUtils.convertSentenceStyle(x.getName()))
                .collect(Collectors.toSet());
    }

    /**
     * This method add the to the current list of quantification methods the given list of Cvparams
     * @param params List of params
     */
    public void addQuantificationMethodsFromCvParams(List<CvParamProvider> params){
        if(quantificationMethods == null){
            quantificationMethods = new HashSet<>();
            quantificationMethodsFacet = new HashSet<>();
        }
        this.quantificationMethods.addAll(params
                .stream()
                .map(x -> StringUtils.convertSentenceStyle(x.getName())).collect(Collectors.toList()));
        this.quantificationMethodsFacet.addAll(this.quantificationMethods);
    }

    /**
     * The implementation of the Project Tags is needed to set also the Facet values.
     * @param projectTags projectsTags
     */
    public void setProjectTags(List<String> projectTags) {
        this.projectTags = projectTags.stream().map(StringUtils::convertSentenceStyle).collect(Collectors.toSet());
        this.projectTagsFacets = this.projectTags;
    }

    /**
     * Set the keywords and the corresponding facets.
     * @param keywords Project Keywords
     */
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords.stream().map(StringUtils::convertSentenceStyle).collect(Collectors.toSet());
        this.keywordsFacets = this.keywords;
    }

    /**
     * Set the Head and PIs of the project
     * @param labPIs {@link ContactProvider} of Lab PIs
     */
    public void setLabPIFromContacts(List<ContactProvider> labPIs) {
        this.labPIs = labPIs.stream().map(x -> StringUtils.convertSentenceStyle(x.getName())).collect(Collectors.toSet());
        this.labPIsFacet = this.labPIs;
    }

    /**
     * Set affiliations from String List
     * @param affiliations PIs and Submitters affiliations
     */
    public void setAffiliationsFromContacts(List<ContactProvider> affiliations) {
        this.affiliations = affiliations.stream().map(ContactProvider::getAffiliation).collect(Collectors.toSet());
        this.affiliationsFacet = this.affiliations.stream().collect(Collectors.toSet());
    }


    /**
     * Set affiliations from String List
     * @param submitterNames submitter Names
     */
    public void setSubmittersFromNames(List<String> submitterNames) {
        this.submitters = new HashSet<>(submitterNames);
    }



    /**
     * Set instruments from and instruments list
     * @param instrumentCvParams
     */
    public void setInstrumentsFromCvParam(List<CvParamProvider> instrumentCvParams) {
        this.instruments = instrumentCvParams.stream().map(x-> StringUtils.convertSentenceStyle(x.getName())).collect(Collectors.toSet());
        this.instrumentsFacet = this.instruments.stream().collect(Collectors.toSet());
    }

    /**
     * Set Softwares information
     * @param softwares
     */
    public void setSoftwaresFromCvParam(List<CvParamProvider> softwares) {
        this.softwares = softwares.stream().map(x -> StringUtils.convertSentenceStyle(x.getName())).collect(Collectors.toSet());
        this.softwaresFacet = this.softwares;
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
        this.experimentalFactors = experimentalFactors.stream().map(Tuple::getKey).map(CvParamProvider::getName).collect(Collectors.toSet());
        experimentalFactorFacets = this.experimentalFactors;
        }

    /**
     * Set sample Atributes from the list of CVParams that are specify as a {@link Tuple}. The current
     * function takes the {@link CvTermReference} Organism, celltype and diseases
     * for the facet.
     *
     * @param sampleAttributes List of Experimental Factors.
     */
    public void setSampleAttributes(List<Tuple<CvParamProvider, List<CvParamProvider>>> sampleAttributes) {

        this.sampleAttributes = sampleAttributes.stream()
                .flatMap(x -> x.getValue()
                        .stream()
                        .map(ParamProvider::getName))
                .collect(Collectors.toSet());

        organisms_facet = new HashSet<>();
        organismPart_facet = new HashSet<>();
        diseases_facet  = new HashSet<>();

        organisms = new HashSet<>();
        organismPart = new HashSet<>();
        diseases  = new HashSet<>();

        sampleAttributes.forEach(x -> {
            if(StringUtils.isCvTerm(x.getKey().getAccession(), CvTermReference.EFO_ORGANISM)) {
                organisms.addAll(x.getValue()
                        .stream()
                        .map(value -> StringUtils.convertSentenceStyle(value.getName()))
                        .collect(Collectors.toList()));
                organisms_facet.addAll(organisms);
            } else if(StringUtils.isCvTerm(x.getKey().getAccession(), CvTermReference.EFO_ORGANISM_PART)) {
                organismPart.addAll(x.getValue()
                        .stream()
                        .map(value -> StringUtils.convertSentenceStyle(value.getName()))
                        .collect(Collectors.toList()));
                organismPart_facet.addAll(organismPart);
            }else if(StringUtils.isCvTerm(x.getKey().getAccession(), CvTermReference.EFO_DISEASE)) {
                diseases.addAll(x.getValue()
                        .stream()
                        .map(value -> StringUtils.convertSentenceStyle(value.getName()))
                        .collect(Collectors.toList()));
                diseases_facet.addAll(diseases);
            }
        });
    }

    /**
     * Set the PTMs
     * @param identifiedPTMS
     */
    public void setIdentifiedPTMStringsFromCvParam(List<CvParamProvider> identifiedPTMS) {
        this.identifiedPTMStrings = identifiedPTMS.stream().map(x -> StringUtils.convertSentenceStyle(x.getName())).collect(Collectors.toSet());
        this.identifiedPTMStringsFacet = this.identifiedPTMStrings.stream().map(StringUtils::convertSentenceStyle).collect(Collectors.toSet());
    }

    /** Return the accession for the Project **/
    @Override
    public Comparable getId() {
        return id;
    }

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
    public Collection<String> getAdditionalAttributesStrings() {
        return additionalAttributes;
    }

    /**
     * @return Get Project Description
     */
    @Override
    public String getDescription() {
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
    public Collection<? extends String> getReferences() {
        return (references != null)?references:Collections.EMPTY_LIST;
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

    @Override
    public Collection<? extends String> getSampleAttributes() {
        return (sampleAttributes != null)?sampleAttributes:Collections.EMPTY_LIST;
    }

    public Set<String> getProteinIdentifications() {
        return proteinIdentifications;
    }

    public void setProteinIdentifications(Set<String> proteinIdentifications) {
        this.proteinIdentifications = proteinIdentifications;
    }

    public void addProteinIdentifications(Set<String> proteinIdentifications){
        if(this.proteinIdentifications == null)
            this.proteinIdentifications = new HashSet<>();
        this.proteinIdentifications.addAll(proteinIdentifications);
    }

    public void addPeptideSequences(Set<String> peptideSequences){
        if(this.peptideSequences == null)
            this.peptideSequences = new HashSet<>();
        this.peptideSequences.addAll(peptideSequences);
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

    @Override
    public String toString() {
        return "PrideSolrProject{" +
                "accession='" + accession + '\'' +
                ", title='" + title + '\'' +
                ", additionalAttributes=" + additionalAttributes +
                ", additionalAttributesFacet=" + additionalAttributesFacet +
                ", projectDescription='" + projectDescription + '\'' +
                ", sampleProcessingProtocol='" + sampleProcessingProtocol + '\'' +
                ", dataProcessingProtocol='" + dataProcessingProtocol + '\'' +
                ", projectTags=" + projectTags +
                ", projectTagsFacets=" + projectTagsFacets +
                ", keywords=" + keywords +
                ", keywordsFacets=" + keywordsFacets +
                ", doi='" + doi + '\'' +
                ", otherOmicsLinks=" + otherOmicsLinks +
                ", submissionType='" + submissionType + '\'' +
                ", submissionDate=" + submissionDate +
                ", publicationDate=" + publicationDate +
                ", updatedDate=" + updatedDate +
                ", submitters=" + submitters +
                ", labPIs=" + labPIs +
                ", labPIsFacet=" + labPIsFacet +
                ", affiliations=" + affiliations +
                ", affiliationsFacet=" + affiliationsFacet +
                ", instruments=" + instruments +
                ", instrumentsFacet=" + instrumentsFacet +
                ", softwares=" + softwares +
                ", softwaresFacet=" + softwaresFacet +
                ", quantificationMethods=" + quantificationMethods +
                ", quantificationMethodsFacet=" + quantificationMethodsFacet +
                ", allCountries=" + allCountries +
                ", allCountriesFacet=" + allCountriesFacet +
                ", experimentalFactors=" + experimentalFactors +
                ", experimentalFactorFacets=" + experimentalFactorFacets +
                ", sampleAttributes=" + sampleAttributes +
                ", organisms_facet=" + organisms_facet +
                ", organismPart_facet=" + organismPart_facet +
                ", diseases_facet=" + diseases_facet +
                ", organisms=" + organisms +
                ", organismPart=" + organismPart +
                ", diseases=" + diseases +
                ", references=" + references +
                ", projectFileNames=" + projectFileNames +
                ", score=" + score +
                ", highlights=" + highlights +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrideSolrProject that = (PrideSolrProject) o;
        return Objects.equals(accession, that.accession) &&
                Objects.equals(title, that.title) &&
                Objects.equals(additionalAttributes, that.additionalAttributes) &&
                Objects.equals(additionalAttributesFacet, that.additionalAttributesFacet) &&
                Objects.equals(projectDescription, that.projectDescription) &&
                Objects.equals(sampleProcessingProtocol, that.sampleProcessingProtocol) &&
                Objects.equals(dataProcessingProtocol, that.dataProcessingProtocol) &&
                Objects.equals(projectTags, that.projectTags) &&
                Objects.equals(projectTagsFacets, that.projectTagsFacets) &&
                Objects.equals(keywords, that.keywords) &&
                Objects.equals(keywordsFacets, that.keywordsFacets) &&
                Objects.equals(doi, that.doi) &&
                Objects.equals(otherOmicsLinks, that.otherOmicsLinks) &&
                Objects.equals(submissionType, that.submissionType) &&
                equalsDatePartOnly(publicationDate, that.publicationDate) &&
                equalsDatePartOnly(submissionDate, that.submissionDate) &&
                equalsDatePartOnly(updatedDate, that.updatedDate) &&
                Objects.equals(submitters, that.submitters) &&
                Objects.equals(labPIs, that.labPIs) &&
                Objects.equals(labPIsFacet, that.labPIsFacet) &&
                Objects.equals(affiliations, that.affiliations) &&
                Objects.equals(affiliationsFacet, that.affiliationsFacet) &&
                Objects.equals(instruments, that.instruments) &&
                Objects.equals(instrumentsFacet, that.instrumentsFacet) &&
                Objects.equals(softwares, that.softwares) &&
                Objects.equals(softwaresFacet, that.softwaresFacet) &&
                Objects.equals(quantificationMethods, that.quantificationMethods) &&
                Objects.equals(quantificationMethodsFacet, that.quantificationMethodsFacet) &&
                Objects.equals(allCountries, that.allCountries) &&
                Objects.equals(allCountriesFacet, that.allCountriesFacet) &&
                Objects.equals(experimentalFactors, that.experimentalFactors) &&
                Objects.equals(experimentalFactorFacets, that.experimentalFactorFacets) &&
                Objects.equals(sampleAttributes, that.sampleAttributes) &&
                Objects.equals(organisms_facet, that.organisms_facet) &&
                Objects.equals(organismPart_facet, that.organismPart_facet) &&
                Objects.equals(diseases_facet, that.diseases_facet) &&
                Objects.equals(organisms, that.organisms) &&
                Objects.equals(organismPart, that.organismPart) &&
                Objects.equals(diseases, that.diseases) &&
                Objects.equals(references, that.references) &&
                Objects.equals(projectFileNames, that.projectFileNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accession, title, additionalAttributes, additionalAttributesFacet, projectDescription, sampleProcessingProtocol, dataProcessingProtocol, projectTags, projectTagsFacets, keywords, keywordsFacets, doi, otherOmicsLinks, submissionType, submissionDate, publicationDate, updatedDate, submitters, labPIs, labPIsFacet, affiliations, affiliationsFacet, instruments, instrumentsFacet, softwares, softwaresFacet, quantificationMethods, quantificationMethodsFacet, allCountries, allCountriesFacet, experimentalFactors, experimentalFactorFacets, sampleAttributes, organisms_facet, organismPart_facet, diseases_facet, organisms, organismPart, diseases, references, projectFileNames);
    }

    private static final DateFormat DATE_FORMAT_DATE_PART = new SimpleDateFormat("yyyy-mm-dd");

//    public static boolean equalsDate(Date a, Date b) {
//        return  ((a == b) || (a != null && b != null && a.getTime() == b.getTime()));
//    }

    public static boolean equalsDatePartOnly(Date a, Date b) {
        String aStr = DATE_FORMAT_DATE_PART.format(a);
        String bStr = DATE_FORMAT_DATE_PART.format(b);
        return  ((a == b) || (a != null && b != null && aStr.equals(bStr)));
    }
}
