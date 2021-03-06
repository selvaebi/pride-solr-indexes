package uk.ac.ebi.pride.solr.indexes.model;

import org.junit.Test;
import org.mockito.Mockito;
import uk.ac.ebi.pride.solr.commons.PrideSolrProject;

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