package uk.ac.ebi.pride.solr.indexes.pride.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;

import java.util.Collection;
import java.util.List;


/**
 * Solr Crud Repository is helping the implementation to handle the {@link PrideSolrProject} with the basics functions suchs as
 * insert/update/delete.
 *
 * @author Yasset Perez-Riverol
 * @version $Id$
 */

@Repository
public interface SolrProjectRepository extends SolrCrudRepository<PrideSolrProject, String> {

    //Derived Query will be "q=popularity:<popularity>&start=<page.number>&rows=<page.size>"
    Page<PrideSolrProject> findByPopularity(Integer popularity, Pageable page);

    //Will execute count prior to determine total number of elements
    //Derived Query will be "q=name:<name>*&start=0&rows=<result of count query for q=name:<name>>"
    List<PrideSolrProject> findByNameStartingWith(String name);

    //Derived Query will be "q=inStock:true&start=<page.number>&rows=<page.size>"
    Page<PrideSolrProject> findByAvailableTrue(Pageable page);

    //Derived Query will be "q=inStock:<inStock>&start=<page.number>&rows=<page.size>"
    @Query("inStock:?0")
    Page<PrideSolrProject> findByAvailableUsingAnnotatedQuery(boolean inStock, Pageable page);

    //Will execute count prior to determine total number of elements
    //Derived Query will be "q=inStock:false&start=0&rows=<result of count query for q=inStock:false>&sort=name desc"
    List<PrideSolrProject> findByAvailableFalseOrderByNameDesc();

    //Execute faceted search
    //Query will be "q=name:<name>&facet=true&facet.field=cat&facet.limit=20&start=<page.number>&rows=<page.size>"
    @Query(value = "name:?0")
    @Facet(fields = { "cat" }, limit=20)
    FacetPage<PrideSolrProject> findByNameAndFacetOnCategory(String name, Pageable page);


    //Highlighting results
    //Query will be "q=name:(<name...>)&hl=true&hl.fl=*"
    @Highlight
    HighlightPage<PrideSolrProject> findByNameIn(Collection<String> name, Pageable page);

}
