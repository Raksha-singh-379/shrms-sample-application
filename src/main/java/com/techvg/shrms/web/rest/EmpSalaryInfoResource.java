package com.techvg.shrms.web.rest;

import com.techvg.shrms.repository.EmpSalaryInfoRepository;
import com.techvg.shrms.service.EmpSalaryInfoQueryService;
import com.techvg.shrms.service.EmpSalaryInfoService;
import com.techvg.shrms.service.criteria.EmpSalaryInfoCriteria;
import com.techvg.shrms.service.dto.EmpSalaryInfoDTO;
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
 * REST controller for managing {@link com.techvg.shrms.domain.EmpSalaryInfo}.
 */
@RestController
@RequestMapping("/api")
public class EmpSalaryInfoResource {

    private final Logger log = LoggerFactory.getLogger(EmpSalaryInfoResource.class);

    private static final String ENTITY_NAME = "empSalaryInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpSalaryInfoService empSalaryInfoService;

    private final EmpSalaryInfoRepository empSalaryInfoRepository;

    private final EmpSalaryInfoQueryService empSalaryInfoQueryService;

    public EmpSalaryInfoResource(
        EmpSalaryInfoService empSalaryInfoService,
        EmpSalaryInfoRepository empSalaryInfoRepository,
        EmpSalaryInfoQueryService empSalaryInfoQueryService
    ) {
        this.empSalaryInfoService = empSalaryInfoService;
        this.empSalaryInfoRepository = empSalaryInfoRepository;
        this.empSalaryInfoQueryService = empSalaryInfoQueryService;
    }

    /**
     * {@code POST  /emp-salary-infos} : Create a new empSalaryInfo.
     *
     * @param empSalaryInfoDTO the empSalaryInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empSalaryInfoDTO, or with status {@code 400 (Bad Request)} if the empSalaryInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emp-salary-infos")
    public ResponseEntity<EmpSalaryInfoDTO> createEmpSalaryInfo(@RequestBody EmpSalaryInfoDTO empSalaryInfoDTO) throws URISyntaxException {
        log.debug("REST request to save EmpSalaryInfo : {}", empSalaryInfoDTO);
        if (empSalaryInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new empSalaryInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmpSalaryInfoDTO result = empSalaryInfoService.save(empSalaryInfoDTO);
        return ResponseEntity
            .created(new URI("/api/emp-salary-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emp-salary-infos/:id} : Updates an existing empSalaryInfo.
     *
     * @param id the id of the empSalaryInfoDTO to save.
     * @param empSalaryInfoDTO the empSalaryInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empSalaryInfoDTO,
     * or with status {@code 400 (Bad Request)} if the empSalaryInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empSalaryInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emp-salary-infos/{id}")
    public ResponseEntity<EmpSalaryInfoDTO> updateEmpSalaryInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmpSalaryInfoDTO empSalaryInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EmpSalaryInfo : {}, {}", id, empSalaryInfoDTO);
        if (empSalaryInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empSalaryInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empSalaryInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmpSalaryInfoDTO result = empSalaryInfoService.update(empSalaryInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empSalaryInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /emp-salary-infos/:id} : Partial updates given fields of an existing empSalaryInfo, field will ignore if it is null
     *
     * @param id the id of the empSalaryInfoDTO to save.
     * @param empSalaryInfoDTO the empSalaryInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empSalaryInfoDTO,
     * or with status {@code 400 (Bad Request)} if the empSalaryInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the empSalaryInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the empSalaryInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/emp-salary-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmpSalaryInfoDTO> partialUpdateEmpSalaryInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmpSalaryInfoDTO empSalaryInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmpSalaryInfo partially : {}, {}", id, empSalaryInfoDTO);
        if (empSalaryInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empSalaryInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empSalaryInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmpSalaryInfoDTO> result = empSalaryInfoService.partialUpdate(empSalaryInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empSalaryInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /emp-salary-infos} : get all the empSalaryInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empSalaryInfos in body.
     */
    @GetMapping("/emp-salary-infos")
    public ResponseEntity<List<EmpSalaryInfoDTO>> getAllEmpSalaryInfos(
        EmpSalaryInfoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmpSalaryInfos by criteria: {}", criteria);
        Page<EmpSalaryInfoDTO> page = empSalaryInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emp-salary-infos/count} : count all the empSalaryInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/emp-salary-infos/count")
    public ResponseEntity<Long> countEmpSalaryInfos(EmpSalaryInfoCriteria criteria) {
        log.debug("REST request to count EmpSalaryInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(empSalaryInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /emp-salary-infos/:id} : get the "id" empSalaryInfo.
     *
     * @param id the id of the empSalaryInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empSalaryInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emp-salary-infos/{id}")
    public ResponseEntity<EmpSalaryInfoDTO> getEmpSalaryInfo(@PathVariable Long id) {
        log.debug("REST request to get EmpSalaryInfo : {}", id);
        Optional<EmpSalaryInfoDTO> empSalaryInfoDTO = empSalaryInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empSalaryInfoDTO);
    }

    /**
     * {@code DELETE  /emp-salary-infos/:id} : delete the "id" empSalaryInfo.
     *
     * @param id the id of the empSalaryInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emp-salary-infos/{id}")
    public ResponseEntity<Void> deleteEmpSalaryInfo(@PathVariable Long id) {
        log.debug("REST request to delete EmpSalaryInfo : {}", id);
        empSalaryInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
