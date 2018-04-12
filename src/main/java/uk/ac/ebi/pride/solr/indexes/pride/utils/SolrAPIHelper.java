package uk.ac.ebi.pride.solr.indexes.pride.utils;

import lombok.Data;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 This class is a API helper for Solr API to enable delete/create/update delete of Collections.

 @author ypriverol
 @version $Id$
 **/

public class SolrAPIHelper {

    /** RestTemplate to query APIs of Solr **/
    /** config http URL **/
    protected SolrConfig config;

    /** Private instance **/
    private static SolrAPIHelper instance;

    private static final Logger LOGGER = LoggerFactory.getLogger(SolrAPIHelper.class);

    /**
     * Default constructor for the SolrAPIHelper
     * @param hostURL host url of Solr 
     */
    private SolrAPIHelper(String hostURL){
        if(instance == null){
            config = new SolrConfig(hostURL);
        }
    }

    /**
     * Public constructor
     * @param hostURL Solr host URL 
     * @return Solr Helper. 
     */
    public static SolrAPIHelper getInstance(String hostURL){
        instance = new SolrAPIHelper(hostURL); 
        return instance; 
    }

    /**
     * Delete an specific collection in Solr and all the data included.
     * @param collection Name of the collection
     * @return True if the collection has been deleted.
     * @throws IOException
     */
    public boolean deleteCollection(String collection) throws IOException {

        String hostQuery = String.format("%s/solr/admin/collections?action=DELETE&name=%s", config.getHostURL(), collection);
        LOGGER.debug(hostQuery);

        CloseableHttpClient client = HttpClientBuilder.create().build(); 
        CloseableHttpResponse response = client.execute(new HttpPost(hostQuery));

        LOGGER.warn(response.toString());
        return response.getStatusLine() != null && response.getStatusLine().getStatusCode() == 200;
    }

    /**
     * Create an specific collection in Solr using the API with different shards and replicates.
     * @param collection Name of the collection
     * @param numShards Number of Shards
     * @param replicationFactor Number of Replicates
     * @param maxShardsPerNode Max of Shards per Node.
     * @return True if the collection has been created.
     * @throws IOException
     */
    public boolean createCollection(String collection, int numShards, int replicationFactor, int maxShardsPerNode) throws IOException {

        String hostQuery = String.format("%s/solr/admin/collections?action=CREATE&name=%s&numShards=%s&replicationFactor=%s&maxShardsPerNode=%s", config.getHostURL(), collection, numShards, replicationFactor, maxShardsPerNode);
        LOGGER.debug(hostQuery);

        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = client.execute(new HttpPost(hostQuery));
        LOGGER.warn(response.toString());
        return response.getStatusLine() != null && response.getStatusLine().getStatusCode() == 200;
    }






    @Data
    private class SolrConfig {
        private String hostURL;
        
        SolrConfig(String hostURL){
            this.hostURL = hostURL; 
        }
    }
}
