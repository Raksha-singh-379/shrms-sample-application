package com.techvg.shrms.web.rest;

import com.techvg.shrms.repository.ProjectTeamsRepository;
import com.techvg.shrms.service.ProjectTeamsQueryService;
import com.techvg.shrms.service.ProjectTeamsService;
import com.techvg.shrms.service.criteria.ProjectTeamsCriteria;
import com.techvg.shrms.service.dto.ProjectTeamsDTO;
import com.techvg.shrms.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.shrms.domain.ProjectTeams}.
 */
@RestController
@RequestMapping("/api")
public class ProjectTeamsResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTeamsResource.class);

    private static final String ENTITY_NAME = "projectTeams";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectTeamsService projectTeamsService;

    private final ProjectTeamsRepository projectTeamsRepository;

    private final ProjectTeamsQueryService projectTeamsQueryService;

    public ProjectTeamsResource(
        ProjectTeamsService projectTeamsService,
        ProjectTeamsRepository projectTeamsRepository,
        ProjectTeamsQueryService projectTeamsQueryService
    ) {
        this.projectTeamsService = projectTeamsService;
        this.projectTeamsRepository = projectTeamsRepository;
        this.projectTeamsQueryService = projectTeamsQueryService;
    }

    /**
     * {@code POST  /project-teams} : Create a new projectTeams.
     *
     * @param projectTeamsDTO the projectTeamsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectTeamsDTO, or with status {@code 400 (Bad Request)} if the projectTeams has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-teams")
    public ResponseEntity<ProjectTeamsDTO> createProjectTeams(@RequestBody ProjectTeamsDTO projectTeamsDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectTeams : {}", projectTeamsDTO);
        if (projectTeamsDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectTeams cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectTeamsDTO result = projectTeamsService.save(projectTeamsDTO);
        return ResponseEntity
            .created(new URI("/api/project-teams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-teams/:id} : Updates an existing projectTeams.
     *
     * @param id the id of the projectTeamsDTO to save.
     * @param projectTeamsDTO the projectTeamsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTeamsDTO,
     * or with status {@code 400 (Bad Request)} if the projectTeamsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectTeamsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-teams/{id}")
    public ResponseEntity<ProjectTeamsDTO> updateProjectTeams(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectTeamsDTO projectTeamsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectTeams : {}, {}", id, projectTeamsDTO);
        if (projectTeamsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectTeamsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectTeamsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectTeamsDTO result = projectTeamsService.update(projectTeamsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectTeamsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-teams/:id} : Partial updates given fields of an existing projectTeams, field will ignore if it is null
     *
     * @param id the id of the projectTeamsDTO to save.
     * @param projectTeamsDTO the projectTeamsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTeamsDTO,
     * or with status {@code 400 (Bad Request)} if the projectTeamsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projectTeamsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectTeamsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-teams/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjectTeamsDTO> partialUpdateProjectTeams(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectTeamsDTO projectTeamsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectTeams partially : {}, {}", id, projectTeamsDTO);
        if (projectTeamsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectTeamsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectTeamsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectTeamsDTO> result = projectTeamsService.partialUpdate(projectTeamsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectTeamsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /project-teams} : get all the projectTeams.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectTeams in body.
     */
    @GetMapping("/project-teams")
    public ResponseEntity<List<ProjectTeamsDTO>> getAllProjectTeams(
        ProjectTeamsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ProjectTeams by criteria: {}", criteria);
        Page<ProjectTeamsDTO> page = projectTeamsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-teams/count} : count all the projectTeams.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-teams/count")
    public ResponseEntity<Long> countProjectTeams(ProjectTeamsCriteria criteria) {
        log.debug("REST request to count ProjectTeams by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectTeamsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-teams/:id} : get the "id" projectTeams.
     *
     * @param id the id of the projectTeamsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectTeamsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-teams/{id}")
    public ResponseEntity<ProjectTeamsDTO> getProjectTeams(@PathVariable Long id) {
        log.debug("REST request to get ProjectTeams : {}", id);
        Optional<ProjectTeamsDTO> projectTeamsDTO = projectTeamsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectTeamsDTO);
    }

    /**
     * {@code DELETE  /project-teams/:id} : delete the "id" projectTeams.
     *
     * @param id the id of the projectTeamsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-teams/{id}")
    public ResponseEntity<Void> deleteProjectTeams(@PathVariable Long id) {
        log.debug("REST request to delete ProjectTeams : {}", id);
        projectTeamsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
