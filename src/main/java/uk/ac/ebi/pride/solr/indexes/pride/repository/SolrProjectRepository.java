package uk.ac.ebi.pride.solr.indexes.pride.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.solr.core.query.result.Cursor;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;


/**
 * Solr Crud Repository is helping the implementation to handle the {@link PrideSolrProject} with the basics functions suchs as
 * insert/update/delete.
 *
 * @author Yasset Perez-Riverol
 * @version $Id$
 */

public interface SolrProjectRepository extends CrudRepository<PrideSolrProject, String> {

    /**
     * Use a {@link Cursor} to scroll through documents in index. <br />
     * <strong>NOTE:</strong> Requires at least Solr 4.7.
     *
     * @return
     */
//    Cursor<PrideSolrProject> findAllUsingCursor();
}
