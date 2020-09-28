package uk.ac.ebi.pride.solr.indexes.pride.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.solr.client.solrj.SolrServerException;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.pride.solr.api.commons.PrideSolrProject;
import uk.ac.ebi.pride.solr.api.commons.dto.FindByKeywordInputDto;
import uk.ac.ebi.pride.solr.indexes.pride.services.ProjectService;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("project")
@Tag(name = "Project")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrideSolrProject> save(@RequestBody PrideSolrProject prideSolrProject) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.save(prideSolrProject));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrideSolrProject> update(@RequestBody PrideSolrProject prideSolrProject) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.update(prideSolrProject));
    }

    @RequestMapping(value = "/upsert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrideSolrProject> upsert(@RequestBody PrideSolrProject prideSolrProject) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.upsert(prideSolrProject));
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<PrideSolrProject>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.findAll());
    }

    @RequestMapping(value = "/deleteByProjectId", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteByProjectId(@RequestParam String projectId) {
        projectService.deleteProjectById(projectId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAll() {
        projectService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "/saveAll", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveAll(List<PrideSolrProject> projects) {
        projectService.saveAll(projects);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(value = "/findAllUsingCursor", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterator<PrideSolrProject>> findAllUsingCursor() {
        return ResponseEntity.ok(projectService.findAllUsingCursor());
    }

    @RequestMapping(value = "/findByAccession", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrideSolrProject> findByAccession(@RequestParam("accession") String accession) {
        return ResponseEntity.ok(projectService.findByAccession(accession));
    }

    @RequestMapping(value = "/findByKeyword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PageableAsQueryParam
    public ResponseEntity<HighlightPage<PrideSolrProject>> findByKeyword(
            @RequestBody FindByKeywordInputDto findByKeywordInputDto, @Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(projectService.findByKeyword(findByKeywordInputDto.getKeywords(), findByKeywordInputDto.getFilterQuery(),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()),
                findByKeywordInputDto.getDateGap()));
    }

    @RequestMapping(value = "/findAllIgnoreCase", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PageableAsQueryParam
    public ResponseEntity<Page<PrideSolrProject>> findAllIgnoreCase(@Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(projectService.findAllIgnoreCase(pageable));

    }

    @RequestMapping(value = "/findAllFacetIgnoreCase", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PageableAsQueryParam
    public ResponseEntity<FacetPage<PrideSolrProject>> findAllFacetIgnoreCase(@Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(projectService.findAllFacetIgnoreCase(pageable));
    }

    @RequestMapping(value = "/findFacetByKeyword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FacetPage<PrideSolrProject>> findFacetByKeyword(@RequestBody FindByKeywordInputDto findByKeywordInputDto,
                                                                          @Parameter(hidden = true) Pageable pageable) {
        FacetPage<PrideSolrProject> facetPage = projectService.findFacetByKeyword(findByKeywordInputDto.getKeywords(),
                findByKeywordInputDto.getFilterQuery(), PageRequest.of(0, 10),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), findByKeywordInputDto.getDateGap());
        return ResponseEntity.ok(facetPage);
    }

    @RequestMapping(value = "/findSimilarProjects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PageableAsQueryParam
    public ResponseEntity<List<PrideSolrProject>> findSimilarProjects(String accession, @Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(projectService.findSimilarProjects(accession, pageable.getPageSize(), pageable.getPageNumber()));
    }

    @RequestMapping(value = "/findAutoComplete", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> findAutoComplete(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(projectService.findAutoComplete(keyword));
    }

    @RequestMapping(value = "/findProjectAccessionsWithEmptyPeptideSequencesOrProteinIdentifications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<String>> findProjectAccessionsWithEmptyPeptideSequencesOrProteinIdentifications() throws IOException, SolrServerException {
        return ResponseEntity.ok(projectService.findProjectAccessionsWithEmptyPeptideSequencesOrProteinIdentifications());
    }

    @RequestMapping(value = "/findProjectAccessionsWithEmptyFileNames", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<String>> findProjectAccessionsWithEmptyFileNames() throws IOException, SolrServerException {
        return ResponseEntity.ok(projectService.findProjectAccessionsWithEmptyFileNames());
    }

}
