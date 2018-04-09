package uk.ac.ebi.pride.solr.indexes.pride.utils;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.pride.data.exception.SubmissionFileException;
import uk.ac.ebi.pride.data.io.SubmissionFileParser;
import uk.ac.ebi.pride.data.model.Contact;
import uk.ac.ebi.pride.data.model.CvParam;
import uk.ac.ebi.pride.data.model.Submission;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;

import java.io.*;
import java.util.*;

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
            project = convertSubmissionToProject(pxSubmission);
        } catch (SubmissionFileException e) {
            LOGGER.error("Error in the file provided -- " + pxSubmissionFile, e);
        }

        return project;
    }

    /**
     * Conversion of PX Submission file into a Pride Solr Project.
     * @param submission PX submission file
     * @return Pride Solr  Project
     */
    static PrideSolrProject convertSubmissionToProject(Submission submission){
        PrideSolrProject project = new PrideSolrProject();

        //Get accession, title, keywords, Data and Sample protocols
        project.setAccession(submission.getProjectMetaData().getResubmissionPxAccession());
        project.setTitle(submission.getProjectMetaData().getProjectTitle());
        project.setKeywords(Arrays.asList(submission.getProjectMetaData().getKeywords().split(",")));
        project.setDataProcessingProtocol(submission.getProjectMetaData().getDataProcessingProtocol());
        project.setSampleProcessingProtocol(submission.getProjectMetaData().getSampleProcessingProtocol());
        project.setProjectDescription(submission.getProjectMetaData().getProjectDescription());

        //Get project Tags
        project.setProjectTags(new ArrayList<>(submission.getProjectMetaData().getProjectTags()));

        //Get the researchers
        List<String> contacts = new ArrayList<>();
        contacts.add(submission.getProjectMetaData().getLabHeadContact().getName());
        project.setLabPIs(contacts);

        //Get the submitters information
        List<String> submitters = new ArrayList<>();
        submitters.add(submission.getProjectMetaData().getSubmitterContact().getName());
        project.setSubmitters(submitters);

        //Get the affiliations
        List<String> affiliations = new ArrayList<>();
        affiliations.add(submission.getProjectMetaData().getSubmitterContact().getAffiliation());
        affiliations.add(submission.getProjectMetaData().getLabHeadContact().getAffiliation());
        project.setAffiliations(affiliations);

        //Read Experimental Factors

        // First Species
        Map<String, List<String>> factors = new HashMap<>();
        submission.getProjectMetaData().getSpecies().stream().forEach(x ->   addValue("organism", factors, x));
        submission.getProjectMetaData().getCellTypes().stream().forEach(x -> addValue("cell type", factors, x));
        submission.getProjectMetaData().getDiseases().stream().forEach(x->   addValue("disease", factors, x));
        submission.getProjectMetaData().getTissues().stream().forEach(x ->   addValue("organism part", factors, x));
        project.setExperimentalFactors(factors);

        return project;

    }

    private static Map<String, List<String>> addValue(String key, Map<String, List<String>> factors, CvParam cvParam){
        List<String> values = new ArrayList<>();
        if (factors.containsKey(key))
            values = factors.get(key);
        values.add(cvParam.getName());
        factors.put(key, values);
        return factors;
    }


}
