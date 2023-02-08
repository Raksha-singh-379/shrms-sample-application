package com.techvg.shrms.service;

import com.techvg.shrms.domain.*; // for static metamodels
import com.techvg.shrms.domain.LeaveApplication;
import com.techvg.shrms.repository.LeaveApplicationRepository;
import com.techvg.shrms.service.criteria.LeaveApplicationCriteria;
import com.techvg.shrms.service.dto.LeaveApplicationDTO;
import com.techvg.shrms.service.mapper.LeaveApplicationMapper;
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
 * Service for executing complex queries for {@link LeaveApplication} entities in the database.
 * The main input is a {@link LeaveApplicationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveApplicationDTO} or a {@link Page} of {@link LeaveApplicationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveApplicationQueryService extends QueryService<LeaveApplication> {

    private final Logger log = LoggerFactory.getLogger(LeaveApplicationQueryService.class);

    private final LeaveApplicationRepository leaveApplicationRepository;

    private final LeaveApplicationMapper leaveApplicationMapper;

    public LeaveApplicationQueryService(
        LeaveApplicationRepository leaveApplicationRepository,
        LeaveApplicationMapper leaveApplicationMapper
    ) {
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.leaveApplicationMapper = leaveApplicationMapper;
    }

    /**
     * Return a {@link List} of {@link LeaveApplicationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveApplicationDTO> findByCriteria(LeaveApplicationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveApplication> specification = createSpecification(criteria);
        return leaveApplicationMapper.toDto(leaveApplicationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LeaveApplicationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveApplicationDTO> findByCriteria(LeaveApplicationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveApplication> specification = createSpecification(criteria);
        return leaveApplicationRepository.findAll(specification, page).map(leaveApplicationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveApplicationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveApplication> specification = createSpecification(criteria);
        return leaveApplicationRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveApplicationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveApplication> createSpecification(LeaveApplicationCriteria criteria) {
        Specification<LeaveApplication> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveApplication_.id));
            }
            if (criteria.getLeaveType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLeaveType(), LeaveApplication_.leaveType));
            }
            if (criteria.getBalanceLeave() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBalanceLeave(), LeaveApplication_.balanceLeave));
            }
            if (criteria.getNoOfDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfDays(), LeaveApplication_.noOfDays));
            }
            if (criteria.getReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReason(), LeaveApplication_.reason));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), LeaveApplication_.year));
            }
            if (criteria.getFormDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFormDate(), LeaveApplication_.formDate));
            }
            if (criteria.getToDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getToDate(), LeaveApplication_.toDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), LeaveApplication_.status));
            }
            if (criteria.getLeaveStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getLeaveStatus(), LeaveApplication_.leaveStatus));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), LeaveApplication_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), LeaveApplication_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), LeaveApplication_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), LeaveApplication_.createdOn));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), LeaveApplication_.deleted));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(LeaveApplication_.employee, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
            if (criteria.getLeavePolicyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeavePolicyId(),
                            root -> root.join(LeaveApplication_.leavePolicy, JoinType.LEFT).get(LeavePolicy_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
