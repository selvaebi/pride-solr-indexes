package uk.ac.ebi.pride.solr.indexes.pride.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;

import java.io.*;

/**
 * @author ypriverol
 * @version $Id$
 */
public class ReadPrideProjectFactory {

    static Logger LOGGER = LoggerFactory.getLogger(ReadPrideProjectFactory.class);

    /**
     * Read a Pride Project from file using Tab delimited system. This function is a helper for testing
     *
     * @param pxSubmissionFile Pride Submission File in PX
     * @return Pride Project
     */
    public static PrideSolrProject readPXSubmissionFile(File pxSubmissionFile){

        PrideSolrProject project = new PrideSolrProject();
        try{
            BufferedReader bReader = new BufferedReader(new FileReader(pxSubmissionFile));
            String line;

            while ((line = bReader.readLine()) != null) {
                String datavalue[] = line.split("\t");
                String value1 = datavalue[0];
                String value2 = datavalue[1];
                int value3 = Integer.parseInt(datavalue[2]);
                double value4 = Double.parseDouble(datavalue[3]);

            }
            bReader.close();
        }catch (IOException e){
            LOGGER.error("The File provided by the function don't exits -- " + pxSubmissionFile, e);
        }
        return project;
    }
}
