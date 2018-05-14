package uk.ac.ebi.pride.solr.indexes.pride.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;
import uk.ac.ebi.pride.solr.indexes.pride.repository.SolrProjectRepository;
import uk.ac.ebi.pride.solr.indexes.pride.utils.StringUtils;

import java.util.*;

/**
 * This service allows to make updates and save of the Pride Projects and should be used instead of pure repository.
 *
 */
@Service
public class SolrProjectService {

    @Autowired
    SolrProjectRepository repository;

    /** Logger use to query and filter the data **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SolrProjectService.class);


    /**
     * This method check first that not project with same accession is in the collection and then insert it the new
     * project. If the project is in Solr then service do not save it. For updating the project please check the following
     * method
     * @param project
     * @return
     */
    public PrideSolrProject save(PrideSolrProject project){
        PrideSolrProject prideSolrProject = repository.findByAccession(project.getAccession());
        if(prideSolrProject == null){
            repository.save(project);
            LOGGER.info("A new project has been saved in Solr index -- " + project.getAccession());
        }else
            LOGGER.info("A project with same accession has been found in the Solr index, you MUST use update -- " + project.getAccession());
        return project;
    }

    /**
     * This method check first that not project with same accession is in the collection and then insert it the new
     * project. If the project is in Solr then service do not save it. For updating the project please check the following
     * method
     * @param project
     * @return
     */
    public PrideSolrProject update(PrideSolrProject project){
        PrideSolrProject prideSolrProject = repository.findByAccession(project.getAccession());
        if(prideSolrProject == null)
            repository.save(project);
        return project;
    }

    /**
     * Find all the Projects in the Repository
     * @return
     */
    public Iterable<PrideSolrProject> findAll() {
        return repository.findAll();
    }

    /**
     * Delete all the Projects in the Repository.
     */
    public void deleteAll() {
        repository.deleteAll();
    }

    /**
     * Parallel save of the projects in the collection. This needs to be tested
     * @param projects
     */
    public void saveAll(List<PrideSolrProject> projects) {
         projects.stream().forEach(this::save);
    }

    public Iterator<PrideSolrProject> findAllUsingCursor() {
        return repository.findAllUsingCursor();
    }

    public PrideSolrProject findByAccession(String accession) {
        return repository.findByAccession(accession);
    }

    /**
     * The keywords are all the words we would like to find datasets for them into the Solr index. Filter Query should have the following structure:
     * field1:queryValue, field1:QueryValue
     * @param keywords List of keywords to be found in the search
     * @param filterQuery filter Query in the following format ield1:queryValue, field1:QueryValue
     * @param pageRequest Page Request.
     * @return List of datasets Pagination
     */
    public HighlightPage<PrideSolrProject> findByKeyword(List<String> keywords, String filterQuery, PageRequest pageRequest) {

        MultiValueMap<String, String> filters = StringUtils.parseFilterParameters(filterQuery);
        return repository.findByKeyword(keywords, filters, pageRequest);
    }

    public Page<PrideSolrProject> findAllIgnoreCase(PageRequest pageRequest) {
        return repository.findAllIgnoreCase(pageRequest);

    }
}
