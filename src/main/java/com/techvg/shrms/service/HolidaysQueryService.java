package com.techvg.shrms.service;

import com.techvg.shrms.domain.*; // for static metamodels
import com.techvg.shrms.domain.Holidays;
import com.techvg.shrms.repository.HolidaysRepository;
import com.techvg.shrms.service.criteria.HolidaysCriteria;
import com.techvg.shrms.service.dto.HolidaysDTO;
import com.techvg.shrms.service.mapper.HolidaysMapper;
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
 * Service for executing complex queries for {@link Holidays} entities in the database.
 * The main input is a {@link HolidaysCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HolidaysDTO} or a {@link Page} of {@link HolidaysDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HolidaysQueryService extends QueryService<Holidays> {

    private final Logger log = LoggerFactory.getLogger(HolidaysQueryService.class);

    private final HolidaysRepository holidaysRepository;

    private final HolidaysMapper holidaysMapper;

    public HolidaysQueryService(HolidaysRepository holidaysRepository, HolidaysMapper holidaysMapper) {
        this.holidaysRepository = holidaysRepository;
        this.holidaysMapper = holidaysMapper;
    }

    /**
     * Return a {@link List} of {@link HolidaysDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HolidaysDTO> findByCriteria(HolidaysCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Holidays> specification = createSpecification(criteria);
        return holidaysMapper.toDto(holidaysRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HolidaysDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HolidaysDTO> findByCriteria(HolidaysCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Holidays> specification = createSpecification(criteria);
        return holidaysRepository.findAll(specification, page).map(holidaysMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HolidaysCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Holidays> specification = createSpecification(criteria);
        return holidaysRepository.count(specification);
    }

    /**
     * Function to convert {@link HolidaysCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Holidays> createSpecification(HolidaysCriteria criteria) {
        Specification<Holidays> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Holidays_.id));
            }
            if (criteria.getHolidayName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHolidayName(), Holidays_.holidayName));
            }
            if (criteria.getHolidayDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHolidayDate(), Holidays_.holidayDate));
            }
            if (criteria.getDay() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDay(), Holidays_.day));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), Holidays_.year));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Holidays_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Holidays_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Holidays_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), Holidays_.createdOn));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), Holidays_.deleted));
            }
            if (criteria.getCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCompanyId(), root -> root.join(Holidays_.company, JoinType.LEFT).get(Company_.id))
                    );
            }
        }
        return specification;
    }
}
