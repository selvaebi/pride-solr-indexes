package uk.ac.ebi.pride.solr.indexes.pride.utils;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.pride.data.exception.SubmissionFileException;
import uk.ac.ebi.pride.data.io.SubmissionFileParser;
import uk.ac.ebi.pride.data.model.Submission;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;

import java.io.*;

/**
 * This class helps to read PRIDE projects from Files and convert them into {@link PrideSolrProject}. This class is using the
 * PX Core library to read PX Submission Files.
 *
 * @author ypriverol
 * @version $Id$
 */
public class PrideProjectReader {

    static Logger LOGGER = LoggerFactory.getLogger(PrideProjectReader.class);

    /**
     * Read a Pride Project from file using Tab delimited system. This function is a helper for testing
     *
     * @param pxSubmissionFile Pride Submission File in PX
     * @return Pride Project
     */
    public static PrideSolrProject read(File pxSubmissionFile){

        PrideSolrProject project = new PrideSolrProject();
        try {
            Submission pxSubmission = SubmissionFileParser.parse(pxSubmissionFile);
            project.setAccession(pxSubmission.getProjectMetaData().getResubmissionPxAccession());
        } catch (SubmissionFileException e) {
            LOGGER.error("Error in the file provided -- " + pxSubmissionFile, e);
        }

        return project;
    }


}
