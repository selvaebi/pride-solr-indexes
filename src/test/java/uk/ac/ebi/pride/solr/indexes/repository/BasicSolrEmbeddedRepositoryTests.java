package uk.ac.ebi.pride.solr.indexes.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.pride.solr.indexes.config.SolrEmbeddedTestConfiguration;

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
 * Created by ypriverol (ypriverol@gmail.com) on 23/03/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SolrEmbeddedTestConfiguration.class)
public class BasicSolrEmbeddedRepositoryTests {

    @Autowired
    SolrProjectRepository repository;

    /**
     * Finds all entries using a single request.
     */
    @Test
    public void findAll() {
        repository.findAll().forEach(System.out::println);
    }

    /**
     * Pages through all entries using cursor marks. Have a look at the Solr console output to see iteration steps.
     */
    @Test
    public void findAllUsingDeepPagination() {
        //repository.findAllUsingCursor().forEachRemaining(System.out::println);
    }
}
