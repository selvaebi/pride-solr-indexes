package uk.ac.ebi.pride.solr.indexes.pride.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Query;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectField;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;


/**
 * Solr Crud Repository is helping the implementation to handle the {@link PrideSolrProject} with the basics functions suchs as
 * insert/update/delete.
 *
 * @author Yasset Perez-Riverol
 * @version $Id$
 */

public interface SolrProjectRepository extends SolrProjectRepositoryCustom, CrudRepository<PrideSolrProject, String> {

    /** Find Projects by Accession **/
    @Query(PrideProjectField.ACCESSION + ":?0")
    PrideSolrProject findByAccession(String accession);

    @Query(value = "*:*")
    @Facet(fields = {PrideProjectField.PROJECT_PUBLICATION_DATE, PrideProjectField.PROJECT_SUBMISSION_DATE, PrideProjectField.PROJECT_UPDATED_DATE, PrideProjectField.PROJECT_TAGS_FACET, PrideProjectField.PROJECT_KEYWORDS_FACET})
    FacetPage<PrideSolrProject> findAllWithFacetIgnoreCase(Pageable pageable);

}
