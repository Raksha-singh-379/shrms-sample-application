package com.techvg.shrms.service;

import com.techvg.shrms.domain.*; // for static metamodels
import com.techvg.shrms.domain.EmpSalaryInfo;
import com.techvg.shrms.repository.EmpSalaryInfoRepository;
import com.techvg.shrms.service.criteria.EmpSalaryInfoCriteria;
import com.techvg.shrms.service.dto.EmpSalaryInfoDTO;
import com.techvg.shrms.service.mapper.EmpSalaryInfoMapper;
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
 * Service for executing complex queries for {@link EmpSalaryInfo} entities in the database.
 * The main input is a {@link EmpSalaryInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmpSalaryInfoDTO} or a {@link Page} of {@link EmpSalaryInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmpSalaryInfoQueryService extends QueryService<EmpSalaryInfo> {

    private final Logger log = LoggerFactory.getLogger(EmpSalaryInfoQueryService.class);

    private final EmpSalaryInfoRepository empSalaryInfoRepository;

    private final EmpSalaryInfoMapper empSalaryInfoMapper;

    public EmpSalaryInfoQueryService(EmpSalaryInfoRepository empSalaryInfoRepository, EmpSalaryInfoMapper empSalaryInfoMapper) {
        this.empSalaryInfoRepository = empSalaryInfoRepository;
        this.empSalaryInfoMapper = empSalaryInfoMapper;
    }

    /**
     * Return a {@link List} of {@link EmpSalaryInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmpSalaryInfoDTO> findByCriteria(EmpSalaryInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmpSalaryInfo> specification = createSpecification(criteria);
        return empSalaryInfoMapper.toDto(empSalaryInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmpSalaryInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmpSalaryInfoDTO> findByCriteria(EmpSalaryInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmpSalaryInfo> specification = createSpecification(criteria);
        return empSalaryInfoRepository.findAll(specification, page).map(empSalaryInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmpSalaryInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmpSalaryInfo> specification = createSpecification(criteria);
        return empSalaryInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link EmpSalaryInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmpSalaryInfo> createSpecification(EmpSalaryInfoCriteria criteria) {
        Specification<EmpSalaryInfo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmpSalaryInfo_.id));
            }
            if (criteria.getSalaryBasis() != null) {
                specification = specification.and(buildSpecification(criteria.getSalaryBasis(), EmpSalaryInfo_.salaryBasis));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), EmpSalaryInfo_.amount));
            }
            if (criteria.getPaymentType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentType(), EmpSalaryInfo_.paymentType));
            }
            if (criteria.getIsPfContribution() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPfContribution(), EmpSalaryInfo_.isPfContribution));
            }
            if (criteria.getPfNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPfNumber(), EmpSalaryInfo_.pfNumber));
            }
            if (criteria.getPfRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPfRate(), EmpSalaryInfo_.pfRate));
            }
            if (criteria.getAdditionalPfRate() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAdditionalPfRate(), EmpSalaryInfo_.additionalPfRate));
            }
            if (criteria.getTotalPfRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPfRate(), EmpSalaryInfo_.totalPfRate));
            }
            if (criteria.getIsEsiContribution() != null) {
                specification = specification.and(buildSpecification(criteria.getIsEsiContribution(), EmpSalaryInfo_.isEsiContribution));
            }
            if (criteria.getEsiNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEsiNumber(), EmpSalaryInfo_.esiNumber));
            }
            if (criteria.getEsiRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEsiRate(), EmpSalaryInfo_.esiRate));
            }
            if (criteria.getAdditionalEsiRate() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAdditionalEsiRate(), EmpSalaryInfo_.additionalEsiRate));
            }
            if (criteria.getTotalEsiRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalEsiRate(), EmpSalaryInfo_.totalEsiRate));
            }
            if (criteria.getEmployeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeId(), EmpSalaryInfo_.employeId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), EmpSalaryInfo_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), EmpSalaryInfo_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), EmpSalaryInfo_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), EmpSalaryInfo_.createdOn));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), EmpSalaryInfo_.deleted));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmpSalaryInfo_.employee, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
