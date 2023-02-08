package com.techvg.shrms.repository;

import com.techvg.shrms.domain.ProjectTeams;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProjectTeams entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectTeamsRepository extends JpaRepository<ProjectTeams, Long>, JpaSpecificationExecutor<ProjectTeams> {}
