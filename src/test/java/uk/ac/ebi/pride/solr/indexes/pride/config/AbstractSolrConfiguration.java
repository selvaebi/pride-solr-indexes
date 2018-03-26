package uk.ac.ebi.pride.solr.indexes.pride.config;


import org.assertj.core.util.Lists;
import org.springframework.data.repository.CrudRepository;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;
import uk.ac.ebi.pride.solr.indexes.pride.repository.SolrProjectRepository;
import uk.ac.ebi.pride.solr.indexes.pride.utils.PrideProjectReader;

import java.io.File;
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
    protected void doInitTestData(SolrProjectRepository repository, String ... filePaths) {
        Lists.newArrayList(filePaths).stream().forEach(x -> {
            PrideSolrProject p = PrideProjectReader.read(new File(x));
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
