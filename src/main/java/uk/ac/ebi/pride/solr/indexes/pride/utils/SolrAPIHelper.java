package uk.ac.ebi.pride.solr.indexes.pride.utils;

import lombok.Data;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideProjectFieldEnum;

import java.io.IOException;

/**
 This class is a API helper for Solr API to enable delete/create/update delete of Collections.
 We have decided to don't use the RestTemplate to do not add another extra dependency. The

 @author ypriverol
 @version $Id$
 **/

public class SolrAPIHelper {

    /** Logger **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SolrAPIHelper.class);

    /** config http URL **/
    protected SolrConfig config;

    /** Private instance **/
    private static SolrAPIHelper instance;

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

    /**
     * List all config sets in the Solr server
     * @return List of ConfigSets
     * @throws IOException
     */
    public boolean containsConfigSet(String collectionSet) throws IOException {
        String hostQuery = String.format("%s/solr/admin/configs?action=LIST&wt=json", config.getHostURL());
        LOGGER.debug(hostQuery);

        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = client.execute(new HttpGet(hostQuery));
        LOGGER.warn(response.toString());
        if(response.getStatusLine() != null && response.getStatusLine().getStatusCode() == 200){
            String jsonString =EntityUtils.toString(response.getEntity());
            if(jsonString.toLowerCase().contains(collectionSet.toLowerCase()))
                return true;
        }
        return false;
    }

    /**
     * This function change the Type of a Field in the schema of a given collection. It can be use to refine and be sure that the collection
     * has the proper Types in the  model.
     * @param collection collection with the field
     * @param fieldName field
     * @param type type of the field
     * @return True if the field has been change.
     */

    public boolean updateFieldType(String collection, String fieldName, String type, Boolean multiValue) throws IOException {

        String hostQuery = String.format("%s/solr/%s/schema", config.getHostURL(), collection);
        HttpPost httpost = new HttpPost(hostQuery);

        String schemaQuery = String.format("{'replace-field':{'name':'%s', 'type':'%s', 'multiValued':'%s'}}", fieldName, type, multiValue.toString());
        //passes the results to a string builder/entity
        StringEntity se = new StringEntity(schemaQuery);
        httpost.setEntity(se);
        //sets a request header so the page receving the request
        //will know what to do with it
        httpost.setHeader("Accept", "application/json");
        httpost.setHeader("Content-type", "application/json");

        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = client.execute(httpost);
        String jsonString =EntityUtils.toString(response.getEntity());
        LOGGER.debug(jsonString);
        return response.getStatusLine() != null && response.getStatusLine().getStatusCode() == 200;
    }


    /**
     * This function add the Type of a Field in the schema of a given collection. It can be use to refine and be sure that the collection
     * has the proper Types in the  model.
     * @param collection collection with the field
     * @param fieldName field
     * @param type type of the field
     * @return True if the field has been change.
     */
    public boolean addField(String collection, String fieldName, String type, Boolean multiValue) throws IOException {

        String hostQuery = String.format("%s/solr/%s/schema", config.getHostURL(), collection);
        HttpPost httpost = new HttpPost(hostQuery);

        String schemaQuery = String.format("{'add-field':{'name':'%s', 'type':'%s', 'multiValued':'%s'}}", fieldName, type, multiValue.toString());
        //passes the results to a string builder/entity
        StringEntity se = new StringEntity(schemaQuery);
        httpost.setEntity(se);
        //sets a request header so the page receving the request
        //will know what to do with it
        httpost.setHeader("Accept", "application/json");
        httpost.setHeader("Content-type", "application/json");

        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = client.execute(httpost);
        String jsonString =EntityUtils.toString(response.getEntity());
        LOGGER.debug(jsonString);
        return response.getStatusLine() != null && response.getStatusLine().getStatusCode() == 200;
    }

    /**
     * Get the Schema for an specific collection
     * @param collection name of the collection
     * @return result the schema in Json String
     * @throws IOException
     */
    public String getSchemaByCollection(String collection) throws IOException {

        String hostQuery = String.format("%s/solr/%s/schema/fields", config.getHostURL(), collection);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = client.execute(new HttpGet(hostQuery));
        LOGGER.warn(response.toString());
        if(response.getStatusLine() != null && response.getStatusLine().getStatusCode() == 200){
            return EntityUtils.toString(response.getEntity());
        }
        return null;
    }
    /**
     * Solr Config Class containing the http URL of the Solr Server.
     */
    @Data
    private class SolrConfig {
        private String hostURL;
        
        SolrConfig(String hostURL){
            this.hostURL = hostURL; 
        }
    }

    /**
     * Refine the schema of PRIDE Solr Projects taking into account the Enum
     * @param collection pride collection
     * @return True if changes are possible
     */
    public boolean refinePrideSolrProjectsSchema(String collection){
        try{
            String schemaFields = getSchemaByCollection(collection);
            for(PrideProjectFieldEnum fieldEnum: PrideProjectFieldEnum.values()){
                if(!schemaFields.contains(fieldEnum.getValue())){
                    String fieldType = (fieldEnum.getType() == ConstantsSolrTypes.DEFAULT)?"text":ConstantsSolrTypes.STRING.getType();
                    if(addField(collection, fieldEnum.getValue(), fieldType, fieldEnum.getMultiValue())){
                        LOGGER.debug("The field -- " + fieldEnum.getValue() + " -- has been created");
                    }
                }else{
                    boolean currentStatus = updateFieldType(collection, fieldEnum.getValue(), fieldEnum.getType().getType(), fieldEnum.getMultiValue());
                    if(currentStatus){
                        LOGGER.debug("The field -- " + fieldEnum.getValue() + " -- has been updated");
                    }
                }
            }
        }catch (IOException e){
            LOGGER.error(e.getMessage());
        }
        return true;
    }



}
