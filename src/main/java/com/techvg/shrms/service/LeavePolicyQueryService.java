package com.techvg.shrms.service;

import com.techvg.shrms.domain.*; // for static metamodels
import com.techvg.shrms.domain.LeavePolicy;
import com.techvg.shrms.repository.LeavePolicyRepository;
import com.techvg.shrms.service.criteria.LeavePolicyCriteria;
import com.techvg.shrms.service.dto.LeavePolicyDTO;
import com.techvg.shrms.service.mapper.LeavePolicyMapper;
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
 * Service for executing complex queries for {@link LeavePolicy} entities in the database.
 * The main input is a {@link LeavePolicyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeavePolicyDTO} or a {@link Page} of {@link LeavePolicyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeavePolicyQueryService extends QueryService<LeavePolicy> {

    private final Logger log = LoggerFactory.getLogger(LeavePolicyQueryService.class);

    private final LeavePolicyRepository leavePolicyRepository;

    private final LeavePolicyMapper leavePolicyMapper;

    public LeavePolicyQueryService(LeavePolicyRepository leavePolicyRepository, LeavePolicyMapper leavePolicyMapper) {
        this.leavePolicyRepository = leavePolicyRepository;
        this.leavePolicyMapper = leavePolicyMapper;
    }

    /**
     * Return a {@link List} of {@link LeavePolicyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeavePolicyDTO> findByCriteria(LeavePolicyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeavePolicy> specification = createSpecification(criteria);
        return leavePolicyMapper.toDto(leavePolicyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LeavePolicyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeavePolicyDTO> findByCriteria(LeavePolicyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeavePolicy> specification = createSpecification(criteria);
        return leavePolicyRepository.findAll(specification, page).map(leavePolicyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeavePolicyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeavePolicy> specification = createSpecification(criteria);
        return leavePolicyRepository.count(specification);
    }

    /**
     * Function to convert {@link LeavePolicyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeavePolicy> createSpecification(LeavePolicyCriteria criteria) {
        Specification<LeavePolicy> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeavePolicy_.id));
            }
            if (criteria.getLeaveType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLeaveType(), LeavePolicy_.leaveType));
            }
            if (criteria.getIsCarryForword() != null) {
                specification = specification.and(buildSpecification(criteria.getIsCarryForword(), LeavePolicy_.isCarryForword));
            }
            if (criteria.getEmployeeType() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeType(), LeavePolicy_.employeeType));
            }
            if (criteria.getGenderLeave() != null) {
                specification = specification.and(buildSpecification(criteria.getGenderLeave(), LeavePolicy_.genderLeave));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), LeavePolicy_.status));
            }
            if (criteria.getTotalLeave() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalLeave(), LeavePolicy_.totalLeave));
            }
            if (criteria.getMaxLeave() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaxLeave(), LeavePolicy_.maxLeave));
            }
            if (criteria.getHasproRataLeave() != null) {
                specification = specification.and(buildSpecification(criteria.getHasproRataLeave(), LeavePolicy_.hasproRataLeave));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), LeavePolicy_.description));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), LeavePolicy_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), LeavePolicy_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), LeavePolicy_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), LeavePolicy_.createdOn));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), LeavePolicy_.deleted));
            }
        }
        return specification;
    }
}
