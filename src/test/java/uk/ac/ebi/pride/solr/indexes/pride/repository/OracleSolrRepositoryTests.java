package uk.ac.ebi.pride.solr.indexes.pride.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.query.result.FacetAndHighlightPage;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.DefaultCvParam;
import uk.ac.ebi.pride.archive.dataprovider.user.ContactProvider;
import uk.ac.ebi.pride.archive.dataprovider.user.DefaultContact;
import uk.ac.ebi.pride.archive.dataprovider.utils.TitleConstants;

import uk.ac.ebi.pride.archive.repo.repos.project.ProjectRepository;
import uk.ac.ebi.pride.archive.repo.repos.project.ProjectSoftwareCvParam;
import uk.ac.ebi.pride.archive.repo.repos.project.ProjectTag;
import uk.ac.ebi.pride.archive.repo.repos.project.Reference;
import uk.ac.ebi.pride.solr.indexes.pride.config.ArchiveOracleConfig;
import uk.ac.ebi.pride.solr.indexes.pride.config.SolrLocalhostTestConfiguration;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectField;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;
import uk.ac.ebi.pride.solr.indexes.pride.services.SolrProjectService;
import uk.ac.ebi.pride.solr.indexes.pride.utils.PrideSolrConstants;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ypriverol
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SolrLocalhostTestConfiguration.class, ArchiveOracleConfig.class})
public class OracleSolrRepositoryTests {

    @Autowired
    SolrProjectService projectService;


    @Autowired
    ProjectRepository oracleRepository;

    @PostConstruct
    public void setTest() throws IOException {

        if(projectService.findAll().iterator().hasNext()){
            //projectService.deleteAll();
            insertDatasetsFromOracle();
        }
    }

    public void insertDatasetsFromOracle(){
        List<PrideSolrProject> projects = new ArrayList<>();
        oracleRepository.findAll().forEach(x -> {
            PrideSolrProject solrProject = new PrideSolrProject();
            solrProject.setAccession(x.getAccession());
            solrProject.setId(x.getAccession());
            solrProject.setTitle(x.getTitle());
            solrProject.setProjectDescription(x.getProjectDescription());
            solrProject.setDataProcessingProtocol(x.getDataProcessingProtocol());
            solrProject.setSampleProcessingProtocol(x.getSampleProcessingProtocol());

            //Capture keywords
            String keywords="";
            if(x.getKeywords()!=null){
                keywords = x.getKeywords().stream().collect(Collectors.joining());
            }
            solrProject.setKeywords(Arrays.asList(keywords.split(",")));
            solrProject.setProjectTags(x.getProjectTags().stream().map(ProjectTag::getTag).collect(Collectors.toList()));

            //Capture datasets
            solrProject.setSubmissionDate(x.getSubmissionDate());
            solrProject.setPublicationDate(x.getPublicationDate());
            solrProject.setUpdatedDate(x.getUpdateDate());

            // Affiliations
            List<ContactProvider> labHead = new ArrayList<>();
            x.getLabHeads().forEach(contactX -> labHead.add(new DefaultContact(TitleConstants.fromString(contactX.getTitle().getTitle()), contactX.getFirstName(), contactX.getLastName(), contactX.getId().toString(), contactX.getAffiliation(),contactX.getEmail(), "US", ""))
            );
            solrProject.setLabPIFromContacts(labHead);

            ContactProvider submitter = new DefaultContact(TitleConstants.fromString(x.getSubmitter().getTitle().getTitle()), x.getSubmitter().getFirstName(), x.getSubmitter().getLastName(), x.getSubmitter().getId().toString(), x.getSubmitter().getAffiliation(),x.getSubmitter().getEmail(), "US", "");
            solrProject.setSubmittersFromContacts(submitter);

            labHead.add(submitter);
            solrProject.setAffiliationsFromContacts(labHead);

            // Get Instruments
            List<CvParamProvider> instruments =  new ArrayList<>();
            x.getInstruments().forEach(instrumet -> instruments.add(new DefaultCvParam(instrumet.getCvLabel(), instrumet.getAccession(), instrumet.getName(), instrumet.getValue())));
            solrProject.setInstrumentsFromCvParam(instruments);

            // References
            List<String> references = x.getReferences().stream().map(Reference::getReferenceLine).collect(Collectors.toList());
            solrProject.setReferences(new HashSet<>(references));

            //Modifications
            solrProject.setIdentifiedPTMStringsFromCvParam(x.getPtms().stream().map(xPTM -> new DefaultCvParam(xPTM.getCvLabel(), xPTM.getAccession(), xPTM.getName(), xPTM.getValue())).collect(Collectors.toList()));

            //Get software information
            Collection<ProjectSoftwareCvParam> softwaresOld = x.getSoftware();
            solrProject.setSoftwaresFromCvParam(softwaresOld.stream().map(xSoft -> new DefaultCvParam(xSoft.getAccession(), xSoft.getName())).collect(Collectors.toList()));

            //Add Additional Attributes
            solrProject.addAdditionalAttributesFromCvParams(x.getExperimentTypes().stream().map(xType -> new DefaultCvParam(xType.getAccession(), xType.getName())).collect(Collectors.toList()));
            solrProject.addQuantificationMethodsFromCvParams(x.getQuantificationMethods().stream().map(xQuant -> new DefaultCvParam(xQuant.getAccession(), xQuant.getName())).collect(Collectors.toList()));

            projects.add(solrProject);
        });
        projectService.saveAll(projects);
    }

