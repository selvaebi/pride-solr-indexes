package uk.ac.ebi.pride.solr.indexes.config;


import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.solr.core.SolrTemplate;
import uk.ac.ebi.pride.solr.indexes.services.SolrProjectService;
import uk.ac.ebi.pride.solr.indexes.utils.PrideProjectReader;

import java.io.File;
import java.io.IOException;

/**
 * Some abstract classes to reuse in the configuration files. It can be used to read examples from px files and
 * submit them into the Solr index.
 *
 * @author ypriverol
 * @version $Id$
 *
 */
public class AbstractSolrConfiguration {

    @Value("${solr.Home}")
    File solrConfigHome = null;

    String collectionName = null;

    @Autowired
    SolrTemplate solrTemplate;

    /**
     * Insert dummy data into the collection.
     *
     * @param repository to insert the data
     */
    protected void doInitTestData(SolrProjectService repository, String ... filePaths) {
        Lists.newArrayList(filePaths).stream().forEach(x -> repository.save(PrideProjectReader.read(new File(x))));
    }

    /**
     * This function helps to clean all.
     * @param repository
     */
    protected void deleteAllData(CrudRepository<SolrProjectService, String> repository){
        repository.deleteAll();
    }


    protected boolean deleteExistingConfigSet(){

        SolrClient solrClient = solrTemplate.getSolrClient();

        try {
            if(solrClient.ping().getStatus() != -1)
                return true;
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
