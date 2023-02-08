package com.techvg.shrms.web.rest;

import com.techvg.shrms.repository.EmployeeDetailsRepository;
import com.techvg.shrms.service.EmployeeDetailsQueryService;
import com.techvg.shrms.service.EmployeeDetailsService;
import com.techvg.shrms.service.criteria.EmployeeDetailsCriteria;
import com.techvg.shrms.service.dto.EmployeeDetailsDTO;
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
 * REST controller for managing {@link com.techvg.shrms.domain.EmployeeDetails}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeDetailsResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeDetailsResource.class);

    private static final String ENTITY_NAME = "employeeDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeDetailsService employeeDetailsService;

    private final EmployeeDetailsRepository employeeDetailsRepository;

    private final EmployeeDetailsQueryService employeeDetailsQueryService;

    public EmployeeDetailsResource(
        EmployeeDetailsService employeeDetailsService,
        EmployeeDetailsRepository employeeDetailsRepository,
        EmployeeDetailsQueryService employeeDetailsQueryService
    ) {
        this.employeeDetailsService = employeeDetailsService;
        this.employeeDetailsRepository = employeeDetailsRepository;
        this.employeeDetailsQueryService = employeeDetailsQueryService;
    }

    /**
     * {@code POST  /employee-details} : Create a new employeeDetails.
     *
     * @param employeeDetailsDTO the employeeDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeDetailsDTO, or with status {@code 400 (Bad Request)} if the employeeDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-details")
    public ResponseEntity<EmployeeDetailsDTO> createEmployeeDetails(@RequestBody EmployeeDetailsDTO employeeDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeDetails : {}", employeeDetailsDTO);
        if (employeeDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeDetailsDTO result = employeeDetailsService.save(employeeDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/employee-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-details/:id} : Updates an existing employeeDetails.
     *
     * @param id the id of the employeeDetailsDTO to save.
     * @param employeeDetailsDTO the employeeDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the employeeDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-details/{id}")
    public ResponseEntity<EmployeeDetailsDTO> updateEmployeeDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeDetailsDTO employeeDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeDetails : {}, {}", id, employeeDetailsDTO);
        if (employeeDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeDetailsDTO result = employeeDetailsService.update(employeeDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-details/:id} : Partial updates given fields of an existing employeeDetails, field will ignore if it is null
     *
     * @param id the id of the employeeDetailsDTO to save.
     * @param employeeDetailsDTO the employeeDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the employeeDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the employeeDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeDetailsDTO> partialUpdateEmployeeDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeDetailsDTO employeeDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeDetails partially : {}, {}", id, employeeDetailsDTO);
        if (employeeDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeDetailsDTO> result = employeeDetailsService.partialUpdate(employeeDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-details} : get all the employeeDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeDetails in body.
     */
    @GetMapping("/employee-details")
    public ResponseEntity<List<EmployeeDetailsDTO>> getAllEmployeeDetails(
        EmployeeDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeDetails by criteria: {}", criteria);
        Page<EmployeeDetailsDTO> page = employeeDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-details/count} : count all the employeeDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-details/count")
    public ResponseEntity<Long> countEmployeeDetails(EmployeeDetailsCriteria criteria) {
        log.debug("REST request to count EmployeeDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-details/:id} : get the "id" employeeDetails.
     *
     * @param id the id of the employeeDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-details/{id}")
    public ResponseEntity<EmployeeDetailsDTO> getEmployeeDetails(@PathVariable Long id) {
        log.debug("REST request to get EmployeeDetails : {}", id);
        Optional<EmployeeDetailsDTO> employeeDetailsDTO = employeeDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeDetailsDTO);
    }

    /**
     * {@code DELETE  /employee-details/:id} : delete the "id" employeeDetails.
     *
     * @param id the id of the employeeDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-details/{id}")
    public ResponseEntity<Void> deleteEmployeeDetails(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeDetails : {}", id);
        employeeDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
