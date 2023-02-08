package com.techvg.shrms.service;

import com.techvg.shrms.domain.*; // for static metamodels
import com.techvg.shrms.domain.ProjectTeams;
import com.techvg.shrms.repository.ProjectTeamsRepository;
import com.techvg.shrms.service.criteria.ProjectTeamsCriteria;
import com.techvg.shrms.service.dto.ProjectTeamsDTO;
import com.techvg.shrms.service.mapper.ProjectTeamsMapper;
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
 * Service for executing complex queries for {@link ProjectTeams} entities in the database.
 * The main input is a {@link ProjectTeamsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectTeamsDTO} or a {@link Page} of {@link ProjectTeamsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectTeamsQueryService extends QueryService<ProjectTeams> {

    private final Logger log = LoggerFactory.getLogger(ProjectTeamsQueryService.class);

    private final ProjectTeamsRepository projectTeamsRepository;

    private final ProjectTeamsMapper projectTeamsMapper;

    public ProjectTeamsQueryService(ProjectTeamsRepository projectTeamsRepository, ProjectTeamsMapper projectTeamsMapper) {
        this.projectTeamsRepository = projectTeamsRepository;
        this.projectTeamsMapper = projectTeamsMapper;
    }

    /**
     * Return a {@link List} of {@link ProjectTeamsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectTeamsDTO> findByCriteria(ProjectTeamsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectTeams> specification = createSpecification(criteria);
        return projectTeamsMapper.toDto(projectTeamsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProjectTeamsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTeamsDTO> findByCriteria(ProjectTeamsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectTeams> specification = createSpecification(criteria);
        return projectTeamsRepository.findAll(specification, page).map(projectTeamsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectTeamsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectTeams> specification = createSpecification(criteria);
        return projectTeamsRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectTeamsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectTeams> createSpecification(ProjectTeamsCriteria criteria) {
        Specification<ProjectTeams> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectTeams_.id));
            }
            if (criteria.getTeamMemberType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeamMemberType(), ProjectTeams_.teamMemberType));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProjectId(), ProjectTeams_.projectId));
            }
            if (criteria.getEmployeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeId(), ProjectTeams_.employeId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), ProjectTeams_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProjectTeams_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProjectTeams_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), ProjectTeams_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), ProjectTeams_.isDeleted));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), ProjectTeams_.companyId));
            }
        }
        return specification;
    }
}