    /**
     *  This Test contains all the test in a big test method.
     */
    @Test
    public void findAll() {

        // Find all Accessions
        projectService.findAll().forEach(x -> System.out.println("Accession: " + x.getAccession() + " -- Title: " + x.getTitle()));


        // Fill all cursor
        projectService.findAllUsingCursor().forEachRemaining(x -> System.out.println("Accession: " + x.getAccession() + " -- Title: " + x.getTitle()));

        // Find by Accession
        PrideSolrProject project = projectService.findByAccession("PXD000001");
        Assert.assertTrue("Accession has been found -- ", project.getAccession().equalsIgnoreCase("PXD000001"));
        System.out.println("Accession: " + project.getAccession() + " -- Title: " + project.getTitle());


        /*** Find by keyword **/
        HighlightPage<PrideSolrProject> page = projectService
                .findByKeyword(Arrays.asList("PRD", "PXD"), "", PageRequest.of(0, 10), PrideSolrConstants.AllowedDateGapConstants.UNKONWN.value);

        page.forEach(System.out::println);

        // Search for two keywords, filter date
        page = projectService
                .findByKeyword(Arrays.asList("PRD", "PXD"), "publication_date==2012-12-31",  PageRequest.of(0, 10), PrideSolrConstants.AllowedDateGapConstants.UNKONWN.value);
        page.forEach(System.out::println);

        // Search for two keywords, filter date
        page = projectService
                .findByKeyword(Collections.singletonList("*:*"), "publication_date==2012-12-31",  PageRequest.of(0, 10), PrideSolrConstants.AllowedDateGapConstants.UNKONWN.value);
        page.forEach(System.out::println);


        FacetPage<PrideSolrProject> pageFacet = projectService
                .findFacetByKeyword(Arrays.asList("PRD", "PXD"), "", PageRequest.of(0, 10), PageRequest.of(0, 10),PrideSolrConstants.AllowedDateGapConstants.YEARLY.value);

        pageFacet.forEach(System.out::println);

        // Search for two keywords, filter date
        pageFacet = projectService
                .findFacetByKeyword(Arrays.asList("PRD", "PXD"), "publication_date==2012-12-31", PageRequest.of(0, 10), PageRequest.of(0, 10),PrideSolrConstants.AllowedDateGapConstants.YEARLY.value);

        pageFacet.forEach(System.out::println);

        // Search for two keywords, filter date
        pageFacet = projectService.findFacetByKeyword(Collections.singletonList("*:*"), "publication_date==2012-12-31", PageRequest.of(0, 10), PageRequest.of(0, 10), PrideSolrConstants.AllowedDateGapConstants.YEARLY.value);
        pageFacet.forEach(System.out::println);


        Page<PrideSolrProject> page2 = projectService.findAllIgnoreCase( PageRequest.of(0, 10));

        // Print all the projects search
        page2.forEach( x-> System.out.println(x.toString()));

        Page<PrideSolrProject> projects = projectService.findAllIgnoreCase(PageRequest.of(1, 10));
        Assert.assertEquals(((FacetAndHighlightPage) projects).getFacetResultPage(PrideProjectField.PROJECT_PUBLICATION_DATE).getContent().size(), 1);


    }



}
