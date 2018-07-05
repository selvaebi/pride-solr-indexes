package uk.ac.ebi.pride.solr.indexes.pride.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import uk.ac.ebi.pride.archive.dataprovider.utils.Tuple;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;
import uk.ac.ebi.pride.solr.indexes.pride.repository.SolrProjectRepository;
import uk.ac.ebi.pride.solr.indexes.pride.utils.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This service allows to make updates and save of the Pride Projects and should be used instead of pure repository.
 *
 */
@Service
@Slf4j
public class SolrProjectService {

    final SolrProjectRepository repository;

    @Autowired
    public SolrProjectService(SolrProjectRepository repository) {
        this.repository = repository;
    }


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
            log.info("A new project has been saved in Solr index -- " + project.getAccession());
        }else
            log.info("A project with same accession has been found in the Solr index, you MUST use update -- " + project.getAccession());
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
         projects.forEach(this::save);
    }

    public Iterator<PrideSolrProject> findAllUsingCursor() {
        return repository.findAllUsingCursor();
    }

    public PrideSolrProject findByAccession(String accession) {
        return repository.findByAccession(accession);
    }

    /**
     * The keywords are all the words we would like to find datasets for them into the Solr index. Filter Query should have the following structure:
     * field1==queryValue, field1==QueryValue
     * @param keywords List of keywords to be found in the search
     * @param filterQuery filter Query in the following format ield1:queryValue, field1:QueryValue
     * @param pageRequest Page Request.
     * @return List of datasets Pagination
     */
    public HighlightPage<PrideSolrProject> findByKeyword(List<String> keywords, String filterQuery, PageRequest pageRequest, String dateGap) {

        MultiValueMap<String, String> filters = StringUtils.parseFilterParameters(filterQuery);
        return repository.findByKeyword(keywords, filters, pageRequest, dateGap);
    }

    public Page<PrideSolrProject> findAllIgnoreCase(PageRequest pageRequest) {
        return repository.findAllIgnoreCase(pageRequest);

    }

    public FacetPage<PrideSolrProject> findAllFacetIgnoreCase(Pageable pageRequest){
        return repository.findAllFacetIgnoreCase(pageRequest);
    }

    public FacetPage<PrideSolrProject> findFacetByKeyword(List<String> keywords, String filterQuery, PageRequest pageRequest, PageRequest facetPage, String gap){
        MultiValueMap<String, String> filters = StringUtils.parseFilterParameters(filterQuery);
        return repository.findFacetByKeyword(keywords, filters, pageRequest, facetPage, gap);
    }

    /**
     * This method retrieve a datasets that are similar to an specific dataset. The method remove the requested dataset
     * from the list.
     * @param accession Project Accession
     * @return List of Similar projects.
     */
    public List<PrideSolrProject> findSimilarProjects(String accession, Integer pageSize, Integer page ){
        Map<String, Double> ids = repository.findMoreLikeThisIds(accession, pageSize, page);
        List<PrideSolrProject> similarDatasets = new ArrayList<>();
        Iterable<PrideSolrProject> itDatasets = repository.findAllById(ids.entrySet().stream().map(x -> x.getKey()).collect(Collectors.toSet()));
        if(itDatasets != null){
            itDatasets.forEach(x -> {
                if(!x.getAccession().equalsIgnoreCase(accession)){
                    x.setScore(new Float(ids.get(x.getId())));
                    similarDatasets.add(x);
                }
            });
        }
        return similarDatasets;
    }

}
