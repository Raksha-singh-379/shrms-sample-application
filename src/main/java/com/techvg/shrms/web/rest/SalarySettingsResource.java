package com.techvg.shrms.web.rest;

import com.techvg.shrms.repository.SalarySettingsRepository;
import com.techvg.shrms.service.SalarySettingsQueryService;
import com.techvg.shrms.service.SalarySettingsService;
import com.techvg.shrms.service.criteria.SalarySettingsCriteria;
import com.techvg.shrms.service.dto.SalarySettingsDTO;
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
 * REST controller for managing {@link com.techvg.shrms.domain.SalarySettings}.
 */
@RestController
@RequestMapping("/api")
public class SalarySettingsResource {

    private final Logger log = LoggerFactory.getLogger(SalarySettingsResource.class);

    private static final String ENTITY_NAME = "salarySettings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalarySettingsService salarySettingsService;

    private final SalarySettingsRepository salarySettingsRepository;

    private final SalarySettingsQueryService salarySettingsQueryService;

    public SalarySettingsResource(
        SalarySettingsService salarySettingsService,
        SalarySettingsRepository salarySettingsRepository,
        SalarySettingsQueryService salarySettingsQueryService
    ) {
        this.salarySettingsService = salarySettingsService;
        this.salarySettingsRepository = salarySettingsRepository;
        this.salarySettingsQueryService = salarySettingsQueryService;
    }

    /**
     * {@code POST  /salary-settings} : Create a new salarySettings.
     *
     * @param salarySettingsDTO the salarySettingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salarySettingsDTO, or with status {@code 400 (Bad Request)} if the salarySettings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/salary-settings")
    public ResponseEntity<SalarySettingsDTO> createSalarySettings(@RequestBody SalarySettingsDTO salarySettingsDTO)
        throws URISyntaxException {
        log.debug("REST request to save SalarySettings : {}", salarySettingsDTO);
        if (salarySettingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new salarySettings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalarySettingsDTO result = salarySettingsService.save(salarySettingsDTO);
        return ResponseEntity
            .created(new URI("/api/salary-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /salary-settings/:id} : Updates an existing salarySettings.
     *
     * @param id the id of the salarySettingsDTO to save.
     * @param salarySettingsDTO the salarySettingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salarySettingsDTO,
     * or with status {@code 400 (Bad Request)} if the salarySettingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salarySettingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/salary-settings/{id}")
    public ResponseEntity<SalarySettingsDTO> updateSalarySettings(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SalarySettingsDTO salarySettingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SalarySettings : {}, {}", id, salarySettingsDTO);
        if (salarySettingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salarySettingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salarySettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SalarySettingsDTO result = salarySettingsService.update(salarySettingsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salarySettingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /salary-settings/:id} : Partial updates given fields of an existing salarySettings, field will ignore if it is null
     *
     * @param id the id of the salarySettingsDTO to save.
     * @param salarySettingsDTO the salarySettingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salarySettingsDTO,
     * or with status {@code 400 (Bad Request)} if the salarySettingsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the salarySettingsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the salarySettingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/salary-settings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SalarySettingsDTO> partialUpdateSalarySettings(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SalarySettingsDTO salarySettingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SalarySettings partially : {}, {}", id, salarySettingsDTO);
        if (salarySettingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salarySettingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salarySettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SalarySettingsDTO> result = salarySettingsService.partialUpdate(salarySettingsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salarySettingsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /salary-settings} : get all the salarySettings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salarySettings in body.
     */
    @GetMapping("/salary-settings")
    public ResponseEntity<List<SalarySettingsDTO>> getAllSalarySettings(
        SalarySettingsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SalarySettings by criteria: {}", criteria);
        Page<SalarySettingsDTO> page = salarySettingsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /salary-settings/count} : count all the salarySettings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/salary-settings/count")
    public ResponseEntity<Long> countSalarySettings(SalarySettingsCriteria criteria) {
        log.debug("REST request to count SalarySettings by criteria: {}", criteria);
        return ResponseEntity.ok().body(salarySettingsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /salary-settings/:id} : get the "id" salarySettings.
     *
     * @param id the id of the salarySettingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salarySettingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/salary-settings/{id}")
    public ResponseEntity<SalarySettingsDTO> getSalarySettings(@PathVariable Long id) {
        log.debug("REST request to get SalarySettings : {}", id);
        Optional<SalarySettingsDTO> salarySettingsDTO = salarySettingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salarySettingsDTO);
    }

    /**
     * {@code DELETE  /salary-settings/:id} : delete the "id" salarySettings.
     *
     * @param id the id of the salarySettingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/salary-settings/{id}")
    public ResponseEntity<Void> deleteSalarySettings(@PathVariable Long id) {
        log.debug("REST request to delete SalarySettings : {}", id);
        salarySettingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
