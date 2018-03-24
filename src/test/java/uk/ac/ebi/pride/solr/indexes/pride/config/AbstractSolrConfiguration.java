package uk.ac.ebi.pride.solr.indexes.pride.config;


import org.springframework.data.repository.CrudRepository;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;
import uk.ac.ebi.pride.solr.indexes.pride.repository.SolrProjectRepository;

import java.util.stream.IntStream;

/**
 * Some abstract classes to reuse in the configuration files.
 *
 * @author ypriverol
 * @version $Id$
 *
 */
public class AbstractSolrConfiguration {

    /**
     * Insert dummy data into the collection.
     *
     * @param repository to insert the data
     */
    protected void doInitTestData(SolrProjectRepository repository) {

        IntStream.range(0, 100)
                .forEach(index -> {
                    PrideSolrProject p = new PrideSolrProject();
                    p.setAccession("p-" + index);
                    p.setTitle("name-" + index);
                    repository.save(p);
                });
    }

    /**
     * This function helps to clean all.
     * @param repository
     */
    protected void deleteAllData(CrudRepository<SolrProjectRepository, String> repository){
        repository.deleteAll();
    }
}
