package com.techvg.shrms.web.rest;

import com.techvg.shrms.repository.HolidaysRepository;
import com.techvg.shrms.service.HolidaysQueryService;
import com.techvg.shrms.service.HolidaysService;
import com.techvg.shrms.service.criteria.HolidaysCriteria;
import com.techvg.shrms.service.dto.HolidaysDTO;
import com.techvg.shrms.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.techvg.shrms.domain.Holidays}.
 */
@RestController
@RequestMapping("/api")
public class HolidaysResource {

    private final Logger log = LoggerFactory.getLogger(HolidaysResource.class);

    private static final String ENTITY_NAME = "holidays";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HolidaysService holidaysService;

    private final HolidaysRepository holidaysRepository;

    private final HolidaysQueryService holidaysQueryService;

    public HolidaysResource(
        HolidaysService holidaysService,
        HolidaysRepository holidaysRepository,
        HolidaysQueryService holidaysQueryService
    ) {
        this.holidaysService = holidaysService;
        this.holidaysRepository = holidaysRepository;
        this.holidaysQueryService = holidaysQueryService;
    }

    /**
     * {@code POST  /holidays} : Create a new holidays.
     *
     * @param holidaysDTO the holidaysDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new holidaysDTO, or with status {@code 400 (Bad Request)} if the holidays has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/holidays")
    public ResponseEntity<HolidaysDTO> createHolidays(@Valid @RequestBody HolidaysDTO holidaysDTO) throws URISyntaxException {
        log.debug("REST request to save Holidays : {}", holidaysDTO);
        if (holidaysDTO.getId() != null) {
            throw new BadRequestAlertException("A new holidays cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HolidaysDTO result = holidaysService.save(holidaysDTO);
        return ResponseEntity
            .created(new URI("/api/holidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /holidays/:id} : Updates an existing holidays.
     *
     * @param id the id of the holidaysDTO to save.
     * @param holidaysDTO the holidaysDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holidaysDTO,
     * or with status {@code 400 (Bad Request)} if the holidaysDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the holidaysDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/holidays/{id}")
    public ResponseEntity<HolidaysDTO> updateHolidays(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HolidaysDTO holidaysDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Holidays : {}, {}", id, holidaysDTO);
        if (holidaysDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, holidaysDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!holidaysRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HolidaysDTO result = holidaysService.update(holidaysDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, holidaysDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /holidays/:id} : Partial updates given fields of an existing holidays, field will ignore if it is null
     *
     * @param id the id of the holidaysDTO to save.
     * @param holidaysDTO the holidaysDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holidaysDTO,
     * or with status {@code 400 (Bad Request)} if the holidaysDTO is not valid,
     * or with status {@code 404 (Not Found)} if the holidaysDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the holidaysDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/holidays/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HolidaysDTO> partialUpdateHolidays(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HolidaysDTO holidaysDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Holidays partially : {}, {}", id, holidaysDTO);
        if (holidaysDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, holidaysDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!holidaysRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HolidaysDTO> result = holidaysService.partialUpdate(holidaysDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, holidaysDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /holidays} : get all the holidays.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of holidays in body.
     */
    @GetMapping("/holidays")
    public ResponseEntity<List<HolidaysDTO>> getAllHolidays(
        HolidaysCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Holidays by criteria: {}", criteria);
        Page<HolidaysDTO> page = holidaysQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /holidays/count} : count all the holidays.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/holidays/count")
    public ResponseEntity<Long> countHolidays(HolidaysCriteria criteria) {
        log.debug("REST request to count Holidays by criteria: {}", criteria);
        return ResponseEntity.ok().body(holidaysQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /holidays/:id} : get the "id" holidays.
     *
     * @param id the id of the holidaysDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the holidaysDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/holidays/{id}")
    public ResponseEntity<HolidaysDTO> getHolidays(@PathVariable Long id) {
        log.debug("REST request to get Holidays : {}", id);
        Optional<HolidaysDTO> holidaysDTO = holidaysService.findOne(id);
        return ResponseUtil.wrapOrNotFound(holidaysDTO);
    }

    /**
     * {@code DELETE  /holidays/:id} : delete the "id" holidays.
     *
     * @param id the id of the holidaysDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/holidays/{id}")
    public ResponseEntity<Void> deleteHolidays(@PathVariable Long id) {
        log.debug("REST request to delete Holidays : {}", id);
        holidaysService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
