package uk.ac.ebi.pride.solr.indexes.pride.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.query.result.FacetAndHighlightPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.DefaultCvParam;
import uk.ac.ebi.pride.archive.dataprovider.user.ContactProvider;
import uk.ac.ebi.pride.archive.dataprovider.user.DefaultContact;
import uk.ac.ebi.pride.archive.dataprovider.utils.TitleConstants;
import uk.ac.ebi.pride.archive.repo.repos.project.*;
import uk.ac.ebi.pride.archive.repo.repos.project.ProjectRepository;
import uk.ac.ebi.pride.solr.indexes.pride.config.ArchiveOracleConfig;
import uk.ac.ebi.pride.solr.indexes.pride.config.SolrLocalhostTestConfiguration;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectField;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;
import uk.ac.ebi.pride.solr.indexes.pride.services.SolrProjectService;
import uk.ac.ebi.pride.solr.indexes.pride.utils.RequiresSolrServer;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ypriverol
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SolrLocalhostTestConfiguration.class, ArchiveOracleConfig.class})
public class OracleSolrRepositoryTests {

    public static RequiresSolrServer requiresRunningServer = RequiresSolrServer.onLocalhost();

    @Autowired
    SolrProjectService projectService;

    @Autowired
    ProjectRepository oracleRepository;

    @PostConstruct
    public void setTest(){
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
            solrProject.setTitle(x.getTitle());
            solrProject.setProjectDescription(x.getProjectDescription());
            solrProject.setDataProcessingProtocol(x.getDataProcessingProtocol());
            solrProject.setSampleProcessingProtocol(x.getSampleProcessingProtocol());

            //Capture keywords
            solrProject.setKeywords(Arrays.asList(x.getKeywords().split(",")));
            solrProject.setProjectTags(x.getProjectTags().stream().map(ProjectTag::getTag).collect(Collectors.toList()));

            //Capture datasets
            solrProject.setSubmissionDate(x.getSubmissionDate());
            solrProject.setPublicationDate(x.getPublicationDate());
            solrProject.setUpdatedDate(x.getUpdateDate());

            // Affiliations
            List<ContactProvider> labHead = new ArrayList<>();
            x.getLabHeads().forEach(contactX -> {
                labHead.add(new DefaultContact(TitleConstants.fromString(contactX.getTitle().getTitle()), contactX.getFirstName(), contactX.getLastName(), contactX.getId().toString(), contactX.getAffiliation(),contactX.getEmail(), "US", "")); }
            );
            solrProject.setLabPIFromContacts(labHead);

            ContactProvider submitter = new DefaultContact(TitleConstants.fromString(x.getSubmitter().getTitle().getTitle()), x.getSubmitter().getFirstName(), x.getSubmitter().getLastName(), x.getSubmitter().getId().toString(), x.getSubmitter().getAffiliation(),x.getSubmitter().getEmail(), "US", "");
            solrProject.setSubmittersFromContacts(submitter);

            List<ContactProvider> contacts= labHead;
            contacts.add(submitter);
            solrProject.setAffiliationsFromContacts(contacts);

            // Get Instruments
            List<CvParamProvider> instruments =  new ArrayList<>();
            x.getInstruments().forEach(instrumet -> {
                instruments.add(new DefaultCvParam(instrumet.getCvLabel(), instrumet.getAccession(), instrumet.getName(), instrumet.getValue()));
            });
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

            Collection<ProjectSampleCvParam> samples = x.getSamples();

            solrProject.setSampleAttributes(samples.stream().map(xSample -> new DefaultCvParam(xSample.getAccession(), xSample.getName())).collect(Collectors.toList()));
            projects.add(solrProject);
        });
        projectService.saveAll(projects);
    }

    /** Finds all entries using a single request. */
    @Test
    public void findAll() {
        projectService.findAll().forEach(x -> {
            System.out.println("Accession: " + x.getAccession() + " -- Title: " + x.getTitle());
        });
    }

    /** Finds all entries using a single request by Cursor */
    @Test
    public void findCursorAll() {
        projectService.findAllUsingCursor().forEachRemaining(x -> System.out.println("Accession: " + x.getAccession() + " -- Title: " + x.getTitle()));
    }

    /** Find the dataset for specific accession **/
    @Test
    public void findByAccession(){
        PrideSolrProject x = projectService.findByAccession("PXD000001");
        System.out.println("Accession: " + x.getAccession() + " -- Title: " + x.getTitle());
    }

    @Test
    public void findProjectsByKey(){
        // Search for two keywords, No filter
        HighlightPage<PrideSolrProject> page = projectService.findByKeyword(Arrays.asList("PRD", "PXD"), "", new PageRequest(0, 10));
        page.forEach(x -> {
            System.out.println(x);
        });

        // Search for two keywords, filter date
        page = projectService.findByKeyword(Arrays.asList("PRD", "PXD"), "publication_date:2012-12-31", new PageRequest(0, 10));
        page.forEach(x -> {
            System.out.println(x);
        });
    }

    @Test
    public void findProjectsByKeyFacet(){

        Page<PrideSolrProject> page = projectService.findAllIgnoreCase(new PageRequest(0, 10));

        // Print all the projects search
        page.forEach( x-> {
            System.out.println(x.toString());
        });
    }

    @Test
    public void finadAllAndaFacet(){
        Page<PrideSolrProject> projects = projectService.findAllIgnoreCase(new PageRequest(1, 10));
        Assert.assertEquals(((FacetAndHighlightPage) projects).getFacetResultPage(PrideProjectField.PROJECT_PUBLICATION_DATE).getContent().size(), 1);
    }
}
