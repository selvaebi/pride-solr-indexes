package uk.ac.ebi.pride.solr.indexes.pride.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 This class will test simple functions to interact with the Solr API.

 @author ypriverol
 @version $Id$

 */
public class SolrAPIHelperTest {

    SolrAPIHelper solrAPIHelper;

    @Before
    public void setUp() throws Exception {
        solrAPIHelper = SolrAPIHelper.getInstance("http://localhost:8983");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void deleteCollection() throws Exception {
        String collection = "pride_projects";
        if(solrAPIHelper.deleteCollection(collection)){
            System.out.println("Collection -- pride_projects has been deleted -- ");
        }
    }

    @Test
    public void createCollection() throws Exception {
        String collection = "pride_projects";
        if(solrAPIHelper.createCollection(collection, 2, 2, 2)){
            System.out.println("Collection -- pride_projects has been created  -- ");
        }
    }

    @Test
    public void listConfigSets() throws Exception {
        String collection = "pride_projects";
        if(solrAPIHelper.containsConfigSet(collection)){
            System.out.println("The following configset has been found in the server --- " + collection);
        }
    }



}