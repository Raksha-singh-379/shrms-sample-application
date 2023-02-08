package com.techvg.shrms.service;

import com.techvg.shrms.domain.*; // for static metamodels
import com.techvg.shrms.domain.Company;
import com.techvg.shrms.repository.CompanyRepository;
import com.techvg.shrms.service.criteria.CompanyCriteria;
import com.techvg.shrms.service.dto.CompanyDTO;
import com.techvg.shrms.service.mapper.CompanyMapper;
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
 * Service for executing complex queries for {@link Company} entities in the database.
 * The main input is a {@link CompanyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompanyDTO} or a {@link Page} of {@link CompanyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanyQueryService extends QueryService<Company> {

    private final Logger log = LoggerFactory.getLogger(CompanyQueryService.class);

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    public CompanyQueryService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    /**
     * Return a {@link List} of {@link CompanyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompanyDTO> findByCriteria(CompanyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Company> specification = createSpecification(criteria);
        return companyMapper.toDto(companyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompanyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyDTO> findByCriteria(CompanyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Company> specification = createSpecification(criteria);
        return companyRepository.findAll(specification, page).map(companyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompanyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Company> specification = createSpecification(criteria);
        return companyRepository.count(specification);
    }

    /**
     * Function to convert {@link CompanyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Company> createSpecification(CompanyCriteria criteria) {
        Specification<Company> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Company_.id));
            }
            if (criteria.getCompanyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyName(), Company_.companyName));
            }
            if (criteria.getContactPerson() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactPerson(), Company_.contactPerson));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Company_.address));
            }
            if (criteria.getPostalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostalCode(), Company_.postalCode));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Company_.email));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), Company_.phoneNumber));
            }
            if (criteria.getMobileNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNumber(), Company_.mobileNumber));
            }
            if (criteria.getWebsiteUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebsiteUrl(), Company_.websiteUrl));
            }
            if (criteria.getFax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFax(), Company_.fax));
            }
            if (criteria.getRegNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegNumber(), Company_.regNumber));
            }
            if (criteria.getGstin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGstin(), Company_.gstin));
            }
            if (criteria.getPan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPan(), Company_.pan));
            }
            if (criteria.getTan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTan(), Company_.tan));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), Company_.createdOn));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Company_.createdBy));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeleted(), Company_.deleted));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Company_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Company_.lastModifiedBy));
            }
            if (criteria.getAddressId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAddressId(), root -> root.join(Company_.address, JoinType.LEFT).get(Address_.id))
                    );
            }
        }
        return specification;
    }
}
