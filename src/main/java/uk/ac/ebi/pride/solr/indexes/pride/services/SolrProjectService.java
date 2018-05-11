package uk.ac.ebi.pride.solr.indexes.pride.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;
import uk.ac.ebi.pride.solr.indexes.pride.repository.SolrProjectRepository;

import java.util.Iterator;
import java.util.List;

/**
 * This service allows to make updates and save of the Pride Projects and should be used instead of pure repository.
 *
 */
@Service
public class SolrProjectService {

    @Autowired
    SolrProjectRepository repository;

    /**
     * This method check first that not project with same accession is in the collection and then insert it the new
     * project. If the project is in Solr then service do not save it. For updating the project please check the following
     * method
     * @param project
     * @return
     */
    public PrideSolrProject save(PrideSolrProject project){
        PrideSolrProject prideSolrProject = repository.findByAccession(project.getAccession());
        if(prideSolrProject == null)
            repository.save(project);
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

    public HighlightPage<PrideSolrProject> findByKeyword(List<String> keywords, PageRequest pageRequest) {
        return repository.findByKeyword(keywords, pageRequest);
    }

    public Page<PrideSolrProject> findAllIgnoreCase(PageRequest pageRequest) {
        return repository.findAllIgnoreCase(pageRequest);

    }
}
