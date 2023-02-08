package com.techvg.shrms.repository;

import com.techvg.shrms.domain.WorkExperience;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WorkExperience entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long>, JpaSpecificationExecutor<WorkExperience> {}
