package uk.ac.ebi.pride.solr.indexes.pride.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Mock a PRIDE Solr Project Object.
 *
 * @author ypriverol
 * @version $Id$
 */
public class PrideSolrProjectMock {

    @Test
    public void prideSolrMock(){

        PrideSolrProject mockPrideSolrProject = Mockito.mock(PrideSolrProject.class);
        System.out.println(mockPrideSolrProject);
    }

}