package com.techvg.shrms.web.rest;

import com.techvg.shrms.repository.LeaveTranscationRepository;
import com.techvg.shrms.service.LeaveTranscationQueryService;
import com.techvg.shrms.service.LeaveTranscationService;
import com.techvg.shrms.service.criteria.LeaveTranscationCriteria;
import com.techvg.shrms.service.dto.LeaveTranscationDTO;
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
 * REST controller for managing {@link com.techvg.shrms.domain.LeaveTranscation}.
 */
@RestController
@RequestMapping("/api")
public class LeaveTranscationResource {

    private final Logger log = LoggerFactory.getLogger(LeaveTranscationResource.class);

    private static final String ENTITY_NAME = "leaveTranscation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveTranscationService leaveTranscationService;

    private final LeaveTranscationRepository leaveTranscationRepository;

    private final LeaveTranscationQueryService leaveTranscationQueryService;

    public LeaveTranscationResource(
        LeaveTranscationService leaveTranscationService,
        LeaveTranscationRepository leaveTranscationRepository,
        LeaveTranscationQueryService leaveTranscationQueryService
    ) {
        this.leaveTranscationService = leaveTranscationService;
        this.leaveTranscationRepository = leaveTranscationRepository;
        this.leaveTranscationQueryService = leaveTranscationQueryService;
    }

    /**
     * {@code POST  /leave-transcations} : Create a new leaveTranscation.
     *
     * @param leaveTranscationDTO the leaveTranscationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveTranscationDTO, or with status {@code 400 (Bad Request)} if the leaveTranscation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-transcations")
    public ResponseEntity<LeaveTranscationDTO> createLeaveTranscation(@RequestBody LeaveTranscationDTO leaveTranscationDTO)
        throws URISyntaxException {
        log.debug("REST request to save LeaveTranscation : {}", leaveTranscationDTO);
        if (leaveTranscationDTO.getId() != null) {
            throw new BadRequestAlertException("A new leaveTranscation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveTranscationDTO result = leaveTranscationService.save(leaveTranscationDTO);
        return ResponseEntity
            .created(new URI("/api/leave-transcations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-transcations/:id} : Updates an existing leaveTranscation.
     *
     * @param id the id of the leaveTranscationDTO to save.
     * @param leaveTranscationDTO the leaveTranscationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveTranscationDTO,
     * or with status {@code 400 (Bad Request)} if the leaveTranscationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveTranscationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-transcations/{id}")
    public ResponseEntity<LeaveTranscationDTO> updateLeaveTranscation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LeaveTranscationDTO leaveTranscationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LeaveTranscation : {}, {}", id, leaveTranscationDTO);
        if (leaveTranscationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveTranscationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveTranscationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeaveTranscationDTO result = leaveTranscationService.update(leaveTranscationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leaveTranscationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-transcations/:id} : Partial updates given fields of an existing leaveTranscation, field will ignore if it is null
     *
     * @param id the id of the leaveTranscationDTO to save.
     * @param leaveTranscationDTO the leaveTranscationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveTranscationDTO,
     * or with status {@code 400 (Bad Request)} if the leaveTranscationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the leaveTranscationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the leaveTranscationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-transcations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeaveTranscationDTO> partialUpdateLeaveTranscation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LeaveTranscationDTO leaveTranscationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeaveTranscation partially : {}, {}", id, leaveTranscationDTO);
        if (leaveTranscationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leaveTranscationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leaveTranscationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeaveTranscationDTO> result = leaveTranscationService.partialUpdate(leaveTranscationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leaveTranscationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-transcations} : get all the leaveTranscations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveTranscations in body.
     */
    @GetMapping("/leave-transcations")
    public ResponseEntity<List<LeaveTranscationDTO>> getAllLeaveTranscations(
        LeaveTranscationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeaveTranscations by criteria: {}", criteria);
        Page<LeaveTranscationDTO> page = leaveTranscationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-transcations/count} : count all the leaveTranscations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-transcations/count")
    public ResponseEntity<Long> countLeaveTranscations(LeaveTranscationCriteria criteria) {
        log.debug("REST request to count LeaveTranscations by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveTranscationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-transcations/:id} : get the "id" leaveTranscation.
     *
     * @param id the id of the leaveTranscationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveTranscationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-transcations/{id}")
    public ResponseEntity<LeaveTranscationDTO> getLeaveTranscation(@PathVariable Long id) {
        log.debug("REST request to get LeaveTranscation : {}", id);
        Optional<LeaveTranscationDTO> leaveTranscationDTO = leaveTranscationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveTranscationDTO);
    }

    /**
     * {@code DELETE  /leave-transcations/:id} : delete the "id" leaveTranscation.
     *
     * @param id the id of the leaveTranscationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-transcations/{id}")
    public ResponseEntity<Void> deleteLeaveTranscation(@PathVariable Long id) {
        log.debug("REST request to delete LeaveTranscation : {}", id);
        leaveTranscationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
