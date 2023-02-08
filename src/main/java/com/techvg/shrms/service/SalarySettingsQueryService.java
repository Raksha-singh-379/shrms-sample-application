package com.techvg.shrms.service;

import com.techvg.shrms.domain.*; // for static metamodels
import com.techvg.shrms.domain.SalarySettings;
import com.techvg.shrms.repository.SalarySettingsRepository;
import com.techvg.shrms.service.criteria.SalarySettingsCriteria;
import com.techvg.shrms.service.dto.SalarySettingsDTO;
import com.techvg.shrms.service.mapper.SalarySettingsMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link SalarySettings} entities in the database.
 * The main input is a {@link SalarySettingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SalarySettingsDTO} or a {@link Page} of {@link SalarySettingsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SalarySettingsQueryService extends QueryService<SalarySettings> {

    private final Logger log = LoggerFactory.getLogger(SalarySettingsQueryService.class);

    private final SalarySettingsRepository salarySettingsRepository;

    private final SalarySettingsMapper salarySettingsMapper;

    public SalarySettingsQueryService(SalarySettingsRepository salarySettingsRepository, SalarySettingsMapper salarySettingsMapper) {
        this.salarySettingsRepository = salarySettingsRepository;
        this.salarySettingsMapper = salarySettingsMapper;
    }

    /**
     * Return a {@link List} of {@link SalarySettingsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SalarySettingsDTO> findByCriteria(SalarySettingsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SalarySettings> specification = createSpecification(criteria);
        return salarySettingsMapper.toDto(salarySettingsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SalarySettingsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SalarySettingsDTO> findByCriteria(SalarySettingsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SalarySettings> specification = createSpecification(criteria);
        return salarySettingsRepository.findAll(specification, page).map(salarySettingsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SalarySettingsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SalarySettings> specification = createSpecification(criteria);
        return salarySettingsRepository.count(specification);
    }

    /**
     * Function to convert {@link SalarySettingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SalarySettings> createSpecification(SalarySettingsCriteria criteria) {
        Specification<SalarySettings> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SalarySettings_.id));
            }
            if (criteria.getDa() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDa(), SalarySettings_.da));
            }
            if (criteria.getHra() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHra(), SalarySettings_.hra));
            }
            if (criteria.getEmployeeshare() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeshare(), SalarySettings_.employeeshare));
            }
            if (criteria.getCompanyshare() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyshare(), SalarySettings_.companyshare));
            }
            if (criteria.getSalaryFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalaryFrom(), SalarySettings_.salaryFrom));
            }
            if (criteria.getSalaryTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalaryTo(), SalarySettings_.salaryTo));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), SalarySettings_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SalarySettings_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SalarySettings_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SalarySettings_.createdOn));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), SalarySettings_.deleted));
            }
        }
        return specification;
    }
}
