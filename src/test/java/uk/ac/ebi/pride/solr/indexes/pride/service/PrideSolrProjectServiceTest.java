package uk.ac.ebi.pride.solr.indexes.pride.service;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.support.EmbeddedSolrServerFactory;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.pride.solr.indexes.pride.utils.RequireSolrServerTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 * <p>
 * This class
 * <p>
 * Created by ypriverol (ypriverol@gmail.com) on 20/03/2018.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PrideSolrProjectService.class)
public class PrideSolrProjectServiceTest {

    public static @ClassRule RequireSolrServerTest requiresRunningServer = RequireSolrServerTest.onLocalhost();

    @Autowired
    PrideSolrProjectService prideSolrProjectService;

    /**
     * Finds all entries using a single request.
     */
    @Test
    public void deleteAll() {
        prideSolrProjectService.deleteAll();
    }

}