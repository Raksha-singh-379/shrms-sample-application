package com.techvg.shrms.service;

import com.techvg.shrms.domain.*; // for static metamodels
import com.techvg.shrms.domain.BanksDetails;
import com.techvg.shrms.repository.BanksDetailsRepository;
import com.techvg.shrms.service.criteria.BanksDetailsCriteria;
import com.techvg.shrms.service.dto.BanksDetailsDTO;
import com.techvg.shrms.service.mapper.BanksDetailsMapper;
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
 * Service for executing complex queries for {@link BanksDetails} entities in the database.
 * The main input is a {@link BanksDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BanksDetailsDTO} or a {@link Page} of {@link BanksDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BanksDetailsQueryService extends QueryService<BanksDetails> {

    private final Logger log = LoggerFactory.getLogger(BanksDetailsQueryService.class);

    private final BanksDetailsRepository banksDetailsRepository;

    private final BanksDetailsMapper banksDetailsMapper;

    public BanksDetailsQueryService(BanksDetailsRepository banksDetailsRepository, BanksDetailsMapper banksDetailsMapper) {
        this.banksDetailsRepository = banksDetailsRepository;
        this.banksDetailsMapper = banksDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link BanksDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BanksDetailsDTO> findByCriteria(BanksDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BanksDetails> specification = createSpecification(criteria);
        return banksDetailsMapper.toDto(banksDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BanksDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BanksDetailsDTO> findByCriteria(BanksDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BanksDetails> specification = createSpecification(criteria);
        return banksDetailsRepository.findAll(specification, page).map(banksDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BanksDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BanksDetails> specification = createSpecification(criteria);
        return banksDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link BanksDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BanksDetails> createSpecification(BanksDetailsCriteria criteria) {
        Specification<BanksDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BanksDetails_.id));
            }
            if (criteria.getAccountNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountNo(), BanksDetails_.accountNo));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), BanksDetails_.bankName));
            }
            if (criteria.getIfscCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIfscCode(), BanksDetails_.ifscCode));
            }
            if (criteria.getPan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPan(), BanksDetails_.pan));
            }
            if (criteria.getBranch() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranch(), BanksDetails_.branch));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), BanksDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), BanksDetails_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), BanksDetails_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), BanksDetails_.createdOn));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), BanksDetails_.deleted));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), BanksDetails_.employeeId));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(BanksDetails_.employee, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
