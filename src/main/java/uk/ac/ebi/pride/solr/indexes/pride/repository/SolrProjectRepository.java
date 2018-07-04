package uk.ac.ebi.pride.solr.indexes.pride.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.solr.repository.Query;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectField;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;

import java.util.List;


/**
 * Solr Crud Repository is helping the implementation to handle the {@link PrideSolrProject} with the basics functions suchs as
 * insert/update/delete.
 *
 * @author Yasset Perez-Riverol
 * @version $Id$
 */

public interface SolrProjectRepository extends SolrProjectRepositoryCustom, CrudRepository<PrideSolrProject, String> {

    /**
     * Return a {@link PrideSolrProject} for a given Accession
     * @param accession PRoject accession
     * @return PrideSolrProject
     */
    @Query(PrideProjectField.ACCESSION + ":?0")
    PrideSolrProject findByAccession(String accession);

    /**
     * This function returns all the {@link PrideSolrProject} in the registry including the following facets:
     *  - project publication date
     *  - project submission date
     *  - project updated date
     *  - project tags
     *  - project keywords
     *  - project instruments
     *  - project affiliations
     *  - project experimental factors
     *  - project organisms
     *  - project organism_parts
     *  - project diseases
     * @param pageable {@link Pageable}
     * @return
     */
    @Query(value = "*:*")
    Page<PrideSolrProject> findAllIgnoreCase(Pageable pageable);






}
