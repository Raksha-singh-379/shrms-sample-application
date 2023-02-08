package com.techvg.shrms.service;

import com.techvg.shrms.domain.*; // for static metamodels
import com.techvg.shrms.domain.LeaveTranscation;
import com.techvg.shrms.repository.LeaveTranscationRepository;
import com.techvg.shrms.service.criteria.LeaveTranscationCriteria;
import com.techvg.shrms.service.dto.LeaveTranscationDTO;
import com.techvg.shrms.service.mapper.LeaveTranscationMapper;
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
 * Service for executing complex queries for {@link LeaveTranscation} entities in the database.
 * The main input is a {@link LeaveTranscationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveTranscationDTO} or a {@link Page} of {@link LeaveTranscationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveTranscationQueryService extends QueryService<LeaveTranscation> {

    private final Logger log = LoggerFactory.getLogger(LeaveTranscationQueryService.class);

    private final LeaveTranscationRepository leaveTranscationRepository;

    private final LeaveTranscationMapper leaveTranscationMapper;

    public LeaveTranscationQueryService(
        LeaveTranscationRepository leaveTranscationRepository,
        LeaveTranscationMapper leaveTranscationMapper
    ) {
        this.leaveTranscationRepository = leaveTranscationRepository;
        this.leaveTranscationMapper = leaveTranscationMapper;
    }

    /**
     * Return a {@link List} of {@link LeaveTranscationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveTranscationDTO> findByCriteria(LeaveTranscationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveTranscation> specification = createSpecification(criteria);
        return leaveTranscationMapper.toDto(leaveTranscationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LeaveTranscationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveTranscationDTO> findByCriteria(LeaveTranscationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveTranscation> specification = createSpecification(criteria);
        return leaveTranscationRepository.findAll(specification, page).map(leaveTranscationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveTranscationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveTranscation> specification = createSpecification(criteria);
        return leaveTranscationRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveTranscationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveTranscation> createSpecification(LeaveTranscationCriteria criteria) {
        Specification<LeaveTranscation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveTranscation_.id));
            }
            if (criteria.getLeaveType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLeaveType(), LeaveTranscation_.leaveType));
            }
            if (criteria.getEmpId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpId(), LeaveTranscation_.empId));
            }
            if (criteria.getMonthDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMonthDate(), LeaveTranscation_.monthDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), LeaveTranscation_.status));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), LeaveTranscation_.year));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), LeaveTranscation_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), LeaveTranscation_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), LeaveTranscation_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), LeaveTranscation_.createdOn));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), LeaveTranscation_.deleted));
            }
        }
        return specification;
    }
}
