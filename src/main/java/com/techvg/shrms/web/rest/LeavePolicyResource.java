package com.techvg.shrms.web.rest;

import com.techvg.shrms.repository.LeavePolicyRepository;
import com.techvg.shrms.service.LeavePolicyQueryService;
import com.techvg.shrms.service.LeavePolicyService;
import com.techvg.shrms.service.criteria.LeavePolicyCriteria;
import com.techvg.shrms.service.dto.LeavePolicyDTO;
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
 * REST controller for managing {@link com.techvg.shrms.domain.LeavePolicy}.
 */
@RestController
@RequestMapping("/api")
public class LeavePolicyResource {

    private final Logger log = LoggerFactory.getLogger(LeavePolicyResource.class);

    private static final String ENTITY_NAME = "leavePolicy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeavePolicyService leavePolicyService;

    private final LeavePolicyRepository leavePolicyRepository;

    private final LeavePolicyQueryService leavePolicyQueryService;

    public LeavePolicyResource(
        LeavePolicyService leavePolicyService,
        LeavePolicyRepository leavePolicyRepository,
        LeavePolicyQueryService leavePolicyQueryService
    ) {
        this.leavePolicyService = leavePolicyService;
        this.leavePolicyRepository = leavePolicyRepository;
        this.leavePolicyQueryService = leavePolicyQueryService;
    }

    /**
     * {@code POST  /leave-policies} : Create a new leavePolicy.
     *
     * @param leavePolicyDTO the leavePolicyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leavePolicyDTO, or with status {@code 400 (Bad Request)} if the leavePolicy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-policies")
    public ResponseEntity<LeavePolicyDTO> createLeavePolicy(@RequestBody LeavePolicyDTO leavePolicyDTO) throws URISyntaxException {
        log.debug("REST request to save LeavePolicy : {}", leavePolicyDTO);
        if (leavePolicyDTO.getId() != null) {
            throw new BadRequestAlertException("A new leavePolicy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeavePolicyDTO result = leavePolicyService.save(leavePolicyDTO);
        return ResponseEntity
            .created(new URI("/api/leave-policies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-policies/:id} : Updates an existing leavePolicy.
     *
     * @param id the id of the leavePolicyDTO to save.
     * @param leavePolicyDTO the leavePolicyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leavePolicyDTO,
     * or with status {@code 400 (Bad Request)} if the leavePolicyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leavePolicyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-policies/{id}")
    public ResponseEntity<LeavePolicyDTO> updateLeavePolicy(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LeavePolicyDTO leavePolicyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LeavePolicy : {}, {}", id, leavePolicyDTO);
        if (leavePolicyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leavePolicyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leavePolicyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeavePolicyDTO result = leavePolicyService.update(leavePolicyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leavePolicyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /leave-policies/:id} : Partial updates given fields of an existing leavePolicy, field will ignore if it is null
     *
     * @param id the id of the leavePolicyDTO to save.
     * @param leavePolicyDTO the leavePolicyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leavePolicyDTO,
     * or with status {@code 400 (Bad Request)} if the leavePolicyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the leavePolicyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the leavePolicyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/leave-policies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeavePolicyDTO> partialUpdateLeavePolicy(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LeavePolicyDTO leavePolicyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeavePolicy partially : {}, {}", id, leavePolicyDTO);
        if (leavePolicyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leavePolicyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leavePolicyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeavePolicyDTO> result = leavePolicyService.partialUpdate(leavePolicyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leavePolicyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /leave-policies} : get all the leavePolicies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leavePolicies in body.
     */
    @GetMapping("/leave-policies")
    public ResponseEntity<List<LeavePolicyDTO>> getAllLeavePolicies(
        LeavePolicyCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LeavePolicies by criteria: {}", criteria);
        Page<LeavePolicyDTO> page = leavePolicyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-policies/count} : count all the leavePolicies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-policies/count")
    public ResponseEntity<Long> countLeavePolicies(LeavePolicyCriteria criteria) {
        log.debug("REST request to count LeavePolicies by criteria: {}", criteria);
        return ResponseEntity.ok().body(leavePolicyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-policies/:id} : get the "id" leavePolicy.
     *
     * @param id the id of the leavePolicyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leavePolicyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-policies/{id}")
    public ResponseEntity<LeavePolicyDTO> getLeavePolicy(@PathVariable Long id) {
        log.debug("REST request to get LeavePolicy : {}", id);
        Optional<LeavePolicyDTO> leavePolicyDTO = leavePolicyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leavePolicyDTO);
    }

    /**
     * {@code DELETE  /leave-policies/:id} : delete the "id" leavePolicy.
     *
     * @param id the id of the leavePolicyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-policies/{id}")
    public ResponseEntity<Void> deleteLeavePolicy(@PathVariable Long id) {
        log.debug("REST request to delete LeavePolicy : {}", id);
        leavePolicyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
