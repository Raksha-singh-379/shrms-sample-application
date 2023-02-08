package com.techvg.shrms.web.rest;

import com.techvg.shrms.repository.BanksDetailsRepository;
import com.techvg.shrms.service.BanksDetailsQueryService;
import com.techvg.shrms.service.BanksDetailsService;
import com.techvg.shrms.service.criteria.BanksDetailsCriteria;
import com.techvg.shrms.service.dto.BanksDetailsDTO;
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
 * REST controller for managing {@link com.techvg.shrms.domain.BanksDetails}.
 */
@RestController
@RequestMapping("/api")
public class BanksDetailsResource {

    private final Logger log = LoggerFactory.getLogger(BanksDetailsResource.class);

    private static final String ENTITY_NAME = "banksDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BanksDetailsService banksDetailsService;

    private final BanksDetailsRepository banksDetailsRepository;

    private final BanksDetailsQueryService banksDetailsQueryService;

    public BanksDetailsResource(
        BanksDetailsService banksDetailsService,
        BanksDetailsRepository banksDetailsRepository,
        BanksDetailsQueryService banksDetailsQueryService
    ) {
        this.banksDetailsService = banksDetailsService;
        this.banksDetailsRepository = banksDetailsRepository;
        this.banksDetailsQueryService = banksDetailsQueryService;
    }

    /**
     * {@code POST  /banks-details} : Create a new banksDetails.
     *
     * @param banksDetailsDTO the banksDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new banksDetailsDTO, or with status {@code 400 (Bad Request)} if the banksDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/banks-details")
    public ResponseEntity<BanksDetailsDTO> createBanksDetails(@RequestBody BanksDetailsDTO banksDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save BanksDetails : {}", banksDetailsDTO);
        if (banksDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new banksDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BanksDetailsDTO result = banksDetailsService.save(banksDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/banks-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /banks-details/:id} : Updates an existing banksDetails.
     *
     * @param id the id of the banksDetailsDTO to save.
     * @param banksDetailsDTO the banksDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated banksDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the banksDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the banksDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/banks-details/{id}")
    public ResponseEntity<BanksDetailsDTO> updateBanksDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BanksDetailsDTO banksDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BanksDetails : {}, {}", id, banksDetailsDTO);
        if (banksDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, banksDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!banksDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BanksDetailsDTO result = banksDetailsService.update(banksDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, banksDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /banks-details/:id} : Partial updates given fields of an existing banksDetails, field will ignore if it is null
     *
     * @param id the id of the banksDetailsDTO to save.
     * @param banksDetailsDTO the banksDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated banksDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the banksDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the banksDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the banksDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/banks-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BanksDetailsDTO> partialUpdateBanksDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BanksDetailsDTO banksDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BanksDetails partially : {}, {}", id, banksDetailsDTO);
        if (banksDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, banksDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!banksDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BanksDetailsDTO> result = banksDetailsService.partialUpdate(banksDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, banksDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /banks-details} : get all the banksDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of banksDetails in body.
     */
    @GetMapping("/banks-details")
    public ResponseEntity<List<BanksDetailsDTO>> getAllBanksDetails(
        BanksDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BanksDetails by criteria: {}", criteria);
        Page<BanksDetailsDTO> page = banksDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /banks-details/count} : count all the banksDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/banks-details/count")
    public ResponseEntity<Long> countBanksDetails(BanksDetailsCriteria criteria) {
        log.debug("REST request to count BanksDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(banksDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /banks-details/:id} : get the "id" banksDetails.
     *
     * @param id the id of the banksDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the banksDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/banks-details/{id}")
    public ResponseEntity<BanksDetailsDTO> getBanksDetails(@PathVariable Long id) {
        log.debug("REST request to get BanksDetails : {}", id);
        Optional<BanksDetailsDTO> banksDetailsDTO = banksDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(banksDetailsDTO);
    }

    /**
     * {@code DELETE  /banks-details/:id} : delete the "id" banksDetails.
     *
     * @param id the id of the banksDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/banks-details/{id}")
    public ResponseEntity<Void> deleteBanksDetails(@PathVariable Long id) {
        log.debug("REST request to delete BanksDetails : {}", id);
        banksDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
