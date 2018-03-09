package uk.ac.ebi.pride.solr.indexes.pride.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetAndHighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.pride.solr.indexes.pride.model.SolrProject;

import java.util.stream.Stream;

/**
 * Solr Crud Repository is helping the implementation to handle the {@link SolrProject} with the basics functions suchs as
 * insert/update/delete.
 *
 * @author Yasset Perez-Riverol
 * @version $Id$
 */

@Repository
public interface SolrProjectRepository extends SolrCrudRepository<SolrProject, String>{

    /**
     * Fin the project but corresponding accession.
     * @param accession accesion of the project
     * @return Solr Project
     */
    public SolrProject findByAccession(String accession);

    @Query("select p from SolrProject p")
    public Stream<SolrProject> findAllByCustomQueryAndStream();


    @Highlight(snipplets = 3)
    FacetAndHighlightPage<SolrProject> findByDescriptionStartingWith(String description, Pageable page);

}
