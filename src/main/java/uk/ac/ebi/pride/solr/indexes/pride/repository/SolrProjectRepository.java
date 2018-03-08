package uk.ac.ebi.pride.solr.indexes.pride.repository;

import org.springframework.data.solr.repository.SolrCrudRepository;
import uk.ac.ebi.pride.solr.indexes.pride.model.SolrProject;

/**
 * Solr Crud Repository in
 *
 * @author Yasset Perez-Riverol
 * @version $Id$
 */
public interface SolrProjectRepository extends SolrCrudRepository<SolrProject, String>{



}
