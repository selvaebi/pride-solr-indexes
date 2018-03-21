package uk.ac.ebi.pride.solr.indexes.pride.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.archive.dataprovider.project.ProjectProvider;
import uk.ac.ebi.pride.archive.services.project.PrideProjectReaderService;
import uk.ac.ebi.pride.archive.services.project.PrideProjectWriterService;
import uk.ac.ebi.pride.solr.indexes.pride.repository.SolrProjectRepository;

import java.util.stream.Stream;

/**
 * The project index service provides the functions to insert/delete/update/search for projects in the Solr Index. It implements
 * to main interfaces {@link PrideProjectWriterService} and {@link PrideProjectReaderService}.
 *
 * @author Yasset Perez-Riverol
 * @version $Id$
 */

@Service
public class PrideSolrProjectService implements PrideProjectReaderService, PrideProjectWriterService{

    @Autowired
    SolrProjectRepository repository;

    @Override
    public ProjectProvider read(String accession) {
        return null;
    }

    @Override
    public ProjectProvider save(ProjectProvider projectProvider) {
        return null;
    }

    @Override
    public Stream<ProjectProvider> readAll(int pageStart, int size) {
        return null;
    }

    @Override
    public ProjectProvider update(String accession, ProjectProvider newProjectEntry) {
        return null;
    }

    @Override
    public ProjectProvider delete(String accession) {
        return null;
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
