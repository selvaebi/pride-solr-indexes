package uk.ac.ebi.pride.solr.indexes.pride.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.DefaultCvParam;
import uk.ac.ebi.pride.data.exception.SubmissionFileException;
import uk.ac.ebi.pride.data.io.SubmissionFileParser;
import uk.ac.ebi.pride.data.model.CvParam;
import uk.ac.ebi.pride.data.model.Submission;
import uk.ac.ebi.pride.solr.indexes.pride.model.PrideSolrProject;
import uk.ac.ebi.pride.utilities.term.CvTermReference;
import uk.ac.ebi.pride.utilities.util.Tuple;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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
        project.setKeywords(Arrays.asList(submission.getProjectMetaData().getKeywords().split(",")).stream().map(String::trim).collect(Collectors.toList()));
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

        List<Tuple<CvParamProvider, CvParamProvider>> factors = new ArrayList<>();
        submission.getProjectMetaData().getSpecies().forEach(x ->   addValue(CvTermReference.EFO_ORGANISM, factors, x));

        // Organism Part
        submission.getProjectMetaData().getCellTypes().forEach(x -> addValue(CvTermReference.EFO_ORGANISM_PART, factors, x));
        submission.getProjectMetaData().getTissues().forEach(x ->   addValue(CvTermReference.EFO_ORGANISM_PART, factors, x));

        // Disease
        submission.getProjectMetaData().getDiseases().stream().forEach(x->   addValue(CvTermReference.EFO_DISEASE, factors, x));
        project.setExperimentalFactors(factors);

        //PTMs
        project.setIdentifiedPTMStrings(submission.getProjectMetaData().getModifications().stream().map(CvParam::getName).collect(Collectors.toList()));

        /** Set Country **/
        String[] countryCodes = Locale.getISOCountries();
        int randomNum = ThreadLocalRandom.current().nextInt(0, countryCodes.length - 1);
        List<String> countries = new ArrayList<>();
        countries.add(countryCodes[randomNum]);
        project.setAllCountries(countries);

        //Add Dump date
        try {
            project.setPublicationDate(new SimpleDateFormat("YY-MM-dd").parse("2013-09-19"));
            project.setSubmissionDate(new SimpleDateFormat("YY-MM-dd").parse("2012-09-19"));
            project.setUpdatedDate(new SimpleDateFormat("YY-MM-dd").parse("2014-09-19"));
        } catch (ParseException e) {
            LOGGER.error("Error parsing date -- 2013-09-19 " + e.getMessage());
        }


        //Instruments properties
        List<CvParamProvider> instruments = new ArrayList<>();
        submission.getProjectMetaData().getInstruments().stream().forEach( x-> {
            instruments.add(new DefaultCvParam(x.getCvLabel(), x.getAccession(), x.getName(), x.getValue()));
        });

        project.setInstrumentsFromCvParam(instruments);
        return project;

    }

    /**
     * Add the experimental factors to the
     * @param key experimental factors name
     * @param factors factors values List
     * @param cvParam values of experimental factor
     * @return factors values List
     */
    private static List<Tuple<CvParamProvider, CvParamProvider>> addValue(CvTermReference key, List<Tuple<CvParamProvider, CvParamProvider>> factors, CvParam cvParam){
        Tuple<CvParamProvider, CvParamProvider> param = new Tuple<>(new DefaultCvParam(key.getAccession(), key.getName()),
                new DefaultCvParam(cvParam.getAccession(),cvParam.getName()));
        if (!factors.contains(param))
            factors.add(param);
        return factors;
    }


}
