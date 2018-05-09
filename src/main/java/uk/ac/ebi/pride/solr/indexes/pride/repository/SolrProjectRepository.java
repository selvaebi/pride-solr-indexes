package uk.ac.ebi.pride.solr.indexes.pride.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.solr.core.query.result.FacetAndHighlightPage;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectField;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrDataset;


/**
 * Solr Crud Repository is helping the implementation to handle the {@link PrideSolrDataset} with the basics functions suchs as
 * insert/update/delete.
 *
 * @author Yasset Perez-Riverol
 * @version $Id$
 */

public interface SolrProjectRepository extends SolrProjectRepositoryCustom, CrudRepository<PrideSolrDataset, String> {

    /**
     * Return a {@link PrideSolrDataset} for a given Accession
     * @param accession PRoject accession
     * @return PrideSolrDataset
     */
    @Query(PrideProjectField.ACCESSION + ":?0")
    PrideSolrDataset findByAccession(String accession);

    /**
     * This function returns all the {@link PrideSolrDataset} in the registry including the following facets:
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
    Page<PrideSolrDataset> findAllIgnoreCase(Pageable pageable);

}
