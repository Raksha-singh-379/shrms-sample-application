package com.techvg.shrms.service;

import com.techvg.shrms.domain.*; // for static metamodels
import com.techvg.shrms.domain.EmployeeDetails;
import com.techvg.shrms.repository.EmployeeDetailsRepository;
import com.techvg.shrms.service.criteria.EmployeeDetailsCriteria;
import com.techvg.shrms.service.dto.EmployeeDetailsDTO;
import com.techvg.shrms.service.mapper.EmployeeDetailsMapper;
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
 * Service for executing complex queries for {@link EmployeeDetails} entities in the database.
 * The main input is a {@link EmployeeDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeDetailsDTO} or a {@link Page} of {@link EmployeeDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeDetailsQueryService extends QueryService<EmployeeDetails> {

    private final Logger log = LoggerFactory.getLogger(EmployeeDetailsQueryService.class);

    private final EmployeeDetailsRepository employeeDetailsRepository;

    private final EmployeeDetailsMapper employeeDetailsMapper;

    public EmployeeDetailsQueryService(EmployeeDetailsRepository employeeDetailsRepository, EmployeeDetailsMapper employeeDetailsMapper) {
        this.employeeDetailsRepository = employeeDetailsRepository;
        this.employeeDetailsMapper = employeeDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDetailsDTO> findByCriteria(EmployeeDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeDetails> specification = createSpecification(criteria);
        return employeeDetailsMapper.toDto(employeeDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDetailsDTO> findByCriteria(EmployeeDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeDetails> specification = createSpecification(criteria);
        return employeeDetailsRepository.findAll(specification, page).map(employeeDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeDetails> specification = createSpecification(criteria);
        return employeeDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeDetails> createSpecification(EmployeeDetailsCriteria criteria) {
        Specification<EmployeeDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeDetails_.id));
            }
            if (criteria.getPassportNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassportNo(), EmployeeDetails_.passportNo));
            }
            if (criteria.getPassportExpDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPassportExpDate(), EmployeeDetails_.passportExpDate));
            }
            if (criteria.getTelephoneNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephoneNo(), EmployeeDetails_.telephoneNo));
            }
            if (criteria.getNationality() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNationality(), EmployeeDetails_.nationality));
            }
            if (criteria.getMaritalStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getMaritalStatus(), EmployeeDetails_.maritalStatus));
            }
            if (criteria.getReligion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReligion(), EmployeeDetails_.religion));
            }
            if (criteria.getIsSpousEmployed() != null) {
                specification = specification.and(buildSpecification(criteria.getIsSpousEmployed(), EmployeeDetails_.isSpousEmployed));
            }
            if (criteria.getNoOfChildren() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfChildren(), EmployeeDetails_.noOfChildren));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), EmployeeDetails_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), EmployeeDetails_.createdOn));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), EmployeeDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), EmployeeDetails_.lastModifiedBy));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), EmployeeDetails_.deleted));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), EmployeeDetails_.employeeId));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAge(), EmployeeDetails_.age));
            }
            if (criteria.getFatherName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFatherName(), EmployeeDetails_.fatherName));
            }
            if (criteria.getMotherName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMotherName(), EmployeeDetails_.motherName));
            }
            if (criteria.getAadharNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAadharNo(), EmployeeDetails_.aadharNo));
            }
            if (criteria.getBloodGroup() != null) {
                specification = specification.and(buildSpecification(criteria.getBloodGroup(), EmployeeDetails_.bloodGroup));
            }
            if (criteria.getDob() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDob(), EmployeeDetails_.dob));
            }
            if (criteria.getExpertise() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExpertise(), EmployeeDetails_.expertise));
            }
            if (criteria.getHobbies() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHobbies(), EmployeeDetails_.hobbies));
            }
            if (criteria.getAreaInterest() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAreaInterest(), EmployeeDetails_.areaInterest));
            }
            if (criteria.getLanguageKnown() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLanguageKnown(), EmployeeDetails_.languageKnown));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), EmployeeDetails_.description));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeDetails_.employee, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
